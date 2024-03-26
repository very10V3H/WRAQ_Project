package com.Very.very.Events.MainEvents;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.VillagerTradeScreenS2CPacket;
import com.Very.very.Render.Gui.VillagerTrade.Trade;
import com.Very.very.Render.Gui.VillagerTrade.TradeList;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.SkyBow;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            if(!data.contains("Owner"))
            {
                data.putString("Owner",player.getName().getString());
            }
        }
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            if(data.contains("Owner") && !data.getString("Owner").equals(player.getName().getString())) event.setCanceled(true);
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
                || event.getTarget() instanceof MinecartHopper) event.setCanceled(true);

    }
}
