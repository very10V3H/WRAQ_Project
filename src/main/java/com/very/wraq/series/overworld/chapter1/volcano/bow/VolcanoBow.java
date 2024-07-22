package com.very.wraq.series.overworld.chapter1.volcano.bow;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VolcanoBow extends WraqBow {
    private final int Num;

    public VolcanoBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.attackDamage.put(this, VolcanoBowAttributes.BaseDamage[Num]);
        Utils.defencePenetration0.put(this, VolcanoBowAttributes.DefencePenetration0[Num]);
        Utils.critRate.put(this, VolcanoBowAttributes.CriticalRate[Num]);
        Utils.critDamage.put(this, VolcanoBowAttributes.CriticalDamage[Num]);
        Element.FireElementValue.put(this, VolcanoBowAttributes.FireElementValue[Num]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("熔岩涌动").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("箭矢命中目标后获得持续2.5s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("40%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
