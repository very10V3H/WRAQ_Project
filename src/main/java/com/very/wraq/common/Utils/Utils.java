package com.very.wraq.common.Utils;

import com.very.wraq.entities.entities.Boss2.Boss2;
import com.very.wraq.entities.entities.SakuraMob.SakuraMob;
import com.very.wraq.entities.entities.Scarecrow.Scarecrow;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.networking.unSorted.PlayerCallBack;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.end.Recall;
import com.very.wraq.series.overworld.chapter1.forest.ForestPowerEffectMob;
import com.very.wraq.series.overworld.chapter1.waterSystem.LakePowerEffect;
import com.very.wraq.series.overworld.chapter7.C7Items;
import com.very.wraq.common.Utils.Struct.*;
import com.very.wraq.common.Utils.Struct.BowSkillStruct.BowSkill3;
import com.very.wraq.common.Utils.Struct.BowSkillStruct.BowSkill6;
import com.very.wraq.common.Utils.Struct.ManaSkillStruct.ManaSkill3;
import com.very.wraq.common.Utils.Struct.ManaSkillStruct.ManaSkill6;
import com.very.wraq.common.Utils.Struct.SwordSkillStruct.SwordSkill13;
import com.very.wraq.common.Utils.Struct.SwordSkillStruct.SwordSkill3;
import com.very.wraq.common.Utils.Struct.SwordSkillStruct.SwordSkill6;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class Utils {
    public static final String MOD_ID = "vmd";
    public static Map<Item, Double> attackDamage = new HashMap<>();
    public static Map<Item, Double> xpLevelAttackDamage = new HashMap<>();
    public static Map<Item, Double> percentAttackDamageEnhance = new HashMap<>();
    public static Map<Item, Double> critRate = new HashMap<>();
    public static Map<Item, Double> critDamage = new HashMap<>();
    public static Map<Item, Double> xpLevelCritDamage = new HashMap<>();
    public static Map<Item, Double> defence = new HashMap<>();
    public static Map<Item, Double> percentDefenceEnhance = new HashMap<>();
    public static Map<Item, Double> defencePenetration = new HashMap<>();
    public static Map<Item, Double> defencePenetration0 = new HashMap<>();
    public static Map<Item, Double> xpLevelDefencePenetration0 = new HashMap<>();
    public static Map<Item, Double> attackRangeUp = new HashMap<>();
    public static Map<Item, Double> swiftnessUp = new HashMap<>();
    public static Map<Item, Double> attackSpeedUp = new HashMap<>();
    public static Map<Item, Double> movementSpeedWithoutBattle = new HashMap<>();
    public static Map<Item, Double> movementSpeedCommon = new HashMap<>();
    public static Map<Item, Double> healthSteal = new HashMap<>();
    public static Map<Item, Double> expUp = new HashMap<>();
    public static Map<Item, Double> maxHealth = new HashMap<>();
    public static Map<Item, Double> percentMaxHealthEnhance = new HashMap<>();
    public static Map<Item, Double> healthRecover = new HashMap<>();
    public static Map<Item, Double> healEffectUp = new HashMap<>();
    public static Map<Item, Double> coolDownDecrease = new HashMap<>();
    public static Map<Item, Double> manaDamage = new HashMap<>();
    public static Map<Item, Double> xpLevelManaDamage = new HashMap<>();
    public static Map<Item, Double> percentManaDamageEnhance = new HashMap<>();
    public static Map<Item, Double> manaDefence = new HashMap<>();
    public static Map<Item, Double> percentManaDefenceEnhance = new HashMap<>();
    public static Map<Item, Double> manaPenetration = new HashMap<>();
    public static Map<Item, Double> manaPenetration0 = new HashMap<>();
    public static Map<Item, Double> xpLevelManaPenetration0 = new HashMap<>();
    public static Map<Item, Double> manaCost = new HashMap<>();
    public static Map<Item, Double> manaRecover = new HashMap<>();
    public static Map<Item, Double> maxMana = new HashMap<>();
    public static Map<Item, Double> manaHealthSteal = new HashMap<>();
    public static Map<Item, Double> critDamageDecrease = new HashMap<>();
    public static Map<Item, Double> luckyUp = new HashMap<>();
    public static Map<Item, Double> toughness = new HashMap<>();
    public static Map<Item, Double> mainHandTag = new HashMap<>();
    public static Map<Item, Double> swordTag = new HashMap<>();
    public static Map<Item, Double> bowTag = new HashMap<>();
    public static Map<Item, Double> sceptreTag = new HashMap<>();
    public static Map<Item, Double> offHandTag = new HashMap<>();
    public static Map<Item, Double> shieldTag = new HashMap<>();
    public static Map<Item, Double> armorTag = new HashMap<>();
    public static Map<Item, Double> powerTag = new HashMap<>();
    public static Map<Item, Double> curiosTag = new HashMap<>();
    public static Map<Item, Double> mobLevel = new HashMap<>();
    public static Map<Item, Double> passiveEquipTag = new HashMap<>();
    public static Map<Item, Integer> levelRequire = new HashMap<>();
    public static int Count = -1;
    public static Map<Item, Integer> gemsTag = new HashMap<>();
    public static Map<String, Double> gemsAttackDamage = new HashMap<>();
    public static Map<String, Double> gemsSpeedUp = new HashMap<>();
    public static Map<String, Double> gemsManaDamage = new HashMap<>();
    public static Map<String, Double> gemsManaRecover = new HashMap<>();
    public static Map<String, Double> gemsHealthRecover = new HashMap<>();
    public static Map<String, Double> gemsMaxHealth = new HashMap<>();
    public static Map<String, Double> gemsDefence = new HashMap<>();
    public static Map<String, Double> gemsCoolDown = new HashMap<>();
    public static Map<String, Double> gemsCritDamage = new HashMap<>();
    public static Map<String, Double> gemsCritRate = new HashMap<>();
    public static Map<String, Double> gemsHealStrength = new HashMap<>();
    public static Map<String, Double> gemsManaHealthSteal = new HashMap<>();
    public static Map<String, Double> gemsDefencePenetration0 = new HashMap<>();
    public static Map<String, Double> gemsManaPenetration0 = new HashMap<>();
    public static Map<String, Double> gemsExpUp = new HashMap<>();
    public static Map<String, Double> gemsLuckyUp = new HashMap<>();
    public static Map<String, Double> gemsDefencePenetration = new HashMap<>();
    public static Map<String, Double> gemsManaPenetration = new HashMap<>();
    public static Map<String, Double> gemsHealthSteal = new HashMap<>();
    public static Map<String, Double> gemsManaDefence = new HashMap<>();

    public static List<LivingEntity> MonsterAttributeDataProvider = new ArrayList<>();

    public static int AttributeDataTick = 0;
    public static Entity EntityCopy;
    public static Map<Item, Boolean> ItemCheck = new HashMap<>() {
        {
            put(Items.PRISMARINE_BRICK_SLAB, true);
            put(Items.SMITHING_TABLE, true);
            put(Items.POLISHED_BLACKSTONE_SLAB, true);
            put(Items.SEA_LANTERN, true);
            put(Items.QUARTZ_SLAB, true);
            put(Items.GRANITE_SLAB, true);
            put(Items.POLISHED_BLACKSTONE_BRICK_SLAB, true);
            put(Items.SPRUCE_SLAB, true);
            put(Items.BIRCH_SLAB, true);
            put(Items.OAK_SLAB, true);
            put(Items.LOOM, true);
            put(Items.SMOKER, true);
            put(Items.GREEN_STAINED_GLASS, true);
            put(Items.SMOOTH_QUARTZ_SLAB, true);
            put(Items.POLISHED_DEEPSLATE_SLAB, true);
            put(Items.ANVIL, true);
            put(Items.WHITE_STAINED_GLASS, true);
            put(Items.LECTERN, true);
            put(Items.CARTOGRAPHY_TABLE, true);
            put(Items.LIME_STAINED_GLASS, true);
            put(Items.DARK_OAK_SLAB, true);
            put(Items.PEARLESCENT_FROGLIGHT, true);
            put(Items.END_STONE, true);
            put(Items.BLACK_WOOL, true);
            put(Items.ACACIA_LOG, true);
            put(Items.BIRCH_LOG, true);
            put(Items.DARK_OAK_LOG, true);
            put(Items.OAK_LOG, true);
            put(Items.JUNGLE_LOG, true);
            put(Items.MANGROVE_LOG, true);
            put(Items.SPRUCE_LOG, true);
            put(Items.OAK_FENCE, true);
            put(Items.BEACON, true);
            put(Items.PURPLE_STAINED_GLASS, true);
            put(Items.ACACIA_LEAVES, true);
            put(Items.AZALEA_LEAVES, true);
            put(Items.BIRCH_LEAVES, true);
            put(Items.JUNGLE_LEAVES, true);
            put(Items.DARK_OAK_LEAVES, true);
            put(Items.OAK_LEAVES, true);
            put(Items.MANGROVE_LEAVES, true);
            put(Items.FLOWERING_AZALEA_LEAVES, true);
            put(Items.SPRUCE_LEAVES, true);
            put(Items.TWISTING_VINES, true);
            put(Items.WEEPING_VINES, true);
            put(Items.GILDED_BLACKSTONE, true);
            put(Items.BAMBOO, true);
            put(Items.PRISMARINE_BRICKS, true);
            put(Items.GRASS, true);
            put(Items.DANDELION, true);
            put(Items.POPPY, true);
            put(Items.BLUE_ORCHID, true);
            put(Items.ALLIUM, true);
            put(Items.AZURE_BLUET, true);
            put(Items.RED_TULIP, true);
            put(Items.ORANGE_TULIP, true);
            put(Items.WHITE_TULIP, true);
            put(Items.PINK_TULIP, true);
            put(Items.OXEYE_DAISY, true);
            put(Items.CORNFLOWER, true);
            put(Items.LILY_OF_THE_VALLEY, true);
            put(Items.WITHER_ROSE, true);
            put(Items.SPORE_BLOSSOM, true);
            put(Items.CRIMSON_ROOTS, true);
            put(Items.WARPED_ROOTS, true);
            put(Items.NETHER_SPROUTS, true);
            put(Items.SUGAR_CANE, true);
            put(Items.KELP, true);
            put(Items.CHORUS_PLANT, true);
            put(Items.CHORUS_FLOWER, true);
            put(Items.CACTUS, true);
            put(Items.CHAIN, true);
            put(Items.VINE, true);
            put(Items.GLOW_LICHEN, true);
            put(Items.SCULK_VEIN, true);
            put(Items.SUNFLOWER, true);
            put(Items.LILAC, true);
            put(Items.ROSE_BUSH, true);
            put(Items.PEONY, true);
            put(Items.TALL_GRASS, true);
            put(Items.LARGE_FERN, true);
            put(Items.POINTED_DRIPSTONE, true);
            put(Items.OBSIDIAN, true);
            put(Items.SEAGRASS, true);
            put(Items.CHERRY_LEAVES, true);
            put(Items.CHERRY_LOG, true);
        }
    };

    public static List<PlayerCallBack> playerCallBackList = new ArrayList<>();

    public static int tick = 0;
    public static Player Vplayer;
    public static boolean Security = true;
    public static double security0 = 64;
    public static double security1 = 64;
    public static double security2 = 64;
    public static double security3 = 64;

    // Entity

    public static Zombie[] PlainZombie = new Zombie[25];
    public static Skeleton[] ForestSkeleton = new Skeleton[15];
    public static Zombie[] ForestZombie = new Zombie[15];
    public static Stray[] SnowStray = new Stray[15];
    public static WitherSkeleton[] WitherSkeleton = new WitherSkeleton[15];
    public static Evoker[] EvokerMaster = new Evoker[15];
    public static Skeleton[] NetherSkeleton = new Skeleton[15];
    public static Zombie[] LightingZombie = new Zombie[15];
    public static Spider[] Spider = new Spider[15];
    public static Silverfish[] SilverFish = new Silverfish[15];
    public static EnderMan[] EnderMan = new EnderMan[15];
    public static SakuraMob[] SakuraMob = new SakuraMob[15];
    public static Scarecrow[] Scarecrow = new Scarecrow[15];
    public static Zombie[] MineWorker = new Zombie[15];
    public static Stray[] IceHunter = new Stray[15];
    public static Pillager[] pillagers = new Pillager[15];
    public static Stray[] IceHunterForIceKnight = new Stray[15];
    public static Zombie[] WoodenStake = new Zombie[15];
    public static Zombie[] EarthMana = new Zombie[15];
    public static Zombie[] BloodMana = new Zombie[15];
    public static Slime[] Slime = new Slime[15];
    public static Vex[] star = new Vex[25];

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

    public static boolean OverWorldLevelIsNight = false;
    public static int netherMobSpawn = 0;

    public static List<Mob> witherBonePowerCCMonster = new ArrayList<>();

    public static int witherBonePowerCount = 0;
    public static List<ServerWaltzPlayer> QuartzSabreCCPlayer = new ArrayList<>();

    public static List<ServerWaltzMonster> QuartzSabreCCMonster = new ArrayList<>();

    public static Map<String, List<Shield>> playerShieldQueue = new HashMap<>();
    public static Map<String, Boolean> PotionMap = new HashMap<>();

    public static void PotionMapInit() {
        if (PotionMap.isEmpty()) {
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
    }

    public static Map<Item, Item> BrewSoulMap = new HashMap<>();
    public static boolean GemsForPlain = true;
    public static boolean GemsForForest = true;
    public static Map<MobEffect, Boolean> MobEffectMap = new HashMap<>();
    public static Map<Item, Boolean> ItemRightClickCheck = new HashMap<>() {
        {
            put(Items.WATER_BUCKET, true);
            put(Items.BUCKET, true);
            put(Items.LAVA_BUCKET, true);
            put(Items.POWDER_SNOW_BUCKET, true);
            put(Items.FLINT_AND_STEEL, true);
            put(Items.DIAMOND_HOE, true);
            put(Items.IRON_HOE, true);
            put(Items.GOLDEN_HOE, true);
            put(Items.NETHERITE_HOE, true);
            put(Items.STONE_HOE, true);
            put(Items.WOODEN_HOE, true);
            put(Items.DIAMOND_SHOVEL, true);
            put(Items.IRON_SHOVEL, true);
            put(Items.GOLDEN_SHOVEL, true);
            put(Items.NETHERITE_SHOVEL, true);
            put(Items.STONE_SHOVEL, true);
            put(Items.WOODEN_SHOVEL, true);
            put(Items.DIAMOND_AXE, true);
            put(Items.IRON_AXE, true);
            put(Items.GOLDEN_AXE, true);
            put(Items.NETHERITE_AXE, true);
            put(Items.STONE_AXE, true);
            put(Items.WOODEN_AXE, true);
            put(Items.SAND, true);
            put(Items.RED_SAND, true);
            put(Items.GRAVEL, true);
            put(Items.TNT, true);
            put(Items.REDSTONE, true);
            put(Items.REDSTONE_TORCH, true);
            put(Items.RAIL, true);
            put(Items.ACTIVATOR_RAIL, true);
            put(Items.DETECTOR_RAIL, true);
            put(Items.POWERED_RAIL, true);
            put(Items.REDSTONE_BLOCK, true);
            put(Items.REPEATER, true);
            put(Items.COMPARATOR, true);
            put(Items.PISTON, true);
            put(Items.STICKY_PISTON, true);
            put(Items.OBSERVER, true);
            put(Items.HOPPER, true);
            put(Items.DISPENSER, true);
            put(Items.DROPPER, true);
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
    public static Map<String, Double> AttributeMap = new HashMap<>() {{
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

    public static boolean SecurityInitFlag = true;
    public static List<MarketItemInfo> marketItemInfos = new ArrayList<>();
    public static Map<String, Double> marketPlayerInfos = new HashMap<>();
    public static ChatFormatting style = ChatFormatting.AQUA;
    public static Map<String, ChatFormatting> prefixColorMap = new HashMap<>() {
        {
            put("初来乍到", ChatFormatting.GRAY);
            put("平原统治者", ChatFormatting.GREEN);
            put("森林统治者", ChatFormatting.DARK_GREEN);
            put("湖泊统治者", ChatFormatting.BLUE);
            put("火山统治者", ChatFormatting.YELLOW);
            put("冰川统治者", ChatFormatting.AQUA);
            put("天空统治者", ChatFormatting.AQUA);
            put("唤魔森林统治者", ChatFormatting.LIGHT_PURPLE);
            put("酿造初识", ChatFormatting.GRAY);
            put("酿造入门", ChatFormatting.GREEN);
            put("酿造初级", ChatFormatting.BLUE);
            put("酿造中级", ChatFormatting.YELLOW);
            put("酿造高级", ChatFormatting.AQUA);
            put("酿造学士", ChatFormatting.GOLD);
            put("酿造大师", ChatFormatting.LIGHT_PURPLE);
            put("股市巴菲特", ChatFormatting.GOLD);
            put("见习渔夫", ChatFormatting.DARK_GRAY);
            put("入门渔夫", ChatFormatting.GRAY);
            put("中阶渔夫", ChatFormatting.YELLOW);
            put("高阶渔夫", ChatFormatting.BLUE);
            put("经常空军的钓鱼佬", ChatFormatting.GOLD);
            put("偶尔空军的钓鱼佬", ChatFormatting.RED);
            put("永不空军的钓鱼佬", ChatFormatting.LIGHT_PURPLE);
            put("见习矿工", ChatFormatting.DARK_GRAY);
            put("入门矿工", ChatFormatting.GRAY);
            put("职业矿工", ChatFormatting.GOLD);
            put("悲催苦力矿工", ChatFormatting.GREEN);
            put("一只挖矿的帕鲁", ChatFormatting.AQUA);
            put("见习伐木工", ChatFormatting.DARK_GRAY);
            put("入门伐木工", ChatFormatting.GRAY);
            put("职业伐木工", ChatFormatting.GOLD);
            put("光头强", ChatFormatting.GREEN);
            put("一只砍树的帕鲁", ChatFormatting.AQUA);
            put("见习农夫", ChatFormatting.DARK_GRAY);
            put("入门农夫", ChatFormatting.GRAY);
            put("职业农夫", ChatFormatting.GOLD);
            put("农耕大师", ChatFormatting.GREEN);
            put("一只种田的帕鲁", ChatFormatting.AQUA);
            put("赌神", ChatFormatting.GOLD);
            put("无产阶级", ChatFormatting.GOLD);
        }
    };
    public static Map<String, Style> prefixStyleMap = new HashMap<>() {
        {
            put("神殿统治者", CustomStyle.styleOfSea);
            put("唤雷岛统治者", CustomStyle.styleOfLightingIsland);
            put("黑色森林统治者", CustomStyle.styleOfHusk);
            put("风之谷统治者", CustomStyle.styleOfKaze);
            put("微光森林统治者", CustomStyle.styleOfSpider);
            put("终界统治者", CustomStyle.styleOfEnd);
            put("绯樱树林统治者", CustomStyle.styleOfSakura);
            put("樱岛稻田统治者", CustomStyle.styleOfWheat);
            put("紫晶矿洞统治者", CustomStyle.styleOfPurpleIron);
            put("冰原统治者", CustomStyle.styleOfIce);
            put("废旧船厂统治者", CustomStyle.styleOfShip);
            put("龙行龘龘", CustomStyle.styleOfSpring);
            put("理塘王", CustomStyle.styleOfField);
            put("旧世地蕴封印者", CustomStyle.styleOfMana);
            put("旧世腥月封印者", CustomStyle.styleOfBloodMana);
            put("史莱姆的好伙伴(?)", CustomStyle.styleOfHealth);
            put("雨纷纷", CustomStyle.styleOfHealth);
        }
    };
    public static Component[] BrewingLevelName = {
            Component.literal("酿造初识").withStyle(ChatFormatting.GRAY),
            Component.literal("酿造入门").withStyle(ChatFormatting.GREEN),
            Component.literal("酿造初级").withStyle(ChatFormatting.BLUE),
            Component.literal("酿造中级").withStyle(ChatFormatting.YELLOW),
            Component.literal("酿造高级").withStyle(ChatFormatting.AQUA),
            Component.literal("酿造学士").withStyle(ChatFormatting.GOLD),
            Component.literal("酿造大师").withStyle(ChatFormatting.LIGHT_PURPLE)
    };
    public static List<Mob> SnowRune2MobController = new ArrayList<>();
    public static int SnowRune2Tick = -1;
    public static Map<ServerPlayer, Power> PowerMap = new HashMap<>();
    public static Map<Gather, Queue<Mob>> GatherMobMap = new HashMap<>();
    public static Map<Gather, Queue<Player>> GatherPlayerMap = new HashMap<>();
    public static int GatherTickCount = 0;
    public static Recall kazeRecall = new Recall();
    public static Skeleton KazeRecallSkeleton = null;
    public static Recall spiderRecall = new Recall();
    public static Spider SpiderRecallSpider = null;
    public static BlockPos[] SpiderNetBlockPos = new BlockPos[15];
    public static Recall huskRecall = new Recall();
    public static Husk HuskRecallHusk = null;
    public static Recall seaRecall = new Recall();
    public static ElderGuardian SeaRecallElderGuardian = null;
    public static Recall lightningRecall = new Recall();
    public static Zombie LightingRecallZombie = null;
    public static Recall netherRecall = new Recall();
    public static WitherSkeleton NetherRecallWither = null;
    public static Recall snowRecall = new Recall();
    public static Stray SnowRecallStray = null;
    public static Recall forestRecall = new Recall();
    public static Zombie ForestRecallZombie = null;
    public static Skeleton ForestRecallSkeleton = null;
    public static int ForestRecallBossKillCount = 0;
    public static Recall volcanoRecall = new Recall();
    public static Blaze VolcanoRecallBlaze = null;
    public static Map<String, SwordSkill3> SwordSkill3Map = new HashMap<>();
    public static Map<String, SwordSkill6> SwordSkill6Map = new HashMap<>();
    public static Map<String, SwordSkill13> SwordSkill13Map = new HashMap<>();
    public static Map<String, Boolean> SwordSkill12 = new HashMap<>();
    public static Map<String, Boolean> BowSkill12 = new HashMap<>();
    public static Map<String, Boolean> ManaSkill12 = new HashMap<>();
    public static Map<String, Boolean> ManaSkill13 = new HashMap<>();
    public static Map<String, BowSkill3> BowSkill3Map = new HashMap<>();
    public static Map<String, BowSkill6> BowSkill6Map = new HashMap<>();
    public static Map<String, ManaSkill3> ManaSkill3Map = new HashMap<>();
    public static Map<String, ManaSkill6> ManaSkill6Map = new HashMap<>();
    public static Map<String, Boolean> SakuraDemonSword = new HashMap<>();
    public static Map<String, Boolean> ZeusSword = new HashMap<>();

    public static class WeaponTypeComponents {
        public static MutableComponent MainHand = Component.literal("主手                   ").withStyle(ChatFormatting.AQUA);
        public static MutableComponent AxeType = Component.literal("斧头").withStyle(ChatFormatting.RED);
        public static MutableComponent ShortHandleSword = Component.literal("短柄剑").withStyle(ChatFormatting.YELLOW);
        public static MutableComponent DemonSword = Component.literal("妖刀").withStyle(CustomStyle.styleOfDemon);
        public static MutableComponent NormalSword = Component.literal("长剑").withStyle(CustomStyle.styleOfPower);
        public static MutableComponent Bow = Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible);
        public static MutableComponent Sceptre = Component.literal("权杖").withStyle(CustomStyle.styleOfIntelligent);

    }

    public static class Emoji {
        public static String Mana = "☄";
        public static String Sword = "⚔";
        public static String Defence = "\uD83D\uDEE1";
        public static String CritRate = "\uD83D\uDCA2";
        public static String CritDamage = "\uD83D\uDCA5";
        public static String HealSteal = "\uD83E\uDE78";
        public static String Speed = "\uD83C\uDF43";
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

    public static HashMap<String, Double> PlayerAttackSpeedHashMap = new HashMap<>();

    public static HashMap<Slime, Player> SlimeRewardPlayer = new HashMap<>();

    public static void Init() {
        BrewSoulMap.put(ModItems.PlainSoul.get(), ModItems.PlainSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.ForestSoul.get(), ModItems.ForestSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.LakeSoul.get(), ModItems.LakeSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.VolcanoSoul.get(), ModItems.VolcanoSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.SnowSoul.get(), ModItems.SnowSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.EvokerSoul.get(), ModItems.EvokerSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.Ruby.get(), ModItems.NetherSolidifiedSoul.get());
        BrewSoulMap.put(ModItems.SkySoul.get(), ModItems.SkySolidifiedSoul.get());
        MobEffectMap.put(ModEffects.PLAINARMOR.get(), true);
        MobEffectMap.put(ModEffects.FORESTARMOR.get(), true);
        MobEffectMap.put(ModEffects.VOLCANOARMOR.get(), true);
        MobEffectMap.put(ModEffects.LAKEARMOR.get(), true);
        MobEffectMap.put(ModEffects.LIFEMANA.get(), true);
        MobEffectMap.put(ModEffects.OBSIMANA.get(), true);
        MobEffectMap.put(ModEffects.SKYARMOR.get(), true);
        MobEffectMap.put(ModEffects.LIGHTNINGARMOR.get(), true);

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
        CoolDownItem.add(ModItems.huskSword0.get());
        CoolDownItem.add(ModItems.huskSword1.get());
        CoolDownItem.add(ModItems.huskSword2.get());
        CoolDownItem.add(ModItems.huskSword3.get());
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

    public static HashMap<String, ParticleOptions> ParticleStringToParticleMap = new HashMap<>() {{
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
        put(StringUtils.ParticleTypes.SporeBlossomAir, ParticleTypes.SPORE_BLOSSOM_AIR);
        put(StringUtils.ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION);
        put(StringUtils.ParticleTypes.FLAME, ParticleTypes.FLAME);

        put(StringUtils.ParticleTypes.LifeElementParticle, ModParticles.LifeElementParticle.get());
        put(StringUtils.ParticleTypes.WaterElementParticle, ModParticles.WaterElementParticle.get());
        put(StringUtils.ParticleTypes.FireElementParticle, ModParticles.FireElementParticle.get());
        put(StringUtils.ParticleTypes.StoneElementParticle, ModParticles.StoneElementParticle.get());
        put(StringUtils.ParticleTypes.IceElementParticle, ModParticles.IceElementParticle.get());
        put(StringUtils.ParticleTypes.LightningElementParticle, ModParticles.LightningElementParticle.get());
        put(StringUtils.ParticleTypes.WindElementParticle, ModParticles.WindElementParticle.get());

        put(StringUtils.ParticleTypes.LifeElement1TickParticle, ModParticles.LifeElement1TickParticle.get());
        put(StringUtils.ParticleTypes.WaterElement1TickParticle, ModParticles.WaterElement1TickParticle.get());
        put(StringUtils.ParticleTypes.FireElement1TickParticle, ModParticles.FireElement1TickParticle.get());
        put(StringUtils.ParticleTypes.StoneElement1TickParticle, ModParticles.StoneElement1TickParticle.get());
        put(StringUtils.ParticleTypes.IceElement1TickParticle, ModParticles.IceElement1TickParticle.get());
        put(StringUtils.ParticleTypes.LightningElement1TickParticle, ModParticles.LightningElement1TickParticle.get());
        put(StringUtils.ParticleTypes.WindElement1TickParticle, ModParticles.WindElement1TickParticle.get());

        put(StringUtils.ParticleTypes.SoraSwordParticle, ModParticles.SoraSwordParticle.get());
        put(StringUtils.ParticleTypes.EndParticle, ModParticles.END_PARTICLE.get());
        put(StringUtils.ParticleTypes.Plain, ModParticles.PLAIN.get());
        put(StringUtils.ParticleTypes.Lava, ParticleTypes.LAVA);
        put(StringUtils.ParticleTypes.Sky, ModParticles.SKY.get());
        put(StringUtils.ParticleTypes.Entropy, ModParticles.ENTROPY.get());
        put(StringUtils.ParticleTypes.Sea, ModParticles.SEA.get());


    }};
    public static HashMap<ParticleOptions, String> ParticleToParticleStringMap = new HashMap<>() {{
        put(ParticleTypes.WITCH, StringUtils.ParticleTypes.Witch);
        put(ParticleTypes.COMPOSTER, StringUtils.ParticleTypes.Composter);
        put(ModParticles.LONG_VOLCANO.get(), StringUtils.ParticleTypes.LongVolcano);
        put(ParticleTypes.SCRAPE, StringUtils.ParticleTypes.Scrape);
        put(ModParticles.EFFECT_MANA.get(), StringUtils.ParticleTypes.EffectMana);
        put(ModParticles.RANGE_MANA.get(), StringUtils.ParticleTypes.RangeMana);
        put(ModParticles.DAMAGE_MANA.get(), StringUtils.ParticleTypes.DamageMana);
        put(ModParticles.BREAKDefence_MANA.get(), StringUtils.ParticleTypes.DefencePenetrationMana);
        put(ModParticles.SNOW_MANA.get(), StringUtils.ParticleTypes.SnowMana);
        put(ModParticles.KAZE_MANA.get(), StringUtils.ParticleTypes.KazeMana);
        put(ModParticles.LIGHTNING_MANA.get(), StringUtils.ParticleTypes.LightningMana);
        put(ModParticles.GATHER_MANA.get(), StringUtils.ParticleTypes.GatherMana);
        put(ModParticles.LONG_VOLCANO.get(), StringUtils.ParticleTypes.LongVolcano);
        put(ParticleTypes.HEART, StringUtils.ParticleTypes.Heart);
        put(ParticleTypes.ANGRY_VILLAGER, StringUtils.ParticleTypes.AngryVillager);
        put(ParticleTypes.ASH, StringUtils.ParticleTypes.Ash);
        put(ParticleTypes.LAVA, StringUtils.ParticleTypes.Lava);
        put(ParticleTypes.ENCHANTED_HIT, StringUtils.ParticleTypes.EnchantedHit);
        put(ParticleTypes.TOTEM_OF_UNDYING, StringUtils.ParticleTypes.TotemOfUndying);
        put(ParticleTypes.SNOWFLAKE, StringUtils.ParticleTypes.SnowFlake);
        put(ParticleTypes.WAX_OFF, StringUtils.ParticleTypes.WAX_OFF);
        put(ModParticles.WORLD.get(), StringUtils.ParticleTypes.World);
        put(ModParticles.LONG_ENTROPY.get(), StringUtils.ParticleTypes.Long_Entropy);
        put(ParticleTypes.CHERRY_LEAVES, StringUtils.ParticleTypes.Cherry_Leaves);
        put(ModParticles.LONG_PLAIN.get(), StringUtils.ParticleTypes.Long_Plain);
        put(ModParticles.LONG_FOREST.get(), StringUtils.ParticleTypes.Long_Forest);
        put(ModParticles.LONG_LAKE.get(), StringUtils.ParticleTypes.Long_Lake);
        put(ModParticles.LONG_SNOW.get(), StringUtils.ParticleTypes.Long_Snow);
        put(ModParticles.LONG_SEA.get(), StringUtils.ParticleTypes.Long_Sea);
        put(ModParticles.BLACKFOREST_RECALL.get(), StringUtils.ParticleTypes.Black_Forest);
        put(ParticleTypes.FALLING_WATER, StringUtils.ParticleTypes.FallingWater);
        put(ParticleTypes.DRIPPING_WATER, StringUtils.ParticleTypes.DrippedWater);
        put(ModParticles.LONG_SKY.get(), StringUtils.ParticleTypes.LongSky);
        put(ModParticles.LONG_SPRING.get(), StringUtils.ParticleTypes.LongSpring);
        put(ModParticles.SPRING.get(), StringUtils.ParticleTypes.Spring);
        put(ModParticles.LONG_LIGHTNINGISLAND.get(), StringUtils.ParticleTypes.LongLightning);
        put(ParticleTypes.SMOKE, StringUtils.ParticleTypes.Smoke);
        put(ModParticles.VOLCANO.get(), StringUtils.ParticleTypes.Volcano);
        put(ModParticles.NETHER.get(), StringUtils.ParticleTypes.Nether);
        put(ModParticles.RED_SPELL.get(), StringUtils.ParticleTypes.RedSpell);
        put(ModParticles.LONG_RED_SPELL.get(), StringUtils.ParticleTypes.LongRedSpell);
        put(ModParticles.SNOW.get(), StringUtils.ParticleTypes.Snow);
        put(ParticleTypes.FIREWORK, StringUtils.ParticleTypes.FireWork);
        put(ModParticles.WHITE_SPELL.get(), StringUtils.ParticleTypes.WhiteSpell);
        put(ModParticles.ENTROPY.get(), StringUtils.ParticleTypes.Entropy);
        put(ModParticles.YSR.get(), StringUtils.ParticleTypes.YSR);
        put(ModParticles.YSR1.get(), StringUtils.ParticleTypes.YSR1);
        put(ModParticles.LiuliSpell.get(), StringUtils.ParticleTypes.LiuliSpell);
        put(ModParticles.BIG.get(), StringUtils.ParticleTypes.BIG);
        put(ModParticles.PurpleIronOneTick.get(), StringUtils.ParticleTypes.PurpleIronOneTick);
        put(ModParticles.PurpleIronBig.get(), StringUtils.ParticleTypes.PurpleIronBig);
        put(ParticleTypes.SPORE_BLOSSOM_AIR, StringUtils.ParticleTypes.SporeBlossomAir);
        put(ParticleTypes.EXPLOSION, StringUtils.ParticleTypes.EXPLOSION);
        put(ParticleTypes.FLAME, StringUtils.ParticleTypes.FLAME);

        put(ModParticles.LifeElementParticle.get(), StringUtils.ParticleTypes.LifeElementParticle);
        put(ModParticles.WaterElementParticle.get(), StringUtils.ParticleTypes.WaterElementParticle);
        put(ModParticles.FireElementParticle.get(), StringUtils.ParticleTypes.FireElementParticle);
        put(ModParticles.StoneElementParticle.get(), StringUtils.ParticleTypes.StoneElementParticle);
        put(ModParticles.IceElementParticle.get(), StringUtils.ParticleTypes.IceElementParticle);
        put(ModParticles.LightningElementParticle.get(), StringUtils.ParticleTypes.LightningElementParticle);
        put(ModParticles.WindElementParticle.get(), StringUtils.ParticleTypes.WindElementParticle);

        put(ModParticles.LifeElement1TickParticle.get(), StringUtils.ParticleTypes.LifeElement1TickParticle);
        put(ModParticles.WaterElement1TickParticle.get(), StringUtils.ParticleTypes.WaterElement1TickParticle);
        put(ModParticles.FireElement1TickParticle.get(), StringUtils.ParticleTypes.FireElement1TickParticle);
        put(ModParticles.StoneElement1TickParticle.get(), StringUtils.ParticleTypes.StoneElement1TickParticle);
        put(ModParticles.IceElement1TickParticle.get(), StringUtils.ParticleTypes.IceElement1TickParticle);
        put(ModParticles.LightningElement1TickParticle.get(), StringUtils.ParticleTypes.LightningElement1TickParticle);
        put(ModParticles.WindElement1TickParticle.get(), StringUtils.ParticleTypes.WindElement1TickParticle);

        put(ModParticles.SoraSwordParticle.get(), StringUtils.ParticleTypes.SoraSwordParticle);


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

    public static Map<String, Integer> rollingTickMap = new HashMap<>();

    public static double WorldEntropyIncreaseSpeed = 0.5;

    public static List<WorldEntropy> WorldEntropyPos = new ArrayList<>() {{
        this.add(new WorldEntropy(new Vec3(976, 249, 47), 0, "天空城 "));
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
        SoulList.add(ModItems.huskSoul.get());
        SoulList.add(ModItems.KazeSoul.get());
        SoulList.add(ModItems.SpiderSoul.get());
        SoulList.add(ModItems.PlainSoul.get());
    }

    public static Map<Item, Item> WorldSoulMap = new HashMap<>();

    public static void WorldSoulMapInit() {
        WorldSoulMap.put(ModItems.WorldSoul1.get(), ModItems.WorldSoul2.get());
        WorldSoulMap.put(ModItems.WorldSoul2.get(), ModItems.WorldSoul3.get());
        WorldSoulMap.put(ModItems.WorldSoul3.get(), ModItems.WorldSoul4.get());
        WorldSoulMap.put(ModItems.WorldSoul4.get(), ModItems.worldSoul5.get());
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
    }

    public static Map<String, Style> gemStringStyleMap = new HashMap<>() {{
        this.put("skyGem", CustomStyle.styleOfSky);
        this.put("EvokerGem", CustomStyle.styleOfMana);
        this.put("plainGem", CustomStyle.styleOfPlain);
        this.put("forestGem", CustomStyle.styleOfForest);
        this.put("lakeGem", CustomStyle.styleOfLake);
        this.put("volcanoGem", CustomStyle.styleOfVolcano);
        this.put("snowGem", CustomStyle.styleOfSnow);
        this.put(StringUtils.GemName.FieldGem, CustomStyle.styleOfField);
        this.put(StringUtils.GemName.MineGem, CustomStyle.styleOfMine);
        this.put(StringUtils.GemName.LifeManaGem, CustomStyle.styleOfHealth);
        this.put(StringUtils.GemName.ObsiManaGem, CustomStyle.styleOfMana);
        this.put(StringUtils.GemName.NetherSkeletonGem, CustomStyle.styleOfWither);
        this.put(StringUtils.GemName.MagmaGem, CustomStyle.styleOfPower);
        this.put(StringUtils.GemName.WitherGem, CustomStyle.styleOfWither);
        this.put(StringUtils.GemName.PiglinGem, CustomStyle.styleOfGold);
        this.put(StringUtils.GemName.SakuraGem, CustomStyle.styleOfSakura);
        this.put(StringUtils.GemName.ShipGem, CustomStyle.styleOfShip);
        this.put(StringUtils.GemName.MoonAttackGem, CustomStyle.styleOfMoon);
        this.put(StringUtils.GemName.MoonManaGem, CustomStyle.styleOfMoon1);

        this.put(StringUtils.GemName.SkyGemD, CustomStyle.styleOfSky);
        this.put(StringUtils.GemName.EvokerGemD, CustomStyle.styleOfMana);
        this.put(StringUtils.GemName.PlainGemD, CustomStyle.styleOfPlain);
        this.put(StringUtils.GemName.ForestGemD, CustomStyle.styleOfForest);
        this.put(StringUtils.GemName.LakeGemD, CustomStyle.styleOfLake);
        this.put(StringUtils.GemName.VolcanoGemD, CustomStyle.styleOfVolcano);
        this.put(StringUtils.GemName.SnowGemD, CustomStyle.styleOfSnow);
        this.put(StringUtils.GemName.FieldGemD, CustomStyle.styleOfField);
        this.put(StringUtils.GemName.MineGemD, CustomStyle.styleOfMine);
        this.put(StringUtils.GemName.LifeManaGemD, CustomStyle.styleOfHealth);
        this.put(StringUtils.GemName.ObsiManaGemD, CustomStyle.styleOfMana);
        this.put(StringUtils.GemName.NetherSkeletonGemD, CustomStyle.styleOfWither);
        this.put(StringUtils.GemName.MagmaGemD, CustomStyle.styleOfPower);
        this.put(StringUtils.GemName.WitherGemD, CustomStyle.styleOfWither);
        this.put(StringUtils.GemName.PiglinGemD, CustomStyle.styleOfGold);
        this.put(StringUtils.GemName.SakuraGemD, CustomStyle.styleOfSakura);
        this.put(StringUtils.GemName.ShipGemD, CustomStyle.styleOfShip);
        this.put(StringUtils.GemName.MoonAttackGemD, CustomStyle.styleOfMoon);
        this.put(StringUtils.GemName.MoonManaGemD, CustomStyle.styleOfMoon1);
        this.put(StringUtils.GemName.CastleWeaponGem, CustomStyle.styleOfCastleCrystal);
        this.put(StringUtils.GemName.CastleArmorGem, CustomStyle.styleOfCastleCrystal);
        this.put(StringUtils.GemName.QingMingGem, CustomStyle.styleOfHealth);
        this.put(StringUtils.GemName.LabourDayGem, CustomStyle.styleOfField);
    }};

    public static List<ForestPowerEffectMob> ForestPowerEffectMobList = new ArrayList<>();

    public static Map<Mob, LakePowerEffect> LakePowerEffectMobMap = new HashMap<>();

    public static int PFSecKillCount = 0;
    public static int LakeSecKillCount = 0;
    public static int VolcanoSecKillCount = 0;

    public static Map<Item, String> SoulBagsMap = new HashMap<>();

    public static void SoulBagsInit() {
        SoulBagsMap.put(ModItems.PlainSoul.get(), StringUtils.PlainSoulCount);
        SoulBagsMap.put(ModItems.ForestSoul.get(), StringUtils.ForestSoulCount);
        SoulBagsMap.put(ModItems.LakeSoul.get(), StringUtils.LakeSoulCount);
        SoulBagsMap.put(ModItems.VolcanoSoul.get(), StringUtils.VolcanoSoulCount);
    }

    public static Map<Player, PlayerTeam> playerTeamMap = new HashMap<>();

    public static Map<Player, List<PlayerTeam>> TeamInvitePlayerMap = new HashMap<>();

    public static Map<Player, List<PlayerTeam>> PlayerRequestTeamMap = new HashMap<>();

    public static int[] ps = {1, 1, 1};

    public static List<Instance> instanceList = new ArrayList<>() {{
        add(new Instance(Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain),
                Component.literal("普莱尼岛").withStyle(CustomStyle.styleOfPlain),
                25, ps, 1, null, false, null,
                0, 0, new Vec3(347, 70, 1198), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("唤雷塔").withStyle(CustomStyle.styleOfLightingIsland),
                Component.literal("唤雷岛").withStyle(CustomStyle.styleOfLightingIsland),
                30, ps, 1, null, false, null,
                0, 0, new Vec3(1430, 84, 566), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("Main1Boss").withStyle(CustomStyle.styleOfSnow),
                Component.literal("玉山中心祭坛").withStyle(CustomStyle.styleOfSnow),
                30, ps, 1, null, false, null,
                0, 0, new Vec3(-171, 115.5, 1424), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("下界征讨").withStyle(CustomStyle.styleOfNether),
                Component.literal("下界").withStyle(CustomStyle.styleOfNether),
                50, ps, 1, null, false, null,
                0, 0, new Vec3(2, 80.5, 249), ServerLevel.NETHER, 0));

        add(new Instance(Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura),
                Component.literal("绯樱岛").withStyle(CustomStyle.styleOfSakura),
                80, ps, 1, null, false, null,
                0, 0, new Vec3(1934, 167.5, 1047), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("冰霜骑士").withStyle(CustomStyle.styleOfIce),
                Component.literal("极寒之地").withStyle(CustomStyle.styleOfIce),
                80, ps, 1, null, false, null,
                0, 0, new Vec3(332, 63, 2325), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("旧世复生魔王").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("封魔庭院").withStyle(CustomStyle.styleOfSakura),
                110, ps, 1, null, false, null,
                0, 0, new Vec3(1906, 112, 1242), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("阿尔忒弥斯").withStyle(CustomStyle.styleOfMoon1),
                Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon1),
                120, ps, 1, null, false, null,
                0, 0, new Vec3(167, 186, 1622), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("新世禁法魔物").withStyle(CustomStyle.styleOfBloodMana),
                Component.literal("绯樱岛").withStyle(CustomStyle.styleOfSakura),
                120, ps, 1, null, false, null,
                0, 0, new Vec3(1431, 72, 1161), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("暗黑城堡 - 1层").withStyle(CustomStyle.styleOfCastle),
                Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                140, ps, 1, null, false, null,
                0, 0, new Vec3(898, 70, 1033), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("暗黑城堡 - 2层").withStyle(CustomStyle.styleOfCastle),
                Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                160, ps, 1, null, false, null,
                0, 0, new Vec3(787.5, 84, 1028.5), ServerLevel.OVERWORLD, 0));

        add(new Instance(Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron),
                Component.literal("决战之巅 - 紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron),
                180, ps, 1, null, false, null,
                0, 0, new Vec3(2174.5, 84, 1196.5), ServerLevel.OVERWORLD, 0));

    }};

    public static List<Style> levelStyleList = new ArrayList<>() {{
        add(CustomStyle.styleOfPlain);
        add(CustomStyle.styleOfMana);
        add(CustomStyle.styleOfSakura);
        add(CustomStyle.styleOfEntropy);
        add(CustomStyle.styleOfWorld);
        add(CustomStyle.styleOfPower);
        add(CustomStyle.styleOfMoon1);
        add(CustomStyle.styleOfPurpleIron);
        add(CustomStyle.styleOfMoon);
        add(CustomStyle.styleOfLife);
        add(CustomStyle.styleOfLightning);
    }};

    public static boolean NetherInstanceFlag = false;

    public static int[] instanceKillCount = new int[instanceList.size()];

    public static Map<String, Integer> playerAttackRingMap = new HashMap<>();
    public static Map<String, Integer> playerManaAttackRingMap = new HashMap<>();
    public static Map<String, Integer> playerHealthRingMap = new HashMap<>();
    public static Map<String, Integer> playerDefenceRingMap = new HashMap<>();

    public static Map<String, Item> ManaCoreMap = new HashMap<>();

    public static void setManaCoreMap() {
        ManaCoreMap.put(StringUtils.ManaCore.SeaCore, ModItems.SeaManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.BlackForestCore, ModItems.BlackForestManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.KazeCore, ModItems.KazeManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.SakuraCore, ModItems.SakuraCore.get());

    }

    public static Map<String, Boolean> playerSakuraBowMap = new HashMap<>();

    public static Map<String, Boolean> playerSakuraCoreMap = new HashMap<>();

    public static Map<String, Integer> playerClickBlockCoolDown = new HashMap<>();

    public static List<BlockSP> playerDigPos = new ArrayList<>();

    public static int playerDigCount = 0;

    public static Map<String, Integer> deleteCommandSecurity = new HashMap<>();

    public static Map<String, ItemStack> playerDailyMissionContent = new HashMap<>();
    public static Map<String, Integer> playerDailyMissionContentNum = new HashMap<>();

    public static Map<String, ItemStack> playerReputationMissionContent = new HashMap<>();
    public static Map<String, Integer> playerReputationMissionContentNum = new HashMap<>();
    public static Map<String, String> playerReputationMissionStartTime = new HashMap<>();
    public static Map<String, Integer> playerReputationMissionPunishLevel = new HashMap<>();
    public static int[] playerReputationMissionPunishTime = {
            0, 3, 10, 30, 60, 60 * 2, 60 * 4, 60 * 8, 60 * 12, 60 * 24
    };
    public static Map<String, String> playerReputationMissionAllowRequestTime = new HashMap<>();

    public static List<Item> missionItemList = new ArrayList<>();

    public static void setMissionItemList() {
        Item[] items = new Item[]{
                ModItems.PlainSoul.get(), ModItems.ForestSoul.get(), ModItems.LakeSoul.get(), ModItems.MineSoul.get(),
                ModItems.SpiderSoul.get(), ModItems.VolcanoSoul.get(), ModItems.EvokerSoul.get(), ModItems.wolfLeather.get(),
                ModItems.SkySoul.get(), ModItems.SlimeBall.get(), ModItems.PurpleIronPiece.get(), ModItems.Ruby.get(),
                ModItems.KazeSoul.get(), ModItems.huskSoul.get(), ModItems.LightningSoul.get(), ModItems.SeaSoul.get(),
                ModItems.witherSkeletonSoul.get(), ModItems.magmaSoul.get(), ModItems.netherSkeletonSoul.get(), ModItems.PigLinSoul.get(),

                ModItems.SnowSoul.get(), ModItems.RecallPiece.get(), ModItems.ShipPiece.get(), ModItems.BloodManaSoul.get(),
                ModItems.EarthManaSoul.get(), ModItems.SakuraPetal.get(), ModItems.EnderMiteSoul.get(), ModItems.ShulkerSoul.get(),

                ModItems.BeaconSoul.get(), ModItems.BlazeSoul.get(), ModItems.TreeSoul.get(), ModItems.StarSoul.get(),
                C7Items.boneImpSoul.get()
        };
        Collections.addAll(missionItemList, items);
    }

    public static int getMissionItemBoundary(int level) {
        if (level < 40) return 3;
        if (level < 60) return 5;
        if (level < 75) return 7;
        if (level < 90) return 15;
        if (level < 110) return 19;
        if (level < 130) return 23;
        if (level < 160) return 27;
        if (level < 190) return 30;
        return 32;
    }

    public static List<Item> ReputationStoreItemList = new ArrayList<>();

    public static void setReputationStoreItemList() {
        ReputationStoreItemList.add(ModItems.ReputationMedal.get());
        ReputationStoreItemList.add(ModItems.notePaper.get());
        ReputationStoreItemList.add(ModItems.GoldCoinBag.get());
        ReputationStoreItemList.add(ModItems.commonLotteries.get());
        ReputationStoreItemList.add(ModItems.UnCommonLotteries.get());
    }

    public static Map<Item, Integer> ReputationStorePrice = new HashMap<>();

    public static void setReputationStorePrice() {
        ReputationStorePrice.put(ModItems.ReputationMedal.get(), 64);
        ReputationStorePrice.put(ModItems.notePaper.get(), 16);
        ReputationStorePrice.put(ModItems.GoldCoinBag.get(), 16);
        ReputationStorePrice.put(ModItems.commonLotteries.get(), 32);
        ReputationStorePrice.put(ModItems.UnCommonLotteries.get(), 64);
    }

    public static Map<BlockPos, String> whoIsUsingBlock = new HashMap<>();

    public static List<Block> canBeDigBlockList = new ArrayList<>() {{
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
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.POINTED_DRIPSTONE
        };
        this.addAll(Arrays.asList(blocks));
    }};

    public static Map<Block, Double> mineExpMap = new HashMap<>() {{
        put(Blocks.COAL_ORE, 0.01);
        put(Blocks.DEEPSLATE_COAL_ORE, 0.01);
        put(Blocks.COPPER_ORE, 0.02);
        put(Blocks.DEEPSLATE_COPPER_ORE, 0.02);
        put(Blocks.RAW_COPPER_BLOCK, 0.02);
        put(Blocks.IRON_ORE, 0.03);
        put(Blocks.DEEPSLATE_IRON_ORE, 0.03);
        put(Blocks.RAW_IRON_BLOCK, 0.03);
        put(Blocks.LAPIS_ORE, 0.03);
        put(Blocks.DEEPSLATE_LAPIS_ORE, 0.03);
        put(Blocks.REDSTONE_ORE, 0.03);
        put(Blocks.DEEPSLATE_REDSTONE_ORE, 0.03);
        put(Blocks.GOLD_ORE, 0.04);
        put(Blocks.DEEPSLATE_GOLD_ORE, 0.04);
        put(Blocks.RAW_GOLD_BLOCK, 0.04);
        put(Blocks.DIAMOND_ORE, 0.05);
        put(Blocks.DEEPSLATE_DIAMOND_ORE, 0.05);
        put(Blocks.EMERALD_ORE, 0.05);
        put(Blocks.DEEPSLATE_EMERALD_ORE, 0.05);
        put(Blocks.AMETHYST_BLOCK, 0.01);
    }};

    public static Map<Block, Item> mineDropMap = new HashMap<>() {{
        put(Blocks.COAL_ORE, Items.COAL);
        put(Blocks.DEEPSLATE_COAL_ORE, Items.COAL);
        put(Blocks.COPPER_ORE, Items.RAW_COPPER);
        put(Blocks.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER);
        put(Blocks.RAW_COPPER_BLOCK, Items.RAW_COPPER);
        put(Blocks.IRON_ORE, Items.RAW_IRON);
        put(Blocks.DEEPSLATE_IRON_ORE, Items.RAW_IRON);
        put(Blocks.RAW_IRON_BLOCK, Items.RAW_IRON);
        put(Blocks.LAPIS_ORE, Items.LAPIS_LAZULI);
        put(Blocks.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI);
        put(Blocks.REDSTONE_ORE, Items.REDSTONE);
        put(Blocks.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE);
        put(Blocks.GOLD_ORE, Items.RAW_GOLD);
        put(Blocks.DEEPSLATE_GOLD_ORE, Items.RAW_GOLD);
        put(Blocks.RAW_GOLD_BLOCK, Items.RAW_GOLD);
        put(Blocks.DIAMOND_ORE, Items.DIAMOND);
        put(Blocks.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND);
        put(Blocks.EMERALD_ORE, Items.EMERALD);
        put(Blocks.DEEPSLATE_EMERALD_ORE, Items.EMERALD);
    }};

    public static Map<String, Queue<BlockAndResetTime>> noMineDigMap = new HashMap<>();
    public static Map<String, Queue<BlockAndResetTime>> blockPlaceMap = new HashMap<>();

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

    public static List<BlockPos> rewardChestPos = new ArrayList<>() {{
        add(new BlockPos(393, 62, 949));
        add(new BlockPos(386, 63, 924));
        add(new BlockPos(356, 72, 870));
        add(new BlockPos(344, 61, 957));
        add(new BlockPos(312, 63, 977));
        add(new BlockPos(227, 69, 920));
        add(new BlockPos(285, 62, 993));
        add(new BlockPos(234, 72, 1061));
        add(new BlockPos(302, 65, 1104));
        add(new BlockPos(221, 107, 1088));
        add(new BlockPos(255, 104, 1208));
        add(new BlockPos(266, 136, 1262));
        // 以上为平原地区奖励箱

        add(new BlockPos(105, 121, 1166));
        add(new BlockPos(152, 87, 1114));
        add(new BlockPos(125, 69, 1060));
        add(new BlockPos(174, 75, 1018));
        add(new BlockPos(141, 71, 954));
        add(new BlockPos(36, 63, 929));
        add(new BlockPos(-28, 77, 925));
        add(new BlockPos(-64, 80, 955));
        add(new BlockPos(-96, 96, 968));
        // 以上为森林地区奖励箱

        add(new BlockPos(36, 56, 932));
        add(new BlockPos(30, 19, 1019));
        add(new BlockPos(77, -20, 979));
        add(new BlockPos(48, -45, 982));
        add(new BlockPos(-6, -53, 1012));
        // 以上为湖泊地区奖励箱

        add(new BlockPos(33, -48, 1012));
        add(new BlockPos(15, -54, 1028));
        add(new BlockPos(-20, -54, 1070));
        add(new BlockPos(8, -54, 1095));
        add(new BlockPos(38, -52, 1116));
        add(new BlockPos(51, -49, 1081));
        // 以上为火山地区奖励箱

        add(new BlockPos(113, 109, 1039));
        add(new BlockPos(77, 115, 1039));
        add(new BlockPos(76, 116, 1028));
        add(new BlockPos(43, 113, 1051));
        add(new BlockPos(51, 118, 1073));
        add(new BlockPos(34, 128, 1053));
        add(new BlockPos(20, 154, 1072));
        add(new BlockPos(15, 119, 1047));
        add(new BlockPos(31, 117, 1032));
        add(new BlockPos(44, 118, 1010));
        add(new BlockPos(19, 118, 1063));
        add(new BlockPos(57, 126, 1044));
        // 以上为天空城地区奖励箱
    }};

    public static Map<String, Map<BlockPos, Calendar>> playerRewardChestCoolDown = new HashMap<>();

    public static Map<String, BlockPos> playerIsUsingBlockBlockPosMap = new HashMap<>();

    public static List<ItemStack> rewardItemList = new ArrayList<>();

    public static void setRewardItemList() {
        rewardItemList.add(new ItemStack(ModItems.Ps_Bottle0.get(), 1));
        rewardItemList.add(new ItemStack(ModItems.CrudeCoal.get(), 16));
        rewardItemList.add(new ItemStack(ModItems.ForgingStone1.get(), 1));
        rewardItemList.add(new ItemStack(ModItems.RevelationBook.get(), 1));
        rewardItemList.add(new ItemStack(ModItems.silverCoin.get(), 32));
        rewardItemList.add(new ItemStack(ModItems.WorldSoul2.get(), 1));
    }

    public static int TimeEventFlag = -1;

    public static List<Recall> recallList = new ArrayList<>() {{
        add(huskRecall);
        add(forestRecall);
        add(kazeRecall);
        add(lightningRecall);
        add(netherRecall);
        add(seaRecall);
        add(snowRecall);
        add(spiderRecall);
        add(volcanoRecall);
    }};

    public static Map<Player, Integer> MineShieldEffect = new HashMap<>();

    public static Map<Mob, Integer> shipSwordTime = new HashMap<>();
    public static Map<Mob, Integer> shipSwordEffect = new HashMap<>();

    public static Map<Player, Integer> ShipSceptreWaterBlockNum = new HashMap<>();

    public static Map<Player, Integer> SakuraBowEffectMap = new HashMap<>();

    public static Map<Player, Integer> IceSwordEffectMap = new HashMap<>();
    public static Map<Player, Double> IceSwordEffectNumMap = new HashMap<>();
    public static Map<Player, Integer> IceBowEffectMap = new HashMap<>();
    public static Map<Player, Double> IceBowEffectNumMap = new HashMap<>();
    public static Map<Player, Integer> IceSceptreEffectMap = new HashMap<>();
    public static Map<Player, Double> IceSceptreEffectNumMap = new HashMap<>();

    public static Map<Player, Integer> DingCoolDown = new HashMap<>();
    public static Map<Player, Integer> DingDingCoolDown = new HashMap<>();

    public static Map<Player, Double> PlayerSpringRingAttackAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringRingManaAttackAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringRingDefencePenetration0Attribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringRingManaPenetration0Attribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringRingExpUpAttribute = new HashMap<>();
    public static Map<Player, Integer> PlayerSpringRingLevelRequire = new HashMap<>();

    public static Map<Player, Double> PlayerSpringHandAttackAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringHandDefenceAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringHandMaxHealthAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringHandDefencePenetraionAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringHandExpUpAttribute = new HashMap<>();
    public static Map<Player, Integer> PlayerSpringHandLevelRequire = new HashMap<>();

    public static Map<Player, Double> PlayerSpringBeltAttackAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBeltDefencePenetration0Attribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBeltSwiftAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBeltMovementAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBeltExpUpAttribute = new HashMap<>();
    public static Map<Player, Integer> PlayerSpringBeltLevelRequire = new HashMap<>();

    public static Map<Player, Double> PlayerSpringBraceletAttackAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBraceletManaPenetration0Attribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBraceletManaRecoverAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBraceletMaxManaAttribute = new HashMap<>();
    public static Map<Player, Double> PlayerSpringBraceletExpUpAttribute = new HashMap<>();
    public static Map<Player, Integer> PlayerSpringBraceletLevelRequire = new HashMap<>();

    public static Map<Player, Integer> PlayerAttackTime = new HashMap<>();
    public static Map<Player, Integer> PlayerArrowAttackTime = new HashMap<>();
    public static Map<Player, Integer> PlayerManaAttackTime = new HashMap<>();

    public static Map<Player, Integer> PlayerFireWorkGunEffect = new HashMap<>();

    public static List<PlayerTeam> ChallengingPlayerTeam = new ArrayList<>();

    public static Map<Player, Boolean> PlayerAFKMap = new HashMap<>();
    public static Map<Player, Integer> PlayerAFKSecondsMap = new HashMap<>();

    public static Map<Player, Integer> PlayerFireWorkFightCoolDown = new HashMap<>();

    public static Map<Player, Integer> PlayerSpringAttackCoolDown = new HashMap<>();
    public static Map<Mob, Integer> MobSpringAttackTick = new HashMap<>();
    public static Map<Mob, Integer> MobSpringAttackEffect = new HashMap<>();
    public static Map<Player, Integer> PlayerSpringSwiftCoolDown = new HashMap<>();
    public static Map<Mob, Integer> MobSpringSwiftTick = new HashMap<>();
    public static Map<Mob, Integer> MobSpringSwiftEffect = new HashMap<>();
    public static Map<Player, Integer> PlayerSpringManaCoolDown = new HashMap<>();
    public static Map<Mob, Integer> MobSpringManaTick = new HashMap<>();
    public static Map<Mob, Integer> MobSpringManaEffect = new HashMap<>();

    public static double[] SpringEffect = {
            0.2, 0.25, 0.3, 0.4
    };

    public static Map<Player, Integer> SpringScaleTime = new HashMap<>();
    public static Map<Player, Integer> SpringScaleEffect = new HashMap<>();

    public static Map<Player, PosAndLastTime> EndRune2Pos = new HashMap<>();

    public static Map<Mob, Integer> NetherBoneMealPowerEffectMap = new HashMap<>();

    public static Map<Player, Double> PiglinPowerAp = new HashMap<>();

    public static Map<Player, Boolean> playerRecycleMap = new HashMap<>();

    public static List<String> GiantPlayerList = new ArrayList<>();
    public static int GiantHour = -1;
    public static Giant giant;
    public static List<Boss2Damage> GiantDamageList = new ArrayList<>();
    public static List<ItemStack> GiantCommonReward = new ArrayList<>();
    public static boolean GiantFlag = false;

    public static void setGiantCommonReward() {
        GiantCommonReward.add(new ItemStack(ModItems.CrudeCoal.get(), 64));
        GiantCommonReward.add(new ItemStack(ModItems.gemPiece.get(), 32));
        GiantCommonReward.add(new ItemStack(ModItems.goldCoin.get(), 10));
        GiantCommonReward.add(new ItemStack(ModItems.Ps_Bottle1.get(), 2));
        GiantCommonReward.add(new ItemStack(ModItems.CropBag.get(), 2));
    }

    public static Map<Player, Boolean> SoulSwordMap = new HashMap<>();

    public static Map<Player, Boolean> BloodManaCurios = new HashMap<>();
    public static Map<Player, Boolean> EarthManaCurios = new HashMap<>();

    public static Map<Player, Boolean> DevilBloodManaCurios = new HashMap<>();
    public static Map<Player, Boolean> DevilEarthManaCurios = new HashMap<>();

    public static Map<String, Integer> PlayerDeadTimeMap = new HashMap<>();

    public static Map<Player, Integer> MeteoriteDefenceMap = new HashMap<>();
    public static Map<Player, Integer> MeteoriteDefenceTimeMap = new HashMap<>();

    public static Map<Player, Integer> MeteoriteAttackTimeMap = new HashMap<>();

    public static Map<Player, Integer> MeteoritePenetrationTimeMap = new HashMap<>();

    public static Map<Player, Integer> PlayerSoulSceptreCoolDown = new HashMap<>();

    public static Map<Mob, Integer> SnowShieldMobEffectMap = new HashMap<>();
    public static Map<Player, Double> SnowShieldPlayerEffectMap = new HashMap<>();
    public static Map<Player, Integer> SnowShieldPlayerEffectTickMap = new HashMap<>();

    public static Map<Mob, Integer> WitherBookMobEffectTick = new HashMap<>();
    public static Map<Player, Integer> WitherBookPlayerEffectTick = new HashMap<>();
    public static Map<Player, Double> WitherBookPlayerEffectNum = new HashMap<>();

    public static Map<Player, Queue<Vec3>> EarthBookPlayerPosMap = new HashMap<>();
    public static Map<Player, Integer> EarthBookPlayerEffectMap = new HashMap<>();

    public static Map<Player, Integer> IceBookCoolDownMap = new HashMap<>();
    public static Map<Mob, Integer> IceBookMobEffectTickMap = new HashMap<>();
    public static Map<Mob, Player> IceBookMobEffectPlayerMap = new HashMap<>();

    public static List<Item> weaponList = new ArrayList<>();
    public static List<Item> armorList = new ArrayList<>();
    public static List<Item> curiosList = new ArrayList<>();
    public static List<Item> customizedList = new ArrayList<>();
    public static List<Item> uniformList = new ArrayList<>();

    public static Map<Player, List<ItemStack>> playerCuriosListMap = new HashMap<>();

    public static Map<Player, Integer> BlackForestSwordActiveMap = new HashMap<>();
    public static Map<Player, Integer> SeaSwordActiveMap = new HashMap<>();

    public static Map<Player, Map<Mob, Integer>> playerLaserCoolDown = new HashMap<>();

    public static ServerBossEvent GiantBossInfo = null;

    public static Map<Player, Integer> LastTimeInstance = new HashMap<>();
    public static Map<Player, Integer> LastTimeDifficulty = new HashMap<>();

    public static Map<Player, Double> playerManaDamageBeforeTransform = new HashMap<>();

    public static int ServerStopTick = -1;

    public static class LogTypes {
        public static String worldSoul5 = "obtained_worldsoul5";
        public static String dropped = "dropped";
        public static String itemUsed = "itemUsed";
        public static String cost = "cost";
        public static String killed = "killed";
        public static String speciousLogin = "speciousLogin";
    }
}
