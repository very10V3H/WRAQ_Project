package com.very.wraq.commands.changeable;

import com.very.wraq.Items.Prefix.PrefixInfo;
import com.very.wraq.blocks.brewing.BrewingNote;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.chapter6_castle.BeaconSpawnController;
import com.very.wraq.events.mob.chapter6_castle.BlazeSpawnController;
import com.very.wraq.events.mob.chapter6_castle.TreeSpawnController;
import com.very.wraq.events.mob.chapter1.*;
import com.very.wraq.events.mob.chapter2.*;
import com.very.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import com.very.wraq.events.mob.chapter3_nether.NetherSkeletonSpawnController;
import com.very.wraq.events.mob.chapter3_nether.PiglinSpawnController;
import com.very.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import com.very.wraq.events.mob.chapter4_end.EnderManSpawnController;
import com.very.wraq.events.mob.chapter4_end.EndermiteSpawnController;
import com.very.wraq.events.mob.chapter4_end.ShulkerSpawnController;
import com.very.wraq.events.mob.chapter5.BloodManaSpawnController;
import com.very.wraq.events.mob.chapter5.EarthManaSpawnController;
import com.very.wraq.events.mob.chapter5.PillagerSpawnController;
import com.very.wraq.events.mob.chapter5.SakuraMobSpawnController;
import com.very.wraq.events.mob.chapter7.BoneImpSpawnController;
import com.very.wraq.events.mob.chapter7.StarSpawnController;
import com.very.wraq.events.mob.chapter7.TorturedSoulSpawnController;
import com.very.wraq.events.mob.instance.instances.*;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.PrefixPackets.PrefixS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.List;

public class PrefixCommand implements Command<CommandSourceStack> {
    public static PrefixCommand instance = new PrefixCommand();

    public static Map<UUID, PrefixInfo> clientPrefixInfo = new HashMap<>();
    public static Map<UUID, PrefixInfo> serverPrefixInfo = new HashMap<>();

    public static String prefix = "prefix";
    public static String prefixColor = "prefixColor";

    public static int simpleKillCountPrefix(Map<String, Map<String, Integer>> countMap,
                                            Player player, SimplePrefixType simplePrefixType) {
        String playerName = player.getName().getString();
        String mobName = simplePrefixType.mobName;
        int needCount = simplePrefixType.needCount;
        if (countMap.containsKey(playerName)) {
            Map<String, Integer> map = countMap.get(playerName);
            if (map.containsKey(mobName) && map.get(mobName) >= needCount) {
                return 1;
            }
        }
        return 0;
    }

    public interface PrefixCondition {
        int matchCondition(Player player);

        String getPrefixDescription();

        Style getStyle();
    }

