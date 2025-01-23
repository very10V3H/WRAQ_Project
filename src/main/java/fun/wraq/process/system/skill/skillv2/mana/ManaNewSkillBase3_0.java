package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.ManaArrowHitEntity;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase3_0 extends SkillV2BaseSkill {

    public ManaNewSkillBase3_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Compute.sendForwardMotionPacketToPlayer(player, -1);
                Item item = player.getMainHandItem().getItem();
                if (item instanceof WraqSceptre wraqSceptre) {
                    MySound.soundToNearPlayer(player.level(), player.getEyePosition(), SoundEvents.EVOKER_CAST_SPELL);
                    wraqSceptre.shootManaArrow(player, 2.5 + skillLevel * 0.2, true, false,
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
        components.add(Te.s("向前方释放一枚", getRateDescription(2.5, 0.2, level),
                CustomStyle.styleOfMana, "伤害的", "强化法球", CustomStyle.styleOfMana));
        components.add(Te.s("法球将", "禁锢", CustomStyle.styleOfStone, "第一个命中的敌人"));
        components.add(Te.s("并使自身向后位移一小段距离"));
        return components;
    }
}
