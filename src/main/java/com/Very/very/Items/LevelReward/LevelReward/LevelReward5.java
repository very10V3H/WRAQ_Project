package com.Very.very.Items.LevelReward.LevelReward;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class LevelReward5 extends Item implements ICurioItem {
    public LevelReward5(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        int rate = 1;
        Compute.ComponentAddLevelReward(components,rate);

        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        if(player.experienceLevel >= 5) data.putBoolean("LevelReward5",true);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        CompoundTag data = player.getPersistentData();
        data.putBoolean("LevelReward5",false);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide)
        {
            if(player.experienceLevel >= 10 && interactionHand.equals(InteractionHand.MAIN_HAND))
            {
                Compute.RandomPotionBagProvider(player,5,0.5);
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                itemStack.setCount(itemStack.getCount()-1);
                player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            }
        }
        return super.use(level, player, interactionHand);
    }
}