    public record SimplePrefixType(String mobName, int needCount, String prefixDescription,
                                   Style style) implements PrefixCondition {

        @Override
        public int matchCondition(Player player) {
            Map<String, Map<String, Integer>> countMap = MobSpawn.totalKillCount;
            String playerName = player.getName().getString();
            if (countMap.containsKey(playerName)) {
                Map<String, Integer> map = countMap.get(playerName);
                if (map.containsKey(mobName) && map.get(mobName) >= needCount) {
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public String getPrefixDescription() {
            return prefixDescription;
        }

        @Override
        public Style getStyle() {
            return style;
        }
    }

    public record ProgressPrefixType(String tag, int downBoundary, int upBoundary, String prefixDescription,
                                     Style style) implements PrefixCondition {

        @Override
        public int matchCondition(Player player) {
            CompoundTag data = player.getPersistentData();
            int count = data.getInt(tag);
            if (downBoundary == upBoundary) {
                if (count == downBoundary) return 1;
            } else return count >= downBoundary && count < upBoundary ? 1 : 0;
            return 0;
        }

        @Override
        public String getPrefixDescription() {
            return prefixDescription;
        }

        @Override
        public Style getStyle() {
            return style;
        }
    }

    public static List<PrefixCondition> simplePrefixTypeList = new ArrayList<>() {{
        add(new SimplePrefixType(PlainZombieSpawnController.mobName, 1000, "平原统治者", CustomStyle.styleOfPlain));
        add(new SimplePrefixType(ForestZombieSpawnController.mobName, 1000, "森林统治者", CustomStyle.styleOfForest));
        add(new SimplePrefixType(LakeDrownSpawnController.mobName, 1000, "河流统治者", CustomStyle.styleOfLake));
        add(new SimplePrefixType(MineSkeletonSpawnController.mobName, 1000, "矿洞统治者", CustomStyle.styleOfMine));
        add(new SimplePrefixType(SpiderSpawnController.mobName, 1000, "雨林统治者", CustomStyle.styleOfSpider));

        add(new SimplePrefixType(PlainInstance.mobName, 320, "生机焕发", CustomStyle.styleOfLife));
        add(new SimplePrefixType(FireLightSpawnController.mobName, 1000, "焰芒虫天敌", CustomStyle.styleOfFire));
        add(new SimplePrefixType(EvokerSpawnController.mobName, 1000, "净魔者", CustomStyle.styleOfMana));
        add(new SimplePrefixType(LumberJackSpawnController.mobName, 1000, "伐伐伐伐伐木工", CustomStyle.styleOfHusk));
        add(new SimplePrefixType(DreadHoundSpawnController.mobName, 1000, "群狼之首", CustomStyle.styleOfForest));
        add(new SimplePrefixType(SkyVexSpawnController.mobName, 1000, "天空城授卫", CustomStyle.styleOfSky));
        add(new SimplePrefixType(SlimeSpawnController.mobName, 1000, "史莱姆的好伙伴(?)", CustomStyle.styleOfLife));
        add(new SimplePrefixType(JorogumoSpawnController.mobName, 1000, "紫水晶之辉", CustomStyle.styleOfPurpleIron));
        add(new SimplePrefixType(SnowStraySpawnController.mobName, 1000, "冰川统治者", CustomStyle.styleOfIce));
        add(new SimplePrefixType(NetherInstance.mobName, 320, "焰烬魂燃", CustomStyle.styleOfPower));

        add(new SimplePrefixType(WindSkeletonSpawnController.mobName, 1000, "风抹层丛", CustomStyle.styleOfWind));
        add(new SimplePrefixType(HuskSpawnController.mobName, 1000, "岩裂石隙", CustomStyle.styleOfHusk));
        add(new SimplePrefixType(LightningZombieController.mobName, 1000, "雷光辐照", CustomStyle.styleOfLightning));
        add(new SimplePrefixType(GuardianSpawnController.mobName, 1000, "远古神殿清道夫", CustomStyle.styleOfSea));
        add(new SimplePrefixType(PurpleIronInstance.mobName, 320, "紫水晶之耀", CustomStyle.styleOfPurpleIron));
        add(new SimplePrefixType(IceInstance.mobName, 320, "迷阵之玉", CustomStyle.styleOfIce));

        add(new SimplePrefixType(WitherSkeletonSpawnController.mobName, 1000, "凋零灵魂收集者", CustomStyle.styleOfWither));
        add(new SimplePrefixType(MagmaSpawnController.mobName, 1000, "熔岩能量收割者", CustomStyle.styleOfPower));
        add(new SimplePrefixType(NetherSkeletonSpawnController.mobName, 1000, "下界猎颅者", CustomStyle.styleOfNether));
        add(new SimplePrefixType(PiglinSpawnController.mobName, 1000, "拒绝交易(?)", CustomStyle.styleOfGold));

        add(new SimplePrefixType(PillagerSpawnController.mobName, 1000, "远洋护卫", CustomStyle.styleOfShip));
        add(new SimplePrefixType(BloodManaSpawnController.mobName, 1000, "旧世腥月封印者", CustomStyle.styleOfBloodMana));
        add(new SimplePrefixType(EarthManaSpawnController.mobName, 1000, "旧世地蕴封印者", CustomStyle.styleOfJacaranda));
        add(new SimplePrefixType(SakuraMobSpawnController.mobName, 1000, "隙之落樱", CustomStyle.styleOfSakura));
        add(new SimplePrefixType(SakuraBossInstance.mobName, 320, "黄金大盗", CustomStyle.styleOfGold));
        add(new SimplePrefixType(DevilInstance.mobName, 320, "旧世弑君者", CustomStyle.styleOfDemon));

        add(new SimplePrefixType(EnderManSpawnController.mobName, 1000, "终界入侵者", CustomStyle.styleOfEnd));
        add(new SimplePrefixType(EndermiteSpawnController.mobName, 1000, "圆寂之域", CustomStyle.styleOfEnd));
        add(new SimplePrefixType(ShulkerSpawnController.mobName, 1000, "圆寂清道夫", CustomStyle.styleOfEnd));

        add(new SimplePrefixType(MoonInstance.mobName, 320, "清辉夜凝", CustomStyle.styleOfMoon1));

        add(new SimplePrefixType(BeaconSpawnController.mobName, 1000, "哨戒烽火", CustomStyle.styleOfFire));
        add(new SimplePrefixType(BlazeSpawnController.mobName, 1000, "熔岩涌动", CustomStyle.styleOfPower));
        add(new SimplePrefixType(TreeSpawnController.mobName, 1000, "古树学者", CustomStyle.styleOfMana));

        add(new SimplePrefixType(StarSpawnController.mobName, 1000, "星梦之灵", CustomStyle.styleOfMoon1));
        add(new SimplePrefixType(BoneImpSpawnController.mobName, 1000, "炽焰魂魄", CustomStyle.styleOfVolcano));
        add(new SimplePrefixType(TorturedSoulSpawnController.mobName, 1000, "解脱者", CustomStyle.styleOfWorld));

        add(new ProgressPrefixType(StringUtils.FishCount, 20, 50, "见习渔夫", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)));
        add(new ProgressPrefixType(StringUtils.FishCount, 50, 100, "入门渔夫", Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        add(new ProgressPrefixType(StringUtils.FishCount, 100, 200, "中阶渔夫", Style.EMPTY.applyFormat(ChatFormatting.YELLOW)));
        add(new ProgressPrefixType(StringUtils.FishCount, 200, 500, "高阶渔夫", Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
        add(new ProgressPrefixType(StringUtils.FishCount, 500, 1000, "经常空军的钓鱼佬", Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        add(new ProgressPrefixType(StringUtils.FishCount, 1000, 2000, "偶尔空军的钓鱼佬", Style.EMPTY.applyFormat(ChatFormatting.RED)));
        add(new ProgressPrefixType(StringUtils.FishCount, 2000, Integer.MAX_VALUE, "永不空军的钓鱼佬", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)));

        add(new ProgressPrefixType(StringUtils.Mine.Exp, 0, 100, "见习矿工", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)));
        add(new ProgressPrefixType(StringUtils.Mine.Exp, 100, 1000, "入门矿工", Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        add(new ProgressPrefixType(StringUtils.Mine.Exp, 1000, 5000, "职业矿工", Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        add(new ProgressPrefixType(StringUtils.Mine.Exp, 5000, 20000, "悲催苦力矿工", Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
        add(new ProgressPrefixType(StringUtils.Mine.Exp, 20000, 100000, "一只挖矿的帕鲁", Style.EMPTY.applyFormat(ChatFormatting.AQUA)));

        add(new ProgressPrefixType(StringUtils.Lop.Xp, 0, 100, "见习伐木工", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)));
        add(new ProgressPrefixType(StringUtils.Lop.Xp, 100, 1000, "入门伐木工", Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        add(new ProgressPrefixType(StringUtils.Lop.Xp, 1000, 5000, "职业伐木工", Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        add(new ProgressPrefixType(StringUtils.Lop.Xp, 5000, 20000, "光头强", Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
        add(new ProgressPrefixType(StringUtils.Lop.Xp, 20000, 100000, "一只砍树的帕鲁", Style.EMPTY.applyFormat(ChatFormatting.AQUA)));

        add(new ProgressPrefixType(StringUtils.Gardening.Xp, 0, 100, "见习农夫", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)));
        add(new ProgressPrefixType(StringUtils.Gardening.Xp, 100, 1000, "入门农夫", Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        add(new ProgressPrefixType(StringUtils.Gardening.Xp, 1000, 5000, "职业农夫", Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        add(new ProgressPrefixType(StringUtils.Gardening.Xp, 5000, 20000, "农耕大师", Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
        add(new ProgressPrefixType(StringUtils.Gardening.Xp, 20000, 100000, "一只种田的帕鲁", Style.EMPTY.applyFormat(ChatFormatting.AQUA)));

        add(new ProgressPrefixType(BrewingNote.brewingLevel, 0, 0, "酿造初识", Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        add(new ProgressPrefixType(BrewingNote.brewingLevel, 1, 1, "酿造入门", Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
        add(new ProgressPrefixType(BrewingNote.brewingLevel, 2, 2, "酿造初级", Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
        add(new ProgressPrefixType(BrewingNote.brewingLevel, 3, 3, "酿造中级", Style.EMPTY.applyFormat(ChatFormatting.YELLOW)));
        add(new ProgressPrefixType(BrewingNote.brewingLevel, 4, 4, "酿造高级", Style.EMPTY.applyFormat(ChatFormatting.AQUA)));
        add(new ProgressPrefixType(BrewingNote.brewingLevel, 5, 5, "酿造学士", Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        add(new ProgressPrefixType(BrewingNote.brewingLevel, 6, 6, "酿造大师", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)));
    }};


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        int count = 0;
        Compute.formatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("可用称号如下:").withStyle(ChatFormatting.WHITE));

        count++;
        player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                append(Component.literal("初来乍到").withStyle(ChatFormatting.GRAY)));

        for (PrefixCondition prefixCondition : simplePrefixTypeList) {
            if (prefixCondition.matchCondition(player) == 1) {
                count++;
                player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(prefixCondition.getPrefixDescription()).withStyle(prefixCondition.getStyle())));
            }
        }

/*        if (data.contains(StringUtils.DragonPrefix)) {
            count++;
            player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("龙行龘龘").withStyle(CustomStyle.styleOfSpring)));
        }
        if (data.contains(StringUtils.XiangLiPrefix)) {
            count++;
            player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("理塘王").withStyle(CustomStyle.styleOfField)));
        }
        if (data.contains(StringUtils.LotteryPrefix)) {
            count++;
            player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("赌神").withStyle(ChatFormatting.GOLD)));
        }
        if (data.contains(StringUtils.QingMingPrefix)) {
            count++;
            player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("雨纷纷").withStyle(CustomStyle.styleOfHealth)));
        }*/
        Compute.formatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("使用/vmd prefix [编号]来激活称号").withStyle(ChatFormatting.WHITE));
        return 0;
    }

    public static void handlePrefix(List<ServerPlayer> playerList) {
        for (ServerPlayer serverPlayer : playerList) {
            CompoundTag data = serverPlayer.getPersistentData();
            String prefix = "初来乍到";
            String color = String.valueOf(CustomStyle.styleOfMine.getColor());
            if (data.contains(PrefixCommand.prefix)) prefix = data.getString(PrefixCommand.prefix);
            if (data.contains(PrefixCommand.prefixColor)) color = data.getString(PrefixCommand.prefixColor);
            PrefixInfo prefixInfo = new PrefixInfo(prefix, color, serverPlayer.experienceLevel);
            PrefixCommand.serverPrefixInfo.put(serverPlayer.getUUID(), prefixInfo);
        }

        List<UUID> currentPlayerUUIDList = new ArrayList<>();
        playerList.forEach(serverPlayer -> currentPlayerUUIDList.add(serverPlayer.getUUID()));
        List<PrefixS2CPacket.PrefixInfoWithUUID> prefixInfoToClient = new ArrayList<>();
        PrefixCommand.serverPrefixInfo.forEach((uuid, prefixInfo) -> {
            if (currentPlayerUUIDList.contains(uuid))
                prefixInfoToClient.add(new PrefixS2CPacket.PrefixInfoWithUUID(uuid, prefixInfo));
        });
        for (ServerPlayer serverPlayer : playerList) {
            ModNetworking.sendToClient(new PrefixS2CPacket(prefixInfoToClient), serverPlayer);
        }
    }
}
