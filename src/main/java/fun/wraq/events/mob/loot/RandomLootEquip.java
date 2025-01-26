package fun.wraq.events.mob.loot;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.RandomEquip;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
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

    String NEW_VERSION_CHANGE_TAG = "version 2.0.37 random equip attribute change";

    static void setRandomAttribute(ItemStack itemStack) {
        if (itemStack.getItem() instanceof RandomLootEquip randomLootEquip) {
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putBoolean(NEW_VERSION_CHANGE_TAG, true);
            List<RandomAttributeValue> randomAttributeValues = randomLootEquip.getRandomAttributeValues();
            Random random = new Random();
            for (RandomAttributeValue randomAttributeValue : randomAttributeValues) {
                data.putDouble(randomAttributeValue.type(),
                        random.nextDouble(randomAttributeValue.downBoundary(), randomAttributeValue.upBoundary()));
            }
            ForgeEquipUtils.setForgeQualityOnEquip(itemStack, random.nextInt(0, 5));
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
                InventoryOperation.giveItemStack(player, pieceStack);
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
