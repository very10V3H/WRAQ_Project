package com.very.wraq.valueAndTools.attributeValues;

import com.very.wraq.customized.players.bow.Hgj.HgjCurios;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanBow;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanCurios;
import com.very.wraq.customized.players.bow.MyMission.MyMissionBow;
import com.very.wraq.customized.players.bow.MyMission.MyMissionCurios;
import com.very.wraq.customized.players.bow.MyMission.MyMissionCurios1;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.players.bow.Shao_Feng.ShaoFengCurios;
import com.very.wraq.customized.players.bow.Wcndymlgb.WcndymlgbCurios;
import com.very.wraq.customized.players.bow.Yxwg.YxwgBow;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios1;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios2;
import com.very.wraq.customized.players.bow.littleart.LittleartCurios;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.*;
import com.very.wraq.customized.players.sceptre.Eliaoi.Eliaoi;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios1;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios2;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiSceptre;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla2;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanillaSword;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdCurios;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdSceptre;
import com.very.wraq.customized.players.sceptre.liulixian_.*;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiCurios;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import com.very.wraq.customized.players.sword.Crush.CrushiSword;
import com.very.wraq.customized.players.sword.Heihuang.HeihuangCurios;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.customized.uniform.attack.AttackCurios0;
import com.very.wraq.customized.uniform.attack.AttackCurios1;
import com.very.wraq.customized.uniform.attack.AttackCurios2;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.customized.uniform.bow.BowCurios2;
import com.very.wraq.customized.uniform.mana.ManaCurios0;
import com.very.wraq.customized.uniform.mana.ManaCurios2;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementBow;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSceptre;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSword;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.instance.Castle.CastleSword;
import com.very.wraq.series.instance.Devil.DevilAttackArmor;
import com.very.wraq.series.instance.Devil.DevilSwiftArmor;
import com.very.wraq.series.instance.Moon.Equip.*;
import com.very.wraq.series.instance.Taboo.TabooManaArmor;
import com.very.wraq.series.nether.Equip.*;
import com.very.wraq.series.nether.Equip.PiglinHelmet.PiglinHelmet;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorBoots;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorChest;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorHelmet;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorLeggings;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Crest.ForestCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Mine.Crest.MineCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Crest.PlainCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Sceptre.PlainSceptre4;
import com.very.wraq.series.overWorld.MainStory_I.Snow.Crest.SnowCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Volcano.Crest.VolcanoCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Crest.LakeCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes.LakeRune0;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes.LakeRune1;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes.LakeRune3;
import com.very.wraq.series.overWorld.MainStory_II.Evoker.Crest.ManaCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_II.Evoker.Runes.ManaRune3;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorBoots;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorChest;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorHelmet;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorLeggings;
import com.very.wraq.series.overWorld.MainStory_II.Sky.BossItems.SkyBoss;
import com.very.wraq.series.overWorld.MainStory_II.Sky.Crest.SkyCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_II.Sky.SkyBow;
import com.very.wraq.series.overWorld.SakuraSeries.Boss2.GoldenBook;
import com.very.wraq.series.overWorld.SakuraSeries.EarthMana.EarthPower;
import com.very.wraq.series.worldSoul.SoulEquipAttribute;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerAttributes {

    public static double PlayerAttackDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        double BaseAttackDamage = 0.0d;
        double ExDamage = 0.0d;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag data0 = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            data0 = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("attackdamage"))
            BaseAttackDamage += stackmainhandtag.getDouble("attackdamage");
        if (Utils.AttackDamage.containsKey(boots)) BaseAttackDamage += Utils.AttackDamage.get(boots);
        if (Utils.AttackDamage.containsKey(leggings)) BaseAttackDamage += Utils.AttackDamage.get(leggings);
        if (Utils.AttackDamage.containsKey(chest)) BaseAttackDamage += Utils.AttackDamage.get(chest);
        if (Utils.AttackDamage.containsKey(helmet)) BaseAttackDamage += Utils.AttackDamage.get(helmet);

        if (helmetTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
            BaseAttackDamage += helmetTag.getInt(StringUtils.RandomAttribute.AttackDamage);
            if (helmetTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.AttackDamage));
        }
        if (chestTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
            BaseAttackDamage += chestTag.getInt(StringUtils.RandomAttribute.AttackDamage);
            if (chestTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.AttackDamage));
        }
        if (leggingsTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
            BaseAttackDamage += leggingsTag.getInt(StringUtils.RandomAttribute.AttackDamage);
            if (leggingsTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.AttackDamage));
        }
        if (bootsTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
            BaseAttackDamage += bootsTag.getInt(StringUtils.RandomAttribute.AttackDamage);
            if (bootsTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.AttackDamage));
        }

        if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackDamage.containsKey(mainhand))
            BaseAttackDamage += Utils.AttackDamage.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.AttackDamage.containsKey(offhand))
            ExDamage += Utils.AttackDamage.get(offhand);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackDamage.containsKey(mainhand) && data0.contains("Forging"))
            ExDamage += Compute.ForgingValue(data0, Utils.AttackDamage.get(mainhand));

        // Fixed:防具攻击强化失效
        if (Utils.ArmorTag.containsKey(helmet) && Utils.AttackDamage.containsKey(helmet) && helmetTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(helmetTag, Utils.AttackDamage.get(helmet));
        if (Utils.ArmorTag.containsKey(chest) && Utils.AttackDamage.containsKey(chest) && chestTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(chestTag, Utils.AttackDamage.get(chest));
        if (Utils.ArmorTag.containsKey(leggings) && Utils.AttackDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(leggingsTag, Utils.AttackDamage.get(leggings));
        if (Utils.ArmorTag.containsKey(boots) && Utils.AttackDamage.containsKey(boots) && bootsTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(bootsTag, Utils.AttackDamage.get(boots));

        if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackDamage.containsKey(mainhand) && data0.contains(StringUtils.KillCount.KillCount)) {
            int killCount = data0.getInt(StringUtils.KillCount.KillCount);
            if (killCount >= 100000) killCount = 100000;
            ExDamage += Utils.AttackDamage.get(mainhand) * 0.5 * (killCount / 100000.0);
        }
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 0)
            ExDamage += player.getAttribute(Attributes.MAX_HEALTH).getValue() * 0.025 * Compute.EndRune3Judge(player, 0);
        if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 1)
            ExDamage += ((player.getMaxHealth() - player.getHealth()) / player.getMaxHealth()) * BaseAttackDamage * 0.25 * Compute.EndRune3Judge(player, 1);
        if (helmetTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(helmetTag);
        if (chestTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(chestTag);
        if (leggingsTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            ExDamage += Compute.ItemBaseDamageGems(stackmainhandtag);
        if (Compute.ArmorCount.Volcano(player) >= 2) ExDamage += BaseAttackDamage * 0.15F;
        if (Compute.ArmorCount.ObsiMana(player) >= 4) ExDamage += BaseAttackDamage * 0.15F;
        if (data.contains("Sword")) ExDamage += BaseAttackDamage * (data.getInt("Sword") / 1000000.0d);
        if (data.contains("Barker")) ExDamage += BaseAttackDamage * ((data.getInt("Barker") / 100000.0d) * 0.05);
        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) ExDamage += BaseAttackDamage * 0.1;
        if (data.contains("ManaSwordActive") && data.getInt("ManaSwordActive") > 0)
            ExDamage += data.getInt("ManaSwordActive");

        if (Utils.PiglinPowerLastTick.containsKey(player) && Utils.PiglinPowerLastTick.get(player) > TickCount
                && Utils.PiglinPowerAp.containsKey(player) && Utils.PiglinPowerNum.containsKey(player)) {
            ExDamage += 0.03 * Utils.PiglinPowerAp.get(player) * Utils.PiglinPowerNum.get(player);
        }

        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                ExDamage += i + 1;
            }
        }
        if (Compute.ArmorCount.Sky(player) > 0) {
            if (player.getHealth() / player.getMaxHealth() > 0.8)
                ExDamage += BaseAttackDamage * 1 * Compute.SkySuitEffectRate(player);
            else if (player.getHealth() / player.getMaxHealth() > 0.4)
                ExDamage += BaseAttackDamage * 0.4 * Compute.SkySuitEffectRate(player);
        }
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 0)
            ExDamage += BaseAttackDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 1)
            ExDamage += BaseAttackDamage * 0.40 + 40;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill0) && data.getInt(StringUtils.VolcanoSwordSkill.Skill0) > TickCount)
            ExDamage += 10;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill1) && data.getInt(StringUtils.VolcanoSwordSkill.Skill1) > TickCount)
            ExDamage += 15;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill2) && data.getInt(StringUtils.VolcanoSwordSkill.Skill2) > TickCount)
            ExDamage += 20;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill3) && data.getInt(StringUtils.VolcanoSwordSkill.Skill3) > TickCount)
            ExDamage += 30;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill4) && data.getInt(StringUtils.VolcanoSwordSkill.Skill4) > TickCount)
            ExDamage += 75;
        if (mainhand.equals(ModItems.GoldSword0.get())) ExDamage += data.getDouble("VB") / 10000.0;

        if (Compute.SwordSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.SwordSkillNum.Skill2) && data.getInt(StringUtils.SwordSkillNum.Skill2) > TickCount) {
            ExDamage += BaseAttackDamage * Compute.SwordSkillLevelGet(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）‘

        if (Compute.BowSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.BowSkillNum.Skill2) && data.getInt(StringUtils.BowSkillNum.Skill2) > TickCount) {
            ExDamage += BaseAttackDamage * Compute.BowSkillLevelGet(data, 2) * 0.02;
        } // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (Compute.SwordSkillLevelGet(data, 5) > 0 && data.contains(StringUtils.SwordSkillNum.Skill5) && data.getInt(StringUtils.SwordSkillNum.Skill5) > TickCount) {
            ExDamage += BaseAttackDamage * Compute.SwordSkillLevelGet(data, 5) * 0.015;
        } // 剑术-狂暴（造成暴击后，提升1%攻击力，持续3s）

        if (Compute.BowSkillLevelGet(data, 5) > 0 && data.contains(StringUtils.BowSkillNum.Skill5) && data.getInt(StringUtils.BowSkillNum.Skill5) > TickCount) {
            ExDamage += BaseAttackDamage * Compute.BowSkillLevelGet(data, 5) * 0.01;
        } // 弓术-狂暴（造成暴击后，提升1%攻击力，持续5s）

        if (Compute.SwordSkillLevelGet(data, 6) > 0 && Utils.SwordSkill6Map.containsKey(player) && Utils.SwordSkill6Map.get(player).getTime() > TickCount) {
            ExDamage += BaseAttackDamage * Compute.SwordSkillLevelGet(data, 6) * 0.003 * Utils.SwordSkill6Map.get(player).getCount();
        } // 完美（持续造成暴击，将提供至多30%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）

        if (Compute.BowSkillLevelGet(data, 6) > 0 && Utils.BowSkill6Map.containsKey(player) && Utils.BowSkill6Map.get(player).getTime() > TickCount) {
            ExDamage += BaseAttackDamage * Compute.BowSkillLevelGet(data, 6) * 0.005 * Utils.BowSkill6Map.get(player).getCount();
        } // 完美（持续的命中目标的箭矢，将提供至多3%攻击力，持续10s，在十次命中后达最大值，在未命中时重置层数）

        if (Compute.BowSkillLevelGet(data, 8) > 0 && Utils.BowTag.containsKey(mainhand)) {
            ExDamage += BaseAttackDamage * Compute.BowSkillLevelGet(data, 8) * 0.02;
        } // 锻弦-力量（手持弓时，获得2%攻击力）

        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            ExDamage += Point;
        } // 能力

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            ExDamage += 50;
        // 矿工裤被动

        if (Compute.ArmorCount.Mine(player) >= 4) ExDamage += BaseAttackDamage * 0.3;

        if (data.getInt(StringUtils.Crest.Volcano.Crest0) > 0)
            ExDamage += VolcanoCrestAttributes.ExAttackDamage[0] * data.getInt(StringUtils.Crest.Volcano.Crest0);
        if (data.getInt(StringUtils.Crest.Volcano.Crest1) > 0)
            ExDamage += VolcanoCrestAttributes.ExAttackDamage[1] * data.getInt(StringUtils.Crest.Volcano.Crest1);
        if (data.getInt(StringUtils.Crest.Volcano.Crest2) > 0)
            ExDamage += VolcanoCrestAttributes.ExAttackDamage[2] * data.getInt(StringUtils.Crest.Volcano.Crest2);
        if (data.getInt(StringUtils.Crest.Volcano.Crest3) > 0)
            ExDamage += VolcanoCrestAttributes.ExAttackDamage[3] * data.getInt(StringUtils.Crest.Volcano.Crest3);
        if (data.getInt(StringUtils.Crest.Volcano.Crest4) > 0)
            ExDamage += VolcanoCrestAttributes.ExAttackDamage[4] * data.getInt(StringUtils.Crest.Volcano.Crest4);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.SwordTag.containsKey(mainhand) || Utils.BowTag.containsKey(mainhand)))
            ExDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.AttackDamage;

        if (data.contains("GemSAttack")) ExDamage += data.getDouble("GemSAttack");

        if (data.contains("snowRune") && data.getInt("snowRune") == 1) {
            PlayerCritRate(player);
            PlayerCritDamage(player);
            ExDamage += BaseAttackDamage * (data.getDouble("snowRune1Rate") + data.getDouble("snowRune1Damage")) * 0.8 * Compute.EndRune3Judge(player, 3);
        }

        ExDamage += Compute.SArmorAttackDamage(player);

        if (Utils.PlayerAttackRingMap.containsKey(player))
            ExDamage += Utils.PlayerAttackRingMap.get(player);

        if (Utils.PlayerSpringRingAttackAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            ExDamage += Utils.PlayerSpringRingAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringHandAttackAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            ExDamage += Utils.PlayerSpringHandAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringBeltAttackAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            ExDamage += Utils.PlayerSpringBeltAttackAttribute.get(player);
        }

        if (Utils.PlayerFireWorkGunEffect.containsKey(player) && Utils.PlayerFireWorkGunEffect.get(player) > TickCount) {
            ExDamage += BaseAttackDamage * 0.1;
        } //新春礼炮

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (SwordSkill > Math.max(BowSkill, ManaSkill))
                ExDamage += BaseAttackDamage * (Utils.SpringScaleEffect.get(player) + 1) * 0.1;
        } //年兽鳞片

        if (Utils.YiZhiXiangLi != null && Utils.YiZhiXiangLi.equals(player) && Utils.YiZhiXiangLiEffect > TickCount)
            ExDamage += 360;

        if (Utils.LiuLiXianPlayerEffectTickMap.containsKey(player) && Utils.LiuLiXianPlayerEffectTickMap.get(player) > TickCount) {
            ExDamage += BaseAttackDamage * Utils.LiuLiXianPlayerEffectMap.get(player) * 0.05;
        }

        if (mainhand.equals(ModItems.SoulSword.get())) {
            if (stackmainhandtag.contains(StringUtils.SoulSwordKillCount)) {
                ExDamage += Math.min(256, stackmainhandtag.getInt(StringUtils.SoulSwordKillCount) / 100);
            }
        } // 本源被动

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            ExDamage += BaseAttackDamage * 0.1;
        }

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) ExDamage += 100;

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.GuangYiBow.get()) && Utils.GuangYiIsInMode && TickCount - Utils.GuangYiModeStartTick > 100) {
            ExDamage += BaseAttackDamage;
        }

        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.CrushCuriosLastTick > TickCount) {
            ExDamage += BaseAttackDamage * Utils.CrushCuriosCounts * 0.5 / 12.0;
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get())) {
            ExDamage += player.getMaxHealth() * 0.15;
        }

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) ExDamage += 400;

        ExDamage += DevilAttackArmor.DevilAttackArmorPassiveExDamage(player);
        ExDamage += DevilSwiftArmor.DevilSwiftArmorPassiveExDamage(player);

        ExDamage += BaseAttackDamage * EarthPower.PlayerDamageEnhance(player);

        ExDamage += Compute.CuriosAttribute.AttributeValue(player, Utils.AttackDamage, StringUtils.CuriosAttribute.AttackDamage); // 新版饰品属性加成

        ExDamage += LeiyanCurios.ExDamage(player);
        ExDamage += MyMissionBow.ExDamage(player);
        ExDamage += MyMissionCurios.AttackDamageUp(player);
        ExDamage += MyMissionCurios1.AttackDamageUp(player);
        ExDamage += LeiyanBow.ExAttackDamage(player);

        ExDamage += Compute.PassiveEquip.AttributeGet(player, Utils.AttackDamage); // 器灵属性加成
        ExDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExAttackDamage);
        ExDamage += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExAttackDamage);
        ExDamage += MoonSword.ExAttackDamage(player);
        ExDamage += MoonSceptre.ExManaDamage(player);
        ExDamage += QiFuCurios1.ExAttackDamage(player);
        ExDamage += ZuoSiCurios.ExAttackOrManaDamage(player, true);
        ExDamage += LifeElementSword.ExAttackDamage(player);
        ExDamage += LifeElementBow.ExAttackDamage(player);
        ExDamage += LiulixianCurios4.attackDamageUp(player);
        // 请在上方添加

        double TotalAttackDamage = BaseAttackDamage + ExDamage;

        TotalAttackDamage *= LiulixianCurios3.ExAttackDamageUp(player);
        TotalAttackDamage *= YxwgCurios1.AttackDamageUp(player);
        TotalAttackDamage *= (1 + MoonShield.damageEnhance(player));
        TotalAttackDamage *= (1 + MoonKnife.damageEnhance(player));
        TotalAttackDamage *= (1 + YxwgBow.ExDamage(player));
        TotalAttackDamage *= Compute.playerFantasyAttributeEnhance(player);
        TotalAttackDamage *= YxwgCurios2.Passive1AttackDamageUp(player);
        TotalAttackDamage *= YxwgCurios2.Passive2AttackDamageUp(player);
        TotalAttackDamage *= (1 + QiFuCurios.CountAttackDamageUp(player));
        TotalAttackDamage *= BlackFeisaCurios4.AttackDamageUp(player);
        TotalAttackDamage *= HgjCurios.AttackDamageEnhance(player);
        TotalAttackDamage *= AttackCurios1.playerAttackDamageEnhance(player);
        TotalAttackDamage *= LittleartCurios.attackDamageEnhance(player);
        TotalAttackDamage *= ShaoFengCurios.attackDamageEnhance(player);

        data.putDouble("NetherRuneDamageCompute", TotalAttackDamage * 0.6f * Compute.EndRune3Judge(player, 2));

        if (data.contains("NetherRune") && data.getInt("NetherRune") == 1) {
            PlayerManaDamage(player);
            TotalAttackDamage += data.getDouble("NetherRuneManaDamageCompute");
        }
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 0) return 0;
        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0)
            return TotalAttackDamage * 0.5f;
        return TotalAttackDamage;
    }

    public static double PlayerCritRate(Player player) {
        CompoundTag data = player.getPersistentData();
        double CriticalHitRate = 0.0d;
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
        if (helmetTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(helmetTag);
        if (chestTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(chestTag);
        if (leggingsTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(leggingsTag);
        if (bootsTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            CriticalHitRate += Compute.ItemCritRateGems(stackmainhandtag);
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticalrate"))
            CriticalHitRate += stackmainhandtag.getDouble("criticalrate");
        if (Utils.CritRate.containsKey(boots)) CriticalHitRate += Utils.CritRate.get(boots);
        if (Utils.CritRate.containsKey(leggings)) CriticalHitRate += Utils.CritRate.get(leggings);
        if (Utils.CritRate.containsKey(chest)) CriticalHitRate += Utils.CritRate.get(chest);
        if (Utils.CritRate.containsKey(helmet)) CriticalHitRate += Utils.CritRate.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.CritRate.containsKey(mainhand))
            CriticalHitRate += Utils.CritRate.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.CritRate.containsKey(offhand))
            CriticalHitRate += Utils.CritRate.get(offhand);
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                CriticalHitRate += (i + 1) * 0.005;
            }
        }
        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 0)
            CriticalHitRate += 0.2;
        if (player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 1)
            CriticalHitRate += 0.4;
        if (data.contains("GemSCritRate")) CriticalHitRate += data.getDouble("GemSCritRate");
        CriticalHitRate += Compute.SArmorCritRate(player);
        if (Compute.BowSkillLevelGet(data, 11) > 0 && Utils.BowTag.containsKey(mainhand)) {
            CriticalHitRate += Compute.BowSkillLevelGet(data, 11) * 0.02;
        } // 锻弦-平衡（手持弓时，获得额外2%暴击几率）

        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            CriticalHitRate += Point * 0.001;
        }

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.SwordTag.containsKey(mainhand) || Utils.BowTag.containsKey(mainhand)))
            CriticalHitRate +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritRate;

        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            CriticalHitRate += 0.05 + PlayerManaRecover(player) * 0.01 * Compute.ManaSkillLevelGet(data, 10) * 0.1;
        } // 法术专精-力凝魔核

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.GoldenShield.get())) {
            CriticalHitRate += PlayerDefence(player) / 20000;
        } // 黄金圣盾

        CriticalHitRate += QiFuCurios.Buff1CritRate(player);

        CriticalHitRate += Compute.CuriosAttribute.AttributeValue(player, Utils.CritRate, StringUtils.CuriosAttribute.CritRate); // 新版饰品属性加成

        CriticalHitRate += YxwgBow.CritRateUp(player);
        CriticalHitRate += YxwgCurios2.Passive1ExCritRate(player);
        CriticalHitRate += QiFuCurios.CountExCritRate(player);
        CriticalHitRate += AttackCurios2.playerCritRateUp(player);
        CriticalHitRate += BowCurios2.playerCritRateUp(player);
        // 请在上方添加
        CriticalHitRate *= Compute.playerFantasyAttributeEnhance(player);
        CriticalHitRate *= LiulixianCurios4.playerAttributeDown(player);

        if (data.contains("snowRune") && data.getInt("snowRune") == 1) {
            data.putDouble("snowRune1Rate", CriticalHitRate);
            return 0;
        }
        return CriticalHitRate;
    }

    public static double PlayerCritDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double CritDamage = 0.0d;
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
        if (helmetTag.contains("Gems1")) CritDamage += Compute.ItemCritDamageGems(helmetTag);
        if (chestTag.contains("Gems1")) CritDamage += Compute.ItemCritDamageGems(chestTag);
        if (leggingsTag.contains("Gems1")) CritDamage += Compute.ItemCritDamageGems(leggingsTag);
        if (bootsTag.contains("Gems1")) CritDamage += Compute.ItemCritDamageGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            CritDamage += Compute.ItemCritDamageGems(stackmainhandtag);
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticaldamage"))
            CritDamage += stackmainhandtag.getDouble("criticaldamage");
        if (Utils.CritDamage.containsKey(boots)) CritDamage += Utils.CritDamage.get(boots);
        if (Utils.CritDamage.containsKey(leggings)) CritDamage += Utils.CritDamage.get(leggings);
        if (Utils.CritDamage.containsKey(chest)) CritDamage += Utils.CritDamage.get(chest);
        if (Utils.CritDamage.containsKey(helmet)) CritDamage += Utils.CritDamage.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.CritDamage.containsKey(mainhand))
            CritDamage += Utils.CritDamage.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.CritDamage.containsKey(offhand))
            CritDamage += Utils.CritDamage.get(offhand);
        if (data.contains("Bow")) CritDamage += (data.getInt("Bow") / 30000.0d) * 0.3F;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill0) && data.getInt(StringUtils.VolcanoSwordSkill.Skill0) > TickCount)
            CritDamage += 0.5;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill1) && data.getInt(StringUtils.VolcanoSwordSkill.Skill1) > TickCount)
            CritDamage += 0.6;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill2) && data.getInt(StringUtils.VolcanoSwordSkill.Skill2) > TickCount)
            CritDamage += 0.7;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill3) && data.getInt(StringUtils.VolcanoSwordSkill.Skill3) > TickCount)
            CritDamage += 0.9;
        if (data.contains(StringUtils.VolcanoSwordSkill.Skill4) && data.getInt(StringUtils.VolcanoSwordSkill.Skill4) > TickCount)
            CritDamage += 1.2;
        if (data.contains("plaingems") && data.getBoolean("plaingems")) CritDamage += 0.1;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) CritDamage += 0.1;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) CritDamage += 0.1;
        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) CritDamage += 0.1;
        if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 3)
            CritDamage += 0.5 * Compute.EndRune3Judge(player, 1);
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                CritDamage += (i + 1) * 0.01;
            }
        }
        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 0)
            CritDamage += 0.4;
        if (player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 1)
            CritDamage += 0.8;
        if (data.contains("GemSCritDamage")) CritDamage += data.getDouble("GemSCritDamage");
        CritDamage += Compute.SArmorCritDamage(player);

        if (Compute.BowSkillLevelGet(data, 7) > 0 && Utils.BowTag.containsKey(mainhand)) {
            CritDamage += Compute.BowSkillLevelGet(data, 7) * 0.1;
        } // 锻矢-锋利（手持弓时，获得10%暴击伤害）

        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            CritDamage += Point * 0.01;
        } // 能力

        if (mainhand.equals(ModItems.SkyBossBow.get())) {
            CritDamage += 0.08 * Compute.EntropyRate(data.getInt(StringUtils.Entropy.Sky));
        }

        if (Compute.ArmorCount.Volcano(player) >= 4) CritDamage += 0.35;

        if (data.getInt(StringUtils.Crest.Sky.Crest0) > 0)
            CritDamage += SkyCrestAttributes.CritDamage[0] * data.getInt(StringUtils.Crest.Sky.Crest0);
        if (data.getInt(StringUtils.Crest.Sky.Crest1) > 0)
            CritDamage += SkyCrestAttributes.CritDamage[1] * data.getInt(StringUtils.Crest.Sky.Crest1);
        if (data.getInt(StringUtils.Crest.Sky.Crest2) > 0)
            CritDamage += SkyCrestAttributes.CritDamage[2] * data.getInt(StringUtils.Crest.Sky.Crest2);
        if (data.getInt(StringUtils.Crest.Sky.Crest3) > 0)
            CritDamage += SkyCrestAttributes.CritDamage[3] * data.getInt(StringUtils.Crest.Sky.Crest3);
        if (data.getInt(StringUtils.Crest.Sky.Crest4) > 0)
            CritDamage += SkyCrestAttributes.CritDamage[4] * data.getInt(StringUtils.Crest.Sky.Crest4);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.SwordTag.containsKey(mainhand) || Utils.BowTag.containsKey(mainhand)))
            CritDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.CritDamage;

        if (Utils.IceSwordEffectMap.containsKey(player) && Utils.IceSwordEffectMap.get(player) > TickCount) {
            CritDamage += Utils.IceSwordEffectNumMap.get(player) / 1200;
        } //冰霜剑

        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            CritDamage += (100 + PlayerMaxMana(player)) * 0.01 * Compute.ManaSkillLevelGet(data, 10) * 0.1;
        } // 法术专精-力凝魔核

        if (mainhand.equals(ModItems.LXYZOSword.get())) {
            if (PlayerAttributes.PlayerDefence(player) > 224)
                CritDamage += 1.12 + PlayerAttributes.PlayerDefence(player) * 0.1 / 224;
        } // LXYZO

        if (Utils.YiZhiXiangLi != null && Utils.YiZhiXiangLi.equals(player) && Utils.YiZhiXiangLiEffect > TickCount) {
            CritDamage += 3.6;
        }

        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1CritDamage1Tick > TickCount)
            CritDamage += 4;
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1CritDamage2Tick > TickCount)
            CritDamage += Utils.Crush1CritDamage2Count * 2.5;

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.GoldenShield.get())) {
            if (PlayerCritRate(player) > 1) CritDamage += (PlayerCritRate(player) - 1) * 2;
        } // 黄金圣盾

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                CritDamage += data.getDouble("HealthStealAfterCompute") * 5;
            }
        } // 封魔者法盾

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaKnife.get())) {
            CritDamage += data.getDouble("HealthStealAfterCompute") * 3;
        } // 猎魔者小刀

        CritDamage += GoldenBook.GoldenBookCritDamage(player);
        CritDamage += QiFuCurios.Buff1CritDamage(player);

        CritDamage += Compute.CuriosAttribute.AttributeValue(player, Utils.CritDamage, StringUtils.CuriosAttribute.CritDamage); // 新版饰品属性加成

        CritDamage += MyMissionBow.ExCritDamage(player);
        CritDamage += YxwgBow.CritDamageUp(player);
        CritDamage += MyMissionCurios.CritDamageUp(player);
        CritDamage += MyMissionCurios1.CritDamageUp(player);
        CritDamage += Compute.PassiveEquip.AttributeGet(player, Utils.CritDamage); // 器灵属性加成
        CritDamage += LeiyanBow.CritDamageUp(player);
        CritDamage += FengXiaoJuCurios1.ExCritDamage(player);
        CritDamage += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExCritDamage);
        CritDamage += LakeRune1.ExCritDamage(player);
        CritDamage += YxwgCurios2.Passive1ExCritDamage(player);
        CritDamage += YxwgCurios2.Passive2ExCritDamage(player);
        CritDamage += YxwgBow.ExCritDamage(player);
        CritDamage += QiFuCurios.CountExCritDamage(player);
        CritDamage += QiFuCurios.Buff2ExCritDamage(player);
        CritDamage += WcndymlgbCurios.ExCritDamage(player);
        CritDamage += AttackCurios1.playerCritDamageUp(player);
        CritDamage += LittleartCurios.critDamageUp(player);
        CritDamage += ShaoFengCurios.critDamageUp(player);
        // 请在上方添加

        CritDamage *= Compute.playerFantasyAttributeEnhance(player);
        CritDamage *= AttackCurios0.PlayerFinalCritDamageEnhance(player);
        CritDamage *= HeihuangCurios.Passive2CritDamageEnhance(player);
        CritDamage *= AttackCurios2.playerCritDamageEnhance(player);
        CritDamage *= BowCurios2.playerCritDamageEnhance(player);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                data.putDouble("CritDamageAfterCompute", CritDamage);
                return 0;
            }
        } // 封魔者法盾

        if (data.contains("snowRune") && data.getInt("snowRune") == 1) {
            data.putDouble("snowRune1Damage", CritDamage);
            return 0;
        }

        return 1 + CritDamage;
    }

    public static double PlayerMovementSpeed(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double SpeedUp = 0;
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("speedup"))
            SpeedUp += stackmainhandtag.getDouble("speedup");
        if (Utils.MovementSpeed.containsKey(boots)) SpeedUp += Utils.MovementSpeed.get(boots);
        if (Utils.MovementSpeed.containsKey(leggings)) SpeedUp += Utils.MovementSpeed.get(leggings);
        if (Utils.MovementSpeed.containsKey(chest)) SpeedUp += Utils.MovementSpeed.get(chest);
        if (Utils.MovementSpeed.containsKey(helmet)) SpeedUp += Utils.MovementSpeed.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.MovementSpeed.containsKey(mainhand))
            SpeedUp += Utils.MovementSpeed.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.MovementSpeed.containsKey(offhand))
            SpeedUp += Utils.MovementSpeed.get(offhand);
        if (helmetTag.contains("Gems1")) SpeedUp += Compute.ItemMovementSpeedUpGems(helmetTag);
        if (chestTag.contains("Gems1")) SpeedUp += Compute.ItemMovementSpeedUpGems(chestTag);
        if (leggingsTag.contains("Gems1")) SpeedUp += Compute.ItemMovementSpeedUpGems(leggingsTag);
        if (bootsTag.contains("Gems1")) SpeedUp += Compute.ItemMovementSpeedUpGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            SpeedUp += Compute.ItemMovementSpeedUpGems(stackmainhandtag);
        if (data.contains("Green") && data.getInt("Green") == 2)
            SpeedUp += (((player.getMaxHealth() - player.getHealth()) * 1.0 / player.getMaxHealth()) / 2.0);
        if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 1 && data.contains(StringUtils.ForestRune.ForestRune1) && data.getInt(StringUtils.ForestRune.ForestRune1) > TickCount)
            SpeedUp += 0.5F * Compute.EndRune3Judge(player, 0);
        if (data.contains(StringUtils.LakeSwordSkill) && data.getInt(StringUtils.LakeSwordSkill) > TickCount)
            SpeedUp += 0.5F;
        if (data.contains(StringUtils.PlainBowSkill) && data.getInt(StringUtils.PlainBowSkill) > TickCount)
            SpeedUp += 0.4F;
        if (data.contains("ManaRune") && data.getInt("ManaRune") == 0 && data.contains("MANA")) SpeedUp +=
                0.5F * ((data.getDouble("MAXMANA") - data.getDouble("MANA")) / data.getDouble("MAXMANA"));
        if (Utils.PiglinPowerLastTick.containsKey(player) && Utils.PiglinPowerLastTick.get(player) > TickCount
                && Utils.PiglinPowerAp.containsKey(player) && Utils.PiglinPowerNum.containsKey(player)) {
            SpeedUp += 0.1 * Utils.PiglinPowerNum.get(player);
        }
        if (data.contains("QuartzSabreSpeedUp") && data.getInt("QuartzSabreSpeedUp") > 0) SpeedUp += 0.5F;
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                SpeedUp += (i + 1) * 0.005;
            }
        }
        if (Compute.ArmorCount.Sky(player) > 0 && player.getHealth() / player.getMaxHealth() > 0.8)
            SpeedUp += 0.8 * Compute.SkySuitEffectRate(player);
        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 0)
            SpeedUp += 0.5;
        if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 1)
            SpeedUp += 1.0;
        if (data.contains("GemSSpeed")) SpeedUp += data.getDouble("GemSSpeed");
        if (mainhand.equals(ModItems.GoldSword0.get())) SpeedUp += data.getDouble("VB") / 1000000.0;

        if (Compute.SwordSkillLevelGet(data, 9) > 0 && Utils.SwordTag.containsKey(mainhand)) {
            SpeedUp += Compute.SwordSkillLevelGet(data, 9) * 0.08;
        } // 剑舞（手持近战武器时，获得8%额外移动速度）

        if (Compute.BowSkillLevelGet(data, 1) > 0 && Utils.BowTag.containsKey(mainhand)) {
            SpeedUp += Compute.BowSkillLevelGet(data, 1) * 0.02;
        } // 原野护符（持有弓时，获得2%的额外移动速度）

        if (Compute.BowSkillLevelGet(data, 9) > 0 && Utils.BowTag.containsKey(mainhand)) {
            SpeedUp += Compute.BowSkillLevelGet(data, 9) * 0.1;
        } // 猎手本能（手持弓时，获得10%额外移动速度）

        if (Compute.ManaSkillLevelGet(data, 9) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            SpeedUp += Compute.ManaSkillLevelGet(data, 9) * 0.08;
        } // 法师（手持法杖时，获得8%额外移动速度）

        int FlexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            int Point = FlexibilityAbilityPoint + (FlexibilityAbilityPoint / 10) * 5;
            SpeedUp += Point * 0.01;
        } // 能力

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            SpeedUp += 0.4;
        // 矿工裤被动

        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > TickCount)
            SpeedUp += 0.6;
        // 妖刀-樱

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge)) SpeedUp +=
                stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MovementSpeed;

        if (stackmainhandtag.contains(StringUtils.ManaCore.ManaCore) && stackmainhandtag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            SpeedUp += 0.3;
        }

        if (Compute.ArmorCount.Lake(player) >= 4) SpeedUp += 0.35;
        if (Compute.ArmorCount.Mine(player) >= 4) SpeedUp -= 0.5;

        if (Utils.PlayerSpringBeltMovementAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            SpeedUp += Utils.PlayerSpringBeltMovementAttribute.get(player);
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.XiangLiSmoke.get())) SpeedUp += 1.5;

        SpeedUp += Compute.CuriosAttribute.AttributeValue(player, Utils.MovementSpeed, StringUtils.CuriosAttribute.MovementSpeed); // 新版饰品属性加成
        SpeedUp += LiulixianCurios2.ExMovementSpeed(player);
        SpeedUp += QiFuCurios1.Passive1ExMovementSpeed(player);
        SpeedUp += QiFuCurios1.Passive2ExMovementSpeed(player);
        SpeedUp += QiFuCurios1.Passive3ExMovementSpeed(player);
        SpeedUp += Compute.PassiveEquip.AttributeGet(player, Utils.MovementSpeed); // 器灵属性加成
        SpeedUp += LittleartCurios.movementSpeedUp(player);
        // 请在上方添加

        SpeedUp *= LiulixianCurios3.MovementEnhance(player);
        SpeedUp *= (1 + YxwgBow.ExMovementSpeed(player));

        SpeedUp *= Compute.playerFantasyAttributeEnhance(player);

        return SpeedUp;
    }

    public static double PlayerExpUp(Player player) {
        CompoundTag data = player.getPersistentData();
        double ExpUp = 0.0d;
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

        if (Utils.ExpUp.containsKey(boots)) ExpUp += Utils.ExpUp.get(boots);
        if (Utils.ExpUp.containsKey(leggings)) ExpUp += Utils.ExpUp.get(leggings);
        if (Utils.ExpUp.containsKey(chest)) ExpUp += Utils.ExpUp.get(chest);
        if (Utils.ExpUp.containsKey(helmet)) ExpUp += Utils.ExpUp.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ExpUp.containsKey(mainhand))
            ExpUp += Utils.ExpUp.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ExpUp.containsKey(offhand))
            ExpUp += Utils.ExpUp.get(offhand);
        if (data.contains("plaingems") && data.getBoolean("plaingems")) ExpUp += 0.1;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) ExpUp += 0.1;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) ExpUp += 0.1;
        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) ExpUp += 0.1;
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                ExpUp += (i + 1) * 0.01;
            }
        }
        if (data.contains("GemSExpImprove")) ExpUp += data.getDouble("GemSExpImprove");
        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            ExpUp += Point * 0.01;
        }

        if (Utils.PlayerSpringRingExpUpAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            ExpUp += Utils.PlayerSpringRingExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringHandExpUpAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            ExpUp += Utils.PlayerSpringHandExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringBeltExpUpAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            ExpUp += Utils.PlayerSpringBeltExpUpAttribute.get(player);
        }

        if (Utils.PlayerSpringBraceletExpUpAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            ExpUp += Utils.PlayerSpringBraceletExpUpAttribute.get(player);
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("Gems1")) ExpUp += Compute.ItemExpUpGems(helmetTag);
        if (chestTag.contains("Gems1")) ExpUp += Compute.ItemExpUpGems(chestTag);
        if (leggingsTag.contains("Gems1")) ExpUp += Compute.ItemExpUpGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ExpUp += Compute.ItemExpUpGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            ExpUp += Compute.ItemExpUpGems(stackmainhandtag);

        ExpUp += Compute.CuriosAttribute.AttributeValue(player, Utils.ExpUp, StringUtils.CuriosAttribute.ExpUp); // 新版饰品属性加成

        ExpUp += Compute.PassiveEquip.AttributeGet(player, Utils.ExpUp); // 器灵属性加成
        ExpUp += BlackFeisaCurios4.ExpUp(player);
        // 请在上方添加
        ExpUp *= Compute.playerFantasyAttributeEnhance(player);

        return ExpUp;
    }

    public static double PlayerDefence(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double Defence = 0.0d;
        double ExDefence = 0.0d;
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
        if (Utils.Defence.containsKey(boots)) Defence += Utils.Defence.get(boots);
        if (Utils.Defence.containsKey(leggings)) Defence += Utils.Defence.get(leggings);
        if (Utils.Defence.containsKey(chest)) Defence += Utils.Defence.get(chest);
        if (Utils.Defence.containsKey(helmet)) Defence += Utils.Defence.get(helmet);

        if (helmetTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += helmetTag.getInt(StringUtils.RandomAttribute.Defence);
            if (helmetTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.Defence));
        }
        if (chestTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += chestTag.getInt(StringUtils.RandomAttribute.Defence);
            if (chestTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.Defence));
        }
        if (leggingsTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += leggingsTag.getInt(StringUtils.RandomAttribute.Defence);
            if (leggingsTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.Defence));
        }
        if (bootsTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += bootsTag.getInt(StringUtils.RandomAttribute.Defence);
            if (bootsTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.Defence));
        }

        if (helmetTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(helmetTag);
        if (chestTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(chestTag);
        if (leggingsTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(bootsTag);
        if (stackmainhandtag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(stackmainhandtag);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.Defence.containsKey(mainhand))
            ExDefence += Utils.Defence.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.Defence.containsKey(offhand))
            ExDefence += Utils.Defence.get(offhand);
        if (Utils.Defence.containsKey(helmet) && helmetTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(helmetTag, Utils.Defence.get(helmet));
        if (Utils.Defence.containsKey(chest) && chestTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(chestTag, Utils.Defence.get(chest));
        if (Utils.Defence.containsKey(leggings) && leggingsTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(leggingsTag, Utils.Defence.get(leggings));
        if (Utils.Defence.containsKey(boots) && bootsTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(bootsTag, Utils.Defence.get(boots));
        if (Compute.ArmorCount.Forest(player) >= 2) ExDefence += Defence * 0.25;
        if (Compute.ArmorCount.LifeMana(player) >= 4) ExDefence += Defence * 0.25;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) ExDefence += Defence * 0.1;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0)
            ExDefence += Defence * 0.25 + 80;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1)
            ExDefence += Defence * 0.40 + 160;
        if (data.contains("GemSDefence")) ExDefence += data.getDouble("GemSDefence");
        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            ExDefence += 100;
        // 矿工裤被动
        if (Compute.ArmorCount.Mine(player) >= 4) ExDefence += 250;

        if (helmet instanceof PiglinHelmet) {
            PiglinHelmet piglinHelmet = (PiglinHelmet) helmet;
            int Rate = (1 + piglinHelmet.Num) * 5;
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.getPosition(1), 10, 10, 10));
            ExDefence += Math.min(200, Rate * mobList.size());
        }

        if (data.getInt(StringUtils.Crest.Mine.Crest0) > 0)
            ExDefence += MineCrestAttributes.ExDefence[0] * data.getInt(StringUtils.Crest.Mine.Crest0);
        if (data.getInt(StringUtils.Crest.Mine.Crest1) > 0)
            ExDefence += MineCrestAttributes.ExDefence[1] * data.getInt(StringUtils.Crest.Mine.Crest1);
        if (data.getInt(StringUtils.Crest.Mine.Crest2) > 0)
            ExDefence += MineCrestAttributes.ExDefence[2] * data.getInt(StringUtils.Crest.Mine.Crest2);
        if (data.getInt(StringUtils.Crest.Mine.Crest3) > 0)
            ExDefence += MineCrestAttributes.ExDefence[3] * data.getInt(StringUtils.Crest.Mine.Crest3);
        if (data.getInt(StringUtils.Crest.Mine.Crest4) > 0)
            ExDefence += MineCrestAttributes.ExDefence[4] * data.getInt(StringUtils.Crest.Mine.Crest4);

        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) ExDefence += 40;

        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = data.getInt(StringUtils.Ability.Power) + (data.getInt(StringUtils.Ability.Power) / 10) * 5;
            ExDefence += Point * 3;
        } // 能力

        if (Utils.PlayerDefenceRingMap.containsKey(player))
            ExDefence += Utils.PlayerDefenceRingMap.get(player);

        if (Utils.MineShieldEffect.containsKey(player) && Utils.MineShieldEffect.get(player) > TickCount) {
            ExDefence += player.experienceLevel * 5;
        }

        if (Utils.SakuraBowEffectMap.containsKey(player) && Utils.SakuraBowEffectMap.get(player) > TickCount) {
            ExDefence += 100;
        } // 妖弓-樱

        if (Utils.PlayerSpringHandDefenceAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            ExDefence += Utils.PlayerSpringHandDefenceAttribute.get(player);
        }

        if (Utils.MeteoriteDefenceMap.containsKey(player) && Utils.MeteoriteDefenceTimeMap.get(player) > TickCount) {
            ExDefence += Utils.MeteoriteDefenceMap.get(player);
        }

        if (Utils.SnowShieldPlayerEffectTickMap.containsKey(player) && Utils.SnowShieldPlayerEffectTickMap.get(player) > TickCount) {
            ExDefence += Utils.SnowShieldPlayerEffectMap.get(player);
        } // 玉山圆盾

        if (Utils.NetherShieldPlayerDefenceEnhanceTickMap.containsKey(player) && Utils.NetherShieldPlayerDefenceEnhanceTickMap.get(player) > TickCount) {
            ExDefence += Utils.NetherShieldPlayerDefenceEnhanceMap.get(player);
        } // 遗骸铸盾

        ExDefence += Compute.CuriosAttribute.AttributeValue(player, Utils.Defence, StringUtils.CuriosAttribute.Defence); // 新版饰品属性加成

        ExDefence += Compute.PassiveEquip.AttributeGet(player, Utils.Defence); // 器灵属性加成

        ExDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExDefence);
        ExDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExDefence);
        ExDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExDefence);
        ExDefence += LiulixianCurios4.defenceUp(player);
        // 请在上方添加
        double totalDefence = 0;
        totalDefence = Defence + ExDefence;
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                && data.getDouble("ManaDamageAfterCompute") > 10000) {
            totalDefence *= (1.25 + ShangMengLiCurios.DefenceEnhance(player));
        }
        totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
        totalDefence *= Compute.playerFantasyAttributeEnhance(player);
        totalDefence *= HeihuangCurios.Passive2DefenceEnhance(player);
        totalDefence *= LiulixianCurios4.playerAttributeDown(player);
        if (data.contains("ManaRune") && data.getInt("ManaRune") == 3) return (Defence + ExDefence) * 0.5f;
