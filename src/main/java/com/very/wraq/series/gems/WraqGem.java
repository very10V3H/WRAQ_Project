package com.very.wraq.series.gems;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.render.gui.illustrate.Display;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WraqGem extends Item {

    private final Style hoverStyle;
    private final Component oneLineDescription;
    private final Component suffix;
    private final List<AttributeMapValue> attributeMapValues;

    public record AttributeMapValue(Map<Item, Double> attributeMap, double value) {}

    public WraqGem(Properties properties, List<AttributeMapValue> attributeMapValues,
                   Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties);
        Utils.gemsTag.put(this, 1);
        for (AttributeMapValue attributeMapValue : attributeMapValues) {
            attributeMapValue.attributeMap.put(this, attributeMapValue.value);
        }
        Display.gemList.add(this);
        this.hoverStyle = hoverStyle;
        this.oneLineDescription = oneLineDescription;
        this.suffix = suffix;
        this.attributeMapValues = attributeMapValues;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = hoverStyle;
        components.add(oneLineDescription);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(suffix);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public Style getHoverStyle() {
        return hoverStyle;
    }

    public List<AttributeMapValue> getAttributeMapValues() {
        return attributeMapValues;
    }

    public static List<WraqGem> getEquipContainGemList(ItemStack equip) throws CommandSyntaxException {
        List<WraqGem> gemList = new ArrayList<>();
        if (equip.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            for (int i = 1 ; i <= 3 ; i ++) {
                if (data.contains("newGems" + i)) {
                    Item gemItem = Compute.getItemFromString(data.getString("newGems" + i)).getItem();
                    if (gemItem instanceof WraqGem wraqGem) gemList.add(wraqGem);
                }
            }
        }
        return gemList;
    }
}
