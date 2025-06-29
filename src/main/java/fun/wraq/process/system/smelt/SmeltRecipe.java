package fun.wraq.process.system.smelt;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.ComsumableItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
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
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(Items.COPPER_INGOT, 8)),
            List.of(new ItemAndRate(new ItemStack(Items.COPPER_INGOT, 2), 0.5)),
            0),
    IRON(Te.m("冶炼粗铁", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.RAW_IRON, 8),
                    new ItemStack(Items.COAL, 1),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(Items.IRON_INGOT, 8)),
            List.of(new ItemAndRate(new ItemStack(Items.IRON_INGOT, 2), 0.5)),
            0),
    GOLD(Te.m("冶炼粗金", CustomStyle.styleOfGold),
            List.of(new ItemStack(Items.RAW_GOLD, 8),
                    new ItemStack(Items.COAL, 1),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(Items.GOLD_INGOT, 8)),
            List.of(new ItemAndRate(new ItemStack(Items.GOLD_INGOT, 2), 0.5)),
            0),

    TINKER_STONE(Te.m("工匠石", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.COBBLESTONE, 32),
                    new ItemStack(Items.COAL, 2),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(PickaxeItems.TINKER_STONE.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.COBBLESTONE, 8), 0.25)),
            5),
    TINKER_COPPER(Te.m("工匠铜锭", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.COPPER_INGOT, 32),
                    new ItemStack(Items.COAL, 4),
                    new ItemStack(ModItems.GOLD_COIN.get(), 2)),
            List.of(new ItemStack(PickaxeItems.TINKER_COPPER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.COPPER_INGOT, 8), 0.25)),
            8),
    TINKER_IRON(Te.m("工匠铁锭", CustomStyle.styleOfMine),
            List.of(new ItemStack(Items.IRON_INGOT, 32),
                    new ItemStack(Items.COAL, 8),
                    new ItemStack(ModItems.GOLD_COIN.get(), 4)),
            List.of(new ItemStack(PickaxeItems.TINKER_IRON.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.IRON_INGOT, 8), 0.25)),
            10),
    TINKER_GOLD(Te.m("工匠金锭", CustomStyle.styleOfGold),
            List.of(new ItemStack(Items.GOLD_INGOT, 32),
                    new ItemStack(Items.COAL, 16),
                    new ItemStack(ModItems.GOLD_COIN.get(), 8)),
            List.of(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.GOLD_INGOT, 8), 0.25)),
            20),
    TINKER_GOLD_EX(Te.m("工匠金锭-EX", CustomStyle.styleOfGold),
            List.of(new ItemStack(Items.GOLD_NUGGET, 288),
                    new ItemStack(Items.COAL, 8),
                    new ItemStack(ModItems.GOLD_COIN.get(), 8)),
            List.of(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.GOLD_INGOT, 8), 0.25)),
            20),
    TINKER_DIAMOND(Te.m("工匠钻石", CustomStyle.styleOfWorld),
            List.of(new ItemStack(Items.DIAMOND, 32),
                    new ItemStack(Items.COAL, 64),
                    new ItemStack(ModItems.GOLD_COIN.get(), 16)),
            List.of(new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.DIAMOND, 8), 0.25)),
            35),
    TINKER_NETHERITE(Te.m("工匠合金", CustomStyle.styleOfRed),
            List.of(new ItemStack(Items.NETHERITE_INGOT, 32),
                    new ItemStack(Items.QUARTZ, 128),
                    new ItemStack(ModItems.GOLD_COIN.get(), 32)),
            List.of(new ItemStack(PickaxeItems.TINKER_NETHERITE.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(Items.NETHERITE_INGOT, 8), 0.25)),
            60),

    PLAIN_COMPLETE_GEM(Te.m("普莱尼水晶", CustomStyle.styleOfPlain),
            List.of(new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 64),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(ModItems.PLAIN_COMPLETE_GEM.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.PLAIN_COMPLETE_GEM.get(), 1), 0.25)),
            15),
    FOILED_NETHER_IMPRINT(Te.m("注魔燃魂印记", CustomStyle.styleOfNether),
            List.of(new ItemStack(ModItems.NETHER_IMPRINT.get(), 16),
                    new ItemStack(ModItems.EVOKER_SOUL.get(), 64),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(ModItems.FOILED_NETHER_IMPRINT.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.RUBY.get(), 16), 1)),
            30),
    PURPLE_IRON_BUD2(Te.m("紫晶矿簇", CustomStyle.styleOfPurpleIron),
            List.of(new ItemStack(ModItems.PURPLE_IRON_BUD_1.get(), 48),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(ModItems.PURPLE_IRON_BUD_2.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.PURPLE_IRON_BUD_2.get(), 1), 0.5)),
            45),
    ICE_COMPLETE_GEM(Te.m("冰霜水晶", CustomStyle.styleOfIce),
            List.of(new ItemStack(ModItems.ICE_BOSS_SOUL.get(), 64),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                    new ItemStack(ModItems.GOLD_COIN.get(), 1)),
            List.of(new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 1), 0.25)),
            60),
    GOLDEN_SHEET(Te.m("大金板", CustomStyle.styleOfGold),
            List.of(new ItemStack(ModItems.Boss2Piece.get(), 16),
                    new ItemStack(ModItems.GOLD_COIN.get(), 16)),
            List.of(new ItemStack(ModItems.GOLDEN_SHEET.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(ModItems.GOLD_COIN.get(), 48), 0.5)),
            75),
    DEVIL_BLOOD(Te.m("魔王之血", CustomStyle.styleOfDemon),
            List.of(new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 8),
                    new ItemStack(ModItems.DEVIL_SWIFT_SOUL.get(), 8),
                    new ItemStack(ModItems.DEVIL_MANA_SOUL.get(), 8),
                    new ItemStack(ModItems.GOLD_COIN.get(), 16)),
            List.of(new ItemStack(ModItems.DEVIL_BLOOD.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(ModItems.DEVIL_BLOOD.get(), 1), 0.25)),
            90),
    MOON_COMPLETE_GEM(Te.m("尘月水晶", CustomStyle.styleOfMoon),
            List.of(new ItemStack(ModItems.MOON_SOUL.get(), 64),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 2),
                    new ItemStack(ModItems.GOLD_COIN.get(), 16)),
            List.of(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 4)),
            List.of(new ItemAndRate(new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 1), 0.25)),
            105),

    MOONTAIN_JADEITE(Te.m("望山翠玉", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.FRAGMENT.get(), 8),
                    new ItemStack(MoontainItems.SOUL_FRAGMENT.get(), 64),
                    new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 16),
                    new ItemStack(ModItems.GOLD_COIN.get(), 32),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)
            ),
            List.of(new ItemStack(MoontainItems.JADEITE.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.JADEITE.get(), 1), 0.25)),
            15),
    MOONTAIN_WEAPON_ENHANCER(Te.m("望山剑翎", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.JADEITE.get(), 2),
                    new ItemStack(MoontainItems.SOUL_FRAGMENT.get(), 64),
                    new ItemStack(MoontainItems.FEATHER.get(), 64),
                    new ItemStack(ModItems.GOLD_COIN.get(), 128),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)
            ),
            List.of(new ItemStack(MoontainItems.WEAPON_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.WEAPON_ENHANCER.get(), 1), 0.25)),
            60),
    MOONTAIN_EMERALD(Te.m("望山碧玉", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.NUGGET.get(), 8),
                    new ItemStack(MoontainItems.LEATHER.get(), 64),
                    new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 16),
                    new ItemStack(ModItems.GOLD_COIN.get(), 32),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)
            ),
            List.of(new ItemStack(MoontainItems.EMERALD.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.EMERALD.get(), 1), 0.25)),
            15),
    MOONTAIN_ARMOR_ENHANCER(Te.m("望山翡玉", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.EMERALD.get(), 2),
                    new ItemStack(MoontainItems.SOUL_FRAGMENT.get(), 64),
                    new ItemStack(OreItems.MOONTAIN_ORE_ITEM.get(), 64),
                    new ItemStack(ModItems.GOLD_COIN.get(), 128),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)
            ),
            List.of(new ItemStack(MoontainItems.ARMOR_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.ARMOR_ENHANCER.get(), 1), 0.25)),
            60),
    MOONTAIN_CURIOS_RATE_ENHANCER(Te.m("望山母岩团", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.CURIOS_PIECE.get(), 4),
                    new ItemStack(MoontainItems.FALLING_SOUL.get(), 64),
                    new ItemStack(ModItems.GOLD_COIN.get(), 128),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 4),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)
            ),
            List.of(new ItemStack(MoontainItems.CURIOS_RATE_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.CURIOS_RATE_ENHANCER.get(), 1), 0.25)),
            60),
    MOONTAIN_CURIOS_FULL_RATE_ENHANCER(Te.m("望山母岩簇", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.CURIOS_PIECE.get(), 8),
                    new ItemStack(MoontainItems.FALLING_SOUL.get(), 128),
                    new ItemStack(ModItems.GOLD_COIN.get(), 256),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 8),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 256)
            ),
            List.of(new ItemStack(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get(), 1)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get(), 1), 0.25)),
            60),
    MOONTAIN_HEART(Te.m("衡望山之心", CustomStyle.styleOfMoontain),
            List.of(new ItemStack(MoontainItems.JADEITE.get(), 1),
                    new ItemStack(MoontainItems.EMERALD.get(), 1),
                    new ItemStack(ModItems.GOLD_COIN.get(), 64),
                    new ItemStack(MoontainItems.STONE_FRAGMENT.get(), 128)),
            List.of(new ItemStack(MoontainItems.HEART.get(), 2)),
            List.of(new ItemAndRate(new ItemStack(MoontainItems.JADEITE.get(), 1), 0.25),
                    new ItemAndRate(new ItemStack(MoontainItems.EMERALD.get(), 1), 0.25)),
            30),

    HEAT_INJECTION_0(Te.s(ComsumableItems.HEAT_INJECTION_0.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 20),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 10),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 10),
                    new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 8)),
            List.of(new ItemStack(ComsumableItems.HEAT_INJECTION_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_INJECTION_0.get()), 0.25)),
            90),
    HEAT_INJECTION_1(Te.s(ComsumableItems.HEAT_INJECTION_1.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 40),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 20),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 20),
                    new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 16)),
            List.of(new ItemStack(ComsumableItems.HEAT_INJECTION_1.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_INJECTION_1.get()), 0.25)),
            120),
    HEAT_INJECTION_2(Te.s(ComsumableItems.HEAT_INJECTION_2.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 80),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 40),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 40),
                    new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 32)),
            List.of(new ItemStack(ComsumableItems.HEAT_INJECTION_2.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_INJECTION_2.get()), 0.25)),
            150),
    HEAT_INJECTION_3(Te.s(ComsumableItems.HEAT_INJECTION_3.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 160),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 80),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 80),
                    new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 64)),
            List.of(new ItemStack(ComsumableItems.HEAT_INJECTION_3.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_INJECTION_3.get()), 0.25)),
            180),

    HEAT_DEVICE_0(Te.s(ComsumableItems.HEAT_DEVICE_0.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 20),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 10),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 10)),
            List.of(new ItemStack(ComsumableItems.HEAT_DEVICE_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_DEVICE_0.get()), 0.25)),
            60),
    HEAT_DEVICE_1(Te.s(ComsumableItems.HEAT_DEVICE_1.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 30),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 15),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 15)),
            List.of(new ItemStack(ComsumableItems.HEAT_DEVICE_1.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_DEVICE_1.get()), 0.25)),
            90),
    HEAT_DEVICE_2(Te.s(ComsumableItems.HEAT_DEVICE_2.get()),
            List.of(new ItemStack(ModItems.VOLCANO_RUNE.get(), 40),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 20),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 20),
                    new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 16)),
            List.of(new ItemStack(ComsumableItems.HEAT_DEVICE_2.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.HEAT_DEVICE_2.get()), 0.25)),
            120),

    WHETSTONE_ATTACK_0(Te.s(ComsumableItems.WHETSTONE_ATTACK_0.get()),
            List.of(new ItemStack(C7Items.BONE_IMP_SOUL.get(), 240),
                    new ItemStack(ModItems.PURPLE_IRON_PIECE.get(), 300),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.WHETSTONE_ATTACK_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.WHETSTONE_ATTACK_0.get()), 0.25)),
            60),
    WHETSTONE_PENETRATION_0(Te.s(ComsumableItems.WHETSTONE_PENETRATION_0.get()),
            List.of(new ItemStack(C7Items.BONE_IMP_SOUL.get(), 240),
                    new ItemStack(ModItems.MINE_SOUL.get(), 240),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.WHETSTONE_PENETRATION_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.WHETSTONE_PENETRATION_0.get()), 0.25)),
            60),
    WHETSTONE_PENETRATION0_0(Te.s(ComsumableItems.WHETSTONE_PENETRATION0_0.get()),
            List.of(new ItemStack(C7Items.BONE_IMP_SOUL.get(), 240),
                    new ItemStack(ModItems.SNOW_SOUL.get(), 240),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.WHETSTONE_PENETRATION0_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.WHETSTONE_PENETRATION0_0.get()), 0.25)),
            60),

    QUIVER_ATTACK_0(Te.s(ComsumableItems.QUIVER_ATTACK_0.get()),
            List.of(new ItemStack(C7Items.VD_SOUL.get(), 30),
                    new ItemStack(ModItems.BEACON_SOUL.get(), 300),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.QUIVER_ATTACK_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.QUIVER_ATTACK_0.get()), 0.25)),
            60),
    QUIVER_PENETRATION_0(Te.s(ComsumableItems.QUIVER_PENETRATION_0.get()),
            List.of(new ItemStack(C7Items.VD_SOUL.get(), 30),
                    new ItemStack(ModItems.SHIP_PIECE.get(), 240),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.QUIVER_PENETRATION_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.QUIVER_PENETRATION_0.get()), 0.25)),
            60),
    QUIVER_PENETRATION0_0(Te.s(ComsumableItems.QUIVER_PENETRATION0_0.get()),
            List.of(new ItemStack(C7Items.VD_SOUL.get(), 30),
                    new ItemStack(ModItems.KAZE_SOUL.get(), 240),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.QUIVER_PENETRATION0_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.QUIVER_PENETRATION0_0.get()), 0.25)),
            60),

    MIXTURE_ATTACK_0(Te.s(ComsumableItems.MIXTURE_ATTACK_0.get()),
            List.of(new ItemStack(ModItems.STAR_SOUL.get(), 240),
                    new ItemStack(ModItems.LIGHTNING_SOUL.get(), 300),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.MIXTURE_ATTACK_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.MIXTURE_ATTACK_0.get()), 0.25)),
            60),
    MIXTURE_PENETRATION_0(Te.s(ComsumableItems.MIXTURE_PENETRATION_0.get()),
            List.of(new ItemStack(ModItems.STAR_SOUL.get(), 240),
                    new ItemStack(ModItems.HUSK_SOUL.get(), 300),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.MIXTURE_PENETRATION_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.MIXTURE_PENETRATION_0.get()), 0.25)),
            60),
    MIXTURE_PENETRATION0_0(Te.s(ComsumableItems.MIXTURE_PENETRATION0_0.get()),
            List.of(new ItemStack(ModItems.STAR_SOUL.get(), 240),
                    new ItemStack(ModItems.SLIME_BALL.get(), 300),
                    new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 5)),
            List.of(new ItemStack(ComsumableItems.MIXTURE_PENETRATION0_0.get())),
            List.of(new ItemAndRate(new ItemStack(ComsumableItems.MIXTURE_PENETRATION0_0.get()), 0.25)),
            60);

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
            if (Smelt.putSlotInfoToEmptySlot(player, recipeIndex,
                    getMinutesLaterCalendar(smeltRecipe.needMinutes
                            - RankData.smeltNeedTimeReduction(player)))) {
                if (RankData.smeltNeedTimeReduction(player) > 0) {
                    RankData.sendFormatMSG(player, Te.s("你的", "职级", ChatFormatting.AQUA,
                            "为你减少了", RankData.smeltNeedTimeReduction(player) + "min", ChatFormatting.AQUA, "的炼造时间"));
                }
                // 成功
                InventoryOperation.removeItemWithoutCheck(player, smeltRecipe.needMaterialList);
                MySound.soundToPlayer(player, SoundEvents.ITEM_FRAME_ADD_ITEM);
            } else {
                // 失败
                // 发包至客户端 在screen显示信息
            }
        } else {
            // 背包中所需材料不足
            Smelt.sendFormatMSG(player, Te.s("所需材料不足"));
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
