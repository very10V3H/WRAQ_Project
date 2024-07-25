package com.very.wraq.projectiles;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.stringtemplate.v4.ST;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface OnCuriosSlotTickEffect {
    void tick(Player player);
    static void tickEvent(Player player) {
        if (!player.level().isClientSide) {
            if (!Utils.playerCuriosListMap.containsKey(player)) return ;
            List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
            Set<Item> set = new HashSet<>();
            for (ItemStack stack : curiosList) {
                Item item = stack.getItem();
                if (!set.contains(item) && item instanceof OnCuriosSlotTickEffect curios) {
                    curios.tick(player);
                    set.add(item);
                }
            }
        } else {
            List<Item> list = new ArrayList<>();
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                for (int i = 0 ; i < inv.getEquippedCurios().getSlots() ; i ++) {
                    Item item = inv.getEquippedCurios().getStackInSlot(i).getItem();
                    boolean duplicate = false;
                    for (Item item1 : list) {
                        if (item.getClass().equals(item1.getClass())) {
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        list.add(item);
                        if (item instanceof OnCuriosSlotTickEffect curios) {
                            curios.tick(player);
                        }
                    }
                }
            });
        }
    }
}
