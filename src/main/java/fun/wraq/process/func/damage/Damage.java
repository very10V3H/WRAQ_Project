package fun.wraq.process.func.damage;

import com.mojang.logging.LogUtils;
import fun.wraq.commands.stable.players.DebugCommand;
import fun.wraq.commands.stable.players.DpsCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.damage.OnCauseFinalDamageCurios;
import fun.wraq.common.impl.onhit.OnPowerCauseDamageEquip;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.mana.ManaCurios1;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobDeadModule;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.instances.element.IceInstance;
import fun.wraq.events.mob.instance.instances.element.MoonInstance;
import fun.wraq.events.modules.HurtEventModule;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceEvent;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.chapter2.HuskNewRune;
import fun.wraq.series.newrunes.chapter3.NetherNewRune;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.sakuraSeries.BloodMana.BloodManaCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

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
                causeManaDamageToMonster_RateApDamage(player, mob, rate, false);
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

    public static double causeTrueDamageToMonster(Player player, Mob monster, double damage) {
        Compute.summonValueItemEntity(monster.level(), player, monster,
                Component.literal(String.format("%.0f", damage)).withStyle(CustomStyle.styleOfSea), 2);
        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeAttackDamageToMonster_RateAdDamage(Player player, Mob monster, double num) {
        double baseDamage = PlayerAttributes.attackDamage(player);
        double damageEnhance = 0;

        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, monster);

        baseDamage *= (1 + damageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        baseDamage *= defenceDamageDecreaseRate(MobAttributes.defence(monster),
                PlayerAttributes.defencePenetration(player),
                PlayerAttributes.defencePenetration0(player));

        Compute.summonValueItemEntity(monster.level(), player, monster,
                Component.literal(String.format("%.0f", baseDamage * num)).withStyle(ChatFormatting.YELLOW), 0);

        DirectDamageToMob(player, monster, baseDamage * num);
        return baseDamage * num;
    }

    public static void causeRateAdDamageToMonsterWithCritJudge(Player player, Mob mob, double num) {
        Random random = new Random();
        if (random.nextDouble() < PlayerAttributes.critRate(player)) {
            Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, num * (1 + PlayerAttributes.critDamage(player)));
        } else Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, num);
    }

    public static void causeAdDamageToMonsterWithCritJudge(Player player, Mob mob, double damage) {
        Random random = new Random();
        if (random.nextDouble() < PlayerAttributes.critRate(player)) {
            Damage.causeAttackDamageToMonster_AdDamage(player, mob, damage * (1 + PlayerAttributes.critDamage(player)));
        } else Damage.causeAttackDamageToMonster_AdDamage(player, mob, damage);
    }

    public static double causeAttackDamageToMonster_AdDamage(Player player, Mob monster, double damage) {
        double damageEnhance = 0;

        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player, monster);

        damage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        damage *= (1 + damageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        damage *= defenceDamageDecreaseRate(MobAttributes.defence(monster),
                PlayerAttributes.defencePenetration(player),
                PlayerAttributes.defencePenetration0(player));

        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);

        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeAttackDamageToMonster_AdDamage_Direct(Player player, Mob monster, double damage, boolean computeDefenceOrEnhance) {
        if (computeDefenceOrEnhance) {
            damage *= defenceDamageDecreaseRate(MobAttributes.defence(monster), PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
            damage *= (1 + DamageInfluence.getPlayerAttackDamageEnhance(player, monster));
        }
        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeAttackDamageToMonsterOnlyComputeDefence(Player player, Mob mob, double damage) {
        damage *= defenceDamageDecreaseRate(MobAttributes.defence(mob), PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
        Compute.summonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        DirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static double causeManaDamageToMonster_ApDamage_Direct(Player player, Mob monster, double damage, boolean computeDefenceOrEnhance) {
        if (computeDefenceOrEnhance) {
            damage *= defenceDamageDecreaseRate(MobAttributes.manaDefence(monster), PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player));
            damage *= (1 + DamageInfluence.getPlayerManaDamageEnhance(player));
        }
        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeAttackDamageToPlayer_RateAdDamage(Player player, Player hurter, double num) {
        double attackDamage = PlayerAttributes.attackDamage(player);
        double damageEnhance = 0;

        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player);
        damageEnhance += DamageInfluence.getPlayerAttackDamageEnhance(player);

        attackDamage *= defenceDamageDecreaseRate(PlayerAttributes.defence(hurter),
                PlayerAttributes.defencePenetration(player),
                PlayerAttributes.defencePenetration0(player));

        attackDamage *= (1 + damageEnhance) + (1 + DamageInfluence.getPlayerFinalDamageEnhance(player));

        DirectDamageToPlayer(player, hurter, attackDamage * num * 0.1f);
        return attackDamage * num * (1 + damageEnhance);
    }

    public static void causeManaDamageToMonster_RateApDamage(Player player, Mob monster, double num, boolean isPower) {
        double Defence = MobAttributes.manaDefence(monster);
        double BaseDamage = PlayerAttributes.manaDamage(player) * num;
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;

        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + BaseDamage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }
        BaseDamage *= Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        ExDamage *= Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        double totalDamage = (BaseDamage + ExDamage) * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));

        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
        if (isPower) {
            Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
            if (playerUnit.value() > 0) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, totalDamage);
                Element.entityElementUnit.put(player, new Element.Unit(Element.life, 0));
            }
        }

        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        if (isPower) Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);

        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExTrueDamage(player, monster, totalDamage);
        if (isPower) {
            Compute.AdditionEffects(player, monster, totalDamage, 1);
            OnPowerCauseDamageEquip.causeDamage(player, monster);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }

    }

    public static void causeManaDamageToMonster_RateApDamage_ElementAddition(Player player, Mob monster, double num,
                                                                             boolean isPower, String elementType, double elementValue) {
        double defence = MobAttributes.manaDefence(monster);
        double baseDamage = PlayerAttributes.manaDamage(player) * num;
        double defencePenetration = PlayerAttributes.manaPenetration(player);
        double defencePenetration0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;

        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + baseDamage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }
        baseDamage *= Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
        ExDamage *= Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
        double totalDamage = (baseDamage + ExDamage) * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));

        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
        if (isPower) {
            ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, elementType, elementValue, false, totalDamage);
            Element.entityElementUnit.put(player, new Element.Unit(Element.life, 0));
        }

        double elementDamage = totalDamage * ((1 + ElementDamageEnhance) * ElementDamageEffect - 1);

        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;

        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);

        if (isPower) {
            if (elementDamage != 0 && !elementType.isEmpty() && elementValue != 0) {
                Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false, elementType, elementDamage);
            }
            else {
                Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);
            }
        }

        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExTrueDamage(player, monster, totalDamage);
        if (isPower) {
            Compute.AdditionEffects(player, monster, totalDamage, 1);
            OnPowerCauseDamageEquip.causeDamage(player, monster);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Damage.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }

    }

    public static void causeManaDamageToMonster_ApDamage(Player player, Mob monster, double damage) {
        double Defence = MobAttributes.manaDefence(monster);
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;

        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

        damage += ExDamage;

        damage *= Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

        double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExTrueDamage(player, monster, totalDamage);
    }

    public static void causeManaDamageToMonster_ApDamage(Player player, Mob monster, double damage, boolean isPower) {
        double Defence = MobAttributes.manaDefence(monster);
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;

        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        DamageEnhance += IceInstance.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + damage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }

        damage += ExDamage;
        damage *= Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

        double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        // 元素
        double ElementDamageEnhance = 0;
        double ElementDamageEffect = 1;
        ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
        if (isPower) {
            Element.Unit playerUnit = Element.entityElementUnit.getOrDefault(player, new Element.Unit(Element.life, 0));
            if (playerUnit.value() > 0) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, playerUnit.type(), playerUnit.value(), false, totalDamage);
                Element.entityElementUnit.put(player, new Element.Unit(Element.life, 0));
            }
        }

        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
        Compute.summonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExTrueDamage(player, monster, totalDamage);
        if (isPower) {
            Compute.AdditionEffects(player, monster, totalDamage, 1);
            OnPowerCauseDamageEquip.causeDamage(player, monster);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Damage.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
            player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
            player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
            player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
            player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
        }
    }

    public static void manaDamageToPlayer(Player player, Player hurter, double num) {

        double BaseDamage = PlayerAttributes.manaDamage(player);
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double Defence = PlayerAttributes.manaDefence(hurter);
        double DamageEnhance = 0;

        DamageEnhance -= (1 - defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0));

        DirectDamageToPlayer(player, hurter, BaseDamage * num * 0.1f * (1 + DamageEnhance));
    }

    public static void manaDamageToPlayer(Mob monster, Player player, double damage) {
        double manaDefence = PlayerAttributes.manaDefence(player);
        damage *= defenceDamageDecreaseRate(manaDefence, 0, 0);
        MonsterAttackEvent.monsterAttack(monster, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void manaDamageToPlayer(Mob monster, Player player, double damage, double penetration, double penetration0) {
        double manaDefence = PlayerAttributes.manaDefence(player);
        damage *= defenceDamageDecreaseRate(manaDefence, penetration, penetration0);
        MonsterAttackEvent.monsterAttack(monster, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void manaDamageToPlayer_RateApDamage(Mob mob, Player player, double rate) {
        double damage = MobAttributes.attackDamage(mob) * rate;
        double manaDefence = PlayerAttributes.manaDefence(player);
        double manaPenetration = PlayerAttributes.manaPenetration(player);
        double manaPenetration0 = PlayerAttributes.manaPenetration0(player);
        damage *= defenceDamageDecreaseRate(manaDefence, manaPenetration, manaPenetration0);
        MonsterAttackEvent.monsterAttack(mob, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void causeTrueDamageToPlayer(Mob mob, Player player, double Damage) {
        MonsterAttackEvent.monsterAttack(mob, player, Damage);
    }

    public static void DirectDamageToPlayer(Mob mob, Player player, double damage) {
        if (damage >= player.getHealth()) {
            Compute.playerDeadModule(player);
            Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Te.m("在与")).
                            append(mob.getDisplayName()).
                            append(Component.literal("的战斗中不幸重伤。").withStyle(ChatFormatting.WHITE)));
            NewTeamInstanceEvent.getOverworldInstances().forEach(newTeamInstance -> {
                if (!newTeamInstance.players.isEmpty()) {
                    if (newTeamInstance.players.contains(player)) newTeamInstance.deadTimes++;
                }
            });
        }
        player.setHealth((float) (player.getHealth() - damage));
    }

    public static void AttackDamageToPlayer(Mob monster, Player player, double Damage) {
        double Defence = PlayerAttributes.defence(player);
        Damage *= defenceDamageDecreaseRate(Defence, MobAttributes.defencePenetration(monster), MobAttributes.defencePenetration0(monster));
        MonsterAttackEvent.monsterAttack(monster, player, Damage);
    }

    public static void AttackDamageToPlayer(Mob monster, Player player, double Damage, double defencePenetration, double defencePenetration0) {
        double Defence = PlayerAttributes.defence(player);
        Damage *= defenceDamageDecreaseRate(Defence, defencePenetration, defencePenetration0);
        MonsterAttackEvent.monsterAttack(monster, player, Damage);
    }

    public static void AttackDamageToPlayer_NumDamage(Mob monster, Player player, double Damage, double BreakDefence, double BreakDefence0) {
        double Defence = PlayerAttributes.defence(player);
        Damage *= defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        MonsterAttackEvent.monsterAttack(monster, player, Damage);
    }

    public static void DirectDamageToMob(Player player, Entity entity, double damage) {
        if (entity instanceof Mob mob && !(entity instanceof Allay)
                && !(entity instanceof Animal && !entity.hasCustomName())) {
            if (entity instanceof Villager) return;
            if (mob.isDeadOrDying()) return;
            if (DailyEndlessInstance.prohibitPlayerCauseDamage(player, mob)) return;
            /*Castle.CauseDamageRecord(player, mob); */
            if (MoonInstance.isMoonAttackImmune(player, (Mob) entity)) damage *= 0.5;
            if (MoonInstance.isMoonManaImmune(player, (Mob) entity)) damage *= 0.5;
            /*AttackEvent.DamageCount(player, (Mob) entity, 0, damage);*/
            if (player.isCreative())
                player.sendSystemMessage(Component.literal("" + mob.getHealth() / mob.getMaxHealth()));
            // ---- // 测试新模式
            entity.hurt(entity.damageSources().playerAttack(player), 0);
            MySound.soundOnMob(mob, SoundEvents.PLAYER_HURT);
            double finalDamage = mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.WoodenStake5.get()) ? 0 : (float) damage;
            if (mob.getHealth() <= finalDamage && mob.isAlive()) {
                // 怪物死亡技艺
                MobDeadModule.deadModule(mob);
                LogUtils.getLogger().info("{} {} {}", player.getName().getString(), Utils.LogTypes.killed, mob.getName().getString());

                mob.kill();
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

            } else mob.setHealth((float) (mob.getHealth() - finalDamage));

            // ---- //
            FireEquip.IgniteEffect(player, mob);
            DpsCommand.CalculateDamage(player, finalDamage);
            entity.invulnerableTime = 0;
            StarBottle.playerBattleTickMapRefresh(player);
            Element.ElementParticleProvider(mob);
            OnCauseFinalDamageCurios.causeFinalDamage(player, mob, finalDamage);
        }
    }

    public static void DirectDamageToPlayer(Player player, Player hurter, double Damage) {
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

    public static double defenceDamageDecreaseRate(double defence, double defencePenetration, double defencePenetration0) {
        double defenceAfterCompute = Math.max(0, defence * (1 - defencePenetration) - defencePenetration0);
        return (100 / (100 + defenceAfterCompute));
    }
}
