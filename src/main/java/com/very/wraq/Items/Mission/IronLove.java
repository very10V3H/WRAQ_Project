package com.very.wraq.Items.Mission;

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

public class IronLove extends Item {
    public IronLove(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("·限定").withStyle(CustomStyle.styleOfGather));
        components.add(Component.literal(" "));
        components.add(Component.literal("来自铁铁铁铁铁头的10V3").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("其实就是每日任务奖励，可以用来兑换一些珍贵物品。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {

        }
        return super.use(level, player, interactionHand);
    }
}
