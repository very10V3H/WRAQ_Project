package fun.wraq.process.func.item;

import com.mojang.logging.LogUtils;
import fun.wraq.process.system.backpack.BackpackData;
import fun.wraq.process.system.backpack.BackpackFileManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * AI-Generated, 2026-10-18
 * 玩家持有物品审计工具。
 *
 * <p>一次性收集玩家以下位置的全部物品：
 * <ol>
 *     <li>物品栏 —— 36 格主背包 + 4 件盔甲 + 副手（{@link Player#getInventory()}）</li>
 *     <li>饰品栏 —— Curios 的全部已装备槽位</li>
 *     <li>VMD 自定义背包 —— {@code process.system.backpack} 中按 UUID 存档的个人背包（B 键打开的那个）</li>
 * </ol>
 * 遇到「精妙背包（Sophisticated Backpacks）」时会递归展开其内容，因此背包里再套背包
 * （Inception 升级）也能被一并统计。
 *
 * <p><b>使用前提</b>
 * <ul>
 *     <li>只能在服务端主线程调用：精妙背包的内容并不存在 {@link ItemStack} 的 NBT 里，
 *     而是存在世界的 {@code BackpackStorage}（SavedData）中，由物品 NBT 的 {@code contentsUuid}
 *     索引；{@code BackpackStorage.get()} 依赖当前服务端实例，客户端线程或异步线程调用会抛异常。</li>
 *     <li>遍历得到的 {@link ItemStack} 是「活的」引用，只读统计没问题，若要做修改请先 {@code copy()}。</li>
 * </ul>
 *
 * <p><b>注意</b>
 * <ul>
 *     <li>不要用 {@code Compute.CuriosAttribute.getDistinctCuriosList(player)} 做审计：
 *     它按物品去重并带缓存，会漏掉重复饰品。</li>
 *     <li>不要用 {@code inventory.getMaxStackSize()} 当物品栏长度（{@code Security#fixDuplicatedBackpack}
 *     里就是 64），物品栏请用 {@code getContainerSize()}（41）。</li>
 *     <li>仅读取、不主动创建：没有 {@code contentsUuid} 的精妙背包被视为空背包直接跳过，
 *     避免给它随机生成一个新的 UUID 并写入存档。</li>
 * </ul>
 */
public final class ItemAudit {

    /** 嵌套精妙背包的最大展开层数（防止异常存档造成无限递归） */
    public static final int MAX_BACKPACK_DEPTH = 8;

    /**
     * 审计结果中的一条记录。
     *
     * @param stack 物品（活引用，只读）
     * @param where 来源路径，如 {@code inventory[12]>backpack[3]>backpack[7]}
     */
    public record FoundItem(ItemStack stack, String where) {
    }

    private ItemAudit() {
    }

    /** 收集物品栏 + 饰品栏 + VMD 自定义背包（含递归展开的精妙背包）中的全部物品 */
    public static List<FoundItem> collectAll(Player player) {
        return collectAll(player, true);
    }

    /**
     * @param includeVmdBackpack 是否包含 VMD 自定义背包；离线玩家或背包系统未初始化时可传 false
     */
    public static List<FoundItem> collectAll(Player player, boolean includeVmdBackpack) {
        List<FoundItem> result = new ArrayList<>();
        Set<UUID> visitedBackpacks = new HashSet<>();

        // 1. 物品栏：36 主背包 + 4 盔甲 + 副手
        scanContainer(player.getInventory(), "inventory", 0, visitedBackpacks, result);

        // 2. 饰品栏：直接遍历底层 IItemHandler，不做去重
        CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler ->
                scanHandler(iCuriosItemHandler.getEquippedCurios(), "curios", 0, visitedBackpacks, result));

        // 3. VMD 自定义背包
        if (includeVmdBackpack && player instanceof ServerPlayer serverPlayer) {
            BackpackData backpackData = BackpackFileManager.get(serverPlayer.getUUID());
            scanHandler(backpackData.getBackpackHandler(), "vmd_backpack", 0, visitedBackpacks, result);
        }
        return result;
    }

    /**
     * 按「内容 UUID」直接读取某个精妙背包的全部内容，用于管理员审计（不依赖任何玩家或物品栏）。
     *
     * <p>做法是造一个临时背包物品，只给它写上 {@code contentsUuid}，再让 {@link BackpackWrapper}
     * 去世界的 {@code BackpackStorage} 里取内容；这个临时物品不进入任何人的物品栏，也不改访问日志，
     * 读操作也不会回写存档（{@code BackpackStorage.getOrCreateBackpackContents} 对不存在的 UUID
     * 只会在内存里放一个不会被标记为脏的空 Tag）。
     *
     * <p>UUID 来源：{@code /sbp list} 输出的背包名（悬停可见 UUID），或玩家物品 NBT 的
     * {@code contentsUuid}。需在服务端主线程调用。
     */
    public static List<FoundItem> collectFromBackpackUuid(UUID contentsUuid) {
        List<FoundItem> result = new ArrayList<>();
        ItemStack probe = new ItemStack(
                net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.NETHERITE_BACKPACK.get());
        BackpackWrapper wrapper = new BackpackWrapper(probe);
        wrapper.setContentsUuid(contentsUuid);
        Set<UUID> visitedBackpacks = new HashSet<>();
        visitedBackpacks.add(contentsUuid); // 让嵌套里出现同一 UUID 时不再展开
        scanHandler(wrapper.getInventoryHandler(), "backpack(" + contentsUuid + ")",
                0, visitedBackpacks, result);
        return result;
    }

    /** 遍历任意原版容器（物品栏、末影箱、容器方块……） */
    public static void scanContainer(Container container, String prefix, int depth,
                                     Set<UUID> visitedBackpacks, List<FoundItem> out) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            handleStack(container.getItem(i), prefix + "[" + i + "]", depth, visitedBackpacks, out);
        }
    }

    /** 遍历任意 Forge {@link IItemHandler}（饰品栏槽位、精妙背包内容、自定义背包……） */
    public static void scanHandler(IItemHandler handler, String prefix, int depth,
                                   Set<UUID> visitedBackpacks, List<FoundItem> out) {
        for (int i = 0; i < handler.getSlots(); i++) {
            handleStack(handler.getStackInSlot(i), prefix + "[" + i + "]", depth, visitedBackpacks, out);
        }
    }

    /** 单格处理：记录物品本身，若为精妙背包则递归展开其内容 */
    private static void handleStack(ItemStack stack, String where, int depth,
                                    Set<UUID> visitedBackpacks, List<FoundItem> out) {
        if (stack.isEmpty()) {
            return;
        }
        out.add(new FoundItem(stack, where));
        if (!(stack.getItem() instanceof BackpackItem) || depth >= MAX_BACKPACK_DEPTH) {
            return;
        }
        // 精妙背包的内容在 BackpackStorage 里，用物品 NBT 的 contentsUuid 索引
        BackpackWrapper wrapper = new BackpackWrapper(stack);
        Optional<UUID> contentsUuid = wrapper.getContentsUuid();
        if (contentsUuid.isEmpty()) {
            return; // 从未打开过的空背包
        }
        if (!visitedBackpacks.add(contentsUuid.get())) {
            return; // 内容已展开过：重复 UUID（复制 bug）或背包嵌套回环
        }
        scanHandler(wrapper.getInventoryHandler(), where + ">backpack", depth + 1, visitedBackpacks, out);
    }

    /** 按物品合并数量（含背包内嵌套物品） */
    public static Map<Item, Integer> countByItem(List<FoundItem> items) {
        Map<Item, Integer> countMap = new LinkedHashMap<>();
        for (FoundItem foundItem : items) {
            countMap.merge(foundItem.stack().getItem(), foundItem.stack().getCount(), Integer::sum);
        }
        return countMap;
    }

    /** 某个物品的总数量（含背包内嵌套物品） */
    public static int count(Player player, Item item) {
        return countByItem(collectAll(player)).getOrDefault(item, 0);
    }

    /** 查找某个物品的所有来源位置，便于审计「这个物品藏在哪」 */
    public static List<FoundItem> find(Player player, Item item) {
        List<FoundItem> foundItems = new ArrayList<>();
        for (FoundItem foundItem : collectAll(player)) {
            if (foundItem.stack().is(item)) {
                foundItems.add(foundItem);
            }
        }
        return foundItems;
    }

    /* ===================== 离线玩家 ===================== */

    private static final Logger LOGGER = LogUtils.getLogger();

    /** 玩家 NBT 里 ItemStack 的形状（id + Count），用于深层扫描 */
    private static final int MAX_TAG_DEPTH = 24;

    /**
     * 收集**离线**玩家持有的物品。
     *
     * <p>玩家在线时直接委托 {@link #collectAll(Player)}；离线时读取
     * {@code <world>/playerdata/<UUID>.dat}，深层扫描其中的物品栏、末影箱、Curios 等结构。
     * 精妙背包的内容不在玩家存档里（在世界的 {@code BackpackStorage} 中，按 UUID 索引），
     * 所以嵌套背包同样能一层层展开，不受玩家在线状态影响。
     *
     * <p>VMD 自定义背包是按 UUID 的独立文件（{@code <world>/data/vmd/backpacks/<UUID>.dat}），
     * 离线同样可读。
     *
     * <p>必须在服务端主线程调用。
     */
    public static List<FoundItem> collectAllOffline(MinecraftServer server, UUID playerUuid) {
        ServerPlayer online = server.getPlayerList().getPlayer(playerUuid);
        if (online != null) {
            return collectAll(online);
        }
        List<FoundItem> result = new ArrayList<>();
        Set<UUID> visitedBackpacks = new HashSet<>();
        CompoundTag playerData = readPlayerData(server, playerUuid);
        if (playerData != null) {
            scanTagForItems(playerData, "playerdata", 0, visitedBackpacks, result);
        }
        try {
            BackpackData vmdBackpack = BackpackFileManager.get(playerUuid);
            scanHandler(vmdBackpack.getBackpackHandler(), "vmd_backpack", 0, visitedBackpacks, result);
        } catch (Exception e) {
            LOGGER.error("[VMD-审计] 读取离线玩家 {} 的 VMD 背包失败", playerUuid, e);
        }
        return result;
    }

    private static CompoundTag readPlayerData(MinecraftServer server, UUID playerUuid) {
        try {
            Path playerDataDir = server.getWorldPath(LevelResource.PLAYER_DATA_DIR);
            Path file = playerDataDir.resolve(playerUuid + ".dat");
            if (!Files.exists(file)) {
                LOGGER.warn("[VMD-审计] 找不到离线玩家存档 {}", file);
                return null;
            }
            return NbtIo.readCompressed(file.toFile());
        } catch (Exception e) {
            LOGGER.error("[VMD-审计] 读取离线玩家 {} 存档失败", playerUuid, e);
            return null;
        }
    }

    /** 深层扫描 NBT：遇到 ItemStack 形状就收集，并继续展开其中的精妙背包内容 */
    private static void scanTagForItems(CompoundTag tag, String where, int depth,
                                        Set<UUID> visitedBackpacks, List<FoundItem> out) {
        if (depth > MAX_TAG_DEPTH) {
            return;
        }
        if (isItemStackTag(tag)) {
            ItemStack stack = ItemStack.of(tag);
            if (!stack.isEmpty()) {
                out.add(new FoundItem(stack, where));
                scanNestedBackpackContents(stack, where, depth + 1, visitedBackpacks, out);
            }
            return;
        }
        for (String key : tag.getAllKeys()) {
            Tag child = tag.get(key);
            if (child instanceof CompoundTag compoundTag) {
                scanTagForItems(compoundTag, where + "/" + key, depth + 1, visitedBackpacks, out);
            } else if (child instanceof ListTag listTag && listTag.getElementType() == Tag.TAG_COMPOUND) {
                for (int i = 0; i < listTag.size(); i++) {
                    scanTagForItems(listTag.getCompound(i), where + "/" + key + "[" + i + "]",
                            depth + 1, visitedBackpacks, out);
                }
            }
        }
    }

    private static boolean isItemStackTag(CompoundTag tag) {
        return tag.contains("id", Tag.TAG_STRING) && tag.contains("Count", Tag.TAG_ANY_NUMERIC);
    }

    /** 从世界存档的背包内容里展开嵌套背包（离线也能用） */
    private static void scanNestedBackpackContents(ItemStack stack, String where, int depth,
                                                   Set<UUID> visitedBackpacks, List<FoundItem> out) {
        if (!(stack.getItem() instanceof BackpackItem) || depth >= MAX_BACKPACK_DEPTH) {
            return;
        }
        UUID contentsUuid = new BackpackWrapper(stack).getContentsUuid().orElse(null);
        if (contentsUuid == null || !visitedBackpacks.add(contentsUuid)) {
            return;
        }
        try {
            CompoundTag contents = BackpackStorage.get().getOrCreateBackpackContents(contentsUuid);
            ListTag items = contents.getCompound("inventory").getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < items.size(); i++) {
                CompoundTag entry = items.getCompound(i);
                ItemStack inner = ItemStack.of(entry);
                if (inner.isEmpty()) {
                    continue;
                }
                String innerWhere = where + ">backpack[" + entry.getInt("Slot") + "]";
                out.add(new FoundItem(inner, innerWhere));
                scanNestedBackpackContents(inner, innerWhere, depth + 1, visitedBackpacks, out);
            }
        } catch (Exception e) {
            LOGGER.error("[VMD-审计] 展开背包 {} 内容失败", contentsUuid, e);
        }
    }
}
