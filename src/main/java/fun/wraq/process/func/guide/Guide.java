package fun.wraq.process.func.guide;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.events.mob.chapter2.JorogumoSpawnController;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.networking.GuideHudCloseStatusS2CPacket;
import fun.wraq.process.func.guide.networking.GuideStageS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.endlessinstance.instance.ManaPlainTemple;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.blade.BladeItems;
import fun.wraq.series.instance.mixture.MixtureItems;
import fun.wraq.series.instance.quiver.QuiverItems;
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
import java.util.List;

public class Guide {

    public final List<Component> description;
    public final MyWayPoint myWayPoint;
    public final RewardPlayer rewardPlayer;
    public final int trigXpLevel;
    public static final String GUIDE_HUD_CLOSE_DATA_KEY = "GuideHudClose";
    public static boolean clientGuideHudCloseStatus = false;

    public Guide(List<Component> description, MyWayPoint myWayPoint, RewardPlayer rewardPlayer, int trigXpLevel) {
        this.description = description;
        this.myWayPoint = myWayPoint;
        this.rewardPlayer = rewardPlayer;
        this.trigXpLevel = trigXpLevel;
    }

    public Guide(List<Component> description, MyWayPoint myWayPoint, RewardPlayer rewardPlayer) {
        this(description, myWayPoint, rewardPlayer, 0);
    }

    public static int clientStage = 0;
    public static String key = "guide";

    public static int getPlayerCurrentStage(Player player) {
        return player.getPersistentData().getInt(key);
    }

    public static void setPlayerCurrentStage(Player player, int stage) {
        player.getPersistentData().putInt(key, stage);
        sendStageToClient(player);
    }

