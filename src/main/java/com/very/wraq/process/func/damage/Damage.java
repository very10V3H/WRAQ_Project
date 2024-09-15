package com.very.wraq.process.func.damage;

import com.mojang.logging.LogUtils;
import com.very.wraq.commands.stable.players.DebugCommand;
import com.very.wraq.commands.stable.players.DpsCommand;
import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.DamageInfluence;
import com.very.wraq.common.attribute.MobAttributes;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.Utils;
import com.very.wraq.customized.uniform.mana.ManaCurios1;
import com.very.wraq.events.fight.MonsterAttackEvent;
import com.very.wraq.events.instance.CastleSecondFloor;
import com.very.wraq.events.instance.IceKnight;
import com.very.wraq.events.instance.Moon;
import com.very.wraq.events.mob.MobDeadModule;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.modules.HurtEventModule;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.system.endlessinstance.DailyEndlessInstance;
import com.very.wraq.process.system.teamInstance.NewTeamInstanceEvent;
import com.very.wraq.projectiles.OnKillEffectCurios;
import com.very.wraq.projectiles.OnKillEffectOffHandItem;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.moon.Equip.MoonBelt;
import com.very.wraq.series.nether.Equip.WitherBook;
import com.very.wraq.series.newrunes.chapter2.HuskNewRune;
import com.very.wraq.series.newrunes.chapter3.NetherNewRune;
import com.very.wraq.series.overworld.chapter7.star.StarBottle;
import com.very.wraq.series.overworld.sakuraSeries.BloodMana.BloodManaCurios;
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

public class Damage {

    public static void causeAutoAdaptionRateDamageToMob(Player player, Mob mob, double rate) {
        ItemStack itemStack = player.getMainHandItem();
        Item item = itemStack.getItem();
        if (Utils.swordTag.containsKey(item) || Utils.bowTag.containsKey(item))
            causeAttackDamageToMonster_RateAdDamage(player, mob, rate * 2);
        if (Utils.sceptreTag.containsKey(item))
            causeManaDamageToMonster_RateApDamage(player, mob, rate, false);
    }

    public static double causeIgNoreDefenceDamageToMonster(Player player, Mob monster, double damage) {
        double damageEnhance = 0;

        damageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        damage *= (1 + damageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));

        Compute.SummonValueItemEntity(monster.level(), player, monster,
                Component.literal(String.format("%.0f", damage)).withStyle(CustomStyle.styleOfSea), 2);

        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeIgnoreDefenceDamageToMonster_Direct(Player player, Mob monster, double damage) {
        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(CustomStyle.styleOfSea), 2);

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

        Compute.SummonValueItemEntity(monster.level(), player, monster,
                Component.literal(String.format("%.0f", baseDamage * num)).withStyle(ChatFormatting.YELLOW), 0);

