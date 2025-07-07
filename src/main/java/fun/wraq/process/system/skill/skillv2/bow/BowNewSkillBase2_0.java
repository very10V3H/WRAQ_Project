package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.core.bow.MyArrowHitBlock;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowReleaseAnyTime;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.sc5.dragon.weapon.SuperColdDragonWeaponCommon;
import fun.wraq.series.overworld.sun.FrameArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowNewSkillBase2_0 extends SkillV2BaseSkill implements SkillV2AllowReleaseAnyTime {

    public BowNewSkillBase2_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<String, MyArrowHitBlock> nextHitEffectMap = new HashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        Compute.sendEffectLastTime(player, getTexture1Url(), 0, true);
        nextHitEffectMap.put(Name.get(player), new MyArrowHitBlock() {
            @Override
            public void onHit(MyArrow myArrow) {
                MySound.soundToNearPlayer(myArrow.level(), myArrow.position(), SoundEvents.GENERIC_EXPLODE);
                ParticleProvider.createSingleParticleToNearPlayer(player, player.level(),
                        myArrow.position(), ParticleTypes.EXPLOSION_EMITTER);
                ParticleProvider.createRandomMoveParticle(myArrow, 1, 1, 24, ParticleTypes.ASH);
                ParticleProvider.createRandomMoveParticle(myArrow, 1, 1, 24, ParticleTypes.LAVA);
                double range = 5;
                double exRange = 0;
                exRange += SuperColdDragonWeaponCommon.getSkillExRange(player);
                Compute.getNearMob(myArrow, range + exRange).forEach(eachMob -> {
                    Damage.causeRateAdDamageToMonsterWithCritJudge(player, eachMob, (3 + skillLevel * 0.15
                            + (FrameArrow.enhanceBowSkillV2_2(player) ? 1 : 0)) * (1 + getEnhanceRate(player)));
                    SuperColdDragonWeaponCommon.addImprisonEffectToMob(player, eachMob);
                });
                Compute.removeEffectLastTime(player, getTexture1Url());
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("使下一支箭矢命中目标/方块时产生", "爆炸", CustomStyle.styleOfPower));
        components.add(Te.s("造成",
                getRateDescription(3, 0.15, level), CustomStyle.styleOfFlexible, "伤害"));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    protected int getCooldownDecrease(Player player) {
        return FrameArrow.enhanceBowSkillV2_2(player) ? Tick.s(1) : 0;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 10;
    }
}
