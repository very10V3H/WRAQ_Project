package fun.wraq.events.core;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.series.specialevents.SpecialEventItems;
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
import net.minecraftforge.registries.RegistryObject;

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
                ItemStack itemStack = inventory.getItem(i);
                Item item = itemStack.getItem();
                if (inventory.getItem(i).getTagElement(Utils.MOD_ID) != null) {
                    CompoundTag data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID);
                    if (item instanceof RandomLootEquip && !data.contains(RandomLootEquip.NEW_VERSION_CHANGE_TAG)) {
                        Compute.sendFormatMSG(player, Te.s("调整", ChatFormatting.AQUA),
                                Te.s(itemStack.getDisplayName(), "在版本改动后数值有所调整，其数值已被重置"));
                        RandomLootEquip.setRandomAttribute(itemStack);
                    }
                    if (data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString())) {
                        Player ItemOwner = player.getServer().getPlayerList().getPlayerByName(data.getString(InventoryCheck.owner));
                        if (ItemOwner == null) {
                            LogUtils.getLogger().info("ItemOwner is null!");
                            inventory.removeItem(itemStack);
                        } else {
                            Compute.broad(player.level(), Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal("已将玩家" + player.getName().getString() + "背包中不属于他的").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).append(Component.literal("转移到" + ItemOwner.getName().getString() + "的背包中。"))));
                            ItemOwner.addItem(itemStack);
                            inventory.removeItem(itemStack);
                        }
                    }
                }
                itemStack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
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

    public static void removeOwnerTagDirect(ItemStack itemStack) {
        if (itemStack.getTagElement(Utils.MOD_ID) != null) {
            itemStack.getOrCreateTagElement(Utils.MOD_ID).remove(owner);
        }
    }

    public static void addOwnerTagToItemStack(Player player, ItemStack itemStack) {
        itemStack.getOrCreateTagElement(Utils.MOD_ID).putString(InventoryCheck.owner, player.getName().getString());
    }

    public static String getOwnerTag(ItemStack itemStack) {
        return itemStack.getOrCreateTagElement(Utils.MOD_ID).getString(InventoryCheck.owner);
    }

    public static boolean containOwnerTag(ItemStack itemStack) {
        return itemStack.getTagElement(Utils.MOD_ID) != null && itemStack.getTagElement(Utils.MOD_ID).contains(InventoryCheck.owner);
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
                UniformItems.uniformPiece.get(),
                ModItems.ID_Card.get(),
                ModItems.MopUpPaperLoot.get(),
                ModItems.CastleMopUpPaper.get(),
                ModItems.DevilMopUpPaper.get(),
                ModItems.IceKnightMopUpPaper.get(),
                ModItems.MoonMopUpPaper.get(),
                ModItems.PlainMopUpPaper.get(),
                ModItems.PurpleIronKnightMopUpPaper.get(),
                ModItems.SakuraBossMopUpPaper.get(),
                ModItems.KillPaperLoot.get(),
                ModItems.killPaper.get(),
                ModItems.RevelationBook.get(),
                ModItems.supplyBoxTier1.get(),
                ModItems.supplyBoxTier2.get(),
                ModItems.supplyBoxTier3.get()
        ));
        for (Object o : SpecialEventItems.ITEMS.getEntries().toArray()) {
            RegistryObject<Item> item = (RegistryObject<Item>) o;
            boundingList.add(item.get());
        }
    }

    public static List<Item> getBoundingList() {
        if (boundingList.isEmpty()) setBoundingList();
        return boundingList;
    }

}
