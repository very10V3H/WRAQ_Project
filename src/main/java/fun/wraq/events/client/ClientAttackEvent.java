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
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
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

    /**
     * 客户端玩家进入游戏（登录完成）时重置按住攻击状态。
     * 修复：PlayerEvent.PlayerLoggedInEvent 只在服务端触发，客户端拿到的是服务端玩家实体，
     * 与本地玩家不是同一个对象，因此原先的登录重置永远不会执行。
     * 这里改用只在客户端触发的 ClientPlayerNetworkEvent。
     */
    @SubscribeEvent
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        resetHoldState();
    }

    @SubscribeEvent
    public static void onClientPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
        resetHoldState();
    }

    private static void resetHoldState() {
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
    /** 上次通过按住逻辑发送攻击的 client tick */
    private static int lastHoldAttackTick = 0;

    /** 按住攻击的最小间隔（tick） */
    private static final int HOLD_ATTACK_INTERVAL = 4;

    /**
     * 每个 client tick 调用一次，检查左键是否按住并在间隔结束后发包。
     * 从客户端的 PlayerTickEvent 中调用。
     *
     * 修复：不再自行监听 InputEvent.MouseButton 记录左键状态。
     * 自己维护的布尔值会因窗口失焦 / 释放事件丢失而一直保持为 true，
     * 客户端进程未重启时重新登录进入游戏就会自动连续释放普通攻击与主动效果。
     * 改为读取原版 keyAttack 的按键状态：原版在打开界面、窗口失焦时会 releaseAll()，
     * 不会出现状态残留，语义也与原版“按住左键自动攻击”一致。
     */
    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null || mc.screen != null) return;
        if (!mc.options.keyAttack.isDown()) return;

        int currentTick = player.tickCount;
        // 玩家实体被重建（重新进入游戏 / 死亡重生）时 tickCount 会重新从小值开始，
        // 此时必须重置记录，否则会长时间无法按住攻击。
        if (currentTick < lastHoldAttackTick) lastHoldAttackTick = 0;
        if (currentTick - lastHoldAttackTick < HOLD_ATTACK_INTERVAL) return;
        lastHoldAttackTick = currentTick;

        leftClick(player);
        activeBowAndSceptre(player);
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
