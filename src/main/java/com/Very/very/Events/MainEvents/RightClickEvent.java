package com.Very.very.Events.MainEvents;

import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
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
        if(event.getEntity().getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
        {
            event.setCanceled(true);
            event.getEntity().sendSystemMessage(Component.literal("Please Input Password First!"));
        }
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
        if(Utils.ItemRightClickCheck.isEmpty()) Utils.ItemRightClickCheckInit();
        if(Utils.ItemRightClickCheck.containsKey(event.getItemStack().getItem()) && !player.isCreative()) event.setCanceled(true);
    }
}
