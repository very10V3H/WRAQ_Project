package fun.wraq.common.registry;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.point.PointItems;
import fun.wraq.process.system.spur.Items.SpurItems;
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
    public static RegistryObject<CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TAB.register("weapon", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.KazeSword3.get())).title(Component.literal("武器")).build());
    public static RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MODE_TAB.register("armor", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NETHER_ARMOR_HELMET.get())).title(Component.literal("护甲")).build());
    public static RegistryObject<CreativeModeTab> BREWING_TAB = CREATIVE_MODE_TAB.register("brewing", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.POTION)).title(Component.literal("酿造")).build());
    public static RegistryObject<CreativeModeTab> FORGING_TAB = CREATIVE_MODE_TAB.register("forgings", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DiamondHammer.get())).title(Component.literal("锻造")).build());
    public static RegistryObject<CreativeModeTab> SARMOR_TAB = CREATIVE_MODE_TAB.register("sarmor", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SunOintment0.get())).title(Component.literal("涂附")).build());
    public static RegistryObject<CreativeModeTab> CODEMANA_TAB = CREATIVE_MODE_TAB.register("codemana", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CodeSceptre.get())).title(Component.literal("魔符")).build());
    public static RegistryObject<CreativeModeTab> MONEYANDMISSION_TAB = CREATIVE_MODE_TAB.register("moneyandmission", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.goldCoin.get())).title(Component.literal("金钱/任务")).build());
    public static RegistryObject<CreativeModeTab> DROPSANDMATERIAL_TAB = CREATIVE_MODE_TAB.register("dropsandmaterial", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PlainSoul.get())).title(Component.literal("掉落物与材料")).build());
    public static RegistryObject<CreativeModeTab> CURIOS_AND_GEMS = CREATIVE_MODE_TAB.register("runesandcuiros", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.END_CURIOS_BOW.get())).title(Component.literal("饰品/宝石")).build());
    public static RegistryObject<CreativeModeTab> MISC_TAB = CREATIVE_MODE_TAB.register("hmisc", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ID_Card.get())).title(Component.literal("杂项")).build());
    public static RegistryObject<CreativeModeTab> DEVELOPMENT_TAB = CREATIVE_MODE_TAB.register("development", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PP.get())).title(Component.literal("开发工具")).build());
    public static RegistryObject<CreativeModeTab> WORLD_SOUL = CREATIVE_MODE_TAB.register("world_soul", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WorldSoul1.get())).title(Component.literal("世界本源")).build());
    public static RegistryObject<CreativeModeTab> KILL_PAPER = CREATIVE_MODE_TAB.register("kill_paper", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.killPaper.get())).title(Component.literal("征讨券/扫荡券")).build());
    public static RegistryObject<CreativeModeTab> FURNACE = CREATIVE_MODE_TAB.register("furnace", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CrudeCoal.get())).title(Component.literal("熔炼")).build());
    public static RegistryObject<CreativeModeTab> CUSTOMIZED = CREATIVE_MODE_TAB.register("customized", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(UniformItems.AttackCurios0.get())).title(Component.literal("制式")).build());
    public static RegistryObject<CreativeModeTab> SPECIAL_FESTIVAL = CREATIVE_MODE_TAB.register("special_festival", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.QingTuan.get())).title(Component.literal("节庆活动")).build());
    public static RegistryObject<CreativeModeTab> ELEMENT = CREATIVE_MODE_TAB.register("elememt", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LifeElementPiece2.get())).title(Component.literal("元素")).build());
    public static RegistryObject<CreativeModeTab> LOOT_EQUIP = CREATIVE_MODE_TAB.register("loot_equip", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(C1LootItems.forestZombieAxe.get())).title(Component.literal("简易装备")).build());
    public static RegistryObject<CreativeModeTab> SPUR_ITEMS = CREATIVE_MODE_TAB.register("spur_items", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(SpurItems.cropPiece.get())).title(Component.literal("支线")).build());
    public static RegistryObject<CreativeModeTab> BLOCK = CREATIVE_MODE_TAB.register("block", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WRAQ_ORE_1.get().asItem())).title(Component.literal("方块")).build());
    public static RegistryObject<CreativeModeTab> POINT = CREATIVE_MODE_TAB.register("point", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(PointItems.EXPT.get().asItem())).title(Component.literal("点数物品")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }


}