    private static List<Guide> guides = new ArrayList<>();
    public static List<Guide> getGuides() {
        if (guides.isEmpty()) {
            // 0
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 兑换背包").withStyle(ChatFormatting.GOLD),
                    Te.s("根据路径点，找到", "背包商人", CustomStyle.styleOfGold, "，兑换背包")
            ), new MyWayPoint(new Vec3(949, 236, -7), "背包商人", MyWayPoint.colorMap.get(MyWayPoint.gold), 1), null));
            // 1
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 使用翻滚").withStyle(ChatFormatting.GOLD),
                    Te.s("按下z键，使用", "翻滚", CustomStyle.styleOfFlexible)), null, null));
            // 2
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 打开图鉴").withStyle(ChatFormatting.GOLD),
                    Te.s("右键", ModItems.ID_Card, "打开", "图鉴", CustomStyle.styleOfGold)), null, null));
            // 3
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 击杀第一只怪物").withStyle(ChatFormatting.GOLD));
                add(Component.literal("前往平原村南部，击杀").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN)));
                add(Component.literal("你可以按下M打开地图，查看位置").withStyle(ChatFormatting.WHITE));
            }}, new MyWayPoint(new Vec3(754, 78, 265), "平原僵尸刷怪点", MyWayPoint.colorMap.get(MyWayPoint.green), 1), null));
            // 4
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 完成第一次锻造").withStyle(ChatFormatting.GOLD));
                add(Component.literal("手持锻造锤右键锻造台").withStyle(ChatFormatting.WHITE));
                add(Component.literal("打开锻造界面，进行锻造").withStyle(ChatFormatting.WHITE));
                add(Component.literal("提示：推荐首先锻造").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("平原靴子").withStyle(ChatFormatting.GREEN)));
                add(Te.s("离开", "平原村", CustomStyle.styleOfPlain, "之前，可以备一组", Items.LEATHER, "在身上"));
                add(Te.s("后续制作", "防具", CustomStyle.styleOfStone, "时可能需要使用"));
            }}, new MyWayPoint(new Vec3(730, 85, 211), "锻造台", MyWayPoint.colorMap.get(MyWayPoint.aqua), 1), null));
            // 5
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 完成第一次灌注").withStyle(ChatFormatting.GOLD));
                add(Component.literal("右键灌注台，对任意武器进行一次灌注").withStyle(ChatFormatting.WHITE));
                add(Component.literal("对于描述最后一行有").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("[可灌注/增幅]").withStyle(CustomStyle.styleOfInject)));
                add(Component.literal("的物品或装备，你可以把它放到").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("灌注台").withStyle(CustomStyle.styleOfInject)));
                add(Component.literal("查看其灌注获得的物品及所需材料").withStyle(ChatFormatting.WHITE));
            }}, new MyWayPoint(new Vec3(730, 85, 211), "灌注台", MyWayPoint.colorMap.get(MyWayPoint.purple), 1), null));
            // 6
            guides.add(new Guide(new ArrayList<>() {{
                add(Component.literal("引导 - 打开元素轮盘").withStyle(ChatFormatting.AQUA));
                add(Component.literal("按下[左ALT]打开元素轮盘").withStyle(ChatFormatting.WHITE));
            }}, null, (player -> {
                ItemStack stack = new ItemStack(ModItems.PlainRing.get());
                Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                        Te.s("你完成了所有基础引导任务，接下来的引导将会指引你进行一般流程的游玩。",
                                "这件", stack.getDisplayName(), "应该能在探索的途中帮到你。"));
                sendFormatMSG(player, Te.s("你也可以", "关闭引导", ChatFormatting.RED, "自行探索"));
                InventoryOperation.itemStackGiveWithMSG(player, stack);
                List.of(new ItemStack(ModItems.ForestRune.get()), new ItemStack(ModItems.SkillReset.get(), 8),
                        new ItemStack(ModItems.TP_TICKET.get(), 32)).forEach(itemStack -> {
                            InventoryOperation.itemStackGiveWithMSG(player, itemStack);
                });
            })));
            // 7
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "森林装备", CustomStyle.styleOfForest),
                    Te.s("击杀", "森林僵尸", CustomStyle.styleOfForest, "收集", ModItems.ForestSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.ForestRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "森林装备", CustomStyle.styleOfForest),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("如果选择游玩", "法师", CustomStyle.styleOfMana, "职业", "推荐兑换", ModItems.ForestPower.get())
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.LakeRune.get()));
            })));
            // 8
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "湖泊装备", CustomStyle.styleOfLake),
                    Te.s("击杀", "河流故灵", CustomStyle.styleOfLake, "收集", ModItems.LakeSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.LakeRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "湖泊装备", CustomStyle.styleOfLake),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("如果选择游玩", "法师", CustomStyle.styleOfMana, "职业", "推荐兑换", ModItems.LakePower.get()),
                    Te.s("推荐所有玩家均锻造一把", ModItems.LakeSword0.get(), "可以配合鞘翅持续飞行")
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.MineRune.get()));
            })));
            // 9
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "矿洞装备", CustomStyle.styleOfMine),
                    Te.s("击杀", "被遗忘的矿工", CustomStyle.styleOfMine, "收集", ModItems.MineSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.MineRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "矿洞装备", CustomStyle.styleOfMine),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("需要", Items.IRON_INGOT, "?", "找", "雨林村", CustomStyle.styleOfForest, "的",
                            "采矿大师", CustomStyle.styleOfMine),
                    Te.s("购买一把镐子，你可以用镐子", "挖掘", CustomStyle.styleOfStone, "任意矿石"),
                    Te.s("在", "11", ChatFormatting.AQUA, "层以下，你还可以挖掘石头与深板岩"),
                    Te.s("此外，你还可以通过", "击杀", ChatFormatting.RED, "矿区的怪物来获取矿石"),
                    Te.s("在收集到粗矿石后，你可以使用", "冶炼炉", CustomStyle.styleOfPower,
                            "来", "冶炼", CustomStyle.styleOfPower, "矿石", CustomStyle.styleOfMine)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.VolcanoRune.get()));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(PickaxeItems.TINKER_STONE.get(), 3));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(PickaxeItems.TINKER_IRON.get(), 1));
            })));
            // 10
            guides.add(new Guide(List.of(
                    Te.s("锻造任意", "火山装备", CustomStyle.styleOfVolcano),
                    Te.s("击杀", "焰芒虫/燃魂", CustomStyle.styleOfVolcano, "收集", ModItems.VolcanoSoul.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "兑换", ModItems.VolcanoRune.get()),
                    Te.s("在", "雨林村", CustomStyle.styleOfForest, "锻造台", CustomStyle.styleOfStone,
                            "锻造任意", "火山装备", CustomStyle.styleOfVolcano),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone),
                    Te.s("如果选择游玩", "法师", CustomStyle.styleOfMana, "职业", "推荐兑换", ModItems.VolcanoPower.get())
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.SkyRune.get()));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.EvokerRune.get()));
            })));
            // 11
            guides.add(new Guide(List.of(
                    Te.s("击败", "普莱尼", CustomStyle.styleOfPlain),
                    Te.s("提示:", ChatFormatting.AQUA, "需要", "大量经验", ChatFormatting.LIGHT_PURPLE, "?"),
                    Te.s("前往", "炼魔庙", CustomStyle.styleOfMana, "，挑战",
                            "无尽熵增 - ", CustomStyle.styleOfWorld, ManaPlainTemple.getInstance().name)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.PlainBossSoul.get(), 8));
            }), 40));
            // 12
            guides.add(new Guide(List.of(
                    Te.s("升级", BladeItems.BLADE.get(), "/", QuiverItems.QUIVER.get(), "/", MixtureItems.MIXTURE.get()),
                    Te.s("使用", ModItems.PlainBossSoul.get(), "将其灌注为", CustomStyle.styleOfInject, "普莱尼等阶", CustomStyle.styleOfPlain),
                    Te.s("为节约", ModItems.COMPLETE_GEM.get(), "你可以使用", "冶炼炉", CustomStyle.styleOfPower, "来炼造", ModItems.PlainCompleteGem.get())
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
            }), 40));
            // 13
            guides.add(new Guide(List.of(
                    Te.s("锻造一件", "进阶装备", CustomStyle.styleOfSky),
                    Te.s("法师", CustomStyle.styleOfMana, "可以选择", ModItems.EvokerSword.get()),
                    Te.s("物理职业", CustomStyle.styleOfAttack, "可以选择", "天空装备", CustomStyle.styleOfSky),
                    Te.s("注: 装备一般包含", "武器", ChatFormatting.AQUA, "与", "防具", CustomStyle.styleOfStone)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
            })));
            // 14
            guides.add(new Guide(List.of(
                    Te.s("前往", "下界", CustomStyle.styleOfNether),
                    Te.s("前往", "天空城下界协会", CustomStyle.styleOfNether, "购买", ModItems.toNether.get()),
                    Te.s("右键使用", ChatFormatting.AQUA, "前往", "下界", CustomStyle.styleOfNether),
                    Te.s("提示: ", ChatFormatting.AQUA, "若要前往", "终界", CustomStyle.styleOfEnd, "也可购买", ModItems.toEnd.get())
            ), new MyWayPoint(new Vec3(985, 227, 29), "下界协会", MyWayPoint.colorMap.get(MyWayPoint.red), 1), (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.toNether.get(), 8));
            }), 75));
            // 15
            guides.add(new Guide(List.of(
                    Te.s("击败", "燃魂", CustomStyle.styleOfPower),
                    Te.s("提示: ", ChatFormatting.AQUA, "若缺失", "防御属性", CustomStyle.styleOfStone),
                    Te.s("可以击杀", JorogumoSpawnController.mobName, CustomStyle.styleOfJacaranda, "获取", ModItems.lavenderBracelet.get())
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.NETHER_IMPRINT.get(), 4));
            }), 80));
            // 16
            guides.add(new Guide(List.of(
                    Te.s("升级", "普莱尼等阶", CustomStyle.styleOfPlain, "器灵", ChatFormatting.AQUA),
                    Te.s("灌注", CustomStyle.styleOfInject, "为", "燃魂等阶", CustomStyle.styleOfPower)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
            }), 80));
            // 17
            guides.add(new Guide(List.of(
                    Te.s("击败", "紫水晶巨蟹", CustomStyle.styleOfPurpleIron)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.PurpleIronBow2.get(), 1));
            }), 96));
            // 18
            guides.add(new Guide(List.of(
                    Te.s("击败", "冰霜骑士", CustomStyle.styleOfIce)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.IceSoul.get(), 16));
            }), 108));
            // 19
            guides.add(new Guide(List.of(
                    Te.s("击败", "突见忍", CustomStyle.styleOfSakura)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.Boss2Piece.get(), 8));
            }), 150));
            // 20
            guides.add(new Guide(List.of(
                    Te.s("击败", "魔王", CustomStyle.styleOfDemon)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.DevilLoot.get(), 8));
            }), 150));
            // 21
            guides.add(new Guide(List.of(
                    Te.s("击败", "阿尔忒弥斯", CustomStyle.styleOfMoon)
            ), null, (player -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get(), 1));
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.MoonLoot.get(), 8));
            }), 160));
        }
        return guides;
    }

    public static void trig(Player player, int stage) {
        if (getPlayerCurrentStage(player) == stage) {
            Guide guide = getGuides().get(stage);
            if (player.experienceLevel < guide.trigXpLevel) return;
            // defaultReward
            Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                    Component.literal("你完成了引导任务，获得了奖励！").withStyle(ChatFormatting.WHITE));
            Compute.giveExpToPlayer(player, (stage + 1) * 10);
            MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            //
            if (guide.myWayPoint != null) {
                MyWayPoint.sendRemovePacketToClient(player, guide.myWayPoint.name);
            }
            if (guide.rewardPlayer != null) {
                guide.rewardPlayer.reward(player);
            }

            int nextStage = stage + 1;
            setPlayerCurrentStage(player, nextStage);
        }
    }

    public static void sendStageToClient(Player player) {
        int currentStage = getPlayerCurrentStage(player);
        ModNetworking.sendToClient(new GuideStageS2CPacket(currentStage), (ServerPlayer) player);
        if (currentStage < getGuides().size()) {
            Guide guide = getGuides().get(currentStage);
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
}
