package com.very.wraq.series.overworld.chapter2.lightningIsland.Armor;

import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LightningArmor extends WraqArmor {

    public LightningArmor(ItemMaterial material, Type type, Properties properties, double maxHealth, double defence) {
        super(material, type, properties);
        Utils.defence.put(this, defence);
        Utils.maxHealth.put(this, maxHealth);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.35);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLightingIsland;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("唤雷庇护").withStyle(CustomStyle.styleOfLightingIsland));
        components.add(Component.literal("1.攻击/受到攻击时，对目标/攻击者造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("伤害的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("雷击。").withStyle(CustomStyle.styleOfLightingIsland)).
                append(Component.literal("(多件唤雷装备的效果能够叠加)").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("2.免疫").withStyle(ChatFormatting.WHITE).
                append(Component.literal("唤雷岛雷击与神秘力量。").withStyle(CustomStyle.styleOfLightingIsland)));
        components.add(Component.literal("以雷霆，击碎黑暗！").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
