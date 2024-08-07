package com.very.wraq.common.attributeValues;

import com.very.wraq.customized.uniform.attack.AttackCurios0;
import com.very.wraq.customized.uniform.attack.AttackCurios1;
import com.very.wraq.customized.uniform.attack.AttackCurios2;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.customized.uniform.bow.BowCurios2;
import com.very.wraq.customized.uniform.mana.ManaCurios0;
import com.very.wraq.customized.uniform.mana.ManaCurios2;
import com.very.wraq.events.mob.loot.C5LootItems;
import com.very.wraq.process.func.plan.PlanPlayer;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementBow;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSceptre;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.process.series.potion.NewPotionEffects;
import com.very.wraq.process.system.tower.TowerMob;
import com.very.wraq.projectiles.OnCuriosSlotAttributesModify;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.series.gems.GemAttributes;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.instance.Castle.CastleSword;
import com.very.wraq.series.instance.Devil.DevilAttackArmor;
import com.very.wraq.series.instance.Devil.DevilSwiftArmor;
import com.very.wraq.series.instance.Moon.Equip.*;
import com.very.wraq.series.instance.Taboo.TabooManaArmor;
import com.very.wraq.series.nether.Equip.PiglinHelmet.PiglinHelmet;
import com.very.wraq.series.newrunes.chapter2.SkyNewRune;
import com.very.wraq.series.newrunes.chapter6.CastleNewRune;
import com.very.wraq.series.overworld.chapter1.Mine.MineShield;
import com.very.wraq.series.overworld.chapter1.forest.armor.ForestArmorBoots;
import com.very.wraq.series.overworld.chapter1.forest.armor.ForestArmorChest;
import com.very.wraq.series.overworld.chapter1.forest.armor.ForestArmorHelmet;
import com.very.wraq.series.overworld.chapter1.forest.armor.ForestArmorLeggings;
import com.very.wraq.series.overworld.chapter1.forest.crest.ForestCrestAttributes;
import com.very.wraq.series.overworld.chapter1.Mine.Crest.MineCrestAttributes;
import com.very.wraq.series.newrunes.chapter1.ForestNewRune;
import com.very.wraq.series.overworld.chapter1.plain.armor.PlainArmorHelmet;
import com.very.wraq.series.overworld.chapter1.plain.crest.PlainCrestAttributes;
import com.very.wraq.series.newrunes.chapter1.PlainNewRune;
import com.very.wraq.series.overworld.chapter1.plain.sceptre.PlainSceptre4;
import com.very.wraq.series.overworld.chapter1.Snow.Crest.SnowCrestAttributes;
import com.very.wraq.series.overworld.chapter1.volcano.armor.VolcanoArmorHelmet;
import com.very.wraq.series.overworld.chapter1.volcano.crest.VolcanoCrestAttributes;
import com.very.wraq.series.overworld.chapter1.waterSystem.equip.armor.LakeArmorHelmet;
import com.very.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrestAttributes;
import com.very.wraq.series.overworld.chapter2.evoker.Crest.ManaCrestAttributes;
import com.very.wraq.series.overworld.chapter2.manaArmor.LifeMana.*;
import com.very.wraq.series.overworld.chapter2.sky.BossItems.SkyBoss;
import com.very.wraq.series.overworld.chapter2.sky.Crest.SkyCrestAttributes;
import com.very.wraq.series.overworld.chapter2.sky.SkyBow;
import com.very.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import com.very.wraq.series.worldsoul.SoulEquipAttribute;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerAttributes {

    public static double attackDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        double baseAttackDamage = player.experienceLevel;
        double exDamage = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag mainHandItemTag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            mainHandItemTag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("attackdamage"))
            baseAttackDamage += stackmainhandtag.getDouble("attackdamage");
        if (Utils.attackDamage.containsKey(boots))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.attackDamage);
        if (Utils.attackDamage.containsKey(leggings))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.attackDamage);
        if (Utils.attackDamage.containsKey(chest))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.attackDamage);
        if (Utils.attackDamage.containsKey(helmet))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.attackDamage);

        baseAttackDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.attackDamage);

        if (Utils.mainHandTag.containsKey(mainhand) && Utils.attackDamage.containsKey(mainhand))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.attackDamage);
        if (Utils.offHandTag.containsKey(offhand) && Utils.attackDamage.containsKey(offhand))
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getOffhandItem(), Utils.attackDamage);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.attackDamage.containsKey(mainhand) && mainHandItemTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(mainHandItemTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.attackDamage));

        // Fixed:防具攻击强化失效
        if (Utils.armorTag.containsKey(helmet) && Utils.attackDamage.containsKey(helmet) && helmetTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.attackDamage));
        if (Utils.armorTag.containsKey(chest) && Utils.attackDamage.containsKey(chest) && chestTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.attackDamage));
        if (Utils.armorTag.containsKey(leggings) && Utils.attackDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.attackDamage));
        if (Utils.armorTag.containsKey(boots) && Utils.attackDamage.containsKey(boots) && bootsTag.contains("Forging"))
            baseAttackDamage += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.attackDamage));

        if (Utils.mainHandTag.containsKey(mainhand) && (Utils.attackDamage.containsKey(mainhand) || mainHandItemTag.contains(StringUtils.RandomAttribute.attackDamage)) && mainHandItemTag.contains(StringUtils.KillCount.KillCount)) {
            int killCount = mainHandItemTag.getInt(StringUtils.KillCount.KillCount);
            if (killCount >= 100000) killCount = 100000;
            baseAttackDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.attackDamage) * 0.5 * (killCount / 100000.0);
            baseAttackDamage += ForgeEquipUtils.getRandomEquipBaseValue(player.getMainHandItem(), StringUtils.RandomAttribute.attackDamage) * 0.5 * (killCount / 100000.0);
        }
        if (helmetTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(helmetTag);
        if (chestTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(chestTag);
        if (leggingsTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(leggingsTag);
        if (bootsTag.contains("newGems1")) exDamage += GemAttributes.gemsAttackDamage(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand)) exDamage += GemAttributes.gemsAttackDamage(stackmainhandtag);
        if (Compute.ArmorCount.Volcano(player) >= 2) exDamage += baseAttackDamage * 0.15F;
        if (Compute.ArmorCount.ObsiMana(player) >= 4) exDamage += baseAttackDamage * 0.15F;
        if (data.contains("Sword")) exDamage += baseAttackDamage * (data.getInt("Sword") / 1000000.0d);
        if (data.contains("Barker")) exDamage += baseAttackDamage * ((data.getInt("Barker") / 100000.0d) * 0.05);
        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) exDamage += baseAttackDamage * 0.1;
        if (data.contains("ManaSwordActive") && data.getInt("ManaSwordActive") > 0)
            exDamage += data.getInt("ManaSwordActive");

        if (Compute.ArmorCount.Sky(player) > 0) {
            if (player.getHealth() / player.getMaxHealth() > 0.8)
                exDamage += baseAttackDamage * 1 * Compute.SkySuitEffectRate(player);
            else if (player.getHealth() / player.getMaxHealth() > 0.4)
                exDamage += baseAttackDamage * 0.4 * Compute.SkySuitEffectRate(player);
        }
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 0)
            exDamage += baseAttackDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 1)
            exDamage += baseAttackDamage * 0.40 + 40;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill0) && data.getInt(StringUtils.VolcanoSwordSkill.Skill0) > TickCount)
            exDamage += 10;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill1) && data.getInt(StringUtils.VolcanoSwordSkill.Skill1) > TickCount)
            exDamage += 15;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill2) && data.getInt(StringUtils.VolcanoSwordSkill.Skill2) > TickCount)
            exDamage += 20;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill3) && data.getInt(StringUtils.VolcanoSwordSkill.Skill3) > TickCount)
            exDamage += 30;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill4) && data.getInt(StringUtils.VolcanoSwordSkill.Skill4) > TickCount)
            exDamage += 75;
        if (mainhand.equals(ModItems.GoldSword0.get())) exDamage += data.getDouble("VB") / 10000.0;

        if (Compute.SwordSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.SwordSkillNum.Skill2) && data.getInt(StringUtils.SwordSkillNum.Skill2) > TickCount) {
            exDamage += baseAttackDamage * Compute.SwordSkillLevelGet(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）‘

        if (Compute.BowSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.BowSkillNum.Skill2) && data.getInt(StringUtils.BowSkillNum.Skill2) > TickCount) {
            exDamage += baseAttackDamage * Compute.BowSkillLevelGet(data, 2) * 0.02;
        } // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (Compute.SwordSkillLevelGet(data, 5) > 0 && data.contains(StringUtils.SwordSkillNum.Skill5) && data.getInt(StringUtils.SwordSkillNum.Skill5) > TickCount) {
            exDamage += baseAttackDamage * Compute.SwordSkillLevelGet(data, 5) * 0.015;
        } // 剑术-狂暴（造成暴击后，提升1%攻击力，持续3s）

        if (Compute.BowSkillLevelGet(data, 5) > 0 && data.contains(StringUtils.BowSkillNum.Skill5) && data.getInt(StringUtils.BowSkillNum.Skill5) > TickCount) {
            exDamage += baseAttackDamage * Compute.BowSkillLevelGet(data, 5) * 0.01;
        } // 弓术-狂暴（造成暴击后，提升1%攻击力，持续5s）

        String name = player.getName().getString();
        if (Compute.SwordSkillLevelGet(data, 6) > 0 && Utils.SwordSkill6Map.containsKey(name) && Utils.SwordSkill6Map.get(name).getTime() > TickCount) {
            exDamage += baseAttackDamage * Compute.SwordSkillLevelGet(data, 6) * 0.003 * Utils.SwordSkill6Map.get(name).getCount();
        } // 完美（持续造成暴击，将提供至多30%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）

        if (Compute.BowSkillLevelGet(data, 6) > 0 && Utils.BowSkill6Map.containsKey(name) && Utils.BowSkill6Map.get(name).getTime() > TickCount) {
            exDamage += baseAttackDamage * Compute.BowSkillLevelGet(data, 6) * 0.005 * Utils.BowSkill6Map.get(name).getCount();
        } // 完美（持续的命中目标的箭矢，将提供至多3%攻击力，持续10s，在十次命中后达最大值，在未命中时重置层数）

        if (Compute.BowSkillLevelGet(data, 8) > 0 && Utils.bowTag.containsKey(mainhand)) {
            exDamage += baseAttackDamage * Compute.BowSkillLevelGet(data, 8) * 0.02;
        } // 锻弦-力量（手持弓时，获得2%攻击力）

        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            exDamage += Point;
        } // 能力

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDamage += 50;
        // 矿工裤被动

        if (Compute.ArmorCount.Mine(player) >= 4) exDamage += baseAttackDamage * 0.3;

        if (data.getInt(StringUtils.Crest.Volcano.Crest0) > 0)
            exDamage += VolcanoCrestAttributes.ExAttackDamage[0] * data.getInt(StringUtils.Crest.Volcano.Crest0);
        if (data.getInt(StringUtils.Crest.Volcano.Crest1) > 0)
            exDamage += VolcanoCrestAttributes.ExAttackDamage[1] * data.getInt(StringUtils.Crest.Volcano.Crest1);
        if (data.getInt(StringUtils.Crest.Volcano.Crest2) > 0)
            exDamage += VolcanoCrestAttributes.ExAttackDamage[2] * data.getInt(StringUtils.Crest.Volcano.Crest2);
        if (data.getInt(StringUtils.Crest.Volcano.Crest3) > 0)
            exDamage += VolcanoCrestAttributes.ExAttackDamage[3] * data.getInt(StringUtils.Crest.Volcano.Crest3);
        if (data.getInt(StringUtils.Crest.Volcano.Crest4) > 0)
            exDamage += VolcanoCrestAttributes.ExAttackDamage[4] * data.getInt(StringUtils.Crest.Volcano.Crest4);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            exDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.AttackDamage;

        if (data.contains("GemSAttack")) exDamage += data.getDouble("GemSAttack");

        exDamage += Compute.SArmorAttackDamage(player);
        if (Utils.playerAttackRingMap.containsKey(name))
            exDamage += Utils.playerAttackRingMap.get(name);

        if (Utils.PlayerSpringRingAttackAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringRingAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringHandAttackAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringHandAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringBeltAttackAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringBeltAttackAttribute.get(player);
        }

        if (Utils.PlayerFireWorkGunEffect.containsKey(player) && Utils.PlayerFireWorkGunEffect.get(player) > TickCount) {
            exDamage += baseAttackDamage * 0.1;
        } //新春礼炮

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
        exDamage += DevilSwiftArmor.DevilSwiftArmorPassiveExDamage(player);

        exDamage += baseAttackDamage * EarthPower.PlayerDamageEnhance(player);

        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.attackDamage, StringUtils.CuriosAttribute.AttackDamage); // 新版饰品属性加成

        exDamage += Compute.PassiveEquip.AttributeGet(player, Utils.attackDamage); // 器灵属性加成
        exDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExAttackDamage);
        exDamage += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExAttackDamage);
        exDamage += MoonSword.ExAttackDamage(player);
        exDamage += MoonSceptre.ExManaDamage(player);
        exDamage += LifeElementSword.ExAttackDamage(player);
        exDamage += LifeElementBow.ExAttackDamage(player);
        exDamage += VolcanoArmorHelmet.exAttackDamage(player);
        exDamage += CastleNewRune.attackDamage(player);
        exDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerAttackDamageModifier);
        exDamage += OnCuriosSlotAttributesModify.getAttributes(player, OnCuriosSlotAttributesModify.exAttackDamage);
        // 请在上方添加

        double totalAttackDamage = baseAttackDamage + exDamage;

        totalAttackDamage *= (1 + MoonShield.damageEnhance(player));
        totalAttackDamage *= (1 + MoonKnife.damageEnhance(player));
        totalAttackDamage *= Compute.playerFantasyAttributeEnhance(player);
        totalAttackDamage *= AttackCurios1.playerAttackDamageEnhance(player);
        totalAttackDamage *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentAttackDamageEnhance));
        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0)
            return totalAttackDamage * 0.5f;
        return totalAttackDamage;
    }

    public static double critRate(Player player) {
        CompoundTag data = player.getPersistentData();
        double critRate = 0.0d;
        if (player.getMainHandItem().is(C5LootItems.pillagerBow.get())) {
            CompoundTag tempData = player.getMainHandItem().getOrCreateTagElement(Utils.MOD_ID);
            if (tempData.getDouble(StringUtils.RandomAttribute.critRate) > 1)
                tempData.putDouble(StringUtils.RandomAttribute.critRate, 0.5);
        }

        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        critRate += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.critRate);

        if (helmetTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(helmetTag);
        if (chestTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(chestTag);
        if (leggingsTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(leggingsTag);
        if (bootsTag.contains("newGems1")) critRate += GemAttributes.gemsCritRate(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            critRate += GemAttributes.gemsCritRate(stackmainhandtag);
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticalrate"))
            critRate += stackmainhandtag.getDouble("criticalrate");

        if (Utils.critRate.containsKey(boots)) critRate += Utils.critRate.get(boots);
        if (Utils.critRate.containsKey(leggings)) critRate += Utils.critRate.get(leggings);
        if (Utils.critRate.containsKey(chest)) critRate += Utils.critRate.get(chest);
        if (Utils.critRate.containsKey(helmet)) critRate += Utils.critRate.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.critRate.containsKey(mainhand))
            critRate += Utils.critRate.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.critRate.containsKey(offhand))
            critRate += Utils.critRate.get(offhand);

        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 0)
            critRate += 0.2;
        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 1)
            critRate += 0.4;
        if (data.contains("GemSCritRate")) critRate += data.getDouble("GemSCritRate");
        critRate += Compute.SArmorCritRate(player);
        if (Compute.BowSkillLevelGet(data, 11) > 0 && Utils.bowTag.containsKey(mainhand)) {
            critRate += Compute.BowSkillLevelGet(data, 11) * 0.02;
        } // 锻弦-平衡（手持弓时，获得额外2%暴击几率）

        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            critRate += Point * 0.001;
        }

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            critRate +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritRate;

        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.sceptreTag.containsKey(mainhand)) {
            double manaRecoverValue = 0.05 + manaRecover(player) * 0.01 * Compute.ManaSkillLevelGet(data, 10) * 0.1;
            critRate += 1 - (1 / (1 + manaRecoverValue));
        } // 法术专精-力凝魔核

        critRate += Compute.CuriosAttribute.attributeValue(player, Utils.critRate, StringUtils.CuriosAttribute.CritRate); // 新版饰品属性加成

        critRate += AttackCurios2.playerCritRateUp(player);
        critRate += BowCurios2.playerCritRateUp(player);
        critRate += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCritRateModifier);
        // 请在上方添加
        critRate *= Compute.playerFantasyAttributeEnhance(player);

        return critRate;
    }

    public static double critDamage(Player player) {
        int tickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double critDamage = 1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        critDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.critDamage);

        if (helmetTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(helmetTag);
        if (chestTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(chestTag);
        if (leggingsTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(leggingsTag);
        if (bootsTag.contains("newGems1")) critDamage += GemAttributes.gemsCritDamage(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            critDamage += GemAttributes.gemsCritDamage(stackmainhandtag);
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticaldamage"))
            critDamage += stackmainhandtag.getDouble("criticaldamage");
        if (Utils.critDamage.containsKey(boots)) critDamage += Utils.critDamage.get(boots);
        if (Utils.critDamage.containsKey(leggings)) critDamage += Utils.critDamage.get(leggings);
        if (Utils.critDamage.containsKey(chest)) critDamage += Utils.critDamage.get(chest);
        if (Utils.critDamage.containsKey(helmet)) critDamage += Utils.critDamage.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.critDamage.containsKey(mainhand))
            critDamage += Utils.critDamage.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.critDamage.containsKey(offhand))
            critDamage += Utils.critDamage.get(offhand);
        if (data.contains("Bow")) critDamage += (data.getInt("Bow") / 30000.0d) * 0.3F;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill0) && data.getInt(StringUtils.VolcanoSwordSkill.Skill0) > tickCount)
            critDamage += 0.5;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill1) && data.getInt(StringUtils.VolcanoSwordSkill.Skill1) > tickCount)
            critDamage += 0.6;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill2) && data.getInt(StringUtils.VolcanoSwordSkill.Skill2) > tickCount)
            critDamage += 0.7;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill3) && data.getInt(StringUtils.VolcanoSwordSkill.Skill3) > tickCount)
            critDamage += 0.9;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill4) && data.getInt(StringUtils.VolcanoSwordSkill.Skill4) > tickCount)
            critDamage += 1.2;
        if (data.contains("plaingems") && data.getBoolean("plaingems")) critDamage += 0.1;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) critDamage += 0.1;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) critDamage += 0.1;
        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) critDamage += 0.1;

        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 0)
            critDamage += 0.4;
        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 1)
            critDamage += 0.8;
        if (data.contains("GemSCritDamage")) critDamage += data.getDouble("GemSCritDamage");
        critDamage += Compute.SArmorCritDamage(player);

        if (Compute.BowSkillLevelGet(data, 7) > 0 && Utils.bowTag.containsKey(mainhand)) {
            critDamage += Compute.BowSkillLevelGet(data, 7) * 0.1;
        } // 锻矢-锋利（手持弓时，获得10%暴击伤害）

        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            critDamage += Point * 0.01;
        } // 能力

        if (Compute.ArmorCount.Volcano(player) >= 4) critDamage += 0.35;

        if (data.getInt(StringUtils.Crest.Sky.Crest0) > 0)
            critDamage += SkyCrestAttributes.CritDamage[0] * data.getInt(StringUtils.Crest.Sky.Crest0);
        if (data.getInt(StringUtils.Crest.Sky.Crest1) > 0)
            critDamage += SkyCrestAttributes.CritDamage[1] * data.getInt(StringUtils.Crest.Sky.Crest1);
        if (data.getInt(StringUtils.Crest.Sky.Crest2) > 0)
            critDamage += SkyCrestAttributes.CritDamage[2] * data.getInt(StringUtils.Crest.Sky.Crest2);
        if (data.getInt(StringUtils.Crest.Sky.Crest3) > 0)
            critDamage += SkyCrestAttributes.CritDamage[3] * data.getInt(StringUtils.Crest.Sky.Crest3);
        if (data.getInt(StringUtils.Crest.Sky.Crest4) > 0)
            critDamage += SkyCrestAttributes.CritDamage[4] * data.getInt(StringUtils.Crest.Sky.Crest4);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            critDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritDamage;

        if (Utils.IceSwordEffectMap.containsKey(player) && Utils.IceSwordEffectMap.get(player) > tickCount) {
            critDamage += Utils.IceSwordEffectNumMap.get(player) / 1200;
        } //冰霜剑

        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.sceptreTag.containsKey(mainhand)) {
            critDamage += maxManaUp(player) * 0.003 * Compute.ManaSkillLevelGet(data, 10) * 0.1;
        } // 法术专精-力凝魔核

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                critDamage += data.getDouble("HealthStealAfterCompute") * 5;
            }
        } // 封魔者法盾

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.manaKnife.get())) {
            critDamage += data.getDouble("HealthStealAfterCompute") * 3;
        } // 猎魔者小刀

        critDamage += Compute.CuriosAttribute.attributeValue(player, Utils.critDamage, StringUtils.CuriosAttribute.CritDamage); // 新版饰品属性加成

        critDamage += Compute.PassiveEquip.AttributeGet(player, Utils.critDamage); // 器灵属性加成
        critDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExCritDamage);
        critDamage += AttackCurios1.playerCritDamageUp(player);
        critDamage += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCritDamageModifier);
        // 请在上方添加

        critDamage *= Compute.playerFantasyAttributeEnhance(player);
        critDamage *= AttackCurios0.PlayerFinalCritDamageEnhance(player);
        critDamage *= AttackCurios2.playerCritDamageEnhance(player);
        critDamage *= BowCurios2.playerCritDamageEnhance(player);
        critDamage *= SkyNewRune.critDamageInfluence(player);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                data.putDouble("CritDamageAfterCompute", critDamage);
                return 0;
            }
        } // 封魔者法盾

        return critDamage;
    }

    public static double movementSpeedCommon(Player player) {
        double movementSpeedUp = 0;
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        int tick = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        Inventory inventory = player.getInventory();
        for (ItemStack itemStack : inventory.armor) {
            if (Utils.movementSpeedCommon.containsKey(itemStack.getItem()))
                movementSpeedUp += Utils.movementSpeedCommon.get(itemStack.getItem());
        }
        ItemStack mainHandStack = player.getMainHandItem();
        Item mainHandItem = mainHandStack.getItem();
        CompoundTag mainHandTag = mainHandStack.getTagElement(Utils.MOD_ID);
        ItemStack offHandStack = player.getOffhandItem();
        Item offHandItem = offHandStack.getItem();
        if (Utils.movementSpeedCommon.containsKey(mainHandItem) && Utils.mainHandTag.containsKey(mainHandItem))
            movementSpeedUp += Utils.movementSpeedCommon.get(mainHandItem);
        if (Utils.movementSpeedCommon.containsKey(offHandItem) && Utils.offHandTag.containsKey(offHandItem))
            movementSpeedUp += Utils.movementSpeedCommon.get(offHandItem);

        movementSpeedUp += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerMovementSpeedModifier);
        movementSpeedUp += ChangedAttributesModifier.getModifierValue(player, ChangedAttributesModifier.movementSpeedUp);

        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > tick)
            movementSpeedUp += 0.6;
        // 妖刀-樱

        if (Compute.ArmorCount.Lake(player) >= 4) movementSpeedUp += 0.35;
        if (Compute.ArmorCount.Mine(player) >= 4) movementSpeedUp -= 0.5;

        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 0)
            movementSpeedUp += 0.3;
        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 1)
            movementSpeedUp += 0.6;

        if (Compute.ArmorCount.Sky(player) > 0 && player.getHealth() / player.getMaxHealth() > 0.8)
            movementSpeedUp += 0.4 * Compute.SkySuitEffectRate(player);

        int flexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            int Point = flexibilityAbilityPoint + (flexibilityAbilityPoint / 10) * 5;
            movementSpeedUp += Point * 0.003;
        } // 能力

        if (Compute.SwordSkillLevelGet(data, 9) > 0 && Utils.swordTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.SwordSkillLevelGet(data, 9) * 0.05;
        } // 剑舞（手持近战武器时，获得5%额外移动速度）

        if (Compute.BowSkillLevelGet(data, 1) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.BowSkillLevelGet(data, 1) * 0.03;
        } // 原野护符（持有弓时，获得3%的额外移动速度）

        if (Compute.BowSkillLevelGet(data, 9) > 0 && Utils.bowTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.BowSkillLevelGet(data, 9) * 0.08;
        } // 猎手本能（手持弓时，获得8%额外移动速度）

        if (Compute.ManaSkillLevelGet(data, 9) > 0 && Utils.sceptreTag.containsKey(mainHandItem)) {
            movementSpeedUp += Compute.ManaSkillLevelGet(data, 9) * 0.05;
        } // 法师（手持法杖时，获得5%额外移动速度）

        if (mainHandTag != null && mainHandTag.contains(StringUtils.ManaCore.ManaCore)
                && mainHandTag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            movementSpeedUp += 0.3;
        }

        if (WraqCurios.isOn(SkyNewRune.class, player)) movementSpeedUp += 0.4;

        movementSpeedUp += Compute.CuriosAttribute.attributeValue(player, Utils.movementSpeedCommon,
                StringUtils.CuriosAttribute.commonMovementSpeed); // 新版饰品属性加成

        movementSpeedUp += GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.movementSpeedCommon);

        // 上方添加

        movementSpeedUp *= Compute.playerFantasyAttributeEnhance(player);

        return movementSpeedUp;
    }

    public static double movementSpeedCurrent(Player player) {
        double movementSpeed = movementSpeedCommon(player);
        if (!Compute.playerIsInBattle(player)) movementSpeed += movementSpeedWithoutBattle(player);
        return movementSpeed;
    }

    public static double movementSpeedWithoutBattle(Player player) {
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        CompoundTag data = player.getPersistentData();

        double speedUp = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("speedup"))
            speedUp += stackmainhandtag.getDouble("speedup");

        if (Utils.movementSpeedWithoutBattle.containsKey(boots)) speedUp += Utils.movementSpeedWithoutBattle.get(boots);
        if (Utils.movementSpeedWithoutBattle.containsKey(leggings)) speedUp += Utils.movementSpeedWithoutBattle.get(leggings);
        if (Utils.movementSpeedWithoutBattle.containsKey(chest)) speedUp += Utils.movementSpeedWithoutBattle.get(chest);
        if (Utils.movementSpeedWithoutBattle.containsKey(helmet)) speedUp += Utils.movementSpeedWithoutBattle.get(helmet);

        if (Utils.mainHandTag.containsKey(mainhand) && Utils.movementSpeedWithoutBattle.containsKey(mainhand))
            speedUp += Utils.movementSpeedWithoutBattle.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.movementSpeedWithoutBattle.containsKey(offhand))
            speedUp += Utils.movementSpeedWithoutBattle.get(offhand);

        speedUp += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.movementSpeed);

        if (helmetTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(helmetTag);
        if (chestTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(chestTag);
        if (leggingsTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(leggingsTag);
        if (bootsTag.contains("newGems1")) speedUp += GemAttributes.gemsMovementSpeedUp(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            speedUp += GemAttributes.gemsMovementSpeedUp(stackmainhandtag);

        if (data.contains("GemSSpeed")) speedUp += data.getDouble("GemSSpeed");
        if (mainhand.equals(ModItems.GoldSword0.get())) speedUp += data.getDouble("VB") / 1000000.0;

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            speedUp += 0.4;
        // 矿工裤被动

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge)) speedUp +=
                stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MovementSpeed;

        if (Utils.PlayerSpringBeltMovementAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            speedUp += Utils.PlayerSpringBeltMovementAttribute.get(player);
        }

        speedUp += Compute.CuriosAttribute.attributeValue(player, Utils.movementSpeedWithoutBattle,
                StringUtils.CuriosAttribute.MovementSpeed); // 新版饰品属性加成
        speedUp += Compute.PassiveEquip.AttributeGet(player, Utils.movementSpeedWithoutBattle); // 器灵属性加成

        // 请在上方添加
        speedUp *= Compute.playerFantasyAttributeEnhance(player);

        return speedUp;
    }

    public static double expUp(Player player) {
        CompoundTag data = player.getPersistentData();
        double expUp = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (Utils.expUp.containsKey(boots)) expUp += Utils.expUp.get(boots);
        if (Utils.expUp.containsKey(leggings)) expUp += Utils.expUp.get(leggings);
        if (Utils.expUp.containsKey(chest)) expUp += Utils.expUp.get(chest);
        if (Utils.expUp.containsKey(helmet)) expUp += Utils.expUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.expUp.containsKey(mainhand))
            expUp += Utils.expUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.expUp.containsKey(offhand))
            expUp += Utils.expUp.get(offhand);
        if (data.contains("plaingems") && data.getBoolean("plaingems")) expUp += 0.1;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) expUp += 0.1;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) expUp += 0.1;
        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) expUp += 0.1;

        if (data.contains("GemSExpImprove")) expUp += data.getDouble("GemSExpImprove");
        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            expUp += Point * 0.01;
        }

        if (Utils.PlayerSpringRingExpUpAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringRingExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringHandExpUpAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringHandExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringBeltExpUpAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringBeltExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringBraceletExpUpAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            expUp += Utils.PlayerSpringBraceletExpUpAttribute.get(player);
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(helmetTag);
        if (chestTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(chestTag);
        if (leggingsTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(leggingsTag);
        if (bootsTag.contains("newGems1")) expUp += GemAttributes.gemsExpUp(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            expUp += GemAttributes.gemsExpUp(stackmainhandtag);

        expUp += Compute.CuriosAttribute.attributeValue(player, Utils.expUp, StringUtils.CuriosAttribute.ExpUp); // 新版饰品属性加成

        expUp += Compute.PassiveEquip.AttributeGet(player, Utils.expUp); // 器灵属性加成

        int tier = 0;
        try {
            tier = PlanPlayer.getPlayerTier(player);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        expUp += new double[]{0, 1, 2, 3}[tier];

        // 请在上方添加
        expUp *= Compute.playerFantasyAttributeEnhance(player);

        return expUp;
    }

    public static double defence(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double baseDefence = player.experienceLevel;
        double exDefence = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.defence.containsKey(boots))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.defence);
        if (Utils.defence.containsKey(leggings))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.defence);
        if (Utils.defence.containsKey(chest))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.defence);
        if (Utils.defence.containsKey(helmet))
            baseDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.defence);

        baseDefence += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.defence);

        if (helmetTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(helmetTag);
        if (chestTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(chestTag);
        if (leggingsTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(leggingsTag);
        if (bootsTag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(bootsTag);
        if (stackmainhandtag.contains("newGems1")) exDefence += GemAttributes.gemsDefence(stackmainhandtag);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.defence.containsKey(mainhand))
            exDefence += Utils.defence.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.defence.containsKey(offhand))
            baseDefence += Utils.defence.get(offhand);
        if (Utils.defence.containsKey(helmet) && helmetTag.contains("Forging"))
            baseDefence += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.defence));
        if (Utils.defence.containsKey(chest) && chestTag.contains("Forging"))
            baseDefence += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.defence));
        if (Utils.defence.containsKey(leggings) && leggingsTag.contains("Forging"))
            baseDefence += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.defence));
        if (Utils.defence.containsKey(boots) && bootsTag.contains("Forging"))
            baseDefence += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.defence));

        if (Compute.ArmorCount.Forest(player) >= 2) exDefence += baseDefence * 0.25;
        if (Compute.ArmorCount.LifeMana(player) >= 4) exDefence += baseDefence * 0.25;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) exDefence += baseDefence * 0.1;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0)
            exDefence += baseDefence * 0.25 + 80;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1)
            exDefence += baseDefence * 0.40 + 160;
        if (data.contains("GemSDefence")) exDefence += data.getDouble("GemSDefence");
        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDefence += 100;
        // 矿工裤被动
        if (Compute.ArmorCount.Mine(player) >= 4) exDefence += 250;

        if (helmet instanceof PiglinHelmet) {
            PiglinHelmet piglinHelmet = (PiglinHelmet) helmet;
            int Rate = (1 + piglinHelmet.tier) * 5;
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.getPosition(1), 10, 10, 10));
            exDefence += Math.min(200, Rate * mobList.size());
        }

        if (data.getInt(StringUtils.Crest.Mine.Crest0) > 0)
            exDefence += MineCrestAttributes.ExDefence[0] * data.getInt(StringUtils.Crest.Mine.Crest0);
        if (data.getInt(StringUtils.Crest.Mine.Crest1) > 0)
            exDefence += MineCrestAttributes.ExDefence[1] * data.getInt(StringUtils.Crest.Mine.Crest1);
        if (data.getInt(StringUtils.Crest.Mine.Crest2) > 0)
            exDefence += MineCrestAttributes.ExDefence[2] * data.getInt(StringUtils.Crest.Mine.Crest2);
        if (data.getInt(StringUtils.Crest.Mine.Crest3) > 0)
            exDefence += MineCrestAttributes.ExDefence[3] * data.getInt(StringUtils.Crest.Mine.Crest3);
        if (data.getInt(StringUtils.Crest.Mine.Crest4) > 0)
            exDefence += MineCrestAttributes.ExDefence[4] * data.getInt(StringUtils.Crest.Mine.Crest4);

        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) exDefence += 40;

        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = data.getInt(StringUtils.Ability.Power) + (data.getInt(StringUtils.Ability.Power) / 10) * 5;
            exDefence += Point * 6;
        } // 能力

        String name = player.getName().getString();
        if (Utils.playerDefenceRingMap.containsKey(name))
            exDefence += Utils.playerDefenceRingMap.get(name);

        if (Utils.MineShieldEffect.containsKey(player) && Utils.MineShieldEffect.get(player) > TickCount) {
            exDefence += player.experienceLevel * 5;
        }

        if (Utils.SakuraBowEffectMap.containsKey(player) && Utils.SakuraBowEffectMap.get(player) > TickCount) {
            exDefence += 100;
        } // 妖弓-樱

        if (Utils.PlayerSpringHandDefenceAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            exDefence += Utils.PlayerSpringHandDefenceAttribute.get(player);
        }

        if (Utils.MeteoriteDefenceMap.containsKey(player) && Utils.MeteoriteDefenceTimeMap.get(player) > TickCount) {
            exDefence += Utils.MeteoriteDefenceMap.get(player);
        }

        if (Utils.SnowShieldPlayerEffectTickMap.containsKey(player) && Utils.SnowShieldPlayerEffectTickMap.get(player) > TickCount) {
            exDefence += Utils.SnowShieldPlayerEffectMap.get(player);
        } // 玉山圆盾

        exDefence += Compute.CuriosAttribute.attributeValue(player, Utils.defence, StringUtils.CuriosAttribute.Defence); // 新版饰品属性加成

        exDefence += Compute.PassiveEquip.AttributeGet(player, Utils.defence); // 器灵属性加成

        exDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExDefence);
        exDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExDefence);
        exDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExDefence);
        exDefence += ForestArmorHelmet.exDefence(player);
        // 请在上方添加
        double totalDefence = 0;
        totalDefence = baseDefence + exDefence;
        totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
        totalDefence *= Compute.playerFantasyAttributeEnhance(player);
        totalDefence *= MineShield.defenceEnhance(player);
        totalDefence *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentDefenceEnhance));
        if (data.contains("ManaRune") && data.getInt("ManaRune") == 3) return (baseDefence + exDefence) * 0.5f;

        if (totalDefence < 0) return 0;
        return totalDefence;
    }

    public static double healEffectUp(Player player) {
        int TickCount = player.getServer().getTickCount();
        double HealEffectUp = 1;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healeffectup"))
            HealEffectUp += stackmainhandtag.getDouble("healeffectup");
        if (Utils.healEffectUp.containsKey(boots)) HealEffectUp += Utils.healEffectUp.get(boots);
        if (Utils.healEffectUp.containsKey(leggings)) HealEffectUp += Utils.healEffectUp.get(leggings);
        if (Utils.healEffectUp.containsKey(chest)) HealEffectUp += Utils.healEffectUp.get(chest);
        if (Utils.healEffectUp.containsKey(helmet)) HealEffectUp += Utils.healEffectUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.healEffectUp.containsKey(mainhand))
            HealEffectUp += Utils.healEffectUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.healEffectUp.containsKey(offhand))
            HealEffectUp += Utils.healEffectUp.get(offhand);
        if (Compute.ArmorCount.Forest(player) >= 4) HealEffectUp += 0.5f;
        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            HealEffectUp += Point * 0.01;
        }
        if (data.getInt(StringUtils.MineMonsterEffect) >= TickCount) HealEffectUp -= 0.8;

        if (helmetTag.contains("newGems1")) HealEffectUp += GemAttributes.gemsHealEffectUp(helmetTag);
        if (chestTag.contains("newGems1")) HealEffectUp += GemAttributes.gemsHealEffectUp(chestTag);
        if (leggingsTag.contains("newGems1")) HealEffectUp += GemAttributes.gemsHealEffectUp(leggingsTag);
        if (bootsTag.contains("newGems1")) HealEffectUp += GemAttributes.gemsHealEffectUp(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            HealEffectUp += GemAttributes.gemsHealEffectUp(stackmainhandtag);

        HealEffectUp += Compute.CuriosAttribute.attributeValue(player, Utils.healEffectUp, StringUtils.CuriosAttribute.HealEffectUp); // 新版饰品属性加成
        return HealEffectUp;
    }

    public static double extraSwiftness(Player player) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double SwiftnessUp = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("Swiftness"))
            SwiftnessUp += stackmainhandtag.getDouble("Swiftness");
        if (Utils.swiftnessUp.containsKey(boots)) SwiftnessUp += Utils.swiftnessUp.get(boots);
        if (Utils.swiftnessUp.containsKey(leggings)) SwiftnessUp += Utils.swiftnessUp.get(leggings);
        if (Utils.swiftnessUp.containsKey(chest)) SwiftnessUp += Utils.swiftnessUp.get(chest);
        if (Utils.swiftnessUp.containsKey(helmet)) SwiftnessUp += Utils.swiftnessUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.swiftnessUp.containsKey(mainhand))
            SwiftnessUp += Utils.swiftnessUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.swiftnessUp.containsKey(offhand))
            SwiftnessUp += Utils.swiftnessUp.get(offhand);

        int FlexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            int Point = FlexibilityAbilityPoint + (FlexibilityAbilityPoint / 10) * 5;
            SwiftnessUp += Point * 0.1;
        } // 能力

        if (Utils.PlayerSpringBeltSwiftAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            SwiftnessUp += Utils.PlayerSpringBeltSwiftAttribute.get(player);
        }

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (BowSkill > Math.max(SwordSkill, ManaSkill)) SwiftnessUp += Utils.SpringScaleEffect.get(player) + 1;
        } //年兽鳞片

        SwiftnessUp += Compute.CuriosAttribute.attributeValue(player, Utils.swiftnessUp, StringUtils.CuriosAttribute.SwiftnessUp); // 新版饰品属性加成

        SwiftnessUp += Compute.PassiveEquip.AttributeGet(player, Utils.swiftnessUp); // 器灵属性加成
        SwiftnessUp += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExSwiftnessUp);
        // 请在上方添加
        SwiftnessUp *= Compute.playerFantasyAttributeEnhance(player);
        SwiftnessUp *= BowCurios0.SwiftnessUp(player);

        return SwiftnessUp;
    }

    public static double attackRangeUp(Player player) {
        CompoundTag data = player.getPersistentData();
        double RangeUp = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("AttackRangeUp"))
            RangeUp += stackmainhandtag.getDouble("AttackRangeUp");
        if (Utils.attackRangeUp.containsKey(boots)) RangeUp += Utils.attackRangeUp.get(boots);
        if (Utils.attackRangeUp.containsKey(leggings)) RangeUp += Utils.attackRangeUp.get(leggings);
        if (Utils.attackRangeUp.containsKey(chest)) RangeUp += Utils.attackRangeUp.get(chest);
        if (Utils.attackRangeUp.containsKey(helmet)) RangeUp += Utils.attackRangeUp.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.attackRangeUp.containsKey(mainhand))
            RangeUp += Utils.attackRangeUp.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.attackRangeUp.containsKey(offhand))
            RangeUp += Utils.attackRangeUp.get(offhand);
        if (Compute.SwordSkillLevelGet(data, 11) > 0 && Utils.swordTag.containsKey(mainhand))
            RangeUp += Compute.SwordSkillLevelGet(data, 11) * 0.2;

        // 请在上方添加
        RangeUp *= Compute.playerFantasyAttributeEnhance(player);

        return RangeUp;
    }

    public static double powerReleaseSpeed(Player player) {
        if (TowerMob.playerIsChallenging2Floor(player)) return 0;
        if (player.isCreative()) return 100;
        CompoundTag data = player.getPersistentData();
        double releaseSpeed = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (helmetTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(helmetTag);
        if (chestTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(chestTag);
        if (leggingsTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(leggingsTag);
        if (bootsTag.contains("newGems1")) releaseSpeed += GemAttributes.gemsCoolDown(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            releaseSpeed += GemAttributes.gemsCoolDown(stackmainhandtag);
        if (Utils.coolDownDecrease.containsKey(boots)) releaseSpeed += Utils.coolDownDecrease.get(boots);
        if (Utils.coolDownDecrease.containsKey(leggings))
            releaseSpeed += Utils.coolDownDecrease.get(leggings);
        if (Utils.coolDownDecrease.containsKey(chest)) releaseSpeed += Utils.coolDownDecrease.get(chest);
        if (Utils.coolDownDecrease.containsKey(helmet)) releaseSpeed += Utils.coolDownDecrease.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.coolDownDecrease.containsKey(mainhand))
            releaseSpeed += Utils.coolDownDecrease.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.coolDownDecrease.containsKey(offhand))
            releaseSpeed += Utils.coolDownDecrease.get(offhand);
        if (Compute.ArmorCount.Lake(player) >= 2) releaseSpeed += 0.2;
        if (Compute.ArmorCount.ObsiMana(player) >= 4) releaseSpeed += 0.2;
        if (player.getPersistentData().contains("Blue") && player.getPersistentData().getInt("Blue") == 0)
            releaseSpeed += player.getAttribute(Attributes.MOVEMENT_SPEED).getValue() - 0.1F;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) releaseSpeed += 0.1;

        releaseSpeed += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.coolDown);

        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 0)
            releaseSpeed += 0.35;
        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 1)
            releaseSpeed += 0.8;
        if (data.contains("GemSCoolDown")) releaseSpeed += data.getDouble("GemSCoolDown");
        releaseSpeed += Compute.SArmorCoolDown(player);
        if (Compute.SwordSkillLevelGet(data, 7) > 0 && Utils.swordTag.containsKey(mainhand))
            releaseSpeed += Compute.SwordSkillLevelGet(data, 7) * 0.03; // 冷静（手持近战武器时，获得3%冷却缩减）
        if (Compute.ManaSkillLevelGet(data, 7) > 0 && Utils.sceptreTag.containsKey(mainhand))
            releaseSpeed += Compute.ManaSkillLevelGet(data, 7) * 0.06; // 冷静（手持法杖时，获得6%冷却缩减）

        if (data.getInt(StringUtils.Crest.Lake.Crest0) > 0) releaseSpeed +=
                LakeCrestAttributes.CoolDown[0] * data.getInt(StringUtils.Crest.Lake.Crest0);
        if (data.getInt(StringUtils.Crest.Lake.Crest1) > 0) releaseSpeed +=
                LakeCrestAttributes.CoolDown[1] * data.getInt(StringUtils.Crest.Lake.Crest1);
        if (data.getInt(StringUtils.Crest.Lake.Crest2) > 0) releaseSpeed +=
                LakeCrestAttributes.CoolDown[2] * data.getInt(StringUtils.Crest.Lake.Crest2);
        if (data.getInt(StringUtils.Crest.Lake.Crest3) > 0) releaseSpeed +=
                LakeCrestAttributes.CoolDown[3] * data.getInt(StringUtils.Crest.Lake.Crest3);
        if (data.getInt(StringUtils.Crest.Lake.Crest4) > 0) releaseSpeed +=
                LakeCrestAttributes.CoolDown[4] * data.getInt(StringUtils.Crest.Lake.Crest4);

        if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.sceptreTag.containsKey(mainhand)) {
            releaseSpeed += Compute.ManaSkillLevelGet(data, 11) * 0.05;
        } // 术法全析

        releaseSpeed += EarthPower.PlayerCoolDownEnhance(player); // 地蕴法术

        releaseSpeed += Compute.CuriosAttribute.attributeValue(player, Utils.coolDownDecrease, StringUtils.CuriosAttribute.CoolDown); // 新版饰品属性加成
        releaseSpeed += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExCoolDownDecrease);
        releaseSpeed += LakeArmorHelmet.exCooldown(player);

        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            releaseSpeed += Point * 0.01;
        }

        releaseSpeed += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerCooldownModifier);
        releaseSpeed += OnCuriosSlotAttributesModify.getAttributes(player, OnCuriosSlotAttributesModify.exReleaseSpeed);
        // 请在上方添加
        releaseSpeed *= Compute.playerFantasyAttributeEnhance(player);
        return releaseSpeed;
    }

    public static double coolDownDecrease(Player player) {
        return 1 - (1 / (1 + (powerReleaseSpeed(player))));
    }

    public static double defencePenetration(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double DefenceRate = 1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("breakDefence"))
            DefenceRate *= (1 - stackmainhandtag.getDouble("breakDefence"));
        if (Utils.defencePenetration.containsKey(boots)) DefenceRate *= (1 - Utils.defencePenetration.get(boots));
        if (Utils.defencePenetration.containsKey(leggings))
            DefenceRate *= (1 - Utils.defencePenetration.get(leggings));
        if (Utils.defencePenetration.containsKey(chest)) DefenceRate *= (1 - Utils.defencePenetration.get(chest));
        if (Utils.defencePenetration.containsKey(helmet)) DefenceRate *= (1 - Utils.defencePenetration.get(helmet));
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.defencePenetration.containsKey(mainhand))
            DefenceRate *= (1 - Utils.defencePenetration.get(mainhand));
        if (Utils.offHandTag.containsKey(offhand) && Utils.defencePenetration.containsKey(offhand))
            DefenceRate *= (1 - Utils.defencePenetration.get(offhand));
        if (data.contains(StringUtils.VolcanoBowSkill) && data.getInt(StringUtils.VolcanoBowSkill) > TickCount)
            DefenceRate *= (1 - 0.4);

        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 0)
            DefenceRate *= (1 - 0.20d);
        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 1)
            DefenceRate *= (1 - 0.45f);
        if (data.contains(StringUtils.ForestSwordSkill0) && data.getInt(StringUtils.ForestSwordSkill0) > TickCount)
            DefenceRate *= (1 - 0.15);
        if (data.contains(StringUtils.ForestSwordSkill1) && data.getInt(StringUtils.ForestSwordSkill1) > TickCount)
            DefenceRate *= (1 - 0.15);
        if (data.contains("GemSBreakDefence")) DefenceRate *= (1 - data.getDouble("GemSBreakDefence"));

        if (Utils.PlayerSpringHandDefencePenetraionAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            DefenceRate *= (1 - Utils.PlayerSpringHandDefencePenetraionAttribute.get(player));
        }

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            DefenceRate *= (1 - 0.2);
        }

        DefenceRate *= (1 - StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefencePenetrationModifier));

        double decreaseRate = 0;
        if (helmetTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(helmetTag);
        if (chestTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(chestTag);
        if (leggingsTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(leggingsTag);
        if (bootsTag.contains("newGems1")) decreaseRate += GemAttributes.gemsDefencePenetration(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            decreaseRate += GemAttributes.gemsDefencePenetration(stackmainhandtag);

        if (decreaseRate > 0) DefenceRate *= (1 - decreaseRate);
        DefenceRate *= (1 - Compute.CuriosAttribute.attributeValue(player, Utils.defencePenetration, StringUtils.CuriosAttribute.DefencePenetration)); // 新版饰品属性加成

        // 请在上方添加
        DefenceRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

        return 1 - DefenceRate;
    }

    public static double defencePenetration0(Player player) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double defencePenetration0 = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        defencePenetration0 += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.defencePenetration0);

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("BreakDefence0"))
            defencePenetration0 += stackmainhandtag.getDouble("BreakDefence0");
        if (Utils.defencePenetration0.containsKey(boots)) defencePenetration0 += Utils.defencePenetration0.get(boots);
        if (Utils.defencePenetration0.containsKey(leggings))
            defencePenetration0 += Utils.defencePenetration0.get(leggings);
        if (Utils.defencePenetration0.containsKey(chest)) defencePenetration0 += Utils.defencePenetration0.get(chest);
        if (Utils.defencePenetration0.containsKey(helmet)) defencePenetration0 += Utils.defencePenetration0.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.defencePenetration0.containsKey(mainhand))
            defencePenetration0 += Utils.defencePenetration0.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.defencePenetration0.containsKey(offhand))
            defencePenetration0 += Utils.defencePenetration0.get(offhand);
        if (data.contains(StringUtils.ForestSwordSkill0) && data.getInt(StringUtils.ForestSwordSkill0) > TickCount)
            defencePenetration0 += 50;
        if (data.contains(StringUtils.ForestSwordSkill1) && data.getInt(StringUtils.ForestSwordSkill1) > TickCount)
            defencePenetration0 += 250;
        if (Compute.SwordSkillLevelGet(data, 10) > 0 && Utils.swordTag.containsKey(mainhand))
            defencePenetration0 += Compute.SwordSkillLevelGet(data, 10) * player.experienceLevel * 0.5;
        if (Compute.BowSkillLevelGet(data, 10) > 0 && Utils.bowTag.containsKey(mainhand))
            defencePenetration0 += Compute.BowSkillLevelGet(data, 10) * player.experienceLevel * 0.5;
        if (data.contains("GemSDefencePenetration0")) defencePenetration0 += data.getDouble("GemSDefencePenetration0");

        int FlexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            int Point = FlexibilityAbilityPoint + (FlexibilityAbilityPoint / 10) * 5;
            defencePenetration0 += Point;
        } // 能力

        if (data.getInt(StringUtils.WitherBow.Effect3) > TickCount) defencePenetration0 += 400;
        else if (data.getInt(StringUtils.WitherBow.Effect2) > TickCount) defencePenetration0 += 300;
        else if (data.getInt(StringUtils.WitherBow.Effect1) > TickCount) defencePenetration0 += 200;
        else if (data.getInt(StringUtils.WitherBow.Effect0) > TickCount) defencePenetration0 += 100;

        if (data.getInt(StringUtils.Crest.Snow.Crest0) > 0)
            defencePenetration0 += SnowCrestAttributes.DefencePenetration[0] * data.getInt(StringUtils.Crest.Snow.Crest0);
        if (data.getInt(StringUtils.Crest.Snow.Crest1) > 0)
            defencePenetration0 += SnowCrestAttributes.DefencePenetration[1] * data.getInt(StringUtils.Crest.Snow.Crest1);
        if (data.getInt(StringUtils.Crest.Snow.Crest2) > 0)
            defencePenetration0 += SnowCrestAttributes.DefencePenetration[2] * data.getInt(StringUtils.Crest.Snow.Crest2);
        if (data.getInt(StringUtils.Crest.Snow.Crest3) > 0)
            defencePenetration0 += SnowCrestAttributes.DefencePenetration[3] * data.getInt(StringUtils.Crest.Snow.Crest3);
        if (data.getInt(StringUtils.Crest.Snow.Crest4) > 0)
            defencePenetration0 += SnowCrestAttributes.DefencePenetration[4] * data.getInt(StringUtils.Crest.Snow.Crest4);


        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand) || Utils.bowTag.containsKey(mainhand)))
            defencePenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.DefencePenetration0;

        if (mainhand instanceof SkyBow || mainhand instanceof SkyBoss.SkyBossBow)
            defencePenetration0 += movementSpeedWithoutBattle(player) * 100;

        if (mainhand.equals(ModItems.ShipBow.get())) {
            List<Player> serverPlayerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger Count = new AtomicInteger();
            serverPlayerList.forEach(player1 -> {
                if (player1.distanceTo(player) <= 6) Count.getAndIncrement();
            });
            defencePenetration0 += 50 * Math.min(Count.get(), 4);
        }

        if (Utils.IceBowEffectMap.containsKey(player) && Utils.IceBowEffectMap.get(player) > TickCount) {
            defencePenetration0 += Utils.IceBowEffectNumMap.get(player) / 8;
        } //冰霜弓

        if (Utils.PlayerSpringRingDefencePenetration0Attribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            defencePenetration0 += Utils.PlayerSpringRingDefencePenetration0Attribute.get(player);
        }

        if (Utils.PlayerSpringBeltDefencePenetration0Attribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            defencePenetration0 += Utils.PlayerSpringBeltDefencePenetration0Attribute.get(player);
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(helmetTag);
        if (chestTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(chestTag);
        if (leggingsTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(leggingsTag);
        if (bootsTag.contains("newGems1")) defencePenetration0 += GemAttributes.gemsDefencePenetration0(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            defencePenetration0 += GemAttributes.gemsDefencePenetration0(stackmainhandtag);

        defencePenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.defencePenetration0, StringUtils.CuriosAttribute.DefencePenetration0); // 新版饰品属性加成

        defencePenetration0 += CastleSword.ExPenetration0(player); // 暗黑武器主动
        defencePenetration0 += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerDefencePenetration0Modifier);
        // 请在上方添加
        defencePenetration0 *= Compute.playerFantasyAttributeEnhance(player);

        return defencePenetration0;
    }

    public static double healthRecover(Player player) {
        double healthRecover = 5 + player.experienceLevel * 0.1;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        healthRecover += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.healthRecover);

        if (helmetTag.contains("newGems1")) healthRecover += GemAttributes.gemsHealRecover(helmetTag);
        if (chestTag.contains("newGems1")) healthRecover += GemAttributes.gemsHealRecover(chestTag);
        if (leggingsTag.contains("newGems1")) healthRecover += GemAttributes.gemsHealRecover(leggingsTag);
        if (bootsTag.contains("newGems1")) healthRecover += GemAttributes.gemsHealRecover(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            healthRecover += GemAttributes.gemsHealRecover(stackmainhandtag);
        if (Utils.healthRecover.containsKey(boots)) healthRecover += Utils.healthRecover.get(boots);
        if (Utils.healthRecover.containsKey(leggings)) healthRecover += Utils.healthRecover.get(leggings);
        if (Utils.healthRecover.containsKey(chest)) healthRecover += Utils.healthRecover.get(chest);
        if (Utils.healthRecover.containsKey(helmet)) healthRecover += Utils.healthRecover.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.healthRecover.containsKey(mainhand))
            healthRecover += Utils.healthRecover.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.healthRecover.containsKey(offhand))
            healthRecover += Utils.healthRecover.get(offhand);
        if (Compute.ArmorCount.Plain(player) >= 2) healthRecover += 0.5 + player.getMaxHealth() * 0.01F;
        if (Compute.ArmorCount.LifeMana(player) >= 4) healthRecover += 1 + player.getMaxHealth() * 0.01F;
        if (!Utils.OverWorldLevelIsNight && Compute.ArmorCount.Forest(player) >= 4) healthRecover += 5;

        if (player.getEffect(ModEffects.HEALTHRECOVER.get()) != null && player.getEffect(ModEffects.HEALTHRECOVER.get()).getAmplifier() == 0)
            healthRecover += player.getMaxHealth() * 0.025;
        if (player.getEffect(ModEffects.HEALTHRECOVER.get()) != null && player.getEffect(ModEffects.HEALTHRECOVER.get()).getAmplifier() == 1)
            healthRecover += player.getMaxHealth() * 0.05;

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            healthRecover += Point;
        }
        if (data.getInt(StringUtils.Crest.Plain.Crest0) > 0)
            healthRecover += PlainCrestAttributes.HealthRecover[0] * data.getInt(StringUtils.Crest.Plain.Crest0);
        if (data.getInt(StringUtils.Crest.Plain.Crest1) > 0)
            healthRecover += PlainCrestAttributes.HealthRecover[1] * data.getInt(StringUtils.Crest.Plain.Crest1);
        if (data.getInt(StringUtils.Crest.Plain.Crest2) > 0)
            healthRecover += PlainCrestAttributes.HealthRecover[2] * data.getInt(StringUtils.Crest.Plain.Crest2);
        if (data.getInt(StringUtils.Crest.Plain.Crest3) > 0)
            healthRecover += PlainCrestAttributes.HealthRecover[3] * data.getInt(StringUtils.Crest.Plain.Crest3);
        if (data.getInt(StringUtils.Crest.Plain.Crest4) > 0)
            healthRecover += PlainCrestAttributes.HealthRecover[4] * data.getInt(StringUtils.Crest.Plain.Crest4);

        healthRecover += Compute.CuriosAttribute.attributeValue(player, Utils.healthRecover, StringUtils.CuriosAttribute.HealthRecover); // 新版饰品属性加成
        healthRecover += PlainNewRune.playerHealthRecover(player);
        healthRecover += ForestNewRune.playerHealthRecoverUp(player);

        healthRecover += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerHealthRecoverModifier);
        // 请在上方添加
        healthRecover *= Compute.playerFantasyAttributeEnhance(player);

        return healthRecover;
    }

    public static double maxHealth(Player player) {
        double maxHealth = 200 + player.experienceLevel * 10;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (helmetTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(helmetTag);
        if (chestTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(chestTag);
        if (leggingsTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(leggingsTag);
        if (bootsTag.contains("newGems1")) maxHealth += GemAttributes.gemsMaxHealth(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            maxHealth += GemAttributes.gemsMaxHealth(stackmainhandtag);

        if (Utils.maxHealth.containsKey(boots))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.maxHealth);
        if (Utils.maxHealth.containsKey(leggings))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.maxHealth);
        if (Utils.maxHealth.containsKey(chest))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.maxHealth);
        if (Utils.maxHealth.containsKey(helmet))
            maxHealth += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.maxHealth);

        maxHealth += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.maxHealth);

        if (Utils.maxHealth.containsKey(helmet) && helmetTag.contains("Forging"))
            maxHealth += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.maxHealth));
        if (Utils.maxHealth.containsKey(chest) && chestTag.contains("Forging"))
            maxHealth += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.maxHealth));
        if (Utils.maxHealth.containsKey(leggings) && leggingsTag.contains("Forging"))
            maxHealth += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.maxHealth));
        if (Utils.maxHealth.containsKey(boots) && bootsTag.contains("Forging"))
            maxHealth += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.maxHealth));

        if (Utils.mainHandTag.containsKey(mainhand) && Utils.maxHealth.containsKey(mainhand))
            maxHealth += Utils.maxHealth.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.maxHealth.containsKey(offhand))
            maxHealth += Utils.maxHealth.get(offhand);
        if (Compute.ArmorCount.Plain(player) >= 4) maxHealth += 200;
        if (data.contains("Barker")) maxHealth *= 1.0d + (data.getInt("Barker") / 100000.0d) * 0.05F;

        if (data.contains("GemSMaxHeal")) maxHealth += data.getDouble("GemSMaxHeal");
        maxHealth += Compute.SArmorMaxHealth(player);

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            maxHealth += Point * 10;
        }

        if (data.getInt(StringUtils.Crest.Forest.Crest0) > 0)
            maxHealth += ForestCrestAttributes.MaxHealth[0] * data.getInt(StringUtils.Crest.Forest.Crest0);
        if (data.getInt(StringUtils.Crest.Forest.Crest1) > 0)
            maxHealth += ForestCrestAttributes.MaxHealth[1] * data.getInt(StringUtils.Crest.Forest.Crest1);
        if (data.getInt(StringUtils.Crest.Forest.Crest2) > 0)
            maxHealth += ForestCrestAttributes.MaxHealth[2] * data.getInt(StringUtils.Crest.Forest.Crest2);
        if (data.getInt(StringUtils.Crest.Forest.Crest3) > 0)
            maxHealth += ForestCrestAttributes.MaxHealth[3] * data.getInt(StringUtils.Crest.Forest.Crest3);
        if (data.getInt(StringUtils.Crest.Forest.Crest4) > 0)
            maxHealth += ForestCrestAttributes.MaxHealth[4] * data.getInt(StringUtils.Crest.Forest.Crest4);

        String name = player.getName().getString();
        if (Utils.playerHealthRingMap.containsKey(name))
            maxHealth += Utils.playerHealthRingMap.get(name);

        if (Utils.PlayerSpringHandMaxHealthAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            maxHealth += Utils.PlayerSpringHandMaxHealthAttribute.get(player);
        }

        maxHealth += Compute.CuriosAttribute.attributeValue(player, Utils.maxHealth, StringUtils.CuriosAttribute.MaxHealth); // 新版饰品属性加成

        maxHealth += Compute.PassiveEquip.AttributeGet(player, Utils.maxHealth); // 器灵属性加成
        maxHealth += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExMaxHealth);
        maxHealth += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExMaxHealth);
        maxHealth += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExMaxHealth);
        maxHealth += PlainArmorHelmet.exMaxHealth(player);
        // 请在上方添加
        maxHealth *= Compute.playerFantasyAttributeEnhance(player);
        maxHealth *= (1 + NewPotionEffects.maxHealthEnhance(player));
        maxHealth *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentMaxHealthEnhance));
        return maxHealth;
    }

    public static double manaDamage(Player player) {
        int tickCount = player.getServer().getTickCount();
        String name = player.getName().getString();
        double baseDamage = 0.0d;
        double exDamage = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag mainHandItemTag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            mainHandItemTag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaDamage.containsKey(mainhand))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.manaDamage);

        // Fixed:防具攻击强化失效
        if (Utils.armorTag.containsKey(helmet) && Utils.manaDamage.containsKey(helmet) && helmetTag.contains("Forging"))
            baseDamage += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDamage));
        if (Utils.armorTag.containsKey(chest) && Utils.manaDamage.containsKey(chest) && chestTag.contains("Forging"))
            baseDamage += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDamage));
        if (Utils.armorTag.containsKey(leggings) && Utils.manaDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
            baseDamage += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDamage));
        if (Utils.armorTag.containsKey(boots) && Utils.manaDamage.containsKey(boots) && bootsTag.contains("Forging"))
            baseDamage += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDamage));

        baseDamage += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaDamage);

        if (helmetTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(helmetTag);
        if (chestTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(chestTag);
        if (leggingsTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(leggingsTag);
        if (bootsTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(bootsTag);
        if (Utils.mainHandTag.containsKey(mainhand) && mainHandItemTag.contains("manadamage"))
            baseDamage += mainHandItemTag.getDouble("manadamage");
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaDamage.containsKey(mainhand) && mainHandItemTag.contains("Forging"))
            baseDamage += Compute.forgingValue(mainHandItemTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.manaDamage));
        if (Utils.manaDamage.containsKey(boots))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDamage);
        if (Utils.manaDamage.containsKey(leggings))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDamage);
        if (Utils.manaDamage.containsKey(chest))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDamage);
        if (Utils.manaDamage.containsKey(helmet))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDamage);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaDamage.containsKey(offhand))
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getOffhandItem(), Utils.manaDamage);
        if (Utils.mainHandTag.containsKey(mainhand) && (Utils.manaDamage.containsKey(mainhand) || mainHandItemTag.contains(StringUtils.RandomAttribute.manaDamage))
                && mainHandItemTag.contains(StringUtils.KillCount.KillCount)) {
            int killCount = mainHandItemTag.getInt(StringUtils.KillCount.KillCount);
            if (killCount >= 100000) killCount = 100000;
            baseDamage += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getMainHandItem(), Utils.manaDamage) * 0.5 * (killCount / 100000.0);
            baseDamage += ForgeEquipUtils.getRandomEquipBaseValue(player.getMainHandItem(), StringUtils.RandomAttribute.manaDamage) * 0.5 * (killCount / 100000.0);
        }

        if (mainHandItemTag.contains("newGems1")) exDamage += GemAttributes.gemsManaDamage(mainHandItemTag);

        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 0)
            exDamage += baseDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 1)
            exDamage += baseDamage * 0.4 + 40;
        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && !Utils.OverWorldLevelIsNight && mainhand instanceof PlainSceptre4)
            exDamage += 45;
        if (data.contains("GemSManaDamage")) exDamage += data.getDouble("GemSManaDamage");
        exDamage += Compute.SArmorManaDamage(player);

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            exDamage += Point * 2;
        } // 能力

        if (Compute.ManaSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.ManaSkillNum.Skill2) && data.getInt(StringUtils.ManaSkillNum.Skill2) > tickCount) {
            exDamage += baseDamage * Compute.ManaSkillLevelGet(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            exDamage += 75;
        // 矿工裤被动

        if (data.getInt(StringUtils.Crest.Mana.Crest0) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[0] * data.getInt(StringUtils.Crest.Mana.Crest0);
        if (data.getInt(StringUtils.Crest.Mana.Crest1) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[1] * data.getInt(StringUtils.Crest.Mana.Crest1);
        if (data.getInt(StringUtils.Crest.Mana.Crest2) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[2] * data.getInt(StringUtils.Crest.Mana.Crest2);
        if (data.getInt(StringUtils.Crest.Mana.Crest3) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[3] * data.getInt(StringUtils.Crest.Mana.Crest3);
        if (data.getInt(StringUtils.Crest.Mana.Crest4) > 0)
            exDamage += ManaCrestAttributes.ExManaDamage[4] * data.getInt(StringUtils.Crest.Mana.Crest4);

        if (Compute.ArmorCount.Volcano(player) >= 2) exDamage += baseDamage * 0.25F;
        if (Compute.ArmorCount.ObsiMana(player) >= 4) exDamage += baseDamage * 0.25F;

        if (mainHandItemTag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            exDamage +=
                    mainHandItemTag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaAttackDamage;

        if (Utils.playerManaAttackRingMap.containsKey(name))
            exDamage += Utils.playerManaAttackRingMap.get(name);

        if (Utils.PlayerSpringRingManaAttackAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringRingManaAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringBraceletAttackAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            exDamage += Utils.PlayerSpringBraceletAttackAttribute.get(player);
        }

        if (Utils.PlayerFireWorkGunEffect.containsKey(player) && Utils.PlayerFireWorkGunEffect.get(player) > tickCount) {
            exDamage += baseDamage * 0.1;
        } //新春礼炮

        if (Utils.IceSceptreEffectMap.containsKey(player) && Utils.IceSceptreEffectMap.get(player) > tickCount) {
            exDamage += Utils.IceSceptreEffectNumMap.get(player) * 2;
        } //天源流光

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
        exDamage += Compute.CuriosAttribute.attributeValue(player, Utils.manaDamage, StringUtils.CuriosAttribute.ManaDamage); // 新版饰品属性加成

        exDamage += Compute.PassiveEquip.AttributeGet(player, Utils.manaDamage); // 器灵属性加成
        exDamage += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDamage);
        exDamage += LifeElementSceptre.ExManaDamage(player);
        exDamage += VolcanoArmorHelmet.exManaDamage(player);
        exDamage += CastleNewRune.manaDamage(player);
        exDamage += OnCuriosSlotAttributesModify.getAttributes(player, OnCuriosSlotAttributesModify.exManaDamage);

        // 请在上方添加
        double totalDamage = baseDamage + exDamage;
        totalDamage *= (1 + MoonBook.damageEnhance(player));
        totalDamage *= Compute.playerFantasyAttributeEnhance(player);
        totalDamage *= ManaCurios0.PlayerFinalManaDamageEnhance(player);
        totalDamage *= ManaCurios2.playerFinalManaDamageEnhance(player);
        totalDamage *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentManaDamageEnhance));

        Utils.playerManaDamageBeforeTransform.put(player, totalDamage);

        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.DevilManaHelmet.get())) return (totalDamage) * 1.25;
        return totalDamage;
    }

    public static double manaHealthSteal(Player player) {
        CompoundTag data = player.getPersistentData();
        double ManaHealSteal = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaHealSteal"))
            ManaHealSteal += stackmainhandtag.getDouble("ManaHealSteal");
        if (Utils.manaHealthSteal.containsKey(boots)) ManaHealSteal += Utils.manaHealthSteal.get(boots);
        if (Utils.manaHealthSteal.containsKey(leggings)) ManaHealSteal += Utils.manaHealthSteal.get(leggings);
        if (Utils.manaHealthSteal.containsKey(chest)) ManaHealSteal += Utils.manaHealthSteal.get(chest);
        if (Utils.manaHealthSteal.containsKey(helmet)) ManaHealSteal += Utils.manaHealthSteal.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaHealthSteal.containsKey(mainhand))
            ManaHealSteal += Utils.manaHealthSteal.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaHealthSteal.containsKey(offhand))
            ManaHealSteal += Utils.manaHealthSteal.get(offhand);
        if (Compute.ArmorCount.LifeMana(player) >= 4) ManaHealSteal += 0.05;
        if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.sceptreTag.containsKey(mainhand))
            ManaHealSteal += Compute.ManaSkillLevelGet(data, 11) * 0.01;
        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) ManaHealSteal += 0.05;

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) ManaHealSteal += GemAttributes.gemsManaHealthSteal(helmetTag);
        if (chestTag.contains("newGems1")) ManaHealSteal += GemAttributes.gemsManaHealthSteal(chestTag);
        if (leggingsTag.contains("newGems1")) ManaHealSteal += GemAttributes.gemsManaHealthSteal(leggingsTag);
        if (bootsTag.contains("newGems1")) ManaHealSteal += GemAttributes.gemsManaHealthSteal(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            ManaHealSteal += GemAttributes.gemsManaHealthSteal(stackmainhandtag);

        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player))
            ManaHealSteal += 0.05;

        ManaHealSteal += Compute.CuriosAttribute.attributeValue(player, Utils.manaHealthSteal, StringUtils.CuriosAttribute.ManaHealthSteal); // 新版饰品属性加成
        //请在上方添加
        ManaHealSteal *= Compute.playerFantasyAttributeEnhance(player);

        return ManaHealSteal;
    }

    public static double manaRecover(Player player) {
        if (player.isCreative()) return 1000;
        double manaRecover = player.experienceLevel * 0.1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        manaRecover += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaRecover);

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(helmetTag);
        if (chestTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(chestTag);
        if (leggingsTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(leggingsTag);
        if (bootsTag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(bootsTag);
        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("manareply"))
            manaRecover += stackmainhandtag.getDouble("manareply");
        if (Utils.manaRecover.containsKey(boots)) manaRecover += Utils.manaRecover.get(boots);
        if (Utils.manaRecover.containsKey(leggings)) manaRecover += Utils.manaRecover.get(leggings);
        if (Utils.manaRecover.containsKey(chest)) manaRecover += Utils.manaRecover.get(chest);
        if (Utils.manaRecover.containsKey(helmet)) manaRecover += Utils.manaRecover.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaRecover.containsKey(mainhand))
            manaRecover += Utils.manaRecover.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaRecover.containsKey(offhand))
            manaRecover += Utils.manaRecover.get(offhand);
        if (Compute.ArmorCount.LifeMana(player) >= 2) manaRecover += 5;
        if (Compute.ArmorCount.ObsiMana(player) >= 2) manaRecover += 5;
        if (stackmainhandtag.contains("newGems1")) manaRecover += GemAttributes.gemsManaRecover(stackmainhandtag);

        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 0)
            manaRecover += 20;
        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 1)
            manaRecover += 45;
        if (data.contains("GemSManaReply")) manaRecover += data.getDouble("GemSManaReply");
        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && !Utils.OverWorldLevelIsNight && mainhand instanceof PlainSceptre4)
            manaRecover += 15;
        if (Compute.SwordSkillLevelGet(data, 8) > 0 && Utils.swordTag.containsKey(mainhand))
            manaRecover += Compute.SwordSkillLevelGet(data, 8); // 洞悉（手持近战武器时，获得1额外法力回复）
        if (Compute.ManaSkillLevelGet(data, 1) > 0 && Utils.sceptreTag.containsKey(mainhand))
            manaRecover += Compute.ManaSkillLevelGet(data, 1) * 0.5; // 仙女护符（持有法杖时，获得额外0.5点法力回复）
        if (Compute.ManaSkillLevelGet(data, 8) > 0 && Utils.sceptreTag.containsKey(mainhand))
            manaRecover += Compute.ManaSkillLevelGet(data, 8); // 洞悉（手持法杖时，获得1额外法力回复）

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            manaRecover +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaRecover;

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            double Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            manaRecover += Point * 0.1;
        } // 能力

        if (Utils.PlayerSpringBraceletManaRecoverAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            manaRecover += Utils.PlayerSpringBraceletManaRecoverAttribute.get(player);
        }

        manaRecover += Compute.CuriosAttribute.attributeValue(player, Utils.manaRecover, StringUtils.CuriosAttribute.ManaRecover); // 新版饰品属性加成
        manaRecover += StableAttributesModifier.getModifierValue(player, StableAttributesModifier.playerManaRecoverModifier);
        // 请在上方添加
        manaRecover *= Compute.playerFantasyAttributeEnhance(player);

        return manaRecover;
    }

    public static double manaDefence(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double manaDefence = player.experienceLevel;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID);
        }
        if (Utils.manaDefence.containsKey(boots))
            manaDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDefence);
        if (Utils.manaDefence.containsKey(leggings))
            manaDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDefence);
        if (Utils.manaDefence.containsKey(chest))
            manaDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDefence);
        if (Utils.manaDefence.containsKey(helmet))
            manaDefence += ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDefence);
        double ExDefence = 0.0d;
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaDefence.containsKey(mainhand))
            ExDefence += Utils.manaDefence.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaDefence.containsKey(offhand))
            manaDefence += Utils.manaDefence.get(offhand);
        if (Utils.manaDefence.containsKey(helmet) && helmetTag.contains("Forging"))
            manaDefence += Compute.forgingValue(helmetTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.HEAD), Utils.manaDefence));
        if (Utils.manaDefence.containsKey(chest) && chestTag.contains("Forging"))
            manaDefence += Compute.forgingValue(chestTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.CHEST), Utils.manaDefence));
        if (Utils.manaDefence.containsKey(leggings) && leggingsTag.contains("Forging"))
            manaDefence += Compute.forgingValue(leggingsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.LEGS), Utils.manaDefence));
        if (Utils.manaDefence.containsKey(boots) && bootsTag.contains("Forging"))
            manaDefence += Compute.forgingValue(bootsTag, ForgeEquipUtils.getTraditionalEquipBaseValue(player.getItemBySlot(EquipmentSlot.FEET), Utils.manaDefence));

        if (boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet)
            ExDefence += manaDefence * 0.25;
        if (LifeManaArmor.getPlayerLifeManaArmorCount(player) == 4) ExDefence += manaDefence * 0.25;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 0)
            ExDefence += manaDefence * 0.25 + 75;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 1)
            ExDefence += manaDefence * 0.4 + 125;
        if (data.contains("GemSManaDefence")) ExDefence += data.getDouble("GemSManaDefence");
