package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ManaNewSkillFinal0 extends SkillV2FinalSkill {

    public ManaNewSkillFinal0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        effectExpiredTickMap.put(player, Tick.get() + Tick.s(5));
        Compute.sendEffectLastTime(player, getTexture1Url(), Tick.s(5), 0, false);
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, Tick.s(5)));
    }

    public static void onShoot(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) <= Tick.get()) {
            return;
        }
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 4);
        if (skillV2 instanceof ManaNewSkillFinal0) {
            int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            WraqMixture.batchAddExShoot(player, 0.5 + skillLevel * 0.05, getArrowCount(skillLevel));
        }
    }

    public static void onHit(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) <= Tick.get()) {
            return;
        }
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 4);
        if (skillV2 instanceof ManaNewSkillFinal0) {
            int skillLevel = skillV2.getPlayerSkillLevel(player);
            getManaSkillV2()
                    .stream().filter(skill -> skill.getSkillType() > 0 && skill.getSkillType() < 4)
                    .forEach(skill -> {
                        decreaseSkillCooldownTick(player, skill, getDecreaseCooldownTick(skillLevel));
                    });
        }
    }

    public static int getArrowCount(int skillLevel) {
        if (skillLevel < 8) {
            return 1;
        } else {
            return 2;
        }
    }

    public static int getDecreaseCooldownTick(int skillLevel) {
        if (skillLevel < 3) {
            return 2;
        } else if (skillLevel < 5) {
            return 3;
        } else if (skillLevel < 8) {
            return 4;
        } else {
            return 5;
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("获得持续5s的", "激化效果", CustomStyle.styleOfMana));
        components.add(Te.s("在持续时间内，普攻将额外释放",
                getArrowCount(level) + "枚法球", CustomStyle.styleOfMana));
        components.add(Te.s("法球", CustomStyle.styleOfMana, "拥有",
                getRateDescription(0.5, 0.05, level), CustomStyle.styleOfMana, "基础伤害"));
        components.add(Te.s("法球命中目标时减少", "基础技能冷却时间", ChatFormatting.AQUA,
                String.format("%.1fs", getDecreaseCooldownTick(level) * 0.05)));
        return components;
    }
}
