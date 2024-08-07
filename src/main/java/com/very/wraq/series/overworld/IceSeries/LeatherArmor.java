package com.very.wraq.series.overworld.IceSeries;

import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LeatherArmor extends ArmorItem {
    private final double Defence = 33;
    private final double MaxHealth = 333;
    private final double BootsMovementSpeed = 0.4;
    private final Style style = CustomStyle.styleOfSnow;
    private final int Num;
    private final String[] Type = {
            "头盔", "胸甲", "护腿", "靴子"
    };

    public LeatherArmor(ItemMaterial Material, Type Slots, int Num) {
        super(Material, Slots, new Properties().rarity(CustomStyle.SnowItalic));
        this.Num = Num;
        Utils.defence.put(this, Defence);
        if (Num != 3) Utils.maxHealth.put(this, MaxHealth);
        if (Num == 3) Utils.movementSpeedWithoutBattle.put(this, BootsMovementSpeed);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal(Type[Num]).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("厚实皮囊").withStyle(style));
        components.add(Component.literal(" 你在冰雪中的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("寒冷值").withStyle(style)).
                append(Component.literal("提升速度").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("降低90%").withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("LeatherArmor").withStyle(CustomStyle.styleOfSnow).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfIce(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
