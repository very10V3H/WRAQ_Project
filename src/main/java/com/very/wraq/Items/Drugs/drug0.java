package com.very.wraq.Items.Drugs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class drug0 extends Item {

    public drug0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND && !level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack.setCount(itemStack.getCount() - 1);
            player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
            player.heal(10);
            player.getCooldowns().addCooldown(this, 200);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" "));
        components.add(Component.literal("右键服用，恢复10点生命值").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" "));
        components.add(Component.literal("冷却时间:10s"));
        super.appendHoverText(stack, level, components, flag);
    }
}