/*            if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
                ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 10;
            }*/
        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) ExDefence += 40;

        if (Utils.SakuraBowEffectMap.containsKey(player) && Utils.SakuraBowEffectMap.get(player) > TickCount) {
            ExDefence += 100;
        } // 妖弓-樱

        if (helmetTag.contains("newGems1")) ExDefence += GemAttributes.gemsManaDefence(helmetTag);
        if (chestTag.contains("newGems1")) ExDefence += GemAttributes.gemsManaDefence(chestTag);
        if (leggingsTag.contains("newGems1")) ExDefence += GemAttributes.gemsManaDefence(leggingsTag);
        if (bootsTag.contains("newGems1")) ExDefence += GemAttributes.gemsManaDefence(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            ExDefence += GemAttributes.gemsManaDefence(stackmainhandtag);

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) ExDefence += 200;
        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) ExDefence += 200;
        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) ExDefence += 400;
        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) ExDefence += 400;

        ExDefence += Compute.CuriosAttribute.attributeValue(player, Utils.manaDefence, StringUtils.CuriosAttribute.ManaDefence); // 新版饰品属性加成
        ExDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExManaDefence);
        ExDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDefence);
        ExDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExManaDefence);
        // 请在上方添加

        double totalDefence = manaDefence + ExDefence;
        totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
        totalDefence *= Compute.playerFantasyAttributeEnhance(player);
        totalDefence *= MineShield.defenceEnhance(player);
        totalDefence *= (1 + GemAttributes.getPlayerCurrentAllEquipGemsValue(player, Utils.percentManaDefenceEnhance));
        if (totalDefence < 0) return 0;
        return totalDefence;
    }

    public static double healthSteal(Player player) {
        double healSteal = 0.0d;
        CompoundTag data = player.getPersistentData();
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        healSteal += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.healthSteal);

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healsteal"))
            healSteal += stackmainhandtag.getDouble("healsteal");
        if (Utils.healthSteal.containsKey(boots)) healSteal += Utils.healthSteal.get(boots);
        if (Utils.healthSteal.containsKey(leggings)) healSteal += Utils.healthSteal.get(leggings);
        if (Utils.healthSteal.containsKey(chest)) healSteal += Utils.healthSteal.get(chest);
        if (Utils.healthSteal.containsKey(helmet)) healSteal += Utils.healthSteal.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.healthSteal.containsKey(mainhand))
            healSteal += Utils.healthSteal.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.healthSteal.containsKey(offhand))
            healSteal += Utils.healthSteal.get(offhand);

        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 0)
            healSteal += 0.12;
        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 1)
            healSteal += 0.25;
        if (data.contains("GemSHealSteal")) healSteal += data.getDouble("GemSHealSteal");
        healSteal += Compute.SArmorHealSteal(player);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.swordTag.containsKey(mainhand)))
            healSteal +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.HealthSteal;

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) healSteal += 0.05;

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                healSteal += data.getDouble("CritDamageAfterCompute") / 5;
            }
        } // 封魔者法盾

        if (helmetTag.contains("newGems1")) healSteal += GemAttributes.gemsHealthSteal(helmetTag);
        if (chestTag.contains("newGems1")) healSteal += GemAttributes.gemsHealthSteal(chestTag);
        if (leggingsTag.contains("newGems1")) healSteal += GemAttributes.gemsHealthSteal(leggingsTag);
        if (bootsTag.contains("newGems1")) healSteal += GemAttributes.gemsHealthSteal(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            healSteal += GemAttributes.gemsHealthSteal(stackmainhandtag);

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) healSteal += 0.05;

        healSteal += Compute.CuriosAttribute.attributeValue(player, Utils.healthSteal, StringUtils.CuriosAttribute.HealthSteal); // 新版饰品属性加成

        // 请在上方添加

        healSteal *= Compute.playerFantasyAttributeEnhance(player);
        healSteal *= (1 + Compute.ArmorCount.BloodMana(player) * 0.08);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.manaKnife.get())) {
            data.putDouble("HealthStealAfterCompute", healSteal);
            return 0;
        } // 猎魔者小刀

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                data.putDouble("HealthStealAfterCompute", healSteal);
                return 0;
            }
        } // 封魔者法盾

        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) return healSteal * 0.25f;

        return healSteal;
    }

    public static double manaPenetration(Player player) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double DefenceRate = 1;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.manaPenetration.containsKey(boots))
            DefenceRate *= (1 - Utils.manaPenetration.get(boots));
        if (Utils.manaPenetration.containsKey(leggings))
            DefenceRate *= (1 - Utils.manaPenetration.get(leggings));
        if (Utils.manaPenetration.containsKey(chest))
            DefenceRate *= (1 - Utils.manaPenetration.get(chest));
        if (Utils.manaPenetration.containsKey(helmet))
            DefenceRate *= (1 - Utils.manaPenetration.get(helmet));
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaPenetration.containsKey(mainhand))
            DefenceRate *= (1 - Utils.manaPenetration.get(mainhand));
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaPenetration.containsKey(offhand))
            DefenceRate *= (1 - Utils.manaPenetration.get(offhand));

        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 0)
            DefenceRate *= (1 - 0.2);
        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 1)
            DefenceRate *= (1 - 0.45);
        if (data.contains("GemSManaBreakDefence")) DefenceRate *= (1 - data.getDouble("GemSManaBreakDefence"));
