package com.very.wraq.series.overworld.sakuraSeries.SakuraMob;

import com.very.wraq.common.MySound;
import com.very.wraq.core.MyArrow;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SakuraBow extends WraqBow {

    public SakuraBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 400d);
        Utils.defencePenetration0.put(this, 1800d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.95);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.LifeElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSakura;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("妖化箭矢").withStyle(CustomStyle.styleOfDemon));
        components.add(Component.literal("第一发箭矢命中目标后，提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("400")).
                append(Component.literal(" ")).
                append(Compute.AttributeDescription.ManaDefence("400")));
        components.add(Component.literal("第二发箭矢回复").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("5%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterV();
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.CHERRY_LEAVES);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.CHERRY_LEAVES);
        return arrow;
    }
}
