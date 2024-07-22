package com.very.wraq.events.client;

import com.very.wraq.common.Compute;
import com.very.wraq.core.BowRequestC2SPacket;
import com.very.wraq.core.ManaAttackRequestC2SPacket;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.bowAndSceptreActive.*;
import com.very.wraq.networking.misc.AnimationPackets.AttackAnimationRequestC2SPacket;
import com.very.wraq.networking.misc.AnimationPackets.UseRequestC2SPacket;
import com.very.wraq.networking.misc.AttackPackets.AttackC2SPacket;
import com.very.wraq.networking.unSorted.SoulSceptreC2SPacket;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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
            if (mainHandWeapon instanceof WraqSceptre) Compute.manaAttack(player);
            if (mainHandWeapon instanceof WraqBow) Compute.bowAttack(player);
        }
    }

    @SubscribeEvent
    public static void ChangeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity().equals(Minecraft.getInstance().player)) ClientUtils.AnimationTickReset();
    }

    @SubscribeEvent
    public static void ClientAttackTimeAndCount(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem && player.getY() < 15)
                    && !ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsBowAttacking(player) && !ClientUtils.PlayerIsRolling(player)
                    && !Utils.sceptreTag.containsKey(item) && !Utils.bowTag.containsKey(item))
                SameModule(player);
            CustomizeModule(player);
        }
    }

    @SubscribeEvent
    public static void ClientAttackTimeAndCount(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (!ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsBowAttacking(player) && !ClientUtils.PlayerIsRolling(player)
                    && !Utils.sceptreTag.containsKey(item) && !Utils.bowTag.containsKey(item))
                SameModule(player);
            CustomizeModule(player);
        }
    }

    @SubscribeEvent
    public static void ClientAttackTimeAndCount(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (!ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsBowAttacking(player) && !ClientUtils.PlayerIsRolling(player)
                    && !Utils.sceptreTag.containsKey(item) && !Utils.bowTag.containsKey(item))
                SameModule(player);
            CustomizeModule(player);
            Item mainHandWeapon = player.getMainHandItem().getItem();
            if (mainHandWeapon instanceof WraqSceptre) Compute.manaAttack(player);
            if (mainHandWeapon instanceof WraqBow) Compute.bowAttack(player);
        }
    }

    public static int ClientAttackCounts = 0;

    public static int ClientAttackTickCounts = 0;

    public static void SameModule(Player player) {
        if (player.tickCount > ClientAttackTickCounts + 10 || player.tickCount > ClientAttackTickCounts + 30) {
            if (ClientAttackCounts > 2 || player.tickCount > ClientAttackTickCounts + 30) ClientAttackCounts = 0;
            ClientAttackTickCounts = player.tickCount;
            ModNetworking.sendToServer(new AttackAnimationRequestC2SPacket(ClientAttackCounts));
            ClientAttackCounts++;
        }
    }

    public static void CustomizeModule(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if ((item instanceof WraqSceptre || item instanceof WraqBow) && item instanceof ActiveItem) {
            ModNetworking.sendToServer(new CommonActiveC2SPacket(item.getDefaultInstance()));
        }
        if (item.equals(ModItems.SoulSceptre.get())) ModNetworking.sendToServer(new SoulSceptreC2SPacket());
    }

    @SubscribeEvent
    public static void AttackC2STickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Item MainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (player.tickCount - ClientAttackTickCounts == 8 && ClientAttackTickCounts != 0
                    && (Utils.swordTag.containsKey(MainHandItem))) {
                ModNetworking.sendToServer(new AttackC2SPacket(ClientAttackCounts - 1));
            }
            if (player.tickCount - ClientUtils.ManaAttackTick == 5 && ClientUtils.ManaAttackTick != 0
                    && Utils.sceptreTag.containsKey(MainHandItem)) {
                ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
            }
            if (player.tickCount - ClientUtils.UseTick == 8 && ClientUtils.UseTick != 0) {
                ModNetworking.sendToServer(new UseRequestC2SPacket(0));
            }
            if (player.tickCount - ClientUtils.BowAttackTick == 5 && ClientUtils.BowAttackTick != 0
                    && Utils.bowTag.containsKey(MainHandItem)) {
                ModNetworking.sendToServer(new BowRequestC2SPacket());
            }
        }
    }
}
