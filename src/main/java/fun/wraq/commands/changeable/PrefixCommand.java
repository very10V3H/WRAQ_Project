package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.Items.Prefix.PrefixInfo;
import fun.wraq.blocks.blocks.brew.BrewingNote;
import fun.wraq.common.Compute;
import fun.wraq.common.util.StringUtils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.chapter1.ForestZombieSpawnController;
import fun.wraq.events.mob.chapter1.LakeDrownSpawnController;
import fun.wraq.events.mob.chapter1.MineSkeletonSpawnController;
import fun.wraq.events.mob.chapter1.PlainZombieSpawnController;
import fun.wraq.events.mob.chapter2.*;
import fun.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import fun.wraq.events.mob.chapter3_nether.NetherSkeletonSpawnController;
import fun.wraq.events.mob.chapter3_nether.PiglinSpawnController;
import fun.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import fun.wraq.events.mob.chapter4_end.EnderManSpawnController;
import fun.wraq.events.mob.chapter4_end.EndermiteSpawnController;
import fun.wraq.events.mob.chapter4_end.ShulkerSpawnController;
import fun.wraq.events.mob.chapter5.BloodManaSpawnController;
import fun.wraq.events.mob.chapter5.EarthManaSpawnController;
import fun.wraq.events.mob.chapter5.PillagerSpawnController;
import fun.wraq.events.mob.chapter5.SakuraMobSpawnController;
import fun.wraq.events.mob.chapter6_castle.BeaconSpawnController;
import fun.wraq.events.mob.chapter6_castle.BlazeSpawnController;
import fun.wraq.events.mob.chapter6_castle.TreeSpawnController;
import fun.wraq.events.mob.chapter7.BoneImpSpawnController;
import fun.wraq.events.mob.chapter7.StarSpawnController;
import fun.wraq.events.mob.chapter7.TorturedSoulSpawnController;
import fun.wraq.events.mob.instance.instances.dimension.NetherInstance;
import fun.wraq.events.mob.instance.instances.element.IceInstance;
import fun.wraq.events.mob.instance.instances.element.MoonInstance;
import fun.wraq.events.mob.instance.instances.element.PlainInstance;
import fun.wraq.events.mob.instance.instances.element.PurpleIronInstance;
import fun.wraq.events.mob.instance.instances.moontain.MoontainBoss1Instance;
import fun.wraq.events.mob.instance.instances.sakura.DevilInstance;
import fun.wraq.events.mob.instance.instances.sakura.SakuraBossInstance;
import fun.wraq.events.mob.moontain.*;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.PrefixPackets.PrefixS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

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

    public static String simpleFlagPrefixDataKey = "simpleFlagPrefixDataKey";

    public record SimpleFlagPrefixType(String tag, String prefix, Style style) implements PrefixCondition {

        @Override
        public int matchCondition(Player player) {
            CompoundTag data = player.getPersistentData();
            CompoundTag prefixData = data.getCompound(simpleFlagPrefixDataKey);
            return prefixData.contains(tag) ? 1 : 0;
        }

        @Override
        public String getPrefixDescription() {
            return prefix;
        }

        @Override
        public Style getStyle() {
            return style;
        }
    }

    public static boolean activePrefix(Player player, String tag) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(simpleFlagPrefixDataKey)) data.put(simpleFlagPrefixDataKey, new CompoundTag());
        CompoundTag prefixData = data.getCompound(simpleFlagPrefixDataKey);
        if (prefixData.contains(tag)) return false;
        prefixData.putBoolean(tag, true);
        return true;
    }

    public static List<PrefixCondition> getSimplePrefixTypeList() {
        if (simplePrefixTypeList.isEmpty()) {
            simplePrefixTypeList = new ArrayList<>(List.of(
                    new SimplePrefixType(PlainZombieSpawnController.mobName, 1000, "平原统治者", CustomStyle.styleOfPlain),
                    new SimplePrefixType(ForestZombieSpawnController.mobName, 1000, "森林统治者", CustomStyle.styleOfForest),
                    new SimplePrefixType(LakeDrownSpawnController.mobName, 1000, "河流统治者", CustomStyle.styleOfLake),
                    new SimplePrefixType(MineSkeletonSpawnController.mobName, 1000, "矿洞统治者", CustomStyle.styleOfMine),
                    new SimplePrefixType(SpiderSpawnController.mobName, 1000, "雨林统治者", CustomStyle.styleOfSpider),

                    new SimplePrefixType(PlainInstance.mobName, 320, "生机焕发", CustomStyle.styleOfLife),
                    new SimplePrefixType(FireLightSpawnController.mobName, 1000, "焰芒虫天敌", CustomStyle.styleOfFire),
                    new SimplePrefixType(EvokerSpawnController.mobName, 1000, "净魔者", CustomStyle.styleOfMana),
                    new SimplePrefixType(LumberJackSpawnController.mobName, 1000, "伐伐伐伐伐木工", CustomStyle.styleOfHusk),
                    new SimplePrefixType(DreadHoundSpawnController.mobName, 1000, "群狼之首", CustomStyle.styleOfForest),
                    new SimplePrefixType(SkyVexSpawnController.mobName, 1000, "天空城授卫", CustomStyle.styleOfSky),
                    new SimplePrefixType(SlimeSpawnController.mobName, 1000, "史莱姆的好伙伴(?)", CustomStyle.styleOfLife),
                    new SimplePrefixType(JorogumoSpawnController.mobName, 1000, "紫水晶之辉", CustomStyle.styleOfPurpleIron),
                    new SimplePrefixType(SnowStraySpawnController.mobName, 1000, "冰川统治者", CustomStyle.styleOfIce),
                    new SimplePrefixType(NetherInstance.mobName, 320, "焰烬魂燃", CustomStyle.styleOfPower),

                    new SimplePrefixType(WindSkeletonSpawnController.mobName, 1000, "风抹层丛", CustomStyle.styleOfWind),
                    new SimplePrefixType(HuskSpawnController.mobName, 1000, "岩裂石隙", CustomStyle.styleOfHusk),
                    new SimplePrefixType(LightningZombieController.mobName, 1000, "雷光辐照", CustomStyle.styleOfLightning),
                    new SimplePrefixType(GuardianSpawnController.mobName, 1000, "远古神殿清道夫", CustomStyle.styleOfSea),
                    new SimplePrefixType(PurpleIronInstance.mobName, 320, "紫水晶之耀", CustomStyle.styleOfPurpleIron),
                    new SimplePrefixType(IceInstance.mobName, 320, "迷阵之玉", CustomStyle.styleOfIce),

                    new SimplePrefixType(WitherSkeletonSpawnController.mobName, 1000, "凋零灵魂收集者", CustomStyle.styleOfWither),
                    new SimplePrefixType(MagmaSpawnController.mobName, 1000, "熔岩能量收割者", CustomStyle.styleOfPower),
                    new SimplePrefixType(NetherSkeletonSpawnController.mobName, 1000, "下界猎颅者", CustomStyle.styleOfNether),
                    new SimplePrefixType(PiglinSpawnController.mobName, 1000, "拒绝交易(?)", CustomStyle.styleOfGold),

                    new SimplePrefixType(PillagerSpawnController.mobName, 1000, "远洋护卫", CustomStyle.styleOfShip),
                    new SimplePrefixType(BloodManaSpawnController.mobName, 1000, "旧世腥月封印者", CustomStyle.styleOfBloodMana),
                    new SimplePrefixType(EarthManaSpawnController.mobName, 1000, "旧世地蕴封印者", CustomStyle.styleOfJacaranda),
                    new SimplePrefixType(SakuraMobSpawnController.mobName, 1000, "隙之落樱", CustomStyle.styleOfSakura),
                    new SimplePrefixType(SakuraBossInstance.mobName, 320, "黄金大盗", CustomStyle.styleOfGold),
                    new SimplePrefixType(DevilInstance.mobName, 320, "旧世弑君者", CustomStyle.styleOfDemon),

                    new SimplePrefixType(EnderManSpawnController.mobName, 1000, "终界入侵者", CustomStyle.styleOfEnd),
                    new SimplePrefixType(EndermiteSpawnController.mobName, 1000, "圆寂之域", CustomStyle.styleOfEnd),
                    new SimplePrefixType(ShulkerSpawnController.mobName, 1000, "圆寂清道夫", CustomStyle.styleOfEnd),

                    new SimplePrefixType(MoonInstance.mobName, 320, "清辉夜凝", CustomStyle.styleOfMoon1),

                    new SimplePrefixType(BeaconSpawnController.mobName, 1000, "哨戒烽火", CustomStyle.styleOfFire),
                    new SimplePrefixType(BlazeSpawnController.mobName, 1000, "熔岩涌动", CustomStyle.styleOfPower),
                    new SimplePrefixType(TreeSpawnController.mobName, 1000, "古树学者", CustomStyle.styleOfMana),

                    new SimplePrefixType(StarSpawnController.mobName, 1000, "星梦之灵", CustomStyle.styleOfMoon1),
                    new SimplePrefixType(BoneImpSpawnController.mobName, 1000, "炽焰魂魄", CustomStyle.styleOfVolcano),
                    new SimplePrefixType(TorturedSoulSpawnController.mobName, 1000, "解脱者", CustomStyle.styleOfWorld),

                    new SimplePrefixType(MoontainCommon1SpawnController.mobName, 1000, "御魂者", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainCommon1ExSpawnController.mobName, 1000, "*御魂者*", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainCommon2SpawnController.mobName, 1000, "御魂师", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainCommon2ExSpawnController.mobName, 1000, "*御魂师*", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainCommon3SpawnController.mobName, 1000, "典狱长", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainCommon3ExSpawnController.mobName, 1000, "*典狱长*", CustomStyle.styleOfMoontain),

                    new SimplePrefixType(MoontainBoss1Instance.mobName, 320, "望山阁之顶", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainBoss1Instance.mobName, 320, "望山阁之峰", CustomStyle.styleOfMoontain),
                    new SimplePrefixType(MoontainBoss1Instance.mobName, 64, "望山阁之巅", CustomStyle.styleOfMoontain),

                    new ProgressPrefixType(StringUtils.FishCount, 20, 50, "见习渔夫", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)),
                    new ProgressPrefixType(StringUtils.FishCount, 50, 100, "入门渔夫", Style.EMPTY.applyFormat(ChatFormatting.GRAY)),
                    new ProgressPrefixType(StringUtils.FishCount, 100, 200, "中阶渔夫", Style.EMPTY.applyFormat(ChatFormatting.YELLOW)),
                    new ProgressPrefixType(StringUtils.FishCount, 200, 500, "高阶渔夫", Style.EMPTY.applyFormat(ChatFormatting.BLUE)),
                    new ProgressPrefixType(StringUtils.FishCount, 500, 1000, "经常空军的钓鱼佬", Style.EMPTY.applyFormat(ChatFormatting.GOLD)),
                    new ProgressPrefixType(StringUtils.FishCount, 1000, 2000, "偶尔空军的钓鱼佬", Style.EMPTY.applyFormat(ChatFormatting.RED)),
                    new ProgressPrefixType(StringUtils.FishCount, 2000, Integer.MAX_VALUE, "永不空军的钓鱼佬", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)),

                    new ProgressPrefixType(StringUtils.Mine.Exp, 0, 100, "见习矿工", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)),
                    new ProgressPrefixType(StringUtils.Mine.Exp, 100, 1000, "入门矿工", Style.EMPTY.applyFormat(ChatFormatting.GRAY)),
                    new ProgressPrefixType(StringUtils.Mine.Exp, 1000, 5000, "职业矿工", Style.EMPTY.applyFormat(ChatFormatting.GOLD)),
                    new ProgressPrefixType(StringUtils.Mine.Exp, 5000, 20000, "悲催苦力矿工", Style.EMPTY.applyFormat(ChatFormatting.GREEN)),
                    new ProgressPrefixType(StringUtils.Mine.Exp, 20000, 100000, "一只挖矿的帕鲁", Style.EMPTY.applyFormat(ChatFormatting.AQUA)),

                    new ProgressPrefixType(StringUtils.Lop.Xp, 0, 100, "见习伐木工", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)),
                    new ProgressPrefixType(StringUtils.Lop.Xp, 100, 1000, "入门伐木工", Style.EMPTY.applyFormat(ChatFormatting.GRAY)),
                    new ProgressPrefixType(StringUtils.Lop.Xp, 1000, 5000, "职业伐木工", Style.EMPTY.applyFormat(ChatFormatting.GOLD)),
                    new ProgressPrefixType(StringUtils.Lop.Xp, 5000, 20000, "光头强", Style.EMPTY.applyFormat(ChatFormatting.GREEN)),
                    new ProgressPrefixType(StringUtils.Lop.Xp, 20000, 100000, "一只砍树的帕鲁", Style.EMPTY.applyFormat(ChatFormatting.AQUA)),

                    new ProgressPrefixType(StringUtils.Gardening.Xp, 0, 100, "见习农夫", Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY)),
                    new ProgressPrefixType(StringUtils.Gardening.Xp, 100, 1000, "入门农夫", Style.EMPTY.applyFormat(ChatFormatting.GRAY)),
                    new ProgressPrefixType(StringUtils.Gardening.Xp, 1000, 5000, "职业农夫", Style.EMPTY.applyFormat(ChatFormatting.GOLD)),
                    new ProgressPrefixType(StringUtils.Gardening.Xp, 5000, 20000, "农耕大师", Style.EMPTY.applyFormat(ChatFormatting.GREEN)),
                    new ProgressPrefixType(StringUtils.Gardening.Xp, 20000, 100000, "一只种田的帕鲁", Style.EMPTY.applyFormat(ChatFormatting.AQUA)),

                    new ProgressPrefixType(BrewingNote.brewingLevel, 0, 0, "酿造初识", Style.EMPTY.applyFormat(ChatFormatting.GRAY)),
                    new ProgressPrefixType(BrewingNote.brewingLevel, 1, 1, "酿造入门", Style.EMPTY.applyFormat(ChatFormatting.GREEN)),
                    new ProgressPrefixType(BrewingNote.brewingLevel, 2, 2, "酿造初级", Style.EMPTY.applyFormat(ChatFormatting.BLUE)),
                    new ProgressPrefixType(BrewingNote.brewingLevel, 3, 3, "酿造中级", Style.EMPTY.applyFormat(ChatFormatting.YELLOW)),
                    new ProgressPrefixType(BrewingNote.brewingLevel, 4, 4, "酿造高级", Style.EMPTY.applyFormat(ChatFormatting.AQUA)),
                    new ProgressPrefixType(BrewingNote.brewingLevel, 5, 5, "酿造学士", Style.EMPTY.applyFormat(ChatFormatting.GOLD)),
                    new ProgressPrefixType(BrewingNote.brewingLevel, 6, 6, "酿造大师", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE))
            ));
        }
        return simplePrefixTypeList;
    }

    public static List<PrefixCondition> simplePrefixTypeList = new ArrayList<>();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        int count = 0;
        Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("可用称号如下:").withStyle(ChatFormatting.WHITE));

        count++;
        player.sendSystemMessage(Component.literal(count + ".").withStyle(ChatFormatting.WHITE).
                append(Component.literal("初来乍到").withStyle(ChatFormatting.GRAY)));

        for (PrefixCondition prefixCondition : getSimplePrefixTypeList()) {
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
        Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
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

    public static void sendFormatMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD), component);
    }

    public static void sendFormatGetMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("已获得 ").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("称号 - ").withStyle(CustomStyle.styleOfWorld)).
                        append(component));
    }

    public static void sendFormatActiveMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("已激活 ").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("称号 - ").withStyle(CustomStyle.styleOfWorld)).
                        append(component));
    }


}
