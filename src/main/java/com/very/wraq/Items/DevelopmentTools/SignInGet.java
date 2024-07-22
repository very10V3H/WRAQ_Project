package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SignInGet extends Item {
    public SignInGet(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal(player.getPersistentData().getString("SignIn")));
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
