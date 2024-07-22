package com.very.wraq.events.core;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.mojang.logging.LogUtils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class InventoryCheck {

    @SubscribeEvent
    public static void InventoryCheckEvent(TickEvent.PlayerTickEvent event) {
        if (!event.player.isCreative() && event.player.tickCount % 20 == 0 && event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            Inventory inventory = player.getInventory();
            for (int i = 0; i < inventory.getMaxStackSize(); i++) {
                if (inventory.getItem(i).getTagElement(Utils.MOD_ID) != null) {
                    CompoundTag data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID);
                    ItemStack itemStack = inventory.getItem(i);
                    if (data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString())) {
                        Player ItemOwner = player.getServer().getPlayerList().getPlayerByName(data.getString(InventoryCheck.owner));
                        if (ItemOwner == null) {
                            LogUtils.getLogger().info("ItemOwner is null!");
                            inventory.removeItem(itemStack);
                        } else {
                            Compute.Broad(player.level(), Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal("已将玩家" + player.getName().getString() + "背包中不属于他的").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).append(Component.literal("转移到" + ItemOwner.getName().getString() + "的背包中。"))));
                            ItemOwner.addItem(itemStack);
                            inventory.removeItem(itemStack);
                        }
                    }
                }
            }
            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            ItemStack offhand = player.getItemBySlot(EquipmentSlot.OFFHAND);
            ItemStack[] itemStacks = {helmet, chest, leggings, boots};
            for (ItemStack itemStack : itemStacks) {
                if (Utils.armorTag.containsKey(itemStack.getItem())) addOwnerTagToItemStack(player, itemStack);
            }
            if (Utils.offHandTag.containsKey(offhand.getItem())) addOwnerTagToItemStack(player, offhand);
        }
    }

    public static String owner = "Owner";

    public static void removeOwnerTag(Player player, ItemStack itemStack) {
        if (itemOwnerCorrect(player, itemStack)) {
            if (itemStack.getTagElement(Utils.MOD_ID) != null) {
                itemStack.getOrCreateTagElement(Utils.MOD_ID).remove(owner);
            }
        }
    }

    public static void addOwnerTagToItemStack(Player player, ItemStack itemStack) {
        itemStack.getOrCreateTagElement(Utils.MOD_ID).putString(InventoryCheck.owner, player.getName().getString());
    }

    public static String getOwnerTag(ItemStack itemStack) {
        return itemStack.getOrCreateTagElement(Utils.MOD_ID).getString(InventoryCheck.owner);
    }

    public static boolean itemOwnerCorrect(Player player, ItemStack itemStack) {
        if (itemStack.getTagElement(Utils.MOD_ID) != null) {
            if (itemStack.getTagElement(Utils.MOD_ID).contains(InventoryCheck.owner)) {
                return getOwnerTag(itemStack).equals(player.getName().getString());
            }
            return true;
        }
        return true;
    }

    public static List<Item> boundingList = new ArrayList<>();

    public static void setBoundingList() {
        boundingList.addAll(List.of(
                ModItems.worldSoul5.get(),
                ModItems.notePaper.get(),
                ModItems.BrewingNote.get(),
                ModItems.SwordLottery.get(),
                ModItems.BowLottery.get(),
                ModItems.SceptreLottery.get(),
                ModItems.uniformPiece.get()
        ));
    }

    public static List<Item> getBoundingList() {
        if (boundingList.isEmpty()) setBoundingList();
        return boundingList;
    }

}
