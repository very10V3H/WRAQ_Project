package com.very.wraq.series.overworld.chapter2.lightningIsland;

import com.very.wraq.common.Compute;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightningChange extends Item {
    public LightningChange(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Rare(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("召唤雷电力量").withStyle(CustomStyle.styleOfLightingIsland));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Island").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && level.equals(level.getServer().getLevel(Level.OVERWORLD)) && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.setWeatherParameters(0, 6000, true, true);
            Compute.formatBroad(level, Component.literal("唤雷").withStyle(CustomStyle.styleOfLightingIsland),
                    Component.literal(player.getName().getString() + "使用唤雷符召唤了持续的雷电。").withStyle(ChatFormatting.WHITE));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack.setCount(itemStack.getCount() - 1);
            player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        }
        return super.use(level, player, interactionHand);
    }
}
