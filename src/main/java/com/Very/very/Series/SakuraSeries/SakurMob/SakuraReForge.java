package com.Very.very.Series.SakuraSeries.SakurMob;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SakuraReForge extends Item {
    public SakuraReForge(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Epic(components);
        components.add(Component.literal("·改造图").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("樱花改造图纸").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("对装备性能进行调整。"));
        components.add(Component.literal("调整属性与范围:"));
        components.add(Component.literal("1.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("-20% ~ 20%")));
        components.add(Component.literal("2.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.BreakDefence("-100% ~ 50%")));
        components.add(Component.literal("3.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritRate("-25% ~ 50%")));
        components.add(Component.literal("4.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("-10% ~ 10%")));
        components.add(Component.literal("5.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.HealthSteal("-10% ~ 10%")));
        components.add(Component.literal("6.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("-25% ~ 50%")));
        components.add(Component.literal(" "));
        components.add(Component.literal("ReForge-Sakura").withStyle(Utils.styleOfSakura).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
