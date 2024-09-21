package com.very.wraq.series.gems.instance.moon;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MoonAttackGem extends Item {
    private final double ManaDefence = 200;
    private final double AttackDamage = 60;

    public MoonAttackGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsManaDefence.put(StringUtils.GemName.MoonAttackGem, ManaDefence);
        Utils.gemsAttackDamage.put(StringUtils.GemName.MoonAttackGem, AttackDamage);
        Utils.manaDefence.put(this, ManaDefence);
        Utils.attackDamage.put(this, AttackDamage);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfMoon;
        components.add(Component.literal("尘月宫明镜之心").withStyle(style));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfMoon(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
