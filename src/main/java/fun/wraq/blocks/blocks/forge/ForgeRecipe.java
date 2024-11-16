package fun.wraq.blocks.blocks.forge;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.ore.PickaxeItems;
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
    public static Map<Item, List<ItemStack>> forgeDrawRecipe = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public static void toolTip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        List<Component> tooltip = event.getToolTip();
        if (forgeDrawRecipe.containsKey(item)
                && ForgeEquipUtils.itemForgePlaceMap.containsKey(item)) {
            if (!Screen.hasAltDown()) {
                tooltip.add(Component.literal("[按下Alt查看锻造方式]").withStyle(CustomStyle.styleOfStone));
            } else {
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
                List<ItemStack> list = forgeDrawRecipe.get(itemStack.getItem());
                for (int i = 0; i < list.size(); i++) {
                    ItemStack stack = list.get(i);
                    int playerInventoryHasNum = InventoryOperation.itemStackCount(Minecraft.getInstance().player, stack.getItem());
                    if (playerInventoryHasNum >= stack.getCount()) {
                        tooltip.add(Component.literal((i + 1) + ".").append(stack.getDisplayName()).
                                append(Component.literal(" (")).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + stack.getCount()).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("" + stack.getCount()).withStyle(CustomStyle.styleOfMoon)).
                                append(Component.literal(")").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" √").withStyle(ChatFormatting.GREEN)));
                    } else {
                        tooltip.add(Component.literal((i + 1) + ".").append(stack.getDisplayName()).
                                append(Component.literal(" (")).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + playerInventoryHasNum).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("" + stack.getCount()).withStyle(CustomStyle.styleOfMoon)).
                                append(Component.literal(")").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" -").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.weaponList.contains(stack.getItem())) {
                        tooltip.add(Te.s("▲将保留强化等级/品质/宝石等信息", CustomStyle.styleOfGold));
                    }
                }
            }
        }
    }

    public static void forgeDrawRecipeInit() {
        forgeDrawRecipe.put(ModItems.PlainArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 5));
        }});

        forgeDrawRecipe.put(ModItems.PlainArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.PlainArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 7));
        }});

        forgeDrawRecipe.put(ModItems.PlainArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.LEATHER, 4));
        }});

        forgeDrawRecipe.put(ModItems.PlainSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
        }});

        forgeDrawRecipe.put(ModItems.PlainBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
        }});

        forgeDrawRecipe.put(ModItems.PlainSceptre0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.ForestSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
        }});

        forgeDrawRecipe.put(ModItems.ForestBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 3));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.LakeSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
        }});

        forgeDrawRecipe.put(ModItems.lakeBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
        }});

        forgeDrawRecipe.put(ModItems.lakeSceptre0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 3));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 3));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 4));
        }});

        forgeDrawRecipe.put(ModItems.MineSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        forgeDrawRecipe.put(ModItems.MineBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 3));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 4));
        }});

        forgeDrawRecipe.put(ModItems.SnowSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 3));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        forgeDrawRecipe.put(ModItems.SkyBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 10));
            add(new ItemStack(ModItems.WindElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        forgeDrawRecipe.put(ModItems.SakuraDemonSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 192));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 24));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SeaSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.huskSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.huskRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.KazeSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KazeRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SeaManaCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.BlackForestManaCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.huskRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.KazeManaCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KazeRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SakuraBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 192));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 24));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SakuraCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.MinePants.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.Wheat.get(), 64));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SeaBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 8));
            add(new ItemStack(ModItems.huskRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 24));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.IceSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.ShipSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.ShipBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.ShipSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.MoonShield.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ManaShield.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.goldCoin.get(), 288));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonKnife.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.manaKnife.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.goldCoin.get(), 288));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonBook.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.EarthBook.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.goldCoin.get(), 288));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 12));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.MoonBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.MoonSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.MoonBelt.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(PickaxeItems.TINKER_IRON.get(), 16));
            add(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.StarBottle.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.StarStar.get(), 24));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 16));
            add(new ItemStack(ModItems.ReputationMedal.get(), 64));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 3));
        }});
    }
}
