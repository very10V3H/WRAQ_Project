package fun.wraq.process.func.power.impl;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class IcePower extends WraqPower {

    public IcePower(Properties properties) {
        super(properties);
    }

    @Override
    public Component getActiveName() {
        return Te.s("凛冬之临", CustomStyle.styleOfIce);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 在", "指针区域", ChatFormatting.AQUA, "制造一个持续3s的",
                "凛冰领域", CustomStyle.styleOfIce));
        components.add(Te.s(" 领域", CustomStyle.styleOfIce, "每秒对区域内"));
        components.add(Te.s(" 1.玩家", ChatFormatting.GREEN, "提供持续1s的", "能力 - 智力 * 10护盾值", ChatFormatting.GRAY));
        components.add(Te.s(" 2.怪物", ChatFormatting.RED, "造成", ComponentUtils.AttributeDescription.manaDamageValue("100%")));
        WraqPower.elementAdditionDescription(components, Element.Description.IceElement("1 + 100%"));
        components.add(Te.s(" 并且会", "短暂减速", ChatFormatting.GRAY, "怪物"));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 10;
    }

    @Override
    public double getManaCost() {
        return 250;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        PersistentRangeEffect.addEffect(player, getDefaultTargetPos(player), 6, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                Compute.getNearEntity(effect.level(), effect.center(), Mob.class, 6)
                        .stream().map(mob -> (Mob) mob)
                        .forEach(mob -> {
                            Damage.causeManaDamageToMonster_RateApDamage_ElementAddition(player, mob,
                                    1, true, Element.ice,
                                    1 + ElementValue.getPlayerIceElementValue(player));
                            ParticleProvider.createBreakBlockParticle(mob, Blocks.ICE);
                        });
                Compute.getNearEntity(effect.level(), effect.center(), Player.class, 6)
                        .stream().map(eachPlayer -> (Player) eachPlayer)
                        .forEach(eachPlayer -> {
                            Shield.providePlayerShield(eachPlayer, 50,
                                    player.getPersistentData().getInt(StringUtils.Ability.Intelligent) * 20);
                            ParticleProvider.createBreakBlockParticle(eachPlayer, Blocks.SNOW_BLOCK);
                        });
                ParticleProvider.dustParticle(player, effect.center(),
                        6, 36, CustomStyle.styleOfIce.getColor().getValue());
                ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), effect.center(),
                        6, 36, ParticleTypes.SNOWFLAKE);
            }
        }, 20, 60);
    }
}
