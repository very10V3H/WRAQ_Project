package com.very.wraq.render.gui.testAndHelper;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SmartPhoneOpen extends Item {

    public SmartPhoneOpen(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            List<ServerPlayer> list = level.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer : list) {
                CompoundTag data = serverPlayer.getPersistentData();
                for (int i = 0; i < Utils.AttributeName.length; i++) {
                    if (data.contains(Utils.AttributeName[i])) {
                        data.putDouble(Utils.AttributeName[i], 0);
                    }
                }
            }
            /* DistExecutor.safeCallWhenOn(Dist.CLIENT,() -> OpenMarketGui::new);*/
        }
        return super.use(level, player, interactionHand);
    }
}
