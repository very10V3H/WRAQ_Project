package fun.wraq.common.util;

import fun.wraq.Items.Forging.WraqForge;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static String[] TickStringArray = {
            "Critical0", "Critical1", "Critical2",
            "Critical3", "Critical4", "volcanobow",
            "plainbow", "lakesword", "BreakDefence",
            "BreakDefenceX", "DGreen1", "NetherRune2",
            SwordSkillNum.Skill2, SwordSkillNum.Skill5,
            BowSkillNum.Skill2, BowSkillNum.Skill13,
            BowSkillNum.Skill14, BowSkillNum.Skill5,
            ManaSkillNum.Skill2, ManaSkillNum.Skill14,
            "SakuraDemonSword", "MineMonsterEffect",
            WitherSword.Effect0, WitherSword.Effect1,
            WitherSword.Effect2, WitherSword.Effect3,
            WitherBow.Effect0, WitherBow.Effect1,
            WitherBow.Effect2, WitherBow.Effect3,
            PlainSwordActive.PlainSceptre,
            "BowAttackSlowDownCount", ForestRune.ForestRune1
    };

    public static String SkillPoint_Total = "SkillPoint_Total";
    public static String AbilityPoint_Total = "AbilityPoint_Total";
    public static String SkillPoint_Used = "SkillPoint_Used";
    public static String AbilityPoint_Used = "AbilityPoint_Used";

    public static class Ability {
        public static String Power = "Ability_Power";
        public static String Intelligent = "Ability_Intelligent";
        public static String Flexibility = "Ability_Flexibility";
        public static String Lucky = "Ability_Lucky";
        public static String Vitality = "Ability_Vitality";
    }

    public static String[] AbilityArray = {
            "Ability_Power",
            "Ability_Intelligent",
            "Ability_Flexibility",
            "Ability_Lucky",
            "Ability_Vitality"
    };

    public static class Skill {
        public static String SwordBase = "Skill_SwordBase";
        public static String BowBase = "Skill_BowBase";
        public static String ManaBase = "Skill_ManaBase";
    }

    public static String[] SkillArray = {
            "Skill_SwordBase",
            "Skill_BowBase",
            "Skill_ManaBase"
    };

    public static class SkillData {
        public static String Sword = "SkillData_Sword";
        public static String Bow = "SkillData_Bow";
        public static String Mana = "SkillData_Mana";
    }

    public static class ForestRune {
        public static String ForestRune = "DGreen";
        public static String ForestRune1 = "DGreen1";
        public static String ForestRune2 = "DGreen2";
    }

    public static class SwordSkillNum {
        public static String Skill2 = "SwordSkill2";
        public static String Skill5 = "SwordSkill5";
    }

    public static class BowSkillNum {
        public static String Skill2 = "BowSkill2";
        public static String Skill5 = "BowSkill5";
        public static String Skill13 = "BowSkill13";
        public static String Skill14 = "BowSkill14";
    }

    public static class ManaSkillNum {
        public static String Skill2 = "ManaSkill2";
        public static String Skill14 = "ManaSkill14";
    }

    public static class DamageTypes {
        public static String ToPlayerDamage = "ToPlayerDamage";
        public static String DamageAmount = "DamageAmount";
        public static String DamageIgnoreDefenceAmount = "DamageIgnoreDefenceAmount";
        public static String Crit = "Crit";
        public static String IsAttack = "IsAttack";
        public static String IsMana = "IsMana";
        public static String IsBow = "IsBow";
    }

    public static class Extracts {
        public static String Prefix = "Extracts_";
        public static String Plain = Prefix + "Plain";
        public static String Forest = Prefix + "Forest";
        public static String Lake = Prefix + "Lake";
        public static String Volcano = Prefix + "Volcano";
        public static String Mine = Prefix + "Mine";
        public static String Snow = Prefix + "Snow";
        public static String Sky = Prefix + "Sky";
        public static String Evoker = Prefix + "Evoker";
    }

    public static class LogsType {
        public static String ItemUse = "ItemUse";
        public static String ItemGet = "ItemGet";
        public static String MobKill = "MobKill";
        public static String ItemPick = "ItemPick";
        public static String ItemDrop = "ItemDrop";
        public static String ItemDelete = "ItemDelete";
        public static String Trade = "Trade";
    }

    public static class Entropy {
        public static String Forest = "ForestEntropy";
        public static String Volcano = "VolcanoEntropy";
        public static String ForestEntropyLevel = "ForestEntropyLevel";
        public static String ForestEntropyTime = "ForestEntropyTime";
        public static String Lake = "LakeEntropy";
        public static String Sky = "SkyEntropy";
        public static String Snow = "SnowEntropy";
    }

    public static class PlainSwordActive {
        public static String PlainSceptre = "PlainSceptre";
    }

    public static class ForestBossSwordActive {
        public static String ForestBossActive = "ForestBossActive";
        public static String Pare = ForestBossActive + "Pare";
        public static String PareTime = ForestBossActive + "PareTime";
    }

    public static class SnowBossSwordActive {
        public static String SnowBossActive = "SnowBossActive";
        public static String Pare = SnowBossActive + "Pare";
        public static String PareTime = SnowBossActive + "PareTime";
    }

    public static class KillCount {
        public static String NEW_KILL_COUNT = "newKillCount";
        public static String KillCountOf = "KillCountOf";
        public static String KillCount = "KillCount";
        public static String Total = "KillCountTotal";
        public static String ForestBoss = KillCountOf + "ForestBoss";
        public static String VolcanoBoss = KillCountOf + "VolcanoBoss";
        public static String SkyBoss = KillCountOf + "SkyBoss";
        public static String LakeBoss = KillCountOf + "LakeBoss";
        public static String SakuraMob = KillCountOf + "SakuraMob";
        public static String Scarecrow = KillCountOf + "Scarecrow";
        public static String RandomSlime = KillCount + "RandomSlime";
        public static String SnowBoss = KillCountOf + "SnowBoss";
        public static String MineWorker = KillCountOf + "MineWorker";
        public static String IceHunter = KillCountOf + "IceHunter";
        public static String ShipWorker = KillCountOf + "ShipWorker";
        public static String EarthMana = KillCountOf + "EarthMana";
        public static String BloodMana = KillCountOf + "BloodMana";
        public static String Slime = KillCountOf + "Slime";
        public static String Beacon = KillCountOf + "Beacon";
        public static String Blaze = KillCountOf + "Blaze";
        public static String Tree = KillCountOf + "Tree";
        public static String Star = KillCountOf + "Star";
        public static String Star1 = KillCountOf + "Star1";
    }

    public static class Login {
        public static String Status = "status";
        public static String Online = "online";
        public static String Offline = "offline";
        public static String Password = "password";
    }

    public static class VillagerType {
        public static class MainMission {
            public static String Trade1 = "MainMission1";
            public static String Trade2 = "MainMission2";
            public static String Trade3 = "MainMission3";
        }

        public static class Spawn {
            public static String Main1Gems = "Main1Gems";
            public static String EpicRune = "EpicRune";
            public static String T1Equip = "T1Equip";
            public static String BossCore = "BossCore";
            public static String DailyFG = "DailyFG";
            public static String DailyMisc = "DailyMisc";
        }

        public static class SakuraIsland {
            public static String Trade1 = "SakuraTrade1";
        }

        public static class Series {
            public static String Plain1 = "Plain1";
            public static String Plain2 = "Plain2";
            public static String PlainForge = "PlainForge";
            public static String Forest1 = "Forest1";
            public static String Forest2 = "Forest2";
            public static String Lake = "Lake";
            public static String Volcano = "Volcano";
            public static String Mine = "Mine";
            public static String Snow = "Snow";
            public static String Sky = "Sky";
            public static String Evoker1 = "Evoker1";
            public static String Evoker2 = "Evoker2";
            public static String Ice = "Ice";
        }

        public static class Nether {
            public static String Wither = "Wither";
            public static String Piglin = "Piglin";
            public static String Skeleton = "Skeleton";
            public static String Magma = "Magma";
        }

        public static class Crest {
            public static String First = "FirstCrest";
            public static String Second = "SecondCrest";
        }

        public static class Soul {
            public static String Equipment = "SoulEquipment";
        }

        public static class GoldCoinStore {
            public static String KillPaper = "GoldCoinKillPaper";
            public static String PsBottle = "PsBottle";
        }

        public static class PurpleIron {
            public static String PurpleIron = "PurpleIron";
        }

        public static class Bank {
            public static String Currency = "Currency";
        }
    }

    public static String SakuraDemonSword = "SakuraDemonSword";
    public static String SnowArmorEffect = "SnowArmorEffect";
    public static String MineMonsterEffect = "MineMonsterEffect";

    public static class WitherSword {
        public static String Effect0 = "WitherSwordEffect0";
        public static String Effect1 = "WitherSwordEffect1";
        public static String Effect2 = "WitherSwordEffect2";
        public static String Effect3 = "WitherSwordEffect3";
    }

    public static class WitherBow {
        public static String Effect = "WitherBowEffect";
        public static String Effect0 = "WitherBowEffect0";
        public static String Effect1 = "WitherBowEffect1";
        public static String Effect2 = "WitherBowEffect2";
        public static String Effect3 = "WitherBowEffect3";
    }

    public static class Crest {
        public static class Plain {
            public static String Crest = "PlainCrest";
            public static String Crest0 = "PlainCrest0";
            public static String Crest1 = "PlainCrest1";
            public static String Crest2 = "PlainCrest2";
            public static String Crest3 = "PlainCrest3";
            public static String Crest4 = "PlainCrest4";
        }

        public static class Forest {
            public static String Crest = "ForestCrest";
            public static String Crest0 = "ForestCrest0";
            public static String Crest1 = "ForestCrest1";
            public static String Crest2 = "ForestCrest2";
            public static String Crest3 = "ForestCrest3";
            public static String Crest4 = "ForestCrest4";
        }

        public static class Lake {
            public static String Crest = "LakeCrest";
            public static String Crest0 = "LakeCrest0";
            public static String Crest1 = "LakeCrest1";
            public static String Crest2 = "LakeCrest2";
            public static String Crest3 = "LakeCrest3";
            public static String Crest4 = "LakeCrest4";
        }

        public static class Volcano {
            public static String Crest = "VolcanoCrest";
            public static String Crest0 = "VolcanoCrest0";
            public static String Crest1 = "VolcanoCrest1";
            public static String Crest2 = "VolcanoCrest2";
            public static String Crest3 = "VolcanoCrest3";
            public static String Crest4 = "VolcanoCrest4";
        }

        public static class Mine {
            public static String Crest = "MineCrest";
            public static String Crest0 = "MineCrest0";
            public static String Crest1 = "MineCrest1";
            public static String Crest2 = "MineCrest2";
            public static String Crest3 = "MineCrest3";
            public static String Crest4 = "MineCrest4";
        }

        public static class Snow {
            public static String Crest = "SnowCrest";
            public static String Crest0 = "SnowCrest0";
            public static String Crest1 = "SnowCrest1";
            public static String Crest2 = "SnowCrest2";
            public static String Crest3 = "SnowCrest3";
            public static String Crest4 = "SnowCrest4";
        }

        public static class Sky {
            public static String Crest = "SkyCrest";
            public static String Crest0 = "SkyCrest0";
            public static String Crest1 = "SkyCrest1";
            public static String Crest2 = "SkyCrest2";
            public static String Crest3 = "SkyCrest3";
            public static String Crest4 = "SkyCrest4";
        }

        public static class Mana {
            public static String Crest = "ManaCrest";
            public static String Crest0 = "ManaCrest0";
            public static String Crest1 = "ManaCrest1";
            public static String Crest2 = "ManaCrest2";
            public static String Crest3 = "ManaCrest3";
            public static String Crest4 = "ManaCrest4";
        }

        public static String[] CrestList = {
                Plain.Crest0, Plain.Crest1, Plain.Crest2, Plain.Crest3,
                Forest.Crest0, Forest.Crest1, Forest.Crest2, Forest.Crest3,
                Lake.Crest0, Lake.Crest1, Lake.Crest2, Lake.Crest3,
                Volcano.Crest0, Volcano.Crest1, Volcano.Crest2, Volcano.Crest3,
                Mine.Crest0, Mine.Crest1, Mine.Crest2, Mine.Crest3,
                Snow.Crest0, Snow.Crest1, Snow.Crest2, Snow.Crest3,
                Sky.Crest0, Sky.Crest1, Sky.Crest2, Sky.Crest3,
                Mana.Crest0, Mana.Crest1, Mana.Crest2, Mana.Crest3,
                Plain.Crest4, Forest.Crest4, Lake.Crest4, Volcano.Crest4,
                Mine.Crest4, Snow.Crest4, Sky.Crest4, Mana.Crest4
        };
    }

    public static class ParticleTypes {
        public static String Witch = "ParticleWitch";
        public static String Composter = "ParticleComposer";
        public static String LongVolcano = "ParticleLoneVolcano";
        public static String Scrape = "ParticleScrape";
        public static String EffectMana = "ParticleEffectMana";
        public static String RangeMana = "ParticleRangeMana";
        public static String DamageMana = "ParticleDamageMana";
        public static String DefencePenetrationMana = "ParticlePenetrationMana";
        public static String SnowMana = "ParticleSnowMana";
        public static String KazeMana = "ParticleKazeMana";
        public static String LightningMana = "ParticleLightningMana";
        public static String GatherMana = "ParticleGatherMana";
        public static String SnowFlake = "ParticleSnowFlake";
        public static String Heart = "ParticleHeart";
        public static String AngryVillager = "ParticleAngryVillager";
        public static String Ash = "ParticleAsh";
        public static String Lava = "ParticleLava";
        public static String EnchantedHit = "ParticleEnchantedHit";
        public static String TotemOfUndying = "ParticleTotemOfUndying";
        public static String WAX_OFF = "WAX_OFF";
        public static String World = "WorldParticle";
        public static String Long_Entropy = "Long_Entropy";
        public static String Cherry_Leaves = "Cherry_Leaves";
        public static String Long_Plain = "Long_Plain";
        public static String Long_Forest = "Long_Forest";
        public static String Long_Lake = "Long_Lake";
        public static String Long_Volcano = "Long_Volcano";
        public static String Long_Snow = "Long_Snow";
        public static String Long_Sea = "Long_Sea";
        public static String Black_Forest = "Black_Forest";
        public static String FallingWater = "FallingWater";
        public static String DrippedWater = "DrippedWater";
        public static String LongSky = "LongSky";
        public static String LongSpring = "LongSpring";
        public static String Spring = "Spring";
        public static String LongLightning = "LongLightning";
        public static String Smoke = "Smoke";
        public static String Volcano = "Volcano";
        public static String Nether = "Nether";
        public static String RedSpell = "RedSpell";
        public static String LongRedSpell = "LongRedSpell";
        public static String Snow = "Snow";
        public static String FireWork = "FireWork";
        public static String WhiteSpell = "WhiteSpell";
        public static String Entropy = "Entropy";
        public static String YSR = "YSR";
        public static String YSR1 = "YSR1";
        public static String LiuliSpell = "LiuliSpell";
        public static String BIG = "BIG";
        public static String PurpleIronOneTick = "PurpleIron";
        public static String PurpleIronBig = "PurpleIronBig";
        public static String SporeBlossomAir = "SporeBlossomAir";
        public static String LifeElementParticle = "LifeElementParticle";
        public static String WaterElementParticle = "WaterElementParticle";
        public static String FireElementParticle = "FireElementParticle";
        public static String StoneElementParticle = "StoneElementParticle";
        public static String IceElementParticle = "IceElementParticle";
        public static String LightningElementParticle = "LightningElementParticle";
        public static String WindElementParticle = "WindElementParticle";
        public static String LifeElement1TickParticle = "LifeElement1TickParticle";
        public static String WaterElement1TickParticle = "WaterElement1TickParticle";
        public static String FireElement1TickParticle = "FireElement1TickParticle";
        public static String StoneElement1TickParticle = "StoneElement1TickParticle";
        public static String IceElement1TickParticle = "IceElement1TickParticle";
        public static String LightningElement1TickParticle = "LightningElement1TickParticle";
        public static String WindElement1TickParticle = "WindElement1TickParticle";
        public static String SoraSwordParticle = "SoraSwordParticle";
        public static String EndParticle = "EndParticle";
        public static String Plain = "Plain";
        public static String Sky = "Sky";
        public static String Sea = "Sea";
        public static String EXPLOSION = "EXPLOSION";
        public static String FLAME = "FLAME";
        public static String END_ROD = "END_ROD";
        public static String BUBBLE = "BUBBLE";
        public static String BUBBLE_POP = "BUBBLE_POP";
        public static String BUBBLE_COLUMN_UP = "BUBBLE_COLUMN_UP";
    }

    public static class MobName {
        public static String NoAttribute = "NoAttribute";
        public static String PlainZombie = "PlainZombie";
        public static String ForestSkeleton = "ForestSkeleton";
        public static String ForestZombie = "ForestZombie";
        public static String LakeDrowned = "LakeDrowned";
        public static String VolcanoBlaze = "VolcanoBlaze";
        public static String MineSkeleton = "MineSkeleton";
        public static String MineZombie = "MineZombie";
        public static String FieldZombie = "FieldZombie";
        public static String SnowStray = "SnowStray";
        public static String SkyVex = "SkyVex";
        public static String Evoker = "Evoker";
        public static String EvokerMaster = "EvokerMaster";
        public static String SeaGuardian = "SeaGuardian";
        public static String LightingZombie = "LightingZombie";
        public static String Husk = "Husk";
        public static String Spider = "Spider";
        public static String KazeZombie = "KazeZombie";
        public static String SilverFish = "SilverFish";
        public static String WitherSkeleton = "WitherSkeleton";
        public static String Piglin = "Piglin";
        public static String NetherSkeleton = "NetherSkeleton";
        public static String NetherMagma = "NetherMagma";
        public static String EnderMan = "EnderMan";
        public static String ForestBoss = "ForestBoss";
        public static String VolcanoBoss = "VolcanoBoss";
        public static String LakeBoss = "LakeBoss";
        public static String SkyBoss = "SkyBoss";
        public static String SnowBoss = "SnowBoss";
        public static String SakuraMob = "SakuraMob";
        public static String Scarecrow = "Scarecrow";
        public static String RandomSlime = "RandomSlime";
        public static String Boss2 = "Boss2";
        public static String PlainBoss = "PlainBoss";
        public static String Main1Boss = "Main1Boss";
        public static String MineWorker = "MineWorker";
        public static String IceKnight = "IceKnight";
        public static String IceHunter = "IceHunter";
        public static String ShipWorker = "ShipWorker";
        public static String SpringMob = "SpringMob";
        public static String Giant = "Giant";
        public static String EarthMana = "EarthMana";
        public static String BloodMana = "BloodMana";
        public static String Devil = "Devil";
        public static String MoonAttack = "MoonAttack";
        public static String MoonMana = "MoonMana";
        public static String Slime = "Slime";

        public static String TabooDevil = "TabooDevil";
        public static String BlackCastleOneFloorMana = "BlackCastleOneFloorMana";
        public static String BlackCastleOneFloorAttack = "BlackCastleOneFloorAttack";
        public static String Beacon = "Beacon";
        public static String Tree = "Tree";
        public static String Blaze = "Blaze";
        public static String CastleKnight = "CastleKnight";
        public static String PurpleIronKnight = "PurpleIronKnight";
        public static String Star = "Star";
        public static String Star1 = "Star1";
        public static String LifeElement = "LifeElement";
        public static String WaterElement = "WaterElement";
        public static String FireElement = "FireElement";
        public static String StoneElement = "StoneElement";
        public static String IceElement = "IceElement";
        public static String LightningElement = "LightningElement";
        public static String WindElement = "WindElement";
        public static String OriginLifeElement = "OriginLifeElement";
        public static String OriginWaterElement = "OriginWaterElement";
        public static String OriginFireElement = "OriginFireElement";
        public static String OriginStoneElement = "OriginStoneElement";
        public static String OriginIceElement = "OriginIceElement";
        public static String OriginLightningElement = "OriginLightningElement";
        public static String OriginWindElement = "OriginWindElement";

        public static String Shulker = "Shulker";
        public static String EnderMite = "EnderMite";
        public static String EndStray = "EndStray";

        public static String LabourDay1 = "LabourDay1";
        public static String LabourDay2 = "LabourDay2";

        public static String Tower1Floor = "Tower1Floor";
        public static String Tower2Floor = "Tower2Floor";
        public static String Tower3Floor = "Tower3Floor";
        public static String Tower4Floor = "Tower4Floor";
        public static String Tower5Floor = "Tower5Floor";
        public static String Tower6Floor = "Tower6Floor";
    }

    public static String BowAttackSlowDown = "BowAttackSlowDownCount";

    public static class PotionTypes {
        public static String AttackUp = "attackup_potion";
        public static String BreakDefenceUp = "breakdefenceup_potion";
        public static String CoolDownDecreaseUp = "cooldownup_potion";
        public static String CritDamageUp = "critdamageup_potion";
        public static String CritRateUp = "critrateup_potion";
        public static String DefenceUp = "defenceup_potion";
        public static String HealStealUp = "healstealup_potion";
        public static String HealthRecover = "healreply_potion";
        public static String ManaBreakDefenceUp = "manabreakdefenceup_potion";
        public static String ManaDamageUp = "manadamageup_potion";
        public static String ManaDefenceUp = "manadefenceup_potion";
        public static String ManaReplyUp = "manareplyup_potion";
        public static String SpeedUp = "speedup_potion";
    }

    public static String Swift = "Swift";
    public static String MaxSwift = "MaxSwift";

    public static class Missions {
        public static String Mission = "Mission";
        public static String Plain = "Mission_Plain";
        public static String Forest = "Mission_Forest";
        public static String Lake = "Mission_Lake";
        public static String Volcano = "Mission_Volcano";
        public static String Mine = "Mission_Mine";
        public static String Snow = "Mission_Snow";
        public static String SkyCity = "Mission_SkyCity";
        public static String SkyVex = "Mission_SkyVex";
        public static String Boss1 = "Mission_Boss1";
        public static String End = "Mission_End";
        public static String SakuraIsland = "Mission_SakuraIsland";
    }

    public static class IgnoreType {
        public static String Fight = "IgnoreFight";
        public static String Rune = "IgnoreRune";
        public static String Exp = "IgnoreExp";
        public static String QuickUse = "IgnoreUse";
        public static String ItemGet = "IgnoreItem";
        public static String VB = "IgnoreVB";
        public static String Mana = "IgnoreMana";
        public static String Instance = "Instance";
    }

    public static String SoulEquipForge = "SoulEquipForge";

    public static String PlainSoulCount = "PlainSoulCount";

    public static String ForestSoulCount = "ForestSoulCount";

    public static String LakeSoulCount = "LakeSoulCount";

    public static String VolcanoSoulCount = "VolcanoSoulCount";

    public static String PsValue = "PsValue";

    public static class ManaCore {
        public static String ManaCore = "ManaCore";
        public static String SeaCore = "Sea" + ManaCore;
        public static String BlackForestCore = "BlackForest" + ManaCore;
        public static String KazeCore = "Kaze" + ManaCore;
        public static String SakuraCore = "Sakura" + ManaCore;
    }

    public static class TextType {
        public static String GoldCoinStore = "GoldCoinStore";
        public static String WelCome = "WelCome";
        public static String PlainStation0 = "PlainStation0";
        public static String PlainStation1 = "PlainStation1";
        public static String WorldPlain = "WorldPlain";
        public static String WorldSky = "WorldSky";
        public static String WorldSakura = "WorldSakura";
        public static String Smithy = "Smithy";
        public static String Bank = "Bank";
        public static String Nether = "Nether";
        public static String Channel0 = "Channel0";
        public static String Channel1 = "Channel1";
        public static String PF = "PF";
        public static String NetherRequisition = "NetherRequisition";
        public static String SkyCityTower = "SkyCityTower";
        public static String SkyCityGems = "SkyCityGems";
        public static String Crest = "Crest";
        public static String Spider = "Spider";
        public static String Evoker = "Evoker";
        public static String Brew = "Brew";
        public static String SL = "SL";
        public static String SV = "SV";
        public static String VolcanoTP = "VolcanoTP";
        public static String PurpleIronHelmet = "PurpleIronHelmet";
        public static String PurpleIronChest = "PurpleIronChest";
        public static String PurpleIronLeggings = "PurpleIronLeggings";
        public static String PurpleIronBoots = "PurpleIronBoots";
        public static String PurpleIronReset = "PurpleIronReset";
        public static String PurpleIronPickaxe0 = "PurpleIronPickaxe0";
        public static String PurpleIronPickaxe1 = "PurpleIronPickaxe1";
        public static String PurpleIronPickaxe2 = "PurpleIronPickaxe2";
        public static String PurpleIronPickaxe3 = "PurpleIronPickaxe3";
        public static String PurpleIron = "PurpleIron";
        public static String IceArmor = "IceArmor";
        public static String GoldSmith = "GoldSmith";
        public static String Blood = "Blood";
        public static String ManaBook = "ManaBook";
        public static String LifeElement = "LifeElement";
        public static String WaterElement = "WaterElement";
        public static String FireElement = "FireElement";
        public static String StoneElement = "StoneElement";
        public static String IceElement = "IceElement";
        public static String LightningElement = "LightningElement";
        public static String WindElement = "WindElement";
        public static String LifeElementResonance = "LifeElementResonance";
        public static String WaterElementResonance = "WaterElementResonance";
        public static String FireElementResonance = "FireElementResonance";
        public static String StoneElementResonance = "StoneElementResonance";
        public static String IceElementResonance = "IceElementResonance";
        public static String LightningElementResonance = "LightningElementResonance";
        public static String WindElementResonance = "WindElementResonance";
        public static String BackEnd = "BackEnd";
    }

    public static String ForgeNum = "ForgeNum";
    public static String ForgeLevel = "Forging";

    public static class RandomAttribute {
        public static String attackDamage = "RandomAttackDamage";
        public static String manaDamage = "RandomManaDamage";
        public static String defence = "RandomDefence";
        public static String maxHealth = "RandomMaxHealth";
        public static String coolDown = "RandomCoolDown";
        public static String movementSpeedWithoutBattle = "RandomMovementSpeed";

        public static String critDamage = "RandomCritDamage";
        public static String critRate = "RandomCritRate";
        public static String manaRecover = "RandomManaRecover";
        public static String defencePenetration0 = "RandomDefencePenetration0";

        public static String healthRecover = "RandomHealthRecover";
        public static String manaPenetration0 = "RandomManaPenetration0";
        public static String healthSteal = "RandomHealthSteal";

        public static String maxMana = "RandomMaxMana";
    }

    public static String MaxCold = "MaxCold";
    public static String CurrentCold = "CurrentCold";

    public static String LastDailyMissionFinishedTime = "LastDailyMissionFinishedTime";

    public static String HasDailyMission = "HasDailyMission";

    public static String ReputationMissionStartTime = "ReputationMissionStartTime";
    public static String Reputation = "Reputation";
    public static String ReputationCalculate = "ReputationCalculate";

    public static String FantasyBracelet = "FantasyBracelet";
    public static String FantasyMedal = "FantasyMedal";

    public static String PsRefreshDate = "PsRefreshDate";

    public static String WeeklyRefreshWeekNum = "WeeklyRefreshWeekNum";
    public static String WeeklyRefreshYearNum = "WeeklyRefreshYearNum";

    public static String monthlyRefreshYearNum = "monthlyRefreshYearNum";
    public static String monthlyRefreshMonthNum = "monthlyRefreshMonthNum";


    public static class Mine {
        public static String Exp = "MineExp";
        public static String Count = "MineCount";
        public static String Coal = Count + "Coal";
        public static String Copper = Count + "Copper";
        public static String Iron = Count + "Iron";
        public static String Lapis = Count + "Lapis";
        public static String RedStone = Count + "RedStone";
        public static String Gold = Count + "Gold";
        public static String Diamond = Count + "Diamond";
        public static String Emerald = Count + "Emerald";
        public static String Ore = Count + "Ore";
    }

    public static class VillagerName {
        public static String Plain = "平原系列打造";
        public static String Forest = "森林系列打造";
        public static String Lake = "湖泊系列打造";
        public static String Volcano = "火山系列打造";
        public static String Mine = "矿洞系列打造";
        public static String Snow = "玉山系列打造";
        public static String Sky = "天空系列打造";
        public static String Evoker = "唤魔系列打造";
        public static String Wither = "凋零系列打造";
        public static String Piglin = "猪灵系列打造";
        public static String Skeleton = "下界骷髅系列打造";
        public static String Magma = "下界熔岩系列打造";
        public static String Crest = "纹章制作师";
        public static String Forge = "锻造大师";
        public static String WorldSoul = "本源学者";
        public static String PurpleIron = "紫晶铁商人";
        public static String Ice = "蔚境之地裁缝";
        public static String Currency = "货币兑换商人";
        public static String SoulToGoldCoin = "根源回收商人";
        public static String BossCore = "Boss核心兑换";
        public static String Main1Gems = "四元饰品兑换";
        public static String Cord = "四元手环兑换";
        public static String T1Equip = "副本物品兑换";
        public static String SeaEquip = "神殿装备展示";
        public static String BlackForestEquip = "灵魂装备展示";
        public static String LightningEquip = "唤雷装备展示";
        public static String PlainRune = "平原符石兑换";
        public static String ForestRune = "森林符石兑换";
        public static String VolcanoRune = "火山符石兑换";
        public static String ManaRune = "唤雷符石兑换";
        public static String SnowRune = "玉山符文兑换";
        public static String NetherRune = "下界符文制作师";
        public static String NetherPower = "下界法术制作师";
        public static String NetherArmor = "下界系列防具展示";
        public static String NetherGem = "下界饰品兑换";
        public static String NetherBow = "夸塔兹长弓展示";
        public static String NetherSwordModel = "下界武器模板制作师";
        public static String NetherWeapon = "下界武器制作师";
        public static String Ruby = "下界能量提取师";
        public static String PlainForestRune = "生机能量提取师";
        public static String LakeVolcanoRune = "黑曜能量提取师";
        public static String ManaArmor = "魔力装备制作师";
        public static String Brewing = "炼金术士";
        public static String Spider = "微光研究所职员";
        public static String Sakura = "樱岛商人";
        public static String Nature = "自然精华兑换";
        public static String Pickaxe = "精英矿工";
        public static String Axe = "精英伐木工";
        public static String Ore = "矿物打造大师";
        public static String GoldCoinStore = "铁铁铁铁铁头";
        public static String NewGive = "物品补给";
        public static String Field = "原野系列兑换";
        public static String Spring = "新春活动兑换";
        public static String EndRune = "末地符石兑换";
        public static String Customized = "定制展示";
        public static String GoldSmith = "黄金匠人";
        public static String Giant = "世界Boss兑换";
        public static String Blood = "旧世腥月研究者";
        public static String Earth = "旧世地蕴研究者";
        public static String ManaBook = "魔导书制作师";
        public static String Slime = "莘岛居民";
        public static String Taboo = "禁忌法术研究者";
        public static String Parkour = "跑酷勋章商人";
        public static String Castle = "暗黑城堡旧主";
        public static String CastleCommon = "暗黑城堡商人";
        public static String EndPower = "终界研究者";
        public static String SkyGemStore = "天空城珠宝商人";
        public static String LakeRune = "湖泊符石兑换";
        public static String QingMing = "清明活动兑换";
        public static String LifeElement = "生机元素博士";
        public static String WindElement = "澄风元素博士";
        public static String StoneElement = "层岩元素博士";
        public static String LightningElement = "怒雷元素博士";
        public static String WaterElement = "碧水元素博士";
        public static String FireElement = "炽焰元素博士";
        public static String IceElement = "凛冰元素博士";
        public static String Food = "咖啡店店员";
        public static String EndRecall = "终界技艺师";
        public static String RoseGoldStore = "名商";
        public static String LabourDayStore = "劳动节活动兑换";
        public static String BackPack = "背包商人";
        public static String Pearl = "本源珍珠收藏家";
    }

    public static class NewVillagerName {
        public static String Plain = "Plain";
        public static String Forest = "Forest";
        public static String Lake = "Lake";
        public static String Volcano = "Volcano";
        public static String Mine = "Mine";
        public static String Field = "Field";
        public static String Snow = "Snow";
        public static String Sky = "Sky";
        public static String Evoker = "Evoker";
        public static String Wither = "Wither";
        public static String Piglin = "Piglin";
        public static String Skeleton = "Skeleton";
        public static String Magma = "Magma";
        public static String Crest = "Crest";
        public static String Forge = "Forge";
        public static String WorldSoul = "WorldSoul";
        public static String PurpleIron = "PurpleIron";
        public static String Ice = "Ice";
        public static String Currency = "Currency";
        public static String SoulToGoldCoin = "SoulToGoldCoin";
        public static String BossCore = "BossCore";
        public static String Main1Gems = "Main1Gems";
        public static String Cord = "Cord";
        public static String T1Equip = "T1Equip";
        public static String SeaEquip = "SeaEquip";
        public static String BlackForestEquip = "BlackForestEquip";
        public static String LightningEquip = "LightningEquip";
        public static String PlainRune = "PlainRune";
        public static String ForestRune = "ForestRune";
        public static String VolcanoRune = "VolcanoRune";
        public static String ManaRune = "ManaRune";
        public static String SnowRune = "SnowRune";
        public static String NetherRune = "NetherRune";
        public static String NetherPower = "NetherPower";
        public static String NetherArmor = "NetherArmor";
        public static String NetherGem = "NetherGem";
        public static String NetherBow = "NetherBow";
        public static String NetherSwordModel = "NetherSwordModel";
        public static String NetherWeapon = "NetherWeapon";
        public static String Ruby = "Ruby";
        public static String PlainForestRune = "PlainForestRune";
        public static String LakeVolcanoRune = "LakeVolcanoRune";
        public static String ManaArmor = "ManaArmor";
        public static String Brewing = "Brewing";
        public static String Spider = "Spider";
        public static String Sakura = "Sakura";
        public static String Nature = "Nature";
        public static String Pickaxe = "Pickaxe";
        public static String Axe = "Axe";
        public static String Ore = "Ore";
        public static String GoldCoinStore = "GoldCoinStore";
        public static String NewGive = "NewGive";
        public static String Spring = "Spring";
        public static String EndRune = "EndRune";
        public static String Customized = "Customized";
        public static String GoldSmith = "GoldSmith";
        public static String Giant = "Giant";
        public static String Blood = "Blood";
        public static String Earth = "Earth";
        public static String ManaBook = "ManaBook";
        public static String Slime = "Slime";
        public static String Taboo = "Taboo";
        public static String Parkour = "Parkour";
        public static String Castle = "Castle";
        public static String CastleCommon = "CastleCommon";
        public static String EndPower = "EndPower";
        public static String SkyGemStore = "SkyGemStore";
        public static String LakeRune = "LakeRune";
        public static String QingMing = "QingMing";
        public static String LifeElement = "LifeElement";
        public static String WindElement = "WindElement";
        public static String StoneElement = "StoneElement";
        public static String LightningElement = "LightningElement";
        public static String WaterElement = "WaterElement";
        public static String FireElement = "FireElement";
        public static String IceElement = "IceElement";
        public static String Food = "Food";
        public static String EndRecall = "EndRecall";
        public static String RoseGoldStore = "RoseGoldStore";
        public static String LabourDayStore = "LabourDayStore";
        public static String BackPack = "BackPack";
        public static String Pearl = "Pearl";
    }

    public static Map<String, MutableComponent> VillagerNameMap = new HashMap<>() {{
        put("Plain".toLowerCase(), Component.literal(VillagerName.Plain).withStyle(CustomStyle.styleOfPlain));
        put("Forest".toLowerCase(), Component.literal(VillagerName.Forest).withStyle(CustomStyle.styleOfForest));
        put("Lake".toLowerCase(), Component.literal(VillagerName.Lake).withStyle(CustomStyle.styleOfLake));
        put("Volcano".toLowerCase(), Component.literal(VillagerName.Volcano).withStyle(CustomStyle.styleOfVolcano));
        put("Mine".toLowerCase(), Component.literal(VillagerName.Mine).withStyle(CustomStyle.styleOfMine));
        put("Snow".toLowerCase(), Component.literal(VillagerName.Snow).withStyle(CustomStyle.styleOfSnow));
        put("Sky".toLowerCase(), Component.literal(VillagerName.Sky).withStyle(CustomStyle.styleOfSky));
        put("Evoker".toLowerCase(), Component.literal(VillagerName.Evoker).withStyle(ChatFormatting.LIGHT_PURPLE));
        put("Wither".toLowerCase(), Component.literal(VillagerName.Wither).withStyle(CustomStyle.styleOfWither));
        put("Piglin".toLowerCase(), Component.literal(VillagerName.Piglin).withStyle(CustomStyle.styleOfNether));
        put("Skeleton".toLowerCase(), Component.literal(VillagerName.Skeleton).withStyle(CustomStyle.styleOfNether));
        put("Magma".toLowerCase(), Component.literal(VillagerName.Magma).withStyle(CustomStyle.styleOfPower));
        put("Crest".toLowerCase(), Component.literal(VillagerName.Crest).withStyle(ChatFormatting.GOLD));
        put("Forge".toLowerCase(), Component.literal(VillagerName.Forge).withStyle(CustomStyle.styleOfPlain));
        put("WorldSoul".toLowerCase(), Component.literal(VillagerName.WorldSoul).withStyle(CustomStyle.styleOfWorld));
        put("PurpleIron".toLowerCase(), Component.literal(VillagerName.PurpleIron).withStyle(CustomStyle.styleOfPurpleIron));
        put("Ice".toLowerCase(), Component.literal(VillagerName.Ice).withStyle(CustomStyle.styleOfIce));
        put("Currency".toLowerCase(), Component.literal(VillagerName.Currency).withStyle(ChatFormatting.GOLD));
        put(NewVillagerName.SoulToGoldCoin.toLowerCase(), Component.literal(VillagerName.SoulToGoldCoin).withStyle(ChatFormatting.GOLD));
        put(NewVillagerName.BossCore.toLowerCase(), Component.literal(VillagerName.BossCore).withStyle(ChatFormatting.AQUA));
        put(NewVillagerName.Main1Gems.toLowerCase(), Component.literal(VillagerName.Main1Gems).withStyle(ChatFormatting.AQUA));
        put(NewVillagerName.Cord.toLowerCase(), Component.literal(VillagerName.Cord).withStyle(ChatFormatting.AQUA));
        put(NewVillagerName.T1Equip.toLowerCase(), Component.literal(VillagerName.T1Equip).withStyle(ChatFormatting.LIGHT_PURPLE));
        put(NewVillagerName.SeaEquip.toLowerCase(), Component.literal(VillagerName.SeaEquip).withStyle(CustomStyle.styleOfSea));
        put(NewVillagerName.BlackForestEquip.toLowerCase(), Component.literal(VillagerName.BlackForestEquip).withStyle(CustomStyle.styleOfHusk));
        put(NewVillagerName.LightningEquip.toLowerCase(), Component.literal(VillagerName.LightningEquip).withStyle(CustomStyle.styleOfLightingIsland));
        put(NewVillagerName.PlainRune.toLowerCase(), Component.literal(VillagerName.PlainRune).withStyle(CustomStyle.styleOfPlain));
        put(NewVillagerName.ForestRune.toLowerCase(), Component.literal(VillagerName.ForestRune).withStyle(CustomStyle.styleOfForest));
        put(NewVillagerName.VolcanoRune.toLowerCase(), Component.literal(VillagerName.VolcanoRune).withStyle(CustomStyle.styleOfVolcano));
        put(NewVillagerName.ManaRune.toLowerCase(), Component.literal(VillagerName.ManaRune).withStyle(CustomStyle.styleOfMana));
        put(NewVillagerName.SnowRune.toLowerCase(), Component.literal(VillagerName.SnowRune).withStyle(CustomStyle.styleOfSnow));
        put(NewVillagerName.NetherRune.toLowerCase(), Component.literal(VillagerName.NetherRune).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.NetherPower.toLowerCase(), Component.literal(VillagerName.NetherPower).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.NetherArmor.toLowerCase(), Component.literal(VillagerName.NetherArmor).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.NetherGem.toLowerCase(), Component.literal(VillagerName.NetherGem).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.NetherBow.toLowerCase(), Component.literal(VillagerName.NetherBow).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.NetherSwordModel.toLowerCase(), Component.literal(VillagerName.NetherSwordModel).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.NetherWeapon.toLowerCase(), Component.literal(VillagerName.NetherWeapon).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.Ruby.toLowerCase(), Component.literal(VillagerName.Ruby).withStyle(CustomStyle.styleOfNether));
        put(NewVillagerName.PlainForestRune.toLowerCase(), Component.literal(VillagerName.PlainForestRune).withStyle(CustomStyle.styleOfHealth));
        put(NewVillagerName.LakeVolcanoRune.toLowerCase(), Component.literal(VillagerName.LakeVolcanoRune).withStyle(CustomStyle.styleOfMana));
        put(NewVillagerName.ManaArmor.toLowerCase(), Component.literal(VillagerName.ManaArmor).withStyle(CustomStyle.styleOfMana));
        put(NewVillagerName.Brewing.toLowerCase(), Component.literal(VillagerName.Brewing).withStyle(CustomStyle.styleOfBrew));
        put(NewVillagerName.Spider.toLowerCase(), Component.literal(VillagerName.Spider).withStyle(CustomStyle.styleOfSpider));
        put(NewVillagerName.Sakura.toLowerCase(), Component.literal(VillagerName.Sakura).withStyle(CustomStyle.styleOfSakura));
        put(NewVillagerName.Nature.toLowerCase(), Component.literal(VillagerName.Nature).withStyle(CustomStyle.styleOfHealth));
        put(NewVillagerName.Pickaxe.toLowerCase(), Component.literal(VillagerName.Pickaxe).withStyle(CustomStyle.styleOfMine));
        put(NewVillagerName.Axe.toLowerCase(), Component.literal(VillagerName.Axe).withStyle(CustomStyle.styleOfHusk));
        put(NewVillagerName.Ore.toLowerCase(), Component.literal(VillagerName.Ore).withStyle(CustomStyle.styleOfMine));
        put(NewVillagerName.GoldCoinStore.toLowerCase(), Component.literal(VillagerName.GoldCoinStore).withStyle(ChatFormatting.GOLD));
        put(NewVillagerName.NewGive.toLowerCase(), Component.literal(VillagerName.NewGive).withStyle(ChatFormatting.AQUA));
        put(NewVillagerName.Field.toLowerCase(), Component.literal(VillagerName.Field).withStyle(ChatFormatting.GOLD));
        put(NewVillagerName.Spring.toLowerCase(), Component.literal(VillagerName.Spring).withStyle(CustomStyle.styleOfSpring));
        put(NewVillagerName.EndRune.toLowerCase(), Component.literal(VillagerName.EndRune).withStyle(CustomStyle.styleOfEnd));
        put(NewVillagerName.Customized.toLowerCase(), Component.literal(VillagerName.Customized).withStyle(CustomStyle.styleOfSpring));
        put(NewVillagerName.GoldSmith.toLowerCase(), Component.literal(VillagerName.GoldSmith).withStyle(CustomStyle.styleOfGold));
        put(NewVillagerName.Giant.toLowerCase(), Component.literal(VillagerName.Giant).withStyle(ChatFormatting.LIGHT_PURPLE));
        put(NewVillagerName.Earth.toLowerCase(), Component.literal(VillagerName.Earth).withStyle(CustomStyle.styleOfMana));
        put(NewVillagerName.Blood.toLowerCase(), Component.literal(VillagerName.Blood).withStyle(CustomStyle.styleOfBloodMana));
        put(NewVillagerName.ManaBook.toLowerCase(), Component.literal(VillagerName.ManaBook).withStyle(CustomStyle.styleOfMana));
        put(NewVillagerName.Slime.toLowerCase(), Component.literal(VillagerName.Slime).withStyle(CustomStyle.styleOfHealth));
        put(NewVillagerName.Taboo.toLowerCase(), Component.literal(VillagerName.Taboo).withStyle(CustomStyle.styleOfBloodMana));
        put(NewVillagerName.Parkour.toLowerCase(), Component.literal(VillagerName.Parkour).withStyle(CustomStyle.styleOfFlexible));
        put(NewVillagerName.Castle.toLowerCase(), Component.literal(VillagerName.Castle).withStyle(CustomStyle.styleOfCastle));
        put(NewVillagerName.CastleCommon.toLowerCase(), Component.literal(VillagerName.CastleCommon).withStyle(CustomStyle.styleOfCastle));
        put(NewVillagerName.EndPower.toLowerCase(), Component.literal(VillagerName.EndPower).withStyle(CustomStyle.styleOfEnd));
        put(NewVillagerName.SkyGemStore.toLowerCase(), Component.literal(VillagerName.SkyGemStore).withStyle(ChatFormatting.LIGHT_PURPLE));
        put(NewVillagerName.LakeRune.toLowerCase(), Component.literal(VillagerName.LakeRune).withStyle(ChatFormatting.BLUE));
        put(NewVillagerName.QingMing.toLowerCase(), Component.literal(VillagerName.QingMing).withStyle(CustomStyle.styleOfHealth));
        put(NewVillagerName.LifeElement.toLowerCase(), Component.literal(VillagerName.LifeElement).withStyle(CustomStyle.styleOfLife));
        put(NewVillagerName.WindElement.toLowerCase(), Component.literal(VillagerName.WindElement).withStyle(CustomStyle.styleOfWind));
        put(NewVillagerName.StoneElement.toLowerCase(), Component.literal(VillagerName.StoneElement).withStyle(CustomStyle.styleOfStone));
        put(NewVillagerName.LightningElement.toLowerCase(), Component.literal(VillagerName.LightningElement).withStyle(CustomStyle.styleOfLightning));
        put(NewVillagerName.WaterElement.toLowerCase(), Component.literal(VillagerName.WaterElement).withStyle(CustomStyle.styleOfWater));
        put(NewVillagerName.FireElement.toLowerCase(), Component.literal(VillagerName.FireElement).withStyle(CustomStyle.styleOfFire));
        put(NewVillagerName.IceElement.toLowerCase(), Component.literal(VillagerName.IceElement).withStyle(CustomStyle.styleOfIce));
        put(NewVillagerName.Food.toLowerCase(), Component.literal(VillagerName.Food).withStyle(CustomStyle.styleOfLife));
        put(NewVillagerName.EndRecall.toLowerCase(), Component.literal(VillagerName.EndRecall).withStyle(CustomStyle.styleOfEnd));
        put(NewVillagerName.RoseGoldStore.toLowerCase(), Component.literal(VillagerName.RoseGoldStore).withStyle(CustomStyle.styleOfPurpleIron));
        put(NewVillagerName.LabourDayStore.toLowerCase(), Component.literal(VillagerName.LabourDayStore).withStyle(ChatFormatting.GOLD));
        put(NewVillagerName.BackPack.toLowerCase(), Component.literal(VillagerName.BackPack).withStyle(ChatFormatting.GOLD));
        put(NewVillagerName.Pearl.toLowerCase(), Component.literal(VillagerName.Pearl).withStyle(CustomStyle.styleOfWorld));
    }};

    public static class Gardening {
        public static String Xp = "GardeningXp";
        public static String Count = "GardeningCount";
        public static String Wheat = Count + "Wheat";
        public static String Carrot = Count + "Carrot";
        public static String Potato = Count + "Potato";
        public static String Beet = Count + "Beet";
        public static String Torch = Count + "Torch";
        public static String SweetBerries = Count + "SweetBerries";
    }

    public static class Lop {
        public static String Xp = "LopXp";
        public static String Count = "LopCount";
        public static String OAK = Count + "OAK";
        public static String SPRUCE = Count + "SPRUCE";
        public static String BIRCH = Count + "BIRCH";
        public static String JUNGLE = Count + "JUNGLE";
        public static String ACACIA = Count + "ACACIA";
        public static String DARK_OAK = Count + "DARK_OAK";
        public static String MANGROVE = Count + "MANGROVE";
        public static String CHERRY = Count + "CHERRY";
    }

    public static class Rank {
        public static String Kill = "Kill";
        public static String Fish = "Fish";
        public static String Mine = "Mine";
        public static String Crop = "Crop";
        public static String Lop = "Lop";
        public static String Online = "Online";
    }

    public static String RewardChestCount = "RewardChestCount";
    public static String ExpLevel = "ExpLevel";

    public static String PatchouliBook = "PatchouliBook";

    public static String SakuraHat = "SakuraHat";
    public static String UnCommonLot = "UnCommonLot";
    public static String WorldSoul3 = "WorldSoul3";
    public static String IceLoot = "IceLoot";
    public static String RevelationBook = "RevelationBook";
    public static String UnCommonLot1 = "UnCommonLot1";
    public static String SkillReset = "SkillReset";
    public static String CompleteGem = "CompleteGem";
    public static String RedEnvelope = "RedEnvelope";

    public static String BowSlowDown = "BowSlowDown";
    public static String ShipFishTimes = "ShipFishTimes";

    public static String FishCount = "FishCount";

    public static class InstanceTimes {
        public static String InstanceTimes = "InstanceTimes";
        public static String Plain = InstanceTimes + "Plain";
        public static String Main1Boss = InstanceTimes + "Main1Boss";
        public static String Nether = InstanceTimes + "Nether";
        public static String Lightning = InstanceTimes + "Lightning";
        public static String SakuraBoss = InstanceTimes + "SakuraBoss";
        public static String IceKnight = InstanceTimes + "IceKnight";
        public static String Spring = InstanceTimes + "Spring";
        public static String Devil = InstanceTimes + "Devil";
        public static String Moon = InstanceTimes + "Moon";
        public static String Taboo = InstanceTimes + "Taboo";
        public static String BlackCastle1 = InstanceTimes + "BlackCastle1";
        public static String BlackCastle2 = InstanceTimes + "BlackCastle2";
        public static String PurpleKnight = InstanceTimes + "PurpleKnight";
    }

    public static String EndRune = "EndRune";
    public static String RecallEndRune3 = "RecallEndRune3";

    public static String LogReward = "LogReward";

    public static String SoulSwordKillCount = "SoulSwordKillCount";

    public static String getBloodCurios = "getBloodCurios";
    public static String getEarthCurios = "getEarthCurios";

    public static String getBlazeCurios = "getBlazeCurios";
    public static String getTreeCurios = "getTreeCurios";
    public static String getBeaconCurios = "getBeaconCurios";

    public static String DragonPrefix = "DragonPrefix";
    public static String XiangLiPrefix = "XiangLiPrefix";

    public static class Parkour {
        public static String RecordPointNum = "RecordPointNum";
        public static String LastRewardRecordNum = "LastRewardRecordNum";
        public static String GetRewardTimes = "GetRewardTimes";
        public static String ParkourTicket = "ParkourTicket";
    }

    public static String MoonCuriosXpLevel = "MoonCuriosXpLevel";
    public static String MoonCuriosPlayerName = "MoonCuriosPlayerName";

    public static class CuriosAttribute {
        public static String attackDamage = "CuriosAttackDamage";
        public static String manaDamage = "CuriosManaDamage";
        public static String maxHealth = "MaxHealth";
        public static String defence = "Defence";
        public static String defencePenetration0 = "CuriosDefencePenetration0";
        public static String manaPenetration0 = "CuriosManaPenetration0";
        public static String coolDown = "CuriosCoolDown";
        public static String manaRecover = "CuriosManaRecover";
        public static String maxMana = "CuriosMaxMana";
        public static String swiftnessUp = "CuriosSwiftnessUp";
        public static String critDamage = "CuriosCritDamage";
        public static String expUp = "CuriosExpUp";
        public static String critRate = "CuriosCritRate";

        public static String healthSteal = "CuriosHealthSteal";
        public static String defencePenetration = "CuriosDefencePenetration";
        public static String movementSpeed = "CuriosMovementSpeed";
        public static String healthRecover = "CuriosHealthRecover";
        public static String healEffectUp = "CuriosHealEffectUp";
        public static String manaPenetration = "CuriosManaPenetration";
        public static String manaHealthSteal = "CuriosManaHealthSteal";
        public static String LuckyUp = "CuriosLuckyUp";
        public static String manaDefence = "CuriosManaDefence";

        public static String commonMovementSpeed = "commonMovementSpeed";
        public static String toughness = "CuriosToughness";

        public static String percentAttackDamage = "percentAttackDamage";
        public static String percentDefenceEnhance = "percentDefenceEnhance";
        public static String percentMaxHealthEnhance = "percentMaxHealthEnhance";
        public static String percentManaDamageEnhance = "percentManaDamageEnhance";
        public static String percentManaDefenceEnhance = "percentManaDefenceEnhance";
        public static String percentHealthRecover = "percentHealthRecover";

        public static String xpLevelAttackDamage = "xpLevelAttackDamage";
        public static String xpLevelCritDamage = "xpLevelCritDamage";
        public static String xpLevelDefencePenetration0 = "xpLevelDefencePenetration0";
        public static String xpLevelManaDamage = "xpLevelManaDamage";
        public static String xpLevelManaPenetration0 = "xpLevelManaPenetration0";
        public static String xpLevelDefence = "xpLevelDefence";
        public static String xpLevelManaDefence = "xpLevelManaDefence";
    }

    public static String PlayerInstanceProgress = "PlayerInstanceProgress";

    public static class DailyInstance {
        public static String DailyLogInstance = "DailyLogInstance";
        public static String DailyMineInstance = "DailyMineInstance";
        public static String DailyCropInstance = "DailyCropInstance";
    }

    public static class PlainRunes {
        public static String Rune = "Green";

    }

    public static String DailyInstanceCode = "DailyInstanceCode";
    public static String DailyInstanceCode0 = "000000000000000000";

    public static String LotteryPrefix = "LotteryPrefix";
    public static String QingMingPrefix = "QingMingPrefix";
    public static String LabourDayPrefix = "LabourDayPrefix";

    public static String LakeRune = "LakeRune";

    public static String IgnoreParticleLevel = "IgnoreParticleLevel";

    public static String ClientLimit = "WraqClientLimit";

    public static String QingMingForgePaper = "QingMingForgePaper";
    public static String LabourDayForgePaper = "LabourDayForgePaper";

    public static String MovementSpeedRate = "MovementSpeedRate";

    public static String OriginElementGetTimes = "OriginElementGetTimes";

    public static String Debug = "Debug";

    public static String ResonanceType = "ResonanceType";

    public static String Exist = "Exist";

    public static class FlagInTag {
        public static String key = "flagInTag";
        public static String originString = "0".repeat(64);

        public static Map<String, Integer> indexMap = new HashMap<>() {{
            put(WraqForge.firstTimeForge, 0);
        }};

        public static String getPlayerString(Player player) {
            CompoundTag tag = player.getPersistentData();
            if (!tag.contains(key)) tag.putString(key, originString);
            return tag.getString(key);
        }

        public static void setPlayerString(Player player, String indexString, boolean flag) {
            StringBuilder stringBuilder = new StringBuilder(getPlayerString(player));
            if (indexMap.containsKey(indexString)) {
                stringBuilder.setCharAt(indexMap.get(indexString), flag ? '1' : '0');
                CompoundTag tag = player.getPersistentData();
                tag.putString(key, stringBuilder.toString());
            } else {
                player.sendSystemMessage(Component.literal("出错了！请联系铁头！").withStyle(ChatFormatting.AQUA));
            }
        }

        public static boolean getPlayerFlag(Player player, String indexString) {
            if (indexMap.containsKey(indexString)) {
                return getPlayerString(player).charAt(indexMap.get(indexString)) == '1';
            }
            return false;
        }
    }
}