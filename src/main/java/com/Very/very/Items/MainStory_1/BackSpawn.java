package com.Very.very.Items.MainStory_1;

import com.Very.very.ValueAndTools.Utils.Utils;
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
        if(!level.isClientSide) {
            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld,437.5,68,917.5,0,0);
            player.getCooldowns().addCooldown(this,200);
        }
        return super.use(level, player, interactionHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        stack.setHoverName(Component.literal("<纪念品>").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura).
                append(Component.literal("回城卷轴").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        components.add(Component.literal(" "));
        components.add(Component.literal("右键返回出生点").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("冷却时间：10min"));
        components.add(Component.literal(" "));
        components.add(Component.literal("随着维瑞阿契铁路交通系统的完善，这件物品正式成为了一件纪念品。").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.STRIKETHROUGH));
        components.add(Component.literal("于2023.12.19日测试中重新启用。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Component.literal("Souvenirs-2023.12.14").withStyle(Utils.styleOfSakura).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}
