package fun.wraq.process.func.power.impl;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SakuraPower extends WraqPower {

    public SakuraPower(Properties properties) {
        super(properties);
    }

    @Override
    public Component getActiveName() {
        return Te.s("开春之樱", CustomStyle.styleOfSakura);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        basicCauseManaDamageDescription(components, 4);
        components.add(Te.s(" 吸收", "自身周围", ChatFormatting.AQUA, "所有", "玩家", ChatFormatting.GREEN,
                ComponentUtils.AttributeDescription.health("20%当前")));
        components.add(Te.s(" 并为所有", "玩家", ChatFormatting.GREEN, "提供持续3s的",
                ComponentUtils.AttributeDescription.healthRecover("5%")));
        components.add(Te.s(" 基于吸收生命值的1%为", "自身", ChatFormatting.AQUA, "回复",
                ComponentUtils.AttributeDescription.manaValue("")));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 10;
    }

    @Override
    public double getManaCost() {
        return 400;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

    @Override
    public void release(Player player) {
        Vec3 targetPos = getDefaultTargetPos(player);
        getDefaultTargetMobList(player)
                .forEach(mob -> {
                    Damage.causeManaDamageToMonster_RateApDamage(player, mob, 4, true);
                    ParticleProvider.createBreakBlockParticle(mob, Blocks.CHERRY_LEAVES);
                });
        getDefaultTargetPlayerList(player)
                .forEach(eachPlayer -> {
                    Compute.decreasePlayerHealth(eachPlayer, eachPlayer.getHealth() * 0.2, Te.s("樱花轻抚"));
                    ParticleProvider.createLineEffectParticle(player.level(),
                            (int) eachPlayer.distanceTo(player) * 5,
                            eachPlayer.getEyePosition(), player.getEyePosition(),
                            CustomStyle.styleOfSakura);
                });

        ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), targetPos, 6, 36, ParticleTypes.CHERRY_LEAVES);
        produceDefaultDustParticle(player, CustomStyle.styleOfSakura);
    }
}
