package fun.wraq.series.instance.series.plain;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PlainHealthRing extends WraqCurios implements Decomposable {

    public PlainHealthRing(Properties p_41383_, int level) {
        super(p_41383_);
        Utils.maxHealth.put(this, new double[]{800, 1600, 2400, 3200}[level]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfPlainBoss();
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.PlainBossSoul.get(), 2);
    }
}
