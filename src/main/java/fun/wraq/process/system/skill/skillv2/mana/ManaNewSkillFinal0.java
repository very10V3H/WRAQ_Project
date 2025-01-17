package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.process.system.skill.skillv2.network.SkillV2LeftCooldownS2CPacket;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ManaNewSkillFinal0 extends SkillV2FinalSkill {

    public ManaNewSkillFinal0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        effectExpiredTickMap.put(player, Tick.get() + Tick.s(5));
        Compute.sendEffectLastTime(player, getTexture1Url(), Tick.s(5), 0, false);
    }

    public static void onShoot(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) <= Tick.get()) {
            return;
        }
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 4);
        if (skillV2 instanceof ManaNewSkillFinal0) {
            int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            WraqMixture.batchAddExShoot(player, 1 + skillLevel * 0.1, 2);
        }
    }

    public static void onHit(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) <= Tick.get()) {
            return;
        }
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 4);
        if (skillV2 instanceof ManaNewSkillFinal0) {
            String name = player.getName().getString();
            Map<SkillV2, Integer> map = SkillV2.playerSkillV2AllowReleaseTickMap.getOrDefault(name, null);
            if (map != null) {
                List<SkillV2> baseManaSkills = getManaSkillV2()
                        .stream().filter(skill -> skill.getSkillType() > 0 && skill.getSkillType() < 4)
                        .toList();
                baseManaSkills.forEach(skill -> {
                    int leftTick = map.getOrDefault(skill, 0) - Tick.get();
                    leftTick = Math.max(0, leftTick - 5);
                    map.put(skill, Tick.get() + leftTick);
                    ModNetworking.sendToClient(
                            new SkillV2LeftCooldownS2CPacket(skill.getSkillType(), leftTick), player);
                });
            }
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("获得持续5s的强化"));
        components.add(Te.s("在持续时间内，普攻将额外释放？枚法球"));
        components.add(Te.s("法球命中目标时减少基础技能?s的冷却时间"));
        return components;
    }
}
