package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HoverTest extends Item {

    public HoverTest(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        player.sendSystemMessage(itemStack.getDisplayName());
        player.sendSystemMessage(itemStack.getHoverName());
        player.sendSystemMessage(itemStack.getHighlightTip(itemStack.getDisplayName()));
        return super.use(level, player, interactionHand);
    }
}

