package com.very.wraq.valueAndTools.Utils;

import com.very.wraq.events.client.ClientAttackEvent;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.Items.Prefix.PrefixInfo;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Utils.Struct.*;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

import static com.very.wraq.series.overWorld.MainStory_I.ManaBook.ManaNote.Name;
import static com.very.wraq.series.overWorld.MainStory_I.ManaBook.ManaNote.styles;

public class ClientUtils {
    public static boolean UseOfSnowSword = false;

    public static boolean UseOfLakeSword = false;

    public static boolean MusicPlayerIdol = false;
    public static int ParticleRandom = 0;
    public static boolean ParticleFlag = false;
    public static double X;
    public static double Y;
    public static double Z;
    public static double X1;
    public static double Y1;
    public static double Z1;

    public static boolean UseOfSnowSwordParticle = false;
    public static boolean UseOfBow = false;
    public static boolean UseOfSkyBow = false;
    public static int PacketsLimit = 10;
    public static Map<UUID,Integer> QuartzSabreClientPlayerUUIDINDEX = new HashMap<>();
    public static Map<UUID,Integer> QuartzSabreClientMonsterUUIDINDEX = new HashMap<>();
    public static boolean GetQuartzSabreMonster = false;
    public static int QuartzSabreParticlePos = -1;
    public static double QuartzSabreParticleIndexX = 0;
    public static double QuartzSabreParticleIndexY = 0;
    public static double QuartzSabreParticleIndexZ = 0;
    public static int Sounds = -1;
    public static int[] DailyMission = new int[20];

    public static double AttackDamageC = 0;
    public static double BreakDefenceC = 0;
    public static double CritRateC = 0;
    public static double CritDamageC = 0;
    public static double ManaDamageC = 0;
    public static double BreakManaDefenceC = 0;
    public static double BreakDefence0C = 0;
    public static double ManaReplyC = 0;
    public static double CoolDownC = 0;
    public static double HealStealC = 0;
    public static double DefenceC = 0;
    public static double ManaDefenceC = 0;
    public static double SpeedC = 0;
    public static double AttackRangeUpC = 0;
    public static double BreakManaDefence0C = 0;
    public static double ExpUpC = 0;
    public static int PlainRune = -1;
    public static int ForestRune = -1;
    public static int VolcanoRune = -1;
    public static int ManaRune = -1;
    public static int NetherRune = -1;
    public static int SnowRune = -1;
    public static int EndRune = -1;
    public static int LakeRune = -1;

    public static int TickCount = 0;
    public static double CritX = 0;
    public static double CritY = 0;
    public static double CritZ = 0;
    public static boolean CritHitFlag = false;
    public static double VBNUM = 0;
    public static double Security0Num = 0;
    public static double Security1Num = 0;
    public static double Security2Num = 0;
    public static double Security3Num = 0;
    public static double SecurityGet = 0;

    public static List<MarketItemInfo> MarketInfo = new ArrayList<>();

