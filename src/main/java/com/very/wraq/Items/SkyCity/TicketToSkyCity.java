package com.very.wraq.Items.SkyCity;

import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TicketToSkyCity extends Item {
    public TicketToSkyCity(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
/*        player.getCooldowns().addCooldown(this,1200);
        if(!level.isClientSide) {
            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.teleportTo(overWorld,43.5,121.5,1052.5,0,0);
        }*/
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        stack.setHoverName(Component.literal("<纪念品>").withStyle(CustomStyle.styleOfSakura).
                append(Component.literal("前往天空城的船票").withStyle(ChatFormatting.BLUE)));
        components.add(Component.literal(" "));
        components.add(Component.literal("前往天空城").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.STRIKETHROUGH));
        components.add(Component.literal("冷却时间：60s").withStyle(ChatFormatting.STRIKETHROUGH));
        components.add(Component.literal(" "));
        components.add(Component.literal("随着维瑞阿契铁路交通系统的完善，这件物品正式成为了一件纪念品。").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("Souvenirs-2023.12.14").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
