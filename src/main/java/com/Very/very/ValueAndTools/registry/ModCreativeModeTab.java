package com.Very.very.ValueAndTools.registry;

import com.Very.very.ValueAndTools.Utils.Utils;
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
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.KazeSword3.get())).title(Component.literal("武器")).build());
    public static RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MODE_TAB.register("armor",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.NetherArmorHelmet.get())).title(Component.literal("护甲")).build());
    public static RegistryObject<CreativeModeTab> BREWING_TAB = CREATIVE_MODE_TAB.register("brewing",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.POTION)).title(Component.literal("酿造")).build());
    public static RegistryObject<CreativeModeTab> FORGING_TAB = CREATIVE_MODE_TAB.register("forgings",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.ForgingStone2.get())).title(Component.literal("锻造")).build());
    public static RegistryObject<CreativeModeTab> SARMOR_TAB = CREATIVE_MODE_TAB.register("sarmor",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.SChest.get())).title(Component.literal("涂附")).build());
    public static RegistryObject<CreativeModeTab> CODEMANA_TAB = CREATIVE_MODE_TAB.register("codemana",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.CodeSceptre.get())).title(Component.literal("魔符")).build());
    public static RegistryObject<CreativeModeTab> MONEYANDMISSION_TAB = CREATIVE_MODE_TAB.register("moneyandmission",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.DailyMission.get())).title(Component.literal("金钱/任务")).build());
    public static RegistryObject<CreativeModeTab> DROPSANDMATERIAL_TAB = CREATIVE_MODE_TAB.register("dropsandmaterial",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.gemspiece.get())).title(Component.literal("掉落物与材料")).build());
    public static RegistryObject<CreativeModeTab> RUNESANDCURIOS_TAB = CREATIVE_MODE_TAB.register("runesandcuiros",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.Breathe.get())).title(Component.literal("符石/饰品")).build());
    public static RegistryObject<CreativeModeTab> MISC_TAB = CREATIVE_MODE_TAB.register("hmisc",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.Main0.get())).title(Component.literal("杂项")).build());
    public static RegistryObject<CreativeModeTab> DEVELOPMENT_TAB = CREATIVE_MODE_TAB.register("development",() ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.PP.get())).title(Component.literal("开发工具")).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }


}
