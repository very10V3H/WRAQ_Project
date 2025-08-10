package fun.wraq.process.func.rank;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.network.RankChangeS2CPacket;
import fun.wraq.process.func.rank.network.RankDataS2CPacket;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

public class RankData {

    // 以下为服务端与客户端的整体操作
    public static Map<UUID, String> serverPlayerCurrentRankMap = new HashMap<>();
    public static Map<UUID, String> clientPlayerCurrentRankMap = new HashMap<>();

    public static void onPlayerLogin(Player player) {
        UUID uuid = player.getUUID();
        String rank = getCurrentRank(player);
        serverPlayerCurrentRankMap.put(uuid, rank);
        sendChangeToAllPlayer(uuid, rank, player.getServer().getPlayerList());
        sendAllDataToPlayer(player);
    }

    public static void onPlayerDailyLogin(Player player) {
        if (getRankSerial(player) >= rankSerialList.indexOf("13C")) {
            Compute.VBIncomeAndMSGSend(player, rankWagesMap.get(getCurrentRank(player)) * 1000);
            sendFormatMSG(player, Te.s("你的", "日薪", CustomStyle.styleOfGold, "已到账!"));
        }
    }

    public static void sendChangeToAllPlayer(UUID uuid, String rank, PlayerList playerList) {
        playerList.getPlayers().forEach(serverPlayer -> {
            ModNetworking.sendToClient(new RankChangeS2CPacket(uuid, rank), serverPlayer);
        });
    }

    public static void sendAllDataToPlayer(Player player) {
        ModNetworking.sendToClient(new RankDataS2CPacket(serverPlayerCurrentRankMap), (ServerPlayer) player);
    }

    // 以下为对单个玩家Tag进行操作
    public static final String RANK_DATA_KEY = "rankData";

