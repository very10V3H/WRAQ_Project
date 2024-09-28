package fun.wraq.blocks.blocks.forge;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
        List<Component> tooltip = event.getToolTip();
        if (forgeDrawRecipe.containsKey(itemStack.getItem())) {
            if (!Screen.hasShiftDown()) {
                tooltip.add(Component.literal("[按下shift查看获取方式]").withStyle(ChatFormatting.GRAY));
            } else {
                if (ForgeEquipUtils.itemZoneMap.containsKey(itemStack.getItem())) {
                    tooltip.add(Component.literal("于").withStyle(ChatFormatting.WHITE).
                            append(ForgeEquipUtils.itemZoneMap.get(itemStack.getItem())).
                            append(Component.literal("锻造").withStyle(ChatFormatting.WHITE)));
                }
                tooltip.add(Component.literal(" 材料需求:").withStyle(CustomStyle.styleOfSky));
                List<ItemStack> list = forgeDrawRecipe.get(itemStack.getItem());
                for (int i = 0 ; i < list.size() ; i++) {
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
                        tooltip.add(Component.literal(" 将保留强化/宝石/熟练度等信息").withStyle(ChatFormatting.GOLD));
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
            add(new ItemStack(Items.IRON_INGOT, 1));
        }});

        forgeDrawRecipe.put(ModItems.PlainBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.IRON_INGOT, 1));
        }});

        forgeDrawRecipe.put(ModItems.PlainSceptre0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PlainRune.get(), 2));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 7));
            add(new ItemStack(Items.IRON_INGOT, 1));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 4));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 4));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 4));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        forgeDrawRecipe.put(ModItems.ForestArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 4));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.ForestSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 4));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.ForestBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestRune.get(), 4));
            add(new ItemStack(ModItems.LifeElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        forgeDrawRecipe.put(ModItems.LakeArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.LakeSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.lakeBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.lakeSceptre0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeRune.get(), 4));
            add(new ItemStack(ModItems.WaterElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 4));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 10));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 4));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 16));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 4));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 14));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 4));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.LEATHER, 8));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 4));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoRune.get(), 4));
            add(new ItemStack(ModItems.FireElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 4));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 10));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 4));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 16));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 4));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 14));
        }});

        forgeDrawRecipe.put(ModItems.MineArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 4));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 8));
        }});

        forgeDrawRecipe.put(ModItems.MineSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 4));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.MineBow0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MineRune.get(), 4));
            add(new ItemStack(ModItems.StoneElementPiece0.get(), 14));
            add(new ItemStack(Items.IRON_INGOT, 2));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 4));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 10));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 4));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 16));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 4));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 14));
        }});

        forgeDrawRecipe.put(ModItems.SnowArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 4));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 8));
        }});

        forgeDrawRecipe.put(ModItems.SnowSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowRune.get(), 4));
            add(new ItemStack(ModItems.IceElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 2));
        }});

        forgeDrawRecipe.put(ModItems.SkyBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 10));
            add(new ItemStack(ModItems.WindElementPiece0.get(), 14));
            add(new ItemStack(Items.DIAMOND, 16));
        }});

        forgeDrawRecipe.put(ModItems.SakuraDemonSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 192));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 24));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.LIGHTNING_HELMET.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LightningRune.get(), 10));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.LIGHTNING_CHEST.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LightningRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.LIGHTNING_LEGGINGS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LightningRune.get(), 14));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.LIGHTNING_BOOTS.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LightningRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SkyArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SkyArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SkyArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SkyArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SeaSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.huskSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.huskRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.WitherRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.NetherSkeletonRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MagmaRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PiglinRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.KazeSword0.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KazeRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.KazeBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KazeRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
            add(new ItemStack(ModItems.completeGem.get(), 3));
            add(new ItemStack(ModItems.ReputationMedal.get(), 12));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.ForestBossSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ForestBossCore.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.VolcanoBossSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.VolcanoBossCore.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.LakeBossSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LakeBossCore.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SkyBossBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SkyBossCore.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SnowBossArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SnowBossCore.get(), 64));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningIron.get(), 2));
            add(new ItemStack(ModItems.RefiningCopper.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SeaManaCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.BlackForestManaCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.huskRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.KazeManaCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.KazeRune.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SakuraBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 192));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 24));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.SakuraCore.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SakuraPetal.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.MinePants.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.Wheat.get(), 64));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.IceArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 4));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningIron.get(), 2));
            add(new ItemStack(ModItems.RefiningCopper.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 4));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningIron.get(), 2));
            add(new ItemStack(ModItems.RefiningCopper.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 4));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningIron.get(), 2));
            add(new ItemStack(ModItems.RefiningCopper.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 4));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningIron.get(), 2));
            add(new ItemStack(ModItems.RefiningCopper.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SeaBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SeaRune.get(), 8));
            add(new ItemStack(ModItems.huskRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 24));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.IceSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.IceSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.IceCompleteGem.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.ShipSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.ShipBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.ShipSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ShipPiece.get(), 576));
            add(new ItemStack(ModItems.goldCoin.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningGold.get(), 2));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.NetherManaArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.WitherRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 5));
            add(new ItemStack(ModItems.ReputationMedal.get(), 20));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherManaArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.NetherSkeletonRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 5));
            add(new ItemStack(ModItems.ReputationMedal.get(), 20));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherManaArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MagmaRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 5));
            add(new ItemStack(ModItems.ReputationMedal.get(), 20));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherManaArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.PiglinRune.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 5));
            add(new ItemStack(ModItems.ReputationMedal.get(), 20));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 1));
        }});

        forgeDrawRecipe.put(ModItems.NetherSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.NetherSoul.get(), 32));
            add(new ItemStack(ModItems.goldCoin.get(), 192));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.NetherRune.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringAttackArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringAttackArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringAttackArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringAttackArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringSwiftArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringSwiftArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringSwiftArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringSwiftArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringManaArmorHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringManaArmorChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringManaArmorLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});

        forgeDrawRecipe.put(ModItems.SpringManaArmorBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.SpringHeart.get(), 8));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 36));
            add(new ItemStack(ModItems.ReputationMedal.get(), 144));
            add(new ItemStack(ModItems.RefiningIron.get(), 3));
            add(new ItemStack(ModItems.RefiningCopper.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 2));
        }});


        forgeDrawRecipe.put(ModItems.DevilAttackChest.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.DevilAttackSoul.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 16));
            add(new ItemStack(ModItems.ReputationMedal.get(), 64));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.DevilSwiftBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.DevilSwiftSoul.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 16));
            add(new ItemStack(ModItems.ReputationMedal.get(), 64));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.DevilManaHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.DevilManaSoul.get(), 128));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 16));
            add(new ItemStack(ModItems.ReputationMedal.get(), 64));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonShield.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ManaShield.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.goldCoin.get(), 288));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonKnife.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.manaKnife.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.goldCoin.get(), 288));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonBook.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.EarthBook.get(), 1));
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 6));
            add(new ItemStack(ModItems.goldCoin.get(), 288));
            add(new ItemStack(ModItems.completeGem.get(), 12));
            add(new ItemStack(ModItems.ReputationMedal.get(), 48));
            add(new ItemStack(ModItems.RefiningIron.get(), 4));
            add(new ItemStack(ModItems.RefiningCopper.get(), 4));
            add(new ItemStack(ModItems.WorldSoul3.get(), 4));
        }});

        forgeDrawRecipe.put(ModItems.MoonLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 12));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }});

        forgeDrawRecipe.put(ModItems.MoonSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.MoonBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.MoonSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.MoonBelt.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }});

        forgeDrawRecipe.put(ModItems.TabooAttackLeggings.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ConstrainTaboo.get(), 1));
            add(new ItemStack(ModItems.PurpleIronArmorLeggings.get(), 1));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }});

        forgeDrawRecipe.put(ModItems.TabooSwiftHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ConstrainTaboo.get(), 1));
            add(new ItemStack(ModItems.PurpleIronArmorHelmet.get(), 1));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }});

        forgeDrawRecipe.put(ModItems.TabooManaBoots.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.ConstrainTaboo.get(), 1));
            add(new ItemStack(ModItems.PurpleIronArmorBoots.get(), 1));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }});

        forgeDrawRecipe.put(ModItems.MoonHelmet.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.MoonCompleteGem.get(), 12));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 20));
            add(new ItemStack(ModItems.ReputationMedal.get(), 80));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }});

        forgeDrawRecipe.put(ModItems.StarBottle.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.StarStar.get(), 24));
            add(new ItemStack(ModItems.goldCoin.get(), 384));
            add(new ItemStack(ModItems.completeGem.get(), 16));
            add(new ItemStack(ModItems.ReputationMedal.get(), 64));
            add(new ItemStack(ModItems.RefiningGold.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 3));
        }});

        forgeDrawRecipe.put(ModItems.LifeElementSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LifeElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.LifeElementBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LifeElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.LifeElementSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.LifeElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.WaterElementSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.WaterElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.WaterElementBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.WaterElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.WaterElementSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.WaterElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.FireElementSword.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FireElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.FireElementBow.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FireElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.FireElementSceptre.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.FireElementPiece2.get(), 21));
            add(new ItemStack(ModItems.goldCoin.get(), 448));
            add(new ItemStack(ModItems.completeGem.get(), 100));
            add(new ItemStack(ModItems.ReputationMedal.get(), 400));
            add(new ItemStack(ModItems.RefiningGold.get(), 12));
            add(new ItemStack(ModItems.WorldSoul3.get(), 12));
        }});

        forgeDrawRecipe.put(ModItems.EndCurios.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.EndCrystal.get(), 12));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningGold.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 3));
        }});

        forgeDrawRecipe.put(ModItems.EndCurios1.get(), new ArrayList<>() {{
            add(new ItemStack(ModItems.EndCrystal.get(), 12));
            add(new ItemStack(ModItems.goldCoin.get(), 320));
            add(new ItemStack(ModItems.completeGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 32));
            add(new ItemStack(ModItems.RefiningGold.get(), 3));
            add(new ItemStack(ModItems.WorldSoul3.get(), 3));
        }});
    }
}
