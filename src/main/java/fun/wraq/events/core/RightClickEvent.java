package fun.wraq.events.core;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
import fun.wraq.process.func.multiblockactive.rightclick.RightClickActiveHandler;
import fun.wraq.process.system.bank.BondDividends;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.smith.SmithPlayerData;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.gui.villagerTrade.TradeList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

@Mod.EventBusSubscriber
public class RightClickEvent {

    @SubscribeEvent
    public static void RightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemStack.getItem();
        if (event.getSide().isServer()) {
            if (item instanceof ActiveItem) {
                if (!Utils.bowTag.containsKey(item) && !Utils.sceptreTag.containsKey(item)) {
                    Compute.use(player, item);
                }
            }
        }

        if (itemStack.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(fun.wraq.events.core.InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString()))
                event.setCanceled(true);
        }
        if (disallowToRightClickItems.contains(event.getItemStack().getItem()) && !player.isCreative())
            event.setCanceled(true);

        // 需要判断当前与哪个手的物品上交互，否则将会执行两次
        if (event.getSide().isServer() && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            NoTeamInstanceModule.handlePlayerRightClick(player);
            RightClickActiveHandler.handleOnPlayerRightClick(player);
        }

        if (event.getHand().equals(InteractionHand.MAIN_HAND)) {
            if (item instanceof ArmorItem) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void rightClickAllay(PlayerInteractEvent.EntityInteract event) {
        if (event.getSide().isServer() && event.getTarget() instanceof Allay allay
                && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            AllayPet.allayPets
                    .stream().filter(allayPet -> allayPet.allay.equals(allay))
                    .findAny().ifPresent(allayPet -> {
                        ServerPlayer serverPlayer = Compute.getPlayerByName(allayPet.ownerName);
                        if (serverPlayer != null) {
                            AllayPetPlayerData.queryAllayInfo(serverPlayer);
                        }
                    });
        }
        if (event.getTarget() instanceof Allay) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event) throws SQLException, ParseException {
        if (event.getSide().isServer() && event.getTarget() instanceof Villager villager) {
            String name = villager.getName().getString();
            Player player = event.getEntity();
            switch (name) {
                case MobKillEntrustment.VILLAGER_NAME -> {
                    MobKillEntrustment.onPlayerInteractWithVillager(player);
                    event.setCanceled(true);
                    return;
                }
                case "联合银行职员" -> {
                    BondDividends.onPlayerInteractWithVillager(player);
                    event.setCanceled(true);
                    return;
                }
                case "悦灵学者" -> {
                    AllayPetPlayerData.onPlayerInteractWithVillager(player);
                    event.setCanceled(true);
                    return;
                }
                case "工匠学者" -> {
                    SmithPlayerData.onPlayerInteractWithVillager(player);
                    event.setCanceled(true);
                    return;
                }
                case "逆熵学者" -> {
                    ModNetworking.sendToClient(new ScreenSetS2CPacket(6), (ServerPlayer) player);
                    return;
                }
            }
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) {
                TradeList.setTradeContent();
            }
            boolean flag = false;
            for (MutableComponent value : StringUtils.VillagerNameMap.values()) {
                if (value.getString().equals(villager.getName().getString())) flag = true;
            }
            for (MutableComponent value : MyVillagerData.villagerNameMap.values()) {
                if (value.getString().equals(villager.getName().getString())) flag = true;
            }
            if (flag) {
                ModNetworking.sendToClient(new VillagerTradeScreenS2CPacket(
                        villager.getName().getString()), (ServerPlayer) event.getEntity());
                event.setCanceled(true);
            } else {
                Set<Item> itemList = Set.of(Items.RAW_IRON, Items.RAW_COPPER, Items.RAW_GOLD, Items.DIAMOND);
                if (villager.getOffers().stream()
                        .anyMatch(merchantOffer -> itemList.contains(merchantOffer.getResult().getItem()))) {
                    event.setCanceled(true);
                }
            }
        }

        if (event.getSide().isServer() && (event.getTarget() instanceof MinecartChest
                || event.getTarget() instanceof ChestBoat)
                || event.getTarget() instanceof MinecartHopper) event.setCanceled(true);
    }

    public static Set<Item> disallowToRightClickItems = Set.of(
            Items.WATER_BUCKET,
            Items.BUCKET,
            Items.LAVA_BUCKET,
            Items.POWDER_SNOW_BUCKET,
            Items.FLINT_AND_STEEL,
            Items.DIAMOND_HOE,
            Items.IRON_HOE,
            Items.GOLDEN_HOE,
            Items.NETHERITE_HOE,
            Items.STONE_HOE,
            Items.WOODEN_HOE,
            Items.DIAMOND_SHOVEL,
            Items.IRON_SHOVEL,
            Items.GOLDEN_SHOVEL,
            Items.NETHERITE_SHOVEL,
            Items.STONE_SHOVEL,
            Items.WOODEN_SHOVEL,
            Items.DIAMOND_AXE,
            Items.IRON_AXE,
            Items.GOLDEN_AXE,
            Items.NETHERITE_AXE,
            Items.STONE_AXE,
            Items.WOODEN_AXE,
            Items.SAND,
            Items.RED_SAND,
            Items.GRAVEL,
            Items.TNT,
            Items.REDSTONE,
            Items.REDSTONE_TORCH,
            Items.RAIL,
            Items.ACTIVATOR_RAIL,
            Items.DETECTOR_RAIL,
            Items.POWERED_RAIL,
            Items.REDSTONE_BLOCK,
            Items.REPEATER,
            Items.COMPARATOR,
            Items.PISTON,
            Items.STICKY_PISTON,
            Items.OBSERVER,
            Items.HOPPER,
            Items.DISPENSER,
            Items.DROPPER
    );
}
