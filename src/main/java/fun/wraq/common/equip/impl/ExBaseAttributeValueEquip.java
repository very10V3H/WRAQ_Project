package fun.wraq.common.equip.impl;

import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface ExBaseAttributeValueEquip {
    record TagAndRate(String tag, double rate) {
        public double getValueByData(CompoundTag data) {
            return data.getDouble(tag) * rate;
        }
    }
    Map<Map<Item, Double>, TagAndRate> getTagAndRateMap();

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
            TagAndRate tagAndRate = equip.getTagAndRateMap().get(attributeMap);
            data.putInt(tagAndRate.tag(), data.getInt(tagAndRate.tag()) + forgeTier);
        }
    }
}
