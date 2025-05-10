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
import fun.wraq.process.system.skill.skillv2.SkillV2AllowInterruptNormalAttack;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase1_1 extends SkillV2BaseSkill implements SkillV2AllowInterruptNormalAttack {

    public SwordNewSkillBase1_1(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = SkillV2.getPlayerSkillLevelBySkillV2(player, this);
        Item mainHandItem = player.getMainHandItem().getItem();
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.stab, Tick.get() + 8, player) {
            @Override
            public void trig() {
                if (Utils.swordTag.containsKey(mainHandItem)) {
                    MySound.soundToNearPlayer(player, SoundEvents.PLAYER_ATTACK_KNOCKBACK);
                    AttackEvent.getPlayerNormalAttackRangeMobList(player).forEach(mob -> {
                        AttackEvent.attackToMonster(mob, player,
                                (1.5 + skillLevel * 0.1) * (1 + getEnhanceRate(player)), true, true);
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
        components.add(Te.s(ComponentUtils.getCritDamageInfluenceDescription(),
                "并", ComponentUtils.getAttackEffectDescription()));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 5;
    }
}
