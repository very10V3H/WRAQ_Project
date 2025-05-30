package fun.wraq.series.events.dragonboat;

import fun.wraq.common.registry.ModItems;
import fun.wraq.render.gui.trade.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.trade.SingleItemChangeRecipe;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DragonBoatStoreRecipe {
    public static List<SingleItemChangeRecipe> recipes = new ArrayList<>() {{
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SWEET_ZONG_ZI.get(), 20),
                new ItemStack(SpecialEventItems.DRAGON_BOAT_FES_PREFIX.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SWEET_ZONG_ZI.get(), 1),
                new ItemStack(ModItems.JUNIOR_SUPPLY.get()), SingleItemChangePurchaseLimit.Type.DAILY, 5));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SWEET_ZONG_ZI.get(), 5),
                new ItemStack(ModItems.WORLD_SOUL_5.get(), 5), SingleItemChangePurchaseLimit.Type.DAILY, 8));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SWEET_ZONG_ZI.get(), 1),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SWEET_ZONG_ZI.get(), 30),
                new ItemStack(SpecialEventItems.GOLDEN_ZONG_ZI_CONDIMENT.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.ZONG_LEAF.get(), 200),
                new ItemStack(SpecialEventItems.GOLDEN_ZONG_LEAF.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.GOLDEN_ZONG_ZI.get(), 3),
                new ItemStack(SpecialEventItems.SEVEN_SHADE_PIECE_RICE.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.GOLDEN_ZONG_ZI.get(), 3),
                new ItemStack(SpecialEventItems.SEVEN_SHADE_PIECE_GOLDEN_LEAF.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.GOLDEN_ZONG_ZI.get(), 3),
                new ItemStack(SpecialEventItems.SEVEN_SHADE_PIECE_DRAGON_BOAT.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.GOLDEN_ZONG_ZI.get(), 1),
                new ItemStack(SpecialEventItems.DRAGON_BOAT_FES_FORGE_PAPER.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.GOLDEN_ZONG_ZI.get(), 2),
                new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get(), 1)));
    }};
}
