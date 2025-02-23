package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class SwordNewSkillBase3_0 extends SkillV2BaseSkill {

    public SwordNewSkillBase3_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<String, Integer> skillEndTickMap = new HashMap<>();
    public static Map<String, Set<Mob>> lineAttackMobSetMap = new HashMap<>();

    public static void handleServerPlayerTick(Player player) {
        if (skillEndTickMap.getOrDefault(Name.get(player), 0) == Tick.get()) {
            lineAttackMobSetMap.get(Name.get(player)).clear();
        } else if (skillEndTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            if (!lineAttackMobSetMap.containsKey(Name.get(player))) {
                lineAttackMobSetMap.put(Name.get(player), new HashSet<>());
            }
            SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 3);
            int skillLevel = skillV2.getPlayerSkillLevel(player);
            Set<Mob> set = lineAttackMobSetMap.get(Name.get(player));
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                            player.getBoundingBox().expandTowards(player.getDeltaMovement()).inflate(1.0D))
                    .stream().toList();
            mobList.stream().filter(mob -> !set.contains(mob))
                    .forEach(mob -> {
                        Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob,
                                (2 + skillLevel * 0.15) * (1 + skillV2.getEnhanceRate(player)));
                    });
            set.addAll(mobList);
        }
    }

    @Override
    protected void releaseOperation(Player player) {
        MySound.soundToNearPlayer(player, ModSounds.Rolling.get());
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        Compute.sendForwardMotionPacketToPlayer(player, 1 + PlayerAttributes.movementSpeedCurrent(player));
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        skillEndTickMap.put(Name.get(player), Tick.get() + 8);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.swordNewSkillBase3_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                MySound.soundToNearPlayer(player, SoundEvents.PLAYER_ATTACK_SWEEP);
                Compute.getNearEntity(player, Mob.class, 6)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(mob -> {
                            Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob,
                                    (2 + skillLevel * 0.15) * (1 + getEnhanceRate(player)));
                        });
            }
        });
    }

    public static void onKillMob(Player player) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 3);
        if (skillV2 instanceof SwordNewSkillBase3_0) {
            SkillV2.decreaseSkillCooldownTick(player, skillV2, skillV2.cooldownTick);
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前突进，对沿途及终点附近敌人造成",
                getRateDescription(2, 0.15, level), CustomStyle.styleOfPower, "伤害。"));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        components.add(Te.s("击杀", CustomStyle.styleOfRed, "任意敌人后，会",
                "刷新", ChatFormatting.AQUA, "这个技能。"));
        components.add(Te.s("位移的距离收益于玩家当前移动速度", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 8;
    }
}