/*            if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
                ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 20;
            }*/

        if (totalDefence < 0) return 0;
        return totalDefence;
    }

    public static double PlayerDefenceWithoutNetherShield(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double Defence = 0.0d;
        double ExDefence = 0.0d;
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
        if (Utils.Defence.containsKey(boots)) Defence += Utils.Defence.get(boots);
        if (Utils.Defence.containsKey(leggings)) Defence += Utils.Defence.get(leggings);
        if (Utils.Defence.containsKey(chest)) Defence += Utils.Defence.get(chest);
        if (Utils.Defence.containsKey(helmet)) Defence += Utils.Defence.get(helmet);

        if (helmetTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += helmetTag.getInt(StringUtils.RandomAttribute.Defence);
            if (helmetTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.Defence));
        }
        if (chestTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += chestTag.getInt(StringUtils.RandomAttribute.Defence);
            if (chestTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.Defence));
        }
        if (leggingsTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += leggingsTag.getInt(StringUtils.RandomAttribute.Defence);
            if (leggingsTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.Defence));
        }
        if (bootsTag.contains(StringUtils.RandomAttribute.Defence)) {
            Defence += bootsTag.getInt(StringUtils.RandomAttribute.Defence);
            if (bootsTag.contains("Forging"))
                ExDefence += Compute.ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.Defence));
        }

        if (helmetTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(helmetTag);
        if (chestTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(chestTag);
        if (leggingsTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(bootsTag);
        if (stackmainhandtag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(stackmainhandtag);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.Defence.containsKey(mainhand))
            ExDefence += Utils.Defence.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.Defence.containsKey(offhand))
            ExDefence += Utils.Defence.get(offhand);
        if (Utils.Defence.containsKey(helmet) && helmetTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(helmetTag, Utils.Defence.get(helmet));
        if (Utils.Defence.containsKey(chest) && chestTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(chestTag, Utils.Defence.get(chest));
        if (Utils.Defence.containsKey(leggings) && leggingsTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(leggingsTag, Utils.Defence.get(leggings));
        if (Utils.Defence.containsKey(boots) && bootsTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(bootsTag, Utils.Defence.get(boots));
        if (Compute.ArmorCount.Forest(player) >= 2) ExDefence += Defence * 0.25;
        if (Compute.ArmorCount.LifeMana(player) >= 4) ExDefence += Defence * 0.25;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) ExDefence += Defence * 0.1;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0)
            ExDefence += Defence * 0.25 + 40;
        if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1)
            ExDefence += Defence * 0.40 + 80;
        if (data.contains("GemSDefence")) ExDefence += data.getDouble("GemSDefence");
        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            ExDefence += 100;
        // 矿工裤被动
        if (Compute.ArmorCount.Mine(player) >= 4) ExDefence += 250;

        if (helmet instanceof PiglinHelmet) {
            PiglinHelmet piglinHelmet = (PiglinHelmet) helmet;
            int Rate = (1 + piglinHelmet.Num) * 5;
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.getPosition(1), 10, 10, 10));
            ExDefence += Math.min(200, Rate * mobList.size());
        }

        if (data.getInt(StringUtils.Crest.Mine.Crest0) > 0)
            ExDefence += MineCrestAttributes.ExDefence[0] * data.getInt(StringUtils.Crest.Mine.Crest0);
        if (data.getInt(StringUtils.Crest.Mine.Crest1) > 0)
            ExDefence += MineCrestAttributes.ExDefence[1] * data.getInt(StringUtils.Crest.Mine.Crest1);
        if (data.getInt(StringUtils.Crest.Mine.Crest2) > 0)
            ExDefence += MineCrestAttributes.ExDefence[2] * data.getInt(StringUtils.Crest.Mine.Crest2);
        if (data.getInt(StringUtils.Crest.Mine.Crest3) > 0)
            ExDefence += MineCrestAttributes.ExDefence[3] * data.getInt(StringUtils.Crest.Mine.Crest3);
        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) ExDefence += 40;

        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = data.getInt(StringUtils.Ability.Power) + (data.getInt(StringUtils.Ability.Power) / 10) * 5;
            ExDefence += Point * 3;
        } // 能力

        if (Utils.PlayerDefenceRingMap.containsKey(player))
            ExDefence += Utils.PlayerDefenceRingMap.get(player);

        if (Utils.MineShieldEffect.containsKey(player) && Utils.MineShieldEffect.get(player) > TickCount) {
            ExDefence += player.experienceLevel * 5;
        }

        if (Utils.SakuraBowEffectMap.containsKey(player) && Utils.SakuraBowEffectMap.get(player) > TickCount) {
            ExDefence += 100;
        } // 妖弓-樱

        if (Utils.PlayerSpringHandDefenceAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            ExDefence += Utils.PlayerSpringHandDefenceAttribute.get(player);
        }

        if (Utils.SnowShieldPlayerEffectTickMap.containsKey(player) && Utils.SnowShieldPlayerEffectTickMap.get(player) > TickCount) {
            ExDefence += Utils.SnowShieldPlayerEffectMap.get(player);
        } // 玉山圆盾

        ExDefence += Compute.CuriosAttribute.AttributeValue(player, Utils.Defence, StringUtils.CuriosAttribute.Defence); // 新版饰品属性加成

        ExDefence += Compute.PassiveEquip.AttributeGet(player, Utils.Defence); // 器灵属性加成

        // 请在上方添加
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                && data.getDouble("ManaDamageAfterCompute") > 10000) {
            Defence *= 1.25;
            ExDefence *= 1.25;
        }

        ExDefence *= Compute.playerFantasyAttributeEnhance(player);
        Defence *= Compute.playerFantasyAttributeEnhance(player);

        if (data.contains("ManaRune") && data.getInt("ManaRune") == 3) return (Defence + ExDefence) * 0.5f;
