package fun.wraq.process.func.power.impl;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class ObsiManaPower extends WraqPower {

    private final int tier;
    public ObsiManaPower(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    private final double[] damageRate = new double[]{2, 2.3, 2.6, 3};

    @Override
    public Component getActiveName() {
        return Te.s("黑曜裹身", CustomStyle.styleOfMana);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        WraqPower.basicCauseManaDamageDescription(components, damageRate[tier]);
        components.add(Te.s(" 提升", ChatFormatting.GREEN, "自身周围所有", "玩家", ChatFormatting.GREEN,
                ComponentUtils.AttributeDescription.defence("20%"), " 与" ,
                ComponentUtils.AttributeDescription.manaDefence("20%")));
        components.add(Te.s(" 降低", ChatFormatting.RED, "指针周围所有", "怪物", ChatFormatting.RED,
                ComponentUtils.AttributeDescription.defence("20%"), " 与 ",
                ComponentUtils.AttributeDescription.manaDefence("20%")));
        components.add(Te.s(" 以上效果均持续", "3s", CustomStyle.styleOfSky));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 10;
    }

    @Override
    public double getManaCost() {
        return new double[]{200, 230, 260, 300}[tier];
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        getDefaultTargetPlayerList(player).forEach(eachPlayer -> {
            StableAttributesModifier.addM(eachPlayer,
                    StableAttributesModifier.playerPercentDefenceModifier,
                    "ObsiManaPercentDefenceModifier", 0.2, Tick.get() + 60);
            StableAttributesModifier.addM(eachPlayer,
                    StableAttributesModifier.playerPercentManaDefenceModifier,
                    "ObsiManaPercentDefenceModifier", 0.2, Tick.get() + 60, "item/obsi_mana_power");
            ParticleProvider.createBreakBlockParticle(eachPlayer, Blocks.OBSIDIAN);
        });
        getDefaultTargetMobList(player).forEach(eachMob -> {
            StableAttributesModifier.addM(eachMob,
                    StableAttributesModifier.mobPercentManaDefenceModifier,
                    "ObsiManaPercentManaDefenceModifier", -0.2, Tick.get() + 60);
            StableAttributesModifier.addM(eachMob,
                    StableAttributesModifier.mobPercentManaDefenceModifier,
                    "ObsiManaPercentManaDefenceModifier", -0.2, Tick.get() + 60, "item/obsi_mana_power");
            ParticleProvider.createBreakBlockParticle(eachMob, Blocks.OBSIDIAN);
        });
        produceDefaultDustParticle(player, CustomStyle.styleOfMana);
    }
}
