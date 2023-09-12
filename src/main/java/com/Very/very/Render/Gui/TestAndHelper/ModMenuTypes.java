package com.Very.very.Render.Gui.TestAndHelper;

import com.Very.very.Render.Gui.Blocks.BrewingMenu;
import com.Very.very.Render.Gui.Blocks.FirstBlockMenu;
import com.Very.very.ValueAndTools.Utils.Utils;
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

    public static final RegistryObject<MenuType<FirstBlockMenu>> FIRST_MENU =
            registerMenuType(FirstBlockMenu::new, "first_block_menu");
    public static final RegistryObject<MenuType<BrewingMenu>> BREWING_MENU =
            registerMenuType(BrewingMenu::new, "h_brewing_block_menu");
    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
