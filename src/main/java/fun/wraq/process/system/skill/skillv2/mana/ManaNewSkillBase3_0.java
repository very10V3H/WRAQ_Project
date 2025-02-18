package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.ManaArrowHitEntity;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.ChatFormatting;
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
import java.util.Map;
import java.util.WeakHashMap;

public class ManaNewSkillBase3_0 extends SkillV2BaseSkill {

    public ManaNewSkillBase3_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();

    public static void onShoot(Player player) {
        if (effectExpiredTickMap.getOrDefault(player, 0) <= Tick.get()) {
            return;
        }
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 3);
        if (skillV2 instanceof ManaNewSkillBase3_0) {
            WraqMixture.batchAddExShoot(player, 0.5, 1);
        }
    }

    @Override
    protected void releaseOperation(Player player) {
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        double damage = ManaNewSkill.modifyDamage(player, 1 + skillLevel * 0.05);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                effectExpiredTickMap.put(player, Tick.get() + Tick.s(3));
                Compute.sendEffectLastTime(player, getTexture1Url(), Tick.s(3), 0, false);
                if (!player.isShiftKeyDown()) {
                    Compute.sendForwardMotionPacketToPlayer(player, -1);
                }
                Item item = player.getMainHandItem().getItem();
                if (item instanceof WraqSceptre wraqSceptre) {
                    MySound.soundToNearPlayer(player.level(), player.getEyePosition(), SoundEvents.EVOKER_CAST_SPELL);
                    for (int i = 0; i < 3; i++) {
                        wraqSceptre.shootManaArrow(player, damage, true, false,
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
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前快速释放", "3枚法球", CustomStyle.styleOfMana));
        components.add(Te.s("每枚法球拥有",
                getRateDescription(1, 0.05, level), CustomStyle.styleOfMana, "伤害"));
        components.add(Te.s("法球将", "禁锢", CustomStyle.styleOfStone, "命中的敌人"));
        components.add(Te.s("释放后，会使自身向后位移一小段距离"));
        components.add(Te.s("按住shift将使后向位移失效", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s("并获得持续3s的", "激化", CustomStyle.styleOfMana));
        components.add(Te.s("在持续时间内，普通攻击将会额外释放"));
        components.add(Te.s("1枚50%基础伤害", CustomStyle.styleOfMana, "法球"));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 20;
    }
}
