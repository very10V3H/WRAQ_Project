package fun.wraq.series.overworld.chapter1.mine;

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

public class MineCrest extends WraqCurios implements RepeatableCurios {

    public MineCrest(Properties p_41383_, int tier) {
        super(p_41383_, 16);
        Utils.defence.put(this, new double[]{1, 2.5, 4, 5.5, 11}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) MineSuitDescription.SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMine;
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