package fun.wraq.process.system.backpack.audit;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.backpack.audit.networking.AuditBackpackContentsS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.AccessLogRecord;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackAccessLogger;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContext;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryProvider;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AI-Generated, 2026-10-18
 * 管理员「按内容 UUID 打开精妙背包」的界面复用层。
 *
 * <p>目标：不发放任何真实背包物品，直接打开精妙背包原版 GUI（含物品格、升级格、设置页）。
 *
 * <p>实现原理（不写 mixin、不注册自定义菜单）：
 * <ol>
 *     <li>精妙背包的 {@code BackpackContainer} 只认 {@link BackpackContext}；
 *     {@code BackpackContext.Item} 解析背包 wrapper 的方式是
 *     {@code PlayerInventoryProvider.getPlayerInventoryHandler(handlerName).getStackInSlot(player, identifier, slot)}
 *     —— 也就是「按名字注册的玩家物品栏」。</li>
 *     <li>本类在 {@link #init()} 里用 {@code addPlayerInventoryHandler} 注册一个虚拟物品栏
 *     {@value #HANDLER_NAME}，它的槽位内容由 {@code identifier} 现场合成：
 *     一个只写了 {@code contentsUuid} / 格数 / 颜色的临时背包栈。</li>
 *     <li>打开时构造 {@code BackpackContext.Item(HANDLER_NAME, identifier, 0, true)} 并
 *     走 {@code NetworkHooks.openScreen}，客户端用同一个 identifier 合成同构物品栈，
 *     于是两端都拿到指向同一份 {@code BackpackStorage} 内容的 wrapper。</li>
 * </ol>
 *
 * <p>注意事项：
 * <ul>
 *     <li>identifier 里带会话序号，保证每次审计都拿到「新的临时栈 → 新的 wrapper」，
 *     避免复用旧 {@code InventoryHandler} 读到过期内容。</li>
 *     <li>同一个会话内必须返回同一个 {@link ItemStack} 实例：{@code BackpackContainer}
 *     用 wrapper 的引用相等判断背包物品是否被换掉（{@code storageItemHasChanged}），
 *     实例变化会导致 GUI 每 tick 自动关闭。</li>
 *     <li>虚拟物品栏的 identifiers 返回空集合，使 SB 自己的 {@code runOnBackpacks} 扫描
 *     （UUID 去重、拾取升级等）完全跳过它。</li>
 * </ul>
 */
public final class AuditBackpackProvider {

    /**
     * AI-Generated, 2026-10-18
     * 虚拟玩家物品栏名称，同时作为 {@code BackpackContext.Item#handlerName}。
     */
    public static final String HANDLER_NAME = "vmd_audit";

    private static final Logger LOGGER = LogUtils.getLogger();

    /** identifier 分隔符 */
    private static final String SEP = "|";

    /** 标识解析失败时的兜底配置 */
    private static final int DEFAULT_INVENTORY_SLOTS = 27;

    /**
     * AI-Generated, 2026-10-18
     * 每个（逻辑端, 会话）一个临时背包栈。
     *
     * <p>注意：单机时客户端与服务端在同一个 JVM 里共用静态字段，如果两端拿到同一个
     * {@link ItemStack} 实例，Forge 的能力缓存会让两端共用同一个 {@code BackpackWrapper}
     * （以及同一个 {@code InventoryHandler}）——客户端点击会直接改服务端 handler 再被服务端
     * 二次应用，堆叠 / 堆叠升级就会错乱。所以缓存 key 必须带逻辑端前缀。
     */
    private static final Map<String, ItemStack> STACK_CACHE = Collections.synchronizedMap(
            new LinkedHashMap<>(16, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, ItemStack> eldest) {
                    return size() > 64;
                }
            });

    private static final String SERVER_KEY_PREFIX = "S|";

    private static final String CLIENT_KEY_PREFIX = "C|";

    private static final AtomicInteger SESSION = new AtomicInteger();

    /**
     * AI-Generated, 2026-10-18
     * 待打开的请求：先把手上的内容同步给客户端，等客户端回执后再真正打开界面，
     * 这样客户端构造 {@code BackpackContainer} 时已经能从 {@code BackpackStorage}
     * 客户端副本里读到完整的物品与升级数据（升级页签、排序等才正确）。
     */
    private record PendingOpen(UUID admin, UUID contentsUuid, String identifier, String name, long createdAt) {
    }

    private static final Map<Integer, PendingOpen> PENDING = new ConcurrentHashMap<>();

    private static final AtomicInteger REQUEST = new AtomicInteger();

    /** 打开请求等待客户端回执的最长时间（tick），超时丢弃 */
    private static final long REQUEST_TIMEOUT = 100;

    private AuditBackpackProvider() {
    }

    /** 注册虚拟玩家物品栏（客户端与服务端都要注册，FMLCommonSetupEvent 调用一次） */
    public static void init() {
        PlayerInventoryProvider.get().addPlayerInventoryHandler(
                HANDLER_NAME,
                gameTime -> Collections.<String>emptySet(),
                (player, identifier) -> 1,
                (player, identifier, slot) -> stackFor(player, identifier),
                false, // visibleInGui：false 才不会锁住玩家物品栏第 0 格
                false, // 是否有自己的渲染
                false, // 是否有自己的 renderer
                false // 是否允许其他玩家访问
        );
        LOGGER.info("[VMD-背包审计] 已注册虚拟背包来源 {}", HANDLER_NAME);
    }

    /**
     * 以管理员身份打开指定内容 UUID 的精妙背包 GUI。
     *
     * @return true 表示已下发打开界面的指令（是否真正显示取决于客户端）
     */
    public static boolean open(ServerPlayer admin, UUID contentsUuid) {
        AccessLogRecord record = BackpackAccessLogger.getBackpackLog(contentsUuid).orElse(null);
        Item item = record == null ? null : ForgeRegistries.ITEMS.getValue(record.getBackpackItemRegistryName());
        BackpackItem backpackItem;
        if (item instanceof BackpackItem backpack) {
            backpackItem = backpack;
        } else {
            backpackItem = (BackpackItem) net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.NETHERITE_BACKPACK.get();
        }

        String itemId = ForgeRegistries.ITEMS.getKey(backpackItem).toString();
        int columns = record == null ? 0 : record.getColumnsTaken();
        int cloth = record == null ? BackpackWrapper.DEFAULT_CLOTH_COLOR : record.getClothColor();
        int trim = record == null ? BackpackWrapper.DEFAULT_BORDER_COLOR : record.getTrimColor();
        String name = record == null ? "" : record.getBackpackName();
        int inventorySlots = resolveInventorySlots(contentsUuid, backpackItem);
        int upgradeSlots = backpackItem.getNumberOfUpgradeSlots();

        String identifier = encode(contentsUuid, itemId, inventorySlots, upgradeSlots,
                columns, cloth, trim, name, SESSION.incrementAndGet());
        // 每次会话都重新构建临时栈，避免复用上一次的 InventoryHandler（可能已经过期）
        STACK_CACHE.remove(SERVER_KEY_PREFIX + identifier);

        int requestId = REQUEST.incrementAndGet();
        PENDING.put(requestId, new PendingOpen(admin.getUUID(), contentsUuid, identifier, name,
                admin.level().getGameTime()));
        // 先把内容同步到客户端副本，客户端回执后再打开界面（见 onClientReady）
        CompoundTag contents = BackpackStorage.get().getOrCreateBackpackContents(contentsUuid).copy();
        ModNetworking.sendToClient(new AuditBackpackContentsS2CPacket(contentsUuid, contents, requestId), admin);
        return true;
    }

    /** 客户端已把内容写入自己的 BackpackStorage 副本，此处在服务端真正打开界面 */
    public static void onClientReady(ServerPlayer admin, int requestId) {
        PendingOpen pending = PENDING.remove(requestId);
        if (pending == null || !pending.admin().equals(admin.getUUID())) {
            return;
        }
        openNow(admin, pending);
    }

    /**
     * AI-Generated, 2026-10-18
     * 打开期间被临时移出玩家物品栏的同类背包，用于在打开后原样放回。
     */
    private record HiddenBackpack(int slot, ItemStack stack) {
    }

    private static void openNow(ServerPlayer admin, PendingOpen pending) {
        BackpackContext.Item context = new BackpackContext.Item(HANDLER_NAME, pending.identifier(), 0, true);
        Component title = Component.literal(pending.name().isEmpty() ? "背包审计" : pending.name())
                .withStyle(ChatFormatting.DARK_AQUA);
        // BackpackContainer 构造里会跑 UUIDDeduplicator：它把「除打开的这个栈之外、同 UUID 的其他背包」
        // 当成复制品并抹掉 contentsUuid。审计用的是合成栈，如果不暂时把这些真背包藏起来，
        // 玩家/管理员身上那个真背包的 UUID 会被抹掉（下次打开就会生成新 UUID、看起来内容全空）。
        java.util.List<HiddenBackpack> hidden = hideSameUuidBackpacks(admin, pending.contentsUuid());
        try {
            NetworkHooks.openScreen(admin,
                    new SimpleMenuProvider((id, inventory, player) -> new BackpackContainer(id, player, context), title),
                    context::toBuffer);
        } finally {
            restoreHiddenBackpacks(admin, hidden);
        }
        LOGGER.info("[VMD-背包审计] 管理员 {} 打开背包 {}", admin.getName().getString(), pending.contentsUuid());
        Compute.sendFormatMSG(admin, Te.s("背包审计", ChatFormatting.AQUA),
                Te.s("已打开背包 ", ChatFormatting.WHITE, pending.contentsUuid().toString(),
                        "（关闭界面后自动释放占用）"));
    }

    /** 把管理员物品栏（含盔甲/副手）里同 UUID 的真实背包临时移出，避免被 SB 的去重器误删 UUID */
    private static java.util.List<HiddenBackpack> hideSameUuidBackpacks(ServerPlayer admin, UUID contentsUuid) {
        java.util.List<HiddenBackpack> hidden = new java.util.ArrayList<>();
        net.minecraft.world.entity.player.Inventory inventory = admin.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!(stack.getItem() instanceof BackpackItem) || stack.isEmpty()) {
                continue;
            }
            UUID uuid = new BackpackWrapper(stack).getContentsUuid().orElse(null);
            if (!contentsUuid.equals(uuid)) {
                continue;
            }
            hidden.add(new HiddenBackpack(i, stack));
            inventory.setItem(i, ItemStack.EMPTY);
        }
        return hidden;
    }

    /** 把临时移出的背包原样放回；槽位被占用时退回物品栏或掉落，绝不丢失 */
    private static void restoreHiddenBackpacks(ServerPlayer admin, java.util.List<HiddenBackpack> hidden) {
        if (hidden.isEmpty()) {
            return;
        }
        net.minecraft.world.entity.player.Inventory inventory = admin.getInventory();
        for (HiddenBackpack hiddenBackpack : hidden) {
            if (inventory.getItem(hiddenBackpack.slot()).isEmpty()) {
                inventory.setItem(hiddenBackpack.slot(), hiddenBackpack.stack());
            } else if (!inventory.add(hiddenBackpack.stack())) {
                admin.drop(hiddenBackpack.stack(), false);
            }
        }
        admin.inventoryMenu.broadcastChanges();
        if (admin.containerMenu != null && admin.containerMenu != admin.inventoryMenu) {
            admin.containerMenu.broadcastChanges();
        }
    }

    /** 每 20 tick 清理客户端迟迟未回执的请求 */
    public static void tick(long gameTime) {
        if (PENDING.isEmpty()) {
            return;
        }
        for (Map.Entry<Integer, PendingOpen> entry : PENDING.entrySet()) {
            PendingOpen pending = entry.getValue();
            if (gameTime - pending.createdAt() > REQUEST_TIMEOUT) {
                PENDING.remove(entry.getKey(), pending);
                LOGGER.warn("[VMD-背包审计] 打开请求超时（客户端未回执），uuid={}", pending.contentsUuid());
            }
        }
    }

    /** 服务器关闭时清理 */
    public static void clearPending() {
        PENDING.clear();
    }

    /** 物品格数量：优先取存档里最后一次保存的 Size，取不到就用该背包物品类型的默认值 */
    private static int resolveInventorySlots(UUID contentsUuid, BackpackItem backpackItem) {
        try {
            CompoundTag contents = BackpackStorage.get().getOrCreateBackpackContents(contentsUuid);
            CompoundTag inventory = contents.getCompound("inventory");
            if (inventory.contains("Size")) {
                int size = inventory.getInt("Size");
                if (size > 0) {
                    return size;
                }
            }
        } catch (Exception ignored) {
            // 内容尚未生成（新背包）时走默认值
        }
        int slots = backpackItem.getNumberOfSlots();
        return slots > 0 ? slots : DEFAULT_INVENTORY_SLOTS;
    }

    /** 按（逻辑端 + identifier）取临时背包栈，保证同一端同一会话内是同一个实例 */
    private static ItemStack stackFor(net.minecraft.world.entity.player.Player player, String identifier) {
        String sidePrefix = player != null && player.level().isClientSide ? CLIENT_KEY_PREFIX : SERVER_KEY_PREFIX;
        String key = sidePrefix + identifier;
        return STACK_CACHE.computeIfAbsent(key, ignored -> createStack(identifier));
    }

    /**
     * 按 identifier 合成临时背包物品栈（服务端与客户端必须得到同构结果）。
     * identifier 结构：{@code uuid|itemId|inventorySlots|upgradeSlots|columns|cloth|trim|session|base64Name}
     */
    private static ItemStack createStack(String identifier) {
        String[] parts = identifier.split("\\" + SEP, -1);
        Item fallback = net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.NETHERITE_BACKPACK.get();
        if (parts.length < 9) {
            LOGGER.error("[VMD-背包审计] 非法的背包标识: {}", identifier);
            return new ItemStack(fallback);
        }
        try {
            UUID contentsUuid = UUID.fromString(parts[0]);
            ResourceLocation itemId = ResourceLocation.tryParse(parts[1]);
            Item item = itemId == null ? null : ForgeRegistries.ITEMS.getValue(itemId);
            if (!(item instanceof BackpackItem)) {
                item = fallback;
            }
            int inventorySlots = Integer.parseInt(parts[2]);
            int upgradeSlots = Integer.parseInt(parts[3]);
            int columns = Integer.parseInt(parts[4]);
            int cloth = Integer.parseInt(parts[5]);
            int trim = Integer.parseInt(parts[6]);
            String name = decodeName(parts[8]);

            ItemStack stack = new ItemStack(item);
            BackpackWrapper wrapper = new BackpackWrapper(stack);
            wrapper.setContentsUuid(contentsUuid);
            wrapper.setSlotNumbers(inventorySlots, upgradeSlots);
            wrapper.setColumnsTaken(columns, false);
            wrapper.setColors(cloth, trim);
            stack.setHoverName(Component.literal(name.isEmpty() ? "背包审计" : name)
                    .withStyle(ChatFormatting.DARK_AQUA));
            return stack;
        } catch (Exception e) {
            LOGGER.error("[VMD-背包审计] 构建虚拟背包失败: {}", identifier, e);
            return new ItemStack(fallback);
        }
    }

    /** 从任意容器菜单反查精妙背包内容 UUID（非背包菜单返回 null） */
    public static UUID contentsUuidOf(net.minecraft.world.inventory.AbstractContainerMenu menu,
                                      net.minecraft.world.entity.player.Player player) {
        if (menu instanceof net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase<?> storageMenu
                && storageMenu.getStorageWrapper()
                instanceof net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper wrapper) {
            return wrapper.getContentsUuid().orElse(null);
        }
        // 背包设置页（BackpackSettingsContainerMenu）不在 StorageContainerMenuBase 体系里，走 context 反查
        if (player != null
                && menu instanceof net.p3pp3rf1y.sophisticatedbackpacks.common.gui.IContextAwareContainer contextAware) {
            BackpackContext context = contextAware.getBackpackContext();
            if (context != null) {
                return context.getBackpackWrapper(player).getContentsUuid().orElse(null);
            }
        }
        return null;
    }

    private static String encode(UUID contentsUuid, String itemId, int inventorySlots, int upgradeSlots,
                                 int columns, int cloth, int trim, String name, int session) {
        return String.join(SEP,
                contentsUuid.toString(),
                itemId,
                Integer.toString(inventorySlots),
                Integer.toString(upgradeSlots),
                Integer.toString(columns),
                Integer.toString(cloth),
                Integer.toString(trim),
                Integer.toString(session),
                encodeName(name));
    }

    private static String encodeName(String name) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(name.getBytes(StandardCharsets.UTF_8));
    }

    private static String decodeName(String encoded) {
        if (encoded == null || encoded.isEmpty()) {
            return "";
        }
        try {
            return new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}
