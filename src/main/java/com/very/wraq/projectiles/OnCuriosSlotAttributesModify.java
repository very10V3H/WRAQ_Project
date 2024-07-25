package com.very.wraq.projectiles;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface OnCuriosSlotAttributesModify {
    String exAttackDamage = "exAttackDamage";
    String exManaDamage = "exManaDamage";
    String exReleaseSpeed = "exReleaseSpeed";
    double attributes(Player player, String attributesType);
    static double getAttributes(Player player, String attributeType) {
        if (!Utils.playerCuriosListMap.containsKey(player)) return 0;
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        double value = 0;
        Set<Item> set = new HashSet<>();
        for (ItemStack stack : curiosList) {
            Item item = stack.getItem();
            if (!set.contains(item) && item instanceof OnCuriosSlotAttributesModify curios) {
                value += curios.attributes(player, attributeType);
                set.add(item);
            }
        }
        return value;
    }
}
