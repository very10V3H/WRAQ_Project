package fun.wraq.process.system.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.StringUtils;
import fun.wraq.process.func.StableTierAttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class BowSkillTree {

    public static int getBowSkillTier(Player player, int index) {
        return SkillUtil.getSkillTier(player, index, StringUtils.SkillData.Bow);
    }

    /**
     * 索命箭雨 - 箭矢命中目标后,为你提供一层【索命】 <br>
     * 每层【索命】提供5% (1% - 5%)基础伤害提升，至多10层，每层持续1s <br>
     */
    public static void skillIndex13(Player player) {
        int tier = getBowSkillTier(player, 13);
        if (tier <= 0) {
            return;
        }
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.baseArrowDamageEnhanceRate,
                "bow skill index 13 passive", 0.01 * tier, Tick.get() + 20, 10, "skills/bow/bow_6_2");
    }

    /**
     * 全身贯注记录表
     */
    public static Map<Player, Integer> skillIndex14RecordMap = new WeakHashMap<>();

    /**
     * 全神贯注 - 基于箭矢的射击间隔，为箭矢提供基础伤害提升 <br>
     * 每级的最大基础伤害提升为30% （30% - 150%）<br>
     * 当攻击间隔在1s时，拥有最大基础伤害提升 <br>
     * @return 基础伤害提升数额
     */
    public static double skillIndex14(Player player) {
        int tier = getBowSkillTier(player, 14);
        if (tier <= 0) {
            return 0;
        }
        double rate = 0.3 * tier
                * Math.min(1, (Tick.get() - skillIndex14RecordMap.getOrDefault(player, 0) - 10) / 20);
        skillIndex14RecordMap.put(player, Tick.get());
        Compute.sendCoolDownTime(player, "skills/bow/bow_6_3", 20);
        return Math.max(0, rate);
    }
}
