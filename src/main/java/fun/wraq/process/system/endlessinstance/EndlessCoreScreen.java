package fun.wraq.process.system.endlessinstance;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.render.gui.trade.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.trade.SingleItemChangeRecipe;
import fun.wraq.render.gui.trade.SingleItemChangeScreen;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EndlessCoreScreen extends SingleItemChangeScreen {

    public static List<SingleItemChangeRecipe> recipeList = List.of(
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.notePaper.get(), 16), SingleItemChangePurchaseLimit.Type.DAILY, 4),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get(), 16), SingleItemChangePurchaseLimit.Type.WEEKLY, 7),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 8),
                    new ItemStack(EndlessInstanceItems.HOURS_EX_HARVEST_POTION.get(), 1),
                    SingleItemChangePurchaseLimit.Type.DAILY, 1),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.ForgingStone2.get(), 4), SingleItemChangePurchaseLimit.Type.DAILY, 4),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.Pearl1.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.Pearl2.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.Pearl3.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.Pearl4.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.Pearl5.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.Pearl6.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(MoontainItems.HEART.get(), 2), SingleItemChangePurchaseLimit.Type.WEEKLY, 4)
    );

    public EndlessCoreScreen() {
        super(recipeList);
    }
}
