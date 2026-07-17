package fun.wraq.process.system.backpack;

import fun.wraq.common.util.Utils;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * AI-Generated, 2026-07-12
 * 背包系统的 MenuType 注册。
 */
public class BackpackMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Utils.MOD_ID);

    public static final RegistryObject<MenuType<BackpackMenu>> BACKPACK_MENU =
            MENUS.register("backpack_menu", () -> IForgeMenuType.create(BackpackMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