        DirectDamageToMob(player, monster, baseDamage * num);
        return baseDamage * num;
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

        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);

        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeAttackDamageToMonster_AdDamage_Direct(Player player, Mob monster, double damage, boolean computeDefenceOrEnhance) {
        if (computeDefenceOrEnhance) {
            damage *= defenceDamageDecreaseRate(MobAttributes.defence(monster), PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
            damage *= (1 + DamageInfluence.getPlayerAttackDamageEnhance(player, monster));
        }
        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        DirectDamageToMob(player, monster, damage);
        return damage;
    }

    public static double causeAttackDamageToMonsterOnlyComputeDefence(Player player, Mob mob, double damage) {
        damage *= defenceDamageDecreaseRate(MobAttributes.defence(mob), PlayerAttributes.defencePenetration(player), PlayerAttributes.defencePenetration0(player));
        Compute.SummonValueItemEntity(mob.level(), player, mob, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW), 0);
        DirectDamageToMob(player, mob, damage);
        return damage;
    }

    public static double causeManaDamageToMonster_ApDamage_Direct(Player player, Mob monster, double damage, boolean computeDefenceOrEnhance) {
        if (computeDefenceOrEnhance) {
            damage *= defenceDamageDecreaseRate(MobAttributes.manaDefence(monster), PlayerAttributes.manaPenetration(player), PlayerAttributes.manaPenetration0(player));
            damage *= (1 + DamageInfluence.getPlayerManaDamageEnhance(player));
        }
        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
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
        DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + BaseDamage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }
        BaseDamage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
        ExDamage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
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
        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);

        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
        if (isPower) {
            Compute.AdditionEffects(player, monster, totalDamage, 1);
            WitherBook.witherBookEffect(player, monster);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
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
        DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升
        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + baseDamage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }
        baseDamage *= Compute.manaDefenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
        ExDamage *= Compute.manaDefenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0);
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

        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);

        if (elementDamage != 0 && !elementType.isEmpty() && elementValue != 0)
            Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false, elementType, elementDamage);
        else
            Compute.damageActionBarPacketSend(player, totalDamage, 0, true, false);

        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
        if (isPower) {
            Compute.AdditionEffects(player, monster, totalDamage, 1);
            WitherBook.witherBookEffect(player, monster);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.defenceDamageDecreaseRate(defence, defencePenetration, defencePenetration0)));
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
        DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

        damage += ExDamage;

        damage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

        double totalDamage = damage * (1 + DamageEnhance) * (1 + DamageInfluence.getPlayerFinalDamageEnhance(player, monster));
        totalDamage *= DamageInfluence.getMonsterControlDamageEffect(player, monster);
        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
    }

    public static void causeManaDamageToMonster_ApDamage(Player player, Mob monster, double damage, boolean isPower) {
        double Defence = MobAttributes.manaDefence(monster);
        double BreakDefence = PlayerAttributes.manaPenetration(player);
        double BreakDefence0 = PlayerAttributes.manaPenetration0(player);
        double DamageEnhance = 0;
        double ExDamage = 0;

        DamageEnhance += DamageInfluence.getPlayerCommonDamageUpOrDown(player, monster);
        DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
        DamageEnhance += DamageInfluence.getPlayerManaDamageEnhance(player); // 魔法伤害提升

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("---ManaPower---"));
            player.sendSystemMessage(Component.literal("BaseDamage : " + damage));
            player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
        }

        damage += ExDamage;
        damage *= Compute.manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

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
        Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE), 1);
        DirectDamageToMob(player, monster, totalDamage);
        Compute.manaDamageExEffect(player, monster, totalDamage);
        ManaCurios1.ManaDamageExIgnoreDefenceDamage(player, monster, totalDamage);
        if (isPower) {
            WitherBook.witherBookEffect(player, monster);
            Compute.AdditionEffects(player, monster, totalDamage, 1);
        }

        if (DebugCommand.playerFlagMap.getOrDefault(player.getName().getString(), false) && isPower) {
            player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
            player.sendSystemMessage(Component.literal("DamageEnhances.PlayerFinalDamageEnhance(player,monster) : " + DamageInfluence.getPlayerFinalDamageEnhance(player, monster)));
            player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.defenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
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

        DamageEnhance -= (1 - manaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0));

        DirectDamageToPlayer(player, hurter, BaseDamage * num * 0.1f * (1 + DamageEnhance));
    }

    public static void manaDamageToPlayer(Mob monster, Player player, double damage) {
        double manaDefence = PlayerAttributes.manaDefence(player);
        damage *= manaDefenceDamageDecreaseRate(manaDefence, MobAttributes.defencePenetration(monster), MobAttributes.defencePenetration0(monster));
        MonsterAttackEvent.monsterAttack(monster, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void manaDamageToPlayer(Mob monster, Player player, double damage, double penetration, double penetration0) {
        double manaDefence = PlayerAttributes.manaDefence(player);
        damage *= manaDefenceDamageDecreaseRate(manaDefence, penetration, penetration0);
        MonsterAttackEvent.monsterAttack(monster, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void manaDamageToPlayer_RateApDamage(Mob mob, Player player, double rate) {
        double damage = MobAttributes.attackDamage(mob) * rate;
        double manaDefence = PlayerAttributes.manaDefence(player);
        double manaPenetration = PlayerAttributes.manaPenetration(player);
        double manaPenetration0 = PlayerAttributes.manaPenetration0(player);
        damage *= manaDefenceDamageDecreaseRate(manaDefence, manaPenetration, manaPenetration0);
        MonsterAttackEvent.monsterAttack(mob, player, damage);
        BloodManaCurios.passive(player);
    }

    public static void DamageIgnoreDefenceToPlayer(Mob mob, Player player, double Damage) {
        MonsterAttackEvent.monsterAttack(mob, player, Damage);
    }

    public static void DirectDamageToPlayer(Mob mob, Player player, double damage) {
        if (damage >= player.getHealth()) {
            Compute.playerDeadModule(player);
            Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Compute.DescriptionWhiteColor("在与")).
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
        Damage *= defenceDamageDecreaseRate(Defence, 0, 0);
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
        if (entity instanceof Mob mob && !(entity instanceof Allay) && !(entity instanceof Animal)) {
            if (entity instanceof Villager) return;
            if (mob.isDeadOrDying()) return;
            if (DailyEndlessInstance.prohibitPlayerCauseDamage(player, mob)) return;
            /*Castle.CauseDamageRecord(player, mob); */
            if (Moon.isMoonAttackImmune(player, (Mob) entity)) damage *= 0.5;
            if (Moon.isMoonManaImmune(player, (Mob) entity)) damage *= 0.5;
            CastleSecondFloor.PlayerPickItemExDamage(player, mob);
            damage *= CastleSecondFloor.MobDamageImmune(player, mob);
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

                // 副手击杀效果
                Item offhandItem = player.getOffhandItem().getItem();
                if (offhandItem instanceof OnKillEffectOffHandItem item) item.onKill(player, mob);

                NetherNewRune.onKill(player, mob);
                HuskNewRune.onKill(player, mob);
                DailyEndlessInstance.onKillMob(player, mob);

                OnKillEffectCurios.kill(player, mob);
            } else mob.setHealth((float) (mob.getHealth() - finalDamage));

            // ---- //
            FireEquip.IgniteEffect(player, mob);
            DpsCommand.CalculateDamage(player, damage);
            MoonBelt.PassiveCauseDamage(player, damage); // 尘月玉缠
            entity.invulnerableTime = 0;
            StarBottle.playerBattleTickMapRefresh(player);
            Element.ElementParticleProvider(mob);
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

    public static double defenceDamageDecreaseRate(double Defence, double DefencePenetration, double DefencePenetration0) {
        double DefenceAfterCompute = Defence * (1 - DefencePenetration) - DefencePenetration0;
        if (DefenceAfterCompute < 0) return 2 - (2000 / (2000 - DefenceAfterCompute));
        return (2000 / (2000 + DefenceAfterCompute));
    }

    public static double manaDefenceDamageDecreaseRate(double Defence, double DefencePenetration, double DefencePenetration0) {
        // DefencePenetration = 百分比穿透 // DefencePenetration0 = 固定穿透
        double DefenceAfterCompute = Defence * (1 - DefencePenetration) - DefencePenetration0;
        // DefenceAfterCompute = 计算完穿透的护甲/魔抗值

        if (DefenceAfterCompute < 0) return 2 - (2000 / (2000 - DefenceAfterCompute));

        return (2000 / (2000 + DefenceAfterCompute));
    }
}
