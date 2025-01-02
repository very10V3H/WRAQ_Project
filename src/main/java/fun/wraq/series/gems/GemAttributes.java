package fun.wraq.series.gems;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class GemAttributes {

    public static double getPlayerCurrentAllEquipGemsValue(Player player, Map<Item, Double> map) {
        double value = 0;
        for (ItemStack stack : InventoryOperation.getAllEquipSlotItems(player)) {
            value += getGemsAttributeModifier(stack.getOrCreateTagElement(Utils.MOD_ID), map);
        }
        return value;
    }

    public static double getGemsAttributeModifier(ItemStack equip, Map<Item, Double> map) {
        return getGemsAttributeModifier(equip.getOrCreateTagElement(Utils.MOD_ID), map);
    }

    public static double getGemsAttributeModifier(CompoundTag data, Map<Item, Double> map) {
        double value = 0;
        for (int i = 1; i <= 3; i++) {
            if (data.contains("newGems" + i)) {
                String gemName = data.getString("newGems" + i);
                ItemStack gem = null;

                try {
                    gem = Compute.getItemFromString(gemName);
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException(e);
                }

                value += map.getOrDefault(gem.getItem(), 0d);
            }
        }
        return value;
    }
}
