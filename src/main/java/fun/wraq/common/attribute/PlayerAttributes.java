package fun.wraq.common.attribute;

import fun.wraq.Items.DevelopmentTools.equip.ManageEquip;
import fun.wraq.Items.DevelopmentTools.equip.OpsAttributes;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.attack.AttackCurios0;
import fun.wraq.customized.uniform.attack.AttackCurios1;
import fun.wraq.customized.uniform.attack.AttackCurios2;
import fun.wraq.customized.uniform.attack.AttackCurios5;
import fun.wraq.customized.uniform.bow.BowCurios0;
import fun.wraq.customized.uniform.bow.BowCurios2;
import fun.wraq.customized.uniform.bow.BowCurios3;
import fun.wraq.customized.uniform.mana.ManaCurios0;
import fun.wraq.customized.uniform.mana.ManaCurios2;
import fun.wraq.customized.uniform.mana.ManaCurios3;
import fun.wraq.customized.uniform.mana.ManaCurios4;
import fun.wraq.events.mob.loot.C5LootItems;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.suit.SArmorAttribute;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementBow;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSceptre;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.profession.alchemy.AlchemyPlayerData;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.tower.TowerMob;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
import fun.wraq.series.gems.GemAttributes;
import fun.wraq.series.holy.ice.curio.IceHolyCrest;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import fun.wraq.series.instance.series.castle.CastleManaArmor;
import fun.wraq.series.instance.series.castle.CastleSwiftArmor;
import fun.wraq.series.instance.series.devil.DevilAttackArmor;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerMainHand;
import fun.wraq.series.instance.series.moon.Equip.MoonBook;
import fun.wraq.series.instance.series.moon.Equip.MoonKnife;
import fun.wraq.series.instance.series.moon.Equip.MoonShield;
import fun.wraq.series.instance.series.moon.MoonCurios;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.chapter1.ForestNewRune;
import fun.wraq.series.newrunes.chapter1.PlainNewRune;
import fun.wraq.series.newrunes.chapter2.SkyNewRune;
import fun.wraq.series.newrunes.chapter6.CastleNewRune;
import fun.wraq.series.overworld.chapter1.forest.ForestArmor;
import fun.wraq.series.overworld.chapter1.mine.MineShield;
import fun.wraq.series.overworld.chapter1.plain.PlainArmor;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoArmor;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeArmor;
import fun.wraq.series.overworld.chapter2.manaArmor.LifeMana.LifeManaArmor;
import fun.wraq.series.overworld.divine.DivineUtils;
import fun.wraq.series.overworld.sakura.EarthMana.EarthPower;
import fun.wraq.series.overworld.sun.TabooPaper;
import fun.wraq.series.worldsoul.SoulEquipAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerAttributes {

    public static Map<Player, Map<Map<Item, Double>, Double>> playerAttributeCache = new HashMap<>();
    public static Map<Player, Map<Map<Item, Double>, Integer>> computeAttributeTick = new HashMap<>();

    public static boolean canGetFromCache(Player player, Map<Item, Double> attribute) {
        if (player.getOffhandItem().getItem() instanceof ManageEquip) return true;
        // 初始化
        int tick = Tick.get();
        if (!playerAttributeCache.containsKey(player)) {
            playerAttributeCache.put(player, new HashMap<>());
        }
        if (!computeAttributeTick.containsKey(player)) {
            computeAttributeTick.put(player, new HashMap<>());
        }
        if (!computeAttributeTick.get(player).containsKey(attribute)) {
            computeAttributeTick.get(player).put(attribute, 0);
        }

        Map<Map<Item, Double>, Integer> tickMap = computeAttributeTick.get(player);
        return tickMap.getOrDefault(attribute, 0) == tick;
    }

    public static double getFromCache(Player player, Map<Item, Double> attribute) {
        if (player.getOffhandItem().getItem() instanceof ManageEquip) {
            return OpsAttributes.getValue(attribute, player);
        }
        Map<Map<Item, Double>, Double> attributeMap = playerAttributeCache.get(player);
        return attributeMap.get(attribute);
    }

    public static void writeToCache(Player player, Map<Item, Double> attribute, double value) {
        Map<Map<Item, Double>, Integer> tickMap = computeAttributeTick.get(player);
        Map<Map<Item, Double>, Double> attributeMap = playerAttributeCache.get(player);
        tickMap.put(attribute, Tick.get());
        attributeMap.put(attribute, value);
    }

    public static double getMainDamage(Player player) {
        double attackDamage = attackDamage(player);
        double manaDamage = manaDamage(player);
        return Math.max(attackDamage * 2, manaDamage);
    }

    public static double getBaseAttackDamage(Player player) {
        double baseAttackDamage = player.experienceLevel;
        baseAttackDamage += computeAllEquipSlotBaseAttributeValue(player, Utils.attackDamage, true);
        baseAttackDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.attackDamage);
        baseAttackDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelAttackDamage, true);
        return baseAttackDamage;
    }

    public static double attackDamage(Player player) {
        if (canGetFromCache(player, Utils.attackDamage)) {
            return getFromCache(player, Utils.attackDamage);
        }
        int TickCount = Tick.get();
        double baseAttackDamage = player.experienceLevel;
        double exDamage = 0;
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        baseAttackDamage += computeAllEquipSlotBaseAttributeValue(player, Utils.attackDamage, true);
        baseAttackDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.attackDamage);

        // 计算线性等级强度属性数值
        baseAttackDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelAttackDamage, true);

        // 额外数值
        exDamage += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.attackDamage);
        if (SuitCount.getVolcanoSuitCount(player) >= 2) exDamage += baseAttackDamage * 0.15F;
        if (SuitCount.getObsiManaSuitCount(player) >= 4) exDamage += baseAttackDamage * 0.15F;
        if (data.contains("ManaSwordActive") && data.getInt("ManaSwordActive") > 0)
            exDamage += data.getInt("ManaSwordActive");

        if (SuitCount.getSkySuitCount(player) > 0) {
            if (player.getHealth() / player.getMaxHealth() > 0.8)
                exDamage += baseAttackDamage * 0.7 * Compute.SkySuitEffectRate(player);
            else if (player.getHealth() / player.getMaxHealth() > 0.4)
                exDamage += baseAttackDamage * 0.2 * Compute.SkySuitEffectRate(player);
        }
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 0)
            exDamage += baseAttackDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 1)
            exDamage += baseAttackDamage * 0.40 + 40;

        if (Compute.getSwordSkillLevel(data, 2) > 0 && data.contains(StringUtils.SwordSkillNum.Skill2) && data.getInt(StringUtils.SwordSkillNum.Skill2) > TickCount) {
            exDamage += baseAttackDamage * Compute.getSwordSkillLevel(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）‘

        if (Compute.getBowSkillLevel(data, 2) > 0 && data.contains(StringUtils.BowSkillNum.Skill2) && data.getInt(StringUtils.BowSkillNum.Skill2) > TickCount) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 2) * 0.02;
        } // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (Compute.getSwordSkillLevel(data, 5) > 0 && data.contains(StringUtils.SwordSkillNum.Skill5) && data.getInt(StringUtils.SwordSkillNum.Skill5) > TickCount) {
            exDamage += baseAttackDamage * Compute.getSwordSkillLevel(data, 5) * 0.015;
        } // 剑术-狂暴（造成暴击后，提升1%攻击力，持续3s）

        if (Compute.getBowSkillLevel(data, 5) > 0 && data.contains(StringUtils.BowSkillNum.Skill5) && data.getInt(StringUtils.BowSkillNum.Skill5) > TickCount) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 5) * 0.01;
        } // 弓术-狂暴（造成暴击后，提升1%攻击力，持续5s）

        String name = player.getName().getString();
        if (Compute.getSwordSkillLevel(data, 6) > 0 && Utils.SwordSkill6Map.containsKey(name) && Utils.SwordSkill6Map.get(name).getTime() > TickCount) {
            exDamage += baseAttackDamage * Compute.getSwordSkillLevel(data, 6) * 0.003 * Utils.SwordSkill6Map.get(name).getCount();
        } // 完美（持续造成暴击，将提供至多30%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）

        if (Compute.getBowSkillLevel(data, 6) > 0 && Utils.BowSkill6Map.containsKey(name) && Utils.BowSkill6Map.get(name).getTime() > TickCount) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 6) * 0.005 * Utils.BowSkill6Map.get(name).getCount();
        } // 完美（持续的命中目标的箭矢，将提供至多3%攻击力，持续10s，在十次命中后达最大值，在未命中时重置层数）

        if (Compute.getBowSkillLevel(data, 8) > 0 && Utils.bowTag.containsKey(mainhand)) {
            exDamage += baseAttackDamage * Compute.getBowSkillLevel(data, 8) * 0.02;
        } // 锻弦-力量（手持弓时，获得2%攻击力）

        int powerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            exDamage += powerAbilityPoint * 2;
        } // 能力

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDamage += 50;
        // 矿工裤被动

        if (SuitCount.getMineSuitCount(player) >= 4) exDamage += baseAttackDamage * 0.3;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            exDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.AttackDamage;

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (SwordSkill > Math.max(BowSkill, ManaSkill))
                exDamage += baseAttackDamage * (Utils.SpringScaleEffect.get(player) + 1) * 0.1;
        } //年兽鳞片

        if (mainhand.equals(ModItems.SoulSword.get())) {
            if (stackmainhandtag.contains(StringUtils.SoulSwordKillCount)) {
                exDamage += Math.min(256, stackmainhandtag.getInt(StringUtils.SoulSwordKillCount) / 100);
            }
        } // 本源被动

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            exDamage += baseAttackDamage * 0.1;
        }

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) exDamage += 100;

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) exDamage += 400;

        exDamage += DevilAttackArmor.DevilAttackArmorPassiveExDamage(player);
        exDamage += baseAttackDamage * EarthPower.PlayerDamageEnhance(player);

        // 新版饰品属性加成
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.attackDamage, StringUtils.RandomCuriosAttribute.attackDamage);
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelAttackDamage,
                StringUtils.RandomCuriosAttribute.xpLevelAttackDamage) * player.experienceLevel;

        // 器灵属性加成
        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.attackDamage);
        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.xpLevelAttackDamage) * player.experienceLevel;
        exDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExAttackDamage);
        exDamage += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExAttackDamage);
        exDamage += LifeElementSword.ExAttackDamage(player);
        exDamage += LifeElementBow.ExAttackDamage(player);
        exDamage += VolcanoArmor.exAttackDamage(player);
        exDamage += CastleNewRune.attackDamage(player);
        exDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerAttackDamageModifier);
        exDamage += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.exAttackDamage);
        exDamage += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.attackDamage);
        exDamage += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.playerExAttackDamage);
        // 请在上方添加

        double totalAttackDamage = baseAttackDamage + exDamage;
        double exRate = 0;
        exRate += MoonShield.damageEnhance(player);
        exRate += MoonKnife.damageEnhance(player);
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AttackCurios1.playerAttackDamageEnhance(player);
        exRate += HarbingerMainHand.getAttackDamageRate(player);
        exRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentAttackDamageEnhance)
                + Compute.CuriosAttribute.attributeValue(player, Utils.percentAttackDamageEnhance,
                StringUtils.RandomCuriosAttribute.percentAttackDamage);
        exRate += Compute.PassiveEquip.getAttribute(player, Utils.percentAttackDamageEnhance);
        exRate += computeAllEquipSlotBaseAttributeValue(player, Utils.percentAttackDamageEnhance, false);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.attackDamage);
        exRate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerPercentAttackDamageModifier);
        exRate += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.playerAttackDamageEnhance);
        exRate += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.percentAttackDamageEnhance);
        exRate += AttackCurios5.getExPercentAttackDamage(player);

        totalAttackDamage *= (1 + exRate);

        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) {
            totalAttackDamage *= 0.5;
        }

        writeToCache(player, Utils.attackDamage, totalAttackDamage);
        return totalAttackDamage;
    }

    public static double critRate(Player player) {
        if (canGetFromCache(player, Utils.critRate)) {
            return getFromCache(player, Utils.critRate);
        }
        CompoundTag data = player.getPersistentData();
        double critRate = 0.0d;
        if (player.getMainHandItem().is(C5LootItems.pillagerBow.get())) {
            CompoundTag tempData = player.getMainHandItem().getOrCreateTagElement(Utils.MOD_ID);
            if (tempData.getDouble(StringUtils.RandomAttribute.critRate) > 1)
                tempData.putDouble(StringUtils.RandomAttribute.critRate, 0.5);
        }

        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        critRate += computeAllEquipSlotBaseAttributeValue(player, Utils.critRate, false);
        critRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.critRate);
        critRate += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.critRate);

        // 器灵属性加成
        critRate += Compute.PassiveEquip.getAttribute(player, Utils.critRate);

        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 0)
            critRate += 0.2;
        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 1)
            critRate += 0.4;
        critRate += SArmorAttribute.value(player, SArmorAttribute.skyPower);
        if (Compute.getBowSkillLevel(data, 11) > 0 && Utils.bowTag.containsKey(mainhand)) {
            critRate += Compute.getBowSkillLevel(data, 11) * 0.02;
        } // 锻弦-平衡（手持弓时，获得额外2%暴击几率）

        int luckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            critRate += luckyAbilityPoint * 0.001;
        }

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            critRate +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritRate;

        critRate += Compute.CuriosAttribute.attributeValue(player, Utils.critRate, StringUtils.RandomCuriosAttribute.critRate); // 新版饰品属性加成

        critRate += AttackCurios2.playerCritRateUp(player);
        critRate += BowCurios2.playerCritRateUp(player);
        critRate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCritRateModifier);
        // 请在上方添加
        double exRate = 0;
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.critRate);
        critRate *= (1 + exRate);
        writeToCache(player, Utils.critRate, critRate);
        return critRate;
    }

    public static double critDamage(Player player) {
        if (canGetFromCache(player, Utils.critDamage)) {
            return getFromCache(player, Utils.critDamage);
        }
        CompoundTag data = player.getPersistentData();
        double critDamage = 1;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        critDamage += computeAllEquipSlotBaseAttributeValue(player, Utils.critDamage, false);
        // 计算随机属性装备数值
        critDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.critDamage);
        // 计算线性等级强度装备数值
        critDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelCritDamage, false);
        critDamage += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelCritDamage,
                StringUtils.RandomCuriosAttribute.xpLevelCritDamage) * player.experienceLevel;
        critDamage += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.critDamage);
        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 0)
            critDamage += 0.1;
        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 1)
            critDamage += 0.2;
        critDamage += SArmorAttribute.value(player, SArmorAttribute.snowPower);

        if (Compute.getBowSkillLevel(data, 7) > 0 && Utils.bowTag.containsKey(mainhand)) {
            critDamage += Compute.getBowSkillLevel(data, 7) * 0.1;
        } // 锻矢-锋利（手持弓时，获得10%暴击伤害）

        if (SuitCount.getVolcanoSuitCount(player) >= 4) critDamage += 0.35;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            critDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritDamage;

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                critDamage += data.getDouble("HealthStealAfterCompute");
            }
        } // 封魔者法盾

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.manaKnife.get())) {
            critDamage += data.getDouble("HealthStealAfterCompute") * 1;
        } // 猎魔者小刀

        critDamage += Compute.CuriosAttribute.attributeValue(player, Utils.critDamage, StringUtils.RandomCuriosAttribute.critDamage); // 新版饰品属性加成

        critDamage += Compute.PassiveEquip.getAttribute(player, Utils.critDamage); // 器灵属性加成
        critDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExCritDamage);
        critDamage += AttackCurios1.playerCritDamageUp(player);
        critDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCritDamageModifier);
        critDamage += MoonCurios.getCritDamage(player);
        critDamage += AttackCurios5.getExCritDamage(player);
        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AttackCurios0.PlayerFinalCritDamageEnhance(player);
        exRate += AttackCurios2.playerCritDamageEnhance(player);
        exRate += BowCurios2.playerCritDamageEnhance(player);
        exRate += SkyNewRune.critDamageInfluence(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.critDamage);
        critDamage *= (1 + exRate);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                data.putDouble("CritDamageAfterCompute", critDamage);
                critDamage = 0;
            }
        } // 封魔者法盾
        writeToCache(player, Utils.critDamage, critDamage);
        return critDamage;
    }

    public static double decreasePlayerCritDamage(Player player) {
        double withstandCritDamageReduction = 0.0d;
        withstandCritDamageReduction += computeAllEquipSlotBaseAttributeValue(player, Utils.critDamageDecrease, false);
        if (SuitCount.getMineSuitCount(player) >= 2) withstandCritDamageReduction += 0.5;
        return withstandCritDamageReduction;
    }

    public static double movementSpeedCommon(Player player) {
        if (canGetFromCache(player, Utils.movementSpeedCommon)) {
            return getFromCache(player, Utils.movementSpeedCommon);
        }
        double movementSpeedUp = 0;
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        int tick = Tick.get();
        CompoundTag data = player.getPersistentData();
        ItemStack mainHandStack = player.getMainHandItem();
        Item mainHandItem = mainHandStack.getItem();
        CompoundTag mainHandTag = mainHandStack.getTagElement(Utils.MOD_ID);

        movementSpeedUp += computeAllEquipSlotBaseAttributeValue(player, Utils.movementSpeedCommon, true);
        movementSpeedUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerMovementSpeedModifier);
        movementSpeedUp += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.movementSpeedUp);

        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > tick)
            movementSpeedUp += 0.2;
        // 妖刀-樱

        if (SuitCount.getLakeSuitCount(player) >= 4) movementSpeedUp += 0.1;
        if (SuitCount.getMineSuitCount(player) >= 4) movementSpeedUp -= 0.2;

        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 0)
            movementSpeedUp += 0.1;
        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 1)
            movementSpeedUp += 0.2;

        if (SuitCount.getSkySuitCount(player) > 0 && player.getHealth() / player.getMaxHealth() > 0.8)
            movementSpeedUp += 0.2 * Compute.SkySuitEffectRate(player);

        int flexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            movementSpeedUp += flexibilityAbilityPoint * 0.001;
        } // 能力

        if (Compute.getSwordSkillLevel(data, 9) > 0 && Utils.swordTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getSwordSkillLevel(data, 9) * 0.02;
        } // 剑舞（手持近战武器时，获得1%额外移动速度）

        if (Compute.getBowSkillLevel(data, 1) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getBowSkillLevel(data, 1) * 0.01;
        } // 原野护符（持有弓时，获得1%的额外移动速度）

        if (Compute.getBowSkillLevel(data, 9) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getBowSkillLevel(data, 9) * 0.02;
        } // 猎手本能（手持弓时，获得1%额外移动速度）

        if (Compute.getManaSkillLevel(data, 9) > 0 && Utils.sceptreTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.getManaSkillLevel(data, 9) * 0.02;
        } // 法师（手持法杖时，获得1%额外移动速度）

        if (mainHandTag != null && mainHandTag.contains(StringUtils.ManaCore.ManaCore)
                && mainHandTag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            movementSpeedUp += 0.08;
        }

        if (Compute.hasCurios(player, NewRuneItems.skyNewRune.get())){
            movementSpeedUp += 0.2;
        }

        movementSpeedUp += Compute.CuriosAttribute.attributeValue(player, Utils.movementSpeedCommon,
                StringUtils.RandomCuriosAttribute.commonMovementSpeed); // 新版饰品属性加成

        movementSpeedUp += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.movementSpeedCommon);

        // 上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.movementSpeedCommon)   ;
        movementSpeedUp *= (1 + exRate);

        writeToCache(player, Utils.movementSpeedCommon, movementSpeedUp);
        return movementSpeedUp;
    }

    public static double movementSpeedCurrent(Player player) {
        double movementSpeed = movementSpeedCommon(player);
        if (!Compute.playerIsInBattle(player)) movementSpeed += movementSpeedWithoutBattle(player);
        return movementSpeed;
    }

    public static double movementSpeedWithoutBattle(Player player) {
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        if (canGetFromCache(player, Utils.movementSpeedWithoutBattle)) {
            return getFromCache(player, Utils.movementSpeedWithoutBattle);
        }
        double speedUp = 0;
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();

        CompoundTag stackmainhandtag = new CompoundTag();

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        speedUp += computeAllEquipSlotBaseAttributeValue(player, Utils.movementSpeedWithoutBattle, false);

        speedUp += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.movementSpeedWithoutBattle);

        speedUp += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.movementSpeedWithoutBattle);

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            speedUp += 0.4;
        // 矿工裤被动

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge)) speedUp +=
                stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MovementSpeed;

        speedUp += Compute.CuriosAttribute.attributeValue(player, Utils.movementSpeedWithoutBattle,
                StringUtils.RandomCuriosAttribute.movementSpeed); // 新版饰品属性加成
        speedUp += Compute.PassiveEquip.getAttribute(player, Utils.movementSpeedWithoutBattle); // 器灵属性加成
        speedUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerMovementSpeedWithoutBattleModifier);

        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        speedUp *= (1 + exRate);

        writeToCache(player, Utils.movementSpeedWithoutBattle, speedUp);
        return speedUp;
    }

    public static double expUp(Player player) {
        if (canGetFromCache(player, Utils.expUp)) {
            return getFromCache(player, Utils.expUp);
        }
        CompoundTag data = player.getPersistentData();
        double expUp = 0;
        expUp += computeAllEquipSlotBaseAttributeValue(player, Utils.expUp, false);
        int luckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            expUp += luckyAbilityPoint * 0.01;
        }

        expUp += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.expUp);

        expUp += Compute.CuriosAttribute.attributeValue(player, Utils.expUp, StringUtils.RandomCuriosAttribute.expUp); // 新版饰品属性加成

        expUp += Compute.PassiveEquip.getAttribute(player, Utils.expUp); // 器灵属性加成

        int tier = PlanPlayer.getPlayerTier(player);
        expUp += new double[]{0, 1, 2, 3}[tier];
        expUp += LabourDayOldCoin.getExExpUp();

        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.expUp);
        expUp *= (1 + exRate);
        writeToCache(player, Utils.expUp, expUp);
        return expUp;
    }

    public static double defence(Player player) {
        if (canGetFromCache(player, Utils.defence)) {
            return getFromCache(player, Utils.defence);
        }
        int TickCount = Tick.get();
        CompoundTag data = player.getPersistentData();
        double baseDefence = player.experienceLevel * 0.2;
        double exDefence = 0.0d;
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();

        baseDefence += computeAllEquipSlotBaseAttributeValue(player, Utils.defence, true);
        baseDefence += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.defence);
        exDefence += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.defence);

        // 计算线性等级强度装备数值
        baseDefence += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelDefence, false);

        baseDefence += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelDefence,
                StringUtils.RandomCuriosAttribute.xpLevelDefence) * player.experienceLevel;

        // 以下为额外护甲
        if (SuitCount.getForestSuitCount(player) >= 2) exDefence += baseDefence * 0.25;
        if (SuitCount.getLifeManaSuitCount(player) >= 4) exDefence += baseDefence * 0.25;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null
                && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0) {
            exDefence += baseDefence * 0.25 + 5;
        }
        if (player.getEffect(ModEffects.DefenceUP.get()) != null
                && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1) {
            exDefence += baseDefence * 0.40 + 10;
        }
        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDefence += 1;
        // 矿工裤被动
        if (SuitCount.getMineSuitCount(player) >= 4) exDefence += 60;

        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) exDefence += 1;

        if (Utils.MineShieldEffect.containsKey(player) && Utils.MineShieldEffect.get(player) > TickCount) {
            exDefence += player.experienceLevel;
        }

        if (Utils.MeteoriteDefenceMap.containsKey(player) && Utils.MeteoriteDefenceTimeMap.get(player) > TickCount) {
            exDefence += Utils.MeteoriteDefenceMap.get(player) * 0.01;
        }

        if (Utils.SnowShieldPlayerEffectTickMap.containsKey(player) && Utils.SnowShieldPlayerEffectTickMap.get(player) > TickCount) {
            exDefence += Utils.SnowShieldPlayerEffectMap.get(player);
        } // 玉山圆盾

        exDefence += Compute.CuriosAttribute.attributeValue(player, Utils.defence, StringUtils.RandomCuriosAttribute.defence); // 新版饰品属性加成

        exDefence += Compute.PassiveEquip.getAttribute(player, Utils.defence); // 器灵属性加成

        exDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExDefence);
        exDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExDefence);
        exDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExDefence);
        exDefence += ForestArmor.exDefence(player);
        exDefence += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.defence);
        exDefence += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.defence);
        exDefence += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefenceModifier);
        // 请在上方添加
        double totalDefence = baseDefence + exDefence;
        double exRate = 0;
        exRate += EarthPower.PlayerDefenceEnhance(player);
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += MineShield.defenceEnhance(player);
        exRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentDefenceEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentDefenceEnhance,
                        StringUtils.RandomCuriosAttribute.percentDefenceEnhance);
        exRate += Compute.PassiveEquip.getAttribute(player, Utils.percentDefenceEnhance);
        exRate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerPercentDefenceModifier);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.defence);
        exRate += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.percentDefenceEnhance);
        totalDefence *= (1 + exRate);
        totalDefence -= StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefenceDecreaseModifier);
        totalDefence = Math.max(0, totalDefence);
        writeToCache(player, Utils.defence, totalDefence);
        return totalDefence;
    }

    public static double damageDirectDecrease(Player player) {
        double value = 0;
        CompoundTag data = player.getPersistentData();

        int powerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            value += powerAbilityPoint * 5;
        } // 能力

        return value;
    }

    public static double getHealingAmplification(Player player) {
        if (canGetFromCache(player, Utils.healingAmplification)) {
            return getFromCache(player, Utils.healingAmplification);
        }
        int tick = Tick.get();
        double healEffectUp = 1;
        CompoundTag data = player.getPersistentData();
        healEffectUp += computeAllEquipSlotBaseAttributeValue(player, Utils.healingAmplification, false);
        if (SuitCount.getForestSuitCount(player) >= 4) healEffectUp += 0.5f;
        int vitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            healEffectUp += vitalityAbilityPoint * 0.01;
        }
        if (data.getInt(StringUtils.MineMonsterEffect) >= tick) healEffectUp -= 0.8;

        healEffectUp += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.healingAmplification);
        healEffectUp += Compute.CuriosAttribute.attributeValue(player, Utils.healingAmplification, StringUtils.RandomCuriosAttribute.healEffectUp); // 新版饰品属性加成
        healEffectUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerHealAmplifierModifier);

        writeToCache(player, Utils.healingAmplification, healEffectUp * (1 - healEffectDecrease(player)));
        return healEffectUp * (1 - healEffectDecrease(player));
    }

    public static double healEffectDecrease(Player player) {
        double rate = 0;
        rate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerHealAmplifierReductionModifier);
        return rate;
    }

    public static double extraSwiftness(Player player) {
        if (canGetFromCache(player, Utils.swiftnessUp)) {
            return getFromCache(player, Utils.swiftnessUp);
        }
        CompoundTag data = player.getPersistentData();
        int TickCount = Tick.get();
        double swiftnessUp = 0.0d;

        swiftnessUp += computeAllEquipSlotBaseAttributeValue(player, Utils.swiftnessUp, false);

        int flexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            swiftnessUp += flexibilityAbilityPoint * 0.1;
        } // 能力

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (BowSkill > Math.max(SwordSkill, ManaSkill)) swiftnessUp += Utils.SpringScaleEffect.get(player) + 1;
        } //年兽鳞片

        swiftnessUp += Compute.CuriosAttribute.attributeValue(player, Utils.swiftnessUp, StringUtils.RandomCuriosAttribute.swiftnessUp); // 新版饰品属性加成

        swiftnessUp += Compute.PassiveEquip.getAttribute(player, Utils.swiftnessUp); // 器灵属性加成
        swiftnessUp += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExSwiftnessUp);
        swiftnessUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerSwiftnessModifier);
        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += BowCurios0.SwiftnessUp(player);
        swiftnessUp *= (1 + exRate);

        writeToCache(player, Utils.swiftnessUp, swiftnessUp);
        return swiftnessUp;
    }

    public static double attackRangeUp(Player player) {
        if (canGetFromCache(player, Utils.attackRangeUp)) {
            return getFromCache(player, Utils.attackRangeUp);
        }
        CompoundTag data = player.getPersistentData();
        double rangeUp = 0.0d;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.getSwordSkillLevel(data, 11) > 0 && Utils.swordTag.containsKey(mainhand))
            rangeUp += Compute.getSwordSkillLevel(data, 11) * 0.2;

        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        rangeUp *= (1 + exRate);
        writeToCache(player, Utils.attackRangeUp, rangeUp);
        return rangeUp;
    }

    public static double powerReleaseSpeed(Player player) {
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        if (player.isCreative()) return 100;
        if (canGetFromCache(player, Utils.coolDownDecrease)) {
            return getFromCache(player, Utils.coolDownDecrease);
        }
        CompoundTag data = player.getPersistentData();
        double releaseSpeed = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        releaseSpeed += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.coolDownDecrease);
        releaseSpeed += computeAllEquipSlotBaseAttributeValue(player, Utils.coolDownDecrease, false);
        if (SuitCount.getLakeSuitCount(player) >= 2) releaseSpeed += 0.1;
        if (SuitCount.getObsiManaSuitCount(player) >= 4) releaseSpeed += 0.2;

        releaseSpeed += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.coolDown);

        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 0)
            releaseSpeed += 0.2;
        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 1)
            releaseSpeed += 0.4;
        releaseSpeed += SArmorAttribute.value(player, SArmorAttribute.lakePower);
        if (Compute.getSwordSkillLevel(data, 7) > 0 && Utils.swordTag.containsKey(mainhand))
            releaseSpeed += Compute.getSwordSkillLevel(data, 7) * 0.03; // 冷静（手持近战武器时，获得3%冷却缩减）

        if (Compute.getManaSkillLevel(data, 7) > 0 && Utils.sceptreTag.containsKey(mainhand))
            releaseSpeed += Compute.getManaSkillLevel(data, 7) * 0.03; // 冷静（手持法杖时，获得3%冷却缩减）

        releaseSpeed += EarthPower.PlayerCoolDownEnhance(player); // 地蕴法术

        releaseSpeed += Compute.CuriosAttribute.attributeValue(player, Utils.coolDownDecrease, StringUtils.RandomCuriosAttribute.coolDown); // 新版饰品属性加成
        releaseSpeed += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExCoolDownDecrease);
        releaseSpeed += LakeArmor.exCooldown(player);

        int luckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            releaseSpeed += luckyAbilityPoint * 0.01;
        }

        releaseSpeed += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCooldownModifier);
        releaseSpeed += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.coolDownDecrease);
        if (Utils.sceptreTag.containsKey(mainhand)) {
            releaseSpeed += Compute.getManaSkillLevel(data, 11) * 0.02; // 术法全析
        }

        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.coolDownDecrease);
        releaseSpeed *= (1 + exRate);

        writeToCache(player, Utils.coolDownDecrease, releaseSpeed);
        return releaseSpeed;
    }

    public static double coolDownDecrease(Player player) {
        return 1 - (1 / (1 + (powerReleaseSpeed(player))));
    }

    public static double defencePenetration(Player player) {
        if (canGetFromCache(player, Utils.defencePenetration)) {
            return getFromCache(player, Utils.defencePenetration);
        }
        double defenceRate = 1;
        defenceRate *= (1 - computeAllEquipSlotBaseAttributeValue(player, Utils.defencePenetration, false));

        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 0)
            defenceRate *= (1 - 0.20);
        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 1)
            defenceRate *= (1 - 0.45);

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > Tick.get()) {
            defenceRate *= (1 - 0.2);
        }
        defenceRate *= (1 - StableAttributesModifier
                .getModifierValue(player, StableAttributesModifier.playerDefencePenetrationModifier));
        double decreaseRate = 0;
        decreaseRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.defencePenetration);
        if (decreaseRate > 0) {
            defenceRate *= (1 - decreaseRate);
        }
        defenceRate *= (1 - Compute.CuriosAttribute.attributeValue(player,
                Utils.defencePenetration, StringUtils.RandomCuriosAttribute.defencePenetration)); // 新版饰品属性加成
        defenceRate *= (1 - StableTierAttributeModifier
                .getModifierValue(player, StableTierAttributeModifier.playerDefencePenetration));
        defenceRate *= (1 - Compute.PassiveEquip.getAttribute(player, Utils.defencePenetration));
        defenceRate *= (1 - MoonCurios.getDefencePenetration(player));
        defenceRate *= (1 - IceHolyCrest.getExDefencePenetration(player));
        // 请在上方添加
        writeToCache(player, Utils.defencePenetration, 1 - defenceRate);
        return 1 - defenceRate;
    }

    public static double defencePenetration0(Player player) {
        if (canGetFromCache(player, Utils.defencePenetration0)) {
            return getFromCache(player, Utils.defencePenetration0);
        }
        CompoundTag data = player.getPersistentData();
        double defencePenetration0 = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        defencePenetration0 += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.defencePenetration0);
        defencePenetration0 += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelDefencePenetration0, false);
        defencePenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelDefencePenetration0,
                StringUtils.RandomCuriosAttribute.xpLevelDefencePenetration0) * player.experienceLevel;

        // 器灵属性加成
        defencePenetration0 += Compute.PassiveEquip.getAttribute(player, Utils.defencePenetration0);

        defencePenetration0 += computeAllEquipSlotBaseAttributeValue(player, Utils.defencePenetration0, false);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            defencePenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.DefencePenetration0;

        if (mainhand.equals(ModItems.ShipBow.get())) {
            List<Player> serverPlayerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger Count = new AtomicInteger();
            serverPlayerList.forEach(player1 -> {
                if (player1.distanceTo(player) <= 6) Count.getAndIncrement();
            });
            defencePenetration0 += 2.5 * Math.min(Count.get(), 4);
        }

        defencePenetration0 += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.defencePenetration0);
        defencePenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.defencePenetration0, StringUtils.RandomCuriosAttribute.defencePenetration0); // 新版饰品属性加成

        defencePenetration0 += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefencePenetration0Modifier);
        defencePenetration0 += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.defencePenetration0);
        defencePenetration0 += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.playerDefencePenetration0);
        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.defencePenetration0);
        defencePenetration0 *= (1 + exRate);

        writeToCache(player, Utils.defencePenetration0, defencePenetration0);
        return defencePenetration0;
    }

    public static double healthRecover(Player player) {
        if (canGetFromCache(player, Utils.healthRecover)) {
            return getFromCache(player, Utils.healthRecover);
        }
        double healthRecover = 5 + player.experienceLevel * 0.1;
        CompoundTag data = player.getPersistentData();

        // 处理随机属性装备
        healthRecover += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.healthRecover);

        // 处理宝石属性
        healthRecover += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.healthRecover);

        // 处理基础属性
        healthRecover += computeAllEquipSlotBaseAttributeValue(player, Utils.healthRecover, true);

        // 处理主动被动等额外属性
        if (SuitCount.getPlainSuitCount(player) >= 2) healthRecover += 0.5 + player.getMaxHealth() * 0.01F;
        if (SuitCount.getLifeManaSuitCount(player) >= 4) healthRecover += 1 + player.getMaxHealth() * 0.01F;
        if (!Utils.OverWorldLevelIsNight && SuitCount.getForestSuitCount(player) >= 4) healthRecover += 5;

        if (player.getEffect(ModEffects.HEALTHRECOVER.get()) != null && player.getEffect(ModEffects.HEALTHRECOVER.get()).getAmplifier() == 0)
            healthRecover += player.getMaxHealth() * 0.025;
        if (player.getEffect(ModEffects.HEALTHRECOVER.get()) != null && player.getEffect(ModEffects.HEALTHRECOVER.get()).getAmplifier() == 1)
            healthRecover += player.getMaxHealth() * 0.05;

        int vitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            healthRecover += vitalityAbilityPoint;
        }

        healthRecover += Compute.CuriosAttribute
                .attributeValue(player, Utils.healthRecover, StringUtils.RandomCuriosAttribute.healthRecover); // 新版饰品属性加成
        healthRecover += PlainNewRune.playerHealthRecover(player);
        healthRecover += ForestNewRune.playerHealthRecoverUp(player);

        healthRecover += StableAttributesModifier.getModifierValue(player,
                StableAttributesModifier.playerHealthRecoverModifier);

        // 最大生命值百分比生命回复
        healthRecover += computeAllEquipSlotBaseAttributeValue(player, Utils.percentHealthRecover, false)
                * player.getMaxHealth();
        healthRecover += StableAttributesModifier.getModifierValue(player,
                StableAttributesModifier.playerPercentHealthRecoverModifier) * player.getMaxHealth();

        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.healthRecover);
        healthRecover *= (1 + exRate);

        writeToCache(player, Utils.healthRecover, healthRecover);
        return healthRecover;
    }

    public static double maxHealth(Player player) {
        if (canGetFromCache(player, Utils.maxHealth)) {
            return getFromCache(player, Utils.maxHealth);
        }
        double maxHealth = 200 + player.experienceLevel * 40;
        CompoundTag data = player.getPersistentData();
        maxHealth += computeAllEquipSlotBaseAttributeValue(player, Utils.maxHealth, true);
        maxHealth += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.maxHealth);
        maxHealth += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.maxHealth);
        if (SuitCount.getPlainSuitCount(player) >= 4) maxHealth += 800;
        maxHealth += SArmorAttribute.value(player, SArmorAttribute.sumPower);

        int vitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            maxHealth += vitalityAbilityPoint * 40;
        }

        maxHealth += Compute.CuriosAttribute.attributeValue(player, Utils.maxHealth, StringUtils.RandomCuriosAttribute.maxHealth); // 新版饰品属性加成

        maxHealth += Compute.PassiveEquip.getAttribute(player, Utils.maxHealth); // 器灵属性加成
        maxHealth += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExMaxHealth);
        maxHealth += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExMaxHealth);
        maxHealth += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExMaxHealth);
        maxHealth += PlainArmor.exMaxHealth(player);
        maxHealth += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.playerMaxHealthExValue);

        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentMaxHealthEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentMaxHealthEnhance,
                        StringUtils.RandomCuriosAttribute.percentMaxHealthEnhance);
        exRate += Compute.PassiveEquip.getAttribute(player, Utils.percentMaxHealthEnhance);
        exRate += Compute.getPlayerPotionEffectRate(player, ModEffects.GIANT.get(), 0.15, 0.25);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.maxHealth);
        maxHealth *= (1 + exRate);

        writeToCache(player, Utils.maxHealth, maxHealth);
        return maxHealth;
    }

    public static double getBaseManaDamage(Player player) {
        double baseDamage = 0;
        baseDamage += computeAllEquipSlotBaseAttributeValue(player, Utils.manaDamage, true);
        baseDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaDamage);
        baseDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaDamage, true);
        return baseDamage;
    }

    public static double manaDamage(Player player) {
        if (canGetFromCache(player, Utils.manaDamage)) {
            return getFromCache(player, Utils.manaDamage);
        }

        int tickCount = Tick.get();
        double baseDamage = 0;
        double exDamage = 0;
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag mainHandItemTag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            mainHandItemTag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        baseDamage += computeAllEquipSlotBaseAttributeValue(player, Utils.manaDamage, true);
        baseDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaDamage);
        baseDamage += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaDamage, true);
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelManaDamage,
                StringUtils.RandomCuriosAttribute.xpLevelManaDamage) * player.experienceLevel;
        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.xpLevelManaDamage) * player.experienceLevel;
        exDamage += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.manaDamage);
        exDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaDamageModifier);

        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 0)
            exDamage += baseDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 1)
            exDamage += baseDamage * 0.4 + 40;
        exDamage += SArmorAttribute.value(player, SArmorAttribute.manaPower);

        int intelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            exDamage += intelligentAbilityPoint * 2;
        } // 能力

        if (Compute.getManaSkillLevel(data, 2) > 0 && data.contains(StringUtils.ManaSkillNum.Skill2) && data.getInt(StringUtils.ManaSkillNum.Skill2) > tickCount) {
            exDamage += baseDamage * Compute.getManaSkillLevel(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDamage += 75;
        // 矿工裤被动

        if (SuitCount.getVolcanoSuitCount(player) >= 2) exDamage += baseDamage * 0.25F;
        if (SuitCount.getObsiManaSuitCount(player) >= 4) exDamage += baseDamage * 0.25F;

        if (mainHandItemTag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            exDamage +=
                    mainHandItemTag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaAttackDamage;

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > tickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (ManaSkill > Math.max(SwordSkill, BowSkill))
                exDamage += (Utils.SpringScaleEffect.get(player) + 1) * 0.1 * baseDamage;
        } //年兽鳞片

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > tickCount) {
            exDamage += baseDamage * 0.1;
        }

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) exDamage += 400;

        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) exDamage += 1600;

        exDamage += baseDamage * EarthPower.PlayerDamageEnhance(player);
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.manaDamage, StringUtils.RandomCuriosAttribute.manaDamage); // 新版饰品属性加成

        exDamage += Compute.PassiveEquip.getAttribute(player, Utils.manaDamage); // 器灵属性加成
        exDamage += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDamage);
        exDamage += LifeElementSceptre.ExManaDamage(player);
        exDamage += VolcanoArmor.exManaDamage(player);
        exDamage += CastleNewRune.manaDamage(player);
        exDamage += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaDamage);
        exDamage += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.exManaDamage);
        exDamage += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaDamage);
        exDamage += ManaCurios4.getExManaDamage(player);
        // 请在上方添加
        double totalDamage = baseDamage + exDamage;
        double exRate = 0;
        exRate += MoonBook.damageEnhance(player);
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += ManaCurios0.PlayerFinalManaDamageEnhance(player);
        exRate += ManaCurios2.playerFinalManaDamageEnhance(player);
        exRate += HarbingerMainHand.getManaDamageRate(player);
        exRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentManaDamageEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentManaDamageEnhance,
                        StringUtils.RandomCuriosAttribute.percentManaDamageEnhance);
        exRate += Compute.PassiveEquip.getAttribute(player, Utils.percentManaDamageEnhance);
        exRate += computeAllEquipSlotBaseAttributeValue(player, Utils.percentManaDamageEnhance, false);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.manaDamage);
        exRate += StableAttributesModifier
                .getModifierValue(player, StableAttributesModifier.playerPercentManaDamageModifier);
        exRate += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.percentManaDamageEnhance);
        exRate += ManaCurios4.getExManaDamageRate(player);

        totalDamage *= (1 + exRate);
        Utils.playerManaDamageBeforeTransform.put(player, totalDamage);

        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.DevilManaHelmet.get())) {
            totalDamage *= 1.25;
        }

        writeToCache(player, Utils.manaDamage, totalDamage);
        return totalDamage;
    }

    public static double manaHealthSteal(Player player) {
        if (canGetFromCache(player, Utils.manaHealthSteal)) {
            return getFromCache(player, Utils.manaHealthSteal);
        }
        double manaHealSteal = 0.0d;

        manaHealSteal += computeAllEquipSlotBaseAttributeValue(player, Utils.manaHealthSteal, false);
        if (SuitCount.getLifeManaSuitCount(player) >= 4) manaHealSteal += 0.05;
        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) manaHealSteal += 0.05;
        manaHealSteal += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.manaHealthSteal);
        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player))
            manaHealSteal += 0.05;

        manaHealSteal += Compute.CuriosAttribute.attributeValue(player, Utils.manaHealthSteal, StringUtils.RandomCuriosAttribute.manaHealthSteal); // 新版饰品属性加成
        //请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        manaHealSteal *= (1 + exRate);

        writeToCache(player, Utils.manaHealthSteal, manaHealSteal);
        return manaHealSteal;
    }

    public static double manaRecover(Player player) {
        if (player.isCreative()) return 1000;
        if (canGetFromCache(player, Utils.manaRecover)) {
            return getFromCache(player, Utils.manaRecover);
        }
        double manaRecover = player.experienceLevel * 0.1;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        manaRecover += computeAllEquipSlotBaseAttributeValue(player, Utils.manaRecover, false);
        manaRecover += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaRecover);
        manaRecover += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.manaRecover);
        if (SuitCount.getLifeManaSuitCount(player) >= 2) manaRecover += 5;
        if (SuitCount.getObsiManaSuitCount(player) >= 2) manaRecover += 5;

        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 0)
            manaRecover += 20;
        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 1)
            manaRecover += 45;
        if (Compute.getSwordSkillLevel(data, 8) > 0 && Utils.swordTag.containsKey(mainhand))
            manaRecover += Compute.getSwordSkillLevel(data, 8); // 洞悉（手持近战武器时，获得1额外法力回复）
        if (Compute.getManaSkillLevel(data, 1) > 0 && Utils.sceptreTag.containsKey(mainhand))
            manaRecover += Compute.getManaSkillLevel(data, 1) * 0.5; // 仙女护符（持有法杖时，获得额外0.5点法力回复）
        if (Compute.getManaSkillLevel(data, 8) > 0 && Utils.sceptreTag.containsKey(mainhand))
            manaRecover += Compute.getManaSkillLevel(data, 8); // 洞悉（手持法杖时，获得1额外法力回复）

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            manaRecover +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaRecover;

        manaRecover += Compute.CuriosAttribute.attributeValue(player, Utils.manaRecover, StringUtils.RandomCuriosAttribute.manaRecover); // 新版饰品属性加成
        manaRecover += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaRecoverModifier);
        manaRecover += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaRecover);
        manaRecover += TabooPaper.getExManaRecoverValue(player);
        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.manaRecover);
        manaRecover *= (1 + exRate);

        writeToCache(player, Utils.manaRecover, manaRecover);
        return manaRecover;
    }

    public static double manaDefence(Player player) {
        if (canGetFromCache(player, Utils.manaDefence)) {
            return getFromCache(player, Utils.manaDefence);
        }
        int tickCount = Tick.get();
        CompoundTag data = player.getPersistentData();
        double baseDefence = player.experienceLevel * 0.2;
        double exDefence = 0.0d;

        // 计算线性等级强度装备数值
        baseDefence += computeAllEquipSlotBaseAttributeValue(player, Utils.manaDefence, true);
        baseDefence += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaDefence, false);
        baseDefence += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelManaDefence,
                StringUtils.RandomCuriosAttribute.xpLevelManaDefence) * player.experienceLevel;

        // 以下为额外魔法抗性
        if (SuitCount.getForestSuitCount(player) >= 4) {
            exDefence += baseDefence * 0.25;
        }
        if (LifeManaArmor.getPlayerLifeManaArmorCount(player) == 4) exDefence += baseDefence * 0.25;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 0)
            exDefence += baseDefence * 0.25 + 3;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 1)
            exDefence += baseDefence * 0.4 + 6;

        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > tickCount) exDefence += 4;

        exDefence += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.manaDefence);

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) exDefence += 2;
        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) exDefence += 2;
        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) exDefence += 4;
        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) exDefence += 4;

        exDefence += Compute.CuriosAttribute.attributeValue(player, Utils.manaDefence, StringUtils.RandomCuriosAttribute.manaDefence); // 新版饰品属性加成
        exDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExManaDefence);
        exDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDefence);
        exDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExManaDefence);
        exDefence += StableTierAttributeModifier.getModifierValue(player, StableTierAttributeModifier.manaDefence);
        exDefence += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaDefenceModifier);
        // 请在上方添加

        double totalDefence = baseDefence + exDefence;
        double exRate = 0;
        exRate += EarthPower.PlayerDefenceEnhance(player);
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += MineShield.defenceEnhance(player);
        exRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentManaDefenceEnhance) +
                Compute.CuriosAttribute.attributeValue(player, Utils.percentManaDefenceEnhance,
                        StringUtils.RandomCuriosAttribute.percentManaDefenceEnhance);
        exRate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerPercentManaDefenceModifier);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.manaDefence);
        exRate += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.percentManaDefenceEnhance);
        totalDefence *= (1 + exRate);

        totalDefence -= StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaDefenceDecreaseModifier);
        totalDefence = Math.max(0, totalDefence);
        writeToCache(player, Utils.manaDefence, totalDefence);
        return totalDefence;
    }

    public static double healthSteal(Player player) {
        if (canGetFromCache(player, Utils.healthSteal)) {
            return getFromCache(player, Utils.healthSteal);
        }
        double healthSteal = 0.0d;
        CompoundTag data = player.getPersistentData();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        healthSteal += computeAllEquipSlotBaseAttributeValue(player, Utils.healthSteal, false);
        healthSteal += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.healthSteal);

        // 器灵属性加成
        healthSteal += Compute.PassiveEquip.getAttribute(player, Utils.healthSteal);

        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 0)
            healthSteal += 0.12;
        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 1)
            healthSteal += 0.25;
        healthSteal += SArmorAttribute.value(player, SArmorAttribute.netherPower);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand)))
            healthSteal +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.HealthSteal;

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) healthSteal += 0.05;

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                healthSteal += data.getDouble("CritDamageAfterCompute");
            }
        } // 封魔者法盾

        healthSteal += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.healthSteal);

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) healthSteal += 0.05;

        healthSteal += Compute.CuriosAttribute.attributeValue(player, Utils.healthSteal, StringUtils.RandomCuriosAttribute.healthSteal); // 新版饰品属性加成

        // 请在上方添加

        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += SuitCount.getBloodManaSuitCount(player) * 0.08;
        healthSteal *= (1 + exRate);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.manaKnife.get())) {
            data.putDouble("HealthStealAfterCompute", healthSteal);
            healthSteal = 0;
        } // 猎魔者小刀

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                data.putDouble("HealthStealAfterCompute", healthSteal);
                healthSteal = 0;
            }
        } // 封魔者法盾

        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) {
            healthSteal *= 0.25;
        }

        writeToCache(player, Utils.healthSteal, healthSteal);
        return healthSteal;
    }

    public static double manaPenetration(Player player) {
        if (canGetFromCache(player, Utils.manaPenetration)) {
            return getFromCache(player, Utils.manaPenetration);
        }
        int tick = Tick.get();
        double defenceRate = 1;
        defenceRate *= (1 - computeAllEquipSlotBaseAttributeValue(player, Utils.manaPenetration, false));

        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 0)
            defenceRate *= (1 - 0.2);
        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 1)
            defenceRate *= (1 - 0.45);

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > tick) {
            defenceRate *= (1 - 0.2);
        }

        double decreaseRate = 0;
        decreaseRate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.manaPenetration);

        if (decreaseRate > 0) defenceRate *= (1 - decreaseRate);

        defenceRate *= (1 - Compute.CuriosAttribute.attributeValue(player, Utils.manaPenetration,
                StringUtils.RandomCuriosAttribute.manaPenetration)); // 新版饰品属性加成
        defenceRate *= (1 - Compute.PassiveEquip.getAttribute(player, Utils.manaPenetration));
        defenceRate *= (1 - StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaPenetrationModifier));
        defenceRate *= (1 - ManaCurios4.getExManaPenetrationRate(player));

        // 请在上方添加
        writeToCache(player, Utils.manaPenetration, 1 - defenceRate);
        return 1 - defenceRate;
    }

    public static double manaPenetration0(Player player) {
        if (canGetFromCache(player, Utils.manaPenetration0)) {
            return getFromCache(player, Utils.manaPenetration0);
        }
        CompoundTag data = player.getPersistentData();
        double manaPenetration0 = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        manaPenetration0 += computeAllEquipSlotBaseAttributeValue(player, Utils.manaPenetration0, false);
        manaPenetration0 += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaPenetration0);
        manaPenetration0 += computeAllEquipSlotXpLevelAttributeValue(player, Utils.xpLevelManaPenetration0, false);
        manaPenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.xpLevelManaPenetration0,
                StringUtils.RandomCuriosAttribute.xpLevelManaPenetration0) * player.experienceLevel;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            manaPenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaPenetration0;

        if (stackmainhandtag.contains(StringUtils.ManaCore.ManaCore) && stackmainhandtag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            manaPenetration0 += movementSpeedWithoutBattle(player);
        }

        if (SuitCount.getVolcanoSuitCount(player) >= 4) manaPenetration0 += 10;

        manaPenetration0 += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.manaPenetration0);
        manaPenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.manaPenetration0, StringUtils.RandomCuriosAttribute.manaPenetration0); // 新版饰品属性加成
        manaPenetration0 += Compute.PassiveEquip.getAttribute(player, Utils.manaPenetration0); // 器灵属性加成
        manaPenetration0 += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.manaPenetration0);
        if (Utils.sceptreTag.containsKey(mainhand)) {
            manaPenetration0 += Compute.getManaSkillLevel(data, 10) * 3; // 力凝魔核
        }

        manaPenetration0 += StableAttributesModifier.getModifierValue(player,
                StableAttributesModifier.playerManaPenetration0Modifier);
        manaPenetration0 += ManaCurios4.getExManaPenetration0(player);
        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.manaPenetration0);
        manaPenetration0 *= (1 + exRate);

        writeToCache(player, Utils.manaPenetration0, manaPenetration0);
        return manaPenetration0;
    }

    public static double maxMana(Player player) {
        if (canGetFromCache(player, Utils.maxMana)) {
            return getFromCache(player, Utils.maxMana);
        }
        CompoundTag data = player.getPersistentData();
        double maxMana = player.experienceLevel;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        maxMana += computeAllEquipSlotBaseAttributeValue(player, Utils.maxMana, false);
        int intelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            maxMana += intelligentAbilityPoint * 10;
        } // 能力

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            maxMana +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MaxMana;
        maxMana += Compute.CuriosAttribute.attributeValue(player, Utils.maxMana, StringUtils.RandomCuriosAttribute.maxMana); // 新版饰品属性加成
        maxMana += Compute.PassiveEquip.getAttribute(player, Utils.maxMana); // 器灵属性加成
        maxMana += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.maxMana);
        maxMana += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.maxMana);
        // 请在上方添加
        double exRate = 0;
        exRate += Compute.playerFantasyAttributeEnhance(player);
        exRate += AlchemyPlayerData.getEnhanceRate(player, Utils.maxMana);

        maxMana *= (1 + exRate);

        writeToCache(player, Utils.maxMana, 250 + maxMana);
        return 250 + maxMana;
    }

    public static double getMineSpeedEnhanceRate(Player player) {
        double rate = 0;
        rate += computeAllEquipSlotBaseAttributeValue(player, WraqPickaxe.mineSpeed, false);
        rate += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, WraqPickaxe.mineSpeed);
        rate += MineSpur.getPlayerMineLevel(player);
        return rate;
    }

    public static double playerToughness(Player player) {
        double value = 0;
        CompoundTag data = player.getPersistentData();
        value += computeAllEquipSlotBaseAttributeValue(player, Utils.toughness, false);
        int powerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            value += powerAbilityPoint * 0.004;
        } // 能力
        return Math.min(0.5, value);
    }

    public static double getAttackSpeedEnhanceRate(Player player) {
        double rate = 0;
        rate += computeAllEquipSlotBaseAttributeValue(player, Utils.attackSpeedEnhance, false);
        rate += Compute.CuriosAttribute.attributeValue(player, Utils.attackSpeedEnhance, null);
        CompoundTag data = player.getPersistentData();
        Item mainHandItem = player.getMainHandItem().getItem();
        if (Compute.getSwordSkillLevel(data, 10) > 0 && Utils.swordTag.containsKey(mainHandItem)) {
            rate += Compute.getSwordSkillLevel(data, 10) * 0.03;
        }
        if (Compute.getBowSkillLevel(data, 10) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            rate += Compute.getBowSkillLevel(data, 10) * 0.03;
        }
        return rate;
    }

    public static double getElementStrength(Player player) {
        double value = 0;
        value += computeAllEquipSlotBaseAttributeValue(player, Utils.elementStrength, false);
        value += InCuriosOrEquipSlotAttributesModify.getAttributes(player, Utils.elementStrength);
        value += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.elementStrength);
        // 以下是对最终属性数值进行调整，这个元素本身就是个百分比元素！
        double rate = 0;
        rate -= DivineUtils.getPlayerElementStrengthDecreaseRate(player);
        value *= (1 + rate);
        return value;
    }

    public static float getArrowExFlySpeed(Player player) {
        float value = 0;
        value += BowCurios3.getArrowExFlySpeed(player);
        return value;
    }

    public static float getManaArrowExFlySpeed(Player player) {
        float value = 0;
        value += ManaCurios3.getManaArrowExFlySpeed(player);
        return value;
    }

    public static double handleArmorRandomAttribute(Player player, String attributeType) {
        double value = 0;
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (helmetTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, helmet, attributeType);
            value += baseValue;
            if (helmetTag.contains("Forging"))
                value += Compute.forgingValue(helmetTag, baseValue);

        }
        if (chestTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, chest, attributeType);
            value += baseValue;
            if (chestTag.contains("Forging"))
                value += Compute.forgingValue(chestTag, baseValue);
        }
        if (leggingsTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, leggings, attributeType);
            value += baseValue;
            if (leggingsTag.contains("Forging"))
                value += Compute.forgingValue(leggingsTag, baseValue);
        }
        if (bootsTag.contains(attributeType)) {
            double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, boots, attributeType);
            value += baseValue;
            if (bootsTag.contains("Forging"))
                value += Compute.forgingValue(bootsTag, baseValue);
        }
        return value;
    }

    public static double handleMainHandRandomAttribute(Player player, String attributeType) {
        double value = 0;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (mainHand.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag tag = mainHand.getOrCreateTagElement(Utils.MOD_ID);
            if (tag.contains(attributeType)) {
                double baseValue = ForgeEquipUtils.getRandomEquipBaseValue(player, mainHand, attributeType);
                value += baseValue;
                if (tag.contains("Forging"))
                    value += Compute.forgingValue(tag, baseValue);
            }
        }
        return value;
    }

    public static double handleAllEquipRandomAttribute(Player player, String attributeType) {
        double value = 0;
        value += handleMainHandRandomAttribute(player, attributeType);
        value += handleArmorRandomAttribute(player, attributeType);
        return value;
    }

    public static double dodgeRate(Player player) {
        double swift = Math.min(PlayerAttributes.extraSwiftness(player), 90);
        double rate = 0;
        if (swift <= 10) rate = swift * 0.02;
        else if (swift <= 20) rate = 0.2 + (swift - 10) * 0.015;
        else if (swift <= 30) rate = 0.35 + (swift - 20) * 0.01;
        else rate = 0.45 + (swift - 30) * 0.005;
        return rate;
    }

    public static List<ItemStack> getAllEquipSlotItems(Player player) {
        List<ItemStack> list = new ArrayList<>(List.of(player.getItemBySlot(EquipmentSlot.HEAD),
                player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS),
                player.getItemBySlot(EquipmentSlot.FEET)));
        if (Utils.mainHandTag.containsKey(player.getMainHandItem().getItem())) list.add(player.getMainHandItem());
        if (Utils.offHandTag.containsKey(player.getOffhandItem().getItem())) list.add(player.getOffhandItem());
        return list;
    }

    private static double computeAllEquipSlotXpLevelAttributeValue(Player player, Map<Item, Double> attributeMap,
                                                                   boolean computeForge) {
        int xpLevel = player.experienceLevel;
        double totalValue = 0;
        for (ItemStack equip : getAllEquipSlotItems(player)) {
            Item item = equip.getItem();
            if (attributeMap.containsKey(item)) {
                double value = 0;
                double baseValue = xpLevel * attributeMap.get(item);
                if (computeForge && equip.getTagElement(Utils.MOD_ID) != null) {
                    value += Compute.forgingValue(equip, baseValue);
                }
                totalValue += baseValue + value;
            }
        }
        return totalValue;
    }

    /**
     * 计算所有盔甲 + 主手 + 副手基础属性的总和
     * @param player 玩家
     * @param attributeMap 属性表
     * @param computeForge 是否计算强化等级
     * @return 得到的基础数值
     */
    public static double computeAllEquipSlotBaseAttributeValue(Player player, Map<Item, Double> attributeMap,
                                                                boolean computeForge) {
        double totalValue = 0;
        for (ItemStack equip : getAllEquipSlotItems(player)) {
            Item item = equip.getItem();
            double traditionalValue = ForgeEquipUtils
                    .getTraditionalEquipBaseValue(equip, attributeMap, player, computeForge);
            if ((attributeMap.containsKey(item) || traditionalValue != 0)
                    && player.experienceLevel >= Utils.levelRequire.getOrDefault(item, 0)) {
                double value = 0;
                if (computeForge && equip.getTagElement(Utils.MOD_ID) != null) {
                    value += Compute.forgingValue(equip, traditionalValue);
                }
                totalValue += traditionalValue + value;
            }
        }
        return totalValue;
    }

    public static class AttributeNames {

        public static final String ATTACK_DAMAGE = "attackDamage";
        public static final String DEFENCE = "defence";
        public static final String DEFENCE_PENETRATION0 = "defencePenetration0";
        public static final String CRIT_RATE = "critRate";
        public static final String CRIT_DAMAGE = "critDamage";
        public static final String MANA_DAMAGE = "manaDamage";
        public static final String MANA_DEFENCE = "manaDefence";
        public static final String MANA_PENETRATION0 = "manaPenetration0";
        public static final String MANA_RECOVER = "manaRecover";
        public static final String MAX_MANA = "maxMana";
        public static final String RELEASE_SPEED = "releaseSpeed";
        public static final String MOVEMENT_SPEED_COMMON = "movementSpeedCommon";
        public static final String MAX_HEALTH = "maxHealth";
        public static final String HEALTH_RECOVER = "healthRecover";
        public static final String EXP_UP = "expUp";

        public static List<String> attributeNames = new ArrayList<>();
        public static List<String> getAttributeNameList() {
            if (attributeNames.isEmpty()) {
                attributeNames.addAll(List.of(
                        ATTACK_DAMAGE, DEFENCE, DEFENCE_PENETRATION0,
                        CRIT_RATE, CRIT_DAMAGE,
                        MANA_DAMAGE, MANA_DEFENCE, MANA_PENETRATION0,
                        MANA_RECOVER, MAX_MANA, RELEASE_SPEED, MOVEMENT_SPEED_COMMON,
                        MAX_HEALTH, HEALTH_RECOVER, EXP_UP
                ));
            }
            return attributeNames;
        }

        public static Map<String, Component> descriptionMap = new HashMap<>();
        public static Component getDescription(String attributeName) {
            if (descriptionMap.isEmpty()) {
                descriptionMap.put(ATTACK_DAMAGE, Te.s("物理攻击", ChatFormatting.YELLOW));
                descriptionMap.put(DEFENCE, Te.s("护甲", ChatFormatting.GRAY));
                descriptionMap.put(DEFENCE_PENETRATION0, Te.s("护甲穿透", ChatFormatting.GRAY));
                descriptionMap.put(CRIT_RATE, Te.s("暴击几率", ChatFormatting.LIGHT_PURPLE));
                descriptionMap.put(CRIT_DAMAGE, Te.s("暴击伤害", ChatFormatting.BLUE));
                descriptionMap.put(MANA_DAMAGE, Te.s("魔法攻击", ChatFormatting.LIGHT_PURPLE));
                descriptionMap.put(MANA_DEFENCE, Te.s("魔法抗性", ChatFormatting.BLUE));
                descriptionMap.put(MANA_PENETRATION0, Te.s("魔法穿透", ChatFormatting.BLUE));
                descriptionMap.put(MANA_RECOVER, Te.s("法力回复", ChatFormatting.LIGHT_PURPLE));
                descriptionMap.put(MAX_MANA, Te.s("最大法力值", ChatFormatting.LIGHT_PURPLE));
                descriptionMap.put(RELEASE_SPEED, Te.s("技能急速", ChatFormatting.AQUA));
                descriptionMap.put(MOVEMENT_SPEED_COMMON, Te.s("移动速度", ChatFormatting.GREEN));
                descriptionMap.put(MAX_HEALTH, Te.s("最大生命值", ChatFormatting.GREEN));
                descriptionMap.put(HEALTH_RECOVER, Te.s("生命回复", ChatFormatting.GREEN));
                descriptionMap.put(EXP_UP, Te.s("经验加成", CustomStyle.styleOfLucky));
            }
            return descriptionMap.get(attributeName);
        }

        public static Map<String, Map<Item, Double>> attributeMap = new HashMap<>();
        public static Map<Item, Double> getAttributeMapByName(String attributeName) {
            if (attributeMap.isEmpty()) {
                attributeMap.put(AttributeNames.ATTACK_DAMAGE, Utils.attackDamage);
                attributeMap.put(AttributeNames.DEFENCE, Utils.defence);
                attributeMap.put(AttributeNames.DEFENCE_PENETRATION0, Utils.defencePenetration0);
                attributeMap.put(AttributeNames.CRIT_RATE, Utils.critRate);
                attributeMap.put(AttributeNames.CRIT_DAMAGE, Utils.critDamage);
                attributeMap.put(AttributeNames.MANA_DAMAGE, Utils.manaDamage);
                attributeMap.put(AttributeNames.MANA_DEFENCE, Utils.manaDefence);
                attributeMap.put(AttributeNames.MANA_PENETRATION0, Utils.manaPenetration0);
                attributeMap.put(AttributeNames.MANA_RECOVER, Utils.manaRecover);
                attributeMap.put(AttributeNames.MAX_MANA, Utils.maxMana);
                attributeMap.put(AttributeNames.RELEASE_SPEED, Utils.coolDownDecrease);
                attributeMap.put(AttributeNames.MOVEMENT_SPEED_COMMON, Utils.movementSpeedCommon);
                attributeMap.put(AttributeNames.MAX_HEALTH, Utils.maxHealth);
                attributeMap.put(AttributeNames.HEALTH_RECOVER, Utils.healthRecover);
                attributeMap.put(AttributeNames.EXP_UP, Utils.expUp);
            }
            return attributeMap.get(attributeName);
        }
    }
}
