package fun.wraq.series.crystal;

import fun.wraq.common.fast.Te;
import fun.wraq.render.gui.trade.single.SingleItemChangeRecipe;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrystalItem extends WraqItem {
    public final int typeTier;
    public final int tier;
    public static List<CrystalItem> list = new ArrayList<>();

    public CrystalItem(Properties properties, int typeTier, int tier) {
        super(properties);
        this.typeTier = typeTier;
        this.tier = tier;
        if (!map.containsKey(typeTier)) {
            map.put(typeTier, new ArrayList<>());
        }
        map.get(typeTier).add(this);
        list.add(this);
    }

    public static Map<Integer, List<Item>> map = new HashMap<>();

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("单个价值:",
                ((int) Math.pow(2, typeTier + tier)) * 4 + "w", ChatFormatting.GOLD));
        components.add(Te.s("总价值:",
                ((int) Math.pow(2, typeTier + tier)) * itemStack.getCount() * 4 + "w", ChatFormatting.GOLD));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    private static final List<SingleItemChangeRecipe> recipes = new ArrayList<>();
    public static List<SingleItemChangeRecipe> getRecipes() {
        if (recipes.isEmpty()) {
            list.forEach(item -> {
                recipes.add(new SingleItemChangeRecipe(new ItemStack(item),
                                new SingleItemChangeRecipe.VBSellOrBuy(false, item.getPrice())));
            });
            list.forEach(item -> {
                recipes.add(new SingleItemChangeRecipe(new ItemStack(item),
                        new SingleItemChangeRecipe.VBSellOrBuy(true, (int) (item.getPrice() * 1.3))));
            });
        }
        return recipes;
    }

    public int getPrice() {
        return ((int) Math.pow(2, typeTier + tier)) * 40000;
    }
}
