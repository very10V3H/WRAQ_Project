package com.very.wraq.events.mob.loot;

import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.projectiles.RandomEquip;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public interface RandomLootEquip extends RandomEquip {
    List<RandomAttributeValue> getRandomAttributeValues();

    int levelRequire();

    static void handleItemAndRate(ItemAndRate itemAndRate) {
        Random random = new Random();
        if (itemAndRate.getItemStack().getItem() instanceof RandomLootEquip randomLootEquip) {
            ItemStack itemStack = new ItemStack(itemAndRate.getItemStack().getItem());
            itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            List<RandomAttributeValue> randomAttributeValues = randomLootEquip.getRandomAttributeValues();
            for (RandomAttributeValue randomAttributeValue : randomAttributeValues) {
                data.putDouble(randomAttributeValue.type(),
                        random.nextDouble(randomAttributeValue.downBoundary(), randomAttributeValue.upBoundary()));
            }
            ForgeEquipUtils.setForgeQualityOnEquip(itemStack, random.nextInt(0, 5));
            itemAndRate.setItemStack(itemStack);
        }
    }

    static void quickDecompose(Player player) throws IOException {

        Map<Integer, Integer> pieceMap = new HashMap<>();
        Inventory inventory = player.getInventory();
        for (ItemStack itemStack : inventory.items) {
            int tier = getTier(itemStack);
            if (tier != -1) {
                pieceMap.put(tier, pieceMap.getOrDefault(tier, 0) + 1);
                itemStack.shrink(1);
            }
        }

        int count = 0;
        for (int i = 0; i < ForgeEquipUtils.getEquipPieceList().size(); i++) {
            if (pieceMap.containsKey(i)) {
                Item piece = ForgeEquipUtils.getEquipPiece(i);
                ItemStack pieceStack = new ItemStack(piece, pieceMap.get(i));
                count += pieceMap.get(i);
                Compute.itemStackGive(player, pieceStack);
            }
        }

        Compute.sendFormatMSG(player, Component.literal("快捷分解").withStyle(ChatFormatting.RED),
                Component.literal("分解了 " + count + " 件简易装备").withStyle(ChatFormatting.WHITE));
    }

    static int getTier(ItemStack itemStack) {
        if (ForgeEquipUtils.itemContainForgeQuality(itemStack) && itemStack.getItem() instanceof RandomLootEquip) {
            return ForgeEquipUtils.getForgeQualityOnEquip(itemStack);
        }
        return -1;
    }
}
