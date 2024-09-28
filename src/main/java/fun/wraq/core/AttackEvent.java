package fun.wraq.core;

import fun.wraq.commands.stable.players.DebugCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.attribute.SameTypeModule;
import fun.wraq.common.impl.onhit.OnHitEffectArmor;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Boss2Damage;
import fun.wraq.customized.uniform.attack.AttackCurios1;
import fun.wraq.entities.entities.Boss2.Boss2;
import fun.wraq.events.fight.HurtEvent;
import fun.wraq.events.modules.AttackEventModule;
import fun.wraq.events.modules.HurtEventModule;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.CritHitParticleS2CPacket;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import fun.wraq.series.instance.series.castle.CastleSword;
import fun.wraq.series.instance.series.moon.Equip.MoonShield;
import fun.wraq.series.instance.series.moon.MoonCurios;
import fun.wraq.series.nether.Equip.ManaSword;
import fun.wraq.series.overworld.castle.BlazeBracelet;
import fun.wraq.series.overworld.chapter7.BoneImpKnife;
import fun.wraq.series.overworld.sakuraSeries.SakuraMob.SakuraSword;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
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
                    entity instanceof Animal
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
        return Compute.getPlayerRayMobList(player, 0.25, 1.25, 4 + PlayerAttributes.attackRangeUp(player));
    }

    public static void module(Player player, double rate) {
        List<Mob> mobList = getPlayerNormalAttackRangeMobList(player);

        AtomicReference<Mob> NearestMob = new AtomicReference<>();
        AtomicReference<Double> Distance = new AtomicReference<>((double) 20);
        mobList.forEach(mob -> {
            if (mob.distanceTo(player) < Distance.get()) {
                NearestMob.set(mob);
                Distance.set((double) mob.distanceTo(player));
            }
        });

        if (NearestMob.get() != null) {
            AttackEvent.attackToMonster(NearestMob.get(), player, rate, false);
            HurtEventModule.ForestRune3Judge(player, NearestMob.get(), PlayerAttributes.attackDamage(player));
            HurtEvent.BlazeReflectDamage(NearestMob.get(), player);
            AttackEventModule.SwordSkill3Attack(player.getPersistentData(), player, NearestMob.get());// 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
            AttackEventModule.SwordSkill12Attack(player.getPersistentData(), player); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
            mobList.forEach(mob -> {
                if (mob != NearestMob.get()) AttackEvent.attackToMonster(mob, player, 0.5, false);
            });
        }
    }

    public static void attackToMonster(Mob monster, Player player, double rate, boolean critSurely) {
        Item equip = player.getMainHandItem().getItem();
        CompoundTag data = player.getPersistentData();
        Utils.PlayerFireWorkFightCoolDown.put(player, player.getServer().getTickCount() + 200);

        boolean mainAttack = (rate > 0.5);

        double defence = MobAttributes.defence(monster);

        double baseDamage = PlayerAttributes.attackDamage(player) * rate;
        if (mainAttack) {
            baseDamage *= DamageInfluence.getPlayerNormalAttackBaseRate(player, 0);
        }

        double defencePenetration = PlayerAttributes.defencePenetration(player);
        double defencePenetration0 = PlayerAttributes.defencePenetration0(player);

        double critRate = PlayerAttributes.critRate(player);
        if (critSurely) critRate = 1;
        double critDamage = PlayerAttributes.critDamage(player);

        if (Utils.SnowRune2MobController.contains(monster)) defence *= 0.5f;
        if (monster instanceof Evoker && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaSword)
            defencePenetration = 1.0d;

        Random r = new Random();
        double RanNum = r.nextDouble(1);
        if (defence == 0) defence = (monster.getAttribute(Attributes.ARMOR).getValue());

        double damage;
        double exDamage = 0;
        double damageIgnoreDefence = 0;
        double damageEnhance = 0;
        double damageBeforeDefence;

        // 变量定义与部分效果附加

        AttackEventModule.MineSwordAndSnowSwordSlowDownForce(equip, monster);
        AttackEventModule.SnowArmorEffect(player, monster); //冰川增幅

        exDamage += AttackEventModule.BlackForest(player, monster); // 灵魂收割者主动
        exDamage += AttackEventModule.SwordSkill12(data, player, baseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
        exDamage += AttackEventModule.SoulSwordActive(player); // 本源具象

        damageIgnoreDefence += AttackEventModule.SeaSword(player, monster); //灵魂救赎者主动
        damageIgnoreDefence += AttackEventModule.SwordSkill0(data, baseDamage); //剑术热诚（获得1%额外真实伤害）
        damageIgnoreDefence += AttackEventModule.SwordSkill13(data, player, baseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
        damageIgnoreDefence += AttackEventModule.SwordSkill14(data, player, baseDamage, monster); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）
        damageIgnoreDefence += MoonCurios.Passive(player, monster); // 朔望馈赠
        damageIgnoreDefence += MoonShield.MoonShield(player, monster); // 尘月护盾
        damageIgnoreDefence += CastleAttackArmor.ExIgnoreDefenceDamage(player);
        damageEnhance += AttackEventModule.SwordSkill3(data, player, monster); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, monster);

        double NormalAttackDamageEnhance = 0;
        NormalAttackDamageEnhance += DamageInfluence.getPlayerNormalSwordAttackDamageEnhance(player); // 普通近战攻击伤害加成

        boolean critFlag = false;
        if (BoneImpKnife.passive(player, monster)) critRate = 1;
        if (RanNum < critRate) {
            critFlag = true;
            damageBeforeDefence = baseDamage * (1 + critDamage);
            data.putBoolean(StringUtils.DamageTypes.Crit, true);
            AttackEventModule.SwordSkill5Attack(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续3s）
            AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
            AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            AttackEventModule.IceSword(player, monster); // 蔚境冰锥
            ModNetworking.sendToClient(new CritHitParticleS2CPacket(monster.getX(), monster.getY(), monster.getZ()), (ServerPlayer) player);
            ModNetworking.sendToClient(new SoundsS2CPacket(0), (ServerPlayer) player);
            HurtEventModule.SabreDamage(player, monster);
            AttackEventModule.snowShieldEffect(player, monster);
            AttackCurios1.playerCritEffect(player);
        } else {
            damageBeforeDefence = baseDamage;
            data.putBoolean(StringUtils.DamageTypes.Crit, false);
            AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
            AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            ModNetworking.sendToClient(new SoundsS2CPacket(1), (ServerPlayer) player);
            HurtEventModule.SabreDamage(player, monster);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
            player.sendSystemMessage(Component.literal("---NormalAttack---"));
            player.sendSystemMessage(Component.literal("DamageBeforeDefence : " + damageBeforeDefence));
            player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
            player.sendSystemMessage(Component.literal("DamageIgnoreDefence : " + damageIgnoreDefence));
        }

        // Normal attack enhance
        damageBeforeDefence *= (1 + damageEnhance) * (1 + NormalAttackDamageEnhance);
        // Damage enhance
        exDamage *= (1 + damageEnhance);
        damageIgnoreDefence *= (1 + damageEnhance);
        // ExDamage
        damageBeforeDefence += exDamage;
        // 妖刀伤害影响
        damageIgnoreDefence += SakuraSword.SakuraDemonSword(player, damageBeforeDefence);
        // Final damage decrease
        damageBeforeDefence *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        damageIgnoreDefence *= (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        // Defence compute
        damage = damageBeforeDefence * Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
        // total damage
        damage *= DamageInfluence.getPlayerTotalDamageRate(player);
        damageIgnoreDefence *= DamageInfluence.getPlayerTotalDamageRate(player);
        // mob control
        damage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        damageIgnoreDefence *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        // 至此 关于基本的计算已结束 下方是最终乘区的计算
        damageIgnoreDefence += BoneImpKnife.exDamageIgnoreDefence(player, monster) * damage;

        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        String elementType = "";

        ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
        Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
        elementType = playerUnit.type();
        if (playerUnit.value() > 0) {
            ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), true, damage + damageIgnoreDefence);
            Element.entityElementUnit.put(player, new Element.Unit(Element.life, 0));
        }

        double elementDamage = (damage + damageIgnoreDefence) * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

        damage *= ((1 + ElementDamageEnhance) * ElementDamageEffect);
        damageIgnoreDefence *= ((1 + ElementDamageEnhance) * ElementDamageEffect);
        // Final damage cause
        Damage.DirectDamageToMob(player, monster, damage + damageIgnoreDefence);
        // Health steal
        Compute.healByHealthSteal(player, damage * PlayerAttributes.healthSteal(player));
        // Display
        if (critFlag)
            Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + damageIgnoreDefence)).withStyle(CustomStyle.styleOfPower), 0);
        else
            Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage + damageIgnoreDefence)).withStyle(ChatFormatting.YELLOW), 0);

        if (mainAttack) {
            if (elementDamage != 0 && !elementType.isEmpty())
                Compute.damageActionBarPacketSend(player, damage, damageIgnoreDefence, false, critFlag, elementType, elementDamage);
            else Compute.damageActionBarPacketSend(player, damage, damageIgnoreDefence, false, critFlag);
            SameTypeModule.onNormalAttackHitMob(player, monster, 0, damage + damageIgnoreDefence);
            if (equip instanceof OnHitEffectMainHandWeapon onHitEffectMainHandWeapon) {
                onHitEffectMainHandWeapon.onHit(player, monster);
            }
            OnHitEffectCurios.hit(player, monster);
            OnHitEffectPassiveEquip.hit(player, monster);
            EnhanceNormalAttackModifier.onHitEffect(player, monster, 0);
            OnHitEffectArmor.hit(player, monster);
        }

        // effect
        SpringAttackArmor(player, monster);
        Compute.ChargingModule(data, player);
        CastleSword.NormalAttack(player, monster, damage);
        BlazeBracelet.Passive(player, monster); // 熔岩手镯
        Compute.AdditionEffects(player, monster, damage + damageIgnoreDefence, 0);

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false)) {
            player.sendSystemMessage(Component.literal("NormalAttackDamageEnhance : " + NormalAttackDamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhance : " + damageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, DefencePenetration, DefencePenetration0) : " + Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("MonsterControl : " + DamageInfluence.getMonsterControlDamageEffect(player, monster)));
            player.sendSystemMessage(Component.literal("Damage + DamageIgnoreDefence : " + (damage + damageIgnoreDefence)));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }
    }

    public static void AttackToPlayer(Player player, Player hurter, CompoundTag data, Item equip, double Rate) {
        double Defence = PlayerAttributes.defence(hurter);
        double BaseDamage = PlayerAttributes.attackDamage(player) * Rate;
        double BreakDefence = PlayerAttributes.defencePenetration(player);
        double CriticalHitRate = PlayerAttributes.critRate(player);
        double CHitDamage = PlayerAttributes.critDamage(player);
        double BreakDefence0 = PlayerAttributes.defencePenetration0(player);
        Random r = new Random();
        double RanNum = r.nextDouble(1.00d);
        if (Defence == 0) Defence = hurter.getAttribute(Attributes.ARMOR).getValue();
        double damage;
        double ExDamage = 0;
        double ExDamageIgnoreDefence = 0;
        double DamageEnhance = 0;
        double DamageBeforeDefence = 0;

        ExDamage += AttackEventModule.ToPlayerBlackForest(data, hurter);
        ExDamage += AttackEventModule.ToPlayerForestRune3(data, hurter);
        ExDamage += AttackEventModule.SwordSkill12(data, player, BaseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
        ExDamageIgnoreDefence += AttackEventModule.ToPlayerSeaSword(data, hurter);
        ExDamageIgnoreDefence += AttackEventModule.ToPlayerVolcanoRune2(data, hurter);
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill0(data, BaseDamage); //剑术热诚（获得1%额外真实伤害）
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill13(data, player, BaseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill14(data, player, BaseDamage, hurter); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）

        DamageEnhance += AttackEventModule.SwordSkill3(data, player, hurter); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
        DamageEnhance += Compute.getSwordSkillLevel(data, 4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）

        if (RanNum < CriticalHitRate && Rate >= 0.8) { // 暴击
            DamageBeforeDefence += (BaseDamage * (1.0d + CHitDamage) + ExDamage);
            data.putBoolean(StringUtils.DamageTypes.Crit, true);
            AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
            AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）

            AttackEventModule.ToPlayerBlackForestGet2(data, hurter, CHitDamage, BreakDefence0, BreakDefence, Defence);
        } else { //非暴击
            DamageBeforeDefence += BaseDamage + ExDamage;
            data.putBoolean(StringUtils.DamageTypes.Crit, false);
            AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
            AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            AttackEventModule.ToPlayerBlackForestGet4(data, hurter, CHitDamage, BreakDefence0, BreakDefence, Defence);
        }

        DamageBeforeDefence *= (1 + DamageEnhance);
        ExDamageIgnoreDefence *= (1 + DamageEnhance);

        DamageBeforeDefence -= SakuraSword.SakuraDemonSword(player, DamageBeforeDefence);
        ExDamageIgnoreDefence += SakuraSword.SakuraDemonSword(player, DamageBeforeDefence);
        // 妖刀伤害影响

        damage = DamageBeforeDefence * Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        data.putDouble(StringUtils.DamageTypes.ToPlayerDamage, (damage + ExDamageIgnoreDefence) * 0.1f);
        Damage.DirectDamageToPlayer(player, hurter, (damage + ExDamageIgnoreDefence) * 0.1f);
    }

    public static void Boss2DamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster instanceof Boss2 && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.boss2DamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.boss2DamageList.add(boss2Damage);
            }
        }
    }

    public static void IceKnightDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorIceHelmet.get()) && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.IceKnightDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.IceKnightDamageList.add(boss2Damage);
            }
        }
    }

    public static void SpringDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorSpringHelmet.get()) && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.SpringDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.SpringDamageList.add(boss2Damage);
            }
        }
    }

    public static void DevilDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorDevilHelmet.get()) && monster.isAlive()
                && Utils.instanceList.get(6).getMobList() != null && Utils.instanceList.get(6).getMobList().get(0) != null
                && Utils.instanceList.get(6).getMobList().get(0).equals(monster)) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.DevilDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.DevilDamageList.add(boss2Damage);
            }
        }
    }

    public static void MoonDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if ((monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonAttack.get())
                || (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonMana.get()))) && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.MoonDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.MoonDamageList.add(boss2Damage);
            }
        }
    }

    public static void TabooDevilDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorTabooDevil.get()) && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.TabooDevilDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.TabooDevilDamageList.add(boss2Damage);
            }
        }
    }

    public static void PurpleIronKnightDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorPurpleIronKnightHelmet.get()) && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.PurpleIronKnightDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.PurpleIronKnightDamageList.add(boss2Damage);
            }
        }
    }

    public static void GiantDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorGiant.get()) && monster.isAlive()) {
            AtomicBoolean flag = new AtomicBoolean(false);
            double finalExDamageIgnoreDefence = ExDamageIgnoreDefence;
            Utils.GiantDamageList.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() == player) {
                    double damage = boss2Damage.getDamage();
                    boss2Damage.setDamage(damage + Damage + finalExDamageIgnoreDefence);
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                Boss2Damage boss2Damage = new Boss2Damage(player, Damage + ExDamageIgnoreDefence);
                Utils.GiantDamageList.add(boss2Damage);
            }
        }
    }

    public static void SpringAttackArmor(Player player, Mob monster) {
        if (!Utils.PlayerSpringAttackCoolDown.containsKey(player)
                || Utils.PlayerSpringAttackCoolDown.get(player) < player.getServer().getTickCount()) {
            if (SuitCount.getSpringAttackSuitCount(player) > 0) {
                Compute.summonFireWork(player, monster);
                Compute.addDefenceDecreaseEffectParticle(monster, 60);
                Compute.coolDownTimeSend(player, ModItems.FireCracker.get().getDefaultInstance(), 100);
                Utils.MobSpringAttackTick.put(monster, monster.getServer().getTickCount() + 60);
                Utils.MobSpringAttackEffect.put(monster, SuitCount.getSpringAttackSuitCount(player));
                Compute.addSlowDownEffect(monster, 60, 99);
                Utils.PlayerSpringAttackCoolDown.put(player, player.getServer().getTickCount() + 60);
            }
        }
    }

    public static void SpringSwiftArmor(Player player, Mob monster) {
        if (!Utils.PlayerSpringSwiftCoolDown.containsKey(player)
                || Utils.PlayerSpringSwiftCoolDown.get(player) < player.getServer().getTickCount()) {
            if (SuitCount.getSpringSwiftSuitCount(player) > 0) {
                Compute.summonFireWork(player, monster);
                Compute.addDefenceDecreaseEffectParticle(monster, 60);
                Compute.coolDownTimeSend(player, ModItems.FireCracker.get().getDefaultInstance(), 100);
                Utils.MobSpringSwiftTick.put(monster, monster.getServer().getTickCount() + 60);
                Utils.MobSpringSwiftEffect.put(monster, SuitCount.getSpringSwiftSuitCount(player));
                Compute.addSlowDownEffect(monster, 60, 99);
                Utils.PlayerSpringSwiftCoolDown.put(player, player.getServer().getTickCount() + 60);

            }
        }
    }

    public static void SpringManaArmor(Player player, Mob monster) {
        if (!Utils.PlayerSpringManaCoolDown.containsKey(player)
                || Utils.PlayerSpringManaCoolDown.get(player) < player.getServer().getTickCount()) {
            if (SuitCount.getSpringManaSuitCount(player) > 0) {
                Compute.summonFireWork(player, monster);
                Compute.addManaDefenceDecreaseEffectParticle(monster, 60);
                Compute.coolDownTimeSend(player, ModItems.FireCracker.get().getDefaultInstance(), 100);
                Utils.MobSpringManaTick.put(monster, monster.getServer().getTickCount() + 60);
                Utils.MobSpringManaEffect.put(monster, SuitCount.getSpringManaSuitCount(player));
                Compute.addSlowDownEffect(monster, 60, 99);
                Utils.PlayerSpringManaCoolDown.put(player, player.getServer().getTickCount() + 60);
            }
        }
    }

    public static void DamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        IceKnightDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        Boss2DamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        SpringDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        GiantDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        DevilDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        MoonDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        TabooDevilDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
        PurpleIronKnightDamageCount(player, monster, ExDamageIgnoreDefence, Damage);
    }
}