/*            if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
                ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 20;
            }*/

        if (Defence + ExDefence < 0) return 0;
        return Defence + ExDefence;
    }

    public static double PlayerHealEffectUp(Player player) {
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healeffectup"))
            HealEffectUp += stackmainhandtag.getDouble("healeffectup");
        if (Utils.HealEffectUp.containsKey(boots)) HealEffectUp += Utils.HealEffectUp.get(boots);
        if (Utils.HealEffectUp.containsKey(leggings)) HealEffectUp += Utils.HealEffectUp.get(leggings);
        if (Utils.HealEffectUp.containsKey(chest)) HealEffectUp += Utils.HealEffectUp.get(chest);
        if (Utils.HealEffectUp.containsKey(helmet)) HealEffectUp += Utils.HealEffectUp.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.HealEffectUp.containsKey(mainhand))
            HealEffectUp += Utils.HealEffectUp.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.HealEffectUp.containsKey(offhand))
            HealEffectUp += Utils.HealEffectUp.get(offhand);
        if (Compute.ArmorCount.Forest(player) >= 4) HealEffectUp += 0.5f;
        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            HealEffectUp += Point * 0.01;
        }
        if (data.getInt(StringUtils.MineMonsterEffect) >= TickCount) HealEffectUp -= 0.8;

        if (helmetTag.contains("Gems1")) HealEffectUp += Compute.ItemHealStrengthGems(helmetTag);
        if (chestTag.contains("Gems1")) HealEffectUp += Compute.ItemHealStrengthGems(chestTag);
        if (leggingsTag.contains("Gems1")) HealEffectUp += Compute.ItemHealStrengthGems(leggingsTag);
        if (bootsTag.contains("Gems1")) HealEffectUp += Compute.ItemHealStrengthGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            HealEffectUp += Compute.ItemHealStrengthGems(stackmainhandtag);

        HealEffectUp += Compute.CuriosAttribute.AttributeValue(player, Utils.HealEffectUp, StringUtils.CuriosAttribute.HealEffectUp); // 新版饰品属性加成
        HealEffectUp += SoraVanillaSword.HealEffect(player);
        return HealEffectUp;
    }

    public static double PlayerExtraSwiftness(Player player) {
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("Swiftness"))
            SwiftnessUp += stackmainhandtag.getDouble("Swiftness");
        if (Utils.SwiftnessUp.containsKey(boots)) SwiftnessUp += Utils.SwiftnessUp.get(boots);
        if (Utils.SwiftnessUp.containsKey(leggings)) SwiftnessUp += Utils.SwiftnessUp.get(leggings);
        if (Utils.SwiftnessUp.containsKey(chest)) SwiftnessUp += Utils.SwiftnessUp.get(chest);
        if (Utils.SwiftnessUp.containsKey(helmet)) SwiftnessUp += Utils.SwiftnessUp.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.SwiftnessUp.containsKey(mainhand))
            SwiftnessUp += Utils.SwiftnessUp.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.SwiftnessUp.containsKey(offhand))
            SwiftnessUp += Utils.SwiftnessUp.get(offhand);

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

        SwiftnessUp += Compute.CuriosAttribute.AttributeValue(player, Utils.SwiftnessUp, StringUtils.CuriosAttribute.SwiftnessUp); // 新版饰品属性加成

        SwiftnessUp += Compute.PassiveEquip.AttributeGet(player, Utils.SwiftnessUp); // 器灵属性加成
        SwiftnessUp += LiulixianCurios2.ExSwiftnessUp(player);
        SwiftnessUp += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExSwiftnessUp);
        SwiftnessUp += QiFuCurios1.Passive1ExSwiftness(player);
        SwiftnessUp += QiFuCurios1.Passive3ExSwiftness(player);
        // 请在上方添加
        SwiftnessUp *= LiulixianCurios3.SwiftnessEnhance(player);
        SwiftnessUp *= Compute.playerFantasyAttributeEnhance(player);
        SwiftnessUp *= BowCurios0.SwiftnessUp(player);

        return SwiftnessUp;
    }

    public static double PlayerAttackRangeUp(Player player) {
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("AttackRangeUp"))
            RangeUp += stackmainhandtag.getDouble("AttackRangeUp");
        if (Utils.AttackRangeUp.containsKey(boots)) RangeUp += Utils.AttackRangeUp.get(boots);
        if (Utils.AttackRangeUp.containsKey(leggings)) RangeUp += Utils.AttackRangeUp.get(leggings);
        if (Utils.AttackRangeUp.containsKey(chest)) RangeUp += Utils.AttackRangeUp.get(chest);
        if (Utils.AttackRangeUp.containsKey(helmet)) RangeUp += Utils.AttackRangeUp.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackRangeUp.containsKey(mainhand))
            RangeUp += Utils.AttackRangeUp.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.AttackRangeUp.containsKey(offhand))
            RangeUp += Utils.AttackRangeUp.get(offhand);
        if (Compute.SwordSkillLevelGet(data, 11) > 0 && Utils.SwordTag.containsKey(mainhand))
            RangeUp += Compute.SwordSkillLevelGet(data, 11) * 0.2;

        // 请在上方添加
        RangeUp *= Compute.playerFantasyAttributeEnhance(player);

        return RangeUp;
    }

    public static double PlayerPowerReleaseSpeed(Player player) {
        if (player.isCreative()) return 3;
        CompoundTag data = player.getPersistentData();
        double ReleaseSpeed = 0;
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
        if (helmetTag.contains("Gems1")) ReleaseSpeed += Compute.ItemCoolDownGems(helmetTag);
        if (chestTag.contains("Gems1")) ReleaseSpeed += Compute.ItemCoolDownGems(chestTag);
        if (leggingsTag.contains("Gems1")) ReleaseSpeed += Compute.ItemCoolDownGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ReleaseSpeed += Compute.ItemCoolDownGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            ReleaseSpeed += Compute.ItemCoolDownGems(stackmainhandtag);
        if (Utils.CoolDownDecrease.containsKey(boots)) ReleaseSpeed += Utils.CoolDownDecrease.get(boots);
        if (Utils.CoolDownDecrease.containsKey(leggings))
            ReleaseSpeed += Utils.CoolDownDecrease.get(leggings);
        if (Utils.CoolDownDecrease.containsKey(chest)) ReleaseSpeed += Utils.CoolDownDecrease.get(chest);
        if (Utils.CoolDownDecrease.containsKey(helmet)) ReleaseSpeed += Utils.CoolDownDecrease.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.CoolDownDecrease.containsKey(mainhand))
            ReleaseSpeed += Utils.CoolDownDecrease.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.CoolDownDecrease.containsKey(offhand))
            ReleaseSpeed += Utils.CoolDownDecrease.get(offhand);
        if (Compute.ArmorCount.Lake(player) >= 2) ReleaseSpeed += 0.2;
        if (Compute.ArmorCount.ObsiMana(player) >= 4) ReleaseSpeed += 0.2;
        if (player.getPersistentData().contains("Blue") && player.getPersistentData().getInt("Blue") == 0)
            ReleaseSpeed += player.getAttribute(Attributes.MOVEMENT_SPEED).getValue() - 0.1F;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) ReleaseSpeed += 0.1;
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                ReleaseSpeed += (i + 1) * 0.005;
            }
        }
        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 0)
            ReleaseSpeed += 0.15;
        if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 1)
            ReleaseSpeed += 0.3;
        if (data.contains("GemSCoolDown")) ReleaseSpeed += data.getDouble("GemSCoolDown");
        ReleaseSpeed += Compute.SArmorCoolDown(player);
        if (Compute.SwordSkillLevelGet(data, 7) > 0 && Utils.SwordTag.containsKey(mainhand))
            ReleaseSpeed += Compute.SwordSkillLevelGet(data, 7) * 0.03; // 冷静（手持近战武器时，获得3%冷却缩减）
        if (Compute.ManaSkillLevelGet(data, 7) > 0 && Utils.SceptreTag.containsKey(mainhand))
            ReleaseSpeed += Compute.ManaSkillLevelGet(data, 7) * 0.06; // 冷静（手持法杖时，获得6%冷却缩减）

        if (data.getInt(StringUtils.Crest.Lake.Crest0) > 0) ReleaseSpeed +=
                LakeCrestAttributes.CoolDown[0] * data.getInt(StringUtils.Crest.Lake.Crest0);
        if (data.getInt(StringUtils.Crest.Lake.Crest1) > 0) ReleaseSpeed +=
                LakeCrestAttributes.CoolDown[1] * data.getInt(StringUtils.Crest.Lake.Crest1);
        if (data.getInt(StringUtils.Crest.Lake.Crest2) > 0) ReleaseSpeed +=
                LakeCrestAttributes.CoolDown[2] * data.getInt(StringUtils.Crest.Lake.Crest2);
        if (data.getInt(StringUtils.Crest.Lake.Crest3) > 0) ReleaseSpeed +=
                LakeCrestAttributes.CoolDown[3] * data.getInt(StringUtils.Crest.Lake.Crest3);
        if (data.getInt(StringUtils.Crest.Lake.Crest4) > 0) ReleaseSpeed +=
                LakeCrestAttributes.CoolDown[4] * data.getInt(StringUtils.Crest.Lake.Crest4);

        if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            ReleaseSpeed += Compute.ManaSkillLevelGet(data, 11) * 0.05;
        } // 术法全析

        ReleaseSpeed += GoldenBook.GoldenBookCoolDown(player);
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1)
            ReleaseSpeed += 0.5;

        ReleaseSpeed += EarthPower.PlayerCoolDownEnhance(player); // 地蕴法术

        ReleaseSpeed += Compute.CuriosAttribute.AttributeValue(player, Utils.CoolDownDecrease, StringUtils.CuriosAttribute.CoolDown); // 新版饰品属性加成
        ReleaseSpeed += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExCoolDownDecrease);

        ReleaseSpeed += LakeRune0.ExCoolDown(player);
        ReleaseSpeed += LakeRune3.ExCoolDown(player);
        // 请在上方添加
        ReleaseSpeed *= LakeRune1.CoolDownDown(player);
        ReleaseSpeed *= LakeRune0.CoolDownUp(player);
        ReleaseSpeed *= Compute.playerFantasyAttributeEnhance(player);

        return ReleaseSpeed;
    }

    public static double PlayerCoolDownDecrease(Player player) {
        return 1 - (1 / (1 + (PlayerPowerReleaseSpeed(player))));

/*            if (player.isCreative()) return 1;
            CompoundTag data = player.getPersistentData();
            double DecreaseRate = 1;
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
            if (helmetTag.contains("Gems1")) DecreaseRate *= (1 - Compute.ItemCoolDownGems(helmetTag));
            if (chestTag.contains("Gems1")) DecreaseRate *= (1 - Compute.ItemCoolDownGems(chestTag));
            if (leggingsTag.contains("Gems1")) DecreaseRate *= (1 - Compute.ItemCoolDownGems(leggingsTag));
            if (bootsTag.contains("Gems1")) DecreaseRate *= (1 - Compute.ItemCoolDownGems(bootsTag));
            if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
                DecreaseRate *= (1 - Compute.ItemCoolDownGems(stackmainhandtag));
            if (Utils.CoolDownDecrease.containsKey(boots)) DecreaseRate *= (1 - Utils.CoolDownDecrease.get(boots));
            if (Utils.CoolDownDecrease.containsKey(leggings))
                DecreaseRate *= (1 - Utils.CoolDownDecrease.get(leggings));
            if (Utils.CoolDownDecrease.containsKey(chest)) DecreaseRate *= (1 - Utils.CoolDownDecrease.get(chest));
            if (Utils.CoolDownDecrease.containsKey(helmet)) DecreaseRate *= (1 - Utils.CoolDownDecrease.get(helmet));
            if (Utils.MainHandTag.containsKey(mainhand) && Utils.CoolDownDecrease.containsKey(mainhand))
                DecreaseRate *= (1 - Utils.CoolDownDecrease.get(mainhand));
            if (Utils.OffHandTag.containsKey(offhand) && Utils.CoolDownDecrease.containsKey(offhand))
                DecreaseRate *= (1 - Utils.CoolDownDecrease.get(offhand));
            if (ArmorCount.Lake(player) >= 2) DecreaseRate *= (1 - 0.2);
            if (ArmorCount.ObsiMana(player) >= 4) DecreaseRate *= (1 - 0.2);
            if (player.getPersistentData().contains("Blue") && player.getPersistentData().getInt("Blue") == 0)
                DecreaseRate *= (1 - player.getAttribute(Attributes.MOVEMENT_SPEED).getValue() - 0.1F);
            if (data.contains("lakegems") && data.getBoolean("lakegems")) DecreaseRate *= (1 - 0.1);
            for (int i = 0; i < 21; i++) {
                if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                    DecreaseRate *= (1 - (i + 1) * 0.005);
                }
            }
            if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 0)
                DecreaseRate *= (1 - 0.15);
            if (player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 1)
                DecreaseRate *= (1 - 0.3);
            if (data.contains("GemSCoolDown")) DecreaseRate *= (1 - data.getDouble("GemSCoolDown"));
            DecreaseRate *= (1 - SArmorCoolDown(player));
            if (Compute.SwordSkillLevelGet(data, 7) > 0 && Utils.SwordTag.containsKey(mainhand))
                DecreaseRate *= (1 - Compute.SwordSkillLevelGet(data, 7) * 0.03); // 冷静（手持近战武器时，获得3%冷却缩减）
            if (ManaSkillLevelGet(data, 7) > 0 && Utils.SceptreTag.containsKey(mainhand))
                DecreaseRate *= (1 - ManaSkillLevelGet(data, 7) * 0.06); // 冷静（手持法杖时，获得6%冷却缩减）

            if (data.getInt(StringUtils.Crest.Lake.Crest0) > 0) DecreaseRate *=
                    (1 - LakeCrestAttributes.CoolDown[0] * data.getInt(StringUtils.Crest.Lake.Crest0));
            if (data.getInt(StringUtils.Crest.Lake.Crest1) > 0) DecreaseRate *=
                    (1 - LakeCrestAttributes.CoolDown[1] * data.getInt(StringUtils.Crest.Lake.Crest1));
            if (data.getInt(StringUtils.Crest.Lake.Crest2) > 0) DecreaseRate *=
                    (1 - LakeCrestAttributes.CoolDown[2] * data.getInt(StringUtils.Crest.Lake.Crest2));
            if (data.getInt(StringUtils.Crest.Lake.Crest3) > 0) DecreaseRate *=
                    (1 - LakeCrestAttributes.CoolDown[3] * data.getInt(StringUtils.Crest.Lake.Crest3));
            if (data.getInt(StringUtils.Crest.Lake.Crest4) > 0) DecreaseRate *=
                    (1 - LakeCrestAttributes.CoolDown[4] * data.getInt(StringUtils.Crest.Lake.Crest4));

            if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
                DecreaseRate *= (1 - Compute.ManaSkillLevelGet(data, 11) * 0.05);
            } // 术法全析

            DecreaseRate *= (1 - GoldenBook.GoldenBookCoolDown(player));
            if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1) {
                DecreaseRate *= (1 - 0.35);
            }

            DecreaseRate *= (1 - EarthPower.PlayerCoolDownEnhance(player)); // 地蕴法术

            DecreaseRate *= (1 - CuriosAttribute.CoolDown(player)); // 新饰品属性加成
            // 请在上方添加
            DecreaseRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

            return 1 - DecreaseRate;*/
    }

    public static double PlayerAttackSpeedUp(Player player) {
        CompoundTag data = player.getPersistentData();
        double AttackSpeedUp = 0.0d;
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("AttackSpeedUp"))
            AttackSpeedUp += stackmainhandtag.getDouble("AttackSpeedUp");
        if (Utils.AttackSpeedUp.containsKey(boots)) AttackSpeedUp += Utils.AttackSpeedUp.get(boots);
        if (Utils.AttackSpeedUp.containsKey(leggings)) AttackSpeedUp += Utils.AttackSpeedUp.get(leggings);
        if (Utils.AttackSpeedUp.containsKey(chest)) AttackSpeedUp += Utils.AttackSpeedUp.get(chest);
        if (Utils.AttackSpeedUp.containsKey(helmet)) AttackSpeedUp += Utils.AttackSpeedUp.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackSpeedUp.containsKey(mainhand))
            AttackSpeedUp += Utils.AttackSpeedUp.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.AttackSpeedUp.containsKey(offhand))
            AttackSpeedUp += Utils.AttackSpeedUp.get(offhand);

        // 请在上方添加
        AttackSpeedUp *= Compute.playerFantasyAttributeEnhance(player);

        return AttackSpeedUp;
    }

    public static double PlayerDefencePenetration(Player player) {
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("breakDefence"))
            DefenceRate *= (1 - stackmainhandtag.getDouble("breakDefence"));
        if (Utils.DefencePenetration.containsKey(boots)) DefenceRate *= (1 - Utils.DefencePenetration.get(boots));
        if (Utils.DefencePenetration.containsKey(leggings))
            DefenceRate *= (1 - Utils.DefencePenetration.get(leggings));
        if (Utils.DefencePenetration.containsKey(chest)) DefenceRate *= (1 - Utils.DefencePenetration.get(chest));
        if (Utils.DefencePenetration.containsKey(helmet)) DefenceRate *= (1 - Utils.DefencePenetration.get(helmet));
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.DefencePenetration.containsKey(mainhand))
            DefenceRate *= (1 - Utils.DefencePenetration.get(mainhand));
        if (Utils.OffHandTag.containsKey(offhand) && Utils.DefencePenetration.containsKey(offhand))
            DefenceRate *= (1 - Utils.DefencePenetration.get(offhand));
        if (data.contains(StringUtils.VolcanoBowSkill) && data.getInt(StringUtils.VolcanoBowSkill) > TickCount)
            DefenceRate *= (1 - 0.4);
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                DefenceRate *= (1 - (i + 1) * 0.005);
            }
        }
        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 0)
            DefenceRate *= (1 - 0.20d);
        if (player.getEffect(ModEffects.BREAKDefenceUP.get()) != null && player.getEffect(ModEffects.BREAKDefenceUP.get()).getAmplifier() == 1)
            DefenceRate *= (1 - 0.45f);
        if (data.contains(StringUtils.ForestSwordSkill0) && data.getInt(StringUtils.ForestSwordSkill0) > TickCount)
            DefenceRate *= (1 - 0.15);
        if (data.contains(StringUtils.ForestSwordSkill1) && data.getInt(StringUtils.ForestSwordSkill1) > TickCount)
            DefenceRate *= (1 - 0.15);
        if (data.contains("GemSBreakDefence")) DefenceRate *= (1 - data.getDouble("GemSBreakDefence"));
