package com.very.wraq.common.Utils;

import com.very.wraq.events.client.ClientAttackEvent;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.common.Utils.Struct.*;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
    public static Map<UUID, Integer> QuartzSabreClientPlayerUUIDINDEX = new HashMap<>();
    public static Map<UUID, Integer> QuartzSabreClientMonsterUUIDINDEX = new HashMap<>();
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
    public static double clientHealthRecover = 0;
    public static double clientSwiftness = 0;
    public static double clientManaHealthSteal = 0;
    public static double clientDodgeRate = 0;

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

    public static List<MarketItemInfo> marketInfo = new ArrayList<>();

    public static boolean receiveMarketInfo = false;

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
            put(null, 0);
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
            for (int i = 1; i <= 15; i++) {
                resourceLocations0[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/sword/sword_" + index + "_" + num + "_0.png");
                resourceLocations[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/sword/sword_" + index + "_" + num + ".png");
                resourceLocations1[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/sword/sword_" + index + "_" + num + "_1.png");
                num++;
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
            for (int i = 1; i <= 15; i++) {
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

        public static void SwordSkillPointRangeClear(int st, int ed) {
            for (int i = st; i <= ed; i++) {
                PointCache[i] = 0;
            }
        }

        public static void SwordSkillPointCacheCheck() {
            if (SwordSkillPointCacheRangeCount(1, 2) < 10) {
                SwordSkillPointRangeClear(3, 15);
            } else if (SwordSkillPointCacheRangeCount(3, 5) < 5) {
                SwordSkillPointRangeClear(6, 15);
            } else if (SwordSkillPointCacheRangeCount(6, 7) < 10) {
                SwordSkillPointRangeClear(8, 15);
            } else if (SwordSkillPointCacheRangeCount(8, 10) < 5) {
                SwordSkillPointRangeClear(11, 15);
            } else if (SwordSkillPointCacheRangeCount(11, 12) < 10) {
                SwordSkillPointRangeClear(13, 15);
            }
        }
    }

    public static class ManaSkillsResource {
        public static ResourceLocation[] resourceLocations = new ResourceLocation[20];
        public static ResourceLocation[] resourceLocations0 = new ResourceLocation[20];

        public static void init() {
            int index = 1;
            int num = 1;
            for (int i = 1; i <= 15; i++) {
                resourceLocations0[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/mana/mana_" + index + "_" + num + "_0.png");
                resourceLocations[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/mana/mana_" + index + "_" + num + ".png");
                num++;
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
            for (int i = 1; i <= 15; i++) {
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

        public static void ManaSkillPointRangeClear(int st, int ed) {
            for (int i = st; i <= ed; i++) {
                PointCache[i] = 0;
            }
        }

        public static void ManaSkillPointCacheCheck() {
            if (ManaSkillPointCacheRangeCount(1, 2) < 10) {
                ManaSkillPointRangeClear(3, 15);
            } else if (ManaSkillPointCacheRangeCount(3, 5) < 5) {
                ManaSkillPointRangeClear(6, 15);
            } else if (ManaSkillPointCacheRangeCount(6, 7) < 10) {
                ManaSkillPointRangeClear(8, 15);
            } else if (ManaSkillPointCacheRangeCount(8, 10) < 5) {
                ManaSkillPointRangeClear(11, 15);
            } else if (ManaSkillPointCacheRangeCount(11, 12) < 10) {
                ManaSkillPointRangeClear(13, 15);
            }
        }
    }

    public static class BowSkillsResource {
        public static ResourceLocation[] resourceLocations = new ResourceLocation[20];
        public static ResourceLocation[] resourceLocations0 = new ResourceLocation[20];

        public static void init() {
            int index = 1;
            int num = 1;
            for (int i = 1; i <= 15; i++) {
                resourceLocations0[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/bow/bow_" + index + "_" + num + "_0.png");
                resourceLocations[i] = new ResourceLocation(Utils.MOD_ID, "textures/skills/bow/bow_" + index + "_" + num + ".png");
                num++;
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
            for (int i = 1; i <= 15; i++) {
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

        public static void BowSkillPointRangeClear(int st, int ed) {
            for (int i = st; i <= ed; i++) {
                PointCache[i] = 0;
            }
        }

        public static void BowSkillPointCacheCheck() {
            if (BowSkillPointCacheRangeCount(1, 2) < 10) {
                BowSkillPointRangeClear(3, 15);
            } else if (BowSkillPointCacheRangeCount(3, 5) < 5) {
                BowSkillPointRangeClear(6, 15);
            } else if (BowSkillPointCacheRangeCount(6, 7) < 10) {
                BowSkillPointRangeClear(8, 15);
            } else if (BowSkillPointCacheRangeCount(8, 10) < 5) {
                BowSkillPointRangeClear(11, 15);
            } else if (BowSkillPointCacheRangeCount(11, 12) < 10) {
                BowSkillPointRangeClear(13, 15);
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
    public static int BowAttackTick = 0;
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

    public static void MissionReset() {
        RollingFlag = false;
        RollingRate = 0;
        MissionIndex = 0;
        oldMissionList = new ArrayList<>();
        MissionChange = false;
        ListIndex = 0;
        NavigateIndex = -1;
    }

    public static boolean RollingFlag = false;
    public static double RollingRate = 0;

    public static boolean Mission = false;
    public static int MissionIndex = 0;

    public static List<OldMission> oldMissionList = new ArrayList<>();

    public static boolean MissionChange = false;
    public static int ListIndex = 0;
    public static int NavigateIndex = -1;

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

    public static Map<String, Component> clientPlayerList = new HashMap<>();

    public static int currentPsValue = 0;

    public static int Boss2AnimationMode = -1;

    public static String serverTime;
    public static String lastDailyMissionFinishedTime;

    public static ItemStack DailyMissionItem;
    public static int DailyMissionItemCount;

    public static ItemStack reputationMissionItem;
    public static int reputationMissionItemCount;
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

    public static Player playerAttribute;
    public static int playerId;

    public static double playerAttack = 0;
    public static double playerDefencePenetration = 0;
    public static double playerCritRate = 0;
    public static double playerCritDamage = 0;
    public static double playerManaDamage = 0;
    public static double playerManaPenetration = 0;
    public static double playerManaRecover = 0;
    public static double playerCooldown = 0;
    public static double playerHealthSteal = 0;
    public static double playerDefence = 0;
    public static double playerManaDefence = 0;
    public static double playerMovementSpeed = 0;
    public static double playerDefencePenetration0 = 0;
    public static double playerManaPenetration0 = 0;
    public static double playerExpUp = 0;

    public static List<Double> playerAttributeList = new ArrayList<>();

    public static int DingCounts = 0;

    public static int SpringInstanceAttackCount = 0;

    public static List<EffectTimeLast> effectTimeLasts = new ArrayList<>();
    public static List<EffectTimeLast> coolDownTimes = new ArrayList<>();
    public static List<EffectTimeLast> debuffTimes = new ArrayList<>();

    public record Effect(ItemStack itemStack, String tag, int startTick, int lastTick, int level, boolean forever) {}

    public static int clientPlayerTick = 0;

    public static Map<Mob, List<Effect>> clientMobEffectMap = new HashMap<>();

    public static List<PosAndLastTime> EndRune2Pos = new ArrayList<>();

    public static int EndRune3Type = -1;

    public static boolean EarthPowerFlag = false;

    public static boolean EarthPowerCompute = false;
    public static int EarthPowerType = -1;

    public static int clientPacketLimit = 1000;

    public static Player clientPlayer = null;

    public static EffectTimeLast elementEffects = null;

    public static int missionScreenFlag = -1;

    public static int ipFlag = -1;

}

