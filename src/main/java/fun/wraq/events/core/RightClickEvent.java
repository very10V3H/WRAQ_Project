package fun.wraq.events.core;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.items.misc.PocketItem;
import fun.wraq.process.func.multiblockactive.rightclick.RightClickActiveHandler;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            if (data.contains(fun.wraq.events.core.InventoryCheck.owner)
                    && !data.getString(InventoryCheck.owner).equals(player.getName().getString())) {
                event.setCanceled(true);
            }
        }
        if (disallowToRightClickItems.contains(event.getItemStack().getItem()) && !player.isCreative()) {
            event.setCanceled(true);
        }
        // 需要判断当前与哪个手的物品上交互，否则将会执行两次
        if (event.getSide().isServer() && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            NoTeamInstanceModule.handlePlayerRightClick(player);
            RightClickActiveHandler.handleOnPlayerRightClick(player);
            PocketItem.onRightClick(itemStack, player);
        }
        if (event.getHand().equals(InteractionHand.MAIN_HAND)) {
            if (item instanceof ArmorItem || item.equals(Items.EGG)) {
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