/*
            if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.SwordTag.containsKey(mainhand) || Utils.BowTag.containsKey(mainhand)))
                DefenceRate *=
                        (1 - stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.DefencePenetration);
*/

        if (Utils.PlayerSpringHandDefencePenetraionAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            DefenceRate *= (1 - Utils.PlayerSpringHandDefencePenetraionAttribute.get(player));
        }

        if (Utils.YiZhiXiangLi != null && Utils.YiZhiXiangLi.equals(player) && Utils.YiZhiXiangLiEffect > TickCount) {
            DefenceRate *= (1 - 0.5);
        }

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            DefenceRate *= (1 - 0.2);
        }

        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && CrushiSword.ActiveTick > TickCount) {
            DefenceRate *= (1 - 0.3);
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get())) {
            DefenceRate *= (1 - Math.min(0.5, PlayerMaxHealth(player) * 0.05 / 1000));
        }

        DefenceRate *= (1 - LeiyanCurios.ExDefencePenetration(player));

        double DecreaseRate = 0;
        if (helmetTag.contains("Gems1")) DecreaseRate += Compute.ItemDefencePenetrationGems(helmetTag);
        if (chestTag.contains("Gems1")) DecreaseRate += Compute.ItemDefencePenetrationGems(chestTag);
        if (leggingsTag.contains("Gems1")) DecreaseRate += Compute.ItemDefencePenetrationGems(leggingsTag);
        if (bootsTag.contains("Gems1")) DecreaseRate += Compute.ItemDefencePenetrationGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            DecreaseRate += Compute.ItemDefencePenetrationGems(stackmainhandtag);

        if (DecreaseRate > 0) DefenceRate *= (1 - DecreaseRate);
        DefenceRate *= (1 - Compute.CuriosAttribute.AttributeValue(player, Utils.DefencePenetration, StringUtils.CuriosAttribute.DefencePenetration)); // 新版饰品属性加成
        DecreaseRate *= (1 - BlackFeisaCurios4.Penetration(player));
        // 请在上方添加
        DefenceRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

        return 1 - DefenceRate;
    }

    public static double PlayerDefencePenetration0(Player player) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double DefencePenetration0 = 0.0d;
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("BreakDefence0"))
            DefencePenetration0 += stackmainhandtag.getDouble("BreakDefence0");
        if (Utils.DefencePenetration0.containsKey(boots)) DefencePenetration0 += Utils.DefencePenetration0.get(boots);
        if (Utils.DefencePenetration0.containsKey(leggings))
            DefencePenetration0 += Utils.DefencePenetration0.get(leggings);
        if (Utils.DefencePenetration0.containsKey(chest)) DefencePenetration0 += Utils.DefencePenetration0.get(chest);
        if (Utils.DefencePenetration0.containsKey(helmet)) DefencePenetration0 += Utils.DefencePenetration0.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.DefencePenetration0.containsKey(mainhand))
            DefencePenetration0 += Utils.DefencePenetration0.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.DefencePenetration0.containsKey(offhand))
            DefencePenetration0 += Utils.DefencePenetration0.get(offhand);
        if (data.contains(StringUtils.ForestSwordSkill0) && data.getInt(StringUtils.ForestSwordSkill0) > TickCount)
            DefencePenetration0 += 50;
        if (data.contains(StringUtils.ForestSwordSkill1) && data.getInt(StringUtils.ForestSwordSkill1) > TickCount)
            DefencePenetration0 += 250;
        if (Compute.SwordSkillLevelGet(data, 10) > 0 && Utils.SwordTag.containsKey(mainhand))
            DefencePenetration0 += Compute.SwordSkillLevelGet(data, 10) * player.experienceLevel;
        if (Compute.BowSkillLevelGet(data, 10) > 0 && Utils.BowTag.containsKey(mainhand))
            DefencePenetration0 += Compute.BowSkillLevelGet(data, 10) * player.experienceLevel;
        if (data.contains("GemSDefencePenetration0")) DefencePenetration0 += data.getDouble("GemSDefencePenetration0");

        int FlexibilityAbilityPoint = data.getInt(StringUtils.Ability.Flexibility);
        if (data.contains(StringUtils.Ability.Flexibility) && data.getInt(StringUtils.Ability.Flexibility) > 0) {
            int Point = FlexibilityAbilityPoint + (FlexibilityAbilityPoint / 10) * 5;
            DefencePenetration0 += Point;
        } // 能力

        if (data.getInt(StringUtils.WitherBow.Effect3) > TickCount) DefencePenetration0 += 400;
        else if (data.getInt(StringUtils.WitherBow.Effect2) > TickCount) DefencePenetration0 += 300;
        else if (data.getInt(StringUtils.WitherBow.Effect1) > TickCount) DefencePenetration0 += 200;
        else if (data.getInt(StringUtils.WitherBow.Effect0) > TickCount) DefencePenetration0 += 100;

        if (data.getInt(StringUtils.Crest.Snow.Crest0) > 0)
            DefencePenetration0 += SnowCrestAttributes.DefencePenetration[0] * data.getInt(StringUtils.Crest.Snow.Crest0);
        if (data.getInt(StringUtils.Crest.Snow.Crest1) > 0)
            DefencePenetration0 += SnowCrestAttributes.DefencePenetration[1] * data.getInt(StringUtils.Crest.Snow.Crest1);
        if (data.getInt(StringUtils.Crest.Snow.Crest2) > 0)
            DefencePenetration0 += SnowCrestAttributes.DefencePenetration[2] * data.getInt(StringUtils.Crest.Snow.Crest2);
        if (data.getInt(StringUtils.Crest.Snow.Crest3) > 0)
            DefencePenetration0 += SnowCrestAttributes.DefencePenetration[3] * data.getInt(StringUtils.Crest.Snow.Crest3);
        if (data.getInt(StringUtils.Crest.Snow.Crest4) > 0)
            DefencePenetration0 += SnowCrestAttributes.DefencePenetration[4] * data.getInt(StringUtils.Crest.Snow.Crest4);


        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.SwordTag.containsKey(mainhand) || Utils.BowTag.containsKey(mainhand)))
            DefencePenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.DefencePenetration0;

        if (mainhand instanceof SkyBow || mainhand instanceof SkyBoss.SkyBossBow)
            DefencePenetration0 += PlayerMovementSpeed(player) * 100;

        if (Utils.ShipSwordTime.containsKey(player) && Utils.ShipSwordTime.get(player) > TickCount) {
            DefencePenetration0 += 50 * Utils.ShipSwordEffect.get(player);
        } // 锚

        if (mainhand.equals(ModItems.ShipBow.get())) {
            List<Player> serverPlayerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger Count = new AtomicInteger();
            serverPlayerList.forEach(player1 -> {
                if (player1.distanceTo(player) <= 6) Count.getAndIncrement();
            });
            DefencePenetration0 += 50 * Math.min(Count.get(), 4);
        }

        if (Utils.IceBowEffectMap.containsKey(player) && Utils.IceBowEffectMap.get(player) > TickCount) {
            DefencePenetration0 += Utils.IceBowEffectNumMap.get(player) / 8;
        } //冰霜弓

        if (Utils.PlayerSpringRingDefencePenetration0Attribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            DefencePenetration0 += Utils.PlayerSpringRingDefencePenetration0Attribute.get(player);
        }

        if (Utils.PlayerSpringBeltDefencePenetration0Attribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
            DefencePenetration0 += Utils.PlayerSpringBeltDefencePenetration0Attribute.get(player);
        }

        if (Utils.YiZhiXiangLi != null && Utils.YiZhiXiangLi.equals(player) && Utils.YiZhiXiangLiEffect > TickCount) {
            DefencePenetration0 += 500;
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("Gems1")) DefencePenetration0 += Compute.ItemDefencePenetration0Gems(helmetTag);
        if (chestTag.contains("Gems1")) DefencePenetration0 += Compute.ItemDefencePenetration0Gems(chestTag);
        if (leggingsTag.contains("Gems1")) DefencePenetration0 += Compute.ItemDefencePenetration0Gems(leggingsTag);
        if (bootsTag.contains("Gems1")) DefencePenetration0 += Compute.ItemDefencePenetration0Gems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            DefencePenetration0 += Compute.ItemDefencePenetration0Gems(stackmainhandtag);

        DefencePenetration0 += Compute.CuriosAttribute.AttributeValue(player, Utils.DefencePenetration0, StringUtils.CuriosAttribute.DefencePenetration0); // 新版饰品属性加成

        DefencePenetration0 += MyMissionBow.ExDefencePenetration0(player);
        DefencePenetration0 += CastleSword.ExPenetration0(player); // 暗黑武器主动
        DefencePenetration0 += YxwgBow.ExDefencePenetration0(player);

        if (data.getInt(StringUtils.WitherSword.Effect3) > TickCount) DefencePenetration0 += 200;
        else if (data.getInt(StringUtils.WitherSword.Effect2) > TickCount) DefencePenetration0 += 300;
        else if (data.getInt(StringUtils.WitherSword.Effect1) > TickCount) DefencePenetration0 += 400;
        else if (data.getInt(StringUtils.WitherSword.Effect0) > TickCount) DefencePenetration0 += 600;

        DefencePenetration0 += QiFuCurios.Buff2DefencePenetration0(player);
        DefencePenetration0 += LittleartCurios.defencePenetration0Up(player);
        DefencePenetration0 += ShaoFengCurios.defencePenetration0Up(player);
        // 请在上方添加
        DefencePenetration0 *= Compute.playerFantasyAttributeEnhance(player);
        DefencePenetration0 *= LiuLiXianCurios1F.PenetrationUp(player);

        return DefencePenetration0;
    }

    public static double PlayerHealthRecover(Player player) {
        double HealthRecover = 0.0d;
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
        if (helmetTag.contains("Gems1")) HealthRecover += Compute.ItemHealRecoverGems(helmetTag);
        if (chestTag.contains("Gems1")) HealthRecover += Compute.ItemHealRecoverGems(chestTag);
        if (leggingsTag.contains("Gems1")) HealthRecover += Compute.ItemHealRecoverGems(leggingsTag);
        if (bootsTag.contains("Gems1")) HealthRecover += Compute.ItemHealRecoverGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            HealthRecover += Compute.ItemHealRecoverGems(stackmainhandtag);
        if (Utils.HealthRecover.containsKey(boots)) HealthRecover += Utils.HealthRecover.get(boots);
        if (Utils.HealthRecover.containsKey(leggings)) HealthRecover += Utils.HealthRecover.get(leggings);
        if (Utils.HealthRecover.containsKey(chest)) HealthRecover += Utils.HealthRecover.get(chest);
        if (Utils.HealthRecover.containsKey(helmet)) HealthRecover += Utils.HealthRecover.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.HealthRecover.containsKey(mainhand))
            HealthRecover += Utils.HealthRecover.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.HealthRecover.containsKey(offhand))
            HealthRecover += Utils.HealthRecover.get(offhand);
        if (Compute.ArmorCount.Plain(player) >= 2) HealthRecover += 0.5 + player.getMaxHealth() * 0.01F;
        if (Compute.ArmorCount.LifeMana(player) >= 4) HealthRecover += 1 + player.getMaxHealth() * 0.01F;
        if (!Utils.OverWorldLevelIsNight && Compute.ArmorCount.Forest(player) >= 4) HealthRecover += 5;
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                HealthRecover += (i + 1) * 0.1;
            }
        }
        if (player.getEffect(ModEffects.HEALREPLY.get()) != null && player.getEffect(ModEffects.HEALREPLY.get()).getAmplifier() == 0)
            HealthRecover += player.getMaxHealth() * 0.025;
        if (player.getEffect(ModEffects.HEALREPLY.get()) != null && player.getEffect(ModEffects.HEALREPLY.get()).getAmplifier() == 1)
            HealthRecover += player.getMaxHealth() * 0.05;

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            HealthRecover += Point;
        }
        if (data.getInt(StringUtils.Crest.Plain.Crest0) > 0)
            HealthRecover += PlainCrestAttributes.HealthRecover[0] * data.getInt(StringUtils.Crest.Plain.Crest0);
        if (data.getInt(StringUtils.Crest.Plain.Crest1) > 0)
            HealthRecover += PlainCrestAttributes.HealthRecover[1] * data.getInt(StringUtils.Crest.Plain.Crest1);
        if (data.getInt(StringUtils.Crest.Plain.Crest2) > 0)
            HealthRecover += PlainCrestAttributes.HealthRecover[2] * data.getInt(StringUtils.Crest.Plain.Crest2);
        if (data.getInt(StringUtils.Crest.Plain.Crest3) > 0)
            HealthRecover += PlainCrestAttributes.HealthRecover[3] * data.getInt(StringUtils.Crest.Plain.Crest3);
        if (data.getInt(StringUtils.Crest.Plain.Crest4) > 0)
            HealthRecover += PlainCrestAttributes.HealthRecover[4] * data.getInt(StringUtils.Crest.Plain.Crest4);

        HealthRecover += Compute.CuriosAttribute.AttributeValue(player, Utils.HealthRecover, StringUtils.CuriosAttribute.HealthRecover); // 新版饰品属性加成
        HealthRecover += SoraVanillaSword.HealthRecover(player);
        // 请在上方添加
        HealthRecover *= Compute.playerFantasyAttributeEnhance(player);

        HealthRecover *= EliaoiCurios1.HealthRecover(player);

        return HealthRecover;
    }

    public static double PlayerMaxHealth(Player player) {
        int TickCount = player.getServer().getTickCount();
        double MaxHealth = 0.0d;
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
        if (helmetTag.contains("Gems1")) MaxHealth += Compute.ItemMaxHealthGems(helmetTag);
        if (chestTag.contains("Gems1")) MaxHealth += Compute.ItemMaxHealthGems(chestTag);
        if (leggingsTag.contains("Gems1")) MaxHealth += Compute.ItemMaxHealthGems(leggingsTag);
        if (bootsTag.contains("Gems1")) MaxHealth += Compute.ItemMaxHealthGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            MaxHealth += Compute.ItemMaxHealthGems(stackmainhandtag);

        if (Utils.MaxHealth.containsKey(boots)) MaxHealth += Utils.MaxHealth.get(boots);
        if (Utils.MaxHealth.containsKey(leggings)) MaxHealth += Utils.MaxHealth.get(leggings);
        if (Utils.MaxHealth.containsKey(chest)) MaxHealth += Utils.MaxHealth.get(chest);
        if (Utils.MaxHealth.containsKey(helmet)) MaxHealth += Utils.MaxHealth.get(helmet);

        if (helmetTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
            MaxHealth += helmetTag.getInt(StringUtils.RandomAttribute.MaxHealth);
            if (helmetTag.contains("Forging"))
                MaxHealth += Compute.ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.MaxHealth));
        }
        if (chestTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
            MaxHealth += chestTag.getInt(StringUtils.RandomAttribute.MaxHealth);
            if (chestTag.contains("Forging"))
                MaxHealth += Compute.ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.MaxHealth));
        }
        if (leggingsTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
            MaxHealth += leggingsTag.getInt(StringUtils.RandomAttribute.MaxHealth);
            if (leggingsTag.contains("Forging"))
                MaxHealth += Compute.ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.MaxHealth));
        }
        if (bootsTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
            MaxHealth += bootsTag.getInt(StringUtils.RandomAttribute.MaxHealth);
            if (bootsTag.contains("Forging"))
                MaxHealth += Compute.ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.MaxHealth));
        }

        if (Utils.MaxHealth.containsKey(helmet) && helmetTag.contains("Forging"))
            MaxHealth += Compute.ForgingValue(helmetTag, Utils.MaxHealth.get(helmet));
        if (Utils.MaxHealth.containsKey(chest) && chestTag.contains("Forging"))
            MaxHealth += Compute.ForgingValue(chestTag, Utils.MaxHealth.get(chest));
        if (Utils.MaxHealth.containsKey(leggings) && leggingsTag.contains("Forging"))
            MaxHealth += Compute.ForgingValue(leggingsTag, Utils.MaxHealth.get(leggings));
        if (Utils.MaxHealth.containsKey(boots) && bootsTag.contains("Forging"))
            MaxHealth += Compute.ForgingValue(bootsTag, Utils.MaxHealth.get(boots));

        if (Utils.MainHandTag.containsKey(mainhand) && Utils.MaxHealth.containsKey(mainhand))
            MaxHealth += Utils.MaxHealth.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.MaxHealth.containsKey(offhand))
            MaxHealth += Utils.MaxHealth.get(offhand);
        if (Compute.ArmorCount.Plain(player) >= 4) MaxHealth += 200;
        if (data.contains("Barker")) MaxHealth *= 1.0d + (data.getInt("Barker") / 100000.0d) * 0.05F;
        if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 3) MaxHealth *= 0.5;
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                MaxHealth += (i + 1) * 10;
            }
        }
        if (data.contains("GemSMaxHeal")) MaxHealth += data.getDouble("GemSMaxHeal");
        MaxHealth += Compute.SArmorMaxHealth(player);

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            MaxHealth += Point * 10;
        }

        if (data.getInt(StringUtils.Crest.Forest.Crest0) > 0)
            MaxHealth += ForestCrestAttributes.MaxHealth[0] * data.getInt(StringUtils.Crest.Forest.Crest0);
        if (data.getInt(StringUtils.Crest.Forest.Crest1) > 0)
            MaxHealth += ForestCrestAttributes.MaxHealth[1] * data.getInt(StringUtils.Crest.Forest.Crest1);
        if (data.getInt(StringUtils.Crest.Forest.Crest2) > 0)
            MaxHealth += ForestCrestAttributes.MaxHealth[2] * data.getInt(StringUtils.Crest.Forest.Crest2);
        if (data.getInt(StringUtils.Crest.Forest.Crest3) > 0)
            MaxHealth += ForestCrestAttributes.MaxHealth[3] * data.getInt(StringUtils.Crest.Forest.Crest3);
        if (data.getInt(StringUtils.Crest.Forest.Crest4) > 0)
            MaxHealth += ForestCrestAttributes.MaxHealth[4] * data.getInt(StringUtils.Crest.Forest.Crest4);

        if (Utils.PlayerHealthRingMap.containsKey(player))
            MaxHealth += Utils.PlayerHealthRingMap.get(player);

        if (Utils.PlayerSpringHandMaxHealthAttribute.containsKey(player) && Utils.PlayerSpringHandLevelRequire.get(player) <= player.experienceLevel) {
            MaxHealth += Utils.PlayerSpringHandMaxHealthAttribute.get(player);
        }

        MaxHealth += Compute.CuriosAttribute.AttributeValue(player, Utils.MaxHealth, StringUtils.CuriosAttribute.MaxHealth); // 新版饰品属性加成

        MaxHealth += Compute.PassiveEquip.AttributeGet(player, Utils.MaxHealth); // 器灵属性加成
        MaxHealth += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExMaxHealth);
        MaxHealth += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExMaxHealth);
        MaxHealth += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExMaxHealth);
        // 请在上方添加
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                && PlayerAttributes.PlayerManaDamage(player) > 10000) {
            MaxHealth *= (1.25 + ShangMengLiCurios.MaxHealthEnhance(player));
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get()) && Utils.WcBowTick > TickCount) {
            MaxHealth *= (1 + 0.05 * Utils.WcBowCount);
        }

        MaxHealth *= Compute.playerFantasyAttributeEnhance(player);

        return MaxHealth;
    }

    public static double PlayerManaDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        double BaseDamage = 0.0d;
        double ExDamage = 0;
        Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data = player.getPersistentData();
        CompoundTag data0 = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            data0 = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        ItemStack equip = player.getItemInHand(InteractionHand.MAIN_HAND);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDamage.containsKey(mainhand))
            BaseDamage += Utils.ManaDamage.get(mainhand);

        // Fixed:防具攻击强化失效
        if (Utils.ArmorTag.containsKey(helmet) && Utils.ManaDamage.containsKey(helmet) && helmetTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(helmetTag, Utils.ManaDamage.get(helmet));
        if (Utils.ArmorTag.containsKey(chest) && Utils.ManaDamage.containsKey(chest) && chestTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(chestTag, Utils.ManaDamage.get(chest));
        if (Utils.ArmorTag.containsKey(leggings) && Utils.ManaDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(leggingsTag, Utils.ManaDamage.get(leggings));
        if (Utils.ArmorTag.containsKey(boots) && Utils.ManaDamage.containsKey(boots) && bootsTag.contains("Forging"))
            ExDamage += Compute.ForgingValue(bootsTag, Utils.ManaDamage.get(boots));

        if (helmetTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
            BaseDamage += helmetTag.getInt(StringUtils.RandomAttribute.ManaDamage);
            if (helmetTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.ManaDamage));
        }
        if (chestTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
            BaseDamage += chestTag.getInt(StringUtils.RandomAttribute.ManaDamage);
            if (chestTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.ManaDamage));
        }
        if (leggingsTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
            BaseDamage += leggingsTag.getInt(StringUtils.RandomAttribute.ManaDamage);
            if (leggingsTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.ManaDamage));
        }
        if (bootsTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
            BaseDamage += bootsTag.getInt(StringUtils.RandomAttribute.ManaDamage);
            if (bootsTag.contains("Forging"))
                ExDamage += Compute.ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.ManaDamage));
        }

        if (data.contains("snowRune0Time") && data.getInt("snowRune0Time") > 0)
            ExDamage += data.getInt("snowRune0") * 0.15 * BaseDamage * Compute.EndRune3Judge(player, 3);
        if (helmetTag.contains("Gems1")) ExDamage += Compute.ItemManaDamageGems(helmetTag);
        if (chestTag.contains("Gems1")) ExDamage += Compute.ItemManaDamageGems(chestTag);
        if (leggingsTag.contains("Gems1")) ExDamage += Compute.ItemManaDamageGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ExDamage += Compute.ItemManaDamageGems(bootsTag);
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("manadamage"))
            ExDamage += stackmainhandtag.getDouble("manadamage");
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDamage.containsKey(mainhand) && data0.contains("Forging"))
            ExDamage += Compute.ForgingValue(data0, Utils.ManaDamage.get(mainhand));
        if (Utils.ManaDamage.containsKey(boots)) ExDamage += Utils.ManaDamage.get(boots);
        if (Utils.ManaDamage.containsKey(leggings)) ExDamage += Utils.ManaDamage.get(leggings);
        if (Utils.ManaDamage.containsKey(chest)) ExDamage += Utils.ManaDamage.get(chest);
        if (Utils.ManaDamage.containsKey(helmet)) ExDamage += Utils.ManaDamage.get(helmet);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ManaDamage.containsKey(offhand))
            ExDamage += Utils.ManaDamage.get(offhand);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDamage.containsKey(mainhand) && data0.contains(StringUtils.KillCount.KillCount)) {
            int killCount = data0.getInt(StringUtils.KillCount.KillCount);
            if (killCount >= 100000) killCount = 100000;
            ExDamage += Utils.ManaDamage.get(mainhand) * 0.5 * (killCount / 100000.0);
        }
        if (data.contains("ManaRune") && data.getInt("ManaRune") == 1) ExDamage *= 1 +
                ((player.getMaxHealth() - player.getHealth()) / player.getMaxHealth()) * 0.5f;
        if (stackmainhandtag.contains("Gems1")) ExDamage += Compute.ItemManaDamageGems(stackmainhandtag);
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                ExDamage += (i + 1);
            }
        }
        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 0)
            ExDamage += BaseDamage * 0.25 + 25;
        if (player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 1)
            ExDamage += BaseDamage * 0.4 + 40;
        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && !Utils.OverWorldLevelIsNight && mainhand instanceof PlainSceptre4)
            ExDamage += 45;
        if (data.contains("GemSManaDamage")) ExDamage += data.getDouble("GemSManaDamage");
        ExDamage += Compute.SArmorManaDamage(player);

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            ExDamage += Point * 4;
        } // 能力

        if (Compute.ManaSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.ManaSkillNum.Skill2) && data.getInt(StringUtils.ManaSkillNum.Skill2) > TickCount) {
            ExDamage += BaseDamage * Compute.ManaSkillLevelGet(data, 2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
            ExDamage += 75;
        // 矿工裤被动

        if (data.getInt(StringUtils.Crest.Mana.Crest0) > 0)
            ExDamage += ManaCrestAttributes.ExManaDamage[0] * data.getInt(StringUtils.Crest.Mana.Crest0);
        if (data.getInt(StringUtils.Crest.Mana.Crest1) > 0)
            ExDamage += ManaCrestAttributes.ExManaDamage[1] * data.getInt(StringUtils.Crest.Mana.Crest1);
        if (data.getInt(StringUtils.Crest.Mana.Crest2) > 0)
            ExDamage += ManaCrestAttributes.ExManaDamage[2] * data.getInt(StringUtils.Crest.Mana.Crest2);
        if (data.getInt(StringUtils.Crest.Mana.Crest3) > 0)
            ExDamage += ManaCrestAttributes.ExManaDamage[3] * data.getInt(StringUtils.Crest.Mana.Crest3);
        if (data.getInt(StringUtils.Crest.Mana.Crest4) > 0)
            ExDamage += ManaCrestAttributes.ExManaDamage[4] * data.getInt(StringUtils.Crest.Mana.Crest4);

        if (Compute.ArmorCount.Volcano(player) >= 2) ExDamage += BaseDamage * 0.25F;
        if (Compute.ArmorCount.ObsiMana(player) >= 4) ExDamage += BaseDamage * 0.25F;

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.SceptreTag.containsKey(mainhand))
            ExDamage +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaAttackDamage;

        if (Utils.PlayerManaAttackRingMap.containsKey(player))
            ExDamage += Utils.PlayerManaAttackRingMap.get(player);

        if (Utils.PlayerSpringRingManaAttackAttribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            ExDamage += Utils.PlayerSpringRingManaAttackAttribute.get(player);
        }

        if (Utils.PlayerSpringBraceletAttackAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            ExDamage += Utils.PlayerSpringBraceletAttackAttribute.get(player);
        }

        if (Utils.PlayerFireWorkGunEffect.containsKey(player) && Utils.PlayerFireWorkGunEffect.get(player) > TickCount) {
            ExDamage += BaseDamage * 0.1;
        } //新春礼炮

        if (Utils.IceSceptreEffectMap.containsKey(player) && Utils.IceSceptreEffectMap.get(player) > TickCount) {
            ExDamage += Utils.IceSceptreEffectNumMap.get(player) * 2;
        } //天源流光

        if (Utils.SpringScaleTime.containsKey(player) && Utils.SpringScaleTime.get(player) > TickCount) {
            int SwordSkill = data.getInt(StringUtils.SkillArray[0]);
            int BowSkill = data.getInt(StringUtils.SkillArray[1]);
            int ManaSkill = data.getInt(StringUtils.SkillArray[2]);
            if (ManaSkill > Math.max(SwordSkill, BowSkill))
                ExDamage += (Utils.SpringScaleEffect.get(player) + 1) * 0.1 * BaseDamage;
        } //年兽鳞片

        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCountsLastTick > TickCount) {
            ExDamage += Utils.ShangMengLiCounts * BaseDamage * 0.15;
        }

        if (Utils.LiuLiXianPlayerEffectTickMap.containsKey(player) && Utils.LiuLiXianPlayerEffectTickMap.get(player) > TickCount) {
            ExDamage += BaseDamage * Utils.LiuLiXianPlayerEffectMap.get(player) * 0.05;
        }
        if (Utils.Fengxiaoju != null && Utils.Fengxiaoju.equals(player) && Utils.FengxiaojuCurios
                && Utils.FengxiaojuPassiveTick > TickCount)
            ExDamage += BaseDamage * 0.15 * Utils.FengxiaojuPassiveCount;

        if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
            ExDamage += BaseDamage * 0.1;
        }

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) ExDamage += 400;

        if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianCountsLastTick > TickCount
                && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get()))
            ExDamage += BaseDamage * 0.15 * Utils.LiuLiXianCounts;

        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) ExDamage += 1600;

        if (BlackFeisa.Player != null && BlackFeisa.Player.equals(player) && BlackFeisa.ActiveExManaDamageTick > TickCount)
            ExDamage += BaseDamage * 0.5;
        if (BlackFeisa.Player != null && BlackFeisa.Player.equals(player) && BlackFeisa.PassiveExManaDamageTick > TickCount)
            ExDamage += BaseDamage * 0.15 * BlackFeisa.PassiveExManaDamageCount;
        if (BlackFeisaCurios.IsPlayer(player) && BlackFeisaCurios.ManaUpTick > TickCount) ExDamage += BaseDamage * 0.75;
        if (Eliaoi.EliaoiPlayerTickMap.containsKey(player) && Eliaoi.EliaoiPlayerTickMap.get(player) > TickCount)
            ExDamage += BaseDamage * 0.5;

        ExDamage += BaseDamage * EarthPower.PlayerDamageEnhance(player);
        ExDamage += Compute.CuriosAttribute.AttributeValue(player, Utils.ManaDamage, StringUtils.CuriosAttribute.ManaDamage); // 新版饰品属性加成

        ExDamage += BaseDamage * ShangMengLiSword.ManaDamageEnhance(player);

        if (BlackFeisaCurios1.IsOn(player) && BlackFeisaCurios1.DamageEnhanceTick > TickCount)
            ExDamage += BlackFeisaCurios1.DamageEnhanceValue;

        ExDamage += BlackFeisaCurios2.ManaDamageUp(player);
        ExDamage += SoraVanilla.ManaDamageUp(player);
        ExDamage += SoraVanilla1.ManaDamageUp(player);
        ExDamage += LiulixianCurios.ManaDamageEnhance(player);
        ExDamage += BaseDamage * CgswdSceptre.ExManaDamage(player);
        ExDamage += Compute.PassiveEquip.AttributeGet(player, Utils.ManaDamage); // 器灵属性加成
        ExDamage += FengXiaoJuCurios1.ExManaDamage(player);
        ExDamage += LiulixianCurios2.ExManaDamage(player);
        ExDamage += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDamage);
        ExDamage += BlackFeisaCurios.ExManaDamage(player);
        ExDamage += ZuoSiCurios.ExAttackOrManaDamage(player, false);
        ExDamage += LifeElementSceptre.ExManaDamage(player);
        ExDamage += LiulixianCurios4.manaDamageUp(player);
        /*ExDamage += ShangMengLiCurios3.ExManaDamage(player);*/
        // 请在上方添加
        double TotalDamage = BaseDamage + ExDamage;
        TotalDamage *= LiulixianCurios3.ExManaDamageUp(player);
        TotalDamage *= SoraVanilla2.ExManaDamage(player);
        TotalDamage *= CgswdCurios.ExManaDamageValue(player);
        TotalDamage *= EliaoiCurios1.ManaDamageUp(player);
        TotalDamage *= EliaoiSceptre.ManaDamageUp(player);
        TotalDamage *= (1 + MoonBook.damageEnhance(player));
        TotalDamage *= Compute.playerFantasyAttributeEnhance(player);
        TotalDamage *= ManaCurios0.PlayerFinalManaDamageEnhance(player);
        TotalDamage *= BlackFeisaCurios3.ManaDamageUp(player);
        TotalDamage *= EliaoiCurios2.ManaDamageEnhance(player);
        TotalDamage *= SoraVanillaSword.ManaDamageUp(player);
        TotalDamage *= ManaCurios2.playerFinalManaDamageEnhance(player);

        Utils.playerManaDamageBeforeTransform.put(player, TotalDamage);

        data.putDouble("NetherRuneManaDamageCompute", (TotalDamage) * 0.1f * Compute.EndRune3Judge(player, 2));
        data.putDouble("ManaDamageAfterCompute", TotalDamage);
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 0) {
            PlayerAttributes.PlayerAttackDamage(player);
            TotalDamage += data.getDouble("NetherRuneDamageCompute");
        }
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.DevilManaHelmet.get())) return (TotalDamage) * 1.25;
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 1) return 0;
        return TotalDamage;
    }

    public static double PlayerManaHealthSteal(Player player) {
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaHealSteal"))
            ManaHealSteal += stackmainhandtag.getDouble("ManaHealSteal");
        if (Utils.ManaHealthSteal.containsKey(boots)) ManaHealSteal += Utils.ManaHealthSteal.get(boots);
        if (Utils.ManaHealthSteal.containsKey(leggings)) ManaHealSteal += Utils.ManaHealthSteal.get(leggings);
        if (Utils.ManaHealthSteal.containsKey(chest)) ManaHealSteal += Utils.ManaHealthSteal.get(chest);
        if (Utils.ManaHealthSteal.containsKey(helmet)) ManaHealSteal += Utils.ManaHealthSteal.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaHealthSteal.containsKey(mainhand))
            ManaHealSteal += Utils.ManaHealthSteal.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ManaHealthSteal.containsKey(offhand))
            ManaHealSteal += Utils.ManaHealthSteal.get(offhand);
        if (Compute.ArmorCount.LifeMana(player) >= 4) ManaHealSteal += 0.05;
        if (Compute.ManaSkillLevelGet(data, 11) > 0 && Utils.SceptreTag.containsKey(mainhand))
            ManaHealSteal += Compute.ManaSkillLevelGet(data, 11) * 0.01;
        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) ManaHealSteal += 0.05;

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("Gems1")) ManaHealSteal += Compute.ItemManaHealthStealGems(helmetTag);
        if (chestTag.contains("Gems1")) ManaHealSteal += Compute.ItemManaHealthStealGems(chestTag);
        if (leggingsTag.contains("Gems1")) ManaHealSteal += Compute.ItemManaHealthStealGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ManaHealSteal += Compute.ItemManaHealthStealGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            ManaHealSteal += Compute.ItemManaHealthStealGems(stackmainhandtag);

        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player))
            ManaHealSteal += 0.05;

        ManaHealSteal += Compute.CuriosAttribute.AttributeValue(player, Utils.ManaHealthSteal, StringUtils.CuriosAttribute.ManaHealthSteal); // 新版饰品属性加成
        //请在上方添加
        ManaHealSteal *= Compute.playerFantasyAttributeEnhance(player);
        ManaHealSteal *= SoraVanillaSword.ManaHealthSteal(player);

        return ManaHealSteal;
    }

    public static double PlayerManaRecover(Player player) {
        if (player.isCreative()) return 1000;
        int TickCount = player.getServer().getTickCount();
        double ManaRecover = 0.0d;
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
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("Gems1")) ManaRecover += Compute.ItemManaRecoverGems(helmetTag);
        if (chestTag.contains("Gems1")) ManaRecover += Compute.ItemManaRecoverGems(chestTag);
        if (leggingsTag.contains("Gems1")) ManaRecover += Compute.ItemManaRecoverGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ManaRecover += Compute.ItemManaRecoverGems(bootsTag);
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("manareply"))
            ManaRecover += stackmainhandtag.getDouble("manareply");
        if (Utils.ManaRecover.containsKey(boots)) ManaRecover += Utils.ManaRecover.get(boots);
        if (Utils.ManaRecover.containsKey(leggings)) ManaRecover += Utils.ManaRecover.get(leggings);
        if (Utils.ManaRecover.containsKey(chest)) ManaRecover += Utils.ManaRecover.get(chest);
        if (Utils.ManaRecover.containsKey(helmet)) ManaRecover += Utils.ManaRecover.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaRecover.containsKey(mainhand))
            ManaRecover += Utils.ManaRecover.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ManaRecover.containsKey(offhand))
            ManaRecover += Utils.ManaRecover.get(offhand);
        if (Compute.ArmorCount.LifeMana(player) >= 2) ManaRecover += 5;
        if (Compute.ArmorCount.ObsiMana(player) >= 2) ManaRecover += 5;
        if (stackmainhandtag.contains("Gems1")) ManaRecover += Compute.ItemManaRecoverGems(stackmainhandtag);
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                ManaRecover += (i + 1) * 0.1;
            }
        }
        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 0)
            ManaRecover += 5;
        if (player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 1)
            ManaRecover += 10;
        if (data.contains("GemSManaReply")) ManaRecover += data.getDouble("GemSManaReply");
        if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && !Utils.OverWorldLevelIsNight && mainhand instanceof PlainSceptre4)
            ManaRecover += 15;
        if (data.contains(StringUtils.NetherRune.NetherRune2) && data.getInt(StringUtils.NetherRune.NetherRune2) > TickCount)
            ManaRecover += 10 * Compute.EndRune3Judge(player, 2);
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 3) {
            Item item = player.getMainHandItem().getItem();
            if (item instanceof ManaSword || item instanceof QuartzSword || item instanceof QuartzSabre || item instanceof NetherBow || item instanceof NetherSceptre)
                ManaRecover += 10 * Compute.EndRune3Judge(player, 2);
        }
        if (Compute.SwordSkillLevelGet(data, 8) > 0 && Utils.SwordTag.containsKey(mainhand))
            ManaRecover += Compute.SwordSkillLevelGet(data, 8); // 洞悉（手持近战武器时，获得1额外法力回复）
        if (Compute.ManaSkillLevelGet(data, 1) > 0 && Utils.SceptreTag.containsKey(mainhand))
            ManaRecover += Compute.ManaSkillLevelGet(data, 1) * 0.5; // 仙女护符（持有法杖时，获得额外0.5点法力回复）
        if (Compute.ManaSkillLevelGet(data, 8) > 0 && Utils.SceptreTag.containsKey(mainhand))
            ManaRecover += Compute.ManaSkillLevelGet(data, 8); // 洞悉（手持法杖时，获得1额外法力回复）

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.SceptreTag.containsKey(mainhand))
            ManaRecover +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaRecover;

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            double Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            ManaRecover += Point * 0.1;
        } // 能力

        if (Utils.PlayerSpringBraceletManaRecoverAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            ManaRecover += Utils.PlayerSpringBraceletManaRecoverAttribute.get(player);
        }

        ManaRecover += GoldenBook.GoldenBookManaRecover1(player);
        ManaRecover += GoldenBook.GoldenBookManaRecover2(player);

        ManaRecover += Compute.CuriosAttribute.AttributeValue(player, Utils.ManaRecover, StringUtils.CuriosAttribute.ManaRecover); // 新版饰品属性加成

        ManaRecover += ShangMengLiCurios.ManaRecover(player);
        // 请在上方添加
        ManaRecover *= Compute.playerFantasyAttributeEnhance(player);

        return ManaRecover;
    }

    public static double PlayerManaDefence(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double Defence = 0.0d;
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
        if (Utils.ManaDefence.containsKey(boots)) Defence += Utils.ManaDefence.get(boots);
        if (Utils.ManaDefence.containsKey(leggings)) Defence += Utils.ManaDefence.get(leggings);
        if (Utils.ManaDefence.containsKey(chest)) Defence += Utils.ManaDefence.get(chest);
        if (Utils.ManaDefence.containsKey(helmet)) Defence += Utils.ManaDefence.get(helmet);
        double ExDefence = 0.0d;
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDefence.containsKey(mainhand))
            ExDefence += Utils.ManaDefence.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ManaDefence.containsKey(offhand))
            ExDefence += Utils.ManaDefence.get(offhand);
        if (Utils.ManaDefence.containsKey(helmet) && helmetTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(helmetTag, Utils.ManaDefence.get(helmet));
        if (Utils.ManaDefence.containsKey(chest) && chestTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(chestTag, Utils.ManaDefence.get(chest));
        if (Utils.ManaDefence.containsKey(leggings) && leggingsTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(leggingsTag, Utils.ManaDefence.get(leggings));
        if (Utils.ManaDefence.containsKey(boots) && bootsTag.contains("Forging"))
            ExDefence += Compute.ForgingValue(bootsTag, Utils.ManaDefence.get(boots));
        if (boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet)
            ExDefence += Defence * 0.25;
        if (boots instanceof LifeManaArmorBoots && leggings instanceof LifeManaArmorLeggings
                && chest instanceof LifeManaArmorChest && helmet instanceof LifeManaArmorHelmet)
            ExDefence += Defence * 0.25;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 0)
            ExDefence += Defence * 0.25 + 75;
        if (player.getEffect(ModEffects.MANADefenceUP.get()) != null && player.getEffect(ModEffects.MANADefenceUP.get()).getAmplifier() == 1)
            ExDefence += Defence * 0.4 + 125;
        if (data.contains("GemSManaDefence")) ExDefence += data.getDouble("GemSManaDefence");
/*            if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
                ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 10;
            }*/
        if (data.getInt(StringUtils.PlainSwordActive.PlainSceptre) > TickCount) ExDefence += 40;

        if (Utils.SakuraBowEffectMap.containsKey(player) && Utils.SakuraBowEffectMap.get(player) > TickCount) {
            ExDefence += 100;
        } // 妖弓-樱

        if (helmetTag.contains("Gems1")) ExDefence += Compute.ItemManaDefenceGems(helmetTag);
        if (chestTag.contains("Gems1")) ExDefence += Compute.ItemManaDefenceGems(chestTag);
        if (leggingsTag.contains("Gems1")) ExDefence += Compute.ItemManaDefenceGems(leggingsTag);
        if (bootsTag.contains("Gems1")) ExDefence += Compute.ItemManaDefenceGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            ExDefence += Compute.ItemManaDefenceGems(stackmainhandtag);

        if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) ExDefence += 200;
        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) ExDefence += 200;
        if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) ExDefence += 400;
        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) ExDefence += 400;

        ExDefence += Compute.CuriosAttribute.AttributeValue(player, Utils.ManaDefence, StringUtils.CuriosAttribute.ManaDefence); // 新版饰品属性加成
        ExDefence += CastleAttackArmor.ExAttributeValue(player, CastleAttackArmor.ExManaDefence);
        ExDefence += CastleManaArmor.ExAttributeValue(player, CastleManaArmor.ExManaDefence);
        ExDefence += CastleSwiftArmor.ExAttributeValue(player, CastleSwiftArmor.ExManaDefence);
        ExDefence += LiulixianCurios4.manaDefenceUp(player);
        // 请在上方添加

        double totalDefence = Defence + ExDefence;
        totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
        totalDefence *= Compute.playerFantasyAttributeEnhance(player);
        totalDefence *= LiulixianCurios4.playerAttributeDown(player);

        if (totalDefence < 0) return 0;
        return totalDefence;
    }

    public static double PlayerHealthSteal(Player player) {
        double HealSteal = 0.0d;
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healsteal"))
            HealSteal += stackmainhandtag.getDouble("healsteal");
        if (Utils.HealthSteal.containsKey(boots)) HealSteal += Utils.HealthSteal.get(boots);
        if (Utils.HealthSteal.containsKey(leggings)) HealSteal += Utils.HealthSteal.get(leggings);
        if (Utils.HealthSteal.containsKey(chest)) HealSteal += Utils.HealthSteal.get(chest);
        if (Utils.HealthSteal.containsKey(helmet)) HealSteal += Utils.HealthSteal.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.HealthSteal.containsKey(mainhand))
            HealSteal += Utils.HealthSteal.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.HealthSteal.containsKey(offhand))
            HealSteal += Utils.HealthSteal.get(offhand);
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                HealSteal += (i + 1) * 0.0025;
            }
        }
        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 0)
            HealSteal += 0.12;
        if (player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 1)
            HealSteal += 0.25;
        if (data.contains("GemSHealSteal")) HealSteal += data.getDouble("GemSHealSteal");
        HealSteal += Compute.SArmorHealSteal(player);

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && (Utils.SwordTag.containsKey(mainhand)))
            HealSteal +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.HealthSteal;

        if (Utils.BloodManaCurios.containsKey(player) && Utils.BloodManaCurios.get(player)) HealSteal += 0.05;

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() < 0.5) {
                HealSteal += data.getDouble("CritDamageAfterCompute") / 5;
            }
        } // 封魔者法盾

        if (helmetTag.contains("Gems1")) HealSteal += Compute.ItemHealthStealGems(helmetTag);
        if (chestTag.contains("Gems1")) HealSteal += Compute.ItemHealthStealGems(chestTag);
        if (leggingsTag.contains("Gems1")) HealSteal += Compute.ItemHealthStealGems(leggingsTag);
        if (bootsTag.contains("Gems1")) HealSteal += Compute.ItemHealthStealGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            HealSteal += Compute.ItemHealthStealGems(stackmainhandtag);

        if (Utils.DevilBloodManaCurios.containsKey(player) && Utils.DevilBloodManaCurios.get(player)) HealSteal += 0.05;

        HealSteal += Compute.CuriosAttribute.AttributeValue(player, Utils.HealthSteal, StringUtils.CuriosAttribute.HealthSteal); // 新版饰品属性加成

        // 请在上方添加

        HealSteal *= Compute.playerFantasyAttributeEnhance(player);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaKnife.get())) {
            data.putDouble("HealthStealAfterCompute", HealSteal);
            return 0;
        } // 猎魔者小刀

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
            if (player.getHealth() / player.getMaxHealth() > 0.5) {
                data.putDouble("HealthStealAfterCompute", HealSteal);
                return 0;
            }
        } // 封魔者法盾

        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) return HealSteal * 0.25f;

        return HealSteal;
    }

    public static double PlayerManaPenetration(Player player) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        double DefenceRate = 1F;
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
        if (Utils.ManaPenetration.containsKey(boots))
            DefenceRate *= (1 - Utils.ManaPenetration.get(boots));
        if (Utils.ManaPenetration.containsKey(leggings))
            DefenceRate *= (1 - Utils.ManaPenetration.get(leggings));
        if (Utils.ManaPenetration.containsKey(chest))
            DefenceRate *= (1 - Utils.ManaPenetration.get(chest));
        if (Utils.ManaPenetration.containsKey(helmet))
            DefenceRate *= (1 - Utils.ManaPenetration.get(helmet));
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaPenetration.containsKey(mainhand))
            DefenceRate *= (1 - Utils.ManaPenetration.get(mainhand));
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ManaPenetration.containsKey(offhand))
            DefenceRate *= (1 - Utils.ManaPenetration.get(offhand));
        for (int i = 0; i < 21; i++) {
            if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                DefenceRate *= (1 - (i + 1) * 0.005);
            }
        }
        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 0)
            DefenceRate *= (1 - 0.2);
        if (player.getEffect(ModEffects.BREAKMANADefenceUP.get()) != null && player.getEffect(ModEffects.BREAKMANADefenceUP.get()).getAmplifier() == 1)
            DefenceRate *= (1 - 0.4);
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
        if (helmetTag.contains("Gems1")) DecreaseRate += Compute.ItemManaPenetrationGems(helmetTag);
        if (chestTag.contains("Gems1")) DecreaseRate += Compute.ItemManaPenetrationGems(chestTag);
        if (leggingsTag.contains("Gems1")) DecreaseRate += Compute.ItemManaPenetrationGems(leggingsTag);
        if (bootsTag.contains("Gems1")) DecreaseRate += Compute.ItemManaPenetrationGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            DecreaseRate += Compute.ItemManaPenetrationGems(stackmainhandtag);

        if (DecreaseRate > 0) DefenceRate *= (1 - DecreaseRate);

        DefenceRate *= (1 - Compute.CuriosAttribute.AttributeValue(player, Utils.ManaPenetration, StringUtils.CuriosAttribute.ManaPenetration)); // 新版饰品属性加成
        DecreaseRate *= (1 - BlackFeisaCurios4.Penetration(player));
        // 请在上方添加
        DefenceRate *= (2 - Compute.playerFantasyAttributeEnhance(player));

        if (CgswdSceptre.ManaPenetrationFlag(player)) return 1;
        return 1 - DefenceRate;
    }

    public static double PlayerManaPenetration0(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double ManaPenetration0 = 0.0d;
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaBreakDefence0"))
            ManaPenetration0 += stackmainhandtag.getDouble("ManaBreakDefence0");
        if (Utils.ManaPenetration0.containsKey(boots))
            ManaPenetration0 += Utils.ManaPenetration0.get(boots);
        if (Utils.ManaPenetration0.containsKey(leggings))
            ManaPenetration0 += Utils.ManaPenetration0.get(leggings);
        if (Utils.ManaPenetration0.containsKey(chest))
            ManaPenetration0 += Utils.ManaPenetration0.get(chest);
        if (Utils.ManaPenetration0.containsKey(helmet))
            ManaPenetration0 += Utils.ManaPenetration0.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaPenetration0.containsKey(mainhand))
            ManaPenetration0 += Utils.ManaPenetration0.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.ManaPenetration0.containsKey(offhand))
            ManaPenetration0 += Utils.ManaPenetration0.get(offhand);
