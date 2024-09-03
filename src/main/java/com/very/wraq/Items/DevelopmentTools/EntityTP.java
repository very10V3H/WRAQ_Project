package com.very.wraq.Items.DevelopmentTools;

import com.very.wraq.common.util.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EntityTP extends Item {

    public EntityTP(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Utils.EntityCopy.moveTo(player.getX(), player.getY(), player.getZ());
        return super.use(level, player, interactionHand);
    }
}
