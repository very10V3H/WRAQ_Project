package fun.wraq.process.system.profession.smith;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SmithHammer extends WraqCurios {

    private final int tier;
    public SmithHammer(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.critDamage.put(this, tier * 0.1);
        Utils.manaPenetration0.put(this, tier * 10d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("匠魂", CustomStyle.styleOfStone));
        components.add(Te.s(" 这个物品被视为", "锻造锤", CustomStyle.styleOfStone));
        components.add(Te.s(" 可以在锻造装备时使用，且", "不会消耗", ChatFormatting.AQUA));
        components.add(Te.s(" 当前等阶为:", tier, CustomStyle.styleOfWorld, "，等价于:",
                ForgeHammer.getForgeHammerByTier(tier)));
        ComponentUtils.descriptionPassive(components, Te.s("奖金", CustomStyle.styleOfGold));
        Item equipPiece5 = ModItems.equipPiece5.get();
        Item equipPiece6 = ModItems.equipPiece6.get();
        ItemStack rewardItem;
        if (tier <= 4) {
            rewardItem = new ItemStack(equipPiece5, tier);
        } else {
            rewardItem = new ItemStack(equipPiece6, tier - 4);
        }
        components.add(Te.s(" 每日首次完成", "委托任务", CustomStyle.styleOfWorld, "，可以获得",
                rewardItem, " * " + rewardItem.getCount(), ChatFormatting.AQUA));
        components.add(Te.s(" 每次完成", "委托任务", CustomStyle.styleOfWorld, "，有",
                String.format("%.0f%%", tier * 0.05 * 100), "获得", equipPiece5, " * 1", ChatFormatting.AQUA));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSmith();
    }

    public int getTier() {
        return tier;
    }

    public static Item getHammerByTier(int tier) {
        return List.of(
                SmithItems.STONE_HAMMER.get(),
                SmithItems.IRON_HAMMER.get(),
                SmithItems.COPPER_HAMMER.get(),
                SmithItems.GOLDEN_HAMMER.get(),
                SmithItems.DIAMOND_HAMMER.get(),
                SmithItems.EMERALD_HAMMER.get(),
                SmithItems.NETHER_HAMMER.get(),
                SmithItems.END_HAMMER.get()
        ).get(tier);
    }
}
