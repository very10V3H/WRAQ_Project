package com.Very.very.ValueAndTools.Utils;

import com.Very.very.Entity.SakuraMob;
import com.Very.very.Series.EndSeries.Recall;
import com.Very.very.Files.ConfigTest;
import com.Very.very.Files.MarketItemInfo;
import com.Very.very.Files.MarketPlayerInfo;
import com.Very.very.Items.Prefix.PrefixInfo;
import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.ValueAndTools.Utils.Struct.*;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.BowSkillStruct.BowSkill6;
import com.Very.very.ValueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill6;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill13;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.SwordSkillStruct.SwordSkill6;
import com.Very.very.Render.Effects.ModEffects;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class Utils {
    public static final String MOD_ID = "vmd";
    public static Map<Item,Float> HealSteal = new HashMap();
    public static Map<Item,Float> BaseDamage = new HashMap();
    public static Map<Item,Float> CriticalHitRate = new HashMap<>();
    public static Map<Item,Float> CHitDamage = new HashMap<>();
    public static Map<Item,Float> SpeedUp = new HashMap<>();
    public static Map<Item,Float> Defence = new HashMap<>();
    public static Map<Item,Float> AttackRangeUp = new HashMap<>();
    public static Map<Item,Float> BreakDefence0 = new HashMap<>();
    public static Map<Item,Float> ExpUp = new HashMap<>();
    public static Map<Item,Float> HealUp = new HashMap<>();
    public static Map<Item,Float> HealReply = new HashMap<>();
    public static Map<Item,Float> HealEffectUp = new HashMap<>();
    public static Map<Item,Float> CoolDownDecrease = new HashMap<>();
    public static Map<Item,Float> BreakDefence = new HashMap<>();
    public static Map<Item,Float> ManaDamage = new HashMap<>();
    public static Map<Item,Float> ManaBreakDefence = new HashMap<>();
    public static Map<Item,Float> ManaBreakDefence0 = new HashMap<>();
    public static Map<Item,Float> ManaReply = new HashMap<>();
    public static Map<Item,Float> ManaDefence = new HashMap<>();
    public static Map<Item,Float> ManaUp = new HashMap<>();
    public static Map<Item,Float> ManaHealSteal = new HashMap<>();
    public static Map<Item,Float> LuckyUp = new HashMap<>();
    public static Map<Item,Float> MainHandTag = new HashMap<>();
    public static Map<Item,Float> SwordTag = new HashMap<>();
    public static Map<Item,Float> BowTag = new HashMap<>();
    public static Map<Item,Float> SceptreTag = new HashMap<>();
    public static Map<Item,Float> OffHandTag = new HashMap<>();
    public static Map<Item,Float> ArmorTag = new HashMap<>();
    public static Map<Item,Float> MobLevel = new HashMap<>();
    public static int Count = -1;
    public static Map <String, Float> GemsAttackDamage = new HashMap <String, Float>();
    public static Map <String, Float> GemsSpeedUp = new HashMap <String, Float>();
    public static Map <String, Float> GemsManaDamage = new HashMap<>();
    public static Map <String, Float> GemsManaRecover = new HashMap<>();
    public static Map <String, Float> GemsHealReply = new HashMap<>();
    public static Map <String, Float> GemsHealUp = new HashMap<>();
    public static Map <String, Float> GemsDefence = new HashMap<>();
    public static Map <String, Float> GemsCoolDown = new HashMap<>();
    public static Map <String, Float> GemsCritDamage = new HashMap<>();
    public static Map <String, Float> GemsCritRate = new HashMap<>();
    public static Queue <BlockPos> blockPosQueue = new LinkedList<BlockPos>();
    public static Queue <BlockPos> netherBlockPosQueue = new LinkedList<BlockPos>();
    public static Queue <BlockPos> blockPosBreakQueue = new LinkedList<BlockPos>();
    public static Queue <BlockState> blockStateQueue = new LinkedList<BlockState>();
    public static Queue <BlockPos> netherBlockPosBreakQueue = new LinkedList<BlockPos>();
    public static Queue <BlockState> netherBlockStateQueue = new LinkedList<BlockState>();
    public static Queue <LivingEntity> MonsterAttributeDataProvider = new LinkedList<>();
    public static int AttributeDataTick = 0;
    public static Map <Player, Queue<Vec3>> FeiLeiShenMap = new HashMap<Player, Queue<Vec3>>();
    public static Entity EntityCopy;
    public static Map <Item, Boolean> ItemCheck = new HashMap<Item, Boolean>();
    public static Map <BlockPos, Player> BlockLimit = new HashMap<>();
    public static Queue <PlayerCallBack> playerCallBackQueue = new LinkedList<>();
    public static void ItemCheckInit()
    {
        Utils.ItemCheck.put(Items.PRISMARINE_BRICK_SLAB,true);
        Utils.ItemCheck.put(Items.SMITHING_TABLE,true);
        Utils.ItemCheck.put(Items.POLISHED_BLACKSTONE_SLAB,true);
        Utils.ItemCheck.put(Items.SEA_LANTERN,true);
        Utils.ItemCheck.put(Items.QUARTZ_SLAB,true);
        Utils.ItemCheck.put(Items.GRANITE_SLAB,true);
        Utils.ItemCheck.put(Items.POLISHED_BLACKSTONE_BRICK_SLAB,true);
        Utils.ItemCheck.put(Items.SPRUCE_SLAB,true);
        Utils.ItemCheck.put(Items.BIRCH_SLAB,true);
        Utils.ItemCheck.put(Items.OAK_SLAB,true);
        Utils.ItemCheck.put(Items.LOOM,true);
        Utils.ItemCheck.put(Items.SMOKER,true);
        Utils.ItemCheck.put(Items.GREEN_STAINED_GLASS,true);
        Utils.ItemCheck.put(Items.SMOOTH_QUARTZ_SLAB,true);
        Utils.ItemCheck.put(Items.POLISHED_DEEPSLATE_SLAB,true);
        Utils.ItemCheck.put(Items.ANVIL,true);
        Utils.ItemCheck.put(Items.WHITE_STAINED_GLASS,true);
        Utils.ItemCheck.put(Items.LECTERN,true);
        Utils.ItemCheck.put(Items.CARTOGRAPHY_TABLE,true);
        Utils.ItemCheck.put(Items.LIME_STAINED_GLASS,true);
        Utils.ItemCheck.put(Items.DARK_OAK_SLAB,true);
        Utils.ItemCheck.put(Items.PEARLESCENT_FROGLIGHT,true);
        Utils.ItemCheck.put(Items.END_STONE,true);
        Utils.ItemCheck.put(Items.BLACK_WOOL,true);
        Utils.ItemCheck.put(Items.ACACIA_LOG,true);
        Utils.ItemCheck.put(Items.BIRCH_LOG,true);
        Utils.ItemCheck.put(Items.DARK_OAK_LOG,true);
        Utils.ItemCheck.put(Items.OAK_LOG,true);
        Utils.ItemCheck.put(Items.JUNGLE_LOG,true);
        Utils.ItemCheck.put(Items.MANGROVE_LOG,true);
        Utils.ItemCheck.put(Items.SPRUCE_LOG,true);
        Utils.ItemCheck.put(Items.OAK_FENCE,true);
        Utils.ItemCheck.put(Items.BEACON,true);
        Utils.ItemCheck.put(Items.PURPLE_STAINED_GLASS,true);
        Utils.ItemCheck.put(Items.ACACIA_LEAVES,true);
        Utils.ItemCheck.put(Items.AZALEA_LEAVES,true);
        Utils.ItemCheck.put(Items.BIRCH_LEAVES,true);
        Utils.ItemCheck.put(Items.JUNGLE_LEAVES,true);
        Utils.ItemCheck.put(Items.DARK_OAK_LEAVES,true);
        Utils.ItemCheck.put(Items.OAK_LEAVES,true);
        Utils.ItemCheck.put(Items.MANGROVE_LEAVES,true);
        Utils.ItemCheck.put(Items.FLOWERING_AZALEA_LEAVES,true);
        Utils.ItemCheck.put(Items.SPRUCE_LEAVES,true);
        Utils.ItemCheck.put(Items.TWISTING_VINES,true);
        Utils.ItemCheck.put(Items.WEEPING_VINES,true);
        Utils.ItemCheck.put(Items.GILDED_BLACKSTONE,true);
        Utils.ItemCheck.put(Items.BAMBOO,true);
        Utils.ItemCheck.put(Items.PRISMARINE_BRICKS,true);
        Utils.ItemCheck.put(Items.GRASS,true);
        Utils.ItemCheck.put(Items.DANDELION,true);
        Utils.ItemCheck.put(Items.POPPY,true);
        Utils.ItemCheck.put(Items.BLUE_ORCHID,true);
        Utils.ItemCheck.put(Items.ALLIUM,true);
        Utils.ItemCheck.put(Items.AZURE_BLUET,true);
        Utils.ItemCheck.put(Items.RED_TULIP,true);
        Utils.ItemCheck.put(Items.ORANGE_TULIP,true);
        Utils.ItemCheck.put(Items.WHITE_TULIP,true);
        Utils.ItemCheck.put(Items.PINK_TULIP,true);
        Utils.ItemCheck.put(Items.OXEYE_DAISY,true);
        Utils.ItemCheck.put(Items.CORNFLOWER,true);
        Utils.ItemCheck.put(Items.LILY_OF_THE_VALLEY,true);
        Utils.ItemCheck.put(Items.WITHER_ROSE,true);
        Utils.ItemCheck.put(Items.SPORE_BLOSSOM,true);
        Utils.ItemCheck.put(Items.CRIMSON_ROOTS,true);
        Utils.ItemCheck.put(Items.WARPED_ROOTS,true);
        Utils.ItemCheck.put(Items.NETHER_SPROUTS,true);
        Utils.ItemCheck.put(Items.SUGAR_CANE,true);
        Utils.ItemCheck.put(Items.KELP,true);
        Utils.ItemCheck.put(Items.CHORUS_PLANT,true);
        Utils.ItemCheck.put(Items.CHORUS_FLOWER,true);
        Utils.ItemCheck.put(Items.CACTUS,true);
        Utils.ItemCheck.put(Items.CHAIN,true);
        Utils.ItemCheck.put(Items.VINE,true);
        Utils.ItemCheck.put(Items.GLOW_LICHEN,true);
        Utils.ItemCheck.put(Items.SCULK_VEIN,true);
        Utils.ItemCheck.put(Items.SUNFLOWER,true);
        Utils.ItemCheck.put(Items.LILAC,true);
        Utils.ItemCheck.put(Items.ROSE_BUSH,true);
        Utils.ItemCheck.put(Items.PEONY,true);
        Utils.ItemCheck.put(Items.TALL_GRASS,true);
        Utils.ItemCheck.put(Items.LARGE_FERN,true);
        Utils.ItemCheck.put(Items.POINTED_DRIPSTONE,true);
        Utils.ItemCheck.put(Items.OBSIDIAN,true);
        Utils.ItemCheck.put(Items.SEAGRASS,true);
        Utils.ItemCheck.put(Items.CHERRY_LEAVES,true);
        Utils.ItemCheck.put(Items.CHERRY_LOG,true);
    }
    public static int tick = 0;
    public static Player Vplayer;
    public static boolean Security = true;
    public static float security0 = 64;
    public static float security1 = 64;
    public static float security2 = 64;
    public static float security3 = 64;
    public static int securityCount = 0;
    public static int securityCount1 = 0;


    // Entity


    public static Vex[] vex = new Vex[15];
    public static Zombie[] PlainZombie = new Zombie[25];
    public static Skeleton[] ForestSkeleton = new Skeleton[15];
    public static Zombie[] ForestZombie = new Zombie[15];
    public static Zombie[] MineZombie = new Zombie[15];
    public static Skeleton[] MineSkeleton = new Skeleton[15];
    public static Stray[] SnowStray = new Stray[15];
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

    public static Zombie ForestBoss = null;
    public static Blaze VolcanoBoss = null;

    public static int SummonTick = 0;
    public static int SummonTick0 = 0;
    public static int PFSummonTick = 0;
    public static int PVSummonTick = 0;
    public static int PLSummonTick = 0;

    public static Style styleOfMana = Style.EMPTY.withColor(TextColor.parseColor("#ba00ff"));
    public static Style styleOfNether = Style.EMPTY.withColor(TextColor.parseColor("#a2001b"));
    public static Style styleOfQuartz = Style.EMPTY.withColor(TextColor.parseColor("#ea7552"));
    public static Style styleOfSea = Style.EMPTY.withColor(TextColor.parseColor("#a4d1c2"));
    public static Style styleOfBlackForest = Style.EMPTY.withColor(TextColor.parseColor("#8f5e00"));
    public static Style styleOfSeaDark = Style.EMPTY.withColor(TextColor.parseColor("#2e6053"));
    public static Style styleOfLightingIsland = Style.EMPTY.withColor(TextColor.parseColor("#515bbd"));
    public static Style styleOfRune =  Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"));
    public static Style styleOfSky = Style.EMPTY.withColor(TextColor.parseColor("#00dcff"));
    public static Style styleOfBrew = Style.EMPTY.withColor(TextColor.parseColor("#ffffb5"));
    public static Style styleOfHealth = Style.EMPTY.withColor(TextColor.parseColor("#6fc301"));
    public static Style styleOfKaze = Style.EMPTY.withColor(TextColor.parseColor("#a1e475"));
    public static Style styleOfSpider = Style.EMPTY.withColor(TextColor.parseColor("#d0cfff"));
    public static Style styleOfRange = Style.EMPTY.withColor(TextColor.parseColor("#00dcff"));
    public static Style styleOfSnow = Style.EMPTY.withColor(TextColor.parseColor("#72f1e4"));
    public static Style styleOfGather = Style.EMPTY.withColor(TextColor.parseColor("#fe68eb"));
    public static Style styleOfVolcano = Style.EMPTY.withColor(TextColor.parseColor("#ffae00"));
    public static Style styleOfEnd = Style.EMPTY.withColor(TextColor.parseColor("#9b4ecf"));
    public static Style styleOfForest = Style.EMPTY.withColor(TextColor.parseColor("#00620b"));
    public static Style styleOfPower = Style.EMPTY.withColor(TextColor.parseColor("#ff7600"));
    public static Style styleOfIntelligent = Style.EMPTY.withColor(TextColor.parseColor("#c600ff"));
    public static Style styleOfFlexible = Style.EMPTY.withColor(TextColor.parseColor("#60ff00"));
    public static Style styleOfLucky = Style.EMPTY.withColor(TextColor.parseColor("#f76ed0"));
    public static Style styleOfVitality = Style.EMPTY.withColor(TextColor.parseColor("#40afff"));
    public static Style styleOfSakura = Style.EMPTY.withColor(TextColor.parseColor("#f6a1fd"));
    public static Style styleOfWheat = Style.EMPTY.withColor(TextColor.parseColor("#ffce00"));
    public static Style styleOfEntropy = Style.EMPTY.withColor(TextColor.parseColor("#fd00a4"));

    public static boolean OverWorldLevelIsNight = false;
    public static int netherMobSpawn = 0;
    public static Queue<Mob> witherBonePowerCCMonster = new LinkedList<Mob>();
    public static int witherBonePowerCount = 0;
    public static Queue<ServerWaltzPlayer> QuartzSabreCCPlayer = new LinkedList<>();
    public static Queue<ServerWaltzMonster> QuartzSabreCCMonster = new LinkedList<>();
    public static int waltztick = 0;
    public static boolean IsLandBarrier = true;
    public static Map<Player,Queue<Shield>> PlayerShieldQueue = new HashMap<>();
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
        for(int i = 0; i < 36; i++){
            PotionMap.put(PotionName[i],true);
        }
    }
    public static Map<Item, Item> BrewSoulMap = new HashMap<>();
    public static void BrewSoulMapInit()
    {
        BrewSoulMap.put(Moditems.PlainSoul.get(), Moditems.PlainSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.ForestSoul.get(), Moditems.ForestSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.WaterSoul.get(), Moditems.LakeSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.VolcanoSoul.get(), Moditems.VolcanoSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.SnowSoul.get(), Moditems.SnowSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.EvokerSoul.get(), Moditems.EvokerSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.ruby.get(), Moditems.NetherSolidifiedSoul.get());
        BrewSoulMap.put(Moditems.SkySoul.get(), Moditems.SkySolidifiedSoul.get());
    }
    public static boolean GemsForPlain = true;
    public static boolean GemsForForest = true;
    public static Map<MobEffect, Boolean> MobEffectMap = new HashMap<>();
    public static void MobEffectMapInit()
    {
        MobEffectMap.put(ModEffects.PLAINARMOR.get(),true);
        MobEffectMap.put(ModEffects.FORESTARMOR.get(),true);
        MobEffectMap.put(ModEffects.VOLCANOARMOR.get(),true);
        MobEffectMap.put(ModEffects.LAKEARMOR.get(),true);
        MobEffectMap.put(ModEffects.LIFEMANA.get(),true);
        MobEffectMap.put(ModEffects.OBSIMANA.get(),true);
        MobEffectMap.put(ModEffects.SKYARMOR.get(),true);
        MobEffectMap.put(ModEffects.LIGHTNINGARMOR.get(),true);
    }
    public static Map<Item, Boolean> ItemRightClickCheck = new HashMap<>();
    public static void ItemRightClickCheckInit()
    {
        ItemRightClickCheck.put(Items.WATER_BUCKET,true);
        ItemRightClickCheck.put(Items.BUCKET,true);
        ItemRightClickCheck.put(Items.LAVA_BUCKET,true);
        ItemRightClickCheck.put(Items.POWDER_SNOW_BUCKET,true);
        ItemRightClickCheck.put(Items.FLINT_AND_STEEL,true);
        ItemRightClickCheck.put(Items.DIAMOND_HOE,true);
        ItemRightClickCheck.put(Items.IRON_HOE,true);
        ItemRightClickCheck.put(Items.GOLDEN_HOE,true);
        ItemRightClickCheck.put(Items.NETHERITE_HOE,true);
        ItemRightClickCheck.put(Items.STONE_HOE,true);
        ItemRightClickCheck.put(Items.WOODEN_HOE,true);
        ItemRightClickCheck.put(Items.DIAMOND_SHOVEL,true);
        ItemRightClickCheck.put(Items.IRON_SHOVEL,true);
        ItemRightClickCheck.put(Items.GOLDEN_SHOVEL,true);
        ItemRightClickCheck.put(Items.NETHERITE_SHOVEL,true);
        ItemRightClickCheck.put(Items.STONE_SHOVEL,true);
        ItemRightClickCheck.put(Items.WOODEN_SHOVEL,true);
        ItemRightClickCheck.put(Items.DIAMOND_AXE,true);
        ItemRightClickCheck.put(Items.IRON_AXE,true);
        ItemRightClickCheck.put(Items.GOLDEN_AXE,true);
        ItemRightClickCheck.put(Items.NETHERITE_AXE,true);
        ItemRightClickCheck.put(Items.STONE_AXE,true);
        ItemRightClickCheck.put(Items.WOODEN_AXE,true);
        ItemRightClickCheck.put(Items.SAND,true);
        ItemRightClickCheck.put(Items.RED_SAND,true);
        ItemRightClickCheck.put(Items.GRAVEL,true);
        ItemRightClickCheck.put(Items.TNT,true);
        ItemRightClickCheck.put(Items.REDSTONE,true);
        ItemRightClickCheck.put(Items.REDSTONE_TORCH,true);
        ItemRightClickCheck.put(Items.RAIL,true);
        ItemRightClickCheck.put(Items.ACTIVATOR_RAIL,true);
        ItemRightClickCheck.put(Items.DETECTOR_RAIL,true);
        ItemRightClickCheck.put(Items.POWERED_RAIL,true);
        ItemRightClickCheck.put(Items.REDSTONE_BLOCK,true);
        ItemRightClickCheck.put(Items.REPEATER,true);
        ItemRightClickCheck.put(Items.COMPARATOR,true);
        ItemRightClickCheck.put(Items.PISTON,true);
        ItemRightClickCheck.put(Items.STICKY_PISTON,true);
        ItemRightClickCheck.put(Items.OBSERVER,true);
        ItemRightClickCheck.put(Items.HOPPER,true);
        ItemRightClickCheck.put(Items.DISPENSER,true);
        ItemRightClickCheck.put(Items.DROPPER,true);

    }
    public static int PFController = 0;
    public static int SVController = 0;
    public static int SLController = 0;
    public static int NSController = -1;
    public static Queue<Player> NSPlayerController = new LinkedList<>();
    public static Queue<Player> NSPlayerInController = new LinkedList<>();
    public static boolean NSClear = false;
    public static boolean NSS1 = false;
    public static boolean NSS2 = false;
    public static WitherSkeleton NSWitherSkeleton1;
    public static WitherSkeleton NSWitherSkeleton2;
    public static ZombifiedPiglin NSZombifiedPiglin1;
    public static ZombifiedPiglin NSZombifiedPiglin2;
    public static ZombifiedPiglin NSZombifiedPiglin3;
    public static String AttributeName[] = {
            "GemSAttack",
            "GemSBreakDefence",
            "GemSCritRate",
            "GemSCritDamage",
            "GemSManaDamage",
            "GemSManaBreakDefence",
            "GemSManaReply",
            "GemSCoolDown",
            "GemSHealSteal",
            "GemSDefence",
            "GemSManaDefence",
            "GemSSpeed",
            "GemSMaxMana",
            "GemSMaxHeal",
            "GemSExpImprove"
    };
    public static Map<String,Double> AttributeMap = new HashMap<>();
    public static void AttributeMapInit(){
        AttributeMap.put(AttributeName[0], 20d);
        AttributeMap.put(AttributeName[1], 0.05d);
        AttributeMap.put(AttributeName[2], 0.1d);
        AttributeMap.put(AttributeName[3], 0.2d);
        AttributeMap.put(AttributeName[4], 20d);
        AttributeMap.put(AttributeName[5], 0.05d);
        AttributeMap.put(AttributeName[6], 1.5d);
        AttributeMap.put(AttributeName[7], 0.05d);
        AttributeMap.put(AttributeName[8], 0.05d);
        AttributeMap.put(AttributeName[9], 40d);
        AttributeMap.put(AttributeName[10], 40d);
        AttributeMap.put(AttributeName[11], 0.15d);
        AttributeMap.put(AttributeName[12], 10d);
        AttributeMap.put(AttributeName[13], 100d);
        AttributeMap.put(AttributeName[14], 0.2d);
    }
    public static boolean FileFlag = true;
    public static boolean LogFlag = true;
    public static void SecurityInit() {
        double sec0 = ConfigTest.Security0.get();
        double sec1 = ConfigTest.Security1.get();
        double sec2 = ConfigTest.Security2.get();
        double sec3 = ConfigTest.Security3.get();
        Utils.security0 = (float) sec0;
        Utils.security1 = (float) sec1;
        Utils.security2 = (float) sec2;
        Utils.security3 = (float) sec3;
    }
    public static boolean SecurityInitFlag = true;
    public static boolean MarketFlag = true;
    public static int MarketTickCount = 0;
    public static Queue<MarketItemInfo> MarketInfo = new LinkedList<>();
    public static Queue<MarketPlayerInfo> MarketPlayerInfo = new LinkedList<>();
    public static Map<String, ItemStack> itemStackMap = new HashMap<>();
    public static void itemStackMapInit() {
        itemStackMap.put(Moditems.PlainSoul.get().toString(),Moditems.PlainSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.PlainRune.get().toString(),Moditems.PlainRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.ForestSoul.get().toString(),Moditems.ForestSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.ForestRune.get().toString(),Moditems.ForestRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.WaterSoul.get().toString(),Moditems.WaterSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.WaterRune.get().toString(),Moditems.WaterRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoSoul.get().toString(),Moditems.VolcanoSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoRune.get().toString(),Moditems.VolcanoRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.MineSoul.get().toString(),Moditems.MineSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.MineSoul1.get().toString(),Moditems.MineSoul1.get().getDefaultInstance());
        itemStackMap.put(Moditems.MineRune.get().toString(),Moditems.MineRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkySoul.get().toString(),Moditems.SkySoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyRune.get().toString(),Moditems.SkyRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.EvokerSoul.get().toString(),Moditems.EvokerSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.EvokerRune.get().toString(),Moditems.EvokerRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.SeaSoul.get().toString(),Moditems.SeaSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.SeaRune.get().toString(),Moditems.SeaRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.BlackForestSoul.get().toString(),Moditems.BlackForestSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.BlackForestRune.get().toString(),Moditems.BlackForestRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.LightningSoul.get().toString(),Moditems.LightningSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.LightningRune.get().toString(),Moditems.LightningRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoCore.get().toString(),Moditems.VolcanoCore.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherOintment0.get().toString(),Moditems.NetherOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherOintment1.get().toString(),Moditems.NetherOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherOintment2.get().toString(),Moditems.NetherOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.ManaOintment0.get().toString(),Moditems.ManaOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.ManaOintment1.get().toString(),Moditems.ManaOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.ManaOintment2.get().toString(),Moditems.ManaOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyOintment0.get().toString(),Moditems.SkyOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyOintment1.get().toString(),Moditems.SkyOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyOintment2.get().toString(),Moditems.SkyOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.SnowOintment0.get().toString(),Moditems.SnowOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.SnowOintment1.get().toString(),Moditems.SnowOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.SnowOintment2.get().toString(),Moditems.SnowOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoOintment0.get().toString(),Moditems.VolcanoOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoOintment1.get().toString(),Moditems.VolcanoOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoOintment2.get().toString(),Moditems.VolcanoOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.LakeOintment0.get().toString(),Moditems.LakeOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.LakeOintment1.get().toString(),Moditems.LakeOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.LakeOintment2.get().toString(),Moditems.LakeOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.SunOintment0.get().toString(),Moditems.SunOintment0.get().getDefaultInstance());
        itemStackMap.put(Moditems.SunOintment1.get().toString(),Moditems.SunOintment1.get().getDefaultInstance());
        itemStackMap.put(Moditems.SunOintment2.get().toString(),Moditems.SunOintment2.get().getDefaultInstance());
        itemStackMap.put(Moditems.SpiderSoul.get().toString(),Moditems.SpiderSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.KazeBootsFG.get().toString(),Moditems.KazeBootsFG.get().getDefaultInstance());
        itemStackMap.put(Moditems.LakeCore.get().toString(),Moditems.LakeCore.get().getDefaultInstance());
        itemStackMap.put(Moditems.KazeSwordFG.get().toString(),Moditems.KazeSwordFG.get().getDefaultInstance());
        itemStackMap.put(Moditems.KazeRune.get().toString(),Moditems.KazeRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.KazeSoul.get().toString(),Moditems.KazeSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherHForgingDrawing.get().toString(),Moditems.NetherHForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherCForgingDrawing.get().toString(),Moditems.NetherCForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherLForgingDrawing.get().toString(),Moditems.NetherLForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherBForgingDrawing.get().toString(),Moditems.NetherBForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.RandomGemPiece.get().toString(),Moditems.RandomGemPiece.get().getDefaultInstance());
        itemStackMap.put(Moditems.GemBag.get().toString(),Moditems.GemBag.get().getDefaultInstance());
        itemStackMap.put(Moditems.SunPower.get().toString(),Moditems.SunPower.get().getDefaultInstance());
        itemStackMap.put(Moditems.BlackForestForgingDrawing.get().toString(),Moditems.BlackForestForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.SeaSwordForgingDrawing.get().toString(),Moditems.SeaSwordForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyBForgingDrawing.get().toString(),Moditems.SkyBForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyLForgingDrawing.get().toString(),Moditems.SkyLForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyCForgingDrawing.get().toString(),Moditems.SkyCForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.SkyHForgingDrawing.get().toString(),Moditems.SkyHForgingDrawing.get().getDefaultInstance());
        itemStackMap.put(Moditems.GoldCoinBag.get().toString(),Moditems.GoldCoinBag.get().getDefaultInstance());
        itemStackMap.put(Moditems.SnowGem.get().toString(),Moditems.SnowGem.get().getDefaultInstance());
        itemStackMap.put(Moditems.VolcanoGem.get().toString(),Moditems.VolcanoGem.get().getDefaultInstance());
        itemStackMap.put(Moditems.LakeGem.get().toString(),Moditems.LakeGem.get().getDefaultInstance());
        itemStackMap.put(Moditems.ForestGem.get().toString(),Moditems.ForestGem.get().getDefaultInstance());
        itemStackMap.put(Moditems.PlainGem.get().toString(),Moditems.PlainGem.get().getDefaultInstance());
        itemStackMap.put(Moditems.LightningRune.get().toString(),Moditems.LightningRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.LightningSoul.get().toString(),Moditems.LightningSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.PigLinSoul.get().toString(),Moditems.PigLinSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.witherBone.get().toString(),Moditems.witherBone.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherSwordModel.get().toString(),Moditems.NetherSwordModel.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherRune.get().toString(),Moditems.NetherRune.get().getDefaultInstance());
        itemStackMap.put(Moditems.NetherSoul.get().toString(),Moditems.NetherSoul.get().getDefaultInstance());
        itemStackMap.put(Moditems.ruby.get().toString(),Moditems.ruby.get().getDefaultInstance());
        itemStackMap.put(Moditems.manabucket.get().toString(),Moditems.manabucket.get().getDefaultInstance());
        itemStackMap.put(Moditems.skygem.get().toString(),Moditems.skygem.get().getDefaultInstance());
        itemStackMap.put(Moditems.main1crystal.get().toString(),Moditems.main1crystal.get().getDefaultInstance());

    }
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
    public static Map<String, ChatFormatting> PrefixColorMap = new HashMap<>();
    public static void PrefixColorMapInit() {
        PrefixColorMap.put("初来乍到",ChatFormatting.GRAY);
        PrefixColorMap.put("平原统治者",ChatFormatting.GREEN);
        PrefixColorMap.put("森林统治者",ChatFormatting.DARK_GREEN);
        PrefixColorMap.put("湖泊统治者",ChatFormatting.BLUE);
        PrefixColorMap.put("火山统治者",ChatFormatting.YELLOW);
        PrefixColorMap.put("冰川统治者",ChatFormatting.AQUA);
        PrefixColorMap.put("天空统治者",ChatFormatting.AQUA);
        PrefixColorMap.put("唤魔森林统治者",ChatFormatting.LIGHT_PURPLE);
        PrefixColorMap.put("酿造初识",ChatFormatting.GRAY);
        PrefixColorMap.put("酿造入门",ChatFormatting.GREEN);
        PrefixColorMap.put("酿造初级",ChatFormatting.BLUE);
        PrefixColorMap.put("酿造中级",ChatFormatting.YELLOW);
        PrefixColorMap.put("酿造高级",ChatFormatting.AQUA);
        PrefixColorMap.put("酿造学士",ChatFormatting.GOLD);
        PrefixColorMap.put("酿造大师",ChatFormatting.LIGHT_PURPLE);
        PrefixColorMap.put("股市巴菲特",ChatFormatting.GOLD);
        PrefixColorMap.put("股市墨菲特",ChatFormatting.DARK_GRAY);
    }
    public static Map<String, Style> PrefixStyleMap = new HashMap<>();
    public static void PrefixStyleMapInit() {
        PrefixStyleMap.put("神殿统治者",Utils.styleOfSea);
        PrefixStyleMap.put("唤雷岛统治者",Utils.styleOfLightingIsland);
        PrefixStyleMap.put("黑色森林统治者",Utils.styleOfBlackForest);
    }
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
    public static Player HBossPlayer = null;
    public static int HBossDelay = -2;
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

    public static class WeaponTypeComponents {
        public static MutableComponent MainHand = Component.literal("主手                   ").withStyle(ChatFormatting.AQUA);
        public static MutableComponent AxeType = Component.literal("斧头").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED);
        public static MutableComponent ShortHandleSword = Component.literal("短柄剑").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW);
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
    }

    public static ArrayList<String> IpArrayList = new ArrayList<>();
    public static Map<String, String> IpLoginMap = new HashMap<>();

}