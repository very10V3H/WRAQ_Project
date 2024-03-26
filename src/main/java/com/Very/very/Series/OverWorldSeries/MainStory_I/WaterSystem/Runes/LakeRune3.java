package com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Runes;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class LakeRune3 extends Item{
    public LakeRune3(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        Compute.DescriptionPassive(components,Component.literal("热量交换").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        components.add(Component.literal(" 处于水中时，为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("200")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("LakeRunes").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide) {
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.LakeRune,3);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),Component.literal("湖泊符石 - 热量交换").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE).
                    append(Component.literal(" 已激活！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    public static double ExCoolDown(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.LakeRune) && data.getInt(StringUtils.LakeRune) == 3 && player.isInWater()) {
            return 2;
        }
        return 0;
    }









}
