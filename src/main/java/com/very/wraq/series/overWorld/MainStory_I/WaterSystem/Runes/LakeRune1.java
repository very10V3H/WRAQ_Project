package com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

public class LakeRune1 extends Item{
    public LakeRune1(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        Compute.DescriptionPassive(components,Component.literal("水之张力").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal(" 将你").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("50%")).
                append(Component.literal("转化为等量").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("LakeRunes").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide) {
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.LakeRune,1);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(ChatFormatting.GRAY),Component.literal("湖泊符石 - 水之张力").withStyle(ChatFormatting.BLUE).
                    append(Component.literal(" 已激活！").withStyle(ChatFormatting.WHITE)));
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    public static double CoolDownDown(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.LakeRune) && data.getInt(StringUtils.LakeRune) == 1) {
            return 0.5;
        }
        return 1;
    }

    public static double ExCritDamage(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.LakeRune) && data.getInt(StringUtils.LakeRune) == 1) {
            return PlayerAttributes.PlayerPowerReleaseSpeed(player);
        }
        return 0;
    }








}
