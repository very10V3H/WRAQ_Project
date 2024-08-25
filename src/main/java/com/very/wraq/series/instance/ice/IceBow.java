package com.very.wraq.series.instance.ice;

import com.very.wraq.common.MySound;
import com.very.wraq.core.MyArrow;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceBow extends WraqBow implements OnHitEffectMainHandWeapon {

    public IceBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 700d);
        Utils.defencePenetration0.put(this, 2100d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.IceElementValue.put(this, 1.25);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("迸晶裂玉").withStyle(style));
        components.add(Component.literal(" 攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("将对目标造成持续1s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(CustomStyle.styleOfIce)));
        Compute.DescriptionPassive(components, Component.literal("凝结爆裂").withStyle(style));
        components.add(Component.literal(" 造成暴击后，为你提供基于攻击目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("的额外").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.DefencePenetration("")).
                append(Component.literal("持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每2点护甲值提供1护甲穿透").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public void shoot(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);

        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Compute.addSlowDownEffect(mob, 20, 1);
        Compute.iceParticleCreate(mob);
    }
}
