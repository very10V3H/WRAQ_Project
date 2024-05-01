package com.very.wraq.valueAndTools.registry;

import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,Utils.MOD_ID);
    public static RegistryObject<CreativeModeTab> SWORD_TAB = CREATIVE_MODE_TAB.register("sword",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.KazeSword3.get())).title(Component.literal("武器")).build());
    public static RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MODE_TAB.register("armor",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NetherArmorHelmet.get())).title(Component.literal("护甲")).build());
    public static RegistryObject<CreativeModeTab> BREWING_TAB = CREATIVE_MODE_TAB.register("brewing",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.POTION)).title(Component.literal("酿造")).build());
    public static RegistryObject<CreativeModeTab> FORGING_TAB = CREATIVE_MODE_TAB.register("forgings",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SakuraSwordForgeDraw.get())).title(Component.literal("锻造")).build());
    public static RegistryObject<CreativeModeTab> SARMOR_TAB = CREATIVE_MODE_TAB.register("sarmor",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SunOintment0.get())).title(Component.literal("涂附")).build());
    public static RegistryObject<CreativeModeTab> CODEMANA_TAB = CREATIVE_MODE_TAB.register("codemana",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CodeSceptre.get())).title(Component.literal("魔符")).build());
    public static RegistryObject<CreativeModeTab> MONEYANDMISSION_TAB = CREATIVE_MODE_TAB.register("moneyandmission",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GoldCoin.get())).title(Component.literal("金钱/任务")).build());
    public static RegistryObject<CreativeModeTab> DROPSANDMATERIAL_TAB = CREATIVE_MODE_TAB.register("dropsandmaterial",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GemPiece.get())).title(Component.literal("掉落物与材料")).build());
    public static RegistryObject<CreativeModeTab> RUNESANDCURIOS_TAB = CREATIVE_MODE_TAB.register("runesandcuiros",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.Breathe.get())).title(Component.literal("符石/饰品")).build());
    public static RegistryObject<CreativeModeTab> MISC_TAB = CREATIVE_MODE_TAB.register("hmisc",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ID_Card.get())).title(Component.literal("杂项")).build());
    public static RegistryObject<CreativeModeTab> DEVELOPMENT_TAB = CREATIVE_MODE_TAB.register("development",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PP.get())).title(Component.literal("开发工具")).build());
    public static RegistryObject<CreativeModeTab> WORLD_SOUL = CREATIVE_MODE_TAB.register("world_soul",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WorldSoul1.get())).title(Component.literal("世界本源")).build());
    public static RegistryObject<CreativeModeTab> KILL_PAPER = CREATIVE_MODE_TAB.register("kill_paper",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PlainZombieKillPaper.get())).title(Component.literal("征讨券/扫荡券")).build());
    public static RegistryObject<CreativeModeTab> FURNACE = CREATIVE_MODE_TAB.register("furnace",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CrudeCoal.get())).title(Component.literal("熔炼")).build());
    public static RegistryObject<CreativeModeTab> SPRING = CREATIVE_MODE_TAB.register("spring",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RedEnvelope.get())).title(Component.literal("春节活动")).build());
    public static RegistryObject<CreativeModeTab> CUSTOMIZED = CREATIVE_MODE_TAB.register("customized",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LXYZOSword.get())).title(Component.literal("定制")).build());
    public static RegistryObject<CreativeModeTab> QINGMING = CREATIVE_MODE_TAB.register("qingming",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.QingTuan.get())).title(Component.literal("清明")).build());
    public static RegistryObject<CreativeModeTab> ELEMENT = CREATIVE_MODE_TAB.register("elememt",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LifeElementPiece2.get())).title(Component.literal("元素")).build());
    public static RegistryObject<CreativeModeTab> LABOUR_DAY = CREATIVE_MODE_TAB.register("labour_day",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.IRON_HOE)).title(Component.literal("劳动节")).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }


}
