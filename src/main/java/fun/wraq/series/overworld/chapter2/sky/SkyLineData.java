package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-30
 * Sky-Line 天空科技的四个配置槽数据。
 */
public final class SkyLineData {

    public static final int SLOT_COUNT = 4;
    private static final String KEY = "SkyLineItems";

    private SkyLineData() {
    }

    public static List<ItemStack> getItems(ItemStack device) {
        List<ItemStack> items = new ArrayList<>();
        CompoundTag data = device.getTagElement(Utils.MOD_ID);
        if (data != null && data.contains(KEY, Tag.TAG_LIST)) {
            ListTag list = data.getList(KEY, Tag.TAG_COMPOUND);
            for (int i = 0; i < SLOT_COUNT; i++) {
                items.add(i < list.size() ? ItemStack.of(list.getCompound(i)) : ItemStack.EMPTY);
            }
        } else {
            for (int i = 0; i < SLOT_COUNT; i++) {
                items.add(ItemStack.EMPTY);
            }
        }
        return items;
    }

    public static void setItems(ItemStack device, List<ItemStack> items) {
        CompoundTag data = device.getOrCreateTagElement(Utils.MOD_ID);
        ListTag list = new ListTag();
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack stack = i < items.size() ? items.get(i) : ItemStack.EMPTY;
            CompoundTag tag = new CompoundTag();
            if (!stack.isEmpty()) {
                stack.save(tag);
            }
            list.add(tag);
        }
        data.put(KEY, list);
    }

    public static boolean isRecognized(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        Item item = stack.getItem();
        return item == ModItems.PLAIN_RUNE.get()
                || item == ModItems.FOREST_RUNE.get()
                || item == ModItems.LAKE_RUNE.get()
                || item == ModItems.VOLCANO_RUNE.get();
    }

    /** 配置界面右侧展示的可选意志。 */
    public static List<ItemStack> getOptionItems() {
        return List.of(
                new ItemStack(ModItems.PLAIN_RUNE.get()),
                new ItemStack(ModItems.FOREST_RUNE.get()),
                new ItemStack(ModItems.LAKE_RUNE.get()),
                new ItemStack(ModItems.VOLCANO_RUNE.get())
        );
    }

    public static int count(ItemStack device, Item item) {
        int count = 0;
        for (ItemStack stack : getItems(device)) {
            if (!stack.isEmpty() && stack.is(item)) {
                count++;
            }
        }
        return count;
    }
}
