package fun.wraq.process.system.profession.smith;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.render.toolTip.CustomStyle;
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
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("匠魂"));
        components.add(Te.s(" 这个物品被视为", "锻造锤"));
        components.add(Te.s(" 可以在锻造装备时使用，且不会消耗"));
        components.add(Te.s(" 当前等阶为:", tier, CustomStyle.styleOfWorld, "，等价于:",
                ForgeHammer.getForgeHammerByTier(tier)));
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
