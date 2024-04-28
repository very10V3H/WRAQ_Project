package com.very.wraq.series.overWorld.SakuraSeries.Boss2;

import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GoldenBook extends Item {
    private final double ManaDamage = 800;
    private final double ManaPenetration0 = 600;
    private final double MaxMana = 50;
    private final double MovementSpeed = 1;
    private final double ExpUp = 1.5;
    public GoldenBook(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaPenetration0.put(this,ManaPenetration0);
        Utils.MaxMana.put(this,MaxMana);
        Utils.MovementSpeed.put(this,MovementSpeed);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    private final Style style = CustomStyle.styleOfGold;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("黄金圣经").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("华贵金属").withStyle(style));
        components.add(Component.literal(" 1.当你拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-力凝魔核").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("每").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("50")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaRecover("1")));
        components.add(Component.literal(" -当你拥有超过").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritRate("100%")).
                append(Component.literal("，使溢出部分以1:2转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        components.add(Component.literal(" 2.当你拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-术法全析").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("每").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("75最大")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CoolDown("8")));
        components.add(Component.literal(" -当你拥有超过").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("240")).
                append(Component.literal("，溢出部分将以1:0.6提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaRecover("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    public static double GoldenBookManaRecover1(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,10) == 10 && player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.GoldenBook.get())) {
            return (Compute.PlayerAttributes.PlayerMaxMana(player) + 100) / 50;
        }
        return 0;
    }

    public static double GoldenBookCritDamage(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,10) == 10
                && player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.GoldenBook.get())
                && Compute.PlayerAttributes.PlayerCritRate(player) > 1) {
            return (Compute.PlayerAttributes.PlayerCritRate(player) - 1) * 2;
        }
        return 0;
    }

    public static double GoldenBookCoolDown(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,11) == 10
                && player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.GoldenBook.get())) {
            return (Compute.PlayerAttributes.PlayerMaxMana(player) + 100) * 0.08 / 75;
        }
        return 0;
    }

    public static double GoldenBookManaRecover2(Player player) {
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,11) == 10
                && player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.GoldenBook.get())
                && Compute.PlayerAttributes.PlayerPowerReleaseSpeed(player) > 2.4) {
            return (Compute.PlayerAttributes.PlayerPowerReleaseSpeed(player) - 2.4) * 60;
        }
        return 0;
    }
}
