package fun.wraq.events.client;

import fun.wraq.common.equip.impl.Laser;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.BowRequestC2SPacket;
import fun.wraq.core.ManaAttackRequestC2SPacket;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.bowAndSceptreActive.CommonActiveC2SPacket;
import fun.wraq.networking.misc.AnimationPackets.AttackAnimationRequestC2SPacket;
import fun.wraq.networking.misc.AnimationPackets.BowAnimationRequestC2SPacket;
import fun.wraq.networking.misc.AnimationPackets.ManaAttackAnimationRequestC2SPacket;
import fun.wraq.networking.misc.AnimationPackets.UseRequestC2SPacket;
import fun.wraq.networking.misc.AttackPackets.AttackC2SPacket;
import fun.wraq.networking.unSorted.SoulSceptreC2SPacket;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqSceptre;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
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
            Item mainHandWeapon = player.getMainHandItem().getItem();
            if (mainHandWeapon instanceof WraqSceptre) manaAttack(player);
            if (mainHandWeapon instanceof WraqBow) bowAttack(player);
        }
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity().equals(Minecraft.getInstance().player)) ClientUtils.AnimationTickReset();
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem && player.getY() < 15)) {
                leftClick(player);
            }
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
            Item mainHandWeapon = player.getMainHandItem().getItem();
            if (mainHandWeapon instanceof WraqSceptre) manaAttack(player);
            if (mainHandWeapon instanceof WraqBow) bowAttack(player);
        }
    }

    public static int clientAttackCounts = 0;

    public static int clientAttackTickCounts = 0;

    // 客户端侧左键
    public static void leftClick(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (!ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                && !ClientUtils.PlayerIsBowAttacking(player) && !ClientUtils.PlayerIsRolling(player)
                && !Utils.sceptreTag.containsKey(item) && !Utils.bowTag.containsKey(item)) {
            if (player.tickCount > clientAttackTickCounts + 10 || player.tickCount > clientAttackTickCounts + 30) {
                if (clientAttackCounts > 2 || player.tickCount > clientAttackTickCounts + 30) clientAttackCounts = 0;
                clientAttackTickCounts = player.tickCount;
                ModNetworking.sendToServer(new AttackAnimationRequestC2SPacket(clientAttackCounts));
                clientAttackCounts++;
            }
        }
        if ((item instanceof WraqSceptre || item instanceof WraqBow) && item instanceof ActiveItem) {
            ModNetworking.sendToServer(new CommonActiveC2SPacket(item.getDefaultInstance()));
        }
        if (item.equals(ModItems.SoulSceptre.get())) ModNetworking.sendToServer(new SoulSceptreC2SPacket());
    }

    // 客户端侧的发包
    @SubscribeEvent
    public static void attackC2STickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Item mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (player.tickCount - clientAttackTickCounts == 8 && clientAttackTickCounts != 0
                    && (Utils.swordTag.containsKey(mainHandItem))) {
                ModNetworking.sendToServer(new AttackC2SPacket(clientAttackCounts - 1));
            }
            if (player.tickCount - ClientUtils.ManaAttackTick == 5 && ClientUtils.ManaAttackTick != 0
                    && Utils.sceptreTag.containsKey(mainHandItem) && !(mainHandItem instanceof Laser)) {
                ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
            }
            if (player.tickCount - ClientUtils.UseTick == 8 && ClientUtils.UseTick != 0) {
                ModNetworking.sendToServer(new UseRequestC2SPacket(0));
            }
            if (player.tickCount - ClientUtils.BowAttackTick == 5 && ClientUtils.BowAttackTick != 0
                    && Utils.bowTag.containsKey(mainHandItem)) {
                ModNetworking.sendToServer(new BowRequestC2SPacket());
            }
        }
    }

    public static void manaAttack(Player player) {
        if (player.level().isClientSide) {
            if (player.tickCount - 10 > ClientUtils.ManaAttackTick && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsAttacking(player) && !ClientUtils.PlayerIsBowAttacking(player)
                    && !ClientUtils.PlayerIsRolling(player)) {
                ModNetworking.sendToServer(new ManaAttackAnimationRequestC2SPacket(0));
                ClientUtils.ManaAttackTick = player.tickCount;
            }
        }
    }

    public static void bowAttack(Player player) {
        if (player.level().isClientSide) {
            if (player.tickCount - 10 > ClientUtils.BowAttackTick && !ClientUtils.PlayerIsAttacking(player)
                    && !ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsRolling(player)) {
                ClientUtils.BowAttackTick = player.tickCount;
                ModNetworking.sendToServer(new BowAnimationRequestC2SPacket(0));
            }
        }
    }
}
