package fun.wraq.process.system.teamInstance.instances.blackCastle;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CastleNecklace extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem, Decomposable {
    public CastleNecklace(Properties properties) {
        super(properties);
        Utils.levelRequire.put(this, 180);
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAllTypeDescriptionOfCurios()).
                append(Component.literal(" v = 6 * " + BasicAttributeDescription.getDecimal(fullRate(), 1)).withStyle(CustomStyle.styleOfCastleCrystal));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    @Override
    public void setAttribute(ItemStack stack) {
        RandomCuriosAttributesUtil.randomAttributeProvide(stack, 6, 1);
    }

    @Override
    public double fullRate() {
        return 1;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("完成副本 - ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机六词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("全能型").withStyle(CustomStyle.styleOfSakura)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.CASTLE_CURIOS_POWDER.get());
    }
}
