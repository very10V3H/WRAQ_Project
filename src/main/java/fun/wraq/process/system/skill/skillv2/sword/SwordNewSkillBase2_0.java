package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowInterruptNormalAttack;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.forest.ForestPowerEffectMob;
import fun.wraq.series.overworld.cold.sc5.dragon.weapon.SuperColdDragonWeaponCommon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase2_0 extends SkillV2BaseSkill implements SkillV2AllowInterruptNormalAttack {

    public SwordNewSkillBase2_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevel(player);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.skill, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Vec3 desPos = WraqPower.getDefaultTargetPos(player, 8);
                double range = 8;
                double exRange = 0;
                exRange += SuperColdDragonWeaponCommon.getSkillExRange(player);
                Compute.getNearMob(player.level(), desPos, range + exRange)
                        .forEach(mob -> {
                            Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(desPos, 10, mob));
                            Compute.addSlowDownEffect(mob, Tick.s(3), 2);
                            Damage.causeAdDamageToMonsterWithCritJudge(player, mob,
                                    (2 + 0.1 * skillLevel) * (1 + getEnhanceRate(player)));
                            MySound.soundToNearPlayer(player.level(), mob.getEyePosition(), SoundEvents.ANVIL_LAND);
                            mob.setTarget(player);
                            SuperColdDragonWeaponCommon.addImprisonEffectToMob(player, mob);
                        });
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("将准星位置周围8格内的所有敌人"));
        components.add(Te.s("牵引", CustomStyle.styleOfStone,
                "至准星位置，并造成", "3s减速", CustomStyle.styleOfStone, "与",
                Te.s(getRateDescription(2, 0.1, level), CustomStyle.styleOfPower, "伤害")));
        components.add(Te.s(" 这会将这些敌人的仇恨转移到自身", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 8;
    }
}
