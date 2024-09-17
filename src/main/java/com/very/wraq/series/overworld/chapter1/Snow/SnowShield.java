package com.very.wraq.series.overworld.chapter1.Snow;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter1.Mine.MineShield;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SnowShield extends Item {
    private final double Defence = 175;
    private final double MaxHealth = 350;
    private final double AttackDamage = 10;
    private final double CritRate = 0.05;
    private final double ExpUp = 0.75;

    public SnowShield() {
        super(new Properties().rarity(CustomStyle.SnowItalic).stacksTo(1));
        Utils.defence.put(this, Defence);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.attackDamage.put(this, AttackDamage);
        Utils.critRate.put(this, CritRate);
        Utils.expUp.put(this, ExpUp);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style MainStyle = CustomStyle.styleOfSnow;
        stack.setHoverName(Component.literal("玉山圆盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.GRAY)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        MineShield.shieldAdditionDescription(components);
        Compute.DescriptionPassive(components, Component.literal("破碎冰玉").withStyle(MainStyle));
        components.add(Component.literal(" 造成暴击后，击碎目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("25%")).
                append(Component.literal("并提升自身等额").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal(" 持续2s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        components.add(Component.literal("SnowShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
