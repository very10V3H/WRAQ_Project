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
                MySound.soundToNearPlayer(player.level(), player.getEyePosition(), SoundEvents.CREEPER_DEATH);
                int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
                Compute.getNearEntity(mob, Mob.class, 1)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(eachMob -> {
                            Damage.causeRateApDamageToMonster(player, eachMob, 1 + skillLevel * 0.1, false);
                        });
                mobFlagMap.put(mob, false);
                Compute.removeMobEffectHudToNearPlayer(mob, skillV2.getTexture1Url(), ICON_TAG);
            }
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("普攻命中的目标将被", "标记", CustomStyle.styleOfMana));
        components.add(Te.s("任意其他", "法术技能", CustomStyle.styleOfMana,
                "将", "引爆标记", CustomStyle.styleOfMana));
        components.add(Te.s("对目标及其附近小范围的敌人造成",
                getRateDescription(1, 0.1, level), CustomStyle.styleOfMana, "伤害"));
        return components;
    }
}
