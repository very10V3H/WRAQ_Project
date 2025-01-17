package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.ManaArrowHitEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase3_0 extends SkillV2BaseSkill {

    public ManaNewSkillBase3_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Compute.sendForwardMotionPacketToPlayer(player, -1);
                Item item = player.getMainHandItem().getItem();
                if (item instanceof WraqSceptre wraqSceptre) {
                    wraqSceptre.shootManaArrow(player, 1 + skillLevel * 0.1, true, false,
                            new ManaArrowHitEntity() {
                                @Override
                                public void onHit(ManaArrow manaArrow, Entity entity) {
                                    if (entity instanceof Mob mob) {
                                        mob.addEffect(
                                                new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                                        20, 100, false, false, false));
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前方释放一枚强化法球，法球将禁锢目中的敌人"));
        components.add(Te.s("并使自身向后位移一小段距离"));
        return components;
    }
}
