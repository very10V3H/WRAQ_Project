package com.very.wraq.valueAndTools;

import com.very.wraq.coreAttackModule.AttackEvent;
import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.players.bow.Hgj.HgjCurios;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanBow;
import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanCurios;
import com.very.wraq.customized.players.bow.MyMission.MyMissionBow;
import com.very.wraq.customized.players.bow.MyMission.MyMissionCurios;
import com.very.wraq.customized.players.bow.MyMission.MyMissionCurios1;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.players.bow.Wcndymlgb.WcndymlgbCurios;
import com.very.wraq.customized.players.bow.Yxwg.YxwgBow;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios1;
import com.very.wraq.customized.players.bow.Yxwg.YxwgCurios2;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.*;
import com.very.wraq.customized.players.sceptre.Eliaoi.Eliaoi;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios1;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiCurios2;
import com.very.wraq.customized.players.sceptre.Eliaoi.EliaoiSceptre;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJu;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla2;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanillaSword;
import com.very.wraq.customized.players.sceptre.YuanShiRen.YuanShiRenCurios;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdCurios;
import com.very.wraq.customized.players.sceptre.cgswd.CgswdSceptre;
import com.very.wraq.customized.players.sceptre.liulixian_.LiuLiXianCurios1F;
import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios;
import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios2;
import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios3;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLi;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiCurios;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiCurios3;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import com.very.wraq.customized.players.sword.Crush.CrushiSword;
import com.very.wraq.customized.players.sword.Heihuang.HeihuangCurios;
import com.very.wraq.customized.players.sword.LXYZO.LXYZO_Sword;
import com.very.wraq.customized.players.sword.XiangLi.XiangLi;
import com.very.wraq.customized.players.sword.XiangLi.XiangLiPickaxe;
import com.very.wraq.customized.players.sword.XiangLi.XiangliSmoke;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.customized.uniform.AttackCurios0;
import com.very.wraq.customized.uniform.BowCurios0;
import com.very.wraq.customized.uniform.ManaCurios0;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.fight.MonsterAttackEvent;
import com.very.wraq.events.instance.Castle;
import com.very.wraq.events.instance.CastleSecondFloor;
import com.very.wraq.events.instance.IceKnight;
import com.very.wraq.events.instance.Moon;
import com.very.wraq.events.core.BlockEvent;
import com.very.wraq.events.mob.MainStoryVII.StarEvent;
import com.very.wraq.events.modules.AttackEventModule;
import com.very.wraq.files.FileHandler;
import com.very.wraq.commands.stable.DpsCommand;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AnimationPackets.*;
import com.very.wraq.netWorking.misc.EffectLastTimeS2CPacket;
import com.very.wraq.netWorking.misc.EntropyPackets.EntropyS2CPacket;
import com.very.wraq.netWorking.misc.Limit.CheckBlockLimitS2CPacket;
import com.very.wraq.netWorking.misc.ManaSyncS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.netWorking.misc.PsValueS2CPacket;
import com.very.wraq.netWorking.misc.ShieldSyncS2CPacket;
import com.very.wraq.netWorking.misc.SkillPackets.Charging.*;
import com.very.wraq.netWorking.misc.SkillPackets.SkillImageS2CPacket;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.netWorking.misc.ToolTipPackets.CoolDownTimeS2CPacket;
import com.very.wraq.netWorking.misc.USE.UtilsLakeSwordS2CPacket;
import com.very.wraq.netWorking.misc.USE.UtilsSnowSwordS2CPacket;
import com.very.wraq.netWorking.reputation.ReputationValueS2CPacket;
import com.very.wraq.netWorking.unSorted.ColdSyncS2CPacket;
import com.very.wraq.netWorking.unSorted.DebuffTimeS2CPacket;
import com.very.wraq.netWorking.unSorted.PlayerCallBack;
import com.very.wraq.netWorking.unSorted.SwiftSyncS2CPacket;
import com.very.wraq.process.element.Color;
import com.very.wraq.process.element.crystal.*;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.ElementValue;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementBow;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementSceptre;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementSword;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementBow;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSceptre;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSword;
import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementBow;
import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementSceptre;
import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementSword;
import com.very.wraq.process.element.originSummon.OriginSummon;
import com.very.wraq.process.Instance.MobEffectAndDamageMethods;
import com.very.wraq.process.Lottery.Lottery;
import com.very.wraq.process.Parkour.Parkour;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.process.Power.PowerLogic;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.series.end.curios.EndCrystal;
import com.very.wraq.series.end.EndPower;
import com.very.wraq.series.end.eventController.BlackForestRecall.BlackForestSword4;
import com.very.wraq.series.end.eventController.ForestRecall.ForestSword4;
import com.very.wraq.series.end.eventController.KazeRecall.KazeSword4;
import com.very.wraq.series.end.eventController.LightningIslandRecall.iLightningArmorBoots;
import com.very.wraq.series.end.eventController.LightningIslandRecall.iLightningArmorChest;
import com.very.wraq.series.end.eventController.LightningIslandRecall.iLightningArmorHelmet;
import com.very.wraq.series.end.eventController.LightningIslandRecall.iLightningArmorLeggings;
import com.very.wraq.series.end.eventController.SeaRecall.SeaSword4;
import com.very.wraq.series.end.eventController.SnowRecall.SnowSword4;
import com.very.wraq.series.end.eventController.VolcanoRecall.VolcanoSword4;
import com.very.wraq.series.instance.Castle.*;
import com.very.wraq.series.instance.Devil.DevilAttackArmor;
import com.very.wraq.series.instance.Devil.DevilSwiftArmor;
import com.very.wraq.series.instance.Ice.IceBook;
import com.very.wraq.series.instance.Ice.IceSword;
import com.very.wraq.series.instance.Moon.Equip.*;
import com.very.wraq.series.instance.PurpleIronKnight.PurpleIronSword;
import com.very.wraq.series.instance.Taboo.TabooManaArmor;
import com.very.wraq.series.nether.Equip.*;
import com.very.wraq.series.nether.Equip.PiglinHelmet.PiglinHelmet;
import com.very.wraq.series.nether.Equip.WitherSword.WitherSword;
import com.very.wraq.series.nether.Power.MagmaPower;
import com.very.wraq.series.nether.Power.PiglinPower;
import com.very.wraq.series.nether.Power.WitherBoneMealPower;
import com.very.wraq.series.nether.Power.WitherBonePower;
import com.very.wraq.series.overWorld.MainStoryVII.StarArmor;
import com.very.wraq.series.overWorld.MainStoryVII.StarBottle;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorBoots;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorChest;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorHelmet;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Armor.ForestArmorLeggings;
import com.very.wraq.series.overWorld.MainStory_I.Forest.BossItems.ForestBossSword;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Crest.ForestCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Forest.ForestPower;
import com.very.wraq.series.overWorld.MainStory_I.Forest.ForestPowerEffectMob;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Sword.ForestSword;
import com.very.wraq.series.overWorld.MainStory_I.Forest.Sword.ForestSwordAttributes;
import com.very.wraq.series.overWorld.MainStory_I.ManaBook.ManaNote;
import com.very.wraq.series.overWorld.MainStory_I.Mine.Crest.MineCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Crest.PlainCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Plain.PlainPower;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Sceptre.PlainSceptre4;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Sword.PlainSword;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Sword.PlainSwordAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Snow.Crest.SnowCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Snow.SnowPower;
import com.very.wraq.series.overWorld.MainStory_I.Snow.Sword.SnowSword3;
import com.very.wraq.series.overWorld.MainStory_I.Volcano.BossItems.VolcanoBossSword;
import com.very.wraq.series.overWorld.MainStory_I.Volcano.Crest.VolcanoCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Volcano.Sword.VolcanoSword;
import com.very.wraq.series.overWorld.MainStory_I.Volcano.VolcanoPower;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.BossItems.LakeBoss;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Crest.LakeCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.LakePower;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes.LakeRune0;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes.LakeRune1;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Runes.LakeRune3;
import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Sword.LakeSword3;
import com.very.wraq.series.overWorld.MainStory_II.BlackForest.BlackForestSword0;
import com.very.wraq.series.overWorld.MainStory_II.BlackForest.BlackForestSword1;
import com.very.wraq.series.overWorld.MainStory_II.BlackForest.BlackForestSword2;
import com.very.wraq.series.overWorld.MainStory_II.BlackForest.BlackForestSword3;
import com.very.wraq.series.overWorld.MainStory_II.Evoker.Crest.ManaCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_II.Evoker.Runes.ManaRune3;
import com.very.wraq.series.overWorld.MainStory_II.Kaze.Sword.KazeSword0;
import com.very.wraq.series.overWorld.MainStory_II.Kaze.Sword.KazeSword1;
import com.very.wraq.series.overWorld.MainStory_II.Kaze.Sword.KazeSword2;
import com.very.wraq.series.overWorld.MainStory_II.Kaze.Sword.KazeSword3;
import com.very.wraq.series.overWorld.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.very.wraq.series.overWorld.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.very.wraq.series.overWorld.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.very.wraq.series.overWorld.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorBoots;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorChest;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorHelmet;
import com.very.wraq.series.overWorld.MainStory_II.ManaArmor.LifeMana.LifeManaArmorLeggings;
import com.very.wraq.series.overWorld.MainStory_II.Sea.Sword.SeaSword0;
import com.very.wraq.series.overWorld.MainStory_II.Sea.Sword.SeaSword1;
import com.very.wraq.series.overWorld.MainStory_II.Sea.Sword.SeaSword2;
import com.very.wraq.series.overWorld.MainStory_II.Sea.Sword.SeaSword3;
import com.very.wraq.series.overWorld.MainStory_II.Sky.BossItems.SkyBoss;
import com.very.wraq.series.overWorld.MainStory_II.Sky.Crest.SkyCrestAttributes;
import com.very.wraq.series.overWorld.MainStory_II.Sky.SkyBow;
import com.very.wraq.series.overWorld.SakuraSeries.Boss2.GoldenBook;
import com.very.wraq.series.overWorld.SakuraSeries.EarthMana.EarthPower;
import com.very.wraq.series.overWorld.SakuraSeries.SakuraMob.SakuraSword;
import com.very.wraq.series.overWorld.SakuraSeries.Ship.ShipSword;
import com.very.wraq.series.springFes.FireWorkGun;
import com.very.wraq.series.springFes.SpringScale;
import com.very.wraq.series.worldSoul.SoulEquipAttribute;
import com.very.wraq.series.worldSoul.SoulSword;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.*;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.very.wraq.coreAttackModule.ManaAttackModule.*;
import static java.lang.Math.*;


