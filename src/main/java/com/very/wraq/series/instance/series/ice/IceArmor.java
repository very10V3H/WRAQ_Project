package com.very.wraq.series.instance.series.ice;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class IceArmor extends WraqArmor {

    public IceArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.attackDamage.put(this, 350d);
        Utils.manaDamage.put(this, 700d);
        Utils.defence.put(this, 600d);
        Utils.maxHealth.put(this, 1600d);
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.4);
            Utils.maxHealth.put(this, 800d);
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("寒意释放").withStyle(style));
        components.add(Component.literal(" 每3s对周围单位造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("15%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法伤害").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal(" + ").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("物理伤害").withStyle(ChatFormatting.LIGHT_PURPLE)));
        components.add(Component.literal("并施加持续2s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("减速效果").withStyle(style)));
        Compute.DescriptionPassive(components, Component.literal("雪上覆霜").withStyle(style));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("移动速度受损").withStyle(style)).
                append(Component.literal("的敌人，造成的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("15%").withStyle(CustomStyle.styleOfIce)));
        components.add(Component.literal(" -多件冰霜骑士装备能够线性提升伤害值").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }
}
