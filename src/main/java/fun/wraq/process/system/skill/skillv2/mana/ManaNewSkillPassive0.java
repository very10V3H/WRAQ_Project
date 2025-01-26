package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ManaNewSkillPassive0 extends SkillV2PassiveSkill {

    public ManaNewSkillPassive0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<Mob, Integer> mobCountMap = new WeakHashMap<>();
    public static String ICON_TAG = "manaNewSkillPassive0Effect";

    public static void onManaArrowHit(Player player, Mob mob) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof ManaNewSkillPassive0) {
            addCount(player, mob, 1);
        }
    }

    public static void addCount(Player player, Mob mob, int addCount) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof ManaNewSkillPassive0) {
            int count = mobCountMap.getOrDefault(mob, 0);
            count = Math.min(5, count + addCount);
            mobCountMap.put(mob, count);
            Compute.sendMobEffectHudToNearPlayer(mob, skillV2.getTexture1Url(),
                    ICON_TAG, 8888, count, true);
        }
    }

    public static void onManaPowerHit(Player player, Mob mob) {
        if (mobCountMap.getOrDefault(mob, 0) > 0) {
            SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
            if (skillV2 instanceof ManaNewSkillPassive0) {
                MySound.soundToNearPlayer(player.level(), player.getEyePosition(), SoundEvents.CREEPER_DEATH);
                int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
                Compute.getNearEntity(mob, Mob.class, 1)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(eachMob -> {
                            Damage.causeRateApDamageToMonster(player, eachMob, 1 + skillLevel * 0.1, false);
                        });
                mobCountMap.compute(mob, (k, v) -> v == null ? 0 : v - 1);
                int count = mobCountMap.get(mob);
                if (count > 0) {
                    Compute.sendMobEffectHudToNearPlayer(mob, skillV2.getTexture1Url(), ICON_TAG,
                            8888, count, true);
                } else {
                    Compute.removeMobEffectHudToNearPlayer(mob, skillV2.getTexture1Url(), ICON_TAG);
                }
                SkillV2 manaFinalSkill = getPlayerCurrentSkillByType(player, 4);
                if (manaFinalSkill instanceof ManaNewSkillFinal0) {
                    decreaseSkillCooldownTick(player, manaFinalSkill, 15);
                }
            }
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("普攻命中目标时，将施加一层 ", "渗", CustomStyle.styleOfMana));
        components.add(Te.s("至多叠加至", "5", CustomStyle.styleOfMana, "层，法术技能命中时"));
        components.add(Te.s("将引爆一层 ", "渗", CustomStyle.styleOfMana, "，对目标周围小范围敌人"));
        components.add(Te.s("造成", getRateDescription(1, 0.1, level),
                CustomStyle.styleOfMana, "伤害"));
        return components;
    }
}
