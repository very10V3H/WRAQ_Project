package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.PlayerHashMap;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.buff.BuffSystem;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowReleaseAnyTime;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.process.system.skill.skillv2.bow.BowNewSkillBase2_1;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SwordNewSkillBase2_1 extends SkillV2BaseSkill implements SkillV2AllowReleaseAnyTime {

    public SwordNewSkillBase2_1(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static PlayerHashMap<Mob> targetMap = new PlayerHashMap<>();

    public static Map<Mob, Integer> targetMobDefenceReductionTier = new WeakHashMap<>();

    public static double getDefenceReductionRate(int tier) {
        if (tier < 4) {
            return 0.1;
        } else if (tier < 8) {
            return 0.2;
        } else {
            return 0.3;
        }
    }

    public static double getMobDefenceReductionRate(Mob mob) {
        if (targetMap.getValues().contains(mob)) {
            return - getDefenceReductionRate(targetMobDefenceReductionTier.getOrDefault(mob, 0));
        }
        return 0;
    }

    public static double getAttackDamageEnhanceRate(Player player, Mob mob) {
        if (targetMap.containsKey(player) && targetMap.get(player).equals(mob)) {
            return getDefenceReductionRate(targetMobDefenceReductionTier.getOrDefault(mob, 0));
        }
        return 0;
    }

    public static void onMobDead(Mob mob) {
        if (!targetMobDefenceReductionTier.containsKey(mob)) {
            return;
        }
        SkillV2 skillV2 = getSkillV2(0, 2, 1);
        Player player = null;
        for (Pair<Player, Mob> pair : targetMap.getEntrySet()) {
            if (mob.equals(pair.getB())) {
                player = pair.getA();
            }
        }
        targetMobDefenceReductionTier.remove(mob);
        if (player == null || skillV2 == null) {
            return;
        }
        SkillV2.decreaseSkillCooldownTick(player, skillV2, Tick.s(20));
        Mana.addOrCostPlayerMana(player, skillV2.getManaCost(skillV2.getPlayerSkillLevel(player)));
    }

    @Override
    protected void releaseOperation(Player player) {
        Mob mob = Compute.getDefaultTarget(player, 16);
        if (mob == null) {
            mob = Compute.getNearestMob(player, 16);
        }
        if (mob == null) {
            return;
        }

        if (targetMap.containsKey(player)) {
            Mob oldMob = targetMap.get(player);
            oldMob.removeEffect(MobEffects.GLOWING);
            BuffSystem.removeMobEffectHudToNearPlayer(oldMob, "skills/v2/sword/sword2_1", "swordNewSkillBase2_1");
        }
        targetMap.put(player, mob);
        int skillLevel = getPlayerSkillLevel(player);
        targetMobDefenceReductionTier.compute(mob, (k, v) -> v == null ? skillLevel : Math.max(skillLevel,  v));
        double rate = 2.5 + skillLevel * 0.25;
        Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob, rate);
        ParticleProvider.createVerticalCircleParticle(player.level().dimension(), mob.getEyePosition(),
                0, 0.5, 8, ParticleTypes.CRIT);
        BuffSystem.sendMobEffectHudToNearPlayer(mob, "skills/v2/sword/sword2_1", "swordNewSkillBase2_1",
                8888, 0, true);
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, Tick.min(10)));
        MySound.soundToPlayer(player, SoundEvents.ARROW_HIT_PLAYER, mob.getEyePosition());
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对准星选中目标造成",
                getRateDescription(2.5, 0.25, level), CustomStyle.styleOfPower, "伤害，",
                "并削减",
                ComponentUtils.AttributeDescription.defence(Compute.getPercent(getDefenceReductionRate(level))),
                ComponentUtils.AttributeDescription.manaDefence(Compute.getPercent(getDefenceReductionRate(level)))));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        components.add(Te.s("并使你对其造成的",
                "物理伤害 + " + Compute.getPercent(getDefenceReductionRate(level)), ChatFormatting.YELLOW));
        components.add(Te.s("目标死亡会立即刷新冷却并返还法力消耗."));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 10;
    }

    @Override
    protected boolean canUpgrade(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return InventoryOperation.checkPlayerHasItem(player, getUpgradeMaterials(skillLevel))
                && BowNewSkillBase2_1.canUpgradeExJudge(player, skillLevel);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription(int skillLevel) {
        return BowNewSkillBase2_1.getNewUpgradeConditionDescription(skillLevel, getUpgradeMaterials(skillLevel));
    }

    @Override
    protected List<ItemStack> getUpgradeMaterials(int skillLevel) {
        return BowNewSkillBase2_1.getNewUpgradeMaterials(skillLevel);
    }
}
