package com.very.wraq.Items.Money;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ResetSecurity extends Item {
    public ResetSecurity(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Utils.Vplayer = player;
        Utils.Security = true;
        if (!level.isClientSide) {
            Utils.security0 = 64;
            Utils.security1 = 64;
            Utils.security2 = 64;
            Utils.security3 = 64;
            player.getPersistentData().putDouble("PersistentSecurity0", 64);
            player.getPersistentData().putDouble("PersistentSecurity1", 64);
            player.getPersistentData().putDouble("PersistentSecurity2", 64);
            player.getPersistentData().putDouble("PersistentSecurity3", 64);
        }
        return super.use(level, player, interactionHand);
    }
}
