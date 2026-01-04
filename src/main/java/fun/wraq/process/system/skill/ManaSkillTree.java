package fun.wraq.process.system.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.SceptreAttribute;
import fun.wraq.common.util.StringUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.hud.Mana;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class ManaSkillTree {

    public static int getManaSkillTier(Player player, int index) {
        return SkillUtil.getSkillTier(player, index, StringUtils.SkillData.Mana);
    }

    /**
     * 法术专精 - 传世禁咒 <br>
     * 每层提升15%技能法力消耗 <br>
     * 每层提升10%技能伤害 <br>
     */
    public static double getManaSkill13ExManaCostRate(Player player, int professionType) {
        return professionType == 2 ? getManaSkillTier(player, 13) * 0.2 : 0;
    }

    public static double getManaSkill13ExSkillRate(Player player, int professionType) {
        return professionType == 2 ? getManaSkillTier(player, 13) * 0.1 : 0;
    }

    public static Map<Player, Double> skill14Map = new WeakHashMap<>();
    /**
     * 法术专精 - 能量倾泻 <br>
     * 战斗状态下，计算回复的法力值 <br>
     * 当回复量等于自身最大法力值后，释放5枚法球 <br>
     * 法球的基础伤害 = 每100最大法力值提供5%基础伤害 <br>
     */
    public static void skill14OnPlayerManaRecover(Player player, double value) {
        if (!SceptreAttribute.isHandling(player)) {
            return;
        }
        int tier = getManaSkillTier(player, 14);
        if (tier > 0 && Compute.playerIsInBattle(player)) {
            skill14Map.compute(player, (k, v) -> v == null ? value : v + value);
            double maxMana = PlayerAttributes.maxMana(player);
            if (skill14Map.getOrDefault(player, 0d) >= maxMana) {
                WraqMixture.batchAddExShoot(player, (maxMana) / 100 * 0.05, tier);
                skill14Map.put(player, 0d);
            }
            Compute.sendEffectLastTime(player, "skills/mana/mana_6_3", 0,
                    (int) (skill14Map.getOrDefault(player, 0d) / maxMana * 100), true);
        }
    }

    public static void handleManaDamageExTrueDamage(Player player, Mob mob, double damage) {
        double rate = 0;
        rate += getSkill0ExTrueDamageRate(player);
        rate += getSkill5ExTrueDamageRate(player);
        rate += getSkill6ExTrueDamageRate(player);
        Damage.causeTrueDamageToMonster(player, mob, damage * rate);
    }

    public static double getSkill0ExTrueDamageRate(Player player) {
        return getManaSkillTier(player, 0) * 0.01;
    }

    public static void skill2OnKillMob(Player player) {
        Mana.addOrCostPlayerMana(player, getManaSkillTier(player, 2) * 4);
    }

    public static double getSkill5ExTrueDamageRate(Player player) {
        double lostHealth = player.getMaxHealth() - player.getHealth();
        return getManaSkillTier(player, 5) * 0.02 * Math.min(1, lostHealth / (player.getMaxHealth() * 0.5));
    }

    public static double getSkill6ExTrueDamageRate(Player player) {
        return player.getHealth() > player.getMaxHealth() * 0.8 ? getManaSkillTier(player, 6) * 0.01 : 0;
    }
}
