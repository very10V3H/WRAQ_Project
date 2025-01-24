package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlainCrest extends WraqCurios implements RepeatableCurios {

    public PlainCrest(Properties properties, int tier) {
        super(properties, 16);
        Utils.healthRecover.put(this, new double[]{1, 3, 5, 7, 14}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) PlainSuitDescription.SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
