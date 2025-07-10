package fun.wraq.series.events.summer2025;

import fun.wraq.common.registry.ModItems;
import fun.wraq.render.gui.trade.single.SingleItemChangePurchaseLimit;
import fun.wraq.render.gui.trade.single.SingleItemChangeRecipe;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.gui.villagerTrade.TradeListNew;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Summer2025StoreRecipe {
    public static List<SingleItemChangeRecipe> recipes = new ArrayList<>() {{
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_GOLD_COIN.get(), 1),
                new ItemStack(SpecialEventItems.SNACK_CHEST.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 100),
                new ItemStack(SpecialEventItems.PREFIX_SUMMER2025_0.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 300),
                new ItemStack(SpecialEventItems.PREFIX_SUMMER2025_1.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 500),
                new ItemStack(SpecialEventItems.PREFIX_SUMMER2025_2.get())));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 5),
                new ItemStack(ModItems.GOLD_COIN.get(), 30), SingleItemChangePurchaseLimit.Type.DAILY, 5));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 10),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 5));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 20),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 1), SingleItemChangePurchaseLimit.Type.DAILY, 1));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 10),
                new ItemStack(ModItems.WORLD_SOUL_5.get(), 8), SingleItemChangePurchaseLimit.Type.DAILY, 5));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 50),
                new ItemStack(ModItems.WORLD_FORGE_STONE.get()), SingleItemChangePurchaseLimit.Type.DAILY, 2));
        add(new SingleItemChangeRecipe(new ItemStack(SpecialEventItems.SNACK_SILVER_COIN.get(), 50),
                new ItemStack(ModItems.FORGE_ENHANCE_3.get()), SingleItemChangePurchaseLimit.Type.DAILY, 2));
    }};

    public static void setVillagerTradeRecipe() {
        List<TradeListNew> list = new ArrayList<>() {{
            add(new TradeListNew(new ItemStack(SpecialEventItems.FRESH_WATER.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_0.get())
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.FRESH_WATER.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_MANA_CURIO_0.get())
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.FRESH_WATER.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_0.get())
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.FRESH_WATER.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_0.get())
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_1.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_0.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 3),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 20)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_2.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_1.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 6),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 40)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_3.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_ATTACK_CURIO_2.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 10),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 60),
                    new ItemStack(CrystalItems.YELLOW_CRYSTAL_PP.get(), 1)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SODA_MANA_CURIO_1.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_MANA_CURIO_0.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 3),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 20)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SODA_MANA_CURIO_2.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_MANA_CURIO_1.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 6),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 40)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SODA_MANA_CURIO_3.get()), List.of(
                    new ItemStack(SpecialEventItems.SODA_MANA_CURIO_2.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 10),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 60),
                    new ItemStack(CrystalItems.YELLOW_CRYSTAL_PP.get(), 1)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_1.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_0.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 3),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 20)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_2.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_1.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 6),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 40)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_3.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_DEFENCE_CURIO_2.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 10),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 60),
                    new ItemStack(CrystalItems.YELLOW_CRYSTAL_PP.get(), 1)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_1.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_0.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 3),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 20)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_2.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_1.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 6),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 40)
            )));
            add(new TradeListNew(new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_3.get()), List.of(
                    new ItemStack(SpecialEventItems.SHAKE_HEALTH_CURIO_2.get()),
                    new ItemStack(SpecialEventItems.FRESH_WATER.get(), 10),
                    new ItemStack(ModItems.GOLDEN_BEANS.get(), 60),
                    new ItemStack(CrystalItems.YELLOW_CRYSTAL_PP.get(), 1)
            )));
        }};
        TradeListNew.setVillagerData("暑期活动饰品升级", "summerCurio",
                CustomStyle.styleOfPower, VillagerType.SAVANNA, VillagerProfession.LIBRARIAN, list);
        MyVillagerData.setMyVillagerData(VILLAGER_NAME,
                "summer2025", CustomStyle.styleOfPower, VillagerType.SAVANNA,
                VillagerProfession.CARTOGRAPHER, List.of());
    }

    public static final String VILLAGER_NAME = "暑期活动兑换";
}
