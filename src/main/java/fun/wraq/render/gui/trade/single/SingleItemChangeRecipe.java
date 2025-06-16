package fun.wraq.render.gui.trade.single;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.cooking.item.FoodCoinStoreRecipe;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.series.crystal.CrystalItem;
import fun.wraq.series.events.dragonboat.DragonBoatStoreRecipe;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SingleItemChangeRecipe {
    public record VBSellOrBuy(boolean isSell, int price) {}
    public ItemStack needStack;
    public ItemStack goods;
    public String limitType;
    public int limitTimes;
    public VBSellOrBuy vbSellOrBuy;
    public SingleItemChangeRecipe(ItemStack needStack, ItemStack goods, String limitType, int limitTimes,
                                  VBSellOrBuy vbSellOrBuy) {
        this.needStack = needStack;
        this.goods = goods;
        this.limitType = limitType;
        this.limitTimes = limitTimes;
        this.vbSellOrBuy = vbSellOrBuy;
    }

    public SingleItemChangeRecipe(ItemStack goods, VBSellOrBuy vbSellOrBuy) {
        this(vbSellOrBuy.isSell
                ? ModItems.SELL_ITEM.get().getDefaultInstance() : ModItems.BUY_ITEM.get().getDefaultInstance(),
                goods, SingleItemChangePurchaseLimit.Type.NULL, 0, vbSellOrBuy);
    }

    public SingleItemChangeRecipe(ItemStack needStack, ItemStack goods, String limitType, int limitTimes) {
        this(needStack, goods, limitType, limitTimes, null);
    }

    public SingleItemChangeRecipe(ItemStack needStack, ItemStack goods) {
        this(needStack, goods, SingleItemChangePurchaseLimit.Type.NULL, 0);
    }

    public static List<SingleItemChangeRecipe> recipeList = new ArrayList<>();

    public static List<SingleItemChangeRecipe> endlessCoreStoreRecipe = List.of(
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.NOTE_PAPER.get(), 16), SingleItemChangePurchaseLimit.Type.DAILY, 4),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.NOTE_PAPER.get(), 16), SingleItemChangePurchaseLimit.Type.WEEKLY, 7),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 8),
                    new ItemStack(EndlessInstanceItems.HOURS_EX_HARVEST_POTION.get(), 1),
                    SingleItemChangePurchaseLimit.Type.DAILY, 1),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.FORGING_STONE_2.get(), 4), SingleItemChangePurchaseLimit.Type.DAILY, 4),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 4),
                    new ItemStack(ModItems.FORGE_ENHANCE_3.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.PEARL_1.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.PEARL_2.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.PEARL_3.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.PEARL_4.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.PEARL_5.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),
            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.PEARL_6.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 2),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(MoontainItems.HEART.get(), 2), SingleItemChangePurchaseLimit.Type.WEEKLY, 4),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 2),
                    new ItemStack(ModItems.EQUIP_PIECE_4.get(), 2), SingleItemChangePurchaseLimit.Type.DAILY, 8),

            new SingleItemChangeRecipe(new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), 16),
                    new ItemStack(ModItems.EQUIP_PIECE_5.get(), 4), SingleItemChangePurchaseLimit.Type.WEEKLY, 4)
    );

    public static List<SingleItemChangeRecipe> getRecipeList() {
        if (recipeList.isEmpty()) {
            recipeList.addAll(endlessCoreStoreRecipe);
            recipeList.addAll(FoodCoinStoreRecipe.recipes);
            recipeList.addAll(DragonBoatStoreRecipe.recipes);
            recipeList.addAll(CrystalItem.getRecipes());
        }
        return recipeList;
    }

    public String getDataKey() {
        return needStack.getItem() + "#" + needStack.getCount() + "#"
                + goods.getItem() + "#" + goods.getCount() + "#" + limitType + "#";
    }
}