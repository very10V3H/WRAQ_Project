package com.Very.very.Events.ClientEvents;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryCheckEvent {
    @SubscribeEvent
    public static void InventoryCheck(TickEvent.PlayerTickEvent event)
    {
        if(event.player.tickCount % 20 == 0 && event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Player player = event.player;
            Inventory inventory = player.getInventory();
            for(int i = 0; i < inventory.getMaxStackSize(); i++)
            {
                if (inventory.getItem(i).getTagElement(Utils.MOD_ID) != null) {
                    CompoundTag data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID);
                    ItemStack itemStack = inventory.getItem(i);
                    if(data.contains("Owner") && !data.getString("Owner").equals(player.getName().getString()))
                    {
                        Player ItemOwner = player.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        if(ItemOwner == null)
                        {
                            LogUtils.getLogger().info("ItemOwner is null!");
                            inventory.removeItem(itemStack);
                        }
                        else
                        {
                            Compute.Broad(player.level(), Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal("已将玩家"+player.getName().getString()+"背包中不属于他的").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).append(Component.literal("转移到"+ItemOwner.getName().getString()+"的背包中。"))));
                            ItemOwner.addItem(itemStack);
                            inventory.removeItem(itemStack);
                        }
                    }
                }
            }
        }
    }
}
