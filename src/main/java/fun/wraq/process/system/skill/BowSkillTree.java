package fun.wraq.process.system.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.PlayerHashMap;
import fun.wraq.common.fast.PlayerIntegerHashMap;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import fun.wraq.process.func.StableTierAttributeModifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

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
                "bow skill index 13 passive", 0.005 * tier, Tick.get() + 30, 10, "skills/bow/bow_6_2");
    }

    /**
     * 全身贯注记录表
     */
    public static PlayerHashMap<Integer> skillIndex14RecordMap = new PlayerHashMap<>();

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

    private static final PlayerIntegerHashMap skillIndex3CountMap = new PlayerIntegerHashMap();

    private static final PlayerHashMap<Mob> skillIndex3TargetMap = new PlayerHashMap<>();

    public static void skillIndex3Hit(Player player, Mob mob) {
        if (mob.equals(skillIndex3TargetMap.getOrDefault(player, null))) {
            skillIndex3CountMap.increment(player, 10);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4, 10, 10,
                    skillIndex3CountMap.getOrDefault(player, 0), 1), (ServerPlayer) player);
        } else {
            skillIndex3TargetMap.put(player, mob);
            skillIndex3CountMap.put(player, 1);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4, 10, 10, 1, 1), (ServerPlayer) player);
        }
    }

    public static double getSkillIndex3DamageEnhanceRate(Player player, Mob mob) {
        return mob.equals(skillIndex3TargetMap.getOrDefault(player, null))
                ? getBowSkillTier(player, 3) * 0.02 * skillIndex3CountMap.getOrDefault(player, 0) / 10
                : 0;
    }
}
