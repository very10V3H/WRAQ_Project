package fun.wraq.process.func.suit;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SArmorAttribute {
    public static String sumPower = "SunPower";
    public static String lakePower = "LakePower";
    public static String volcanoPower = "VolcanoPower";
    public static String skyPower = "SkyPower";
    public static String snowPower = "SnowPower";
    public static String manaPower = "ManaPower";
    public static String netherPower = "NetherPower";

    public static Map<String, Double> valueMap = new HashMap<>() {{
        put(sumPower, 10d * 2);
        put(lakePower, 0.5 * 0.015);
        put(volcanoPower, 1.5d);
        put(skyPower, 0.5 * 0.015);
        put(snowPower, 0.015);
        put(manaPower, 1.5);
        put(netherPower, 0.25 * 0.015);
    }};

    public static double value(Player player, String powerName) {
        double value = 0;
        double rate = valueMap.get(powerName);
        ItemStack equip = player.getItemBySlot(EquipmentSlot.HEAD);
        if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorHelmet.get())) rate *= 2;
            for (int i = 1; i <= 5; i++) {
                String isPower = "#Slot#" + i + "#" + powerName;
                if (data.contains(isPower)) {
                    value += data.getDouble(isPower) * rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorChest.get())) rate *= 2;
            else rate = 1;
            for (int i = 1; i <= 5; i++) {
                String isPower = "#Slot#" + i + "#" + powerName;
                if (data.contains(isPower)) {
                    value += data.getDouble(isPower) * rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.LEGS);
        if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorLeggings.get())) rate *= 2;
            else rate = 1;
            for (int i = 1; i <= 5; i++) {
                String isPower = "#Slot#" + i + "#" + powerName;
                if (data.contains(isPower)) {
                    value += data.getDouble(isPower) * rate;
                }
            }
        }
        equip = player.getItemBySlot(EquipmentSlot.FEET);
        if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (equip.is(ModItems.ISArmorBoots.get())) rate *= 2;
            else rate = 1;
            for (int i = 1; i <= 5; i++) {
                String isPower = "#Slot#" + i + "#" + powerName;
                if (data.contains(isPower)) {
                    value += data.getDouble(isPower) * rate;
                }
            }
        }
        return value;
    }
}
