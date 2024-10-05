package fun.wraq.events.core;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        if (Utils.ItemRightClickCheck.containsKey(event.getItemStack().getItem()) && !player.isCreative())
            event.setCanceled(true);

        NoTeamInstanceModule.handlePlayerRightClick(player);
    }

    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event) {
        if (event.getSide().isServer() && event.getTarget() instanceof Villager villager) {
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) TradeList.setTradeContent();
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
            }
        }

        if (event.getSide().isServer() && (event.getTarget() instanceof MinecartChest
                || event.getTarget() instanceof ChestBoat)
                || event.getTarget() instanceof MinecartHopper
                || event.getTarget() instanceof Allay) event.setCanceled(true);
    }
}
