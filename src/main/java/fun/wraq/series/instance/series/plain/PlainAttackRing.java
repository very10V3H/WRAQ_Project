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

public class PlainAttackRing extends WraqCurios implements Decomposable {

    public PlainAttackRing(Properties p_41383_, int level) {
        super(p_41383_);
        Utils.attackDamage.put(this, new double[]{30, 60, 90, 120}[level]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
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
        return new ItemStack(ModItems.PLAIN_BOSS_SOUL.get(), 2);
    }
}
