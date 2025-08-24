package fun.wraq.series.overworld.wind;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WindSword extends WraqSword implements ActiveItem, ForgeItem {

    private final int tier;

    public List<Item> windSword = new ArrayList<>();

    public WindSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{700, 800, 900, 1200, 1500}[tier]);
        Utils.defencePenetration0.put(this, new double[]{20, 22, 24, 28, 35}[tier]);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.3);
        Element.windElementValue.put(this, new double[]{0.8, 1, 1.2, 1.6, 2}[tier]);
        Utils.movementSpeedWithoutBattle.put(this, new double[]{0.8, 0.8, 0.8, 1, 1.2}[tier]);
        Utils.levelRequire.put(this, 180);
        windSword.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfKaze;
    }

    public double getDamageRate() {
        return new double[]{1, 1.25, 1.5, 2, 3}[tier];
    }

    public double getMovementSpeedRate() {
        return new double[]{0.3, 0.5, 0.7, 1, 1.25}[tier];
    }

    public int getMaxDistance() {
        return new int[]{16, 16, 16, 24, 32}[tier];
    }

    public int getRange() {
        return new int[]{6, 6, 6, 8, 10}[tier];
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("狂风绝息", getMainStyle()));
        components.add(Te.s(" 对准星(最大距离", getMaxDistance(), getMainStyle(),
                ")", getRange(), getMainStyle(), "格内的敌人:"));
        components.add(Te.s(" · ", getMainStyle(), "若其位于地面，则", "击飞", getMainStyle()));
        components.add(Te.s(" · ", getMainStyle(), "若其位于空中，则"));
        components.add(Te.s( "          对其造成",
                ComponentUtils.getAutoAdaptTrueDamageDescription(Te.getPercent(getDamageRate())), "并强制牵引至地面."));
        ComponentUtils.descriptionPassive(components, Te.s("唤风", getMainStyle()));
        components.add(Te.s(" 释放", "狂风绝息", getMainStyle(), "后，获得"));
        components.add(Te.s("          在3s内持续衰减的",
                ComponentUtils.AttributeDescription.movementSpeed(Te.getPercent(getMovementSpeedRate()))));
        components.add(Te.s(" 若触发了伤害，则", "固定3s冷却时间", ChatFormatting.AQUA));
        components.add(Te.s(" 否则", "固定0.8s冷却时间", ChatFormatting.AQUA));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfWind();
    }

    @Override
    public void active(Player player) {
        Vec3 pos = WraqPower.getDefaultTargetPos(player, 24);
        boolean causedDamage = false;
        for (Mob mob : Compute.getNearMob(player.level(), pos, 6)) {
            if (Compute.isOnSky(mob)) {
                mob.addDeltaMovement(new Vec3(0, -1, 0));
                Damage.causeAutoAdaptionRateDamageToMob(player, mob, getDamageRate(), true);
                sendMobPosParticle(mob, ParticleTypes.ENCHANTED_HIT);
                causedDamage = true;
            } else {
                mob.addDeltaMovement(new Vec3(0, 1, 0));
                sendMobPosParticle(mob, ParticleTypes.TOTEM_OF_UNDYING);
            }
        }
        if (causedDamage) {
            windSword.forEach(item -> {
                player.getCooldowns().addCooldown(item, Tick.s(3));
            });
            sendParticleAndSound(player, SoundEvents.PLAYER_ATTACK_KNOCKBACK);
        } else {
            windSword.forEach(item -> {
                player.getCooldowns().addCooldown(item, 16);
            });
            sendParticleAndSound(player, SoundEvents.PLAYER_ATTACK_SWEEP);
        }
        ChangedAttributesModifier.addAttributeModifier(player, ChangedAttributesModifier.movementSpeedUp,
                "windSwordEffect", getMovementSpeedRate(), Tick.s(3), true);
    }

    private void sendParticleAndSound(Player player, SoundEvent soundEvent) {
        Vec3 faceVec = player.pick(1, 0, false).getLocation();
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                ParticleTypes.SWEEP_ATTACK, true, faceVec.x, faceVec.y, faceVec.z, 0, 0, 0, 0, 0
        );
        ServerPlayer serverPlayer1 = (ServerPlayer) player;
        serverPlayer1.connection.send(clientboundLevelParticlesPacket);
        ClientboundSoundPacket clientboundSoundPacket
                = new ClientboundSoundPacket(Holder.direct(soundEvent),
                SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
        serverPlayer1.connection.send(clientboundSoundPacket);
    }

    private void sendMobPosParticle(Mob mob, ParticleOptions particleOptions) {
        ParticleProvider.EntityEffectVerticleCircleParticle(mob,
                1, 1, 16, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(mob,
                0.5, 0.75, 16, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(mob,
                0, 0.75, 16, particleOptions, 0);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(ModItems.SAKURA_KANATA.get()),
                    new ItemStack(WindItems.WIND_CRYSTAL_0.get(), 10),
                    new ItemStack(WindItems.WIND_RUNE.get(), 10)
            );
        }
        if (tier == 3) {
            return List.of(
                    new ItemStack(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())
            );
        }
        return List.of();
    }
}
