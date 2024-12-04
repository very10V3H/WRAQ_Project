package fun.wraq.common.util;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.struct.*;
import fun.wraq.common.util.struct.BowSkillStruct.BowSkill3;
import fun.wraq.common.util.struct.BowSkillStruct.BowSkill6;
import fun.wraq.common.util.struct.ManaSkillStruct.ManaSkill3;
import fun.wraq.common.util.struct.ManaSkillStruct.ManaSkill6;
import fun.wraq.common.util.struct.SwordSkillStruct.SwordSkill13;
import fun.wraq.common.util.struct.SwordSkillStruct.SwordSkill3;
import fun.wraq.common.util.struct.SwordSkillStruct.SwordSkill6;
import fun.wraq.entities.entities.Boss2.Boss2;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.networking.unSorted.BlockLimit;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.Recall;
import fun.wraq.series.overworld.chapter1.forest.ForestPowerEffectMob;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    public static Map<Item, Double> xpLevelDefence = new HashMap<>();
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
    public static Map<Item, Double> healingAmplification = new HashMap<>();
    public static Map<Item, Double> coolDownDecrease = new HashMap<>();
    public static Map<Item, Double> manaDamage = new HashMap<>();
    public static Map<Item, Double> xpLevelManaDamage = new HashMap<>();
    public static Map<Item, Double> percentManaDamageEnhance = new HashMap<>();
    public static Map<Item, Double> manaDefence = new HashMap<>();
    public static Map<Item, Double> xpLevelManaDefence = new HashMap<>();
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
    public static Map<Item, Double> percentHealthRecover = new HashMap<>();

    public static Map<Item, Double> mainHandTag = new HashMap<>();
    public static Map<Item, Double> swordTag = new HashMap<>();
    public static Map<Item, Double> bowTag = new HashMap<>();
    public static Map<Item, Double> sceptreTag = new HashMap<>();
    public static Map<Item, Double> offHandTag = new HashMap<>();
    public static Map<Item, Double> shieldTag = new HashMap<>();
    public static Map<Item, Double> armorTag = new HashMap<>();
    public static Map<Item, Double> powerTag = new HashMap<>();
    public static Map<Item, Double> mobLevel = new HashMap<>();
    public static Map<Item, Double> passiveEquipTag = new HashMap<>();
    public static Map<Item, Integer> levelRequire = new HashMap<>();
    public static Map<Item, Integer> gemsTag = new HashMap<>();

    public static List<LivingEntity> MonsterAttributeDataProvider = new ArrayList<>();

    public static int AttributeDataTick = 0;
    public static Entity EntityCopy;

    public static List<BlockLimit> blockLimitList = new ArrayList<>();

    public static int tick = 0;
    public static Player Vplayer;
    public static boolean Security = true;
    public static double security0 = 64;
    public static double security1 = 64;
    public static double security2 = 64;
    public static double security3 = 64;

    // Entity

    public static Stray[] IceHunterForIceKnight = new Stray[15];

    public static boolean OverWorldLevelIsNight = false;

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
    public static Map<MobEffect, Boolean> MobEffectMap = new HashMap<>();

    public static int NSController = -1;
    public static List<Player> NSPlayerController = new ArrayList<>();

    public static List<MarketItemInfo> marketItemInfos = new ArrayList<>();
    public static Map<String, Double> marketPlayerInfos = new HashMap<>();

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

        put(StringUtils.ParticleTypes.END_ROD, ParticleTypes.END_ROD);

        put(StringUtils.ParticleTypes.BUBBLE, ParticleTypes.BUBBLE);
        put(StringUtils.ParticleTypes.BUBBLE_POP, ParticleTypes.BUBBLE_POP);
        put(StringUtils.ParticleTypes.BUBBLE_COLUMN_UP, ParticleTypes.BUBBLE_COLUMN_UP);
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

        put(ParticleTypes.END_ROD, StringUtils.ParticleTypes.END_ROD);

        put(ParticleTypes.BUBBLE, StringUtils.ParticleTypes.BUBBLE);
        put(ParticleTypes.BUBBLE_POP, StringUtils.ParticleTypes.BUBBLE_POP);
        put(ParticleTypes.BUBBLE_COLUMN_UP, StringUtils.ParticleTypes.BUBBLE_COLUMN_UP);
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
        add(new WorldEntropy(new Vec3(976, 249, 47), 0, "天空城 "));
        add(new WorldEntropy(new Vec3(1774, 75, 339), 0, "旭升岛 "));
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

    public static List<ForestPowerEffectMob> ForestPowerEffectMobList = new ArrayList<>();

    public static Map<Item, String> SoulBagsMap = new HashMap<>();

    public static void SoulBagsInit() {
        SoulBagsMap.put(ModItems.PlainSoul.get(), StringUtils.PlainSoulCount);
        SoulBagsMap.put(ModItems.ForestSoul.get(), StringUtils.ForestSoulCount);
        SoulBagsMap.put(ModItems.LakeSoul.get(), StringUtils.LakeSoulCount);
        SoulBagsMap.put(ModItems.VolcanoSoul.get(), StringUtils.VolcanoSoulCount);
    }

    public static WeakHashMap<Player, PlayerTeam> playerTeamMap = new WeakHashMap<>();

    public static WeakHashMap<Player, List<PlayerTeam>> TeamInvitePlayerMap = new WeakHashMap<>();

    public static WeakHashMap<Player, List<PlayerTeam>> PlayerRequestTeamMap = new WeakHashMap<>();

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
        add(CustomStyle.styleOfMoontain);
        add(CustomStyle.styleOfLightning);
    }};

    public static Style getLevelStyle(int level) {
        return Utils.levelStyleList.get(Math.min(Utils.levelStyleList.size() - 1, level / 25));
    }

    public static int[] instanceKillCount = new int[instanceList.size()];

    public static Map<String, Item> ManaCoreMap = new HashMap<>();

    public static void setManaCoreMap() {
        ManaCoreMap.put(StringUtils.ManaCore.SeaCore, ModItems.SeaManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.BlackForestCore, ModItems.BlackForestManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.KazeCore, ModItems.KazeManaCore.get());
        ManaCoreMap.put(StringUtils.ManaCore.SakuraCore, ModItems.SakuraCore.get());
    }

    public static Map<String, Boolean> playerSakuraCoreMap = new HashMap<>();

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
        ReputationStoreItemList.add(ModItems.GoldCoinBag.get());
        ReputationStoreItemList.add(ModItems.commonLotteries.get());
        ReputationStoreItemList.add(ModItems.UnCommonLotteries.get());
    }

    public static Map<Item, Integer> ReputationStorePrice = new HashMap<>();

    public static void setReputationStorePrice() {
        ReputationStorePrice.put(ModItems.ReputationMedal.get(), 64);
        ReputationStorePrice.put(ModItems.GoldCoinBag.get(), 16);
        ReputationStorePrice.put(ModItems.commonLotteries.get(), 32);
        ReputationStorePrice.put(ModItems.UnCommonLotteries.get(), 64);
    }

    public static Map<BlockPos, Player> whoIsUsingBlock = new HashMap<>();

    public static Map<String, Queue<BlockAndResetTime>> noMineDigMap = new HashMap<>();
    public static Map<String, Queue<BlockAndResetTime>> blockPlaceMap = new HashMap<>();

    public static List<BlockPos> posEvenBeenDigOrPlace = new ArrayList<>();

    public static Queue<BlockAndResetTime> worldMineList = new LinkedList<>();

    public static Queue<BlockAndResetTime> netherMineList = new LinkedList<>();

    public static Queue<ItemEntityAndResetTime> valueItemEntity = new LinkedList<>();

    public static Queue<BlockAndResetTime> worldWoodList = new LinkedList<>();

    public static Map<String, Component> playerNameMap = new HashMap<>();

    public static Map<String, Integer> dayFishCount = new HashMap<>();

    public static Map<String, Integer> dayMineCount = new HashMap<>();

    public static Map<String, Integer> dayLopCount = new HashMap<>();

    public static Map<String, Integer> dayCropCount = new HashMap<>();

    public static Map<String, Integer> dayOnlineCount = new HashMap<>();

    public static Map<String, BlockPos> playerIsUsingBlockBlockPosMap = new HashMap<>();

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

    public static WeakHashMap<Player, Integer> MineShieldEffect = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> DingCoolDown = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> DingDingCoolDown = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> PlayerAttackTime = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerArrowAttackTime = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerManaAttackTime = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> PlayerFireWorkGunEffect = new WeakHashMap<>();

    public static List<PlayerTeam> ChallengingPlayerTeam = new ArrayList<>();

    public static WeakHashMap<Player, Boolean> PlayerAFKMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerAFKSecondsMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> PlayerFireWorkFightCoolDown = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> PlayerSpringAttackCoolDown = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> MobSpringAttackTick = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> MobSpringAttackEffect = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerSpringSwiftCoolDown = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> MobSpringSwiftTick = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> MobSpringSwiftEffect = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerSpringManaCoolDown = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> MobSpringManaTick = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> MobSpringManaEffect = new WeakHashMap<>();

    public static double[] SpringEffect = {
            0.2, 0.25, 0.3, 0.4
    };

    public static WeakHashMap<Player, Integer> SpringScaleTime = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> SpringScaleEffect = new WeakHashMap<>();

    public static WeakHashMap<Player, PosAndLastTime> EndRune2Pos = new WeakHashMap<>();

    public static WeakHashMap<Player, Boolean> playerRecycleMap = new WeakHashMap<>();

    public static List<String> GiantPlayerList = new ArrayList<>();
    public static Giant giant;
    public static List<Boss2Damage> GiantDamageList = new ArrayList<>();
    public static List<ItemStack> GiantCommonReward = new ArrayList<>();

    public static WeakHashMap<Player, Boolean> SoulSwordMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Boolean> BloodManaCurios = new WeakHashMap<>();
    public static WeakHashMap<Player, Boolean> EarthManaCurios = new WeakHashMap<>();

    public static WeakHashMap<Player, Boolean> DevilBloodManaCurios = new WeakHashMap<>();
    public static WeakHashMap<Player, Boolean> DevilEarthManaCurios = new WeakHashMap<>();

    public static Map<String, Integer> PlayerDeadTimeMap = new HashMap<>();

    public static WeakHashMap<Player, Integer> MeteoriteDefenceMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> MeteoriteDefenceTimeMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> MeteoriteAttackTimeMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> MeteoritePenetrationTimeMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> PlayerSoulSceptreCoolDown = new WeakHashMap<>();

    public static WeakHashMap<Mob, Integer> SnowShieldMobEffectMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> SnowShieldPlayerEffectMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> SnowShieldPlayerEffectTickMap = new WeakHashMap<>();

    public static List<Item> weaponList = new ArrayList<>();
    public static List<Item> armorList = new ArrayList<>();
    public static List<Item> curiosList = new ArrayList<>();
    public static List<Item> customizedList = new ArrayList<>();
    public static List<Item> uniformList = new ArrayList<>();

    public static WeakHashMap<Player, Integer> BlackForestSwordActiveMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> SeaSwordActiveMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Map<Mob, Integer>> playerLaserCoolDown = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> LastTimeInstance = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> LastTimeDifficulty = new WeakHashMap<>();

    public static WeakHashMap<Player, Double> playerManaDamageBeforeTransform = new WeakHashMap<>();

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
