package com.very.wraq.projectiles;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.chat.Component;
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
        Set<Class<? extends Item>> set = new HashSet<>();
        HashSet<Item> curiosSet = new HashSet<>();

        CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
            for (int i = 0; i < inv.getEquippedCurios().getSlots(); i++) {
                Item item = inv.getEquippedCurios().getStackInSlot(i).getItem();
                curiosSet.add(item);
            }
        });

        curiosSet.forEach(item -> {
            if (!set.contains(item.getClass())) {
                set.add(item.getClass());
                if (item instanceof OnCuriosSlotTickEffect curios) {
                    curios.tick(player);
                }
            }
        });
    }
}
