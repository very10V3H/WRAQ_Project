package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SwordNewSkillFinal0 extends SkillV2FinalSkill {

    public SwordNewSkillFinal0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();
    public static Map<Player, Integer> effectTierMap = new WeakHashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        effectExpiredTickMap.put(player, Tick.get() + Tick.s(5));
        effectTierMap.put(player, getPlayerSkillLevelBySkillV2(player, this));
        Compute.sendEffectLastTime(player, getTexture1Url(), Tick.s(5), 0, false);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("获得持续5s的强化"));
        components.add(Te.s("在强化持续时间内，普通攻击将额外释放剑气"));
        return components;
    }

    public static void onPlayerNormalAttack(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) > Tick.get()
                && effectTierMap.containsKey(player)) {
            SwordAir swordAir = new SwordAir(ModEntityType.SWORD_AIR.get(), player, player.level()) {
                @Override
                public void onHitEntity(Mob mob) {
                    if (player != null && !level().isClientSide) {
                        Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob,
                                1 + effectTierMap.get(player) * 0.1);
                    }
                    super.onHitEntity(mob);
                }

                @Override
                protected void onHitBlock(BlockHitResult hitResult) {
                    this.remove(RemovalReason.KILLED);
                    super.onHitBlock(hitResult);
                }

                @Override
                public void clientTick() {
                    ParticleOptions particleOptions = new DustParticleOptions(
                            Vec3.fromRGB24(CustomStyle.styleOfGold.getColor().getValue()).toVector3f(), 1);
                    this.level().addParticle(particleOptions,
                            this.getX() + (random.nextInt(3) - 1.5) * random.nextDouble(),
                            this.getY() + random.nextDouble(),
                            this.getZ() + (random.nextInt(3) - 1.5) * random.nextDouble(),
                            0, 0, 0);
                    super.clientTick();
                }
            };
            swordAir.setNoGravity(true);
            swordAir.shootFromRotation(3);
        }
    }
}
