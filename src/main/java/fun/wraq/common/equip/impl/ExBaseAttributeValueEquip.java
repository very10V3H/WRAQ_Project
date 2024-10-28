package fun.wraq.common.equip.impl;

import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface ExBaseAttributeValueEquip {
    record TagAndEachTierValue(String tag, double eachTierValue) {
        public double getValueByData(CompoundTag data) {
            return data.getDouble(tag) * eachTierValue;
        }
    }

    /**
     * 注意，必须使用此接口的getStackExBaseAttributeData方法来获取nbt放入对应key
     * @return 额外属性表
     */
    Map<Map<Item, Double>, TagAndEachTierValue> getTagAndRateMap();

    String EX_BASE_ATTRIBUTE_DATA_KEY = "EX_BASE_ATTRIBUTE_DATA_KEY";
    static CompoundTag getStackExBaseAttributeData(ItemStack stack) {
        if (!stack.getOrCreateTagElement(Utils.MOD_ID).contains(EX_BASE_ATTRIBUTE_DATA_KEY)) {
            stack.getOrCreateTagElement(Utils.MOD_ID).put(EX_BASE_ATTRIBUTE_DATA_KEY, new CompoundTag());
        }
        return stack.getOrCreateTagElement(Utils.MOD_ID).getCompound(EX_BASE_ATTRIBUTE_DATA_KEY);
    }

    static void forgeThisTypeEquip(ItemStack itemStack, Map<Item, Double> attributeMap, int forgeTier) {
        if (itemStack.getItem() instanceof ExBaseAttributeValueEquip equip) {
            CompoundTag data = getStackExBaseAttributeData(itemStack);
            TagAndEachTierValue tagAndEachTierValue = equip.getTagAndRateMap().get(attributeMap);
            data.putInt(tagAndEachTierValue.tag(), data.getInt(tagAndEachTierValue.tag()) + forgeTier);
        }
    }

    static int getForgeTier(ItemStack itemStack, Map<Item, Double> attributeMap) {
        if (itemStack.getItem() instanceof ExBaseAttributeValueEquip equip) {
            CompoundTag data = getStackExBaseAttributeData(itemStack);
            TagAndEachTierValue tagAndEachTierValue = equip.getTagAndRateMap().get(attributeMap);
            return data.getInt(tagAndEachTierValue.tag());
        }
        return 0;
    }

    static double getExBaseAttributeValue(ItemStack itemStack, Map<Item, Double> map) {
        if (itemStack.getItem() instanceof ExBaseAttributeValueEquip equip && equip.getTagAndRateMap().containsKey(map)) {
            CompoundTag data = getStackExBaseAttributeData(itemStack);
            TagAndEachTierValue tagAndEachTierValue = equip.getTagAndRateMap().get(map);
            return tagAndEachTierValue.getValueByData(data);
        }
        return 0;
    }
}
