package fun.wraq.core;

import fun.wraq.commands.stable.players.DebugCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.attribute.SameTypeModule;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnCritHitEffectMainHandWeapon;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.customized.uniform.attack.AttackCurios1;
import fun.wraq.customized.uniform.attack.AttackCurios3;
import fun.wraq.customized.uniform.attack.AttackCurios4;
import fun.wraq.events.modules.AttackEventModule;
import fun.wraq.events.modules.HurtEventModule;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.CritHitParticleS2CPacket;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.skillv2.sword.SwordNewSkillPassive0;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.passive.whetstone.Whetstone;
import fun.wraq.series.comsumable.passive.whetstone.WhetstoneAttack;
import fun.wraq.series.end.citadel.CitadelCurio;
import fun.wraq.series.instance.blade.WraqBlade;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import fun.wraq.series.instance.series.castle.CastleSword;
import fun.wraq.series.nether.equip.attack.sword.ManaSword;
import fun.wraq.series.overworld.chapter2.blackForest.HuskSword;
import fun.wraq.series.overworld.chapter2.sea.Sword.SeaSword;
import fun.wraq.series.overworld.chapter7.BoneImpKnife;
import fun.wraq.series.overworld.extraordinary.equip.KanupusSword;
import fun.wraq.series.overworld.sakura.SakuraMob.SakuraSword;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class AttackEvent {
    @SubscribeEvent
    public static void Attack(AttackEntityEvent event) {
        if (!event.getEntity().level().isClientSide) {
            event.setCanceled(true);
            Player player = event.getEntity();
            Entity entity = event.getTarget();
            if (entity.getClass() == Villager.class ||
                    entity.getClass() == WanderingTrader.class ||
                    (entity instanceof Animal && !entity.hasCustomName())
            ) event.setCanceled(true);                    //保护动物人人有责。
            CompoundTag data = player.getPersistentData();
            data.putBoolean("ARROW", false);
            if (player.getName().getString().equals("very_H") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765))
                    || player.getName().getString().equals("Dev") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765))
                    || player.isCreative() && player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.STICK))
                entity.remove(Entity.RemovalReason.KILLED);
        }
    }

    public static List<Mob> getPlayerNormalAttackRangeMobList(Player player) {
        double rangeEnhanceRate = AttackCurios3.getAttackRangeEnhanceRate(player);
        return Compute.getPlayerRayMobList(player, 0.25, 1.25 * (1 + rangeEnhanceRate),
                        (4 + PlayerAttributes.attackRangeUp(player)) * (1 + rangeEnhanceRate))
                .stream().filter(LivingEntity::isAlive).toList();
    }

    public static void module(Player player, double rate) {
        List<Mob> mobList = new ArrayList<>(getPlayerNormalAttackRangeMobList(player));
        AtomicReference<Mob> nearestMob = new AtomicReference<>();
        AtomicReference<Double> distance = new AtomicReference<>((double) 20);
        mobList.forEach(mob -> {
            if (mob.distanceTo(player) < distance.get()) {
                nearestMob.set(mob);
                distance.set((double) mob.distanceTo(player));
            }
        });
        mobList.removeIf(mob -> mob instanceof Allay || mob instanceof Animal);
        if (nearestMob.get() != null) {
            ItemStack itemStack = player.getMainHandItem();
            rate += WhetstoneAttack.getExAttackRate(player);
            if (itemStack.getItem() instanceof KanupusSword) {
                rate *= 0.5;
            }
            boolean crit = AttackEvent.crit(player, nearestMob.get(), false);
            AttackEvent.attackToMonster(nearestMob.get(), player, rate *
                    (mobList.size() == 1 ? 1 + SwordNewSkillPassive0.exTargetsDamageRate(player) : 1),
                    true, crit);
            if (player.getMainHandItem().getItem() instanceof KanupusSword) {
                MyArrow.causeDamage(player, nearestMob.get(), 0.5, true);
            }
            AttackEventModule.SwordSkill3Attack(player.getPersistentData(), player, nearestMob.get());// 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
            AttackEventModule.SwordSkill12Attack(player.getPersistentData(), player); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
            double finalRate = rate;
            mobList.forEach(mob -> {
                if (mob != nearestMob.get()) {
                    AttackEvent.attackToMonster(mob, player,
                            finalRate * Math.min(1, SwordNewSkillPassive0.exTargetsDamageRate(player)),
                            false, crit);
                    if (player.getMainHandItem().getItem() instanceof KanupusSword) {
                        MyArrow.causeDamage(player, mob, 0.5, false);
                    }
                }
            });
            Whetstone.onReleaseNormalAttackAndHit(player);
        }
    }

    public static boolean crit(Player player, Mob mob, boolean critSurely) {
        double critRate = PlayerAttributes.critRate(player);
        if (critSurely) critRate = 1;
        if (BoneImpKnife.passive(player, mob)) critRate = 1;
        return RandomUtils.nextDouble(0, 1) < critRate;
    }

    public static void attackToMonster(Mob monster, Player player, double rate, boolean mainAttack, boolean crit) {
        if (SpecialEffectOnPlayer.inBlind(player)) {
            Compute.summonValueItemEntity(monster.level(), player, monster,
                    Te.s("未命中", CustomStyle.styleOfEnd), 0);
            return;
        }
        Item equip = player.getMainHandItem().getItem();
        CompoundTag data = player.getPersistentData();
        Utils.PlayerFireWorkFightCoolDown.put(player, Tick.get() + 200);

        double defence = MobAttributes.defence(monster);

        if (mainAttack) {
            rate += DamageInfluence.getPlayerNormalAttackBaseDamageEnhance(player, 0);
            rate += AttackCurios4.getAttackDamageRate(player, monster);
        }

        double baseDamage = PlayerAttributes.attackDamage(player) * rate;
        double defencePenetration = PlayerAttributes.defencePenetration(player);
        double defencePenetration0 = PlayerAttributes.defencePenetration0(player);

        double critDamage = PlayerAttributes.critDamage(player);

        if (Utils.SnowRune2MobController.contains(monster)) defence *= 0.5f;
        if (monster instanceof Evoker && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaSword)
            defencePenetration = 1.0d;

        if (defence == 0) defence = (monster.getAttribute(Attributes.ARMOR).getValue());

        double damage;
        double exDamage = 0;
        double trueDamage = 0;
        double damageEnhance = 0;
        double damageBeforeDefence;

        // 变量定义与部分效果附加

        AttackEventModule.MineSwordAndSnowSwordSlowDownForce(equip, monster);
        AttackEventModule.SnowArmorEffect(player, monster); //冰川增幅

        exDamage += HuskSword.getHuskSwordExDamage(player, monster); // 灵魂收割者主动
        exDamage += AttackEventModule.SwordSkill12(data, player, baseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
        exDamage += AttackEventModule.SoulSwordActive(player); // 本源具象

        trueDamage += SeaSword.getSeaSwordExDamage(player, monster); //灵魂救赎者主动
        trueDamage += AttackEventModule.SwordSkill0(data, baseDamage); //剑术热诚（获得1%额外真实伤害）
        trueDamage += AttackEventModule.SwordSkill13(data, player, baseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
        trueDamage += AttackEventModule.SwordSkill14(data, player, baseDamage, monster); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）
        trueDamage += CastleAttackArmor.ExIgnoreDefenceDamage(player);
        damageEnhance += AttackEventModule.SwordSkill3(data, player, monster); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, monster);

        double NormalAttackDamageEnhance = 0;
        NormalAttackDamageEnhance += DamageInfluence.getPlayerNormalSwordAttackDamageEnhance(player); // 普通近战攻击伤害加成

        if (crit) {
            damageBeforeDefence = baseDamage * (1 + critDamage);
            data.putBoolean(StringUtils.DamageTypes.Crit, true);
            if (mainAttack) {
                AttackEventModule.SwordSkill5Attack(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续3s）
                AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
                AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
                HurtEventModule.SabreDamage(player, monster);
                AttackEventModule.snowShieldEffect(player, monster);
                AttackCurios1.playerCritEffect(player);
                OnCritHitEffectMainHandWeapon.critHit(player, monster);
                MySound.soundToPlayer(player, SoundEvents.PLAYER_ATTACK_CRIT);
            }
            ModNetworking.sendToClient(new CritHitParticleS2CPacket(monster.getX(), monster.getY(), monster.getZ()), (ServerPlayer) player);
        } else {
            damageBeforeDefence = baseDamage;
            data.putBoolean(StringUtils.DamageTypes.Crit, false);
            if (mainAttack) {
                AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
                AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
                HurtEventModule.SabreDamage(player, monster);
                MySound.soundToPlayer(player, SoundEvents.PLAYER_ATTACK_STRONG);
            }
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
            player.sendSystemMessage(Component.literal("---NormalAttack---"));
            player.sendSystemMessage(Component.literal("DamageBeforeDefence : " + damageBeforeDefence));
            player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
            player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + trueDamage));
        }

        // Normal attack enhance
        damageBeforeDefence *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance);
        // Damage enhance
        exDamage *= (1 + damageEnhance);
        trueDamage *= (1 + damageEnhance);
        // ExDamage
        damageBeforeDefence += exDamage;
        // 妖刀伤害影响
        trueDamage += SakuraSword.SakuraDemonSword(player, damageBeforeDefence);
        // Final damage decrease
        damageBeforeDefence *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        trueDamage *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        // Defence compute
        damage = damageBeforeDefence * Damage.defenceDamageDecreaseRate(player, monster,
                defence, defencePenetration, defencePenetration0);
        // total damage
        damage *= DamageInfluence.getPlayerTotalDamageRate(player);
        trueDamage *= DamageInfluence.getPlayerTotalDamageRate(player);
        // livingEntity control
        damage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        trueDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        // 至此 关于基本的计算已结束 下方是最终乘区的计算
        trueDamage += BoneImpKnife.exTrueDamage(player, monster) * damage;

        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        String elementType = "";

        Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
        elementType = playerUnit.type();
        if (playerUnit.value() > 0) {
            ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(),
                    playerUnit.value(), true, damage + trueDamage);
        }

        double elementDamage = (damage + trueDamage) * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

        damage *= ((1 + ElementDamageEnhance) * ElementDamageEffect);
        trueDamage *= ((1 + ElementDamageEnhance) * ElementDamageEffect);
        // Final damage cause
        Damage.beforeCauseDamage(player, monster, damage + trueDamage);
        Damage.causeDirectDamageToMob(player, monster, damage + trueDamage);
        // Health steal
        Compute.healByHealthSteal(player, monster, damage);
        // Display
        if (crit) {
            Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + trueDamage)).withStyle(CustomStyle.styleOfPower), 0);
        }
        else {
            Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + trueDamage)).withStyle(ChatFormatting.YELLOW), 0);
        }
        if (mainAttack) {
            if (elementDamage != 0 && !elementType.isEmpty())
                Compute.damageActionBarPacketSend(player, damage, trueDamage, false, crit, elementType, elementDamage);
            else Compute.damageActionBarPacketSend(player, damage, trueDamage, false, crit);
            SameTypeModule.onNormalAttackHitMob(player, monster, 0, damage + trueDamage);
            OnHitEffectEquip.hit(player, monster);
            OnHitEffectCurios.hit(player, monster);
            OnHitEffectPassiveEquip.hit(player, monster);
            EnhanceNormalAttackModifier.onHitEffect(player, monster, 0);
            CitadelCurio.onNormalAttackOrSkillHit(player, monster, damage + trueDamage, true);
            Compute.additionEffects(player, monster, damage + trueDamage, 0);
        }
        // effect
        Compute.ChargingModule(data, player);
        CastleSword.onNormalAttack(player, monster, damage);
        WraqBlade.onAttackHitEachTarget(player);
        SeaSword.checkSeaSwordEffect(player, monster);
        HuskSword.checkHuskSwordEffect(player, monster);

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
            player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhance : " + damageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : "
                    + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : "
                    + Damage.defenceDamageDecreaseRate(player, monster, defence, defencePenetration, defencePenetration0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("MonsterControl : " + DamageInfluence.getMonsterControlDamageEffect(player, monster)));
            player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (damage + trueDamage)));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }
    }
}