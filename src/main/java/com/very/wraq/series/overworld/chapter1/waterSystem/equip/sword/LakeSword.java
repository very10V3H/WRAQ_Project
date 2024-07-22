package com.very.wraq.series.overworld.chapter1.waterSystem.equip.sword;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.projectiles.WraqSword;
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

public class LakeSword extends WraqSword implements OnHitEffectMainHandWeapon {
    public LakeSword(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, new double[]{50, 60, 70}[tier]);
        Utils.defencePenetration0.put(this, new double[]{150, 200, 250}[tier]);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, new double[]{0.25, 0.3, 0.35}[tier]);
        Utils.movementSpeedWithoutBattle.put(this, new double[]{0.3, 0.4, 0.5}[tier]);
        Element.WaterElementValue.put(this, new double[]{0.2, 0.4, 0.6}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLake;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("潜泳").withStyle(ChatFormatting.BLUE));
        Compute.DescriptionNum(components, "攻击后获得持续1秒的", ComponentUtils.AttributeDescription.movementSpeed("50%"), "");
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerMovementSpeedModifier,
                new StableAttributesModifier("lakeSwordPassiveExMovementSpeed", 0.5, player.getServer().getTickCount() + 20));
    }
}
