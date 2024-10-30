package fun.wraq.process.system.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.util.StringUtils;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class ManaSkillTree {

    public static int getManaSkillTier(Player player, int index) {
        return SkillUtil.getSkillTier(player, index, StringUtils.SkillData.Mana);
    }

    public static Map<Player, Double> skill14Map = new WeakHashMap<>();

    /**
     * 法术专精 - 能量倾泻 <br>
     * 战斗状态下，计算回复的法力值 <br>
     * 当回复量等于自身最大法力值后，释放5枚法球 <br>
     * 法球的基础伤害 = 每100最大法力值提供5%基础伤害 <br>
     */
    public static void skill14OnPlayerManaRecover(Player player, double value) {
        int tier = getManaSkillTier(player, 14);
        if (tier > 0 && Compute.playerIsInBattle(player)) {
            skill14Map.compute(player, (k, v) -> v == null ? value : v + value);
            double maxMana = 1000 + PlayerAttributes.maxManaUp(player);
            if (skill14Map.getOrDefault(player, 0d) >= maxMana) {
                WraqMixture.batchAddExShoot(player, (maxMana) / 100 * 0.05, tier);
                skill14Map.put(player, 0d);
            }
            Compute.sendEffectLastTime(player, "skills/mana/mana_6_3", 0,
                    (int) (skill14Map.getOrDefault(player, 0d) / maxMana * 100), true);
        }
    }
}
