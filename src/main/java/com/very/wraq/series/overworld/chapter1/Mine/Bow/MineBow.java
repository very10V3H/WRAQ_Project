package com.very.wraq.series.overworld.chapter1.Mine.Bow;

import com.very.wraq.core.MyArrow;
import com.very.wraq.process.func.damage.Dot;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class MineBow extends WraqBow implements OnHitEffectMainHandWeapon {

    private final int tier;

    public MineBow(Properties p_40524_, int tier) {
        super(p_40524_);
        this.tier = tier;
        Utils.attackDamage.put(this, MineBowAttributes.BaseDamage[tier]);
        Utils.defencePenetration0.put(this, MineBowAttributes.DefencePenetration0[tier]);
        Utils.critRate.put(this, MineBowAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, MineBowAttributes.CriticalDamage[tier]);
        Utils.movementSpeedWithoutBattle.put(this, MineBowAttributes.SpeedUp[tier]);
        Element.StoneElementValue.put(this, MineBowAttributes.StoneElementValue[tier]);
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.BLACKFOREST_RECALL.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LONG_SEA.get());
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("钢屑").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" -命中目标后，使周围单位").withStyle(ChatFormatting.WHITE));
        components.add(ComponentUtils.getAttackDamageDotDescription(2, 4, new String[]{"10%", "15%", "20%", "25%"}[tier]));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        double rate = (tier + 1) * 0.2;
        Level level = player.level();
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 13, 13, 13));
        mobList.forEach(mob1 -> {
            if (mob1.distanceTo(mob) <= 4) {
                Dot.addDotOnMob(mob1, new Dot(1, PlayerAttributes.attackDamage(player) * new double[]{0.1, 0.15, 0.2, 0.25}[tier],
                        2, player.getServer().getTickCount() + 40, player.getName().getString(), true));
            }
        });
    }
}
