package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.core.bow.MyArrowHitBlock;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class BowNewSkillBase2_0 extends SkillV2BaseSkill {

    public BowNewSkillBase2_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    public static Map<Player, MyArrowHitBlock> nextHitEffectMap = new WeakHashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        Compute.sendEffectLastTime(player, getTexture1Url(), 0, true);
        nextHitEffectMap.put(player, new MyArrowHitBlock() {
            @Override
            public void onHit(MyArrow myArrow) {
                MySound.soundToNearPlayer(myArrow.level(), myArrow.position(), SoundEvents.GENERIC_EXPLODE);
                ParticleProvider.createSingleParticleToNearPlayer(player, player.level(),
                        myArrow.position(), ParticleTypes.EXPLOSION_EMITTER);
                ParticleProvider.RandomMoveParticle(myArrow, 1, 1, 24, ParticleTypes.ASH);
                ParticleProvider.RandomMoveParticle(myArrow, 1, 1, 24, ParticleTypes.LAVA);
                myArrow.level().getEntitiesOfClass(Mob.class, AABB.ofSize(myArrow.position(), 10, 10, 10))
                        .forEach(eachMob -> {
                            Damage.causeRateAdDamageToMonsterWithCritJudge(player, eachMob, 1 + skillLevel * 0.1);
                            Compute.addSlowDownEffect(eachMob, 20, 0);
                        });
                Compute.removeEffectLastTime(player, getTexture1Url());
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("使下一支箭矢命中目标/方块时在其位置产生爆炸"));
        return components;
    }
}
