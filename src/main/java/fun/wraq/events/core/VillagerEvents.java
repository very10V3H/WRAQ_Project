package fun.wraq.events.core;

import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
import fun.wraq.process.system.bank.Bank;
import fun.wraq.process.system.cooking.CookingVillager;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.smith.SmithPlayerData;
import fun.wraq.render.gui.trade.weekly.WeeklyStorePlayerData;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.gui.villagerTrade.TradeListNew;
import fun.wraq.series.crystal.OriginStone;
import fun.wraq.series.events.summer2025.Summer2025StoreRecipe;
import fun.wraq.series.overworld.cold.sc5.dragon.IceDragonTpUtil;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber
public class VillagerEvents {
    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event) {
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
                    Bank.onPlayerInteractWithVillager(player);
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
                case "联合研院大吃货 - 老八" -> {
                    CookingVillager.onPlayerInteractWithVillager(player);
                    event.setCanceled(true);
                    return;
                }
                case "粽子大王" -> {
                    ModNetworking.sendToClient(new ScreenSetS2CPacket(9), player);
                    event.setCanceled(true);
                    return;
                }
                case TradeListNew.WEEKLY_STORE_VILLAGER_NAME -> {
                    WeeklyStorePlayerData.sendDataToClient(player);
                    ModNetworking.sendToClient(new ScreenSetS2CPacket(10), player);
                    event.setCanceled(true);
                    return;
                }
                case OriginStone.VILLAGER_NAME -> {
                    OriginStone.onPlayerRightClick(player);
                    event.setCanceled(true);
                    return;
                }
                case IceDragonTpUtil.VILLAGER_NAME -> {
                    IceDragonTpUtil.onInteractWithVillager(player);
                    event.setCanceled(true);
                    return;
                }
                case Summer2025StoreRecipe.VILLAGER_NAME -> {
                    ModNetworking.sendToClient(new ScreenSetS2CPacket(12), player);
                    event.setCanceled(true);
                    return;
                }
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
}
