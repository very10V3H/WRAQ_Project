package fun.wraq.series.overworld.newarea;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.gems.WraqGem;
import fun.wraq.series.overworld.newarea.stone.StoneSpiderKnife;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class NewAreaItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> STONE_SPIDER_PIECE = ITEMS.register("stone_spider_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Stone)));
    public static final RegistryObject<Item> STONE_SPIDER_RUNE = ITEMS.register("stone_spider_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.StoneBold), true, true));

    public static final RegistryObject<Item> STONE_SPIDER_KNIFE_0 = ITEMS.register("stone_spider_knife_0",
            () -> new StoneSpiderKnife(new Item.Properties().rarity(CustomStyle.StoneItalic), 0));
    public static final RegistryObject<Item> STONE_SPIDER_KNIFE_1 = ITEMS.register("stone_spider_knife_1",
            () -> new StoneSpiderKnife(new Item.Properties().rarity(CustomStyle.StoneItalic), 1));
    public static final RegistryObject<Item> STONE_SPIDER_KNIFE_2 = ITEMS.register("stone_spider_knife_2",
            () -> new StoneSpiderKnife(new Item.Properties().rarity(CustomStyle.StoneItalic), 2));
    public static final RegistryObject<Item> STONE_SPIDER_KNIFE_3 = ITEMS.register("stone_spider_knife_3",
            () -> new StoneSpiderKnife(new Item.Properties().rarity(CustomStyle.StoneItalic), 3));

    public static final RegistryObject<Item> STONE_SPIDER_GEM_ATTACK_0
            = ITEMS.register("stone_spider_gem_attack_0",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.StoneBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 20));
            }}, CustomStyle.styleOfStone, Te.s("锋石锯齿", CustomStyle.styleOfStone),
                    ComponentUtils.getSuffixOfStoneSpider()));
    public static final RegistryObject<Item> STONE_SPIDER_GEM_ATTACK_1
            = ITEMS.register("stone_spider_gem_attack_1",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.StoneBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 30));
            }}, CustomStyle.styleOfStone, Te.s("锋石锯齿", CustomStyle.styleOfStone),
                    ComponentUtils.getSuffixOfStoneSpider()));
    public static final RegistryObject<Item> STONE_SPIDER_GEM_ATTACK_2
            = ITEMS.register("stone_spider_gem_attack_2",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.StoneBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.defencePenetration0, 40));
            }}, CustomStyle.styleOfStone, Te.s("锋石锯齿", CustomStyle.styleOfStone),
                    ComponentUtils.getSuffixOfStoneSpider()));

    public static final RegistryObject<Item> STONE_SPIDER_GEM_MANA_0
            = ITEMS.register("stone_spider_gem_mana_0",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.StoneBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 20));
            }}, CustomStyle.styleOfStone, Te.s("锋石锯齿", CustomStyle.styleOfStone),
                    ComponentUtils.getSuffixOfStoneSpider()));
    public static final RegistryObject<Item> STONE_SPIDER_GEM_MANA_1
            = ITEMS.register("stone_spider_gem_mana_1",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.StoneBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 30));
            }}, CustomStyle.styleOfStone, Te.s("锋石锯齿", CustomStyle.styleOfStone),
                    ComponentUtils.getSuffixOfStoneSpider()));
    public static final RegistryObject<Item> STONE_SPIDER_GEM_MANA_2
            = ITEMS.register("stone_spider_gem_mana_2",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.StoneBold), new ArrayList<>() {{
                add(new WraqGem.AttributeMapValue(Utils.manaPenetration0, 40));
            }}, CustomStyle.styleOfStone, Te.s("锋石锯齿", CustomStyle.styleOfStone),
                    ComponentUtils.getSuffixOfStoneSpider()));

    public static final RegistryObject<Item> ORCHARD_FRUIT = ITEMS.register("orchard_fruit",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ORCHARD_RARITY)));

    public static final RegistryObject<Item> PRIMEVAL_FLUORITE = ITEMS.register("primeval_fluorite",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PRIMEVAL_RARITY)));
}
