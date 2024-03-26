package com.Very.very.Events.ClientEvents;

import com.Very.very.Customize.Players.shangmengli.ShangMengLiSword;
import com.Very.very.NetWorking.*;
import com.Very.very.NetWorking.BowAndSceptre.CastleBowC2SPacket;
import com.Very.very.NetWorking.BowAndSceptre.CastleSceptreC2SPacket;
import com.Very.very.NetWorking.Customized.*;
import com.Very.very.NetWorking.Packets.AnimationPackets.*;
import com.Very.very.NetWorking.Packets.AttackPackets.AttackC2SPacket;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
                    && !Utils.SceptreTag.containsKey(item) && !Utils.BowTag.containsKey(item))
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
                    && !Utils.SceptreTag.containsKey(item) && !Utils.BowTag.containsKey(item))
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
                    && !Utils.SceptreTag.containsKey(item) && !Utils.BowTag.containsKey(item))
                SameModule(player);
            CustomizeModule(player);
        }
    }

    public static int ClientAttackCounts = 0;

    public static int ClientAttackTickCounts = 0;

    public static void SameModule(Player player) {
        if (ShangMengLiSword.isOn(player) && ShangMengLiSword.ShangMengLiAnimationClientTick > 0) return;
        if (player.tickCount > ClientAttackTickCounts + 10 || player.tickCount > ClientAttackTickCounts + 30) {
            if (ClientAttackCounts > 2 || player.tickCount > ClientAttackTickCounts + 30) ClientAttackCounts = 0;
            ClientAttackTickCounts = player.tickCount;
            ModNetworking.sendToServer(new AttackAnimationRequestC2SPacket(ClientAttackCounts));
            ClientAttackCounts ++;
        }
    }

    public static void CustomizeModule(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLi_Sceptre.get())) {
            ModNetworking.sendToServer(new ShangMengLiC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShowDickerBow.get())) {
            ModNetworking.sendToServer(new ShowDickerBowC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SoulSceptre.get())) {
            ModNetworking.sendToServer(new SoulSceptreC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
            SameModule(player);
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get())) {
            ModNetworking.sendToServer(new WcBowC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.BlackFeisaSceptre.get())) {
            ModNetworking.sendToServer(new BlackFeisaC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.EliaoiBook.get())) {
            ModNetworking.sendToServer(new EliaoiC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get())) {
            ModNetworking.sendToServer(new GuangYiBowC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.QiFuBow.get())) {
            ModNetworking.sendToServer(new QiFuSceptreC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLiSword.get())) {
            SameModule(player);
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CgswdSceptre.get())) {
            ModNetworking.sendToServer(new CgswdC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.YxwgBow.get())) {
            ModNetworking.sendToServer(new YxwgC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MyMissionBow.get())) {
            ModNetworking.sendToServer(new MyMissionC2SPacket());
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LeiyanBow.get())) {
            ModNetworking.sendToServer(new LeiyanC2SPacket());
        }
        if (item.equals(ModItems.CastleBow.get())) ModNetworking.sendToServer(new CastleBowC2SPacket());
        if (item.equals(ModItems.CastleSceptre.get())) ModNetworking.sendToServer(new CastleSceptreC2SPacket());
    }

    @SubscribeEvent
    public static void AttackC2STickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Item MainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (player.tickCount - ClientAttackTickCounts == 8 && ClientAttackTickCounts != 0
                    && (Utils.SwordTag.containsKey(MainHandItem )
                    || MainHandItem.equals(ModItems.LiuLiXianSword.get())
                    || MainHandItem.equals(ModItems.ShangMengLiSword.get()))) {
                ModNetworking.sendToServer(new AttackC2SPacket(ClientAttackCounts - 1));
            }
            if (player.tickCount - ClientUtils.ManaAttackTick == 5 && ClientUtils.ManaAttackTick != 0
                    && Utils.SceptreTag.containsKey(MainHandItem)) {
                ModNetworking.sendToServer(new ManaAttackRequestC2SPacket(ClientUtils.ManaAttackCounts));
            }
            if (player.tickCount - ClientUtils.UseTick == 8 && ClientUtils.UseTick != 0) {
                ModNetworking.sendToServer(new UseRequestC2SPacket(0));
            }
            if (player.tickCount - ClientUtils.BowAttackTick == 5 && ClientUtils.BowAttackTick != 0
                    && Utils.BowTag.containsKey(MainHandItem)) {
                ModNetworking.sendToServer(new BowRequestC2SPacket(ClientUtils.BowAttackCounts));
            }
        }
    }
}
