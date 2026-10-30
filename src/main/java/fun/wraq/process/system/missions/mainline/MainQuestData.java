package fun.wraq.process.system.missions.mainline;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.mob.instance.instances.element.PlainInstance;
import fun.wraq.events.mob.instance.instances.element.PurpleIronInstance;
import fun.wraq.process.system.missions.mainline.MainQuestStage.*;
import fun.wraq.process.system.npc.NpcIds;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI-Generated, 2026-10-18
 * 主线阶段表（数据）。
 * <p>
 * 阶段与任务只通过 {@link Stage#mission()} 字符串关联，本类不反向引用 {@code MissionV2}。
 * 新增阶段：追加一个 {@link Stage} 常量并放进 {@link #STAGES}，再在 {@code MissionV2} 里加一条
 * 同名枚举即可。任务目标（击杀/通关/每日）只记录进度，推进仍由对话完成。
 */
public final class MainQuestData {

    private MainQuestData() {
    }

    // =================================================================
    // 第一章 · 序章：旭升岛 → 天空城
    // =================================================================

    public static final Stage MAIN_01 = new Stage(
            "main_01_depart_xusheng", "MAIN_01_DEPART_XUSHENG",
            NpcIds.MAIN_BEIBEI_STATION, NpcIds.MAIN_BEIBEI, Kind.AUTO,
            Te.s("出发：旭升岛轻轨", CustomStyle.styleOfSky),
            Te.s("使用世界地图传送"),
            Te.s("旭升岛轻轨站 · 贝贝"),
            Te.s("主线起点"),
            List.of(new Objective.MapTeleport("天空城")),
            List.of(),
            wp("主线 · 站台贝贝", 1799, 72, 253),
            wp("主线 · 前往天空城", 1005, 226, -3),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】你在旭升岛轻轨站的候车椅上醒来，远处是驶向天空城的轨道。"),
                            Te.s("我是联合研院的秘书贝贝，负责接引新到的考察员。"),
                            Te.s("按 M 打开世界地图，右键「天空城」的路径点，选择 Teleport 传送过去。"),
                            Te.s("传送完成后，去天空城的传送中枢找我。")),
                    List.of(
                            Te.s("看来你平安到了。欢迎来到天空城。"),
                            Te.s("这里就是联合研院的前哨，也是我们这次考察的起点。")),
                    "「出发」", "「交付」",
                    "目标：按 M 打开世界地图 → 右键「天空城」路径点 → 选择 Teleport。",
                    "天空城就交给你慢慢熟悉了。"));

    public static final Stage MAIN_02 = new Stage(
            "main_02_arrive_sky", "MAIN_02_ARRIVE_SKY",
            NpcIds.MAIN_BEIBEI, null, Kind.TALK,
            Te.s("在天空城寻找贝贝", CustomStyle.styleOfSky),
            Te.s("领取基础物资"),
            Te.s("天空城 · 贝贝"),
            Te.s("需完成 出发：旭升岛轻轨"),
            List.of(new Objective.Talk(NpcIds.MAIN_BEIBEI)),
            List.of(
                    new Reward(() -> new ItemStack(ModItems.SKY_SWORD.get())),
                    new Reward(() -> new ItemStack(ModItems.SKY_BOW.get())),
                    new Reward(() -> new ItemStack(ModItems.SKY_SCEPTRE.get())),
                    new Reward(() -> new ItemStack(ModItems.GRAVITY_BYPASS_DEVICE.get())),
                    new Reward(() -> new ItemStack(ModItems.BACK_SPAWN_TICKET.get())),
                    new Reward(() -> new ItemStack(ModItems.SKY_LINE.get())),
                    new Reward(() -> new ItemStack(Items.GOLDEN_CARROT, 32)),
                    new Reward(() -> new ItemStack(Items.BAKED_POTATO, 32)),
                    new Reward(() -> {
                        ItemStack elytra = new ItemStack(Items.ELYTRA);
                        elytra.enchant(Enchantments.UNBREAKING, 10);
                        return elytra;
                    })),
            wp("主线 · 天空城传送中枢", 1005, 226, -3),
            wp("主线 · 天空城传送中枢", 1005, 226, -3),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】维瑞阿契带着天空城，在诸大陆之间做没有尽头的巡游。"),
                            Te.s("【剧情占位】作为曾经的一员，你再次收到联合研院的委托，展开一场没有期限的考察。"),
                            Te.s("考察员唯一的任务：探索各地，协助居民处理本源灾害。"),
                            Te.s("这些是给你的基础物资：天空之锋、天空之隼、天空之杖，还有一些食物和鞘翅。"),
                            Te.s("可以先熟悉一下基础操作 Z 翻滚；N 打开技能界面"),
                            Te.s("打开物品栏可以领取每日补给。都收好之后，去和城主索尔谈谈。")),
                    List.of(),
                    "「收下物资」", "「交付」",
                    null,
                    "物资还够用吗？不够的话记得领每日补给。"));

    public static final Stage MAIN_03 = new Stage(
            "main_03_mayor_briefing", "MAIN_03_MAYOR_BRIEFING",
            NpcIds.MAIN_SOL, null, Kind.TALK,
            Te.s("城主的简报", CustomStyle.styleOfSky),
            Te.s("了解三种机制"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 初到天空城"),
            List.of(new Objective.Talk(NpcIds.MAIN_SOL)),
            List.of(
                    new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 100)),
                    new Reward(() -> new ItemStack(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.NETHERITE_BACKPACK.get(), 1)),
                    new Reward(() -> new ItemStack(ModItems.U_DISK.get(), 1)),
                    new Reward(() -> new ItemStack(Items.GOLDEN_CARROT, 16))),
            wp("主线 · 天空城城主", 956, 232, 19),
            wp("主线 · 天空城城主", 956, 232, 19),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】我是天空城城主索尔，负责协调联合研院在这里的事务。"),
                            Te.s("出发之前，先记住三件事。"),
                            Te.s("聚星：完成本源挑战与塔类挑战可以获得，在身份卡的本源商店兑换补给包。"),
                            Te.s("每日任务：右键身份卡打开任务列表，每天刷新，完成后有奖励。"),
                            Te.s("委托：找联合研院秘书贝尔接取击杀委托，积累委托债券。"),
                            Te.s("这里有些物资，带上它们去平原村找村长 PP，他会教你基础战斗。")),
                    List.of(),
                    "「记住了」", "「交付」",
                    null,
                    "有进展就回来找我。"));

    // =================================================================
    // 第二章 · 平原
    // =================================================================

    public static final Stage MAIN_04 = new Stage(
            "main_04_plain_chief", "MAIN_04_PLAIN_CHIEF",
            NpcIds.MAIN_PP, null, Kind.TALK,
            Te.s("平原村的请求", CustomStyle.styleOfPlain),
            Te.s("学习基础战斗"),
            Te.s("平原村 · PP"),
            Te.s("需完成 城主的简报"),
            List.of(new Objective.Talk(NpcIds.MAIN_PP)),
            List.of(new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 100))),
            wp("主线 · 平原村", 754, 84, 207),
            wp("主线 · 平原村", 754, 84, 207),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】平原村最近不太平，怪物一天比一天多。"),
                            Te.s("你是联合研院派来的考察员吧？先教你两手。"),
                            Te.s("左键普攻；Z 翻滚可以躲开攻击；N 打开技能界面，选好技能组后按 Q 释放技能。"),
                            Te.s("按 E 打开物品栏，按 M 打开世界地图，右键身份卡可以领取每日补给。"),
                            Te.s("村外有农田，村里有肉类、蛋奶、作物制品、水果制品的烹饪商人，出发前可以补给。"),
                            Te.s("先去练一次翻滚，再按 N 选好技能组，然后去村外拿平原僵尸练手。")),
                    List.of(),
                    "「我记住了」", "「交付」",
                    null,
                    "村外就交给年轻人了。"));

    // ---- 基础操作练习：先翻滚，再选技能组，两个独立任务 ----

    public static final Stage MAIN_04A = new Stage(
            "main_04a_roll", "MAIN_04A_ROLL",
            NpcIds.MAIN_PP, null, Kind.AUTO,
            Te.s("翻滚练习", CustomStyle.styleOfPlain),
            Te.s("使用一次翻滚"),
            Te.s("平原村 · PP"),
            Te.s("需完成 平原村的请求"),
            List.of(new Objective.Roll(1)),
            List.of(new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 50))),
            wp("主线 · 平原村", 754, 84, 207),
            wp("主线 · 平原村", 754, 84, 207),
            new Dialogue(
                    List.of(
                            Te.s("先练练走位。按 Z 翻滚，可以快速位移；体力充足时还能躲开怪物的攻击。"),
                            Te.s("随便找块空地滚一次就会自动记录，不用回来交付。")),
                    List.of(
                            Te.s("翻滚的手感找到了吧？"),
                            Te.s("接下来按 N 打开技能界面，挑一个技能组。")),
                    "「试试」", "「交付」",
                    "目标：按 Z 使用一次翻滚（完成会自动记录）。",
                    "翻滚要多练，之后打怪时能救命。"));

    public static final Stage MAIN_04B = new Stage(
            "main_04b_skill_group", "MAIN_04B_SKILL_GROUP",
            NpcIds.MAIN_PP, null, Kind.AUTO,
            Te.s("选择技能组", CustomStyle.styleOfPlain),
            Te.s("选择一种技能组"),
            Te.s("平原村 · PP"),
            Te.s("需完成 翻滚练习"),
            List.of(new Objective.SelectSkillGroup(1)),
            List.of(new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 50))),
            wp("主线 · 平原村", 754, 84, 207),
            wp("主线 · 平原村", 754, 84, 207),
            new Dialogue(
                    List.of(
                            Te.s("按 N 打开技能界面，在战士、弓箭手、法师三个技能组里选一个。"),
                            Te.s("选好之后按 Q 就能释放对应技能，选好就会自动记录。")),
                    List.of(
                            Te.s("技能组选好了，接下来该动真格的了。"),
                            Te.s("村外有不少平原僵尸，先拿它们练手。")),
                    "「我看看」", "「交付」",
                    "目标：按 N 打开技能界面，选择一个技能组（完成会自动记录）。",
                    "技能组选定后，战斗会顺手很多。"));

    /** 技能组选择后的精通教学：自动开始，保存一次有点数变化的精通配置即完成。 */
    public static final Stage MAIN_04C = new Stage(
            "main_04c_mastery", "MAIN_04C_MASTERY",
            NpcIds.MAIN_PP, null, Kind.AUTO,
            Te.s("精通配置", CustomStyle.styleOfWorld),
            Te.s("配置 1 点精通"),
            Te.s("物品栏 · 精通"),
            Te.s("需完成 选择技能组"),
            List.of(new Objective.Mastery(1)),
            List.of(new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 50))),
            wp("主线 · 平原村", 754, 84, 207),
            wp("主线 · 平原村", 754, 84, 207),
            new Dialogue(
                    List.of(
                            Te.s("技能组选好了。刚才那一级给了你 1 点精通点。"),
                            Te.s("按 E 打开物品栏，点击右侧「身份卡」面板里的「精通」。"),
                            Te.s("在精通页面里分配 1 点，然后点击保存；保存后会自动完成。")),
                    List.of(
                            Te.s("精通配置好了。"),
                            Te.s("接下来去村外清理平原僵尸吧。")),
                    "「明白了」", "「交付」",
                    "按 E 打开物品栏 → 身份卡 → 精通，分配 1 点并保存。",
                    "精通点之后每升一级都会增加。"));

    public static final Stage MAIN_05 = new Stage(
            "main_05_plain_purge", "MAIN_05_PLAIN_PURGE",
            NpcIds.MAIN_PP, null, Kind.OBJECTIVE,
            Te.s("平原清剿", CustomStyle.styleOfPlain),
            Te.s("清剿平原僵尸"),
            Te.s("平原村 · PP"),
            Te.s("需完成 选择技能组"),
            List.of(
                    new Objective.Kill("平原僵尸", 10),
                    new Objective.Kill("森林僵尸", 20),
                    new Objective.Level(5)),
            List.of(
                    new Reward(() -> new ItemStack(ModItems.PLAIN_SOUL.get(), 10)),
                    new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 300)),
                    new Reward(() -> new ItemStack(ModItems.PLAIN_BOOTS.get(), 1)),
                    new Reward(() -> new ItemStack(ModItems.FOREST_CHEST.get(), 1)),
                    new Reward(() -> new ItemStack(ModItems.FOREST_LEGGINGS.get(), 1)),
                    new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 10))),
            wp("主线 · 平原村", 754, 84, 207),
            wp("主线 · 平原村", 754, 84, 207),
            new Dialogue(
                    List.of(
                            Te.s("先清理村外 10 只平原僵尸；它们掉落的平原灵魂可以换购武器。"),
                            Te.s("村里的武器商人那里能买到平原短匕、平原长弓或生机权杖，顺手把 U盾也买了。"),
                            Te.s("练到 5 级之后，去西边的森林清理 20 只森林僵尸，它们的灵魂能换森林武器。"),
                            Te.s("装备强化在锻造台进行，装备跟上了会轻松很多。")),
                    List.of(
                            Te.s("干得不错，平原暂时安稳了。"),
                            Te.s("雨林村的村长 Chen 前天来求助，你去看看。")),
                    "「开工」", "「交付」",
                    null,
                    "你已经是能独当一面的考察员了。"));

    // =================================================================
    // 第三章 · 雨林
    // =================================================================

    public static final Stage MAIN_06 = new Stage(
            "main_06_forest_chief", "MAIN_06_FOREST_CHIEF",
            NpcIds.MAIN_CHEN, null, Kind.TALK,
            Te.s("雨林村的委托", CustomStyle.styleOfForest),
            Te.s("了解雨林与支线"),
            Te.s("雨林村 · Chen"),
            Te.s("需完成 平原清剿"),
            List.of(new Objective.Talk(NpcIds.MAIN_CHEN)),
            List.of(new Reward(() -> new ItemStack(ModItems.FOREST_SOUL.get(), 10))),
            wp("主线 · 雨林村", 1102, 80, 43),
            wp("主线 · 雨林村", 1102, 80, 43),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】雨林村被藤蔓和潮气包围，最近连矿洞都不太安分。"),
                            Te.s("考察员，我得先跟你说说这里的情况。"),
                            Te.s("村外有雨林蜘蛛、矿洞骷髅，玛瑙矿区深处还有紫晶巨蟹；它们的材料都有用处。"),
                            Te.s("能逛的地方不少：玉林小镇、玉林饭店、旧主城、旧铁匠铺、旧商品兑换都在附近，水果制品和菌菇类商人也在。"),
                            Te.s("支线方面：玉林饭店最近缺人手；玛瑙与紫水晶巨蟹也可以去会一会。"),
                            Te.s("主线这边，普莱尼在村外作乱（位置待调整），我们需要你击败它。")),
                    List.of(),
                    "「我明白了」", "「交付」",
                    null,
                    "雨林村的路你应该比我熟了吧。"));

    public static final Stage MAIN_07 = new Stage(
            "main_07_plaini", "MAIN_07_PLAINI",
            NpcIds.MAIN_CHEN, null, Kind.OBJECTIVE,
            Te.s("击败普莱尼", CustomStyle.styleOfPlain),
            Te.s("击败普莱尼"),
            Te.s("雨林村 · Chen"),
            Te.s("需完成 雨林村的委托"),
            List.of(new Objective.Instance("普莱尼", "普莱尼", 1)),
            List.of(
                    new Reward(() -> new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 4)),
                    new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 80)),
                    new Reward(() -> new ItemStack(ModItems.FORGING_STONE_1.get(), 3))),
            wp("主线 · 雨林村", 1102, 80, 43),
            new QuestWaypoint("主线 · 普莱尼", PlainInstance.POS),
            new Dialogue(
                    List.of(
                            Te.s("普莱尼在平原村南边，它会召唤「普莱尼信徒」协助作战。"),
                            Te.s("它会发射法球，请注意走位。"),
                            Te.s("击败它之后回来找我。")),
                    List.of(
                            Te.s("普莱尼被击败了，雨林村终于能喘口气。"),
                            Te.s("天空城的索尔在找你，说炼金术士那边需要人手。")),
                    "「交给我」", "「交付」",
                    null,
                    "普莱尼不会再回来了吧？"));

    public static final Stage MAIN_07A = new Stage(
            "main_07a_vp_supply", "MAIN_07A_VP_SUPPLY",
            NpcIds.MAIN_SOL, null, Kind.AUTO,
            Te.s("聚星补给", CustomStyle.styleOfGold),
            Te.s("使用任意补给包"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 击败普莱尼"),
            List.of(new Objective.SupplyPack(1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 40))),
            wp("主线 · 天空城城主", 956, 232, 19),
            wp("主线 · 天空城城主", 956, 232, 19),
            new Dialogue(
                    List.of(
                            Te.s("打开物品栏，在下方找到「VP商店」。"),
                            Te.s("使用聚星购买任意补给包，然后使用它。"),
                            Te.s("使用完成后，我会把 40 聚星补给给你。")),
                    List.of(),
                    "「我去准备」", "「完成」",
                    "打开物品栏 - VP商店，用聚星购买任意补给包并使用。",
                    "补给包已经用完了？"));

    // =================================================================
    // 第四章 · 天空城：炼金术士的请求
    // =================================================================

    public static final Stage MAIN_08 = new Stage(
            "main_08_alchemist_support", "MAIN_08_ALCHEMIST_SUPPORT",
            NpcIds.MAIN_SOL, null, Kind.OBJECTIVE,
            Te.s("炼金术士的请求", CustomStyle.styleOfSky),
            Te.s("收集炼金素材"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 聚星补给"),
            List.of(
                    new Objective.Kill("焰芒虫", 10),
                    new Objective.Kill("唤魔者", 10)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 天空城城主", 956, 232, 19),
            wp("主线 · 唤魔湖", 1484, 63, -240),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】炼金术士们正在准备一批试剂，缺两种关键素材。"),
                            Te.s("一种是熔岩地带的产物：去熔岩区域清理焰芒虫，带回它们的掉落。"),
                            Te.s("另一种与唤魔有关：唤魔湖一带的唤魔者身上有我们需要的东西。"),
                            Te.s("两种素材各 10 份（建议值），收集齐了回来找我。")),
                    List.of(
                            Te.s("素材够了，炼金术士那边会记你一功。"),
                            Te.s("海岸村的村长 Ushio 传来消息，海岸一带需要支援。")),
                    "「我去收集」", "「交付」",
                    null,
                    "炼金术士们最近很忙。"));

    // =================================================================
    // 第四章补充 · 海岸前的试炼
    // =================================================================

    public static final Stage MAIN_08A = new Stage(
            "main_08a_purple_crab", "MAIN_08A_PURPLE_CRAB",
            NpcIds.MAIN_SOL, null, Kind.OBJECTIVE,
            Te.s("挑战紫晶巨蟹", CustomStyle.styleOfPurpleIron),
            Te.s("挑战紫晶巨蟹"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 炼金术士的请求"),
            List.of(new Objective.Instance("紫水晶巨蟹", "紫晶巨蟹", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 天空城城主", 956, 232, 19),
            new QuestWaypoint("主线 · 紫晶巨蟹", PurpleIronInstance.POS),
            new Dialogue(
                    List.of(
                            Te.s("雨林矿洞深处还有一只紫晶巨蟹在盘踞。"),
                            Te.s("它比普通巨蟹更坚硬，带上足够的补给再去。"),
                            Te.s("击败它之后回来找我。")),
                    List.of(
                            Te.s("紫晶巨蟹已经被击败了。"),
                            Te.s("接下来去挑战本源回廊-I，证明你的实力。")),
                    "「我去挑战」", "「交付」",
                    null,
                    "紫晶巨蟹的甲壳，连矿镐都要退让三分。"));

    public static final Stage MAIN_08B = new Stage(
            "main_08b_origin_corridor", "MAIN_08B_ORIGIN_CORRIDOR",
            NpcIds.MAIN_SOL, null, Kind.OBJECTIVE,
            Te.s("挑战本源回廊-I", CustomStyle.styleOfWorld),
            Te.s("挑战本源回廊-I"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 紫晶巨蟹"),
            List.of(new Objective.Tower(0, "本源回廊-I", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 天空城城主", 956, 232, 19),
            wp("主线 · 本源回廊-I", 956, -62.5, -69.5),
            new Dialogue(
                    List.of(
                            Te.s("本源回廊-I 是天空城附近的试炼。"),
                            Te.s("进入回廊并完成挑战，哪怕只拿到一星也算通过。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("本源回廊-I 的记录已经更新。"),
                            Te.s("海岸村的 Ushio 传来消息，海岸一带需要支援。")),
                    "「我去挑战」", "「交付」",
                    null,
                    "本源回廊会记住每一位挑战者。"));

    // =================================================================
    // 第五章 · 海岸
    // =================================================================

    public static final Stage MAIN_09 = new Stage(
            "main_09_coast_chief", "MAIN_09_COAST_CHIEF",
            NpcIds.MAIN_USHIO, null, Kind.OBJECTIVE,
            Te.s("海岸村的潮声", CustomStyle.styleOfWater),
            Te.s("清理海岸怪物"),
            Te.s("海岸村 · Ushio"),
            Te.s("需完成 本源回廊-I"),
            List.of(
                    new Objective.Kill("脆弱的岩灵", 10),
                    new Objective.Kill("神殿守卫", 10)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 海岸村", 881, 62, -423),
            wp("主线 · 海岸村", 881, 62, -423),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】海岸村外的海底神殿近来躁动，连海面都不平静了。"),
                            Te.s("主线：清理海岸一带的脆弱灵魂（脆弱的岩灵）与神殿守卫；深处的澈源之灵也会出现。"),
                            Te.s("支线：附近的史莱姆最近很活跃，可以顺路处理。"),
                            Te.s("两种怪物各 10 只（建议值），完成后回来找我。")),
                    List.of(
                            Te.s("海面又平静了，谢谢你。"),
                            Te.s("薰曦村有人想见你，一个叫「铁铁铁铁铁头」的家伙。")),
                    "「我去清理」", "「交付」",
                    null,
                    "海风很适合养老，对吧。"));

    // =================================================================
    // 第六章 · 薰曦村
    // =================================================================

    public static final Stage MAIN_10 = new Stage(
            "main_10_xunxi_visit", "MAIN_10_XUNXI_VISIT",
            NpcIds.MAIN_TIETOU, null, Kind.TALK,
            Te.s("薰曦村的访客", CustomStyle.styleOfJacaranda),
            Te.s("拜访铁头"),
            Te.s("薰曦村 · 铁头"),
            Te.s("需完成 海岸村的潮声"),
            List.of(new Objective.Talk(NpcIds.MAIN_TIETOU)),
            List.of(new Reward(() -> new ItemStack(ModItems.DIAMOND_HAMMER.get(), 2))),
            wp("主线 · 薰曦村", 1093, 80, -1326),
            wp("主线 · 薰曦村", 1093, 80, -1326),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】薰曦村是玩家们自己建起来的社区，房子一栋比一栋精致。"),
                            Te.s("我是铁铁铁铁铁头，这里的事情我说了算。"),
                            Te.s("你能特地跑一趟，说明联合研院还惦记着我们。这个给你。"),
                            Te.s("（获得钻石锤 ×2）"),
                            Te.s("村子随便逛，有什么需要就来找我。")),
                    List.of(),
                    "「收下」", "「交付」",
                    null,
                    "常来啊。"));

    // =================================================================
    // 第七章 · 下界I
    // =================================================================

    public static final Stage MAIN_11 = new Stage(
            "main_11_nether_i", "MAIN_11_NETHER_I",
            NpcIds.MAIN_SINGID, null, Kind.OBJECTIVE,
            Te.s("下界I", CustomStyle.styleOfMana),
            Te.s("完成下界I"),
            Te.s("天空城 · 辛Gi德"),
            Te.s("需完成 薰曦村的访客"),
            List.of(new Objective.Instance("燃魂", "下界I（燃魂）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 天空城炼金区", 967, 230, 5),
            wp("主线 · 下界I", 529, 64, -540, Level.NETHER),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】我是炼金术士辛Gi德，负责下界方向的试剂研究。"),
                            Te.s("下界I 的入口已经稳定，但里面的「燃魂」还在活动。"),
                            Te.s("去下界击败燃魂，带一枚燃魂核心回来（击败即完成）。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("材料收到。下界II 的通道还需要时间准备。"),
                            Te.s("北洋村的夫夫在等你，那边的寒气越来越重了。")),
                    "「我去一趟」", "「交付」",
                    null,
                    "下界的研究不能急。"));

    // =================================================================
    // 第八章 · 北洋
    // =================================================================

    public static final Stage MAIN_12 = new Stage(
            "main_12_beifu_chief", "MAIN_12_BEIFU_CHIEF",
            NpcIds.MAIN_FUFU, null, Kind.OBJECTIVE,
            Te.s("北洋村的严寒", CustomStyle.styleOfSnow),
            Te.s("清理北洋怪物"),
            Te.s("北洋村 · 夫夫"),
            Te.s("需完成 下界I"),
            List.of(
                    new Objective.Kill("冰川流浪者", 10),
                    new Objective.Instance("冰霜骑士", "冰霜骑士", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 北洋村", 1325, 63, -1655),
            wp("主线 · 北洋村", 1325, 63, -1655),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】北洋村被冰雪围住，风一停，怪物的脚步声就格外清楚。"),
                            Te.s("主线：清理村外的冰川流浪者 10 只，以及冰霜骑士驻地里的冰霜骑士。"),
                            Te.s("支线：北境晶钻矿区就在更北边，晶钻可以顺路开采。"),
                            Te.s("极寒前哨所是这里的前哨，补给不够可以去看看。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("风雪小了些。"),
                            Te.s("风之高地的索索在等你。")),
                    "「我去清理」", "「交付」",
                    null,
                    "北边的风，听久了也就习惯了。"));

    // =================================================================
    // 第九章 · 风之高地
    // =================================================================

    public static final Stage MAIN_13 = new Stage(
            "main_13_wind_highland", "MAIN_13_WIND_HIGHLAND",
            NpcIds.MAIN_SOSO, null, Kind.OBJECTIVE,
            Te.s("风之高地", CustomStyle.styleOfWind),
            Te.s("击败唤风者"),
            Te.s("风之高地 · 索索"),
            Te.s("需完成 北洋村的严寒"),
            List.of(
                    new Objective.Kill("怀德风骨", 10),
                    new Objective.Kill("唤风者仆从", 10),
                    new Objective.Instance("唤风者", "唤风者", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 风之高地", 1902, 162, -1657),
            wp("主线 · 风之高地", 1902, 162, -1657),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】风之高地，风从来没有停过。"),
                            Te.s("我是索索，负责这一带的观测。"),
                            Te.s("主线：击败游荡的怀德风骨与唤风者仆从各 10 只，最后去会一会唤风者。"),
                            Te.s("这里只有一件事要注意：风会推着你走，别被吹下高地。")),
                    List.of(
                            Te.s("风暂时平了。"),
                            Te.s("贝贝让你回天空城，暗黑城堡需要一支小队。")),
                    "「交给我」", "「交付」",
                    null,
                    "高地上的风，永远会再起来。"));

    // =================================================================
    // 第十章 · 暗黑城堡
    // =================================================================

    public static final Stage MAIN_14 = new Stage(
            "main_14_dark_castle", "MAIN_14_DARK_CASTLE",
            NpcIds.MAIN_BEIBEI, null, Kind.OBJECTIVE,
            Te.s("暗黑城堡", CustomStyle.styleOfCastle),
            Te.s("通关暗黑城堡"),
            Te.s("天空城 · 贝贝"),
            Te.s("需完成 风之高地"),
            List.of(new Objective.Instance("暗黑城堡", "暗黑城堡", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.CASTLE_ATTACK_CHEST.get()))),
            wp("主线 · 天空城传送中枢", 1005, 226, -3),
            wp("主线 · 暗黑城堡", 2417, 152, -1372),
            new Dialogue(
                    List.of(
                            Te.s("暗黑城堡是一处危险的暗黑魔法遗址，这次只需要通关一次。"),
                            Te.s("城堡里有烽火台哨卫、古树魔能研究者、熔岩湖溢出物，注意它们的配合。"),
                            Te.s("通关后可以自选一件绑定防具；当前版本先发放「暗黑城堡攻击胸甲」，自选界面后续补上。"),
                            Te.s("完成了回来找我。")),
                    List.of(
                            Te.s("城堡的封印暂时稳住了。防具挑好了吗？"),
                            Te.s("火山村那边，炽魂和炽鬼又开始活动了。")),
                    "「出发」", "「交付」",
                    null,
                    "暗黑城堡的报告我已经交上去了。"));

    // =================================================================
    // 第十一章 · 火山村
    // =================================================================

    public static final Stage MAIN_15 = new Stage(
            "main_15_volcano", "MAIN_15_VOLCANO",
            NpcIds.MAIN_BEIBEI, null, Kind.OBJECTIVE,
            Te.s("火山村", CustomStyle.styleOfVolcano),
            Te.s("清理炽魂炽鬼"),
            Te.s("天空城 · 贝贝"),
            Te.s("需完成 暗黑城堡"),
            List.of(
                    new Objective.Kill("炽魂", 10),
                    new Objective.Kill("炽鬼", 10)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 天空城传送中枢", 1005, 226, -3),
            wp("主线 · 火山村", 2573, 120, -492),
            new Dialogue(
                    List.of(
                            Te.s("火山村的炽魂与炽鬼在村外游荡，各清理 10 只。"),
                            Te.s("火山区域温度高、落石多，补给要带够。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("火山村安静了。"),
                            Te.s("去东洋塔看看，那里每天都有挑战。")),
                    "「我去清理」", "「交付」",
                    null,
                    "火山灰落进眼睛里可不好受。"));

    // =================================================================
    // 第十二章 · 东洋塔
    // =================================================================

    public static final Stage MAIN_16 = new Stage(
            "main_16_east_tower", "MAIN_16_EAST_TOWER",
            NpcIds.MAIN_BEIBEI, null, Kind.OBJECTIVE,
            Te.s("东洋塔", CustomStyle.styleOfHusk),
            Te.s("完成东洋塔每日"),
            Te.s("天空城 · 贝贝"),
            Te.s("需完成 火山村"),
            List.of(new Objective.Daily("东洋塔")),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 天空城传送中枢", 1005, 226, -3),
            wp("主线 · 东洋塔", 2335, 148, 17),
            new Dialogue(
                    List.of(
                            Te.s("东洋塔是每日挑战，完成一次即可。"),
                            Te.s("塔里每一层都是硬仗，带好补给再进。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("东洋塔的记录已经更新。"),
                            Te.s("去旭升岛找 PM 托里，他管着那里的功能区。")),
                    "「我去挑战」", "「交付」",
                    null,
                    "东洋塔的层数，你还能再往上走。"));

    // =================================================================
    // 第十三章 · 旭升岛功能区
    // =================================================================

    public static final Stage MAIN_17 = new Stage(
            "main_17_function_island", "MAIN_17_FUNCTION_ISLAND",
            NpcIds.MAIN_TOLI, null, Kind.TALK,
            Te.s("旭升岛功能区", CustomStyle.styleOfSunIsland),
            Te.s("拜访 PM 托里"),
            Te.s("旭升岛 · 托里"),
            Te.s("需完成 东洋塔"),
            List.of(new Objective.Talk(NpcIds.MAIN_TOLI)),
            List.of(new Reward(() -> new ItemStack(ModItems.COPPER_COIN.get(), 100))),
            wp("主线 · 旭升岛", 1807, 74, 341),
            wp("主线 · 旭升岛", 1807, 74, 341),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】我是 PM 托里，旭升岛功能区的负责人。"),
                            Te.s("这里能兑换秘藏、交易物品，南部沙滩还有活动。"),
                            Te.s("超凡秘藏的入口就在岛上，秘藏商人也都在附近。"),
                            Te.s("之后雷光岛那边需要人手，去清理雷光守卫。")),
                    List.of(),
                    "「明白了」", "「交付」",
                    null,
                    "岛上的事情随时找我。"));

    // =================================================================
    // 第十四章 · 雷光
    // =================================================================

    public static final Stage MAIN_18 = new Stage(
            "main_18_leiguang", "MAIN_18_LEIGUANG",
            NpcIds.MAIN_TOLI, null, Kind.OBJECTIVE,
            Te.s("雷光", CustomStyle.styleOfLightning),
            Te.s("清理雷光守卫"),
            Te.s("旭升岛 · 托里"),
            Te.s("需完成 旭升岛功能区"),
            List.of(new Objective.Kill("雷光灯塔驻卫", 10)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 20))),
            wp("主线 · 旭升岛", 1807, 74, 341),
            wp("主线 · 雷光岛", 1743, 68, 1285),
            new Dialogue(
                    List.of(
                            Te.s("雷光岛上游荡着雷光守卫（雷光灯塔驻卫），清理 10 只。"),
                            Te.s("岛上雷声不断，注意躲避落雷。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("雷光岛稳定了。"),
                            Te.s("樱岛的樱在等你。")),
                    "「我去清理」", "「交付」",
                    null,
                    "雷光岛的天空，从来没有安静过。"));

    // =================================================================
    // 第十五章 · 樱岛（挑战主线起点）
    // =================================================================

    public static final Stage MAIN_19 = new Stage(
            "main_19_sakura_island", "MAIN_19_SAKURA_ISLAND",
            NpcIds.MAIN_YING, null, Kind.OBJECTIVE,
            Te.s("樱岛", CustomStyle.styleOfSakura),
            Te.s("清理樱岛"),
            Te.s("樱岛 · 樱"),
            Te.s("需完成 雷光"),
            List.of(
                    new Objective.Kill("樱灵", 10),
                    new Objective.Kill("地蕴蓝灵", 10),
                    new Objective.Kill("腥月血灵", 10),
                    new Objective.Instance("突见忍", "突见忍", 1),
                    new Objective.Instance("魔王", "魔王", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 绯樱村", 2388, 182, 1752),
            wp("主线 · 绯樱村", 2388, 182, 1752),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】樱岛的樱花一年四季都不落。"),
                            Te.s("主线：清理岛上的樱灵、蓝灵（地蕴蓝灵）、血灵（腥月血灵）各 10 只；突见忍与魔王在岛深处。"),
                            Te.s("粉钻矿区在岛北，需要的话可以顺路开采。"),
                            Te.s("从这里开始，后面的主线都是「挑战主线」，难度会明显上升。")),
                    List.of(
                            Te.s("樱岛的花又开了。"),
                            Te.s("托里在找你，说尘月那边有动静。")),
                    "「我去看看」", "「交付」",
                    null,
                    "樱岛的风景，值得多留一会儿。"));

    // =================================================================
    // 第十六章 · 尘月
    // =================================================================

    public static final Stage MAIN_20 = new Stage(
            "main_20_moon_palace", "MAIN_20_MOON_PALACE",
            NpcIds.MAIN_TOLI, null, Kind.OBJECTIVE,
            Te.s("尘月", CustomStyle.styleOfMoon),
            Te.s("通关尘月副本"),
            Te.s("旭升岛 · 托里"),
            Te.s("需完成 樱岛"),
            List.of(new Objective.Instance("阿尔忒弥斯", "尘月（阿尔忒弥斯）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 旭升岛", 1807, 74, 341),
            wp("主线 · 尘月宫", 1761, 130, -463),
            new Dialogue(
                    List.of(
                            Te.s("尘月宫是一座副本，通关一次即可。"),
                            Te.s("宫里的月引会把人推下高台，注意站位。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("尘月的月亮圆了。"),
                            Te.s("索尔在找你，望山那边需要人。")),
                    "「出发」", "「交付」",
                    null,
                    "尘月的月光，照得人心里发静。"));

    // =================================================================
    // 第十七章 · 望山
    // =================================================================

    public static final Stage MAIN_21 = new Stage(
            "main_21_wangshan", "MAIN_21_WANGSHAN",
            NpcIds.MAIN_SOL, null, Kind.OBJECTIVE,
            Te.s("望山", CustomStyle.styleOfMoontain),
            Te.s("清理望山怪物"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 尘月"),
            List.of(
                    new Objective.Kill("望山翎凤", 10),
                    new Objective.Kill("望山孤魂", 10),
                    new Objective.Kill("望山魂驹", 10),
                    new Objective.Kill("望山阁灵", 10),
                    new Objective.Kill("望山矿工", 10),
                    new Objective.Instance("望山武士", "望山武士", 1),
                    new Objective.Instance("望山骑士", "望山骑士", 1),
                    new Objective.Instance("望山武魂", "望山武魂", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 天空城城主", 956, 232, 19),
            wp("主线 · 望山据点", 1921, 151, -936),
            new Dialogue(
                    List.of(
                            Te.s("望山的怪物成群结队：望山翎凤、望山孤魂、望山魂驹、望山阁灵、望山矿工各 10 只。"),
                            Te.s("望山武士、望山骑士、望山武魂在望山阁高层，各击败一次。"),
                            Te.s("望山据点有强化与分解设施，路上可以补给。")),
                    List.of(
                            Te.s("望山暂时安静了。"),
                            Te.s("鹰眼工厂有新的委托，去找樱。")),
                    "「我去清理」", "「交付」",
                    null,
                    "望山阁的风景，是拿命换来的。"));

    // =================================================================
    // 第十八章 · 鹰眼工厂
    // =================================================================

    public static final Stage MAIN_22 = new Stage(
            "main_22_eagle_factory", "MAIN_22_EAGLE_FACTORY",
            NpcIds.MAIN_YING, null, Kind.OBJECTIVE,
            Te.s("鹰眼工厂", CustomStyle.styleOfHarbinger),
            Te.s("通关鹰眼工厂"),
            Te.s("樱岛 · 樱"),
            Te.s("需完成 望山"),
            List.of(new Objective.Instance("鹰眼工厂", "鹰眼工厂", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 绯樱村", 2388, 182, 1752),
            wp("主线 · 鹰眼工厂", 1934, -25, 1798),
            new Dialogue(
                    List.of(
                            Te.s("鹰眼工厂是团队副本，通关一次即可。"),
                            Te.s("工厂里的实验体配合紧密，最好带上队友。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("工厂的实验体被清理了。"),
                            Te.s("菌菇聚落那边出现了新的东西，贝贝在等你。")),
                    "「出发」", "「交付」",
                    null,
                    "工厂的灯还亮着。"));

    // =================================================================
    // 第十九章 · 菌菇
    // =================================================================

    public static final Stage MAIN_23 = new Stage(
            "main_23_mushroom", "MAIN_23_MUSHROOM",
            NpcIds.MAIN_BEIBEI, null, Kind.OBJECTIVE,
            Te.s("菌菇", CustomStyle.MUSHROOM_STYLE),
            Te.s("清理菌菇灵"),
            Te.s("天空城 · 贝贝"),
            Te.s("需完成 鹰眼工厂"),
            List.of(
                    new Objective.Kill("菌菇灵", 10),
                    new Objective.Instance("菌菇灵统领", "菌菇灵统领", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 天空城传送中枢", 1005, 226, -3),
            wp("主线 · 菌菇聚落", 2006, 130, -1785),
            new Dialogue(
                    List.of(
                            Te.s("菌菇聚落的菌菇灵在扩散，清理 10 只。"),
                            Te.s("深处的菌菇灵统领也需要处理。"),
                            Te.s("聚落里地形复杂，小心孢子。")),
                    List.of(
                            Te.s("菌菇聚落恢复了平静。"),
                            Te.s("索尔让你去末地。")),
                    "「我去清理」", "「交付」",
                    null,
                    "孢子味儿，闻久了也就习惯了。"));

    // =================================================================
    // 第二十章 · 末地
    // =================================================================

    public static final Stage MAIN_24 = new Stage(
            "main_24_end", "MAIN_24_END",
            NpcIds.MAIN_SOL, null, Kind.OBJECTIVE,
            Te.s("末地", CustomStyle.styleOfEnd),
            Te.s("探索末地"),
            Te.s("天空城 · 索尔"),
            Te.s("需完成 菌菇"),
            List.of(new Objective.Instance("影珀守卫", "末地（影珀守卫）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 天空城城主", 956, 232, 19),
            wp("主线 · 末地", 1075, 39, -704, Level.END),
            new Dialogue(
                    List.of(
                            Te.s("末地出现了新的威胁，去末地击败「影珀守卫」。"),
                            Te.s("具体位置已在路径点中标记（仅在末地显示）。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("末地的波动平息了。"),
                            Te.s("禁忌之地的慎想见你。")),
                    "「我去看看」", "「交付」",
                    null,
                    "末地的静默，比任何声音都可怕。"));

    // =================================================================
    // 第二十一章 · 炼魔塔
    // =================================================================

    public static final Stage MAIN_25 = new Stage(
            "main_25_mana_tower", "MAIN_25_MANA_TOWER",
            NpcIds.MAIN_SHEN, null, Kind.OBJECTIVE,
            Te.s("炼魔塔", CustomStyle.MANA_TOWER_STYLE),
            Te.s("完成炼魔塔每日"),
            Te.s("绯樱村 · 慎"),
            Te.s("需完成 末地"),
            List.of(new Objective.Daily("炼魔塔")),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 绯樱村", 2368, 172, 1743),
            wp("主线 · 炼魔平原", 1364, 79, 44),
            new Dialogue(
                    List.of(
                            Te.s("【剧情占位】我是慎，负责禁忌之地的观测。"),
                            Te.s("炼魔塔是每日挑战，完成一次即可。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("塔的记录更新了。"),
                            Te.s("远古之城需要你。")),
                    "「我去挑战」", "「交付」",
                    null,
                    "禁忌之地的秘密，比你想象的多。"));

    // =================================================================
    // 第二十二章 · 远古之城
    // =================================================================

    public static final Stage MAIN_26 = new Stage(
            "main_26_ancient_city", "MAIN_26_ANCIENT_CITY",
            NpcIds.MAIN_SHEN, null, Kind.OBJECTIVE,
            Te.s("远古之城", CustomStyle.styleOfWarden),
            Te.s("通关远古之城"),
            Te.s("绯樱村 · 慎"),
            Te.s("需完成 炼魔塔"),
            List.of(new Objective.Instance("坚守者", "远古之城（坚守者）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 绯樱村", 2368, 172, 1743),
            wp("主线 · 远古之城", 2352, -34, -704),
            new Dialogue(
                    List.of(
                            Te.s("远古之城的石墙里藏着「坚守者」，击败它。"),
                            Te.s("古城地形复杂，注意幽匿尖啸体。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("远古之城的封印又稳了一层。"),
                            Te.s("熔岩地堡出事了。")),
                    "「出发」", "「交付」",
                    null,
                    "古城的石墙，记得每一个来访者。"));

    // =================================================================
    // 第二十三章 · 熔岩地堡
    // =================================================================

    public static final Stage MAIN_27 = new Stage(
            "main_27_lava_bunker", "MAIN_27_LAVA_BUNKER",
            NpcIds.MAIN_SHEN, null, Kind.OBJECTIVE,
            Te.s("熔岩地堡", CustomStyle.BUNKER_STYLE),
            Te.s("通关熔岩地堡"),
            Te.s("绯樱村 · 慎"),
            Te.s("需完成 远古之城"),
            List.of(new Objective.Instance("熔蚀", "熔岩地堡（熔蚀）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 绯樱村", 2368, 172, 1743),
            wp("主线 · 熔岩地堡", 3841, -17, 1902),
            new Dialogue(
                    List.of(
                            Te.s("熔岩地堡里的「熔蚀」暴动，击败它。"),
                            Te.s("地堡温度极高，注意装备与补给。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("地堡稳定了。"),
                            Te.s("圣光岛那边需要支援。")),
                    "「我去清理」", "「交付」",
                    null,
                    "地堡的岩浆，还在翻滚。"));

    // =================================================================
    // 第二十四章 · 圣光岛
    // =================================================================

    public static final Stage MAIN_28 = new Stage(
            "main_28_holy_island", "MAIN_28_HOLY_ISLAND",
            NpcIds.MAIN_TOLI, null, Kind.OBJECTIVE,
            Te.s("圣光岛", CustomStyle.DIVINE_STYLE),
            Te.s("完成圣光岛挑战"),
            Te.s("旭升岛 · 托里"),
            Te.s("需完成 熔岩地堡"),
            List.of(new Objective.Instance("圣光班尼", "圣光岛（圣光班尼）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 旭升岛", 1807, 74, 341),
            wp("主线 · 圣光岛码头", 1899.5, 70, 295.5),
            new Dialogue(
                    List.of(
                            Te.s("圣光岛的圣光会灼伤飞行者，尽量不要在高空久留。"),
                            Te.s("主线：在圣光岛击败「圣光班尼」。"),
                            Te.s("完成后回来找我。")),
                    List.of(
                            Te.s("圣光岛暂时平息了。"),
                            Te.s("极冬之境是最后一站，去找夫夫。")),
                    "「出发」", "「交付」",
                    null,
                    "圣光下的影子，比黑暗更长。"));

    // =================================================================
    // 第二十五章 · 极冬之境
    // =================================================================

    public static final Stage MAIN_29 = new Stage(
            "main_29_polar", "MAIN_29_POLAR",
            NpcIds.MAIN_FUFU, null, Kind.OBJECTIVE,
            Te.s("极冬之境", CustomStyle.styleOfIce),
            Te.s("完成极冬之境"),
            Te.s("北洋村 · 夫夫"),
            Te.s("需完成 圣光岛"),
            List.of(new Objective.Instance("霜冻巨兽", "极冬之境（霜冻巨兽）", 1)),
            List.of(new Reward(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 30))),
            wp("主线 · 北洋村", 1325, 63, -1655),
            wp("主线 · 极冬之境", 2076, 222, 1973),
            new Dialogue(
                    List.of(
                            Te.s("极冬之境是最后一段主线。"),
                            Te.s("这里比北洋村更冷，做好准备再进。"),
                            Te.s("目标：击败「霜冻巨兽」。")),
                    List.of(
                            Te.s("主线到此告一段落。"),
                            Te.s("后续主线与支线，待联合研院的新委托。")),
                    "「出发」", "「交付」",
                    null,
                    "极冬的风雪，掩盖了很多东西。"));

    public static final List<Stage> STAGES = List.of(
            MAIN_01, MAIN_02, MAIN_03, MAIN_04, MAIN_04A, MAIN_04B, MAIN_04C,
            MAIN_05, MAIN_06, MAIN_07, MAIN_07A, MAIN_08, MAIN_08A, MAIN_08B, MAIN_09, MAIN_10,
            MAIN_11, MAIN_12, MAIN_13, MAIN_14, MAIN_15, MAIN_16, MAIN_17, MAIN_18, MAIN_19, MAIN_20,
            MAIN_21, MAIN_22, MAIN_23, MAIN_24, MAIN_25, MAIN_26, MAIN_27, MAIN_28, MAIN_29);

    /** 主线涉及的 NPC dialogId（去重），供交互/对话注册与可见性表使用。 */
    public static final List<String> NPC_IDS = STAGES.stream()
            .flatMap(stage -> java.util.stream.Stream.of(stage.npc(), stage.effectiveReportNpc()))
            .distinct()
            .toList();

    private static final Map<String, Stage> BY_MISSION = new HashMap<>();
    private static final Map<String, Stage> BY_ID = new HashMap<>();

    static {
        STAGES.forEach(stage -> {
            BY_MISSION.put(stage.mission(), stage);
            BY_ID.put(stage.id(), stage);
        });
    }

    public static Stage byMission(String mission) {
        return BY_MISSION.get(mission);
    }

    public static Stage byId(String id) {
        return BY_ID.get(id);
    }

    public static int indexOf(String id) {
        for (int i = 0; i < STAGES.size(); i++) {
            if (STAGES.get(i).id().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static Stage next(Stage stage) {
        int index = indexOf(stage.id());
        if (index < 0 || index + 1 >= STAGES.size()) {
            return null;
        }
        return STAGES.get(index + 1);
    }

    public static Stage first() {
        return STAGES.get(0);
    }

    private static QuestWaypoint wp(String name, double x, double y, double z) {
        return new QuestWaypoint(name, new Vec3(x, y, z));
    }

    private static QuestWaypoint wp(String name, double x, double y, double z,
                                    net.minecraft.resources.ResourceKey<Level> dimension) {
        return new QuestWaypoint(name, new Vec3(x, y, z), dimension);
    }
}
