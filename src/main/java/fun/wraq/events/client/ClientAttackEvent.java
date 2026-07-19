package fun.wraq.events.client;

import fun.wraq.common.equip.*;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.Laser;
import fun.wraq.common.equip.impl.PreventLeftClickShoot;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.bowAndSceptreActive.CommonActiveC2SPacket;
import fun.wraq.networking.misc.attack.AttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.BowAttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.ManaAttackRequestC2SPacket;
import fun.wraq.networking.unSorted.SoulSceptreC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientAttackEvent {

    @SubscribeEvent
    public static void clientShootManaAndArrow0(PlayerInteractEvent event) {
        if (event.getSide().isClient()) {
            Player player = event.getEntity();
            Item item = player.getMainHandItem().getItem();
            if (item instanceof WraqBow || item instanceof WraqSceptre) {
                if (item instanceof WraqBow) {
                    ModNetworking.sendToServer(new BowAttackRequestC2SPacket());
                }
                if (item instanceof WraqSceptre && !(item instanceof Laser)) {
                    ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
                }
            }
        }
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity().equals(Minecraft.getInstance().player)) ClientUtils.AnimationTickReset();
        resetHoldState();
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().equals(Minecraft.getInstance().player)) {
            resetHoldState();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().equals(Minecraft.getInstance().player)) {
            resetHoldState();
        }
    }

    private static void resetHoldState() {
        isLeftClickDown = false;
        lastHoldAttackTick = 0;
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (!(item instanceof WraqPickaxe)) {
                leftClick(player);
                activeBowAndSceptre(player);
                if (!player.isCreative() && !(item instanceof AxeItem)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
            activeBowAndSceptre(player);
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
            activeBowAndSceptre(player);
        }
    }

    // === 按住左键连续攻击 ===
    /** 鼠标左键是否处于按下状态 */
    private static boolean isLeftClickDown = false;

    /** 上次通过按住逻辑发送攻击的 client tick */
    private static int lastHoldAttackTick = 0;

    /** 按住攻击的最小间隔（tick） */
    private static final int HOLD_ATTACK_INTERVAL = 4;

    /**
     * 监听鼠标按键，追踪左键的按下/释放状态。
     * 仅用于连续攻击的开关，不替代现有的左键事件。
     */
    @SubscribeEvent
    public static void onMouseButton(InputEvent.MouseButton event) {
        if (event.getButton() == 0) { // 0 = 左键
            if (event.getAction() == 1) { // PRESS
                isLeftClickDown = true;
            } else if (event.getAction() == 0) { // RELEASE
                isLeftClickDown = false;
            }
        }
    }

    /**
     * 每个 client tick 调用一次，检查左键是否按住并在冷却结束后发包。
     * 从客户端的 PlayerTickEvent / ClientTickEvent 中调用。
     */
    public static void tick() {
        if (!isLeftClickDown) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        int currentTick = mc.player.tickCount;
        if (currentTick - lastHoldAttackTick < HOLD_ATTACK_INTERVAL || currentTick <= lastHoldAttackTick) return;
        lastHoldAttackTick = currentTick;

        leftClick(mc.player);
        activeBowAndSceptre(mc.player);
    }

    // 客户端侧左键发包
    public static void leftClick(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (item instanceof WraqSword) {
            ModNetworking.sendToServer(new AttackRequestC2SPacket());
        }
        if (item instanceof WraqBow && !(item instanceof PreventLeftClickShoot)) {
            ModNetworking.sendToServer(new BowAttackRequestC2SPacket());
        }
        if (item instanceof WraqSceptre && !(item instanceof Laser)
                && !(item instanceof PreventLeftClickShoot)) {
            ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
        }
    }

    public static void activeBowAndSceptre(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if ((item instanceof WraqSceptre || item instanceof WraqBow) && item instanceof ActiveItem) {
            ModNetworking.sendToServer(new CommonActiveC2SPacket(item.getDefaultInstance()));
        }
        if (item.equals(ModItems.SOUL_SCEPTRE.get())) {
            ModNetworking.sendToServer(new SoulSceptreC2SPacket());
        }
    }
}