    public static ResourceLocation[] resourceLocations = {
            new ResourceLocation(Utils.MOD_ID, "textures/item/plain_souls.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/plain_souls.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/green_dye.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/green_dye.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/light_blue_dye.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/light_blue_dye.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/blaze_powder.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/blaze_rod.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/iron_ingot.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/gold_ingot.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/diamond.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/skysoul.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/diamond.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/evokersoul.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/evokerrune.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/seasoul.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/searune.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/brown_dye.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/brown_dye.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/purpleiron_piece.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/iron_ingot.png"),
    };
    public static MarketItemInfo[] MarketInfoArray = new  MarketItemInfo[999];
    public static void MarketItemInfoGet() {
        Iterator<MarketItemInfo> iterator = MarketInfo.iterator();
        int index = 0;
        while(iterator.hasNext()){
            MarketInfoArray[index] = iterator.next();
            index ++;
        }
    }
    public static Map<String, PrefixInfo> clientPrefixInfoMap = new HashMap<>();
    public static boolean IsHandlingPower = false;
    public static boolean IsAdjustingPower = false;
    public static Queue<Integer> PowerQueue = new LinkedList<>();
    public static Map<Integer, Integer> PowerInSlot = new HashMap<>();
    public static Map<Integer, Item> NumToItem = new HashMap<>() {
        {
            put(1, ModItems.EffectMana.get());
            put(2, ModItems.RangeMana.get());
            put(3, ModItems.DamageMana.get());
            put(4, ModItems.BreakMana.get());
            put(5, ModItems.KazeMana.get());
            put(6, ModItems.SnowMana.get());
            put(7, ModItems.LightningMana.get());
            put(8, ModItems.GatherMana.get());
        }
    };
    public static Map<Item, Integer> ItemToNum = new HashMap<>() {
        {
            put(null,0);
            put(ModItems.EffectMana.get(), 1);
            put(ModItems.RangeMana.get(), 2);
            put(ModItems.DamageMana.get(), 3);
            put(ModItems.BreakMana.get(), 4);
            put(ModItems.KazeMana.get(), 5);
            put(ModItems.SnowMana.get(), 6);
            put(ModItems.LightningMana.get(), 7);
            put(ModItems.GatherMana.get(), 8);
        }
    };
    public static boolean BlackForestParticle = false;
    public static boolean NetherParticle = false;
    public static int RangeAttackCount = -1;
    public static int RangeAttackAnimationCount = -1;
    public static int PickAxeAttackCount = -1;
    public static int PickAxeAttackAnimationCount = -1;
    public static boolean AnimationFlag = false;
    public static int PlayerSkillPoint_Total = 0;
    public static int PlayerSkillPoint_Used = 0;
    public static int PlayerAbilityPoint_Total = 0;
    public static int PlayerAbilityPoint_Used = 0;
    public static class AbilityChangeCache {
        public static int Power = 0;
        public static int Intelligent = 0;
        public static int Flexibility = 0;
        public static int Lucky = 0;
        public static int Vitality = 0;
        public static int SumPoint() {
            return Power + Intelligent + Flexibility + Lucky + Vitality;
        }
    }
    public static class AbilityCache {
        public static int Power = 0;
        public static int Intelligent = 0;
        public static int Flexibility = 0;
        public static int Lucky = 0;
        public static int Vitality = 0;
    }
    public static class SkillChangeCache {
        public static int Sword = 0;
        public static int Bow = 0;
        public static int Mana = 0;
        public static int SumPoint() {
            return Sword + Bow + Mana;
        }
    }
    public static class SkillCache {
        public static int Sword = 0;
        public static int Bow = 0;
        public static int Mana = 0;
    }
    public static class SwordSkillsResource {
        public static ResourceLocation[] resourceLocations = new ResourceLocation[20];
        public static ResourceLocation[] resourceLocations0 = new ResourceLocation[20];
        public static ResourceLocation[] resourceLocations1 = new ResourceLocation[20];
        public static void init() {
            int index = 1;
            int num = 1;
            for (int i = 1; i <= 15; i ++) {
                resourceLocations0[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/sword/sword_" + index + "_" + num + "_0.png");
                resourceLocations[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/sword/sword_" + index + "_" + num + ".png");
                resourceLocations1[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/sword/sword_" + index + "_" + num + "_1.png");
                num ++;
                if (i % 5 == 2 || i % 5 == 0) {
                    index++;
                    num = 1;
                }
            }
        }
    }
    public static class SwordSkillPoint {
        public static int[] Point = new int[20];
        public static int[] PointCache = new int[20];
        public static void SwordSkillPointCacheInit() {
            System.arraycopy(Point, 1, PointCache, 1, 15);
        }
        public static int SwordSkillPointCacheCount() {
            int sum = 0;
            for (int i = 1; i <= 15; i ++) {
                sum += PointCache[i];
            }
            return sum;
        }
        public static int SwordSkillPointCacheRangeCount(int st, int ed) {
            int sum = 0;
            for (int i = st; i <= ed; i++) {
                sum += PointCache[i];
            }
            return sum;
        }
        public static void SwordSkillPointRangeClear(int st,int ed) {
            for (int i = st; i <= ed; i++) {
                PointCache[i] = 0;
            }
        }
        public static void SwordSkillPointCacheCheck() {
            if (SwordSkillPointCacheRangeCount(1,2) < 10) {
                SwordSkillPointRangeClear(3,15);
            }
            else if (SwordSkillPointCacheRangeCount(3,5) < 5) {
                SwordSkillPointRangeClear(6,15);
            }
            else if (SwordSkillPointCacheRangeCount(6,7) < 10) {
                SwordSkillPointRangeClear(8,15);
            }
            else if (SwordSkillPointCacheRangeCount(8,10) < 5) {
                SwordSkillPointRangeClear(11,15);
            }
            else if (SwordSkillPointCacheRangeCount(11,12) < 10) {
                SwordSkillPointRangeClear(13,15);
            }
        }
    }
    public static class ManaSkillsResource {
        public static ResourceLocation[] resourceLocations = new ResourceLocation[20];
        public static ResourceLocation[] resourceLocations0 = new ResourceLocation[20];
        public static void init() {
            int index = 1;
            int num = 1;
            for (int i = 1; i <= 15; i ++) {
                resourceLocations0[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/mana/mana_" + index + "_" + num + "_0.png");
                resourceLocations[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/mana/mana_" + index + "_" + num + ".png");
                num ++;
                if (i % 5 == 2 || i % 5 == 0) {
                    index++;
                    num = 1;
                }
            }
        }
    }
    public static class ManaSkillPoint {
        public static int[] Point = new int[20];
        public static int[] PointCache = new int[20];
        public static void ManaSkillPointCacheInit() {
            System.arraycopy(Point, 1, PointCache, 1, 15);
        }
        public static int ManaSkillPointCacheCount() {
            int sum = 0;
            for (int i = 1; i <= 15; i ++) {
                sum += PointCache[i];
            }
            return sum;
        }
        public static int ManaSkillPointCacheRangeCount(int st, int ed) {
            int sum = 0;
            for (int i = st; i <= ed; i++) {
                sum += PointCache[i];
            }
            return sum;
        }
        public static void ManaSkillPointRangeClear(int st,int ed) {
            for (int i = st; i <= ed; i++) {
                PointCache[i] = 0;
            }
        }
        public static void ManaSkillPointCacheCheck() {
            if (ManaSkillPointCacheRangeCount(1,2) < 10) {
                ManaSkillPointRangeClear(3,15);
            }
            else if (ManaSkillPointCacheRangeCount(3,5) < 5) {
                ManaSkillPointRangeClear(6,15);
            }
            else if (ManaSkillPointCacheRangeCount(6,7) < 10) {
                ManaSkillPointRangeClear(8,15);
            }
            else if (ManaSkillPointCacheRangeCount(8,10) < 5) {
                ManaSkillPointRangeClear(11,15);
            }
            else if (ManaSkillPointCacheRangeCount(11,12) < 10) {
                ManaSkillPointRangeClear(13,15);
            }
        }
    }

    public static class BowSkillsResource {
        public static ResourceLocation[] resourceLocations = new ResourceLocation[20];
        public static ResourceLocation[] resourceLocations0 = new ResourceLocation[20];
        public static void init() {
            int index = 1;
            int num = 1;
            for (int i = 1; i <= 15; i ++) {
                resourceLocations0[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/bow/bow_" + index + "_" + num + "_0.png");
                resourceLocations[i] = new ResourceLocation(Utils.MOD_ID,"textures/skills/bow/bow_" + index + "_" + num + ".png");
                num ++;
                if (i % 5 == 2 || i % 5 == 0) {
                    index++;
                    num = 1;
                }
            }
        }
    }
    public static class BowSkillPoint {
        public static int[] Point = new int[20];
        public static int[] PointCache = new int[20];
        public static void BowSkillPointCacheInit() {
            System.arraycopy(Point, 1, PointCache, 1, 15);
        }
        public static int BowSkillPointCacheCount() {
            int sum = 0;
            for (int i = 1; i <= 15; i ++) {
                sum += PointCache[i];
            }
            return sum;
        }
        public static int BowSkillPointCacheRangeCount(int st, int ed) {
            int sum = 0;
            for (int i = st; i <= ed; i++) {
                sum += PointCache[i];
            }
            return sum;
        }
        public static void BowSkillPointRangeClear(int st,int ed) {
            for (int i = st; i <= ed; i++) {
                PointCache[i] = 0;
            }
        }
        public static void BowSkillPointCacheCheck() {
            if (BowSkillPointCacheRangeCount(1,2) < 10) {
                BowSkillPointRangeClear(3,15);
            }
            else if (BowSkillPointCacheRangeCount(3,5) < 5) {
                BowSkillPointRangeClear(6,15);
            }
            else if (BowSkillPointCacheRangeCount(6,7) < 10) {
                BowSkillPointRangeClear(8,15);
            }
            else if (BowSkillPointCacheRangeCount(8,10) < 5) {
                BowSkillPointRangeClear(11,15);
            }
            else if (BowSkillPointCacheRangeCount(11,12) < 10) {
                BowSkillPointRangeClear(13,15);
            }
        }
    }

    public static double ChargedCountsSwordSkill12 = 0;
    public static double ChargedCountsBowSkill12 = 0;
    public static double ChargedCountsManaSkill12 = 0;
    public static double ChargedCountsManaSkill13 = 0;
    public static double ChargedCountsSakuraDemonSword = 0;
    public static double ChargedCountsZeusSword = 0;

    public static boolean ChargedFlagSwordSkill12 = true;
    public static boolean ChargedFlagBowSkill12 = true;
    public static boolean ChargedFlagManaSkill12 = true;
    public static boolean ChargedFlagManaSkill13 = true;
    public static boolean ChargedFlagSakuraDemonSword = true;
    public static boolean ChargedFlagZeusSword = true;

    public static SkillImage[] Sword_Image = new SkillImage[20];
    public static SkillImage[] Bow_Image = new SkillImage[20];
    public static SkillImage[] Mana_Image = new SkillImage[20];
    public static SkillImage[] Demon_Image = new SkillImage[20];
    public static SkillImage[] Rune_Image = new SkillImage[20];

    public static ResourceLocation[] RuneResourceLocation = {
            null,
            new ResourceLocation(Utils.MOD_ID, "textures/item/foreststone.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/icestone.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/item/manarune.png"),
    };

    public static ResourceLocation[] CdResourceLocation = {
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd1.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd2.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd3.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd4.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd5.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd6.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd7.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd8.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd9.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd10.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd11.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cd12.png"),
    };

    public static ResourceLocation[] dCdResourceLocation = {
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd1.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd2.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd3.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd4.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd5.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd6.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd7.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd8.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd9.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd10.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd11.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/dcd12.png"),
    };

    public static class Entropy {
        public static int Forest = 0;
        public static int Volcano = 0;
        public static int Lake = 0;
        public static int Sky = 0;
        public static int Snow = 0;
    }

    public static ArrayList<ManaAttackParticle> manaAttackParticleArrayList = new ArrayList<>();

    public static boolean AbilityDataBuffer = true;
    public static boolean SkillDataBuffer = true;

    public static boolean SkillCacheFlag = true;

    public static HashMap<Integer, Integer> DefencePenetrationParticle = new HashMap<>();
    public static HashMap<Integer, Integer> ManaDefencePenetrationParticle = new HashMap<>();
    public static HashMap<Integer, Integer> SlowDownParticle = new HashMap<>();
    public static HashMap<Integer, Integer> DamageDecreaseParticle = new HashMap<>();

    public static HashMap<String, Boolean> CrestMap = new HashMap<>();

    public static int ManaAttackTick = 0;
    public static int ManaAttackCounts = 0;
    public static int BowAttackTick = 0;
    public static int BowAttackCounts = 0;
    public static int UseTick = 0;
    public static int RollingTick = 0;

    public static boolean PlayerIsAttacking(Player player) {
        return (player.tickCount - ClientAttackEvent.ClientAttackTickCounts <= 10);
    }
    public static boolean PlayerIsUsing(Player player) {
        return (player.tickCount - ClientUtils.UseTick <= 10);
    }
    public static boolean PlayerIsManaAttacking(Player player) {
        return (player.tickCount - ClientUtils.ManaAttackTick <= 10);
    }
    public static boolean PlayerIsBowAttacking(Player player) {
        return player.tickCount - ClientUtils.UseTick <= 10;
    }
    public static boolean PlayerIsRolling(Player player) {
        return (player.tickCount - ClientUtils.RollingTick <= 10);
    }

    public static void AnimationTickReset() {
        ClientUtils.UseTick = 0;
        ClientUtils.ManaAttackTick = 0;
        ClientUtils.BowAttackTick = 0;
        ClientAttackEvent.ClientAttackTickCounts = 0;
        ClientAttackEvent.ClientAttackCounts = 0;
        ClientUtils.RollingTick = 0;
    }

    public static void MissionReset () {
        RollingFlag = false;
        RollingRate = 0;
        MissionIndex = 0;
        MissionList = new ArrayList<>();
        MissionChange = false;
        ListIndex = 0;
        NavigateIndex = -1;
    }

    public static boolean RollingFlag = false;
    public static double RollingRate = 0;

    public static boolean Mission = false;
    public static int MissionIndex = 0;

    public static List<com.very.wraq.valueAndTools.Utils.Struct.Mission> MissionList = new ArrayList<>();

    public static boolean MissionChange = false;
    public static int ListIndex = 0;
    public static int NavigateIndex = -1;

    public static Map<Item, Component> ItemComponentMap = new HashMap<>();

    public static void ItemComponentMapInit() {
        ItemComponentMap.put(ModItems.PlainSword0.get().asItem(),Component.literal("平原短匕").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSword1.get().asItem(),Component.literal("平原短匕-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSword2.get().asItem(),Component.literal("平原短匕-II").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSword3.get().asItem(),Component.literal("平原短匕-III").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.PlainBow0.get().asItem(),Component.literal("平原长弓").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainBow1.get().asItem(),Component.literal("平原长弓-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainBow2.get().asItem(),Component.literal("平原长弓-II").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainBow3.get().asItem(),Component.literal("平原长弓-III").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.PlainSceptre0.get().asItem(),Component.literal("平原权杖").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSceptre1.get().asItem(),Component.literal("平原权杖-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSceptre2.get().asItem(),Component.literal("平原权杖-II").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSceptre3.get().asItem(),Component.literal("平原权杖-III").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainSceptre4.get().asItem(),Component.literal("生机权杖-X").withStyle(CustomStyle.styleOfHealth).withStyle(ChatFormatting.BOLD));


        ItemComponentMap.put(ModItems.LakeSword0.get().asItem(),Component.literal("蛟龙").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeSword1.get().asItem(),Component.literal("蛟龙-I").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeSword2.get().asItem(),Component.literal("蛟龙-II").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeSword3.get().asItem(),Component.literal("蛟龙-III").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.VolcanoSword0.get().asItem(),Component.literal("火山之心").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoSword1.get().asItem(),Component.literal("火山之心-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoSword2.get().asItem(),Component.literal("火山之心-II").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoSword3.get().asItem(),Component.literal("火山之心-III").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.VolcanoBow0.get().asItem(),Component.literal("熔岩长弓").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoBow1.get().asItem(),Component.literal("熔岩长弓-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoBow2.get().asItem(),Component.literal("熔岩长弓-II").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoBow3.get().asItem(),Component.literal("熔岩长弓-III").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.MineSword0.get().asItem(),Component.literal("层岩探索者").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MineSword1.get().asItem(),Component.literal("层岩探索者-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MineSword2.get().asItem(),Component.literal("层岩探索者-II").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MineSword3.get().asItem(),Component.literal("层岩探索者-III").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.FieldSword0.get().asItem(),Component.literal("收获之镰").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.FieldSword1.get().asItem(),Component.literal("收获之镰-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.FieldSword2.get().asItem(),Component.literal("收获之镰-II").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.FieldSword3.get().asItem(),Component.literal("收获之镰-III").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SnowSword0.get().asItem(),Component.literal("冰川探索者").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowSword1.get().asItem(),Component.literal("冰川探索者-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowSword2.get().asItem(),Component.literal("冰川探索者-II").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowSword3.get().asItem(),Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("冰川探索者").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.SkyBow.get().asItem(),Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.EvokerSword.get().asItem(),Component.literal("魔源之杖").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.NetherPower.get().asItem(),Component.literal("下界能量矩阵").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.NetherBow.get().asItem(),Component.literal("夸塔兹长弓").withStyle(CustomStyle.styleOfQuartz).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.ManaSword.get().asItem(),Component.literal("玛莫提乌斯之噬").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.QuartzSword.get().asItem(),Component.literal("夸塔兹之刃").withStyle(CustomStyle.styleOfQuartz).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.QuartzSabre.get().asItem(),Component.literal("夸塔兹佩剑").withStyle(CustomStyle.styleOfQuartz).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.EvokerBook0.get().asItem(),Component.literal("唤魔典籍").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.EvokerBook1.get().asItem(),Component.literal("唤魔典籍-I").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.EvokerBook2.get().asItem(),Component.literal("唤魔典籍-II").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.EvokerBook3.get().asItem(),Component.literal("唤魔典籍-III").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SeaSword0.get().asItem(),Component.literal("灵魂救赎者").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SeaSword1.get().asItem(),Component.literal("灵魂救赎者-I").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SeaSword2.get().asItem(),Component.literal("灵魂救赎者-II").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SeaSword3.get().asItem(),Component.literal("灵魂救赎者-III").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SeaSword0.get().asItem(),Component.literal("灵魂收割者").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SeaSword1.get().asItem(),Component.literal("灵魂收割者-I").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SeaSword2.get().asItem(),Component.literal("灵魂收割者-II").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SeaSword3.get().asItem(),Component.literal("灵魂收割者-III").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.KazeSword0.get().asItem(),Component.literal("灵风").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.KazeSword1.get().asItem(),Component.literal("灵风-I").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.KazeSword2.get().asItem(),Component.literal("灵风-II").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.KazeSword3.get().asItem(),Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("灵风").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD)));
        ItemComponentMap.put(ModItems.KazeSword4.get().asItem(),Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("<狂风能量激化>").withStyle(CustomStyle.styleOfVolcano)).append(Component.literal("灵风").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.BlackForestSword4.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("灵魂收割者").withStyle(CustomStyle.styleOfBlackForest).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.SeaSword4.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("灵魂救赎者").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.ManaSword1.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("玛莫提乌斯之噬").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.SnowSword4.get().asItem(),Component.literal("<维瑞级><能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("冰川探索者").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.ForestSword4.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("森林粉碎者").withStyle(CustomStyle.styleOfForest).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.VolcanoSword4.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("火山之心").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.ForestBossSword.get().asItem(),Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("森林制造者").withStyle(CustomStyle.styleOfHealth).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.VolcanoBossSword.get().asItem(),Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("熔岩制造者").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.LakeBossSword.get().asItem(),Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("湖泊制造者").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.SkyBossBow.get().asItem(),Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("寰宇之耀").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));

        ItemComponentMap.put(ModItems.SakuraDemonSword.get().asItem(),Component.literal("妖刀-樱").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.WitherSword0.get().asItem(),Component.literal("凋零碳刃").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.WitherSword1.get().asItem(),Component.literal("凋零碳刃-I").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.WitherSword2.get().asItem(),Component.literal("凋零碳刃-II").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.WitherSword3.get().asItem(),Component.literal("凋零碳刃-III").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.WitherBow0.get().asItem(),Component.literal("凋零长弓").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.WitherBow1.get().asItem(),Component.literal("凋零长弓-I").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.WitherBow2.get().asItem(),Component.literal("凋零长弓-II").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.WitherBow3.get().asItem(),Component.literal("凋零长弓-III").withStyle(CustomStyle.styleOfWither).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.MagmaSceptre0.get().asItem(),Component.literal("爆裂魔杖").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MagmaSceptre1.get().asItem(),Component.literal("爆裂魔杖-I").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MagmaSceptre2.get().asItem(),Component.literal("爆裂魔杖-II").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MagmaSceptre3.get().asItem(),Component.literal("爆裂魔杖-III").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.PlainManaBook.get().asItem(),Component.literal("见习魔法师的笔记" + Name[0]).withStyle(styles[0]).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.ForestManaBook.get().asItem(),Component.literal("见习魔法师的笔记" + Name[1]).withStyle(styles[1]).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeManaBook.get().asItem(),Component.literal("见习魔法师的笔记" + Name[2]).withStyle(styles[2]).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoManaBook.get().asItem(),Component.literal("见习魔法师的笔记" + Name[3]).withStyle(styles[3]).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowManaBook.get().asItem(),Component.literal("见习魔法师的笔记" + Name[4]).withStyle(styles[4]).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.PlainArmorHelmet.get().asItem(),Component.literal("平原头盔").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainArmorChest.get().asItem(),Component.literal("平原胸甲").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainArmorLeggings.get().asItem(),Component.literal("平原护腿").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PlainArmorBoots.get().asItem(),Component.literal("平原靴子").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.ForestArmorHelmet.get().asItem(),Component.literal("森林头盔").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.ForestArmorChest.get().asItem(),Component.literal("森林胸甲").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.ForestArmorLeggings.get().asItem(),Component.literal("森林护腿").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.ForestArmorBoots.get().asItem(),Component.literal("森林靴子").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.LakeArmorHelmet.get().asItem(),Component.literal("湖泊头盔").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeArmorChest.get().asItem(),Component.literal("湖泊胸甲").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeArmorLeggings.get().asItem(),Component.literal("湖泊护腿").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LakeArmorBoots.get().asItem(),Component.literal("湖泊靴子").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.VolcanoArmorHelmet.get().asItem(),Component.literal("火山头盔").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoArmorChest.get().asItem(),Component.literal("火山胸甲").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoArmorLeggings.get().asItem(),Component.literal("火山护腿").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.VolcanoArmorBoots.get().asItem(),Component.literal("火山靴子").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.LifeManaArmorHelmet.get().asItem(),Component.literal("火山头盔").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LifeManaArmorChest.get().asItem(),Component.literal("火山胸甲").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LifeManaArmorLeggings.get().asItem(),Component.literal("火山护腿").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.LifeManaArmorBoots.get().asItem(),Component.literal("火山靴子").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.IslandArmorHelmet.get().asItem(),Component.literal("唤雷之顶").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.IslandArmorChest.get().asItem(),Component.literal("唤雷之心").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.IslandArmorLeggings.get().asItem(),Component.literal("唤雷之源").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.IslandArmorBoots.get().asItem(),Component.literal("唤雷之基").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SkyArmorHelmet.get().asItem(),Component.literal("天空之眼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SkyArmorChest.get().asItem(),Component.literal("天空之心").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SkyArmorLeggings.get().asItem(),Component.literal("天空之迹").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SkyArmorBoots.get().asItem(),Component.literal("天空之翼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.NetherArmorHelmet.get().asItem(),Component.literal("要塞之核").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.NetherArmorChest.get().asItem(),Component.literal("灵魂沙之峡").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.NetherArmorLeggings.get().asItem(),Component.literal("玄武岩之洲").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.NetherArmorBoots.get().asItem(),Component.literal("熔岩之湖").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.KazeBoots.get().asItem(),Component.literal("翠绿足具").withStyle(CustomStyle.styleOfKaze).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SHelmet.get().asItem(),Component.literal("微光头盔").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SChest.get().asItem(),Component.literal("微光胸甲").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SLeggings.get().asItem(),Component.literal("微光护腿").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SBoots.get().asItem(),Component.literal("微光靴子").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.ISArmorHelmet.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("微光头盔").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));
        ItemComponentMap.put(ModItems.ISArmorChest.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("微光胸甲").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));
        ItemComponentMap.put(ModItems.ISArmorLeggings.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("微光护腿").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));
        ItemComponentMap.put(ModItems.ISArmorBoots.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("微光靴子").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));

        ItemComponentMap.put(ModItems.ILArmorHelmet.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("唤雷之顶").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));
        ItemComponentMap.put(ModItems.ILArmorChest.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("唤雷之心").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));
        ItemComponentMap.put(ModItems.ILArmorLeggings.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("唤雷之源").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));
        ItemComponentMap.put(ModItems.ILArmorBoots.get().asItem(),Component.literal("<能量激化>").withStyle(CustomStyle.styleOfVolcano).append(Component.literal("唤雷之基").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfSpider)));

        ItemComponentMap.put(ModItems.SakuraArmorHelmet.get().asItem(),Component.literal("绯樱帽").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.WheatArmorChest.get().asItem(),Component.literal("稻草甲").withStyle(CustomStyle.styleOfWheat).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.MinePants.get().asItem(),Component.literal("矿工的牛仔裤").withStyle(CustomStyle.styleOfMine).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.MineArmorHelmet.get().asItem(),Component.literal("矿洞头盔").withStyle(CustomStyle.styleOfMine).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MineArmorChest.get().asItem(),Component.literal("矿洞胸甲").withStyle(CustomStyle.styleOfMine).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MineArmorLeggings.get().asItem(),Component.literal("矿洞护腿").withStyle(CustomStyle.styleOfMine).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.MineArmorBoots.get().asItem(),Component.literal("矿洞靴子").withStyle(CustomStyle.styleOfMine).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SnowArmorHelmet.get().asItem(),Component.literal("玉山头盔").withStyle(CustomStyle.styleOfSnow).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowArmorChest.get().asItem(),Component.literal("玉山胸甲").withStyle(CustomStyle.styleOfSnow).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowArmorLeggings.get().asItem(),Component.literal("玉山护腿").withStyle(CustomStyle.styleOfSnow).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.SnowArmorBoots.get().asItem(),Component.literal("玉山靴子").withStyle(CustomStyle.styleOfSnow).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.PiglinHelmet0.get().asItem(),Component.literal("猪灵头盔").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PiglinHelmet1.get().asItem(),Component.literal("猪灵头盔-I").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PiglinHelmet2.get().asItem(),Component.literal("猪灵头盔-II").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        ItemComponentMap.put(ModItems.PiglinHelmet3.get().asItem(),Component.literal("猪灵头盔-III").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));

        ItemComponentMap.put(ModItems.SnowBossArmorChest.get().asItem(),Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("凛冽冰铠").withStyle(CustomStyle.styleOfSnow).withStyle(ChatFormatting.BOLD)));
    }


    public static Map<String, ClientPlayerTeam> clientPlayerTeamMap = new HashMap<>();

    public static List<ClientPlayerTeam> clientPlayerTeamList = new ArrayList<>();

    public static void setClientPlayerTeamList() {
        clientPlayerTeamList.clear();
        clientPlayerTeamMap.keySet().forEach(s -> {
            ClientPlayerTeam clientPlayerTeam = clientPlayerTeamMap.get(s);
            if (!clientPlayerTeamList.contains(clientPlayerTeam)) clientPlayerTeamList.add(clientPlayerTeam);
        });
    }

    public static Map<String, Component> TeamPlayerRequestList = new HashMap<>();

    public static Map<String, Component> TeamInviteRequestList = new HashMap<>();

    public static Level clientLevel;

    public static int clientScreenSetFlag = -1;

    public static int clientLevelType = 0;

    public static boolean clientTeamScreenFlag = false;

    public static Map<String,Component> clientPlayerList = new HashMap<>();

    public static int currentPsValue = 0;

    public static int Boss2AnimationMode = -1;

    public static String serverTime;
    public static String lastDailyMissionFinishedTime;

    public static ItemStack DailyMissionItem;
    public static int DailyMissionItemCount;

    public static ItemStack ReputationMissionItem;
    public static int ReputationMissionItemCount;
    public static String ReputationMissionStartTime = "";
    public static String ReputationMissionAllowRequestTime = "";

    public static int ReputationNum = 0;
    public static int ScreenCoolDown = 0;

    public static String TradeScreenType = "";
    public static boolean TradeScreenOpenFlag = false;

    public static Mob mobAttribute;

    public static int mobId = 0;
    public static double mobAttack = 0;
    public static double mobDefence = 0;
    public static double mobManaDefence = 0;
    public static double mobCritRate = 0;
    public static double mobDefencePenetration = 0;
    public static double mobDefencePenetration0 = 0;
    public static double mobHealthSteal = 0;
    public static double mobCritDamage = 0;
    public static String mobElementType;
    public static double mobElementValue = 0;

    public static int DingCounts = 0;

    public static int SpringInstanceAttackCount = 0;

    public static List<EffectTimeLast> effectTimeLasts = new ArrayList<>();
    public static List<EffectTimeLast> coolDownTimes = new ArrayList<>();
    public static List<EffectTimeLast> debuffTimes = new ArrayList<>();

    public static List<PosAndLastTime> EndRune2Pos = new ArrayList<>();

    public static int EndRune3Type = -1;

    public static int ClientLevelFlag = 0;

    public static int LiuLiXianKey = 0;

    public static int GuangYiSwitchModeTick = 0;

    public static boolean EarthPowerFlag = false;

    public static boolean EarthPowerCompute = false;
    public static int EarthPowerType = -1;

    public static int clientPacketLimit = 1000;

    public static Player clientPlayer = null;

    public static EffectTimeLast elementEffects = null;
}
