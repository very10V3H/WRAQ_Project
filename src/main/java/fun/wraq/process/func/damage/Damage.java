package fun.wraq.process.func.damage;

import fun.wraq.commands.stable.players.DebugCommand;
import fun.wraq.commands.stable.players.DpsCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.BeforeCauseFinalDamageCurios;
import fun.wraq.common.impl.damage.OnCauseFinalDamageCurios;
import fun.wraq.common.impl.damage.OnCauseFinalDamageEquip;
import fun.wraq.common.impl.onhit.OnPowerCauseDamageEquip;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.mana.normal.ManaCurios1;
import fun.wraq.customized.uniform.mana.normal.ManaCurios4;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobDeadModule;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.events.mob.instance.instances.element.IceInstance;
import fun.wraq.events.mob.instance.instances.element.MoonInstance;
import fun.wraq.events.mob.instance.instances.element.WardenInstance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss3Instance;
import fun.wraq.events.mob.instance.instances.tower.ManaTowerEachFloorMob;
import fun.wraq.events.mob.jungle.JungleMobSpawn;
import fun.wraq.events.modules.HurtEventModule;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.randomevent.RandomEventsHandler;
import fun.wraq.process.system.randomevent.impl.killmob.SlimeKingEvent;
import fun.wraq.process.system.randomevent.impl.special.SpringMobEvent;
import fun.wraq.process.system.skill.skillv2.mana.ManaNewSkillPassive0;
import fun.wraq.process.system.skill.skillv2.sword.SwordNewSkillBase3_0;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.citadel.CitadelCurio;
import fun.wraq.series.events.summer2025.Summer2025;
import fun.wraq.series.gems.passive.impl.GemOnCauseDamage;
import fun.wraq.series.gems.passive.impl.GemOnKillMob;
import fun.wraq.series.holy.ice.FrostInstance;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerMainHand;
import fun.wraq.series.newrunes.chapter2.HuskNewRune;
import fun.wraq.series.newrunes.chapter3.NetherNewRune;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.cold.sc4.SuperColdIronGolemSpawnController;
import fun.wraq.series.overworld.cold.sc5.dragon.IceDragonSpawnController;
import fun.wraq.series.overworld.cold.sc5.dragon.SimulateIceDragonSpawnController;
import fun.wraq.series.overworld.divine.DivineUtils;
import fun.wraq.series.overworld.divine.mob.boss.DivineBunnyInstance;
import fun.wraq.series.overworld.divine.mob.common.DivineGolemSpawnController;
import fun.wraq.series.overworld.divine.mob.common.DivineSentrySpawnController;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.sakura.BloodMana.BloodManaCurios;
import fun.wraq.series.overworld.wind.mob.WindBossInstance;
import fun.wraq.series.overworld.wind.mob.WindJungleMob0SpawnController;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Damage {

    /**
     * 自适应伤害，根据手持武器类型判定。物理类自带暴击几率与暴击伤害修正
     * @param player 玩家
     * @param mob 目标怪物
     * @param rate 倍率
     * @param trueDamage 真实伤害
     */
    public static void causeAutoAdaptionRateDamageToMob(Player player, Mob mob, double rate, boolean trueDamage) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        ItemStack itemStack = player.getMainHandItem();
        Item item = itemStack.getItem();
        if (Utils.swordTag.containsKey(item) || Utils.bowTag.containsKey(item)) {
            double damageRate = rate;
            if (RandomUtils.nextDouble(0, 1) < PlayerAttributes.critRate(player)) {
                damageRate *= (1 + PlayerAttributes.critDamage(player));
            }
            if (trueDamage) {
                causeTrueDamageToMonster(player, mob, PlayerAttributes.attackDamage(player) * damageRate);
            } else {
                causeAttackDamageToMonster_RateAdDamage(player, mob, damageRate);
            }
        }
        if (Utils.sceptreTag.containsKey(item)) {
            if (trueDamage) {
                causeTrueDamageToMonster(player, mob, PlayerAttributes.manaDamage(player));
            } else {
                causeRateApDamageToMonster(player, mob, rate, false);
            }
        }
    }

    public static double getAutoAdaptionDamageValue(Player player, double rate) {
        ItemStack itemStack = player.getMainHandItem();
        Item item = itemStack.getItem();
        if (Utils.swordTag.containsKey(item) || Utils.bowTag.containsKey(item)) {
            double damageRate = rate;
            if (RandomUtils.nextDouble(0, 1) < PlayerAttributes.critRate(player)) {
                damageRate *= (1 + PlayerAttributes.critDamage(player));
            }
            return PlayerAttributes.attackDamage(player) * damageRate;
        }
        return PlayerAttributes.manaDamage(player) * rate;
    }

    public static double causeTrueDamageToMonster(Player player, Mob mob, double damage) {
        if (!Compute.isWraqMob(mob)) {
            return 0;
        }
        if (MobSpawn.getMobOriginName(mob).equals(IceDragonSpawnController.MOB_NAME)
                || MobSpawn.getMobOriginName(mob).equals(SimulateIceDragonSpawnController.MOB_NAME)) {
            return damage;
        }
        Compute.summonValueItemEntity(mob.level(), player, mob,
                Component.literal(String.format("%.0f", damage)).withStyle(CustomStyle.styleOfSea), 2);
        beforeCauseDamage(player, mob, damage);
        causeDirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static double causeAttackDamageToMonster_RateAdDamage(Player player, Mob mob, double num) {
        if (!Compute.isWraqMob(mob)) {
            return 0;
        }
        double baseDamage = PlayerAttributes.attackDamage(player);
        double damageEnhance = 0;

        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, mob);

        baseDamage *= (1 + damageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        baseDamage *= defenceDamageDecreaseRate(player, mob, MobAttributes.defence(mob),
                PlayerAttributes.defencePenetration(player),
                PlayerAttributes.defencePenetration0(player));

        baseDamage *= (1 + DamageInfluence.getAdjustAttackDamageRate(player, mob));

        Compute.summonValueItemEntity(mob.level(), player, mob,
                Component.literal(String.format("%.0f", baseDamage * num)).withStyle(ChatFormatting.YELLOW), 0);
        beforeCauseDamage(player, mob, baseDamage * num);
        causeDirectDamageToMob(player, mob, baseDamage * num);
        return baseDamage * num;
    }

    public static void causeRateAdDamageToMonsterWithCritJudge(Player player, Mob mob, double num) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        Random random = new Random();
        if (random.nextDouble() < PlayerAttributes.critRate(player)) {
            Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, num * (1 + PlayerAttributes.critDamage(player)));
        } else Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, num);
    }

    public static void causeAdDamageToMonsterWithCritJudge(Player player, Mob mob, double damage) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        Random random = new Random();
        if (random.nextDouble() < PlayerAttributes.critRate(player)) {
            Damage.causeAttackDamageToMonster(player, mob, damage * (1 + PlayerAttributes.critDamage(player)));
        } else Damage.causeAttackDamageToMonster(player, mob, damage);
    }

    public static double causeAttackDamageToMonster(Player player, Mob mob, double damage) {
        if (!Compute.isWraqMob(mob)) {
            return 0;
        }
        double damageEnhance = 0;
        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, mob);
        damage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        damage *= (1 + damageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        damage *= defenceDamageDecreaseRate(player, mob, MobAttributes.defence(mob),
                PlayerAttributes.defencePenetration(player),
                PlayerAttributes.defencePenetration0(player));
        damage *= (1 + DamageInfluence.getAdjustAttackDamageRate(player, mob));
        Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        beforeCauseDamage(player, mob, damage);
        causeDirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static double causeAttackDamageToMonster_AdDamage_Direct(Player player, Mob mob, double damage, boolean computeDefenceOrEnhance) {
        if (!Compute.isWraqMob(mob)) {
            return 0;
        }
        if (computeDefenceOrEnhance) {
            damage *= defenceDamageDecreaseRate(player, mob, MobAttributes.defence(mob),
                    PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
            damage *= (1 + DamageInfluence.getPlayerAttackDamageEnhance(player, mob));
        }

        Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        beforeCauseDamage(player, mob, damage);
        causeDirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static double causeAttackDamageToMonsterOnlyComputeDefence(Player player, Mob mob, double damage) {
        if (!Compute.isWraqMob(mob)) {
            return 0;
        }
        damage *= defenceDamageDecreaseRate(player, mob, MobAttributes.defence(mob),
                PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
        Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        beforeCauseDamage(player, mob, damage);
        causeDirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static double causeManaDamageToMonster_ApDamage_Direct(Player player, Mob mob, double damage, boolean computeDefenceOrEnhance) {
        if (!Compute.isWraqMob(mob)) {
            return 0;
        }
        if (computeDefenceOrEnhance) {
            damage *= defenceDamageDecreaseRate(player, mob, MobAttributes.manaDefence(mob),
                    PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player));
            damage *= (1 + DamageInfluence.getPlayerManaDamageEnhance(player));
        }
        Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        Damage.beforeCauseDamage(player, mob, damage);
        causeDirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static void causeRateApDamageToMonster(Player player, Mob mob, double num, boolean isPower) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        double baseDamage = PlayerAttributes.manaDamage(player) * num;
        double damageEnhance = 0;
        double exDamage = 0;
        double defenceDamageDecreaseRate =
                Damage.defenceDamageDecreaseRate(player, mob, MobAttributes.manaDefence(mob),
                        PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player));
        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        damageEnhance += IceInstance.IceKnightHealthManaDamageFix(mob); // 冰霜骑士伤害修正
        damageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + baseDamage));
            player.sendSystemMessage(Component.literal("ExDamage : " + exDamage));
        }
        baseDamage *= defenceDamageDecreaseRate;
        exDamage *= defenceDamageDecreaseRate;
        double totalDamage = (baseDamage + exDamage) * (1 + damageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        // 元素
        double elementDamageEnhance = 0;
        double elementDamageEffect = 1;
        if (isPower) {
            Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
            if (playerUnit.value() > 0) {
                elementDamageEffect = Element.ElementEffectAddToEntity(player, mob, playerUnit.type(), playerUnit.value(), false, totalDamage);
            }
        }
        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        totalDamage *= (1 + elementDamageEnhance) * elementDamageEffect;
        totalDamage *= (1 + DamageInfluence.getAdjustManaDamageRate(player, mob));
        Compute.summonValueItemEntity(mob.level(), player, mob,
                Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        if (isPower) {
            Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);
        }
        beforeCauseDamage(player, mob, totalDamage);
        causeDirectDamageToMob(player, mob, totalDamage);
        Compute.manaDamageExEffect(player, mob, totalDamage);
        ManaCurios1.getManaDamageExTrueDamage(player, mob, totalDamage);
        if (isPower) {
            Compute.additionEffects(player, mob, totalDamage, 1);
            OnPowerCauseDamageEquip.causeDamage(player, mob);
            ManaNewSkillPassive0.onManaPowerHit(player, mob);
        }
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + damageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,mob) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, mob)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + defenceDamageDecreaseRate));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + elementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + elementDamageEnhance));
            player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }
    }

    public static void causeRateApDamageWithElement(Player player, Mob mob, double num,
                                                    boolean isPower, String elementType, double elementValue) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        double defence = MobAttributes.manaDefence(mob);
        double baseDamage = PlayerAttributes.manaDamage(player) * num;
        double defencePenetration = PlayerAttributes.manaPenetration(player);
        double defencePenetration0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;
        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(mob); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + baseDamage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }
        baseDamage *= Damage.defenceDamageDecreaseRate(player, mob,
                defence, defencePenetration, defencePenetration0);
        ExDamage *= Damage.defenceDamageDecreaseRate(player, mob,
                defence, defencePenetration, defencePenetration0);
        double totalDamage = (baseDamage + ExDamage) * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        if (isPower) {
            ElementDamageEffect = Element.ElementEffectAddToEntity(player, mob, elementType, elementValue, false, totalDamage);
        }
        double elementDamage = totalDamage * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);
        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
        totalDamage *= (1 + DamageInfluence.getAdjustManaDamageRate(player, mob));
        Compute.summonValueItemEntity(mob.level(), player, mob,
                Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        if (isPower) {
            if (elementDamage != 0 && !elementType.isEmpty() && elementValue != 0) {
                Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false, elementType, elementDamage);
            }
            else {
                Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);
            }
        }
        beforeCauseDamage(player, mob, totalDamage);
        causeDirectDamageToMob(player, mob, totalDamage);
        Compute.healByHealthSteal(player, mob, totalDamage * PlayerAttributes.manaHealthSteal(player) * 0.33);
        Compute.manaDamageExEffect(player, mob, totalDamage);
        ManaCurios1.getManaDamageExTrueDamage(player, mob, totalDamage);
        if (isPower) {
            Compute.additionEffects(player, mob, totalDamage, 1);
            OnPowerCauseDamageEquip.causeDamage(player, mob);
            ManaNewSkillPassive0.onManaPowerHit(player, mob);
            CitadelCurio.onNormalAttackOrSkillHit(player, mob, totalDamage, false);
        }
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,mob) : "
                    + DamageInfluence.getPlayerFinalDamageEnhance(player, mob)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : "
                    + Damage.defenceDamageDecreaseRate(player, mob, defence, defencePenetration, defencePenetration0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }
    }

    public static void causeRateApDamageWithElement(Player player, Mob mob, double num, boolean isPower) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        Element.Unit unit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
        causeRateApDamageWithElement(player, mob, num, isPower, unit.type(), unit.value());
    }

    public static void causeManaDamageToMonster(Player player, Mob mob, double damage) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        double Defence = MobAttributes.manaDefence(mob);
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;
        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(mob); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        damage += ExDamage;
        damage *= Damage.defenceDamageDecreaseRate(player, mob, Defence, BreakDefence, BreakDefence0);
        double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        totalDamage *= (1 + DamageInfluence.getAdjustManaDamageRate(player, mob));
        Compute.summonValueItemEntity(mob.level(), player, mob,
                Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        beforeCauseDamage(player, mob, totalDamage);
        causeDirectDamageToMob(player, mob, totalDamage);
        Compute.manaDamageExEffect(player, mob, totalDamage);
        ManaCurios1.getManaDamageExTrueDamage(player, mob, totalDamage);
    }

    public static void causeManaDamageToMonster(Player player, Mob mob, double damage, boolean isPower) {
        if (!Compute.isWraqMob(mob)) {
            return;
        }
        double Defence = MobAttributes.manaDefence(mob);
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;

        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, mob);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(mob); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + damage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }

        damage += ExDamage;
        damage *= Damage.defenceDamageDecreaseRate(player, mob, Defence, BreakDefence, BreakDefence0);

        double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, mob));
        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        if (isPower) {
            Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
            if (playerUnit.value() > 0) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player, mob, playerUnit.type(), playerUnit.value(), false, totalDamage);
            }
        }

        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, mob);
        totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
        totalDamage *= (1 + DamageInfluence.getAdjustManaDamageRate(player, mob));

        Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        beforeCauseDamage(player, mob, totalDamage);
        causeDirectDamageToMob(player, mob, totalDamage);
        Compute.manaDamageExEffect(player, mob, totalDamage);
        ManaCurios1.getManaDamageExTrueDamage(player, mob, totalDamage);
        if (isPower) {
            Compute.additionEffects(player, mob, totalDamage, 1);
            OnPowerCauseDamageEquip.causeDamage(player, mob);
            ManaNewSkillPassive0.onManaPowerHit(player, mob);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,mob) : "
                    + DamageInfluence.getPlayerFinalDamageEnhance(player, mob)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : "
                    + Damage.defenceDamageDecreaseRate(player, mob, Defence, BreakDefence, BreakDefence0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }
    }

    public static void causeManaDamageToPlayer(Mob mob, Player player, double damage) {
        double manaDefence = PlayerAttributes.manaDefence(player);
        damage *= defenceDamageDecreaseRate(manaDefence,
                MobSpawn.MobBaseAttributes.defencePenetration.get(MobSpawn.getMobOriginName(mob)),
                MobSpawn.MobBaseAttributes.defencePenetration0.get(MobSpawn.getMobOriginName(mob)));
        MonsterAttackEvent.mobAttack(mob, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void causeManaDamageToPlayer(Mob mob, Player player, double damage, double penetration, double penetration0) {
        double manaDefence = PlayerAttributes.manaDefence(player);
        damage *= defenceDamageDecreaseRate(manaDefence, penetration, penetration0);
        MonsterAttackEvent.mobAttack(mob, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void manaDamageToPlayer_RateApDamage(Mob mob, Player player, double rate) {
        double damage = MobAttributes.attackDamage(mob) * rate;
        double manaDefence = PlayerAttributes.manaDefence(player);
        double manaPenetration = PlayerAttributes.manaPenetration(player);
        double manaPenetration0 = PlayerAttributes.manaPenetration0(player);
        damage *= defenceDamageDecreaseRate(manaDefence, manaPenetration, manaPenetration0);
        MonsterAttackEvent.mobAttack(mob, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void causeTrueDamageToPlayer(Mob mob, Player player, double Damage) {
        MonsterAttackEvent.mobAttack(mob, player, Damage);
    }

    public static void causeDirectDamageToPlayer(Mob mob, Player player, double damage) {
        if (MobSpawn.getMobOriginName(mob).equals(FrostInstance.mobName)
                || MobSpawn.getMobOriginName(mob).equals(SuperColdIronGolemSpawnController.mobName)) {
            damage = Math.min(300000, damage);
        }
        if (damage >= player.getHealth()) {
            Compute.playerDeadModule(player);
            Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Te.m("在与")).
                            append(mob.getDisplayName()).
                            append(Component.literal("的战斗中不幸重伤。").withStyle(ChatFormatting.WHITE)));
            NewTeamInstanceHandler.getInstances().forEach(newTeamInstance -> {
                if (!newTeamInstance.players.isEmpty()) {
                    if (newTeamInstance.players.contains(player)) newTeamInstance.deadTimes++;
                }
            });
        }
        player.setHealth((float) (player.getHealth() - damage));
    }

    public static void causeAttackDamageToPlayer(Mob mob, Player player, double Damage) {
        double Defence = PlayerAttributes.defence(player);
        Damage *= defenceDamageDecreaseRate(Defence, MobAttributes.defencePenetration(mob), MobAttributes.defencePenetration0(mob));
        MonsterAttackEvent.mobAttack(mob, player, Damage);
    }

    public static void causeAttackDamageToPlayer(Mob mob, Player player, double Damage, double defencePenetration, double defencePenetration0) {
        double Defence = PlayerAttributes.defence(player);
        Damage *= defenceDamageDecreaseRate(Defence, defencePenetration, defencePenetration0);
        MonsterAttackEvent.mobAttack(mob, player, Damage);
    }

    public static void causeDirectDamageToMob(Player player, Entity entity, double damage) {
        if (entity instanceof Mob mob && !(entity instanceof Allay)
                && !(entity instanceof Animal && !entity.hasCustomName())) {
            if (!Compute.isWraqMob(mob)) {
                return;
            }
            if (entity instanceof Villager) return;
            if (mob.isDeadOrDying()) return;
            if (DailyEndlessInstance.prohibitPlayerCauseDamage(player, mob)) return;
            if (SlimeKingEvent.isSlimeKing(mob)) return;
            /*Castle.CauseDamageRecord(player, livingEntity); */
            if (MoonInstance.isMoonAttackImmune(player, (Mob) entity)) damage *= 0.5;
            if (MoonInstance.isMoonManaImmune(player, (Mob) entity)) damage *= 0.5;
            /*AttackEvent.DamageCount(player, (Mob) entity, 0, damage);*/
            // ---- // 测试新模式
            entity.hurt(entity.damageSources().playerAttack(player), 0);
            MySound.soundOnMob(mob, SoundEvents.PLAYER_HURT);

            OnCauseFinalDamageCurios.causeFinalDamage(player, mob, damage);
            OnCauseFinalDamageEquip.causeFinalDamage(player, mob, damage);

            damage *= WardenInstance.mobWithstandDamageRate(mob, player);
            damage = NewTeamInstanceHandler.judgeDamage(player, mob, damage);
            damage *= NoTeamInstanceModule.onMobWithstandDamageRate(player, mob);
            damage = Summer2025.getMobWithstandModifiedDamage(player, mob, damage);

            double finalDamage = damage;
            if (finalDamage == 0) {
                Compute.damageActionBarPacketSend(player, 0, 0, false, false);
            }
            finalDamage *= getAfterScornAdjustRate(player, mob);
            finalDamage *= SpringMobEvent.onMobWithStandDamage(mob);
            finalDamage *= HarbingerMainHand.onMobWithstand(mob, player);
            finalDamage *= JungleMobSpawn.modifyMobWithstandDamage(mob, player);
            finalDamage *= DivineUtils.getManifestMobDamageModifyRate(player, mob);
            if (mob.getHealth() <= finalDamage && !MoontainBoss3Instance.beforeKill(mob)) return;
            if (!(mob instanceof Civil)) {
                if (mob.getHealth() <= finalDamage && mob.isAlive()) {
                    // 怪物死亡技艺
                    MobDeadModule.deadModule(mob);
                    mob.kill();
                    mob.setHealth((float) (mob.getHealth() - finalDamage));
                    CompoundTag data = player.getPersistentData();
                    MobSpawn.drop(mob, player);
                    HurtEventModule.SwordSkill2(data, player); // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）
                    HurtEventModule.BowSkill2(data, player); // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）
                    HurtEventModule.ManaSkill2(data, player); // 魔力汲取（击杀一个单位时，提升2%法术攻击，持续10s）
                    NetherNewRune.onKill(player, mob);
                    HuskNewRune.onKill(player, mob);
                    DailyEndlessInstance.onKillMob(player, mob);
                    OnKillEffectEquip.kill(player, mob);
                    OnKillEffectCurios.kill(player, mob);
                    RandomEventsHandler.onKillMob(player, mob);
                    GemOnKillMob.kill(player, mob);
                    MobKillEntrustment.onKill(player, mob);
                    AllayPet.onKillMob(player, mob);
                    SwordNewSkillBase3_0.onKillMob(player);
                    DivineUtils.onPlayerKillMob(player, mob);
                    Summer2025.onKill(player, mob);
                } else {
                    mob.setHealth((float) (mob.getHealth() - finalDamage));
                }
            }

            // ---- //
            CitadelGuardianInstance.mobWithstandDamage(mob, damage);
            FireEquip.IgniteEffect(player, mob);
            DpsCommand.CalculateDamage(player, damage);
            SpringMobEvent.onPlayerCauseDamage(player, mob, damage);
            entity.invulnerableTime = 0;
            StarBottle.playerBattleTickMapRefresh(player);
            Element.provideElementParticle(mob);
            GemOnCauseDamage.causeDamage(player, mob, damage);
            AllayPet.playerIsAttackingMobMap.put(player.getName().getString(), mob);
            JungleMobSpawn.onMobWithstandDamage(mob, player, damage);
            ManaCurios4.onCauseDamage(player);
        }
    }

    public static Map<String, Double> scornValueMap = new HashMap<>() {{
        put(ManaTowerEachFloorMob.FLOOR_3_MOB_NAME, 0.7);
        put(DivineSentrySpawnController.mobName, 0.7);
        put(DivineGolemSpawnController.mobName, 1d);
        put(DivineBunnyInstance.mobName, 1.5);
        put(WindJungleMob0SpawnController.MOB_NAME, 1d);
        put(WindBossInstance.mobName, 1d);
    }};

    public static Map<String, Integer> nextAllowSendMSGTickMap = new HashMap<>();
    public static double getAfterScornAdjustRate(Player player, Mob mob) {
        String mobName = MobSpawn.getMobOriginName(mob);
        if (scornValueMap.containsKey(mobName) &&
                (Element.getResonanceType(player) == null || ElementValue
                        .getElementValueJudgeByType(player, Element.getResonanceType(player))
                        < scornValueMap.get(mobName))) {
            if (nextAllowSendMSGTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                nextAllowSendMSGTickMap.put(Name.get(player), Tick.get() + Tick.s(1));
                Compute.sendFormatMSG(player, Te.s("蔑视", CustomStyle.styleOfRed),
                        Te.s(mob, "十分高傲，你当前的元素强度无法对其造成任何伤害.", "(需要"
                                        + String.format("%.0f%%", scornValueMap.get(mobName) * 100) + "任意元素强度)",
                                CustomStyle.DIVINE_STYLE));
            }
            return 0;
        }
        return 1;
    }

    public static void causeDirectDamageToPlayer(Player player, Player hurter, double Damage) {
        if (player.isCreative()) {
            player.sendSystemMessage(Component.literal("" + Damage));
            // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
        } else {
            hurter.hurt(hurter.damageSources().playerAttack(player), (float) Damage);
            hurter.invulnerableTime = 0;
            // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
        }
    }

    // 对于任意怪物对玩家造成的伤害，都需经过MonsterAttackEvent.MonsterAttack进行伤害以及其他增益的计算。
    // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
    // 对于玩家对怪物造成的伤害增益，需分为不同类型的伤害值进行计算。即需前往每个类型进行修改。其中，有RateAd/ApDamage可用。

    public static double defenceDamageDecreaseRate(Player player, Mob mob, double defence,
                                                   double defencePenetration, double defencePenetration0) {
        double defenceAfterCompute = Math.max(0, defence * (1 - defencePenetration) - defencePenetration0);
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(ExtraordinaryItems.NAN_HAI_A.get())
                && mob.getHealth() / mob.getMaxHealth() < 0.4) {
            defenceAfterCompute = 0;
        }
        return (100 / (100 + defenceAfterCompute));
    }

    public static double defenceDamageDecreaseRate(double defence,
                                                   double defencePenetration, double defencePenetration0) {
        double defenceAfterCompute = Math.max(0, defence * (1 - defencePenetration) - defencePenetration0);
        return (100 / (100 + defenceAfterCompute));
    }

    public static void beforeCauseDamage(Player player, Mob mob, double value) {
        BeforeCauseFinalDamageCurios.beforeCauseFinalDamage(player, mob, value);
    }

    public static void onPlayerReleaseNormalAttack(Player player) {
        WardenInstance.onPlayerNormalAttackOrReleasePower(player);
    }
}
