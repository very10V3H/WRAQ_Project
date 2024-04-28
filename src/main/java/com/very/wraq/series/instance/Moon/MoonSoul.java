package com.very.wraq.series.instance.Moon;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.render.ToolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class MoonSoul extends Item {
    public MoonSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {

        components.add(Component.literal(" 右键以前往:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("[尘月之梦]").withStyle(CustomStyle.styleOfMoon1)));

        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {

            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld,1046.5,227.5,1260.5,0,0);
            player.getCooldowns().addCooldown(this,200);

        }
        return super.use(level, player, interactionHand);
    }
}
