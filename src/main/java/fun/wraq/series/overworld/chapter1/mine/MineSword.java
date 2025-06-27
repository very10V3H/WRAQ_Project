package fun.wraq.series.overworld.chapter1.mine;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MineSword extends WraqSword {

    private final int tier;
    public MineSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{50, 55, 60, 70}[tier]);
        Utils.defencePenetration0.put(this, new double[]{3, 4, 5, 6}[tier]);
        Utils.healthSteal.put(this, 0.04);
        Utils.critRate.put(this, new double[]{0.55, 0.6, 0.65, 0.7}[tier]);
        Element.stoneElementValue.put(this, 0.2);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("凿击").withStyle(ChatFormatting.GRAY));
        ComponentUtils.descriptionNum(components, "攻击将会降低目标生物的移动速度", Component.literal("2s").withStyle(ChatFormatting.GRAY), "");
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public int getWraqTier() {
        return tier;
    }
}
