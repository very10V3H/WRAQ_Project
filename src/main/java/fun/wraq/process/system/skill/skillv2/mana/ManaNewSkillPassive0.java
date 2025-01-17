package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ManaNewSkillPassive0 extends SkillV2PassiveSkill {

    public ManaNewSkillPassive0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    public static Map<Mob, Boolean> mobFlagMap = new WeakHashMap<>();
    public static String ICON_TAG = "manaNewSkillPassive0Effect";

    public static void onManaArrowHit(Player player, Mob mob) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof ManaNewSkillPassive0) {
            mobFlagMap.put(mob, true);
            Compute.sendMobEffectHudToNearPlayer(mob, skillV2.getTexture1Url(),
                    ICON_TAG, 8888, 0, true);
        }
    }

    public static void onManaPowerHit(Player player, Mob mob) {
        if (mobFlagMap.getOrDefault(mob, false)) {
            SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
            if (skillV2 instanceof ManaNewSkillPassive0) {
                int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
                Damage.causeManaDamageToMonster_RateApDamage(player, mob, 1 + skillLevel * 0.1, false);
                mobFlagMap.put(mob, false);
                Compute.removeMobEffectHudToNearPlayer(mob, skillV2.getTexture1Url(), ICON_TAG);
            }
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("普攻命中的目标将被标记"));
        components.add(Te.s("任意其他法术技能将会引爆此标记"));
        components.add(Te.s("造成??伤害"));
        return components;
    }
}
