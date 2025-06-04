package fun.wraq.events.core;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.instance.MopUpPaperItems;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.process.system.profession.smith.SmithItems;
import fun.wraq.series.TickItem;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.events.dragonboat.DragonDiamond;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.overworld.chapter2.lavender.LavenderBracelet;
import fun.wraq.series.overworld.mt.ManaTowerItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber
public class InventoryCheck {

    @SubscribeEvent
    public static void InventoryCheckEvent(TickEvent.PlayerTickEvent event) {
        if (!event.player.isCreative() && !event.player.isSpectator() && event.player.tickCount % 20 == 0
                && event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
/*            String name = serverPlayer.getName().getString();
            if (name.equals("Dev")) {
                return;
            }*/
            Inventory inventory = player.getInventory();
            DragonDiamond.countMap.remove(Name.get(player));
            for (int i = 0; i < inventory.getMaxStackSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);
                DragonDiamond.handleEachStack(itemStack, player);
                Item item = itemStack.getItem();
                if (item instanceof TickItem tickItem) {
                    tickItem.handleTick(player, itemStack);
                }
                if (item.toString().contains("schedule")) {
                    if (!serverPlayer.getName().getString().equals("Dev")
                            && !serverPlayer.getName().getString().equals("very_H")) {
                        UserBanList banList = serverPlayer.getServer().getPlayerList().getBans();
                        UserBanListEntry entry = new UserBanListEntry(serverPlayer.getGameProfile());
                        banList.add(entry);
                        serverPlayer.connection.disconnect(Te.s("因干扰列车运行而被封禁，请联系管理员"));
                        return;
                    }
                }
                if (getBoundingList().contains(item)) {
                    CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    if (!data.contains(owner)) {
                        addOwnerTagToItemStack(player, itemStack);
                    }
                }
                if (itemStack.getTagElement(Utils.MOD_ID) != null) {
                    CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    if (item instanceof RandomLootEquip && !data.contains(RandomLootEquip.NEW_VERSION_CHANGE_TAG)) {
                        Compute.sendFormatMSG(player, Te.s("调整", ChatFormatting.AQUA),
                                Te.s(itemStack.getDisplayName(), "在版本改动后数值有所调整，其数值已被重置"));
                        RandomLootEquip.setRandomAttribute(itemStack);
                    }
                    if (data.contains(InventoryCheck.owner)
                            && !data.getString(InventoryCheck.owner).equalsIgnoreCase(player.getName().getString())) {
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
                    if (LavenderBracelet.resetBugAttributes(itemStack)) {
                        Compute.sendFormatMSG(player, Te.s("bug修复", ChatFormatting.RED),
                                Te.s("以下物品属性已修复:", itemStack.getDisplayName()));
                    }
                }
                itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            }
            Compute.CuriosAttribute.getDistinctCuriosList(player).forEach(stack -> {
                if (LavenderBracelet.resetBugAttributes(stack)) {
                    Compute.sendFormatMSG(player, Te.s("bug修复", ChatFormatting.RED),
                            Te.s("以下物品属性已修复:", stack.getDisplayName()));
                }
            });

            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            ItemStack offhand = player.getItemBySlot(EquipmentSlot.OFFHAND);
            ItemStack[] itemStacks = {helmet, chest, leggings, boots};
            for (ItemStack itemStack : itemStacks) {
                if (Utils.armorTag.containsKey(itemStack.getItem())) {
                    removeOwnerTag(player, itemStack);
                }
            }
            if (Utils.offHandTag.containsKey(offhand.getItem())) {
                removeOwnerTag(player, offhand);
            }
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

    private static final Set<Item> boundingList = new HashSet<>();

    private static void setBoundingList() {
        boundingList.addAll(List.of(
                ModItems.WORLD_SOUL_5.get(),
                ModItems.NOTE_PAPER.get(),
                ModItems.BREWING_NOTE.get(),
                ModItems.SWORD_LOTTERY.get(),
                ModItems.BOW_LOTTERY.get(),
                ModItems.SCEPTRE_LOTTERY.get(),
                ModItems.SWORD_LOTTERY_1.get(),
                ModItems.BOW_LOTTERY_1.get(),
                ModItems.SCEPTRE_LOTTERY_1.get(),
                ModItems.ID_CARD.get(),
                ModItems.KILL_PAPER_LOOT.get(),
                ModItems.KILL_PAPER.get(),
                ModItems.REVELATION_BOOK.get(),
                ModItems.SUPPLY_BOX_TIER_0.get(),
                ModItems.SUPPLY_BOX_TIER_1.get(),
                ModItems.SUPPLY_BOX_TIER_2.get(),
                ModItems.SUPPLY_BOX_TIER_3.get(),
                EndlessInstanceItems.EASTERN_TOWER_PAPER.get(),
                EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(),
                ModItems.TP_TICKET.get(),
                ModItems.SENIOR_POTION_SUPPLY.get(),
                ModItems.ORE_SUPPLY.get(),
                ModItems.JUNIOR_SUPPLY.get(),
                ModItems.SENIOR_SUPPLY.get(),
                SpecialEventItems.TRAIN_SOUVENIRS.get(),
                ModItems.BOND.get(),
                ModItems.SPECIAL_BOND.get(),
                SpecialEventItems.SOUVENIRS_2024.get(),
                SpecialEventItems.RED_ENVELOPE.get(),
                SpecialEventItems.BIG_RED_ENVELOPE.get(),
                IceHolyItems.CHEST.get(),
                SpecialEventItems.QING_MING_REBORN_CHEST.get(),
                SpecialEventItems.OLD_SILVER_COIN.get(),
                SpecialEventItems.OLD_GOLD_COIN.get(),
                SpecialEventItems.LABOUR_DAY_IRON_HOE.get(),
                SpecialEventItems.LABOUR_DAY_IRON_PICKAXE.get(),
                SpecialEventItems.LABOUR_DAY_LOTTERY.get(),
                ModItems.ESTATE_KEY.get(),
                ModItems.REAL_ESTATE_KEY.get(),
                ModItems.TP_PASS_1DAY.get(),
                ModItems.TP_PASS_2DAY.get(),
                ModItems.TP_PASS_3DAY.get(),
                CookingItems.FOOD_BIG_COIN.get(),
                CookingItems.FOOD_MEDAL_0.get(), CookingItems.FOOD_MEDAL_1.get(),
                CookingItems.FOOD_MEDAL_2.get(), CookingItems.FOOD_MEDAL_3.get()
        ));
        UniformItems.ITEMS.getEntries()
                .stream()
                .map(entry -> entry.get().asItem())
                .forEach(item -> boundingList.add(item));
        SmithItems.ITEMS.getEntries()
                .stream()
                .map(entry -> entry.get().asItem())
                .forEach(item -> boundingList.add(item));
        AllayItems.ITEMS.getEntries()
                .stream()
                .map(entry -> entry.get().asItem())
                .forEach(item -> boundingList.add(item));
        ManaTowerItems.ITEMS.getEntries()
                .stream()
                .map(entry -> entry.get().asItem())
                .forEach(item -> boundingList.add(item));
        MopUpPaperItems.ITEMS.getEntries()
                .stream()
                .map(entry -> entry.get().asItem())
                .forEach(item -> boundingList.add(item));
    }

    public static Set<Item> getBoundingList() {
        if (boundingList.isEmpty()) setBoundingList();
        return boundingList;
    }
}
