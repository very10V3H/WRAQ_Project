package com.Very.very.ValueAndTools;

import com.Very.very.Files.FileHandler;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.ArmorHuskRecall;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.BlackForestSword4;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.ArmorForestRecall;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.ForestSword4;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.ArmorkazeRecall;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.KazeSword4;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.*;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.ArmorNetherRecall;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.ArmorSeaRecall;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.SeaSword4;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.ArmorSnowRecall;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.SnowSword4;
import com.Very.very.Series.EndSeries.EventControl.SpiderRecall.ArmorSpiderRecall;
import com.Very.very.Series.EndSeries.EventControl.VolcanoRecall.ArmorVolcanoRecall;
import com.Very.very.Series.EndSeries.EventControl.VolcanoRecall.VolcanoSword4;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.AnimationPackets.PickAxeAttackAnimationRequestC2SPacket;
import com.Very.very.NetWorking.Packets.AnimationPackets.SwordAttackAnimationRequestC2SPacket;
import com.Very.very.NetWorking.Packets.ManaSyncS2CPacket;
import com.Very.very.NetWorking.Packets.ShieldSyncS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.BowSkill12S2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ManaSkill12S2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ManaSkill13S2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.SwordSkill12S2CPacket;
import com.Very.very.NetWorking.Packets.USE.UtilsLakeSwordS2CPacket;
import com.Very.very.NetWorking.Packets.USE.UtilsSnowSwordS2CPacket;
import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.Series.NetherSeries.Equip.*;
import com.Very.very.Series.NetherSeries.Power.MagmaPower;
import com.Very.very.Series.NetherSeries.Power.PiglinPower;
import com.Very.very.Series.NetherSeries.Power.WitherBoneMealPower;
import com.Very.very.Series.NetherSeries.Power.WitherBonePower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.BossItems.ForestBossSword;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sceptre.PlainSceptre4;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSwrod1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.BossItems.VolcanoBossSword;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest.BlackForestSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest.BlackForestSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest.BlackForestSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest.BlackForestSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword3;
import com.Very.very.ValueAndTools.Utils.*;
import com.Very.very.ValueAndTools.Utils.Struct.Gather;
import com.Very.very.ValueAndTools.Utils.Struct.Shield;
import com.Very.very.Render.Effects.ModEffects;
import com.Very.very.Entity.HEntity;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Math.*;
import static java.lang.Math.abs;


