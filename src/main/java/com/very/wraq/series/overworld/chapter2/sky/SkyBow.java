package com.very.wraq.series.overworld.chapter2.sky;

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

public class SkyBow extends WraqBow {
    private final double BaseDamage = 100;
    private final double DefencePenetration0 = 800;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 0.35;
    private final double SpeedUp = 0.6F;

    public SkyBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration0.put(this, this.DefencePenetration0);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Element.WindElementValue.put(this, 0.8);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("鹰隼之速").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExMovementSpeed("1%")).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.DefencePenetration("1")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

}
