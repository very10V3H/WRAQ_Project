package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VolcanoCrest extends WraqCurios implements RepeatableCurios {

    public static List<Item> crestList = new ArrayList<>();

    public VolcanoCrest(Properties properties, int tier) {
        super(properties, 16);
        Utils.attackDamage.put(this, new double[]{25, 50, 75, 100, 250}[tier]);
        Element.fireElementValue.put(this, new double[]{0.05, 0.12, 0.2, 0.32, 0.5}[tier]);
        crestList.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) VolcanoSuitDescription.VolcanoSuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }


    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
