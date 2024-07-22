package com.very.wraq.series.overworld.chapter1.plain.bow;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.StableAttributesModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlainBow extends WraqBow implements OnHitEffectMainHandWeapon {

    public PlainBow(Properties p_40524_, int tier) {
        super(p_40524_);
        Utils.attackDamage.put(this, PlainBowAttributes.BaseDamage[tier]);
        Utils.critRate.put(this, PlainBowAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, PlainBowAttributes.CriticalDamage[tier]);
        Utils.movementSpeedWithoutBattle.put(this, PlainBowAttributes.SpeedUp[tier]);
        Element.LifeElementValue.put(this, PlainBowAttributes.LifeElementValue[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("平原祝福").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("箭矢命中目标后提供持续2s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeed("40%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerMovementSpeedModifier,
                new StableAttributesModifier("plainBowPassiveExMovementSpeed", 0.4, player.getServer().getTickCount() + 40));
    }
}
