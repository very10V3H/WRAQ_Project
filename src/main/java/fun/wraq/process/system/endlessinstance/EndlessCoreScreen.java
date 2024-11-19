package fun.wraq.process.system.endlessinstance;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.render.gui.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.SingleItemChangeRecipe;
import fun.wraq.render.gui.SingleItemChangeScreen;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EndlessCoreScreen extends SingleItemChangeScreen {

    public static List<SingleItemChangeRecipe> recipeList = List.of(
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get()), SingleItemChangePurchaseLimit.Type.NULL, 0)
    );

    public EndlessCoreScreen() {
        super(recipeList);
    }
}