public class Compute {
    public static class PlayerAttributes {
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
                    ExDamage += ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.AttackDamage));
            }
            if (chestTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
                BaseAttackDamage += chestTag.getInt(StringUtils.RandomAttribute.AttackDamage);
                if (chestTag.contains("Forging"))
                    ExDamage += ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.AttackDamage));
            }
            if (leggingsTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
                BaseAttackDamage += leggingsTag.getInt(StringUtils.RandomAttribute.AttackDamage);
                if (leggingsTag.contains("Forging"))
                    ExDamage += ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.AttackDamage));
            }
            if (bootsTag.contains(StringUtils.RandomAttribute.AttackDamage)) {
                BaseAttackDamage += bootsTag.getInt(StringUtils.RandomAttribute.AttackDamage);
                if (bootsTag.contains("Forging"))
                    ExDamage += ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.AttackDamage));
            }

            if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackDamage.containsKey(mainhand))
                BaseAttackDamage += Utils.AttackDamage.get(mainhand);
            if (Utils.OffHandTag.containsKey(offhand) && Utils.AttackDamage.containsKey(offhand))
                ExDamage += Utils.AttackDamage.get(offhand);
            if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackDamage.containsKey(mainhand) && data0.contains("Forging"))
                ExDamage += ForgingValue(data0, Utils.AttackDamage.get(mainhand));

            // Fixed:防具攻击强化失效
            if (Utils.ArmorTag.containsKey(helmet) && Utils.AttackDamage.containsKey(helmet) && helmetTag.contains("Forging"))
                ExDamage += ForgingValue(helmetTag, Utils.AttackDamage.get(helmet));
            if (Utils.ArmorTag.containsKey(chest) && Utils.AttackDamage.containsKey(chest) && chestTag.contains("Forging"))
                ExDamage += ForgingValue(chestTag, Utils.AttackDamage.get(chest));
            if (Utils.ArmorTag.containsKey(leggings) && Utils.AttackDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
                ExDamage += ForgingValue(leggingsTag, Utils.AttackDamage.get(leggings));
            if (Utils.ArmorTag.containsKey(boots) && Utils.AttackDamage.containsKey(boots) && bootsTag.contains("Forging"))
                ExDamage += ForgingValue(bootsTag, Utils.AttackDamage.get(boots));

            if (Utils.MainHandTag.containsKey(mainhand) && Utils.AttackDamage.containsKey(mainhand) && data0.contains(StringUtils.KillCount.KillCount)) {
                int killCount = data0.getInt(StringUtils.KillCount.KillCount);
                if (killCount >= 100000) killCount = 100000;
                ExDamage += Utils.AttackDamage.get(mainhand) * 0.5 * (killCount / 100000.0);
            }
            if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 0)
                ExDamage += player.getAttribute(Attributes.MAX_HEALTH).getValue() * 0.025 * EndRune3Judge(player, 0);
            if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 1)
                ExDamage += ((player.getMaxHealth() - player.getHealth()) / player.getMaxHealth()) * BaseAttackDamage * 0.25 * Compute.EndRune3Judge(player, 1);
            if (helmetTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(helmetTag);
            if (chestTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(chestTag);
            if (leggingsTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(leggingsTag);
            if (bootsTag.contains("Gems1")) ExDamage += Compute.ItemBaseDamageGems(bootsTag);
            if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
                ExDamage += Compute.ItemBaseDamageGems(stackmainhandtag);
            if (ArmorCount.Volcano(player) >= 2) ExDamage += BaseAttackDamage * 0.15F;
            if (ArmorCount.ObsiMana(player) >= 4) ExDamage += BaseAttackDamage * 0.15F;
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
            if (ArmorCount.Sky(player) > 0) {
                if (player.getHealth() / player.getMaxHealth() > 0.8)
                    ExDamage += BaseAttackDamage * 1 * SkySuitEffectRate(player);
                else if (player.getHealth() / player.getMaxHealth() > 0.4)
                    ExDamage += BaseAttackDamage * 0.4 * SkySuitEffectRate(player);
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

            if (SwordSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.SwordSkillNum.Skill2) && data.getInt(StringUtils.SwordSkillNum.Skill2) > TickCount) {
                ExDamage += BaseAttackDamage * SwordSkillLevelGet(data, 2) * 0.02;
            } // 战斗渴望（击杀一个单位时，提升2%攻击力，持续10s）‘

            if (BowSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.BowSkillNum.Skill2) && data.getInt(StringUtils.BowSkillNum.Skill2) > TickCount) {
                ExDamage += BaseAttackDamage * BowSkillLevelGet(data, 2) * 0.02;
            } // 狩猎渴望（击杀一个单位时，提升2%攻击力，持续10s）

            if (SwordSkillLevelGet(data, 5) > 0 && data.contains(StringUtils.SwordSkillNum.Skill5) && data.getInt(StringUtils.SwordSkillNum.Skill5) > TickCount) {
                ExDamage += BaseAttackDamage * SwordSkillLevelGet(data, 5) * 0.015;
            } // 剑术-狂暴（造成暴击后，提升1%攻击力，持续3s）

            if (BowSkillLevelGet(data, 5) > 0 && data.contains(StringUtils.BowSkillNum.Skill5) && data.getInt(StringUtils.BowSkillNum.Skill5) > TickCount) {
                ExDamage += BaseAttackDamage * BowSkillLevelGet(data, 5) * 0.01;
            } // 弓术-狂暴（造成暴击后，提升1%攻击力，持续5s）

            if (SwordSkillLevelGet(data, 6) > 0 && Utils.SwordSkill6Map.containsKey(player) && Utils.SwordSkill6Map.get(player).getTime() > TickCount) {
                ExDamage += BaseAttackDamage * SwordSkillLevelGet(data, 6) * 0.003 * Utils.SwordSkill6Map.get(player).getCount();
            } // 完美（持续造成暴击，将提供至多30%攻击力，持续10s，在十次暴击后达最大值，在未造成暴击时重置层数）

            if (BowSkillLevelGet(data, 6) > 0 && Utils.BowSkill6Map.containsKey(player) && Utils.BowSkill6Map.get(player).getTime() > TickCount) {
                ExDamage += BaseAttackDamage * BowSkillLevelGet(data, 6) * 0.005 * Utils.BowSkill6Map.get(player).getCount();
            } // 完美（持续的命中目标的箭矢，将提供至多3%攻击力，持续10s，在十次命中后达最大值，在未命中时重置层数）

            if (BowSkillLevelGet(data, 8) > 0 && Utils.BowTag.containsKey(mainhand)) {
                ExDamage += BaseAttackDamage * BowSkillLevelGet(data, 8) * 0.02;
            } // 锻弦-力量（手持弓时，获得2%攻击力）

            int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
            if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
                int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
                ExDamage += Point;
            } // 能力

            if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
                ExDamage += 50;
            // 矿工裤被动

            if (ArmorCount.Mine(player) >= 4) ExDamage += BaseAttackDamage * 0.3;

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

            ExDamage += SArmorAttackDamage(player);

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

            ExDamage += CuriosAttribute.AttributeValue(player,Utils.AttackDamage,StringUtils.CuriosAttribute.AttackDamage); // 新版饰品属性加成

            ExDamage += LeiyanCurios.ExDamage(player);
            ExDamage += MyMissionBow.ExDamage(player);
            ExDamage += MyMissionCurios.AttackDamageUp(player);
            ExDamage += MyMissionCurios1.AttackDamageUp(player);
            ExDamage += LeiyanBow.ExAttackDamage(player);

            ExDamage += PassiveEquip.AttributeGet(player,Utils.AttackDamage); // 器灵属性加成
            ExDamage += CastleAttackArmor.ExAttributeValue(player,CastleAttackArmor.ExAttackDamage);
            ExDamage += CastleSwiftArmor.ExAttributeValue(player,CastleSwiftArmor.ExAttackDamage);
            ExDamage += MoonSword.ExAttackDamage(player);
            ExDamage += MoonSceptre.ExManaDamage(player);
            ExDamage += QiFuCurios1.ExAttackDamage(player);
            ExDamage += ZuoSiCurios.ExAttackOrManaDamage(player,true);
            ExDamage += LifeElementSword.ExAttackDamage(player);
            ExDamage += LifeElementBow.ExAttackDamage(player);
            // 请在上方添加

            double TotalAttackDamage = BaseAttackDamage + ExDamage;

            TotalAttackDamage *= LiulixianCurios3.ExAttackDamageUp(player);
            TotalAttackDamage *= YxwgCurios1.AttackDamageUp(player);
            TotalAttackDamage *= (1 + MoonShield.damageEnhance(player));
            TotalAttackDamage *= (1 + MoonKnife.damageEnhance(player));
            TotalAttackDamage *= (1 + YxwgBow.ExDamage(player));
            TotalAttackDamage *= playerFantasyAttributeEnhance(player);
            TotalAttackDamage *= YxwgCurios2.Passive1AttackDamageUp(player);
            TotalAttackDamage *= YxwgCurios2.Passive2AttackDamageUp(player);
            TotalAttackDamage *= (1 + QiFuCurios.CountAttackDamageUp(player));
            TotalAttackDamage *= BlackFeisaCurios4.AttackDamageUp(player);
            TotalAttackDamage *= HgjCurios.AttackDamageEnhance(player);

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
            CriticalHitRate += SArmorCritRate(player);
            if (BowSkillLevelGet(data, 11) > 0 && Utils.BowTag.containsKey(mainhand)) {
                CriticalHitRate += BowSkillLevelGet(data, 11) * 0.02;
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

            CriticalHitRate += CuriosAttribute.AttributeValue(player,Utils.CritRate,StringUtils.CuriosAttribute.CritRate); // 新版饰品属性加成

            CriticalHitRate += YxwgBow.CritRateUp(player);
            CriticalHitRate += YxwgCurios2.Passive1ExCritRate(player);
            CriticalHitRate += QiFuCurios.CountExCritRate(player);
            // 请在上方添加
            CriticalHitRate *= playerFantasyAttributeEnhance(player);

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
            CritDamage += SArmorCritDamage(player);

            if (BowSkillLevelGet(data, 7) > 0 && Utils.BowTag.containsKey(mainhand)) {
                CritDamage += BowSkillLevelGet(data, 7) * 0.1;
            } // 锻矢-锋利（手持弓时，获得10%暴击伤害）

            int PowerAbilityPoint = data.getInt(StringUtils.Ability.Power);
            if (data.contains(StringUtils.Ability.Power) && data.getInt(StringUtils.Ability.Power) > 0) {
                int Point = PowerAbilityPoint + (PowerAbilityPoint / 10) * 5;
                CritDamage += Point * 0.01;
            } // 能力

            if (mainhand.equals(ModItems.SkyBossBow.get())) {
                CritDamage += 0.08 * EntropyRate(data.getInt(StringUtils.Entropy.Sky));
            }

            if (ArmorCount.Volcano(player) >= 4) CritDamage += 0.35;

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
                if (Compute.PlayerAttributes.PlayerDefence(player) > 224)
                    CritDamage += 1.12 + Compute.PlayerAttributes.PlayerDefence(player) * 0.1 / 224;
            } // LXYZO

            if (Utils.YiZhiXiangLi != null && Utils.YiZhiXiangLi.equals(player) && Utils.YiZhiXiangLiEffect > TickCount) {
                CritDamage += 3.6;
            }

            if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1CritDamage1Tick > TickCount) CritDamage += 4;
            if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1CritDamage2Tick > TickCount) CritDamage += Utils.Crush1CritDamage2Count * 2.5;

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

            CritDamage += CuriosAttribute.AttributeValue(player,Utils.CritDamage,StringUtils.CuriosAttribute.CritDamage); // 新版饰品属性加成

            CritDamage += MyMissionBow.ExCritDamage(player);
            CritDamage += YxwgBow.CritDamageUp(player);
            CritDamage += MyMissionCurios.CritDamageUp(player);
            CritDamage += MyMissionCurios1.CritDamageUp(player);
            CritDamage += PassiveEquip.AttributeGet(player,Utils.CritDamage); // 器灵属性加成
            CritDamage += LeiyanBow.CritDamageUp(player);
            CritDamage += FengXiaoJuCurios1.ExCritDamage(player);
            CritDamage += CastleAttackArmor.ExAttributeValue(player,CastleAttackArmor.ExCritDamage);
            CritDamage += LakeRune1.ExCritDamage(player);
            CritDamage += YxwgCurios2.Passive1ExCritDamage(player);
            CritDamage += YxwgCurios2.Passive2ExCritDamage(player);
            CritDamage += YxwgBow.ExCritDamage(player);
            CritDamage += QiFuCurios.CountExCritDamage(player);
            CritDamage += QiFuCurios.Buff2ExCritDamage(player);
            CritDamage += WcndymlgbCurios.ExCritDamage(player);
            // 请在上方添加

            CritDamage *= playerFantasyAttributeEnhance(player);
            CritDamage *= AttackCurios0.PlayerFinalCritDamageEnhance(player);
            CritDamage *= HeihuangCurios.Passive2CritDamageEnhance(player);

            if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
                if (player.getHealth() / player.getMaxHealth() < 0.5) {
                    data.putDouble("CritDamageAfterCompute",CritDamage);
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
            if (ArmorCount.Sky(player) > 0 && player.getHealth() / player.getMaxHealth() > 0.8)
                SpeedUp += 0.8 * SkySuitEffectRate(player);
            if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 0)
                SpeedUp += 0.5;
            if (player.getEffect(ModEffects.SPEEDUP.get()) != null && player.getEffect(ModEffects.SPEEDUP.get()).getAmplifier() == 1)
                SpeedUp += 1.0;
            if (data.contains("GemSSpeed")) SpeedUp += data.getDouble("GemSSpeed");
            if (mainhand.equals(ModItems.GoldSword0.get())) SpeedUp += data.getDouble("VB") / 1000000.0;

            if (SwordSkillLevelGet(data, 9) > 0 && Utils.SwordTag.containsKey(mainhand)) {
                SpeedUp += SwordSkillLevelGet(data, 9) * 0.08;
            } // 剑舞（手持近战武器时，获得8%额外移动速度）

            if (BowSkillLevelGet(data, 1) > 0 && Utils.BowTag.containsKey(mainhand)) {
                SpeedUp += BowSkillLevelGet(data, 1) * 0.02;
            } // 原野护符（持有弓时，获得2%的额外移动速度）

            if (BowSkillLevelGet(data, 9) > 0 && Utils.BowTag.containsKey(mainhand)) {
                SpeedUp += BowSkillLevelGet(data, 9) * 0.1;
            } // 猎手本能（手持弓时，获得10%额外移动速度）

            if (ManaSkillLevelGet(data, 9) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
                SpeedUp += ManaSkillLevelGet(data, 9) * 0.08;
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

            if (ArmorCount.Lake(player) >= 4) SpeedUp += 0.35;
            if (ArmorCount.Mine(player) >= 4) SpeedUp -= 0.5;

            if (Utils.PlayerSpringBeltMovementAttribute.containsKey(player) && Utils.PlayerSpringBeltLevelRequire.get(player) <= player.experienceLevel) {
                SpeedUp += Utils.PlayerSpringBeltMovementAttribute.get(player);
            }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.XiangLiSmoke.get())) SpeedUp += 1.5;

            SpeedUp += CuriosAttribute.AttributeValue(player,Utils.MovementSpeed,StringUtils.CuriosAttribute.MovementSpeed); // 新版饰品属性加成
            SpeedUp += LiulixianCurios2.ExMovementSpeed(player);
            SpeedUp += QiFuCurios1.Passive1ExMovementSpeed(player);
            SpeedUp += QiFuCurios1.Passive2ExMovementSpeed(player);
            SpeedUp += QiFuCurios1.Passive3ExMovementSpeed(player);
            SpeedUp += PassiveEquip.AttributeGet(player,Utils.MovementSpeed); // 器灵属性加成
            // 请在上方添加

            SpeedUp *= LiulixianCurios3.MovementEnhance(player);
            SpeedUp *= (1 + YxwgBow.ExMovementSpeed(player));

            SpeedUp *= playerFantasyAttributeEnhance(player);

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

            ExpUp += CuriosAttribute.AttributeValue(player,Utils.ExpUp,StringUtils.CuriosAttribute.ExpUp); // 新版饰品属性加成

            ExpUp += PassiveEquip.AttributeGet(player, Utils.ExpUp); // 器灵属性加成
            ExpUp += BlackFeisaCurios4.ExpUp(player);
            // 请在上方添加
            ExpUp *= playerFantasyAttributeEnhance(player);

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
                    ExDefence += ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.Defence));
            }
            if (chestTag.contains(StringUtils.RandomAttribute.Defence)) {
                Defence += chestTag.getInt(StringUtils.RandomAttribute.Defence);
                if (chestTag.contains("Forging"))
                    ExDefence += ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.Defence));
            }
            if (leggingsTag.contains(StringUtils.RandomAttribute.Defence)) {
                Defence += leggingsTag.getInt(StringUtils.RandomAttribute.Defence);
                if (leggingsTag.contains("Forging"))
                    ExDefence += ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.Defence));
            }
            if (bootsTag.contains(StringUtils.RandomAttribute.Defence)) {
                Defence += bootsTag.getInt(StringUtils.RandomAttribute.Defence);
                if (bootsTag.contains("Forging"))
                    ExDefence += ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.Defence));
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
                ExDefence += ForgingValue(helmetTag, Utils.Defence.get(helmet));
            if (Utils.Defence.containsKey(chest) && chestTag.contains("Forging"))
                ExDefence += ForgingValue(chestTag, Utils.Defence.get(chest));
            if (Utils.Defence.containsKey(leggings) && leggingsTag.contains("Forging"))
                ExDefence += ForgingValue(leggingsTag, Utils.Defence.get(leggings));
            if (Utils.Defence.containsKey(boots) && bootsTag.contains("Forging"))
                ExDefence += ForgingValue(bootsTag, Utils.Defence.get(boots));
            if (ArmorCount.Forest(player) >= 2) ExDefence += Defence * 0.25;
            if (ArmorCount.LifeMana(player) >= 4) ExDefence += Defence * 0.25;
            if (data.contains("forestgems") && data.getBoolean("forestgems")) ExDefence += Defence * 0.1;
            if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0)
                ExDefence += Defence * 0.25 + 80;
            if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1)
                ExDefence += Defence * 0.40 + 160;
            if (data.contains("GemSDefence")) ExDefence += data.getDouble("GemSDefence");
            if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
                ExDefence += 100;
            // 矿工裤被动
            if (ArmorCount.Mine(player) >= 4) ExDefence += 250;

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

            ExDefence += CuriosAttribute.AttributeValue(player,Utils.Defence,StringUtils.CuriosAttribute.Defence); // 新版饰品属性加成

            ExDefence += PassiveEquip.AttributeGet(player,Utils.Defence); // 器灵属性加成

            ExDefence += CastleAttackArmor.ExAttributeValue(player,CastleAttackArmor.ExDefence);
            ExDefence += CastleManaArmor.ExAttributeValue(player,CastleManaArmor.ExDefence);
            ExDefence += CastleSwiftArmor.ExAttributeValue(player,CastleSwiftArmor.ExDefence);
            // 请在上方添加
            double totalDefence = 0;
            totalDefence = Defence + ExDefence;
            if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                    && data.getDouble("ManaDamageAfterCompute") > 10000) {
                totalDefence *= (1.25 + ShangMengLiCurios.DefenceEnhance(player));
            }
            totalDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
            totalDefence *= playerFantasyAttributeEnhance(player);
            totalDefence *= HeihuangCurios.Passive2DefenceEnhance(player);
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
                    ExDefence += ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.Defence));
            }
            if (chestTag.contains(StringUtils.RandomAttribute.Defence)) {
                Defence += chestTag.getInt(StringUtils.RandomAttribute.Defence);
                if (chestTag.contains("Forging"))
                    ExDefence += ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.Defence));
            }
            if (leggingsTag.contains(StringUtils.RandomAttribute.Defence)) {
                Defence += leggingsTag.getInt(StringUtils.RandomAttribute.Defence);
                if (leggingsTag.contains("Forging"))
                    ExDefence += ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.Defence));
            }
            if (bootsTag.contains(StringUtils.RandomAttribute.Defence)) {
                Defence += bootsTag.getInt(StringUtils.RandomAttribute.Defence);
                if (bootsTag.contains("Forging"))
                    ExDefence += ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.Defence));
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
                ExDefence += ForgingValue(helmetTag, Utils.Defence.get(helmet));
            if (Utils.Defence.containsKey(chest) && chestTag.contains("Forging"))
                ExDefence += ForgingValue(chestTag, Utils.Defence.get(chest));
            if (Utils.Defence.containsKey(leggings) && leggingsTag.contains("Forging"))
                ExDefence += ForgingValue(leggingsTag, Utils.Defence.get(leggings));
            if (Utils.Defence.containsKey(boots) && bootsTag.contains("Forging"))
                ExDefence += ForgingValue(bootsTag, Utils.Defence.get(boots));
            if (ArmorCount.Forest(player) >= 2) ExDefence += Defence * 0.25;
            if (ArmorCount.LifeMana(player) >= 4) ExDefence += Defence * 0.25;
            if (data.contains("forestgems") && data.getBoolean("forestgems")) ExDefence += Defence * 0.1;
            if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 0)
                ExDefence += Defence * 0.25 + 40;
            if (player.getEffect(ModEffects.DefenceUP.get()) != null && player.getEffect(ModEffects.DefenceUP.get()).getAmplifier() == 1)
                ExDefence += Defence * 0.40 + 80;
            if (data.contains("GemSDefence")) ExDefence += data.getDouble("GemSDefence");
            if (leggings.equals(ModItems.MinePants.get()) && (Utils.OverWorldLevelIsNight || player.getY() < 63))
                ExDefence += 100;
            // 矿工裤被动
            if (ArmorCount.Mine(player) >= 4) ExDefence += 250;

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

            ExDefence += CuriosAttribute.AttributeValue(player,Utils.Defence,StringUtils.CuriosAttribute.Defence); // 新版饰品属性加成

            ExDefence += PassiveEquip.AttributeGet(player,Utils.Defence); // 器灵属性加成

            // 请在上方添加
            if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                    && data.getDouble("ManaDamageAfterCompute") > 10000) {
                Defence *= 1.25;
                ExDefence *= 1.25;
            }

            ExDefence *= playerFantasyAttributeEnhance(player);
            Defence *= playerFantasyAttributeEnhance(player);

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
            if (ArmorCount.Forest(player) >= 4) HealEffectUp += 0.5f;
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

            HealEffectUp += CuriosAttribute.AttributeValue(player,Utils.HealEffectUp,StringUtils.CuriosAttribute.HealEffectUp); // 新版饰品属性加成
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

            SwiftnessUp += CuriosAttribute.AttributeValue(player,Utils.SwiftnessUp,StringUtils.CuriosAttribute.SwiftnessUp); // 新版饰品属性加成

            SwiftnessUp += PassiveEquip.AttributeGet(player,Utils.SwiftnessUp); // 器灵属性加成
            SwiftnessUp += LiulixianCurios2.ExSwiftnessUp(player);
            SwiftnessUp += CastleSwiftArmor.ExAttributeValue(player,CastleSwiftArmor.ExSwiftnessUp);
            SwiftnessUp += QiFuCurios1.Passive1ExSwiftness(player);
            SwiftnessUp += QiFuCurios1.Passive3ExSwiftness(player);
            // 请在上方添加
            SwiftnessUp *= LiulixianCurios3.SwiftnessEnhance(player);
            SwiftnessUp *= playerFantasyAttributeEnhance(player);
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
            if (SwordSkillLevelGet(data, 11) > 0 && Utils.SwordTag.containsKey(mainhand))
                RangeUp += SwordSkillLevelGet(data, 11) * 0.2;

            // 请在上方添加
            RangeUp *= playerFantasyAttributeEnhance(player);

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
            if (ArmorCount.Lake(player) >= 2) ReleaseSpeed += 0.2;
            if (ArmorCount.ObsiMana(player) >= 4) ReleaseSpeed += 0.2;
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
            ReleaseSpeed += SArmorCoolDown(player);
            if (SwordSkillLevelGet(data, 7) > 0 && Utils.SwordTag.containsKey(mainhand))
                ReleaseSpeed += SwordSkillLevelGet(data, 7) * 0.03; // 冷静（手持近战武器时，获得3%冷却缩减）
            if (ManaSkillLevelGet(data, 7) > 0 && Utils.SceptreTag.containsKey(mainhand))
                ReleaseSpeed += ManaSkillLevelGet(data, 7) * 0.06; // 冷静（手持法杖时，获得6%冷却缩减）

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
            if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1) ReleaseSpeed += 0.5;

            ReleaseSpeed += EarthPower.PlayerCoolDownEnhance(player); // 地蕴法术

            ReleaseSpeed += CuriosAttribute.AttributeValue(player,Utils.CoolDownDecrease,StringUtils.CuriosAttribute.CoolDown); // 新版饰品属性加成
            ReleaseSpeed += CastleManaArmor.ExAttributeValue(player,CastleManaArmor.ExCoolDownDecrease);

            ReleaseSpeed += LakeRune0.ExCoolDown(player);
            ReleaseSpeed += LakeRune3.ExCoolDown(player);
            // 请在上方添加
            ReleaseSpeed *= LakeRune1.CoolDownDown(player);
            ReleaseSpeed *= LakeRune0.CoolDownUp(player);
            ReleaseSpeed *= playerFantasyAttributeEnhance(player);

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
            if (SwordSkillLevelGet(data, 7) > 0 && Utils.SwordTag.containsKey(mainhand))
                DecreaseRate *= (1 - SwordSkillLevelGet(data, 7) * 0.03); // 冷静（手持近战武器时，获得3%冷却缩减）
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
            DecreaseRate *= (2 - playerFantasyAttributeEnhance(player));

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
            AttackSpeedUp *= playerFantasyAttributeEnhance(player);

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
            DefenceRate *= (1 - CuriosAttribute.AttributeValue(player,Utils.DefencePenetration,StringUtils.CuriosAttribute.DefencePenetration)); // 新版饰品属性加成
            DecreaseRate *= (1 - BlackFeisaCurios4.Penetration(player));
            // 请在上方添加
            DefenceRate *= (2 - playerFantasyAttributeEnhance(player));

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
            if (SwordSkillLevelGet(data, 10) > 0 && Utils.SwordTag.containsKey(mainhand))
                DefencePenetration0 += SwordSkillLevelGet(data, 10) * player.experienceLevel;
            if (BowSkillLevelGet(data, 10) > 0 && Utils.BowTag.containsKey(mainhand))
                DefencePenetration0 += BowSkillLevelGet(data, 10) * player.experienceLevel;
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

            DefencePenetration0 += CuriosAttribute.AttributeValue(player,Utils.DefencePenetration0,StringUtils.CuriosAttribute.DefencePenetration0); // 新版饰品属性加成

            DefencePenetration0 += MyMissionBow.ExDefencePenetration0(player);
            DefencePenetration0 += CastleSword.ExPenetration0(player); // 暗黑武器主动
            DefencePenetration0 += YxwgBow.ExDefencePenetration0(player);

            if (data.getInt(StringUtils.WitherSword.Effect3) > TickCount) DefencePenetration0 += 200;
            else if (data.getInt(StringUtils.WitherSword.Effect2) > TickCount) DefencePenetration0 += 300;
            else if (data.getInt(StringUtils.WitherSword.Effect1) > TickCount) DefencePenetration0 += 400;
            else if (data.getInt(StringUtils.WitherSword.Effect0) > TickCount) DefencePenetration0 += 600;

            DefencePenetration0 += QiFuCurios.Buff2DefencePenetration0(player);
            // 请在上方添加
            DefencePenetration0 *= playerFantasyAttributeEnhance(player);
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
            if (ArmorCount.Plain(player) >= 2) HealthRecover += 0.5 + player.getMaxHealth() * 0.01F;
            if (ArmorCount.LifeMana(player) >= 4) HealthRecover += 1 + player.getMaxHealth() * 0.01F;
            if (!Utils.OverWorldLevelIsNight && ArmorCount.Forest(player) >= 4) HealthRecover += 5;
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

            HealthRecover += CuriosAttribute.AttributeValue(player,Utils.HealthRecover,StringUtils.CuriosAttribute.HealthRecover); // 新版饰品属性加成
            HealthRecover += SoraVanillaSword.HealthRecover(player);
            // 请在上方添加
            HealthRecover *= playerFantasyAttributeEnhance(player);

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
                    MaxHealth += ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.MaxHealth));
            }
            if (chestTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
                MaxHealth += chestTag.getInt(StringUtils.RandomAttribute.MaxHealth);
                if (chestTag.contains("Forging"))
                    MaxHealth += ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.MaxHealth));
            }
            if (leggingsTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
                MaxHealth += leggingsTag.getInt(StringUtils.RandomAttribute.MaxHealth);
                if (leggingsTag.contains("Forging"))
                    MaxHealth += ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.MaxHealth));
            }
            if (bootsTag.contains(StringUtils.RandomAttribute.MaxHealth)) {
                MaxHealth += bootsTag.getInt(StringUtils.RandomAttribute.MaxHealth);
                if (bootsTag.contains("Forging"))
                    MaxHealth += ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.MaxHealth));
            }

            if (Utils.MaxHealth.containsKey(helmet) && helmetTag.contains("Forging"))
                MaxHealth += ForgingValue(helmetTag, Utils.MaxHealth.get(helmet));
            if (Utils.MaxHealth.containsKey(chest) && chestTag.contains("Forging"))
                MaxHealth += ForgingValue(chestTag, Utils.MaxHealth.get(chest));
            if (Utils.MaxHealth.containsKey(leggings) && leggingsTag.contains("Forging"))
                MaxHealth += ForgingValue(leggingsTag, Utils.MaxHealth.get(leggings));
            if (Utils.MaxHealth.containsKey(boots) && bootsTag.contains("Forging"))
                MaxHealth += ForgingValue(bootsTag, Utils.MaxHealth.get(boots));

            if (Utils.MainHandTag.containsKey(mainhand) && Utils.MaxHealth.containsKey(mainhand))
                MaxHealth += Utils.MaxHealth.get(mainhand);
            if (Utils.OffHandTag.containsKey(offhand) && Utils.MaxHealth.containsKey(offhand))
                MaxHealth += Utils.MaxHealth.get(offhand);
            if (ArmorCount.Plain(player) >= 4) MaxHealth += 200;
            if (data.contains("Barker")) MaxHealth *= 1.0d + (data.getInt("Barker") / 100000.0d) * 0.05F;
            if (data.contains("volcanoRune") && data.getInt("volcanoRune") == 3) MaxHealth *= 0.5;
            for (int i = 0; i < 21; i++) {
                if (data.contains("LevelReward" + 5 * (i + 1)) && data.getBoolean("LevelReward" + 5 * (i + 1))) {
                    MaxHealth += (i + 1) * 10;
                }
            }
            if (data.contains("GemSMaxHeal")) MaxHealth += data.getDouble("GemSMaxHeal");
            MaxHealth += SArmorMaxHealth(player);

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

            MaxHealth += CuriosAttribute.AttributeValue(player,Utils.MaxHealth,StringUtils.CuriosAttribute.MaxHealth); // 新版饰品属性加成

            MaxHealth += PassiveEquip.AttributeGet(player,Utils.MaxHealth); // 器灵属性加成
            MaxHealth += CastleAttackArmor.ExAttributeValue(player,CastleAttackArmor.ExMaxHealth);
            MaxHealth += CastleManaArmor.ExAttributeValue(player,CastleManaArmor.ExMaxHealth);
            MaxHealth += CastleSwiftArmor.ExAttributeValue(player,CastleSwiftArmor.ExMaxHealth);
            // 请在上方添加
            if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                    && Compute.PlayerAttributes.PlayerManaDamage(player) > 10000) {
                MaxHealth *= (1.25 + ShangMengLiCurios.MaxHealthEnhance(player));
            }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WcBow.get()) && Utils.WcBowTick > TickCount) {
                MaxHealth *= (1 + 0.05 * Utils.WcBowCount);
            }

            MaxHealth *= playerFantasyAttributeEnhance(player);

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
                ExDamage += ForgingValue(helmetTag, Utils.ManaDamage.get(helmet));
            if (Utils.ArmorTag.containsKey(chest) && Utils.ManaDamage.containsKey(chest) && chestTag.contains("Forging"))
                ExDamage += ForgingValue(chestTag, Utils.ManaDamage.get(chest));
            if (Utils.ArmorTag.containsKey(leggings) && Utils.ManaDamage.containsKey(leggings) && leggingsTag.contains("Forging"))
                ExDamage += ForgingValue(leggingsTag, Utils.ManaDamage.get(leggings));
            if (Utils.ArmorTag.containsKey(boots) && Utils.ManaDamage.containsKey(boots) && bootsTag.contains("Forging"))
                ExDamage += ForgingValue(bootsTag, Utils.ManaDamage.get(boots));

            if (helmetTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
                BaseDamage += helmetTag.getInt(StringUtils.RandomAttribute.ManaDamage);
                if (helmetTag.contains("Forging"))
                    ExDamage += ForgingValue(helmetTag, helmetTag.getInt(StringUtils.RandomAttribute.ManaDamage));
            }
            if (chestTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
                BaseDamage += chestTag.getInt(StringUtils.RandomAttribute.ManaDamage);
                if (chestTag.contains("Forging"))
                    ExDamage += ForgingValue(chestTag, chestTag.getInt(StringUtils.RandomAttribute.ManaDamage));
            }
            if (leggingsTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
                BaseDamage += leggingsTag.getInt(StringUtils.RandomAttribute.ManaDamage);
                if (leggingsTag.contains("Forging"))
                    ExDamage += ForgingValue(leggingsTag, leggingsTag.getInt(StringUtils.RandomAttribute.ManaDamage));
            }
            if (bootsTag.contains(StringUtils.RandomAttribute.ManaDamage)) {
                BaseDamage += bootsTag.getInt(StringUtils.RandomAttribute.ManaDamage);
                if (bootsTag.contains("Forging"))
                    ExDamage += ForgingValue(bootsTag, bootsTag.getInt(StringUtils.RandomAttribute.ManaDamage));
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
                ExDamage += ForgingValue(data0, Utils.ManaDamage.get(mainhand));
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
            ExDamage += SArmorManaDamage(player);

            int IntelligentAbilityPoint = data.getInt(StringUtils.Ability.Intelligent);
            if (data.contains(StringUtils.Ability.Intelligent) && data.getInt(StringUtils.Ability.Intelligent) > 0) {
                int Point = IntelligentAbilityPoint + (IntelligentAbilityPoint / 10) * 5;
                ExDamage += Point * 4;
            } // 能力

            if (ManaSkillLevelGet(data, 2) > 0 && data.contains(StringUtils.ManaSkillNum.Skill2) && data.getInt(StringUtils.ManaSkillNum.Skill2) > TickCount) {
                ExDamage += BaseDamage * ManaSkillLevelGet(data, 2) * 0.02;
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

            if (ArmorCount.Volcano(player) >= 2) ExDamage += BaseDamage * 0.25F;
            if (ArmorCount.ObsiMana(player) >= 4) ExDamage += BaseDamage * 0.25F;

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
                    && Utils.FengxiaojuPassiveTick > TickCount) ExDamage += BaseDamage * 0.15 * Utils.FengxiaojuPassiveCount;

            if (Utils.MeteoriteAttackTimeMap.containsKey(player) && Utils.MeteoriteAttackTimeMap.get(player) > TickCount) {
                ExDamage += BaseDamage * 0.1;
            }

            if (Utils.EarthManaCurios.containsKey(player) && Utils.EarthManaCurios.get(player)) ExDamage += 400;

            if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianCountsLastTick > TickCount
                    && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get()))
                ExDamage += BaseDamage * 0.15 * Utils.LiuLiXianCounts;

            if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) ExDamage += 1600;

            if (BlackFeisa.Player != null && BlackFeisa.Player.equals(player) && BlackFeisa.ActiveExManaDamageTick > TickCount) ExDamage += BaseDamage * 0.5;
            if (BlackFeisa.Player != null && BlackFeisa.Player.equals(player) && BlackFeisa.PassiveExManaDamageTick > TickCount) ExDamage += BaseDamage * 0.15 * BlackFeisa.PassiveExManaDamageCount;
            if (BlackFeisaCurios.IsPlayer(player) && BlackFeisaCurios.ManaUpTick > TickCount) ExDamage += BaseDamage * 0.75;
            if (Eliaoi.EliaoiPlayerTickMap.containsKey(player) && Eliaoi.EliaoiPlayerTickMap.get(player) > TickCount) ExDamage += BaseDamage * 0.5;

            ExDamage += BaseDamage * EarthPower.PlayerDamageEnhance(player);
            ExDamage += CuriosAttribute.AttributeValue(player,Utils.ManaDamage,StringUtils.CuriosAttribute.ManaDamage); // 新版饰品属性加成

            ExDamage += BaseDamage * ShangMengLiSword.ManaDamageEnhance(player);

            if (BlackFeisaCurios1.IsOn(player) && BlackFeisaCurios1.DamageEnhanceTick > TickCount) ExDamage += BlackFeisaCurios1.DamageEnhanceValue;

            ExDamage += BlackFeisaCurios2.ManaDamageUp(player);
            ExDamage += SoraVanilla.ManaDamageUp(player);
            ExDamage += SoraVanilla1.ManaDamageUp(player);
            ExDamage += LiulixianCurios.ManaDamageEnhance(player);
            ExDamage += BaseDamage * CgswdSceptre.ExManaDamage(player);
            ExDamage += PassiveEquip.AttributeGet(player,Utils.ManaDamage); // 器灵属性加成
            ExDamage += FengXiaoJuCurios1.ExManaDamage(player);
            ExDamage += LiulixianCurios2.ExManaDamage(player);
            ExDamage += CastleManaArmor.ExAttributeValue(player,CastleManaArmor.ExManaDamage);
            ExDamage += BlackFeisaCurios.ExManaDamage(player);
            ExDamage += ZuoSiCurios.ExAttackOrManaDamage(player,false);
            ExDamage += LifeElementSceptre.ExManaDamage(player);
            /*ExDamage += ShangMengLiCurios3.ExManaDamage(player);*/
            // 请在上方添加
            double TotalDamage = BaseDamage + ExDamage;
            TotalDamage *= LiulixianCurios3.ExManaDamageUp(player);
            TotalDamage *= SoraVanilla2.ExManaDamage(player);
            TotalDamage *= CgswdCurios.ExManaDamageValue(player);
            TotalDamage *= EliaoiCurios1.ManaDamageUp(player);
            TotalDamage *= EliaoiSceptre.ManaDamageUp(player);
            TotalDamage *= (1 + MoonBook.damageEnhance(player));
            TotalDamage *= playerFantasyAttributeEnhance(player);
            TotalDamage *= ManaCurios0.PlayerFinalManaDamageEnhance(player);
            TotalDamage *= BlackFeisaCurios3.ManaDamageUp(player);
            TotalDamage *= EliaoiCurios2.ManaDamageEnhance(player);
            TotalDamage *= SoraVanillaSword.ManaDamageUp(player);

            Utils.playerManaDamageBeforeTransform.put(player,TotalDamage);

            data.putDouble("NetherRuneManaDamageCompute", (TotalDamage) * 0.1f * Compute.EndRune3Judge(player, 2));
            data.putDouble("ManaDamageAfterCompute",TotalDamage);
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
            if (ArmorCount.LifeMana(player) >= 4) ManaHealSteal += 0.05;
            if (ManaSkillLevelGet(data, 11) > 0 && Utils.SceptreTag.containsKey(mainhand))
                ManaHealSteal += ManaSkillLevelGet(data, 11) * 0.01;
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

            if (Utils.DevilEarthManaCurios.containsKey(player) && Utils.DevilEarthManaCurios.get(player)) ManaHealSteal += 0.05;

            ManaHealSteal += CuriosAttribute.AttributeValue(player,Utils.ManaHealthSteal,StringUtils.CuriosAttribute.ManaHealthSteal); // 新版饰品属性加成
            //请在上方添加
            ManaHealSteal *= playerFantasyAttributeEnhance(player);
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
            if (ArmorCount.LifeMana(player) >= 2) ManaRecover += 5;
            if (ArmorCount.ObsiMana(player) >= 2) ManaRecover += 5;
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
            if (SwordSkillLevelGet(data, 8) > 0 && Utils.SwordTag.containsKey(mainhand))
                ManaRecover += SwordSkillLevelGet(data, 8); // 洞悉（手持近战武器时，获得1额外法力回复）
            if (ManaSkillLevelGet(data, 1) > 0 && Utils.SceptreTag.containsKey(mainhand))
                ManaRecover += ManaSkillLevelGet(data, 1) * 0.5; // 仙女护符（持有法杖时，获得额外0.5点法力回复）
            if (ManaSkillLevelGet(data, 8) > 0 && Utils.SceptreTag.containsKey(mainhand))
                ManaRecover += ManaSkillLevelGet(data, 8); // 洞悉（手持法杖时，获得1额外法力回复）

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

            ManaRecover += CuriosAttribute.AttributeValue(player,Utils.ManaRecover,StringUtils.CuriosAttribute.ManaRecover); // 新版饰品属性加成

            ManaRecover += ShangMengLiCurios.ManaRecover(player);
            // 请在上方添加
            ManaRecover *= playerFantasyAttributeEnhance(player);

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
                ExDefence += ForgingValue(helmetTag,Utils.ManaDefence.get(helmet));
            if (Utils.ManaDefence.containsKey(chest) && chestTag.contains("Forging"))
                ExDefence += ForgingValue(chestTag,Utils.ManaDefence.get(chest));
            if (Utils.ManaDefence.containsKey(leggings) && leggingsTag.contains("Forging"))
                ExDefence += ForgingValue(leggingsTag,Utils.ManaDefence.get(leggings));
            if (Utils.ManaDefence.containsKey(boots) && bootsTag.contains("Forging"))
                ExDefence += ForgingValue(bootsTag,Utils.ManaDefence.get(boots));
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

            ExDefence += CuriosAttribute.AttributeValue(player,Utils.ManaDefence,StringUtils.CuriosAttribute.ManaDefence); // 新版饰品属性加成
            ExDefence += CastleAttackArmor.ExAttributeValue(player,CastleAttackArmor.ExManaDefence);
            ExDefence += CastleManaArmor.ExAttributeValue(player,CastleManaArmor.ExManaDefence);
            ExDefence += CastleSwiftArmor.ExAttributeValue(player,CastleSwiftArmor.ExManaDefence);
            // 请在上方添加

            ExDefence *= (1 + EarthPower.PlayerDefenceEnhance(player));
            Defence *= (1 + EarthPower.PlayerDefenceEnhance(player));

            Defence *= playerFantasyAttributeEnhance(player);
            ExDefence *= playerFantasyAttributeEnhance(player);

            if (Defence + ExDefence < 0) return 0;
            return Defence + ExDefence;
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
            HealSteal += SArmorHealSteal(player);

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

            HealSteal += CuriosAttribute.AttributeValue(player,Utils.HealthSteal,StringUtils.CuriosAttribute.HealthSteal); // 新版饰品属性加成

            // 请在上方添加

            HealSteal *= playerFantasyAttributeEnhance(player);

            if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaKnife.get())) {
                data.putDouble("HealthStealAfterCompute",HealSteal);
                return 0;
            } // 猎魔者小刀

            if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.ManaShield.get())) {
                if (player.getHealth() / player.getMaxHealth() > 0.5) {
                    data.putDouble("HealthStealAfterCompute",HealSteal);
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

            DefenceRate *= (1 - CuriosAttribute.AttributeValue(player,Utils.ManaPenetration,StringUtils.CuriosAttribute.ManaPenetration)); // 新版饰品属性加成
            DecreaseRate *= (1 - BlackFeisaCurios4.Penetration(player));
            // 请在上方添加
            DefenceRate *= (2 - playerFantasyAttributeEnhance(player));

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

            if (ArmorCount.Volcano(player) >= 4) ManaPenetration0 += 35;

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

            ManaPenetration0 += CuriosAttribute.AttributeValue(player,Utils.ManaPenetration0,StringUtils.CuriosAttribute.ManaPenetration0); // 新版饰品属性加成

            ManaPenetration0 += TabooManaArmor.storeCostValue(player);
            ManaPenetration0 += TabooManaArmor.PenetrationDirectEnhance(player);

            ManaPenetration0 += PassiveEquip.AttributeGet(player,Utils.ManaPenetration0); // 器灵属性加成
            ManaPenetration0 += CastleSword.ExPenetration0(player); // 暗黑武器主动
            ManaPenetration0 += ManaRune3.ManaPenetration0(player); // 法师之威
            // 请在上方添加
            ManaPenetration0 *= playerFantasyAttributeEnhance(player);
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

            MaxMana += CuriosAttribute.AttributeValue(player,Utils.MaxMana,StringUtils.CuriosAttribute.MaxMana); // 新版饰品属性加成

            MaxMana += PassiveEquip.AttributeGet(player,Utils.MaxMana); // 器灵属性加成

            // 请在上方添加
            if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore
                    && Compute.PlayerAttributes.PlayerDefence(player) > 1000) {
                MaxMana *= 1.25;
            }

            if (mainhand.equals(ModItems.LiuLiXianSceptre.get()) || mainhand.equals(ModItems.LiuLiXianSword.get())) {
                MaxMana += PlayerMovementSpeed(player) * 25;
                MaxMana *= 1.1;
            }
            MaxMana *= LiulixianCurios3.MaxManaUp(player);
            MaxMana *= ShangMengLiSword.MaxManaEnhance(player);
            MaxMana *= playerFantasyAttributeEnhance(player);
            MaxMana *= FengXiaoJuCurios1.MaxMana(player);

            return MaxMana;
        }

    }

    public static double MonsterDefence(Mob monster) {
        int TickCount = monster.getServer().getTickCount();
        double Defence = 0.0d;
        double ExDefence = 0;
        CompoundTag data = monster.getPersistentData();
        Iterator<ItemStack> iterator = monster.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();
        if (Utils.Defence.containsKey(boots)) Defence += Utils.Defence.get(boots);
        if (Utils.Defence.containsKey(leggings)) Defence += Utils.Defence.get(leggings);
        if (Utils.Defence.containsKey(chest)) Defence += Utils.Defence.get(chest);
        if (Utils.Defence.containsKey(helmet)) Defence += Utils.Defence.get(helmet);
/*        if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
            ExDefence -= EntropyRate(data.getInt(StringUtils.ForestBossSwordActive.Pare)) * 20;
        }*/
        if (data.getInt(StringUtils.Entropy.Snow) > TickCount) {
            ExDefence -= Defence * data.getDouble(StringUtils.SnowBossSwordActive.Pare);
        }
        if (Utils.MobSpringAttackTick.containsKey(monster) && Utils.MobSpringAttackTick.get(monster) > TickCount) {
            ExDefence -= Defence * Utils.SpringEffect[Utils.MobSpringAttackEffect.get(monster) - 1];
        }
        if (Utils.MobSpringSwiftTick.containsKey(monster) && Utils.MobSpringSwiftTick.get(monster) > TickCount) {
            ExDefence -= Defence * Utils.SpringEffect[Utils.MobSpringSwiftEffect.get(monster) - 1];
        }
        if (Utils.CrushDefenceDecreaseMap.containsKey(monster) && Utils.CrushDefenceDecreaseMap.get(monster) > TickCount) {
            ExDefence -= Defence * 0.5;
        }

        if (Utils.SnowShieldMobEffectMap.containsKey(monster) && Utils.SnowShieldMobEffectMap.get(monster) > TickCount) {
            ExDefence -= Defence * 0.25;
        }
        if (LiuLiXianCurios1F.Skill7Mob != null && LiuLiXianCurios1F.Skill7Mob.equals(monster) && LiuLiXianCurios1F.Skill7Tick > TickCount) ExDefence += Defence * 0.2;
        if (Defence + ExDefence < 0) return 0;

        double totalDefence = Defence + ExDefence;
        totalDefence *= Element.ElementDefenceDecrease(monster);
        totalDefence *= WaterElementSword.MobDefenceDecrease(monster);

        return totalDefence;
    }

    public static void ManaStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        double MaxMana = data.getDouble("MAXMANA");
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand))
            MaxMana *= (1 - Compute.ManaSkillLevelGet(data, 10) * 0.1);
        ModNetworking.sendToClient(new ManaSyncS2CPacket(MaxMana, data.getDouble("MANA")), (ServerPlayer) player);
    }

    public static void SwiftStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new SwiftSyncS2CPacket(100, data.getDouble(StringUtils.Swift)), (ServerPlayer) player);
    }

    public static boolean PlayerSwiftChange(Player player, double Num) {
        CompoundTag data = player.getPersistentData();
        double CurrentSwift = data.getDouble(StringUtils.Swift);
        double MaxSwift = data.getDouble(StringUtils.MaxSwift);
        if (CurrentSwift + Num < 0) return false;
        else {
            data.putDouble(StringUtils.Swift, min(MaxSwift, CurrentSwift + Num));
            SwiftStatusUpdate(player);
            return true;
        }
    }

    public static double MonsterManaDefence(Mob monster) {
        int TickCount = monster.getServer().getTickCount();
        double Defence = 0.0d;
        double ExDefence = 0;
        CompoundTag data = monster.getPersistentData();
        Iterator<ItemStack> iterator = monster.getArmorSlots().iterator();
        Item boots = iterator.next().getItem();
        Item leggings = iterator.next().getItem();
        Item chest = iterator.next().getItem();
        Item helmet = iterator.next().getItem();

        if (Utils.ManaDefence.containsKey(boots)) Defence += Utils.ManaDefence.get(boots);
        if (Utils.ManaDefence.containsKey(leggings)) Defence += Utils.ManaDefence.get(leggings);
        if (Utils.ManaDefence.containsKey(chest)) Defence += Utils.ManaDefence.get(chest);
        if (Utils.ManaDefence.containsKey(helmet)) Defence += Utils.ManaDefence.get(helmet);

        if (data.contains("ManaRune2") && data.getInt("ManaRune2") > 0) Defence = 0;
        if (Utils.LakePowerEffectMobMap.containsKey(monster) && Utils.LakePowerEffectMobMap.get(monster).getTick() > TickCount) {
            Defence *= (1 - Utils.LakePowerEffectMobMap.get(monster).getEffect() * 0.1);
        }
/*        if (data.contains(StringUtils.ForestBossSwordActive.Pare) && data.getInt(StringUtils.ForestBossSwordActive.PareTime) > TickCount) {
            ExDefence -= data.getInt(StringUtils.ForestBossSwordActive.Pare) * 10;
        }*/
        if (Utils.MobSpringManaTick.containsKey(monster) && Utils.MobSpringManaTick.get(monster) > TickCount) {
            ExDefence -= Defence * Utils.SpringEffect[Utils.MobSpringManaEffect.get(monster) - 1];
        }
        if (Utils.NetherBoneMealPowerEffectMap.containsKey(monster) && Utils.NetherBoneMealPowerEffectMap.get(monster) > TickCount) {
            ExDefence -= Defence * 0.5;
        }
        if (Utils.ShangMengLiSkillEffectMob.containsKey(monster) && Utils.ShangMengLiSkillEffectMob.get(monster) > TickCount) {
            ExDefence -= Defence * 0.5;
        }
        if (Utils.WitherBookMobEffectTick.containsKey(monster) && Utils.WitherBookMobEffectTick.get(monster) > TickCount) {
            ExDefence -= Defence * 0.5;
        }
        ExDefence += Defence * EarthPower.MobManaDefenceDecrease(monster); // 地蕴法术
        if (LiuLiXianCurios1F.Skill7Mob != null && LiuLiXianCurios1F.Skill7Mob.equals(monster) && LiuLiXianCurios1F.Skill7Tick > TickCount) ExDefence += Defence * 0.2;
        Defence += ExDefence;
        if (Defence < 0) return 0;

        Defence *= Element.ElementDefenceDecrease(monster); // 元素
        Defence *= WaterElementSword.MobDefenceDecrease(monster);
        return Defence;
    }

    public static double PlayerCritDamageDecrease(Player player) {
        CompoundTag data = player.getPersistentData();
        double CritDamageDecrease = 0.0d;
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
        if (Utils.MainHandTag.containsKey(mainhand) && stackmainhandtag.contains("CritDamageDecrease"))
            CritDamageDecrease += stackmainhandtag.getDouble("CritDamageDecrease");
        if (Utils.CritDamageDecrease.containsKey(boots)) CritDamageDecrease += Utils.CritDamageDecrease.get(boots);
        if (Utils.CritDamageDecrease.containsKey(leggings))
            CritDamageDecrease += Utils.CritDamageDecrease.get(leggings);
        if (Utils.CritDamageDecrease.containsKey(chest)) CritDamageDecrease += Utils.CritDamageDecrease.get(chest);
        if (Utils.CritDamageDecrease.containsKey(helmet)) CritDamageDecrease += Utils.CritDamageDecrease.get(helmet);
        if (Utils.MainHandTag.containsKey(mainhand) && Utils.CritDamageDecrease.containsKey(mainhand))
            CritDamageDecrease += Utils.CritDamageDecrease.get(mainhand);
        if (Utils.OffHandTag.containsKey(offhand) && Utils.CritDamageDecrease.containsKey(offhand))
            CritDamageDecrease += Utils.CritDamageDecrease.get(offhand);
        if (ArmorCount.Mine(player) >= 2) CritDamageDecrease += 0.5;
        return CritDamageDecrease;
    }

    public static void PlayerManaAddOrCost(Player player, double Mana) {
        CompoundTag data = player.getPersistentData();
        if (Mana > 0) data.putDouble("MANA", Math.min(data.getDouble("MANA") + Mana, data.getDouble("MAXMANA")));
        else data.putDouble("MANA", Math.max(data.getDouble("MANA") + Mana, 0));
        ManaStatusUpdate(player);
    }

    public static double PlayerCurrentManaNum(Player player) {
        return player.getPersistentData().getDouble("MANA");
    }

    public static double PlayerMaxManaNum(Player player) {
        return player.getPersistentData().getDouble("MAXMANA");
    }

    public static double PlayerLuckyUp(Player player) {
        double LuckyUp = 0;
        CompoundTag data = player.getPersistentData();
        Item Helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item Chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item Leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item Boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.LuckyUp.containsKey(Helmet)) LuckyUp += Utils.LuckyUp.get(Helmet);
        if (Utils.LuckyUp.containsKey(Chest)) LuckyUp += Utils.LuckyUp.get(Chest);
        if (Utils.LuckyUp.containsKey(Leggings)) LuckyUp += Utils.LuckyUp.get(Leggings);
        if (Utils.LuckyUp.containsKey(Boots)) LuckyUp += Utils.LuckyUp.get(Boots);
        int LuckyAbilityPoint = data.getInt(StringUtils.Ability.Lucky);
        if (data.contains(StringUtils.Ability.Lucky) && data.getInt(StringUtils.Ability.Lucky) > 0) {
            int Point = LuckyAbilityPoint + (LuckyAbilityPoint / 10) * 5;
            LuckyUp += Point * 0.01;
        }

        CompoundTag helmetTag = player.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag chestTag = player.getItemBySlot(EquipmentSlot.CHEST).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag leggingsTag = player.getItemBySlot(EquipmentSlot.LEGS).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag bootsTag = player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTagElement(Utils.MOD_ID);
        CompoundTag stackmainhandtag = new CompoundTag();
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getTagElement(Utils.MOD_ID) != null) {
            stackmainhandtag = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
        }

        if (helmetTag.contains("Gems1")) LuckyUp += Compute.ItemLuckyUpGems(helmetTag);
        if (chestTag.contains("Gems1")) LuckyUp += Compute.ItemLuckyUpGems(chestTag);
        if (leggingsTag.contains("Gems1")) LuckyUp += Compute.ItemLuckyUpGems(leggingsTag);
        if (bootsTag.contains("Gems1")) LuckyUp += Compute.ItemLuckyUpGems(bootsTag);
        if (stackmainhandtag.contains("Gems1") && Utils.MainHandTag.containsKey(mainhand))
            LuckyUp += Compute.ItemLuckyUpGems(stackmainhandtag);

        LuckyUp += CuriosAttribute.AttributeValue(player,Utils.LuckyUp,StringUtils.CuriosAttribute.LuckyUp); // 新版饰品属性加成


        return Math.min(LuckyUp, 0.5);
    }

    public static double SwordSkill1And4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.SwordTag.containsKey(mainhand)) {
            Decrease += Compute.SwordSkillLevelGet(data, 1) * 0.01;
            Decrease -= Compute.SwordSkillLevelGet(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double SwordSkill14(CompoundTag data, Player player, LivingEntity monster) {
        double Enhance = 0;
        if (SwordSkillLevelGet(data, 14) > 0) {
            double PlayerHealthRate = player.getHealth() / player.getMaxHealth();
            double MonsterHealthRate = monster.getHealth() / monster.getMaxHealth();
            if (PlayerHealthRate < MonsterHealthRate) {
                Enhance += 0.2 * Math.min(1.0, (MonsterHealthRate - PlayerHealthRate) / 0.66);
            }
        }
        return Enhance;
    }

    public static double BowSkill4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.BowTag.containsKey(mainhand)) {
            Decrease -= Compute.BowSkillLevelGet(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double ManaSkill4(CompoundTag data, Player player) {
        double Decrease = 0;
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.SceptreTag.containsKey(mainhand)) {
            Decrease -= Compute.ManaSkillLevelGet(data, 4) * 0.015;
        }
        return Decrease;
    }

    public static double PlayerShieldDecrease(Player player, double value) {
        double TmpNum = value;
        List<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
        if (shieldQueue != null && !shieldQueue.isEmpty()) {
            Iterator<Shield> iterator1 = shieldQueue.iterator();
            while (iterator1.hasNext()) {
                Shield shield = iterator1.next();
                if (shield.getValue() > TmpNum) {
                    shield.setValue(shield.getValue() - TmpNum);
                    PlayerShieldCompute(player);
                    return 0;
                } else {
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

    public static void PlayerShieldProvider(Player player, int tick, double value) {
        Shield shield = new Shield(tick, value);
        List<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
        if (shieldQueue == null) {
            shieldQueue = new LinkedList<>();
            shieldQueue.add(shield);
            Utils.PlayerShieldQueue.put(player, shieldQueue);
        } else {
            shieldQueue.add(shield);
        }
        PlayerShieldCompute(player);
    }

    public static double PlayerShieldCompute(Player player) {
        List<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
        if (shieldQueue != null && !shieldQueue.isEmpty()) {
            Iterator<Shield> iterator0 = shieldQueue.iterator();
            double shieldValue = 0;
            while (iterator0.hasNext()) {
                Shield shield = iterator0.next();
                if (shield.getTick() > 0) shieldValue += shield.getValue();
                if (shield.getTick() == 0) {
                    YxwgCurios2.Passive6(player, shield.getValue());
                }
                if (shield.getTick() == 0 || shield.getValue() == 0) iterator0.remove();
            }
            double tmpShieldNum = 9.0 * (shieldValue * 1.0 / player.getMaxHealth());
            ModNetworking.sendToClient(new ShieldSyncS2CPacket((int) ceil(tmpShieldNum), (int) ceil(shieldValue)), (ServerPlayer) player);
            return shieldValue;
        }
        ModNetworking.sendToClient(new ShieldSyncS2CPacket(0, 0), (ServerPlayer) player);
        return 0;
    }

    public static double PlayerShieldClear(Player player) {
        double shieldValue = Compute.PlayerShieldCompute(player);
        Compute.PlayerShieldDecrease(player, shieldValue);
        return shieldValue;
    }

    public static double ItemBaseDamageGems(CompoundTag data) {
        double BaseDamage = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsAttackDamage.containsKey(data.getString("Gems" + i)))
                BaseDamage += Utils.GemsAttackDamage.get(data.getString("Gems" + i));
        }
        return BaseDamage;
    }

    public static double ItemMovementSpeedUpGems(CompoundTag data) {
        double SpeedUp = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsSpeedUp.containsKey(data.getString("Gems" + i)))
                SpeedUp += Utils.GemsSpeedUp.get(data.getString("Gems" + i));
        }
        return SpeedUp;
    }

    public static double ItemManaDamageGems(CompoundTag data) {
        double BaseDamage = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsManaDamage.containsKey(data.getString("Gems" + i)))
                BaseDamage += Utils.GemsManaDamage.get(data.getString("Gems" + i));
        }
        return BaseDamage;
    }

    public static double ItemManaRecoverGems(CompoundTag data) {
        double Recover = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsManaRecover.containsKey(data.getString("Gems" + i)))
                Recover += Utils.GemsManaRecover.get(data.getString("Gems" + i));
        }
        return Recover;
    }

    public static double ItemHealRecoverGems(CompoundTag data) {
        double Recover = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsHealthRecover.containsKey(data.getString("Gems" + i)))
                Recover += Utils.GemsHealthRecover.get(data.getString("Gems" + i));
        }
        return Recover;
    }

    public static double ItemMaxHealthGems(CompoundTag data) {
        double MaxHealth = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsMaxHealth.containsKey(data.getString("Gems" + i)))
                MaxHealth += Utils.GemsMaxHealth.get(data.getString("Gems" + i));
        }
        return MaxHealth;
    }

    public static double ItemDefenceGems(CompoundTag data) {
        double Defence = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsDefence.containsKey(data.getString("Gems" + i)))
                Defence += Utils.GemsDefence.get(data.getString("Gems" + i));
        }
        return Defence;
    }

    public static double ItemCoolDownGems(CompoundTag data) {
        double CoolDown = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsCoolDown.containsKey(data.getString("Gems" + i)))
                CoolDown += Utils.GemsCoolDown.get(data.getString("Gems" + i));
        }
        return CoolDown;
    }

    public static double ItemCritDamageGems(CompoundTag data) {
        double CritDamage = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsCritDamage.containsKey(data.getString("Gems" + i)))
                CritDamage += Utils.GemsCritDamage.get(data.getString("Gems" + i));
        }
        return CritDamage;
    }

    public static double ItemCritRateGems(CompoundTag data) {
        double CritRate = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsCritRate.containsKey(data.getString("Gems" + i)))
                CritRate += Utils.GemsCritRate.get(data.getString("Gems" + i));
        }
        return CritRate;
    }

    public static double ItemHealStrengthGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsHealStrength.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsHealStrength.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemManaHealthStealGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsManaHealthSteal.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsManaHealthSteal.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemDefencePenetration0Gems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsDefencePenetration0.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsDefencePenetration0.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemManaPenetration0Gems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsManaPenetration0.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsManaPenetration0.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemExpUpGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsExpUp.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsExpUp.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemLuckyUpGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsLuckyUp.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsLuckyUp.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemDefencePenetrationGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsDefencePenetration.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsDefencePenetration.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemManaPenetrationGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsManaPenetration.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsManaPenetration.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemHealthStealGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsHealthSteal.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsHealthSteal.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static double ItemManaDefenceGems(CompoundTag data) {
        double value = 0;
        for (int i = 1 ; i <= 3 ; i ++) {
            if (data.contains("Gems" + i) && Utils.GemsManaDefence.containsKey(data.getString("Gems" + i)))
                value += Utils.GemsManaDefence.get(data.getString("Gems" + i));
        }
        return value;
    }

    public static void ForgingHoverName(ItemStack stack, Component component) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);

        MutableComponent GemComponent = Component.literal("");
        MutableComponent Suffix = Component.literal("");
        MutableComponent Prefix = Component.literal("");

        if (data.contains(StringUtils.ForgeNum)) {
            Prefix.append(Component.literal("[").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("#").withStyle(CustomStyle.styleOfSky)).
                    append(Component.literal("" + data.getInt(StringUtils.ForgeNum)).withStyle(CustomStyle.styleOfDemon)).
                    append(Component.literal("]").withStyle(ChatFormatting.AQUA)));
        }

        if (Utils.SceptreTag.containsKey(stack.getItem())) {
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                String ManaCore = data.getString(StringUtils.ManaCore.ManaCore);
                if (ManaCore.equals(StringUtils.ManaCore.SeaCore))
                    Suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfSea)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
                if (ManaCore.equals(StringUtils.ManaCore.BlackForestCore))
                    Suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfBlackForest)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
                if (ManaCore.equals(StringUtils.ManaCore.KazeCore))
                    Suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfKaze)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
                if (ManaCore.equals(StringUtils.ManaCore.SakuraCore))
                    Suffix = Component.literal(" <").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("●").withStyle(CustomStyle.styleOfDemon)).
                            append(Component.literal(">").withStyle(ChatFormatting.GRAY));
            }
        }
        for (int i = 1; i <= 3; i++) {
            String Gems = null;
            if (data.contains("Gems" + i)) Gems = data.getString("Gems" + i);
            if (Gems != null)
                GemComponent.append(Component.literal("[").withStyle(CustomStyle.styleOfPower).
                        append(Component.literal("◈").withStyle(Utils.gemStringStyleMap.get(Gems))).
                        append(Component.literal("]").withStyle(CustomStyle.styleOfPower)));
        }

        if (data.contains("MaxSlot") && data.contains("Slot")) {
            for (int i = 0; i < data.getInt("Slot"); i++) {
                GemComponent.append(Component.literal("[ ]").withStyle(CustomStyle.styleOfPower));
            }
        }

        GemComponent.withStyle(ChatFormatting.BOLD);
        if (data.contains("Forging")) {
            int forgeLevel = data.getInt("Forging");
            if (data.contains(StringUtils.QingMingForgePaper)) ++ forgeLevel;
            if (forgeLevel <= 10) stack.setHoverName(Component.literal("")
                    .append(Prefix)
                    .append(stack.getItem().getDefaultInstance().getHoverName())
                    .append(Component.literal("[").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("+" + String.valueOf(forgeLevel)).withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("]").withStyle(ChatFormatting.AQUA)).append(GemComponent).append(Suffix));
            else {
                if (forgeLevel <= 20) stack.setHoverName(Component.literal("")
                        .append(Prefix)
                        .append(stack.getItem().getDefaultInstance().getHoverName())
                        .append(Component.literal("[").withStyle(ChatFormatting.AQUA))
                        .append(Component.literal("+" + String.valueOf(forgeLevel)).withStyle(ChatFormatting.GOLD))
                        .append(Component.literal("]").withStyle(ChatFormatting.AQUA)).append(GemComponent).append(Suffix));
                else {
                    stack.setHoverName(Component.literal("")
                            .append(Prefix)
                            .append(stack.getItem().getDefaultInstance().getHoverName())
                            .append(Component.literal("[").withStyle(ChatFormatting.AQUA))
                            .append(Component.literal("+" + String.valueOf(forgeLevel)).withStyle(ChatFormatting.LIGHT_PURPLE))
                            .append(Component.literal("]").withStyle(ChatFormatting.AQUA)).append(GemComponent).append(Suffix));
                }
            }
        } else {
            stack.setHoverName(Component.literal("")
                    .append(Prefix)
                    .append(stack.getItem().getDefaultInstance().getHoverName())
                    .append(GemComponent)
                    .append(Suffix));
        }
        /*        else stack.setHoverName(Component.literal("").append(component));*/
    }

    public static void Drops(Player player, Mob monster, Item monsterhelmet, ItemStack equip) throws IOException {
        CompoundTag data = player.getPersistentData();
        Random r = new Random();
        double ExpUp = Compute.PlayerAttributes.PlayerExpUp(player);
        double LuckyUp = Compute.PlayerLuckyUp(player);
        int num = 1;
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19) num = 2;
        Proficiency(equip, monsterhelmet);

        if (monsterhelmet.equals(ModItems.MobArmorEndStrayHelmet.get())) {
            EndCrystal.Drop(player, monster);
        }

        if (monsterhelmet.equals(ModItems.MobArmorShulkerHelmet.get())) {
            Drops.Shulker(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorEnderMiteHelmet.get())) {
            Drops.Endermite(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginLifeElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.LifeElementPiece1.get());
            Drops.LifeElement(player,4);
            if (r.nextDouble() < 0.007) {
                Item[] items = {ModItems.LifeElementSwordForgeDraw.get(), ModItems.LifeElementBowForgeDraw.get(), ModItems.LifeElementSceptreForgeDraw.get()};
                ItemStack itemStack = items[r.nextInt(items.length)].getDefaultInstance();
                Compute.FormatBroad(player.level(), Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了:").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                Compute.ItemStackGive(player, itemStack);
            }
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginWaterElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.WaterElementPiece1.get());
            Drops.WaterElement(player,4);
            if (r.nextDouble() < 0.007) {
                Item[] items = {ModItems.WaterElementSwordForgeDraw.get(), ModItems.WaterElementBowForgeDraw.get(), ModItems.WaterElementSceptreForgeDraw.get()};
                ItemStack itemStack = items[r.nextInt(items.length)].getDefaultInstance();
                Compute.FormatBroad(player.level(), Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了:").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                Compute.ItemStackGive(player, itemStack);
            }
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginFireElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.FireElementPiece1.get());
            Drops.FireElement(player,4);
            if (r.nextDouble() < 0.007) {
                Item[] items = {ModItems.FireElementSwordForgeDraw.get(), ModItems.FireElementBowForgeDraw.get(), ModItems.FireElementSceptreForgeDraw.get()};
                ItemStack itemStack = items[r.nextInt(items.length)].getDefaultInstance();
                Compute.FormatBroad(player.level(), Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了:").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                Compute.ItemStackGive(player, itemStack);
            }
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginStoneElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.StoneElementPiece1.get());
            Drops.StoneElement(player,4);
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginIceElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.IceElementPiece1.get());
            Drops.IceElement(player,4);
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginLightningElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.LightningElementPiece1.get());
            Drops.LightningElement(player,4);
        }

        if (monsterhelmet.equals(ModItems.MobArmorOriginWindElement.get())) {
            OriginSummon.Drops(player, monster,ModItems.WindElementPiece1.get());
            Drops.WindElement(player,4);
        }

        if (monsterhelmet.equals(ModItems.MobArmorLifeElementHelmet.get())) {
            Drops.LifeElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorWindElementHelmet.get())) {
            Drops.WindElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorStoneElementHelmet.get())) {
            Drops.StoneElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorWaterElementHelmet.get())) {
            Drops.WaterElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorLightningElementHelmet.get())) {
            Drops.LightningElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorFireElementHelmet.get())) {
            Drops.FireElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorIceElementHelmet.get())) {
            Drops.IceElement(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorStar1.get())) {
            Drops.Star1(player, num);
        }

        if (monsterhelmet.equals(ModItems.MobArmorStar.get())) {
            if (r.nextDouble() < 0.05) StarEvent.SummonStar1(player.level(),monster.position());
            Drops.Star(player, num);
        }

