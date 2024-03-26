package com.Very.very.CoreAttackModule;

import com.Very.very.Customize.Players.Crush.Crush1;
import com.Very.very.Entity.Entities.Boss2.Boss2;
import com.Very.very.Events.Instance.CastleSecondFloor;
import com.Very.very.Events.Instance.IceKnight;
import com.Very.very.Events.WaltzAndModule.AttackEventModule;
import com.Very.very.Events.WaltzAndModule.HurtEventModule;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.CritHitParticleS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ChargedClearS2CPacket;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.Series.InstanceSeries.Castle.CastleAttackArmor;
import com.Very.very.Series.InstanceSeries.Castle.CastleSword;
import com.Very.very.Series.InstanceSeries.Moon.Equip.MoonShield;
import com.Very.very.Series.InstanceSeries.Moon.Equip.MoonSword;
import com.Very.very.Series.InstanceSeries.Moon.MoonCurios;
import com.Very.very.Series.NetherSeries.Equip.ManaSword;
import com.Very.very.Series.OverWorldSeries.Castle.BlazeBracelet;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Struct.Boss2Damage;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public static void AttackToMonster(Mob monster, Player player, Item equip, CompoundTag data, double Rate) {
        Utils.PlayerFireWorkFightCoolDown.put(player,player.getServer().getTickCount() + 200);
        double Defence = Compute.MonsterDefence(monster);
        double BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player) * Rate;
        double BreakDefence = Compute.PlayerAttributes.PlayerDefencePenetration(player);
        double CriticalHitRate = Compute.PlayerAttributes.PlayerCritRate(player);
        double CHitDamage = Compute.PlayerAttributes.PlayerCritDamage(player);
        if (Utils.SnowRune2MobController.contains(monster)) Defence *= 0.5f;
        double BreakDefence0 = Compute.PlayerAttributes.PlayerDefencePenetration0(player);
        int LightningArmorCount = Compute.LightningArmorCount(player);
        if (monster instanceof Evoker && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaSword)
            BreakDefence = 1.0d;
        Random r = new Random();
        double RanNum = r.nextDouble(1.00d);
        if (Defence == 0) Defence = (monster.getAttribute(Attributes.ARMOR).getValue());

        double Damage;
        double ExDamage = 0;
        double ExDamageIgnoreDefence = 0;
        double DamageEnhance = 0;
        double DamageBeforeDefence = 0;

        // 变量定义与部分效果附加

        AttackEventModule.SnowRune2(data, monster, player, Defence);
        AttackEventModule.MineSwordAndSnowSwordSlowDownForce(equip, monster);
        AttackEventModule.LakeSwordSpeedImproveModule(equip, player, data);
        AttackEventModule.AnimalThingsGetModule(monster, player);
        AttackEventModule.SnowArmorEffect(player, monster); //冰川增幅

        ExDamage += AttackEventModule.ForestRune3(player, data); // 森林符石-生长汲取
        ExDamage += AttackEventModule.BlackForest(player, monster); // 灵魂收割者主动
        ExDamage += AttackEventModule.SwordSkill12(data, player, BaseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
        ExDamage += AttackEventModule.EndRune0DamageEnhance(player, BaseDamage); // 末地符石-终界跃迁
        ExDamage += Crush1.ZeusCurios(player,monster);
        ExDamage += AttackEventModule.CrushEffect(player,monster);
        ExDamage += AttackEventModule.SoulSwordActive(player); // 本源具象
        ExDamage += AttackEventModule.CrushExDamage(player,monster);

        ExDamageIgnoreDefence += AttackEventModule.WaltzCompute(player, monster, data); //华尔兹
        ExDamageIgnoreDefence += AttackEventModule.LightningArmor(player, LightningArmorCount, data); // 唤雷套被动
        ExDamageIgnoreDefence += AttackEventModule.SeaSword(player,  monster); //灵魂救赎者主动
        ExDamageIgnoreDefence += AttackEventModule.VolcanoRune2(player); //火山符石-熔岩强击
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill0(data, BaseDamage); //剑术热诚（获得1%额外真实伤害）
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill13(data, player, BaseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill14(data, player, BaseDamage, monster); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）
        ExDamageIgnoreDefence += AttackEventModule.CrushCuriosCountsIgnoreDefenceDamage(player,BaseDamage);
        ExDamageIgnoreDefence += MoonCurios.Passive(player,monster); // 朔望馈赠
        ExDamageIgnoreDefence += MoonShield.MoonShield(player,monster); // 尘月护盾
        ExDamageIgnoreDefence += CastleAttackArmor.ExIgnoreDefenceDamage(player);

        DamageEnhance += AttackEventModule.SwordSkill3(data, player, monster); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
        DamageEnhance += AttackEventModule.NetherArmorEffect(player, monster); // 下界套装
        DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player,monster);
        DamageEnhance += Compute.PlayerAttackDamageEnhance(player);
        DamageEnhance += IceKnight.IceKnightHealthAttackDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += AttackEventModule.CrushSwordCritDamageEnhance(player,monster);
        DamageEnhance += AttackEventModule.LengXueSword(player,monster);
        DamageEnhance += AttackEventModule.NetherShieldEffect(player,monster); // 遗骸铸盾

        double NormalAttackDamageEnhance = 0;
        NormalAttackDamageEnhance += Compute.PlayerNormalSwordAttackDamageEnhance(player); // 普通近战攻击伤害加成

        boolean CritFlag = false;
        if (RanNum < CriticalHitRate) {
            CritFlag = true;
            DamageBeforeDefence = BaseDamage * (1.0d + CHitDamage);
            data.putBoolean(StringUtils.DamageTypes.Crit, true);
            AttackEventModule.SwordSkill5Attack(data, player); // 狂暴（造成暴击后，提升1%攻击力，持续3s）
            AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
            AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            AttackEventModule.IceSword(player,monster); // 蔚境冰锥
            AttackEventModule.CrushSwordCrit(player,monster);
            ModNetworking.sendToClient(new CritHitParticleS2CPacket(monster.getX(), monster.getY(), monster.getZ()), (ServerPlayer) player);
            ModNetworking.sendToClient(new SoundsS2CPacket(0), (ServerPlayer) player);
            HurtEventModule.SabreDamage(player, monster);
            AttackEventModule.CrushCuriosCountsAdd(player,true);
            AttackEventModule.SnowShieldEffect(player,monster);
        } else {
            DamageBeforeDefence = BaseDamage;
            data.putBoolean(StringUtils.DamageTypes.Crit, false);
            AttackEventModule.SwordSkill6Attack(data, player); // 完美（持续造成暴击，将提供至多3%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）
            AttackEventModule.SwordSkill13Attack(player.getPersistentData(), player); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            ModNetworking.sendToClient(new SoundsS2CPacket(1), (ServerPlayer) player);
            HurtEventModule.SabreDamage(player, monster);
            AttackEventModule.CrushCuriosCountsAdd(player,false);
        }

        // Normal attack enhance
        DamageBeforeDefence *= (1 + DamageEnhance) * (1 + NormalAttackDamageEnhance);
        // Damage enhance
        ExDamage *= (1 + DamageEnhance);
        ExDamageIgnoreDefence *= (1 + DamageEnhance);
        // ExDamage
        DamageBeforeDefence += ExDamage;
        // 妖刀伤害影响
        DamageBeforeDefence -= Compute.SakuraDemonSword(player, DamageBeforeDefence);
        ExDamageIgnoreDefence += Compute.SakuraDemonSword(player, DamageBeforeDefence);
        // Final damage decrease
        DamageBeforeDefence *= (1 + Compute.PlayerFinalDamageEnhance(player,monster));
        ExDamageIgnoreDefence *= (1 + Compute.PlayerFinalDamageEnhance(player,monster));
        // Defence compute
        Damage = DamageBeforeDefence * Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        // Final damage cause
        Compute.Damage.DirectDamageToMob(player, monster, Damage + ExDamageIgnoreDefence);
        // Health steal
        Compute.PlayerHealSteal(player, Damage * Compute.PlayerAttributes.PlayerHealthSteal(player) * 0.5);
        // Display
        if (CritFlag) Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", Damage + ExDamageIgnoreDefence)).withStyle(Utils.styleOfPower));
        else Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", Damage + ExDamageIgnoreDefence)).withStyle(ChatFormatting.YELLOW));
        Compute.DamageActionBarPacketSend(player,Damage,ExDamageIgnoreDefence,false,CritFlag);
        // effect
        SpringAttackArmor(player,monster);
        Compute.ChargingModule(data,player);
        AttackEventModule.CrushSwordAttackLightning(player);
        CastleSword.NormalAttack(player,monster,Damage); //
        BlazeBracelet.Passive(player,monster); // 熔岩手镯
        AttackEventModule.EndRune0Judge(player,monster);
        MoonSword.Passive(player,monster); //
        MoonSword.MoonSwordActive(player,monster); // 星蚀
    }

    public static void AttackToPlayer(Player player, Player hurter, CompoundTag data, Item equip, double Rate) {
        double Defence = Compute.PlayerAttributes.PlayerDefence(hurter);
        double BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player) * Rate;
        double BreakDefence = Compute.PlayerAttributes.PlayerDefencePenetration(player);
        double CriticalHitRate = Compute.PlayerAttributes.PlayerCritRate(player);
        double CHitDamage = Compute.PlayerAttributes.PlayerCritDamage(player);
        double BreakDefence0 = Compute.PlayerAttributes.PlayerDefencePenetration0(player);
        int LightningArmorCount = Compute.LightningArmorCount(player);
        Random r = new Random();
        double RanNum = r.nextDouble(1.00d);
        if (Defence == 0) Defence = hurter.getAttribute(Attributes.ARMOR).getValue();
        double Damage;
        double ExDamage = 0;
        double ExDamageIgnoreDefence = 0;
        double DamageEnhance = 0;
        double DamageBeforeDefence = 0;

        AttackEventModule.MineSwordAndSnowSwordSlowDownForcePlayer(equip, hurter);
        ExDamage += AttackEventModule.ToPlayerBlackForest(data, hurter);
        ExDamage += AttackEventModule.ToPlayerForestRune3(data, hurter);
        ExDamage += AttackEventModule.SwordSkill12(data, player, BaseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
        ExDamageIgnoreDefence += AttackEventModule.ToPlayerWaltz(player, hurter, data);
        ExDamageIgnoreDefence += AttackEventModule.LightningArmor(player, LightningArmorCount, data);
        ExDamageIgnoreDefence += AttackEventModule.ToPlayerSeaSword(data, hurter);
        ExDamageIgnoreDefence += AttackEventModule.ToPlayerVolcanoRune2(data, hurter);
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill0(data, BaseDamage); //剑术热诚（获得1%额外真实伤害）
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill13(data, player, BaseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
        ExDamageIgnoreDefence += AttackEventModule.SwordSkill14(data, player, BaseDamage, hurter); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）

        DamageEnhance += AttackEventModule.SwordSkill3(data, player, hurter); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
        DamageEnhance += Compute.SwordSkillLevelGet(data, 4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）
        DamageEnhance += AttackEventModule.NetherArmorEffect(player, hurter); // 下界套装

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

        DamageBeforeDefence -= Compute.SakuraDemonSword(player, DamageBeforeDefence);
        ExDamageIgnoreDefence += Compute.SakuraDemonSword(player, DamageBeforeDefence);
        // 妖刀伤害影响

        Damage = DamageBeforeDefence * Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        data.putDouble(StringUtils.DamageTypes.ToPlayerDamage, (Damage + ExDamageIgnoreDefence) * 0.1f);
        Compute.Damage.DirectDamageToPlayer(player, hurter, (Damage + ExDamageIgnoreDefence) * 0.1f);
    }

    public static double AttackSpeedRate(Player player) {
        double AttackSpeed = Compute.PlayerAttributes.PlayerAttackSpeedUp(player);
        double AttackIntervalTime = 1 / (4 + AttackSpeed) * 20;
        double Rate = 0;
        int TickCount = player.getServer().getTickCount();

        if (Utils.PlayerAttackSpeedHashMap.containsKey(player)) {
            double NextFullAttackTime = Utils.PlayerAttackSpeedHashMap.get(player);
            if (TickCount >= NextFullAttackTime) Rate = 1;
            else Rate = (1 - ((NextFullAttackTime - TickCount) / AttackIntervalTime));
        } else Rate = 1;
        Utils.PlayerAttackSpeedHashMap.put(player, TickCount + AttackIntervalTime);
        return Rate;
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

    public static void CastleKnightDamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorCastleKnightHelmet.get()) && monster.isAlive()) {
            CastleSecondFloor.playerDamageCount.put(player,CastleSecondFloor.playerDamageCount.getOrDefault(player,0d) + ExDamageIgnoreDefence + Damage);
        }
    }

    public static void SpringAttackArmor(Player player, Mob monster) {
        if (!Utils.PlayerSpringAttackCoolDown.containsKey(player)
                || Utils.PlayerSpringAttackCoolDown.get(player) < player.getServer().getTickCount()) {
            if (Compute.ArmorCount.SpringAttack(player) > 0) {
                Compute.FireWorkSummon(player,monster);
                Compute.AddDefenceDescreaseEffectParticle(monster,60);
                Compute.CoolDownTimeSend(player,ModItems.FireCracker.get().getDefaultInstance(),100);
                Utils.MobSpringAttackTick.put(monster,monster.getServer().getTickCount() + 60);
                Utils.MobSpringAttackEffect.put(monster,Compute.ArmorCount.SpringAttack(player));
                Compute.AddSlowDownEffect(monster,60,99);
                Utils.PlayerSpringAttackCoolDown.put(player,player.getServer().getTickCount() + 60);
            }
        }
    }
    public static void SpringSwiftArmor(Player player, Mob monster) {
        if (!Utils.PlayerSpringSwiftCoolDown.containsKey(player)
                || Utils.PlayerSpringSwiftCoolDown.get(player) < player.getServer().getTickCount()) {
            if (Compute.ArmorCount.SpringSwift(player) > 0) {
                Compute.FireWorkSummon(player,monster);
                Compute.AddDefenceDescreaseEffectParticle(monster,60);
                Compute.CoolDownTimeSend(player,ModItems.FireCracker.get().getDefaultInstance(),100);
                Utils.MobSpringSwiftTick.put(monster,monster.getServer().getTickCount() + 60);
                Utils.MobSpringSwiftEffect.put(monster,Compute.ArmorCount.SpringSwift(player));
                Compute.AddSlowDownEffect(monster,60,99);
                Utils.PlayerSpringSwiftCoolDown.put(player,player.getServer().getTickCount() + 60);

            }
        }
    }
    public static void SpringManaArmor(Player player, Mob monster) {
        if (!Utils.PlayerSpringManaCoolDown.containsKey(player)
                || Utils.PlayerSpringManaCoolDown.get(player) < player.getServer().getTickCount()) {
            if (Compute.ArmorCount.SpringMana(player) > 0) {
                Compute.FireWorkSummon(player, monster);
                Compute.AddManaDefenceDescreaseEffectParticle(monster, 60);
                Compute.CoolDownTimeSend(player,ModItems.FireCracker.get().getDefaultInstance(),100);
                Utils.MobSpringManaTick.put(monster, monster.getServer().getTickCount() + 60);
                Utils.MobSpringManaEffect.put(monster, Compute.ArmorCount.SpringMana(player));
                Compute.AddSlowDownEffect(monster, 60, 99);
                Utils.PlayerSpringManaCoolDown.put(player, player.getServer().getTickCount() + 60);
            }
        }
    }
    public static double ZeusSword(Player player) {
        if (Utils.ZeusSword.containsKey(player) && Utils.ZeusSword.get(player)
                && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get())) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class,
                    AABB.ofSize(player.position(),15,15,15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) < 6) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(mob.position());
                    lightningBolt.setCause((ServerPlayer) player);
                    level.addFreshEntity(lightningBolt);
                    Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,10);
                }
            });
            ModNetworking.sendToClient(new ChargedClearS2CPacket(5),(ServerPlayer) player);
            Utils.ZeusSword.put(player,false);
            return Compute.PlayerAttributes.PlayerAttackDamage(player) * 4;
        }
        return 0;
    }
    public static void DamageCount(Player player, Mob monster, double ExDamageIgnoreDefence, double Damage) {
        IceKnightDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        Boss2DamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        SpringDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        GiantDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        DevilDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        MoonDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        TabooDevilDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        CastleKnightDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
        PurpleIronKnightDamageCount(player,monster,ExDamageIgnoreDefence,Damage);
    }
}