/*            if (ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand))
                DefenceRate *= (1 - ManaSkillLevelGet(data, 10) * 0.03); // 结构研究（获得30% + 50法术穿透）*/

/*
            if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.SceptreTag.containsKey(mainhand))
                DefenceRate *=
                        (1 - stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaPenetration);
*/

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            DefenceRate *= (1 - 0.2);
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);

        double DecreaseRate = 0;
        if (helmetTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(helmetTag);
        if (chestTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(chestTag);
        if (leggingsTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(leggingsTag);
        if (bootsTag.contains("newGems1")) DecreaseRate += GemAttributes.gemsManaPenetration(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            DecreaseRate += GemAttributes.gemsManaPenetration(stackmainhandtag);

        if (DecreaseRate > 0) DefenceRate *= (1 - DecreaseRate);

        DefenceRate *= (1 - Compute.CuriosAttribute.attributeValue(player, Utils.manaPenetration, StringUtils.CuriosAttribute.ManaPenetration)); // 新版饰品属性加成
        // 请在上方添加
        DefenceRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

        return 1 - DefenceRate;
    }

    public static double manaPenetration0(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double manaPenetration0 = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        manaPenetration0 += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.manaPenetration0);

        if (Utils.mainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaBreakDefence0"))
            manaPenetration0 += stackmainhandtag.getDouble("ManaBreakDefence0");
        if (Utils.manaPenetration0.containsKey(boots))
            manaPenetration0 += Utils.manaPenetration0.get(boots);
        if (Utils.manaPenetration0.containsKey(leggings))
            manaPenetration0 += Utils.manaPenetration0.get(leggings);
        if (Utils.manaPenetration0.containsKey(chest))
            manaPenetration0 += Utils.manaPenetration0.get(chest);
        if (Utils.manaPenetration0.containsKey(helmet))
            manaPenetration0 += Utils.manaPenetration0.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.manaPenetration0.containsKey(mainhand))
            manaPenetration0 += Utils.manaPenetration0.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.manaPenetration0.containsKey(offhand))
            manaPenetration0 += Utils.manaPenetration0.get(offhand);
/*            if (ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand))
                ManaBreakDefence0 += ManaSkillLevelGet(data, 10) * 5;*/
        if (data.contains("GemSManaDefencePenetration0"))
            manaPenetration0 += data.getDouble("GemSManaDefencePenetration0");
        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            manaPenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaPenetration0;

        if (data.getInt(StringUtils.Crest.Sky.Crest0) > 0)
            manaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[0] * data.getInt(StringUtils.Crest.Sky.Crest0);
        if (data.getInt(StringUtils.Crest.Sky.Crest1) > 0)
            manaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[1] * data.getInt(StringUtils.Crest.Sky.Crest1);
        if (data.getInt(StringUtils.Crest.Sky.Crest2) > 0)
            manaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[2] * data.getInt(StringUtils.Crest.Sky.Crest2);
        if (data.getInt(StringUtils.Crest.Sky.Crest3) > 0)
            manaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[3] * data.getInt(StringUtils.Crest.Sky.Crest3);
        if (data.getInt(StringUtils.Crest.Sky.Crest4) > 0)
            manaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[4] * data.getInt(StringUtils.Crest.Sky.Crest4);

        if (stackmainhandtag.contains(StringUtils.ManaCore.ManaCore) && stackmainhandtag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            manaPenetration0 += movementSpeedWithoutBattle(player) / 0.01;
        }

        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) manaPenetration0 += 10;
        if (data.contains("plaingems") && data.getBoolean("plaingems")) manaPenetration0 += 10;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) manaPenetration0 += 10;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) manaPenetration0 += 10;

        if (Compute.ArmorCount.Volcano(player) >= 4) manaPenetration0 += 35;

        if (mainhand.equals(ModItems.ShipSceptre.get())) {
            if (Utils.ShipSceptreWaterBlockNum.containsKey(player)) {
                int Count = Utils.ShipSceptreWaterBlockNum.get(player);
                if (Count > 0) {
                    manaPenetration0 += 100 + 0.5 * Count;
                }
            }
        } // 唤潮之杖

        if (Utils.PlayerSpringRingManaPenetration0Attribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            manaPenetration0 += Utils.PlayerSpringRingManaPenetration0Attribute.get(player);
        }

        if (Utils.PlayerSpringBraceletManaPenetration0Attribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            manaPenetration0 += Utils.PlayerSpringBraceletManaPenetration0Attribute.get(player);
        }

        if (Utils.WitherBookPlayerEffectTick.containsKey(player) && Utils.WitherBookPlayerEffectTick.get(player) > TickCount) {
            manaPenetration0 += Utils.WitherBookPlayerEffectNum.get(player);
        } // 凋零秘典

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(helmetTag);
        if (chestTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(chestTag);
        if (leggingsTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(leggingsTag);
        if (bootsTag.contains("newGems1")) manaPenetration0 += GemAttributes.gemsManaPenetration0(bootsTag);
        if (stackmainhandtag.contains("newGems1") && Utils.mainHandTag.containsKey(mainhand))
            manaPenetration0 += GemAttributes.gemsManaPenetration0(stackmainhandtag);

        manaPenetration0 += Compute.CuriosAttribute.attributeValue(player, Utils.manaPenetration0, StringUtils.CuriosAttribute.ManaPenetration0); // 新版饰品属性加成

        manaPenetration0 += TabooManaArmor.storeCostValue(player);
        manaPenetration0 += TabooManaArmor.PenetrationDirectEnhance(player);

        manaPenetration0 += Compute.PassiveEquip.AttributeGet(player, Utils.manaPenetration0); // 器灵属性加成
        manaPenetration0 += CastleSword.ExPenetration0(player); // 暗黑武器主动
        // 请在上方添加
        manaPenetration0 *= Compute.playerFantasyAttributeEnhance(player);
        return manaPenetration0;
    }

    public static double maxManaUp(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double maxMana = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if (Utils.maxMana.containsKey(boots)) maxMana += Utils.maxMana.get(boots);
        if (Utils.maxMana.containsKey(leggings)) maxMana += Utils.maxMana.get(leggings);
        if (Utils.maxMana.containsKey(chest)) maxMana += Utils.maxMana.get(chest);
        if (Utils.maxMana.containsKey(helmet)) maxMana += Utils.maxMana.get(helmet);
        if (Utils.mainHandTag.containsKey(mainhand) && Utils.maxMana.containsKey(mainhand))
            maxMana += Utils.maxMana.get(mainhand);
        if (Utils.offHandTag.containsKey(offhand) && Utils.maxMana.containsKey(offhand))
            maxMana += Utils.maxMana.get(offhand);
        if (data.contains("GemSMaxMana")) maxMana += data.getDouble("GemSMaxMana");

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            maxMana += Point;
        } // 能力

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.sceptreTag.containsKey(mainhand))
            maxMana +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MaxMana;

        if (Utils.PlayerSpringBraceletMaxManaAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            maxMana += Utils.PlayerSpringBraceletMaxManaAttribute.get(player);
        }

        if (Utils.EarthBookPlayerEffectMap.containsKey(player) && Utils.EarthBookPlayerEffectMap.get(player) > TickCount) {
            maxMana += player.getMaxHealth() * 0.075;
        } // 地蕴传世秘典

        maxMana += Compute.CuriosAttribute.attributeValue(player, Utils.maxMana, StringUtils.CuriosAttribute.MaxMana); // 新版饰品属性加成

        maxMana += Compute.PassiveEquip.AttributeGet(player, Utils.maxMana); // 器灵属性加成

        maxMana += handleAllEquipRandomAttribute(player, StringUtils.RandomAttribute.maxMana);
        // 请在上方添加

        maxMana *= Compute.playerFantasyAttributeEnhance(player);

        return maxMana;
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

    public static double playerToughness(Player player) {
        double value = 0;

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
}
