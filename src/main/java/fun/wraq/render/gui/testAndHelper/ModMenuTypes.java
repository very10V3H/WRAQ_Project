package fun.wraq.render.gui.testAndHelper;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.profession.pet.dev.PetMenu;
import fun.wraq.render.gui.blocks.BrewingMenu;
import fun.wraq.render.gui.blocks.ForgingBlockMenu;
import fun.wraq.render.gui.blocks.FurnaceMenu;
import fun.wraq.render.gui.blocks.InjectBlockMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Utils.MOD_ID);

    public static final RegistryObject<MenuType<ForgingBlockMenu>> FIRST_MENU =
            registerMenuType(ForgingBlockMenu::new, "first_block_menu");

    public static final RegistryObject<MenuType<BrewingMenu>> BREWING_MENU =
            registerMenuType(BrewingMenu::new, "h_brewing_block_menu");

    public static final RegistryObject<MenuType<InjectBlockMenu>> INJECT_BLOCK_MENU =
            registerMenuType(InjectBlockMenu::new, "inject_block_menu");

    public static final RegistryObject<MenuType<FurnaceMenu>> Furnace_Menu =
            registerMenuType(FurnaceMenu::new, "furnace_menu");

    public static final RegistryObject<MenuType<PetMenu>> PET_MENU =
            registerMenuType(PetMenu::new, "pet_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
