package com.Very.very.ValueAndTools.Utils;

import com.Very.very.Entity.Entities.Boss2.Boss2;
import com.Very.very.Entity.Entities.SakuraMob.SakuraMob;
import com.Very.very.Entity.Entities.Scarecrow.Scarecrow;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Series.EndSeries.Recall;
import com.Very.very.Files.ConfigTest;
import com.Very.very.Files.MarketItemInfo;
import com.Very.very.Files.MarketPlayerInfo;
import com.Very.very.Items.Prefix.PrefixInfo;
import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.ForestPowerEffectMob;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.LakePowerEffect;
import com.Very.very.ValueAndTools.Utils.Struct.*;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill6;
import com.Very.very.ValueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill6;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill13;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill6;
import com.Very.very.Render.Effects.ModEffects;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class Utils {
    public static final String MOD_ID = "vmd";
    public static Map<Item,Double> HealthSteal = new HashMap<>();
    public static Map<Item,Double> AttackDamage = new HashMap<>();
    public static Map<Item,Double> CritRate = new HashMap<>();
    public static Map<Item,Double> CritDamage = new HashMap<>();
    public static Map<Item,Double> Defence = new HashMap<>();
    public static Map<Item,Double> DefencePenetration = new HashMap<>();
    public static Map<Item,Double> DefencePenetration0 = new HashMap<>();
    public static Map<Item,Double> AttackRangeUp = new HashMap<>();
    public static Map<Item,Double> SwiftnessUp = new HashMap<>();
    public static Map<Item,Double> AttackSpeedUp = new HashMap<>();
    public static Map<Item,Double> MovementSpeed = new HashMap<>();
    public static Map<Item,Double> ExpUp = new HashMap<>();
    public static Map<Item,Double> MaxHealth = new HashMap<>();
    public static Map<Item,Double> HealthRecover = new HashMap<>();
    public static Map<Item,Double> HealEffectUp = new HashMap<>();
    public static Map<Item,Double> CoolDownDecrease = new HashMap<>();
    public static Map<Item,Double> ManaDamage = new HashMap<>();
    public static Map<Item,Double> ManaDefence = new HashMap<>();
    public static Map<Item,Double> ManaPenetration = new HashMap<>();
    public static Map<Item,Double> ManaPenetration0 = new HashMap<>();
    public static Map<Item,Double> ManaCost = new HashMap<>();
    public static Map<Item,Double> ManaRecover = new HashMap<>();
    public static Map<Item,Double> MaxMana = new HashMap<>();
    public static Map<Item,Double> ManaHealthSteal = new HashMap<>();
    public static Map<Item,Double> CritDamageDecrease = new HashMap<>();
    public static Map<Item,Double> LuckyUp = new HashMap<>();
    public static Map<Item,Double> MainHandTag = new HashMap<>();
    public static Map<Item,Double> SwordTag = new HashMap<>();
    public static Map<Item,Double> BowTag = new HashMap<>();
    public static Map<Item,Double> SceptreTag = new HashMap<>();
    public static Map<Item,Double> OffHandTag = new HashMap<>();
    public static Map<Item,Double> ShieldTag = new HashMap<>();
    public static Map<Item,Double> ArmorTag = new HashMap<>();
    public static Map<Item,Double> PowerTag = new HashMap<>();
    public static Map<Item,Double> CuriosTag = new HashMap<>();
    public static Map<Item,Double> MobLevel = new HashMap<>();
    public static Map<Item,Double> PassiveEquipTag = new HashMap<>();
    public static Map<Item,Integer> LevelRequire = new HashMap<>();
    public static int Count = -1;
    public static Map <String, Double> GemsAttackDamage = new HashMap <>();
    public static Map <String, Double> GemsSpeedUp = new HashMap <>();
    public static Map <String, Double> GemsManaDamage = new HashMap<>();
    public static Map <String, Double> GemsManaRecover = new HashMap<>();
    public static Map <String, Double> GemsHealthRecover = new HashMap<>();
    public static Map <String, Double> GemsMaxHealth = new HashMap<>();
    public static Map <String, Double> GemsDefence = new HashMap<>();
    public static Map <String, Double> GemsCoolDown = new HashMap<>();
    public static Map <String, Double> GemsCritDamage = new HashMap<>();
    public static Map <String, Double> GemsCritRate = new HashMap<>();
    public static Map <String, Double> GemsHealStrength = new HashMap<>();
    public static Map <String, Double> GemsManaHealthSteal = new HashMap<>();
    public static Map <String, Double> GemsDefencePenetration0 = new HashMap<>();
    public static Map <String, Double> GemsManaPenetration0 = new HashMap<>();
    public static Map <String, Double> GemsExpUp = new HashMap<>();
    public static Map <String, Double> GemsLuckyUp = new HashMap<>();
    public static Map <String, Double> GemsDefencePenetration = new HashMap<>();
    public static Map <String, Double> GemsManaPenetration = new HashMap<>();
    public static Map <String, Double> GemsHealthSteal = new HashMap<>();
    public static Map <String, Double> GemsManaDefence = new HashMap<>();

    public static List <BlockPos> blockPosQueue = new ArrayList<>();
    public static List <BlockPos> netherBlockPosQueue = new ArrayList<>();
    public static List <BlockPos> blockPosBreakQueue = new ArrayList<>();
    public static List <BlockState> blockStateQueue = new ArrayList<>();
    public static List <BlockPos> netherBlockPosBreakQueue = new ArrayList<>();
    public static List <BlockState> netherBlockStateQueue = new ArrayList<>();

    public static List <LivingEntity> MonsterAttributeDataProvider = new ArrayList<>();

    public static int AttributeDataTick = 0;
    public static Map <Player, List<Vec3>> FeiLeiShenMap = new HashMap<>();
    public static Entity EntityCopy;
    public static Map <Item, Boolean> ItemCheck = new HashMap<>() {
        {
            put(Items.PRISMARINE_BRICK_SLAB,true);
            put(Items.SMITHING_TABLE,true);
            put(Items.POLISHED_BLACKSTONE_SLAB,true);
            put(Items.SEA_LANTERN,true);
            put(Items.QUARTZ_SLAB,true);
            put(Items.GRANITE_SLAB,true);
            put(Items.POLISHED_BLACKSTONE_BRICK_SLAB,true);
            put(Items.SPRUCE_SLAB,true);
            put(Items.BIRCH_SLAB,true);
            put(Items.OAK_SLAB,true);
            put(Items.LOOM,true);
            put(Items.SMOKER,true);
            put(Items.GREEN_STAINED_GLASS,true);
            put(Items.SMOOTH_QUARTZ_SLAB,true);
            put(Items.POLISHED_DEEPSLATE_SLAB,true);
            put(Items.ANVIL,true);
            put(Items.WHITE_STAINED_GLASS,true);
            put(Items.LECTERN,true);
            put(Items.CARTOGRAPHY_TABLE,true);
            put(Items.LIME_STAINED_GLASS,true);
            put(Items.DARK_OAK_SLAB,true);
            put(Items.PEARLESCENT_FROGLIGHT,true);
            put(Items.END_STONE,true);
            put(Items.BLACK_WOOL,true);
            put(Items.ACACIA_LOG,true);
            put(Items.BIRCH_LOG,true);
            put(Items.DARK_OAK_LOG,true);
            put(Items.OAK_LOG,true);
            put(Items.JUNGLE_LOG,true);
            put(Items.MANGROVE_LOG,true);
            put(Items.SPRUCE_LOG,true);
            put(Items.OAK_FENCE,true);
            put(Items.BEACON,true);
            put(Items.PURPLE_STAINED_GLASS,true);
            put(Items.ACACIA_LEAVES,true);
            put(Items.AZALEA_LEAVES,true);
            put(Items.BIRCH_LEAVES,true);
            put(Items.JUNGLE_LEAVES,true);
            put(Items.DARK_OAK_LEAVES,true);
            put(Items.OAK_LEAVES,true);
            put(Items.MANGROVE_LEAVES,true);
            put(Items.FLOWERING_AZALEA_LEAVES,true);
            put(Items.SPRUCE_LEAVES,true);
            put(Items.TWISTING_VINES,true);
            put(Items.WEEPING_VINES,true);
            put(Items.GILDED_BLACKSTONE,true);
            put(Items.BAMBOO,true);
            put(Items.PRISMARINE_BRICKS,true);
            put(Items.GRASS,true);
            put(Items.DANDELION,true);
            put(Items.POPPY,true);
            put(Items.BLUE_ORCHID,true);
            put(Items.ALLIUM,true);
            put(Items.AZURE_BLUET,true);
            put(Items.RED_TULIP,true);
            put(Items.ORANGE_TULIP,true);
            put(Items.WHITE_TULIP,true);
            put(Items.PINK_TULIP,true);
            put(Items.OXEYE_DAISY,true);
            put(Items.CORNFLOWER,true);
            put(Items.LILY_OF_THE_VALLEY,true);
            put(Items.WITHER_ROSE,true);
            put(Items.SPORE_BLOSSOM,true);
            put(Items.CRIMSON_ROOTS,true);
            put(Items.WARPED_ROOTS,true);
            put(Items.NETHER_SPROUTS,true);
            put(Items.SUGAR_CANE,true);
            put(Items.KELP,true);
            put(Items.CHORUS_PLANT,true);
            put(Items.CHORUS_FLOWER,true);
            put(Items.CACTUS,true);
            put(Items.CHAIN,true);
            put(Items.VINE,true);
            put(Items.GLOW_LICHEN,true);
            put(Items.SCULK_VEIN,true);
            put(Items.SUNFLOWER,true);
            put(Items.LILAC,true);
            put(Items.ROSE_BUSH,true);
            put(Items.PEONY,true);
            put(Items.TALL_GRASS,true);
            put(Items.LARGE_FERN,true);
            put(Items.POINTED_DRIPSTONE,true);
            put(Items.OBSIDIAN,true);
            put(Items.SEAGRASS,true);
            put(Items.CHERRY_LEAVES,true);
            put(Items.CHERRY_LOG,true);
        }
    };
    public static Map <BlockPos, Player> BlockLimit = new HashMap<>();

    public static List <PlayerCallBack> playerCallBackQueue = new ArrayList<>();

    public static int tick = 0;
    public static Player Vplayer;
    public static boolean Security = true;
    public static double security0 = 64;
    public static double security1 = 64;
    public static double security2 = 64;
    public static double security3 = 64;
    public static int securityCount = 0;
    public static int securityCount1 = 0;

    // Entity

    public static Vex[] vex = new Vex[15];
    public static Zombie[] PlainZombie = new Zombie[25];
    public static Skeleton[] ForestSkeleton = new Skeleton[15];
    public static Zombie[] ForestZombie = new Zombie[15];
    public static Zombie[] MineZombie = new Zombie[15];
    public static Skeleton[] MineSkeleton = new Skeleton[15];
    public static Zombie[] FieldZombie = new Zombie[15];
    public static Stray[] SnowStray = new Stray[15];
    public static Stray[] SnowStray1 = new Stray[15];
    public static Drowned[] LakeDrown = new Drowned[15];
    public static Blaze[] Blazes = new Blaze[15];
    public static Evoker[] Evokers = new Evoker[15];
    public static WitherSkeleton[] WitherSkeleton = new WitherSkeleton[15];
    public static Evoker[] EvokerMaster = new Evoker[15];
    public static Piglin[] ZombiePiglin = new Piglin[15];
    public static Skeleton[] NetherSkeleton = new Skeleton[15];
    public static Guardian[] Guardians = new Guardian[15];
    public static Zombie[] LightingZombie = new Zombie[15];
    public static Zombie[] DesertZombie = new Zombie[15];
    public static Skeleton[] KazeSkeleton = new Skeleton[15];
    public static Spider[] BlackForestSpider = new Spider[15];
    public static Spider[] Spider = new Spider[15];
    public static Silverfish[] SilverFish = new Silverfish[15];
    public static Zombie PFZombie;
    public static Blaze[] SVBlaze = new Blaze[15];
    public static Drowned[] PLDrown = new Drowned[15];
    public static MagmaCube[] MagmaCube = new MagmaCube[15];
    public static EnderMan[] EnderMan = new EnderMan[15];
    public static SakuraMob[] SakuraMob = new SakuraMob[15];
    public static Scarecrow[] Scarecrow = new Scarecrow[15];
    public static Zombie[] MineWorker = new Zombie[15];
    public static Stray[] IceHunter = new Stray[15];
    public static Pillager[] Pillager = new Pillager[15];
    public static Stray[] IceHunterForIceKnight = new Stray[15];
    public static Zombie[] WoodenStake = new Zombie[15];
    public static Zombie[] EarthMana = new Zombie[15];
    public static Zombie[] BloodMana = new Zombie[15];
    public static Slime[] Slime = new Slime[15];

    public static Zombie ForestBoss = null;
    public static Blaze VolcanoBoss = null;
    public static Drowned LakeBoss = null;
    public static Vex SkyBoss = null;
    public static Stray SnowBoss = null;

    public static int SummonTick = 0;
    public static int SummonTick0 = 0;
    public static int PFSummonTick = 0;
    public static int PVSummonTick = 0;
    public static int PLSummonTick = 0;

    public static Style styleOfMana = Style.EMPTY.withColor(TextColor.parseColor("#ba00df"));
    public static Style styleOfNether = Style.EMPTY.withColor(TextColor.parseColor("#a2001b"));
    public static Style styleOfQuartz = Style.EMPTY.withColor(TextColor.parseColor("#ea7552"));
    public static Style styleOfSea = Style.EMPTY.withColor(TextColor.parseColor("#a4d1c2"));
    public static Style styleOfLake = Style.EMPTY.withColor(TextColor.parseColor("#001fff"));
    public static Style styleOfBlackForest = Style.EMPTY.withColor(TextColor.parseColor("#8f5e00"));
    public static Style styleOfSeaDark = Style.EMPTY.withColor(TextColor.parseColor("#2e6053"));
    public static Style styleOfLightingIsland = Style.EMPTY.withColor(TextColor.parseColor("#515bbd"));
    public static Style styleOfRune =  Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"));
    public static Style styleOfSky = Style.EMPTY.withColor(TextColor.parseColor("#92ecfa"));
    public static Style styleOfBrew = Style.EMPTY.withColor(TextColor.parseColor("#ffffb5"));
    public static Style styleOfHealth = Style.EMPTY.withColor(TextColor.parseColor("#6fc301"));
    public static Style styleOfKaze = Style.EMPTY.withColor(TextColor.parseColor("#a1e475"));
    public static Style styleOfSpider = Style.EMPTY.withColor(TextColor.parseColor("#d0cfff"));
    public static Style styleOfRange = Style.EMPTY.withColor(TextColor.parseColor("#00dcff"));
    public static Style styleOfSnow = Style.EMPTY.withColor(TextColor.parseColor("#ccfff9"));
    public static Style styleOfGather = Style.EMPTY.withColor(TextColor.parseColor("#fe68eb"));
    public static Style styleOfVolcano = Style.EMPTY.withColor(TextColor.parseColor("#ffae00"));
    public static Style styleOfEnd = Style.EMPTY.withColor(TextColor.parseColor("#9b4ecf"));
    public static Style styleOfForest = Style.EMPTY.withColor(TextColor.parseColor("#00620b"));
    public static Style styleOfPower = Style.EMPTY.withColor(TextColor.parseColor("#ff7600"));
    public static Style styleOfIntelligent = Style.EMPTY.withColor(TextColor.parseColor("#c600df"));
    public static Style styleOfFlexible = Style.EMPTY.withColor(TextColor.parseColor("#60df00"));
    public static Style styleOfLucky = Style.EMPTY.withColor(TextColor.parseColor("#f76ed0"));
    public static Style styleOfVitality = Style.EMPTY.withColor(TextColor.parseColor("#40afff"));
    public static Style styleOfSakura = Style.EMPTY.withColor(TextColor.parseColor("#f6a1fd"));
    public static Style styleOfWheat = Style.EMPTY.withColor(TextColor.parseColor("#ffce00"));
    public static Style styleOfEntropy = Style.EMPTY.withColor(TextColor.parseColor("#fd00a4"));
    public static Style styleOfSakuraMine = Style.EMPTY.withColor(TextColor.parseColor("#114788"));
    public static Style styleOfDemon = Style.EMPTY.withColor(TextColor.parseColor("#ff00d7"));
    public static Style styleOfMine = Style.EMPTY.withColor(TextColor.parseColor("#d0c7cc"));
    public static Style styleOfWither = Style.EMPTY.withColor(TextColor.parseColor("#363633"));
    public static Style styleOfWorld = Style.EMPTY.withColor(TextColor.parseColor("#9ef7ff"));
    public static Style styleOfInject = Style.EMPTY.withColor(TextColor.parseColor("#be9bcf"));
    public static Style styleOfPlain = Style.EMPTY.withColor(TextColor.parseColor("#02ff00"));
    public static Style styleOfPurpleIron = Style.EMPTY.withColor(TextColor.parseColor("#baa0ce"));
    public static Style styleOfIce = Style.EMPTY.withColor(TextColor.parseColor("#7cfdfc"));
    public static Style styleOfFantasy = Style.EMPTY.withColor(TextColor.parseColor("#fcc2f0"));
    public static Style styleOfAttack = Style.EMPTY.withColor(TextColor.parseColor("#f9ae54"));
    public static Style styleOfDefence = Style.EMPTY.withColor(TextColor.parseColor("#817a72"));
    public static Style styleOfDefencePenetration = Style.EMPTY.withColor(TextColor.parseColor("#ad8161"));
    public static Style styleOfCritRate = Style.EMPTY.withColor(TextColor.parseColor("#f363a6"));
    public static Style styleOfCritDamage = Style.EMPTY.withColor(TextColor.parseColor("#ff3e3e"));
    public static Style styleOfHealthSteal = Style.EMPTY.withColor(TextColor.parseColor("#822200"));
    public static Style styleOfManaDefence = Style.EMPTY.withColor(TextColor.parseColor("#4069c0"));
    public static Style styleOfManaPenetration = Style.EMPTY.withColor(TextColor.parseColor("#6840e2"));
    public static Style styleOfCoolDown = Style.EMPTY.withColor(TextColor.parseColor("#94e4ff"));
    public static Style styleOfMovementSpeed = Style.EMPTY.withColor(TextColor.parseColor("#94ffa0"));
    public static Style styleOfMaxHealth= Style.EMPTY.withColor(TextColor.parseColor("#afff00"));
    public static Style styleOfShip = Style.EMPTY.withColor(TextColor.parseColor("#1a4db7"));
    public static Style styleOfSpring = Style.EMPTY.withColor(TextColor.parseColor("#ff6f2c"));
    public static Style styleOfField = Style.EMPTY.withColor(TextColor.parseColor("#ffd300"));
    public static Style styleOfGold = Style.EMPTY.withColor(TextColor.parseColor("#eff767"));
    public static Style styleOfBloodMana = Style.EMPTY.withColor(TextColor.parseColor("#d63fa8"));
    public static Style styleOfRed = Style.EMPTY.withColor(TextColor.parseColor("#ff0000"));
    public static Style styleOfMoon = Style.EMPTY.withColor(TextColor.parseColor("#ffe7c1"));
    public static Style styleOfMoon1 = Style.EMPTY.withColor(TextColor.parseColor("#7374ff"));
    public static Style styleOfYSR = Style.EMPTY.withColor(TextColor.parseColor("#48d1cc"));
    public static Style styleOfYSR1 = Style.EMPTY.withColor(TextColor.parseColor("#dc143c"));
    public static Style styleOfBlack = Style.EMPTY.withColor(TextColor.parseColor("#000000"));
    public static Style styleOfCastle = Style.EMPTY.withColor(TextColor.parseColor("#535353"));
    public static Style styleOfCastleCrystal = Style.EMPTY.withColor(TextColor.parseColor("#444375"));

    public static boolean OverWorldLevelIsNight = false;
    public static int netherMobSpawn = 0;

    public static List<Mob> witherBonePowerCCMonster = new ArrayList<>();

    public static int witherBonePowerCount = 0;
    public static List<ServerWaltzPlayer> QuartzSabreCCPlayer = new ArrayList<>();

    public static List<ServerWaltzMonster> QuartzSabreCCMonster = new ArrayList<>();

    public static int waltztick = 0;
    public static boolean IsLandBarrier = true;
    public static Map<Player,List<Shield>> PlayerShieldQueue = new HashMap<>();
    public static int BlockLimitTick = 0;
    public static Map<String, Boolean> PotionMap = new HashMap<>();
    public static void PotionMapInit()
    {
        String[] PotionName = {
                "vmd:attackup_potion",
                "vmd:breakdefenceup_potion",
                "vmd:critrateup_potion",
                "vmd:critdamageup_potion",
                "vmd:manadamageup_potion",
                "vmd:manabreakdefenceup_potion",
                "vmd:manareplyup_potion",
                "vmd:cooldownup_potion",
                "vmd:healstealup_potion",
                "vmd:manadefenceup_potion",
                "vmd:defenceup_potion",
                "vmd:speedup_potion",
                "vmd:healreply_potion",

                "vmd:long_attackup_potion",
                "vmd:long_breakdefenceup_potion",
                "vmd:long_critrateup_potion",
                "vmd:long_critdamageup_potion",
                "vmd:long_manadamageup_potion",
                "vmd:long_manabreakdefenceup_potion",
                "vmd:long_manareplyup_potion",
                "vmd:long_cooldownup_potion",
                "vmd:long_healstealup_potion",
                "vmd:long_manadefenceup_potion",
                "vmd:long_defenceup_potion",
                "vmd:long_speedup_potion",
                "vmd:long_healreply_potion",

                "vmd:con_attackup_potion",
                "vmd:con_breakdefenceup_potion",
                "vmd:con_critrateup_potion",
                "vmd:con_critdamageup_potion",
                "vmd:con_manadamageup_potion",
                "vmd:con_manabreakdefenceup_potion",
                "vmd:con_manareplyup_potion",
                "vmd:con_cooldownup_potion",
                "vmd:con_healstealup_potion",
                "vmd:con_manadefenceup_potion",
                "vmd:con_defenceup_potion",
                "vmd:con_speedup_potion",
                "vmd:con_healreply_potion",
        };
        for (String s : PotionName) {
            PotionMap.put(s, true);
        }
    }
    public static Map<Item, Item> BrewSoulMap = new HashMap<>();
    public static boolean GemsForPlain = true;
    public static boolean GemsForForest = true;
    public static Map<MobEffect, Boolean> MobEffectMap = new HashMap<>();
    public static Map<Item, Boolean> ItemRightClickCheck = new HashMap<>() {
        {
            put(Items.WATER_BUCKET,true);
            put(Items.BUCKET,true);
            put(Items.LAVA_BUCKET,true);
            put(Items.POWDER_SNOW_BUCKET,true);
            put(Items.FLINT_AND_STEEL,true);
            put(Items.DIAMOND_HOE,true);
            put(Items.IRON_HOE,true);
            put(Items.GOLDEN_HOE,true);
            put(Items.NETHERITE_HOE,true);
            put(Items.STONE_HOE,true);
            put(Items.WOODEN_HOE,true);
            put(Items.DIAMOND_SHOVEL,true);
            put(Items.IRON_SHOVEL,true);
            put(Items.GOLDEN_SHOVEL,true);
            put(Items.NETHERITE_SHOVEL,true);
            put(Items.STONE_SHOVEL,true);
            put(Items.WOODEN_SHOVEL,true);
            put(Items.DIAMOND_AXE,true);
            put(Items.IRON_AXE,true);
            put(Items.GOLDEN_AXE,true);
            put(Items.NETHERITE_AXE,true);
            put(Items.STONE_AXE,true);
            put(Items.WOODEN_AXE,true);
            put(Items.SAND,true);
            put(Items.RED_SAND,true);
            put(Items.GRAVEL,true);
            put(Items.TNT,true);
            put(Items.REDSTONE,true);
            put(Items.REDSTONE_TORCH,true);
            put(Items.RAIL,true);
            put(Items.ACTIVATOR_RAIL,true);
            put(Items.DETECTOR_RAIL,true);
            put(Items.POWERED_RAIL,true);
            put(Items.REDSTONE_BLOCK,true);
            put(Items.REPEATER,true);
            put(Items.COMPARATOR,true);
            put(Items.PISTON,true);
            put(Items.STICKY_PISTON,true);
            put(Items.OBSERVER,true);
            put(Items.HOPPER,true);
            put(Items.DISPENSER,true);
            put(Items.DROPPER,true);
        }
    };
    public static int PFController = 0;
    public static int SVController = 0;
    public static int SLController = 0;
    public static int NSController = -1;
    public static List<Player> NSPlayerController = new ArrayList<>();
    public static List<Player> NSPlayerInController = new ArrayList<>();
    public static boolean NSClear = false;
    public static boolean NSS1 = false;
    public static boolean NSS2 = false;
    public static WitherSkeleton NSWitherSkeleton1;
    public static WitherSkeleton NSWitherSkeleton2;
    public static ZombifiedPiglin NSZombifiedPiglin1;
    public static ZombifiedPiglin NSZombifiedPiglin2;
    public static ZombifiedPiglin NSZombifiedPiglin3;
    public static String[] AttributeName = {
            "GemSAttack", // 20
            "GemSBreakDefence", // 5%
            "GemSCritRate", // 5%
            "GemSCritDamage", // 20%
            "GemSManaDamage", // 100
            "GemSManaBreakDefence", // 5%
            "GemSManaReply", // 1.5
            "GemSCoolDown", // 5%
            "GemSHealSteal", // 5%
            "GemSDefence", // 40
            "GemSManaDefence", // 40
            "GemSSpeed", // 20%
            "GemSMaxMana", // 10
            "GemSMaxHeal", // 100
            "GemSExpImprove", // 20%
            "GemSDefencePenetration0", // 20
            "GemSManaDefencePenetration0" // 20
    };
    public static Map<String,Double> AttributeMap = new HashMap<>() {{
        put(AttributeName[0], 20d);
        put(AttributeName[1], 0.05d);
        put(AttributeName[2], 0.03d);
        put(AttributeName[3], 0.2d);
        put(AttributeName[4], 100d);
        put(AttributeName[5], 0.05d);
        put(AttributeName[6], 1.5d);
        put(AttributeName[7], 0.03d);
        put(AttributeName[8], 0.05d);
        put(AttributeName[9], 40d);
        put(AttributeName[10], 40d);
        put(AttributeName[11], 0.2d);
        put(AttributeName[12], 10d);
        put(AttributeName[13], 100d);
        put(AttributeName[14], 0.2d);
        put(AttributeName[15], 20d);
        put(AttributeName[16], 20d);
    }};
    public static boolean FileFlag = true;
    public static boolean LogFlag = true;
    public static void SecurityInit() {
        double sec0 = ConfigTest.Security0.get();
        double sec1 = ConfigTest.Security1.get();
        double sec2 = ConfigTest.Security2.get();
        double sec3 = ConfigTest.Security3.get();
        Utils.security0 =  sec0;
        Utils.security1 =  sec1;
        Utils.security2 =  sec2;
        Utils.security3 =  sec3;
    }
    public static boolean SecurityInitFlag = true;
    public static boolean MarketFlag = true;
    public static int MarketTickCount = 0;
    public static List<MarketItemInfo> MarketInfo = new ArrayList<>();
    public static List<MarketPlayerInfo> MarketPlayerInfo = new ArrayList<>();
    public static Map<String, ItemStack> itemStackMap = new HashMap<>();
/*    public static Map<String, Integer> itemStackCode = new HashMap<>();
    public static void itemStackCodeInit() {
        itemStackCode.put(Moditems.Plant_Souls.get().toString(),0);
        itemStackCode.put(Moditems.plain_runes.get().toString(),1);
        itemStackCode.put(Moditems.forestsoul.get().toString(),2);
        itemStackCode.put(Moditems.forestrune.get().toString(),3);
        itemStackCode.put(Moditems.watersoul.get().toString(),4);
        itemStackCode.put(Moditems.waterrune.get().toString(),5);
        itemStackCode.put(Moditems.volcanosoul.get().toString(),6);
        itemStackCode.put(Moditems.volcanorune.get().toString(),7);
        itemStackCode.put(Moditems.minesoul.get().toString(),8);
        itemStackCode.put(Moditems.minesoul1.get().toString(),9);
        itemStackCode.put(Moditems.minerune.get().toString(),10);
        itemStackCode.put(Moditems.skysoul.get().toString(),11);
        itemStackCode.put(Moditems.skyrune.get().toString(),12);
        itemStackCode.put(Moditems.evokersoul.get().toString(),13);
        itemStackCode.put(Moditems.evokerrune.get().toString(),14);
        itemStackCode.put(Moditems.SeaSoul.get().toString(),15);
        itemStackCode.put(Moditems.SeaRune.get().toString(),16);
        itemStackCode.put(Moditems.BlackForestSoul.get().toString(),17);
        itemStackCode.put(Moditems.BlackForestRune.get().toString(),18);
        itemStackCode.put(Moditems.LightningSoul.get().toString(),19);
        itemStackCode.put(Moditems.LightningRune.get().toString(),20);
    }*/
/*    public static Map<String,Item> itemStackStringToItemMap = new HashMap<>();
    public static void setItemStackStringToItemMap() {
        itemStackStringToItemMap.put(Moditems.Plant_Souls.get().toString(),Moditems.Plant_Souls.get());
        itemStackStringToItemMap.put(Moditems.plain_runes.get().toString(),Moditems.plain_runes.get());
        itemStackStringToItemMap.put(Moditems.forestsoul.get().toString(),Moditems.forestsoul.get());
        itemStackStringToItemMap.put(Moditems.forestrune.get().toString(),Moditems.forestrune.get());
        itemStackStringToItemMap.put(Moditems.watersoul.get().toString(),Moditems.watersoul.get());
        itemStackStringToItemMap.put(Moditems.waterrune.get().toString(),Moditems.waterrune.get());
        itemStackStringToItemMap.put(Moditems.volcanosoul.get().toString(),Moditems.volcanosoul.get());
        itemStackStringToItemMap.put(Moditems.volcanorune.get().toString(),Moditems.volcanorune.get());
        itemStackStringToItemMap.put(Moditems.minesoul.get().toString(),Moditems.minesoul.get());
        itemStackStringToItemMap.put(Moditems.minesoul1.get().toString(),Moditems.minesoul1.get());
        itemStackStringToItemMap.put(Moditems.minerune.get().toString(),Moditems.minerune.get());
        itemStackStringToItemMap.put(Moditems.skysoul.get().toString(),Moditems.skysoul.get());
        itemStackStringToItemMap.put(Moditems.skyrune.get().toString(),Moditems.skyrune.get());
        itemStackStringToItemMap.put(Moditems.evokersoul.get().toString(),Moditems.evokersoul.get());
        itemStackStringToItemMap.put(Moditems.evokerrune.get().toString(),Moditems.evokerrune.get());
        itemStackStringToItemMap.put(Moditems.SeaSoul.get().toString(),Moditems.SeaSoul.get());
        itemStackStringToItemMap.put(Moditems.SeaRune.get().toString(),Moditems.SeaRune.get());
        itemStackStringToItemMap.put(Moditems.BlackForestSoul.get().toString(),Moditems.BlackForestSoul.get());
        itemStackStringToItemMap.put(Moditems.BlackForestRune.get().toString(),Moditems.BlackForestRune.get());
        itemStackStringToItemMap.put(Moditems.LightningSoul.get().toString(),Moditems.LightningSoul.get());
        itemStackStringToItemMap.put(Moditems.LightningRune.get().toString(),Moditems.LightningRune.get());
    }*/
    public static ChatFormatting style = ChatFormatting.AQUA;
    public static Map<String, ChatFormatting> PrefixColorMap = new HashMap<>() {
        {
            put("初来乍到",ChatFormatting.GRAY);
            put("平原统治者",ChatFormatting.GREEN);
            put("森林统治者",ChatFormatting.DARK_GREEN);
            put("湖泊统治者",ChatFormatting.BLUE);
            put("火山统治者",ChatFormatting.YELLOW);
            put("冰川统治者",ChatFormatting.AQUA);
            put("天空统治者",ChatFormatting.AQUA);
            put("唤魔森林统治者",ChatFormatting.LIGHT_PURPLE);
            put("酿造初识",ChatFormatting.GRAY);
            put("酿造入门",ChatFormatting.GREEN);
            put("酿造初级",ChatFormatting.BLUE);
            put("酿造中级",ChatFormatting.YELLOW);
            put("酿造高级",ChatFormatting.AQUA);
            put("酿造学士",ChatFormatting.GOLD);
            put("酿造大师",ChatFormatting.LIGHT_PURPLE);
            put("股市巴菲特",ChatFormatting.GOLD);
            put("见习渔夫",ChatFormatting.DARK_GRAY);
            put("入门渔夫",ChatFormatting.GRAY);
            put("中阶渔夫",ChatFormatting.YELLOW);
            put("高阶渔夫",ChatFormatting.BLUE);
            put("经常空军的钓鱼佬",ChatFormatting.GOLD);
            put("偶尔空军的钓鱼佬",ChatFormatting.RED);
            put("永不空军的钓鱼佬",ChatFormatting.LIGHT_PURPLE);
            put("见习矿工",ChatFormatting.DARK_GRAY);
            put("入门矿工",ChatFormatting.GRAY);
            put("职业矿工",ChatFormatting.GOLD);
            put("悲催苦力矿工",ChatFormatting.GREEN);
            put("一只挖矿的帕鲁",ChatFormatting.AQUA);
            put("见习伐木工",ChatFormatting.DARK_GRAY);
            put("入门伐木工",ChatFormatting.GRAY);
            put("职业伐木工",ChatFormatting.GOLD);
            put("光头强",ChatFormatting.GREEN);
            put("一只砍树的帕鲁",ChatFormatting.AQUA);
            put("见习农夫",ChatFormatting.DARK_GRAY);
            put("入门农夫",ChatFormatting.GRAY);
            put("职业农夫",ChatFormatting.GOLD);
            put("农耕大师",ChatFormatting.GREEN);
            put("一只种田的帕鲁",ChatFormatting.AQUA);
            put("赌神",ChatFormatting.GOLD);
        }
    };
    public static Map<String, Style> PrefixStyleMap = new HashMap<>() {
        {
            put("神殿统治者",Utils.styleOfSea);
            put("唤雷岛统治者",Utils.styleOfLightingIsland);
            put("黑色森林统治者",Utils.styleOfBlackForest);
            put("风之谷统治者",Utils.styleOfKaze);
            put("微光森林统治者",Utils.styleOfSpider);
            put("终界统治者",Utils.styleOfEnd);
            put("绯樱树林统治者",Utils.styleOfSakura);
            put("樱岛稻田统治者",Utils.styleOfWheat);
            put("紫晶矿洞统治者",Utils.styleOfPurpleIron);
            put("冰原统治者",Utils.styleOfIce);
            put("废旧船厂统治者",Utils.styleOfShip);
            put("龙行龘龘",Utils.styleOfSpring);
            put("理塘王",Utils.styleOfField);
            put("旧世地蕴封印者",Utils.styleOfMana);
            put("旧世腥月封印者",Utils.styleOfBloodMana);
            put("史莱姆的好伙伴(?)",Utils.styleOfHealth);
        }
    };
    public static Component[] BrewingLevelName = {
            Component.literal("酿造初识").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
            Component.literal("酿造入门").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
            Component.literal("酿造初级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE),
            Component.literal("酿造中级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW),
            Component.literal("酿造高级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
            Component.literal("酿造学士").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
            Component.literal("酿造大师").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)
    };
    public static Map<String, PrefixInfo> prefixInfoMap = new HashMap<>();
    public static List<Mob> SnowRune2MobController = new ArrayList<>();
    public static int SnowRune2Tick = -1;
    public static Vec2 KazeDot1 = new Vec2(27,1405);
    public static Vec2 KazeDot2 = new Vec2(-64,1525);
    public static Vec2 KazeDot3 = new Vec2(-15,1538);
    public static Vec2 KazeDot4 = new Vec2(68,1414);
    public static Vec2 SnowDot1 = new Vec2(-249,1276);
    public static Vec2 SnowDot2 = new Vec2(-263,1634);
    public static Vec2 SnowDot3 = new Vec2(-17,1432);
    public static Vec2 SnowDot4 = new Vec2(-39,1351);
    public static Vec3 SkyCity1 = new Vec3(-7,70,998);
    public static Vec3 SkyCity2 = new Vec3(103,205,1110);
    public static Vec2 ForestDot1 = new Vec2(125,985);
    public static Vec2 ForestDot2 = new Vec2(125,1091);
    public static Vec2 ForestDot3 = new Vec2(171,1088);
    public static Vec2 ForestDot4 = new Vec2(173,979);
    public static Vec2 Forest1Dot1 = new Vec2(-25,900);
    public static Vec2 Forest1Dot2 = new Vec2(-26,957);
    public static Vec2 Forest1Dot3 = new Vec2(113,897);
    public static Vec2 Forest1Dot4 = new Vec2(111,958);
    public static Vec2 PlainDot1 = new Vec2(225,854);
    public static Vec2 PlainDot2 = new Vec2(220,1135);
    public static Vec2 PlainDot3 = new Vec2(516,1135);
    public static Vec2 PlainDot4 = new Vec2(525,852);
    public static Vec2 ManaForest1Dot1 = new Vec2(31,849);
    public static Vec2 ManaForest1Dot2 = new Vec2(191,1062);
    public static Vec2 ManaForest1Dot3 = new Vec2(237,1032);
    public static Vec2 ManaForest1Dot4 = new Vec2(240,937);
    public static Vec2 ManaForest2Dot1 = new Vec2(27,844);
    public static Vec2 ManaForest2Dot2 = new Vec2(-179,984);
    public static Vec2 ManaForest2Dot3 = new Vec2(-120,996);
    public static Vec2 ManaForest2Dot4 = new Vec2(29,896);
    public static Vec2 SeaDot1 = new Vec2(640,271);
    public static Vec2 SeaDot2 = new Vec2(639,176);
    public static Vec2 SeaDot3 = new Vec2(735,177);
    public static Vec2 SeaDot4 = new Vec2(735,272);
    public static Vec2 LightningDot1 = new Vec2(1185,630);
    public static Vec2 LightningDot2 = new Vec2(1183,369);
    public static Vec2 LightningDot3 = new Vec2(1523,372);
    public static Vec2 LightningDot4 = new Vec2(1519,632);
    public static Map<ServerPlayer, Power> PowerMap = new HashMap<>();
    public static Map<Gather,Queue<Mob>> GatherMobMap = new HashMap<>();
    public static Map<Gather,Queue<Player>> GatherPlayerMap = new HashMap<>();
    public static int GatherTickCount = 0;
    public static Recall KazeRecall = new Recall();
    public static Skeleton KazeRecallSkeleton = null;
    public static Recall SpiderRecall = new Recall();
    public static Spider SpiderRecallSpider = null;
    public static BlockPos[] SpiderNetBlockPos = new BlockPos[15];
    public static Recall HuskRecall = new Recall();
    public static Husk HuskRecallHusk = null;
    public static Recall SeaRecall = new Recall();
    public static ElderGuardian SeaRecallElderGuardian = null;
    public static Recall LightningRecall = new Recall();
    public static Zombie LightingRecallZombie = null;
    public static Recall NetherRecall = new Recall();
    public static WitherSkeleton NetherRecallWither = null;
    public static Recall SnowRecall = new Recall();
    public static Stray SnowRecallStray = null;
    public static Recall ForestRecall = new Recall();
    public static Zombie ForestRecallZombie = null;
    public static Skeleton ForestRecallSkeleton = null;
    public static int ForestRecallBossKillCount = 0;
    public static Recall VolcanoRecall = new Recall();
    public static Blaze VolcanoRecallBlaze = null;
    public static Map<Player, SwordSkill3> SwordSkill3Map = new HashMap<>();
    public static Map<Player, SwordSkill6> SwordSkill6Map = new HashMap<>();
    public static Map<Player, SwordSkill13> SwordSkill13Map = new HashMap<>();
    public static Map<Player, Boolean> SwordSkill12 = new HashMap<>();
    public static Map<Player, Boolean> BowSkill12 = new HashMap<>();
    public static Map<Player, Boolean> ManaSkill12 = new HashMap<>();
    public static Map<Player, Boolean> ManaSkill13 = new HashMap<>();
    public static Map<Player, BowSkill3> BowSkill3Map = new HashMap<>();
    public static Map<Player, BowSkill6> BowSkill6Map = new HashMap<>();
    public static Map<Player, ManaSkill3> ManaSkill3Map = new HashMap<>();
    public static Map<Player, ManaSkill6> ManaSkill6Map = new HashMap<>();
    public static Map<Player, Boolean> SakuraDemonSword = new HashMap<>();
    public static Map<Player, Boolean> ZeusSword = new HashMap<>();

    public static class WeaponTypeComponents {
        public static MutableComponent MainHand = Component.literal("主手                   ").withStyle(ChatFormatting.AQUA);
        public static MutableComponent AxeType = Component.literal("斧头").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED);
        public static MutableComponent ShortHandleSword = Component.literal("短柄剑").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW);
        public static MutableComponent DemonSword = Component.literal("妖刀").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfDemon);
        public static MutableComponent NormalSword = Component.literal("长剑").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower);
        public static MutableComponent Bow = Component.literal("长弓").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible);
        public static MutableComponent Sceptre = Component.literal("权杖").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfIntelligent);

    }

    public static class WeaponSkillComponents {
        public static MutableComponent Active = Component.literal("主动:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA);
    }

    public static class Emoji {
        public static String Mana = "☄";
        public static String Sword = "⚔";
        public static String Defence = "\uD83D\uDEE1";
        public static String CritRate = "\uD83D\uDCA2";
        public static String CritDamage = "\uD83D\uDCA5";
        public static String HealSteal = "\uD83E\uDE78";
        public static String Speed = "\uD83C\uDFBF";
        public static String AttackRange = "\uD83E\uDD3A";
        public static String ManaCost = "\uD83D\uDCA7";
        public static String ManaRecover = "\uD83D\uDD2E";
        public static String Health = "❤";
        public static String ExpUp = "☘";
        public static String CoolDown = "\uD83D\uDD52";
        public static String HealthRecover = "\uD83C\uDF31";
        public static String MaxMana = "\uD83D\uDD2F";
        public static String Lucky = "\uD83C\uDF40";
        public static String HealthAmplification = "⚕";
        public static String Suit = "\uD83E\uDDE5";
        public static String AttackSpeed = "\uD83D\uDD2A";
        public static String Swiftness = "\uD83D\uDE80";
    }

    public static ArrayList<String> IpArrayList = new ArrayList<>();
    public static Map<String, String> IpLoginMap = new HashMap<>();

    public static ArrayList<Item> CoolDownItem = new ArrayList<>();

    public static HashMap<Player, Double> PlayerAttackSpeedHashMap = new HashMap<>();

    public static HashMap<Player, List<Slime>> PlayerRandomSlimeMap = new HashMap<>();
    public static HashMap<Slime, Player> SlimeRewardPlayer = new HashMap<>();

    public static void Init (){
        BrewSoulMap.put(ModItems.PlainSoul.get(), ModItems.PlainSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.ForestSoul.get(), ModItems.ForestSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.LakeSoul.get(), ModItems.LakeSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.VolcanoSoul.get(), ModItems.VolcanoSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.SnowSoul.get(), ModItems.SnowSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.EvokerSoul.get(), ModItems.EvokerSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.Ruby.get(), ModItems.NetherSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.SkySoul.get(), ModItems.SkySolidifiedSoul.get());
        MobEffectMap.put(ModEffects.PLAINARMOR.get(),true);
        MobEffectMap.put(ModEffects.FORESTARMOR.get(),true);
        MobEffectMap.put(ModEffects.VOLCANOARMOR.get(),true);
        MobEffectMap.put(ModEffects.LAKEARMOR.get(),true);
        MobEffectMap.put(ModEffects.LIFEMANA.get(),true);
        MobEffectMap.put(ModEffects.OBSIMANA.get(),true);
        MobEffectMap.put(ModEffects.SKYARMOR.get(),true);
        MobEffectMap.put(ModEffects.LIGHTNINGARMOR.get(),true);
        itemStackMap.put(ModItems.PlainSoul.get().toString(), ModItems.PlainSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainRune.get().toString(), ModItems.PlainRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestSoul.get().toString(), ModItems.ForestSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestRune.get().toString(), ModItems.ForestRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeSoul.get().toString(), ModItems.LakeSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeRune.get().toString(), ModItems.LakeRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoSoul.get().toString(), ModItems.VolcanoSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoRune.get().toString(), ModItems.VolcanoRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineSoul.get().toString(), ModItems.MineSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineSoul1.get().toString(), ModItems.MineSoul1.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineRune.get().toString(), ModItems.MineRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkySoul.get().toString(), ModItems.SkySoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyRune.get().toString(), ModItems.SkyRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.EvokerSoul.get().toString(), ModItems.EvokerSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.EvokerRune.get().toString(), ModItems.EvokerRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.SeaSoul.get().toString(), ModItems.SeaSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.SeaRune.get().toString(), ModItems.SeaRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlackForestSoul.get().toString(), ModItems.BlackForestSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlackForestRune.get().toString(), ModItems.BlackForestRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.LightningSoul.get().toString(), ModItems.LightningSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.LightningRune.get().toString(), ModItems.LightningRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoCore.get().toString(), ModItems.VolcanoCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherOintment0.get().toString(), ModItems.NetherOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherOintment1.get().toString(), ModItems.NetherOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherOintment2.get().toString(), ModItems.NetherOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaOintment0.get().toString(), ModItems.ManaOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaOintment1.get().toString(), ModItems.ManaOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaOintment2.get().toString(), ModItems.ManaOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyOintment0.get().toString(), ModItems.SkyOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyOintment1.get().toString(), ModItems.SkyOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyOintment2.get().toString(), ModItems.SkyOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowOintment0.get().toString(), ModItems.SnowOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowOintment1.get().toString(), ModItems.SnowOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowOintment2.get().toString(), ModItems.SnowOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoOintment0.get().toString(), ModItems.VolcanoOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoOintment1.get().toString(), ModItems.VolcanoOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoOintment2.get().toString(), ModItems.VolcanoOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeOintment0.get().toString(), ModItems.LakeOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeOintment1.get().toString(), ModItems.LakeOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeOintment2.get().toString(), ModItems.LakeOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.SunOintment0.get().toString(), ModItems.SunOintment0.get().getDefaultInstance());
        itemStackMap.put(ModItems.SunOintment1.get().toString(), ModItems.SunOintment1.get().getDefaultInstance());
        itemStackMap.put(ModItems.SunOintment2.get().toString(), ModItems.SunOintment2.get().getDefaultInstance());
        itemStackMap.put(ModItems.SpiderSoul.get().toString(), ModItems.SpiderSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.KazeBootsForgeDraw.get().toString(), ModItems.KazeBootsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeCore.get().toString(), ModItems.LakeCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.KazeSwordForgeDraw.get().toString(), ModItems.KazeSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.KazeRune.get().toString(), ModItems.KazeRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.KazeSoul.get().toString(), ModItems.KazeSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherHelmetForgeDraw.get().toString(), ModItems.NetherHelmetForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherChestForgeDraw.get().toString(), ModItems.NetherChestForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherLeggingsForgeDraw.get().toString(), ModItems.NetherLeggingsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherBootsForgeDraw.get().toString(), ModItems.NetherBootsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.RandomGemPiece.get().toString(), ModItems.RandomGemPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.GemBag.get().toString(), ModItems.GemBag.get().getDefaultInstance());
        itemStackMap.put(ModItems.SunPower.get().toString(), ModItems.SunPower.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlackForestSwordForgeDraw.get().toString(), ModItems.BlackForestSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SeaSwordForgeDraw.get().toString(), ModItems.SeaSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyBootsForgeDraw.get().toString(), ModItems.SkyBootsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyLeggingsForgeDraw.get().toString(), ModItems.SkyLeggingsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyChestForgeDraw.get().toString(), ModItems.SkyChestForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyHelmetForgeDraw.get().toString(), ModItems.SkyHelmetForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.GoldCoinBag.get().toString(), ModItems.GoldCoinBag.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowGem.get().toString(), ModItems.SnowGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoGem.get().toString(), ModItems.VolcanoGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeGem.get().toString(), ModItems.LakeGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestGem.get().toString(), ModItems.ForestGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainGem.get().toString(), ModItems.PlainGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.LightningRune.get().toString(), ModItems.LightningRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.LightningSoul.get().toString(), ModItems.LightningSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.PigLinSoul.get().toString(), ModItems.PigLinSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.witherBone.get().toString(), ModItems.witherBone.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherSwordModel.get().toString(), ModItems.NetherSwordModel.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherRune.get().toString(), ModItems.NetherRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherSoul.get().toString(), ModItems.NetherSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.Ruby.get().toString(), ModItems.Ruby.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaBucket.get().toString(), ModItems.ManaBucket.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyGem.get().toString(), ModItems.SkyGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.Main1Crystal.get().toString(), ModItems.Main1Crystal.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestBossCore.get().toString(), ModItems.ForestBossCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoBossCore.get().toString(), ModItems.VolcanoBossCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeBossCore.get().toString(), ModItems.LakeBossCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyBossCore.get().toString(), ModItems.SkyBossCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.SakuraPetal.get().toString(), ModItems.SakuraPetal.get().getDefaultInstance());
        itemStackMap.put(ModItems.Wheat.get().toString(), ModItems.Wheat.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowBossCore.get().toString(), ModItems.SnowBossCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainBossSoul.get().toString(), ModItems.PlainBossSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.PurpleIron.get().toString(), ModItems.PurpleIron.get().getDefaultInstance());
        itemStackMap.put(ModItems.PurpleIronPiece.get().toString(), ModItems.PurpleIronPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceSoul.get().toString(), ModItems.IceSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainCompleteGem.get().toString(), ModItems.PlainCompleteGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceCompleteGem.get().toString(), ModItems.IceCompleteGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.LeatherSoul.get().toString(), ModItems.LeatherSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.LeatherRune.get().toString(), ModItems.LeatherRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.OreSoul.get().toString(), ModItems.OreSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.OreRune.get().toString(), ModItems.OreRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.NaturalCore.get().toString(), ModItems.NaturalCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.CrudeCoal.get().toString(), ModItems.CrudeCoal.get().getDefaultInstance());
        itemStackMap.put(ModItems.HotCoal.get().toString(), ModItems.HotCoal.get().getDefaultInstance());
        itemStackMap.put(ModItems.RefiningCoal.get().toString(), ModItems.RefiningCoal.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlazeCoal.get().toString(), ModItems.BlazeCoal.get().getDefaultInstance());
        itemStackMap.put(ModItems.CrudeIron.get().toString(), ModItems.CrudeIron.get().getDefaultInstance());
        itemStackMap.put(ModItems.HotIron.get().toString(), ModItems.HotIron.get().getDefaultInstance());
        itemStackMap.put(ModItems.RefiningIron.get().toString(), ModItems.RefiningIron.get().getDefaultInstance());
        itemStackMap.put(ModItems.CrudeCopper.get().toString(), ModItems.CrudeCopper.get().getDefaultInstance());
        itemStackMap.put(ModItems.HotCopper.get().toString(), ModItems.HotCopper.get().getDefaultInstance());
        itemStackMap.put(ModItems.RefiningCopper.get().toString(), ModItems.RefiningCopper.get().getDefaultInstance());
        itemStackMap.put(ModItems.CrudeGold.get().toString(), ModItems.CrudeGold.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlazeGold.get().toString(), ModItems.BlazeGold.get().getDefaultInstance());
        itemStackMap.put(ModItems.RefiningGold.get().toString(), ModItems.RefiningGold.get().getDefaultInstance());
        itemStackMap.put(ModItems.ReputationMedal.get().toString(), ModItems.ReputationMedal.get().getDefaultInstance());
        itemStackMap.put(ModItems.CompleteGem.get().toString(), ModItems.CompleteGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.RevelationBook.get().toString(), ModItems.RevelationBook.get().getDefaultInstance());
        itemStackMap.put(ModItems.WorldSoul1.get().toString(), ModItems.WorldSoul1.get().getDefaultInstance());
        itemStackMap.put(ModItems.WorldSoul2.get().toString(), ModItems.WorldSoul2.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainCrest0.get().toString(), ModItems.PlainCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainCrest1.get().toString(), ModItems.PlainCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainCrest2.get().toString(), ModItems.PlainCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.PlainCrest3.get().toString(), ModItems.PlainCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestCrest0.get().toString(), ModItems.ForestCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestCrest1.get().toString(), ModItems.ForestCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestCrest2.get().toString(), ModItems.ForestCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestCrest3.get().toString(), ModItems.ForestCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeCrest0.get().toString(), ModItems.LakeCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeCrest1.get().toString(), ModItems.LakeCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeCrest2.get().toString(), ModItems.LakeCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeCrest3.get().toString(), ModItems.LakeCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoCrest0.get().toString(), ModItems.VolcanoCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoCrest1.get().toString(), ModItems.VolcanoCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoCrest2.get().toString(), ModItems.VolcanoCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoCrest3.get().toString(), ModItems.VolcanoCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineCrest0.get().toString(), ModItems.MineCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineCrest1.get().toString(), ModItems.MineCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineCrest2.get().toString(), ModItems.MineCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.MineCrest3.get().toString(), ModItems.MineCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowCrest0.get().toString(), ModItems.SnowCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowCrest1.get().toString(), ModItems.SnowCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowCrest2.get().toString(), ModItems.SnowCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowCrest3.get().toString(), ModItems.SnowCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyCrest0.get().toString(), ModItems.SkyCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyCrest1.get().toString(), ModItems.SkyCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyCrest2.get().toString(), ModItems.SkyCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyCrest3.get().toString(), ModItems.SkyCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaCrest0.get().toString(), ModItems.ManaCrest0.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaCrest1.get().toString(), ModItems.ManaCrest1.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaCrest2.get().toString(), ModItems.ManaCrest2.get().getDefaultInstance());
        itemStackMap.put(ModItems.ManaCrest3.get().toString(), ModItems.ManaCrest3.get().getDefaultInstance());
        itemStackMap.put(ModItems.ShipPiece.get().toString(), ModItems.ShipPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeImprove0.get().toString(), ModItems.ForgeImprove0.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeImprove1.get().toString(), ModItems.ForgeImprove1.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeImprove2.get().toString(), ModItems.ForgeImprove2.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeEnhance0.get().toString(), ModItems.ForgeEnhance0.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeEnhance1.get().toString(), ModItems.ForgeEnhance1.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeEnhance2.get().toString(), ModItems.ForgeEnhance2.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForgeProtect.get().toString(), ModItems.ForgeProtect.get().getDefaultInstance());

        itemStackMap.put(ModItems.KazeSwordForgeDraw.get().toString(), ModItems.KazeSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.KazeBootsForgeDraw.get().toString(), ModItems.KazeBootsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SakuraSwordForgeDraw.get().toString(), ModItems.SakuraSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.ForestSword4ForgeDraw.get().toString(), ModItems.ForestSword4ForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.VolcanoSword4ForgeDraw.get().toString(), ModItems.VolcanoSword4ForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.LakeSword4ForgeDraw.get().toString(), ModItems.LakeSword4ForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SkyBossBowForgeDraw.get().toString(), ModItems.SkyBossBowForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SnowBossChestForgeDraw.get().toString(), ModItems.SnowBossChestForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SeaManaCoreForgeDraw.get().toString(), ModItems.SeaManaCoreForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlackForestManaCoreForgeDraw.get().toString(), ModItems.BlackForestManaCoreForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.KazeManaCoreForgeDraw.get().toString(), ModItems.KazeManaCoreForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SakuraBowForgingDraw.get().toString(), ModItems.SakuraBowForgingDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SakuraCoreForgingDraw.get().toString(), ModItems.SakuraCoreForgingDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceHelmetForgeDraw.get().toString(), ModItems.IceHelmetForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceChestForgeDraw.get().toString(), ModItems.IceChestForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceLeggingsForgeDraw.get().toString(), ModItems.IceLeggingsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceBootsForgeDraw.get().toString(), ModItems.IceBootsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.SeaBowForgeDraw.get().toString(), ModItems.SeaBowForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceSwordForgeDraw.get().toString(), ModItems.IceSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceBowForgeDraw.get().toString(), ModItems.IceBowForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceSceptreForgeDraw.get().toString(), ModItems.IceSceptreForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.ShipSwordForgeDraw.get().toString(), ModItems.ShipSwordForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.ShipBowForgeDraw.get().toString(), ModItems.ShipBowForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.ShipSceptreForgeDraw.get().toString(), ModItems.ShipSceptreForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherManaHelmetForgeDraw.get().toString(), ModItems.NetherManaHelmetForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherManaChestForgeDraw.get().toString(), ModItems.NetherManaChestForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherManaLeggingsForgeDraw.get().toString(), ModItems.NetherManaLeggingsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherManaBootsForgeDraw.get().toString(), ModItems.NetherManaBootsForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NetherSceptreForgeDraw.get().toString(), ModItems.NetherSceptreForgeDraw.get().getDefaultInstance());
        itemStackMap.put(ModItems.NaturalCore.get().toString(), ModItems.NaturalCore.get().getDefaultInstance());
        itemStackMap.put(ModItems.ShipPiece.get().toString(), ModItems.ShipPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceHeart.get().toString(), ModItems.IceHeart.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceBracelet.get().toString(), ModItems.IceBracelet.get().getDefaultInstance());
        itemStackMap.put(ModItems.IceBook.get().toString(), ModItems.IceBook.get().getDefaultInstance());
        itemStackMap.put(ModItems.SpringSoul.get().toString(), ModItems.SpringSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.SpringHeart.get().toString(), ModItems.SpringHeart.get().getDefaultInstance());
        itemStackMap.put(ModItems.GiantMedal.get().toString(), ModItems.GiantMedal.get().getDefaultInstance());
        itemStackMap.put(ModItems.CropBag.get().toString(), ModItems.CropBag.get().getDefaultInstance());
        itemStackMap.put(ModItems.LogBag.get().toString(), ModItems.LogBag.get().getDefaultInstance());
        itemStackMap.put(ModItems.EarthManaSoul.get().toString(), ModItems.EarthManaSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.BloodManaSoul.get().toString(), ModItems.BloodManaSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.EarthManaRune.get().toString(), ModItems.EarthManaRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.BloodManaRune.get().toString(), ModItems.BloodManaRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.DevilBlood.get().toString(), ModItems.DevilBlood.get().getDefaultInstance());
        itemStackMap.put(ModItems.DevilAttackSoul.get().toString(), ModItems.DevilAttackSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.DevilSwiftSoul.get().toString(), ModItems.DevilSwiftSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.DevilManaSoul.get().toString(), ModItems.DevilManaSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.MoonSoul.get().toString(), ModItems.MoonSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.MoonCompleteGem.get().toString(), ModItems.MoonCompleteGem.get().getDefaultInstance());
        itemStackMap.put(ModItems.SlimeBall.get().toString(), ModItems.SlimeBall.get().getDefaultInstance());
        itemStackMap.put(ModItems.BigSlimeBall.get().toString(), ModItems.BigSlimeBall.get().getDefaultInstance());
        itemStackMap.put(ModItems.IntensifiedDevilBlood.get().toString(), ModItems.IntensifiedDevilBlood.get().getDefaultInstance());
        itemStackMap.put(ModItems.TabooPiece.get().toString(), ModItems.TabooPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.PurpleIronConstraintStone.get().toString(), ModItems.PurpleIronConstraintStone.get().getDefaultInstance());
        itemStackMap.put(ModItems.ConstrainTaboo.get().toString(), ModItems.ConstrainTaboo.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastlePiece.get().toString(), ModItems.CastlePiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastleIngot.get().toString(), ModItems.CastleIngot.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastleSwordPiece.get().toString(), ModItems.CastleSwordPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastleBowPiece.get().toString(), ModItems.CastleBowPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastleSceptrePiece.get().toString(), ModItems.CastleSceptrePiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastleArmorPiece.get().toString(), ModItems.CastleArmorPiece.get().getDefaultInstance());
        itemStackMap.put(ModItems.CastleSoul.get().toString(), ModItems.CastleSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.BeaconSoul.get().toString(), ModItems.BeaconSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.BeaconRune.get().toString(), ModItems.BeaconRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlazeSoul.get().toString(), ModItems.BlazeSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.BlazeRune.get().toString(), ModItems.BlazeRune.get().getDefaultInstance());
        itemStackMap.put(ModItems.TreeSoul.get().toString(), ModItems.TreeSoul.get().getDefaultInstance());
        itemStackMap.put(ModItems.TreeRune.get().toString(), ModItems.TreeRune.get().getDefaultInstance());

        CoolDownItem.add(ModItems.PlainSword0.get());
        CoolDownItem.add(ModItems.PlainSword1.get());
        CoolDownItem.add(ModItems.PlainSword2.get());
        CoolDownItem.add(ModItems.PlainSword3.get());
        CoolDownItem.add(ModItems.ForestSword0.get());
        CoolDownItem.add(ModItems.ForestSword1.get());
        CoolDownItem.add(ModItems.ForestSword2.get());
        CoolDownItem.add(ModItems.ForestSword3.get());
        CoolDownItem.add(ModItems.ForestSword4.get());
        CoolDownItem.add(ModItems.LakeSword3.get());
        CoolDownItem.add(ModItems.VolcanoSword0.get());
        CoolDownItem.add(ModItems.VolcanoSword1.get());
        CoolDownItem.add(ModItems.VolcanoSword2.get());
        CoolDownItem.add(ModItems.VolcanoSword3.get());
        CoolDownItem.add(ModItems.VolcanoSword4.get());
        CoolDownItem.add(ModItems.SnowSword3.get());
        CoolDownItem.add(ModItems.SnowSword4.get());
        CoolDownItem.add(ModItems.ManaSword.get());
        CoolDownItem.add(ModItems.ManaSword1.get());
        CoolDownItem.add(ModItems.QuartzSabre.get());
        CoolDownItem.add(ModItems.QuartzSword.get());
        CoolDownItem.add(ModItems.SeaSword0.get());
        CoolDownItem.add(ModItems.SeaSword1.get());
        CoolDownItem.add(ModItems.SeaSword2.get());
        CoolDownItem.add(ModItems.SeaSword3.get());
        CoolDownItem.add(ModItems.SeaSword4.get());
        CoolDownItem.add(ModItems.BlackForestSword0.get());
        CoolDownItem.add(ModItems.BlackForestSword1.get());
        CoolDownItem.add(ModItems.BlackForestSword2.get());
        CoolDownItem.add(ModItems.BlackForestSword3.get());
        CoolDownItem.add(ModItems.BlackForestSword4.get());
        CoolDownItem.add(ModItems.KazeSword0.get());
        CoolDownItem.add(ModItems.KazeSword1.get());
        CoolDownItem.add(ModItems.KazeSword2.get());
        CoolDownItem.add(ModItems.KazeSword3.get());
        CoolDownItem.add(ModItems.KazeSword4.get());
        CoolDownItem.add(ModItems.ForestBossSword.get());
        CoolDownItem.add(ModItems.VolcanoBossSword.get());
        CoolDownItem.add(ModItems.SakuraDemonSword.get());
    }
    public static HashMap<String, ParticleOptions> ParticleStringToParticleMap = new HashMap<>(){{
        put(StringUtils.ParticleTypes.Witch, ParticleTypes.WITCH);
        put(StringUtils.ParticleTypes.Composter, ParticleTypes.COMPOSTER);
        put(StringUtils.ParticleTypes.LongVolcano, ModParticles.LONG_VOLCANO.get());
        put(StringUtils.ParticleTypes.Scrape, ParticleTypes.SCRAPE);
        put(StringUtils.ParticleTypes.EffectMana, ModParticles.EFFECT_MANA.get());
        put(StringUtils.ParticleTypes.RangeMana, ModParticles.RANGE_MANA.get());
        put(StringUtils.ParticleTypes.DamageMana, ModParticles.DAMAGE_MANA.get());
        put(StringUtils.ParticleTypes.DefencePenetrationMana, ModParticles.BREAKDefence_MANA.get());
        put(StringUtils.ParticleTypes.SnowMana, ModParticles.SNOW_MANA.get());
        put(StringUtils.ParticleTypes.KazeMana, ModParticles.KAZE_MANA.get());
        put(StringUtils.ParticleTypes.LightningMana, ModParticles.LIGHTNING_MANA.get());
        put(StringUtils.ParticleTypes.GatherMana, ModParticles.GATHER_MANA.get());
        put(StringUtils.ParticleTypes.SnowFlake, ParticleTypes.SNOWFLAKE);
        put(StringUtils.ParticleTypes.Heart, ParticleTypes.HEART);
        put(StringUtils.ParticleTypes.AngryVillager, ParticleTypes.ANGRY_VILLAGER);
        put(StringUtils.ParticleTypes.Ash, ParticleTypes.ASH);
        put(StringUtils.ParticleTypes.Lava, ParticleTypes.LAVA);
        put(StringUtils.ParticleTypes.EnchantedHit, ParticleTypes.ENCHANTED_HIT);
        put(StringUtils.ParticleTypes.TotemOfUndying, ParticleTypes.TOTEM_OF_UNDYING);
        put(StringUtils.ParticleTypes.SnowFlake, ParticleTypes.SNOWFLAKE);
        put(StringUtils.ParticleTypes.WAX_OFF, ParticleTypes.WAX_OFF);
        put(StringUtils.ParticleTypes.World, ModParticles.WORLD.get());
        put(StringUtils.ParticleTypes.Long_Entropy, ModParticles.LONG_ENTROPY.get());
        put(StringUtils.ParticleTypes.Cherry_Leaves, ParticleTypes.CHERRY_LEAVES);
        put(StringUtils.ParticleTypes.Long_Plain, ModParticles.LONG_PLAIN.get());
        put(StringUtils.ParticleTypes.Long_Forest, ModParticles.LONG_FOREST.get());
        put(StringUtils.ParticleTypes.Long_Lake, ModParticles.LONG_LAKE.get());
        put(StringUtils.ParticleTypes.Long_Snow, ModParticles.LONG_SNOW.get());
        put(StringUtils.ParticleTypes.Long_Sea, ModParticles.LONG_SEA.get());
        put(StringUtils.ParticleTypes.Black_Forest, ModParticles.BLACKFOREST_RECALL.get());
        put(StringUtils.ParticleTypes.FallingWater, ParticleTypes.FALLING_WATER);
        put(StringUtils.ParticleTypes.DrippedWater, ParticleTypes.DRIPPING_WATER);
        put(StringUtils.ParticleTypes.LongSky, ModParticles.LONG_SKY.get());
        put(StringUtils.ParticleTypes.LongSpring, ModParticles.LONG_SPRING.get());
        put(StringUtils.ParticleTypes.Spring, ModParticles.SPRING.get());
        put(StringUtils.ParticleTypes.LongLightning, ModParticles.LONG_LIGHTNINGISLAND.get());
        put(StringUtils.ParticleTypes.Smoke, ParticleTypes.SMOKE);
        put(StringUtils.ParticleTypes.Volcano, ModParticles.VOLCANO.get());
        put(StringUtils.ParticleTypes.Nether, ModParticles.NETHER.get());
        put(StringUtils.ParticleTypes.RedSpell, ModParticles.RED_SPELL.get());
        put(StringUtils.ParticleTypes.LongRedSpell, ModParticles.LONG_RED_SPELL.get());
        put(StringUtils.ParticleTypes.Snow, ModParticles.SNOW.get());
        put(StringUtils.ParticleTypes.FireWork, ParticleTypes.FIREWORK);
        put(StringUtils.ParticleTypes.WhiteSpell, ModParticles.WHITE_SPELL.get());
        put(StringUtils.ParticleTypes.Entropy, ModParticles.ENTROPY.get());
        put(StringUtils.ParticleTypes.YSR, ModParticles.YSR.get());
        put(StringUtils.ParticleTypes.YSR1, ModParticles.YSR1.get());
        put(StringUtils.ParticleTypes.LiuliSpell, ModParticles.LiuliSpell.get());
        put(StringUtils.ParticleTypes.BIG, ModParticles.BIG.get());
        put(StringUtils.ParticleTypes.PurpleIronBig, ModParticles.PurpleIronBig.get());
        put(StringUtils.ParticleTypes.PurpleIronOneTick, ModParticles.PurpleIronOneTick.get());
    }};
    public static HashMap<ParticleOptions, String> ParticleToParticleStringMap = new HashMap<>() {{
        put(ParticleTypes.WITCH,StringUtils.ParticleTypes.Witch);
        put(ParticleTypes.COMPOSTER,StringUtils.ParticleTypes.Composter);
        put(ModParticles.LONG_VOLCANO.get(),StringUtils.ParticleTypes.LongVolcano);
        put(ParticleTypes.SCRAPE,StringUtils.ParticleTypes.Scrape);
        put(ModParticles.EFFECT_MANA.get(),StringUtils.ParticleTypes.EffectMana);
        put(ModParticles.RANGE_MANA.get(),StringUtils.ParticleTypes.RangeMana);
        put(ModParticles.DAMAGE_MANA.get(),StringUtils.ParticleTypes.DamageMana);
        put(ModParticles.BREAKDefence_MANA.get(),StringUtils.ParticleTypes.DefencePenetrationMana);
        put(ModParticles.SNOW_MANA.get(),StringUtils.ParticleTypes.SnowMana);
        put(ModParticles.KAZE_MANA.get(),StringUtils.ParticleTypes.KazeMana);
        put(ModParticles.LIGHTNING_MANA.get(),StringUtils.ParticleTypes.LightningMana);
        put(ModParticles.GATHER_MANA.get(),StringUtils.ParticleTypes.GatherMana);
        put(ModParticles.LONG_VOLCANO.get(),StringUtils.ParticleTypes.LongVolcano);
        put(ParticleTypes.HEART,StringUtils.ParticleTypes.Heart);
        put(ParticleTypes.ANGRY_VILLAGER,StringUtils.ParticleTypes.AngryVillager);
        put(ParticleTypes.ASH,StringUtils.ParticleTypes.Ash);
        put(ParticleTypes.LAVA,StringUtils.ParticleTypes.Lava);
        put(ParticleTypes.ENCHANTED_HIT,StringUtils.ParticleTypes.EnchantedHit);
        put(ParticleTypes.TOTEM_OF_UNDYING,StringUtils.ParticleTypes.TotemOfUndying);
        put(ParticleTypes.SNOWFLAKE,StringUtils.ParticleTypes.SnowFlake);
        put(ParticleTypes.WAX_OFF,StringUtils.ParticleTypes.WAX_OFF);
        put(ModParticles.WORLD.get(),StringUtils.ParticleTypes.World);
        put(ModParticles.LONG_ENTROPY.get(),StringUtils.ParticleTypes.Long_Entropy);
        put(ParticleTypes.CHERRY_LEAVES,StringUtils.ParticleTypes.Cherry_Leaves);
        put(ModParticles.LONG_PLAIN.get(),StringUtils.ParticleTypes.Long_Plain);
        put(ModParticles.LONG_FOREST.get(),StringUtils.ParticleTypes.Long_Forest);
        put(ModParticles.LONG_LAKE.get(),StringUtils.ParticleTypes.Long_Lake);
        put(ModParticles.LONG_SNOW.get(),StringUtils.ParticleTypes.Long_Snow);
        put(ModParticles.LONG_SEA.get(),StringUtils.ParticleTypes.Long_Sea);
        put(ModParticles.BLACKFOREST_RECALL.get(),StringUtils.ParticleTypes.Black_Forest);
        put(ParticleTypes.FALLING_WATER,StringUtils.ParticleTypes.FallingWater);
        put(ParticleTypes.DRIPPING_WATER,StringUtils.ParticleTypes.DrippedWater);
        put(ModParticles.LONG_SKY.get(),StringUtils.ParticleTypes.LongSky);
        put(ModParticles.LONG_SPRING.get(),StringUtils.ParticleTypes.LongSpring);
        put(ModParticles.SPRING.get(),StringUtils.ParticleTypes.Spring);
        put(ModParticles.LONG_LIGHTNINGISLAND.get(),StringUtils.ParticleTypes.LongLightning);
        put(ParticleTypes.SMOKE,StringUtils.ParticleTypes.Smoke);
        put(ModParticles.VOLCANO.get(),StringUtils.ParticleTypes.Volcano);
        put(ModParticles.NETHER.get(),StringUtils.ParticleTypes.Nether);
        put(ModParticles.RED_SPELL.get(),StringUtils.ParticleTypes.RedSpell);
        put(ModParticles.LONG_RED_SPELL.get(),StringUtils.ParticleTypes.LongRedSpell);
        put(ModParticles.SNOW.get(),StringUtils.ParticleTypes.Snow);
        put(ParticleTypes.FIREWORK,StringUtils.ParticleTypes.FireWork);
        put(ModParticles.WHITE_SPELL.get(),StringUtils.ParticleTypes.WhiteSpell);
        put(ModParticles.ENTROPY.get(),StringUtils.ParticleTypes.Entropy);
        put(ModParticles.YSR.get(),StringUtils.ParticleTypes.YSR);
        put(ModParticles.YSR1.get(),StringUtils.ParticleTypes.YSR1);
        put(ModParticles.LiuliSpell.get(),StringUtils.ParticleTypes.LiuliSpell);
        put(ModParticles.BIG.get(),StringUtils.ParticleTypes.BIG);
        put(ModParticles.PurpleIronOneTick.get(),StringUtils.ParticleTypes.PurpleIronOneTick);
        put(ModParticles.PurpleIronBig.get(),StringUtils.ParticleTypes.PurpleIronBig);
    }};

    public static List<Boss2Damage> boss2DamageList = new ArrayList<>();

    public static List<Boss2Damage> IceKnightDamageList = new ArrayList<>();

    public static List<Boss2Damage> SpringDamageList = new ArrayList<>();

    public static List<Boss2Damage> DevilDamageList = new ArrayList<>();

    public static List<Boss2Damage> MoonDamageList = new ArrayList<>();

    public static List<Boss2Damage> TabooDevilDamageList = new ArrayList<>();

    public static List<Boss2Damage> PurpleIronKnightDamageList = new ArrayList<>();

    public static int Boss2DeadTimes = 0;

    public static Boss2 boss2;

    public static Map<String,Integer> BowNumMap = new HashMap<>(){{
        this.put(StringUtils.BowNameString.PlainBow,0);
        this.put(StringUtils.BowNameString.ForestBow,1);
        this.put(StringUtils.BowNameString.VolcanoBow,2);
        this.put(StringUtils.BowNameString.NetherBow,3);
        this.put(StringUtils.BowNameString.SkyBossBow,4);
        this.put(StringUtils.BowNameString.SkyBow,4);
        this.put(StringUtils.BowNameString.WitherBow,5);
        this.put(StringUtils.BowNameString.WorldBow,6);
        this.put(StringUtils.BowNameString.SakuraBow,7);
        this.put(StringUtils.BowNameString.SeaBow,8);
        this.put(StringUtils.BowNameString.MineBow,9);
        this.put(StringUtils.BowNameString.IceBow,10);
        this.put(StringUtils.BowNameString.ShipBow,11);
        this.put(StringUtils.BowNameString.ShowdickerBow,12);
        this.put(StringUtils.BowNameString.GuangYiBow,13);
        this.put(StringUtils.BowNameString.WcndymlgbBow,14);
        this.put(StringUtils.BowNameString.MoonBow,15);
        this.put(StringUtils.BowNameString.YxwgBow,16);
        this.put(StringUtils.BowNameString.MyMissionBow,17);
        this.put(StringUtils.BowNameString.LeiyanBow,18);
        this.put(StringUtils.BowNameString.QiFuBow,19);
    }};

    public static Map<Component,String> PotionComponentMap = new HashMap<>(){{
        this.put(Component.literal("攻击提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW), StringUtils.PotionTypes.AttackUp);
        this.put(Component.literal("破甲提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY), StringUtils.PotionTypes.BreakDefenceUp);
        this.put(Component.literal("冷却缩减提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA), StringUtils.PotionTypes.CoolDownDecreaseUp);
        this.put(Component.literal("暴击伤害提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE), StringUtils.PotionTypes.CritDamageUp);
        this.put(Component.literal("暴击几率提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE), StringUtils.PotionTypes.CritRateUp);
        this.put(Component.literal("护甲提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY), StringUtils.PotionTypes.DefenceUp);
        this.put(Component.literal("生命偷取提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED), StringUtils.PotionTypes.HealStealUp);
        this.put(Component.literal("生命回复提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN), StringUtils.PotionTypes.HealthRecover);
        this.put(Component.literal("魔法穿透提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE), StringUtils.PotionTypes.ManaBreakDefenceUp);
        this.put(Component.literal("魔法攻击提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE), StringUtils.PotionTypes.ManaDamageUp);
        this.put(Component.literal("魔法抗性提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE), StringUtils.PotionTypes.ManaDefenceUp);
        this.put(Component.literal("魔法回复提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE), StringUtils.PotionTypes.ManaReplyUp);
        this.put(Component.literal("移动速度提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN), StringUtils.PotionTypes.SpeedUp);
    }};

    public static Map<String,Component> PotionStringComponentMap = new HashMap<>(){{
        this.put(StringUtils.PotionTypes.AttackUp, Component.literal("攻击提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW));
        this.put(StringUtils.PotionTypes.BreakDefenceUp, Component.literal("破甲提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        this.put(StringUtils.PotionTypes.CoolDownDecreaseUp, Component.literal("冷却缩减提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        this.put(StringUtils.PotionTypes.CritDamageUp, Component.literal("暴击伤害提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        this.put(StringUtils.PotionTypes.CritRateUp, Component.literal("暴击几率提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        this.put(StringUtils.PotionTypes.DefenceUp, Component.literal("护甲提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        this.put(StringUtils.PotionTypes.HealStealUp, Component.literal("生命偷取提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
        this.put(StringUtils.PotionTypes.HealthRecover, Component.literal("生命回复提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
        this.put(StringUtils.PotionTypes.ManaBreakDefenceUp, Component.literal("魔法穿透提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        this.put(StringUtils.PotionTypes.ManaDamageUp, Component.literal("魔法攻击提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        this.put(StringUtils.PotionTypes.ManaDefenceUp, Component.literal("魔法抗性提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        this.put(StringUtils.PotionTypes.ManaReplyUp, Component.literal("魔法回复提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        this.put(StringUtils.PotionTypes.SpeedUp, Component.literal("移动速度提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));

    }};

    public static Map<Player,Integer> RollingTickMap = new HashMap<>();

    public static Map<Integer,Mission> MissionMap = new HashMap<>(){{
        this.put(1,new Mission("任务：游览地图1","  前往平原。",new Vec3(244,83,1087)));
        this.put(2,new Mission("任务：游览地图2","  前往森林。",new Vec3(166,72,1020)));
        this.put(3,new Mission("任务：游览地图3","  前往湖泊。",new Vec3(38,69,922)));
        this.put(4,new Mission("任务：游览地图4","  前往矿洞。",new Vec3(59,-2,914)));
        this.put(5,new Mission("任务：游览地图5","  前往平原列车站。",new Vec3(447,71,1014)));
        this.put(6,new Mission("任务：游览地图6","  前往天空城。（请注意列车方向）",new Vec3(43,121,1052)));
        this.put(7,new Mission("任务：游览地图7","  前往天空城高塔。",new Vec3(32,154,1079)));
        this.put(8,new Mission("任务：游览地图8","  前往天空城飞艇。（在天空城高塔处右键“前往飞艇”告示牌）",new Vec3(28,175,1026)));
        this.put(9,new Mission("任务：游览地图9","  前往玉山。（可以在天空城列车站通过乘坐列车前往）",new Vec3(-74,194,1371)));
        this.put(10,new Mission("任务：游览地图10","  前往樱岛。（距离较远，建议乘坐列车前往）",new Vec3(1312,69,1225)));
    }};

    public static double WorldEntropyIncreaseSpeed = 0.5;

    public static List<WorldEntropy> WorldEntropyPos = new ArrayList<>(){{
        this.add(new WorldEntropy(new Vec3(370,73,989),0,"平原  "));
        this.add(new WorldEntropy(new Vec3(63,138,1083),0,"天空城 "));
        this.add(new WorldEntropy(new Vec3(1324,71,1236),0,"樱岛  "));
    }};

    public static List<Item> SoulList = new ArrayList<>();

    public static void SoulListInit() {
        SoulList.add(ModItems.PlainSoul.get());
        SoulList.add(ModItems.ForestSoul.get());
        SoulList.add(ModItems.LakeSoul.get());
        SoulList.add(ModItems.VolcanoSoul.get());
        SoulList.add(ModItems.MineSoul.get());
        SoulList.add(ModItems.FieldSoul.get());
        SoulList.add(ModItems.SnowSoul.get());
        SoulList.add(ModItems.SkySoul.get());
        SoulList.add(ModItems.EvokerSoul.get());
        SoulList.add(ModItems.SeaSoul.get());
        SoulList.add(ModItems.LightningSoul.get());
        SoulList.add(ModItems.BlackForestSoul.get());
        SoulList.add(ModItems.KazeSoul.get());
        SoulList.add(ModItems.SpiderSoul.get());
        SoulList.add(ModItems.PlainSoul.get());
    }

    public static Map<Item,Item> WorldSoulMap = new HashMap<>();

    public static void WorldSoulMapInit() {
        WorldSoulMap.put(ModItems.WorldSoul1.get(),ModItems.WorldSoul2.get());
        WorldSoulMap.put(ModItems.WorldSoul2.get(),ModItems.WorldSoul3.get());
        WorldSoulMap.put(ModItems.WorldSoul3.get(),ModItems.WorldSoul4.get());
        WorldSoulMap.put(ModItems.WorldSoul4.get(),ModItems.WorldSoul5.get());
    }

    public static List<ParticleOptions> particleOptionsList = new ArrayList<>();

    public static void setParticleOptionsList() {
        particleOptionsList.add(ModParticles.LONG_LAKE.get());
        particleOptionsList.add(ModParticles.LONG_SPIDER.get());
        particleOptionsList.add(ModParticles.LONG_SNOW.get());
        particleOptionsList.add(ModParticles.LONG_SKY.get());
        particleOptionsList.add(ModParticles.LONG_SEA.get());
        particleOptionsList.add(ModParticles.LONG_PLAIN.get());
        particleOptionsList.add(ModParticles.LONG_VOLCANO.get());
        particleOptionsList.add(ModParticles.LONG_KAZE.get());
        particleOptionsList.add(ModParticles.LONG_MANAFOREST.get());
        particleOptionsList.add(ModParticles.BLACKFOREST.get());
        particleOptionsList.add(ModParticles.LONG_LIGHTNINGISLAND.get());
    };

    public static Rarity WorldBold = Rarity.create("world", style -> {
        return style.withColor(TextColor.parseColor("#9ef7ff")).withBold(true);
    });

    public static Rarity WorldItalic = Rarity.create("world", style -> {
        return style.withColor(TextColor.parseColor("#9ef7ff")).withItalic(true);
    });

    public static Rarity PlainItalic = Rarity.create("plain_italic", style -> {
        return style.applyFormat(ChatFormatting.GREEN).withItalic(true).withBold(true);
    });

    public static Rarity Plain = Rarity.create("plain", style -> {
        return style.applyFormat(ChatFormatting.GREEN);
    });

    public static Rarity PlainBold = Rarity.create("plain_bold", style -> {
        return style.applyFormat(ChatFormatting.GREEN).withBold(true);
    });

    public static Rarity ForestItalic = Rarity.create("forest_italic", style -> {
        return style.applyFormat(ChatFormatting.DARK_GREEN).withItalic(true).withBold(true);
    });

    public static Rarity Forest = Rarity.create("forest", style -> {
        return style.applyFormat(ChatFormatting.DARK_GREEN);
    });

    public static Rarity ForestBold = Rarity.create("forest_bold", style -> {
        return style.applyFormat(ChatFormatting.DARK_GREEN).withBold(true);
    });

    public static Rarity LakeItalic = Rarity.create("lake_italic", style -> {
        return style.applyFormat(ChatFormatting.BLUE).withItalic(true).withBold(true);
    });

    public static Rarity Lake = Rarity.create("lake", style -> {
        return style.applyFormat(ChatFormatting.BLUE);
    });

    public static Rarity LakeBold = Rarity.create("lake_bold", style -> {
        return style.applyFormat(ChatFormatting.BLUE).withBold(true);
    });

    public static Rarity VolcanoItalic = Rarity.create("volcano_italic", style -> {
        return style.applyFormat(ChatFormatting.YELLOW).withItalic(true).withBold(true);
    });

    public static Rarity Volcano = Rarity.create("volcano", style -> {
        return style.applyFormat(ChatFormatting.YELLOW);
    });

    public static Rarity VolcanoBold = Rarity.create("volcano_bold", style -> {
        return style.applyFormat(ChatFormatting.YELLOW).withBold(true);
    });

    public static Rarity MineItalic = Rarity.create("mine_italic", style -> {
        return style.applyFormat(ChatFormatting.GRAY).withItalic(true).withBold(true);
    });

    public static Rarity Mine = Rarity.create("mine", style -> {
        return style.applyFormat(ChatFormatting.GRAY);
    });

    public static Rarity MineBold = Rarity.create("mine_bold", style -> {
        return style.applyFormat(ChatFormatting.GRAY).withBold(true);
    });

    public static Rarity SnowItalic = Rarity.create("snow_italic", style -> {
        return style.applyTo(Utils.styleOfSnow).withItalic(true).withBold(true);
    });

    public static Rarity Snow = Rarity.create("snow", style -> {
        return style.applyTo(Utils.styleOfSnow);
    });

    public static Rarity SnowBold = Rarity.create("snow_bold", style -> {
        return style.applyTo(Utils.styleOfSnow).withBold(true);
    });

    public static Rarity SkyItalic = Rarity.create("sky_italic", style -> {
        return style.applyTo(Utils.styleOfSky).withItalic(true).withBold(true);
    });

    public static Rarity Sky = Rarity.create("sky", style -> {
        return style.applyTo(Utils.styleOfSky);
    });

    public static Rarity SkyBold = Rarity.create("sky_bold", style -> {
        return style.applyTo(Utils.styleOfSky).withBold(true);
    });

    public static Rarity EvokerItalic = Rarity.create("evoker_italic", style -> {
        return style.applyTo(Utils.styleOfMana).withItalic(true).withBold(true);
    });

    public static Rarity Evoker = Rarity.create("evoker", style -> {
        return style.applyTo(Utils.styleOfMana);
    });

    public static Rarity EvokerBold = Rarity.create("evoker_bold", style -> {
        return style.applyTo(Utils.styleOfMana).withBold(true);
    });

    public static Rarity LifeItalic = Rarity.create("life_italic", style -> {
        return style.applyTo(Utils.styleOfHealth).withItalic(true).withBold(true);
    });

    public static Rarity Life = Rarity.create("life", style -> {
        return style.applyTo(Utils.styleOfHealth);
    });

    public static Rarity LifeBold = Rarity.create("life_bold", style -> {
        return style.applyTo(Utils.styleOfHealth).withBold(true);
    });

    public static Rarity NetherItalic = Rarity.create("nether_italic", style -> {
        return style.applyTo(Utils.styleOfNether).withItalic(true).withBold(true);
    });

    public static Rarity Nether = Rarity.create("nether", style -> {
        return style.applyTo(Utils.styleOfNether);
    });

    public static Rarity NetherBold = Rarity.create("nether_bold", style -> {
        return style.applyTo(Utils.styleOfNether).withBold(true);
    });

    public static Rarity QuartzItalic = Rarity.create("quartz_italic", style -> {
        return style.applyTo(Utils.styleOfQuartz).withItalic(true).withBold(true);
    });

    public static Rarity Quartz = Rarity.create("quartz", style -> {
        return style.applyTo(Utils.styleOfQuartz);
    });

    public static Rarity QuartzBold = Rarity.create("quartz_bold", style -> {
        return style.applyTo(Utils.styleOfQuartz).withBold(true);
    });

    public static Rarity SeaItalic = Rarity.create("sea_italic", style -> {
        return style.applyTo(Utils.styleOfSea).withItalic(true).withBold(true);
    });

    public static Rarity Sea = Rarity.create("sea", style -> {
        return style.applyTo(Utils.styleOfSea);
    });

    public static Rarity SeaBold = Rarity.create("sea_bold", style -> {
        return style.applyTo(Utils.styleOfSea).withBold(true);
    });

    public static Rarity BlackForestItalic = Rarity.create("blackforest_italic", style -> {
        return style.applyTo(Utils.styleOfBlackForest).withItalic(true).withBold(true);
    });

    public static Rarity BlackForest = Rarity.create("blackforest", style -> {
        return style.applyTo(Utils.styleOfBlackForest);
    });

    public static Rarity BlackForestBold = Rarity.create("blackforest_bold", style -> {
        return style.applyTo(Utils.styleOfBlackForest).withBold(true);
    });

    public static Rarity KazeItalic = Rarity.create("kaze_italic", style -> {
        return style.applyTo(Utils.styleOfKaze).withItalic(true).withBold(true);
    });

    public static Rarity Kaze = Rarity.create("kaze", style -> {
        return style.applyTo(Utils.styleOfKaze);
    });

    public static Rarity KazeBold = Rarity.create("kaze_bold", style -> {
        return style.applyTo(Utils.styleOfKaze).withBold(true);
    });

    public static Rarity SakuraItalic = Rarity.create("sakura_italic", style -> {
        return style.applyTo(Utils.styleOfSakura).withItalic(true).withBold(true);
    });

    public static Rarity Sakura = Rarity.create("sakura", style -> {
        return style.applyTo(Utils.styleOfSakura);
    });

    public static Rarity SakuraBold = Rarity.create("sakura_bold", style -> {
        return style.applyTo(Utils.styleOfSakura).withBold(true);
    });

    public static Rarity WitherItalic = Rarity.create("wither_italic", style -> {
        return style.applyTo(Utils.styleOfWither).withItalic(true).withBold(true);
    });

    public static Rarity Wither = Rarity.create("wither", style -> {
        return style.applyTo(Utils.styleOfWither);
    });

    public static Rarity WitherBold = Rarity.create("wither_bold", style -> {
        return style.applyTo(Utils.styleOfWither).withBold(true);
    });

    public static Rarity MagmaItalic = Rarity.create("magma_italic", style -> {
        return style.applyTo(Utils.styleOfPower).withItalic(true).withBold(true);
    });

    public static Rarity Magma = Rarity.create("magma", style -> {
        return style.applyTo(Utils.styleOfPower);
    });

    public static Rarity MagmaBold = Rarity.create("magma_bold", style -> {
        return style.applyTo(Utils.styleOfPower).withBold(true);
    });

    public static Rarity LightningItalic = Rarity.create("lightning_italic", style -> {
        return style.applyTo(Utils.styleOfLightingIsland).withItalic(true).withBold(true);
    });

    public static Rarity Lightning = Rarity.create("lightning", style -> {
        return style.applyTo(Utils.styleOfLightingIsland);
    });

    public static Rarity LightningBold = Rarity.create("lightning_bold", style -> {
        return style.applyTo(Utils.styleOfLightingIsland).withBold(true);
    });

    public static Rarity SpiderItalic = Rarity.create("spider_italic", style -> {
        return style.applyTo(Utils.styleOfSpider).withItalic(true).withBold(true);
    });

    public static Rarity SpiderRarity = Rarity.create("spider", style -> {
        return style.applyTo(Utils.styleOfSpider);
    });

    public static Rarity SpiderBold = Rarity.create("spider_bold", style -> {
        return style.applyTo(Utils.styleOfSpider).withBold(true);
    });

    public static Rarity End = Rarity.create("end", style -> {
        return style.applyTo(Utils.styleOfEnd).withBold(true);
    });

    public static Rarity Piglin = Rarity.create("piglin", style -> {
        return style.applyFormat(ChatFormatting.GOLD).withBold(true);
    });


    public static Rarity PiglinItalic = Rarity.create("piglin_italic", style -> {
        return style.applyFormat(ChatFormatting.GOLD).withBold(true);
    });

    public static Rarity SakuraMineItalic = Rarity.create("sakura_mine_italic", style -> {
        return style.applyTo(Utils.styleOfSakuraMine).withBold(true);
    });

    public static Rarity EntropyItalic = Rarity.create("entropy_italic", style -> {
        return style.applyTo(Utils.styleOfEntropy).withBold(true).withItalic(true);
    });

    public static Rarity InjectItalic = Rarity.create("inject_italic", style -> {
        return style.applyTo(Utils.styleOfInject).withBold(true).withItalic(true);
    });

    public static Rarity PurpleIron = Rarity.create("purple_iron", style -> {
        return style.applyTo(Utils.styleOfPurpleIron);
    });

    public static Rarity PurpleIronBold = Rarity.create("purple_iron_bold", style -> {
        return style.applyTo(Utils.styleOfPurpleIron).withBold(true);
    });

    public static Rarity PurpleIronItalic = Rarity.create("purple_iron_italic", style -> {
        return style.applyTo(Utils.styleOfPurpleIron).withBold(true).withItalic(true);
    });

    public static Rarity Ice = Rarity.create("ice_iron", style -> {
        return style.applyTo(Utils.styleOfIce);
    });

    public static Rarity IceBold = Rarity.create("ice_iron_bold", style -> {
        return style.applyTo(Utils.styleOfIce).withBold(true);
    });

    public static Rarity IceItalic = Rarity.create("ice_iron_italic", style -> {
        return style.applyTo(Utils.styleOfIce).withBold(true).withItalic(true);
    });

    public static Rarity Fantasy = Rarity.create("fantasy", style -> {
        return style.applyTo(Utils.styleOfFantasy);
    });

    public static Rarity FantasyBold = Rarity.create("fantasy_bold", style -> {
        return style.applyTo(Utils.styleOfFantasy).withBold(true);
    });

    public static Rarity FantasyItalic = Rarity.create("fantasy_iron_italic", style -> {
        return style.applyTo(Utils.styleOfFantasy).withBold(true).withItalic(true);
    });

    public static Rarity Ship = Rarity.create("ship", style -> {
        return style.applyTo(Utils.styleOfShip);
    });

    public static Rarity ShipBold = Rarity.create("ship_bold", style -> {
        return style.applyTo(Utils.styleOfShip).withBold(true);
    });

    public static Rarity ShipItalic = Rarity.create("ship_iron_italic", style -> {
        return style.applyTo(Utils.styleOfShip).withBold(true).withItalic(true);
    });

    public static Rarity Spring = Rarity.create("spring", style -> {
        return style.applyTo(Utils.styleOfSpring);
    });

    public static Rarity SpringBold = Rarity.create("spring_bold", style -> {
        return style.applyTo(Utils.styleOfSpring).withBold(true);
    });

    public static Rarity SpringItalic = Rarity.create("spring_iron_italic", style -> {
        return style.applyTo(Utils.styleOfSpring).withBold(true).withItalic(true);
    });

    public static Rarity Field = Rarity.create("field", style -> {
        return style.applyTo(Utils.styleOfField);
    });

    public static Rarity FieldBold = Rarity.create("field_bold", style -> {
        return style.applyTo(Utils.styleOfField).withBold(true);
    });

    public static Rarity FieldItalic = Rarity.create("field_italic", style -> {
        return style.applyTo(Utils.styleOfField).withBold(true).withItalic(true);
    });

    public static Rarity Gold = Rarity.create("gold", style -> {
        return style.applyTo(Utils.styleOfGold);
    });

    public static Rarity GoldBold = Rarity.create("gold_bold", style -> {
        return style.applyTo(Utils.styleOfGold).withBold(true);
    });

    public static Rarity GoldItalic = Rarity.create("gold_italic", style -> {
        return style.applyTo(Utils.styleOfGold).withBold(true).withItalic(true);
    });

    public static Rarity BloodManaStyle = Rarity.create("blood_mana", style -> {
        return style.applyTo(Utils.styleOfBloodMana);
    });

    public static Rarity BloodManaBold = Rarity.create("blood_mana_bold", style -> {
        return style.applyTo(Utils.styleOfBloodMana).withBold(true);
    });

    public static Rarity BloodManaItalic = Rarity.create("blood_mana_italic", style -> {
        return style.applyTo(Utils.styleOfBloodMana).withBold(true).withItalic(true);
    });

    public static Rarity Red = Rarity.create("red", style -> {
        return style.applyTo(Utils.styleOfRed);
    });

    public static Rarity RedBold = Rarity.create("red_bold", style -> {
        return style.applyTo(Utils.styleOfRed).withBold(true);
    });

    public static Rarity RedItalic = Rarity.create("red_italic", style -> {
        return style.applyTo(Utils.styleOfRed).withBold(true).withItalic(true);
    });

    public static Rarity Moon = Rarity.create("moon", style -> {
        return style.applyTo(Utils.styleOfMoon);
    });



    public static Rarity MoonBold = Rarity.create("moon_bold", style -> {
        return style.applyTo(Utils.styleOfMoon).withBold(true);
    });

    public static Rarity MoonItalic = Rarity.create("moon_italic", style -> {
        return style.applyTo(Utils.styleOfMoon).withBold(true).withItalic(true);
    });

    public static Rarity Moon1 = Rarity.create("moon1", style -> {
        return style.applyTo(Utils.styleOfMoon1);
    });

    public static Rarity Moon1Bold = Rarity.create("moon1_bold", style -> {
        return style.applyTo(Utils.styleOfMoon1).withBold(true);
    });

    public static Rarity Moon1Italic = Rarity.create("moon1_italic", style -> {
        return style.applyTo(Utils.styleOfMoon1).withBold(true).withItalic(true);
    });

    public static Rarity YSR = Rarity.create("ysr", style -> {
        return style.applyTo(Utils.styleOfYSR);
    });

    public static Rarity YSRBold = Rarity.create("ysr_bold", style -> {
        return style.applyTo(Utils.styleOfYSR).withBold(true);
    });

    public static Rarity YSRItalic = Rarity.create("ysr_italic", style -> {
        return style.applyTo(Utils.styleOfYSR).withBold(true).withItalic(true);
    });

    public static Rarity YSR1 = Rarity.create("ysr1", style -> {
        return style.applyTo(Utils.styleOfYSR1);
    });

    public static Rarity YSR1Bold = Rarity.create("ysr1_bold", style -> {
        return style.applyTo(Utils.styleOfYSR1).withBold(true);
    });

    public static Rarity YSR1Italic = Rarity.create("ysr1_italic", style -> {
        return style.applyTo(Utils.styleOfYSR1).withBold(true).withItalic(true);
    });

    public static Rarity Black = Rarity.create("black", style -> {
        return style.applyTo(Utils.styleOfBlack);
    });

    public static Rarity BlackBold = Rarity.create("black_bold", style -> {
        return style.applyTo(Utils.styleOfBlack).withBold(true);
    });

    public static Rarity BlackItalic = Rarity.create("black_italic", style -> {
        return style.applyTo(Utils.styleOfBlack).withBold(true).withItalic(true);
    });

    public static Rarity Castle = Rarity.create("castle", style -> {
        return style.applyTo(Utils.styleOfCastle);
    });

    public static Rarity CastleBold = Rarity.create("castle_bold", style -> {
        return style.applyTo(Utils.styleOfCastle).withBold(true);
    });

    public static Rarity CastleItalic = Rarity.create("castle_italic", style -> {
        return style.applyTo(Utils.styleOfCastle).withBold(true).withItalic(true);
    });

    public static Rarity CastleCrystal = Rarity.create("castle_crystal", style -> {
        return style.applyTo(Utils.styleOfCastleCrystal);
    });

    public static Rarity CastleCrystalBold = Rarity.create("castle_crystal_bold", style -> {
        return style.applyTo(Utils.styleOfCastleCrystal).withBold(true);
    });

    public static Rarity CastleCrystalItalic = Rarity.create("castle_crystal_italic", style -> {
        return style.applyTo(Utils.styleOfCastleCrystal).withBold(true).withItalic(true);
    });

    public static Map<String, Style> gemStringStyleMap = new HashMap<>(){{
        this.put("skyGem",Utils.styleOfSky);
        this.put("EvokerGem",Utils.styleOfMana);
        this.put("plainGem",Utils.styleOfPlain);
        this.put("forestGem",Utils.styleOfForest);
        this.put("lakeGem",Utils.styleOfLake);
        this.put("volcanoGem",Utils.styleOfVolcano);
        this.put("snowGem",Utils.styleOfSnow);
        this.put(StringUtils.GemName.FieldGem,Utils.styleOfField);
        this.put(StringUtils.GemName.MineGem,Utils.styleOfMine);
        this.put(StringUtils.GemName.LifeManaGem,Utils.styleOfHealth);
        this.put(StringUtils.GemName.ObsiManaGem,Utils.styleOfMana);
        this.put(StringUtils.GemName.NetherSkeletonGem,Utils.styleOfWither);
        this.put(StringUtils.GemName.MagmaGem,Utils.styleOfPower);
        this.put(StringUtils.GemName.WitherGem,Utils.styleOfWither);
        this.put(StringUtils.GemName.PiglinGem,Utils.styleOfGold);
        this.put(StringUtils.GemName.SakuraGem,Utils.styleOfSakura);
        this.put(StringUtils.GemName.ShipGem,Utils.styleOfShip);
        this.put(StringUtils.GemName.MoonAttackGem,Utils.styleOfMoon);
        this.put(StringUtils.GemName.MoonManaGem,Utils.styleOfMoon1);

        this.put(StringUtils.GemName.SkyGemD,Utils.styleOfSky);
        this.put(StringUtils.GemName.EvokerGemD,Utils.styleOfMana);
        this.put(StringUtils.GemName.PlainGemD,Utils.styleOfPlain);
        this.put(StringUtils.GemName.ForestGemD,Utils.styleOfForest);
        this.put(StringUtils.GemName.LakeGemD,Utils.styleOfLake);
        this.put(StringUtils.GemName.VolcanoGemD,Utils.styleOfVolcano);
        this.put(StringUtils.GemName.SnowGemD,Utils.styleOfSnow);
        this.put(StringUtils.GemName.FieldGemD,Utils.styleOfField);
        this.put(StringUtils.GemName.MineGemD,Utils.styleOfMine);
        this.put(StringUtils.GemName.LifeManaGemD,Utils.styleOfHealth);
        this.put(StringUtils.GemName.ObsiManaGemD,Utils.styleOfMana);
        this.put(StringUtils.GemName.NetherSkeletonGemD,Utils.styleOfWither);
        this.put(StringUtils.GemName.MagmaGemD,Utils.styleOfPower);
        this.put(StringUtils.GemName.WitherGemD,Utils.styleOfWither);
        this.put(StringUtils.GemName.PiglinGemD,Utils.styleOfGold);
        this.put(StringUtils.GemName.SakuraGemD,Utils.styleOfSakura);
        this.put(StringUtils.GemName.ShipGemD,Utils.styleOfShip);
        this.put(StringUtils.GemName.MoonAttackGemD,Utils.styleOfMoon);
        this.put(StringUtils.GemName.MoonManaGemD,Utils.styleOfMoon1);
        this.put(StringUtils.GemName.CastleWeaponGem,Utils.styleOfCastleCrystal);
        this.put(StringUtils.GemName.CastleArmorGem,Utils.styleOfCastleCrystal);
    }};

    public static List<ForestPowerEffectMob> ForestPowerEffectMobList = new ArrayList<>();

    public static Map<Mob, LakePowerEffect> LakePowerEffectMobMap = new HashMap<>();

    public static Map<Item, List<ItemStack>> ForgeDrawRecipe = new HashMap<>();

    public static void ForgeDrawRecipeInit() {
        ForgeDrawRecipe.put(ModItems.SakuraDemonSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SakuraPetal.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),192));
            add(new ItemStack(ModItems.CompleteGem.get(),24));
            add(new ItemStack(ModItems.ReputationMedal.get(),24));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),10));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),14));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IslandArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LightningRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SkyArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SeaSword0.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SeaRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.BlackForestSword0.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.BlackForestRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WitherRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.NetherSkeletonRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MagmaRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.PiglinRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.KazeSword0.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.KazeRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.KazeBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.KazeRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),64));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.ForestBossSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ForestBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.VolcanoBossSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.VolcanoBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.LakeBossSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.LakeBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SkyBossBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SkyBossCore.get(),128));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SnowBossArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SnowBossCore.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),2));
            add(new ItemStack(ModItems.RefiningCopper.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SeaManaCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SeaRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.BlackForestManaCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.BlackForestRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.KazeManaCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.KazeRune.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SakuraBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SakuraPetal.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),192));
            add(new ItemStack(ModItems.CompleteGem.get(),24));
            add(new ItemStack(ModItems.ReputationMedal.get(),24));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.SakuraCore.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SakuraPetal.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.NaturalCore.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.MinePants.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.Wheat.get(),64));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),4));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningIron.get(),4));
            add(new ItemStack(ModItems.RefiningCopper.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SeaBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SeaRune.get(),8));
            add(new ItemStack(ModItems.BlackForestRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.IceSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.IceSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.IceCompleteGem.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),64));
            add(new ItemStack(ModItems.ReputationMedal.get(),64));
            add(new ItemStack(ModItems.NaturalCore.get(),16));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.ShipSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShipPiece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.ShipBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShipPiece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.RefiningGold.get(),2));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.ShipSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ShipPiece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),256));
            add(new ItemStack(ModItems.CompleteGem.get(),32));
            add(new ItemStack(ModItems.ReputationMedal.get(),32));
            add(new ItemStack(ModItems.NaturalCore.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.WitherRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.NetherSkeletonRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MagmaRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherManaArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.PiglinRune.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),12));
            add(new ItemStack(ModItems.ReputationMedal.get(),12));
            add(new ItemStack(ModItems.RefiningIron.get(),1));
            add(new ItemStack(ModItems.RefiningCopper.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.NetherSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.NetherSoul.get(),32));
            add(new ItemStack(ModItems.GoldCoin.get(),192));
            add(new ItemStack(ModItems.CompleteGem.get(),24));
            add(new ItemStack(ModItems.ReputationMedal.get(),24));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.NetherRune.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringAttackArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringSwiftArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.SpringManaArmorBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.SpringHeart.get(),8));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),72));
            add(new ItemStack(ModItems.ReputationMedal.get(),72));
            add(new ItemStack(ModItems.RefiningIron.get(),3));
            add(new ItemStack(ModItems.RefiningCopper.get(),3));
            add(new ItemStack(ModItems.WorldSoul3.get(),2));
        }});

        ForgeDrawRecipe.put(ModItems.GoldenShield.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.Boss2Piece.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),128));
            add(new ItemStack(ModItems.CompleteGem.get(),8));
            add(new ItemStack(ModItems.ReputationMedal.get(),8));
            add(new ItemStack(ModItems.RefiningGold.get(),1));
            add(new ItemStack(ModItems.WorldSoul3.get(),1));
        }});

        ForgeDrawRecipe.put(ModItems.DevilAttackChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.DevilAttackSoul.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),80));
            add(new ItemStack(ModItems.ReputationMedal.get(),80));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),4));
        }});

        ForgeDrawRecipe.put(ModItems.DevilSwiftBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.DevilSwiftSoul.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),80));
            add(new ItemStack(ModItems.ReputationMedal.get(),80));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),4));
        }});

        ForgeDrawRecipe.put(ModItems.DevilManaHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.DevilManaSoul.get(),256));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),80));
            add(new ItemStack(ModItems.ReputationMedal.get(),80));
            add(new ItemStack(ModItems.RefiningGold.get(),4));
            add(new ItemStack(ModItems.WorldSoul3.get(),4));
        }});

        ForgeDrawRecipe.put(ModItems.MoonShield.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ManaShield.get(),1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(),6));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),96));
            add(new ItemStack(ModItems.ReputationMedal.get(),96));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonKnife.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ManaKnife.get(),1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(),6));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),96));
            add(new ItemStack(ModItems.ReputationMedal.get(),96));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonBook.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.EarthBook.get(),1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(),6));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),96));
            add(new ItemStack(ModItems.ReputationMedal.get(),96));
            add(new ItemStack(ModItems.RefiningGold.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),12));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.MoonBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.MoonSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.NaturalCore.get(),16));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.MoonBelt.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),16));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),160));
            add(new ItemStack(ModItems.ReputationMedal.get(),160));
            add(new ItemStack(ModItems.RefiningGold.get(),8));
            add(new ItemStack(ModItems.WorldSoul3.get(),8));
        }});

        ForgeDrawRecipe.put(ModItems.TabooAttackLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ConstrainTaboo.get(),8));
            add(new ItemStack(ModItems.PurpleIronArmorLeggings.get(),1));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.TabooSwiftHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ConstrainTaboo.get(),8));
            add(new ItemStack(ModItems.PurpleIronArmorHelmet.get(),1));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.TabooManaBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.ConstrainTaboo.get(),8));
            add(new ItemStack(ModItems.PurpleIronArmorBoots.get(),1));
            add(new ItemStack(ModItems.GoldCoin.get(),320));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.MoonHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.MoonCompleteGem.get(),12));
            add(new ItemStack(ModItems.GoldCoin.get(),384));
            add(new ItemStack(ModItems.CompleteGem.get(),128));
            add(new ItemStack(ModItems.ReputationMedal.get(),128));
            add(new ItemStack(ModItems.RefiningIron.get(),6));
            add(new ItemStack(ModItems.RefiningCopper.get(),6));
            add(new ItemStack(ModItems.WorldSoul3.get(),6));
        }});

        ForgeDrawRecipe.put(ModItems.CastleAttackHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleAttackChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleAttackLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleAttackBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});

        ForgeDrawRecipe.put(ModItems.CastleSwiftHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSwiftChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSwiftLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSwiftBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});

        ForgeDrawRecipe.put(ModItems.CastleManaHelmet.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleManaChest.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleManaLeggings.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});
        ForgeDrawRecipe.put(ModItems.CastleManaBoots.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleArmorPiece.get(),128));
        }});

        ForgeDrawRecipe.put(ModItems.CastleSword.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleSwordPiece.get(),32));
        }});
        ForgeDrawRecipe.put(ModItems.CastleBow.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleBowPiece.get(),32));
        }});
        ForgeDrawRecipe.put(ModItems.CastleSceptre.get(),new ArrayList<>(){{
            add(new ItemStack(ModItems.CastleSceptrePiece.get(),32));
        }});

    }

    public static int PFSecKillCount = 0;
    public static int LakeSecKillCount = 0;
    public static int VolcanoSecKillCount = 0;

    public static Map<Item,String> SoulBagsMap = new HashMap<>();

    public static void SoulBagsInit() {
        SoulBagsMap.put(ModItems.PlainSoul.get(),StringUtils.PlainSoulCount);
        SoulBagsMap.put(ModItems.ForestSoul.get(),StringUtils.ForestSoulCount);
        SoulBagsMap.put(ModItems.LakeSoul.get(),StringUtils.LakeSoulCount);
        SoulBagsMap.put(ModItems.VolcanoSoul.get(),StringUtils.VolcanoSoulCount);
    }

    public static Map<Player,PlayerTeam> playerTeamMap = new HashMap<>();

    public static Map<Player,List<PlayerTeam>> TeamInvitePlayerMap = new HashMap<>();

    public static Map<Player,List<PlayerTeam>> PlayerRequestTeamMap = new HashMap<>();

    public static int[] ps = {3,6,10};

    public static List<Instance> instanceList = new ArrayList<>(){{
        add(new Instance(Component.literal("普莱尼").withStyle(Utils.styleOfPlain),
                Component.literal("普莱尼岛").withStyle(Utils.styleOfPlain),
                25,ps,1,null,false,null,
                0,0,new Vec3(347,70,1198), ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("唤雷塔").withStyle(Utils.styleOfLightingIsland),
                Component.literal("唤雷岛").withStyle(Utils.styleOfLightingIsland),
                30,ps,1,null,false,null,
                0,0,new Vec3(1430,84,566),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("Main1Boss").withStyle(Utils.styleOfSnow),
                Component.literal("玉山中心祭坛").withStyle(Utils.styleOfSnow),
                30,ps,1,null,false,null,
                0,0,new Vec3(-171,115.5,1424),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("下界征讨").withStyle(Utils.styleOfNether),
                Component.literal("下界").withStyle(Utils.styleOfNether),
                50,ps,1,null,false,null,
                0,0,new Vec3(2,80.5,249),ServerLevel.NETHER,0));

        add(new Instance(Component.literal("突见忍").withStyle(Utils.styleOfSakura),
                Component.literal("绯樱岛").withStyle(Utils.styleOfSakura),
                80,ps,1,null,false,null,
                0,0,new Vec3(1934,167.5,1047),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("冰霜骑士").withStyle(Utils.styleOfIce),
                Component.literal("极寒之地").withStyle(Utils.styleOfIce),
                80,ps,1,null,false,null,
                0,0,new Vec3(332,63,2325),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("旧世复生魔王").withStyle(Utils.styleOfBloodMana),
                Component.literal("封魔庭院").withStyle(Utils.styleOfSakura),
                110,ps,1,null,false,null,
                0,0,new Vec3(1906,112,1242),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("阿尔忒弥斯").withStyle(Utils.styleOfMoon1),
                Component.literal("尘月宫").withStyle(Utils.styleOfMoon1),
                120,ps,1,null,false,null,
                0,0,new Vec3(167,186,1622),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("新世禁法魔物").withStyle(Utils.styleOfBloodMana),
                Component.literal("绯樱岛").withStyle(Utils.styleOfSakura),
                120,ps,1,null,false,null,
                0,0,new Vec3(1431,72,1161),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("暗黑城堡 - 1层").withStyle(Utils.styleOfCastle),
                Component.literal("暗黑城堡").withStyle(Utils.styleOfCastle),
                140,ps,1,null,false,null,
                0,0,new Vec3(898,70,1033),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("暗黑城堡 - 2层").withStyle(Utils.styleOfCastle),
                Component.literal("暗黑城堡").withStyle(Utils.styleOfCastle),
                160,ps,1,null,false,null,
                0,0,new Vec3(787.5,84,1028.5),ServerLevel.OVERWORLD,0));

        add(new Instance(Component.literal("紫晶骑士").withStyle(Utils.styleOfPurpleIron),
                Component.literal("决战之巅 - 紫晶骑士").withStyle(Utils.styleOfPurpleIron),
                180,ps,1,null,false,null,
                0,0,new Vec3(2174.5,84,1196.5),ServerLevel.OVERWORLD,0));

    }};

    public static List<Style> levelStyleList = new ArrayList<>(){{
        add(Utils.styleOfPlain);
        add(Utils.styleOfMana);
        add(Utils.styleOfSakura);
        add(Utils.styleOfEntropy);
        add(Utils.styleOfWorld);
        add(Utils.styleOfPower);
        add(Utils.styleOfMoon1);
        add(Utils.styleOfPurpleIron);
    }};

    public static boolean NetherInstanceFlag = false;

    public static int[] instanceKillCount = new int[instanceList.size()];

    public static Map<Player,Integer> PlayerAttackRingMap = new HashMap<>();
    public static Map<Player,Integer> PlayerManaAttackRingMap = new HashMap<>();
    public static Map<Player,Integer> PlayerHealthRingMap = new HashMap<>();
    public static Map<Player,Integer> PlayerDefenceRingMap = new HashMap<>();

    public static Map<String,Item> ManaCoreMap = new HashMap<>();

    public static void setManaCoreMap() {
        ManaCoreMap.put(StringUtils.ManaCore.SeaCore, ModItems.SeaManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.BlackForestCore, ModItems.BlackForestManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.KazeCore, ModItems.KazeManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.SakuraCore, ModItems.SakuraCore.get());

    }

    public static Map<Player,Integer> VolcanoTpCounts = new HashMap<>();

    public static Map<Player, BlockEntity> playerBlockEntityMap = new HashMap<>();

    public static Map<Player, Boolean> playerSakuraBowMap = new HashMap<>();

    public static Map<Player, Boolean> playerSakuraCoreMap = new HashMap<>();

    public static Map<Item,Integer> forgeNumMap = new HashMap<>();

    public static List<Item> ForgedItemList = new ArrayList<>();

    public static void setForgedItemList () {
        ForgedItemList.add(ModItems.SakuraDemonSword.get());
        ForgedItemList.add(ModItems.IslandArmorHelmet.get());
        ForgedItemList.add(ModItems.IslandArmorChest.get());
        ForgedItemList.add(ModItems.IslandArmorLeggings.get());
        ForgedItemList.add(ModItems.IslandArmorBoots.get());
        ForgedItemList.add(ModItems.SkyArmorHelmet.get());
        ForgedItemList.add(ModItems.SkyArmorChest.get());
        ForgedItemList.add(ModItems.SkyArmorLeggings.get());
        ForgedItemList.add(ModItems.SkyArmorBoots.get());
        ForgedItemList.add(ModItems.SeaSword0.get());
        ForgedItemList.add(ModItems.BlackForestSword0.get());
        ForgedItemList.add(ModItems.NetherArmorHelmet.get());
        ForgedItemList.add(ModItems.NetherArmorChest.get());
        ForgedItemList.add(ModItems.NetherArmorLeggings.get());
        ForgedItemList.add(ModItems.NetherArmorBoots.get());
        ForgedItemList.add(ModItems.KazeSword0.get());
        ForgedItemList.add(ModItems.KazeBoots.get());
        ForgedItemList.add(ModItems.ForestBossSword.get());
        ForgedItemList.add(ModItems.VolcanoBossSword.get());
        ForgedItemList.add(ModItems.LakeBossSword.get());
        ForgedItemList.add(ModItems.SkyBossBow.get());
        ForgedItemList.add(ModItems.SnowBossArmorChest.get());
        ForgedItemList.add(ModItems.SeaManaCore.get());
        ForgedItemList.add(ModItems.BlackForestManaCore.get());
        ForgedItemList.add(ModItems.KazeManaCore.get());
        ForgedItemList.add(ModItems.SakuraBow.get());
        ForgedItemList.add(ModItems.SakuraCore.get());
        ForgedItemList.add(ModItems.MinePants.get());
        ForgedItemList.add(ModItems.IceArmorHelmet.get());
        ForgedItemList.add(ModItems.IceArmorChest.get());
        ForgedItemList.add(ModItems.IceArmorLeggings.get());
        ForgedItemList.add(ModItems.IceArmorBoots.get());
        ForgedItemList.add(ModItems.SeaBow.get());
        ForgedItemList.add(ModItems.IceSword.get());
        ForgedItemList.add(ModItems.IceBow.get());
        ForgedItemList.add(ModItems.IceSceptre.get());
        ForgedItemList.add(ModItems.ShipSword.get());
        ForgedItemList.add(ModItems.ShipBow.get());
        ForgedItemList.add(ModItems.ShipSceptre.get());
        ForgedItemList.add(ModItems.NetherManaArmorHelmet.get());
        ForgedItemList.add(ModItems.NetherManaArmorChest.get());
        ForgedItemList.add(ModItems.NetherManaArmorLeggings.get());
        ForgedItemList.add(ModItems.NetherManaArmorBoots.get());
        ForgedItemList.add(ModItems.NetherSceptre.get());
        ForgedItemList.add(ModItems.SpringAttackArmorHelmet.get());
        ForgedItemList.add(ModItems.SpringAttackArmorChest.get());
        ForgedItemList.add(ModItems.SpringAttackArmorLeggings.get());
        ForgedItemList.add(ModItems.SpringAttackArmorBoots.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorHelmet.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorChest.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorLeggings.get());
        ForgedItemList.add(ModItems.SpringSwiftArmorBoots.get());
        ForgedItemList.add(ModItems.SpringManaArmorHelmet.get());
        ForgedItemList.add(ModItems.SpringManaArmorChest.get());
        ForgedItemList.add(ModItems.SpringManaArmorLeggings.get());
        ForgedItemList.add(ModItems.SpringManaArmorBoots.get());
        ForgedItemList.add(ModItems.GoldenShield.get());
        ForgedItemList.add(ModItems.DevilAttackChest.get());
        ForgedItemList.add(ModItems.DevilSwiftBoots.get());
        ForgedItemList.add(ModItems.DevilManaHelmet.get());
        ForgedItemList.add(ModItems.MoonShield.get());
        ForgedItemList.add(ModItems.MoonKnife.get());
        ForgedItemList.add(ModItems.MoonBook.get());
        ForgedItemList.add(ModItems.MoonLeggings.get());
        ForgedItemList.add(ModItems.MoonSword.get());
        ForgedItemList.add(ModItems.MoonBow.get());
        ForgedItemList.add(ModItems.MoonSceptre.get());
        ForgedItemList.add(ModItems.MoonBelt.get());
        ForgedItemList.add(ModItems.TabooAttackLeggings.get());
        ForgedItemList.add(ModItems.TabooSwiftHelmet.get());
        ForgedItemList.add(ModItems.TabooManaBoots.get());
        ForgedItemList.add(ModItems.MoonHelmet.get());
        ForgedItemList.add(ModItems.CastleAttackHelmet.get());
        ForgedItemList.add(ModItems.CastleAttackChest.get());
        ForgedItemList.add(ModItems.CastleAttackLeggings.get());
        ForgedItemList.add(ModItems.CastleAttackBoots.get());
        ForgedItemList.add(ModItems.CastleSwiftHelmet.get());
        ForgedItemList.add(ModItems.CastleSwiftChest.get());
        ForgedItemList.add(ModItems.CastleSwiftLeggings.get());
        ForgedItemList.add(ModItems.CastleSwiftBoots.get());
        ForgedItemList.add(ModItems.CastleManaHelmet.get());
        ForgedItemList.add(ModItems.CastleManaChest.get());
        ForgedItemList.add(ModItems.CastleManaLeggings.get());
        ForgedItemList.add(ModItems.CastleManaBoots.get());
        ForgedItemList.add(ModItems.CastleSword.get());
        ForgedItemList.add(ModItems.CastleBow.get());
        ForgedItemList.add(ModItems.CastleSceptre.get());
    }

    public static Map<Player,Integer> playerClickBlockCoolDown = new HashMap<>();

    public static List<BlockSP> playerDigPos = new ArrayList<>();

    public static int playerDigCount = 0;

    public static Map<Player, Integer> deleteCommandSecurity = new HashMap<>();

    public static Map<String, ItemStack> playerDailyMissionContent = new HashMap<>();
    public static Map<String, Integer> playerDailyMissionContentNum = new HashMap<>();

    public static Map<String, ItemStack> playerReputationMissionContent = new HashMap<>();
    public static Map<String, Integer> playerReputationMissionContentNum = new HashMap<>();
    public static Map<String, String> playerReputationMissionStartTime = new HashMap<>();
    public static Map<String, Integer> playerReputationMissionPunishLevel = new HashMap<>();
    public static int[] playerReputationMissionPunishTime = {
            0,3,10,30,60,60 * 2,60 * 4,60 * 8,60 * 12,60 * 24
    };
    public static Map<String, String> playerReputationMissionAllowRequestTime = new HashMap<>();

    public static List<ItemStack> MissionItemList = new ArrayList<>();
    public static void setMissionItemList() {
        MissionItemList.add(ModItems.SilverCoin.get().getDefaultInstance());
        MissionItemList.add(ModItems.PlainSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.ForestSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.LakeSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.VolcanoSoul.get().getDefaultInstance()); // 4
        MissionItemList.add(ModItems.MineSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.SnowSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.SkySoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.EvokerSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.SeaSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.BlackForestSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.LightningSoul.get().getDefaultInstance()); // 11
        MissionItemList.add(ModItems.witherBone.get().getDefaultInstance());
        MissionItemList.add(ModItems.NetherMagmaPower.get().getDefaultInstance());
        MissionItemList.add(ModItems.PigLinSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.netherbonemeal.get().getDefaultInstance()); // 15
        MissionItemList.add(ModItems.KazeSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.SpiderSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.PurpleIronPiece.get().getDefaultInstance()); // 18
        MissionItemList.add(ModItems.ShipPiece.get().getDefaultInstance()); // 19
        MissionItemList.add(ModItems.SlimeBall.get().getDefaultInstance());
        MissionItemList.add(ModItems.BeaconSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.BlazeSoul.get().getDefaultInstance());
        MissionItemList.add(ModItems.TreeSoul.get().getDefaultInstance()); // 23

    }

    public static List<Item> ReputationStoreItemList = new ArrayList<>();
    public static void setReputationStoreItemList() {
        ReputationStoreItemList.add(ModItems.ReputationMedal.get());
        ReputationStoreItemList.add(ModItems.Ps_Bottle0.get());
        ReputationStoreItemList.add(ModItems.Ps_Bottle1.get());
        ReputationStoreItemList.add(ModItems.Ps_Bottle2.get());
        ReputationStoreItemList.add(ModItems.GoldCoinBag.get());
        ReputationStoreItemList.add(ModItems.CommonLotteries.get());
        ReputationStoreItemList.add(ModItems.UnCommonLotteries.get());
    }

    public static Map<Item, Integer> ReputationStorePrice = new HashMap<>();
    public static void setReputationStorePrice() {
        ReputationStorePrice.put(ModItems.ReputationMedal.get(),64);
        ReputationStorePrice.put(ModItems.Ps_Bottle0.get(),40);
        ReputationStorePrice.put(ModItems.Ps_Bottle1.get(),100);
        ReputationStorePrice.put(ModItems.Ps_Bottle2.get(),200);
        ReputationStorePrice.put(ModItems.GoldCoinBag.get(),256);
        ReputationStorePrice.put(ModItems.CommonLotteries.get(),256);
        ReputationStorePrice.put(ModItems.UnCommonLotteries.get(),512);
    }

    public static Map<BlockPos, String> whoIsUsingBlock = new HashMap<>();

    public static List<Block> canBeDigBlockList = new ArrayList<>(){{
        Block[] blocks = {
                Blocks.DIRT,
                Blocks.COBBLESTONE,
                Blocks.STONE,
                Blocks.GRAVEL,
                Blocks.SMOOTH_BASALT,
                Blocks.TUFF,
                Blocks.DIORITE,
                Blocks.GRANITE,
                Blocks.ANDESITE,
                Blocks.DEEPSLATE,
                Blocks.COBBLED_DEEPSLATE,
                Blocks.SCULK,
                Blocks.COAL_ORE,
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.RAW_COPPER_BLOCK,
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.RAW_IRON_BLOCK,
                Blocks.LAPIS_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.REDSTONE_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.RAW_GOLD_BLOCK,
                Blocks.DIAMOND_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.EMERALD_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE
        };
        this.addAll(Arrays.asList(blocks));
    }};

    public static Map<Block,Double> mineExpMap = new HashMap<>(){{
        put(Blocks.COAL_ORE,0.01);
        put(Blocks.DEEPSLATE_COAL_ORE,0.01);
        put(Blocks.COPPER_ORE,0.02);
        put(Blocks.DEEPSLATE_COPPER_ORE,0.02);
        put(Blocks.RAW_COPPER_BLOCK,0.02);
        put(Blocks.IRON_ORE,0.03);
        put(Blocks.DEEPSLATE_IRON_ORE,0.03);
        put(Blocks.RAW_IRON_BLOCK,0.03);
        put(Blocks.LAPIS_ORE,0.03);
        put(Blocks.DEEPSLATE_LAPIS_ORE,0.03);
        put(Blocks.REDSTONE_ORE,0.03);
        put(Blocks.DEEPSLATE_REDSTONE_ORE,0.03);
        put(Blocks.GOLD_ORE,0.04);
        put(Blocks.DEEPSLATE_GOLD_ORE,0.04);
        put(Blocks.RAW_GOLD_BLOCK,0.04);
        put(Blocks.DIAMOND_ORE,0.05);
        put(Blocks.DEEPSLATE_DIAMOND_ORE,0.05);
        put(Blocks.EMERALD_ORE,0.05);
        put(Blocks.DEEPSLATE_EMERALD_ORE,0.05);
        put(Blocks.AMETHYST_BLOCK,0.01);
    }};

    public static Map<Block,Item> mineDropMap = new HashMap<>(){{
        put(Blocks.COAL_ORE,Items.COAL);
        put(Blocks.DEEPSLATE_COAL_ORE,Items.COAL);
        put(Blocks.COPPER_ORE,Items.RAW_COPPER);
        put(Blocks.DEEPSLATE_COPPER_ORE,Items.RAW_COPPER);
        put(Blocks.RAW_COPPER_BLOCK,Items.RAW_COPPER);
        put(Blocks.IRON_ORE,Items.RAW_IRON);
        put(Blocks.DEEPSLATE_IRON_ORE,Items.RAW_IRON);
        put(Blocks.RAW_IRON_BLOCK,Items.RAW_IRON);
        put(Blocks.LAPIS_ORE,Items.LAPIS_LAZULI);
        put(Blocks.DEEPSLATE_LAPIS_ORE,Items.LAPIS_LAZULI);
        put(Blocks.REDSTONE_ORE,Items.REDSTONE);
        put(Blocks.DEEPSLATE_REDSTONE_ORE,Items.REDSTONE);
        put(Blocks.GOLD_ORE,Items.RAW_GOLD);
        put(Blocks.DEEPSLATE_GOLD_ORE,Items.RAW_GOLD);
        put(Blocks.RAW_GOLD_BLOCK,Items.RAW_GOLD);
        put(Blocks.DIAMOND_ORE,Items.DIAMOND);
        put(Blocks.DEEPSLATE_DIAMOND_ORE,Items.DIAMOND);
        put(Blocks.EMERALD_ORE,Items.EMERALD);
        put(Blocks.DEEPSLATE_EMERALD_ORE,Items.EMERALD);
    }};

    public static Map<Player,Queue<BlockAndResetTime>> noMineDigMap = new HashMap<>();
    public static Map<Player,Queue<BlockAndResetTime>> blockPlaceMap = new HashMap<>();

    public static List<BlockPos> posEvenBeenDigOrPlace = new ArrayList<>();

    public static Queue<BlockAndResetTime> worldMineList = new LinkedList<>();

    public static Queue<ItemEntityAndResetTime> valueItemEntity = new LinkedList<>();

    public static Queue<BlockAndResetTime> worldWoodList = new LinkedList<>();

    public static Map<String, Component> playerNameMap = new HashMap<>();

    public static Map<String, Integer> dayKillCount = new HashMap<>();
    public static List<PlayerNameAndCount> dayKillCountList = new ArrayList<>();
    public static List<ArmorStand> dayKillCountDisplayList = new ArrayList<>();

    public static Map<String, Double> dayVBCount = new HashMap<>();
    public static Map<String, Integer> dayInstanceFinishedTime = new HashMap<>();

    public static Map<String, Integer> dayFishCount = new HashMap<>();
    public static List<PlayerNameAndCount> dayFishCountList = new ArrayList<>();
    public static List<ArmorStand> dayFishCountDisplayList = new ArrayList<>();

    public static Map<String, Integer> dayMineCount = new HashMap<>();
    public static List<PlayerNameAndCount> dayMineCountList = new ArrayList<>();
    public static List<ArmorStand> dayMineCountDisplayList = new ArrayList<>();

    public static Map<String, Integer> dayLopCount = new HashMap<>();
    public static List<PlayerNameAndCount> dayLopCountList = new ArrayList<>();
    public static List<ArmorStand> dayLopCountDisplayList = new ArrayList<>();

    public static Map<String, Integer> dayCropCount = new HashMap<>();
    public static List<PlayerNameAndCount> dayCropCountList = new ArrayList<>();
    public static List<ArmorStand> dayCropCountDisplayList = new ArrayList<>();

    public static Map<String, Integer> dayOnlineCount = new HashMap<>();
    public static List<PlayerNameAndCount> dayOnlineCountList = new ArrayList<>();
    public static List<ArmorStand> dayOnlineCountDisplayList = new ArrayList<>();

    public static Calendar countBeginToRecordTime = null;
    public static ArmorStand recordTimeArmorStand = null;
    public static ArmorStand recordTimeArmorStand1 = null;

    public static List<BlockPos> rewardChestPos = new ArrayList<>(){{
        add(new BlockPos(393,62,949));
        add(new BlockPos(386,63,924));
        add(new BlockPos(356,72,870));
        add(new BlockPos(344,61,957));
        add(new BlockPos(312,63,977));
        add(new BlockPos(227,69,920));
        add(new BlockPos(285,62,993));
        add(new BlockPos(234,72,1061));
        add(new BlockPos(302,65,1104));
        add(new BlockPos(221,107,1088));
        add(new BlockPos(255,104,1208));
        add(new BlockPos(266,136,1262));
        // 以上为平原地区奖励箱

        add(new BlockPos(105,121,1166));
        add(new BlockPos(152,87,1114));
        add(new BlockPos(125,69,1060));
        add(new BlockPos(174,75,1018));
        add(new BlockPos(141,71,954));
        add(new BlockPos(36,63,929));
        add(new BlockPos(-28,77,925));
        add(new BlockPos(-64,80,955));
        add(new BlockPos(-96,96,968));
        // 以上为森林地区奖励箱

        add(new BlockPos(36,56,932));
        add(new BlockPos(30,19,1019));
        add(new BlockPos(77,-20,979));
        add(new BlockPos(48,-45,982));
        add(new BlockPos(-6,-53,1012));
        // 以上为湖泊地区奖励箱

        add(new BlockPos(33,-48,1012));
        add(new BlockPos(15,-54,1028));
        add(new BlockPos(-20,-54,1070));
        add(new BlockPos(8,-54,1095));
        add(new BlockPos(38,-52,1116));
        add(new BlockPos(51,-49,1081));
        // 以上为火山地区奖励箱

        add(new BlockPos(113,109,1039));
        add(new BlockPos(77,115,1039));
        add(new BlockPos(76,116,1028));
        add(new BlockPos(43,113,1051));
        add(new BlockPos(51,118,1073));
        add(new BlockPos(34,128,1053));
        add(new BlockPos(20,154,1072));
        add(new BlockPos(15,119,1047));
        add(new BlockPos(31,117,1032));
        add(new BlockPos(44,118,1010));
        add(new BlockPos(19,118,1063));
        add(new BlockPos(57,126,1044));
        // 以上为天空城地区奖励箱
    }};

    public static Map<String,Map<BlockPos,Calendar>> playerRewardChestCoolDown = new HashMap<>();

    public static Map<String,BlockPos> playerIsUsingBlockBlockPosMap = new HashMap<>();

    public static List<ItemStack> rewardItemList = new ArrayList<>();

    public static void setRewardItemList() {
        rewardItemList.add(new ItemStack(ModItems.Ps_Bottle0.get(),1));
        rewardItemList.add(new ItemStack(ModItems.CrudeCoal.get(),16));
        rewardItemList.add(new ItemStack(ModItems.ForgingStone1.get(),1));
        rewardItemList.add(new ItemStack(ModItems.RevelationBook.get(),1));
        rewardItemList.add(new ItemStack(ModItems.SilverCoin.get(),32));
        rewardItemList.add(new ItemStack(ModItems.GemBag.get(),1));
        rewardItemList.add(new ItemStack(ModItems.TpToSky.get(),1));
        rewardItemList.add(new ItemStack(ModItems.TpToSakura.get(),1));
        rewardItemList.add(new ItemStack(ModItems.WorldSoul2.get(),1));
    }

    public static int TimeEventFlag = -1;

    public static List<Recall> recallList = new ArrayList<>(){{
        add(HuskRecall);
        add(ForestRecall);
        add(KazeRecall);
        add(LightningRecall);
        add(NetherRecall);
        add(SeaRecall);
        add(SnowRecall);
        add(SpiderRecall);
        add(VolcanoRecall);
    }};

    public static Map<Player,Integer> MineShieldEffect = new HashMap<>();

    public static Map<Player,Integer> ShipSwordTime = new HashMap<>();
    public static Map<Player,Integer> ShipSwordEffect = new HashMap<>();
    public static Map<Player,Integer> ShipSceptreWaterBlockNum = new HashMap<>();

    public static Map<Player,Integer> SakuraBowEffectMap = new HashMap<>();

    public static Map<Player,Integer> IceSwordEffectMap = new HashMap<>();
    public static Map<Player,Double> IceSwordEffectNumMap = new HashMap<>();
    public static Map<Player,Integer> IceBowEffectMap = new HashMap<>();
    public static Map<Player,Double> IceBowEffectNumMap = new HashMap<>();
    public static Map<Player,Integer> IceSceptreEffectMap = new HashMap<>();
    public static Map<Player,Double> IceSceptreEffectNumMap = new HashMap<>();

    public static Map<Player,Integer> DingCoolDown = new HashMap<>();
    public static Map<Player,Integer> DingDingCoolDown = new HashMap<>();

    public static Map<Player,Double> PlayerSpringRingAttackAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringRingManaAttackAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringRingDefencePenetration0Attribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringRingManaPenetration0Attribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringRingExpUpAttribute = new HashMap<>();
    public static Map<Player,Integer> PlayerSpringRingLevelRequire = new HashMap<>();

    public static Map<Player,Double> PlayerSpringHandAttackAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringHandDefenceAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringHandMaxHealthAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringHandDefencePenetraionAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringHandExpUpAttribute = new HashMap<>();
    public static Map<Player,Integer> PlayerSpringHandLevelRequire = new HashMap<>();

    public static Map<Player,Double> PlayerSpringBeltAttackAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBeltDefencePenetration0Attribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBeltSwiftAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBeltMovementAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBeltExpUpAttribute = new HashMap<>();
    public static Map<Player,Integer> PlayerSpringBeltLevelRequire = new HashMap<>();

    public static Map<Player,Double> PlayerSpringBraceletAttackAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBraceletManaPenetration0Attribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBraceletManaRecoverAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBraceletMaxManaAttribute = new HashMap<>();
    public static Map<Player,Double> PlayerSpringBraceletExpUpAttribute = new HashMap<>();
    public static Map<Player,Integer> PlayerSpringBraceletLevelRequire = new HashMap<>();

    public static Map<Player,Integer> PlayerAttackTime = new HashMap<>();
    public static Map<Player,Integer> PlayerArrowAttackTime = new HashMap<>();
    public static Map<Player,Integer> PlayerManaAttackTime = new HashMap<>();

    public static Map<Player,Integer> PlayerFireWorkGunEffect = new HashMap<>();

    public static List<PlayerTeam> ChallengingPlayerTeam = new ArrayList<>();

    public static Map<Player,Boolean> PlayerAFKMap = new HashMap<>();
    public static Map<Player,Integer> PlayerAFKSecondsMap = new HashMap<>();

    public static Map<Player,Integer> PlayerFireWorkFightCoolDown = new HashMap<>();

    public static Map<Player,Integer> PlayerSpringAttackCoolDown = new HashMap<>();
    public static Map<Mob,Integer> MobSpringAttackTick = new HashMap<>();
    public static Map<Mob,Integer> MobSpringAttackEffect = new HashMap<>();
    public static Map<Player,Integer> PlayerSpringSwiftCoolDown = new HashMap<>();
    public static Map<Mob,Integer> MobSpringSwiftTick = new HashMap<>();
    public static Map<Mob,Integer> MobSpringSwiftEffect = new HashMap<>();
    public static Map<Player,Integer> PlayerSpringManaCoolDown = new HashMap<>();
    public static Map<Mob,Integer> MobSpringManaTick = new HashMap<>();
    public static Map<Mob,Integer> MobSpringManaEffect = new HashMap<>();

    public static double[] SpringEffect = {
            0.2,0.25,0.3,0.4
    };

    public static Map<Player,Integer> SpringScaleTime = new HashMap<>();
    public static Map<Player,Integer> SpringScaleEffect = new HashMap<>();

    public static Map<Player,Boolean> EndRune0Effect = new HashMap<>();
    public static Map<Player,Integer> EndRune0CoolDown = new HashMap<>();

    public static Map<Player,Integer> EndRune1CoolDown = new HashMap<>();

    public static Map<Player,Integer> EndRune2CoolDown = new HashMap<>();
    public static Map<Player,PosAndLastTime> EndRune2Pos = new HashMap<>();

    public static Player LengXuePlayer;
    public static boolean LengXueAttackFlag = false;
    public static Mob LengXueMob;
    public static int LengXueMobCount;

    public static Vec3 LengXueVec3;
    public static int LengXueCounts = -1;

    public static Map<Mob,Integer> NetherBoneMealPowerEffectMap = new HashMap<>();

    public static Player ShangMengLi;
    public static boolean ShangMengLiManaCount = false;
    public static int ShangMengLiDoubleManaAttackDelay = -1;
    public static int ShangMengLiCounts = 0;
    public static int ShangMengLiCountsLastTick = 0;

    public static int ShangMengLiManaSkillDelay = 0;
    public static int ShangMengLiSkillCoolDown = 0;
    public static int ShangMengLiActiveCounts = 0;
    public static int ShangMengLiActiveTick = 0;
    public static Map<Mob,Integer> ShangMengLiSkillEffectMob = new HashMap<>();

    public static Player YiZhiXiangLi;
    public static int YiZhiXiangLiEffect = 0;

    public static Player Crush1;
    public static boolean Crush1ZeusIsOn = false;
    public static int Crush1SkillTickCount = 0;
    public static int Crush1CritDamage1Tick = 0;
    public static Map<Mob,Integer> CrushDefenceDecreaseMap = new HashMap<>();
    public static int Crush1CritDamage2Count = 0;
    public static int Crush1CritDamage2Tick = 0;
    public static int CrushCuriosCounts = 0;
    public static int CrushCuriosLastTick = 0;

    public static boolean ShangMengLiCore = false;
    public static boolean ShangMengLiCore1 = false;
    public static boolean ShangMengLiCore2 = false;
    public static int ShangMengLiCore1Count = 0;
    public static int ShangMengLiCore1Temperature = 0;
    public static boolean ShangMengLiCore1TemperatureShoot = false;

    public static Player LiuLiXian;
    public static boolean LiuLiXianCore = false;
    public static int LiuLiXianManaAttackDelay = -1;
    public static int LiuLiXianCounts = 0;
    public static int LiuLiXianCountsLastTick = 0;
    public static boolean LiuLiXianManaFlag = false;
    public static Map<Player,Integer> LiuLiXianPlayerEffectMap = new HashMap<>();
    public static Map<Player,Integer> LiuLiXianPlayerEffectTickMap = new HashMap<>();
    public static int LiuLiXianTick = 0;

    public static Player VeryNew;
    public static boolean VeryNewCurios = false;
    public static int VeryNewManaCount = 0;
    public static int VeryNewManaDalay = -1;
    public static Vec3 VeryNewManaPos;

    public static Player Fengxiaoju;
    public static boolean FengxiaojuCurios = false;
    public static Map<Mob,List<CalculateDamage>> FengxiaojuPassiveMap = new HashMap<>();
    public static int FengxiaojuAttackCount = 0;
    public static Map<Mob,Boolean> FengxiaojuPassiveEffecetMap = new HashMap<>();
    public static int FengxiaojuPassiveCount = 0;
    public static int FengxiaojuPassiveTick = 0;

    public static boolean XiangliCurios = false;
    public static Player Xiangli;

    public static Map<Player,Integer> PiglinPowerLastTick = new HashMap<>();
    public static Map<Player,Integer> PiglinPowerNum = new HashMap<>();
    public static Map<Player,Double> PiglinPowerAp = new HashMap<>();

    public static Map<Player,Boolean> playerRecycleMap = new HashMap<>();

    public static List<String> GiantPlayerList = new ArrayList<>();
    public static int GiantHour = -1;
    public static Giant giant;
    public static List<Boss2Damage> GiantDamageList = new ArrayList<>();
    public static List<ItemStack> GiantCommonReward = new ArrayList<>();
    public static boolean GiantFlag = false;
    public static void setGiantCommonReward() {
        GiantCommonReward.add(new ItemStack(ModItems.CrudeCoal.get(),64));
        GiantCommonReward.add(new ItemStack(ModItems.GemPiece.get(),32));
        GiantCommonReward.add(new ItemStack(ModItems.GoldCoin.get(),10));
        GiantCommonReward.add(new ItemStack(ModItems.Ps_Bottle1.get(),2));
        GiantCommonReward.add(new ItemStack(ModItems.CropBag.get(),2));
    }

    public static Player ShowDicker;
    public static int ShowDickerCount;
    public static int ShowDickerArrowCount;

    public static Map<Player,Boolean> SoulSwordMap = new HashMap<>();

    public static Map<Player,Boolean> BloodManaCurios = new HashMap<>();
    public static Map<Player,Boolean> EarthManaCurios = new HashMap<>();

    public static Map<Player,Boolean> DevilBloodManaCurios = new HashMap<>();
    public static Map<Player,Boolean> DevilEarthManaCurios = new HashMap<>();

    public static Mob CrushMob;

    public static Map<String,Integer> PlayerDeadTimeMap = new HashMap<>();

    public static List<LastDamage> PlayerLastDamageToMonster = new ArrayList<>();
    public static List<LastDamage> PlayerLastXpStrengthDamageToMonster = new ArrayList<>();

    public static Map<Player,Integer> MeteoriteDefenceMap = new HashMap<>();
    public static Map<Player,Integer> MeteoriteDefenceTimeMap = new HashMap<>();

    public static Map<Player,Integer> MeteoriteAttackMap = new HashMap<>();
    public static Map<Player,Integer> MeteoriteAttackTimeMap = new HashMap<>();

    public static Map<Player,Integer> MeteoritePenetrationMap = new HashMap<>();
    public static Map<Player,Integer> MeteoritePenetrationTimeMap = new HashMap<>();

    public static Map<Player,Integer> PlayerSoulSceptreCoolDown = new HashMap<>();

    public static boolean GuangYiIsInMode = false;
    public static int GuangYiSecondArrowDelay = -1;
    public static int GuangYiModeStartTick = 0;
    public static boolean GuangYiArrowFlag = false;
    public static Map<Mob,Integer> GuangYiMobDefenceDecreaseMap = new HashMap<>();
    public static Map<Mob,Integer> GuangYiMobDefenceDecreaseTime = new HashMap<>();

    public static Map<Mob,Integer> SnowShieldMobEffectMap = new HashMap<>();
    public static Map<Player,Double> SnowShieldPlayerEffectMap = new HashMap<>();
    public static Map<Player,Integer> SnowShieldPlayerEffectTickMap = new HashMap<>();

    public static Map<Player,Double> NetherShieldPlayerDefenceEnhanceMap = new HashMap<>();
    public static Map<Player,Integer> NetherShieldPlayerDefenceEnhanceTickMap = new HashMap<>();

    public static boolean WcBowStatus = false;
    public static int WcBowCount = 0;
    public static int WcBowTick = 0;

    public static Map<Mob,Integer> WitherBookMobEffectTick = new HashMap<>();
    public static Map<Player,Integer> WitherBookPlayerEffectTick = new HashMap<>();
    public static Map<Player,Double> WitherBookPlayerEffectNum = new HashMap<>();

    public static Map<Player,Queue<Vec3>> EarthBookPlayerPosMap = new HashMap<>();
    public static Map<Player,Integer> EarthBookPlayerEffectMap = new HashMap<>();

    public static Map<Player,Integer> IceBookCoolDownMap = new HashMap<>();
    public static Map<Mob,Integer> IceBookMobEffectTickMap = new HashMap<>();
    public static Map<Mob,Player> IceBookMobEffectPlayerMap = new HashMap<>();

    public static List<Item> WeaponList = new ArrayList<>();
    public static List<Item> ArmorList = new ArrayList<>();
    public static List<Item> CuriosList = new ArrayList<>();
    public static List<Item> CustomizedList = new ArrayList<>();

    public static Map<Player,List<ItemStack>> playerCuriosListMap = new HashMap<>();

    public static Map<Player,Integer> BlackForestSwordActiveMap = new HashMap<>();
    public static Map<Player,Integer> SeaSwordActiveMap = new HashMap<>();

    public static Map<Player,Integer> VolcanoRune2Map = new HashMap<>();

    public static Map<Player,Map<Mob,Integer>> playerLaserCoolDown = new HashMap<>();

    public static ServerBossEvent GiantBossInfo = null;

    public static Map<Player,Integer> LastTimeInstance = new HashMap<>();
    public static Map<Player,Integer> LastTimeDifficulty = new HashMap<>();


}
