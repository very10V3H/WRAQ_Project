package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.forest.ForestPowerEffectMob;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase2_0 extends SkillV2BaseSkill {

    public SwordNewSkillBase2_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevel(player);
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.skill, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Vec3 desPos = player.pick(1, 0, false).getLocation();
                Compute.getNearEntity(player, Mob.class, 8)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(mob -> {
                            Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(desPos, 20, mob));
                            Compute.addSlowDownEffect(mob, Tick.s(3), 2);
                            Damage.causeAdDamageToMonsterWithCritJudge(player, mob, 2 + 0.1 * skillLevel);
                            MySound.soundToNearPlayer(player.level(), mob.getEyePosition(), SoundEvents.ANVIL_LAND);
                        });
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("将自身周围8格内的所有敌人"));
        components.add(Te.s("牵引", CustomStyle.styleOfStone,
                "至身前，并造成", "3s减速", CustomStyle.styleOfStone));
        components.add(Te.s(
                getRateDescription(2, 0.1, level), CustomStyle.styleOfPower, "伤害"));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 8;
    }
}
