package com.Very.very.Items.MainStory_1;

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

public class BackSpawn extends Item {

    public BackSpawn(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.getCooldowns().addCooldown(this,1200);
        if(!level.isClientSide) {
            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld,350.5,64,889.5,0,0);
        }
        return super.use(level, player, interactionHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" "));
        components.add(Component.literal("右键返回出生点").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("冷却时间：60s"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Present").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
