package fun.wraq.process.func.item;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.hud.networking.ItemGetS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.*;

public class InventoryOperation {
    public static int itemStackCount(Player player, Item item) {
        Inventory inventory = player.getInventory();
        return itemStackCount(inventory, item);
    }

    public static int itemStackCount(Inventory inventory, Item item) {
        int ExistNum = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.is(item) && InventoryCheck.itemOwnerCorrect(inventory.player, itemStack))
                ExistNum += itemStack.getCount();
        }
        return ExistNum;
    }

    public static boolean checkPlayerHasItem(Player player, Item item, int requirementNum) {
        return requirementNum == 0 || itemStackCount(player.getInventory(), item) >= requirementNum;
    }

    public static boolean checkPlayerHasItem(Inventory inventory, Item item, int requirementNum) {
        return requirementNum == 0 || itemStackCount(inventory, item) >= requirementNum;
    }

    public static boolean checkPlayerHasItem(Player player, List<ItemStack> list) {
        for (ItemStack itemStack : list) {
            if (!checkPlayerHasItem(player.getInventory(), itemStack.getItem(), itemStack.getCount())) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkItemRemoveIfHas(Player player, List<ItemStack> list) {
        if (checkPlayerHasItem(player, list)) {
            list.forEach(stack -> {
                removeItem(player.getInventory(), stack.getItem(), stack.getCount());
            });
            return true;
        }
        return false;
    }

    public static void itemStackRemoveIgnoreVB(Inventory inventory, Item item, int removeNum) {
        removeItem(inventory, item, removeNum);
    }

    public static void removeItemWithoutCheck(Player player, List<ItemStack> list) {
        list.forEach(stack -> {
            removeItem(player.getInventory(), stack.getItem(), stack.getCount());
        });
    }

    public static void removeItemWithoutCheck(Player player, ItemStack itemStack) {
        removeItem(player.getInventory(), itemStack.getItem(), itemStack.getCount());
    }

    public static boolean removeItem(Player player, Item item, int removeNum) {
        return removeItem(player.getInventory(), item, removeNum);
    }

    public static boolean removeItem(Inventory inventory, Item item, int removeNum) {
        int num = removeNum;
        if (!checkPlayerHasItem(inventory, item, removeNum)) return false;
        else {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (inventory.getItem(i).is(item)) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.getCount() < num) {
                        num -= itemStack.getCount();
                        itemStack.setCount(0);
                        inventory.setItem(i, itemStack);
                    } else {
                        itemStack.setCount(itemStack.getCount() - num);
                        inventory.setItem(i, itemStack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int checkItem(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.items.size(); i++) {
            if (!inventory.items.get(i).isEmpty() && inventory.getItem(i).is(itemStack.getItem())) return i;
        }
        return -1;
    }

    public static void giveItemStack(Player player, ItemStack itemStack) {
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (itemStack.getCount() > 0) {
            if (InventoryCheck.getBoundingList().contains(itemStack.getItem())) {
                InventoryCheck.addOwnerTagToItemStack(player, itemStack);
            }
            Inventory inventory = player.getInventory();
            if (inventory.getFreeSlot() != -1) {
                ModNetworking.sendToClient(new ItemGetS2CPacket(itemStack), (ServerPlayer) player);
                player.addItem(itemStack);
            } else {
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, player.level());
                itemEntity.setItem(itemStack);
                itemEntity.moveTo(player.position());
                player.level().addFreshEntity(itemEntity);
                if (!Compute.PlayerIgnore.ignoreItemGet(player)) {
                    Compute.sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Te.s("背包已无空位，请注意。无法放入背包的物品将掉落在地上"));
                }
            }
        }
    }

    public static void giveItemStack(Player player, Item item) {
        giveItemStack(player, new ItemStack(item));
    }

    public static void giveItemStackWithMSG(Player player, Item item, int count) {
        giveItemStackWithMSG(player, new ItemStack(item, count));
    }

    public static void giveItemStackWithMSG(Player player, ItemStack itemStack) {
        if (!Compute.PlayerIgnore.ignoreItemGet(player)) {
            Compute.sendFormatMSG(player, Te.s("物品", ChatFormatting.GREEN),
                    Te.s("你获得了", itemStack.getDisplayName(), " * " + itemStack.getCount(), ChatFormatting.AQUA));
        }
        giveItemStack(player, itemStack);
    }

    public static void giveItemStackWithMSGByBatch(Player player, ItemStack itemStack) {
        Item item = itemStack.getItem();
        int count = itemStack.getCount();
        for (int i = 0 ; i < count / 64 ; i ++) {
            giveItemStackWithMSG(player, new ItemStack(item, 64));
        }
        giveItemStackWithMSG(player, new ItemStack(item, count % 64));
    }

    public static void giveItemStackWithMSG(Player player, Item item) {
        ItemStack itemStack = item.getDefaultInstance();
        giveItemStackWithMSG(player, itemStack);
    }

    public static void giveItemStackByRate(ItemStack itemStack, double rate, Player player) {
        Random r = new Random();
        if (rate > 1) {
            int Count = (int) rate;
            if (r.nextDouble() < rate % 1) Count++;
            InventoryOperation.giveItemStack(player, new ItemStack(itemStack.getItem(), Count));
        } else {
            if (r.nextDouble(1.0d) < rate) {
                if (rate <= 0.01) {
                    ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_LEVELUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1, 1, 0);
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    serverPlayer.connection.send(clientboundSoundPacket);
                    Compute.formatBroad(player.level(), Component.literal("稀有掉落").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(player.getName().getString() + "获得了").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()));
                }
                InventoryOperation.giveItemStack(player, itemStack);
            }
        }
    }

    public static void itemTrade(Player player, ItemStack needItem, ItemStack targetItem) throws IOException {
        int playerInventoryNeedItemCount = InventoryOperation.itemStackCount(player, needItem.getItem());
        if (playerInventoryNeedItemCount >= needItem.getCount()) {
            InventoryOperation.itemStackRemoveIgnoreVB(player.getInventory(), needItem.getItem(), needItem.getCount());
            InventoryOperation.giveItemStack(player, new ItemStack(targetItem.getItem(), targetItem.getCount()));
        } else {
            Compute.sendFormatMSG(player, Component.literal("交易").withStyle(ChatFormatting.GOLD),
                    Component.literal("背包中似乎没有足够数量的 ").withStyle(ChatFormatting.WHITE).
                            append(needItem.getDisplayName()));
        }
    }

    public static List<ItemStack> getPassiveEquip(Player player) {
        List<ItemStack> passiveEquip = new ArrayList<>();
        Inventory inventory = player.getInventory();
        for (int i = 3; i < 9; i++) {
            ItemStack stack = inventory.getItem(i);
            if (Utils.passiveEquipTag.containsKey(stack.getItem())) {
                passiveEquip.add(stack);
            }
        }
        return passiveEquip;
    }

    public static List<ItemStack> getArmors(Player player) {
        return List.of(player.getItemBySlot(EquipmentSlot.HEAD),
                player.getItemBySlot(EquipmentSlot.CHEST), player.getItemBySlot(EquipmentSlot.LEGS),
                player.getItemBySlot(EquipmentSlot.FEET));
    }

    public static List<ItemStack> getDistinctArmors(Player player) {
        List<ItemStack> list = getArmors(player);
        List<ItemStack> distinctArmors = new ArrayList<>();
        Set<Class<? extends Item>> set = new HashSet<>();
        for (ItemStack itemStack : list) {
            if (!set.contains(itemStack.getItem().getClass())) {
                set.add(itemStack.getItem().getClass());
                distinctArmors.add(itemStack);
            }
        }
        return distinctArmors;
    }

    public static List<ItemStack> getAllEquipSlotItems(Player player) {
        List<ItemStack> list = new ArrayList<>(getArmors(player));
        if (Utils.mainHandTag.containsKey(player.getMainHandItem().getItem())) list.add(player.getMainHandItem());
        if (Utils.offHandTag.containsKey(player.getOffhandItem().getItem())) list.add(player.getOffhandItem());
        return list;
    }

    public static List<ItemStack> getDistinctAllEquipSlotItems(Player player) {
        List<ItemStack> list = getAllEquipSlotItems(player);
        List<ItemStack> distinctAllEquipSlotItems = new ArrayList<>();
        Set<Class<? extends Item>> set = new HashSet<>();
        for (ItemStack itemStack : list) {
            if (!set.contains(itemStack.getItem().getClass())) {
                set.add(itemStack.getItem().getClass());
                distinctAllEquipSlotItems.add(itemStack);
            }
        }
        return distinctAllEquipSlotItems;
    }

    public static ItemStack findFirstItem(Player player, Item item) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(item)) {
                return stack;
            }
        }
        return null;
    }
}