public class Compute{
    public static float PlayerAttackDamage(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        float attackdamage = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("attackdamage")) attackdamage += stackmainhandtag.getFloat("attackdamage");
        if(Utils.BaseDamage.containsKey(boots)) attackdamage += Utils.BaseDamage.get(boots);
        if(Utils.BaseDamage.containsKey(leggings)) attackdamage += Utils.BaseDamage.get(leggings);
        if(Utils.BaseDamage.containsKey(chest)) attackdamage += Utils.BaseDamage.get(chest);
        if(Utils.BaseDamage.containsKey(helmet)) attackdamage += Utils.BaseDamage.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.BaseDamage.containsKey(mainhand)) attackdamage +=  Utils.BaseDamage.get(mainhand);
        float ExDamage = 0.0F;
        if(Utils.OffHandTag.containsKey(offhand) && Utils.BaseDamage.containsKey(offhand)) ExDamage +=  Utils.BaseDamage.get(offhand);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.BaseDamage.containsKey(mainhand) && data0.contains("Forging")) ExDamage += Utils.BaseDamage.get(mainhand) * (data0.getInt("Forging") / 20.0F);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.BaseDamage.containsKey(mainhand) && data0.contains(StringUtils.KillCount.KillCount)) {
            int killCount = data0.getInt(StringUtils.KillCount.KillCount);
            if (killCount >= 100000) killCount = 100000;
            ExDamage += Utils.BaseDamage.get(mainhand) * 0.5 * (killCount/100000.0);
        }
        if(data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 0) ExDamage += player.getAttribute(Attributes.MAX_HEALTH).getValue()*0.1;
        if(data.contains("volcanoRune") && data.getInt("volcanoRune") == 1) ExDamage += ((player.getMaxHealth()-player.getHealth())/player.getMaxHealth()) * attackdamage * 0.25;
        if(helmetTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(helmetTag);
        if(chestTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(chestTag);
        if(leggingsTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(leggingsTag);
        if(bootsTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(bootsTag);
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) ExDamage += Compute.ItemBaseDamageGems(stackmainhandtag);
        if(boots instanceof VolcanoArmorBoots && leggings instanceof VolcanoArmorLeggings
                && chest instanceof VolcanoArmorChest && helmet instanceof VolcanoArmorHelmet) ExDamage += attackdamage * 0.2F;
        if(boots instanceof ObsiManaArmorBoots && leggings instanceof ObsiManaArmorLeggings
                && chest instanceof ObsiManaArmorChest && helmet instanceof ObsiManaArmorHelmet) ExDamage += attackdamage * 0.2F;
        if(data.contains("Sword")) ExDamage += attackdamage * (data.getInt("Sword")/1000000.0F);
        if(data.contains("Barker")) ExDamage += attackdamage * ((data.getInt("Barker")/100000.0F)*0.05);
        if(data.contains("volcanogems") && data.getBoolean("volcanogems")) ExDamage += attackdamage * 0.1;
        if(data.contains("ManaSwordActive") && data.getInt("ManaSwordActive") > 0) ExDamage += data.getInt("ManaSwordActive");
        if(data.contains("pigLinPower") && data.contains("pigLinPowerNumber") && data.contains("pigLinPowerAp") && data.getInt("pigLinPower") > 0)
        {
            ExDamage += 0.03 * data.getFloat("pigLinPowerAp") * data.getInt("pigLinPowerNumber");
        }
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                ExDamage += i+1;
            }
        }
        if(data.contains("SkyArmor80") && data.getBoolean("SkyArmor80")) ExDamage += attackdamage*0.25;
        if(data.contains("SkyArmor40") && data.getBoolean("SkyArmor40")) ExDamage += attackdamage*0.1;
        if(boots instanceof NetherArmorBoots && leggings instanceof NetherArmorLeggings
                && chest instanceof NetherArmorChest && helmet instanceof NetherArmorHelmet){
            ExDamage += MaxHealImprove(player) * 0.15;
        }
        if(player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 0) ExDamage += attackdamage * 0.25 + 25;
        if(player.getEffect(ModEffects.ATTACKUP.get()) != null && player.getEffect(ModEffects.ATTACKUP.get()).getAmplifier() == 1) ExDamage += attackdamage * 0.40 + 40;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill0) && data.getInt(StringUtils.VolcanoSwordSkill.Skill0) > TickCount ) ExDamage += 10;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill1) && data.getInt(StringUtils.VolcanoSwordSkill.Skill1) > TickCount ) ExDamage += 15;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill2) && data.getInt(StringUtils.VolcanoSwordSkill.Skill2) > TickCount ) ExDamage += 20;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill3) && data.getInt(StringUtils.VolcanoSwordSkill.Skill3) > TickCount ) ExDamage += 30;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill4) && data.getInt(StringUtils.VolcanoSwordSkill.Skill4) > TickCount ) ExDamage += 75;
        if (mainhand.equals(Moditems.GoldSword0.get())) ExDamage += data.getFloat("VB") / 10000.0;
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = data.getInt(StringUtils.Ability.Power) + (data.getInt(StringUtils.Ability.Power) / 10) * 5;
            ExDamage += Point;
        }

        if (SwordSkillLevelGet(data,2) > 0 && data.contains(StringUtils.SwordSkillNum.Skill2) && data.getInt(StringUtils.SwordSkillNum.Skill2) > TickCount) {
            ExDamage += attackdamage * SwordSkillLevelGet(data,2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）‘

        if (BowSkillLevelGet(data,2) > 0 && data.contains(StringUtils.BowSkillNum.Skill2) && data.getInt(StringUtils.BowSkillNum.Skill2) > TickCount) {
            ExDamage += attackdamage * BowSkillLevelGet(data,2) * 0.02;
        } // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）

        if (SwordSkillLevelGet(data,5) > 0 && data.contains(StringUtils.SwordSkillNum.Skill5) && data.getInt(StringUtils.SwordSkillNum.Skill5) > TickCount) {
            ExDamage += attackdamage * SwordSkillLevelGet(data,5) * 0.01;
        } // 剑术-狂暴（造成暴击后，提升1%攻击力，持续3s）

        if (BowSkillLevelGet(data,5) > 0 && data.contains(StringUtils.BowSkillNum.Skill5) && data.getInt(StringUtils.BowSkillNum.Skill5) > TickCount) {
            ExDamage += attackdamage * BowSkillLevelGet(data,5) * 0.01;
        } // 弓术-狂暴（造成暴击后，提升1%攻击力，持续5s）

        if (SwordSkillLevelGet(data,6) > 0 && Utils.SwordSkill6Map.containsKey(player) && Utils.SwordSkill6Map.get(player).getTime() > TickCount) {
            ExDamage += attackdamage * SwordSkillLevelGet(data,6) * 0.003 * Utils.SwordSkill6Map.get(player).getCount();
        } // 完美（持续造成暴击，将提供至多30%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）

        if (BowSkillLevelGet(data,6) > 0 && Utils.BowSkill6Map.containsKey(player) && Utils.BowSkill6Map.get(player).getTime() > TickCount) {
            ExDamage += attackdamage * BowSkillLevelGet(data,6) * 0.01 * Utils.BowSkill6Map.get(player).getCount();
        } // 完美（持续的命中目标的箭矢，将提供至多3%攻击力，持续10s，在十次命中后达最大值，在未命中时重置层数）

        if (BowSkillLevelGet(data,8) > 0 && Utils.BowTag.containsKey(mainhand)) {
            ExDamage += attackdamage * BowSkillLevelGet(data,8) * 0.02;
        } // 锻弦-力量（手持弓时，获得2%攻击力）

        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            ExDamage += Point;
        } // 能力

        data.putFloat("NetherRuneDamageCompute",(attackdamage + ExDamage) * 0.6f);
        if(data.contains("GemSAttack")) ExDamage += data.getDouble("GemSAttack");
        if (data.contains("snowRune") && data.getInt("snowRune") == 1) {
            PlayerCriticalHitRate(player);
            PlayerCriticalHitDamage(player);
            ExDamage += attackdamage * (data.getFloat("snowRune1Rate") + data.getFloat("snowRune1Damage")) * 0.8;
        }
        ExDamage += SArmorAttackDamage(player);
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 1) {
            PlayerManaDamage(player);
            ExDamage += data.getFloat("NetherRuneManaDamageCompute");
        }
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 0) return 0;
        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) return (attackdamage + ExDamage) * 0.5f;
        return attackdamage + ExDamage;
    }
    public static float PlayerDefence(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        float Defence = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data0 = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag data1 = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag data2 = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag data3 = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(Utils.Defence.containsKey(boots)) Defence += Utils.Defence.get(boots);
        if(Utils.Defence.containsKey(leggings)) Defence += Utils.Defence.get(leggings);
        if(Utils.Defence.containsKey(chest)) Defence += Utils.Defence.get(chest);
        if(Utils.Defence.containsKey(helmet)) Defence += Utils.Defence.get(helmet);
        float ExDefence = 0.0F;
        if(helmetTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(helmetTag);
        if(chestTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(chestTag);
        if(leggingsTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(leggingsTag);
        if(bootsTag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(bootsTag);
        if(stackmainhandtag.contains("Gems1")) ExDefence += Compute.ItemDefenceGems(stackmainhandtag);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.Defence.containsKey(mainhand)) ExDefence +=  Utils.Defence.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.Defence.containsKey(offhand)) ExDefence +=  Utils.Defence.get(offhand);
        if(Utils.Defence.containsKey(helmet) && data0.contains("Forging")) ExDefence += Utils.Defence.get(helmet) * (data0.getInt("Forging") / 20.0F);
        if(Utils.Defence.containsKey(chest) && data1.contains("Forging")) ExDefence += Utils.Defence.get(chest) * (data1.getInt("Forging") / 20.0F);
        if(Utils.Defence.containsKey(leggings) && data2.contains("Forging")) ExDefence += Utils.Defence.get(leggings) * (data2.getInt("Forging") / 20.0F);
        if(Utils.Defence.containsKey(boots) && data3.contains("Forging")) ExDefence += Utils.Defence.get(boots) * (data3.getInt("Forging") / 20.0F);
        if(boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet) ExDefence += Defence * 0.25;
        if(boots instanceof LifeManaArmorBoots && leggings instanceof LifeManaArmorLeggings
                && chest instanceof LifeManaArmorChest && helmet instanceof LifeManaArmorHelmet) ExDefence += Defence * 0.25;
        if(data.contains("forestgems") && data.getBoolean("forestgems")) ExDefence += Defence * 0.1;
        if(player.getEffect(ModEffects.DEFENCEUP.get()) != null && player.getEffect(ModEffects.DEFENCEUP.get()).getAmplifier() == 0) ExDefence += Defence * 0.25 + 40;
        if(player.getEffect(ModEffects.DEFENCEUP.get()) != null && player.getEffect(ModEffects.DEFENCEUP.get()).getAmplifier() == 1) ExDefence += Defence * 0.40 + 80;
        if(data.contains("GemSDefence")) ExDefence += data.getDouble("GemSDefence");
        if(data.contains("ManaRune") && data.getInt("ManaRune") == 3) return (Defence+ExDefence)*0.5f;
        if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
            ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 20;
        }
        if (Defence + ExDefence < 0) return 0;
        return Defence+ExDefence;
    }
    public static float PlayerBreakDefence(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        float DefenceRate = 1;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("breakdefence")) DefenceRate *= (1-stackmainhandtag.getFloat("breakdefence"));
        if(Utils.BreakDefence.containsKey(boots)) DefenceRate *= (1-Utils.BreakDefence.get(boots));
        if(Utils.BreakDefence.containsKey(leggings)) DefenceRate *= (1-Utils.BreakDefence.get(leggings));
        if(Utils.BreakDefence.containsKey(chest)) DefenceRate *= (1-Utils.BreakDefence.get(chest));
        if(Utils.BreakDefence.containsKey(helmet)) DefenceRate *= (1-Utils.BreakDefence.get(helmet));
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.BreakDefence.containsKey(mainhand)) DefenceRate *= (1-Utils.BreakDefence.get(mainhand));
        if(Utils.OffHandTag.containsKey(offhand) && Utils.BreakDefence.containsKey(offhand)) DefenceRate *= (1-Utils.BreakDefence.get(offhand));
        if (data.contains(StringUtils.VolcanoBowSkill) && data.getInt(StringUtils.VolcanoBowSkill) > TickCount) DefenceRate *= (1-0.4);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                DefenceRate *= (1-(i+1) * 0.005);
            }
        }
        if(player.getEffect(ModEffects.BREAKDEFENCEUP.get()) != null && player.getEffect(ModEffects.BREAKDEFENCEUP.get()).getAmplifier() == 0) DefenceRate *= (1-0.20f);
        if(player.getEffect(ModEffects.BREAKDEFENCEUP.get()) != null && player.getEffect(ModEffects.BREAKDEFENCEUP.get()).getAmplifier() == 1) DefenceRate *= (1-0.45f);
        if (data.contains(StringUtils.ForestSwordSkill0) && data.getInt(StringUtils.ForestSwordSkill0) > TickCount) DefenceRate *= (1-0.3);
        if (data.contains(StringUtils.ForestSwordSkill1) && data.getInt(StringUtils.ForestSwordSkill1) > TickCount) DefenceRate *= (1-0.3);
        if (data.contains("GemSBreakDefence")) DefenceRate *= (1-data.getDouble("GemSBreakDefence"));
        if (SwordSkillLevelGet(data,10) > 0 && Utils.SwordTag.containsKey(mainhand)) DefenceRate *= (1 - SwordSkillLevelGet(data,10) * 0.03);
        if (BowSkillLevelGet(data,10) > 0 && Utils.BowTag.containsKey(mainhand)) DefenceRate *= (1 - BowSkillLevelGet(data,10) * 0.03);

        return 1-DefenceRate;
    }
    public static float PlayerHealSteal(Player player)
    {
        float HealSteal = 0.0F;
        CompoundTag data = player.getPersistentData();
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healsteal")) HealSteal += stackmainhandtag.getFloat("healsteal");
        if(Utils.HealSteal.containsKey(boots)) HealSteal += Utils.HealSteal.get(boots);
        if(Utils.HealSteal.containsKey(leggings)) HealSteal += Utils.HealSteal.get(leggings);
        if(Utils.HealSteal.containsKey(chest)) HealSteal += Utils.HealSteal.get(chest);
        if(Utils.HealSteal.containsKey(helmet)) HealSteal += Utils.HealSteal.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.HealSteal.containsKey(mainhand)) HealSteal +=  Utils.HealSteal.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.HealSteal.containsKey(offhand)) HealSteal +=  Utils.HealSteal.get(offhand);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                HealSteal += (i+1) * 0.0025;
            }
        }
        if(player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 0) HealSteal += 0.12;
        if(player.getEffect(ModEffects.HEALSTEALUP.get()) != null && player.getEffect(ModEffects.HEALSTEALUP.get()).getAmplifier() == 1) HealSteal += 0.25;
        if(data.contains("GemSHealSteal")) HealSteal += data.getDouble("GemSHealSteal");
        HealSteal += SArmorHealSteal(player);
        if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0) return HealSteal * 0.25f;
        return HealSteal;
    }
    public static float PlayerCriticalHitRate(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float CriticalHitRate = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(helmetTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(helmetTag);
        if(chestTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(chestTag);
        if(leggingsTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(leggingsTag);
        if(bootsTag.contains("Gems1")) CriticalHitRate += Compute.ItemCritRateGems(bootsTag);
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) CriticalHitRate += Compute.ItemCritRateGems(stackmainhandtag);
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticalrate")) CriticalHitRate += stackmainhandtag.getFloat("criticalrate");
        if(Utils.CriticalHitRate.containsKey(boots)) CriticalHitRate += Utils.CriticalHitRate.get(boots);
        if(Utils.CriticalHitRate.containsKey(leggings)) CriticalHitRate += Utils.CriticalHitRate.get(leggings);
        if(Utils.CriticalHitRate.containsKey(chest)) CriticalHitRate += Utils.CriticalHitRate.get(chest);
        if(Utils.CriticalHitRate.containsKey(helmet)) CriticalHitRate += Utils.CriticalHitRate.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.CriticalHitRate.containsKey(mainhand)) CriticalHitRate +=  Utils.CriticalHitRate.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.CriticalHitRate.containsKey(offhand)) CriticalHitRate +=  Utils.CriticalHitRate.get(offhand);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                CriticalHitRate += (i+1) * 0.005;
            }
        }
        if(player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 0) CriticalHitRate += 0.2;
        if(player.getEffect(ModEffects.CRITRATEUP.get()) != null && player.getEffect(ModEffects.CRITRATEUP.get()).getAmplifier() == 1) CriticalHitRate += 0.4;
        if(data.contains("GemSCritRate")) CriticalHitRate += data.getDouble("GemSCritRate");
        CriticalHitRate += SArmorCritRate(player);
        if (BowSkillLevelGet(data,11) > 0 && Utils.BowTag.containsKey(mainhand)) {
            CriticalHitRate += BowSkillLevelGet(data,11) * 0.02;
        } // 锻弦-平衡（手持弓时，获得额外2%暴击几率）

        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            CriticalHitRate += Point * 0.001;
        }

        if (data.contains("snowRune") && data.getInt("snowRune") == 1) {
            data.putFloat("snowRune1Rate",CriticalHitRate);
            return 0;
        }
        return CriticalHitRate;
    }
    public static float PlayerCriticalHitDamage(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        float CHitDamage = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(helmetTag.contains("Gems1")) CHitDamage += Compute.ItemCritDamageGems(helmetTag);
        if(chestTag.contains("Gems1")) CHitDamage += Compute.ItemCritDamageGems(chestTag);
        if(leggingsTag.contains("Gems1")) CHitDamage += Compute.ItemCritDamageGems(leggingsTag);
        if(bootsTag.contains("Gems1")) CHitDamage += Compute.ItemCritDamageGems(bootsTag);
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) CHitDamage += Compute.ItemCritDamageGems(stackmainhandtag);
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("criticaldamage")) CHitDamage += stackmainhandtag.getFloat("criticaldamage");
        if(Utils.CHitDamage.containsKey(boots)) CHitDamage += Utils.CHitDamage.get(boots);
        if(Utils.CHitDamage.containsKey(leggings)) CHitDamage += Utils.CHitDamage.get(leggings);
        if(Utils.CHitDamage.containsKey(chest)) CHitDamage += Utils.CHitDamage.get(chest);
        if(Utils.CHitDamage.containsKey(helmet)) CHitDamage += Utils.CHitDamage.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.CHitDamage.containsKey(mainhand)) CHitDamage +=  Utils.CHitDamage.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.CHitDamage.containsKey(offhand)) CHitDamage +=  Utils.CHitDamage.get(offhand);
        if(data.contains("Bow")) CHitDamage += (data.getInt("Bow")/30000.0F)*0.3F;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill0) && data.getInt(StringUtils.VolcanoSwordSkill.Skill0) > TickCount ) CHitDamage *= 2.2;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill1) && data.getInt(StringUtils.VolcanoSwordSkill.Skill1) > TickCount ) CHitDamage *= 1.9;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill2) && data.getInt(StringUtils.VolcanoSwordSkill.Skill2) > TickCount ) CHitDamage *= 1.7;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill3) && data.getInt(StringUtils.VolcanoSwordSkill.Skill3) > TickCount ) CHitDamage *= 1.6;
        if(data.contains(StringUtils.VolcanoSwordSkill.Skill4) && data.getInt(StringUtils.VolcanoSwordSkill.Skill4) > TickCount ) CHitDamage *= 1.5;
        if(data.contains("plaingems") && data.getBoolean("plaingems")) CHitDamage += 0.1;
        if(data.contains("forestgems") && data.getBoolean("forestgems")) CHitDamage += 0.1;
        if(data.contains("lakegems") && data.getBoolean("lakegems")) CHitDamage += 0.1;
        if(data.contains("volcanogems") && data.getBoolean("volcanogems")) CHitDamage += 0.1;
        if(data.contains("volcanoRune") && data.getInt("volcanoRune") == 3) CHitDamage += 0.5;
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                CHitDamage += (i+1) * 0.01;
            }
        }
        if(boots instanceof NetherArmorBoots && leggings instanceof NetherArmorLeggings
                && chest instanceof NetherArmorChest && helmet instanceof NetherArmorHelmet){
            CHitDamage += PlayerHealthRecover(player)*0.1;
        }
        if(player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 0) CHitDamage += 0.4;
        if(player.getEffect(ModEffects.CRITDAMAGEUP.get()) != null && player.getEffect(ModEffects.CRITDAMAGEUP.get()).getAmplifier() == 1) CHitDamage += 0.8;
        if(data.contains("GemSCritDamage")) CHitDamage += data.getDouble("GemSCritDamage");
        CHitDamage += SArmorCritDamage(player);

        if (BowSkillLevelGet(data,7) > 0 && Utils.BowTag.containsKey(mainhand)) {
            CHitDamage += BowSkillLevelGet(data,7) * 0.06;
        } // 锻矢-锋利（手持弓时，获得6%暴击伤害）

        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            CHitDamage += Point * 0.01;
        } // 能力

        if (data.contains("snowRune") && data.getInt("snowRune") == 1) {
            data.putFloat("snowRune1Damage",CHitDamage);
            return 0;
        }
        return CHitDamage;
    }
    public static float PlayerAttackRangeUp(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float RangeUp = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
                CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) RangeUp += Compute.ItemCritDamageGems(stackmainhandtag);
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("AttackRangeUp")) RangeUp += stackmainhandtag.getFloat("criticaldamage");
        if(Utils.AttackRangeUp.containsKey(boots)) RangeUp += Utils.AttackRangeUp.get(boots);
        if(Utils.AttackRangeUp.containsKey(leggings)) RangeUp += Utils.AttackRangeUp.get(leggings);
        if(Utils.AttackRangeUp.containsKey(chest)) RangeUp += Utils.AttackRangeUp.get(chest);
        if(Utils.AttackRangeUp.containsKey(helmet)) RangeUp += Utils.AttackRangeUp.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.AttackRangeUp.containsKey(mainhand)) RangeUp +=  Utils.AttackRangeUp.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.AttackRangeUp.containsKey(offhand)) RangeUp +=  Utils.AttackRangeUp.get(offhand);
        if (SwordSkillLevelGet(data,11) > 0 && Utils.SwordTag.containsKey(mainhand)) RangeUp += SwordSkillLevelGet(data,11) * 0.02;

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            RangeUp += Point * 0.01;
        } // 能力

        return RangeUp;
    }
    public static float PlayerSpeedImprove(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        float SpeedUp = 0;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("speedup")) SpeedUp += stackmainhandtag.getFloat("speedup");
        if(Utils.SpeedUp.containsKey(boots)) SpeedUp += Utils.SpeedUp.get(boots);
        if(Utils.SpeedUp.containsKey(leggings)) SpeedUp += Utils.SpeedUp.get(leggings);
        if(Utils.SpeedUp.containsKey(chest)) SpeedUp += Utils.SpeedUp.get(chest);
        if(Utils.SpeedUp.containsKey(helmet)) SpeedUp += Utils.SpeedUp.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.SpeedUp.containsKey(mainhand)) SpeedUp +=  Utils.SpeedUp.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.SpeedUp.containsKey(offhand)) SpeedUp +=  Utils.SpeedUp.get(offhand);
        if(helmetTag.contains("Gems1")) SpeedUp += Compute.ItemSpeedUpGems(helmetTag);
        if(chestTag.contains("Gems1")) SpeedUp += Compute.ItemSpeedUpGems(chestTag);
        if(leggingsTag.contains("Gems1")) SpeedUp += Compute.ItemSpeedUpGems(leggingsTag);
        if(bootsTag.contains("Gems1")) SpeedUp += Compute.ItemSpeedUpGems(bootsTag);
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) SpeedUp += Compute.ItemSpeedUpGems(stackmainhandtag);
        if(data.contains("Green") && data.getInt("Green") == 2) SpeedUp += (((player.getMaxHealth()-player.getHealth())*1.0 / player.getMaxHealth()) / 2.0);
        if(data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 1 && data.contains(StringUtils.ForestRune.ForestRune1) && data.getInt(StringUtils.ForestRune.ForestRune1) > TickCount) SpeedUp += 0.5F;
        if (data.contains(StringUtils.LakeSwordSkill) && data.getInt(StringUtils.LakeSwordSkill) > TickCount) SpeedUp += 0.5F;
        if (data.contains(StringUtils.PlainBowSkill) && data.getInt(StringUtils.PlainBowSkill) > TickCount) SpeedUp += 0.4F;
        if(data.contains("ManaRune") && data.getInt("ManaRune") == 0 && data.contains("MANA")) SpeedUp +=
                0.5F * ((float) (data.getInt("MAXMANA") - data.getInt("MANA")) /data.getInt("MAXMANA"));
        if(data.contains("pigLinPower") && data.contains("pigLinPowerNumber") && data.contains("pigLinPowerAp") && data.getInt("pigLinPower") > 0)
        {
            SpeedUp += 0.1 * data.getInt("pigLinPowerNumber");
        }
        if(data.contains("QuartzSabreSpeedUp") && data.getInt("QuartzSabreSpeedUp") > 0) SpeedUp += 0.5F;
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                SpeedUp += (i+1) * 0.005;
            }
        }
        if(data.contains("SkyArmor80") && data.getBoolean("SkyArmor80")) SpeedUp += 0.2;
        if(player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 0) SpeedUp += 0.5;
        if(player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 1) SpeedUp += 1.0;
        if(data.contains("GemSSpeed")) SpeedUp += data.getDouble("GemSSpeed");
        if (mainhand.equals(Moditems.GoldSword0.get())) SpeedUp += data.getFloat("VB") / 1000000.0;

        if (SwordSkillLevelGet(data,9) > 0 && Utils.SwordTag.containsKey(mainhand)) {
            SpeedUp += SwordSkillLevelGet(data,9) * 0.08;
        } // 剑舞（手持近战武器时，获得8%额外移动速度）

        if (BowSkillLevelGet(data,1) > 0 && Utils.BowTag.containsKey(mainhand)) {
            SpeedUp += BowSkillLevelGet(data,1) * 0.02;
        } // 原野护符（持有弓时，获得2%的额外移动速度）

        if (BowSkillLevelGet(data,9) > 0 && Utils.BowTag.containsKey(mainhand)) {
            SpeedUp += BowSkillLevelGet(data,9) * 0.1;
        } // 猎手本能（手持弓时，获得10%额外移动速度）

        if (ManaSkillLevelGet(data,9) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
            SpeedUp += ManaSkillLevelGet(data,9) * 0.08;
        } // 法师（手持法杖时，获得8%额外移动速度）

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            SpeedUp += Point * 0.01;
        } // 能力

        return SpeedUp;
    }
    public static float ExpGetImprove(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float ExpUp = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();

        if(Utils.ExpUp.containsKey(boots)) ExpUp += Utils.ExpUp.get(boots);
        if(Utils.ExpUp.containsKey(leggings)) ExpUp += Utils.ExpUp.get(leggings);
        if(Utils.ExpUp.containsKey(chest)) ExpUp += Utils.ExpUp.get(chest);
        if(Utils.ExpUp.containsKey(helmet)) ExpUp += Utils.ExpUp.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ExpUp.containsKey(mainhand)) ExpUp += Utils.ExpUp.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ExpUp.containsKey(offhand)) ExpUp += Utils.ExpUp.get(offhand);
        if(data.contains("plaingems") && data.getBoolean("plaingems")) ExpUp += 0.1;
        if(data.contains("forestgems") && data.getBoolean("forestgems")) ExpUp += 0.1;
        if(data.contains("lakegems") && data.getBoolean("lakegems")) ExpUp += 0.1;
        if(data.contains("volcanogems") && data.getBoolean("volcanogems")) ExpUp += 0.1;
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                ExpUp += (i+1) * 0.01;
            }
        }
        if(data.contains("GemSExpImprove")) ExpUp += data.getDouble("GemSExpImprove");
        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            ExpUp += Point * 0.01;
        }
        return ExpUp;
    }
    public static float MaxHealImprove(Player player)
    {
        float HealUp = 0.0F;
        CompoundTag data = player.getPersistentData();
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(helmetTag.contains("Gems1")) HealUp += Compute.ItemHealUpGems(helmetTag);
        if(chestTag.contains("Gems1")) HealUp += Compute.ItemHealUpGems(chestTag);
        if(leggingsTag.contains("Gems1")) HealUp += Compute.ItemHealUpGems(leggingsTag);
        if(bootsTag.contains("Gems1")) HealUp += Compute.ItemHealUpGems(bootsTag);
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) HealUp += Compute.ItemHealUpGems(stackmainhandtag);
        if(Utils.HealUp.containsKey(boots)) HealUp += Utils.HealUp.get(boots);
        if(Utils.HealUp.containsKey(leggings)) HealUp += Utils.HealUp.get(leggings);
        if(Utils.HealUp.containsKey(chest)) HealUp += Utils.HealUp.get(chest);
        if(Utils.HealUp.containsKey(helmet)) HealUp += Utils.HealUp.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.HealUp.containsKey(mainhand)) HealUp +=  Utils.HealUp.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.HealUp.containsKey(offhand)) HealUp +=  Utils.HealUp.get(offhand);
        if(boots instanceof PlainArmorBoots && leggings instanceof PlainArmorLeggings && chest instanceof PlainArmorChest && helmet instanceof PlainArmorHelmet) HealUp += player.getMaxHealth()*0.2;
        if(data.contains("Barker")) HealUp *= 1.0F+(data.getInt("Barker")/100000.0F)*0.05F;
        if(data.contains("volcanoRune") && data.getInt("volcanoRune") == 3) HealUp *= 0.5;
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                HealUp += (i+1) * 10;
            }
        }
        if(data.contains("GemSMaxHeal")) HealUp += data.getDouble("GemSMaxHeal");
        HealUp += SArmorMaxHealth(player);

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            HealUp += Point;
        }
        return HealUp;
    }
    public static float PlayerHealthRecover(Player player)
    {
        float HealReply = 0.0F;
        CompoundTag data = player.getPersistentData();
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(helmetTag.contains("Gems1")) HealReply += Compute.ItemHealReplyGems(helmetTag);
        if(chestTag.contains("Gems1")) HealReply += Compute.ItemHealReplyGems(chestTag);
        if(leggingsTag.contains("Gems1")) HealReply += Compute.ItemHealReplyGems(leggingsTag);
        if(bootsTag.contains("Gems1")) HealReply += Compute.ItemHealReplyGems(bootsTag);
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) HealReply += Compute.ItemHealReplyGems(stackmainhandtag);
        if(Utils.HealReply.containsKey(boots)) HealReply += Utils.HealReply.get(boots);
        if(Utils.HealReply.containsKey(leggings)) HealReply += Utils.HealReply.get(leggings);
        if(Utils.HealReply.containsKey(chest)) HealReply += Utils.HealReply.get(chest);
        if(Utils.HealReply.containsKey(helmet)) HealReply += Utils.HealReply.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.HealReply.containsKey(mainhand)) HealReply +=  Utils.HealReply.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.HealReply.containsKey(offhand)) HealReply +=  Utils.HealReply.get(offhand);
        if(boots instanceof PlainArmorBoots && leggings instanceof PlainArmorLeggings && chest instanceof PlainArmorChest && helmet instanceof PlainArmorHelmet) HealReply += 0.5 + player.getMaxHealth()*0.01F;
        if(boots instanceof LifeManaArmorBoots && leggings instanceof LifeManaArmorLeggings
                && chest instanceof LifeManaArmorChest && helmet instanceof LifeManaArmorHelmet) HealReply += 1+player.getMaxHealth()*0.01F;
        if(!Utils.OverWorldLevelIsNight && boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet) {
            HealReply += 0.8;
        }
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                HealReply += (i+1) * 0.1;
            }
        }
        if(player.getEffect(ModEffects.HEALREPLY.get()) != null && player.getEffect(ModEffects.HEALREPLY.get()).getAmplifier() == 0) HealReply += player.getMaxHealth() * 0.025;
        if(player.getEffect(ModEffects.HEALREPLY.get()) != null && player.getEffect(ModEffects.HEALREPLY.get()).getAmplifier() == 1) HealReply += player.getMaxHealth() * 0.05;

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            HealReply += Point * 0.1;
        }
        return HealReply;
    }
    public static float PlayerHealEffectUp(Player player)
    {
        float HealEffectUp = 0.0F;
        CompoundTag data = player.getPersistentData();
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("healeffectup")) HealEffectUp += stackmainhandtag.getFloat("healeffectup");
        if(Utils.HealEffectUp.containsKey(boots)) HealEffectUp += Utils.HealEffectUp.get(boots);
        if(Utils.HealEffectUp.containsKey(leggings)) HealEffectUp += Utils.HealEffectUp.get(leggings);
        if(Utils.HealEffectUp.containsKey(chest)) HealEffectUp += Utils.HealEffectUp.get(chest);
        if(Utils.HealEffectUp.containsKey(helmet)) HealEffectUp += Utils.HealEffectUp.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.HealEffectUp.containsKey(mainhand)) HealEffectUp +=  Utils.HealEffectUp.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.HealEffectUp.containsKey(offhand)) HealEffectUp +=  Utils.HealEffectUp.get(offhand);
        if(boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet) HealEffectUp += 0.5f;

        int VitalityAbilityPoint = data.getInt(StringUtils.Ability.Vitality);
        if (data.contains(StringUtils.Ability.Vitality) && data.getInt(StringUtils.Ability.Vitality) > 0) {
            int Point = VitalityAbilityPoint + (VitalityAbilityPoint / 10) * 5;
            HealEffectUp += Point * 0.01;
        }
        return HealEffectUp;
    }
    public static float PlayerCoolDownDecrease(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float DecreaseRate = 1;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(helmetTag.contains("Gems1")) DecreaseRate *= (1-Compute.ItemCoolDownGems(helmetTag));
        if(chestTag.contains("Gems1")) DecreaseRate *= (1-Compute.ItemCoolDownGems(chestTag));
        if(leggingsTag.contains("Gems1")) DecreaseRate *= (1-Compute.ItemCoolDownGems(leggingsTag));
        if(bootsTag.contains("Gems1")) DecreaseRate *= (1-Compute.ItemCoolDownGems(bootsTag));
        if(stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand)) DecreaseRate *= (1-Compute.ItemCoolDownGems(stackmainhandtag));
        if(Utils.CoolDownDecrease.containsKey(boots)) DecreaseRate *= (1-Utils.CoolDownDecrease.get(boots));
        if(Utils.CoolDownDecrease.containsKey(leggings)) DecreaseRate *= (1-Utils.CoolDownDecrease.get(leggings));
        if(Utils.CoolDownDecrease.containsKey(chest)) DecreaseRate *= (1-Utils.CoolDownDecrease.get(chest));
        if(Utils.CoolDownDecrease.containsKey(helmet)) DecreaseRate *= (1-Utils.CoolDownDecrease.get(helmet));
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.CoolDownDecrease.containsKey(mainhand)) DecreaseRate *= (1- Utils.CoolDownDecrease.get(mainhand));
        if(Utils.OffHandTag.containsKey(offhand) && Utils.CoolDownDecrease.containsKey(offhand)) DecreaseRate *= (1-Utils.CoolDownDecrease.get(offhand));
        if(boots instanceof LakeArmorBoots && leggings instanceof LakeArmorLeggings
                && chest instanceof LakeArmorChest && helmet instanceof LakeArmorHelmet) DecreaseRate *= (1-0.25);
        if(boots instanceof ObsiManaArmorBoots && leggings instanceof ObsiManaArmorLeggings
                && chest instanceof ObsiManaArmorChest && helmet instanceof ObsiManaArmorHelmet) DecreaseRate *= (1-0.25);
        if(player.getPersistentData().contains("Blue") && player.getPersistentData().getInt("Blue") == 0) DecreaseRate *= (1-player.getAttribute(Attributes.MOVEMENT_SPEED).getValue()-0.1F);
        if(data.contains("lakegems") && data.getBoolean("lakegems")) DecreaseRate *= (1-0.1);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1)))
            {
                DecreaseRate *= (1-(i+1) * 0.005);
            }
        }
        if(player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 0) DecreaseRate *= (1-0.15);
        if(player.getEffect(ModEffects.COOLDOWNUP.get()) != null && player.getEffect(ModEffects.COOLDOWNUP.get()).getAmplifier() == 1) DecreaseRate *= (1-0.3);
        if(data.contains("GemSCoolDown")) DecreaseRate *= (1-data.getDouble("GemSCoolDown"));
        DecreaseRate *= (1-SArmorCoolDown(player));
        if (SwordSkillLevelGet(data,7) > 0 && Utils.SwordTag.containsKey(mainhand)) DecreaseRate *= (1 - SwordSkillLevelGet(data,7) * 0.03); // 冷静（手持近战武器时，获得3%冷却缩减）
        if (ManaSkillLevelGet(data,7) > 0 && Utils.SceptreTag.containsKey(mainhand)) DecreaseRate *= (1 - ManaSkillLevelGet(data,7) * 0.03); // 冷静（手持法杖时，获得3%冷却缩减）

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            DecreaseRate *= (1 - Point * 0.001);
        } // 能力

        return 1-DecreaseRate;
    }
    public static float MonsterDefence(Mob monster)
    {
        int TickCount = monster.getServer().getTickCount();
        float Defence = 0.0F;
        float ExDefence = 0;
        CompoundTag data = monster.getPersistentData();
        Iterator<ItemStack> iterator = monster.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        if(Utils.Defence.containsKey(boots)) Defence += Utils.Defence.get(boots);
        if(Utils.Defence.containsKey(leggings)) Defence += Utils.Defence.get(leggings);
        if(Utils.Defence.containsKey(chest)) Defence += Utils.Defence.get(chest);
        if(Utils.Defence.containsKey(helmet)) Defence += Utils.Defence.get(helmet);
        if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
            ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 20;
        }
        if (Defence + ExDefence < 0) return 0;
        return Defence + ExDefence;
    }
    public static float PlayerBreakDefence0(Player player)
    {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        float BreakDefence0 = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
                CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("BreakDefence0")) BreakDefence0 += stackmainhandtag.getFloat("BreakDefence0");
        if(Utils.BreakDefence0.containsKey(boots)) BreakDefence0 += Utils.BreakDefence0.get(boots);
        if(Utils.BreakDefence0.containsKey(leggings)) BreakDefence0 += Utils.BreakDefence0.get(leggings);
        if(Utils.BreakDefence0.containsKey(chest)) BreakDefence0 += Utils.BreakDefence0.get(chest);
        if(Utils.BreakDefence0.containsKey(helmet)) BreakDefence0 += Utils.BreakDefence0.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.BreakDefence0.containsKey(mainhand)) BreakDefence0 +=  Utils.BreakDefence0.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.BreakDefence0.containsKey(offhand)) BreakDefence0 +=  Utils.BreakDefence0.get(offhand);
        if(data.contains(StringUtils.ForestSwordSkill0) && data.getInt(StringUtils.ForestSwordSkill0) > TickCount) BreakDefence0 += 100;
        if(data.contains(StringUtils.ForestSwordSkill1) && data.getInt(StringUtils.ForestSwordSkill1) > TickCount) BreakDefence0 += 500;
        if (SwordSkillLevelGet(data,10) > 0 && Utils.SwordTag.containsKey(mainhand)) BreakDefence0 += SwordSkillLevelGet(data,10) * 5;
        if (BowSkillLevelGet(data,10) > 0 && Utils.BowTag.containsKey(mainhand)) BreakDefence0 += BowSkillLevelGet(data,10) * 5;
        int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
        if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
            int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
            BreakDefence0 += Point;
        } // 能力
        return BreakDefence0;
    }
    public static void ManaStatusUpdate(Player player)
    {
        CompoundTag data = player.getPersistentData();
        double tmpManaNum =  9.0*(data.getInt("MANA") * 1.0 / data.getInt("MAXMANA"));
        ModNetworking.sendToClient(new ManaSyncS2CPacket((int) ceil(tmpManaNum),data.getInt("MANA")),(ServerPlayer) player);
    }
    public static float PlayerManaDamage(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        float manadamage = 0.0F;
        float Exdamage = 0;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDamage.containsKey(mainhand)) manadamage +=  Utils.ManaDamage.get(mainhand);
        if(data.contains("snowRune0Time") && data.getInt("snowRune0Time") > 0) Exdamage += data.getInt("snowRune0") * 0.2 * manadamage;
        if(helmetTag.contains("Gems1")) Exdamage += Compute.ItemManaDamageGems(helmetTag);
        if(chestTag.contains("Gems1")) Exdamage += Compute.ItemManaDamageGems(chestTag);
        if(leggingsTag.contains("Gems1")) Exdamage += Compute.ItemManaDamageGems(leggingsTag);
        if(bootsTag.contains("Gems1")) Exdamage += Compute.ItemManaDamageGems(bootsTag);
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("manadamage")) Exdamage += stackmainhandtag.getFloat("manadamage");
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDamage.containsKey(mainhand) && data0.contains("Forging")) Exdamage += Utils.ManaDamage.get(equip.getItem()) * (data0.getInt("Forging") / 20.0F);
        if(Utils.ManaDamage.containsKey(boots)) Exdamage += Utils.ManaDamage.get(boots);
        if(Utils.ManaDamage.containsKey(leggings)) Exdamage += Utils.ManaDamage.get(leggings);
        if(Utils.ManaDamage.containsKey(chest)) Exdamage += Utils.ManaDamage.get(chest);
        if(Utils.ManaDamage.containsKey(helmet)) Exdamage += Utils.ManaDamage.get(helmet);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaDamage.containsKey(offhand)) Exdamage +=  Utils.ManaDamage.get(offhand);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDamage.containsKey(mainhand) && data0.contains(StringUtils.KillCount.KillCount)) {
            int killCount = data0.getInt(StringUtils.KillCount.KillCount);
            if (killCount >= 100000) killCount = 100000;
            Exdamage += Utils.ManaDamage.get(mainhand)*0.5*(killCount/100000.0);
        }
        if(data.contains("ManaRune") && data.getInt("ManaRune") == 1) Exdamage *= 1 +
                ((player.getMaxHealth()-player.getHealth())/player.getMaxHealth()) * 0.5f;
        if(stackmainhandtag.contains("Gems1")) Exdamage += Compute.ItemManaDamageGems(stackmainhandtag);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                Exdamage += (i+1);
            }
        }
        if(player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 0) Exdamage += manadamage * 0.25 + 25;
        if(player.getEffect(ModEffects.MANADAMAGEUP.get()) != null && player.getEffect(ModEffects.MANADAMAGEUP.get()).getAmplifier() == 1) Exdamage += manadamage * 0.4 + 40;
        if(player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && !Utils.OverWorldLevelIsNight && mainhand instanceof PlainSceptre4) Exdamage += 45;
        if(data.contains("GemSManaDamage")) Exdamage += data.getDouble("GemSManaDamage");
        Exdamage += SArmorManaDamage(player);

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            Exdamage += Point;
        } // 能力

        if (ManaSkillLevelGet(data,2) > 0 && data.contains(StringUtils.ManaSkillNum.Skill2) && data.getInt(StringUtils.ManaSkillNum.Skill2) > TickCount) {
            Exdamage += manadamage * ManaSkillLevelGet(data,2) * 0.02;
        } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）

        data.putFloat("NetherRuneManaDamageCompute",(manadamage + Exdamage) * 0.4f);

        if (data.contains("NetherRune") && data.getInt("NetherRune") == 0) {
            PlayerAttackDamage(player);
            Exdamage += data.getFloat("NetherRuneDamageCompute");
        }
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 1) return 0;
        return manadamage + Exdamage;
    }
    public static float PlayerManaDefence(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        float Defence = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag data0 = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag data1 = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag data2 = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag data3 = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        if(Utils.ManaDefence.containsKey(boots)) Defence += Utils.ManaDefence.get(boots);
        if(Utils.ManaDefence.containsKey(leggings)) Defence += Utils.ManaDefence.get(leggings);
        if(Utils.ManaDefence.containsKey(chest)) Defence += Utils.ManaDefence.get(chest);
        if(Utils.ManaDefence.containsKey(helmet)) Defence += Utils.ManaDefence.get(helmet);
        float ExDefence = 0.0F;
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaDefence.containsKey(mainhand)) ExDefence +=  Utils.ManaDefence.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaDefence.containsKey(offhand)) ExDefence +=  Utils.ManaDefence.get(offhand);
        if(Utils.ManaDefence.containsKey(helmet) && data0.contains("Forging")) ExDefence += Utils.ManaDefence.get(helmet) * (data0.getInt("Forging") / 24.0F);
        if(Utils.ManaDefence.containsKey(chest) && data1.contains("Forging")) ExDefence += Utils.ManaDefence.get(chest) * (data1.getInt("Forging") / 24.0F);
        if(Utils.ManaDefence.containsKey(leggings) && data2.contains("Forging")) ExDefence += Utils.ManaDefence.get(leggings) * (data2.getInt("Forging") / 24.0F);
        if(Utils.ManaDefence.containsKey(boots) && data3.contains("Forging")) ExDefence += Utils.ManaDefence.get(boots) * (data3.getInt("Forging") / 24.0F);
        if(boots instanceof ForestArmorBoots && leggings instanceof ForestArmorLeggings
                && chest instanceof ForestArmorChest && helmet instanceof ForestArmorHelmet) ExDefence += Defence * 0.25;
        if(boots instanceof LifeManaArmorBoots && leggings instanceof LifeManaArmorLeggings
                && chest instanceof LifeManaArmorChest && helmet instanceof LifeManaArmorHelmet) ExDefence += Defence * 0.25;
        if(data.contains("forestgems") && data.getBoolean("forestgems")) ExDefence += Defence * 0.1;
        if(player.getEffect(ModEffects.MANADEFENCEUP.get()) != null && player.getEffect(ModEffects.MANADEFENCEUP.get()).getAmplifier() == 0) ExDefence += Defence * 0.25 + 25;
        if(player.getEffect(ModEffects.MANADEFENCEUP.get()) != null && player.getEffect(ModEffects.MANADEFENCEUP.get()).getAmplifier() == 1) ExDefence += Defence * 0.4 + 40;
        if(data.contains("GemSManaDefence")) ExDefence += data.getDouble("GemSManaDefence");
        if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
            ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 10;
        }
        if (Defence + ExDefence < 0) return 0;
        return Defence+ExDefence;
    }
    public static float PlayerManaReply(Player player)
    {
        int TickCount = player.getServer().getTickCount();
        float manareply = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
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
        if(helmetTag.contains("Gems1")) manareply += Compute.ItemManaReplyGems(helmetTag);
        if(chestTag.contains("Gems1")) manareply += Compute.ItemManaReplyGems(chestTag);
        if(leggingsTag.contains("Gems1")) manareply += Compute.ItemManaReplyGems(leggingsTag);
        if(bootsTag.contains("Gems1")) manareply += Compute.ItemManaReplyGems(bootsTag);
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("manareply")) manareply += stackmainhandtag.getFloat("manareply");
        if(Utils.ManaReply.containsKey(boots)) manareply += Utils.ManaReply.get(boots);
        if(Utils.ManaReply.containsKey(leggings)) manareply += Utils.ManaReply.get(leggings);
        if(Utils.ManaReply.containsKey(chest)) manareply += Utils.ManaReply.get(chest);
        if(Utils.ManaReply.containsKey(helmet)) manareply += Utils.ManaReply.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaReply.containsKey(mainhand)) manareply +=  Utils.ManaReply.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaReply.containsKey(offhand)) manareply +=  Utils.ManaReply.get(offhand);
        if(boots instanceof LifeManaArmorBoots && leggings instanceof LifeManaArmorLeggings
                && chest instanceof LifeManaArmorChest && helmet instanceof LifeManaArmorHelmet) manareply += 5;
        if(boots instanceof ObsiManaArmorBoots && leggings instanceof ObsiManaArmorLeggings
                && chest instanceof ObsiManaArmorChest && helmet instanceof ObsiManaArmorHelmet) manareply += 5;
        if(stackmainhandtag.contains("Gems1")) manareply += Compute.ItemManaReplyGems(stackmainhandtag);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                manareply += (i+1) * 0.1;
            }
        }
        if(player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 0) manareply += 5;
        if(player.getEffect(ModEffects.MANAREPLYUP.get()) != null && player.getEffect(ModEffects.MANAREPLYUP.get()).getAmplifier() == 1) manareply += 10;
        if(data.contains("GemSManaReply")) manareply += data.getDouble("GemSManaReply");
        if(player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) && !Utils.OverWorldLevelIsNight && mainhand instanceof PlainSceptre4) manareply += 15;
        if(data.contains(StringUtils.NetherRune.NetherRune2) && data.getInt(StringUtils.NetherRune.NetherRune2) > TickCount) manareply += 10;
        if(data.contains("NetherRune") && data.getInt("NetherRune") == 3) {
            Item item = player.getMainHandItem().getItem();
            if(item instanceof ManaSword || item instanceof QuartzSword || item instanceof QuartzSabre || item instanceof NetherBow) manareply += 10;
        }
        if (SwordSkillLevelGet(data,8) > 0 && Utils.SwordTag.containsKey(mainhand)) manareply += SwordSkillLevelGet(data,8); // 洞悉（手持近战武器时，获得1额外法力回复）
        if (ManaSkillLevelGet(data,1) > 0 && Utils.SceptreTag.containsKey(mainhand)) manareply += ManaSkillLevelGet(data,1) * 0.5; // 仙女护符（持有法杖时，获得额外0.5点法力回复）
        if (ManaSkillLevelGet(data,8) > 0 && Utils.SceptreTag.containsKey(mainhand)) manareply += ManaSkillLevelGet(data,8); // 洞悉（手持法杖时，获得1额外法力回复）

        return manareply;
    }
    public static float MonsterManaDefence(Mob monster)
    {
        int TickCount = monster.getServer().getTickCount();
        float Defence = 0.0F;
        float ExDefence = 0;
        CompoundTag data = monster.getPersistentData();
        Iterator<ItemStack> iterator = monster.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        if(Utils.ManaDefence.containsKey(boots)) Defence += Utils.ManaDefence.get(boots);
        if(Utils.ManaDefence.containsKey(leggings)) Defence += Utils.ManaDefence.get(leggings);
        if(Utils.ManaDefence.containsKey(chest)) Defence += Utils.ManaDefence.get(chest);
        if(Utils.ManaDefence.containsKey(helmet)) Defence += Utils.ManaDefence.get(helmet);
        if(data.contains("ManaRune2") && data.getInt("ManaRune2") > 0) Defence = 0;
        if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
            ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 10;
        }
        if (Defence + ExDefence < 0) return 0;
        return Defence;
    }
    public static float PlayerManaBreakDefence(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float DefenceRate = 1F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
                CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(Utils.ManaBreakDefence.containsKey(boots)) DefenceRate *= (1-Utils.ManaBreakDefence.get(boots));
        if(Utils.ManaBreakDefence.containsKey(leggings)) DefenceRate *= (1-Utils.ManaBreakDefence.get(leggings));
        if(Utils.ManaBreakDefence.containsKey(chest)) DefenceRate *= (1-Utils.ManaBreakDefence.get(chest));
        if(Utils.ManaBreakDefence.containsKey(helmet)) DefenceRate *= (1-Utils.ManaBreakDefence.get(helmet));
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaBreakDefence.containsKey(mainhand)) DefenceRate *= (1-Utils.ManaBreakDefence.get(mainhand));
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaBreakDefence.containsKey(offhand)) DefenceRate *= (1-Utils.ManaBreakDefence.get(offhand));
        if(data.contains("ManaRune") && data.getInt("ManaRune") == 3) DefenceRate *= (1-0.65f);
        for(int i = 0; i < 21; i++)
        {
            if(data.contains("LevelReward"+ 5 * (i+1) ) && data.getBoolean("LevelReward"+ 5 * (i+1) ))
            {
                DefenceRate *= (1-(i+1) * 0.005);
            }
        }
        if(player.getEffect(ModEffects.BREAKMANADEFENCEUP.get()) != null && player.getEffect(ModEffects.BREAKMANADEFENCEUP.get()).getAmplifier() == 0) DefenceRate *= (1-0.2);
        if(player.getEffect(ModEffects.BREAKMANADEFENCEUP.get()) != null && player.getEffect(ModEffects.BREAKMANADEFENCEUP.get()).getAmplifier() == 1) DefenceRate *= (1- 0.4);
        if(data.contains("GemSManaBreakDefence")) DefenceRate *= (1-data.getDouble("GemSManaBreakDefence"));
        if (ManaSkillLevelGet(data,10) > 0 && Utils.SceptreTag.containsKey(mainhand)) DefenceRate *= (1 - ManaSkillLevelGet(data,10) * 0.03); // 结构研究（获得30% + 50法术穿透）
        return 1-DefenceRate;
    }

    public static float PlayerManaBreakDefence0(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float ManaBreakDefence0 = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
                CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaBreakDefence0")) ManaBreakDefence0 += stackmainhandtag.getFloat("ManaBreakDefence0");
        if(Utils.ManaBreakDefence0.containsKey(boots)) ManaBreakDefence0 += Utils.ManaBreakDefence0.get(boots);
        if(Utils.ManaBreakDefence0.containsKey(leggings)) ManaBreakDefence0 += Utils.ManaBreakDefence0.get(leggings);
        if(Utils.ManaBreakDefence0.containsKey(chest)) ManaBreakDefence0 += Utils.ManaBreakDefence0.get(chest);
        if(Utils.ManaBreakDefence0.containsKey(helmet)) ManaBreakDefence0 += Utils.ManaBreakDefence0.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaBreakDefence0.containsKey(mainhand)) ManaBreakDefence0 +=  Utils.ManaBreakDefence0.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaBreakDefence0.containsKey(offhand)) ManaBreakDefence0 +=  Utils.ManaBreakDefence0.get(offhand);
        if (ManaSkillLevelGet(data,10) > 0 && Utils.SceptreTag.containsKey(mainhand)) ManaBreakDefence0 += ManaSkillLevelGet(data,10) * 5;

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            ManaBreakDefence0 += Point;
        } // 能力

        return ManaBreakDefence0;
    }
    public static float PlayerManaRecover(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float ManaUp = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
                CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(Utils.ManaUp.containsKey(boots)) ManaUp += Utils.ManaUp.get(boots);
        if(Utils.ManaUp.containsKey(leggings)) ManaUp += Utils.ManaUp.get(leggings);
        if(Utils.ManaUp.containsKey(chest)) ManaUp += Utils.ManaUp.get(chest);
        if(Utils.ManaUp.containsKey(helmet)) ManaUp += Utils.ManaUp.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaUp.containsKey(mainhand)) ManaUp +=  Utils.ManaUp.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaUp.containsKey(offhand)) ManaUp +=  Utils.ManaUp.get(offhand);
        if(data.contains("GemSMaxMana")) ManaUp += data.getDouble("GemSMaxMana");

        int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
        if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
            int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
            ManaUp += Point;
        } // 能力

        return ManaUp;
    }
    public static float PlayerManaHealSteal(Player player)
    {
        CompoundTag data = player.getPersistentData();
        float ManaHealSteal = 0.0F;
        Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }
        if(Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("ManaHealSteal")) ManaHealSteal += stackmainhandtag.getFloat("ManaHealSteal");
        if(Utils.ManaHealSteal.containsKey(boots)) ManaHealSteal += Utils.ManaHealSteal.get(boots);
        if(Utils.ManaHealSteal.containsKey(leggings)) ManaHealSteal += Utils.ManaHealSteal.get(leggings);
        if(Utils.ManaHealSteal.containsKey(chest)) ManaHealSteal += Utils.ManaHealSteal.get(chest);
        if(Utils.ManaHealSteal.containsKey(helmet)) ManaHealSteal += Utils.ManaHealSteal.get(helmet);
        if(Utils.MainHandTag.containsKey(mainhand) && Utils.ManaHealSteal.containsKey(mainhand)) ManaHealSteal +=  Utils.ManaHealSteal.get(mainhand);
        if(Utils.OffHandTag.containsKey(offhand) && Utils.ManaHealSteal.containsKey(offhand)) ManaHealSteal +=  Utils.ManaHealSteal.get(offhand);
        if (ManaSkillLevelGet(data,11) > 0 && Utils.SceptreTag.containsKey(mainhand)) ManaHealSteal += ManaSkillLevelGet(data,11) * 0.01;
        return ManaHealSteal;
    }
    public static void PlayerManaAddOrCost(Player player, int Mana) {
        CompoundTag data = player.getPersistentData();
        if (Mana > 0) data.putInt("MANA", Math.min(data.getInt("MANA") + Mana, data.getInt("MAXMANA")));
        else data.putInt("MANA", Math.max(data.getInt("MANA") + Mana, 0));
    }
    public static float PlayerLuckyUp (Player player) {
        float LuckyUp = 0;
        CompoundTag data = player.getPersistentData();
        Item Helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item Chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item Leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item Boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        if (Utils.LuckyUp.containsKey(Helmet)) LuckyUp += Utils.LuckyUp.get(Helmet);
        if (Utils.LuckyUp.containsKey(Chest)) LuckyUp += Utils.LuckyUp.get(Chest);
        if (Utils.LuckyUp.containsKey(Leggings)) LuckyUp += Utils.LuckyUp.get(Leggings);
        if (Utils.LuckyUp.containsKey(Boots)) LuckyUp += Utils.LuckyUp.get(Boots);
        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            LuckyUp += Point * 0.01;
        }
        return LuckyUp;
    }
    public static float SwordSkill1And4 (CompoundTag data, Player player) {
        float Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.SwordTag.containsKey(mainhand)) {
            Decrease += Compute.SwordSkillLevelGet(data,1) * 0.01;
            Decrease -= Compute.SwordSkillLevelGet(data,4) * 0.015;
        }
        return Decrease;
    }
    public static float SwordSkill14 (CompoundTag data, Player player, LivingEntity monster) {
        float Enhance = 0;
        if (SwordSkillLevelGet(data,14) > 0) {
            float PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            float MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate < MonsterHealthRate) {
                Enhance += 0.2 * Math.min(1.0,(MonsterHealthRate - PlayerHealthRate) / 0.66);
            }
        }
        return Enhance;
    }
    public static float BowSkill4 (CompoundTag data, Player player) {
        float Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.BowTag.containsKey(mainhand)) {
            Decrease -= Compute.BowSkillLevelGet(data,4) * 0.015;
        }
        return Decrease;
    }
    public static float ManaSkill4 (CompoundTag data, Player player) {
        float Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.SceptreTag.containsKey(mainhand)) {
            Decrease -= Compute.ManaSkillLevelGet(data,4) * 0.015;
        }
        return Decrease;
    }
    public static double PlayerShieldDecrease(Player player, double value)
    {
        double TmpNum = value;
        Queue<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
        if(shieldQueue != null && !shieldQueue.isEmpty())
        {
            Iterator<Shield> iterator1 = shieldQueue.iterator();
            while(iterator1.hasNext())
            {
                Shield shield = iterator1.next();
                if(shield.getValue() > TmpNum) {
                    shield.setValue(shield.getValue()-TmpNum);
                    PlayerShieldCompute(player);
                    return 0;
                }
                else
                {
                    TmpNum -= shield.getValue();
                    shield.setTick(0);
                    shield.setValue(0);
                }
            }
            PlayerShieldCompute(player);
            return TmpNum;
        }
        return value;
    }
    public static void PlayerShieldProvider(Player player, int tick, double value)
    {
        Shield shield = new Shield(tick, value);
        Queue<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
        if(shieldQueue == null) {
            shieldQueue = new LinkedList<>();
            shieldQueue.add(shield);
            Utils.PlayerShieldQueue.put(player, shieldQueue);
        }
        else
        {
            shieldQueue.add(shield);
        }
        PlayerShieldCompute(player);
    }
    public static double PlayerShieldCompute(Player player)
    {
        Queue<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
        if(shieldQueue != null && !shieldQueue.isEmpty())
        {
            Iterator<Shield> iterator0 = shieldQueue.iterator();
            double shieldValue = 0;
            while(iterator0.hasNext())
            {
                Shield shield = iterator0.next();
                if(shield.getTick() > 0) shieldValue += shield.getValue();
            }
            double tmpShieldNum = 9.0*(shieldValue * 1.0 / player.getMaxHealth());
            ModNetworking.sendToClient(new ShieldSyncS2CPacket((int) ceil(tmpShieldNum), (int) ceil(shieldValue)),(ServerPlayer) player);
            return shieldValue;
        }
        ModNetworking.sendToClient(new ShieldSyncS2CPacket(0, 0),(ServerPlayer) player);
        return 0;
    }
    public static float ItemBaseDamageGems(CompoundTag data)
    {
        float BaseDamage = 0;
        if(data.contains("Gems1") && Utils.GemsAttackDamage.containsKey(data.getString("Gems1"))) BaseDamage += Utils.GemsAttackDamage.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsAttackDamage.containsKey(data.getString("Gems2"))) BaseDamage += Utils.GemsAttackDamage.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsAttackDamage.containsKey(data.getString("Gems3"))) BaseDamage += Utils.GemsAttackDamage.get(data.getString("Gems3"));
        return BaseDamage;
    }
    public static float ItemSpeedUpGems(CompoundTag data)
    {
        float SpeedUp = 0;
        if(data.contains("Gems1") && Utils.GemsSpeedUp.containsKey(data.getString("Gems1"))) SpeedUp += Utils.GemsSpeedUp.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsSpeedUp.containsKey(data.getString("Gems2"))) SpeedUp += Utils.GemsSpeedUp.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsSpeedUp.containsKey(data.getString("Gems3"))) SpeedUp += Utils.GemsSpeedUp.get(data.getString("Gems3"));
        return SpeedUp;
    }
    public static float ItemManaDamageGems(CompoundTag data)
    {
        float BaseDamage = 0;
        if(data.contains("Gems1") && Utils.GemsManaDamage.containsKey(data.getString("Gems1"))) BaseDamage += Utils.GemsManaDamage.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsManaDamage.containsKey(data.getString("Gems2"))) BaseDamage += Utils.GemsManaDamage.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsManaDamage.containsKey(data.getString("Gems3"))) BaseDamage += Utils.GemsManaDamage.get(data.getString("Gems3"));
        return BaseDamage;
    }
    public static float ItemManaReplyGems(CompoundTag data)
    {
        float Reply = 0;
        if(data.contains("Gems1") && Utils.GemsManaRecover.containsKey(data.getString("Gems1"))) Reply += Utils.GemsManaRecover.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsManaRecover.containsKey(data.getString("Gems2"))) Reply += Utils.GemsManaRecover.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsManaRecover.containsKey(data.getString("Gems3"))) Reply += Utils.GemsManaRecover.get(data.getString("Gems3"));
        return Reply;
    }
    public static float ItemHealReplyGems(CompoundTag data)
    {
        float Reply = 0;
        if(data.contains("Gems1") && Utils.GemsHealReply.containsKey(data.getString("Gems1"))) Reply += Utils.GemsHealReply.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsHealReply.containsKey(data.getString("Gems2"))) Reply += Utils.GemsHealReply.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsHealReply.containsKey(data.getString("Gems3"))) Reply += Utils.GemsHealReply.get(data.getString("Gems3"));
        return Reply;
    }
    public static float ItemHealUpGems(CompoundTag data)
    {
        float HealUp = 0;
        if(data.contains("Gems1") && Utils.GemsHealUp.containsKey(data.getString("Gems1"))) HealUp += Utils.GemsHealUp.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsHealUp.containsKey(data.getString("Gems2"))) HealUp += Utils.GemsHealUp.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsHealUp.containsKey(data.getString("Gems3"))) HealUp += Utils.GemsHealUp.get(data.getString("Gems3"));
        return HealUp;
    }
    public static float ItemDefenceGems(CompoundTag data)
    {
        float Defence = 0;
        if(data.contains("Gems1") && Utils.GemsDefence.containsKey(data.getString("Gems1"))) Defence += Utils.GemsDefence.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsDefence.containsKey(data.getString("Gems2"))) Defence += Utils.GemsDefence.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsDefence.containsKey(data.getString("Gems3"))) Defence += Utils.GemsDefence.get(data.getString("Gems3"));
        return Defence;
    }
    public static float ItemCoolDownGems(CompoundTag data)
    {
        float CoolDown = 0;
        if(data.contains("Gems1") && Utils.GemsCoolDown.containsKey(data.getString("Gems1"))) CoolDown += Utils.GemsCoolDown.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsCoolDown.containsKey(data.getString("Gems2"))) CoolDown += Utils.GemsCoolDown.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsCoolDown.containsKey(data.getString("Gems3"))) CoolDown += Utils.GemsCoolDown.get(data.getString("Gems3"));
        return CoolDown;
    }
    public static float ItemCritDamageGems(CompoundTag data)
    {
        float CritDamage = 0;
        if(data.contains("Gems1") && Utils.GemsCritDamage.containsKey(data.getString("Gems1"))) CritDamage += Utils.GemsCritDamage.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsCritDamage.containsKey(data.getString("Gems2"))) CritDamage += Utils.GemsCritDamage.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsCritDamage.containsKey(data.getString("Gems3"))) CritDamage += Utils.GemsCritDamage.get(data.getString("Gems3"));
        return CritDamage;
    }
    public static float ItemCritRateGems(CompoundTag data)
    {
        float CritRate = 0;
        if(data.contains("Gems1") && Utils.GemsCritRate.containsKey(data.getString("Gems1"))) CritRate += Utils.GemsCritRate.get(data.getString("Gems1"));
        if(data.contains("Gems2") && Utils.GemsCritRate.containsKey(data.getString("Gems2"))) CritRate += Utils.GemsCritRate.get(data.getString("Gems2"));
        if(data.contains("Gems3") && Utils.GemsCritRate.containsKey(data.getString("Gems3"))) CritRate += Utils.GemsCritRate.get(data.getString("Gems3"));
        return CritRate;
    }
    public static void ParticleWITCH(double X , double Y , double Z, Level level, double r, ParticleOptions particleOptions)
    {
        double XArray[] = {
                X+r,
                X-r,
                X, X, X, X,
                X+r/Math.sqrt(2),X+r/Math.sqrt(2),X+r/Math.sqrt(2),X+r/Math.sqrt(2),
                X-r/Math.sqrt(2),X-r/Math.sqrt(2),X-r/Math.sqrt(2),X-r/Math.sqrt(2)
        };
        double YArray[] = {
                Y,Y,Y,Y,Y+r,Y-r,
                Y+r/Math.sqrt(2),Y+r/Math.sqrt(2),Y-r/Math.sqrt(2),Y-r/Math.sqrt(2),
                Y+r/Math.sqrt(2),Y+r/Math.sqrt(2),Y-r/Math.sqrt(2),Y-r/Math.sqrt(2),
        };
        double ZArray[] = {
                Z,Z,Z+r,Z-r,Z,Z,
                Z+r/Math.sqrt(2),Z-r/Math.sqrt(2),
                Z+r/Math.sqrt(2),Z-r/Math.sqrt(2),
                Z+r/Math.sqrt(2),Z-r/Math.sqrt(2),
                Z+r/Math.sqrt(2),Z-r/Math.sqrt(2),
        };
/*        level.addParticle(ParticleTypes.WITCH,X+r,Y,Z,0,0,0);
        level.addParticle(ParticleTypes.WITCH,X-r,Y,Z,0,0,0);
        level.addParticle(ParticleTypes.WITCH,X,Y,Z+r,0,0,0);
        level.addParticle(ParticleTypes.WITCH,X,Y,Z-r,0,0,0);
        level.addParticle(ParticleTypes.WITCH,X,Y+r,Z,0,0,0);
        level.addParticle(ParticleTypes.WITCH,X,Y-r,Z,0,0,0);
        level.addParticle(ParticleTypes.WITCH,X+r/Math.sqrt(2),Y+r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X+r/Math.sqrt(2),Y+r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X+r/Math.sqrt(2),Y-r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X+r/Math.sqrt(2),Y-r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X-r/Math.sqrt(2),Y+r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X-r/Math.sqrt(2),Y+r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X-r/Math.sqrt(2),Y-r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.WITCH,X-r/Math.sqrt(2),Y-r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);*/
        List<ServerPlayer> list = level.getServer().getPlayerList().getPlayers();
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                particleOptions,true,X+r,Y,Z,0,0,0,0,0
        );
        for (ServerPlayer serverPlayer : list) {
            serverPlayer.connection.send(clientboundLevelParticlesPacket);
        }
        for (int i = 1; i < 14; i++) {
            clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions,true,XArray[i],YArray[i],ZArray[i],0,0,0,0,0
            );
            for (ServerPlayer serverPlayer : list) {
                serverPlayer.connection.send(clientboundLevelParticlesPacket);
            }
        }
    }
    public static void ParticleSCRAPE(double X , double Y , double Z, Level level, double r)
    {
        level.addParticle(ParticleTypes.SCRAPE,X+r,Y,Z,0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X-r,Y,Z,0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X,Y,Z+r,0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X,Y,Z-r,0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X,Y+r,Z,0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X,Y-r,Z,0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X+r/Math.sqrt(2),Y+r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X+r/Math.sqrt(2),Y+r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X+r/Math.sqrt(2),Y-r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X+r/Math.sqrt(2),Y-r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X-r/Math.sqrt(2),Y+r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X-r/Math.sqrt(2),Y+r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X-r/Math.sqrt(2),Y-r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.SCRAPE,X-r/Math.sqrt(2),Y-r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
    }
    public static void ParticleComposter(double X , double Y , double Z, Level level, double r)
    {
        level.addParticle(ParticleTypes.COMPOSTER,X+r,Y,Z,0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X-r,Y,Z,0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X,Y,Z+r,0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X,Y,Z-r,0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X,Y+r,Z,0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X,Y-r,Z,0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X+r/Math.sqrt(2),Y+r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X+r/Math.sqrt(2),Y+r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X+r/Math.sqrt(2),Y-r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X+r/Math.sqrt(2),Y-r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X-r/Math.sqrt(2),Y+r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X-r/Math.sqrt(2),Y+r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X-r/Math.sqrt(2),Y-r/Math.sqrt(2),Z+r/Math.sqrt(2),0,0,0);
        level.addParticle(ParticleTypes.COMPOSTER,X-r/Math.sqrt(2),Y-r/Math.sqrt(2),Z-r/Math.sqrt(2),0,0,0);
    }
    public static void ForgingHoverName(ItemStack stack,Component component)
    {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        if(data.contains("Forging")) {
            if(data.getInt("Forging") <= 10) stack.setHoverName(Component.literal("").append(component).append(Component.literal("[").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("+"+String.valueOf(data.getInt("Forging"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
            else{
                if(data.getInt("Forging") <= 20) stack.setHoverName(Component.literal("").append(component).append(Component.literal("[").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("+"+String.valueOf(data.getInt("Forging"))) .withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                    else{
                    stack.setHoverName(Component.literal("").append(component).append(Component.literal("[").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("+"+String.valueOf(data.getInt("Forging"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                }
            }

        }
        else stack.setHoverName(Component.literal("").append(component));
    }

    public static void Drops(Player player, Mob monster, Item monsterhelmet, ItemStack equip) throws IOException {
        CompoundTag data = player.getPersistentData();
        Random r = new Random();
        float ExpUp = Compute.ExpGetImprove(player);
        float LuckyUp = Compute.PlayerLuckyUp(player);
        if(monsterhelmet.equals(Moditems.ArmorPlain.get()))
        {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);
            RateItemStackGive(Moditems.SilverCoin.get().getDefaultInstance(),0.3,player);
            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.2,player);
            RateItemStackGive(Moditems.PlainSoul.get().getDefaultInstance(),0.8,player);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            if(!data.contains("KillCountOfPlainZombie")) data.putInt("KillCountOfPlainZombie",1);
            else data.putInt("KillCountOfPlainZombie",data.getInt("KillCountOfPlainZombie")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission1")) data.putInt("DailyMission1",data.getInt("DailyMission1")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,2);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 2);
            }
        }
        if(monsterhelmet.equals(Moditems.ArmorForestSkeleton.get()))
        {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.SilverCoin.get().getDefaultInstance(),0.4,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.2,player);

            RateItemStackGive(Moditems.ForestSoul.get().getDefaultInstance(),0.6,player);


            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            if(!data.contains("KillCountOfForestSkeleton")) data.putInt("KillCountOfForestSkeleton",1);
            else data.putInt("KillCountOfForestSkeleton",data.getInt("KillCountOfForestSkeleton")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission2")) data.putInt("DailyMission2",data.getInt("DailyMission2")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,4);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 4);
            }

            if (Utils.ForestRecall.RecallPlayer != null && Utils.ForestRecall.RecallPlayer != null && Utils.ForestRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.ForestRecall.KillCount != -1) {
                Utils.ForestRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("森林骷髅/森林僵尸击杀数:"+(40-Utils.ForestRecall.KillCount)+ "/"
                                + "40").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfForest));
                Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorForestZombie.get()))
        {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.SilverCoin.get().getDefaultInstance(),0.6,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.2,player);

            RateItemStackGive(Moditems.ForestSoul.get().getDefaultInstance(),0.9,player);


            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            if(!data.contains("KillCountOfForestZombie")) data.putInt("KillCountOfForestZombie",1);
            else data.putInt("KillCountOfForestZombie",data.getInt("KillCountOfForestZombie")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission3")) data.putInt("DailyMission3",data.getInt("DailyMission3")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,6);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 6);
            }

            if (Utils.ForestRecall.RecallPlayer != null && Utils.ForestRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.ForestRecall.KillCount != -1) {
                Utils.ForestRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("森林骷髅/森林僵尸击杀数:"+(40-Utils.ForestRecall.KillCount)+ "/"
                                + "40").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfForest));
                Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorDrown.get()))
        {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.SilverCoin.get().getDefaultInstance(),0.8,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.25,player);

            RateItemStackGive(Moditems.WaterSoul.get().getDefaultInstance(),0.8,player);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfLakeDrowned")) data.putInt("KillCountOfLakeDrowned",1);
            else data.putInt("KillCountOfLakeDrowned",data.getInt("KillCountOfLakeDrowned")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission4")) data.putInt("DailyMission4",data.getInt("DailyMission4")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,8);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 8);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorBlaze.get()))
        {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.VolcanoSoul.get().getDefaultInstance(),0.8,player);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            if(!data.contains("KillCountOfVolcanoBlazw")) data.putInt("KillCountOfVolcanoBlazw",1);
            else data.putInt("KillCountOfVolcanoBlazw",data.getInt("KillCountOfVolcanoBlazw")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission5")) data.putInt("DailyMission5",data.getInt("DailyMission5")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,10);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 10);
            }

            if (Utils.VolcanoRecall.RecallPlayer != null && Utils.VolcanoRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.VolcanoRecall.KillCount != -1) {
                Utils.VolcanoRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("火山烈焰击杀数:"+(20-Utils.VolcanoRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano));
                Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
/*        if(monsterhelmet instanceof bossarmor1)
        {

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            player.addItem(Moditems.bossaward1.get().getDefaultInstance());

            if(!data.contains("KillCountOfBoss1")) data.putInt("KillCountOfBoss1",1);
            else data.putInt("KillCountOfBoss1",data.getInt("KillCountOfBoss1")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,20);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 20);
            }
        }
        if(monsterhelmet instanceof bossarmor2)
        {

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            player.addItem(Moditems.bossaward2.get().getDefaultInstance());

            if(!data.contains("KillCountOfBoss2")) data.putInt("KillCountOfBoss2",1);
            else data.putInt("KillCountOfBoss2",data.getInt("KillCountOfBoss2")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,50);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 50);
            }
        }*/
        if (monsterhelmet.equals(Moditems.ArmorMine.get())) {

            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            RateItemStackGive(Moditems.MineSoul1.get().getDefaultInstance(),0.05,player);

            RateItemStackGive(Moditems.MineSoul.get().getDefaultInstance(),0.8,player);

            ItemStack itemStack4 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack4);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,10);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 10);
            }

            if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            if (monster instanceof Zombie && data.contains("DailyMission6"))
            {
                if (!data.contains("KillCountOfMineZombie")) data.putInt("KillCountOfMineZombie", 1);
                else data.putInt("KillCountOfMineZombie", data.getInt("KillCountOfMineZombie") + 1);
                data.putInt("DailyMission6", data.getInt("DailyMission6") + 1);
            }
            if (monster instanceof Skeleton && data.contains("DailyMission7"))
            {
                if (!data.contains("KillCountOfMineSkeleton")) data.putInt("KillCountOfMineSkeleton", 1);
                else data.putInt("KillCountOfMineSkeleton", data.getInt("KillCountOfMineSkeleton") + 1);
                data.putInt("DailyMission7", data.getInt("DailyMission7") + 1);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorField.get())) {

            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.FieldSoul.get().getDefaultInstance(),0.8,player);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            if(!data.contains("KillCountOfFeildZombie")) data.putInt("KillCountOfFeildZombie",1);
            else data.putInt("KillCountOfFeildZombie",data.getInt("KillCountOfFeildZombie")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,12);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 12);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorSnow.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.SnowSoul.get().getDefaultInstance(),0.8,player);

            Compute.ItemStackGive(player,itemStack1);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfSnowStray")) data.putInt("KillCountOfSnowStray",1);
            else data.putInt("KillCountOfSnowStray",data.getInt("KillCountOfSnowStray")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission8")) data.putInt("DailyMission8",data.getInt("DailyMission8")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,14);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 14);
            }

            if (Utils.SnowRecall.RecallPlayer != null && Utils.SnowRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.SnowRecall.KillCount != -1) {
                Utils.SnowRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("冰川流浪者击杀数:"+(20-Utils.SnowRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSnow));
                Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorSky.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.SkySoul.get().getDefaultInstance(),0.8,player);

            Compute.ItemStackGive(player,itemStack1);

            ItemStack itemStack00 = Moditems.SkyHForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack00.getDisplayName()));
                Compute.ItemStackGive(player,itemStack00);
            }

            ItemStack itemStack01 = Moditems.SkyCForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack01.getDisplayName()));
                Compute.ItemStackGive(player,itemStack01);
            }

            ItemStack itemStack02 = Moditems.SkyLForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack02.getDisplayName()));
                Compute.ItemStackGive(player,itemStack02);
            }

            ItemStack itemStack03 = Moditems.SkyBForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack03.getDisplayName()));
                Compute.ItemStackGive(player,itemStack03);
            }

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfVex")) data.putInt("KillCountOfVex",1);
            else data.putInt("KillCountOfVex",data.getInt("KillCountOfVex")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission9")) data.putInt("DailyMission9",data.getInt("DailyMission9")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,16);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 16);
            }

        }
        if (monsterhelmet.equals(Moditems.ArmorEvoker.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(3);
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.EvokerSoul.get().getDefaultInstance(),0.8,player);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

            if(!data.contains("KillCountOfEvoker")) data.putInt("KillCountOfEvoker",1);
            else data.putInt("KillCountOfEvoker",data.getInt("KillCountOfEvoker")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission10")) data.putInt("DailyMission10",data.getInt("DailyMission10")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,24);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 24);
            }

        }
        if (monsterhelmet.equals(Moditems.ArmorEvokerMaster.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.04,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(5);
            Compute.ItemStackGive(player,itemStack1);

            ItemStack itemStack3 = Moditems.EvokerSoul.get().getDefaultInstance();
            itemStack3.setCount(4);
            Compute.ItemStackGive(player,itemStack3);


            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfEvokerMaster")) data.putInt("KillCountOfEvokerMaster",1);
            else data.putInt("KillCountOfEvokerMaster",data.getInt("KillCountOfEvokerMaster")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,45);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 45);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorGuardian.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.SeaSoul.get().getDefaultInstance(),0.8,player);

            ItemStack itemStack00 = Moditems.SeaSwordForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.001 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack00.getDisplayName()));
                Compute.ItemStackGive(player,itemStack00);
            }

            Compute.ItemStackGive(player,itemStack1);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfGuardian")) data.putInt("KillCountOfGuardian",1);
            else data.putInt("KillCountOfGuardian",data.getInt("KillCountOfGuardian")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission12")) data.putInt("DailyMission12",data.getInt("DailyMission12")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,40);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 40);
            }

            if (Utils.SeaRecall.RecallPlayer != null && Utils.SeaRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.SeaRecall.KillCount != -1) {
                Utils.SeaRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("神殿守卫击杀数:"+(20-Utils.SeaRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea));
                Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorLZHelmet.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.LightningSoul.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack00 = Moditems.LAHForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack00.getDisplayName()));
                Compute.ItemStackGive(player,itemStack00);
            }

            ItemStack itemStack01 = Moditems.LACForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack01.getDisplayName()));
                Compute.ItemStackGive(player,itemStack01);
            }

            ItemStack itemStack02 = Moditems.LALForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack02.getDisplayName()));
                Compute.ItemStackGive(player,itemStack02);
            }

            ItemStack itemStack03 = Moditems.LABForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.00025 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack03.getDisplayName()));
                Compute.ItemStackGive(player,itemStack03);
            }

            Compute.ItemStackGive(player,itemStack1);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfLZ")) data.putInt("KillCountOfLZ",1);
            else data.putInt("KillCountOfLZ",data.getInt("KillCountOfLZ")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission13")) data.putInt("DailyMission13",data.getInt("DailyMission13")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,40);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 40);
            }

            if (Utils.LightningRecall.RecallPlayer != null && Utils.LightningRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.LightningRecall.KillCount != -1) {
                Utils.LightningRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("唤雷守卫击杀数:"+(20-Utils.LightningRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland));
                Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorHusk.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.BlackForestSoul.get().getDefaultInstance(),0.7,player);

            ItemStack itemStack00 = Moditems.BlackForestForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.001 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack00.getDisplayName()));
                Compute.ItemStackGive(player,itemStack00);
            }

            Compute.ItemStackGive(player,itemStack1);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfBFHusk")) data.putInt("KillCountOfBFHusk",1);
            else data.putInt("KillCountOfBFHusk",data.getInt("KillCountOfBFHusk")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission11")) data.putInt("DailyMission11",data.getInt("DailyMission11")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,40);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 40);
            }

            if (Utils.HuskRecall.RecallPlayer != null && Utils.HuskRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.HuskRecall.KillCount != -1) {
                Utils.HuskRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("脆弱的灵魂击杀数:"+(20-Utils.HuskRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest));
                Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorKazeHelmet.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.KazeSoul.get().getDefaultInstance(),0.7,player);

            ItemStack itemStack00 = Moditems.KazeSwordFG.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.0005 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack00.getDisplayName()));
                Compute.ItemStackGive(player,itemStack00);
            }

            ItemStack itemStack01 = Moditems.KazeBootsFG.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.0005 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack01.getDisplayName()));
                Compute.ItemStackGive(player,itemStack01);
            }

            Compute.ItemStackGive(player,itemStack1);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfKaze")) data.putInt("KillCountOfKaze",1);
            else data.putInt("KillCountOfKaze",data.getInt("KillCountOfKaze")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission18")) data.putInt("DailyMission18",data.getInt("DailyMission18")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,40);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 40);
            }

            if (Utils.KazeRecall.RecallPlayer != null && Utils.KazeRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.KazeRecall.KillCount != -1) {
                Utils.KazeRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("狂风击杀数:"+(20-Utils.KazeRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze));
                Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorSpider.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.SpiderSoul.get().getDefaultInstance(),0.7,player);

            Compute.ItemStackGive(player,itemStack1);

            int Exp = 80;
            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfSpider")) data.putInt("KillCountOfSpider",1);
            else data.putInt("KillCountOfSpider",data.getInt("KillCountOfSpider")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission19")) data.putInt("DailyMission19",data.getInt("DailyMission19")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,Exp);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + Exp);
            }
            if (Utils.SpiderRecall.RecallPlayer != null && Utils.SpiderRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.SpiderRecall.KillCount != -1) {
                Utils.SpiderRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("微光蜘蛛击杀数:"+(20-Utils.SpiderRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider));
                Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorSilverFish.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.03,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.SilverFishSoul.get().getDefaultInstance(),0.1,player);

            Compute.ItemStackGive(player,itemStack1);

            int Exp = 100;
            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            Compute.ExpPercentGetAndMSGSend(player,0.005,ExpUp,70);
            if(!data.contains("KillCountOfSilverfish")) data.putInt("KillCountOfSilverfish",1);
            else data.putInt("KillCountOfSilverfish",data.getInt("KillCountOfSilverfish")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission20")) data.putInt("DailyMission20",data.getInt("DailyMission20")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,Exp);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + Exp);
            }
        }

        if (monsterhelmet.equals(Moditems.ArmorWitherSkeleton.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.04,player);

            RateItemStackGive(Moditems.ruby.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(5);
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.witherBone.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack5 = Moditems.NetherHForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.001 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack5.getDisplayName()));
                Compute.ItemStackGive(player,itemStack5);
            }

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfWitherSkeleton")) data.putInt("KillCountOfWitherSkeleton",1);
            else data.putInt("KillCountOfWitherSkeleton",data.getInt("KillCountOfWitherSkeleton")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission14")) data.putInt("DailyMission14",data.getInt("DailyMission14")+1);

            if(Utils.netherMobSpawn == 1 && r.nextFloat(1.0F) < 0.3)
            {
                ItemStack itemStack4 = Moditems.NetherQuartz.get().getDefaultInstance();
                Compute.FormatBroad(player.level(),Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal(player.getName().getString()+"通过下界限时事件击杀怪物，获得了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack4.getDisplayName()).append("*1").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                Compute.ItemStackGive(player,itemStack4);
            }

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,55);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 55);
            }

            if (Utils.NetherRecall.RecallPlayer != null && Utils.NetherRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.NetherRecall.KillCount != -1) {
                Utils.NetherRecall.KillCount --;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("下界凋零骷髅击杀数:"+(20-Utils.NetherRecall.KillCount)+ "/"
                                + "20").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether));
                Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorZombiePiglin.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.04,player);

            RateItemStackGive(Moditems.ruby.get().getDefaultInstance(),0.35,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(5);
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.PigLinSoul.get().getDefaultInstance(),0.35,player);

            ItemStack itemStack5 = Moditems.NetherBForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.001 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack5.getDisplayName()));
                Compute.ItemStackGive(player,itemStack5);
            }

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfZombiePigLin")) data.putInt("KillCountOfZombiePigLin",1);
            else data.putInt("KillCountOfZombiePigLin",data.getInt("KillCountOfZombiePigLin")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission15")) data.putInt("DailyMission15",data.getInt("DailyMission15")+1);


            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,60);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 60);
            }

            if(Utils.netherMobSpawn == 2 && r.nextFloat(1.0F) < 0.3)
            {
                ItemStack itemStack4 = Moditems.NetherQuartz.get().getDefaultInstance();
                Compute.FormatBroad(player.level(),Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal(player.getName().getString()+"通过下界限时事件击杀怪物，获得了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack4.getDisplayName()).append("*1").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                Compute.ItemStackGive(player,itemStack4);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorNetherSkeletonHelmet.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.045,player);

            RateItemStackGive(Moditems.ruby.get().getDefaultInstance(),0.45,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(5);
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.netherbonemeal.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack5 = Moditems.NetherCForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.001 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack5.getDisplayName()));
                Compute.ItemStackGive(player,itemStack5);
            }

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfNetherSkeleton")) data.putInt("KillCountOfNetherSkeleton",1);
            else data.putInt("KillCountOfNetherSkeleton",data.getInt("KillCountOfNetherSkeleton")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission16")) data.putInt("DailyMission16",data.getInt("DailyMission16")+1);


            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,65);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 65);
            }
            if(Utils.netherMobSpawn == 3 && r.nextFloat(1.0F) < 0.3)
            {
                ItemStack itemStack4 = Moditems.NetherQuartz.get().getDefaultInstance();
                Compute.FormatBroad(player.level(),Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal(player.getName().getString()+"通过下界限时事件击杀怪物，获得了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack4.getDisplayName()).append("*1").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                Compute.ItemStackGive(player,itemStack4);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorMagma.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.04,player);

            RateItemStackGive(Moditems.ruby.get().getDefaultInstance(),0.45,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(5);
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.NetherMagmaPower.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack5 = Moditems.NetherLForgingDrawing.get().getDefaultInstance();
            if(r.nextFloat(1.0F) < 0.001 * (1 + LuckyUp)) {
                Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack5.getDisplayName()));
                Compute.ItemStackGive(player,itemStack5);
            }

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            if(!data.contains("KillCountOfNetherMagma")) data.putInt("KillCountOfNetherMagma",1);
            else data.putInt("KillCountOfNetherMagma",data.getInt("KillCountOfNetherMagma")+1);

            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            if(data.contains("DailyMission17")) data.putInt("DailyMission17",data.getInt("DailyMission17")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,60);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 60);
            }
            if(Utils.netherMobSpawn == 4 && r.nextFloat(1.0F) < 0.3 * (1 + LuckyUp))
            {
                ItemStack itemStack4 = Moditems.NetherQuartz.get().getDefaultInstance();
                Compute.FormatBroad(player.level(),Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal(player.getName().getString()+"通过下界限时事件击杀怪物，获得了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(itemStack4.getDisplayName()).append("*1").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                Compute.ItemStackGive(player,itemStack4);
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorEnderMan.get())) {
            RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.075,player);

            RateItemStackGive(Moditems.Piece.get().getDefaultInstance(),0.3,player);

            ItemStack itemStack1 = Moditems.SilverCoin.get().getDefaultInstance();
            itemStack1.setCount(8);
            Compute.ItemStackGive(player,itemStack1);

            RateItemStackGive(Moditems.RecallPiece.get().getDefaultInstance(),0.25,player);

            int Exp = 300;
            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            Compute.ExpPercentGetAndMSGSend(player,0.0075,0,75);
            if(!data.contains("KillCountOfEnderMan")) data.putInt("KillCountOfEnderMan",1);
            else data.putInt("KillCountOfEnderMan",data.getInt("KillCountOfEnderMan")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(data.contains("DailyMission21")) data.putInt("DailyMission21",data.getInt("DailyMission21")+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,Exp);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + Exp);
            }
        }
        if(monster instanceof HEntity)
        {
            ItemStack itemStack = Moditems.main1crystal.get().getDefaultInstance();
            Compute.ItemStackGive(Utils.HBossPlayer,itemStack);
            Compute.ExpPercentGetAndMSGSend(Utils.HBossPlayer,0.2,ExpUp,32);
            Level overWorld = monster.getServer().getLevel(Level.OVERWORLD);
            List<Player> list = overWorld.getEntitiesOfClass(Player.class,AABB.ofSize(new Vec3(-163.5,115,1417.5),16,10,16));
            for (Player player1 : list) {
                ItemStack itemStack1 = Items.AIR.getDefaultInstance();
                switch (r.nextInt(4)){
                    case 0 -> {
                        itemStack1 = Moditems.Main1BeltGem.get().getDefaultInstance();
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.75,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.5,player1);
                    }
                    case 1 -> {
                        itemStack1 = Moditems.Main1BraceletGem.get().getDefaultInstance();
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.75,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.5,player1);
                    }
                    case 2 -> {
                        itemStack1 = Moditems.Main1HandGem.get().getDefaultInstance();
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.75,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.5,player1);
                    }
                    case 3 -> {
                        itemStack1 = Moditems.Main1necklaceGem.get().getDefaultInstance();
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,1,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.75,player1);
                        Compute.RandomAttributeProvider(itemStack1,1,0.5,player1);
                    }
                }
                Compute.FormatBroad(player.level(),Component.literal("BOSS").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_RED),
                        Component.literal(player.getName().getString()+"击杀了MainIBoss,").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal(player1.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(itemStack1.getDisplayName()));
                Compute.ItemStackGive(player1,itemStack1);
            }
            if(!data.contains("KillCountOfHBoss")) data.putInt("KillCountOfHBoss",1);
            else data.putInt("KillCountOfHBoss",data.getInt("KillCountOfHBoss")+1);
            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                dataI.putInt(StringUtils.KillCount.KillCount,1000);
            }
            else {
                dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 1000);
            }
        }
        if(monsterhelmet.equals(Moditems.ArmorPFH.get()))
        {
            if(data.getInt("PFTIME") > 0){
                ItemStack itemStack = Moditems.PlainSoul.get().getDefaultInstance();
                itemStack.setCount(r.nextInt(6));

                ItemStack itemStack1 = Moditems.ForestSoul.get().getDefaultInstance();
                itemStack1.setCount(r.nextInt(6));

                RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.045,player);

                ItemStack itemStack00 = Moditems.SunPower.get().getDefaultInstance();
                if(r.nextFloat(1.0F) < 0.1 * (1 + LuckyUp)) {
                    Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(itemStack00.getDisplayName()));
                    Compute.ItemStackGive(player,itemStack00);
                }

                Compute.ItemStackGive(player,itemStack);
                Compute.ItemStackGive(player,itemStack1);
                Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

                CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
                if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                    dataI.putInt(StringUtils.KillCount.KillCount,20);
                }
                else {
                    dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 20);
                }
                if(!data.contains("KillCountOfPF")) data.putInt("KillCountOfPF",1);
                else data.putInt("KillCountOfPF",data.getInt("KillCountOfPF")+1);

                if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
                else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

                if(!data.contains("KillCountOfPFC")) data.putInt("KillCountOfPFC",1);
                else data.putInt("KillCountOfPFC",data.getInt("KillCountOfPFC")+1);
            }
            else {
                player.teleportTo(214,102,1074);
                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                        withStyle(Utils.styleOfHealth),Component.literal("副本未向你激活，这将不会获得奖励。"));
            }
        }
        if(monsterhelmet.equals(Moditems.ArmorSVH))
        {
            if(data.getInt("SVTIME") > 0){
                ItemStack itemStack = Moditems.VolcanoSoul.get().getDefaultInstance();
                itemStack.setCount(r.nextInt(8));

                ItemStack itemStack0 = Moditems.gemspiece.get().getDefaultInstance();
                if(r.nextFloat(1.0F) < 0.05 * (1 + LuckyUp)) Compute.ItemStackGive(player,itemStack0);

                ItemStack itemStack00 = Moditems.VolcanoCore.get().getDefaultInstance();
                if(r.nextFloat(1.0F) < 0.1 * (1 + LuckyUp)) {
                    Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(itemStack00.getDisplayName()));
                    Compute.ItemStackGive(player,itemStack00);
                }

/*                ItemStack itemStack2 = Moditems.SunPower.get().getDefaultInstance();

                if(r.nextFloat(1.0F) < 0.25) Compute.ItemStackGive(player,itemStack2);*/

                Compute.ItemStackGive(player,itemStack);
                Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

                CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
                if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                    dataI.putInt(StringUtils.KillCount.KillCount,35);
                }
                else {
                    dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 35);
                }
                if(!data.contains("KillCountOfSV")) data.putInt("KillCountOfSV",1);
                else data.putInt("KillCountOfSV",data.getInt("KillCountOfSV")+1);

                if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
                else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

                if(!data.contains("KillCountOfSVC")) data.putInt("KillCountOfSVC",1);
                else data.putInt("KillCountOfSVC",data.getInt("KillCountOfSVC")+1);
            }
            else {
                player.teleportTo(5,-54,990);
                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                        withStyle(ChatFormatting.YELLOW),Component.literal("副本未向你激活，这将不会获得奖励。"));
            }
        }
        if(monsterhelmet.equals(Moditems.ArmorSLH))
        {
            if(data.getInt("SLTIME") > 0){
                ItemStack itemStack = Moditems.WaterSoul.get().getDefaultInstance();
                itemStack.setCount(r.nextInt(8));

                RateItemStackGive(Moditems.gemspiece.get().getDefaultInstance(),0.05,player);

                ItemStack itemStack00 = Moditems.LakeCore.get().getDefaultInstance();
                if(r.nextFloat(1.0F) < 0.1 * (1 + LuckyUp)) {
                    Compute.FormatBroad(player.level(),Component.literal("稀有掉落").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(itemStack00.getDisplayName()));
                    Compute.ItemStackGive(player,itemStack00);
                }

/*                ItemStack itemStack2 = Moditems.SunPower.get().getDefaultInstance();

                if(r.nextFloat(1.0F) < 0.25) Compute.ItemStackGive(player,itemStack2);*/

                Compute.ItemStackGive(player,itemStack);
                Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());

                CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
                if(!dataI.contains(StringUtils.KillCount.KillCount)) {
                    dataI.putInt(StringUtils.KillCount.KillCount,30);
                }
                else {
                    dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 30);
                }

                if(!data.contains("KillCountOfSL")) data.putInt("KillCountOfSL",1);
                else data.putInt("KillCountOfSL",data.getInt("KillCountOfSL")+1);

                if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
                else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);

                if(!data.contains("KillCountOfSLC")) data.putInt("KillCountOfSLC",1);
                else data.putInt("KillCountOfSLC",data.getInt("KillCountOfSLC")+1);
            }
            else {
                player.teleportTo(66,-24,982);
                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                        withStyle(ChatFormatting.BLUE),Component.literal("副本未向你激活，这将不会获得奖励。"));
            }
        }
        if (monsterhelmet.equals(Moditems.ArmorForestBoss.get())) {
            RateItemStackGive(Moditems.ForestBossCore.get().getDefaultInstance(),0.99,player);
            ItemStackGive(player,Moditems.ForestSoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);

            if(!dataI.contains(StringUtils.KillCount.KillCount)) dataI.putInt(StringUtils.KillCount.KillCount,30);
            else dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 30);

            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(!data.contains(StringUtils.KillCount.ForestBoss)) data.putInt(StringUtils.KillCount.ForestBoss,1);
            else data.putInt(StringUtils.KillCount.ForestBoss,data.getInt(StringUtils.KillCount.ForestBoss)+1);
        }
        if (monsterhelmet.equals(Moditems.ArmorVolcanoBoss.get())) {
            RateItemStackGive(Moditems.VolcanoBossCore.get().getDefaultInstance(),0.99,player);
            ItemStackGive(player,Moditems.VolcanoSoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);

            if(!dataI.contains(StringUtils.KillCount.KillCount)) dataI.putInt(StringUtils.KillCount.KillCount,30);
            else dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 30);

            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(!data.contains(StringUtils.KillCount.VolcanoBoss)) data.putInt(StringUtils.KillCount.VolcanoBoss,1);
            else data.putInt(StringUtils.KillCount.VolcanoBoss,data.getInt(StringUtils.KillCount.VolcanoBoss)+1);
        }
        if (monsterhelmet.equals(Moditems.ArmorSakuraMob.get())) {
            RateItemStackGive(Moditems.SakuraPetal.get().getDefaultInstance(),0.05,player);
            RateItemStackGive(Moditems.GoldCoin.get().getDefaultInstance(),0.2,player);

            Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,Utils.MobLevel.get(monsterhelmet).intValue());
            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);

            if(!dataI.contains(StringUtils.KillCount.KillCount)) dataI.putInt(StringUtils.KillCount.KillCount,100);
            else dataI.putInt(StringUtils.KillCount.KillCount,dataI.getInt(StringUtils.KillCount.KillCount) + 100);

            if(!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total,1);
            else data.putInt(StringUtils.KillCount.Total,data.getInt(StringUtils.KillCount.Total)+1);
            if(!data.contains(StringUtils.KillCount.SakuraMob)) data.putInt(StringUtils.KillCount.SakuraMob,1);
            else data.putInt(StringUtils.KillCount.SakuraMob,data.getInt(StringUtils.KillCount.SakuraMob)+1);
        }


        if(player.getPersistentData().getBoolean("SignAward"))
        {
            player.sendSystemMessage(Component.literal("你获得了签到奖励！"));
            player.addItem(Moditems.SignInReward.get().getDefaultInstance());
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
            String DateString = tmpDate.format(date);
            player.getPersistentData().putString("SignIn",DateString);
            player.getPersistentData().putBoolean("SignAward",false);
            for(int i = 1; i <= 17; i++) data.putInt("DailyMission"+i,0);

/*            data.putInt("DailyMission1",0);
            data.putInt("DailyMission2",0);
            data.putInt("DailyMission3",0);
            data.putInt("DailyMission4",0);
            data.putInt("DailyMission5",0);
            data.putInt("DailyMission6",0);
            data.putInt("DailyMission7",0);
            data.putInt("DailyMission8",0);
            data.putInt("DailyMission9",0);
            data.putInt("DailyMission10",0);
            data.putInt("DailyMission11",0);
            data.putInt("DailyMission12",0);
            data.putInt("DailyMission13",0);
            data.putInt("DailyMission14",0);
            data.putInt("DailyMission15",0);
            data.putInt("DailyMission16",0);*/
            ItemStack itemStack = Moditems.DailyMission.get().getDefaultInstance();
            CompoundTag ItemData = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            int MissionLevel = 0;
            if(player.experienceLevel <= 15) MissionLevel = 3;
            else if(player.experienceLevel <= 25) MissionLevel = 6;
            else if(player.experienceLevel <= 35) MissionLevel = 8;
            else if(player.experienceLevel <= 49) MissionLevel = 12;
            else MissionLevel = 17;
            boolean flag = true;
            for(int i = 1; i <= MissionLevel; i++)
            {
                if(r.nextInt(100) < 50) {
                    flag = false;
                    ItemData.putInt("DailyMission"+i,r.nextInt(24,64));
                }
            }
            if(flag) ItemData.putInt("DailyMission1",24);
/*            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission1",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission2",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission3",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission4",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission5",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission6",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission7",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission8",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission9",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission10",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission11",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission12",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission13",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission14",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission15",r.nextInt(16,80));
            if(r.nextInt(100) < 50) ItemData.putInt("DailyMission16",r.nextInt(16,80));*/
            itemStack.getOrCreateTagElement(Utils.MOD_ID).putString("Owner",player.getName().getString());
            Compute.ItemStackGive(player,itemStack);
        }
        if (Utils.KazeRecall.RecallPlayer != null && monsterhelmet instanceof ArmorkazeRecall && Utils.KazeRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.KazeRecall.RecallSuccess = true;
        }
        if (Utils.SpiderRecall.RecallPlayer != null && monsterhelmet instanceof ArmorSpiderRecall && Utils.SpiderRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.SpiderRecall.RecallSuccess = true;
        }
        if (Utils.HuskRecall.RecallPlayer != null && monsterhelmet instanceof ArmorHuskRecall && Utils.HuskRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.HuskRecall.RecallSuccess = true;
        }
        if (Utils.SeaRecall.RecallPlayer != null && monsterhelmet instanceof ArmorSeaRecall && Utils.SeaRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.SeaRecall.RecallSuccess = true;
        }
        if (Utils.LightningRecall.RecallPlayer != null && monsterhelmet instanceof ArmorLightningRecall && Utils.LightningRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.LightningRecall.RecallSuccess = true;
        }
        if (Utils.NetherRecall.RecallPlayer != null && monsterhelmet instanceof ArmorNetherRecall && Utils.NetherRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.NetherRecall.RecallSuccess = true;
        }
        if (Utils.SnowRecall.RecallPlayer != null && monsterhelmet instanceof ArmorSnowRecall && Utils.SnowRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.SnowRecall.RecallSuccess = true;
        }
        if (Utils.ForestRecall.RecallPlayer != null && monsterhelmet instanceof ArmorForestRecall && Utils.ForestRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.ForestRecallBossKillCount ++;
            if (Utils.ForestRecallBossKillCount == 2) Utils.ForestRecall.RecallSuccess = true;
        }
        if (Utils.VolcanoRecall.RecallPlayer != null && monsterhelmet instanceof ArmorVolcanoRecall && Utils.VolcanoRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.VolcanoRecall.RecallSuccess = true;
        }
