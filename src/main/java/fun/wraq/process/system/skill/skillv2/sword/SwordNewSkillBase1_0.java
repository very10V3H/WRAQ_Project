package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.AttackEvent;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase1_0 extends SkillV2BaseSkill {

    public SwordNewSkillBase1_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        int skillLevel = SkillV2.getPlayerSkillLevelBySkillV2(player, this);
        Item mainHandItem = player.getMainHandItem().getItem();
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.samurai, Tick.get() + 8, player) {
            @Override
            public void trig() {
                if (Utils.swordTag.containsKey(mainHandItem)) {
                    SwordNewSkillFinal0.onPlayerNormalAttack(player);
                    MySound.soundToNearPlayer(player, SoundEvents.PLAYER_ATTACK_KNOCKBACK);
                    AttackEvent.getPlayerNormalAttackRangeMobList(player).forEach(mob -> {
                        AttackEvent.attackToMonster(mob, player, 1.5 + skillLevel * 0.1, true, true);
                    });
                }
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对前方所有敌人造成",
                getRateDescription(1.5, 0.1, level), CustomStyle.styleOfPower, "伤害。"));
        components.add(Te.s("必定暴击", CustomStyle.styleOfPower,
                "且", ComponentUtils.getAttackEffectDescription()));
        return components;
    }
}
