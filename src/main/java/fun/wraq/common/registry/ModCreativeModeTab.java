package fun.wraq.common.registry;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.composites.CompositesItems;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.point.PointItems;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import fun.wraq.series.overworld.sun.SunIslandItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB
            = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);
    public static RegistryObject<CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TAB.register("aa_wraq_a_weapon",
            () -> CreativeModeTab.builder().icon(
                    () -> new ItemStack(ModItems.KAZE_SWORD_3.get())).title(Component.literal("维瑞阿契 | 武器")).build());
    public static RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MODE_TAB.register("aa_wraq_b_armor",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.NETHER_HELMET.get())).title(Component.literal("维瑞阿契 | 护甲")).build());
    public static RegistryObject<CreativeModeTab> BREWING_TAB = CREATIVE_MODE_TAB.register("aa_wraq_c_brewing",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(Items.POTION)).title(Component.literal("维瑞阿契 | 酿造")).build());
    public static RegistryObject<CreativeModeTab> FORGING_TAB = CREATIVE_MODE_TAB.register("aa_wraq_d_forgings",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.DIAMOND_HAMMER.get())).title(Component.literal("维瑞阿契 | 锻造")).build());
    public static RegistryObject<CreativeModeTab> MONEYANDMISSION_TAB = CREATIVE_MODE_TAB.register("aa_wraq_e_moneyandmission",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.GOLD_COIN.get())).title(Component.literal("维瑞阿契 | 金钱/任务")).build());
    public static RegistryObject<CreativeModeTab> DROPSANDMATERIAL_TAB = CREATIVE_MODE_TAB.register("aa_wraq_f_dropsandmaterial",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.PLAIN_SOUL.get())).title(Component.literal("维瑞阿契 | 掉落物与材料")).build());
    public static RegistryObject<CreativeModeTab> CURIOS_AND_GEMS = CREATIVE_MODE_TAB.register("aa_wraq_g_runesandcuiros",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.END_CURIOS_BOW.get())).title(Component.literal("维瑞阿契 | 饰品/宝石")).build());
    public static RegistryObject<CreativeModeTab> MISC_TAB = CREATIVE_MODE_TAB.register("aa_wraq_h_hmisc",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.ID_CARD.get())).title(Component.literal("维瑞阿契 | 杂项")).build());
    public static RegistryObject<CreativeModeTab> DEVELOPMENT_TAB = CREATIVE_MODE_TAB.register("aa_wraq_i_development",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.PP.get())).title(Component.literal("维瑞阿契 | 开发工具")).build());
    public static RegistryObject<CreativeModeTab> WORLD_SOUL = CREATIVE_MODE_TAB.register("aa_wraq_j_world_soul",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.WORLD_SOUL_1.get())).title(Component.literal("维瑞阿契 | 世界本源")).build());
    public static RegistryObject<CreativeModeTab> KILL_PAPER = CREATIVE_MODE_TAB.register("aa_wraq_k_kill_paper",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.KILL_PAPER.get())).title(Component.literal("维瑞阿契 | 征讨券/扫荡券")).build());
    public static RegistryObject<CreativeModeTab> FURNACE = CREATIVE_MODE_TAB.register("aa_wraq_l_furnace",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.CRUDE_COAL.get())).title(Component.literal("维瑞阿契 | 熔炼")).build());
    public static RegistryObject<CreativeModeTab> CUSTOMIZED = CREATIVE_MODE_TAB.register("aa_wraq_m_customized",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(UniformItems.ATTACK_CURIOS_0.get())).title(Component.literal("维瑞阿契 | 制式")).build());
    public static RegistryObject<CreativeModeTab> SPECIAL_FESTIVAL = CREATIVE_MODE_TAB.register("aa_wraq_n_special_festival",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(SpecialEventItems.QING_TUAN.get())).title(Component.literal("维瑞阿契 | 节庆活动")).build());
    public static RegistryObject<CreativeModeTab> ELEMENT = CREATIVE_MODE_TAB.register("aa_wraq_o_elememt",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.LIFE_ELEMENT_PIECE_2.get())).title(Component.literal("维瑞阿契 | 元素")).build());
    public static RegistryObject<CreativeModeTab> LOOT_EQUIP = CREATIVE_MODE_TAB.register("aa_wraq_p_loot_equip",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(C1LootItems.FOREST_ZOMBIE_AXE.get())).title(Component.literal("维瑞阿契 | 简易装备")).build());
    public static RegistryObject<CreativeModeTab> SPUR_ITEMS = CREATIVE_MODE_TAB.register("aa_wraq_q_spur_items",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(SpurItems.CROP_PIECE.get())).title(Component.literal("维瑞阿契 | 支线")).build());
    public static RegistryObject<CreativeModeTab> BLOCK = CREATIVE_MODE_TAB.register("aa_wraq_r_block",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModBlocks.WRAQ_ORE_1.get().asItem())).title(Component.literal("维瑞阿契 | 方块")).build());
    public static RegistryObject<CreativeModeTab> POINT = CREATIVE_MODE_TAB.register("aa_wraq_s_point",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(PointItems.EXPT.get().asItem())).title(Component.literal("维瑞阿契 | 点数物品")).build());
    public static RegistryObject<CreativeModeTab> MOONTAIN = CREATIVE_MODE_TAB.register("aa_wraq_t_moontain",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(MoontainItems.SWORD.get().asItem())).title(Component.literal("维瑞阿契 | 望山物品")).build());
    public static RegistryObject<CreativeModeTab> SUN_ISLAND = CREATIVE_MODE_TAB.register("aa_wraq_u_sun_island",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(SunIslandItems.TEAR_CURIO_0.get().asItem())).title(Component.literal("维瑞阿契 | 旭升岛")).build());
    public static RegistryObject<CreativeModeTab> HARBINGER = CREATIVE_MODE_TAB.register("aa_wraq_v_harbinger",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(HarbingerItems.HARBINGER_INGOT.get().asItem())).title(Component.literal("维瑞阿契 | 鹰眼工厂")).build());
    public static RegistryObject<CreativeModeTab> ENDLESS_INSTANCE = CREATIVE_MODE_TAB.register("aa_wraq_w_endless_instance",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get().asItem())).title(Component.literal("维瑞阿契 | 无尽熵增")).build());
    public static RegistryObject<CreativeModeTab> PROFESSION = CREATIVE_MODE_TAB.register("aa_wraq_x_profession",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(AllayItems.ALLAY_SPAWNER.get().asItem())).title(Component.literal("维瑞阿契 | 副职业")).build());
    public static RegistryObject<CreativeModeTab> COMPOSITES = CREATIVE_MODE_TAB.register("aa_wraq_y_composites",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(CompositesItems.SPEED_COMPOSITES.get())).title(Component.literal("维瑞阿契 | 能晶")).build());
    public static RegistryObject<CreativeModeTab> DIVINE_ISLAND = CREATIVE_MODE_TAB.register("aa_wraq_z_divine_island",
            () -> CreativeModeTab.builder().icon(
                                    () -> new ItemStack(DivineIslandItems.DIVINE_SWORD_0.get()))
                            .title(Te.s("维瑞阿契 | 圣光岛")).build());
    public static RegistryObject<CreativeModeTab> HOLY = CREATIVE_MODE_TAB.register("aa_wraq_za_holy",
            () -> CreativeModeTab.builder().icon(
                                    () -> new ItemStack(IceHolyItems.SWORD_ATTACK_0.get()))
                            .title(Te.s("维瑞阿契 | 圣器")).build());
    public static RegistryObject<CreativeModeTab> NEW_AREA = CREATIVE_MODE_TAB.register("aa_wraq_zb_new_area",
            () -> CreativeModeTab.builder().icon(
                                    () -> new ItemStack(NewAreaItems.STONE_SPIDER_KNIFE_0.get()))
                            .title(Te.s("维瑞阿契 | 新区域")).build());
    public static RegistryObject<CreativeModeTab> MANA_TOWER = CREATIVE_MODE_TAB.register("aa_wraq_zc_mana_tower",
            () -> CreativeModeTab.builder().icon(
                                    () -> new ItemStack(ExtraordinaryItems.NAN_HAI_A.get()))
                            .title(Te.s("维瑞阿契 | 炼魔塔")).build());
    public static RegistryObject<CreativeModeTab> COOKING = CREATIVE_MODE_TAB.register("aa_wraq_zd_cooking",
            () -> CreativeModeTab.builder().icon(
                                    () -> new ItemStack(CookingItems.FOOD_PACKAGE_0.get()))
                            .title(Te.s("维瑞阿契 | 烹饪")).build());
    public static RegistryObject<CreativeModeTab> CRYSTAL = CREATIVE_MODE_TAB.register("aa_wraq_ze_crystal",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(CrystalItems.PURPLE_CRYSTAL_PP.get()))
                    .title(Te.s("维瑞阿契 | 各色宝石")).build());
    public static RegistryObject<CreativeModeTab> SILVER_DRAGON = CREATIVE_MODE_TAB.register("aa_wraq_zf_silver_dragon",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get()))
                    .title(Te.s("维瑞阿契 | 银龙传说装备")).build());
    public static RegistryObject<CreativeModeTab> ALL = CREATIVE_MODE_TAB.register("aa_wraq_zg_all",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.DEVIL_SWORD.get()))
                    .title(Te.s("维瑞阿契 | 总集")).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
