package fun.wraq.series.overworld.chapter2.sea;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.core.MyArrow;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
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
import java.util.Random;

public class SeaBow extends WraqBow implements OnHitEffectMainHandWeapon {

    public SeaBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 400d);
        Utils.defencePenetration0.put(this, 18d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.WaterElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSea;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("破碎晶石").withStyle(CustomStyle.styleOfSea));
        components.add(Component.literal("箭矢命中目标后，会对目标周围所有单位造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("50%")).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("包括目标本身").withStyle(ChatFormatting.GRAY)));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        components.add(Component.literal("若此真实伤害使目标死亡，则获得持续15s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("等同于此伤害的护盾值").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.BLACKFOREST_RECALL.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LONG_SEA.get());
        return arrow;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Level level = player.level();
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 10, 10, 10));
        Random random = new Random();
        mobList.forEach(mob1 -> {
            double damage = PlayerAttributes.attackDamage(player) * 0.5;
            if (random.nextDouble() < PlayerAttributes.critRate(player)) damage *= (1 + PlayerAttributes.critDamage(player));
            if (mob1.distanceTo(mob) <= 3) {
                if (mob1.getHealth() < damage) Shield.providePlayerShield(player, 200, damage);
                Damage.DirectDamageToMob(player, mob1, damage);
            }
        });
    }
}
