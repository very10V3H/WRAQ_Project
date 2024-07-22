package com.very.wraq.series.instance.PurpleIronKnight;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PurpleIronBow extends PickaxeItem implements PurpleIronTier {

    private static final double[] ExAttackDamage = {
            100, 150, 200, 250
    };

    private static final double[] CritDamage = {
            0.15, 0.2, 0.25, 0.35
    };

    private static final double[] Swiftness = {
            0.5, 1, 1.5, 2
    };

    private final int tier;

    public PurpleIronBow(Properties p_40524_, int tier) {
        super(ItemTier.VMaterial, 2, 0, p_40524_);
        this.tier = tier;
        Utils.attackDamage.put(this, ExAttackDamage[tier]);
        Utils.critDamage.put(this, CritDamage[tier]);
        Utils.swiftnessUp.put(this, Swiftness[tier]);
        Utils.passiveEquipTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.levelRequire.put(this, 120);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfPurpleIron;
        Compute.forgingHoverName(stack);
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura).append(Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionPassive(components, Component.literal("晶体析构").withStyle(style));
        components.add(Component.literal(" 基于你与目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("差的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("绝对值").withStyle(style)).
                append(Component.literal("至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%伤害提升").withStyle(style)));
        Compute.LevelRequire(components, Utils.levelRequire.get(this));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfPurpleIronKnight(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public int getPassiveTier() {
        return tier;
    }
}
