package com.very.wraq.series.specialevents.springFes;

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

public class SpringSwiftArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfSpring;
    private String type = "";

    public SpringSwiftArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 1024d);
        Utils.attackDamage.put(this, 224d);
        Utils.defence.put(this, 124d);
        Utils.swiftnessUp.put(this, 2.4);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
        switch (type) {
            case 0 -> this.type = "头盔";
            case 1 -> this.type = "胸甲";
            case 2 -> this.type = "护腿";
            case 3 -> this.type = "靴子";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("金龙怒吼").withStyle(style));
        components.add(Component.literal("每过5s,你命中目标的箭矢会释放一个").withStyle(ChatFormatting.WHITE).
                append(Component.literal("烟花").withStyle(style)).
                append(Component.literal("使得一定范围内的怪物禁锢并降低").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("20%~40%")).
                append(Component.literal("持续3s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