/*        if(equip.getTagElement(Utils.MOD_ID).contains(StringUtils.KillCount.KillCount))
        {
            int Count = equip.getTagElement(Utils.MOD_ID).getInt(StringUtils.KillCount.KillCount);
            equip.getOrCreateTagElement(Utils.MOD_ID).putInt(StringUtils.KillCount.KillCount,Count+1);
        }
        else
        {
            equip.getOrCreateTagElement(Utils.MOD_ID).putInt(StringUtils.KillCount.KillCount,1);
        }*/
        if(data.contains("Sword")) data.putInt("Sword",data.getInt("Sword")+1);
        if(data.contains("Barker")) data.putInt("Barker",data.getInt("Barker")+1);
        if(data.contains("Bow") && equip.getItem() instanceof BowItem) data.putInt("Bow",data.getInt("Bow")+1);

    }
    public static void ExpPercentGetAndMSGSend(Player player, double num, float ExpUp, int ExpLevel)
    {
        CompoundTag data = player.getPersistentData();
        double Xp = (Math.pow(Math.E,3 + (ExpLevel/50d) * 7) * num) * (1 + ExpUp);
        if(data.contains("Xp")) data.putDouble("Xp",data.getDouble("Xp")+Xp);
        else data.putDouble("Xp",Xp);
        if(!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp"))) Compute.FormatMSGSend(player,Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                Component.literal("你获得了 ").append(Component.literal(String.format("%.1f",Xp))).append(Component.literal("经验值").
                        append(Component.literal("(其中"+String.format("%.1f",(Math.pow(Math.E,3 + (ExpLevel/50d) * 7) * num) * ExpUp)+"来自经验加成)").withStyle(ChatFormatting.LIGHT_PURPLE)).
                        append(Component.literal("["+String.format("%.1f",data.getDouble("Xp"))+"/"+String.format("%.1f",Math.pow(Math.E,3 + (player.experienceLevel/50d) * 7))+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY))));
    }
    public static void USE(Player player, Item Tool)
    {
        CompoundTag data = player.getPersistentData();
        if(Tool instanceof PlainSword0 || Tool instanceof PlainSwrod1 || Tool instanceof PlainSword2 || Tool instanceof PlainSword3)
        {
            if(data.getInt("MANA") < 20) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                Compute.VerticleCircleParticle(serverPlayer,0.75 * 2,1,12,ParticleTypes.HEART);
                Compute.RandomMoveParticle(serverPlayer,0.75 * 2,1,8,ParticleTypes.COMPOSTER);
                float HealEffectUp = Compute.PlayerHealEffectUp(player);
                data.putInt("MANA",data.getInt("MANA")-20);
                Compute.ManaStatusUpdate(player);
                if(Tool instanceof PlainSword0) player.heal(player.getMaxHealth() * 0.1f * (1+HealEffectUp));
                else if(Tool instanceof PlainSwrod1) player.heal(player.getMaxHealth() * 0.12f * (1+HealEffectUp));
                else if(Tool instanceof PlainSword2) player.heal(player.getMaxHealth() * 0.15f * (1+HealEffectUp));
                else player.heal(player.getMaxHealth() * 0.2f * (1+HealEffectUp));;
                player.getCooldowns().addCooldown(Moditems.PlainSword0.get(),(int) (400-400.0*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.PlainSword1.get(),(int) (400-400.0*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.PlainSword2.get(),(int) (400-400.0*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.PlainSword3.get(),(int) (400-400.0*Compute.PlayerCoolDownDecrease(player)));
            }
        }
        else if(Tool instanceof ForestSword0 || Tool instanceof ForestSword1 || Tool instanceof ForestSword2 || Tool instanceof ForestSword3 || Tool instanceof ForestSword4)
        {
            if(data.getInt("MANA") < 20) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                int TickCount = player.getServer().getTickCount();
                data.putInt("MANA",data.getInt("MANA")-20);
                Compute.ManaStatusUpdate(player);
                Compute.VerticleCircleParticle(serverPlayer,1.5 * 2,0.25,12,ParticleTypes.COMPOSTER);
                Compute.VerticleCircleParticle(serverPlayer,1.25 * 2,0.5,16,ParticleTypes.COMPOSTER);
                Compute.VerticleCircleParticle(serverPlayer,1.0 * 2,0.75,20,ParticleTypes.COMPOSTER);
                Compute.VerticleCircleParticle(serverPlayer,0.75 * 2,1,24,ParticleTypes.COMPOSTER);
                Compute.VerticleCircleParticle(serverPlayer,0.5 * 2,1.25,28,ParticleTypes.COMPOSTER);
                Compute.VerticleCircleParticle(serverPlayer,0.25 * 2,1.5,32,ParticleTypes.COMPOSTER);
                Compute.VerticleCircleParticle(serverPlayer,0.0 * 2,1.75,36,ParticleTypes.COMPOSTER);
                if(Tool instanceof ForestSword0) {
                    player.getPersistentData().putInt(StringUtils.ForestSwordSkill0,TickCount + 160);
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTSWORD.get(),160));
                }
                else if(Tool instanceof ForestSword1) {
                    player.getPersistentData().putInt(StringUtils.ForestSwordSkill0,TickCount + 200);
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTSWORD.get(),200));
                }
                else if(Tool instanceof ForestSword2) {
                    player.getPersistentData().putInt(StringUtils.ForestSwordSkill0,TickCount + 240);
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTSWORD.get(),240));
                }
                else if(Tool instanceof ForestSword3){
                    player.getPersistentData().putInt(StringUtils.ForestSwordSkill0,TickCount + 400);
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTSWORD.get(),400));
                }
                else {
                    player.getPersistentData().putInt(StringUtils.ForestSwordSkill1,TickCount + 400);
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTSWORD.get(),400));
                }
                player.getCooldowns().addCooldown(Moditems.forestsword0.get(),(int) (600-600.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.forestsword1.get(),(int) (600-600.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.forestsword2.get(),(int) (600-600.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.forestsword3.get(),(int) (600-600.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.forestsword4.get(),(int) (600-600.0* Compute.PlayerCoolDownDecrease(player)));
            }
        }
        else if(Tool instanceof LakeSword3)
        {
            if(data.getInt("MANA") < 20) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                data.putInt("MANA",data.getInt("MANA")-20);
                Compute.ManaStatusUpdate(player);
                ModNetworking.sendToClient(new UtilsLakeSwordS2CPacket(true),(ServerPlayer) player);
                ClientUtils.UseOfLakeSword = true;
                player.getCooldowns().addCooldown(Moditems.lakesword3.get(),(int) (160*(1.0-Compute.PlayerCoolDownDecrease(player))));
            }
        }
        else if(Tool instanceof VolcanoSword0 || Tool instanceof VolcanoSword1 || Tool instanceof VolcanoSword2 || Tool instanceof VolcanoSword3 || Tool instanceof VolcanoSword4)
        {
            if(data.getInt("MANA") < 20) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                VerticleCircleParticle((ServerPlayer) player,0.25,1,16,ParticleTypes.ANGRY_VILLAGER);
                data.putInt("MANA",data.getInt("MANA")-20);
                Compute.ManaStatusUpdate(player);
                Compute.RandomMoveParticle((ServerPlayer) player,0,0.25,32,ParticleTypes.ASH);
                int TickCount = player.getServer().getTickCount();
                if(Tool instanceof VolcanoSword0) player.getPersistentData().putInt(StringUtils.VolcanoSwordSkill.Skill0,TickCount + 100);
                else if(Tool instanceof VolcanoSword1) player.getPersistentData().putInt(StringUtils.VolcanoSwordSkill.Skill1,TickCount + 100);
                else if(Tool instanceof VolcanoSword2) player.getPersistentData().putInt(StringUtils.VolcanoSwordSkill.Skill2,TickCount + 100);
                else if(Tool instanceof VolcanoSword3) player.getPersistentData().putInt(StringUtils.VolcanoSwordSkill.Skill3,TickCount + 100);
                else player.getPersistentData().putInt(StringUtils.VolcanoSwordSkill.Skill4,TickCount + 100);
                player.getCooldowns().addCooldown(Moditems.volcanosword0.get(),(int) (200-200.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.volcanosword1.get(),(int) (200-200.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.volcanosword2.get(),(int) (200-200.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.volcanosword3.get(),(int) (200-200.0* Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.VolcanoSword4.get(),(int) (200-200.0* Compute.PlayerCoolDownDecrease(player)));
                player.addEffect(new MobEffectInstance(ModEffects.VOLCANOSWORD.get(),100));
            }
        }
        else if(Tool instanceof SnowSword3 || Tool instanceof SnowSword4)
        {
            if(data.getInt("MANA") < 20) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                data.putInt("MANA",data.getInt("MANA")-20);
                Compute.ManaStatusUpdate(player);
                ModNetworking.sendToClient(new UtilsSnowSwordS2CPacket(true),(ServerPlayer) player);
                player.getCooldowns().addCooldown(Moditems.snowsword3.get(),(int) (200*(1.0- Compute.PlayerCoolDownDecrease(player))));
                player.getCooldowns().addCooldown(Moditems.snowsword4.get(),(int) (200*(1.0- Compute.PlayerCoolDownDecrease(player))));
            }
        }
        else if(Tool instanceof ManaSword)
        {
            Compute.PlayerPowerParticle(player);
            player.getCooldowns().addCooldown(Moditems.ManaSword.get(),(int) (100-100*Compute.PlayerCoolDownDecrease(player)));
            data.putInt("ManaSwordActive",data.getInt("MANA"));
            data.putInt("MANA",0);
            Compute.ManaStatusUpdate(player);
        }
        else if(Tool instanceof WitherBonePower)
        {
            if(data.getInt("MANA") < 80) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Compute.PlayerPowerParticle(player);
                data.putInt("MANA",data.getInt("MANA")-80);
                int TickCount = player.getServer().getTickCount();
                if (data.contains("NetherRune") && data.getInt("NetherRune") == 2) data.putInt(StringUtils.NetherRune.NetherRune2,TickCount + 100);
                Compute.ManaStatusUpdate(player);
                player.getCooldowns().addCooldown(Moditems.WitherBonePower.get(),(int) (200*(1.0- Compute.PlayerCoolDownDecrease(player))));

                Level level = player.level();
                List<Mob> monsterList = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                for (Mob mob : monsterList) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Compute.ManaDamageToMonster(player,mob,2);
                        mob.getPersistentData().putInt("witherBonePower",100);
                        Utils.witherBonePowerCCMonster.add(mob);
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2));
                        Compute.EntityEffectVerticleCircleParticle(mob,1,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0.75,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0.25,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0,0.4,8,ParticleTypes.WITCH,0);
                    }
                }
                List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                Iterator<Player> iterator1 = playerList.iterator();
                for (Player player1 : playerList) {
                    if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        if(!player1.level().equals(player1.getServer().getLevel(Level.OVERWORLD))){
                            Compute.ManaDamageToPlayer(player,player1,2);
                            player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2));
                            player1.addEffect(new MobEffectInstance(MobEffects.WITHER,100,2));
                            Compute.EntityEffectVerticleCircleParticle(player1,1,0.4,8,ParticleTypes.WITCH,0);
                            Compute.EntityEffectVerticleCircleParticle(player1,0.75,0.4,8,ParticleTypes.WITCH,0);
                            Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.4,8,ParticleTypes.WITCH,0);
                            Compute.EntityEffectVerticleCircleParticle(player1,0.25,0.4,8,ParticleTypes.WITCH,0);
                            Compute.EntityEffectVerticleCircleParticle(player1,0,0.4,8,ParticleTypes.WITCH,0);
                        }
                    }
                }
            }
        }
        else if(Tool instanceof PiglinPower)
        {
            if(data.getInt("MANA") < 80) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Compute.PlayerPowerParticle(player);
                data.putInt("MANA",data.getInt("MANA")-80);
                Compute.ManaStatusUpdate(player);
                player.getCooldowns().addCooldown(Moditems.PigLinPower.get(),(int) (200*(1.0- Compute.PlayerCoolDownDecrease(player))));
                int TickCount = player.getServer().getTickCount();
                if (data.contains("NetherRune") && data.getInt("NetherRune") == 2) data.putInt(StringUtils.NetherRune.NetherRune2,TickCount + 100);

                Level level = player.level();
                List<Mob> monsterList = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                for (Mob mob : monsterList) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Compute.ManaDamageToMonster(player,mob,monsterList.size());
                        Compute.EntityEffectVerticleCircleParticle(mob,1,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0.75,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0.25,0.4,8,ParticleTypes.WITCH,0);
                        Compute.EntityEffectVerticleCircleParticle(mob,0,0.4,8,ParticleTypes.WITCH,0);

                    }
                }
                List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                for (Player player1 : playerList) {
                    if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        if(!player1.level().equals(player1.getServer().getLevel(Level.OVERWORLD))){
                            CompoundTag data1 = player1.getPersistentData();
                            data1.putInt("pigLinPower",200);
                            data1.putInt("pigLinPowerNumber",playerList.size());
                            data1.putFloat("pigLinPowerAp",Compute.PlayerManaDamage(player));
                        }
                        Compute.EntityEffectVerticleCircleParticle(player1,1,0.4,8,ParticleTypes.COMPOSTER,0);
                        Compute.EntityEffectVerticleCircleParticle(player1,0.75,0.4,8,ParticleTypes.COMPOSTER,0);
                        Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.4,8,ParticleTypes.COMPOSTER,0);
                        Compute.EntityEffectVerticleCircleParticle(player1,0.25,0.4,8,ParticleTypes.COMPOSTER,0);
                        Compute.EntityEffectVerticleCircleParticle(player1,0,0.4,8,ParticleTypes.COMPOSTER,0);

                    }
                }
            }
        }
        else if(Tool instanceof WitherBoneMealPower)
        {
            Compute.PlayerPowerParticle(player);
            player.getCooldowns().addCooldown(Moditems.WitherBoneMealPower.get(),(int) (200-200*Compute.PlayerCoolDownDecrease(player)));
            int TickCount = player.getServer().getTickCount();
            if (data.contains("NetherRune") && data.getInt("NetherRune") == 2) data.putInt(StringUtils.NetherRune.NetherRune2,TickCount + 100);

            Level level = player.level();
            List<Mob> monsterList = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
            for (Mob mob : monsterList) {
                if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                    Compute.ManaDamageToMonster(player,mob,10);
                    Compute.EntityEffectVerticleCircleParticle(mob,1,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(mob,0.75,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(mob,0.25,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(mob,0,0.4,8,ParticleTypes.WITCH,0);

                }
            }
            List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
            for (Player player1 : playerList) {
                if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                    if(!player1.level().equals(player1.getServer().getLevel(Level.OVERWORLD))) Compute.ManaDamageToPlayer(player,player1,10);
                    Compute.EntityEffectVerticleCircleParticle(player1,1,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0.75,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0.25,0.4,8,ParticleTypes.WITCH,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0,0.4,8,ParticleTypes.WITCH,0);
                }
            }
            player.getPersistentData().putInt("MANA",0);
            Compute.ManaStatusUpdate(player);
            if(player.getHealth() * 1.0 / player.getMaxHealth() <= 0.44) {
                player.kill();
                FormatBroad(level,Component.literal("死亡").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal(player.getName().getString()+"被自己的魔法干掉了。"));

            }
            else player.setHealth(player.getHealth()-player.getMaxHealth()*0.44f);
        }
        else if(Tool instanceof QuartzSword)
        {
            Compute.PlayerPowerParticle(player);
            if(data.getInt("MANA") < 30) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Level level = player.level();
                player.getCooldowns().addCooldown(Moditems.QuartzSword.get(), (int) (100-100*Compute.PlayerCoolDownDecrease(player)));
                data.putInt("MANA",data.getInt("MANA")-30);
                List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT,player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                Iterator<Player> iterator = playerList.iterator();
                while(iterator.hasNext())
                {
                    Player player1 = iterator.next();
                    Compute.ManaDamageToPlayer(player,player1,2.5f);
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt.setCause((ServerPlayer) player);
                    lightningBolt.setSilent(true);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.setDamage(0);
                    lightningBolt.moveTo(player1.getPosition(1.0f));
                    level.addFreshEntity(lightningBolt);
                }
                List<Mob> monsterList = level.getNearbyEntities(Mob.class,TargetingConditions.DEFAULT,player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
                Iterator<Mob> iterator1 = monsterList.iterator();
                while(iterator1.hasNext())
                {
                    Mob monster = iterator1.next();
                    Compute.ManaDamageToMonster(player,monster,2.5f);
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt.setCause((ServerPlayer) player);
                    lightningBolt.setSilent(true);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.setDamage(0);
                    lightningBolt.moveTo(monster.getPosition(1.0f));
                    level.addFreshEntity(lightningBolt);
                }
                Compute.ManaStatusUpdate(player);
            }
        }
        else if(Tool instanceof SeaSword0 || Tool instanceof SeaSword1 || Tool instanceof SeaSword2 || Tool instanceof SeaSword3 || Tool instanceof SeaSword4)
        {
            if(data.getInt("MANA") < 10) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Level level = player.level();
                player.getCooldowns().addCooldown(Moditems.SeaSword0.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.SeaSword1.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.SeaSword2.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.SeaSword3.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                data.putInt("MANA",data.getInt("MANA")-10);
                Compute.ManaStatusUpdate(player);
                data.putBoolean("SeaSword4",false);
                data.putBoolean("SeaSword3",false);
                data.putBoolean("SeaSword0",false);
                if(Tool instanceof SeaSword4) data.putBoolean("SeaSword4",true);
                else if(Tool instanceof SeaSword3) data.putBoolean("SeaSword3",true);
                else data.putBoolean("SeaSword0",true);
                player.addEffect(new MobEffectInstance(ModEffects.SEASWORD.get(),400));
            }
        }
        else if(Tool instanceof BlackForestSword0 || Tool instanceof BlackForestSword1 || Tool instanceof BlackForestSword2 || Tool instanceof BlackForestSword3 || Tool instanceof BlackForestSword4)
        {
            if(data.getInt("MANA") < 10) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Level level = player.level();
                player.getCooldowns().addCooldown(Moditems.BlackForestSword0.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.BlackForestSword1.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.BlackForestSword2.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                player.getCooldowns().addCooldown(Moditems.BlackForestSword3.get(), (int) (60-60*Compute.PlayerCoolDownDecrease(player)));
                data.putInt("MANA",data.getInt("MANA")-10);
                Compute.ManaStatusUpdate(player);
                data.putBoolean("BlackForestSword4",false);
                data.putBoolean("BlackForestSword3",false);
                data.putBoolean("BlackForestSword0",false);
                if(Tool instanceof BlackForestSword4) data.putBoolean("BlackForestSword4",true);
                else if(Tool instanceof BlackForestSword3) data.putBoolean("BlackForestSword3",true);
                else data.putBoolean("BlackForestSword0",true);
                player.addEffect(new MobEffectInstance(ModEffects.BLACKFORESTSWORD.get(),400));
            }
        }
        else if(Tool instanceof MagmaPower) {
            if(data.getInt("MANA") < 40) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Level level = player.level();
                player.getCooldowns().addCooldown(Moditems.MagmaPower.get(), (int) (200-200*Compute.PlayerCoolDownDecrease(player)));
                data.putInt("MANA",data.getInt("MANA")-40);
                int TickCount = player.getServer().getTickCount();
                if (data.contains("NetherRune") && data.getInt("NetherRune") == 2) data.putInt(StringUtils.NetherRune.NetherRune2,TickCount + 100);
                Compute.ManaStatusUpdate(player);
                data.putBoolean("MagmaPower",true);
            }
        }
        else if(Tool instanceof KazeSword0 || Tool instanceof KazeSword1 || Tool instanceof KazeSword2 || Tool instanceof KazeSword3 || Tool instanceof KazeSword4) {
            if(data.getInt("MANA") < 15) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else {
                int Rate = 0;
                Level level = player.level();
                if (Tool instanceof KazeSword0) Rate = 2;
                else if (Tool instanceof KazeSword1) Rate = 3;
                else if (Tool instanceof KazeSword2) Rate = 4;
                else if (Tool instanceof KazeSword3) Rate = 5;
                else if (Tool instanceof KazeSword4) Rate = 10;
                data.putInt("MANA",data.getInt("MANA") - 15);
                Compute.ManaStatusUpdate(player);
                List<Mob> MobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.getPosition(1.0f),15,15,15));
                List<Player> PlayerList1 = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.getPosition(1.0f),15,15,15));
                List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
                boolean InSkyFlag = false;
                for (Mob mob : MobList1) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                        if (IsOnSky(mob) && !mob.isInWater()) {
                            InSkyFlag = true;
                            break;
                        }
                    }
                }
                if (!InSkyFlag) {
                    for (Player player1 : PlayerList1) {
                        if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                            if (IsOnSky(player1) && !player1.isInWater()&& player1 != player) {
                                InSkyFlag = true;
                                break;
                            }
                        }
                    }
                }

                Vec3 FaceVec = player.pick(1,0,false).getLocation();
                if (InSkyFlag) {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                            ParticleTypes.SWEEP_ATTACK, true, FaceVec.x, FaceVec.y, FaceVec.z,
                            0,0,0,0,0
                    );
                    ServerPlayer serverPlayer1 = (ServerPlayer) player;
                    serverPlayer1.connection.send(clientboundLevelParticlesPacket);
                    ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_KNOCKBACK),
                            SoundSource.PLAYERS,player.getX(),player.getY(),player.getZ(),0.4f,0.4f,0);
                    serverPlayer1.connection.send(clientboundSoundPacket);
                    float MobDamageCount = 0;
                    for (Mob mob : MobList1) {
                        if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                            if (IsOnSky(mob) && !mob.isInWater()) {
                                mob.setDeltaMovement(0,-1,0);
                                Compute.EntityEffectVerticleCircleParticle(mob,1,1,16, ParticleTypes.ENCHANTED_HIT,0);
                                Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.75,16, ParticleTypes.ENCHANTED_HIT,0);
                                Compute.EntityEffectVerticleCircleParticle(mob,0,0.75,16, ParticleTypes.ENCHANTED_HIT,0);
                                MobDamageCount += Compute.DamageToMonster(player,mob,Rate);
                            }
                        }
                    }
                    float PlayerDamageCount = 0;
                    for (Player player1 : PlayerList1) {
                        if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                            if (IsOnSky(player1) && !player1.isInWater() && player1 != player) {
                                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(),new Vec3(0,1,0));
                                for (ServerPlayer serverPlayer : playerList) {
                                    serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                                }
                                Compute.EntityEffectVerticleCircleParticle(player1,1,1,16, ParticleTypes.ENCHANTED_HIT,0);
                                Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.75,16, ParticleTypes.ENCHANTED_HIT,0);
                                Compute.EntityEffectVerticleCircleParticle(player1,0,0.75,16, ParticleTypes.ENCHANTED_HIT,0);
                                PlayerDamageCount += Compute.DamageToPlayer(player,player1,Rate);
                            }
                        }
                    }
                    player.getCooldowns().addCooldown(Moditems.KazeSword0.get(), (int) (60 * (1 - Compute.PlayerCoolDownDecrease(player))));
                    player.getCooldowns().addCooldown(Moditems.KazeSword1.get(), (int) (60 * (1 - Compute.PlayerCoolDownDecrease(player))));
                    player.getCooldowns().addCooldown(Moditems.KazeSword2.get(), (int) (60 * (1 - Compute.PlayerCoolDownDecrease(player))));
                    player.getCooldowns().addCooldown(Moditems.KazeSword3.get(), (int) (60 * (1 - Compute.PlayerCoolDownDecrease(player))));
                    player.getCooldowns().addCooldown(Moditems.KazeSword4.get(), (int) (60 * (1 - Compute.PlayerCoolDownDecrease(player))));

                    if (MobDamageCount > 0) {
                        if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player,Component.literal("战斗").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                                Component.literal("狂风绝息").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze).
                                        append(Component.literal("对怪物造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",MobDamageCount)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze)).
                                        append(Component.literal("伤害值。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (PlayerDamageCount > 0) {
                        if(!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight"))) Compute.FormatMSGSend(player,Component.literal("战斗").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                                Component.literal("狂风绝息").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze).
                                        append(Component.literal("对玩家造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",PlayerDamageCount)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze)).
                                        append(Component.literal("伤害值。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                }
                else {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                            ParticleTypes.SWEEP_ATTACK,
                            true,
                            FaceVec.x,
                            FaceVec.y,
                            FaceVec.z,
                            0,0,0,0,0
                    );
                    ServerPlayer serverPlayer1 = (ServerPlayer) player;
                    serverPlayer1.connection.send(clientboundLevelParticlesPacket);
                    ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_SWEEP),
                            SoundSource.PLAYERS,player.getX(),player.getY(),player.getZ(),0.4f,0.4f,0);
                    serverPlayer1.connection.send(clientboundSoundPacket);
                    for (Mob mob : MobList1) {
                        if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                            mob.setDeltaMovement(0,1,0);
                            Compute.EntityEffectVerticleCircleParticle(mob,1,1,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                            Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                            Compute.EntityEffectVerticleCircleParticle(mob,0,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                        }
                    }
                    for (Player player1 : PlayerList1) {
                        if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                            if (player1 != player) {
                                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(),new Vec3(0,1,0));
                                for (ServerPlayer serverPlayer : playerList) {
                                    serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                                }
                                Compute.EntityEffectVerticleCircleParticle(player1,1,1,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                                Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                                Compute.EntityEffectVerticleCircleParticle(player1,0,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                            }
                        }
                    }
                    player.getCooldowns().addCooldown(Moditems.KazeSword0.get(), 16);
                    player.getCooldowns().addCooldown(Moditems.KazeSword1.get(), 16);
                    player.getCooldowns().addCooldown(Moditems.KazeSword2.get(), 16);
                    player.getCooldowns().addCooldown(Moditems.KazeSword3.get(), 16);
                    player.getCooldowns().addCooldown(Moditems.KazeSword4.get(), 14);
                }
            }
        }
        else if(Tool instanceof ForestBossSword) {
            if(data.getInt("MANA") < 60) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else {
                if (data.getInt(StringUtils.Entropy.Forest) > 0) {
                    Level level = player.level();
                    player.getCooldowns().addCooldown(Moditems.ForestBossSword.get(), (int) (200-200*Compute.PlayerCoolDownDecrease(player)));
                    Compute.PlayerManaAddOrCost(player,-60);
                    Compute.ManaStatusUpdate(player);

                    int TickCount = player.getServer().getTickCount();
                    List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
                    for (Mob mob : mobList) {
                        if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                            CompoundTag MobData = mob.getPersistentData();
                            MobData.putInt(StringUtils.ForestBossSwordActive.Pare,data.getInt(StringUtils.Entropy.Forest));
                            MobData.putInt(StringUtils.ForestBossSwordActive.PareTime,TickCount + 100);
                        }
                    }

                    List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
                    for (Player player1 : playerList) {
                        if (player1 != player && player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                            CompoundTag Player1Data = player1.getPersistentData();
                            Player1Data.putInt(StringUtils.ForestBossSwordActive.Pare,data.getInt(StringUtils.Entropy.Forest));
                            Player1Data.putInt(StringUtils.ForestBossSwordActive.PareTime,TickCount + 100);
                        }
                    }
                }
                else {
                    Compute.FormatMSGSend(player,Component.literal("次元能量").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth),
                            Component.literal("你的次元能量不足以召唤这个次元。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
            }
        }
        else if(Tool instanceof VolcanoBossSword) {
            if(data.getInt("MANA") < 60) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("快捷使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                if (data.getInt(StringUtils.Entropy.Volcano) > 0) {
                    Level level = player.level();
                    player.getCooldowns().addCooldown(Moditems.VolcanoBossSword.get(), (int) (200-200*Compute.PlayerCoolDownDecrease(player)));
                    Compute.PlayerManaAddOrCost(player,-60);
                    Compute.ManaStatusUpdate(player);

                    int TickCount = player.getServer().getTickCount();
                    List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
                    for (Mob mob : mobList) {
                        if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                            Compute.DamageToMonster(player,mob,data.getInt(StringUtils.Entropy.Volcano));
                            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2));
                        }
                    }
                    List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
                    for (Player player1 : playerList) {
                        if (player1 != player && player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                            Compute.DamageToPlayer(player,player1,data.getInt(StringUtils.Entropy.Volcano));
                            player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2));
                        }
                    }
                }
                else {
                    Compute.FormatMSGSend(player,Component.literal("次元能量").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower),
                            Component.literal("你的次元能量不足以召唤这个次元。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
            }
        }
    }
    public static void FormatBroad(Level level, Component type, Component content)
    {
        List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
        Iterator<ServerPlayer> iterator = playerList.iterator();
        while(iterator.hasNext())
        {
            ServerPlayer player = iterator.next();
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                    append(content));
        }
    }
    public static void FormatMSGSend(Player player, Component type, Component content)
    {
        if(player != null)
        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(content));
    }
    public static void ManaDamageToMonster(Player player, Mob monster, float num)
    {
        float Defence = Compute.MonsterManaDefence(monster);
        float BaseDamage = Compute.PlayerManaDamage(player);
        float damage = 0;
        float BreakDefence = Compute.PlayerManaBreakDefence(player);
        if(Defence == 0)
        {
            if(BreakDefence > monster.getAttribute(Attributes.ARMOR).getValue()) damage = BaseDamage;
            else damage = BaseDamage*(1.0F-(0.25F*(float)log(((monster.getAttribute(Attributes.ARMOR).getValue()*(1.0F-BreakDefence)*(E*E/250)+1)))));
            monster.hurt(monster.damageSources().playerAttack(player),damage*num);
        }
        else
        {
            if(BreakDefence >= Defence) damage = BaseDamage;
            else damage = BaseDamage*(1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/250)+1)));
            monster.hurt(monster.damageSources().playerAttack(player),damage*num);
        }
    }
    public static void ManaDamageToPlayer(Player player, Player hurter, float num)
    {
        float damage = 0;
        float BaseDamage = Compute.PlayerManaDamage(player);
        float BreakDefence = Compute.PlayerManaBreakDefence(player);
        float Defence = Compute.PlayerManaDefence(hurter);
        if(Defence == 0)
        {
            damage = BaseDamage*(1.0F-(0.25F*(float)log(((hurter.getAttribute(Attributes.ARMOR).getValue()*(1.0F-BreakDefence)*(E*E/250)+1)))));
            hurter.hurt(hurter.damageSources().playerAttack(player),damage*0.1f*num);
        }
        else
        {
            damage = BaseDamage*(1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/250)+1)));
            hurter.hurt(hurter.damageSources().playerAttack(player),damage*0.1f*num);
        }
    }
    public static float MonsterDamageDecrease(Monster monster)
    {
        float Decrease = 0;
        CompoundTag data = monster.getPersistentData();
        if(data.contains("witherBonePower") && data.getInt("witherBonePower") > 0) Decrease += 0.2f;
        return Decrease;
    }
    public static float WaltzPlayer(Vec3 vec3, Player player, Player hurter)
    {
        float ExDamage = 0;
        float AttackDamage = Compute.PlayerAttackDamage(player);
        float MaxHealth = hurter.getMaxHealth();
        float num = AttackDamage*0.05f*0.01f;
        boolean flag = false;
        if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) >  abs(vec3.z)) // 西
                flag = true;
            if(flag)
            {
                ExDamage += MaxHealth * num;
                hurter.getPersistentData().putInt("QuartzSabrePos",-1);
            }
        }
        return ExDamage;
    }
    public static float WaltzMonster(Vec3 vec3, Player player, Monster hurter)
    {
        float ExDamage = 0;
        float AttackDamage = Compute.PlayerAttackDamage(player);
        float MaxHealth = hurter.getMaxHealth();
        float num = AttackDamage*0.05f*0.01f;
        boolean flag = false;
        if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) >  abs(vec3.z)) // 西
                flag = true;
            if(flag)
            {
                ExDamage += MaxHealth * num;
                hurter.getPersistentData().putInt("QuartzSabrePos",-1);
            }
        }
        return ExDamage;
    }
    public static float WaltzPlayerBefore(Vec3 vec3, Player player, Player hurter)
    {
        float ExDamage = 0;
        float AttackDamage = Compute.PlayerAttackDamage(player);
        float MaxHealth = hurter.getMaxHealth();
        float num = AttackDamage*0.05f*0.01f;
        boolean flag = false;
        if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) >  abs(vec3.z)) // 西
                flag = true;
            if(flag)
            {
                ExDamage += MaxHealth * num;
            }
        }
        return ExDamage;
    }
    public static float WaltzMonsterBefore(Vec3 vec3, Player player, Mob hurter)
    {
        float ExDamage = 0;
        float AttackDamage = Compute.PlayerAttackDamage(player);
        float MaxHealth = hurter.getMaxHealth();
        float num = AttackDamage*0.05f*0.01f;
        boolean flag = false;
        if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if(hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) >  abs(vec3.z)) // 西
                flag = true;
            if(flag)
            {
                ExDamage += MaxHealth * num;
            }
        }
        return ExDamage;
    }
    public static Component MaterialRequirement(int index, Component materialType, int num)
    {
        return Component.literal(" "+index+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(materialType).
                append(Component.literal("*"+num).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

    }
    public static int ItemStackCount(Inventory inventory, Item item)
    {
        int ExistNum = 0;
        for(int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack.is(item)) ExistNum += itemStack.getCount();
        }
        return ExistNum;
    }
    public static boolean ItemStackCheck(Inventory inventory, Item item, int RequirementNum)
    {
        if(ItemStackCount(inventory,item) < RequirementNum) return false;
        else return true;
    }
    public static boolean ItemStackRemove(Inventory inventory, Item item, int RemoveNum)
    {
        int num = RemoveNum;
        if(!ItemStackCheck(inventory,item,RemoveNum)) return false;
        else
        {
            for(int i = 0; i < inventory.getContainerSize(); i++)
            {
                if(inventory.getItem(i).is(item))
                {
                    ItemStack itemStack = inventory.getItem(i);
                    if(itemStack.getCount() < num)
                    {
                        num -= itemStack.getCount();
                        itemStack.setCount(0);
                        inventory.setItem(i,itemStack);
                    }
                    else
                    {
                        itemStack.setCount(itemStack.getCount()-num);
                        inventory.setItem(i,itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void DailyMissionGive(Player player)
    {
        ItemStack itemStack = Moditems.SignInReset.get().getDefaultInstance();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random r = new Random();
        if(r.nextInt(100) < 50) data.putInt("DailyMission1",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission2",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission3",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission4",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission5",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission6",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission7",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission8",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission9",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission10",r.nextInt(16,80));
        if(r.nextInt(100) < 50) data.putInt("DailyMission11",r.nextInt(16,80));
    }
    public static int NeedExpForLevelUpOriginal(int XpLevel)
    {
        if(XpLevel <= 15) return 2*XpLevel+7;
        else if(XpLevel <= 30) return 5*XpLevel-38;
        return 9*XpLevel-158;
    }
    public static void ComponentAddLevelReward(List<Component> components, int rate)
    {
        components.add(Component.literal("不断地探索，使你的阅历逐渐提高。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionExAttackDamage(components,rate);
        Compute.EmojiDescriptionManaAttackDamage(components,rate);
        Compute.EmojiDescriptionBreakDefence(components,0.005f * rate);
        Compute.EmojiDescriptionManaBreakDefence(components,0.005f * rate);
        Compute.EmojiDescriptionCritRate(components,0.005f * rate);
        Compute.EmojiDescriptionCritDamage(components,0.01f * rate);
        Compute.EmojiDescriptionHealSteal(components,0.0025f * rate);
        Compute.EmojiDescriptionManaRecover(components,0.1f * rate);
        Compute.EmojiDescriptionMovementSpeed(components,0.005f * rate);
        Compute.EmojiDescriptionMaxHealth(components, 10 * rate);
        Compute.EmojiDescriptionHealthRecover(components,0.1f * rate);
        Compute.EmojiDescriptionCoolDown(components,0.005f * rate);
        Compute.EmojiDescriptionExpUp(components, 0.01f * rate);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        if (rate != 12) {
            components.add(Component.literal(" 当你获得下一等阶附身符时").
                    append(Component.literal("("+(rate+1)*5+")").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
            components.add(Component.literal("右键可以将附身符化为一些补给物资。"));
        }
        else {
            components.add(Component.literal("你在维瑞阿契已经有些时日了，感谢你的陪伴！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        components.add(Component.literal("LevelReward").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
    }
    public static int ItemCheck(Player player,ItemStack itemStack)
    {
        Inventory inventory = player.getInventory();
        for(int i = 0; i < inventory.items.size(); i++)
        {
            if(!inventory.items.get(i).isEmpty() && inventory.getItem(i).is(itemStack.getItem())) return i;
        }
        return -1;
    }
    public static void Broad(Level level, Component component)
    {
        PlayerList list =  level.getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        ListIterator<ServerPlayer> iterator = list1.listIterator();
        while(iterator.hasNext())
        {
            Player TmpPlayer = iterator.next();
            if(!TmpPlayer.level().isClientSide) TmpPlayer.sendSystemMessage(component);
        }
    }
    public static void DescriptionDash(List<Component> components, ChatFormatting chatFormatting0, ChatFormatting chatFormatting1, ChatFormatting chatFormatting2)
    {
        components.add(Component.literal("<").withStyle(ChatFormatting.RESET).withStyle(chatFormatting0).withStyle(ChatFormatting.BOLD).
                append(Component.literal("————————————————————————").withStyle(ChatFormatting.RESET).withStyle(chatFormatting1)).
                append(Component.literal(">").withStyle(ChatFormatting.RESET).withStyle(chatFormatting2).withStyle(ChatFormatting.BOLD)));
    }
    public static void DescriptionDash(List<Component> components, ChatFormatting chatFormatting0, Style chatFormatting1, ChatFormatting chatFormatting2)
    {
        components.add(Component.literal("<").withStyle(ChatFormatting.RESET).withStyle(chatFormatting0).withStyle(ChatFormatting.BOLD).
                append(Component.literal("————————————————————————").withStyle(ChatFormatting.RESET).withStyle(chatFormatting1)).
                append(Component.literal(">").withStyle(ChatFormatting.RESET).withStyle(chatFormatting2).withStyle(ChatFormatting.BOLD)));
    }
    public static void DescriptionOfBasic(List<Component> components) {
        components.add(Component.literal("基础属性:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.UNDERLINE));
    }
    public static void DescriptionOfAddtion(List<Component> components) {
        components.add(Component.literal("额外属性:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.UNDERLINE));
    }
    public static void DescriptionNum(List<Component> components, String string, Component component, String string1)
    {
        components.add(Component.literal(string).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(component).append(string1).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
    }
    public static void RandomPotionBagProvider(Player player, int MaxNum, double Rate)
    {
        int Count = 0;
        Random random = new Random();
        ItemStack itemStack[] = new ItemStack[12];
        itemStack[0] = Moditems.AttackUp_PotionBag.get().getDefaultInstance();
        itemStack[1] = Moditems.BreakDefenceUp_PotionBag.get().getDefaultInstance();
        itemStack[2] = Moditems.CoolDownDecreaseUp_PotionBag.get().getDefaultInstance();
        itemStack[3] = Moditems.CritDamageUp_PotionBag.get().getDefaultInstance();
        itemStack[4] = Moditems.CritRateUp_PotionBag.get().getDefaultInstance();
        itemStack[5] = Moditems.DefenceUp_PotionBag.get().getDefaultInstance();
        itemStack[6] = Moditems.HealStealUp_PotionBag.get().getDefaultInstance();
        itemStack[7] = Moditems.ManaBreakDefenceUp_PotionBag.get().getDefaultInstance();
        itemStack[8] = Moditems.ManaDamageUp_PotionBag.get().getDefaultInstance();
        itemStack[9] = Moditems.ManaDefenceUp_PotionBag.get().getDefaultInstance();
        itemStack[10] = Moditems.ManaReplyUp_PotionBag.get().getDefaultInstance();
        itemStack[11] = Moditems.SpeedUp_PotionBag.get().getDefaultInstance();
        for(int i = 0; i < MaxNum; i++)
        {
            ItemStack TmpStack = itemStack[random.nextInt(12)];
            TmpStack.getOrCreateTagElement(Utils.MOD_ID);
            if(random.nextDouble(1) < Rate) TmpStack.setCount(random.nextInt(2,4));
            player.addItem(TmpStack);
        }
    }
    public static boolean BlockLimitContainBlockPos(BlockPos blockPos)
    {
        Queue <PlayerCallBack> CopyQueue = new LinkedList<>();
        CopyQueue.addAll(Utils.playerCallBackQueue);
        Iterator<PlayerCallBack> iterator = CopyQueue.iterator();
        while(iterator.hasNext())
        {
            PlayerCallBack PerCallBack = iterator.next();
            if(PerCallBack.getBlockPos().equals(blockPos)) return true;
        }
        return false;
    }
    public static int BrewingLevel(ItemStack itemStack)
    {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        String[] DataName = {
                "PlainBrewingExp",
                "ForestBrewingExp",
                "LakeBrewingExp",
                "VolcanoBrewingExp",
                "SnowBrewingExp",
                "SkyBrewingExp",
                "EvokerBrewingExp",
                "NetherBrewingExp"
        };
        boolean flag = true;
        for(int i = 0; i < 8; i++) {
            if(data.getInt(DataName[i]) <= 1000) flag = false;
        }
        if(flag) return 6;

        flag = true;
        for(int i = 0; i < 8; i++) {
            if(data.getInt(DataName[i]) <= 500) flag = false;
        }
        if(flag) return 5;

        int Count = 0;
        for(int i = 0; i < 8; i++) {
            if(data.getInt(DataName[i]) >= 200) Count++;
        }
        if(Count >= 6) return 4;

        Count = 0;
        for(int i = 0; i < 8; i++) {
            if(data.getInt(DataName[i]) >= 100) Count++;
        }
        if(Count >= 5) return 3;

        Count = 0;
        for(int i = 0; i < 8; i++) {
            if(data.getInt(DataName[i]) >= 50) Count++;
        }
        if(Count >= 5) return 2;

        Count = 0;
        for(int i = 0; i < 8; i++) {
            if(data.getInt(DataName[i]) >= 20) Count++;
        }
        if(Count >= 4) return 1;
        return 0;
    }
    public static boolean BrewingLevelReward(Player player,int Level, CompoundTag data)
    {
        Random random = new Random();
        double[] LevelRate = {0, 0.05, 0.1, 0.15, 0.2, 0.4, 0.6};
        float ExpUp = Compute.ExpGetImprove(player);
        if(random.nextDouble(1) < LevelRate[Level]){
            if(data.contains("Owner"))
            {
                Compute.FormatMSGSend(player,Component.literal("酿造").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew),Component.literal("你的酿造经验为你节省了这次酿造的材料消耗并为你提供了经验值。"));
                Compute.FormatBroad(player.level(),Component.literal("酿造").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew),
                        Component.literal(player.getName().getString()+"完成了一次完美酿造！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                Compute.ExpPercentGetAndMSGSend(player,0.02,ExpUp,45);
            }
            return true;
        }
        return false;
    }
    public static void TimeGive(String string, Player player)
    {
        Calendar calendar = Calendar.getInstance();
        int StringInt = Integer.parseInt(string);
        int timeCode = calendar.get(Calendar.YEAR) *
                calendar.get(Calendar.MONTH) *
                calendar.get(Calendar.DATE) +
                calendar.get(Calendar.MONTH) *
                        calendar.get(Calendar.DATE) +
                        calendar.get(Calendar.DATE);
        int timeCode0 = calendar.get(Calendar.HOUR) *
                calendar.get(Calendar.YEAR) *
                calendar.get(Calendar.MONTH) *
                calendar.get(Calendar.DATE) + timeCode;
        if(StringInt == timeCode) {
            Utils.GemsForPlain = false;
            player.sendSystemMessage(Component.literal("~1"));
        }
        if(StringInt == timeCode0) {
            Utils.GemsForForest = false;
            player.sendSystemMessage(Component.literal("~2"));
        }
    }
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    public static void ItemStackGive(Player player, ItemStack itemStack)
    {
        Inventory inventory = player.getInventory();
        if(inventory.getFreeSlot() != -1){
            player.addItem(itemStack);
        }
        else{
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM,player.level());
            itemEntity.setItem(itemStack);
            itemEntity.moveTo(player.getPosition(1.0f));
            player.level().addFreshEntity(itemEntity);
            FormatMSGSend(player,Component.literal("物品").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                    Component.literal("背包已无空位，请注意。"));
        }
    }

    public static void AttributeProvider(Player player, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataI = stack.getOrCreateTagElement(Utils.MOD_ID);
        for(int i = 0; i < Utils.AttributeName.length; i++){
            if(dataI.contains(Utils.AttributeName[i])){
                if(data.contains(Utils.AttributeName[i])){
                    data.putDouble(Utils.AttributeName[i],data.getDouble(Utils.AttributeName[i])+dataI.getDouble(Utils.AttributeName[i]));
                }
                else data.putDouble(Utils.AttributeName[i],dataI.getDouble(Utils.AttributeName[i]));
            }
        }
    }

    public static void AttributeDecrease(Player player, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataI = stack.getOrCreateTagElement(Utils.MOD_ID);
        for(int i = 0; i < Utils.AttributeName.length; i++){
            if(dataI.contains(Utils.AttributeName[i])){
                if(data.contains(Utils.AttributeName[i])){
                    data.putDouble(Utils.AttributeName[i],data.getDouble(Utils.AttributeName[i])-dataI.getDouble(Utils.AttributeName[i]));
                }
            }
        }
    }
    public static void RandomAttributeProvider(ItemStack stack, int num, double level, Player player){
        float LuckyUp = Compute.PlayerLuckyUp(player);
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        Random r = new Random();
        if(Utils.AttributeMap.isEmpty()) Utils.AttributeMapInit();
        for(int i = 0; i < num; i++){
            String Attribute = Utils.AttributeName[r.nextInt(Utils.AttributeName.length)];
            if(data.contains(Attribute)){
                i--;
                continue;
            }
            double BaseValue = Utils.AttributeMap.get(Attribute);
            data.putDouble(Attribute,BaseValue*r.nextDouble(0.1 * (1 + LuckyUp),level));
        }
    }
    public static void VBgetAndMSGSend(Player player, float num){
        CompoundTag data = player.getPersistentData();
        if(!data.contains("VB")) data.putFloat("VB",num);
        else data.putFloat("VB",data.getFloat("VB")+num);
        FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                Component.literal("你的账户收入:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Component.literal(String.format("%.1f",num)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)).
                        append(Component.literal("VB,").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("当前余额:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.format("%.1f",data.getFloat("VB"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
    }
    public static void VBputAndMSGSend(Player player, float num){
        CompoundTag data = player.getPersistentData();
        data.putFloat("VB",data.getFloat("VB")-num);
        FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                Component.literal("你的账户支出:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Component.literal(String.format("%.1f",num)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                        append(Component.literal("VB,").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("当前余额:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.format("%.1f",data.getFloat("VB"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
    }
    public static void FaceCircleCreate(ServerPlayer serverPlayer, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        Vec3 PickVec = serverPlayer.pick(10,0,true).getLocation();
        Vec3 FaceVec = serverPlayer.pick(pickDistance,0,true).getLocation();
        Vec3 nVec = PickVec.subtract(FaceVec);
        Vec3 iVec = new Vec3(1,0,0);
        Vec3 jVec = new Vec3(0,1,0);
        Vec3 kVec = new Vec3(0,0,1);
        Vec3 aVec;
        if (nVec.cross(iVec).length() == 0) {
            aVec = nVec.cross(jVec);
        }
        else aVec = nVec.cross(iVec);
        aVec = aVec.normalize();
        Vec3 bVec = nVec.cross(aVec).normalize();
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i);
            Vec3 Point = new Vec3(FaceVec.x+r*Math.cos(angle)*aVec.x+r*Math.sin(angle)*bVec.x,
                    FaceVec.y+r*Math.cos(angle)*aVec.y+r*Math.sin(angle)*bVec.y,
                    FaceVec.z+r*Math.cos(angle)*aVec.z+r*Math.sin(angle)*bVec.z);
            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions,
                    true,
                    Point.x,
                    Point.y,
                    Point.z,
                    0,
                    0,
                    0,
                    0,
                    0
            );
            List<ServerPlayer> list = serverPlayer.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : list) {
                serverPlayer1.connection.send(particlesPacket);
            }
        }
    }
    public static void EntityFaceCircleCreate(Entity entity, Vec3 Position, Vec3 Location, double pickDistance, double r, int num, ParticleOptions particleOptions, boolean flag) {
        Vec3 nVec = Position;
        Vec3 iVec = new Vec3(1,0,0);
        Vec3 jVec = new Vec3(0,1,0);
        Vec3 kVec = new Vec3(0,0,1);
        Vec3 aVec;
        if (nVec.cross(iVec).length() == 0) {
            aVec = nVec.cross(jVec);
        }
        else aVec = nVec.cross(iVec);
        aVec = aVec.normalize();
        Vec3 bVec = nVec.cross(aVec).normalize();
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i);
            Vec3 Point = new Vec3(Location.x+r*Math.cos(angle)*aVec.x+r*Math.sin(angle)*bVec.x,
                    Location.y+r*Math.cos(angle)*aVec.y+r*Math.sin(angle)*bVec.y,
                    Location.z+r*Math.cos(angle)*aVec.z+r*Math.sin(angle)*bVec.z);
            if (flag) {
                List<ServerPlayer> list = entity.level().getServer().getPlayerList().getPlayers();
                for (ServerPlayer serverPlayer : list) {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                            particleOptions,
                            true,
                            Point.x,
                            Point.y,
                            Point.z,
                            0,0,0,0,0
                    );
                    serverPlayer.connection.send(clientboundLevelParticlesPacket);
                }
            }
            else {
                entity.level().addParticle(particleOptions,Point.x,Point.y,Point.z,0,0,0);
            }
        }
    }
    public static void EntityFaceConnectCircleCreate(Entity entity, Vec3 Position, Vec3 Location, double pickDistance, double r, int num, ParticleOptions particleOptions, double DX, double DY, double DZ, double Start) {
        Vec3 nVec = Position;
        Vec3 iVec = new Vec3(1,0,0);
        Vec3 jVec = new Vec3(0,1,0);
        Vec3 kVec = new Vec3(0,0,1);
        Vec3 aVec;
        if (nVec.cross(iVec).length() == 0) {
            aVec = nVec.cross(jVec);
        }
        else aVec = nVec.cross(iVec);
        aVec = aVec.normalize();
        Vec3 bVec = nVec.cross(aVec).normalize();
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i) + Start * (PI);
            Vec3 Point = new Vec3(Location.x+r*Math.cos(angle)*aVec.x+r*Math.sin(angle)*bVec.x,
                    Location.y+r*Math.cos(angle)*aVec.y+r*Math.sin(angle)*bVec.y,
                    Location.z+r*Math.cos(angle)*aVec.z+r*Math.sin(angle)*bVec.z);
            double rate = i * 1.0 / num * 1.0;
            entity.level().addParticle(particleOptions,Point.x + DX * rate,Point.y + DY * rate,Point.z + DZ * rate,0,0,0);
            List<ServerPlayer> list = entity.level().getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer : list) {
                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                        particleOptions,
                        true,
                        Point.x + DX * rate,
                        Point.y + DY * rate,
                        Point.z + DZ * rate,
                        0,0,0,0,0
                );
                serverPlayer.connection.send(clientboundLevelParticlesPacket);
            }
        }
    }
    public static void VerticleCircleParticle(ServerPlayer serverPlayer, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        Vec3 bottomPos = new Vec3(serverPlayer.getX(),serverPlayer.getY(),serverPlayer.getZ());
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i);
            Vec3 Point = new Vec3(bottomPos.x+r*cos(angle),bottomPos.y+pickDistance,bottomPos.z+r*sin(angle));
            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions,
                    true,
                    Point.x,
                    Point.y,
                    Point.z,
                    0,
                    0,
                    0,
                    0,
                    0
            );
            List<ServerPlayer> list = serverPlayer.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : list) {
                serverPlayer1.connection.send(particlesPacket);
            }
        }
    }
    public static void RandomMoveParticle(Entity entity, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        Vec3 bottomPos = new Vec3(entity.getX(),entity.getY(),entity.getZ());
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i);
            Vec3 Point = new Vec3(bottomPos.x+r*cos(angle),bottomPos.y+pickDistance,bottomPos.z+r*sin(angle));
            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions,
                    true,
                    Point.x,
                    Point.y,
                    Point.z,
                    1,
                    1,
                    1,
                    1,
                    1
            );
            List<ServerPlayer> list = entity.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : list) {
                serverPlayer1.connection.send(particlesPacket);
            }
        }
    }
    public static void EntityEffectVerticleCircleParticle(Entity entity, double pickDistance, double r, int num, ParticleOptions particleOptions, int tick) {
        Vec3 bottomPos = new Vec3(entity.getX(),entity.getY(),entity.getZ());
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i);
            Vec3 Point = new Vec3(bottomPos.x+r*cos(angle),bottomPos.y+pickDistance,bottomPos.z+r*sin(angle));
            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions,
                    true,
                    Point.x,
                    Point.y,
                    Point.z,
                    0,
                    0,
                    0,
                    0,
                    tick
            );
            List<ServerPlayer> list = entity.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : list) {
                serverPlayer1.connection.send(particlesPacket);
            }
        }
    }
    public static void EntityEffectVerticleCircleParticle(Entity entity, double pickDistance, double r, int num, ParticleOptions particleOptions, int tick, boolean flag) {
        Vec3 bottomPos = new Vec3(entity.getX(),entity.getY(),entity.getZ());
        for (int i = 0; i < num; i++) {
            double angle = (2*Math.PI/num)*(i);
            Vec3 Point = new Vec3(bottomPos.x+r*cos(angle),bottomPos.y+pickDistance,bottomPos.z+r*sin(angle));
            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions,
                    true,
                    Point.x,
                    Point.y,
                    Point.z,
                    0,
                    0,
                    0,
                    0,
                    tick
            );
            if (flag) {
                List<ServerPlayer> list = entity.getServer().getPlayerList().getPlayers();
                for (ServerPlayer serverPlayer1 : list) {
                    serverPlayer1.connection.send(particlesPacket);
                }
            }
            else entity.level().addParticle(particleOptions, Point.x, Point.y, Point.z,0,0,0);
        }
    }
    public static void PlayerPowerParticle(Player player) {
        Compute.EntityEffectVerticleCircleParticle(player,1.5,0.4,8,ParticleTypes.WITCH,0);
        Compute.EntityEffectVerticleCircleParticle(player,1.25,0.4,8,ParticleTypes.WITCH,0);
        Compute.EntityEffectVerticleCircleParticle(player,1,0.4,8,ParticleTypes.WITCH,0);
        Compute.EntityEffectVerticleCircleParticle(player,0.75,0.4,8,ParticleTypes.WITCH,0);
        Compute.EntityEffectVerticleCircleParticle(player,0.5,0.4,8,ParticleTypes.WITCH,0);
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ParticleTypes.WITCH,true,
                player.getX(),
                player.getY(),
                player.getZ(),
                0.5f,
                0.5f,
                0.5f,
                1,
                0);
        List<ServerPlayer> list = player.getServer().getPlayerList().getPlayers();
        for (ServerPlayer serverPlayer1 : list) {
            serverPlayer1.connection.send(clientboundLevelParticlesPacket);
        }
    }
    public static void LineParticle(Level level, int num, Vec3 startVec, Vec3 endVec, ParticleOptions particleOptions) {
        Vec3 vec = endVec.subtract(startVec);
        for (int i = 0; i < num; i++) {
            Vec3 pointVec = new Vec3(startVec.x + vec.x * (i*1.0/num*1.0), startVec.y + vec.y * (i*1.0/num*1.0), startVec.z + vec.z * (i*1.0/num*1.0));
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(particleOptions,true,
                    pointVec.x,
                    pointVec.y,
                    pointVec.z,
                    0f,
                    0f,
                    0f,
                    1,
                    0);
            List<ServerPlayer> list = level.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : list) {
                serverPlayer1.connection.send(clientboundLevelParticlesPacket);
            }
        }
    }
    public static void MagmaPower(Entity entity, Level level, Player player) {
        List<ServerPlayer> playerList0 = entity.getServer().getPlayerList().getPlayers();
        for (ServerPlayer serverPlayer : playerList0) {
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.EXPLOSION_EMITTER,true,entity.getX(),entity.getY(),entity.getZ(),0,0,0,0,1);
            serverPlayer.connection.send(clientboundLevelParticlesPacket);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.GENERIC_EXPLODE), SoundSource.PLAYERS, entity.getX(),entity.getY(),entity.getZ(), 1f, 1f,0);
            serverPlayer.connection.send(clientboundSoundPacket);
        }
        RandomMoveParticle(entity,1,1,24,ParticleTypes.ASH);
        RandomMoveParticle(entity,1,1,24,ParticleTypes.LAVA);
        List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(entity.getPosition(1.0f),10,10,10));
        for (Mob mob : list) {
            Compute.ManaDamageToMonster(player,mob,3);
            Compute.EntityEffectVerticleCircleParticle(mob,1,0.4,8, ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0.75,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0.25,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0,0.4,8,ParticleTypes.WITCH,0);
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,60,2));
        }
        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(entity.getPosition(1.0f),10,10,10));
        for (Player player1 : playerList) {
            if (player1 != player) {
                Compute.ManaDamageToPlayer(player,player1,3);
                Compute.EntityEffectVerticleCircleParticle(player1,1,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.75,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.25,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0,0.4,8,ParticleTypes.WITCH,0);
                player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,60,2));
            }
        }
    }
    public static float DamageToMonster(Player player, Mob monster, float num)
    {
        float damage = 0;
        float Defence = Compute.MonsterDefence(monster);
        float BaseDamage = Compute.PlayerAttackDamage(player);
        float BreakDefence = Compute.PlayerBreakDefence(player);
        float BreakDefence0 = Compute.PlayerBreakDefence0(player);
        float DamageEnhance = 0;
        CompoundTag data = player.getPersistentData();
        DamageEnhance += Compute.SwordSkillLevelGet(data,4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）
        if (BreakDefence0 >= Defence) damage = BaseDamage;
        else damage = BaseDamage * DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
        monster.hurt(monster.damageSources().playerAttack(player),damage * num);
        return damage * num;
    }
    public static float DamageToPlayer(Player player, Player hurter, float num)
    {
        float damage = 0;
        float Defence = Compute.PlayerDefence(hurter);
        float BaseDamage = Compute.PlayerAttackDamage(player);
        float BreakDefence = Compute.PlayerBreakDefence(player);
        float BreakDefence0 = Compute.PlayerBreakDefence0(player);
        float DamageEnhance = 0;
        CompoundTag data = player.getPersistentData();
        DamageEnhance += Compute.SwordSkillLevelGet(data,4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）
        if (BreakDefence0 >= Defence) damage = BaseDamage;
        else damage = BaseDamage * DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
        hurter.hurt(hurter.damageSources().playerAttack(player),damage * num * 0.1f);
        return damage * num;
    }
    public static boolean DotIn(Vec2 dot, Vec2 dot1, Vec2 dot2, Vec2 dot3, Vec2 dot4) {
/*        Vec2 vec1 = Vec2Sub(dot1,dot2);
        Vec2 vec2 = Vec2Sub(dot2,dot3);
        Vec2 vec3 = Vec2Sub(dot3,dot4);
        Vec2 vec4 = Vec2Sub(dot4,dot1);
        if ((Vec2Cross(vec1,Vec2Sub(dot1,dot)) > 0 && Vec2Cross(vec2,Vec2Sub(dot2,dot)) > 0
                && Vec2Cross(vec3,Vec2Sub(dot3,dot)) > 0 && Vec2Cross(vec4,Vec2Sub(dot4,dot)) > 0 ) ||
                (Vec2Cross(vec1,Vec2Sub(dot1,dot)) < 0 && Vec2Cross(vec2,Vec2Sub(dot2,dot)) < 0
                        && Vec2Cross(vec3,Vec2Sub(dot3,dot)) < 0 && Vec2Cross(vec4,Vec2Sub(dot4,dot)) < 0 ) ) {
            return true;
        }
        return false;*/
       float a = (dot2.x - dot1.x) * (dot.y - dot1.y) - (dot2.y - dot1.y) * (dot.x - dot1.x);
       float b = (dot3.x - dot2.x) * (dot.y - dot2.y) - (dot3.y - dot2.y) * (dot.x - dot2.x);
       float c = (dot4.x - dot3.x) * (dot.y - dot3.y) - (dot4.y - dot3.y) * (dot.x - dot3.x);
       float d = (dot1.x - dot4.x) * (dot.y - dot4.y) - (dot1.y - dot4.y) * (dot.x - dot4.x);
       if ((a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0)) return true;
       return false;
    }
    public static float Vec2Cross(Vec2 vec1,Vec2 vec2) {
        return vec1.x * vec2.y - vec1.y * vec2.x;
    }
    public static Vec2 Vec2Sub(Vec2 vec1,Vec2 vec2) {
        Vec2 vec = new Vec2(vec2.x - vec1.x,vec2.y - vec1.x);
        return vec;
    }
    public static float SArmorMaxHealth(Player player) {
        float MaxHealthCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;
        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#"+i+"#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getFloat(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#"+i+"#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getFloat(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#"+i+"#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getFloat(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#"+i+"#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getFloat(IsSunPower) * 10 * Rate;
                }
            }
        }
        return MaxHealthCount * 2;
    }
    public static float SArmorCoolDown(Player player) {
        float CoolDown = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#"+i+"#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getFloat(IsLakePower) * Rate/2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(Moditems.SChest.get())|| equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#"+i+"#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getFloat(IsLakePower) * Rate/2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#"+i+"#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getFloat(IsLakePower) * Rate/2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#"+i+"#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getFloat(IsLakePower) * Rate/2.0;
                }
            }
        }
        return CoolDown * 0.01f * 1.5f;
    }
    public static float SArmorAttackDamage(Player player) {
        float AttackDamageCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;
        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#"+i+"#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getFloat(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#"+i+"#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getFloat(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#"+i+"#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getFloat(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#"+i+"#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getFloat(IsVolcanoPower) * Rate;
                }
            }
        }
        return AttackDamageCount * 1.5f;
    }
    public static float SArmorCritRate(Player player) {
        float CritRate = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#"+i+"#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getFloat(IsSkyPower) * Rate/2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#"+i+"#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getFloat(IsSkyPower) * Rate/2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#"+i+"#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getFloat(IsSkyPower) * Rate/2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#"+i+"#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getFloat(IsSkyPower) * Rate/2.0;
                }
            }
        }
        return CritRate * 0.01f * 1.5f;
    }
    public static float SArmorCritDamage(Player player) {
        float CritDamage = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#"+i+"#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getFloat(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#"+i+"#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getFloat(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#"+i+"#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getFloat(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#"+i+"#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getFloat(IsSnowPower) * Rate;
                }
            }
        }
        return CritDamage * 0.01f * 1.5f;
    }
    public static float SArmorManaDamage(Player player) {
        float ManaDamageCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#"+i+"#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getFloat(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#"+i+"#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getFloat(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#"+i+"#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getFloat(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#"+i+"#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getFloat(IsManaPower) * Rate;
                }
            }
        }
        return ManaDamageCount * 1.5f;
    }
    public static float SArmorHealSteal(Player player) {
        float HealSteal = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#"+i+"#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getFloat(IsNetherPower) * Rate/4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#"+i+"#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getFloat(IsNetherPower) * Rate/4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#"+i+"#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getFloat(IsNetherPower) * Rate/4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(Moditems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#"+i+"#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getFloat(IsNetherPower) * Rate/4.0;
                }
            }
        }
        return HealSteal * 0.01f * 1.5f;
    }
    public static int LightningArmorCount(Player player) {
        Item PlayerHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item PlayerChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item PlayerLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item PlayerBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        int LightningArmorCount = 0;
        if (PlayerHelmet instanceof LightningArmorHelmet) LightningArmorCount ++;
        if (PlayerChest instanceof LightningArmorChest) LightningArmorCount ++;
        if (PlayerLeggings instanceof LightningArmorLeggings) LightningArmorCount ++;
        if (PlayerBoots instanceof LightningArmorBoots) LightningArmorCount ++;
        if (PlayerHelmet instanceof iLightningArmorHelmet) LightningArmorCount += 2;
        if (PlayerChest instanceof iLightningArmorChest) LightningArmorCount += 2;
        if (PlayerLeggings instanceof iLightningArmorLeggings) LightningArmorCount += 2;
        if (PlayerBoots instanceof iLightningArmorBoots) LightningArmorCount += 2;
        return LightningArmorCount;
    }
    public static void CodeHitMonster(Level level, Mob monster, int Range, ServerPlayer player, float damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(monster.getPosition(1.0f),10 * Range,10 * Range,10 * Range));
        if (Range == 0 && !mobList.contains(monster)) mobList.add(monster);
        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(monster.getPosition(1.0f),10 * Range,10 * Range,10 * Range));
        for (Mob mob : mobList) {
            if (mob != monster) {
                mob.hurt(mob.damageSources().playerAttack(player),damage);
                Compute.EntityEffectVerticleCircleParticle(mob,1,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0.75,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0.25,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0,0.4,8,ParticleTypes.WITCH,0);
            }
            if (Kaze > 0) {
                mob.setDeltaMovement(0,Effect,0);
                Compute.EntityEffectVerticleCircleParticle(mob,1,1,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20 * Effect,99,false,false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(),(int) (mob.getY() + 0.9),(int) mob.getZ());

                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos,blockState);
                    player.level().destroyBlock(blockPos,false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.getPosition(1.0f));
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                com.Very.very.ValueAndTools.Utils.Struct.Gather gather = new Gather(20*Effect,monster.getPosition(1.0f));
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                }
                else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather,mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                player1.hurt(player1.damageSources().playerAttack(player),damage);
                Compute.EntityEffectVerticleCircleParticle(player1,1,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.75,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.25,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0,0.4,8,ParticleTypes.WITCH,0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(),new Vec3(0,Effect,0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    Compute.EntityEffectVerticleCircleParticle(player1,1,1,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20 * Effect,99,false,false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(),(int) (player1.getY() + 0.9),(int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos,blockState);
                        player.level().destroyBlock(blockPos,false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.getPosition(1.0f));
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20*Effect,monster.getPosition(1.0f));
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    }
                    else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather,mobQueue);
                    }
                }
            }
        }
    }
    public static void CodeHitPlayer(Level level, Player hurter, int Range, ServerPlayer player, float damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(hurter.getPosition(1.0f),10 * Range,10 * Range,10 * Range));
        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(hurter.getPosition(1.0f),10 * Range,10 * Range,10 * Range));
        if (Range == 0 && !playerList.contains(hurter)) playerList.add(hurter);
        for (Mob mob : mobList) {
            mob.hurt(mob.damageSources().playerAttack(player),damage);
            Compute.EntityEffectVerticleCircleParticle(mob,1,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0.75,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0.25,0.4,8,ParticleTypes.WITCH,0);
            Compute.EntityEffectVerticleCircleParticle(mob,0,0.4,8,ParticleTypes.WITCH,0);

            if (Kaze > 0) {
                mob.setDeltaMovement(0,Effect,0);
                Compute.EntityEffectVerticleCircleParticle(mob,1,1,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0.5,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                Compute.EntityEffectVerticleCircleParticle(mob,0,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20 * Effect,99,false,false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(),(int) (mob.getY() + 0.9),(int) mob.getZ());
                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos,blockState);
                    player.level().destroyBlock(blockPos,false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.getPosition(1.0f));
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                Gather gather = new Gather(20*Effect,hurter.getPosition(1.0f));
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                }
                else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather,mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                if(player1 != hurter) player1.hurt(player1.damageSources().playerAttack(player),damage);
                Compute.EntityEffectVerticleCircleParticle(player1,1,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.75,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0.25,0.4,8,ParticleTypes.WITCH,0);
                Compute.EntityEffectVerticleCircleParticle(player1,0,0.4,8,ParticleTypes.WITCH,0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(),new Vec3(0,Effect,0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    Compute.EntityEffectVerticleCircleParticle(player1,1,1,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0.5,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);
                    Compute.EntityEffectVerticleCircleParticle(player1,0,0.75,16, ParticleTypes.TOTEM_OF_UNDYING,0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20 * Effect,99,false,false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(),(int) (player1.getY() + 0.9),(int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos,blockState);
                        player.level().destroyBlock(blockPos,false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.getPosition(1.0f));
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20*Effect,hurter.getPosition(1.0f));
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    }
                    else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather,mobQueue);
                    }
                }
            }
        }
    }

    public static boolean RecallPlayerCheck(ServerPlayer serverPlayer) {
        if (Utils.KazeRecall.RecallPlayer != null && Utils.KazeRecall.RecallPlayer.equals(serverPlayer)) return true;
        if (Utils.SpiderRecall.RecallPlayer != null && Utils.SpiderRecall.RecallPlayer.equals(serverPlayer)) return true;
        return false;
    }

    public static double Vec3Angle(Vec3 VecA, Vec3 VecB) {
        return acos(abs(VecA.dot(VecB)) / (VecA.length() * VecB.length()));
    }
    public static void AttackJudge(Player player) {
        ItemStack MainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (MainHandItem.is(Moditems.volcanosword0.get()) || MainHandItem.is(Moditems.volcanosword1.get())
                || MainHandItem.is(Moditems.volcanosword2.get()) || MainHandItem.is(Moditems.VolcanoSword4.get())
                || MainHandItem.is(Moditems.ManaSword.get()) || MainHandItem.is(Moditems.QuartzSword.get())
                || MainHandItem.is(Moditems.QuartzSabre.get()) || MainHandItem.is(Moditems.SeaSword0.get())
                || MainHandItem.is(Moditems.SeaSword1.get()) || MainHandItem.is(Moditems.SeaSword2.get())
                || MainHandItem.is(Moditems.SeaSword3.get()) || MainHandItem.is(Moditems.SeaSword4.get())
                || MainHandItem.is(Moditems.KazeSword0.get()) || MainHandItem.is(Moditems.KazeSword1.get())
                || MainHandItem.is(Moditems.KazeSword2.get()) || MainHandItem.is(Moditems.KazeSword3.get())
                || MainHandItem.is(Moditems.KazeSword4.get())) {
            ModNetworking.sendToServer(new SwordAttackAnimationRequestC2SPacket(player.getId()));
            ClientUtils.RangeAttackCount = 10;
            ClientUtils.RangeAttackAnimationCount = 14;
        }
        if (MainHandItem.is(Moditems.minesword0.get()) || MainHandItem.is(Moditems.minesword1.get())
                || MainHandItem.is(Moditems.minesword2.get()) || MainHandItem.is(Moditems.minesword3.get())
                || MainHandItem.is(Moditems.snowsword0.get()) || MainHandItem.is(Moditems.snowsword1.get())
                || MainHandItem.is(Moditems.snowsword2.get()) || MainHandItem.is(Moditems.snowsword3.get())
                || MainHandItem.is(Moditems.snowsword4.get()) || MainHandItem.is(Moditems.ANIMATED_ITEM.get())) {
            ModNetworking.sendToServer(new PickAxeAttackAnimationRequestC2SPacket(player.getId()));
            ClientUtils.PickAxeAttackCount = 10;
            ClientUtils.PickAxeAttackAnimationCount = 14;
        }
    }
    public static void RateItemStackGive (ItemStack itemStack, double rate, Player player) throws IOException {
        Random r = new Random();
        float LuckyUp = PlayerLuckyUp(player);
        if(r.nextDouble(1.0F) < rate * (1 + LuckyUp)) {
            String LogContent = "获得:" + itemStack.getDisplayName().getString() + " *" + itemStack.getCount();
            FileHandler.WARQLogsWrite(player,StringUtils.LogsType.ItemGet,LogContent);
            ItemStackGive(player,itemStack);
        }
    }
    public static int SwordSkillLevelGet (CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Sword);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }
    public static int BowSkillLevelGet (CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Bow);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }
    public static int ManaSkillLevelGet (CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Mana);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }
    public static void ChargingModule (CompoundTag data, Player player) {
        if (Compute.SwordSkillLevelGet(data,12) > 0) ModNetworking.sendToClient(new SwordSkill12S2CPacket(8),(ServerPlayer) player);
        if (Compute.ManaSkillLevelGet(data,12) > 0) ModNetworking.sendToClient(new ManaSkill12S2CPacket(8),(ServerPlayer) player);
        if (Compute.ManaSkillLevelGet(data,13) > 0) ModNetworking.sendToClient(new ManaSkill13S2CPacket(8),(ServerPlayer) player);
        if (Compute.BowSkillLevelGet(data,12) > 0) ModNetworking.sendToClient(new BowSkill12S2CPacket(8),(ServerPlayer) player);
    }
    public static void SetMobCustomName (Mob mob, Item ArmorItem, Component component){
        int Level = Utils.MobLevel.get(ArmorItem).intValue();
        if (Level < 25) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)
                    .append(component));
        }
        else if (Level < 50) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)
                    .append(component));
        }
        else if (Level < 75) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)
                    .append(component));
        }
        else if (Level < 100) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)
                    .append(component));
        }
    }
    public static double ExtractRate (int ExtractLevel) {
        return Math.min(Math.min(0.1,ExtractLevel * 0.001)
                + Math.max(0,(ExtractLevel - 100) * 0.0002)
                + Math.max(0,(ExtractLevel - 1000) * 0.002)
                ,0.33);
    }
    public static float LevelSuppress (Player player, Mob monster) {
        int MobLevel = 0;
        Item MobHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (Utils.MobLevel.containsKey(MobHelmet)) MobLevel = Utils.MobLevel.get(MobHelmet).intValue();
        return (player.experienceLevel - MobLevel) / 100.0f;
    }
    public static float DefenceDamageDecreaseRate (float Defence, float BreakDefence, float BreakDefence0) {
        return (1.0F-(0.25F*(float)log((Math.max(1,(Defence - BreakDefence0) * (1 - BreakDefence)))*(E*E/250))));
    }
    public static void CoolDownTimeDescription (List<Component> components, int time) {
        components.add(Component.literal("冷却时间:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal(time + "s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
    }
    public static void CoolDownTimeDescription (List<Component> components, float time) {
        components.add(Component.literal("冷却时间:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.1f",time) + "s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
    }
    public static void EmojiDescriptionManaCost(List<Component> components, int num) {
        components.add(Component.literal("法力消耗:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal(num + "").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
    }
    public static ItemStack FoilAddItemStack (ItemStack itemStack) {
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
        map.put(Enchantments.UNBREAKING,1);
        EnchantmentHelper.setEnchantments(map,itemStack);
        return itemStack;
    }
    public static Boolean IsOnSky (LivingEntity entity) {
        int X = entity.getBlockX();
        int Y = entity.getBlockY();
        int Z = entity.getBlockZ();
        if (entity.level().getBlockState(new BlockPos(X,Y-2,Z)) != Blocks.AIR.defaultBlockState()
                && entity.level().getBlockState(new BlockPos(X,Y-1,Z)) != Blocks.AIR.defaultBlockState() ) {
            return false;
        }
        return true;
    }
    public static void EmojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionBreakDefence(List<Component> components, double BreakDefence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+"+String.format("%.0f%%",BreakDefence*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionBreakDefence0(List<Component> components, double BreakDefence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+"+String.format("%.0f",BreakDefence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionCritRate(List<Component> components, double CriticalHitRate) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+"+String.format("%.1f%%",CriticalHitRate*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+"+String.format("%.0f%%",CriticalHitDamage*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+"+String.format("%.0f%%",HealSteal*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionMovementSpeed(List<Component> components, double SpeedUp) {
        components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+"+String.format("%.0f%%",SpeedUp*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionAttackRange(List<Component> components, double AttackRangeUp) {
        components.add(Component.literal(Utils.Emoji.AttackRange + " 攻击距离").withStyle(Utils.styleOfSea).
                append(Component.literal("+"+String.format("%.2f",AttackRangeUp)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" "+String.format("%.0f",ManaDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionManaCost(List<Component> components, double ManaCost) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal("+"+String.format("%.0f",ManaCost)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionManaBreakDefence(List<Component> components, double ManaBreakDefence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+"+String.format("%.0f%%",ManaBreakDefence*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionManaBreakDefence0(List<Component> components, double ManaBreakDefence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+"+String.format("%.0f",ManaBreakDefence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionManaRecover(List<Component> components, double ManaReply) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+"+String.format("%.0f",ManaReply)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionMaxHealth(List<Component> components, double MaxHealth) {
        components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+"+String.format("%.0f",MaxHealth)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionHealAmplification(List<Component> components, double HealEffect) {
        components.add(Component.literal(Utils.Emoji.HealthAmplification + " 治疗强度").withStyle(Utils.styleOfHealth).
                append(Component.literal("+"+String.format("%.0f%%",HealEffect * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionExpUp(List<Component> components, double ExpUp) {
        components.add(Component.literal(Utils.Emoji.ExpUp + " 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+"+String.format("%.0f%%",ExpUp * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionLuckyUp(List<Component> components, double Lucky) {
        components.add(Component.literal(Utils.Emoji.Lucky + " 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+"+String.format("%.0f%%",Lucky * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionDefenceRate(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲加成").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+"+String.format("%.0f%%",Defence * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionExAttackDamageRate(List<Component> components, double ExDamageRate) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" "+String.format("%.0f%%",ExDamageRate * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionCoolDown(List<Component> components, double CoolDown) {
        components.add(Component.literal(Utils.Emoji.Sword + " 冷却缩减").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" "+String.format("%.0f%%",CoolDown * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 基础护甲").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+"+String.format("%.0f",Defence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionHealthRecover(List<Component> components, double HealthReplay) {
        components.add(Component.literal(Utils.Emoji.HealthRecover + " 生命回复").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+"+String.format("%.1f",HealthReplay)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionExAttackDamage(List<Component> components, double ExDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" "+String.format("%.0f",ExDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionManaDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法抗性").withStyle(ChatFormatting.BLUE).
                append(Component.literal(" "+String.format("%.0f",Defence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void EmojiDescriptionMaxMana(List<Component> components, double MaxMana) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+"+String.format("%.0f",MaxMana)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
    }
    public static void BasicSwordDescription (List<Component> components, float BaseDamage, float BreakDefence,
                                              float CriticalHitRate, float CriticalHitDamage, float HealSteal, float SpeedUp) {
        EmojiDescriptionBaseAttackDamage(components,BaseDamage);
        EmojiDescriptionBreakDefence(components,BreakDefence);
        EmojiDescriptionCritRate(components,CriticalHitRate);
        EmojiDescriptionCritDamage(components,CriticalHitDamage);
        EmojiDescriptionHealSteal(components,HealSteal);
        EmojiDescriptionMovementSpeed(components,SpeedUp);
    }
    public static void BasicSwordDescription (List<Component> components, float BaseDamage, float BreakDefence,
                                              float CriticalHitRate, float CriticalHitDamage, float HealSteal, float SpeedUp, float AttackRangeUp) {
        EmojiDescriptionBaseAttackDamage(components,BaseDamage);
        EmojiDescriptionAttackRange(components,AttackRangeUp);
        EmojiDescriptionBreakDefence(components,BreakDefence);
        EmojiDescriptionCritRate(components,CriticalHitRate);
        EmojiDescriptionCritDamage(components,CriticalHitDamage);
        EmojiDescriptionHealSteal(components,HealSteal);
        EmojiDescriptionMovementSpeed(components,SpeedUp);    }
    public static void BasicSceptreDescription (List<Component> components, float ManaDamage, float ManaCost,
                                              float ManaBreakDefence, float ManaReply, float SpeedUp) {
        Compute.EmojiDescriptionManaAttackDamage(components,ManaDamage);
        Compute.EmojiDescriptionManaCost(components,ManaCost);
        Compute.EmojiDescriptionManaBreakDefence(components,ManaBreakDefence);
        Compute.EmojiDescriptionManaRecover(components,ManaReply);
        Compute.EmojiDescriptionMovementSpeed(components,SpeedUp);
    }
    public static class AttributeDescription {
        public static Component MaxHealth (String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "最大生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN);
        }
        public static Component LossHealth (String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "已损失生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN);
        }
        public static Component MovementSpeed (String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN);
        }
        public static Component ManaDamage (String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        public static Component ManaRecover (String content) {
            return Component.literal(Utils.Emoji.ManaRecover + " " + content + "法力回复").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        public static Component Health (String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN);
        }
        public static Component Defence (String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY);
        }
        public static Component HealAmplification (String content) {
            return Component.literal(Utils.Emoji.HealthAmplification + " " + content + "治疗强度").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth);
        }
        public static Component HealthRecover (String content) {
            return Component.literal(Utils.Emoji.HealthRecover + " " + content + "生命回复").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth);
        }
        public static Component ManaDefence (String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "魔法抗性").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE);
        }
        public static Component ExAttackDamage (String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "额外攻击力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW);
        }
        public static Component BreakDefence (String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲穿透").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY);
        }
        public static Component CritDamage (String content) {
            return Component.literal(Utils.Emoji.CritDamage + " " + content + "暴击伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE);
        }
        public static Component CritRate (String content) {
            return Component.literal(Utils.Emoji.CritRate + " " + content + "暴击几率").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        public static Component AttackDamage (String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "攻击力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW);
        }
        public static Component ManaBreakDefence (String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "法术穿透").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE);
        }
        public static Component CoolDown (String content) {
            return Component.literal(Utils.Emoji.CoolDown + " " + content + "冷却缩减").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA);
        }
        public static Component HealthSteal (String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "生命偷取").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED);
        }
        public static Component ManaHealSteal (String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "法术吸血").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana);
        }
        public static Component MaxMana (String content) {
            return Component.literal(Utils.Emoji.MaxMana + " " + content + "法力值").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana);
        }
        public static Component ExHealth (String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "额外生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN);
        }
        public static Component AttackRange (String content) {
            return Component.literal(Utils.Emoji.AttackRange + " " + content + "攻击距离").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea);
        }
    }
    public static void SuitDescription (List<Component> components) {
        components.add(Component.literal(Utils.Emoji.Suit + " " + "套装效果").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
    }
    public static void SuffixOfMainStoryI (List<Component> components) {
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }
    public static void SuffixOfMainStoryII (List<Component> components) {
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }
    public static void SuffixOfMainStoryIII (List<Component> components) {
        components.add(Component.literal("MainStoryIII").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.ITALIC));
    }
    public static void SuffixOfMainStoryV (List<Component> components) {
        components.add(Component.literal("MainStoryV").withStyle(Utils.styleOfSakura).withStyle(ChatFormatting.ITALIC));
    }
    public static class MaterialLevelDescription {
        public static void Low (List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.GREEN));
        }
        public static void Normal (List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.YELLOW));
        }
        public static void Rare (List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.AQUA));
        }
        public static void Epic (List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }
    public static void RuneAttributeDescription (List<Component> components) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append("符石属性:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
    }
    public static void DescriptionPassive (List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(Component.literal("被动 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                append(name));
    }
    public static void DescriptionActive (List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(Component.literal("主动 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                append(name));
    }

}
