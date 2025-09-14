package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowReleaseAnyTime;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sun.BrokenBlade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SwordNewSkillFinal0 extends SkillV2FinalSkill implements SkillV2AllowReleaseAnyTime {

    public SwordNewSkillFinal0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();
    public static Map<Player, Integer> effectTierMap = new WeakHashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        int lastSeconds = getLastSeconds(getPlayerSkillLevel(player))
                + (BrokenBlade.enhanceSwordSkillV2_4(player) ? 1 : 0);
        effectExpiredTickMap.put(player, Tick.get() + Tick.s(lastSeconds));
        effectTierMap.put(player, getPlayerSkillLevelBySkillV2(player, this));
        Compute.sendEffectLastTime(player, getTexture1Url(), Tick.s(lastSeconds), 0, false);
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, Tick.s(lastSeconds)));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerExAttackSpeed,
                "SwordNewSkillFinal0_ExAttackSpeedEffect", 0.3, Tick.get() + Tick.s(lastSeconds));
    }

    private int getLastSeconds(int skillLevel) {
        int lastSeconds;
        if (skillLevel < 3) {
            lastSeconds = 5;
        } else if (skillLevel < 5) {
            lastSeconds = 6;
        } else if (skillLevel < 8) {
            lastSeconds = 7;
        } else {
            lastSeconds = 8;
        }
        return lastSeconds;
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("获得持续", getLastSeconds(level) + "秒", ChatFormatting.AQUA,
                "的", "注魔之刃", ChatFormatting.LIGHT_PURPLE));
        components.add(Te.s("在持续时间内，普通攻击将释放", "剑气", CustomStyle.styleOfPower));
        components.add(Te.s("并获得", ComponentUtils.AttributeDescription
                .getAttackSpeed(getRateDescription(0.2, 0.01, level))));
        components.add(Te.s("剑气", CustomStyle.styleOfPower, "会对沿途的目标造成",
                getRateDescription(3, 0.3, level), CustomStyle.styleOfPower, "伤害。"));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    public static void onPlayerNormalAttack(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) > Tick.get()
                && effectTierMap.containsKey(player)) {
            MySound.soundToNearPlayer(player, SoundEvents.PARROT_IMITATE_EVOKER);
            SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 4);
            if (skillV2 == null) {
                return;
            }
            SwordAir swordAir = new SwordAir(ModEntityType.SWORD_AIR.get(), player, player.level()) {
                @Override
                public void onHitEntity(Mob mob) {
                    if (player != null && !level().isClientSide) {
                        Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob,
                                ((3 + effectTierMap.get(player) * 0.3
                                        + (BrokenBlade.enhanceSwordSkillV2_4(player) ? 1 : 0)))
                                        * (1 + skillV2.getEnhanceRate(player)));
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

    @Override
    protected int getEachLevelExManaCost() {
        return 10;
    }
}
