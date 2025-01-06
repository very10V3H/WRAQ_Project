package fun.wraq.render.gui.trade;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record SingleItemChangeRecipe(ItemStack needStack, ItemStack goods, String limitType, int limitTimes) {
    public static List<SingleItemChangeRecipe> recipeList = new ArrayList<>();

    public static List<SingleItemChangeRecipe> endlessCoreStoreRecipe = List.of(
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.notePaper.get(), 16), SingleItemChangePurchaseLimit.Type.DAILY, 4),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.notePaper.get(), 16), SingleItemChangePurchaseLimit.Type.WEEKLY, 7),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 8),
                    new ItemStack(EndlessInstanceItems.HOURS_EX_HARVEST_POTION.get(), 1),
                    SingleItemChangePurchaseLimit.Type.DAILY, 1),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.ForgingStone2.get(), 4), SingleItemChangePurchaseLimit.Type.DAILY, 4),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.ForgeEnhance3.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),

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
                    new ItemStack(MoontainItems.HEART.get(), 2), SingleItemChangePurchaseLimit.Type.WEEKLY, 4),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.equipPiece4.get(), 2), SingleItemChangePurchaseLimit.Type.DAILY, 8),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.equipPiece5.get(), 4), SingleItemChangePurchaseLimit.Type.WEEKLY, 4)
    );

    public static List<SingleItemChangeRecipe> bondStoreRecipe = List.of(
            new SingleItemChangeRecipe(new ItemStack(ModItems.BOND.get(), 3),
                    new ItemStack(AllayItems.ALLAY_SPAWNER.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(ModItems.BOND.get()),
                    new ItemStack(AllayItems.ATTACK_SKILL_BOOK.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(ModItems.BOND.get()),
                    new ItemStack(AllayItems.HEALING_SKILL_BOOK.get()), SingleItemChangePurchaseLimit.Type.NULL, 0),
            new SingleItemChangeRecipe(new ItemStack(ModItems.BOND.get()),
                    new ItemStack(AllayItems.GEM_PIECE_SKILL_BOOK.get()), SingleItemChangePurchaseLimit.Type.NULL, 0)
    );

    public static List<SingleItemChangeRecipe> getRecipeList() {
        if (recipeList.isEmpty()) {
            recipeList.addAll(endlessCoreStoreRecipe);
            recipeList.addAll(bondStoreRecipe);
        }
        return recipeList;
    }

    public String getDataKey() {
        return needStack().getItem() + "#" + needStack().getCount() + "#"
                + goods().getItem() + "#" + goods().getCount() + "#" + limitType + "#";
    }
}