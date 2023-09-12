package com.Very.very.ValueAndTools.Utils;

import com.Very.very.Files.MarketItemInfo;
import com.Very.very.Items.Prefix.PrefixInfo;
import com.Very.very.ValueAndTools.Utils.Struct.SkillImage;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.*;
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

    public static float AttackDamageC = 0;
    public static float BreakDefenceC = 0;
    public static float CritRateC = 0;
    public static float CritDamageC = 0;
    public static float ManaDamageC = 0;
    public static float BreakManaDefenceC = 0;
    public static float BreakDefence0C = 0;
    public static float ManaReplyC = 0;
    public static float CoolDownC = 0;
    public static float HealStealC = 0;
    public static float DefenceC = 0;
    public static float ManaDefenceC = 0;
    public static float SpeedC = 0;
    public static float AttackRangeUpC = 0;
    public static float BreakManaDefence0C = 0;
    public static float ExpUpC = 0;
    public static int PlainRune = -1;
    public static int ForestRune = -1;
    public static int VolcanoRune = -1;

    public static int ManaRune = -1;

    public static int NetherRune = -1;
    public static int SnowRune = -1;

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
    public static Queue<MarketItemInfo> MarketInfo = new LinkedList<>();
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
            new ResourceLocation(Utils.MOD_ID, "textures/item/iron_nugget.png"),
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
    public static Map<Integer, Item> NumToItem = new HashMap<>();
    public static void NumToItemInit() {
        NumToItem.put(1, Moditems.EffectMana.get());
        NumToItem.put(2, Moditems.RangeMana.get());
        NumToItem.put(3, Moditems.DamageMana.get());
        NumToItem.put(4, Moditems.BreakMana.get());
        NumToItem.put(5, Moditems.KazeMana.get());
        NumToItem.put(6, Moditems.SnowMana.get());
        NumToItem.put(7, Moditems.LightningMana.get());
        NumToItem.put(8, Moditems.GatherMana.get());
    }
    public static Map<Item, Integer> ItemToNum = new HashMap<>();
    public static void ItemToNumInit() {
        ItemToNum.put(null,0);
        ItemToNum.put(Moditems.EffectMana.get(), 1);
        ItemToNum.put(Moditems.RangeMana.get(), 2);
        ItemToNum.put(Moditems.DamageMana.get(), 3);
        ItemToNum.put(Moditems.BreakMana.get(), 4);
        ItemToNum.put(Moditems.KazeMana.get(), 5);
        ItemToNum.put(Moditems.SnowMana.get(), 6);
        ItemToNum.put(Moditems.LightningMana.get(), 7);
        ItemToNum.put(Moditems.GatherMana.get(), 8);
    }
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
    public static class PlayerAbility {
        public static int Power = 0;
        public static int Intelligent = 0;
        public static int Flexibility = 0;
        public static int Lucky = 0;
        public static int Vitality = 0;
    }
    public static class AbilityVision {
        public static int Power = 0;
        public static int Intelligent = 0;
        public static int Flexibility = 0;
        public static int Lucky = 0;
        public static int Vitality = 0;
    }
    public static class AbilityChangeCache {
        public static int Power = 0;
        public static int Intelligent = 0;
        public static int Flexibility = 0;
        public static int Lucky = 0;
        public static int Vitality = 0;
        public static int SumPoint() {
            return Power + Intelligent + Flexibility + Lucky + Vitality;
        }
        public static void Clear() {
            Power = 0;
            Intelligent = 0;
            Flexibility = 0;
            Lucky = 0;
            Vitality = 0;
        }
    }
    public static class PlayerSkill {
        public static int Sword = 0;
        public static int Bow = 0;
        public static int Mana = 0;
    }
    public static class SkillVision {
        public static int Sword = 0;
        public static int Bow = 0;
        public static int Mana = 0;
    }
    public static class SkillChangeCache {
        public static int Sword = 0;
        public static int Bow = 0;
        public static int Mana = 0;
        public static int SumPoint() {
            return Sword + Bow + Mana;
        }
        public static void Clear() {
            Sword = 0;
            Bow = 0;
            Mana = 0;
        }
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
    public static boolean ChargedFlagSwordSkill12 = true;
    public static boolean ChargedFlagBowSkill12 = true;
    public static boolean ChargedFlagManaSkill12 = true;
    public static boolean ChargedFlagManaSkill13 = true;

    public static SkillImage[] Sword_Image = new SkillImage[20];
    public static SkillImage[] Bow_Image = new SkillImage[20];
    public static SkillImage[] Mana_Image = new SkillImage[20];

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

    public static class Entropy {
        public static int Forest = 0;
        public static int Volcano = 0;
    }
}
