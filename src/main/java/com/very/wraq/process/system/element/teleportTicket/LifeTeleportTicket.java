package com.very.wraq.process.system.element.teleportTicket;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
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

import java.util.List;

public class LifeTeleportTicket extends Item {

    public LifeTeleportTicket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && player.experienceLevel >= 200) {
            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld, 1105.5, 126, 1079.5, 180, 0);
            player.getCooldowns().addCooldown(this, 2400);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以前往 - ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("生机岛").withStyle(CustomStyle.styleOfLife)));
        Compute.CoolDownTimeDescription(components, 120);
        Compute.LevelRequire(components, 200);
        Compute.suffixOfElement(components);
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}
