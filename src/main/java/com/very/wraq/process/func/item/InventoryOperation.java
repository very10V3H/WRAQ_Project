package com.very.wraq.process.func.item;

import com.mojang.logging.LogUtils;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.core.InventoryCheck;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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

    public static boolean checkPlayerHasItem(Inventory inventory, Item item, int RequirementNum) {
        return itemStackCount(inventory, item) >= RequirementNum;
    }

    public static boolean checkPlayerHasItem(Player player, List<ItemStack> list) {
        for (ItemStack itemStack : list) {
            if (!checkPlayerHasItem(player.getInventory(), itemStack.getItem(), itemStack.getCount())) return false;
        }
        return true;
    }

    public static boolean checkItemRemoveIfHas(Player player, List<ItemStack> list) {
        if (checkPlayerHasItem(player, list)) {
            list.forEach(stack -> {
                removeItem(player.getInventory(), stack.getItem(), stack.getCount());
            });
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

    public static boolean removeItem(Inventory inventory, Item item, int removeNum) {
        int num = removeNum;
        if (!checkPlayerHasItem(inventory, item, removeNum)) return false;
        else {
            LogUtils.getLogger().info("{} {} {} {}", inventory.player.getName().getString(), Utils.LogTypes.cost, item.toString(), removeNum);
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

    public static void itemStackGive(Player player, ItemStack itemStack) {
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (itemStack.getCount() > 0) {
            if (InventoryCheck.getBoundingList().contains(itemStack.getItem()))
                InventoryCheck.addOwnerTagToItemStack(player, itemStack);
            if (!Compute.PlayerIgnore.IgnoreItemGet(player)) {
                if (itemStack.getCount() > 1) {
                    Compute.sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Component.literal("你获得了：").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("*" + itemStack.getCount()).withStyle(ChatFormatting.AQUA)));
                } else {
                    Compute.sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                            Component.literal("你获得了：").withStyle(ChatFormatting.WHITE).
                                    append(itemStack.getDisplayName()));
                }
            }
            Inventory inventory = player.getInventory();
            if (inventory.getFreeSlot() != -1) {
                player.addItem(itemStack);
            } else {
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, player.level());
                itemEntity.setItem(itemStack);
                itemEntity.moveTo(player.position());
                player.level().addFreshEntity(itemEntity);
                Compute.sendFormatMSG(player, Component.literal("物品").withStyle(ChatFormatting.GREEN),
                        Component.literal("背包已无空位，请注意。"));
            }
        }
    }

    public static void giveItemStackByRate(ItemStack itemStack, double rate, Player player) {
        Random r = new Random();
        if (rate > 1) {
            int Count = (int) rate;
            if (r.nextDouble() < rate % 1) Count++;
            InventoryOperation.itemStackGive(player, new ItemStack(itemStack.getItem(), Count));
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
                InventoryOperation.itemStackGive(player, itemStack);
            }
        }
    }

    public static void itemTrade(Player player, ItemStack needItem, ItemStack targetItem) throws IOException {
        int playerInventoryNeedItemCount = InventoryOperation.itemStackCount(player, needItem.getItem());
        if (playerInventoryNeedItemCount >= needItem.getCount()) {
            InventoryOperation.itemStackRemoveIgnoreVB(player.getInventory(), needItem.getItem(), needItem.getCount());
            InventoryOperation.itemStackGive(player, new ItemStack(targetItem.getItem(), targetItem.getCount()));
        } else {
            Compute.sendFormatMSG(player, Component.literal("交易").withStyle(ChatFormatting.GOLD),
                    Component.literal("背包中似乎没有足够数量的 ").withStyle(ChatFormatting.WHITE).
                            append(needItem.getDisplayName()));
        }
    }
}
