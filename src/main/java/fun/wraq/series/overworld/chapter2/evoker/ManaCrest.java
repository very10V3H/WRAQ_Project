package fun.wraq.series.overworld.chapter2.evoker;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManaCrest extends WraqCurios implements RepeatableCurios {

    public ManaCrest(Properties p_41383_, int tier) {
        super(p_41383_, 16);
        Utils.manaDamage.put(this, new double[]{50, 100, 150, 200, 500}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 可以作为", "[生机魔力盔甲]", CustomStyle.styleOfLife, "/", ChatFormatting.AQUA,
                "[黑曜魔力盔甲]", CustomStyle.styleOfMana, "套装之一"));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
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
