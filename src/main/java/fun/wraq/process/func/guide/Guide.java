package fun.wraq.process.func.guide;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.system.xp.MyExpSystem;
import fun.wraq.series.overworld.c1.NewC1Items;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.networking.GuideDisplayS2CPacket;
import fun.wraq.process.func.guide.networking.GuideHudCloseStatusS2CPacket;
import fun.wraq.process.func.guide.networking.GuideStageS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.skill.skillv2.SkillV2;
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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** AI-Generated, 2026-07-18 */
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

    public static String getPlayerCurrentStageV2(Player player) {
        return player.getPersistentData().getString(keyV2);
    }

    public static void setPlayerCurrentStageV2(Player player, String tag) {
        player.getPersistentData().putString(keyV2, tag);
    }

    // ==================== 引导阶段常量 ====================

    public static class StageV2 {
        // ===== 新引导流程 =====
        public static final String UNLOCK_TIDE_CENTER = "unlockTideCenter";
        public static final String MAP_TELEPORT_TIDE_CENTER = "mapTeleportTideCenter";
        public static final String OPEN_BACKPACK = "openBackpack";
        public static final String CLAIM_DAILY_SUPPLY = "claimDailySupply";
        public static final String ROLLING = "rolling";
        public static final String UNLOCK_TIDE_NORTHEAST = "unlockTideNortheast";
        public static final String CHOOSE_SWORD_SKILL = "chooseSwordSkill";
        public static final String KILL_10_PLAIN_ZOMBIE = "kill10PlainZombie";
        public static final String BUY_PLAIN_WEAPON = "buyPlainWeapon";
        public static final String BUY_U_DISK = "buyUDisk";
        public static final String BUY_PLAIN_WEAPON_1 = "buyPlainWeapon1";
        public static final String LEVEL_15 = "level15";
        public static final String KILL_20_FOREST_ZOMBIE = "kill20ForestZombie";
        public static final String BUY_FOREST_WEAPON = "buyForestWeapon";
        public static final String LEVEL_32 = "level32";
        public static final String UNLOCK_XIANGCHAOLIN = "unlockXiangchaolin";
        public static final String DEFEAT_PLAIN_BOSS = "defeatPlainBoss";
        public static final String FIRST_FORGE_STONE = "firstForgeStone";
        public static final String DROWNED_CHALLENGE = "drownedChallenge";

        // ===== 苍岩区域引导 =====
        public static final String UNLOCK_CANGYAN_PASS = "unlockCangyanPass";
        public static final String KILL_20_GRAY_SLIME = "kill20GraySlime";
        public static final String DEFEAT_GRAY_GUARDIAN = "defeatGrayGuardian";
        public static final String FORGE_MINE_HELMET = "forgeMineHelmet";
        public static final String ACCEPT_EXPEDITION_O = "acceptExpeditionO";

        // ===== 旧版常量保留（仅用于避免编译错误，无实际作用） =====
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String BACKPACK = "backpack";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String ILLUSTRATE = "illustrate";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String CHOOSE_SKILL_V2 = "chooseSkillV2";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String FIRST_KILL = "firstKill";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String FIRST_FORGE = "firstForge";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String FIRST_INJECT = "firstInject";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String ELEMENT_ROULETTE = "elementRoulette";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String FOREST_EQUIP = "forestEquip";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String LAKE_EQUIP = "lakeEquip";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String MINE_EQUIP = "mineEquip";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String VOLCANO_EQUIP = "volcanoEquip";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String MANA_DAILY_INSTANCE = "manaDailyInstance";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String DAILY_MISSION_STAR = "dailyMissionStar";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String DAILY_MISSION_COLLECT = "dailyMissionCollect";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String PLAIN_BOSS = "plainBoss";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String PASSIVE_4_LEVEL = "passive4Level";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String ENTRUSTMENT = "entrustment";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String ENHANCE_EQUIP = "enhanceEquip";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String TO_NETHER = "toNether";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String NETHER_BOSS = "netherBoss";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String PURPLE_IRON_BOSS = "purpleIronBoss";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String CORRIDOR = "corridor";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String ICE_KNIGHT = "iceKnight";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String WEEKLY_STORE = "weeklyStore";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String SAKURA_BOSS = "sakuraBoss";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String DEVIL_BOSS = "devilBoss";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String MOON_BOSS = "moonBoss";
        /** @deprecated 旧版引导，仅保留兼容性 */
        @Deprecated public static final String FINAL = "final";
    }

    private static final List<Guide> guides = new ArrayList<>();

    // ==================== 引导定义 ====================

    public static List<Guide> getGuides() {
        if (guides.isEmpty()) {
            // 1. 解锁潮汐城中央广场传送锚点
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 解锁传送锚点").withStyle(ChatFormatting.GOLD),
                    Te.s("前往潮汐城中央广场，找到发光的", "传送锚点", CustomStyle.styleOfEnd),
                    Te.s("站在附近", "右键", ChatFormatting.AQUA, "以解锁"),
                    Te.s("按M打开世界地图可以看到自己的位置"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(3925, 82, 3499), "潮汐城中央广场传送锚点",
                    MyWayPoint.colorMap.get(MyWayPoint.gold), 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.ORIGIN_KNIFE_PLAIN.get()));
            }), StageV2.MAP_TELEPORT_TIDE_CENTER));

            // 2. 按下M使用世界地图传送
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 使用路径点传送").withStyle(ChatFormatting.GOLD),
                    Te.s("按", "M", ChatFormatting.AQUA, "打开世界地图"),
                    Te.s("在地图上找到", "潮汐城中央广场", CustomStyle.styleOfSea, "的路径点标记"),
                    Te.s("右键点击路径点，执行", "T", ChatFormatting.GREEN, "Teleport to Waypoint", "命令进行传送")
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(Items.GOLDEN_CARROT, 50));
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(Items.BAKED_POTATO, 50));
            }), StageV2.OPEN_BACKPACK));

            // 3. 按下B打开背包
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 打开个人背包").withStyle(ChatFormatting.GOLD),
                    Te.s("按下", "B", ChatFormatting.AQUA, "键打开个人背包"),
                    Te.s("你可以在背包中存放物品")
            ), null, (player -> {
                ItemStack elytra = new ItemStack(Items.ELYTRA);
                elytra.enchant(Enchantments.UNBREAKING, 10);
                InventoryOperation.giveItemStackWithMSG(player, elytra);
            }), StageV2.CLAIM_DAILY_SUPPLY));

            // 4. 右键身份卡领取每日补给
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 领取每日补给").withStyle(ChatFormatting.GOLD),
                    Te.s("右键", ModItems.ID_CARD, "打开身份卡"),
                    Te.s("点击", "每日补给", CustomStyle.styleOfGold, "领取今日补给")
            ), null, null, StageV2.ROLLING));

            // 5. 按下z键使用翻滚
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 使用翻滚").withStyle(ChatFormatting.GOLD),
                    Te.s("按下", "Z", ChatFormatting.AQUA, "键使用翻滚"),
                    Te.s("翻滚可以躲避敌人的攻击")
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.BAMBOO_KANATA.get()));
            }), StageV2.UNLOCK_TIDE_NORTHEAST));

            // 6. 解锁潮汐城东北门传送锚点
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 解锁传送锚点").withStyle(ChatFormatting.GOLD),
                    Te.s("前往潮汐城", "东北门", CustomStyle.styleOfSea, "，找到传送锚点"),
                    Te.s("站在附近", "右键", ChatFormatting.AQUA, "以解锁"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(3977, 76, 3416), "潮汐城东北门传送锚点",
                    MyWayPoint.colorMap.get(MyWayPoint.gold), 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.COPPER_COIN.get(), 30));
            }), StageV2.CHOOSE_SWORD_SKILL));

            // 7. 按下N选择剑术-技能组，使用一次横扫
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 使用剑术技能").withStyle(ChatFormatting.GOLD),
                    Te.s("按下", "N", ChatFormatting.AQUA, "键打开技能界面"),
                    Te.s("选择", "剑术", ChatFormatting.AQUA, "技能组"),
                    Te.s("按下", "Q", ChatFormatting.AQUA, "键使用技能", "横扫", CustomStyle.styleOfPower)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.PLAIN_CREST_0.get()));
            }), StageV2.KILL_10_PLAIN_ZOMBIE));

            // 8. 击杀10只平原僵尸
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 击杀平原僵尸").withStyle(ChatFormatting.GOLD),
                    Te.s("前往潮汐城外，击杀", "10", ChatFormatting.RED, "只",
                            "平原僵尸", ChatFormatting.GREEN),
                    Te.s("装备上获得的武器，可以让你更轻松地战斗"),
                    Te.s("可以按住左键进行普攻"),
                    Te.s("合理释放技能可以提高清怪效率")
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.PLAIN_SOUL.get(), 10));
            }), StageV2.BUY_PLAIN_WEAPON));

            // 8. 购买平原短匕或平原长弓或生机权杖
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 购买武器").withStyle(ChatFormatting.GOLD),
                    Te.s("前往潮汐城找到", "武器商人", CustomStyle.styleOfGold),
                    Te.s("购买", "平原短匕", ChatFormatting.AQUA, "或",
                            "平原长弓", ChatFormatting.GREEN, "或",
                            "生机权杖", ChatFormatting.LIGHT_PURPLE)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.COPPER_COIN.get(), 300));
            }), StageV2.BUY_U_DISK));

            // 9. 购买U盾
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 购买U盾").withStyle(ChatFormatting.GOLD),
                    Te.s("前往潮汐城找到", "武器商人", CustomStyle.styleOfGold),
                    Te.s("购买", "U盾", CustomStyle.styleOfWorld)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 10));
            }), StageV2.BUY_PLAIN_WEAPON_1));

            // 10. 购买平原短匕1或平原长弓1或生机权杖1
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 升级武器").withStyle(ChatFormatting.GOLD),
                    Te.s("前往潮汐城找到", "武器商人", CustomStyle.styleOfGold),
                    Te.s("购买", "平原短匕¹", ChatFormatting.AQUA, "或",
                            "平原长弓¹", ChatFormatting.GREEN, "或",
                            "生机权杖¹", ChatFormatting.LIGHT_PURPLE)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.COPPER_COIN.get(), 100));
            }), StageV2.LEVEL_15));

            // 11. 达到15级
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 提升等级").withStyle(ChatFormatting.GOLD),
                    Te.s("需要达到", "15级", ChatFormatting.LIGHT_PURPLE),
                    Te.s("击杀怪物可以获得经验值")
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 10));
            }), 15, StageV2.KILL_20_FOREST_ZOMBIE));

            // 12. 击杀20只森林僵尸
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 击杀森林僵尸").withStyle(ChatFormatting.GOLD),
                    Te.s("前往雨林地区，击杀", "20", ChatFormatting.RED, "只",
                            "森林僵尸", ChatFormatting.GREEN)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.FOREST_SOUL.get(), 10));
            }), StageV2.BUY_FOREST_WEAPON));

            // 13. 购买森林粉碎者或森林长弓或生机权杖2
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 购买森林武器").withStyle(ChatFormatting.GOLD),
                    Te.s("前往项潮桥找到", "武器商人", CustomStyle.styleOfGold),
                    Te.s("购买", "森林粉碎者", ChatFormatting.AQUA, "或",
                            "森林长弓", ChatFormatting.GREEN, "或",
                            "生机权杖²", ChatFormatting.LIGHT_PURPLE)
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.COPPER_COIN.get(), 300));
            }), StageV2.LEVEL_32));

            // 14. 达到32级
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 提升等级").withStyle(ChatFormatting.GOLD),
                    Te.s("需要达到", "32级", ChatFormatting.LIGHT_PURPLE),
                    Te.s("击杀高等级怪物可以获得更多经验"),
                    Te.s("前往", "项潮林锚点西侧", CustomStyle.styleOfForest),
                    Te.s("挑战", "无尽熵增 - ", CustomStyle.styleOfWorld, "尸潮", CustomStyle.styleOfForest, "可以快速获取经验"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(3967.5, 92, 3092.5), "无尽熵增 - 尸潮",
                    MyWayPoint.colorMap.get(MyWayPoint.gold), CustomStyle.styleOfWorld, 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 20));
            }), 32, StageV2.UNLOCK_XIANGCHAOLIN));

            // 15. 解锁项潮林传送锚点
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 解锁传送锚点").withStyle(ChatFormatting.GOLD),
                    Te.s("前往", "项潮林", CustomStyle.styleOfForest, "，找到传送锚点"),
                    Te.s("站在附近", "右键", ChatFormatting.AQUA, "以解锁"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(4021, 119, 3158), "项潮林传送锚点",
                    MyWayPoint.colorMap.get(MyWayPoint.gold), 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.PLAIN_RING.get()));
            }), StageV2.DEFEAT_PLAIN_BOSS));

            // 16. 击败普莱尼
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 击败普莱尼").withStyle(ChatFormatting.GOLD),
                    Te.s("前往", "普莱尼所在地", CustomStyle.styleOfPlain, "击败他"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(4132, 67, 3164), "普莱尼",
                    MyWayPoint.colorMap.get(MyWayPoint.green), CustomStyle.styleOfPlain, 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.PLAIN_HEALTH_RING_0.get()));
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.PLAIN_SOUL.get(), 2));
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.FORGING_STONE_0.get(), 10));
            }), StageV2.FIRST_FORGE_STONE));

            // 使用普通强化石在锻造台强化一次任意装备
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 使用强化石").withStyle(ChatFormatting.GOLD),
                    Te.s("在潮汐城东北门附近找到", "锻造台", CustomStyle.styleOfStone),
                    Te.s("将装备放在锻造台左侧，", "普通强化石", ChatFormatting.GREEN, "放在右侧"),
                    Te.s("点击锻造按钮完成一次强化"),
                    Te.s("强化成功与否都可以完成任务")
            ), new MyWayPoint(new Vec3(3977, 76, 3416), "潮汐城东北门锻铁",
                    MyWayPoint.colorMap.get(MyWayPoint.gold), 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 10));
            }), StageV2.DROWNED_CHALLENGE));

            // 完成本源挑战Drowned
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 完成本源挑战").withStyle(ChatFormatting.GOLD),
                    Te.s("前往", "项潮林", CustomStyle.styleOfForest, "西侧"),
                    Te.s("手持", ModItems.WORLD_SOUL_1, "右键以召唤本源溺尸"),
                    Te.s("击败4只本源溺尸完成挑战"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置"),
                    Te.s(""),
                    Te.s("本源挑战每日可挑战数次，直到达成4星奖励"),
                    Te.s("本源挑战会给予聚星奖励，"),
                    Te.s("聚星可以在身份卡 - 本源商店兑换补给包"),
                    Te.s("打开补给包可概率获得强力饰品、补给品")
            ), new MyWayPoint(new Vec3(3967.5, 92, 3092.5), "本源溺尸",
                    MyWayPoint.colorMap.get(MyWayPoint.blue), CustomStyle.styleOfSea, 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 40));
            }), StageV2.UNLOCK_CANGYAN_PASS));

            // 解锁苍岩隘口传送点
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 解锁传送锚点").withStyle(ChatFormatting.GOLD),
                    Te.s("前往", "苍岩隘口", CustomStyle.styleOfStone, "，找到传送锚点"),
                    Te.s("站在附近", "右键", ChatFormatting.AQUA, "以解锁"),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(3725, 69, 2976), "苍岩隘口",
                    MyWayPoint.colorMap.get(MyWayPoint.gray), 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.GOLD_COIN.get(), 5));
            }), StageV2.KILL_20_GRAY_SLIME));

            // 击杀20只苍岩软玉
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 击杀苍岩软玉").withStyle(ChatFormatting.GOLD),
                    Te.s("前往", "苍岩隘口", CustomStyle.styleOfStone, "周围，击杀", "20", ChatFormatting.RED, "只",
                            "苍岩软玉", CustomStyle.styleOfStone),
                    Te.s("苍岩软玉分布在峡谷中")
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(NewC1Items.GRAY_SLIME_BALL.get(), 10));
            }), StageV2.DEFEAT_GRAY_GUARDIAN));

            // 击败苍岩守卫
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 击败苍岩守卫").withStyle(ChatFormatting.GOLD),
                    Te.s("前往", "苍岩隘口", CustomStyle.styleOfStone, "深处，击败", "苍岩守卫", CustomStyle.styleOfStone),
                    Te.s("可以在世界中找到", "路径点", ChatFormatting.AQUA),
                    Te.s("可以按", "M", ChatFormatting.AQUA, "打开世界地图查看路径点位置")
            ), new MyWayPoint(new Vec3(3577, 80, 2922), "苍岩守卫",
                    MyWayPoint.colorMap.get(MyWayPoint.gray), CustomStyle.styleOfStone, 1), (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.DIAMOND_HAMMER.get(), 2));
            }), StageV2.FORGE_MINE_HELMET));

            // 锻造铁铁铁铁铁头
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 锻造铁铁铁铁铁头").withStyle(ChatFormatting.GOLD),
                    Te.s("在", "潮汐城东北门附近", CustomStyle.styleOfStone, "，使用锻造锤锻造",
                            "铁铁铁铁铁头", CustomStyle.styleOfMine),
                    Te.s("使用任意品质的锻造锤右键锻造台即可打开锻造页面"),
                    Te.s("锻造所需的材料可以在苍岩区域获得")
            ), null, (player -> {
                InventoryOperation.giveItemStackWithMSG(player,
                        new ItemStack(ModItems.GOLD_COIN.get(), 10));
            }), StageV2.ACCEPT_EXPEDITION_O));

            // 接受远征任务
            guides.add(new Guide(List.of(
                    Component.literal("引导 - 接受远征任务").withStyle(ChatFormatting.GOLD),
                    Te.s("右键", ModItems.ID_CARD, "打开身份卡"),
                    Te.s("点击", "任务列表", CustomStyle.styleOfFlexible, "切换到任务页面"),
                    Te.s("找到并接受", "远征O - 熔岩 - 熔岩废墟", CustomStyle.styleOfRed),
                    Te.s("开启远征任务")
            ), null, (player -> {
                MyExpSystem.giveExpToPlayer(player, 100);
            }), StageV2.FINAL));
        }
        return guides;
    }

    public static Map<String, Integer> stageToIndexMap = new HashMap<>();

    public static Map<String, Integer> getStageToIndexMap() {
        if (stageToIndexMap.isEmpty()) {
            for (int i = 0; i < getGuides().size(); i++) {
                stageToIndexMap.put(getGuides().get(i).nextStageTag, i + 1);
            }
            stageToIndexMap.put(StageV2.UNLOCK_TIDE_CENTER, 0);
        }
        return stageToIndexMap;
    }

    // ==================== 核心触发方法 ====================

    public static void trigV2(Player player, String stageTag) {
        if (getPlayerCurrentStageV2(player).equals(stageTag)) {
            if (!getStageToIndexMap().containsKey(stageTag)) {
                Compute.sendErrorTips(player, Te.s("引导任务出错了！速速联系铁头"));
                return;
            }
            int stage = getStageToIndexMap().get(stageTag);
            Guide guide = getGuides().get(stage);
            if (player.experienceLevel < guide.trigXpLevel) {
                return;
            }
            Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                    Component.literal("你完成了引导任务，获得了奖励！").withStyle(ChatFormatting.WHITE));
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

    // ==================== 击杀计数（用于僵尸击杀检测） ====================

    private static final String GUIDE_KILL_COUNT_KEY = "GuideKillCount";

    public static void incrementMobKill(Player player, String mobKey) {
        CompoundTag data = player.getPersistentData();
        CompoundTag killData = data.getCompound(GUIDE_KILL_COUNT_KEY);
        killData.putInt(mobKey, killData.getInt(mobKey) + 1);
        data.put(GUIDE_KILL_COUNT_KEY, killData);
    }

    public static int getMobKillCount(Player player, String mobKey) {
        return player.getPersistentData().getCompound(GUIDE_KILL_COUNT_KEY).getInt(mobKey);
    }

    // ==================== 客户端同步 ====================

    public static void sendStageToClientV2(Player player) {
        if (!player.getPersistentData().contains(keyV2)) {
            setPlayerCurrentStageV2(player, StageV2.UNLOCK_TIDE_CENTER);
        }
        String currentStageTag = getPlayerCurrentStageV2(player);
        if (!getStageToIndexMap().containsKey(currentStageTag)) {
            setPlayerCurrentStageV2(player, StageV2.UNLOCK_TIDE_CENTER);
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

    public static void handlePlayerTick(Player player) {
        if (player.tickCount % 200 == 101) {
            // 等级检测
            if (player.experienceLevel >= 15) {
                trigV2(player, StageV2.LEVEL_15);
            }
            if (player.experienceLevel >= 32) {
                trigV2(player, StageV2.LEVEL_32);
            }
            // 被动技能等级检测（与旧版兼容，但可移除）
            if (SkillV2.getProfessionSkillLevel(player, 0, 0, 0) >= 4
                    || SkillV2.getProfessionSkillLevel(player, 1, 0, 0) >= 4
                    || SkillV2.getProfessionSkillLevel(player, 2, 0, 0) >= 4) {
                // 不再触发旧的 PASSIVE_4_LEVEL，保留空方法体避免破坏已有调用
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
