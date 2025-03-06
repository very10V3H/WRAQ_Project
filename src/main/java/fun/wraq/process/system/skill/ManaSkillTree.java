package fun.wraq.process.system.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.hud.Mana;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class ManaSkillTree {

    public static int getManaSkillTier(Player player, int index) {
        return SkillUtil.getSkillTier(player, index, StringUtils.SkillData.Mana);
    }

    public record ManaSkill13RecoverData(double value, int expiredTick) {}
    public static Map<String, Queue<ManaSkill13RecoverData>> near5secondsRecoverValue = new HashMap<>();
    /**
     * 法术专精 - 传世禁咒 <br>
     * 生命值高于75%，若法力值未达100% <br>
     * 则回复的5%生命值转化为法力值 <br>
     */
    public static boolean onHealthRecover(Player player, double value) {
        if (!Utils.sceptreTag.containsKey(player.getMainHandItem().getItem())) {
            return false;
        }
        int tier = getManaSkillTier(player, 13);
        double maxMana = Mana.getPlayerMaxManaNum(player);
        if (tier > 0 && player.getHealth() > player.getMaxHealth() * 0.75
                && Mana.getPlayerCurrentManaNum(player) < maxMana) {
            double recoverValue = Math.min(maxMana - Mana.getPlayerCurrentManaNum(player),
                    value / player.getMaxHealth() * maxMana * tier * 0.1);
            Mana.addOrCostPlayerMana(player, recoverValue);
            if (!near5secondsRecoverValue.containsKey(Name.get(player))) {
                near5secondsRecoverValue.put(Name.get(player), new ArrayDeque<>());
            }
            Queue<ManaSkill13RecoverData> queue = near5secondsRecoverValue.get(Name.get(player));
            while (queue.peek() != null && queue.peek().expiredTick < Tick.get()) {
                queue.poll();
            }
            queue.add(new ManaSkill13RecoverData(recoverValue, Tick.get() + 100));
            return true;
        }
        return false;
    }

    private static final String MANA_SKILL_13_IMAGE_URL = "skills/mana/mana_6_2";
    public static void manaSkill13Tick(Player player) {
        if (near5secondsRecoverValue.containsKey(Name.get(player))) {
            Queue<ManaSkill13RecoverData> queue = near5secondsRecoverValue.get(Name.get(player));
            while (queue.peek() != null && queue.peek().expiredTick < Tick.get()) {
                queue.poll();
            }
            double sum = queue.stream().mapToDouble(data -> data.value).sum();
            if (sum == 0) {
                Compute.removeEffectLastTime(player, MANA_SKILL_13_IMAGE_URL);
            } else {
                Compute.sendEffectLastTime(player, MANA_SKILL_13_IMAGE_URL, (int) sum, true);
            }
        }
    }

    public static Map<Player, Double> skill14Map = new WeakHashMap<>();
    /**
     * 法术专精 - 能量倾泻 <br>
     * 战斗状态下，计算回复的法力值 <br>
     * 当回复量等于自身最大法力值后，释放5枚法球 <br>
     * 法球的基础伤害 = 每100最大法力值提供5%基础伤害 <br>
     */
    public static void skill14OnPlayerManaRecover(Player player, double value) {
        if (!Utils.sceptreTag.containsKey(player.getMainHandItem().getItem())) {
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
}
