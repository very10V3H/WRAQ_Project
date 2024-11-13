package fun.wraq.series.nether.power;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

import static fun.wraq.common.Compute.playerItemCoolDown;

public class MagmaPower extends WraqPower {

    private final int tier;
    public MagmaPower(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    private final double[] damageRate = new double[]{3, 4, 5, 6};

    @Override
    public Component getActiveName() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("·[强化法术攻击]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("下次法术攻击命中时，将形成范围爆炸，对范围内的目标造成:").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f%%", damageRate[tier] * 100))));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("并对受到影响的目标造成减速效果。").withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 3;
    }

    @Override
    public double getManaCost() {
        return 300;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        playerItemCoolDown(player, this, 3);
        Compute.sendEffectLastTime(player, this, 0, true);
        EnhanceNormalAttackModifier.addModifier(player,
                new EnhanceNormalAttackModifier("MagmaPowerAttack", 2, ((player1, mob) -> {
                    MySound.soundToNearPlayer(mob.level(), mob.position(), SoundEvents.GENERIC_EXPLODE);
                    ParticleProvider.createSingleParticleToNearPlayer(player, player.level(),
                            mob.position(), ParticleTypes.EXPLOSION_EMITTER);
                    ParticleProvider.RandomMoveParticle(mob, 1, 1, 24, ParticleTypes.ASH);
                    ParticleProvider.RandomMoveParticle(mob, 1, 1, 24, ParticleTypes.LAVA);
                    mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 10, 10, 10))
                            .forEach(eachMob -> {
                                Damage.causeManaDamageToMonster_RateApDamage_ElementAddition(player, mob, damageRate[tier], true,
                                        Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
                                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                            });
                    Compute.removeEffectLastTime(player, this);
                })));
    }
}
