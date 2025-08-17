package fun.wraq.series.overworld.cold.sc5.dragon.weapon;

import com.github.alexthe666.iceandfire.enums.EnumParticles;
import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.core.AttackEvent;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface SuperColdDragonWeaponCommon extends Decomposable, ForgeItem {
    int getWeaponTier();
    // 共有被动：极寒龙息
    static void handleTick(Player player) {
        if (player.tickCount % 20 != 5) {
            return;
        }
        Mob mob = Compute.getDefaultTarget(player);
        if (mob == null) {
            mob = Compute.getNearestMob(player, 16);
        }
        if (mob == null) {
            return;
        }
        Vec3 startPos = Compute.getPlayerHandItemPos(player, true);
        ParticleProvider.createIafLineParticle(player.level(),
                (int) mob.getEyePosition().distanceTo(startPos) * 5, startPos, mob.getEyePosition(),
                EnumParticles.DragonIce);
        List<Mob> mobs = player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(mob.position(), 4, 4, 4));
        double rate = 1 + Math.min(4, ElementValue.getPlayerIceElementValue(player) / 2.5);
        mobs.forEach(eachMob -> {
            Compute.createIceParticle(eachMob);
            adaptiveNormalAttack(player, eachMob, rate);
        });
    }

    // 共有主动：冰雨
    static void active(Player player) {
        MySound.soundToPlayer(player, SoundEvents.SNOW_BREAK);
        Vec3 pos = WraqPower.getDefaultTargetPos(player, 24);
        ParticleProvider.createBallDisperseParticle(ParticleTypes.SNOWFLAKE,
                (ServerLevel) player.level(), pos, 1, 40);
        Vec3 startPos = Compute.getPlayerHandItemPos(player, true);
        ParticleProvider.createIafLineParticle(player.level(),
                (int) pos.distanceTo(startPos) * 5, startPos, pos, EnumParticles.DragonIce);
        double rate = (1 + Math.min(4, ElementValue.getPlayerIceElementValue(player) / 2.5)) * 0.33;
        Compute.getNearMob(player.level(), pos, 8).forEach(mob -> {
            adaptiveNormalAttack(player, mob, rate);
        });
        PersistentRangeEffect.addEffect(player, pos, 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangeMob().forEach(mob -> {
                    adaptiveNormalAttack(player, mob, rate);
                });
                ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), pos,
                        8, 100, ParticleTypes.SNOWFLAKE);
            }
        }, 20, Tick.s(3));
        SuperColdDragonSword.items.forEach(item -> player.getCooldowns().addCooldown(item, Tick.s(1)));
        SuperColdDragonBow.items.forEach(item -> player.getCooldowns().addCooldown(item, Tick.s(1)));
        SuperColdDragonSceptre.items.forEach(item -> player.getCooldowns().addCooldown(item, Tick.s(1)));
    }

    private static void adaptiveNormalAttack(Player player, Mob eachMob, double rate) {
        int type = 0;
        if (player.getMainHandItem().getItem() instanceof WraqBow) {
            type = 1;
        } else if (player.getMainHandItem().getItem() instanceof WraqSceptre) {
            type = 2;
        }
        if (type == 0) {
            AttackEvent.attackToMonster(eachMob, player, rate, false, false);
        } else if (type == 1) {
            MyArrow.causeDamage(player, eachMob, rate, false);
        } else {
            Damage.causeManaDamageToMonster(player, eachMob,
                    PlayerAttributes.manaDamage(player) * rate, true);
        }
    }

    // 被动2 强化基础技能
    static double getSkillExRange(Player player) {
        if (player.getMainHandItem().getItem() instanceof SuperColdDragonWeaponCommon superColdDragonWeaponCommon) {
            if (superColdDragonWeaponCommon.getWeaponTier() >= 1) {
                return 1 + Math.min(4, ElementValue.getPlayerIceElementValue(player) / 2.5);
            }
        }
        return 0;
    }

    // 被动2 强化基础技能
    static void addImprisonEffectToMob(Player player, Mob mob) {
        if (player.getMainHandItem().getItem() instanceof SuperColdDragonWeaponCommon superColdDragonWeaponCommon) {
            if (superColdDragonWeaponCommon.getWeaponTier() >= 1) {
                Compute.addImprisonEffectToMob(mob, 30);
                Compute.createIceParticle(mob);
            }
        }
    }

    Style style = CustomStyle.styleOfIce;

    static void handleCommonPassive1Description(List<Component> components) {
        ComponentUtils.descriptionPassive(components, Te.s("极寒龙息", style));
        components.add(Te.s(" 间隔1s，对准星选定或最近的敌人释放", "极寒龙息", style));
        components.add(Te.s(" 造成", "自适应伤害", CustomStyle.styleOfSea));
        components.add(Te.s(" 倍率可由", "冰元素强度", style, "，至多提升至", "500%", style));
        components.add(Te.s(" 拥有", "1000冰元素强度", style, "获得最大倍率"));
    }

    static void handleSwordPassive2Description(List<Component> components) {
        ComponentUtils.descriptionPassive(components, Te.s("极寒践踏", style));
        components.add(Te.s(" 践踏的范围基于", "冰元素强度", style, "至多增加", "5格"));
        components.add(Te.s(" 对范围内的所有敌人施加", "禁锢效果", style));
    }

    static void handleBowPassive2Description(List<Component> components) {
        ComponentUtils.descriptionPassive(components, Te.s("寒爆箭矢", style));
        components.add(Te.s(" 烈矢的范围基于", "冰元素强度", style, "至多增加", "5格"));
        components.add(Te.s(" 对范围内的所有敌人施加", "禁锢效果", style));
    }

    static void handleSceptrePassive2Description(List<Component> components) {
        ComponentUtils.descriptionPassive(components, Te.s("极寒撕裂", style));
        components.add(Te.s(" 撕裂的范围基于", "冰元素强度", style, "至多增加", "5格"));
        components.add(Te.s(" 对范围内的所有敌人施加", "禁锢效果", style));
    }

    static void handleCommonActiveDescription(List<Component> components) {
        ComponentUtils.descriptionActive(components, Te.s("极寒之域", style));
        components.add(Te.s(" 在目标区域制造一片领域，领域持续", "3s", ChatFormatting.AQUA));
        components.add(Te.s(" 每秒对领域内的敌人造成", "自适应伤害", CustomStyle.styleOfSea));
        components.add(Te.s(" 倍率可由", "冰元素强度", style, "，至多提升至", "500%", style));
        ComponentUtils.getStableCoolDownTimeDescription(components, 1);
    }
}
