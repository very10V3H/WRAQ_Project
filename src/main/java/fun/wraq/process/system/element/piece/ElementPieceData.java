package fun.wraq.process.system.element.piece;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.element.networking.ElementPieceS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ElementPieceData {
    public static CompoundTag clientData;
    private static final String DATA_KEY = "ElementPieceData";
    public static CompoundTag getData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, DATA_KEY);
    }

    public static void setCount(Player player, Item item, int count) {
        getData(player).putInt(item.toString(), count);
    }

    public static int getCount(Player player, Item item) {
        return getData(player).getInt(item.toString());
    }

    public static int getCountClient(Item item) {
        if (clientData == null) {
            return 0;
        }
        return clientData.getInt(item.toString());
    }

    public static void addCount(Player player, Item item, int count) {
        setCount(player, item, getCount(player, item) + count);
        sendDataToClient(player);
    }

    public static void reduceCount(Player player, Item item, int count) {
        setCount(player, item, getCount(player, item) - count);
        sendDataToClient(player);
    }

    public static void convert(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            Item item = stack.getItem();
            if (ElementPieceRecipe.getItemSet().contains(item)) {
                addCount(player, item, stack.getCount());
                if (!Compute.PlayerIgnore.ignoreItemGet(player)) {
                    ElementPieceRecipe.sendMSG(player,
                            Te.s(item, " * " + stack.getCount(), ChatFormatting.AQUA, "已被量子化"));
                }
                stack.shrink(stack.getCount());
            }
        }
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new ElementPieceS2CPacket(getData(player)), player);
    }
}
