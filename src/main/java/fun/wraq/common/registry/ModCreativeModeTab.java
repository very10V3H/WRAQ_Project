package fun.wraq.common.registry;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.composites.CompositesItems;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.process.system.point.PointItems;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.overworld.divine.DivineIslandItems;
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
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);
    public static RegistryObject<CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TAB.register("wraq_weapon", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.KazeSword3.get())).title(Component.literal("维瑞阿契 | 武器")).build());
    public static RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MODE_TAB.register("wraq_armor", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NETHER_ARMOR_HELMET.get())).title(Component.literal("维瑞阿契 | 护甲")).build());
    public static RegistryObject<CreativeModeTab> BREWING_TAB = CREATIVE_MODE_TAB.register("wraq_brewing", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.POTION)).title(Component.literal("维瑞阿契 | 酿造")).build());
    public static RegistryObject<CreativeModeTab> FORGING_TAB = CREATIVE_MODE_TAB.register("wraq_forgings", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DiamondHammer.get())).title(Component.literal("维瑞阿契 | 锻造")).build());
    public static RegistryObject<CreativeModeTab> SARMOR_TAB = CREATIVE_MODE_TAB.register("wraq_sarmor", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SunOintment0.get())).title(Component.literal("维瑞阿契 | 涂附")).build());
    public static RegistryObject<CreativeModeTab> CODEMANA_TAB = CREATIVE_MODE_TAB.register("wraq_codemana", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CodeSceptre.get())).title(Component.literal("维瑞阿契 | 魔符")).build());
    public static RegistryObject<CreativeModeTab> MONEYANDMISSION_TAB = CREATIVE_MODE_TAB.register("wraq_moneyandmission", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GOLD_COIN.get())).title(Component.literal("维瑞阿契 | 金钱/任务")).build());
    public static RegistryObject<CreativeModeTab> DROPSANDMATERIAL_TAB = CREATIVE_MODE_TAB.register("wraq_dropsandmaterial", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PlainSoul.get())).title(Component.literal("维瑞阿契 | 掉落物与材料")).build());
    public static RegistryObject<CreativeModeTab> CURIOS_AND_GEMS = CREATIVE_MODE_TAB.register("wraq_runesandcuiros", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.END_CURIOS_BOW.get())).title(Component.literal("维瑞阿契 | 饰品/宝石")).build());
    public static RegistryObject<CreativeModeTab> MISC_TAB = CREATIVE_MODE_TAB.register("wraq_hmisc", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ID_Card.get())).title(Component.literal("维瑞阿契 | 杂项")).build());
    public static RegistryObject<CreativeModeTab> DEVELOPMENT_TAB = CREATIVE_MODE_TAB.register("wraq_development", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PP.get())).title(Component.literal("维瑞阿契 | 开发工具")).build());
    public static RegistryObject<CreativeModeTab> WORLD_SOUL = CREATIVE_MODE_TAB.register("wraq_world_soul", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WORLD_SOUL_1.get())).title(Component.literal("维瑞阿契 | 世界本源")).build());
    public static RegistryObject<CreativeModeTab> KILL_PAPER = CREATIVE_MODE_TAB.register("wraq_kill_paper", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.killPaper.get())).title(Component.literal("维瑞阿契 | 征讨券/扫荡券")).build());
    public static RegistryObject<CreativeModeTab> FURNACE = CREATIVE_MODE_TAB.register("wraq_furnace", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CrudeCoal.get())).title(Component.literal("维瑞阿契 | 熔炼")).build());
    public static RegistryObject<CreativeModeTab> CUSTOMIZED = CREATIVE_MODE_TAB.register("wraq_customized", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(UniformItems.AttackCurios0.get())).title(Component.literal("维瑞阿契 | 制式")).build());
    public static RegistryObject<CreativeModeTab> SPECIAL_FESTIVAL = CREATIVE_MODE_TAB.register("wraq_special_festival", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(SpecialEventItems.QING_TUAN.get())).title(Component.literal("维瑞阿契 | 节庆活动")).build());
    public static RegistryObject<CreativeModeTab> ELEMENT = CREATIVE_MODE_TAB.register("wraq_elememt", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LifeElementPiece2.get())).title(Component.literal("维瑞阿契 | 元素")).build());
    public static RegistryObject<CreativeModeTab> LOOT_EQUIP = CREATIVE_MODE_TAB.register("wraq_loot_equip", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(C1LootItems.forestZombieAxe.get())).title(Component.literal("维瑞阿契 | 简易装备")).build());
    public static RegistryObject<CreativeModeTab> SPUR_ITEMS = CREATIVE_MODE_TAB.register("wraq_spur_items", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(SpurItems.cropPiece.get())).title(Component.literal("维瑞阿契 | 支线")).build());
    public static RegistryObject<CreativeModeTab> BLOCK = CREATIVE_MODE_TAB.register("wraq_block", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WRAQ_ORE_1.get().asItem())).title(Component.literal("维瑞阿契 | 方块")).build());
    public static RegistryObject<CreativeModeTab> POINT = CREATIVE_MODE_TAB.register("wraq_point", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(PointItems.EXPT.get().asItem())).title(Component.literal("维瑞阿契 | 点数物品")).build());
    public static RegistryObject<CreativeModeTab> MOONTAIN = CREATIVE_MODE_TAB.register("wraq_moontain", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(MoontainItems.SWORD.get().asItem())).title(Component.literal("维瑞阿契 | 望山物品")).build());
    public static RegistryObject<CreativeModeTab> SUN_ISLAND = CREATIVE_MODE_TAB.register("wraq_sun_island", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(SunIslandItems.TEAR_CURIO_0.get().asItem())).title(Component.literal("维瑞阿契 | 旭升岛")).build());
    public static RegistryObject<CreativeModeTab> HARBINGER = CREATIVE_MODE_TAB.register("wraq_harbinger", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(HarbingerItems.HARBINGER_INGOT.get().asItem())).title(Component.literal("维瑞阿契 | 鹰眼工厂")).build());
    public static RegistryObject<CreativeModeTab> ENDLESS_INSTANCE = CREATIVE_MODE_TAB.register("wraq_endless_instance", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get().asItem())).title(Component.literal("维瑞阿契 | 无尽熵增")).build());
    public static RegistryObject<CreativeModeTab> PROFESSION = CREATIVE_MODE_TAB.register("wraq_profession", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(AllayItems.ALLAY_SPAWNER.get().asItem())).title(Component.literal("维瑞阿契 | 副职业")).build());
    public static RegistryObject<CreativeModeTab> COMPOSITES = CREATIVE_MODE_TAB.register("wraq_composites", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(CompositesItems.SPEED_COMPOSITES.get())).title(Component.literal("维瑞阿契 | 能晶")).build());
    public static RegistryObject<CreativeModeTab> DIVINE_ISLAND = CREATIVE_MODE_TAB.register("wraq_divine_island", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(DivineIslandItems.DIVINE_SWORD_0.get()))
                    .title(Te.s("维瑞阿契 | 圣光岛")).build());
    public static RegistryObject<CreativeModeTab> HOLY = CREATIVE_MODE_TAB.register("wraq_holy", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(IceHolyItems.SWORD_ATTACK_0.get()))
                    .title(Te.s("维瑞阿契 | 圣器")).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }


}
