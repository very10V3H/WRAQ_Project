package fun.wraq.series.comsumable;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.active.HeatDevice;
import fun.wraq.series.comsumable.active.HeatInjection;
import fun.wraq.series.comsumable.passive.mixture.MixtureAttack;
import fun.wraq.series.comsumable.passive.mixture.MixturePenetration;
import fun.wraq.series.comsumable.passive.mixture.MixturePenetration0;
import fun.wraq.series.comsumable.passive.quiver.QuiverAttack;
import fun.wraq.series.comsumable.passive.quiver.QuiverPenetration;
import fun.wraq.series.comsumable.passive.quiver.QuiverPenetration0;
import fun.wraq.series.comsumable.passive.whetstone.WhetstoneAttack;
import fun.wraq.series.comsumable.passive.whetstone.WhetstonePenetration;
import fun.wraq.series.comsumable.passive.whetstone.WhetstonePenetration0;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ComsumableItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> WHETSTONE_ATTACK_0 = ITEMS.register("whetstone_attack_0",
            () -> new WhetstoneAttack(new Item.Properties().rarity(Rarity.RARE), 1000, 0.25));
    public static final RegistryObject<Item> WHETSTONE_PENETRATION_0 = ITEMS.register("whetstone_penetration_0",
            () -> new WhetstonePenetration(new Item.Properties().rarity(Rarity.RARE), 1000, 0.25));
    public static final RegistryObject<Item> WHETSTONE_PENETRATION0_0 = ITEMS.register("whetstone_penetration0_0",
            () -> new WhetstonePenetration0(new Item.Properties().rarity(Rarity.RARE), 1000, 50));

    public static final RegistryObject<Item> QUIVER_ATTACK_0 = ITEMS.register("quiver_attack_0",
            () -> new QuiverAttack(new Item.Properties().rarity(Rarity.RARE), 3000, 0.25));
    public static final RegistryObject<Item> QUIVER_PENETRATION_0 = ITEMS.register("quiver_penetration_0",
            () -> new QuiverPenetration(new Item.Properties().rarity(Rarity.RARE), 3000, 0.25));
    public static final RegistryObject<Item> QUIVER_PENETRATION0_0 = ITEMS.register("quiver_penetration0_0",
            () -> new QuiverPenetration0(new Item.Properties().rarity(Rarity.RARE), 3000, 50));

    public static final RegistryObject<Item> MIXTURE_ATTACK_0 = ITEMS.register("mixture_attack_0",
            () -> new MixtureAttack(new Item.Properties().rarity(Rarity.RARE), 3000, 0.25));
    public static final RegistryObject<Item> MIXTURE_PENETRATION_0 = ITEMS.register("mixture_penetration_0",
            () -> new MixturePenetration(new Item.Properties().rarity(Rarity.RARE), 3000, 0.25));
    public static final RegistryObject<Item> MIXTURE_PENETRATION0_0 = ITEMS.register("mixture_penetration0_0",
            () -> new MixturePenetration0(new Item.Properties().rarity(Rarity.RARE), 3000, 50));

    public static final RegistryObject<Item> HEAT_INJECTION_0 = ITEMS.register("heat_injection_0",
            () -> new HeatInjection(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> HEAT_INJECTION_1 = ITEMS.register("heat_injection_1",
            () -> new HeatInjection(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 1));
    public static final RegistryObject<Item> HEAT_INJECTION_2 = ITEMS.register("heat_injection_2",
            () -> new HeatInjection(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 2));
    public static final RegistryObject<Item> HEAT_INJECTION_3 = ITEMS.register("heat_injection_3",
            () -> new HeatInjection(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 3));

    public static final RegistryObject<Item> HEAT_DEVICE_0 = ITEMS.register("heat_device_0",
            () -> new HeatDevice(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 0));
    public static final RegistryObject<Item> HEAT_DEVICE_1 = ITEMS.register("heat_device_1",
            () -> new HeatDevice(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 1));
    public static final RegistryObject<Item> HEAT_DEVICE_2 = ITEMS.register("heat_device_2",
            () -> new HeatDevice(new Item.Properties().rarity(CustomStyle.BUNKER_BOLD_RARITY), 2));
}
