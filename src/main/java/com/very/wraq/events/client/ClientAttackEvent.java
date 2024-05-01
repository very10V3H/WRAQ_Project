package com.very.wraq.events.client;

import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import com.very.wraq.netWorking.bowAndSceptreActive.*;
import com.very.wraq.netWorking.customized.*;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AnimationPackets.AttackAnimationRequestC2SPacket;
import com.very.wraq.coreAttackModule.BowRequestC2SPacket;
import com.very.wraq.coreAttackModule.ManaAttackRequestC2SPacket;
import com.very.wraq.netWorking.misc.AnimationPackets.UseRequestC2SPacket;
import com.very.wraq.netWorking.misc.AttackPackets.AttackC2SPacket;
import com.very.wraq.netWorking.unSorted.SoulSceptreC2SPacket;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
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
        if (item.equals(ModItems.ShangMengLi_Sceptre.get())) ModNetworking.sendToServer(new ShangMengLiC2SPacket());
        if (item.equals(ModItems.ShowDickerBow.get())) ModNetworking.sendToServer(new ShowDickerBowC2SPacket());
        if (item.equals(ModItems.SoulSceptre.get())) ModNetworking.sendToServer(new SoulSceptreC2SPacket());
        if (item.equals(ModItems.LiuLiXianSword.get())) SameModule(player);
        if (item.equals(ModItems.WcBow.get())) ModNetworking.sendToServer(new WcBowC2SPacket());
        if (item.equals(ModItems.BlackFeisaSceptre.get())) ModNetworking.sendToServer(new BlackFeisaC2SPacket());
        if (item.equals(ModItems.EliaoiBook.get())) ModNetworking.sendToServer(new EliaoiC2SPacket());
        if (item.equals(ModItems.GuangYiBow.get())) ModNetworking.sendToServer(new GuangYiBowC2SPacket());
        if (item.equals(ModItems.QiFuBow.get())) ModNetworking.sendToServer(new QiFuSceptreC2SPacket());
        if (item.equals(ModItems.ShangMengLiSword.get())) SameModule(player);
        if (item.equals(ModItems.CgswdSceptre.get())) ModNetworking.sendToServer(new CgswdC2SPacket());
        if (item.equals(ModItems.YxwgBow.get())) ModNetworking.sendToServer(new YxwgC2SPacket());
        if (item.equals(ModItems.MyMissionBow.get())) ModNetworking.sendToServer(new MyMissionC2SPacket());
        if (item.equals(ModItems.LeiyanBow.get())) ModNetworking.sendToServer(new LeiyanC2SPacket());
        if (item.equals(ModItems.CastleBow.get())) ModNetworking.sendToServer(new CastleBowC2SPacket());
        if (item.equals(ModItems.CastleSceptre.get())) ModNetworking.sendToServer(new CastleSceptreC2SPacket());
        if (item.equals(ModItems.SoraSword.get())) ModNetworking.sendToServer(new SoraSwordC2SPacket());
        if (item.equals(ModItems.LifeElementBow.get())) ModNetworking.sendToServer(new LifeElementBowC2SPacket());
        if (item.equals(ModItems.LifeElementSceptre.get())) ModNetworking.sendToServer(new LifeElementSceptreC2SPacket());
        if (item.equals(ModItems.WaterElementBow.get())) ModNetworking.sendToServer(new WaterElementBowC2SPacket());
        if (item.equals(ModItems.WaterElementSceptre.get())) ModNetworking.sendToServer(new WaterElementSceptreC2SPacket());
        if (item.equals(ModItems.FireElementBow.get())) ModNetworking.sendToServer(new FireElementBowC2SPacket());
        if (item.equals(ModItems.FireElementSceptre.get())) ModNetworking.sendToServer(new FireElementSceptreC2SPacket());
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
