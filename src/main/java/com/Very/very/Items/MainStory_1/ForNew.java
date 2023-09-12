package com.Very.very.Items.MainStory_1;

import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForNew extends Item {
    public ForNew(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack RewardPack = player.getItemInHand(InteractionHand.MAIN_HAND);
            RewardPack.setCount(0);
            player.setItemInHand(InteractionHand.MAIN_HAND,RewardPack);
            player.addItem(Moditems.Main0.get().getDefaultInstance());
            ItemStack food = Items.GOLDEN_CARROT.getDefaultInstance();
            food.setCount(64);
            player.addItem(food);
            player.addItem(Moditems.ExploreNote.get().getDefaultInstance());
            player.addItem(Moditems.Knife.get().getDefaultInstance());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("右键打开它!"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Present").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
