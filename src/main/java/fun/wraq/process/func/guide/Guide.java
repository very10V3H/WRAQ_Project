package fun.wraq.process.func.guide;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.events.mob.chapter2.JorogumoSpawnController;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.networking.GuideDisplayS2CPacket;
import fun.wraq.process.func.guide.networking.GuideHudCloseStatusS2CPacket;
import fun.wraq.process.func.guide.networking.GuideStageS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.endlessinstance.instance.ManaPlainTemple;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guide {

    public final List<Component> description;
    public final MyWayPoint myWayPoint;
    public final RewardPlayer rewardPlayer;
    public final int trigXpLevel;
    public static final String GUIDE_HUD_CLOSE_DATA_KEY = "GuideHudClose";
    public static boolean clientGuideHudCloseStatus = false;
    public final String nextStageTag;

    public Guide(List<Component> description, MyWayPoint myWayPoint, RewardPlayer rewardPlayer,
                 int trigXpLevel, String nextStageTag) {
        this.description = description;
        this.myWayPoint = myWayPoint;
        this.rewardPlayer = rewardPlayer;
        this.trigXpLevel = trigXpLevel;
        this.nextStageTag = nextStageTag;
    }

    public Guide(List<Component> description, MyWayPoint myWayPoint, RewardPlayer rewardPlayer, String nextStageTag) {
        this(description, myWayPoint, rewardPlayer, 0, nextStageTag);
    }

    public static boolean clientDisplay = true;
    public static int clientStage = 0;
    public static String key = "guide";
    public static String keyV2 = "newGuide";

    public static int getPlayerCurrentStage(Player player) {
        return player.getPersistentData().getInt(key);
    }

    public static void setPlayerCurrentStage(Player player, int stage) {
        player.getPersistentData().putInt(key, stage);
        sendStageToClientV2(player);
    }

    public static void transferToV2Key(Player player) {
        int oldStage = getPlayerCurrentStage(player);
        if (oldStage == -1) {
            return;
        }
        switch (oldStage) {
            case 0 -> {
                setPlayerCurrentStageV2(player, StageV2.BACKPACK);
            }
            case 1 -> {
                setPlayerCurrentStageV2(player, StageV2.ROLLING);
            }
            case 2 -> {
                setPlayerCurrentStageV2(player, StageV2.ILLUSTRATE);
            }
            case 3 -> {
                setPlayerCurrentStageV2(player, StageV2.FIRST_KILL);
            }
            case 4 -> {
                setPlayerCurrentStageV2(player, StageV2.FIRST_FORGE);
            }
            case 5 -> {
                setPlayerCurrentStageV2(player, StageV2.FIRST_INJECT);
            }
            case 6 -> {
                setPlayerCurrentStageV2(player, StageV2.ELEMENT_ROULETTE);
            }
            case 7 -> {
                setPlayerCurrentStageV2(player, StageV2.FOREST_EQUIP);
            }
            case 8 -> {
                setPlayerCurrentStageV2(player, StageV2.LAKE_EQUIP);
            }
            case 9 -> {
                setPlayerCurrentStageV2(player, StageV2.MINE_EQUIP);
            }
            case 10 -> {
                setPlayerCurrentStageV2(player, StageV2.VOLCANO_EQUIP);
            }
            case 11 -> {
                setPlayerCurrentStageV2(player, StageV2.PLAIN_BOSS);
            }
            case 12 -> {
                setPlayerCurrentStageV2(player, StageV2.PASSIVE_4_LEVEL);
            }
            case 13 -> {
                setPlayerCurrentStageV2(player, StageV2.ENHANCE_EQUIP);
            }
            case 14 -> {
                setPlayerCurrentStageV2(player, StageV2.TO_NETHER);
            }
            case 15 -> {
                setPlayerCurrentStageV2(player, StageV2.NETHER_BOSS);
            }
            case 16, 17 -> {
                setPlayerCurrentStageV2(player, StageV2.PURPLE_IRON_BOSS);
            }
            case 18 -> {
                setPlayerCurrentStageV2(player, StageV2.ICE_KNIGHT);
            }
            case 19 -> {
                setPlayerCurrentStageV2(player, StageV2.SAKURA_BOSS);
            }
            case 20 -> {
                setPlayerCurrentStageV2(player, StageV2.DEVIL_BOSS);
            }
            case 21 -> {
                setPlayerCurrentStageV2(player, StageV2.MOON_BOSS);
            }
            case 22 -> {
                setPlayerCurrentStageV2(player, StageV2.FINAL);
            }
            default -> {
                setPlayerCurrentStageV2(player, StageV2.BACKPACK);
            }
        }
    }

    public static String getPlayerCurrentStageV2(Player player) {
        return player.getPersistentData().getString(keyV2);
    }

    public static void setPlayerCurrentStageV2(Player player, String tag) {
        player.getPersistentData().putString(keyV2, tag);
    }

    public static class StageV2 {
        public static final String BACKPACK = "backpack";
        public static final String ROLLING = "rolling";
        public static final String ILLUSTRATE = "illustrate";
        public static final String FIRST_KILL = "firstKill";
        public static final String CHOOSE_SKILL_V2 = "chooseSkillV2";
        public static final String FIRST_FORGE = "firstForge";
        public static final String FIRST_INJECT = "firstInject";
        public static final String ELEMENT_ROULETTE = "elementRoulette";
        public static final String FOREST_EQUIP = "forestEquip";
        public static final String LAKE_EQUIP = "lakeEquip";
        public static final String MINE_EQUIP = "mineEquip";
        public static final String VOLCANO_EQUIP = "volcanoEquip";
        public static final String PLAIN_BOSS = "plainBoss";
        public static final String PASSIVE_4_LEVEL = "passive4Level";
        public static final String ENHANCE_EQUIP = "enhanceEquip";
        public static final String TO_NETHER = "toNether";
        public static final String NETHER_BOSS = "netherBoss";
        public static final String PURPLE_IRON_BOSS = "purpleIronBoss";
        public static final String ICE_KNIGHT = "iceKnight";
        public static final String SAKURA_BOSS = "sakuraBoss";
        public static final String DEVIL_BOSS = "devilBoss";
        public static final String MOON_BOSS = "moonBoss";
        public static final String FINAL = "final";
    }

    private static List<Guide> guides = new ArrayList<>();

    public static List<Guide> getGuides() {
        if (guides.isEmpty()) {
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 兑换背包").withStyle(ChatFormatting.GOLD),
                    Te.s("根据路径点，找到", "背包商人", CustomStyle.styleOfGold, "，兑换背包")
            ), new MyWayPoint(new Vec3(949, 236, -7), "背包商人", MyWayPoint.colorMap.get(MyWayPoint.gold), 1), null,
                    StageV2.ROLLING));
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 使用翻滚").withStyle(ChatFormatting.GOLD),
                    Te.s("按下z键，使用", "翻滚", CustomStyle.styleOfFlexible)), null, null,
                    StageV2.ILLUSTRATE));
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 打开图鉴").withStyle(ChatFormatting.GOLD),
                    Te.s("右键", ModItems.ID_Card, "打开", "图鉴", CustomStyle.styleOfGold)), null, null,
                    StageV2.CHOOSE_SKILL_V2));
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 选择技能组").withStyle(ChatFormatting.GOLD),
                    Te.s("按下[O]键，打开", "技能配置页面", CustomStyle.styleOfWorld),
                    Te.s("选择一个", "技能组", CustomStyle.styleOfWorld)), null, null,
                    StageV2.FIRST_KILL));
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 击杀第一只怪物").withStyle(ChatFormatting.GOLD));
                add(Component.literal("前往平原村南部，击杀").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN)));
                add(Component.literal("你可以按下M打开地图，查看位置").withStyle(ChatFormatting.WHITE));
            }}, new MyWayPoint(new Vec3(754, 78, 265), "平原僵尸刷怪点", MyWayPoint.colorMap.get(MyWayPoint.green), 1), null,
                    StageV2.FIRST_FORGE));
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 完成第一次锻造").withStyle(ChatFormatting.GOLD));
                add(Component.literal("手持锻造锤右键锻造台").withStyle(ChatFormatting.WHITE));
                add(Component.literal("打开锻造界面，进行锻造").withStyle(ChatFormatting.WHITE));
                add(Component.literal("提示：推荐首先锻造").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原靴子").withStyle(ChatFormatting.GREEN)));
                add(Te.s("离开", "平原村", CustomStyle.styleOfPlain, "之前，可以备一组", Items.LEATHER, "在身上"));
                add(Te.s("后续制作", "防具", CustomStyle.styleOfStone, "时可能需要使用"));
            }}, new MyWayPoint(new Vec3(730, 85, 211), "锻造台", MyWayPoint.colorMap.get(MyWayPoint.aqua), 1), null,
                    StageV2.FIRST_INJECT));
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 完成第一次灌注").withStyle(ChatFormatting.GOLD));
                add(Component.literal("右键灌注台，对任意武器进行一次灌注").withStyle(ChatFormatting.WHITE));
                add(Component.literal("对于描述最后一行有").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("[可灌注/增幅]").withStyle(CustomStyle.styleOfInject)));
                add(Component.literal("的物品或装备，你可以把它放到").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("灌注台").withStyle(CustomStyle.styleOfInject)));
                add(Component.literal("查看其灌注获得的物品及所需材料").withStyle(ChatFormatting.WHITE));
            }}, new MyWayPoint(new Vec3(730, 85, 211), "灌注台", MyWayPoint.colorMap.get(MyWayPoint.purple), 1), null,
                    StageV2.ELEMENT_ROULETTE));
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 打开元素轮盘").withStyle(ChatFormatting.AQUA));
                add(Component.literal("按下[左ALT]打开元素轮盘").withStyle(ChatFormatting.WHITE));
            }}, null, (player -> {
                ItemStack stack = new ItemStack(ModItems.PlainRing.get());
                Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                        Te.s("你完成了所有基础引导任务，接下来的引导将会指引你进行一般流程的游玩。",
                                "这件", stack.getDisplayName(), "应该能在探索的途中帮到你。"));
                sendFormatMSG(player, Te.s("你也可以", "关闭引导", ChatFormatting.RED, "自行探索"));
                InventoryOperation.giveItemStackWithMSG(player, stack);
                List.of(new ItemStack(ModItems.ForestRune.get()), new ItemStack(ModItems.SkillReset.get(), 8),
                        new ItemStack(ModItems.TP_TICKET.get(), 32)).forEach(itemStack -> {
                    InventoryOperation.giveItemStackWithMSG(player, itemStack);
                });
            }), StageV2.FOREST_EQUIP));
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "森林装备", CustomStyle.styleOfForest),
                    Te.s("击杀", "森林僵尸", CustomStyle.styleOfForest, "收集", ModItems.ForestSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.ForestRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "森林装备", CustomStyle.styleOfForest),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("选择", "法术", CustomStyle.styleOfMana, "的玩家可以选择灌注", ModItems.LIFE_SCEPTRE_2),
                    Te.s("至", ModItems.LIFE_SCEPTRE_3, "或", ModItems.LIFE_SCEPTRE_X),
                    Te.s("或兑换", ModItems.ForestManaBook)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.LakeRune.get()));
            }), StageV2.LAKE_EQUIP));
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "湖泊装备", CustomStyle.styleOfLake),
                    Te.s("击杀", "河流故灵", CustomStyle.styleOfLake, "收集", ModItems.LakeSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.LakeRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "湖泊装备", CustomStyle.styleOfLake),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("选择", "法术", CustomStyle.styleOfMana, "的玩家可以选择兑换", ModItems.LakeManaBook)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.MineRune.get()));
            }), StageV2.MINE_EQUIP));
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "矿洞装备", CustomStyle.styleOfMine),
                    Te.s("击杀", "被遗忘的矿工", CustomStyle.styleOfMine, "收集", ModItems.MineSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.MineRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "矿洞装备", CustomStyle.styleOfMine),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("选择", "法术", CustomStyle.styleOfMana, "的玩家可以选择兑换", ModItems.MINE_MANA_NOTE),
                    Te.s(""),
                    Te.s("需要", Items.IRON_INGOT, "?", "找", "雨林村", CustomStyle.styleOfForest, "的",
                            "采矿大师", CustomStyle.styleOfMine),
                    Te.s("购买一把镐子，你可以用镐子", "挖掘", CustomStyle.styleOfStone, "任意矿石"),
                    Te.s("在", "11", ChatFormatting.AQUA, "层以下，你还可以挖掘石头与深板岩"),
                    Te.s("此外，你还可以通过", "击杀", ChatFormatting.RED, "矿区的怪物来获取矿石"),
                    Te.s("在收集到粗矿石后，你可以使用", "冶炼炉", CustomStyle.styleOfPower,
                            "来", "冶炼", CustomStyle.styleOfPower, "矿石", CustomStyle.styleOfMine)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.VolcanoRune.get()));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(PickaxeItems.TINKER_STONE.get(), 3));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(PickaxeItems.TINKER_IRON.get(), 1));
            }), StageV2.VOLCANO_EQUIP));
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "火山装备", CustomStyle.styleOfVolcano),
                    Te.s("击杀", "焰芒虫/燃魂", CustomStyle.styleOfVolcano, "收集", ModItems.VolcanoSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.VolcanoRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "火山装备", CustomStyle.styleOfVolcano),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("选择", "法术", CustomStyle.styleOfMana, "的玩家可以选择兑换", ModItems.VolcanoManaBook)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SkyRune.get()));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.EvokerRune.get()));
            }), StageV2.PLAIN_BOSS));
            guides.add(new Guide(List.of(
                    Te.s("击败", "普莱尼", CustomStyle.styleOfPlain),
                    Te.s("提示:", ChatFormatting.AQUA, "需要", "大量经验", ChatFormatting.LIGHT_PURPLE, "?"),
                    Te.s("前往", "炼魔庙", CustomStyle.styleOfMana, "，挑战",
                            "无尽熵增 - ", CustomStyle.styleOfWorld, ManaPlainTemple.getInstance().name)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.PlainBossSoul.get(), 8));
            }), 40, StageV2.PASSIVE_4_LEVEL));
            guides.add(new Guide(List.of(
                    Te.s("将", "任意被动技能", ChatFormatting.GREEN, "提升至", "4级", ChatFormatting.LIGHT_PURPLE),
                    Te.s("提示:", ChatFormatting.AQUA, "需要", "大量经验", ChatFormatting.LIGHT_PURPLE, "?"),
                    Te.s("前往", "炼魔庙", CustomStyle.styleOfMana, "，挑战",
                            "无尽熵增 - ", CustomStyle.styleOfWorld, ManaPlainTemple.getInstance().name)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.GEM_PIECE.get(), 16));
            }), 60, StageV2.ENHANCE_EQUIP));
            guides.add(new Guide(List.of(
                    Te.s("锻造一件", "天空城进阶装备", CustomStyle.styleOfSky),
                    Te.s("法师", CustomStyle.styleOfMana, "可以选择", ModItems.EvokerSword.get()),
                    Te.s("物理职业", CustomStyle.styleOfAttack, "可以选择", "天空装备", CustomStyle.styleOfSky),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
            }), StageV2.TO_NETHER));
            guides.add(new Guide(List.of(
                    Te.s("前往", "下界", CustomStyle.styleOfNether),
                    Te.s("前往", "天空城下界协会", CustomStyle.styleOfNether, "购买", ModItems.toNether.get()),
                    Te.s("右键使用", ChatFormatting.AQUA, "前往", "下界", CustomStyle.styleOfNether),
                    Te.s("提示: ", ChatFormatting.AQUA, "若要前往", "终界", CustomStyle.styleOfEnd, "也可购买", ModItems.toEnd.get())
            ), new MyWayPoint(new Vec3(985, 227, 29), "下界协会", MyWayPoint.colorMap.get(MyWayPoint.red), 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.toNether.get(), 8));
            }), StageV2.NETHER_BOSS));
            guides.add(new Guide(List.of(
                    Te.s("击败", "燃魂", CustomStyle.styleOfPower),
                    Te.s("提示: ", ChatFormatting.AQUA, "若缺失", "防御属性", CustomStyle.styleOfStone),
                    Te.s("可以击杀", JorogumoSpawnController.mobName, CustomStyle.styleOfJacaranda, "获取", ModItems.lavenderBracelet.get())
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.NETHER_IMPRINT.get(), 4));
            }), 80, StageV2.PURPLE_IRON_BOSS));
            guides.add(new Guide(List.of(
                    Te.s("击败", "紫水晶巨蟹", CustomStyle.styleOfPurpleIron)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.PurpleIronBow2.get(), 1));
            }), 96, StageV2.ICE_KNIGHT));
            guides.add(new Guide(List.of(
                    Te.s("击败", "冰霜骑士", CustomStyle.styleOfIce)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.IceSoul.get(), 16));
            }), 108, StageV2.SAKURA_BOSS));
            guides.add(new Guide(List.of(
                    Te.s("击败", "突见忍", CustomStyle.styleOfSakura)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.Boss2Piece.get(), 8));
            }), 150, StageV2.DEVIL_BOSS));
            guides.add(new Guide(List.of(
                    Te.s("击败", "魔王", CustomStyle.styleOfDemon)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.DevilLoot.get(), 8));
            }), 150, StageV2.MOON_BOSS));
            guides.add(new Guide(List.of(
                    Te.s("击败", "阿尔忒弥斯", CustomStyle.styleOfMoon)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.MoonLoot.get(), 8));
            }), 160, StageV2.FINAL));
        }
        return guides;
    }

    public static Map<String, Integer> stageToIndexMap = new HashMap<>();

    public static Map<String, Integer> getStageToIndexMap() {
        if (stageToIndexMap.isEmpty()) {
            for (int i = 0; i < getGuides().size(); i++) {
                stageToIndexMap.put(getGuides().get(i).nextStageTag, i + 1);
            }
            stageToIndexMap.put(StageV2.BACKPACK, 0);
        }
        return stageToIndexMap;
    }

    public static void trigV2(Player player, String stageTag) {
        if (getPlayerCurrentStageV2(player).equals(stageTag)) {
            if (!getStageToIndexMap().containsKey(stageTag)) {
                Compute.sendErrorTips(player, Te.s("引导任务出错了！速速联系铁头"));
                return;
            }
            int stage = getStageToIndexMap().get(stageTag);
            Guide guide = getGuides().get(stage);
            if (player.experienceLevel < guide.trigXpLevel) return;
            Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                    Component.literal("你完成了引导任务，获得了奖励！").withStyle(ChatFormatting.WHITE));
            Compute.giveExpToPlayer(player, (stage + 1) * 10);
            MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            if (guide.myWayPoint != null) {
                MyWayPoint.sendRemovePacketToClient(player, guide.myWayPoint.name);
            }
            if (guide.rewardPlayer != null) {
                guide.rewardPlayer.reward(player);
            }
            setPlayerCurrentStageV2(player, guide.nextStageTag);
            sendGuideDisplayStatusToClient(player, true);
        }
    }

    public static void sendStageToClientV2(Player player) {
        int oldStage = getPlayerCurrentStage(player);
        if (oldStage > 0) {
            transferToV2Key(player);
            setPlayerCurrentStage(player, -1);
        }
        if (oldStage == 0) {
            setPlayerCurrentStageV2(player, StageV2.BACKPACK);
            setPlayerCurrentStage(player, -1);
        }
        String currentStageTag = getPlayerCurrentStageV2(player);
        if (!getStageToIndexMap().containsKey(currentStageTag)) {
            Compute.sendErrorTips(player, Te.s("引导任务出错了！速速联系铁头"));
            return;
        }
        int currentStageIndex = getStageToIndexMap().get(currentStageTag);
        ModNetworking.sendToClient(new GuideStageS2CPacket(currentStageIndex), (ServerPlayer) player);
        if (currentStageIndex < getGuides().size()) {
            Guide guide = getGuides().get(currentStageIndex);
            if (guide.myWayPoint != null) {
                MyWayPoint.sendAddPacketToClient(player, guide.myWayPoint);
            }
        }
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("引导", ChatFormatting.AQUA), content);
    }

    public static void sendGuideCloseStatusToClient(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(GUIDE_HUD_CLOSE_DATA_KEY)) {
            ModNetworking.sendToClient(
                    new GuideHudCloseStatusS2CPacket(data.getBoolean(GUIDE_HUD_CLOSE_DATA_KEY)), (ServerPlayer) player);
        }
    }

    public static void sendGuideDisplayStatusToClient(Player player, boolean display) {
        ModNetworking.sendToClient(new GuideDisplayS2CPacket(display), player);
    }
}
