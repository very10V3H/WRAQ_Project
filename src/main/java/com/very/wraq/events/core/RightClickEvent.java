package com.very.wraq.events.core;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
import com.very.wraq.render.gui.villagerTrade.MyVillagerData;
import com.very.wraq.render.gui.villagerTrade.TradeList;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber
public class RightClickEvent {

    @SubscribeEvent
    public static void RightClick(PlayerInteractEvent.RightClickItem event) throws IOException {
        Player player = event.getEntity();
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemStack.getItem();
/*        if (event.getSide().isServer()) {
            List<Item> elementPiece0 = new ArrayList<>(){{
                add(ModItems.LifeElementPiece0.get());
                add(ModItems.WaterElementPiece0.get());
                add(ModItems.FireElementPiece0.get());
                add(ModItems.StoneElementPiece0.get());
                add(ModItems.IceElementPiece0.get());
                add(ModItems.LightningElementPiece0.get());
                add(ModItems.WindElementPiece0.get());
            }};
            if (elementPiece0.contains(item) && !player.getCooldowns().isOnCooldown(item)) {
                Compute.PlayerItemUseWithRecord(player);
                Compute.PlayerItemCoolDown(player, item, 2);
                if (item.equals(ModItems.LifeElementPiece0.get())) Element.resonance(player, Element.life);
                if (item.equals(ModItems.WaterElementPiece0.get())) Element.resonance(player, Element.water);
                if (item.equals(ModItems.FireElementPiece0.get())) Element.resonance(player, Element.fire);
                if (item.equals(ModItems.StoneElementPiece0.get())) Element.resonance(player, Element.stone);
                if (item.equals(ModItems.IceElementPiece0.get())) Element.resonance(player, Element.ice);
                if (item.equals(ModItems.LightningElementPiece0.get())) Element.resonance(player, Element.lightning);
                if (item.equals(ModItems.WindElementPiece0.get())) Element.resonance(player, Element.wind);
            }
        }*/
        if (itemStack.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString()))
                event.setCanceled(true);
        }
        if (Utils.ItemRightClickCheck.containsKey(event.getItemStack().getItem()) && !player.isCreative())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event) {
        if (event.getSide().isServer() && event.getTarget() instanceof Villager villager) {
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) TradeList.setTradeContent();
            boolean flag = false;
            for (MutableComponent value : StringUtils.VillagerNameMap.values()) {
                if (value.getString().equals(villager.getName().getString())) flag = true;
            }
            for (MutableComponent value : MyVillagerData.villagerNameMap.values()) {
                if (value.getString().equals(villager.getName().getString())) flag = true;
            }
            if (flag) {
                ModNetworking.sendToClient(new VillagerTradeScreenS2CPacket(
                        villager.getName().getString()), (ServerPlayer) event.getEntity());
                event.setCanceled(true);
            }
        }

        if (event.getSide().isServer() && (event.getTarget() instanceof MinecartChest
                || event.getTarget() instanceof ChestBoat)
                || event.getTarget() instanceof MinecartHopper
                || event.getTarget() instanceof Allay) event.setCanceled(true);
    }
}
