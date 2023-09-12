package com.Very.very.Items.DevelopmentTools;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InventoryCheck extends Item {

    public InventoryCheck(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.sendSystemMessage(Component.literal(String.valueOf(Compute.ItemCheck(player,Moditems.snowsword3.get().getDefaultInstance()))));
        return super.use(level, player, interactionHand);
    }
}