/*            if (ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand))
                ManaBreakDefence0 += ManaSkillLevelGet(data, 10) * 5;*/
        if (data.contains("GemSManaDefencePenetration0"))
            ManaPenetration0 += data.getDouble("GemSManaDefencePenetration0");
        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.SceptreTag.containsKey(mainhand))
            ManaPenetration0 +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.ManaPenetration0;

        if (data.getInt(StringUtils.Crest.Sky.Crest0) > 0)
            ManaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[0] * data.getInt(StringUtils.Crest.Sky.Crest0);
        if (data.getInt(StringUtils.Crest.Sky.Crest1) > 0)
            ManaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[1] * data.getInt(StringUtils.Crest.Sky.Crest1);
        if (data.getInt(StringUtils.Crest.Sky.Crest2) > 0)
            ManaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[2] * data.getInt(StringUtils.Crest.Sky.Crest2);
        if (data.getInt(StringUtils.Crest.Sky.Crest3) > 0)
            ManaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[3] * data.getInt(StringUtils.Crest.Sky.Crest3);
        if (data.getInt(StringUtils.Crest.Sky.Crest4) > 0)
            ManaPenetration0 += SkyCrestAttributes.ManaDefencePenetration0[4] * data.getInt(StringUtils.Crest.Sky.Crest4);

        if (stackmainhandtag.contains(StringUtils.ManaCore.ManaCore) && stackmainhandtag.getString(StringUtils.ManaCore.ManaCore).
                equals(StringUtils.ManaCore.KazeCore)) {
            ManaPenetration0 += PlayerMovementSpeed(player) / 0.01;
        }

        if (data.contains("volcanogems") && data.getBoolean("volcanogems")) ManaPenetration0 += 10;
        if (data.contains("plaingems") && data.getBoolean("plaingems")) ManaPenetration0 += 10;
        if (data.contains("lakegems") && data.getBoolean("lakegems")) ManaPenetration0 += 10;
        if (data.contains("forestgems") && data.getBoolean("forestgems")) ManaPenetration0 += 10;

        if (Compute.ArmorCount.Volcano(player) >= 4) ManaPenetration0 += 35;

        if (mainhand.equals(ModItems.ShipSceptre.get())) {
            if (Utils.ShipSceptreWaterBlockNum.containsKey(player)) {
                int Count = Utils.ShipSceptreWaterBlockNum.get(player);
                if (Count > 0) {
                    ManaPenetration0 += 100 + 0.5 * Count;
                }
            }
        } // 唤潮之杖

        if (Utils.PlayerSpringRingManaPenetration0Attribute.containsKey(player) && Utils.PlayerSpringRingLevelRequire.get(player) <= player.experienceLevel) {
            ManaPenetration0 += Utils.PlayerSpringRingManaPenetration0Attribute.get(player);
        }

        if (Utils.PlayerSpringBraceletManaPenetration0Attribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            ManaPenetration0 += Utils.PlayerSpringBraceletManaPenetration0Attribute.get(player);
        }

        if (Utils.WitherBookPlayerEffectTick.containsKey(player) && Utils.WitherBookPlayerEffectTick.get(player) > TickCount) {
            ManaPenetration0 += Utils.WitherBookPlayerEffectNum.get(player);
        } // 凋零秘典

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if (helmetTag.contains("Gems1")) ManaPenetration0 += Compute.ItemManaPenetration0Gems(helmetTag);
        if (chestTag.contains("Gems1")) ManaPenetration0 += Compute.ItemManaPenetration0Gems(chestTag);
        if (leggingsTag.contains("Gems1")) ManaPenetration0 += Compute.ItemManaPenetration0Gems(leggingsTag);
        if (bootsTag.contains("Gems1")) ManaPenetration0 += Compute.ItemManaPenetration0Gems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            ManaPenetration0 += Compute.ItemManaPenetration0Gems(stackmainhandtag);

        ManaPenetration0 += Compute.CuriosAttribute.AttributeValue(player, Utils.ManaPenetration0, StringUtils.CuriosAttribute.ManaPenetration0); // 新版饰品属性加成

        ManaPenetration0 += TabooManaArmor.storeCostValue(player);
        ManaPenetration0 += TabooManaArmor.PenetrationDirectEnhance(player);

        ManaPenetration0 += Compute.PassiveEquip.AttributeGet(player, Utils.ManaPenetration0); // 器灵属性加成
        ManaPenetration0 += CastleSword.ExPenetration0(player); // 暗黑武器主动
        ManaPenetration0 += ManaRune3.ManaPenetration0(player); // 法师之威
        // 请在上方添加
        ManaPenetration0 *= Compute.playerFantasyAttributeEnhance(player);
        ManaPenetration0 *= LiuLiXianCurios1F.PenetrationUp(player);
        return ManaPenetration0;
    }

    public static double PlayerMaxMana(Player player) {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        double MaxMana = 0.0d;
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
        if (Utils.MaxMana.containsKey(boots)) MaxMana += Utils.MaxMana.get(boots);
        if (Utils.MaxMana.containsKey(leggings)) MaxMana += Utils.MaxMana.get(leggings);
        if (Utils.MaxMana.containsKey(chest)) MaxMana += Utils.MaxMana.get(chest);
        if (Utils.MaxMana.containsKey(helmet)) MaxMana += Utils.MaxMana.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.MaxMana.containsKey(mainhand))
            MaxMana += Utils.MaxMana.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.MaxMana.containsKey(offhand))
            MaxMana += Utils.MaxMana.get(offhand);
        if (data.contains("GemSMaxMana")) MaxMana += data.getDouble("GemSMaxMana");

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            MaxMana += Point;
        } // 能力

        if (stackmainhandtag.contains(StringUtils.SoulEquipForge) && Utils.SceptreTag.containsKey(mainhand))
            MaxMana +=
                    stackmainhandtag.getInt(StringUtils.SoulEquipForge) * SoulEquipAttribute.ForgingAddition.MaxMana;

        if (Utils.PlayerSpringBraceletMaxManaAttribute.containsKey(player) && Utils.PlayerSpringBraceletLevelRequire.get(player) <= player.experienceLevel) {
            MaxMana += Utils.PlayerSpringBraceletMaxManaAttribute.get(player);
        }

        if (Utils.EarthBookPlayerEffectMap.containsKey(player) && Utils.EarthBookPlayerEffectMap.get(player) > TickCount) {
            MaxMana += player.getMaxHealth() * 0.025;
        } // 地蕴传世秘典

        MaxMana += Compute.CuriosAttribute.AttributeValue(player, Utils.MaxMana, StringUtils.CuriosAttribute.MaxMana); // 新版饰品属性加成

        MaxMana += Compute.PassiveEquip.AttributeGet(player, Utils.MaxMana); // 器灵属性加成

        // 请在上方添加
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                && PlayerAttributes.PlayerDefence(player) > 1000) {
            MaxMana *= 1.25;
        }

        if (mainhand.equals(ModItems.LiuLiXianSceptre.get()) || mainhand.equals(ModItems.LiuLiXianSword.get())) {
            MaxMana += PlayerMovementSpeed(player) * 25;
            MaxMana *= 1.1;
        }
        MaxMana *= LiulixianCurios3.MaxManaUp(player);
        MaxMana *= ShangMengLiSword.MaxManaEnhance(player);
        MaxMana *= Compute.playerFantasyAttributeEnhance(player);
        MaxMana *= FengXiaoJuCurios1.MaxMana(player);

        return MaxMana;
    }

    
}