    public static CompoundTag getRankData(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(RANK_DATA_KEY)) {
            tag.put(RANK_DATA_KEY, new CompoundTag());
        }
        return tag.getCompound(RANK_DATA_KEY);
    }

    public static final String CURRENT_RANK_KEY = "currentRank";
    public static String getCurrentRank(Player player) {
        if (!getRankData(player).contains(CURRENT_RANK_KEY)) return "null";
        return getRankData(player).getString(CURRENT_RANK_KEY);
    }

    public static void setCurrentRank(Player player, String rank) {
        getRankData(player).putString(CURRENT_RANK_KEY, rank);
    }

    public static void addNewRank(Player player, String rank, String description) {
        setCurrentRank(player, rank);
        getRankData(player).putString(rank, description);
        sendChangeToAllPlayer(player.getUUID(), rank, player.getServer().getPlayerList());
        sendFormatMSG(player, Te.s("已提升至 ", rank, rankStyleMap.get(rank), " " + description));
    }

    public static String getRankDescription(Player player, String rank) {
        return getRankData(player).getString(rank);
    }

    public static List<Component> getProfiler(Player player) {
        List<Component> components = new ArrayList<>();
        String rank = getCurrentRank(player);
        components.add(Te.s(player.getDisplayName(), " 当前职级为: ", rank + " - " + rankNameMap.get(rank),
                rankStyleMap.get(rank)));
        components.add(Te.s("其履历如下: ", CustomStyle.styleOfStone));
        for (int i = rankSerialList.size() - 1; i >= 0; i--) {
            String rankKey = rankSerialList.get(i);
            if (!getRankDescription(player, rankKey).isEmpty()) {
                components.add(Te.s(rankKey + " - " + rankNameMap.get(rankKey), rankStyleMap.get(rankKey)));
                components.add(Te.s(" > " , getRankDescription(player, rankKey)));
            }
        }
        return components;
    }

    public static int getRankSerial(Player player) {
       return rankSerialList.indexOf(getCurrentRank(player));
    }
    public static int getRankSerial(String rank) {
        return rankSerialList.indexOf(rank);
    }

    public static void sendFormatMSG(Player player, Component content) {
        if (player != null) {
            Compute.sendFormatMSG(player, Te.s("Rank", ChatFormatting.AQUA), content);
        } else {
            LogUtils.getLogger().info(content.getString());
        }
    }

    public static final String _13C = "13C";
    public static final String _13B = "13B";
    public static final String _13A = "13A";
    public static final String _14C = "14C";
    public static final String _14B = "14B";
    public static final String _14A = "14A";
    public static final String _15B = "15B";
    public static final String _15A = "15A";
    public static final String _16B = "16B";
    public static final String _16A = "16A";
    public static final String _17 = "17";
    public static final String _18 = "18";
    public static final String _19 = "19";
    public static final String _20 = "20";
    public static final String _21 = "21";
    public static final String _22 = "22";
    public static final String _23 = "23";
    public static final String _x = "*";

    public static List<String> rankSerialList = new ArrayList<>() {{
        add("null");
        add("13C");
        add("13B");
        add("13A");
        add("14C");
        add("14B");
        add("14A");
        add("15B");
        add("15A");
        add("16B");
        add("16A");
        add("17");
        add("18");
        add("19");
        add("20");
        add("21");
        add("22");
        add("23");
        add("*");
    }};

    public static Map<String, Style> rankStyleMap = new HashMap<>() {{
        put("null", Style.EMPTY.applyFormat(ChatFormatting.GRAY));
        put("13C", CustomStyle.styleOfGold);
        put("13B", CustomStyle.styleOfGold);
        put("13A", CustomStyle.styleOfGold);
        put("14C", CustomStyle.styleOfSunIsland);
        put("14B", CustomStyle.styleOfSunIsland);
        put("14A", CustomStyle.styleOfSunIsland);
        put("15B", CustomStyle.styleOfWorld);
        put("15A", CustomStyle.styleOfWorld);
        put("16B", CustomStyle.styleOfIce);
        put("16A", CustomStyle.styleOfIce);
        put("17", CustomStyle.styleOfSakura);
        put("18", CustomStyle.styleOfDemon);
        put("19", CustomStyle.styleOfMoon);
        put("20", CustomStyle.styleOfMoon1);
        put("21", CustomStyle.styleOfCastleCrystal);
        put("22", CustomStyle.styleOfMoontain);
        put("23", CustomStyle.styleOfMoontain);
        put("*", Style.EMPTY.applyFormat(ChatFormatting.RED));
    }};

    public static Map<String, String> rankNameMap = new HashMap<>() {{
        put("null", "勘探学徒");
        put("13C", "助理勘探师");
        put("13B", "助理勘探师");
        put("13A", "助理勘探师");
        put("14C", "初级勘探师");
        put("14B", "初级勘探师");
        put("14A", "初级勘探师");
        put("15A", "勘探师");
        put("15B", "勘探师");
        put("16B", "高级勘探师");
        put("16A", "高级勘探师");
        put("17", "高级勘探师");
        put("18", "勘探专家");
        put("19", "勘探专家");
        put("20", "基础勘探部部长");
        put("21", "院长");
        put("22", "审计");
        put("23", "元勋");
        put("*", "-");
    }};

    public static Map<String, Integer> rankWagesMap = new HashMap<>() {{
        put("null", 0);
        put("13C", 15);
        put("13B", 17);
        put("13A", 19);
        put("14C", 21);
        put("14B", 25);
        put("14A", 30);
        put("15B", 40);
        put("15A", 60);
        put("16B", 80);
        put("16A", 100);
        put("17", 150);
        put("18", 250);
        put("19", 350);
        put("20", 500);
        put("21", 700);
        put("22", 900);
        put("23", 1200);
        put("*", 2000);
    }};

    // 以下是职权内容

    // 完成新每日任务
    public static void onPlayerFishedNewDailyMission(Player player) {
        if (getRankSerial(player) >= rankSerialList.indexOf("13B")) {
            Tower.givePlayerStar(player, 2, "职权");
            sendFormatMSG(player, Te.s("你的", "职权", ChatFormatting.AQUA, "为你额外提供了",
                    ModItems.WORLD_SOUL_5.get().getDefaultInstance().getDisplayName(), " * 2", CustomStyle.styleOfWorld));
        } else {
            if (getRankSerial(player) >= rankSerialList.indexOf("13C")) {
                Tower.givePlayerStar(player, 1, "职权");
                sendFormatMSG(player, Te.s("你的", "职权", ChatFormatting.AQUA, "为你额外提供了",
                        ModItems.WORLD_SOUL_5.get().getDefaultInstance().getDisplayName(), " * 1", CustomStyle.styleOfWorld));
            }
        }
    }

    // 完成每日悬赏任务
    public static void onPlayerFinishDailyReputationMission(Player player) {
        if (getRankSerial(player) >= rankSerialList.indexOf("13A")) {
            List.of(new ItemStack(ModItems.SENIOR_POTION_SUPPLY.get()), new ItemStack(ModItems.REVELATION_HEART.get()))
                    .forEach(stack -> InventoryOperation.giveItemStackWithMSG(player, stack));
            sendFormatMSG(player, Te.s("你的", "职权", ChatFormatting.AQUA, "为你额外提供了奖励!"));
        }
    }

    // 炼造耗时减少
    public static int smeltNeedTimeReduction(Player player) {
        if (getRankSerial(player) >= rankSerialList.indexOf("14A")) {
            return 30;
        } else if (getRankSerial(player) >= rankSerialList.indexOf("14B")) {
            return 20;
        } else if (getRankSerial(player) >= rankSerialList.indexOf("14C")) {
            return 10;
        }
        return 0;
    }

    public static int smeltNeedTimeReduction(String rank) {
        if (getRankSerial(rank) >= rankSerialList.indexOf("14A")) {
            return 30;
        } else if (getRankSerial(rank) >= rankSerialList.indexOf("14B")) {
            return 20;
        } else if (getRankSerial(rank) >= rankSerialList.indexOf("14C")) {
            return 10;
        }
        return 0;
    }

    // 额外悬赏值
    public static double getExReputationMissionRewardRate(Player player) {
        if (getRankSerial(player) >= rankSerialList.indexOf("14A")) {
            return 0.3;
        } else if (getRankSerial(player) >= rankSerialList.indexOf("14B")) {
            return 0.2;
        } else if (getRankSerial(player) >= rankSerialList.indexOf("14C")) {
            return 0.1;
        }
        return 0;
    }

    public static double getExReputationMissionRewardRate(String rank) {
        if (getRankSerial(rank) >= rankSerialList.indexOf("14A")) {
            return 0.3;
        } else if (getRankSerial(rank) >= rankSerialList.indexOf("14B")) {
            return 0.2;
        } else if (getRankSerial(rank) >= rankSerialList.indexOf("14C")) {
            return 0.1;
        }
        return 0;
    }

    // 额外产出
    public static double getExHarvestRate(Player player) {
        return getRankSerial(player) * 0.02;
    }

    public static void onTryToEnhanceRank(Player player) {
        if (rankSerialList.indexOf(getCurrentRank(player))
                < rankSerialList.indexOf(_14A)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            if (rankSerialList.indexOf(getCurrentRank(player))
                    < rankSerialList.indexOf(_14C)) {
                if (InventoryOperation.checkPlayerHasItem(player, ModItems.SPECIAL_BOND.get(), 15)) {
                    addNewRank(player, rankSerialList.get(rankSerialList.indexOf(getCurrentRank(player)) + 1),
                            "-" + df.format(calendar.getTime()) + "-使用特别债券提升至此Rank.");
                    InventoryOperation.removeItem(player, ModItems.SPECIAL_BOND.get(), 15);
                    MySound.soundToPlayer(player, SoundEvents.VILLAGER_CELEBRATE);
                } else {
                    sendFormatMSG(player, Te.s("需要15张", ModItems.SPECIAL_BOND.get(),
                            "才能提升Rank."));
                    MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
                }
            } else {
                if (InventoryOperation.checkPlayerHasItem(player, ModItems.SPECIAL_BOND.get(), 20)) {
                    addNewRank(player, rankSerialList.get(rankSerialList.indexOf(getCurrentRank(player)) + 1),
                            "-" + df.format(calendar.getTime()) + "-使用特别债券提升至此Rank.");
                    InventoryOperation.removeItem(player, ModItems.SPECIAL_BOND.get(), 20);
                    MySound.soundToPlayer(player, SoundEvents.VILLAGER_CELEBRATE);
                } else {
                    sendFormatMSG(player, Te.s("需要20张", ModItems.SPECIAL_BOND.get(),
                            "才能提升Rank."));
                    MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
                }
            }
        } else {
            sendFormatMSG(player, Te.s("15B及以上的职级是不能自助提升的."));
        }
    }

    public static void onPlayerFinishEntrustment(Player player) {
        Compute.VBIncomeAndMSGSend(player, rankWagesMap.get(getCurrentRank(player)) * 100);
        sendFormatMSG(player, Te.s("你的", "职级", CustomStyle.styleOfWorld,
                "为你额外提供了", "奖金!", CustomStyle.styleOfGold));
    }

    public static final String COMPENSATE_DATA_KEY = "EntrustmentCompensate";

    public static void onPlayerLoginCompensate(Player player) {
        if (!Compute.getDataBooleanValue(player, COMPENSATE_DATA_KEY)) {
            Compute.setDataBooleanValue(player, COMPENSATE_DATA_KEY, true);
            Compute.VBIncomeAndMSGSend(player, rankWagesMap.get(getCurrentRank(player)) * 100
                    * MobKillEntrustment.getTotalFinishedTimes(player));
            sendFormatMSG(player, Te.s("你已收到来自委托与职级的VB补偿!", CustomStyle.styleOfGold));
        }
    }

    public static Component getRankName(String rank) {
        return Te.s(rankNameMap.get(rank), rankStyleMap.get(rank));
    }

    public static List<Component> getRankRightDescription(String rank) {
        List<Component> rankDescription = new ArrayList<>();
        rankDescription.add(Te.s(" · ", "日薪:" +
                RankData.rankWagesMap.getOrDefault(rank, 0) * 1000 + "VB", CustomStyle.styleOfGold));
        if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("19")) {
            rankDescription.add(Te.s(" · ", "永久激活",
                    ChatFormatting.RED, "[因子计划]", ChatFormatting.LIGHT_PURPLE));
        } else if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("17")) {
            rankDescription.add(Te.s(" · ", "永久激活",
                    ChatFormatting.RED, "[本源杰青]", ChatFormatting.AQUA));
        } else if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("15B")) {
            rankDescription.add(Te.s(" · ", "永久激活",
                    ChatFormatting.RED, "[本源学者]", ChatFormatting.GREEN));
        }
        if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("13B")) {
            rankDescription.add(Te.s(" · ", "完成每日任务额外提供",
                    ModItems.WORLD_SOUL_5.get(), " * 2", CustomStyle.styleOfWorld));
        } else {
            if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("13C")) {
                rankDescription.add(Te.s(" · ", "完成每日任务额外提供",
                        ModItems.WORLD_SOUL_5.get(), " * 1", CustomStyle.styleOfWorld));
            }
        }
        if (RankData.getRankSerial(rank) >= RankData.rankSerialList.indexOf("13A")) {
            rankDescription.add(Te.s(" · ", "完成每日悬赏任务获得",
                    ModItems.SENIOR_POTION_SUPPLY.get()));
        }
        if (RankData.smeltNeedTimeReduction(rank) > 0) {
            rankDescription.add(Te.s(" · ", "炼造物品耗时 ",
                    "-" + RankData.smeltNeedTimeReduction(rank) + "s", ChatFormatting.AQUA));
        }
        if (RankData.getExReputationMissionRewardRate(rank) > 0) {
            rankDescription.add(Te.s(" · ", "额外声望获取 ",
                    String.format("+%.0f%%", RankData.getExReputationMissionRewardRate(rank) * 100),
                    CustomStyle.styleOfGold));
        }
        rankDescription.add(Te.s(" · ",
                "额外产出 + " + String.format("%.0f%%", RankData.getRankSerial(rank) * 0.02 * 100),
                CustomStyle.styleOfGold));
        rankDescription.add(Te.s(" · ", "完成委托任务额外奖金: ",
                RankData.rankWagesMap.get(rank) * 100 + "VB", CustomStyle.styleOfGold));
        return rankDescription;
    }

    public static String getNextTierRank(String rank) {
        return RankData.rankSerialList.get(RankData.rankSerialList.indexOf(rank) + 1);
    }
}
