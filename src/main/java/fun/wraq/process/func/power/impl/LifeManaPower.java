package fun.wraq.process.func.power.impl;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class LifeManaPower extends WraqPower {

    private final int tier;
    public LifeManaPower(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    private final double[] damageRate = new double[]{2, 2.3, 2.6, 3};

    @Override
    public Component getActiveName() {
        return Te.s("魔源生机", CustomStyle.styleOfLife);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        WraqPower.basicCauseManaDamageDescription(components, damageRate[tier]);
        WraqPower.elementAdditionDescription(components, Element.Description.LifeElement("1 + 100%"));
        components.add(Te.s(" 提升", ChatFormatting.GREEN, "自身周围所有", "玩家", ChatFormatting.GREEN,
                ComponentUtils.AttributeDescription.healAmplification("25%")));
        components.add(Te.s(" 降低", ChatFormatting.RED, "指针周围所有", "怪物", ChatFormatting.RED,
                ComponentUtils.AttributeDescription.healAmplification("40%")));
        components.add(Te.s(" 以上效果均持续", "3s", CustomStyle.styleOfSky));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 10;
    }

    @Override
    public double getManaCost() {
        return damageRate[tier] * 100;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        getDefaultTargetPlayerList(player).forEach(eachPlayer -> {
            StableAttributesModifier.addM(eachPlayer,
                    StableAttributesModifier.playerHealAmplifierModifier,
                    "LifeManaPowerAmplifierModifier", 0.25, Tick.get() + 60, "item/life_mana_power");
            produceDefaultPlayerEnhanceEffectParticle(eachPlayer);
            ParticleProvider.createBreakBlockParticle(eachPlayer, Blocks.MOSS_BLOCK);
        });
        getDefaultTargetMobList(player).forEach(eachMob -> {
            StableAttributesModifier.addM(eachMob,
                    StableAttributesModifier.mobHealAmplifierModifier,
                    "LifeManaPowerAmplifierModifier", -0.4, Tick.get() + 60, "item/life_mana_power");
            produceDefaultMobDeBuffParticle(eachMob);
            Damage.causeManaDamageToMonster_RateApDamage_ElementAddition(player, eachMob, damageRate[tier], true,
                    Element.life, 1 + ElementValue.getPlayerLifeElementValue(player));
            ParticleProvider.createBreakBlockParticle(eachMob, Blocks.MOSS_BLOCK);
        });
        produceDefaultDustParticle(player, CustomStyle.styleOfLife);
    }
}
