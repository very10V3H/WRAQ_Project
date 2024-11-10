package fun.wraq.series.overworld.sakuraSeries.EarthMana;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EarthManaCurios extends WraqCurios {

    public EarthManaCurios(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.manaDefence.put(this, tier == 0 ? 4d : 8d);
        Utils.manaDamage.put(this, tier == 0 ? 200d : 400d);
        Utils.manaHealthSteal.put(this, tier == 0 ? 0.04 : 0.08);
        Utils.manaRecover.put(this, tier == 0 ? 8d : 16d);
        Utils.curiosList.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("旧世仁慈魔浴").withStyle(style));
        components.add(Component.literal(" 当你受到").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamageValue("")).
                append(Component.literal("时，为你提供持续3s的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.healthRecover("5%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }
}
