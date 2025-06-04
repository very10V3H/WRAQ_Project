package fun.wraq.render.gui.trade.weekly;

import fun.wraq.render.gui.villagerTrade.TradeList;
import fun.wraq.render.gui.villagerTrade.TradeListNew;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class WeeklyStoreRecipeDataS2CPacket {

    private final List<WeeklyStoreRecipe> recipeList;

    public WeeklyStoreRecipeDataS2CPacket(List<WeeklyStoreRecipe> recipeList) {
        this.recipeList = recipeList;
    }

    public WeeklyStoreRecipeDataS2CPacket(FriendlyByteBuf buf) {
        this.recipeList = buf.readList((friendlyByteBuf -> {
            ItemStack materialStack0 = friendlyByteBuf.readItem();
            ItemStack materialStack1 = friendlyByteBuf.readItem();
            ItemStack materialStack2 = friendlyByteBuf.readItem();
            ItemStack product = friendlyByteBuf.readItem();
            int count = friendlyByteBuf.readInt();
            return new WeeklyStoreRecipe(materialStack0, materialStack1, materialStack2, product, count);
        }));
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.recipeList, ((friendlyByteBuf, weeklyStoreRecipe) -> {
            ItemStack materialStack0 = weeklyStoreRecipe.materialStack0;
            ItemStack materialStack1 = weeklyStoreRecipe.materialStack1;
            ItemStack materialStack2 = weeklyStoreRecipe.materialStack2;
            ItemStack product = weeklyStoreRecipe.product;
            int count = weeklyStoreRecipe.count;
            buf.writeItem(materialStack0);
            buf.writeItem(materialStack1);
            buf.writeItem(materialStack2);
            buf.writeItem(product);
            buf.writeInt(count);
        }));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            WeeklyStore.recipes.clear();
            WeeklyStore.recipes.addAll(recipeList);
            recipeList.forEach(recipe -> {
                TradeList.tradeRecipeMap.put(recipe.product, recipe.getMaterialList());
            });
            TradeList.tradeContent.put(TradeListNew.WEEKLY_STORE_VILLAGER_NAME,
                    recipeList.stream().map(recipe -> recipe.product).toList());
        });
        return true;
    }
}