/*        if (monsterhelmet instanceof MobArmor mobArmor) {
            Compute.RateItemStackGive(ModItems.QingTuan.get().getDefaultInstance(),0.0025 * (1 + mobArmor.MobLevel / 50),player);
        } // 青团*/

        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SoulSword.get())) {
            CompoundTag ItemData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            ItemData.putInt(StringUtils.SoulSwordKillCount, ItemData.getInt(StringUtils.SoulSwordKillCount) + 1);
        }

        Utils.dayKillCount.put(player.getName().getString(), Utils.dayKillCount.getOrDefault(player.getName().getString(), 0) + 1);
        for (int i = 0; i < Utils.instanceKillCount.length; i++) {
            if (Utils.instanceList.get(i).getMobList() != null && Utils.instanceList.get(i).getMobList().contains(monster))
                Utils.instanceKillCount[i]++;
        }

        if (monsterhelmet.equals(ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get())
                || monsterhelmet.equals(ModItems.MobArmorBlackCastleOneFloorManaHelmet.get())) {
            List<Player> playerList = Castle.getCastlePlayerList(player.level());
            if (playerList != null) {
                playerList.forEach(player1 -> {
                    Compute.ExpPercentGetAndMSGSend(player1,0.01,Compute.PlayerAttributes.PlayerExpUp(player1),140);

                    try {
                        Compute.ItemStackGive(player1,ModItems.CastleSoul.get().getDefaultInstance());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            }
        }
        if (monsterhelmet.equals(ModItems.MobArmorBeaconHelmet.get())) {
            Drops.Beacon(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorBlazeHelmet.get())) {
            Drops.Blaze(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorTreeHelmet.get())) {
            Drops.Tree(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorPlain.get())) {
            Drops.PlainZombie(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorForestSkeleton.get())) {
            Drops.ForestSkeleton(player, num);
            if (Utils.ForestRecall.RecallPlayer != null && Utils.ForestRecall.RecallPlayer != null && Utils.ForestRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.ForestRecall.KillCount != -1) {
                Utils.ForestRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("森林骷髅/森林僵尸击杀数:" + (40 - Utils.ForestRecall.KillCount) + "/"
                                + "40").withStyle(CustomStyle.styleOfForest));
                Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorForestZombie.get())) {
            Drops.ForestZombie(player, num);
            if (Utils.ForestRecall.RecallPlayer != null && Utils.ForestRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.ForestRecall.KillCount != -1) {
                Utils.ForestRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("森林骷髅/森林僵尸击杀数:" + (40 - Utils.ForestRecall.KillCount) + "/"
                                + "40").withStyle(CustomStyle.styleOfForest));
                Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorDrown.get())) {
            Drops.LakeDrown(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorBlaze.get())) {
            Drops.VolcanoBlaze(player, num);
            if (Utils.VolcanoRecall.RecallPlayer != null && Utils.VolcanoRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.VolcanoRecall.KillCount != -1) {
                Utils.VolcanoRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("火山烈焰击杀数:" + (20 - Utils.VolcanoRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfVolcano));
                Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorMine.get())) {
            Drops.Mine(player, 1, true);
            if (monster instanceof Zombie && data.contains("DailyMission6")) {
                if (!data.contains("KillCountOfMineZombie")) data.putInt("KillCountOfMineZombie", 1);
                else data.putInt("KillCountOfMineZombie", data.getInt("KillCountOfMineZombie") + 1);
                data.putInt("DailyMission6", data.getInt("DailyMission6") + 1);
            }
            if (monster instanceof Skeleton && data.contains("DailyMission7")) {
                if (!data.contains("KillCountOfMineSkeleton")) data.putInt("KillCountOfMineSkeleton", 1);
                else data.putInt("KillCountOfMineSkeleton", data.getInt("KillCountOfMineSkeleton") + 1);
                data.putInt("DailyMission7", data.getInt("DailyMission7") + 1);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorField.get())) {
            Drops.Field(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorSnow.get())) {
            Drops.SnowStray(player, num);
            if (Utils.SnowRecall.RecallPlayer != null && Utils.SnowRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.SnowRecall.KillCount != -1) {
                Utils.SnowRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("冰川流浪者击杀数:" + (20 - Utils.SnowRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfSnow));
                Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorSky.get())) {
            Drops.SkyVex(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorEvoker.get())) {
            Drops.Evoker(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorEvokerMaster.get())) {
            Drops.EvokerMaster(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorGuardian.get())) {
            Drops.SeaGuardian(player, num);
            if (Utils.SeaRecall.RecallPlayer != null && Utils.SeaRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.SeaRecall.KillCount != -1) {
                Utils.SeaRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("神殿守卫击杀数:" + (20 - Utils.SeaRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfSea));
                Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorLZHelmet.get())) {
            Drops.LightingZombie(player, num);
            if (Utils.LightningRecall.RecallPlayer != null && Utils.LightningRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.LightningRecall.KillCount != -1) {
                Utils.LightningRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("唤雷守卫击杀数:" + (20 - Utils.LightningRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfLightingIsland));
                Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorHusk.get())) {
            Drops.Husk(player, num);
            if (Utils.HuskRecall.RecallPlayer != null && Utils.HuskRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.HuskRecall.KillCount != -1) {
                Utils.HuskRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("脆弱的灵魂击杀数:" + (20 - Utils.HuskRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfBlackForest));
                Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorKazeHelmet.get())) {
            Drops.Kaze(player, num);
            if (Utils.KazeRecall.RecallPlayer != null && Utils.KazeRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.KazeRecall.KillCount != -1) {
                Utils.KazeRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("狂风击杀数:" + (20 - Utils.KazeRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfKaze));
                Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorSpider.get())) {
            Drops.Spider(player, num);
            if (Utils.SpiderRecall.RecallPlayer != null && Utils.SpiderRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.SpiderRecall.KillCount != -1) {
                Utils.SpiderRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("微光蜘蛛击杀数:" + (20 - Utils.SpiderRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfSpider));
                Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorSilverFish.get())) {
            Drops.SilverFish(player, num);
        }

        if (monsterhelmet.equals(ModItems.ArmorWitherSkeleton.get())) {
            Drops.WitherSkeleton(player, num);
            if (Utils.NetherRecall.RecallPlayer != null && Utils.NetherRecall.RecallPlayer.equals((ServerPlayer) player) && Utils.NetherRecall.KillCount != -1) {
                Utils.NetherRecall.KillCount--;
                ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                        new ClientboundSetActionBarTextPacket(Component.literal("下界凋零骷髅击杀数:" + (20 - Utils.NetherRecall.KillCount) + "/"
                                + "20").withStyle(CustomStyle.styleOfNether));
                Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetActionBarTextPacket);
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorZombiePiglin.get())) {
            Drops.Piglin(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorNetherSkeletonHelmet.get())) {
            Drops.NetherSkeleton(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorMagma.get())) {
            Drops.NetherMagma(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorEnderMan.get())) {
            Drops.EnderMan(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorMoonAttack.get())) {
            Compute.RateItemStackGive(ModItems.MoonAttackGem.get().getDefaultInstance(), 0.0025, player);
        }
        if (monsterhelmet.equals(ModItems.MobArmorMoonMana.get())) {
            Compute.RateItemStackGive(ModItems.MoonManaGem.get().getDefaultInstance(), 0.0025, player);
        }
        if (monsterhelmet.equals(ModItems.ArmorPFH.get())) {
            if (data.getInt("PFTIME") > 0) {

                RateItemStackGive(ModItems.GemPiece.get().getDefaultInstance(), 0.045, player);

                ItemStack itemStack00 = ModItems.SunPower.get().getDefaultInstance();

                Compute.RateItemStackGive(itemStack00, 0.1, player);

                Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());

                Utils.PFSecKillCount++;

                if (Utils.MainHandTag.containsKey(equip.getItem())) {
                    CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
                    if (!dataI.contains(StringUtils.KillCount.KillCount))
                        dataI.putInt(StringUtils.KillCount.KillCount, Utils.MobLevel.get(monsterhelmet).intValue() * 5);
                    else
                        dataI.putInt(StringUtils.KillCount.KillCount, dataI.getInt(StringUtils.KillCount.KillCount) + Utils.MobLevel.get(monsterhelmet).intValue() * 5);
                }

                if (!data.contains("KillCountOfPF")) data.putInt("KillCountOfPF", 1);
                else data.putInt("KillCountOfPF", data.getInt("KillCountOfPF") + 1);

                if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
                else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);

            } else {
                player.teleportTo(214, 102, 1074);
                Compute.FormatMSGSend(player, Component.literal("隐藏副本").
                        withStyle(CustomStyle.styleOfHealth), Component.literal("副本未向你激活，这将不会获得奖励。"));
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorSVH.get())) {
            if (data.getInt("SVTIME") > 0) {


                ItemStack itemStack0 = ModItems.GemPiece.get().getDefaultInstance();
                if (r.nextDouble(1.0d) < 0.05 * (1 + LuckyUp)) Compute.ItemStackGive(player, itemStack0);

                ItemStack itemStack00 = ModItems.VolcanoCore.get().getDefaultInstance();
                RateItemStackGive(itemStack00, 0.1, player);

                Utils.VolcanoSecKillCount++;

/*                ItemStack itemStack2 = Moditems.SunPower.get().getDefaultInstance();

                if(r.nextDouble(1.0d) < 0.25) Compute.ItemStackGive(player,itemStack2);*/

                Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());

                if (Utils.MainHandTag.containsKey(equip.getItem())) {
                    CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
                    if (!dataI.contains(StringUtils.KillCount.KillCount))
                        dataI.putInt(StringUtils.KillCount.KillCount, Utils.MobLevel.get(monsterhelmet).intValue() * 5);
                    else
                        dataI.putInt(StringUtils.KillCount.KillCount, dataI.getInt(StringUtils.KillCount.KillCount) + Utils.MobLevel.get(monsterhelmet).intValue() * 5);
                }

                if (!data.contains("KillCountOfSV")) data.putInt("KillCountOfSV", 1);
                else data.putInt("KillCountOfSV", data.getInt("KillCountOfSV") + 1);

                if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
                else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);

            } else {
                player.teleportTo(5, -54, 990);
                Compute.FormatMSGSend(player, Component.literal("隐藏副本").
                        withStyle(ChatFormatting.YELLOW), Component.literal("副本未向你激活，这将不会获得奖励。"));
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorSLH.get())) {
            if (data.getInt("SLTIME") > 0) {
                ItemStack itemStack = ModItems.LakeSoul.get().getDefaultInstance();
                itemStack.setCount(r.nextInt(8));

                Utils.LakeSecKillCount++;

                RateItemStackGive(ModItems.GemPiece.get().getDefaultInstance(), 0.05, player);

                ItemStack itemStack00 = ModItems.LakeCore.get().getDefaultInstance();
                if (r.nextDouble(1.0d) < 0.1 * (1 + LuckyUp)) {
                    Compute.FormatBroad(player.level(), Component.literal("稀有掉落").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString() + "获得了").withStyle(ChatFormatting.WHITE).
                                    append(itemStack00.getDisplayName()));
                    Compute.ItemStackGive(player, itemStack00);
                }

/*                ItemStack itemStack2 = Moditems.SunPower.get().getDefaultInstance();

                if(r.nextDouble(1.0d) < 0.25) Compute.ItemStackGive(player,itemStack2);*/

                Compute.ItemStackGive(player, itemStack);
                Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());


                if (!data.contains("KillCountOfSL")) data.putInt("KillCountOfSL", 1);
                else data.putInt("KillCountOfSL", data.getInt("KillCountOfSL") + 1);

                if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
                else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);

                if (!data.contains("KillCountOfSLC")) data.putInt("KillCountOfSLC", 1);
                else data.putInt("KillCountOfSLC", data.getInt("KillCountOfSLC") + 1);
            } else {
                player.teleportTo(66, -24, 982);
                Compute.FormatMSGSend(player, Component.literal("隐藏副本").
                        withStyle(ChatFormatting.BLUE), Component.literal("副本未向你激活，这将不会获得奖励。"));
            }
        }
        if (monsterhelmet.equals(ModItems.ArmorForestBoss.get())) {
            int BoundaryX1 = 126;
            int BoundaryX2 = -15;
            int BoundaryY1 = 90;
            int BoundaryY2 = 59;
            int BoundaryZ1 = 979;
            int BoundaryZ2 = 907;

            List<Player> list0 = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                    Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));
            for (Player player1 : list0) {
                Compute.FormatMSGSend(player1, Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                append(Utils.ForestBoss.getDisplayName()).
                                append(Component.literal("已被").withStyle(ChatFormatting.WHITE)).
                                append(player.getDisplayName()).
                                append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)));
            }

            RateItemStackGive(ModItems.ForestBossCore.get().getDefaultInstance(), 0.99, player);
            ItemStackGive(player, ModItems.ForestSoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());


            if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
            else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
            if (!data.contains(StringUtils.KillCount.ForestBoss)) data.putInt(StringUtils.KillCount.ForestBoss, 1);
            else data.putInt(StringUtils.KillCount.ForestBoss, data.getInt(StringUtils.KillCount.ForestBoss) + 1);
        }
        if (monsterhelmet.equals(ModItems.ArmorVolcanoBoss.get())) {
            int BoundaryX1 = 69;
            int BoundaryX2 = -12;
            int BoundaryY1 = -18;
            int BoundaryY2 = -57;
            int BoundaryZ1 = 1123;
            int BoundaryZ2 = 1007;

            List<Player> list0 = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                    Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));
            for (Player player1 : list0) {
                Compute.FormatMSGSend(player1, Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                append(Utils.VolcanoBoss.getDisplayName()).
                                append(Component.literal("已被").withStyle(ChatFormatting.WHITE)).
                                append(player.getDisplayName()).
                                append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)));
            }

            RateItemStackGive(ModItems.VolcanoBossCore.get().getDefaultInstance(), 0.99, player);
            ItemStackGive(player, ModItems.VolcanoSoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());


            if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
            else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
            if (!data.contains(StringUtils.KillCount.VolcanoBoss)) data.putInt(StringUtils.KillCount.VolcanoBoss, 1);
            else data.putInt(StringUtils.KillCount.VolcanoBoss, data.getInt(StringUtils.KillCount.VolcanoBoss) + 1);
        }
        if (monsterhelmet.equals(ModItems.ArmorLakeBoss.get())) {
            int BoundaryX1 = 84;
            int BoundaryX2 = -32;
            int BoundaryY1 = 65;
            int BoundaryY2 = 17;
            int BoundaryZ1 = 1014;
            int BoundaryZ2 = 926;

            List<Player> list0 = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                    Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));
            for (Player player1 : list0) {
                Compute.FormatMSGSend(player1, Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                append(monster.getDisplayName()).
                                append(Component.literal("已被").withStyle(ChatFormatting.WHITE)).
                                append(player.getDisplayName()).
                                append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)));
            }

            RateItemStackGive(ModItems.LakeBossCore.get().getDefaultInstance(), 0.99, player);
            ItemStackGive(player, ModItems.LakeSoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());


            if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
            else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
            if (!data.contains(StringUtils.KillCount.LakeBoss)) data.putInt(StringUtils.KillCount.LakeBoss, 1);
            else data.putInt(StringUtils.KillCount.LakeBoss, data.getInt(StringUtils.KillCount.LakeBoss) + 1);
        }
        if (monsterhelmet.equals(ModItems.ArmorSkyBoss.get())) {
            int BoundaryX1 = 49;
            int BoundaryX2 = 6;
            int BoundaryY1 = 200;
            int BoundaryY2 = 159;
            int BoundaryZ1 = 1040;
            int BoundaryZ2 = 998;

            List<Player> list0 = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                    Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));
            for (Player player1 : list0) {
                Compute.FormatMSGSend(player1, Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                append(monster.getDisplayName()).
                                append(Component.literal("已被").withStyle(ChatFormatting.WHITE)).
                                append(player.getDisplayName()).
                                append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)));
            }

            RateItemStackGive(ModItems.SkyBossCore.get().getDefaultInstance(), 0.99, player);
            ItemStackGive(player, ModItems.SkySoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());


            if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
            else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
            if (!data.contains(StringUtils.KillCount.SkyBoss)) data.putInt(StringUtils.KillCount.SkyBoss, 1);
            else data.putInt(StringUtils.KillCount.SkyBoss, data.getInt(StringUtils.KillCount.SkyBoss) + 1);
        }
        if (monsterhelmet.equals(ModItems.ArmorSnowBoss.get())) {
            int BoundaryX1 = -182;
            int BoundaryX2 = 0;
            int BoundaryY1 = 144;
            int BoundaryY2 = 210;
            int BoundaryZ1 = 1291;
            int BoundaryZ2 = 1499;

            List<Player> list0 = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                    Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));
            for (Player player1 : list0) {
                Compute.FormatMSGSend(player1, Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                append(monster.getDisplayName()).
                                append(Component.literal("已被").withStyle(ChatFormatting.WHITE)).
                                append(player.getDisplayName()).
                                append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)));
            }

            RateItemStackGive(ModItems.SnowBossCore.get().getDefaultInstance(), 0.99, player);
            ItemStackGive(player, ModItems.SnowSoul.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, Utils.MobLevel.get(monsterhelmet).intValue());

            if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
            else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
            if (!data.contains(StringUtils.KillCount.SnowBoss)) data.putInt(StringUtils.KillCount.SnowBoss, 1);
            else data.putInt(StringUtils.KillCount.SnowBoss, data.getInt(StringUtils.KillCount.SnowBoss) + 1);
        }
        if (monsterhelmet.equals(ModItems.ArmorSakuraMob.get())) {
            Drops.SakuraMob(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorScarecrow.get())) {
            Drops.ScareCrow(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorPurpleIronHelmet.get())) {
            Drops.MineWorker(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorIceHunterHelmet.get())) {
            Drops.IceHunter(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorShipHelmet.get())) {
            Drops.ShipWorker(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorEarthManaHelmet.get())) {
            Drops.EarthMana(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorBloodManaHelmet.get())) {
            Drops.BloodMana(player, num);
        }
        if (monsterhelmet.equals(ModItems.MobArmorSlime.get())) {
            Drops.Slime(player, num);
        }
        if (monsterhelmet.equals(ModItems.ArmorRandomSlime.get())) {

            AtomicReference<ServerPlayer> RewardPlayer = new AtomicReference<>();
            AtomicBoolean HasPlayer = new AtomicBoolean(false);
            List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();

            playerList.forEach(serverPlayer -> {
                if (serverPlayer.equals(Utils.SlimeRewardPlayer.get(monster))) {
                    RewardPlayer.set(serverPlayer);
                    HasPlayer.set(true);
                }
            });

            if (HasPlayer.get()) {
                ServerPlayer serverPlayer = RewardPlayer.get();

                Compute.ExpPercentGetAndMSGSend(serverPlayer, 0.01, ExpUp, serverPlayer.experienceLevel);

                ItemStack itemStack = ModItems.WorldSoul1.get().getDefaultInstance();
                itemStack.setCount(serverPlayer.experienceLevel);
                Compute.ItemStackGive(player, itemStack);


                if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
                else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
                if (!data.contains(StringUtils.KillCount.RandomSlime))
                    data.putInt(StringUtils.KillCount.RandomSlime, 1);
                else data.putInt(StringUtils.KillCount.RandomSlime, data.getInt(StringUtils.KillCount.RandomSlime) + 1);

            }
        }
        if (player.getPersistentData().getBoolean("SignAward")) {
            Compute.FormatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("你回复了所有体力！").withStyle(ChatFormatting.WHITE));

            Compute.addOrCostPlayerPsValue(player, 100);

