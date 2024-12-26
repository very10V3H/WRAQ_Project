package fun.wraq.series.specialevents.train;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrainSouvenirs extends WraqCurios {

    public TrainSouvenirs(Properties properties) {
        super(properties);
        Utils.coolDownDecrease.put(this, 0.08);
        Utils.defencePenetration.put(this, 0.08);
        Utils.manaPenetration.put(this, 0.08);
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
        return CustomStyle.styleOfField;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSouvenirs();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        components.add(Te.s("以此物纪念维瑞阿契2蒙特轻轨的初次运行", CustomStyle.styleOfField, ChatFormatting.ITALIC));
        components.add(Te.s(" - 2024.12.26", CustomStyle.styleOfWorld));
    }
}
