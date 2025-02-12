package fun.wraq.process.system.missions.mission2;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.element.PlainInstance;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class MissionV2Helper {
    public static final String MISSION_V2_DATA_KEY = "MissionV2Data";
    public static CompoundTag getMissionV2Data(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, MISSION_V2_DATA_KEY);
    }

    public static final String MISSION_V2_STATUS_KEY = "MissionV2Status";
    public static CompoundTag getMissionV2StatusData(Player player) {
        if (!getMissionV2Data(player).contains(MISSION_V2_STATUS_KEY)) {
            getMissionV2Data(player).put(MISSION_V2_STATUS_KEY, new CompoundTag());
        }
        return getMissionV2Data(player).getCompound(MISSION_V2_STATUS_KEY);
    }

    public static final String MISSION_V2_DAILY_MISSION_DATA_KEY = "MissionV2DailyMissionData";
    public static CompoundTag getDailyMissionData(Player player) {
        if (!getMissionV2Data(player).contains(MISSION_V2_DAILY_MISSION_DATA_KEY)) {
            getMissionV2Data(player).put(MISSION_V2_DAILY_MISSION_DATA_KEY, new CompoundTag());
        }
        return getMissionV2Data(player).getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY);
    }

    // 每日勘探任务

    public static final String DAILY_EXPLORE_MISSION_NAME_DATA_KEY = "DailyExploreMission";
    public static Map<String, Vec3> exploreNameToPosMap = new HashMap<>() {{
        // < 100
        put("炼魔涌溢", new Vec3(1573, 54, 149));
        put("旭升岛", new Vec3(1808, 74, 339));
        put("蒙特轻轨检修基地", new Vec3(2454, 130, -171));
        put("火山村", new Vec3(2573, 120, -492));
        // < 150
        put("紫晶遗迹", new Vec3(1171, -35, -171));
        put("北境晶钻矿区", new Vec3(1408, 12, -2853));
        put("冰霜骑士所在", new Vec3(1565, 63, -2924));
        put("艾樱地铁检修基地", new Vec3(2054, 60, 1705));
        // < 200
        put("魔王居所", new Vec3(2624, 192, 1724));
        put("尘月宫", new Vec3(1761, 130, -463));
        put("望山据点", new Vec3(1921, 151, -936));
        put("暗黑城堡", new Vec3(2417, 152, -1372));
        // 其他
        put("菌菇聚落", new Vec3(2006, 130, -1785));
        put("远古之城", new Vec3(2352, -34, -704));
        put("鹰眼工厂", new Vec3(1934, -25, 1798));
    }};

    public static void setNewDailyExploreMission(Player player) {
        String locationName;
        List<String> list;
        int xpLevel = player.experienceLevel;
        if (xpLevel < 100) {
            list = List.of("炼魔涌溢", "旭升岛", "蒙特轻轨检修基地", "火山村");
        } else if (xpLevel < 150) {
            list = List.of("紫晶遗迹", "北境晶钻矿区", "冰霜骑士所在", "艾樱地铁检修基地");
        } else if (xpLevel < 200) {
            list = List.of("魔王居所", "尘月宫", "望山据点", "暗黑城堡");
        } else {
            list = List.of("菌菇聚落", "远古之城", "鹰眼工厂");
        }
        locationName = list.get(RandomUtils.nextInt(0, list.size()));
        getDailyMissionData(player).putString(DAILY_EXPLORE_MISSION_NAME_DATA_KEY, locationName);
    }

    public static MissionV2.PlayerCondition getDailyExploreMissionSubmitCondition() {
        return (player -> {
            Vec3 pos = exploreNameToPosMap.get(getDailyMissionData(player).getString(DAILY_EXPLORE_MISSION_NAME_DATA_KEY));
            return player.position().distanceTo(pos) < 32;
        });
    }

    public static MissionV2.ClientComponentOperation getDailyExploreMissionTitle() {
        return ((missionV2, data) -> {
            return Te.s("勘探: ", CustomStyle.styleOfField,
                    data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY).getString(DAILY_EXPLORE_MISSION_NAME_DATA_KEY));
        });
    }

    public static MissionV2.ClientComponentOperation getDailyExploreMissionDetail() {
        return ((missionV2, data) -> {
            return Te.s("前往: ", CustomStyle.styleOfField,
                    exploreNameToPosMap.get(data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY)
                            .getString(DAILY_EXPLORE_MISSION_NAME_DATA_KEY)).toString());
        });
    }

    // 每日击杀任务

    public static final String DAILY_KILL_MOB_NAME_DATA_KEY = "DailyKillMobName";
    public static final String DAILY_KILL_MOB_COUNT_DATA_KEY = "DailyKillMobCount";

    public static void setNewDailyKillMobMission(Player player) {
        List<MobSpawnController> controllers = MobSpawn.getAllControllers(true)
                .stream().filter(controller -> {
                    return controller.averageLevel <= player.experienceLevel - 8;
                }).toList();
        Random random = new Random();
        MobSpawnController controller = controllers.get(random.nextInt(controllers.size()));
        getDailyMissionData(player).putString(DAILY_KILL_MOB_NAME_DATA_KEY, controller.mobName.getString());
    }

    public static void onKillMob(Player player, Mob mob) {
        CompoundTag data = getDailyMissionData(player);
        if (MobSpawn.getMobOriginName(mob).equals(data.getString(DAILY_KILL_MOB_NAME_DATA_KEY))) {
            data.putInt(DAILY_KILL_MOB_COUNT_DATA_KEY, data.getInt(DAILY_KILL_MOB_COUNT_DATA_KEY) + 1);
        }
    }

    public static MissionV2.PlayerCondition getDailyKillMissionSubmitCondition() {
        return (player -> {
            return getDailyMissionData(player).getInt(DAILY_KILL_MOB_COUNT_DATA_KEY) >= 128;
        });
    }

    public static MissionV2.ClientComponentOperation getDailyKillMissionTitle() {
        return ((missionV2, data) -> {
            String mobName = data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY)
                    .getString(DAILY_KILL_MOB_NAME_DATA_KEY);

            return Te.s("击杀: ", CustomStyle.styleOfRed, MobSpawn.getMobNameMap().get(mobName), " * 128");
        });
    }

    public static MissionV2.ClientComponentOperation getDailyKillMissionDetail() {
        return ((missionV2, data) -> {
            return Te.s("进度: ", CustomStyle.styleOfRed, data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY)
                    .getInt(DAILY_KILL_MOB_COUNT_DATA_KEY), " / ", 128, CustomStyle.styleOfMoon);
        });
    }

    // 每日收集任务

    public static final String DAILY_COLLECTION_ITEM_STRING_DATA_KEY = "DailyCollectionItemString";

    public static void setNewDailyCollectionItemMission(Player player) {
        Random random = new Random();
        int boundary = Utils.getMissionItemBoundary(player.experienceLevel);
        int chooseIndex = random.nextInt(0, boundary + 1);
        if (Utils.missionItemList.isEmpty()) {
            Utils.setMissionItemList();
        }
        ItemStack chooseItemStack = Utils.missionItemList.get(chooseIndex).getDefaultInstance();
        ItemStack itemStack = chooseItemStack.copy();
        getDailyMissionData(player).putString(DAILY_COLLECTION_ITEM_STRING_DATA_KEY,
                Compute.getItemStackString(itemStack));
    }

    public static MissionV2.PlayerCondition getDailyCollectionItemMissionSubmitCondition() {
        return (player -> {
            Item item = Compute.getItemFromString(
                    getDailyMissionData(player).getString(DAILY_COLLECTION_ITEM_STRING_DATA_KEY)).getItem();
            return InventoryOperation.checkPlayerHasItem(player, item, 64);
        });
    }

    public static MissionV2.ClientComponentOperation getDailyCollectionMissionTitle() {
        return ((missionV2, data) -> {
            ItemStack itemStack = Compute.getItemFromString(data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY)
                    .getString(DAILY_COLLECTION_ITEM_STRING_DATA_KEY));
            return Te.s("收集: ", CustomStyle.styleOfFlexible, itemStack, " * 64", CustomStyle.styleOfWorld);
        });
    }

    // 每日讨伐任务

    public static final String DAILY_CHALLENGE_NAME_DATA_KEY = "DailyChallengeName";
    public static final String DAILY_CHALLENGE_COUNT_DATA_KEY = "DailyChallengeCount";

    public static void setNewDailyChallengeMission(Player player) {
        int xpLevel = player.experienceLevel;
        if (xpLevel < 50) {
            getDailyMissionData(player).putString(DAILY_CHALLENGE_NAME_DATA_KEY, PlainInstance.mobName);
        } else {
            List<NoTeamInstance> list = NoTeamInstanceModule.getAllInstance()
                    .stream().filter(noTeamInstance -> noTeamInstance.level <= xpLevel)
                    .toList();
            getDailyMissionData(player).putString(DAILY_CHALLENGE_NAME_DATA_KEY,
                    list.get(RandomUtils.nextInt(0, list.size())).name.getString());
        }
    }

    public static void onChallengeFinished(Player player, String challengeName) {
        CompoundTag data = getDailyMissionData(player);
        if (data.getString(DAILY_CHALLENGE_NAME_DATA_KEY).equals(challengeName)) {
            data.putInt(DAILY_CHALLENGE_COUNT_DATA_KEY, data.getInt(DAILY_CHALLENGE_COUNT_DATA_KEY) + 1);
        }
    }

    public static MissionV2.PlayerCondition getDailyChallengeMissionSubmitCondition() {
        return (player -> {
            return getDailyMissionData(player).getInt(DAILY_CHALLENGE_COUNT_DATA_KEY) >= 8;
        });
    }

    public static MissionV2.ClientComponentOperation getDailyChallengeMissionTitle() {
        return ((missionV2, data) -> {
            String name = data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY)
                    .getString(DAILY_CHALLENGE_NAME_DATA_KEY);

            return Te.s("挑战: ", CustomStyle.styleOfRed, NoTeamInstanceModule.getNameMap().get(name),
                    " 8次");
        });
    }

    public static MissionV2.ClientComponentOperation getDailyChallengeMissionDetail() {
        return ((missionV2, data) -> {
            return Te.s("进度: ", CustomStyle.styleOfRed,
                    data.getCompound(MISSION_V2_DAILY_MISSION_DATA_KEY).getInt(DAILY_CHALLENGE_COUNT_DATA_KEY),
                    " / ", 8, CustomStyle.styleOfMoon);
        });
    }

    public static MissionV2.PlayerAction getDailyMissionRewardAction() {
        return (player -> {
            List<ItemStack> rewardList = List.of(
                    new ItemStack(ModItems.GoldCoinBag.get()),
                    new ItemStack(ModItems.RevelationBook.get(), 10)
            );
            rewardList.forEach(stack -> {
                InventoryOperation.giveItemStackWithMSG(player, stack.getItem(), stack.getCount());
            });
            Tower.givePlayerStar(player, 4 * (PlanPlayer.getPlayerTier(player) > 0 ? 2 : 1), "每日任务");
            if (PlanPlayer.getPlayerTier(player) > 0) {
                Compute.sendFormatMSG(player, Te.s("计划", CustomStyle.styleOfWorld),
                        Te.s("计划为你额外提供了", ModItems.WORLD_SOUL_5, " * 4", CustomStyle.styleOfWorld));
            }
            RankData.onPlayerFishedNewDailyMission(player);
        });
    }

    public static List<Component> getDailyRewardDescription() {
        List<Component> components = new ArrayList<>();
        components.addAll(ComponentUtils.getItemStackDescriptionList(List.of(
                new ItemStack(ModItems.GoldCoinBag.get()),
                new ItemStack(ModItems.RevelationBook.get(), 10),
                new ItemStack(ModItems.WORLD_SOUL_5.get(), 4)
        )));
        return components;
    }

    public static void onResetDailyContent(Player player) {
        MissionV2.DAILY_EXPLORE.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
        setNewDailyExploreMission(player);
        MissionV2.DAILY_KILL.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
        setNewDailyKillMobMission(player);
        MissionV2.DAILY_COLLECTION.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
        setNewDailyCollectionItemMission(player);
        MissionV2.DAILY_CHALLENGE.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
        setNewDailyChallengeMission(player);
    }
}
