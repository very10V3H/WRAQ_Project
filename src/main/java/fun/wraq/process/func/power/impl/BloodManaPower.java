package fun.wraq.process.func.power.impl;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class BloodManaPower extends WraqPower {

    public BloodManaPower(Properties properties) {
        super(properties);
    }

    @Override
    public Component getActiveName() {
        return Te.s("禁忌激化", CustomStyle.styleOfBloodMana);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();

        basicCauseManaDamageDescription(components, 4);
        components.add(Te.s(" 使", "自身周围", ChatFormatting.AQUA, "所有玩家",
                ComponentUtils.AttributeDescription.healAmplification(""), "-80%", ChatFormatting.RED));
        components.add(Te.s(" 获得等同于其当前", "已损失生命值百分比", ChatFormatting.RED,
                "40%的", ComponentUtils.getCommonDamageEnhance("")));
        components.add(Te.s(" 持续", "5s", ChatFormatting.AQUA));
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
        return ComponentUtils.getSuffixOfDemon();
    }

    @Override
    public void release(Player player) {
        Compute.playerItemCoolDown(player,this, getCoolDownSecond());;
        getDefaultTargetMobList(player)
                .forEach(mob -> {
                    Damage.causeRateApDamageToMonster(player, mob, 4, true);
                    ParticleProvider.createBreakBlockParticle(player, Blocks.NETHERRACK);
                });
        getDefaultTargetPlayerList(player)
                .forEach(eachPlayer -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerHealAmplifierReductionModifier,
                            "BloodManaPowerPlayerHealAmplifierDecrease", 0.8, Tick.get() + 60);
                    double damageEnhanceRate = (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth();
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerCommonDamageEnhance,
                            "BloodManaPowerPlayerCommonDamageEnhance", damageEnhanceRate, Tick.get() + 100);
                    Compute.sendEffectLastTime(player, this, 100, (int) (damageEnhanceRate * 100), false);
                    ParticleProvider.createBreakBlockParticle(player, Blocks.NETHERRACK);
                });
        produceDefaultDustParticle(player, CustomStyle.styleOfBloodMana);
    }
}
