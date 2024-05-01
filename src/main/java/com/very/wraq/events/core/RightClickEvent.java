package com.very.wraq.events.core;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.unSorted.VillagerTradeScreenS2CPacket;
import com.very.wraq.render.gui.villagerTrade.TradeList;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.decoration.ArmorStand;
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

@Mod.EventBusSubscriber
public class RightClickEvent {
    @SubscribeEvent
    public static void RightClick(PlayerInteractEvent.RightClickItem event)
    {
        Player player = event.getEntity();
/*        if(event.getEntity().getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) {
            event.setCanceled(true);
            event.getEntity().sendSystemMessage(Component.literal("Please Input Password First!"));
        }*/
        Item item = player.getMainHandItem().getItem();
        if(Utils.MainHandTag.containsKey(item) || Utils.ArmorTag.containsKey(item)){
            ItemStack mainhand = player.getMainHandItem();
            CompoundTag data = mainhand.getOrCreateTagElement(Utils.MOD_ID);
            if(!data.contains(InventoryCheck.owner)) {
                data.putString(InventoryCheck.owner,player.getName().getString());
            }
        }
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            if(data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString())) event.setCanceled(true);
        }
        if(Utils.ItemRightClickCheck.containsKey(event.getItemStack().getItem()) && !player.isCreative()) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event){
        if (event.getTarget() instanceof ArmorStand) {
            event.setCanceled(true);
        }

        if (event.getSide().isServer() && event.getTarget() instanceof Villager villager) {
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) TradeList.setTradeContent();
            boolean flag = false;
            for (MutableComponent value : StringUtils.VillagerNameMap.values()) {
                if (value.getString().equals(villager.getName().getString())) flag = true;
            }
            if (flag) {
                ModNetworking.sendToClient(new VillagerTradeScreenS2CPacket(
                        villager.getName().getString()),(ServerPlayer) event.getEntity());
                event.setCanceled(true);
            }
        }

        if (event.getSide().isServer() && (event.getTarget() instanceof MinecartChest
                || event.getTarget() instanceof ChestBoat)
                || event.getTarget() instanceof MinecartHopper
                || event.getTarget() instanceof Allay) event.setCanceled(true);

    }
}
