package fun.wraq.series.events.midautumn;

import fun.wraq.common.registry.ModItems;
import fun.wraq.render.gui.trade.single.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.trade.single.SingleItemChangeRecipe;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.gui.villagerTrade.TradeListNew;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MidAutumnStoreRecipe {
    public static List<SingleItemChangeRecipe> recipes = new ArrayList<>() {{
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 5),
                new ItemStack(ModItems.JUNIOR_SUPPLY.get()), SingleItemChangePurchaseLimit.Type.DAILY, 5));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 2),
                new ItemStack(ModItems.WORLD_SOUL_5.get(), 5), SingleItemChangePurchaseLimit.Type.DAILY, 8));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 1),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 20));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 20),
                new ItemStack(SpecialEventItems.DRAGON_DIAMOND.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 10),
                new ItemStack(SpecialEventItems.PREFIX_MID_AUTUMN_2025_0.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 10),
                new ItemStack(SpecialEventItems.MOON_FEATHER_0.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 15),
                new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_0.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 30),
                new ItemStack(SpecialEventItems.MID_AUTUMN_FORGE_PAPER.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 30),
                new ItemStack(SpecialEventItems.MID_AUTUMN_GEM.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 5),
                new ItemStack(SpecialEventItems.GOLDEN_WHEAT_DOUGH.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 2),
                new ItemStack(SpecialEventItems.MANA_ADZUKI_BEANS.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 2),
                new ItemStack(SpecialEventItems.OLIVE_OIL.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOONCAKE.get(), 2),
                new ItemStack(SpecialEventItems.SUPREME_EGG.get(), 1)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.OSMANTHUS.get(), 1),
                new SingleItemChangeRecipe.VBSellOrBuy(true, 8150)));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.MOON_OSMANTHUS.get(), 1),
                new SingleItemChangeRecipe.VBSellOrBuy(true, 81500)));
    }};

    public static final String VILLAGER_NAME = "天官 - 饕餮";

    public static void setVillagerData() {
        List<TradeListNew> list = new ArrayList<>() {{
            add(new TradeListNew(new ItemStack(SpecialEventItems.MOON_FEATHER_1.get()), List.of(
                    new ItemStack(SpecialEventItems.MOON_FEATHER_0.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 15)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.MOON_FEATHER_2.get()), List.of(
                    new ItemStack(SpecialEventItems.MOON_FEATHER_1.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 20)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.MOON_FEATHER_3.get()), List.of(
                    new ItemStack(SpecialEventItems.MOON_FEATHER_2.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 25)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.MOON_FEATHER_4.get()), List.of(
                    new ItemStack(SpecialEventItems.MOON_FEATHER_3.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 30),
                    new ItemStack(ModItems.REPUTATION_MEDAL.get(), 30)
            )));

            add(new TradeListNew(new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_1.get()), List.of(
                    new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_0.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 20)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_2.get()), List.of(
                    new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_1.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 30)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_3.get()), List.of(
                    new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_2.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 40),
                    new ItemStack(ModItems.REPUTATION_MEDAL.get(), 30)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_4.get()), List.of(
                    new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_3.get()),
                    new ItemStack(SpecialEventItems.SUPREME_MOONCAKE.get(), 50),
                    new ItemStack(ModItems.REPUTATION_MEDAL.get(), 60)
            )));
        }};

        TradeListNew.setVillagerData("天官 - 宝", "midAutumnCurio",
                CustomStyle.styleOfMoon, VillagerType.SNOW, VillagerProfession.LIBRARIAN, list);
        MyVillagerData.setMyVillagerData(VILLAGER_NAME,
                "midAutumn", CustomStyle.styleOfMoon, VillagerType.SNOW,
                VillagerProfession.CARTOGRAPHER, List.of());
    }
}
