package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
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

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase3_0 extends SkillV2BaseSkill {

    public SwordNewSkillBase3_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        MySound.soundToNearPlayer(player, ModSounds.Rolling.get());
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        Compute.sendForwardMotionPacketToPlayer(player, 1);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.swordNewSkillBase3_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                MySound.soundToNearPlayer(player, SoundEvents.PLAYER_ATTACK_SWEEP);
                Compute.getNearEntity(player, Mob.class, 6)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(mob -> {
                            Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob, 2 + skillLevel * 0.15);
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
        components.add(Te.s("向前突进，并对终点附近敌人造成",
                getRateDescription(2, 0.15, level), CustomStyle.styleOfPower, "伤害。"));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        components.add(Te.s("击杀", CustomStyle.styleOfRed, "任意敌人后，会",
                "刷新", ChatFormatting.AQUA, "这个技能。"));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 8;
    }
}
