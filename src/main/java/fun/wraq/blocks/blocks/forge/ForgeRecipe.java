package fun.wraq.blocks.blocks.forge;

import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeRecipe {
    public static Map<Item, List<ItemStack>> recipes = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public static void toolTip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        List<Component> tooltip = event.getToolTip();
        boolean flag1 = recipes.containsKey(item);
        boolean flag2 = ForgeEquipUtils.itemForgePlaceMap.containsKey(item);
        if (itemStack.getTagElement(Utils.MOD_ID) != null
                && itemStack.getTagElement(Utils.MOD_ID).contains(Illustrate.DISPLAY_FLAG)
                && recipes.containsKey(item) && ForgeEquipUtils.itemForgePlaceMap.containsKey(item)
                && !Screen.hasControlDown() && !Screen.hasShiftDown()) {
            if (!Screen.hasAltDown()) {
                event.getToolTip().add(Te.s(""));
                tooltip.add(Component.literal("[按下ALT查看锻造方式]").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfStone));
            } else {
                event.getToolTip().add(Te.s(""));
                tooltip.add(Te.s("->", ChatFormatting.GOLD, "锻造方式", CustomStyle.styleOfStone, "->", ChatFormatting.GOLD));
                List<Component> components = ForgeEquipUtils.itemForgePlaceMap.get(item);
                MutableComponent description = Te.s("于 ");
                for (int i = 0; i < components.size(); i++) {
                    Component component = components.get(i);
                    if (i != components.size() - 1) {
                        description.append(component).append("、");
                    } else {
                        description.append(component);
                    }
                }
                description.append(Te.s(" 锻造"));
                tooltip.add(description);
                tooltip.add(Component.literal(" 材料需求:").withStyle(CustomStyle.styleOfSky));
                List<ItemStack> list;
                if (item instanceof ForgeItem forgeItem) {
                    list = forgeItem.forgeRecipe();
                } else {
                    list = recipes.get(itemStack.getItem());
                }
                for (int i = 0; i < list.size(); i++) {
                    ItemStack material = list.get(i);
                    Item materialItem = material.getItem();
                    int playerInventoryHasNum = InventoryOperation.itemStackCount(Minecraft.getInstance().player, material.getItem());
                    if (playerInventoryHasNum >= material.getCount()) {
                        tooltip.add(Component.literal((i + 1) + ".").append(material.getDisplayName()).
                                append(Component.literal(" (")).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + material.getCount()).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("" + material.getCount()).withStyle(CustomStyle.styleOfMoon)).
                                append(Component.literal(")").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" √").withStyle(ChatFormatting.GREEN)));
                    } else {
                        tooltip.add(Component.literal((i + 1) + ".").append(material.getDisplayName()).
                                append(Component.literal(" (")).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + playerInventoryHasNum).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("" + material.getCount()).withStyle(CustomStyle.styleOfMoon)).
                                append(Component.literal(")").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" -").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.weaponList.contains(material.getItem()) || item instanceof WraqPickaxe) {
                        if ((Utils.mainHandTag.containsKey(materialItem) && Utils.mainHandTag.containsKey(item))
                                || (Utils.offHandTag.containsKey(materialItem) && Utils.offHandTag.containsKey(item))
                                || (Utils.armorTag.containsKey(materialItem) && Utils.armorTag.containsKey(item))) {
                            tooltip.add(Te.s("▲将保留强化等级/品质/宝石等信息", CustomStyle.styleOfGold));
                        }
                    }
                }
            }
        }
    }

    public static void forgeDrawRecipeInit() {
        recipes.put(ModItems.PLAIN_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
            add(new ItemStack(Items.LEATHER, 5));
        }});

        recipes.put(ModItems.PLAIN_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.PLAIN_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
            add(new ItemStack(Items.LEATHER, 7));
        }});

        recipes.put(ModItems.PLAIN_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
            add(new ItemStack(Items.LEATHER, 4));
        }});

        recipes.put(ModItems.PLAIN_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
        }});

        recipes.put(ModItems.PLAIN_BOW_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
        }});

        recipes.put(ModItems.LIFE_SCEPTRE_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PLAIN_RUNE.get(), 2));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 7));
        }});

        recipes.put(ModItems.FOREST_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FOREST_RUNE.get(), 3));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        recipes.put(ModItems.FOREST_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FOREST_RUNE.get(), 3));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        recipes.put(ModItems.FOREST_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FOREST_RUNE.get(), 3));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        recipes.put(ModItems.FOREST_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FOREST_RUNE.get(), 3));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.FOREST_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FOREST_RUNE.get(), 3));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14));
        }});

        recipes.put(ModItems.FOREST_BOW_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FOREST_RUNE.get(), 3));
            add(new ItemStack(ModItems.LIFE_ELEMENT_PIECE_0.get(), 14));
        }});

        recipes.put(ModItems.LAKE_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        recipes.put(ModItems.LAKE_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        recipes.put(ModItems.LAKE_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        recipes.put(ModItems.LAKE_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.LAKE_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
        }});

        recipes.put(ModItems.LAKE_BOW_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
        }});

        recipes.put(ModItems.LAKE_SCEPTRE_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LAKE_RUNE.get(), 3));
            add(new ItemStack(ModItems.WATER_ELEMENT_PIECE_0.get(), 14));
        }});

        recipes.put(ModItems.VOLCANO_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 3));
            add(new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        recipes.put(ModItems.VOLCANO_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 3));
            add(new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        recipes.put(ModItems.VOLCANO_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 3));
            add(new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        recipes.put(ModItems.VOLCANO_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 3));
            add(new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.VOLCANO_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 3));
            add(new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        recipes.put(ModItems.VOLCANO_BOW_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VOLCANO_RUNE.get(), 3));
            add(new ItemStack(ModItems.FIRE_ELEMENT_PIECE_0.get(), 14));
        }});

        recipes.put(ModItems.MINE_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MINE_RUNE.get(), 3));
            add(new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MINE_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MINE_RUNE.get(), 3));
            add(new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MINE_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MINE_RUNE.get(), 3));
            add(new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MINE_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MINE_RUNE.get(), 3));
            add(new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MINE_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MINE_RUNE.get(), 3));
            add(new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        recipes.put(ModItems.MINE_BOW_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MINE_RUNE.get(), 3));
            add(new ItemStack(ModItems.STONE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        recipes.put(ModItems.SNOW_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SNOW_RUNE.get(), 3));
            add(new ItemStack(ModItems.ICE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SNOW_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SNOW_RUNE.get(), 3));
            add(new ItemStack(ModItems.ICE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SNOW_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SNOW_RUNE.get(), 3));
            add(new ItemStack(ModItems.ICE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SNOW_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SNOW_RUNE.get(), 3));
            add(new ItemStack(ModItems.ICE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SNOW_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SNOW_RUNE.get(), 3));
            add(new ItemStack(ModItems.ICE_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        recipes.put(ModItems.SKY_BOW.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SKY_RUNE.get(), 8));
            add(new ItemStack(ModItems.WIND_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        recipes.put(ModItems.SKY_SWORD.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SKY_RUNE.get(), 8));
            add(new ItemStack(ModItems.WIND_ELEMENT_PIECE_0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        recipes.put(ModItems.SAKURA_SWORD.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SAKURA_PETAL.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 192));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SEA_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SEA_RUNE.get(), 16));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.HUSK_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.HUSK_RUNE.get(), 16));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.KAZE_SWORD_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KAZE_RUNE.get(), 8));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SAKURA_BOW.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SAKURA_PETAL.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 192));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.MINE_PANTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.WHEAT.get(), 64));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SEA_BOW.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SEA_RUNE.get(), 8));
            add(new ItemStack(ModItems.HUSK_RUNE.get(), 8));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SHIP_SWORD.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SHIP_PIECE.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 2));
        }});

        recipes.put(ModItems.SHIP_BOW.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SHIP_PIECE.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 2));
        }});

        recipes.put(ModItems.SHIP_SCEPTRE.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SHIP_PIECE.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 2));
        }});

        recipes.put(ModItems.MOON_SHIELD.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MANA_SHIELD.get(), 1));
            add(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 6));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }});

        recipes.put(ModItems.MOON_KNIFE.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MANA_KNIFE.get(), 1));
            add(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 6));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }});

        recipes.put(ModItems.MOON_BOOK.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.EARTH_BOOK.get(), 1));
            add(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 6));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }});

        recipes.put(ModItems.MOON_BELT.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 384));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 20));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 80));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 8));
        }});

        recipes.put(ModItems.STAR_BOTTLE.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.STAR_STAR.get(), 24));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 384));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 16));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 64));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 3));
        }});
    }
}
