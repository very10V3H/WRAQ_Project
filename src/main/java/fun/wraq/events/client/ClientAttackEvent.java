package fun.wraq.events.client;

import fun.wraq.common.equip.*;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.Laser;
import fun.wraq.common.equip.impl.PreventLeftClickShoot;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.bowAndSceptreActive.CommonActiveC2SPacket;
import fun.wraq.networking.misc.attack.AttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.BowAttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.ManaAttackRequestC2SPacket;
import fun.wraq.networking.unSorted.SoulSceptreC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientAttackEvent {

    @SubscribeEvent
    public static void clientShootManaAndArrow0(PlayerInteractEvent event) {
        if (event.getSide().isClient()) {
            Player player = event.getEntity();
            Item item = player.getMainHandItem().getItem();
            if (item instanceof WraqBow || item instanceof WraqSceptre) {
                if (item instanceof WraqBow) {
                    ModNetworking.sendToServer(new BowAttackRequestC2SPacket());
                }
                if (item instanceof WraqSceptre && !(item instanceof Laser)) {
                    ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
                }
            }
        }
    }

    @SubscribeEvent
    public static void changeDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity().equals(Minecraft.getInstance().player)) ClientUtils.AnimationTickReset();
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (!(item instanceof WraqPickaxe)) {
                leftClick(player);
                activeBowAndSceptre(player);
                if (!player.isCreative() && !(item instanceof AxeItem)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
            activeBowAndSceptre(player);
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
            activeBowAndSceptre(player);
        }
    }

    // 客户端侧左键发包
    public static void leftClick(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (item instanceof WraqSword) {
            ModNetworking.sendToServer(new AttackRequestC2SPacket());
        }
        if (item instanceof WraqBow && !(item instanceof PreventLeftClickShoot)) {
            ModNetworking.sendToServer(new BowAttackRequestC2SPacket());
        }
        if (item instanceof WraqSceptre && !(item instanceof Laser)
                && !(item instanceof PreventLeftClickShoot)) {
            ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
        }
    }

    public static void activeBowAndSceptre(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if ((item instanceof WraqSceptre || item instanceof WraqBow) && item instanceof ActiveItem) {
            ModNetworking.sendToServer(new CommonActiveC2SPacket(item.getDefaultInstance()));
        }
        if (item.equals(ModItems.SOUL_SCEPTRE.get())) {
            ModNetworking.sendToServer(new SoulSceptreC2SPacket());
        }
    }
}
