package fun.wraq.process.system.smelt;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public enum SmeltRecipe {

    COPPER(Te.m("冶炼粗铜", CustomStyle.styleOfCopper),
            List.of(new ItemStack(Items.RAW_COPPER, 8),
                    new ItemStack(Items.COAL, 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(Items.COPPER_INGOT, 8)),
            List.of(new ItemAndRate(new ItemStack(Items.COPPER_INGOT, 2), 0.5)),
            0),

    IRON(Te.m("冶炼粗铁", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.RAW_IRON, 8),
                    new ItemStack(Items.COAL, 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(Items.IRON_INGOT, 8)),
            List.of(new ItemAndRate(new ItemStack(Items.IRON_INGOT, 2), 0.5)),
            0),

    GOLD(Te.m("冶炼粗金", CustomStyle.styleOfGold),
            List.of(new ItemStack(Items.RAW_GOLD, 8),
                    new ItemStack(Items.COAL, 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(Items.GOLD_INGOT, 8)),
            List.of(new ItemAndRate(new ItemStack(Items.GOLD_INGOT, 2), 0.5)),
            0),

    TINKER_STONE(Te.m("工匠石", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.COBBLESTONE, 32),
                    new ItemStack(Items.COAL, 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(PickaxeItems.TINKER_STONE.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.COBBLESTONE, 8), 0.25)),
            5),

    TINKER_IRON(Te.m("工匠铁锭", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.IRON_INGOT, 32),
                    new ItemStack(Items.COAL, 2),
                    new ItemStack(ModItems.goldCoin.get(), 4)),
            List.of(new ItemStack(PickaxeItems.TINKER_IRON.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.IRON_INGOT, 8), 0.25)),
            10),

    TINKER_GOLD(Te.m("工匠金锭", CustomStyle.styleOfGold),
            List.of(new ItemStack(Items.GOLD_INGOT, 32),
                    new ItemStack(Items.COAL, 4),
                    new ItemStack(ModItems.goldCoin.get(), 8)),
            List.of(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.GOLD_INGOT, 8), 0.25)),
            20),

    TINKER_DIAMOND(Te.m("工匠钻石", CustomStyle.styleOfWorld),
            List.of(new ItemStack(Items.DIAMOND, 32),
                    new ItemStack(Items.COAL, 8),
                    new ItemStack(ModItems.goldCoin.get(), 16)),
            List.of(new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.DIAMOND, 8), 0.25)),
            35),

    TINKER_NETHERITE(Te.m("工匠合金", CustomStyle.styleOfRed),
            List.of(new ItemStack(Items.NETHERITE_INGOT, 32),
                    new ItemStack(Items.COAL, 16),
                    new ItemStack(ModItems.goldCoin.get(), 32)),
            List.of(new ItemStack(PickaxeItems.TINKER_NETHERITE.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.NETHERITE_INGOT, 8), 0.25)),
            60),

    PLAIN_COMPLETE_GEM(Te.m("普莱尼水晶", CustomStyle.styleOfPlain),
            List.of(new ItemStack(ModItems.PlainBossSoul.get(), 64),
                    new ItemStack(ModItems.completeGem.get(), 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.PlainCompleteGem.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.PlainCompleteGem.get(), 1), 0.25)),
            15),

    FOILED_NETHER_IMPRINT(Te.m("注魔燃魂印记", CustomStyle.styleOfNether),
            List.of(new ItemStack(ModItems.NETHER_IMPRINT.get(), 16),
                    new ItemStack(ModItems.EvokerSoul.get(), 64),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.FOILED_NETHER_IMPRINT.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.Ruby.get(), 16), 1)),
            30),

    PURPLE_IRON_BUD2(Te.m("紫晶矿簇", CustomStyle.styleOfPurpleIron),
            List.of(new ItemStack(ModItems.PurpleIronBud1.get(), 48),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.PurpleIronBud2.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.PurpleIronBud2.get(), 1), 0.5)),
            45),

    ICE_COMPLETE_GEM(Te.m("冰霜水晶", CustomStyle.styleOfIce),
            List.of(new ItemStack(ModItems.IceSoul.get(), 64),
                    new ItemStack(ModItems.completeGem.get(), 1),
                    new ItemStack(ModItems.goldCoin.get(), 1)),
            List.of(new ItemStack(ModItems.IceCompleteGem.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.IceCompleteGem.get(), 1), 0.25)),
            60),

    GOLDEN_SHEET(Te.m("大金板", CustomStyle.styleOfGold),
            List.of(new ItemStack(ModItems.Boss2Piece.get(), 32),
                    new ItemStack(ModItems.goldCoin.get(), 32)),
            List.of(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(ModItems.goldCoin.get(), 96), 0.5)),
            75),

    DEVIL_BLOOD(Te.m("魔王之血", CustomStyle.styleOfDemon),
            List.of(new ItemStack(ModItems.DevilAttackSoul.get(), 8),
                    new ItemStack(ModItems.DevilSwiftSoul.get(), 8),
                    new ItemStack(ModItems.DevilManaSoul.get(), 8),
                    new ItemStack(ModItems.goldCoin.get(), 16)),
            List.of(new ItemStack(ModItems.DevilBlood.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(ModItems.DevilBlood.get(), 1), 0.25)),
            90),

    MOON_COMPLETE_GEM(Te.m("尘月水晶", CustomStyle.styleOfMoon),
            List.of(new ItemStack(ModItems.MoonSoul.get(), 64),
                    new ItemStack(ModItems.completeGem.get(), 2),
                    new ItemStack(ModItems.goldCoin.get(), 16)),
            List.of(new ItemStack(ModItems.MoonCompleteGem.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.MoonCompleteGem.get(), 1), 0.25)),
            105),

    MOONTAIN_JADEITE(Te.m("望山翡翠", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.FRAGMENT.get(), 8),
                    new ItemStack(MoontainItems.SOUL_FRAGMENT.get(), 128),
                    new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 16),
                    new ItemStack(ModItems.goldCoin.get(), 32),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 256)
            ),
            List.of(new ItemStack(MoontainItems.JADEITE.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.JADEITE.get(), 1), 0.25)),
            15),

    MOONTAIN_WEAPON_ENHANCER(Te.m("望山剑翎", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.JADEITE.get(), 8),
                    new ItemStack(MoontainItems.SOUL_FRAGMENT.get(), 128),
                    new ItemStack(MoontainItems.FEATHER.get(), 256),
                    new ItemStack(ModItems.goldCoin.get(), 128),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 256)
            ),
            List.of(new ItemStack(MoontainItems.WEAPON_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.WEAPON_ENHANCER.get(), 1), 0.25)),
            60),

    MOONTAIN_EMERALD(Te.m("望山碧玉", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.NUGGET.get(), 8),
                    new ItemStack(MoontainItems.LEATHER.get(), 128),
                    new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 16),
                    new ItemStack(ModItems.goldCoin.get(), 32),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 256)
            ),
            List.of(new ItemStack(MoontainItems.EMERALD.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.EMERALD.get(), 1), 0.25)),
            15),

    MOONTAIN_ARMOR_ENHANCER(Te.m("望山翡玉", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.EMERALD.get(), 8),
                    new ItemStack(MoontainItems.SOUL_FRAGMENT.get(), 128),
                    new ItemStack(OreItems.MOONTAIN_ORE_ITEM.get(), 256),
                    new ItemStack(ModItems.goldCoin.get(), 128),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 256)
            ),
            List.of(new ItemStack(MoontainItems.ARMOR_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.ARMOR_ENHANCER.get(), 1), 0.25)),
            60),

    MOONTAIN_CURIOS_RATE_ENHANCER(Te.m("望山母岩团", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.CURIOS_PIECE.get(), 4),
                    new ItemStack(MoontainItems.FALLING_SOUL.get(), 128),
                    new ItemStack(ModItems.goldCoin.get(), 128),
                    new ItemStack(ModItems.completeGem.get(), 4),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)
            ),
            List.of(new ItemStack(MoontainItems.CURIOS_RATE_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.CURIOS_RATE_ENHANCER.get(), 1), 0.25)),
            60),

    MOONTAIN_CURIOS_FULL_RATE_ENHANCER(Te.m("望山母岩簇", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.CURIOS_PIECE.get(), 8),
                    new ItemStack(MoontainItems.FALLING_SOUL.get(), 256),
                    new ItemStack(ModItems.goldCoin.get(), 256),
                    new ItemStack(ModItems.completeGem.get(), 8),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 256)
            ),
            List.of(new ItemStack(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get(), 1), 0.25)),
            60),

    MOONTAIN_HEART(Te.m("衡望山之心", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.JADEITE.get(), 1),
                    new ItemStack(MoontainItems.EMERALD.get(), 1),
                    new ItemStack(ModItems.goldCoin.get(), 64),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)),
            List.of(new ItemStack(MoontainItems.HEART.get(), 2)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.JADEITE.get(), 1), 0.25),
                    new ItemAndRate(new ItemStack(MoontainItems.EMERALD.get(), 1), 0.25)),
            30);

    public final Component name;
    public final List<ItemStack> needMaterialList;
    public final List<ItemStack> productList;
    public final List<ItemAndRate> exProductList;
    public final int needMinutes;

    SmeltRecipe(Component name, List<ItemStack> needMaterialList, List<ItemStack> productList,
                List<ItemAndRate> exProductList, int needMinutes) {
        this.name = name;
        this.needMaterialList = needMaterialList;
        this.productList = productList;
        this.exProductList = exProductList;
        this.needMinutes = needMinutes;
    }

    public static SmeltRecipe getRecipeByIndex(int index) {
        return SmeltRecipe.values()[index];
    }

    public static void createSmeltProcess(Player player, int recipeIndex) {
        SmeltRecipe smeltRecipe = getRecipeByIndex(recipeIndex);
        if (InventoryOperation.checkPlayerHasItem(player, smeltRecipe.needMaterialList)) {
            if (Smelt.putSlotInfoToEmptySlot(player, recipeIndex, getMinutesLaterCalendar(smeltRecipe.needMinutes))) {
                // 成功
                InventoryOperation.removeItemWithoutCheck(player, smeltRecipe.needMaterialList);
                MySound.soundToPlayer(player, SoundEvents.ITEM_FRAME_ADD_ITEM);
            } else {
                // 失败
                // 发包至客户端 在screen显示信息

            }
        } else {
            // 背包中所需材料不足
        }
    }

    public static Calendar getMinutesLaterCalendar(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar;
    }

    public List<Component> getDescription() {
        List<Component> description = new ArrayList<>();
        description.add(Te.m("").append(name));
        description.add(Te.m(" 所需时间: ", ChatFormatting.AQUA).append(Te.m(needMinutes + "min")));
        description.add(Te.m(""));
        description.add(Component.literal(" 产物:").withStyle(ChatFormatting.YELLOW));
        productList.forEach(stack -> {
            description.add(Te.m("  ").append(stack.getDisplayName()).append(Te.m(" * " + stack.getCount(), ChatFormatting.AQUA)));
        });
        description.add(Te.m(""));
        description.add(Te.m(" 需求材料:", ChatFormatting.GREEN));
        needMaterialList.forEach(stack -> {
            description.add(Te.m("  ").append(stack.getDisplayName()).append(Te.m(" * " + stack.getCount(), ChatFormatting.AQUA)));
        });
        if (!exProductList.isEmpty()) {
            description.add(Te.m(""));
            description.add(Te.m(" 副产物表:", ChatFormatting.AQUA));
            exProductList.forEach(itemAndRate -> {
                description.add(Te.m("  ").append(itemAndRate.getItemStack().getDisplayName())
                        .append(Te.m(" * " + itemAndRate.getItemStack().getCount(), ChatFormatting.AQUA))
                        .append(Te.m(" [", ChatFormatting.AQUA))
                        .append(Te.m(String.format("%.0f%%", itemAndRate.getRate() * 100)))
                        .append(Te.m("]", ChatFormatting.AQUA)));
            });
        }
        return description;
    }
}
