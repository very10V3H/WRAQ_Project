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
        if (itemStack.getTagElement(Utils.MOD_ID) != null
                && (itemStack.getTagElement(Utils.MOD_ID).contains(Illustrate.DISPLAY_FLAG))
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
        recipes.put(ModItems.PlainArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 5));
        }});

        recipes.put(ModItems.PlainArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.PlainArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 7));
        }});

        recipes.put(ModItems.PlainArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 4));
        }});

        recipes.put(ModItems.PlainSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
        }});

        recipes.put(ModItems.PlainBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
        }});

        recipes.put(ModItems.LIFE_SCEPTRE_0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
        }});

        recipes.put(ModItems.ForestArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        recipes.put(ModItems.ForestArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        recipes.put(ModItems.ForestArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        recipes.put(ModItems.ForestArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.ForestSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
        }});

        recipes.put(ModItems.ForestBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
        }});

        recipes.put(ModItems.LakeArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        recipes.put(ModItems.LakeArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        recipes.put(ModItems.LakeArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        recipes.put(ModItems.LakeArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.LakeSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
        }});

        recipes.put(ModItems.lakeBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
        }});

        recipes.put(ModItems.lakeSceptre0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
        }});

        recipes.put(ModItems.VolcanoArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        recipes.put(ModItems.VolcanoArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        recipes.put(ModItems.VolcanoArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        recipes.put(ModItems.VolcanoArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        recipes.put(ModItems.VolcanoSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        recipes.put(ModItems.VolcanoBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
        }});

        recipes.put(ModItems.MineArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MineArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MineArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MineArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        recipes.put(ModItems.MineSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        recipes.put(ModItems.MineBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        recipes.put(ModItems.SnowArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SnowArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SnowArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SnowArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        recipes.put(ModItems.SnowSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        recipes.put(ModItems.SkyBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 8));
            add(new ItemStack(ModItems.WindElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        recipes.put(ModItems.SKY_SWORD.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 8));
            add(new ItemStack(ModItems.WindElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        recipes.put(ModItems.SakuraDemonSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 192));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SeaSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 16));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.huskSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.HUSK_RUNE.get(), 16));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.KazeSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KazeRune.get(), 8));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SakuraBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 192));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.MinePants.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.Wheat.get(), 64));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.SeaBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 8));
            add(new ItemStack(ModItems.HUSK_RUNE.get(), 8));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 1));
        }});

        recipes.put(ModItems.ShipSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 2));
        }});

        recipes.put(ModItems.ShipBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 2));
        }});

        recipes.put(ModItems.ShipSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 256));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 2));
        }});

        recipes.put(ModItems.MoonShield.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ManaShield.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }});

        recipes.put(ModItems.MoonKnife.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.manaKnife.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }});

        recipes.put(ModItems.MoonBook.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.EarthBook.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }});

        recipes.put(ModItems.MoonBelt.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 384));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 8));
        }});

        recipes.put(ModItems.StarBottle.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.StarStar.get(), 24));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 384));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 16));
            add(new ItemStack(ModItems.ReputationMedal.get(), 64));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 3));
        }});
    }
}