/*            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
            String DateString = tmpDate.format(date);
            player.getPersistentData().putString("SignIn",DateString);
            player.getPersistentData().putBoolean("SignAward",false);
            for(int i = 1; i <= 17; i++) data.putInt("DailyMission"+i,0);
            ItemStack itemStack = ModItems.DailyMission.get().getDefaultInstance();
            CompoundTag ItemData = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            int MissionLevel = 0;
            if(player.experienceLevel <= 15) MissionLevel = 3;
            else if(player.experienceLevel <= 25) MissionLevel = 6;
            else if(player.experienceLevel <= 35) MissionLevel = 8;
            else if(player.experienceLevel <= 49) MissionLevel = 12;
            else MissionLevel = 17;

            boolean flag = true;
            for(int i = MissionLevel - 5; i <= MissionLevel; i++)
            {
                if(r.nextInt(100) < 50) {
                    flag = false;
                    ItemData.putInt("DailyMission"+i,r.nextInt(24,64));
                }
            }
            if(flag) ItemData.putInt("DailyMission" + MissionLevel,24);
            ItemData.putString("Owner",player.getName().getString());

            itemStack.getOrCreateTagElement(Utils.MOD_ID).putString("Owner",player.getName().getString());
            Compute.ItemStackGive(player,itemStack);*/
        }
        if (Utils.KazeRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorKazeRecall.get()) && Utils.KazeRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.KazeRecall.RecallSuccess = true;
        }
        if (Utils.SpiderRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorSpiderRecall.get()) && Utils.SpiderRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.SpiderRecall.RecallSuccess = true;
        }
        if (Utils.HuskRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorHuskRecall.get()) && Utils.HuskRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.HuskRecall.RecallSuccess = true;
        }
        if (Utils.SeaRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorSeaRecall.get()) && Utils.SeaRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.SeaRecall.RecallSuccess = true;
        }
        if (Utils.LightningRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorLightningRecall.get()) && Utils.LightningRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.LightningRecall.RecallSuccess = true;
        }
        if (Utils.NetherRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorNetherRecall.get()) && Utils.NetherRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.NetherRecall.RecallSuccess = true;
        }
        if (Utils.SnowRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorSnowRecall.get()) && Utils.SnowRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.SnowRecall.RecallSuccess = true;
        }
        if (Utils.ForestRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorForestRecall.get()) && Utils.ForestRecall.RecallPlayer.equals((ServerPlayer) player)) {
            Utils.ForestRecallBossKillCount++;
            if (Utils.ForestRecallBossKillCount == 2) Utils.ForestRecall.RecallSuccess = true;
        }
        if (Utils.VolcanoRecall.RecallPlayer != null && monsterhelmet.equals(ModItems.ArmorVolcanoRecall.get()) && Utils.VolcanoRecall.RecallPlayer.equals((ServerPlayer) player)) {
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
        if (data.contains("Sword")) data.putInt("Sword", data.getInt("Sword") + 1);
        if (data.contains("Barker")) data.putInt("Barker", data.getInt("Barker") + 1);
        if (data.contains("Bow") && equip.getItem() instanceof BowItem) data.putInt("Bow", data.getInt("Bow") + 1);

    }

    public static void ExpPercentGetAndMSGSend(Player player, double num, double ExpUp, int ExpLevel) {
        if (player.experienceLevel >= 220) return;
        if (ExpLevel >= 120) ExpLevel = 120;
        if (ExpLevel - player.experienceLevel > 8) ExpLevel = player.experienceLevel;

        CompoundTag data = player.getPersistentData();
        double LevelUpNeedXp = Math.pow(Math.E, 3 + (player.experienceLevel / 100d) * 7);
        double ExpLevelXp = Math.pow(Math.E, 3 + (ExpLevel / 100d) * 7);
        double XpBeforeUp = (ExpLevelXp * num);
        double XpUp = (ExpLevelXp * num) * ExpUp;
        if (player.experienceLevel < 20) XpUp += 20;
        double Xp = XpBeforeUp + XpUp;
        if (data.contains("Xp")) data.putDouble("Xp", data.getDouble("Xp") + Xp);
        else data.putDouble("Xp", Xp);
        if (!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp")))
            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" + ").withStyle(ChatFormatting.DARK_PURPLE)).
                            append(Component.literal(String.format("%.1f", XpBeforeUp)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal(" + " + String.format("%.1f", XpUp)).withStyle(CustomStyle.styleOfLucky)).
                            append(Component.literal(String.format(" (%.1f/%.1f)", data.getDouble("Xp"), LevelUpNeedXp)).withStyle(ChatFormatting.GRAY)));
    }
    public static void ExpPercentGetIgnoreLimitAndMSGSend(Player player, double num, double ExpUp, int ExpLevel) {
        if (player.experienceLevel >= 220) return;
        if (ExpLevel - player.experienceLevel > 8) ExpLevel = player.experienceLevel;

        CompoundTag data = player.getPersistentData();
        double LevelUpNeedXp = Math.pow(Math.E, 3 + (player.experienceLevel / 100d) * 7);
        double ExpLevelXp = Math.pow(Math.E, 3 + (ExpLevel / 100d) * 7);
        double XpBeforeUp = (ExpLevelXp * num);
        double XpUp = (ExpLevelXp * num) * ExpUp;
        if (player.experienceLevel < 20) XpUp += 20;
        double Xp = XpBeforeUp + XpUp;
        if (data.contains("Xp")) data.putDouble("Xp", data.getDouble("Xp") + Xp);
        else data.putDouble("Xp", Xp);
        if (!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp")))
            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" + ").withStyle(ChatFormatting.DARK_PURPLE)).
                            append(Component.literal(String.format("%.1f", XpBeforeUp)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal(" + " + String.format("%.1f", XpUp)).withStyle(CustomStyle.styleOfLucky)).
                            append(Component.literal(String.format(" (%.1f/%.1f)", data.getDouble("Xp"), LevelUpNeedXp)).withStyle(ChatFormatting.GRAY)));
    }

    /*    public static void USE(Player player) {
        List<ServerPlayer> serverPlayerList = player.getServer().getPlayerList().getPlayers();
        serverPlayerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new UseAnimationS2CPacket(player.getId(),0),serverPlayer);
        });
    }*/
    public static void USE(Player player) {
        if (player.level().isClientSide) {
            if (player.tickCount - 10 > ClientUtils.UseTick && !ClientUtils.PlayerIsAttacking(player)
                    && !ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsBowAttacking(player)
                    && !ClientUtils.PlayerIsRolling(player)) {
                ClientUtils.UseTick = player.tickCount;
                ModNetworking.sendToServer(new UseAnimationRequestC2SPacket(0));
            }
        }
    }

    public static void ManaAttack(Player player, int count) {
        if (player.level().isClientSide) {
            if (player.tickCount - 10 > ClientUtils.ManaAttackTick && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsAttacking(player) && !ClientUtils.PlayerIsBowAttacking(player)
                    && !ClientUtils.PlayerIsRolling(player)) {
                ModNetworking.sendToServer(new ManaAttackAnimationRequestC2SPacket(0));
                ClientUtils.ManaAttackTick = player.tickCount;
                ClientUtils.ManaAttackCounts = count;
            }
        }
    }

    public static void BowAttack(Player player, int count) {
        if (player.level().isClientSide) {
            if (player.tickCount - 10 > ClientUtils.BowAttackTick && !ClientUtils.PlayerIsAttacking(player)
                    && !ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                    && !ClientUtils.PlayerIsRolling(player)) {
                ClientUtils.BowAttackTick = player.tickCount;
                ClientUtils.BowAttackCounts = count;
                ModNetworking.sendToServer(new BowAnimationRequestC2SPacket(0));
            }
        }
    }

    public static void USE(Player player, Item Tool) {
        CompoundTag data = player.getPersistentData();
        int TickCount = player.getServer().getTickCount();
        if (player.getCooldowns().isOnCooldown(Tool)) return;
        if (Tool instanceof PlainSword plainSword && PlayerManaCost(player, 20)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 12, ParticleTypes.HEART);
            ParticleProvider.RandomMoveParticle(serverPlayer, 0.75 * 2, 1, 8, ParticleTypes.COMPOSTER);
            double HealEffectUp = Compute.PlayerAttributes.PlayerHealEffectUp(player);
            PlayerHeal(player, player.getMaxHealth() * PlainSwordAttributes.EffectNum[plainSword.getNum()]);
            player.getCooldowns().addCooldown(ModItems.PlainSword0.get(), (int) (400 - 400.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.PlainSword1.get(), (int) (400 - 400.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.PlainSword2.get(), (int) (400 - 400.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.PlainSword3.get(), (int) (400 - 400.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Healing.get());
        } else if (Tool instanceof ForestSword forestSword && PlayerManaCost(player, 20)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5 * 2, 0.25, 12, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.25 * 2, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.0 * 2, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 24, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.5 * 2, 1.25, 28, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.25 * 2, 1.5, 32, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.0 * 2, 1.75, 36, ParticleTypes.COMPOSTER);
            Compute.EffectLastTimeSend(player,ModItems.ForestSword3.get().getDefaultInstance(),(int) ForestSwordAttributes.EffectNum[forestSword.getNum()] * 20);
            data.putInt(StringUtils.ForestSwordSkill0, TickCount + (int) ForestSwordAttributes.EffectNum[forestSword.getNum()] * 20);
            player.getCooldowns().addCooldown(ModItems.ForestSword0.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword1.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword2.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword3.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword4.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Attack.get());
        } else if (Tool instanceof ForestSword4 && PlayerManaCost(player, 20)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5 * 2, 0.25, 12, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.25 * 2, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.0 * 2, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 24, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.5 * 2, 1.25, 28, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.25 * 2, 1.5, 32, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.0 * 2, 1.75, 36, ParticleTypes.COMPOSTER);
            data.putInt(StringUtils.ForestSwordSkill1, TickCount + 400);
            player.getCooldowns().addCooldown(ModItems.ForestSword0.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword1.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword2.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword3.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword4.get(), (int) (600 - 600.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Attack.get());
        } else if (Tool instanceof LakeSword3 && (player.onGround() || player.isInWater()) && PlayerManaCost(player, 20)) {
            if (Parkour.PlayerIsInParkourRange(player)) {
                Compute.FormatMSGSend(player,Component.literal("跑酷").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("在跑酷区域不能使用蛟龙哦！").withStyle(ChatFormatting.WHITE));
            }
            else {
                ModNetworking.sendToClient(new UtilsLakeSwordS2CPacket(true), (ServerPlayer) player);
                player.getCooldowns().addCooldown(ModItems.LakeSword3.get(), (int) (160 * (1.0 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
            }
        } else if (Tool instanceof VolcanoSword volcanoSword && PlayerManaCost(player, 20)) {
            ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 0.25, 1, 16, ParticleTypes.ANGRY_VILLAGER);
            ParticleProvider.RandomMoveParticle((ServerPlayer) player, 0, 0.25, 32, ParticleTypes.ASH);
            String[] EffectString = {
                    StringUtils.VolcanoSwordSkill.Skill0,
                    StringUtils.VolcanoSwordSkill.Skill1,
                    StringUtils.VolcanoSwordSkill.Skill2,
                    StringUtils.VolcanoSwordSkill.Skill3
            };
            Compute.EffectLastTimeSend(player,ModItems.VolcanoSword3.get().getDefaultInstance(),100);
            data.putInt(EffectString[volcanoSword.getNum()], TickCount + 100);
            player.getCooldowns().addCooldown(ModItems.VolcanoSword0.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword1.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword2.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword3.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword4.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Lava.get());
        } else if (Tool instanceof VolcanoSword4 && PlayerManaCost(player, 20)) {
            ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 0.25, 1, 16, ParticleTypes.ANGRY_VILLAGER);
            ParticleProvider.RandomMoveParticle((ServerPlayer) player, 0, 0.25, 32, ParticleTypes.ASH);
            data.putInt(StringUtils.VolcanoSwordSkill.Skill4, TickCount + 100);
            Compute.EffectLastTimeSend(player,ModItems.VolcanoSword3.get().getDefaultInstance(),100);
            player.getCooldowns().addCooldown(ModItems.VolcanoSword0.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword1.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword2.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword3.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword4.get(), (int) (200 - 200.0 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Lava.get());
        } else if ((Tool instanceof SnowSword3 || Tool instanceof SnowSword4) && PlayerManaCost(player, 20)) {
            if (Parkour.PlayerIsInParkourRange(player)) {
                Compute.FormatMSGSend(player,Component.literal("跑酷").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("在跑酷区域不能使用冰川探索者哦！").withStyle(ChatFormatting.WHITE));
            }
            else {
                if (Lottery.PlayerIsInLotteryZone(player)) {
                    Compute.FormatMSGSend(player,Component.literal("博彩").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("在博彩区域不能使用冰川探索者哦！").withStyle(ChatFormatting.WHITE));

                }
                else {
                    ModNetworking.sendToClient(new UtilsSnowSwordS2CPacket(true), (ServerPlayer) player);
                    player.getCooldowns().addCooldown(ModItems.SnowSword3.get(), (int) (200 * (1.0 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
                    player.getCooldowns().addCooldown(ModItems.SnowSword4.get(), (int) (200 * (1.0 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
                }
            }
        } else if (Tool instanceof ManaSword) {
            Compute.PlayerPowerParticle(player);
            player.getCooldowns().addCooldown(ModItems.ManaSword.get(), (int) (100 - 100 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            data.putInt("ManaSwordActive", data.getInt("MANA"));
            data.putInt("MANA", 0);
            Compute.ManaStatusUpdate(player);
            Compute.SoundToAll(player, ModSounds.Mana_Sword.get());
        } else if (Tool instanceof WitherBonePower && PlayerManaCost(player, 80,true)) {
            PowerLogic.WitherBonePower(player,Tool);
            PowerLogic.PlayerReleasePowerType(player,0);
        } else if (Tool instanceof PiglinPower && PlayerManaCost(player, 80,true)) {
            PowerLogic.PiglinPower(player,Tool);
            PowerLogic.PlayerReleasePowerType(player,1);
        } else if (Tool instanceof WitherBoneMealPower) {
            PowerLogic.WitherBoneMealPower(player,Tool);
            PowerLogic.PlayerReleasePowerType(player,2);
        } else if (Tool instanceof QuartzSword && PlayerManaCost(player, 30)) {
            Compute.PlayerPowerParticle(player);
            Level level = player.level();
            player.getCooldowns().addCooldown(ModItems.QuartzSword.get(), (int) (100 - 100 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 10, 10, 10));
            Iterator<Player> iterator = playerList.iterator();
            while (iterator.hasNext()) {
                Player player1 = iterator.next();
                Compute.Damage.ManaDamageToPlayer(player, player1, 2.5f);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setCause((ServerPlayer) player);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.setDamage(0);
                lightningBolt.moveTo(player1.position());
                level.addFreshEntity(lightningBolt);
            }
            List<Mob> monsterList = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 10, 10, 10));
            Iterator<Mob> iterator1 = monsterList.iterator();
            while (iterator1.hasNext()) {
                Mob monster = iterator1.next();
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, monster, 2.5f,true);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setCause((ServerPlayer) player);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.setDamage(0);
                lightningBolt.moveTo(monster.position());
                level.addFreshEntity(lightningBolt);
            }
            Compute.SoundToAll(player, ModSounds.Nether_Power.get());
        } else if ((Tool instanceof SeaSword0 || Tool instanceof SeaSword1
                || Tool instanceof SeaSword2 || Tool instanceof SeaSword3
                || Tool instanceof SeaSword4) && PlayerManaCost(player, 10)) {
            Level level = player.level();
            player.getCooldowns().addCooldown(ModItems.SeaSword0.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.SeaSword1.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.SeaSword2.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.SeaSword3.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            PlayerItemCoolDown(player, ModItems.SeaSword4.get(), 3);
            data.putBoolean("SeaSword4", false);
            data.putBoolean("SeaSword3", false);
            data.putBoolean("SeaSword0", false);
            if (Tool instanceof SeaSword4) Utils.SeaSwordActiveMap.put(player,3);
            else if (Tool instanceof SeaSword3) Utils.SeaSwordActiveMap.put(player,2);
            else Utils.SeaSwordActiveMap.put(player,1);
            EffectLastTimeSend(player,ModItems.SeaSword0.get().getDefaultInstance(),8888,0,true);
            Compute.SoundToAll(player, ModSounds.Attack.get());

        } else if ((Tool instanceof BlackForestSword0 || Tool instanceof BlackForestSword1
                || Tool instanceof BlackForestSword2 || Tool instanceof BlackForestSword3
                || Tool instanceof BlackForestSword4) && PlayerManaCost(player, 10)) {
            Level level = player.level();
            player.getCooldowns().addCooldown(ModItems.BlackForestSword0.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.BlackForestSword1.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.BlackForestSword2.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.BlackForestSword3.get(), (int) (60 - 60 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            PlayerItemCoolDown(player, ModItems.BlackForestSword4.get(), 3);
            data.putBoolean("BlackForestSword4", false);
            data.putBoolean("BlackForestSword3", false);
            data.putBoolean("BlackForestSword0", false);
            if (Tool instanceof BlackForestSword4) Utils.BlackForestSwordActiveMap.put(player,3);
            else if (Tool instanceof BlackForestSword3) Utils.BlackForestSwordActiveMap.put(player,2);
            else Utils.BlackForestSwordActiveMap.put(player,1);
            EffectLastTimeSend(player,ModItems.BlackForestSword0.get().getDefaultInstance(),8888,0,true);
            Compute.SoundToAll(player, ModSounds.Attack.get());
        } else if (Tool instanceof MagmaPower && PlayerManaCost(player, 40,true)) {
            PowerLogic.MagmaPower(player,Tool);
            PowerLogic.PlayerReleasePowerType(player,3);
        } else if ((Tool instanceof KazeSword0 || Tool instanceof KazeSword1
                || Tool instanceof KazeSword2 || Tool instanceof KazeSword3
                || Tool instanceof KazeSword4) && PlayerManaCost(player,15)) {
            int Rate = 0;
            Level level = player.level();
            if (Tool instanceof KazeSword0) Rate = 2;
            else if (Tool instanceof KazeSword1) Rate = 3;
            else if (Tool instanceof KazeSword2) Rate = 4;
            else if (Tool instanceof KazeSword3) Rate = 5;
            else if (Tool instanceof KazeSword4) Rate = 10;
            List<Mob> MobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            List<Player> PlayerList1 = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 15, 15, 15));
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
                        if (IsOnSky(player1) && !player1.isInWater() && player1 != player) {
                            InSkyFlag = true;
                            break;
                        }
                    }
                }
            }

            Vec3 FaceVec = player.pick(1, 0, false).getLocation();
            if (InSkyFlag) {
                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                        ParticleTypes.SWEEP_ATTACK, true, FaceVec.x, FaceVec.y, FaceVec.z,
                        0, 0, 0, 0, 0
                );
                ServerPlayer serverPlayer1 = (ServerPlayer) player;
                serverPlayer1.connection.send(clientboundLevelParticlesPacket);
                ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_KNOCKBACK),
                        SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
                serverPlayer1.connection.send(clientboundSoundPacket);
                double MobDamageCount = 0;
                for (Mob mob : MobList1) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                        if (IsOnSky(mob) && !mob.isInWater()) {
                            mob.setDeltaMovement(0, -1, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.ENCHANTED_HIT, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                            MobDamageCount += Compute.Damage.AttackDamageToMonster_RateAdDamage(player, mob, Rate);
                        }
                    }
                }
                double PlayerDamageCount = 0;
                for (Player player1 : PlayerList1) {
                    if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                        if (IsOnSky(player1) && !player1.isInWater() && player1 != player) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, 1, 0));
                            for (ServerPlayer serverPlayer : playerList) {
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            }
                            ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.ENCHANTED_HIT, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                            PlayerDamageCount += Compute.Damage.AttackDamageToPlayer_RateAdDamage(player, player1, Rate);
                        }
                    }
                }
                player.getCooldowns().addCooldown(ModItems.KazeSword0.get(), (int) (60 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
                player.getCooldowns().addCooldown(ModItems.KazeSword1.get(), (int) (60 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
                player.getCooldowns().addCooldown(ModItems.KazeSword2.get(), (int) (60 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
                player.getCooldowns().addCooldown(ModItems.KazeSword3.get(), (int) (60 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
                player.getCooldowns().addCooldown(ModItems.KazeSword4.get(), (int) (60 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));

                if (MobDamageCount > 0) {
                    if (!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))
                        Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.RED),
                                Component.literal("狂风绝息").withStyle(CustomStyle.styleOfKaze).
                                        append(Component.literal("对怪物造成了").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f", MobDamageCount)).withStyle(CustomStyle.styleOfKaze)).
                                        append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
                }
                if (PlayerDamageCount > 0) {
                    if (!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))
                        Compute.FormatMSGSend(player, Component.literal("战斗").withStyle(ChatFormatting.RED),
                                Component.literal("狂风绝息").withStyle(CustomStyle.styleOfKaze).
                                        append(Component.literal("对玩家造成了").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f", PlayerDamageCount)).withStyle(CustomStyle.styleOfKaze)).
                                        append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
                }
            } else {
                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                        ParticleTypes.SWEEP_ATTACK,
                        true,
                        FaceVec.x,
                        FaceVec.y,
                        FaceVec.z,
                        0, 0, 0, 0, 0
                );
                ServerPlayer serverPlayer1 = (ServerPlayer) player;
                serverPlayer1.connection.send(clientboundLevelParticlesPacket);
                ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_SWEEP),
                        SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
                serverPlayer1.connection.send(clientboundSoundPacket);
                for (Mob mob : MobList1) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        mob.setDeltaMovement(0, 1, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    }
                }
                for (Player player1 : PlayerList1) {
                    if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        if (player1 != player) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, 1, 0));
                            for (ServerPlayer serverPlayer : playerList) {
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            }
                            ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                            ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                        }
                    }
                }
                player.getCooldowns().addCooldown(ModItems.KazeSword0.get(), 8);
                player.getCooldowns().addCooldown(ModItems.KazeSword1.get(), 8);
                player.getCooldowns().addCooldown(ModItems.KazeSword2.get(), 8);
                player.getCooldowns().addCooldown(ModItems.KazeSword3.get(), 8);
                player.getCooldowns().addCooldown(ModItems.KazeSword4.get(), 8);
            }

        } else if (Tool instanceof ForestBossSword && PlayerManaCost(player, 60)) {
            if (data.getInt(StringUtils.Entropy.Forest) > 0) {
                Level level = player.level();
                player.getCooldowns().addCooldown(ModItems.ForestBossSword.get(), (int) (200 - 200 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
/*                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Mob mob : mobList) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        CompoundTag MobData = mob.getPersistentData();
                        MobData.putInt(StringUtils.ForestBossSwordActive.Pare, data.getInt(StringUtils.Entropy.Forest));
                        MobData.putInt(StringUtils.ForestBossSwordActive.PareTime, TickCount + 100);
                        player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
                            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(mob.getId(), 100), serverPlayer);
                            ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(mob.getId(), 100), serverPlayer);
                        });
                    }
                }

                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Player player1 : playerList) {
                    if (player1 != player && player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        CompoundTag Player1Data = player1.getPersistentData();
                        Player1Data.putInt(StringUtils.ForestBossSwordActive.Pare, data.getInt(StringUtils.Entropy.Forest));
                        Player1Data.putInt(StringUtils.ForestBossSwordActive.PareTime, TickCount + 100);
                    }
                }*/
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1, 6, 100, ModParticles.LONG_ENTROPY.get());
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1.5, 6, 100, ModParticles.LONG_ENTROPY.get());
                Compute.SoundToAll(player, ModSounds.Nether_Power.get());
            } else {
                Compute.FormatMSGSend(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfHealth),
                        Component.literal("你的次元能量不足以召唤这个次元。").withStyle(ChatFormatting.WHITE));
            }
        } else if (Tool instanceof VolcanoBossSword && PlayerManaCost(player, 60)) {
            if (data.getInt(StringUtils.Entropy.Volcano) > 0) {
                Level level = player.level();
                player.getCooldowns().addCooldown(ModItems.VolcanoBossSword.get(), (int) (200 - 200 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));

                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Mob mob : mobList) {
                    if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Compute.Damage.AttackDamageToMonster_RateAdDamage(player, mob, EntropyRate(data.getInt(StringUtils.Entropy.Volcano)));
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                        player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                                ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 100), serverPlayer));
                    }
                }
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 10, 10, 10));
                for (Player player1 : playerList) {
                    if (player1 != player && player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                        Compute.Damage.AttackDamageToPlayer_RateAdDamage(player, player1, EntropyRate(data.getInt(StringUtils.Entropy.Volcano)));
                        player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                    }
                }
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1, 6, 100, ModParticles.LONG_ENTROPY.get());
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 1.5, 6, 100, ModParticles.LONG_ENTROPY.get());

                Compute.SoundToAll(player, ModSounds.Lava.get());
            } else {
                Compute.FormatMSGSend(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfPower),
                        Component.literal("你的次元能量不足以召唤这个次元。").withStyle(ChatFormatting.WHITE));
            }
        } else if (Tool instanceof SakuraSword && PlayerCurrentManaNum(player) >= 40) {
            if (Utils.SakuraDemonSword.containsKey(player) && Utils.SakuraDemonSword.get(player)) {
                if (PlayerManaCost(player, 40)) {
                    ModNetworking.sendToClient(new ChargedClearS2CPacket(4), (ServerPlayer) player);
                    ModNetworking.sendToClient(new SkillImageS2CPacket(1, 5, 5, 0, 3), (ServerPlayer) player);
                    Utils.SakuraDemonSword.put(player, false);
                    data.putInt(StringUtils.SakuraDemonSword, TickCount + 100);
                    player.getCooldowns().addCooldown(ModItems.SakuraDemonSword.get(), (int) (300 - 300 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));

                }
            } else {
                Compute.FormatMSGSend(player, Component.literal("妖刀").withStyle(CustomStyle.styleOfDemon),
                        Component.literal("妖刀能量尚未充盈完毕。").withStyle(ChatFormatting.WHITE));
            }
        } else if (Tool instanceof LakeBoss.LakeBossSword && PlayerManaCost(player, 60)) {
            player.getCooldowns().addCooldown(ModItems.LakeBossSword.get(), (int) (600 - 20 * EntropyRate(data.getInt(StringUtils.Entropy.Lake))));
            for (Item item : Utils.CoolDownItem) {
                player.getCooldowns().removeCooldown(item);
            }
        } else if (Tool instanceof WitherSword && PlayerManaCost(player, 20)) {
            player.getCooldowns().addCooldown(ModItems.WitherSword0.get(), (int) (600 - 600 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.WitherSword1.get(), (int) (600 - 600 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.WitherSword2.get(), (int) (600 - 600 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.WitherSword3.get(), (int) (600 - 600 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            Compute.EffectLastTimeSend(player, ModItems.WitherSword0.get().getDefaultInstance(), 100);
            if (player.getHealth() <= player.getMaxHealth() * 0.6f) {
                player.kill();
                FormatBroad(player.level(), Component.literal("死亡").withStyle(ChatFormatting.RED),
                        Component.literal(player.getName().getString() + "被自己的魔法干掉了。"));
            }
            player.setHealth(player.getHealth() - player.getMaxHealth() * 0.6f);
            if (Tool.equals(ModItems.WitherSword0.get())) data.putInt(StringUtils.WitherSword.Effect0, TickCount + 100);
            else if (Tool.equals(ModItems.WitherSword1.get()))
                data.putInt(StringUtils.WitherSword.Effect1, TickCount + 100);
            else if (Tool.equals(ModItems.WitherSword2.get()))
                data.putInt(StringUtils.WitherSword.Effect2, TickCount + 100);
            else if (Tool.equals(ModItems.WitherSword3.get()))
                data.putInt(StringUtils.WitherSword.Effect3, TickCount + 100);
            Compute.SoundToAll(player, ModSounds.Nether_Power.get());
        } else if (Tool instanceof PlainPower power && PlayerManaCost(player, PlainPower.ManaCost[power.getLevel()] - 10 * ArmorCount.LifeManaE(player),true)) {
            PowerLogic.PlainPower(player,Tool,power.getLevel());
            PowerLogic.PlayerReleasePowerType(player,4);
        } else if (Tool instanceof ForestPower power && PlayerManaCost(player, ForestPower.ManaCost[power.getLevel()] - 10 * ArmorCount.LifeManaE(player),true)) {
            PowerLogic.ForestPower(player,Tool,power.getLevel());
            PowerLogic.PlayerReleasePowerType(player,5);
        } else if (Tool instanceof LakePower power && PlayerManaCost(player, LakePower.ManaCost[power.getLevel()] - 10 * ArmorCount.ObsiManaE(player),true)) {
            PowerLogic.LakePower(player,Tool,power.getLevel());
            PowerLogic.PlayerReleasePowerType(player,6);
        } else if (Tool instanceof VolcanoPower power && PlayerManaCost(player, VolcanoPower.ManaCost[power.getLevel()] - 10 * ArmorCount.ObsiManaE(player),true)) {
            PowerLogic.VolcanoPower(player,Tool,power.getLevel());
            PowerLogic.PlayerReleasePowerType(player,7);
        } else if (Tool instanceof SnowPower power && PlayerManaCost(player, SnowPower.ManaCost[power.getLevel()],true)) {
            PowerLogic.SnowPower(player,Tool,power.getLevel());
            PowerLogic.PlayerReleasePowerType(player,8);
        } else if (Tool instanceof ShipSword shipSword && PlayerManaCost(player, 25)) {
            Level Dimension = player.level();
            Compute.PlayerItemCoolDown(player, Tool, 12);
            Vec3 DesPos = player.position();
            List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                    AABB.ofSize(player.position(), 20, 20, 20));
            AtomicInteger MobCount = new AtomicInteger();
            mobList.forEach(mob -> {
                if (mob.position().subtract(DesPos).length() <= 6) {
                    Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(DesPos, 20, mob));
                    AddSlowDownEffect(mob, 40, 2);
                    AddDefenceDescreaseEffectParticle(mob, 100);
                    MobCount.getAndIncrement();
                }
            });
            SoundToAll(player, SoundEvents.ANVIL_LAND);
            Utils.ShipSwordTime.put(player, TickCount + 100);
            Utils.ShipSwordEffect.put(player, Math.min(MobCount.get(), 4));

            ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1, 6, 120, ModParticles.LONG_LAKE.get(), 0.25);
            ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1.5, 6, 120, ModParticles.LONG_LAKE.get(), 0.25);
        } else if (Tool instanceof IceSword iceSword && PlayerManaCost(player, 25)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            Level level = player.level();
            Compute.PlayerItemCoolDown(player, Tool, 4);
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) <= 6) {
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 99, false, false));
                    player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                        ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 40), TmpServerPlayer);
                    });
                    if (level.getBlockState(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ())).is(Blocks.AIR)) {
                        level.setBlockAndUpdate(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), Blocks.ICE.defaultBlockState());
                        level.destroyBlock(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), false);
                    }
                }
            });
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 80, ParticleTypes.SNOWFLAKE);
        } else if (Tool.equals(ModItems.FieldSword0.get()) || Tool.equals(ModItems.FieldSword1.get())
                || Tool.equals(ModItems.FieldSword2.get()) || Tool.equals(ModItems.FieldSword3.get())) {
            Vec3 vec3 = player.pick(5, 0, false).getLocation();
            BlockPos blockPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos blockPos1 = new BlockPos(blockPos.getX() - 1 + i, blockPos.getY(), blockPos.getZ() - 1 + j);
                    try {
                        BlockEvent.HarvestCrops(player, blockPos1, player.level());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else if (Tool instanceof SpringScale springScale) {
            int Num = springScale.getNum();
            Utils.SpringScaleTime.put(player, TickCount + 1200);
            Utils.SpringScaleEffect.put(player, Num);
            EffectLastTimeSend(player, ModItems.SpringScale0.get().getDefaultInstance(), 1200);
            Compute.PlayerItemCoolDown(player, ModItems.SpringScale0.get(), 60);
            Compute.PlayerItemCoolDown(player, ModItems.SpringScale1.get(), 60);
            Compute.PlayerItemCoolDown(player, ModItems.SpringScale2.get(), 60);
            Compute.PlayerItemCoolDown(player, ModItems.SpringScale3.get(), 60);
        } else if (Tool instanceof LXYZO_Sword lxyzoSword) {
            if (PlayerManaCost(player, 20)) {
                Utils.LengXuePlayer = player;
                Utils.LengXueAttackFlag = true;
                EffectLastTimeSend(player,ModItems.LXYZOSword.get().getDefaultInstance(),88888,0,true);
                PlayerItemCoolDown(player, ModItems.LXYZOSword.get(), 10);
            }
        } else if (Tool instanceof XiangLiPickaxe xiangLiPickaxe) {
            if (PlayerManaCost(player, 50)) {
                Compute.PlayerItemCoolDown(player, ModItems.XiangLiPickaxe.get(), 30);
                Utils.YiZhiXiangLi = player;
                Utils.YiZhiXiangLiEffect = TickCount + 400;
                EffectLastTimeSend(player, ModItems.XiangLiPickaxe.get().getDefaultInstance(), 400);
            }
        } else if (Tool instanceof FireWorkGun fireWorkGun) {
            Utils.PlayerFireWorkGunEffect.put(player, player.getServer().getTickCount() + 200);
            Compute.EffectLastTimeSend(player, ModItems.FireWorkGun.get().getDefaultInstance(), 200);
            for (int i = 0; i < 2; i++) {
                CompoundTag compoundTag = new CompoundTag();
                byte a = 1;
                byte[] bytes = {0, 1, 2, 3, 4};
                String[] strings = {
                        "SMALL_BALL",
                        "LARGE_BALL",
                        "CREEPER",
                        "STAR",
                        "BURST"
                };
                Random random = new Random();
                compoundTag.putByte("Type", a);
                compoundTag.putByte("Trail", a);
                compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
                compoundTag.putByte("Flicker", a);
                compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
                compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
                ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
                itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
                ListTag listTag = new ListTag();
                listTag.add(compoundTag);
                itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

                Vec3 vec3 = player.pick(5, 0, false).getLocation();
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), player, vec3.x, vec3.y, vec3.z, itemStack);
                player.level().addFreshEntity(fireworkRocketEntity);
            }
            player.getCooldowns().addCooldown(fireWorkGun, 20);
        } else if (Tool instanceof SoulSword) {
            if (PlayerManaCost(player, 20)) {
                Compute.EffectLastTimeSend(player, ModItems.SoulSword.get().getDefaultInstance(), 8888,0,true);
                Utils.SoulSwordMap.put(player, true);
            }
        }
        else if (Tool instanceof CrushiSword) {
            if (PlayerManaCost(player, 20)) {
                Utils.Crush1 = player;
                CrushiSword.ActiveTick = player.getServer().getTickCount() + 200;
                Compute.EffectLastTimeSend(player,ModItems.ZeusSword1.get().getDefaultInstance(),200);
            }
        }
        else if (Tool instanceof XiangliSmoke xiangliSmoke) {
            player.getCooldowns().addCooldown(xiangliSmoke,20);
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),15,15,15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) < 6) {
                    Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,10);
                    mob.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,100));
                }
            });
            List<Player> playerList = player.level().getEntitiesOfClass(Player.class,AABB.ofSize(player.position(),15,15,15));
            playerList.forEach(player1 -> {
                if (player1.distanceTo(player1) < 7) {
                    ModNetworking.sendToClient(new SoundsS2CPacket(9),(ServerPlayer) player1);
                    player1.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20));
                }
            });
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1, 1, 120, ParticleTypes.SMOKE, 1);
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1.5, 1, 120, ParticleTypes.SMOKE, 1);
            Vec3 vec3 = player.pick(0.5,0,false).getLocation();
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,false,vec3.x,vec3.y,vec3.z,0,0.25f,0,0,10);
            List<ServerPlayer> players = player.getServer().getPlayerList().getPlayers();
            players.forEach(serverPlayer -> {
                serverPlayer.connection.send(clientboundLevelParticlesPacket);
            });
        }
        else if (Tool instanceof MoonSword) {
            if (PlayerManaCost(player, 20)) {
                Compute.PlayerItemCoolDown(player, ModItems.MoonSword.get(), 27);
                MoonSword.Active(player);
            }
        }
        else if (Tool instanceof EarthPower) {
            if (PlayerManaCost(player,75,true)) {
                PowerLogic.EarthPower(player,false);
                PowerLogic.PlayerReleasePowerType(player,9);
            }
        }
        else if (Tool instanceof EndPower) {
            EndPower.Release(player);
        }
        else if (Tool instanceof CastleSword) CastleSword.Active(player);
        else if (Tool instanceof CastleBow) CastleBow.Active(player);
        else if (Tool instanceof CastleSceptre) CastleSceptre.Active(player);

        else if (Tool instanceof LifeCrystal) LifeCrystal.Active(player, Tool);
        else if (Tool instanceof WaterCrystal) WaterCrystal.Active(player, Tool);
        else if (Tool instanceof FireCrystal) FireCrystal.Active(player, Tool);
        else if (Tool instanceof StoneCrystal) StoneCrystal.Active(player, Tool);
        else if (Tool instanceof IceCrystal) IceCrystal.Active(player, Tool);
        else if (Tool instanceof LightningCrystal) LightningCrystal.Active(player, Tool);
        else if (Tool instanceof WindCrystal) WindCrystal.Active(player, Tool);

        else if (Tool instanceof LifeElementSword) LifeElementSword.Active(player);
        else if (Tool instanceof LifeElementBow) LifeElementBow.Active(player);
        else if (Tool instanceof LifeElementSceptre) LifeElementSceptre.Active(player);

        else if (Tool instanceof WaterElementSword) WaterElementSword.Active(player);
        else if (Tool instanceof WaterElementBow) WaterElementBow.Active(player);
        else if (Tool instanceof WaterElementSceptre) WaterElementSceptre.Active(player);

        else if (Tool instanceof FireElementSword) FireElementSword.Active(player);
        else if (Tool instanceof FireElementBow) FireElementBow.Active(player);
        else if (Tool instanceof FireElementSceptre) FireElementSceptre.Active(player);
    }

    public static void AddManaDefenceDescreaseEffectParticle(Mob mob, int Tick) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void AddDefenceDescreaseEffectParticle(Mob mob, int Tick) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void AddDamageDescreaseEffectParticle(Mob mob, int Tick) {
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void AddSlowDownEffect(Mob mob, int Tick, int Tier) {
        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Tick, Tier, false, false, false));
        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), Tick), serverPlayer);
        });
    }

    public static void PlayerItemCoolDown(Player player, Item item, double Seconds) {
        double CoolDownDecrease = Compute.PlayerAttributes.PlayerCoolDownDecrease(player);
        player.getCooldowns().addCooldown(item, (int) (Seconds * 20 * (1 - CoolDownDecrease)));
        if (Utils.PowerTag.containsKey(item) && Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1) {
            ShangMengLi.CoolDownRecord(player,item,(int) (Seconds * 20 * (1 - CoolDownDecrease)));
        }

        if (Utils.PowerTag.containsKey(item) && BlackFeisaCurios.IsPlayer(player)) {
            BlackFeisaCurios.CoolDownRecord(player,item,(int) (Seconds * 20 * (1 - CoolDownDecrease)));
        }

        if (Utils.PowerTag.containsKey(item)) {
            if (!PowerLogic.playerPowerCoolDownRecord.containsKey(player)) PowerLogic.playerPowerCoolDownRecord.put(player,new HashMap<>());
            Map<Item,Integer> map = PowerLogic.playerPowerCoolDownRecord.get(player);
            map.put(item,(int) (Seconds * 20 * (1 - CoolDownDecrease)));

            PowerLogic.playerLastTimeReleasePowerCoolDownTime.put(player,(int) (Seconds * 20 * (1 - CoolDownDecrease)));
        }
    }

    public static boolean PlayerManaCost(Player player, double Mana) {
        if (PlayerCurrentManaNum(player) < Mana) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            if (Compute.ArmorCount.EarthMana(player) > 0) {
                PlayerHeal(player,Mana * 3 * Compute.ArmorCount.EarthMana(player));
            }
            TabooManaArmor.storeCostToList(player,Mana); //
            CgswdCurios.StoreValueAdd(player,Mana);
            if (BlackFeisaCurios2.IsPlayer(player)
                    || SoraVanilla1.IsPlayer(player)
                    || ShangMengLiCurios3.IsOn(player)
                    || LiulixianCurios3.ManaCost(player)
                    || BlackFeisaCurios4.PlayerIsUnderProtect(player)) return true;
            PlayerManaAddOrCost(player, -Mana);
        }
        return true;
    }

    public static boolean PlayerManaCost(Player player, double Mana, boolean IsMana) {
        if (PlayerCurrentManaNum(player) < Mana) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            PowerLogic.playerLastTimeReleasePowerManaCost.put(player,Mana);
            if (Compute.ArmorCount.EarthMana(player) > 0) {
                PlayerHeal(player,Mana * 3 * Compute.ArmorCount.EarthMana(player));
            }
            TabooManaArmor.storeCostToList(player,Mana); //
            CgswdCurios.StoreValueAdd(player,Mana);

            if (BlackFeisaCurios2.IsPlayer(player)
                    || SoraVanilla1.IsPlayer(player)
                    || ShangMengLiCurios3.IsOn(player)
                    || LiulixianCurios3.ManaCost(player)
                    || BlackFeisaCurios4.PlayerIsUnderProtect(player)) return true;
            PlayerManaAddOrCost(player, -Mana);
        }
        return true;
    }

    public static void FormatBroad(Level level, Component type, Component content) {
        List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
        Iterator<ServerPlayer> iterator = playerList.iterator();
        while (iterator.hasNext()) {
            ServerPlayer player = iterator.next();
            CompoundTag data = player.getPersistentData();
            if (type.getString().equals("副本") || type.getString().equals("黄金屋")) {
                if (!data.getBoolean(StringUtils.IgnoreType.Instance) && Utils.playerTeamMap.containsKey(player)) {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                            append(content));
                }
            } else {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                        append(content));

            }
        }
    }

    public static void FormatMSGSend(Player player, Component type, Component content) {
        if (player != null)
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                    append(content));
    }

    public static double WaltzPlayer(Vec3 vec3, Player player, Player hurter) {
        double ExDamage = 0;
        double AttackDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
        double MaxHealth = hurter.getMaxHealth();
        double num = AttackDamage * 0.05f * 0.01f;
        boolean flag = false;
        if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) > abs(vec3.z)) // 西
                flag = true;
            if (flag) {
                ExDamage += MaxHealth * num;
                hurter.getPersistentData().putInt("QuartzSabrePos", -1);
            }
        }
        return ExDamage;
    }

    public static double WaltzMonster(Vec3 vec3, Player player, Monster hurter) {
        double ExDamage = 0;
        double AttackDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
        double MaxHealth = hurter.getMaxHealth();
        double num = AttackDamage * 0.05f * 0.01f;
        boolean flag = false;
        if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) > abs(vec3.z)) // 西
                flag = true;
            if (flag) {
                ExDamage += MaxHealth * num;
                hurter.getPersistentData().putInt("QuartzSabrePos", -1);
            }
        }
        return ExDamage;
    }

    public static double WaltzPlayerBefore(Vec3 vec3, Player player, Player hurter) {
        double ExDamage = 0;
        double AttackDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
        double MaxHealth = hurter.getMaxHealth();
        double num = AttackDamage * 0.05f * 0.01f;
        boolean flag = false;
        if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) > abs(vec3.z)) // 西
                flag = true;
            if (flag) {
                ExDamage += MaxHealth * num;
            }
        }
        return ExDamage;
    }

    public static double WaltzMonsterBefore(Vec3 vec3, Player player, Mob hurter) {
        double ExDamage = 0;
        double AttackDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
        double MaxHealth = hurter.getMaxHealth();
        double num = AttackDamage * 0.05f * 0.01f;
        boolean flag = false;
        if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabre") > 0) {
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 0 &&
                    vec3.z < 0 && abs(vec3.z) > abs(vec3.x)) // 北
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 1 &&
                    vec3.x > 0 && abs(vec3.x) > abs(vec3.z)) // 东
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 2 &&
                    vec3.z > 0 && abs(vec3.z) > abs(vec3.x)) // 南
                flag = true;
            if (hurter.getPersistentData().contains("QuartzSabre") && hurter.getPersistentData().getInt("QuartzSabrePos") == 3 &&
                    vec3.x < 0 && abs(vec3.x) > abs(vec3.z)) // 西
                flag = true;
            if (flag) {
                ExDamage += MaxHealth * num;
            }
        }
        return ExDamage;
    }

    public static Component MaterialRequirement(int index, Component materialType, int num) {
        return Component.literal(" " + index + ".").withStyle(ChatFormatting.GRAY).
                append(materialType).
                append(Component.literal("*" + num).withStyle(ChatFormatting.WHITE));

    }

    public static int ItemStackCount(Inventory inventory, Item item) {
        int ExistNum = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.is(item)) ExistNum += itemStack.getCount();
        }
        return ExistNum;
    }

    public static boolean ItemStackCheck(Inventory inventory, Item item, int RequirementNum) {
        if (item.equals(ModItems.GoldCoin.get()) && CurrentVB(inventory.player) >= RequirementNum * 64) return true;
        if (item.equals(ModItems.SilverCoin.get()) && CurrentVB(inventory.player) >= RequirementNum * 1) return true;
        return ItemStackCount(inventory,item) >= RequirementNum;
    }

    public static boolean ItemStackRemoveIgnoreVB(Inventory inventory, Item item, int RemoveNum) throws IOException {
        int num = RemoveNum;
        if (!ItemStackCheck(inventory, item, RemoveNum)) return false;
        else {
            FileHandler.WRAQLogWrite(inventory.player, StringUtils.LogsType.ItemUse, item.toString() + "*" + RemoveNum);
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (inventory.getItem(i).is(item)) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.getCount() < num) {
                        num -= itemStack.getCount();
                        itemStack.setCount(0);
                        inventory.setItem(i, itemStack);
                    } else {
                        itemStack.setCount(itemStack.getCount() - num);
                        inventory.setItem(i, itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean ItemStackRemove(Inventory inventory, Item item, int RemoveNum) throws IOException {
        int num = RemoveNum;
        if (!ItemStackCheck(inventory, item, RemoveNum)) return false;
        else {
            FileHandler.WRAQLogWrite(inventory.player, StringUtils.LogsType.ItemUse, item.toString() + "*" + RemoveNum);
            if (item.equals(ModItems.GoldCoin.get()) && CurrentVB(inventory.player) >= RemoveNum * 64) {
                VBputAndMSGSend(inventory.player,RemoveNum * 64);
                return true;
            }
            if (item.equals(ModItems.SilverCoin.get()) && CurrentVB(inventory.player) >= RemoveNum * 1) {
                VBputAndMSGSend(inventory.player,RemoveNum * 1);
                return true;
            }
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (inventory.getItem(i).is(item)) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.getCount() < num) {
                        num -= itemStack.getCount();
                        itemStack.setCount(0);
                        inventory.setItem(i, itemStack);
                    } else {
                        itemStack.setCount(itemStack.getCount() - num);
                        inventory.setItem(i, itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void DailyMissionGive(Player player) {
        ItemStack itemStack = ModItems.SignInReset.get().getDefaultInstance();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random r = new Random();
        if (r.nextInt(100) < 50) data.putInt("DailyMission1", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission2", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission3", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission4", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission5", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission6", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission7", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission8", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission9", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission10", r.nextInt(16, 80));
        if (r.nextInt(100) < 50) data.putInt("DailyMission11", r.nextInt(16, 80));
    }

    public static int NeedExpForLevelUpOriginal(int XpLevel) {
        if (XpLevel <= 15) return 2 * XpLevel + 7;
        else if (XpLevel <= 30) return 5 * XpLevel - 38;
        return 9 * XpLevel - 158;
    }

    public static void ComponentAddLevelReward(List<Component> components, int rate) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionExAttackDamage(components,rate);
        Compute.EmojiDescriptionManaAttackDamage(components,rate);
        Compute.EmojiDescriptionDefencePenetration(components,0.005f * rate);
        Compute.EmojiDescriptionManaPenetration(components,0.005f * rate);
        Compute.EmojiDescriptionCritRate(components,0.005f * rate);
        Compute.EmojiDescriptionCritDamage(components,0.01f * rate);
        Compute.EmojiDescriptionHealSteal(components,0.0025f * rate);
        Compute.EmojiDescriptionManaRecover(components,0.1f * rate);
        Compute.EmojiDescriptionMovementSpeed(components,0.005f * rate);
        Compute.EmojiDescriptionMaxHealth(components, 10 * rate);
        Compute.EmojiDescriptionHealthRecover(components,0.1f * rate);
        Compute.EmojiDescriptionCoolDown(components,0.005f * rate);
        Compute.EmojiDescriptionExpUp(components, 0.01f * rate);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        if (rate != 12) {
            components.add(Component.literal(" 当你获得下一等阶附身符时").
                    append(Component.literal("(" + (rate + 1) * 5 + ")").withStyle(ChatFormatting.LIGHT_PURPLE)));
            components.add(Component.literal("右键可以将附身符化为一些补给物资。"));
        } else {
            components.add(Component.literal("你在维瑞阿契已经有些时日了，感谢你的陪伴！").withStyle(ChatFormatting.WHITE));
        }
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal("不断地探索，使你的阅历逐渐提高。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("LevelReward").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
    }

    public static int ItemCheck(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.items.size(); i++) {
            if (!inventory.items.get(i).isEmpty() && inventory.getItem(i).is(itemStack.getItem())) return i;
        }
        return -1;
    }

    public static void Broad(Level level, Component component) {
        PlayerList list = level.getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        ListIterator<ServerPlayer> iterator = list1.listIterator();
        while (iterator.hasNext()) {
            Player TmpPlayer = iterator.next();
            if (!TmpPlayer.level().isClientSide) TmpPlayer.sendSystemMessage(component);
        }
    }

    public static void DescriptionDash(List<Component> components, ChatFormatting chatFormatting0, ChatFormatting chatFormatting1, ChatFormatting chatFormatting2) {
        components.add(Component.literal("─").withStyle(chatFormatting0).
                append(Component.literal("───────────────────").withStyle(chatFormatting1).
                append(Component.literal("─").withStyle(chatFormatting2))));
    }

    public static void DescriptionDash(List<Component> components, ChatFormatting chatFormatting0, Style chatFormatting1, ChatFormatting chatFormatting2) {
        components.add(Component.literal("─").withStyle(chatFormatting0).
                append(Component.literal("───────────────────").withStyle(chatFormatting1).
                append(Component.literal("─").withStyle(chatFormatting2))));
    }

    public static void DescriptionOfBasic(List<Component> components) {
        components.add(Component.literal("β-基础属性:").withStyle(CustomStyle.styleOfPlain));
    }



    public static void DescriptionOfOnly(List<Component> components) {
        components.add(Component.literal("-唯一:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.GREEN));
    }

    public static void DescriptionOfAddtion(List<Component> components) {
        components.add(Component.literal("α-额外属性:").withStyle(CustomStyle.styleOfPower));
    }

    public static void DescriptionNum(List<Component> components, String string, Component component, String string1) {
        components.add(Component.literal(string).withStyle(ChatFormatting.WHITE).
                append(component).append(string1).withStyle(ChatFormatting.WHITE));
    }

    public static void RandomPotionBagProvider(Player player, int MaxNum, double Rate) {
        int Count = 0;
        Random random = new Random();
        ItemStack itemStack[] = new ItemStack[13];
        itemStack[0] = ModItems.AttackUp_PotionBag.get().getDefaultInstance();
        itemStack[1] = ModItems.Breakdefenceup_potionBag.get().getDefaultInstance();
        itemStack[2] = ModItems.CoolDownDecreaseUp_PotionBag.get().getDefaultInstance();
        itemStack[3] = ModItems.CritDamageUp_PotionBag.get().getDefaultInstance();
        itemStack[4] = ModItems.CritRateUp_PotionBag.get().getDefaultInstance();
        itemStack[5] = ModItems.defenceup_potionBag.get().getDefaultInstance();
        itemStack[6] = ModItems.HealStealUp_PotionBag.get().getDefaultInstance();
        itemStack[7] = ModItems.ManaBreakdefenceup_potionBag.get().getDefaultInstance();
        itemStack[8] = ModItems.ManaDamageUp_PotionBag.get().getDefaultInstance();
        itemStack[9] = ModItems.Manadefenceup_potionBag.get().getDefaultInstance();
        itemStack[10] = ModItems.ManaReplyUp_PotionBag.get().getDefaultInstance();
        itemStack[11] = ModItems.SpeedUp_PotionBag.get().getDefaultInstance();
        itemStack[12] = ModItems.HealthRecover_PotionBag.get().getDefaultInstance();
        for (int i = 0; i < MaxNum; i++) {
            ItemStack TmpStack = itemStack[random.nextInt(13)];
            TmpStack.getOrCreateTagElement(Utils.MOD_ID);
            if (random.nextDouble(1) < Rate) TmpStack.setCount(random.nextInt(2, 4));
            player.addItem(TmpStack);
        }
    }

    public static boolean BlockLimitContainBlockPos(BlockPos blockPos) {
        boolean contains = false;
        PlayerCallBack removing = null;
        for (PlayerCallBack playerCallBack : Utils.playerCallBackList) {
            if (playerCallBack.getBlockPos().equals(blockPos)) {
                if (playerCallBack.getPlayer() == null || playerCallBack.getPlayer().position().distanceTo(playerCallBack.getBlockPos().getCenter()) > 8) {
                    contains = false;
                    removing = playerCallBack;
                }
                else {
                    ModNetworking.sendToClient(new CheckBlockLimitS2CPacket(),(ServerPlayer) playerCallBack.getPlayer());
                    contains = true;
                }
            }
        }
        Utils.playerCallBackList.remove(removing);
        return contains;
    }

    public static int BrewingLevel(ItemStack itemStack) {
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
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) <= 1000) flag = false;
        }
        if (flag) return 6;

        flag = true;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) <= 500) flag = false;
        }
        if (flag) return 5;

        int Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 200) Count++;
        }
        if (Count >= 6) return 4;

        Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 100) Count++;
        }
        if (Count >= 5) return 3;

        Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 50) Count++;
        }
        if (Count >= 5) return 2;

        Count = 0;
        for (int i = 0; i < 8; i++) {
            if (data.getInt(DataName[i]) >= 20) Count++;
        }
        if (Count >= 4) return 1;
        return 0;
    }

    public static boolean BrewingLevelReward(Player player, int Level, CompoundTag data) {
        Random random = new Random();
        double[] LevelRate = {0, 0.05, 0.1, 0.15, 0.2, 0.4, 0.6};
        double ExpUp = Compute.PlayerAttributes.PlayerExpUp(player);
        if (random.nextDouble(1) < LevelRate[Level]) {
            if (data.contains("Owner")) {
                Compute.FormatMSGSend(player, Component.literal("酿造").withStyle(CustomStyle.styleOfBrew), Component.literal("你的酿造经验为你节省了这次酿造的材料消耗并为你提供了经验值。"));
                Compute.FormatBroad(player.level(), Component.literal("酿造").withStyle(CustomStyle.styleOfBrew),
                        Component.literal(player.getName().getString() + "完成了一次完美酿造！").withStyle(ChatFormatting.WHITE));
                Compute.ExpPercentGetAndMSGSend(player, 0.02, ExpUp, 50);
            }
            return true;
        }
        return false;
    }

    public static void TimeGive(String string, Player player) {
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
        if (StringInt == timeCode) {
            Utils.GemsForPlain = false;
            player.sendSystemMessage(Component.literal("~1"));
        }
        if (StringInt == timeCode0) {
            Utils.GemsForForest = false;
            player.sendSystemMessage(Component.literal("~2"));
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void ItemStackGive(Player player, ItemStack itemStack) throws IOException {
        if (itemStack.getCount() > 0) {
            FileHandler.WRAQLogWrite(player, StringUtils.LogsType.ItemGet, itemStack.toString());
            if (!PlayerIgnore.IgnoreItemGet(player)) {
                if (itemStack.getCount() > 1) {
                    FormatMSGSend(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Component.literal("你获得了：").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("*" + itemStack.getCount()).withStyle(ChatFormatting.AQUA)));
                } else {
                    FormatMSGSend(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Component.literal("你获得了：").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()));
                }
            }
            Inventory inventory = player.getInventory();
            if (inventory.getFreeSlot() != -1) {
                player.addItem(itemStack);
            } else {
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, player.level());
                itemEntity.setItem(itemStack);
                itemEntity.moveTo(player.position());
                player.level().addFreshEntity(itemEntity);
                FormatMSGSend(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                        Component.literal("背包已无空位，请注意。"));
            }
        }
    }

    public static class PlayerIgnore {
        public static boolean IgnoreItemGet(Player player) {
            return player.getPersistentData().contains(StringUtils.IgnoreType.ItemGet)
                    && player.getPersistentData().getBoolean(StringUtils.IgnoreType.ItemGet);
        }
    }

    public static void AttributeProvider(Player player, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataI = stack.getOrCreateTagElement(Utils.MOD_ID);
        for (int i = 0; i < Utils.AttributeName.length; i++) {
            if (dataI.contains(Utils.AttributeName[i])) {
                if (data.contains(Utils.AttributeName[i])) {
                    data.putDouble(Utils.AttributeName[i], data.getDouble(Utils.AttributeName[i]) + dataI.getDouble(Utils.AttributeName[i]));
                } else data.putDouble(Utils.AttributeName[i], dataI.getDouble(Utils.AttributeName[i]));
            }
        }
    }

    public static void AttributeDecrease(Player player, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        CompoundTag dataI = stack.getOrCreateTagElement(Utils.MOD_ID);
        for (int i = 0; i < Utils.AttributeName.length; i++) {
            if (dataI.contains(Utils.AttributeName[i])) {
                if (data.contains(Utils.AttributeName[i])) {
                    data.putDouble(Utils.AttributeName[i], data.getDouble(Utils.AttributeName[i]) - dataI.getDouble(Utils.AttributeName[i]));
                }
            }
        }
    }

    public static void RandomAttributeProvider(ItemStack stack, int num, double level, Player player) {
        double LuckyUp = Compute.PlayerLuckyUp(player);
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            String Attribute = Utils.AttributeName[r.nextInt(Utils.AttributeName.length)];
            double BaseValue = Utils.AttributeMap.get(Attribute);
            data.putDouble(Attribute, data.getDouble(Attribute) + BaseValue * r.nextDouble(0.1 * (1 + LuckyUp), level));
        }
    }

    public static void VBgetAndMSGSend(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains("VB")) data.putDouble("VB", num);
        else data.putDouble("VB", data.getDouble("VB") + num);
        if (!data.getBoolean(StringUtils.IgnoreType.VB)) {
            FormatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                    Component.literal("你的账户收入:").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f", num)).withStyle(ChatFormatting.RED)).
                            append(Component.literal("VB,").withStyle(ChatFormatting.GOLD)).
                            append(Component.literal("当前余额:").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f", data.getDouble("VB"))).withStyle(ChatFormatting.GOLD)));

        }
    }

    public static void VBputAndMSGSend(Player player, double num) {
        CompoundTag data = player.getPersistentData();
        data.putDouble("VB", data.getDouble("VB") - num);
        if (!data.getBoolean(StringUtils.IgnoreType.VB)) {
            FormatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                    Component.literal("你的账户支出:").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f", num)).withStyle(ChatFormatting.GREEN)).
                            append(Component.literal("VB,").withStyle(ChatFormatting.GOLD)).
                            append(Component.literal("当前余额:").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f", data.getDouble("VB"))).withStyle(ChatFormatting.GOLD)));
        }
    }

    public static double CurrentVB(Player player) {
        return player.getPersistentData().getDouble("VB");
    }

    public static Vec3 getFaceCircleVec(Player player, double angle) {

        double r = 1;
        Vector3f pickVec3 = player.pick(10, 0, true).getLocation().toVector3f();
        Vector3f faceVec3 = player.pick(1, 0, true).getLocation().toVector3f();
        Vector3f vector3f = pickVec3.sub(faceVec3);
        Vec3 nVec = new Vec3(vector3f.x, vector3f.y, vector3f.z);
        Vec3 iVec = new Vec3(1, 0, 0);
        Vec3 jVec = new Vec3(0, 1, 0);
        Vec3 kVec = new Vec3(0, 0, 1);
        Vec3 aVec;
        if (nVec.cross(iVec).length() == 0) {
            aVec = nVec.cross(jVec);
        } else aVec = nVec.cross(iVec);
        aVec = aVec.normalize();
        Vec3 bVec = nVec.cross(aVec).normalize();

        return new Vec3(r * Math.cos(angle) * aVec.x + r * Math.sin(angle) * bVec.x,
                r * Math.cos(angle) * aVec.y + r * Math.sin(angle) * bVec.y,
                r * Math.cos(angle) * aVec.z + r * Math.sin(angle) * bVec.z);

    }

    public static void PlayerPowerParticle(Player player) {
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ParticleTypes.WITCH, true,
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

    public static void MagmaPower(Entity entity, Level level, Player player) {
        List<ServerPlayer> playerList0 = entity.getServer().getPlayerList().getPlayers();
        for (ServerPlayer serverPlayer : playerList0) {
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.EXPLOSION_EMITTER, true, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0, 0, 1);
            serverPlayer.connection.send(clientboundLevelParticlesPacket);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.GENERIC_EXPLODE), SoundSource.PLAYERS, entity.getX(), entity.getY(), entity.getZ(), 1f, 1f, 0);
            serverPlayer.connection.send(clientboundSoundPacket);
        }
        ParticleProvider.RandomMoveParticle(entity, 1, 1, 24, ParticleTypes.ASH);
        ParticleProvider.RandomMoveParticle(entity, 1, 1, 24, ParticleTypes.LAVA);
        List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(entity.position(), 10, 10, 10));
        for (Mob mob : list) {
            Compute.Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, 3, true,
                    Element.Fire, ElementValue.ElementValueJudgeByType(player, Element.Fire) + 1);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2, false, false));
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                    ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 60), serverPlayer));

        }
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(entity.position(), 10, 10, 10));
        for (Player player1 : playerList) {
            if (player1 != player) {
                Compute.Damage.ManaDamageToPlayer(player, player1, 3);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
            }
        }
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
        double a = (dot2.x - dot1.x) * (dot.y - dot1.y) - (dot2.y - dot1.y) * (dot.x - dot1.x);
        double b = (dot3.x - dot2.x) * (dot.y - dot2.y) - (dot3.y - dot2.y) * (dot.x - dot2.x);
        double c = (dot4.x - dot3.x) * (dot.y - dot3.y) - (dot4.y - dot3.y) * (dot.x - dot3.x);
        double d = (dot1.x - dot4.x) * (dot.y - dot4.y) - (dot1.y - dot4.y) * (dot.x - dot4.x);
        if ((a > 0 && b > 0 && c > 0 && d > 0) || (a < 0 && b < 0 && c < 0 && d < 0)) return true;
        return false;
    }

    public static double Vec2Cross(Vec2 vec1, Vec2 vec2) {
        return vec1.x * vec2.y - vec1.y * vec2.x;
    }

    public static Vec2 Vec2Sub(Vec2 vec1, Vec2 vec2) {
        Vec2 vec = new Vec2(vec2.x - vec1.x, vec2.y - vec1.x);
        return vec;
    }

    public static double SArmorMaxHealth(Player player) {
        double MaxHealthCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;
        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSunPower = "#Slot#" + i + "#SunPower";
                if (data.contains(IsSunPower)) {
                    MaxHealthCount += data.getDouble(IsSunPower) * 10 * Rate;
                }
            }
        }
        return MaxHealthCount * 2;
    }

    public static double SArmorCoolDown(Player player) {
        double CoolDown = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsLakePower = "#Slot#" + i + "#LakePower";
                if (data.contains(IsLakePower)) {
                    CoolDown += data.getDouble(IsLakePower) * Rate / 2.0;
                }
            }
        }
        return CoolDown * 0.01f * 1.5f;
    }

    public static double SArmorAttackDamage(Player player) {
        double AttackDamageCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;
        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                if (data.contains(IsVolcanoPower)) {
                    AttackDamageCount += data.getDouble(IsVolcanoPower) * Rate;
                }
            }
        }
        return AttackDamageCount * 1.5f;
    }

    public static double SArmorCritRate(Player player) {
        double CritRate = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSkyPower = "#Slot#" + i + "#SkyPower";
                if (data.contains(IsSkyPower)) {
                    CritRate += data.getDouble(IsSkyPower) * Rate / 2.0;
                }
            }
        }
        return CritRate * 0.01f * 1.5f;
    }

    public static double SArmorCritDamage(Player player) {
        double CritDamage = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsSnowPower = "#Slot#" + i + "#SnowPower";
                if (data.contains(IsSnowPower)) {
                    CritDamage += data.getDouble(IsSnowPower) * Rate;
                }
            }
        }
        return CritDamage * 0.01f * 1.5f;
    }

    public static double SArmorManaDamage(Player player) {
        double ManaDamageCount = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2.0 : 1.0;
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsManaPower = "#Slot#" + i + "#ManaPower";
                if (data.contains(IsManaPower)) {
                    ManaDamageCount += data.getDouble(IsManaPower) * Rate;
                }
            }
        }
        return ManaDamageCount * 1.5f;
    }

    public static double SArmorHealSteal(Player player) {
        double HealSteal = 0;
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        double Rate = 1;

        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) Rate = 2;
            else Rate = 1;
            for (int i = 1; i <= 5; i++) {
                String IsNetherPower = "#Slot#" + i + "#NetherPower";
                if (data.contains(IsNetherPower)) {
                    HealSteal += data.getDouble(IsNetherPower) * Rate / 4.0;
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
        if (PlayerHelmet instanceof LightningArmorHelmet) LightningArmorCount++;
        if (PlayerChest instanceof LightningArmorChest) LightningArmorCount++;
        if (PlayerLeggings instanceof LightningArmorLeggings) LightningArmorCount++;
        if (PlayerBoots instanceof LightningArmorBoots) LightningArmorCount++;
        if (PlayerHelmet instanceof iLightningArmorHelmet) LightningArmorCount += 2;
        if (PlayerChest instanceof iLightningArmorChest) LightningArmorCount += 2;
        if (PlayerLeggings instanceof iLightningArmorLeggings) LightningArmorCount += 2;
        if (PlayerBoots instanceof iLightningArmorBoots) LightningArmorCount += 2;
        return LightningArmorCount;
    }

    public static void CodeHitMonster(Level level, Mob monster, int Range, ServerPlayer player, double damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(monster.position(), 10 * Range, 10 * Range, 10 * Range));
        if (Range == 0 && !mobList.contains(monster)) mobList.add(monster);
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(monster.position(), 10 * Range, 10 * Range, 10 * Range));
        for (Mob mob : mobList) {
            if (mob != monster) {
                mob.hurt(mob.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
            if (Kaze > 0) {
                mob.setDeltaMovement(0, Effect, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());

                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.position());
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                com.very.wraq.valueAndTools.Utils.Struct.Gather gather = new Gather(20 * Effect, monster.position());
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                } else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather, mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                player1.hurt(player1.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, Effect, 0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.position());
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20 * Effect, monster.position());
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    } else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather, mobQueue);
                    }
                }
            }
        }
    }

    public static void CodeHitPlayer(Level level, Player hurter, int Range, ServerPlayer player, double damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(hurter.position(), 10 * Range, 10 * Range, 10 * Range));
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.position(), 10 * Range, 10 * Range, 10 * Range));
        if (Range == 0 && !playerList.contains(hurter)) playerList.add(hurter);
        for (Mob mob : mobList) {
            mob.hurt(mob.damageSources().playerAttack(player), (float) damage);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);

            if (Kaze > 0) {
                mob.setDeltaMovement(0, Effect, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());
                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.position());
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                Gather gather = new Gather(20 * Effect, hurter.position());
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                } else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather, mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                if (player1 != hurter) player1.hurt(player1.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, Effect, 0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.position());
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20 * Effect, hurter.position());
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    } else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather, mobQueue);
                    }
                }
            }
        }
    }

    public static boolean RecallPlayerCheck(ServerPlayer serverPlayer) {
        if (Utils.KazeRecall.RecallPlayer != null && Utils.KazeRecall.RecallPlayer.equals(serverPlayer)) return true;
        if (Utils.SpiderRecall.RecallPlayer != null && Utils.SpiderRecall.RecallPlayer.equals(serverPlayer))
            return true;
        return false;
    }

    public static double Vec3Angle(Vec3 VecA, Vec3 VecB) {
        return acos(abs(VecA.dot(VecB)) / (VecA.length() * VecB.length()));
    }

    public static void AttackJudge(Player player) {
        ItemStack MainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (MainHandItem.is(ModItems.VolcanoSword0.get()) || MainHandItem.is(ModItems.VolcanoSword1.get())
                || MainHandItem.is(ModItems.VolcanoSword2.get()) || MainHandItem.is(ModItems.VolcanoSword4.get())
                || MainHandItem.is(ModItems.ManaSword.get()) || MainHandItem.is(ModItems.QuartzSword.get())
                || MainHandItem.is(ModItems.QuartzSabre.get()) || MainHandItem.is(ModItems.SeaSword0.get())
                || MainHandItem.is(ModItems.SeaSword1.get()) || MainHandItem.is(ModItems.SeaSword2.get())
                || MainHandItem.is(ModItems.SeaSword3.get()) || MainHandItem.is(ModItems.SeaSword4.get())
                || MainHandItem.is(ModItems.KazeSword0.get()) || MainHandItem.is(ModItems.KazeSword1.get())
                || MainHandItem.is(ModItems.KazeSword2.get()) || MainHandItem.is(ModItems.KazeSword3.get())
                || MainHandItem.is(ModItems.KazeSword4.get())) {
            ModNetworking.sendToServer(new SwordAttackAnimationRequestC2SPacket(player.getId()));
            ClientUtils.RangeAttackCount = 10;
            ClientUtils.RangeAttackAnimationCount = 14;
        }
        if (MainHandItem.is(ModItems.MineSword0.get()) || MainHandItem.is(ModItems.MineSword1.get())
                || MainHandItem.is(ModItems.MineSword2.get()) || MainHandItem.is(ModItems.MineSword3.get())
                || MainHandItem.is(ModItems.SnowSword0.get()) || MainHandItem.is(ModItems.SnowSword1.get())
                || MainHandItem.is(ModItems.SnowSword2.get()) || MainHandItem.is(ModItems.SnowSword3.get())
                || MainHandItem.is(ModItems.SnowSword4.get())) {
            ModNetworking.sendToServer(new PickAxeAttackAnimationRequestC2SPacket(player.getId()));
            ClientUtils.PickAxeAttackCount = 10;
            ClientUtils.PickAxeAttackAnimationCount = 14;
        }
    }

    public static void RateItemStackGive(ItemStack itemStack, double rate, Player player) throws IOException {
        Random r = new Random();
        double LuckyUp = PlayerLuckyUp(player);
        double totalRate = rate * (1 + LuckyUp);
        if (totalRate > 1) {
            int Count = (int) totalRate;
            if (r.nextDouble() < totalRate % 1) Count ++;
            ItemStackGive(player, new ItemStack(itemStack.getItem(), Count));
        }
        else {
            if (r.nextDouble(1.0d) < rate * (1 + LuckyUp)) {
                if (rate <= 0.01) {
                    ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_LEVELUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1, 1, 0);
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    serverPlayer.connection.send(clientboundSoundPacket);
                    Compute.FormatBroad(player.level(), Component.literal("稀有掉落").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString() + "获得了").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()));
                }
                ItemStackGive(player, itemStack);
            }
        }
    }

    public static int SwordSkillLevelGet(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Sword);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static int BowSkillLevelGet(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Bow);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static int ManaSkillLevelGet(CompoundTag data, int index) {
        int Level = 0;
        String SkillData = data.getString(StringUtils.SkillData.Mana);
        if (SkillData.length() != 15) return 0;
        else {
            if (SkillData.charAt(index) == 'X') Level = 10;
            else Level = SkillData.charAt(index) - 48;
        }
        return Level;
    }

    public static void ChargingModule(CompoundTag data, Player player) {
        if (Compute.SwordSkillLevelGet(data, 12) > 0)
            ModNetworking.sendToClient(new SwordSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.ManaSkillLevelGet(data, 12) > 0)
            ModNetworking.sendToClient(new ManaSkill12S2CPacket(8), (ServerPlayer) player);
        if (Compute.ManaSkillLevelGet(data, 13) > 0)
            ModNetworking.sendToClient(new ManaSkill13S2CPacket(8), (ServerPlayer) player);
        if (Compute.BowSkillLevelGet(data, 12) > 0)
            ModNetworking.sendToClient(new BowSkill12S2CPacket(8), (ServerPlayer) player);
    }

    public static void SetMobCustomName(Mob mob, Item ArmorItem, Component component) {
        int Level = Utils.MobLevel.get(ArmorItem).intValue();
        if (Level < 25) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.GREEN)
                    .append(component));
        } else if (Level < 50) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.BLUE)
                    .append(component));
        } else if (Level < 75) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.RED)
                    .append(component));
        } else if (Level < 100) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(ChatFormatting.LIGHT_PURPLE)
                    .append(component));
        } else if (Level < 125) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfEntropy)
                    .append(component));
        }
        else if (Level < 175) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfCastle)
                    .append(component));
        }
        else if (Level < 200) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfPurpleIron)
                    .append(component));
        }
        else if (Level < 225) {
            mob.setCustomName(Component.literal("Lv." + Level + " ").withStyle(CustomStyle.styleOfMoon1)
                    .append(component));
        }
        mob.setCustomNameVisible(true);
        mob.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(0);
        mob.getAttribute(Attributes.ARMOR).setBaseValue(0);
    }

    public static double ExtractRate(int ExtractLevel) {
        return Math.min(Math.min(0.1, ExtractLevel * 0.001)
                        + Math.max(0, (ExtractLevel - 100) * 0.0002)
                        + Math.max(0, (ExtractLevel - 1000) * 0.002)
                , 0.33);
    }

    public static double LevelSuppress(Player player, Mob monster) {
        int MobLevel = 0;
        Item MobHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (Utils.MobLevel.containsKey(MobHelmet)) MobLevel = Utils.MobLevel.get(MobHelmet).intValue();
        return (player.experienceLevel - MobLevel) / 200.0d;
    }

    public static double DefenceDamageDecreaseRate(double Defence, double DefencePenetration, double DefencePenetration0) {

        double DefenceAfterCompute = Defence * (1 - DefencePenetration) - DefencePenetration0;

        if (DefenceAfterCompute < 0) return 2 - (400 / (400 - DefenceAfterCompute));

        return (400 / (400 + DefenceAfterCompute));

    }

    public static double ManaDefenceDamageDecreaseRate(double Defence, double DefencePenetration, double DefencePenetration0) {
        // DefencePenetration = 百分比穿透 // DefencePenetration0 = 固定穿透
        double DefenceAfterCompute = Defence * (1 - DefencePenetration) - DefencePenetration0;
        // DefenceAfterCompute = 计算完穿透的护甲/魔抗值

        if (DefenceAfterCompute < 0) return 2 - (400 / (400 - DefenceAfterCompute));

        return (400 / (400 + DefenceAfterCompute));
/*        if (DefenceAfterCompute < 0) return 1 + (0.25 * log(1 - (DefenceAfterCompute) * (E * E / 400)));
        return 1 - (0.25 * log(1 + (DefenceAfterCompute) * (E * E / 400)));*/
    }

    public static void CoolDownTimeDescription(List<Component> components, int seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(seconds + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static void CoolDownTimeDescription(List<Component> components, double seconds) {
        components.add(Component.literal(" 冷却时间:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.1f", seconds) + "s").withStyle(ChatFormatting.AQUA)));
    }

    public static void ManaCostDescription(List<Component> components, int num) {
        components.add(Component.literal(" 法力消耗:").withStyle(ChatFormatting.WHITE).
                append(Component.literal(num + "").withStyle(ChatFormatting.LIGHT_PURPLE)));
    }

    public static ItemStack FoilAddItemStack(ItemStack itemStack) {
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
        map.put(Enchantments.UNBREAKING, 1);
        EnchantmentHelper.setEnchantments(map, itemStack);
        return itemStack;
    }

    public static Boolean IsOnSky(LivingEntity entity) {
        int X = entity.getBlockX();
        int Y = entity.getBlockY();
        int Z = entity.getBlockZ();
        if (entity.level().getBlockState(new BlockPos(X, Y - 2, Z)) != Blocks.AIR.defaultBlockState()
                && entity.level().getBlockState(new BlockPos(X, Y - 1, Z)) != Blocks.AIR.defaultBlockState()) {
            return false;
        }
        return true;
    }

    public static void EmojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionBaseAttackDamage(List<Component> components, double BaseDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionAttackSpeed(List<Component> components, double AttackSpeed) {
        components.add(Component.literal(Utils.Emoji.AttackSpeed + " 攻击速度").withStyle(CustomStyle.styleOfSky).
                append(Component.literal(" " + String.format("%.1f", (4 + AttackSpeed))).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionDefencePenetration(List<Component> components, double DefencePenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionDefencePenetration(List<Component> components, double DefencePenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionDefencePenetration0(List<Component> components, double DefencePenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", DefencePenetration)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionDefencePenetration0(List<Component> components, double DefencePenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲穿透").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", DefencePenetration)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionCritRate(List<Component> components, double CriticalHitRate) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.1f%%", CriticalHitRate * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionCritRate(List<Component> components, double CriticalHitRate, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.1f%%", CriticalHitRate * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", CriticalHitDamage * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionCritDamage(List<Component> components, double CriticalHitDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", CriticalHitDamage * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionManaHealSteal(List<Component> components, double HealSteal) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 法术吸血").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionHealSteal(List<Component> components, double HealSteal, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                append(Component.literal("+" + String.format("%.0f‱", HealSteal * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f‱", SoulEquipAttribute.ForgingAddition.HealthSteal * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionMovementSpeed(List<Component> components, double MovementSpeed) {
        if (MovementSpeed >= 0) {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));
        } else {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.RED).
                    append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));

        }
    }

    public static void SoulEmojiDescriptionMovementSpeed(List<Component> components, double MovementSpeed, int ForgeTimes) {
        if (MovementSpeed >= 0) {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("  ")).
                    append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                    append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
        } else {
            components.add(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.RED).
                    append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("  ")).
                    append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                    append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));

        }
    }

    public static void EmojiDescriptionAttackRange(List<Component> components, double AttackRangeUp) {
        components.add(Component.literal(Utils.Emoji.AttackRange + " 攻击距离").withStyle(CustomStyle.styleOfSea).
                append(Component.literal("+" + String.format("%.2f", AttackRangeUp)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaAttackDamage(List<Component> components, double ManaDamage, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaAttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void ManaCostDescription(List<Component> components, double ManaCost) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulManaCostDescription(List<Component> components, double ManaCost, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.ManaCost + " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("- " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaCost)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionManaPenetration(List<Component> components, double ManaPenetration) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaPenetration(List<Component> components, double ManaPenetration, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.ManaPenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionManaPenetration0(List<Component> components, double ManaPenetration0) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", ManaPenetration0)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaPenetration0(List<Component> components, double ManaPenetration0, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法穿透").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", ManaPenetration0)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaPenetration0)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionManaRecover(List<Component> components, double ManaRecover) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.1f", ManaRecover)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionManaRecover(List<Component> components, double ManaRecover, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", ManaRecover)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaRecover)).withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionMaxHealth(List<Component> components, double MaxHealth) {
        if (MaxHealth < 0) {
            components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("-" + String.format("%.0f", -MaxHealth)).withStyle(ChatFormatting.RED)));

        }
        else {
            components.add(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f", MaxHealth)).withStyle(ChatFormatting.WHITE)));
        }
    }


    public static void EmojiDescriptionHealAmplification(List<Component> components, double HealEffect) {
        components.add(Component.literal(Utils.Emoji.HealthAmplification + " 治疗强度").withStyle(CustomStyle.styleOfHealth).
                append(Component.literal("+" + String.format("%.0f%%", HealEffect * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionExpUp(List<Component> components, double ExpUp) {
        components.add(Component.literal(Utils.Emoji.ExpUp + " 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionLuckyUp(List<Component> components, double Lucky) {
        components.add(Component.literal(Utils.Emoji.Lucky + " 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f%%", Lucky * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionDefenceRate(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 护甲加成").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f%%", Defence * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionExAttackDamageRate(List<Component> components, double ExDamageRate) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" " + String.format("%.0f%%", ExDamageRate * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionCoolDown(List<Component> components, double CoolDown) {
        components.add(Component.literal(Utils.Emoji.CoolDown + " 技能急速").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", CoolDown * 100)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 基础护甲").withStyle(ChatFormatting.GRAY).
                append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionHealthRecover(List<Component> components, double HealthReplay) {
        components.add(Component.literal(Utils.Emoji.HealthRecover + " 生命回复").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+" + String.format("%.1f", HealthReplay)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionExAttackDamage(List<Component> components, double ExDamage) {
        components.add(Component.literal(Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" " + String.format("%.0f", ExDamage)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionManaDefence(List<Component> components, double Defence) {
        components.add(Component.literal(Utils.Emoji.Defence + " 魔法抗性").withStyle(ChatFormatting.BLUE).
                append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));
    }

    public static void EmojiDescriptionMaxMana(List<Component> components, double MaxMana) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", MaxMana)).withStyle(ChatFormatting.WHITE)));
    }

    public static void SoulEmojiDescriptionMaxMana(List<Component> components, double MaxMana, int ForgeTimes) {
        components.add(Component.literal(Utils.Emoji.MaxMana + " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("+" + String.format("%.0f", MaxMana)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("  ")).
                append(Component.literal("+ 16 x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
    }

    public static void EmojiDescriptionSwiftness(List<Component> components, double Swiftness) {
        components.add(Component.literal(Utils.Emoji.Swiftness + " 迅捷").withStyle(ChatFormatting.GREEN).
                append(Component.literal("+" + String.format("%.2f", Swiftness)).withStyle(ChatFormatting.WHITE)));
    }

    public static void DescriptionModuleSword(ItemStack itemStack, List<Component> components, double BaseDamage) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        double ExDamageForging = 0;
        if (data.contains("Forging")) ExDamageForging = ForgingValue(data, BaseDamage);
        double ExDamageProficiency = 0;
        if (data.contains("KillCount")) ExDamageProficiency = BaseDamage * 0.5 * (data.getInt("KillCount") / 100000.0);
        ChatFormatting[] chatFormattings = {
                ChatFormatting.GREEN,
                ChatFormatting.AQUA,
                ChatFormatting.YELLOW,
                ChatFormatting.LIGHT_PURPLE,
                ChatFormatting.RED
        };
        if (data.contains("Forging")) {
            components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageForging)).withStyle(ChatFormatting.YELLOW)));
        } else components.add(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal(" ")).
                append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                        withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])));
    }

    public static void DescriptionModuleSceptre(ItemStack itemStack, List<Component> components, double BaseDamage) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        double ExDamageForging = 0;
        if (data.contains("Forging")) ExDamageForging = ForgingValue(data, BaseDamage);
        double ExDamageProficiency = 0;
        if (data.contains("KillCount")) ExDamageProficiency = BaseDamage * 0.5 * (data.getInt("KillCount") / 100000.0);
        ChatFormatting[] chatFormattings = {
                ChatFormatting.GREEN,
                ChatFormatting.AQUA,
                ChatFormatting.YELLOW,
                ChatFormatting.LIGHT_PURPLE,
                ChatFormatting.RED
        };
        if (data.contains("Forging")) {
            components.add(Component.literal(Utils.Emoji.Sword + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])).
                    append(Component.literal(" ")).
                    append(Component.literal("+ " + String.format("%.0f", ExDamageForging)).withStyle(ChatFormatting.BLUE)));
        } else components.add(Component.literal(Utils.Emoji.Sword + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)).
                append(Component.literal(" ")).
                append(Component.literal("+ " + String.format("%.0f", ExDamageProficiency)).
                        withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)])));

    }

    public static void BasicSwordDescription(ItemStack itemStack, List<Component> components, double BaseDamage, double BreakDefence,
                                             double CriticalHitRate, double CriticalHitDamage, double HealSteal, double SpeedUp) {
        DescriptionModuleSword(itemStack, components, BaseDamage);
        EmojiDescriptionDefencePenetration(components, BreakDefence);
        EmojiDescriptionCritRate(components, CriticalHitRate);
        EmojiDescriptionCritDamage(components, CriticalHitDamage);
        EmojiDescriptionHealSteal(components, HealSteal);
        EmojiDescriptionMovementSpeed(components, SpeedUp);
    }

    public static class AttributeDescription {

        public static Component ExpUp(String content) {
            return Component.literal(Utils.Emoji.ExpUp + " " + content + "经验加成").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component MaxHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "最大生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component LossHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "已损失生命值").withStyle(ChatFormatting.DARK_GREEN);
        }

        public static Component Swiftness(String content) {
            return Component.literal(Utils.Emoji.Swiftness + " " + content + "迅捷").withStyle(ChatFormatting.GREEN);
        }

        public static Component MovementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component ExMovementSpeed(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "额外移动速度").withStyle(ChatFormatting.GREEN);
        }

        public static Component MovementSpeedDecrease(String content) {
            return Component.literal(Utils.Emoji.Speed + " " + content + "移动速度").withStyle(ChatFormatting.RED);
        }

        public static Component ManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ManaCost(String content) {
            return Component.literal(Utils.Emoji.ManaCost + " " + content + "法力消耗").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ExManaDamage(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "额外魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component ManaRecover(String content) {
            return Component.literal(Utils.Emoji.ManaRecover + " " + content + "法力回复").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component Health(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component Defence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲").withStyle(ChatFormatting.GRAY);
        }

        public static Component HealAmplification(String content) {
            return Component.literal(Utils.Emoji.HealthAmplification + " " + content + "治疗强度").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component HealthRecover(String content) {
            return Component.literal(Utils.Emoji.HealthRecover + " " + content + "生命回复").withStyle(CustomStyle.styleOfHealth);
        }

        public static Component ManaDefence(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "魔法抗性").withStyle(ChatFormatting.BLUE);
        }

        public static Component ExAttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "额外攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component DefencePenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "护甲穿透").withStyle(ChatFormatting.GRAY);
        }

        public static Component CritDamage(String content) {
            return Component.literal(Utils.Emoji.CritDamage + " " + content + "暴击伤害").withStyle(ChatFormatting.BLUE);
        }

        public static Component CritRate(String content) {
            return Component.literal(Utils.Emoji.CritRate + " " + content + "暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        public static Component AttackDamage(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "攻击力").withStyle(ChatFormatting.YELLOW);
        }

        public static Component ManaPenetration(String content) {
            return Component.literal(Utils.Emoji.Defence + " " + content + "法术穿透").withStyle(ChatFormatting.BLUE);
        }

        public static Component CoolDown(String content) {
            return Component.literal(Utils.Emoji.CoolDown + " " + content + "技能急速").withStyle(ChatFormatting.AQUA);
        }

        public static Component HealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "生命偷取").withStyle(ChatFormatting.RED);
        }

        public static Component SkillHealthSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "全能吸血").withStyle(CustomStyle.styleOfField);
        }

        public static Component ManaHealSteal(String content) {
            return Component.literal(Utils.Emoji.HealSteal + " " + content + "法术吸血").withStyle(CustomStyle.styleOfMana);
        }

        public static Component MaxMana(String content) {
            return Component.literal(Utils.Emoji.MaxMana + " " + content + "法力值").withStyle(CustomStyle.styleOfMana);
        }

        public static Component ExHealth(String content) {
            return Component.literal(Utils.Emoji.Health + " " + content + "额外生命值").withStyle(ChatFormatting.GREEN);
        }

        public static Component AttackRange(String content) {
            return Component.literal(Utils.Emoji.AttackRange + " " + content + "攻击距离").withStyle(CustomStyle.styleOfSea);
        }

        public static Component AttackDamageValue(String content) {
            return Component.literal(Utils.Emoji.Sword + " " + content + "物理伤害").withStyle(ChatFormatting.YELLOW);
        }

        public static Component ManaDamageValue(String content) {
            return Component.literal(Utils.Emoji.Mana + " " + content + "魔法伤害").withStyle(ChatFormatting.LIGHT_PURPLE);
        }
    }

    public static void SuitDescription(List<Component> components) {
        components.add(Component.literal(Utils.Emoji.Suit + " " + "套装效果").withStyle(ChatFormatting.AQUA));
    }

    public static void SuitDoubleDesription(List<Component> components) {
        components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
    }

    public static void SuitDoubleDesription(List<Component> components, int Count) {
        if (Count >= 2)
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.YELLOW));
        else
            components.add(Component.literal("▷2件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void SuitQuadraDesription(List<Component> components) {
        components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public static void SuitQuadraDesription(List<Component> components, int Count) {
        if (Count >= 4)
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.LIGHT_PURPLE));
        else
            components.add(Component.literal("▷4件套效果:").withStyle(ChatFormatting.GRAY));
    }

    public static void SuffixOfMainStoryI(List<Component> components) {
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryII(List<Component> components) {
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryIII(List<Component> components) {
        components.add(Component.literal("MainStoryIII").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryV(List<Component> components) {
        components.add(Component.literal("MainStoryV").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMoon(List<Component> components) {
        components.add(Component.literal("MoonPalace").withStyle(CustomStyle.styleOfMoon).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfCastle(List<Component> components) {
        components.add(Component.literal("BlackCastle").withStyle(CustomStyle.styleOfCastle).withStyle(ChatFormatting.ITALIC));
    }

    public static String GetRGB(int r, int g, int b) {
        char[] chars = {
                '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
        };
        return "#" + chars[r/16] + chars[r%16] + chars[g/16] + chars[g%16] + chars[b/16] + chars[b%16];
    }

    public static List<Color> colorList = new ArrayList<>(){{
        add(new Color(new Color.RGB(0,255,0), new Color.RGB(0,255,255), 100));
        add(new Color(new Color.RGB(0,255,255), new Color.RGB(255,0,0), 100));
        add(new Color(new Color.RGB(255,0,0), new Color.RGB(128,128,128), 100));
        add(new Color(new Color.RGB(128,128,128), new Color.RGB(1,255,255), 100));
        add(new Color(new Color.RGB(1,255,255), new Color.RGB(100,149,237), 100));
        add(new Color(new Color.RGB(100,149,237), new Color.RGB(0,255,127), 100));
        add(new Color(new Color.RGB(0,255,127), new Color.RGB(0,255,0), 100));
    }};

    public static Map<Color.RGB, Color.RGB> colorMap = new HashMap<>(){{
        put(new Color.RGB(0,255,0), new Color.RGB(0,255,255));
        put(new Color.RGB(0,255,255), new Color.RGB(255,0,0));
        put(new Color.RGB(255,0,0), new Color.RGB(128,128,128));
        put(new Color.RGB(128,128,128), new Color.RGB(1,255,255));
        put(new Color.RGB(1,255,255), new Color.RGB(100,149,237));
        put(new Color.RGB(100,149,237), new Color.RGB(0,255,127));
        put(new Color.RGB(0,255,127), new Color.RGB(0,255,0));
    }};

    public static void SuffixOfElement(List<Component> components) {
        Calendar calendar = Calendar.getInstance();
        long times = calendar.getTimeInMillis();

            for (int i = 0 ; i < colorList.size(); i ++) {
                Color color = colorList.get(i);
                if (color.Add()) {
                    colorList.set(i, new Color(color.targetRGB, colorMap.get(color.targetRGB), 100));
                }
            }

        components.add(Component.literal("E").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(0).getRGB()))).withStyle(ChatFormatting.ITALIC).
                append(Component.literal("l").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(1).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("e").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(2).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("m").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(3).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("e").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(4).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("n").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(5).getRGB()))).withStyle(ChatFormatting.ITALIC)).
                append(Component.literal("t").withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorList.get(6).getRGB()))).withStyle(ChatFormatting.ITALIC)));
    }

    public static void SuffixOfPurpleIronKnight(List<Component> components) {
        components.add(Component.literal("PurpleIronKnight").withStyle(CustomStyle.styleOfPurpleIron).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfIce(List<Component> components) {
        components.add(Component.literal("Ice").withStyle(CustomStyle.styleOfIce).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfWorldSoul(List<Component> components) {
        components.add(Component.literal("WorldSoul").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryIV(List<Component> components) {
        components.add(Component.literal("MainStoryIV").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
    }

    public static void SuffixOfMainStoryVII(List<Component> components) {
        components.add(Component.literal("MainStoryVII").withStyle(CustomStyle.styleOfMoon1).withStyle(ChatFormatting.ITALIC));
    }

    public static class MaterialLevelDescription {
        public static void Low(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.GREEN));
        }

        public static void Normal(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.YELLOW));
        }

        public static void Rare(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.AQUA));
        }

        public static void Epic(List<Component> components) {
            components.add(Component.literal("材料").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

    public static void RuneAttributeDescription(List<Component> components) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append("符石属性:").withStyle(ChatFormatting.WHITE));
    }

    public static void DescriptionPassive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("被动 ").withStyle(ChatFormatting.GREEN)).
                append(name));
    }

    public static void DescriptionActive(List<Component> components, Component name) {
        components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                append(Component.literal("主动 ").withStyle(ChatFormatting.AQUA)).
                append(name));
    }

    public static double ForgingValue(CompoundTag data, double BaseValue) {
        int forgingLevel = data.getInt("Forging");
        if (data.contains(StringUtils.QingMingForgePaper)) ++ forgingLevel;

        if (forgingLevel <= 10) {
            return 2 * forgingLevel + BaseValue * 0.02f * forgingLevel;
        } else if (forgingLevel <= 20) {
            return 20 + 4 * (forgingLevel - 10) + BaseValue * (0.04f * (forgingLevel - 10) + 0.2f);
        } else {
            return 60 + BaseValue * (0.2f * (forgingLevel - 20) + 0.6f);
        }

    }


    public static double SakuraDemonSword(Player player, double DamageBeforeDefence) {
        double DamageInfluence = 0;
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > TickCount) {
            DamageInfluence += DamageBeforeDefence * 0.5f;
        }
        return DamageInfluence;
    }

    public static class ArmorCount {
        public static int Star(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.StarHelmet.get())) Count++;

            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.StarLeggings.get())) Count++;

            return Count;
        }

        public static int Moon(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MoonHelmet.get())) Count++;

            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MoonLeggings.get())) Count++;

            return Count;
        }

        public static int Plain(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PlainArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PlainArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PlainArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PlainArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.PlainBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Plain.Crest3) > 0) Count++;
            return Count;
        }

        public static int Forest(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ForestArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ForestArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ForestArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ForestArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.ForestBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Forest.Crest3) > 0) Count++;
            return Count;
        }

        public static int Lake(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LakeArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LakeArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LakeArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LakeArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.LakeBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Lake.Crest3) > 0) Count++;
            return Count;
        }

        public static int Volcano(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.VolcanoArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.VolcanoArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.VolcanoArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.VolcanoArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.VolcanoBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Volcano.Crest3) > 0) Count++;
            return Count;
        }

        public static int LifeMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LifeManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LifeManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LifeManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LifeManaArmorBoots.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest3) > 0) Count++;
            if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) Count ++;
            return Count;
        }

        public static int LifeManaE(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LifeManaArmorHelmetE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LifeManaArmorChestE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LifeManaArmorLeggingsE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LifeManaArmorBootsE.get())) Count++;
            return Count;
        }

        public static int ObsiMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ObsiManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ObsiManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ObsiManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ObsiManaArmorBoots.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mana.Crest3) > 0) Count++;
            if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) Count ++;
            return Count;
        }

        public static int ObsiManaE(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ObsiManaArmorHelmetE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ObsiManaArmorChestE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ObsiManaArmorLeggingsE.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ObsiManaArmorBootsE.get())) Count++;
            return Count;
        }

        public static int Mine(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MineArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.MineArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MineArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.MineArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.MineBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Mine.Crest3) > 0) Count++;
            return Count;
        }

        public static int Snow(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SnowArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SnowArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SnowArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SnowArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.SnowBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Snow.Crest3) > 0) Count++;
            return Count;
        }

        public static int Sky(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SkyArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SkyArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SkyArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SkyArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.SkyBracelet.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest0) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest1) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest2) > 0
                    || player.getPersistentData().getInt(StringUtils.Crest.Sky.Crest3) > 0) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int Nether(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NetherArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NetherArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NetherArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NetherArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.NetherPower.get())) Count++;
            if (player.getPersistentData().getInt(StringUtils.Crest.Nether) > 0) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int Leather(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LeatherArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LeatherArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LeatherArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LeatherArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int PurpleIron(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PurpleIronArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PurpleIronArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PurpleIronArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PurpleIronArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int Ice(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.IceArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.IceArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.IceArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.IceArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int NetherMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NetherManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NetherManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NetherManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NetherManaArmorBoots.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.NetherSceptre.get())) Count += 2;
            if (Count > 6) return 6;
            return Count;
        }

        public static int SpringAttack(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringAttackArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringAttackArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringAttackArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringAttackArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int SpringSwift(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringSwiftArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringSwiftArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringSwiftArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringSwiftArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int SpringMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringManaArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int EarthMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.EarthManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.EarthManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.EarthManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.EarthManaArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int BloodMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.BloodManaArmorHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BloodManaArmorChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.BloodManaArmorLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.BloodManaArmorBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int CastleAttack(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleAttackHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleAttackChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleAttackLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleAttackBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int CastleSwift(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleSwiftHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleSwiftChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleSwiftLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleSwiftBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }

        public static int CastleMana(Player player) {
            int Count = 0;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleManaHelmet.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleManaChest.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleManaLeggings.get())) Count++;
            if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleManaBoots.get())) Count++;
            if (Count > 4) return 4;
            return Count;
        }
    }

    public static double SkySuitEffectRate(Player player) {
        int Count = ArmorCount.Sky(player);
        switch (Count) {
            case 1 -> {
                return 0.2f;
            }
            case 2 -> {
                return 0.5f;
            }
            case 3 -> {
                return 1.4f;
            }
            case 4 -> {
                return 2;
            }
        }
        return 0;

    }

    public static double NetherSuitEffectRate(Player player) {
        int Count = ArmorCount.Nether(player);
        switch (Count) {
            case 1 -> {
                return 0.2f;
            }
            case 2 -> {
                return 0.5f;
            }
            case 3 -> {
                return 0.7f;
            }
            case 4 -> {
                return 1;
            }
        }
        return 0;
    }

    public static void BallParticle(Vec3 vec3, Level level, double r, ParticleOptions particleOptions, int N) {
        for (int i = 0; i <= N / 4; i++) {
            double v = (r / (N / 4.0) * i) * (r / (N / 4.0) * i);
            ParticleProvider.VerticleCircleParticle(vec3, level, r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
            ParticleProvider.VerticleCircleParticle(vec3, level, -r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
        }
    }

    public static void BallParticle(ServerPlayer serverPlayer, Vec3 vec3, double r, ParticleOptions particleOptions, int N) {
        for (int i = 0; i <= N / 4; i++) {
            double v = (r / (N / 4.0) * i) * (r / (N / 4.0) * i);
            ParticleProvider.VerticleCircleParticle(vec3, serverPlayer, r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
            ParticleProvider.VerticleCircleParticle(vec3, serverPlayer, -r / (N / 4.0) * i + 1, Math.sqrt(r * r - v),
                    Math.max(1, N - 4 * i), particleOptions);
        }
    }

    public static double EntropyRate(int EntropyLevel) {
        int Level = 0;
        if (EntropyLevel <= 10) {
            return EntropyLevel;
        } else {
            Level += 10;
            for (int i = 1; i <= 8; i++) {
                double TmpNum1 = Math.pow(10, i);
                double TmpNum2 = Math.pow(10, i) * 9;
                if (EntropyLevel > TmpNum1) {
                    Level += (EntropyLevel - TmpNum1) * (TmpNum1 / TmpNum2);
                }
            }
        }
        return Level;
    }

    public static void EntropyPacketSend(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        CompoundTag data = serverPlayer.getPersistentData();
        if (data.contains(StringUtils.Entropy.Forest) && data.getInt(StringUtils.Entropy.Forest) > 0
                || data.contains(StringUtils.Entropy.Volcano) && data.getInt(StringUtils.Entropy.Volcano) > 0
                || data.contains(StringUtils.Entropy.Lake) && data.getInt(StringUtils.Entropy.Lake) > 0
                || data.contains(StringUtils.Entropy.Sky) && data.getInt(StringUtils.Entropy.Sky) > 0) {
            ModNetworking.sendToClient(new EntropyS2CPacket(data.getInt(StringUtils.Entropy.Forest),
                    data.getInt(StringUtils.Entropy.Volcano),
                    data.getInt(StringUtils.Entropy.Lake),
                    data.getInt(StringUtils.Entropy.Sky),
                    data.getInt(StringUtils.Entropy.Snow)
            ), serverPlayer);
        }
    }

    public static Component DescriptionWhiteColor(String content) {
        return Component.literal(content).withStyle(ChatFormatting.WHITE);
    }

    public static void PlayerHeal(Player player, double Num) {
        if (Num < 0) return;
        double healNum = Num * (PlayerAttributes.PlayerHealEffectUp(player));
        healNum = Math.min(healNum, player.getMaxHealth() - player.getHealth());
        if (EliaoiSceptre.IsInEffect(player)) {
            Compute.PlayerShieldProvider(player,100,healNum);
        }
        else {
            LifeElementSword.StoreToList(player,healNum);
            FengXiaoJuCurios1.Store(player,Math.min(player.getMaxHealth() - player.getHealth(),(float) healNum));
            player.heal((float) healNum);
        }
    }

    public static void PlayerHealSteal(Player player, double Num) {
        double rate = 1;
        if (Compute.ArmorCount.BloodMana(player) > 0) rate += 0.35 * Compute.ArmorCount.BloodMana(player);
        double overflowHealth = Num * rate * 0.5 * (1 + PlayerAttributes.PlayerHealEffectUp(player)) + player.getHealth() - player.getMaxHealth();
        if (overflowHealth > 0 && Compute.ArmorCount.BloodMana(player) > 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),15,15,15));
            mobList.forEach(mob -> {
                Vec3 PosVec = mob.position().subtract(player.position());
                if (PosVec.length() <= 6) {
                    Compute.Damage.DamageIgNoreDefenceToMonster(player,mob,overflowHealth * Compute.ArmorCount.BloodMana(player));
                }
            });
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        }
        PlayerHeal(player,Num * rate * 0.5 * 0.01);
    }

    public static void Proficiency(ItemStack equip, Item monsterhelmet) {
        if (Utils.MainHandTag.containsKey(equip.getItem()) && !Compute.IsSoulEquip(equip) && Utils.MobLevel.containsKey(monsterhelmet)) {
            CompoundTag dataI = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (!dataI.contains(StringUtils.KillCount.KillCount)) dataI.putInt(StringUtils.KillCount.KillCount, Utils.MobLevel.get(monsterhelmet).intValue() * 5);
            else dataI.putInt(StringUtils.KillCount.KillCount, Math.min(Integer.MAX_VALUE, dataI.getInt(StringUtils.KillCount.KillCount) + Utils.MobLevel.get(monsterhelmet).intValue() * 5));
        }
    }

    public static int SuitItemVision(Player player, Item item, EquipmentSlot equipmentSlot, List<Component> components, Style MainStyle) {
        if (player.getItemBySlot(equipmentSlot).is(item)) {
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(MainStyle));
            return 1;
        } else
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(ChatFormatting.GRAY));
        return 0;
    }

    public static int SuitItemVision(Player player, Item item, EquipmentSlot equipmentSlot, List<Component> components, ChatFormatting MainStyle) {
        if (player.getItemBySlot(equipmentSlot).is(item)) {
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(MainStyle));
            return 1;
        } else
            components.add(Component.literal(item.getDefaultInstance().getDisplayName().getString()).withStyle(ChatFormatting.GRAY));
        return 0;
    }

    public static void SuitEffectRateDescription(List<Component> components, int Count) {
        switch (Count) {
            case 1 -> {
                components.add(Component.literal("基于套装数量的数值:(").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("1_20%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",2_50%,3_70%,4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 2 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("2_50%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",3_70%,4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 3 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("3_70%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 4 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_70%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("4_100%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            default -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_70%,4_100%").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
        }
    }

    public static void SkySuitEffectRateDescription(List<Component> components, int Count) {
        switch (Count) {
            case 1 -> {
                components.add(Component.literal("基于套装数量的数值:(").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("1_20%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",2_50%,3_140%,4_200%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 2 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("2_50%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",3_140%,4_200%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 3 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("3_140%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(",4_200%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            case 4 -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_140%,").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal("4_200%").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
            default -> {
                components.add(Component.literal("基于套装数量的数值:(1_20%,2_50%,3_140%,4_200%").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC).
                        append(Component.literal(")").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            }
        }
    }

    public static void SoundToAll(Player player, SoundEvent soundEvent) {
        if (soundEvent.equals(ModSounds.Mana.get()) || soundEvent.equals(ModSounds.Use.get()) || soundEvent.equals(ModSounds.Wind.get())
                || soundEvent.equals(ModSounds.Lava.get()) || soundEvent.equals(ModSounds.Nether_Power.get()) || soundEvent.equals(ModSounds.Mana_Sword.get()))
            return;
        Level level = player.level();
        List<ServerPlayer> serverPlayerList = level.getServer().getPlayerList().getPlayers();
        serverPlayerList.forEach(serverPlayer1 -> {
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(soundEvent),
                    SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1f, 1, 0);
            serverPlayer1.connection.send(clientboundSoundPacket);
        });
    }

    public static void PlayerItemUseWithRecord(Player player) throws IOException {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        FileHandler.WRAQLogWrite(player, StringUtils.LogsType.ItemUse, itemStack.getHoverName().getString() + "Num * 1");
        itemStack.setCount(itemStack.getCount() - 1);
    }

    public static void PlayerItemUseOffHandWithRecord(Player player) throws IOException {
        ItemStack itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        FileHandler.WRAQLogWrite(player, StringUtils.LogsType.ItemUse, itemStack.getHoverName().getString() + "Num * 1");
        itemStack.setCount(itemStack.getCount() - 1);
    }

    public static void PlayerItemDeleteWithRecord(Player player, ItemStack itemStack) throws IOException {
        FileHandler.WRAQLogWrite(player, StringUtils.LogsType.ItemDelete, itemStack.getHoverName().getString() + "Num * 1");
    }

    public static void PlayerItemUseWithRecord(Player player, int Num) throws IOException {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        FileHandler.WRAQLogWrite(player, StringUtils.LogsType.ItemUse, itemStack.getHoverName().getString() + "Num * " + Num);
        itemStack.setCount(itemStack.getCount() - Num);
    }

    public static class Damage {

        public static void LastDamageToMob(Player player, Mob mob, double rate, int tick, int pertick, boolean IsAd) {
            LastDamage lastDamage = new LastDamage(player,mob,IsAd,tick,pertick,rate);
            Utils.PlayerLastDamageToMonster.add(lastDamage);
        }

        public static void LastXpStrengthDamageToMob(Player player, Mob mob, double rate, int tick, int pertick, boolean IsAd) {
            LastDamage lastDamage = new LastDamage(player,mob,IsAd,tick,pertick,rate);
            Utils.PlayerLastXpStrengthDamageToMonster.add(lastDamage);
        }

        public static double DamageIgNoreDefenceToMonster(Player player, Mob monster, double Damage) {
            CompoundTag data = player.getPersistentData();

            double DamageEnhance = 0;
            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装

            Damage *= (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));

            Compute.SummonValueItemEntity(monster.level(), player, monster,
                    Component.literal(String.format("%.0f", Damage)).withStyle(CustomStyle.styleOfSea));
            DirectDamageToMob(player, monster, Damage);
            return Damage;
        }
        public static double AttackDamageToMonster_RateAdDamage(Player player, Mob monster, double num) {
            CompoundTag data = player.getPersistentData();

            double Defence = Compute.MonsterDefence(monster);
            double BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
            double BreakDefence = Compute.PlayerAttributes.PlayerDefencePenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerDefencePenetration0(player);
            double DamageEnhance = 0;

            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += Compute.PlayerAttackDamageEnhance(player);
            DamageEnhance += IceKnight.IceKnightHealthAttackDamageFix(monster); // 冰霜骑士伤害修正

            BaseDamage *= (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));
            BaseDamage *= DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", BaseDamage * num)).withStyle(ChatFormatting.YELLOW));
            DirectDamageToMob(player, monster, BaseDamage * num);
            return BaseDamage * num;
        }

        public static double AttackDamageToMonster_AdDamage(Player player, Mob monster, double damage) {
            CompoundTag data = player.getPersistentData();

            double Defence = Compute.MonsterDefence(monster);
            double BreakDefence = Compute.PlayerAttributes.PlayerDefencePenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerDefencePenetration0(player);
            double DamageEnhance = 0;
            double Damage = damage;

            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += Compute.PlayerAttackDamageEnhance(player);
            DamageEnhance += IceKnight.IceKnightHealthAttackDamageFix(monster); // 冰霜骑士伤害修正

            damage *= (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));
            damage *= DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", damage)).withStyle(ChatFormatting.YELLOW));
            DirectDamageToMob(player, monster, damage);
            return damage;
        }

        public static double AttackDamageToPlayer_RateAdDamage(Player player, Player hurter, double num) {

            double Defence = Compute.PlayerAttributes.PlayerDefence(hurter);
            double BaseDamage = Compute.PlayerAttributes.PlayerAttackDamage(player);
            double BreakDefence = Compute.PlayerAttributes.PlayerDefencePenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerDefencePenetration0(player);
            double DamageEnhance = 0;
            CompoundTag data = player.getPersistentData();

            DamageEnhance += Compute.SwordSkillLevelGet(data, 4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）
            DamageEnhance -= (1 - DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0));

            DirectDamageToPlayer(player, hurter, BaseDamage * num * 0.1f * (1 + DamageEnhance));
            return BaseDamage * num * (1 + DamageEnhance);
        }

        public static void ManaDamageToMonster_RateApDamage(Player player, Mob monster, double num, boolean isPower) {
            CompoundTag data = player.getPersistentData();

            double Defence = Compute.MonsterManaDefence(monster);
            double BaseDamage = Compute.PlayerAttributes.PlayerManaDamage(player);
            double BreakDefence = Compute.PlayerAttributes.PlayerManaPenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerManaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += Eliaoi.EliaoiEffect(player,monster,false);
            DamageEnhance += Compute.PlayerManaDamageEnhance(player); // 魔法伤害提升
            if (data.getBoolean(StringUtils.Debug) && isPower) {
                player.sendSystemMessage(Component.literal("---ManaPower---"));
                player.sendSystemMessage(Component.literal("BaseDamage : " + BaseDamage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
            }
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) ExDamage += LiuLiXianCurios1F.Skill4ExDamage(player);
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.LiuLiXianIgnoreDefenceDamage(player,monster,(BaseDamage * num + ExDamage) * (1 + DamageEnhance));
            BaseDamage *= Compute.ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            ExDamage *= Compute.ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            double totalDamage = (BaseDamage * num + ExDamage) * (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));

            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            if (isPower) {
                Element.Unit playerUnit = Element.EntityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.Life, 0));
                if (playerUnit.value() > 0) {
                    ElementDamageEffect = Element.ElementEffectAddToEntity(player,monster,playerUnit.type(),playerUnit.value(),false,totalDamage);
                    Element.EntityElementUnit.put(player.getId(),new Element.Unit(Element.Life,0));
                }
                Compute.AddtionEffects(player,monster);
            }

            totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE));
            DirectDamageToMob(player, monster, totalDamage);
            WitherBook.WitherBookEffect(player,monster);
            CastleSceptre.ExDamage(player,monster,totalDamage);
            SoraVanillaSword.ExAttackDamage(player,monster,totalDamage);

            if (data.getBoolean(StringUtils.Debug) && isPower) {
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("Compute.PlayerFinalDamageEnhance(player,monster) : " + Compute.PlayerFinalDamageEnhance(player,monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }

        }

        public static void ManaDamageToMonster_RateApDamage_ElementAddition(Player player, Mob monster, double num, boolean isPower, String elementType, double elementValue) {
            CompoundTag data = player.getPersistentData();

            double Defence = Compute.MonsterManaDefence(monster);
            double BaseDamage = Compute.PlayerAttributes.PlayerManaDamage(player);
            double BreakDefence = Compute.PlayerAttributes.PlayerManaPenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerManaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += Eliaoi.EliaoiEffect(player,monster,false);
            DamageEnhance += Compute.PlayerManaDamageEnhance(player); // 魔法伤害提升
            if (data.getBoolean(StringUtils.Debug) && isPower) {
                player.sendSystemMessage(Component.literal("---ManaPower---"));
                player.sendSystemMessage(Component.literal("BaseDamage : " + BaseDamage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
            }
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) ExDamage += LiuLiXianCurios1F.Skill4ExDamage(player);
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.LiuLiXianIgnoreDefenceDamage(player,monster,(BaseDamage * num + ExDamage) * (1 + DamageEnhance));
            BaseDamage *= Compute.ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            ExDamage *= Compute.ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);
            double totalDamage = (BaseDamage * num + ExDamage) * (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));

            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            if (isPower) {
                ElementDamageEffect = Element.ElementEffectAddToEntity(player, monster, elementType, elementValue, false, totalDamage);
                Element.EntityElementUnit.put(player.getId(), new Element.Unit(Element.Life, 0));
                Compute.AddtionEffects(player,monster);
            }

            totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE));
            DirectDamageToMob(player, monster, totalDamage);
            WitherBook.WitherBookEffect(player,monster);
            CastleSceptre.ExDamage(player,monster,totalDamage);
            SoraVanillaSword.ExAttackDamage(player,monster,totalDamage);

            if (data.getBoolean(StringUtils.Debug) && isPower) {
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("Compute.PlayerFinalDamageEnhance(player,monster) : " + Compute.PlayerFinalDamageEnhance(player,monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }

        }

        public static void ManaDamageToMonster_ApDamage(Player player, Mob monster, double damage) {
            CompoundTag data = player.getPersistentData();

            double Defence = Compute.MonsterManaDefence(monster);
            double BreakDefence = Compute.PlayerAttributes.PlayerManaPenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerManaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += Eliaoi.EliaoiEffect(player,monster,false);
            DamageEnhance += Compute.PlayerManaDamageEnhance(player); // 魔法伤害提升

            if (LiuLiXianCurios1F.IsLiuLiXian(player)) ExDamage += LiuLiXianCurios1F.Skill4ExDamage(player);

            damage += ExDamage;

            damage *= Compute.ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

            double totalDamage = damage * (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));
            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE));
            DirectDamageToMob(player, monster, totalDamage);
            WitherBook.WitherBookEffect(player,monster);
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.LiuLiXianIgnoreDefenceDamage(player,monster,damage * (1 + DamageEnhance));
            CastleSceptre.ExDamage(player,monster,totalDamage);
            SoraVanillaSword.ExAttackDamage(player,monster,totalDamage);
        }

        public static void ManaDamageToMonster_ApDamage(Player player, Mob monster, double damage, boolean isPower) {
            CompoundTag data = player.getPersistentData();

            double Defence = Compute.MonsterManaDefence(monster);
            double BreakDefence = Compute.PlayerAttributes.PlayerManaPenetration(player);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerManaPenetration0(player);
            double DamageEnhance = 0;
            double ExDamage = 0;

            DamageEnhance += ManaSkill3(data, player, monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.PlayerCommonDamageUpOrDown(player, monster);
            DamageEnhance += IceKnight.IceKnightHealthManaDamageFix(monster); // 冰霜骑士伤害修正
            DamageEnhance += NetherManaArmor(player, monster); // 下界混沌套装
            DamageEnhance += Eliaoi.EliaoiEffect(player,monster,false);
            DamageEnhance += Compute.PlayerManaDamageEnhance(player); // 魔法伤害提升
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) ExDamage += LiuLiXianCurios1F.Skill4ExDamage(player);

            if (data.getBoolean(StringUtils.Debug) && isPower) {
                player.sendSystemMessage(Component.literal("---ManaPower---"));
                player.sendSystemMessage(Component.literal("BaseDamage : " + damage));
                player.sendSystemMessage(Component.literal("ExDamage : " + ExDamage));
            }

            damage += ExDamage;
            damage *= Compute.ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

            double totalDamage = damage * (1 + DamageEnhance) * (1 + Compute.PlayerFinalDamageEnhance(player,monster));
            // 元素
            double ElementDamageEnhance = 0;
            double ElementDamageEffect = 1;
            ElementDamageEnhance += Element.ElementWithstandDamageEnhance(monster);
            if (isPower) {
                Element.Unit playerUnit = Element.EntityElementUnit.getOrDefault(player.getId(), new Element.Unit(Element.Life, 0));
                if (playerUnit.value() > 0) {
                    ElementDamageEffect = Element.ElementEffectAddToEntity(player,monster,playerUnit.type(),playerUnit.value(),false,totalDamage);
                    Element.EntityElementUnit.put(player.getId(),new Element.Unit(Element.Life,0));
                }
                Compute.AddtionEffects(player,monster);
            }

            totalDamage *= (1 + ElementDamageEnhance) * ElementDamageEffect;

            Compute.SummonValueItemEntity(monster.level(), player, monster, Component.literal(String.format("%.0f", totalDamage)).withStyle(ChatFormatting.LIGHT_PURPLE));
            DirectDamageToMob(player, monster, totalDamage);
            WitherBook.WitherBookEffect(player,monster);
            if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.LiuLiXianIgnoreDefenceDamage(player,monster,damage * (1 + DamageEnhance));
            CastleSceptre.ExDamage(player,monster,totalDamage);
            SoraVanillaSword.ExAttackDamage(player,monster,totalDamage);

            if (data.getBoolean(StringUtils.Debug) && isPower) {
                player.sendSystemMessage(Component.literal("DamageEnhance : " + DamageEnhance));
                player.sendSystemMessage(Component.literal("Compute.PlayerFinalDamageEnhance(player,monster) : " + Compute.PlayerFinalDamageEnhance(player,monster)));
                player.sendSystemMessage(Component.literal("Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0) : " + Compute.DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0)));
                player.sendSystemMessage(Component.literal("ElementDamageEffect : " + ElementDamageEffect));
                player.sendSystemMessage(Component.literal("ElementDamageEnhance : " + ElementDamageEnhance));
                player.sendSystemMessage(Component.literal("totalDamage : " + totalDamage));
                player.sendSystemMessage(Component.literal("——————————————————————————————————————————"));
            }
        }

        public static void ManaDamageToPlayer(Player player, Player hurter, double num) {
            double damage = 0;

            double BaseDamage = Compute.PlayerAttributes.PlayerManaDamage(player);
            double BreakDefence = Compute.PlayerAttributes.PlayerManaPenetration(player);
            double BreakDefence0 = PlayerAttributes.PlayerManaPenetration0(player);
            double Defence = Compute.PlayerAttributes.PlayerManaDefence(hurter);
            double DamageEnhance = 0;

            DamageEnhance -= (1 - ManaDefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0));

            DirectDamageToPlayer(player, hurter, BaseDamage * num * 0.1f * (1 + DamageEnhance));
        }

        public static void ManaDamageToPlayer(Mob monster, Player player, double Damage) {
            CompoundTag data = player.getPersistentData();
            double ManaDefence = Compute.PlayerAttributes.PlayerManaDefence(player);
            Damage *= ManaDefenceDamageDecreaseRate(ManaDefence, 0, 0);
            if (player.isAlive()) MonsterAttackEvent.MonsterAttack(monster, player, Damage);
        }

        public static void DamageIgnoreDefenceToPlayer(Mob mob, Player player, double Damage) {
            MonsterAttackEvent.MonsterAttack(mob,player,Damage);
        }

        public static void AttackDamageToPlayer(Mob monster, Player player, double Damage) {
            CompoundTag data = player.getPersistentData();
            double Defence = PlayerAttributes.PlayerDefence(player);

            Damage *= DefenceDamageDecreaseRate(Defence, 0, 0);

            MonsterAttackEvent.MonsterAttack(monster, player, Damage);
        }

        public static void AttackDamageToPlayer_NumDamage(Mob monster, Player player, double Damage, double BreakDefence, double BreakDefence0) {
            double Defence = PlayerAttributes.PlayerDefence(player);

            Damage *= DefenceDamageDecreaseRate(Defence, BreakDefence, BreakDefence0);

            MonsterAttackEvent.MonsterAttack(monster, player, Damage);
        }

        public static void DirectDamageToMob(Player player, Entity entity, double damage) {
            if (entity instanceof Mob mob && !(entity instanceof Allay)) {
                FengXiaoJu.FengXiaoJuPassive(player,mob,damage);
                XiangLi.XiangliCuriosSkillHealthSteal(player,damage);
                Castle.CauseDamageRecord(player,mob); // 城堡伤害记录
                if (Moon.IsMoonAttackImmune(player, (Mob) entity)) damage *= 0.5;
                if (Moon.IsMoonManaImmune(player, (Mob) entity)) damage *= 0.5;
                if (LiuLiXianCurios1F.IsLiuLiXian(player)) damage *= LiuLiXianCurios1F.LiuLiXianTotalDamageEnhance(player);
                if (LiuLiXianCurios1F.IsLiuLiXian(player)) LiuLiXianCurios1F.Skill8Damage(player);
                EliaoiCurios1.CountAdd(player);
                EliaoiCurios2.CountAdd(player,mob);
                /*EliaoiCurios2.GroupBuff1Ignite(player,mob);*/
                EliaoiCurios2.MobCountsDamage(mob);
                CastleSecondFloor.PlayerPickItemExDamage(player,mob);
                damage *= CastleSecondFloor.MobDamageImmune(player,mob);
                AttackEvent.DamageCount(player,(Mob) entity,0,damage);
                if (player.isCreative()) player.sendSystemMessage(Component.literal("" + mob.getHealth() / mob.getMaxHealth()));
                entity.hurt(entity.damageSources().playerAttack(player), mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.WoodenStake5.get()) ? 0 : (float) damage);

                FireEquip.IgniteEffect(player, mob);

                WcndymlgbCurios.RangeAttack(player,mob,damage);
                WcndymlgbCurios.Summon(player);
                DpsCommand.CalculateDamage(player,damage);
                MoonBelt.PassiveCauseDamage(player,damage); // 尘月玉缠
                SoraVanillaSword.ShieldProvider(player,damage);
                YuanShiRenCurios.Passive(player,mob);
                BlackFeisaCurios3.CountAdd(player);
                entity.invulnerableTime = 0;
                StarBottle.PlayerBattleTickMapRefresh(player);
                Element.ElementParticleProvider(mob);
            }
        }

        public static void DirectDamageToPlayer(Player player, Player hurter, double Damage) {
            if (player.isCreative()) {
                player.sendSystemMessage(Component.literal("" + Damage));
                // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
            }
            else {
                hurter.hurt(hurter.damageSources().playerAttack(player), (float) Damage);
                hurter.invulnerableTime = 0;
                // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
            }
        }

        // 对于任意怪物对玩家造成的伤害，都需经过MonsterAttackEvent.MonsterAttack进行伤害以及其他增益的计算。
        // 对 怪物对玩家的伤害 或 玩家受到怪物伤害，只需在MonsterAttack修改
        // 对于玩家对怪物造成的伤害增益，需分为不同类型的伤害值进行计算。即需前往每个类型进行修改。其中，有RateAd/ApDamage可用。
    }

    public static void RandomToDesParticle(int num, Vec3 DesPos, Level level, double r) {
        if (Utils.particleOptionsList.isEmpty()) Utils.setParticleOptionsList();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            double angle = (2 * PI * random.nextDouble(1));
            Vec3 ParticleStartPos = new Vec3(DesPos.x - 4 + random.nextDouble(8) + r * Math.cos(angle),
                    DesPos.y - 2 + random.nextDouble(4),
                    DesPos.z - 4 + random.nextDouble(8) + r * Math.sin(angle));

            Vec3 Delta = DesPos.subtract(ParticleStartPos);
            double randomDouble = random.nextDouble(2);
            level.addParticle(Utils.particleOptionsList.get(random.nextInt(Utils.particleOptionsList.size())),
                    ParticleStartPos.x, ParticleStartPos.y, ParticleStartPos.z,
                    Delta.normalize().scale(randomDouble).x,
                    Delta.normalize().scale(randomDouble).y,
                    Delta.normalize().scale(randomDouble).z);


        }
    }

    public static boolean IsSoulEquip(ItemStack itemStack) {
        return itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())
                || itemStack.is(ModItems.SoulSceptre.get());
    }

    public static void ResetSkillAndAbility(Player player) {
        CompoundTag data = player.getPersistentData();
        data.remove(StringUtils.SkillPoint_Total);
        data.remove(StringUtils.SkillPoint_Used);
        data.remove(StringUtils.SkillData.Sword);
        data.remove(StringUtils.SkillData.Bow);
        data.remove(StringUtils.SkillData.Mana);
        data.remove(StringUtils.Skill.SwordBase);
        data.remove(StringUtils.Skill.BowBase);
        data.remove(StringUtils.Skill.ManaBase);
        data.remove(StringUtils.AbilityPoint_Total);
        data.remove(StringUtils.AbilityPoint_Used);
        for (int i = 0; i < 5; i++) {
            data.remove(StringUtils.AbilityArray[i]);
        }
        for (int i = 0; i < 3; i++) {
            data.remove(StringUtils.SkillArray[i]);
        }
        Compute.FormatMSGSend(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                Component.literal("你的大脑对于技艺的理解回归了数个时段。。。").withStyle(ChatFormatting.WHITE));
    }

    public static int getPlayerPsValue(Player player) {
        return player.getPersistentData().getInt(StringUtils.PsValue);
    }

    public static boolean addOrCostPlayerPsValue(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int CurrentPsValue = data.getInt(StringUtils.PsValue);
        if (CurrentPsValue > 100 && value > 0) return false;
        if (CurrentPsValue + value < 0) return false;
        CurrentPsValue += value;
        if (value < 0) {
            data.putInt(StringUtils.PsValue, CurrentPsValue);
            ModNetworking.sendToClient(new PsValueS2CPacket(CurrentPsValue), (ServerPlayer) player);
        }
        else {
            data.putInt(StringUtils.PsValue, Math.min(CurrentPsValue, 100));
            ModNetworking.sendToClient(new PsValueS2CPacket(Math.min(CurrentPsValue, 100)), (ServerPlayer) player);
        }
        return true;
    }

    public static boolean addOrCostPlayerPsValueIgnoreLimit(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int CurrentPsValue = data.getInt(StringUtils.PsValue);
        if (CurrentPsValue + value < 0) return false;
        CurrentPsValue += value;
        data.putInt(StringUtils.PsValue, CurrentPsValue);
        ModNetworking.sendToClient(new PsValueS2CPacket(CurrentPsValue), (ServerPlayer) player);
        return true;
    }

    public static void RespawnPlayer(Player player) {
        player.heal(player.getMaxHealth());
        ServerLevel overWorld = player.level().getServer().getLevel(Level.OVERWORLD);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.teleportTo(overWorld, 437.5, 68, 917.5, 0, 0);
    }

    public static void ManaCoreDescription(List<Component> components) {
        components.add(Component.literal("δ-魔核属性:").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public static void ClientCircleParticle(Level level, Vec3 bottomPos, Vec3 delta, double r, ParticleOptions particleOptions, int num) {
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y, bottomPos.z + r * sin(angle));
            level.addParticle(particleOptions, Point.x, Point.y, Point.z,
                    delta.x, delta.y, delta.z);
        }
    }

    public static void ClientCircleRollParticle(Level level, Vec3 bottomPos, Vec3 delta, double r, ParticleOptions particleOptions, int particleNum, double Height, int RollNum) {
        for (int j = 0; j < RollNum; j++) {
            for (int i = 0; i < particleNum; i++) {
                double angle = (2 * Math.PI / particleNum) * (i);
                Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y + Height * (j + i * 1.0 / particleNum), bottomPos.z + r * sin(angle));
                level.addParticle(particleOptions, Point.x, Point.y, Point.z,
                        delta.x, delta.y, delta.z);
            }
        }
    }

    public static void PurpleIronArmorAttributeGive(ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random random = new Random();
        data.putInt(StringUtils.RandomAttribute.AttackDamage, random.nextInt(50, 100));
        data.putInt(StringUtils.RandomAttribute.ManaDamage, random.nextInt(300, 400));
        data.putInt(StringUtils.RandomAttribute.Defence, random.nextInt(50, 100));
        data.putInt(StringUtils.RandomAttribute.MaxHealth, random.nextInt(400, 800));
    }

    public static void IceArmorAttributeGive(ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random random = new Random();
        data.putInt(StringUtils.RandomAttribute.AttackDamage, random.nextInt(100, 140));
        data.putInt(StringUtils.RandomAttribute.ManaDamage, random.nextInt(600, 800));
        data.putInt(StringUtils.RandomAttribute.Defence, random.nextInt(100, 200));
        data.putInt(StringUtils.RandomAttribute.MaxHealth, random.nextInt(800, 1600));
    }


    public static void purpleMineBlockPosInit(Level level, boolean byCommand) {
        if (Utils.playerDigCount > 200 || byCommand) {
            Compute.FormatBroad(level, Component.literal("紫晶矿").withStyle(CustomStyle.styleOfPurpleIron),
                    Component.literal("樱岛紫晶矿已刷新！").withStyle(ChatFormatting.WHITE));
            if (!Utils.playerDigPos.isEmpty()) {
                Utils.playerDigPos.forEach(blockSP -> {
                    if (blockSP.getBlockState().is(Blocks.AMETHYST_BLOCK))
                        level.setBlockAndUpdate(blockSP.getBlockPos(), Blocks.STONE.defaultBlockState());
                    else
                        level.setBlockAndUpdate(blockSP.getBlockPos(), blockSP.getBlockState());
                });
            }
            Random random = new Random();
            BlockPos blockPos1 = new BlockPos(2025, 45, 1280);
            BlockPos blockPos2 = new BlockPos(1982, 24, 1243);
            BlockPos blockPos3 = new BlockPos(1955, 45, 1231);
            BlockPos blockPos4 = new BlockPos(1901, 24, 1194);
            for (int i = 0; i < 16000; i++) {
                BlockPos blockPos = new BlockPos(random.nextInt(blockPos2.getX(), blockPos1.getX()),
                        random.nextInt(blockPos2.getY(), blockPos1.getY()),
                        random.nextInt(blockPos2.getZ(), blockPos1.getZ()));
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.STONE)) {
                    level.setBlockAndUpdate(blockPos, Blocks.AMETHYST_BLOCK.defaultBlockState());
                }

                blockPos = new BlockPos(random.nextInt(blockPos4.getX(), blockPos3.getX()),
                        random.nextInt(blockPos4.getY(), blockPos3.getY()),
                        random.nextInt(blockPos4.getZ(), blockPos3.getZ()));
                blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.STONE)) {
                    level.setBlockAndUpdate(blockPos, Blocks.AMETHYST_BLOCK.defaultBlockState());
                }
            }
        }
    }

    /*    public static boolean PlayerIsOnline(Player player) {
        CompoundTag data = player.getPersistentData();
        return (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online));
    }*/
    public static void PlayerColdNumAddOrCost(Player player, double Num) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.MaxCold) || !data.contains(StringUtils.CurrentCold)) {
            data.putDouble(StringUtils.MaxCold, 100);
            data.putDouble(StringUtils.CurrentCold, 0);
        } else {
            double MaxCold = data.getDouble(StringUtils.MaxCold);
            double CurrentCold = data.getDouble(StringUtils.CurrentCold);
            if (Num > 0) data.putDouble(StringUtils.CurrentCold, Math.min(100, CurrentCold + Num));
            else data.putDouble(StringUtils.CurrentCold, Math.max(0, CurrentCold + Num));
        }
        PlayerColdNumStatusUpdate(player);
    }

    public static void PlayerColdNumStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new ColdSyncS2CPacket(data.getDouble(StringUtils.MaxCold), data.getDouble(StringUtils.CurrentCold)), (ServerPlayer) player);
    }

    public static double PlayerCurrentColdNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.CurrentCold);
    }

    public static double PlayerMaxColdNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.MaxCold);
    }

    public static double PlayerColdEffect(Player player) {
        if (PlayerCurrentColdNum(player) > 90) return -0.5;
        return 0;
    }

    public static Calendar StringToCalendar(String DateString) throws ParseException {
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        if (DateString != "") {
            Date date1 = tmpDate.parse(DateString);
            cal.setTime(date1);
        }
        return cal;
    }

    public static String CalendarToString(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
        return tmpDate.format(date);
    }

    public static int playerReputation(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(StringUtils.Reputation);
    }

    public static boolean playerReputationAddOrCost(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        ChatFormatting chatFormatting = ChatFormatting.GREEN;
        if (Num < 0) {
            if (playerReputation(player) + Num < 0) {
                Compute.FormatMSGSend(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                        Component.literal("当前声望不足。").withStyle(ChatFormatting.WHITE));
                return false;
            }
            chatFormatting = ChatFormatting.RED;
        }
        data.putInt(StringUtils.Reputation, data.getInt(StringUtils.Reputation) + Num);
        data.putInt(StringUtils.ReputationCalculate, data.getInt(StringUtils.ReputationCalculate) + Num);
        Compute.FormatMSGSend(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                Component.literal("你的声望值:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("" + playerReputation(player)).withStyle(ChatFormatting.YELLOW)).
                        append(Component.literal(" (" + Num + ")").withStyle(chatFormatting)));
        ModNetworking.sendToClient(new ReputationValueS2CPacket(data.getInt(StringUtils.Reputation)), (ServerPlayer) player);
        return true;
    }

    public static double playerFantasyAttributeEnhance(Player player) {
        double enhance = 1;
        CompoundTag data = player.getPersistentData();
        if (data.getBoolean(StringUtils.FantasyMedal)) enhance += 0.1;
        if (data.getBoolean(StringUtils.FantasyBracelet)) enhance += 0.1;
        return enhance;
    }

    public static boolean hasBonfireNearBy(Player player) {
        int x = player.getBlockX() - 5, y = player.getBlockY() - 5, z = player.getBlockZ() - 5;
        Level level = player.level();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    BlockState blockState = level.getBlockState(new BlockPos(x + i, y + j, z + k));
                    if (blockState.is(Blocks.CAMPFIRE) || blockState.is(Blocks.SOUL_CAMPFIRE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int playerMineLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Mine.Exp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void playerMineExpAdd(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Mine.Exp, data.getInt(StringUtils.Mine.Exp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("采矿经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(ChatFormatting.DARK_GREEN)).
                append(Component.literal("" + Num).withStyle(ChatFormatting.GREEN)).
                append(Component.literal(" (" + data.getInt(StringUtils.Mine.Exp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static int playerGardeningLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Gardening.Xp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void playerGardeningExpAdd(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Gardening.Xp, data.getInt(StringUtils.Gardening.Xp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("园艺经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(ChatFormatting.DARK_GREEN)).
                append(Component.literal("" + Num).withStyle(ChatFormatting.GREEN)).
                append(Component.literal(" (" + data.getInt(StringUtils.Gardening.Xp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static int playerLopLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Lop.Xp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void playerLopExpAdd(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Lop.Xp, data.getInt(StringUtils.Lop.Xp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("伐木经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(CustomStyle.styleOfBlackForest)).
                append(Component.literal("" + Num).withStyle(CustomStyle.styleOfBlackForest)).
                append(Component.literal(" (" + data.getInt(StringUtils.Lop.Xp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }

    public static void SummonValueItemEntity(Level level, Player player, Mob mob, Component component) {
        Vec3 delta = player.position().subtract(mob.position());
        Vec3 delta0 = new Vec3(delta.x, 0, delta.z);
        Random random = new Random();
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
        itemEntity.setItem(ModItems.Value.get().getDefaultInstance());
        itemEntity.setCustomName(component);
        itemEntity.setCustomNameVisible(true);
        itemEntity.moveTo(mob.getEyePosition());
        itemEntity.setPickUpDelay(200);
        itemEntity.setDeltaMovement(new Vec3(delta0.normalize().scale(0.1).x, 0.1, delta0.normalize().scale(0.1).z));
        Utils.valueItemEntity.add(new ItemEntityAndResetTime(itemEntity, level.getServer().getTickCount() + 12));
        level.addFreshEntity(itemEntity);
    }

    public static double XpStrengthADDamage(Player player, double rate) {
        return Compute.PlayerAttributes.PlayerAttackDamage(player) * (1 + player.experienceLevel * 5 / 100) * rate;
        // rate 为倍率
    }

    public static double XpStrengthAPDamage(Player player, double rate) {
        return Compute.PlayerAttributes.PlayerManaDamage(player) * (1 + player.experienceLevel * 5 / 100) * rate;
        // rate 为倍率
    }

    public static double XpStrengthDamage(Player player, double rate) {
        double attackDamage = Compute.PlayerAttributes.PlayerAttackDamage(player)
                , manaDamage = Compute.PlayerAttributes.PlayerManaDamage(player)
                , base = (1 + player.experienceLevel * 5 / 100);
        return attackDamage * 4 > manaDamage ? attackDamage * 4 * base * rate : manaDamage * base * rate;
    }

    public static void TransformMapToSortedList() {
        if (Utils.dayKillCountList.size() > 0) Utils.dayKillCountList = new ArrayList<>();
        if (Utils.dayFishCountList.size() > 0) Utils.dayFishCountList = new ArrayList<>();
        if (Utils.dayMineCountList.size() > 0) Utils.dayMineCountList = new ArrayList<>();
        if (Utils.dayCropCountList.size() > 0) Utils.dayCropCountList = new ArrayList<>();
        if (Utils.dayLopCountList.size() > 0) Utils.dayLopCountList = new ArrayList<>();
        if (Utils.dayOnlineCountList.size() > 0) Utils.dayOnlineCountList = new ArrayList<>();

        Utils.dayKillCount.keySet().forEach(s -> {
            Utils.dayKillCountList.add(new PlayerNameAndCount(s, Utils.dayKillCount.get(s)));
        });
        Utils.dayFishCount.keySet().forEach(s -> {
            Utils.dayFishCountList.add(new PlayerNameAndCount(s, Utils.dayFishCount.get(s)));
        });
        Utils.dayMineCount.keySet().forEach(s -> {
            Utils.dayMineCountList.add(new PlayerNameAndCount(s, Utils.dayMineCount.get(s)));
        });
        Utils.dayCropCount.keySet().forEach(s -> {
            Utils.dayCropCountList.add(new PlayerNameAndCount(s, Utils.dayCropCount.get(s)));
        });
        Utils.dayLopCount.keySet().forEach(s -> {
            Utils.dayLopCountList.add(new PlayerNameAndCount(s, Utils.dayLopCount.get(s)));
        });
        Utils.dayOnlineCount.keySet().forEach(s -> {
            Utils.dayOnlineCountList.add(new PlayerNameAndCount(s, Utils.dayOnlineCount.get(s)));
        });

        Utils.dayKillCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayFishCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayMineCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayCropCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayLopCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());
        Utils.dayOnlineCountList.sort(Comparator.comparing(PlayerNameAndCount::count).reversed());

    }

    public static void SummonArmorStandForDisplay(Level level, List<ArmorStand> armorStands, List<PlayerNameAndCount> playerNameAndCounts, String type, Vec3 location) {
        armorStands.forEach(armorStand -> {
            if (armorStand.isAlive()) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        List<Component> components = ListToComponents(playerNameAndCounts, level, type);
        for (int i = 0; i < 6; i++) {
            ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
            armorStands.add(armorStand);
            armorStand.setNoGravity(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(components.get(i));
            armorStand.setInvulnerable(true);
            armorStand.setInvisible(true);
            armorStand.noPhysics = true;
            armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
            armorStand.moveTo(location.add(0, -0.25 * i, 0));
            level.addFreshEntity(armorStand);
        }
    }

    public static List<Component> ListToComponents(List<PlayerNameAndCount> playerNameAndCounts, Level level, String type) {
        List<Component> components = new ArrayList<>();
        switch (type) {
            case "Kill" -> components.add(Component.literal("击杀数排名").withStyle(CustomStyle.styleOfNether));
            case "Fish" -> components.add(Component.literal("钓鱼数排名").withStyle(CustomStyle.styleOfSea));
            case "Mine" -> components.add(Component.literal("挖矿数排名").withStyle(CustomStyle.styleOfSakuraMine));
            case "Crop" -> components.add(Component.literal("采集数排名").withStyle(CustomStyle.styleOfHealth));
            case "Lop" -> components.add(Component.literal("伐木数排名").withStyle(CustomStyle.styleOfBlackForest));
            case "Online" -> components.add(Component.literal("在线时长排名").withStyle(CustomStyle.styleOfSakura));
        }
        for (int i = 0; i < 5; i++) {
            if (i >= playerNameAndCounts.size()) {
                components.add(Component.literal((i + 1) + ".暂无").withStyle(ChatFormatting.GRAY));
            } else {
                ChatFormatting chatFormatting;
                switch (i) {
                    case 0 -> chatFormatting = ChatFormatting.RED;
                    case 1 -> chatFormatting = ChatFormatting.LIGHT_PURPLE;
                    case 2 -> chatFormatting = ChatFormatting.GOLD;
                    default -> chatFormatting = ChatFormatting.AQUA;
                }
                PlayerNameAndCount playerNameAndCount = playerNameAndCounts.get(i);
                Component playerName = null;
                if (level.getServer().getPlayerList().getPlayerByName(playerNameAndCount.name()) == null) {
                    playerName = Utils.playerNameMap.get(playerNameAndCount.name());
                } else
                    playerName = level.getServer().getPlayerList().getPlayerByName(playerNameAndCount.name()).getDisplayName();

                components.add(Component.literal((i + 1) + ". ").withStyle(chatFormatting).
                        append(playerName).
                        append(Component.literal(": " + playerNameAndCount.count()).withStyle(ChatFormatting.WHITE)));

            }
        }
        return components;
    }

    public static void RemoveAllArmorStandForDisplay() {
        LogUtils.getLogger().info("Removing armorstands.");
        Utils.dayKillCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayFishCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayMineCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayCropCountDisplayList.forEach(armorStand -> {
            if (armorStand != null) armorStand.remove(Entity.RemovalReason.KILLED);
        });
        Utils.dayLopCountDisplayList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
        Utils.dayOnlineCountDisplayList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
        if (Utils.recordTimeArmorStand != null) Utils.recordTimeArmorStand.remove(Entity.RemovalReason.KILLED);
        if (Utils.recordTimeArmorStand1 != null) Utils.recordTimeArmorStand1.remove(Entity.RemovalReason.KILLED);
    }

    public static void SummonRecordTimeDisplay(Level level, Vec3 location, boolean isFirst) {
        ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
        if (isFirst) {
            if (Utils.recordTimeArmorStand != null) Utils.recordTimeArmorStand.remove(Entity.RemovalReason.KILLED);
            Utils.recordTimeArmorStand = armorStand;
        } else {
            if (Utils.recordTimeArmorStand1 != null) Utils.recordTimeArmorStand1.remove(Entity.RemovalReason.KILLED);
            Utils.recordTimeArmorStand1 = armorStand;
        }
        armorStand.setNoGravity(true);
        armorStand.setCustomNameVisible(true);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String time = simpleDateFormat.format(Utils.countBeginToRecordTime.getTime());
        armorStand.setCustomName(Component.literal("记录开始时间:" + time).withStyle(ChatFormatting.AQUA));
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.noPhysics = true;
        armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
        armorStand.moveTo(location);
        level.addFreshEntity(armorStand);
    }

    public static double PlayerCommonDamageUpOrDown(Player player, Mob monster) {
        double rate = 0;
        rate += LevelSuppress(player,monster); // 等级压制
        rate += AttackEventModule.IceArmorDamageEnhance(player, monster); // 雪上覆霜
        rate += IceBook.IceBookDamageEnhance(player,monster); // 冰封的记忆
        rate += MoonArmor.DamageEnhance(player,monster); // 尘月膝
        rate += LeiyanBow.MobDamageEnhance(monster);
        rate += CastleCurios.DamageEnhance(player,monster); // 随机饰品被动
        rate += PurpleIronSword.DamageEnhance(player,monster);
        rate += QiFuCurios1.DamageEnhance(player,monster);
        rate += StarArmor.DamageEnhance(player,monster); // 梦月
        rate += HeihuangCurios.MobWithstandDamageEnhance(monster);
        rate += PlayerCommonDamageUpOrDown(player);
        return rate;
    }

    public static double PlayerCommonDamageUpOrDown(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSkill5DamageEnhance(player); // 双刃剑
        rate += AttackEventModule.ManaSkill5DamageEnhance(player); // 法术专注
        rate += PlayerColdEffect(player); // 寒冷
        rate += CastleSword.DamageEnhance(player);
        rate += EliaoiCurios2.GroupBuff2DamageEnhance(player);
        rate += StarBottle.DamageEnhance(player); // 星星瓶
        rate += ZuoSiCurios.DamageEnhance(player);
        rate += BlackFeisaCurios4.DamageEnhance(player);
        rate += BlackFeisaCurios4.DamageEnhance1(player);
        rate += LiulixianCurios3.DamageEnhance(player);
        rate += FireEquip.DamageEnhance(player); // 炽焰元素武器
        rate += HgjCurios.DamageEnhance(player);
        return rate;
    }

    public static double PlayerFinalDamageEnhance(Player player, Mob mob) {
        double rate = 0;
        rate -= MobEffectAndDamageMethods.PlayerDamageDecreaseRate(player,mob);
        rate += AttackCurios0.PlayerFinalDamageEnhance(player);
        rate += BowCurios0.PlayerFinalDamageEnhance(player);
        rate += ManaCurios0.PlayerFinalDamageEnhance(player);
        rate += YuanShiRenCurios.DamageEnhance(mob);
        return rate;
    }

    public static double PlayerAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.SwordSKillEnhance(player); // 多余技能点
        rate += AttackEventModule.CrushCuriosCountsDamageEnhance(player);
        rate += AttackEventModule.BowSKillEnhance(player); // 多余技能点
        return rate;
    }

    public static double PlayerManaDamageEnhance(Player player) {
        double rate = 0;
        rate += EliaoiCurios1.DamageEnhance(player);
        rate += EliaoiSceptre.DamageEnhance(player);
        rate += CgswdCurios.ExManaDamageEnhance(player);
        rate += SoraVanilla2.ManaDamageEnhance(player);
        rate += BlackFeisaCurios.DamageEnhance(player);
        rate += ManaAttackModule.ManaSkill10DamageEnhance(player);
        rate += ManaSkill11(player);
        rate += ShangMengLi.ShangMengLiCurios1(player);
        rate += FengXiaoJuCurios1.DamageEnhance(player);
        rate += AttackEventModule.ManaSKillEnhance(player);
        rate += BlackFeisaCurios3.Passive1ManaDamageEnhance(player);
        rate += BlackFeisaCurios3.Passive2ManaDamageEnhance(player);
        rate += ShangMengLiCurios3.ManaDamageEnhance(player);
        rate += LiulixianCurios2.DamageEnhance(player);
        return rate;
    }

    public static double PlayerNormalSwordAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackEventModule.MineShield(player); // 盾击
        return rate;
    }

    public static double PlayerNormalBowAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += 1.5 - (12 / (8 + Compute.PlayerAttributes.PlayerExtraSwiftness(player))); // 迅捷加成
        return rate;
    }

    public static double PlayerNormalManaAttackDamageEnhance(Player player) {
        double rate = 0;
        rate += EliaoiCurios1.NormalAttackDamageEnhance(player);
        rate += BlackFeisaCurios3.Passive1ManaDamageEnhance(player);
        rate += LiulixianCurios2.DamageEnhance(player);
        return rate;
    }

    public static double PlayerFinalDamageEnhance(Player player) {
        double rate = 0;
        rate += AttackCurios0.PlayerFinalDamageEnhance(player);
        rate += BowCurios0.PlayerFinalDamageEnhance(player);
        rate += ManaCurios0.PlayerFinalDamageEnhance(player);
        rate += LiulixianCurios3.FinalDamageEnhance(player);
        return rate;
    }

    public static double PlayerTotalDamageRate(Player player) {
        double rate = 1;
        rate += LiulixianCurios3.SelfDamageDecrease(player);
        return rate;
    }

    public static void AddtionEffects(Player player, Mob mob) {
        EliaoiCurios2.GroupBuff1Ignite(player,mob);

        if (!Element.ElementPieceOnWeapon(player)) Element.ResonanceEffectGive(player);
    }

    public static void SummonWoodenStake(Level level) {
        Item[] Armors = {
                ModItems.WoodenStake0.get(),
                ModItems.WoodenStake1.get(),
                ModItems.WoodenStake2.get(),
                ModItems.WoodenStake3.get(),
                ModItems.WoodenStake4.get(),
                ModItems.WoodenStake5.get()
        };
        Vec3[] vec3s = {
                new Vec3(309.5, 64, 961.5),
                new Vec3(311.5, 64, 961.5),
                new Vec3(313.5, 64, 961.5),
                new Vec3(315.5, 64, 961.5),
                new Vec3(317.5, 64, 961.5),
                new Vec3(370.5, 83, 989.5)
        };
        for (int i = 0; i < 6; i++) {
            if (Utils.WoodenStake[i] == null || !Utils.WoodenStake[i].isAlive()) {
                Utils.WoodenStake[i] = new Zombie(EntityType.ZOMBIE, level);
                Compute.SetMobCustomName(Utils.WoodenStake[i], Armors[i], Component.literal("木桩").withStyle(ChatFormatting.GREEN));
                Utils.WoodenStake[i].setItemSlot(EquipmentSlot.HEAD, Armors[i].getDefaultInstance());
                Utils.WoodenStake[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.pow(10,(i == 5) ? 12 : 9));
                Utils.WoodenStake[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
                Utils.WoodenStake[i].heal(Utils.WoodenStake[i].getMaxHealth());
                Utils.WoodenStake[i].moveTo(vec3s[i]);
                level.addFreshEntity(Utils.WoodenStake[i]);
            }
        }
    }

    public static void ClearWoodenStake() {
        for (Zombie zombie : Utils.WoodenStake) {
            if (zombie != null) zombie.remove(Entity.RemovalReason.KILLED);
        }
    }

    public static void HealWoodenStake() {
        for (Zombie zombie : Utils.WoodenStake) {
            if (zombie != null) zombie.heal(zombie.getMaxHealth());
        }
    }

    public static boolean thisTeamIsChallenging(PlayerTeam playerTeam) {
        return Utils.ChallengingPlayerTeam.contains(playerTeam);
    }

    public static void ManaCoreAddition(ItemStack stack, List<Component> components) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)) {
            Compute.ManaCoreDescription(components);
            String ManaCore = data.getString(StringUtils.ManaCore.ManaCore);
            String SeaCore = StringUtils.ManaCore.SeaCore;
            String BlackForestCore = StringUtils.ManaCore.BlackForestCore;
            String KazeCore = StringUtils.ManaCore.KazeCore;
            String SakuraCore = StringUtils.ManaCore.SakuraCore;
            if (ManaCore.equals(SeaCore)) {
                Compute.DescriptionPassive(components, Component.literal("灵魂救赎").withStyle(CustomStyle.styleOfSea));
                components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
                components.add(Component.literal("基于目标已损失生命值造成至多0.5倍的").withStyle(CustomStyle.styleOfSea).
                        append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                        append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
                components.add(Component.literal("倍率随目标已损失生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            } else if (ManaCore.equals(BlackForestCore)) {
                Compute.DescriptionPassive(components, Component.literal("灵魂收割").withStyle(CustomStyle.styleOfBlackForest));
                components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
                components.add(Component.literal("基于目标当前生命值造成至多0.5倍的").withStyle(CustomStyle.styleOfBlackForest).
                        append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                        append(Component.literal("额外魔法伤害").withStyle(CustomStyle.styleOfBlackForest)));
                components.add(Component.literal("倍率随目标当前生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));

            } else if (ManaCore.equals(KazeCore)) {
                Compute.DescriptionPassive(components, Component.literal("狂风晶核").withStyle(CustomStyle.styleOfSea));
                components.add(Component.literal("-获得").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.MovementSpeed("30%")));
                components.add(Component.literal("-基于你的").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.MovementSpeed("")).
                        append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.ManaPenetration("")));
                components.add(Component.literal("-每").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.ExMovementSpeed("1%")).
                        append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.ManaPenetration("1")));
            } else if (ManaCore.equals(SakuraCore)) {
                Compute.DescriptionPassive(components, Component.literal("樱妖晶核").withStyle(CustomStyle.styleOfDemon));
                components.add(Component.literal("第一枚法球造成").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.ManaDamage("100%")).
                        append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
                components.add(Component.literal("第二枚法球回复").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.ManaDamage("1.25%")).
                        append(Compute.AttributeDescription.Health("")));
            }
        }
        else {
            components.add(Component.literal(" 「尚未加载魔核」").withStyle(CustomStyle.styleOfMana));
        }
    }

    public static void FireWorkSummon(Player player, Mob monster) {
        CompoundTag compoundTag = new CompoundTag();
        byte a = 1;
        byte[] bytes = {0, 1, 2, 3, 4};
        String[] strings = {
                "SMALL_BALL",
                "LARGE_BALL",
                "CREEPER",
                "STAR",
                "BURST"
        };
        Random random = new Random();
        compoundTag.putByte("Type", a);
        compoundTag.putByte("Trail", a);
        compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
        compoundTag.putByte("Flicker", a);
        compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
        compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
        ListTag listTag = new ListTag();
        listTag.add(compoundTag);
        itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(
                player.level(), itemStack, monster);
        fireworkRocketEntity.setSilent(true);
        fireworkRocketEntity.setDeltaMovement(0, 0, 0);
        fireworkRocketEntity.moveTo(monster.position().add(0, 1, 0));
        player.level().addFreshEntity(fireworkRocketEntity);
    }

    public static void EffectLastTimeSend(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(itemStack, tickCount), (ServerPlayer) player);
    }

    public static void EffectLastTimeSend(Player player, ItemStack itemStack, int tickCount, int level) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(itemStack, tickCount, level), (ServerPlayer) player);
    }

    public static void EffectLastTimeSend(Player player, ItemStack itemStack, int tickCount, int level, boolean NoTime) {
        ModNetworking.sendToClient(new EffectLastTimeS2CPacket(itemStack, tickCount, level, NoTime), (ServerPlayer) player);
    }

    public static void CoolDownTimeSend(Player player, ItemStack itemStack, int tickCount) {
        ModNetworking.sendToClient(new CoolDownTimeS2CPacket(itemStack, tickCount), (ServerPlayer) player);
    }

    public static void DebuffTimeSend(Player player, ItemStack itemStack, int tickCount, int level) {
        ModNetworking.sendToClient(new DebuffTimeS2CPacket(itemStack,tickCount,level),(ServerPlayer) player);
    }

    public static int EndRune3Judge(Player player, int type) {
        CompoundTag data = player.getPersistentData();
        if (data.getInt(StringUtils.EndRune) == 3 && data.getInt(StringUtils.RecallEndRune3) == type) {
            return 2;
        }
        return 1;
    }

    public static int LevelTypeJudge(Player player) {
        Level level = player.level();
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        Level nether = player.getServer().getLevel(Level.NETHER);
        Level end = player.getServer().getLevel(Level.END);
        if (level.equals(overWorld)) return 0;
        if (level.equals(nether)) return 1;
        return 2;
    }

    public static void IceParticleCreate(Entity entity, Level level) {
        BlockPos blockPos = entity.blockPosition().above();
        if (level.getBlockState(blockPos).is(Blocks.AIR)) {
            level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
            level.destroyBlock(blockPos, false);
        }
    }

    public static void FaceIceParticleCreate(Entity entity, Level level) {
        Vec3 vec3 = entity.pick(0.5,0,false).getLocation();
        BlockPos blockPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
        if (level.getBlockState(blockPos).is(Blocks.AIR)) {
            level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
            level.destroyBlock(blockPos, false);
        }
    }

    public static boolean MonsterCantBeMove(Mob mob) {
        for (int i = 0; i < Utils.instanceKillCount.length; i++) {
            if (mob instanceof Civil) return true;
            if (Utils.instanceList.get(i).getMobList() != null && Utils.instanceList.get(i).getMobList().contains(mob)) return true;
        }
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorGiant.get())) return true;
        return false;
    }

    public static void RepelMob(Player player, Vec3 StartPos, double range, double rate, double scaleLimit) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(StartPos, 20, 20, 20));
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(StartPos);
            if (PosVec.length() <= range) {
                if (!MonsterCantBeMove(mob)) mob.setDeltaMovement(PosVec.normalize().scale(Math.min(scaleLimit, rate / PosVec.length())));
            }
        });
    }

    public static void RepelPlayer(Mob mob, Vec3 StartPos, double range, double rate, double scaleLimit) {
        List<Player> mobList = mob.level().getEntitiesOfClass(Player.class,
                AABB.ofSize(StartPos, 20, 20, 20));
        mobList.forEach(player -> {
            Vec3 PosVec = player.position().subtract(StartPos);
            if (PosVec.length() <= range) {
                Vec3 deltaMovement = PosVec.normalize().scale(Math.min(scaleLimit, rate / PosVec.length()));
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket
                        = new ClientboundSetEntityMotionPacket(player.getId(),deltaMovement);
                ((ServerPlayer) player).connection.send(clientboundSetEntityMotionPacket);
            }
        });
    }

    public static void Laser(Player player, ParticleOptions particleOptions, double BaseDamage, int TickCoolDown) {
        Level level = player.level();
        int TickCount = player.getServer().getTickCount();
        Vec3 TargetPos = player.pick(25,0,false).getLocation();
        Vec3 StartPos = player.pick(0.5,0,false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.LineParticle(level,(int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0 ; i < Distance ; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)),0.5,0.5,0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }

        if (!Utils.playerLaserCoolDown.containsKey(player)) Utils.playerLaserCoolDown.put(player,new HashMap<>());
        Map<Mob,Integer> laserCoolDownMap = Utils.playerLaserCoolDown.get(player);

        mobList.forEach(mob -> {
            if (!laserCoolDownMap.containsKey(mob) || laserCoolDownMap.get(mob) <= TickCount) {
                laserCoolDownMap.put(mob,TickCount + TickCoolDown);
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                        Compute.PlayerAttributes.PlayerManaDamage(player),
                        Compute.PlayerAttributes.PlayerManaPenetration(player),
                        Compute.PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Lava);
                ManaAttackModule.BasicAttack(player,mob,BaseDamage,
                        Compute.PlayerAttributes.PlayerManaPenetration(player),
                        Compute.PlayerAttributes.PlayerManaPenetration0(player),
                        level,newArrow);
            }
        });
    }

    public static void TargetLocationLaser(Player player, Vec3 location, ParticleOptions particleOptions, double BaseDamage, int TickCoolDown) {
        Level level = player.level();
        int TickCount = player.getServer().getTickCount();
        Vec3 TargetPos = location;
        Vec3 StartPos = player.pick(0.5,0,false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.LineParticle(level,(int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0 ; i < Distance ; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)),0.5,0.5,0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }

        if (!Utils.playerLaserCoolDown.containsKey(player)) Utils.playerLaserCoolDown.put(player,new HashMap<>());
        Map<Mob,Integer> laserCoolDownMap = Utils.playerLaserCoolDown.get(player);

        mobList.forEach(mob -> {
            if (!laserCoolDownMap.containsKey(mob) || laserCoolDownMap.get(mob) <= TickCount) {
                laserCoolDownMap.put(mob,TickCount + TickCoolDown);
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                        Compute.PlayerAttributes.PlayerManaDamage(player),
                        Compute.PlayerAttributes.PlayerManaPenetration(player),
                        Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Lava);
                ManaAttackModule.BasicAttack(player,mob,BaseDamage,
                        Compute.PlayerAttributes.PlayerManaPenetration(player),
                        Compute.PlayerAttributes.PlayerManaPenetration0(player),
                        level,newArrow);
            }
        });
    }

    public static double PlayerDodgeRate(Player player) {
        double swift = Math.min(PlayerAttributes.PlayerExtraSwiftness(player), 90);
        double rate = 0;
        if (swift <= 10) rate = swift * 0.02;
        else if (swift <= 20) rate = 0.2 + (swift - 10) * 0.015;
        else if (swift <= 30) rate = 0.35 + (swift - 20) * 0.01;
        else rate = 0.45 + (swift - 30) * 0.005;
        rate += YxwgCurios2.Passive4DodgeRate(player);
        return rate;
    }

    public static class CuriosAttribute {

        public static void ResetCuriosList(Player player) {
            if (Utils.playerCuriosListMap.containsKey(player)) {
                Utils.playerCuriosListMap.get(player).clear();
            }
        }

        public static double AttributeValue(Player player, Map<Item,Double> attributeMap, String attributeName) {
            if (Utils.playerCuriosListMap.containsKey(player)) {
                AtomicReference<Double> value = new AtomicReference<>((double) 0);
                List<ItemStack> list = Utils.playerCuriosListMap.get(player);
                List<Item> itemList = new ArrayList<>();
                list.forEach(itemStack -> {
                    if (!itemList.contains(itemStack.getItem())
                            && (!Utils.LevelRequire.containsKey(itemStack.getItem()) || player.experienceLevel >= Utils.LevelRequire.get(itemStack.getItem()))) {
                        if (attributeMap.containsKey(itemStack.getItem()))
                            value.set(value.get() + attributeMap.get(itemStack.getItem()));
                        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        if (data.contains(attributeName)) {
                            if (itemStack.getItem() instanceof CastleCurios)
                                value.set(value.get() + data.getDouble(attributeName)
                                        * CastleCurios.AttributeValueMap.get(attributeName));
                            else value.set(value.get() + data.getInt(attributeName));
                        }
                        itemList.add(itemStack.getItem());
                    }
                });
                return value.get();
            }
            return 0;
        }
    }

    public static boolean isOnCurios(Player player, Item curios) {
        if (Utils.playerCuriosListMap.containsKey(player)) {
            List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
            for (ItemStack itemStack : curiosList) {
                if (itemStack.is(curios)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Mob powerDetectPlayerPickMob(Player player) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        Vec3 StartPos = player.pick(0.5,0,false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        for (double i = 0 ; i < Distance ; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)),0.5,0.5,0.5));
            for (Mob mob : mobList1) {
                return mob;
            }
        }
        return null;
    }

    public static Mob powerDetectPlayerPickMob(Player player, double distance, double range) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(distance,0,false).getLocation();
        Vec3 StartPos = player.pick(0.5,0,false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        for (double i = 0 ; i < Distance ; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)),
                    i > range ? range : 0.5,i > range ? range : 0.5,i > range ? range : 0.5));
            for (Mob mob : mobList1) {
                return mob;
            }
        }
        return null;
    }

    public static void ParticleEffectAddOnEntity(Entity entity, ParticleOptions particleOptions) {
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 1.25, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 1, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.75, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.5, 0.4, 8, particleOptions, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(entity, 0.25, 0.4, 8, particleOptions, 0);
    }

    public static void AddCuriosToList(Player player, ItemStack stack) {
        if (!Utils.playerCuriosListMap.containsKey(player)) Utils.playerCuriosListMap.put(player,new ArrayList<>());
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        boolean hasSamaItem = false;
        for (ItemStack itemStack : curiosList) {
            if (itemStack.is(stack.getItem())) hasSamaItem = true;
        }
        if (!hasSamaItem) curiosList.add(stack);
    }

    public static void RemoveCuriosInList(Player player, ItemStack stack) {
        if (!Utils.playerCuriosListMap.containsKey(player)) Utils.playerCuriosListMap.put(player,new ArrayList<>());
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        curiosList.removeIf(s -> s.is(Items.AIR) || s.is(stack.getItem()));
    }

    public static boolean PlayerHasCurios(Player player, Item item) {
        if (Utils.playerCuriosListMap.containsKey(player)) {
            List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
            for (ItemStack itemStack : curiosList) {
                if (itemStack.is(item)) return true;
            }
        }
        return false;
    }

    public static void EndTp(List<Player> playerList, Vec3 pos) {
        playerList.forEach(player -> {
            ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),pos.x,pos.y,pos.z,0,0);
        });
    }

    public static void DamageActionBarPacketSend(Player player, double baseDamage, double ignoreDefenceDamage, boolean isMana, boolean isCrit) {

        String string = "";
        String Crit = " ";
        if (isCrit) Crit = Utils.Emoji.CritRate;
        if (ignoreDefenceDamage > 0) {
            string = "+ [" + String.format("%.1f", ignoreDefenceDamage) + "]";
        }

        Style critStyle = isMana ? CustomStyle.styleOfEntropy : CustomStyle.styleOfPower;
        ChatFormatting damageStyle = isMana ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.YELLOW;

        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(Component.literal(Crit).withStyle(critStyle).
                        append(Component.literal(String.format("%.1f", baseDamage) + " ").withStyle(damageStyle)).
                        append(Component.literal(string).withStyle(CustomStyle.styleOfSea)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);

    }

    public static boolean PlayerCanChallengeThisInstance(Player player, int instanceNum) {
        CompoundTag data = player.getPersistentData();
        if (instanceNum > 9) return true;
        if (data.getInt(StringUtils.PlayerInstanceProgress) >= instanceNum) return true;
        return false;
    }

    public record GatherMob(Mob mob, int tick, Vec3 pos) {}
    public static List<GatherMob> gatherMobList = new ArrayList<>();

    public static void Gather(int TickCount) {
        gatherMobList.removeIf(gatherMob -> gatherMob.tick < TickCount);
        gatherMobList.forEach(gatherMob -> {
            if (!MonsterCantBeMove(gatherMob.mob))
                gatherMob.mob.setDeltaMovement(gatherMob.pos.subtract(gatherMob.mob.position()).scale(0.2));
        });
    }

    public static void MonsterGatherProvider(Mob mob, int lastTick, Vec3 gatherPos) {
        int TickCount = mob.getServer().getTickCount();
        gatherMobList.add(new Compute.GatherMob(mob,TickCount + lastTick,gatherPos));
    }

    public static void EntitySmoothlyMoveToPos(Entity entity, Vec3 pos) {
        if (entity.position().subtract(pos).length() > 0.1) entity.setDeltaMovement(pos.subtract(entity.position()).scale(0.2));
        else {
            entity.setDeltaMovement(0,0,0);
        }
    }

    public static void EntitySmoothlyMoveToPosWithLimitSpeed(Entity entity, Vec3 pos, double speed) {
        if (entity.position().subtract(pos).length() > 0.1) entity.setDeltaMovement(pos.subtract(entity.position()).normalize().scale(speed));
        else {
            entity.setDeltaMovement(0,0,0);
        }
    }

    public static Vec3 GetPlayerBackPos(Player player, int type) {
        Vec3 vec3 = player.pick(- 1,0,false).getLocation();
        switch (type) {
            case 0 -> {
                return vec3.add(0,2,0);
            }
            case 1 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.YuanShiRenSceptre.get()).scale(Math.sqrt(4)));
            }
            case 2 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.YuanShiRenSceptre.get()).scale(- Math.sqrt(4)));
            }
            case 3 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.YuanShiRenSceptre.get()).scale(4)).add(0,-1,0);
            }
            case 4 -> {
                return vec3.add(player.getHandHoldingItemAngle(ModItems.YuanShiRenSceptre.get()).scale(-4)).add(0,-1,0);
            }
        }
        vec3 = player.pick(- 1,0,false).getLocation().
                add(player.getHandHoldingItemAngle(ModItems.YuanShiRenSceptre.get()));
        return vec3;
    }

    public static class PassiveEquip {
        public static boolean IsSameClass(Item item, List<Item> itemList) {
            for (Item item1 : itemList) {
                if (item1.getClass().equals(item.getClass())) return true;
            }
            return false;
        }
        public static double AttributeGet(Player player, Map<Item,Double> map) {
            List<Item> itemList = new ArrayList<>();
            double total = 0;
            for (int i = 3 ; i < 9 ; i ++) {
                Item item = player.getInventory().getItem(i).getItem();
                if (!IsSameClass(item,itemList) && Utils.PassiveEquipTag.containsKey(item)
                        && map.containsKey(item)
                        && (!Utils.LevelRequire.containsKey(item) || Utils.LevelRequire.get(item) <= player.experienceLevel)) {
                    total += map.get(item);
                    itemList.add(item);
                }
            }
            return total;
        }
    }

    public static void LevelRequire(List<Component> components, int Level) {
        components.add(Component.literal(" 等级需求:").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("" + Level).withStyle(Utils.levelStyleList.get(Level / 25))));
    }

    public static void PlayerHealthDecrease(Player player, double value, Component component) {
        if (player.getHealth() <= value) {
            FormatBroad(player.level(),Component.literal("").withStyle(ChatFormatting.RED),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(component));
            player.setHealth(0);
        }
        else {
            FengXiaoJuCurios1.Store(player,value);
            player.setHealth((float) (player.getHealth() - value));
        }
    }

    public static void PlayerHealthDecrease(Player player, double value, double threshold) {
        FengXiaoJuCurios1.Store(player,value);
        if ((player.getHealth() - value) / player.getMaxHealth() < threshold) {
            player.setHealth((float) (player.getMaxHealth() * threshold));
        }
        else player.setHealth((float) (player.getHealth() - value));
    }

    public static boolean PlayerUseWithHud(Player player, Map<Player,Integer> coolDownMap, Item item, Map<Player,Integer> lastTickMap, int lastTick, int manaCost, int coolDownSeconds) {
        int tickCount = player.getServer().getTickCount();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Compute.PlayerManaCost(player,manaCost);
            coolDownMap.put(player,tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.PlayerCoolDownDecrease(player))));
            Compute.CoolDownTimeSend(player,item.getDefaultInstance(),(int) (coolDownSeconds * 15 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
            lastTickMap.put(player,tickCount + lastTick);
            Compute.EffectLastTimeSend(player,item.getDefaultInstance(),lastTick);
            return true;
        }
        return false;
    }

    public static boolean PlayerUseWithHud(Player player, Map<Player,Integer> coolDownMap, Item item, int manaCost, int coolDownSeconds) {
        int tickCount = player.getServer().getTickCount();
        if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < tickCount) {
            Compute.PlayerManaCost(player,manaCost);
            coolDownMap.put(player,tickCount + (int) (coolDownSeconds * 15 * (1 - PlayerAttributes.PlayerCoolDownDecrease(player))));
            Compute.CoolDownTimeSend(player,item.getDefaultInstance(),(int) (coolDownSeconds * 15 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player))));
            return true;
        }
        return false;
    }

    public static List<Item> mobArmorList = new ArrayList<>();
    public static void setMobArmorList() {
        mobArmorList.add(ModItems.MobArmorCastleKnightHelmet.get());
        mobArmorList.add(ModItems.MobArmorPurpleIronKnightHelmet.get());
        mobArmorList.add(ModItems.MobArmorGiant.get());
    }

    public static double InstanceMobDamageLimit(Player player, Mob mob, double damage) {
        Item mobArmor = mob.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (mobArmorList.isEmpty()) setMobArmorList();
        if (!mobArmorList.contains(mobArmor)) return damage;
        double damageBoundary = 0.5;
        return Math.min(mob.getMaxHealth() * 0.5, damage);
    }

    public static Vec3 MyPlayerPickLocation(Player player, double distance) {
        Vec3 TargetPos = player.pick(distance, 0, false).getLocation();
        if (powerDetectPlayerPickMob(player, distance, 0.5) != null) TargetPos = powerDetectPlayerPickMob(player, distance, 0.5).position();
        return TargetPos;
    }

    public static void TraceArrowShoot(Player player, ParticleOptions particleOptions) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),80,80,80));
        mobList.removeIf(mob -> mob.distanceTo(player) > 30 || mob instanceof Civil);
        Mob NearestMob = null;
        double Distance = 80;
        for (Mob mob : mobList) {
            if (mob.distanceTo(player) < Distance) {
                NearestMob = mob;
                Distance = mob.distanceTo(player);
            }
        }

        MyArrow myArrow = new MyArrow(EntityType.ARROW,player.level(),player,true);
        myArrow.setDeltaMovement(NearestMob.position().add(0,1,0).subtract(player.position().add(0,1.5,0)).normalize().scale(4.5));
        myArrow.moveTo(player.pick(0.5,0,false).getLocation());
        myArrow.setCritArrow(true);
        myArrow.setNoGravity(true);
        ProjectileUtil.rotateTowardsMovement(myArrow,1);
        player.level().addFreshEntity(myArrow);
        ParticleProvider.LineParticle(player.level(), (int) NearestMob.distanceTo(player),
                player.pick(0.5,0,false).getLocation(),NearestMob.position().add(0,1,0), particleOptions);
    }
    
    public static void IgniteMob(Player player, Mob mob, int lastTick) {
        FireEquip.PlayerIgniteMobEffect(player, mob);
        mob.setRemainingFireTicks(lastTick);
    }

    public static List<Mob> OneShotLaser(Player player, boolean isAd, double damage, ParticleOptions particleOptions) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(25,0,false).getLocation();
        Vec3 StartPos = player.pick(0.5,0,false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        ParticleProvider.LineParticle(level,(int) Distance * 5, StartPos, TargetPos, particleOptions);
        List<Mob> mobList = new ArrayList<>();
        for (double i = 0 ; i < Distance ; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)),0.5,0.5,0.5));
            for (Mob mob : mobList1) {
                if (!mobList.contains(mob)) mobList.add(mob);
            }
        }
        mobList.forEach(mob -> {
            if (isAd) {
                Compute.Damage.AttackDamageToMonster_AdDamage(player,mob,damage);
            }
            else {
                Compute.Damage.ManaDamageToMonster_ApDamage(player,mob,damage);
            }
        });
        return mobList;
    }
}
