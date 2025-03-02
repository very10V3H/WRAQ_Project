package fun.wraq.series.moontain.equip.weapon;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.MoontainEquip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoontainBow extends WraqBow implements ExBaseAttributeValueEquip, WraqMainHandOrPassiveEquip,
        Decomposable, MoontainEquip {

    public MoontainBow(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1800d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.critRate.put(this, 0.25);
        Utils.levelRequire.put(this, 210);
    }

    @Override
    public Style getQuoteStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public Style getExValueStyle() {
        return Style.EMPTY.applyFormat(ChatFormatting.GREEN);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("筑造", getMainStyle()));
        components.add(Te.s(" 筑造阶数: ", getMainStyle(),
                String.valueOf(ExBaseAttributeValueEquip.getForgeTier(stack, MoontainUtils.getTraditionalAttributeMap(stack)))));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoontain();
    }

    @Override
    public double rate() {
        return 0.25;
    }

    @Override
    public Map<Map<Item, Double>, TagAndEachTierValue> getTagAndRateMap() {
        return new HashMap<>() {{
            put(Utils.attackDamage, new TagAndEachTierValue(MoontainUtils.MOONTAIN_ATTACK_TAG_KEY, 100));
        }};
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MoontainItems.HEART.get(), 4);
    }
}
