package com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KazeCore extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand.equals(InteractionHand.OFF_HAND)
                && Utils.SceptreTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem())
                && player.isShiftKeyDown()) {
            ItemStack MainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag MainHandData = MainHand.getOrCreateTagElement(Utils.MOD_ID);
            if (MainHandData.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(MainHandData.getString(StringUtils.ManaCore.ManaCore))));
                MainHandData.putString(StringUtils.ManaCore.ManaCore,StringUtils.ManaCore.KazeCore);
            }
            else {
                player.setItemInHand(InteractionHand.OFF_HAND, Items.AIR.getDefaultInstance());
                MainHandData.putString(StringUtils.ManaCore.ManaCore,StringUtils.ManaCore.KazeCore);
            }
            Compute.ForgingHoverName(player.getItemInHand(InteractionHand.MAIN_HAND),Component.literal(""));

        }
        return super.use(level, player, interactionHand);
    }

    public KazeCore(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.ManaCoreDescription(components);
        Compute.DescriptionPassive(components,Component.literal("狂风晶核").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea));
        components.add(Component.literal("-获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("30%")));
        components.add(Component.literal("-基于你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("")).
                append(Component.literal("提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")));
        components.add(Component.literal("-每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExMovementSpeed("1%")).
                append(Component.literal("提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("1")));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
