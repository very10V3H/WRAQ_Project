package fun.wraq.events.client;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.Laser;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.misc.attack.BowAttackRequestC2SPacket;
import fun.wraq.networking.misc.attack.ManaAttackRequestC2SPacket;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.bowAndSceptreActive.CommonActiveC2SPacket;
import fun.wraq.networking.misc.attack.AttackRequestC2SPacket;
import fun.wraq.networking.unSorted.SoulSceptreC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
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
            if (Utils.bowTag.containsKey(item) || Utils.sceptreTag.containsKey(item)) {
                leftClick(player);
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
            if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof WraqPickaxe)) {
                leftClick(player);
                if (!player.isCreative()) {
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
        }
    }

    @SubscribeEvent
    public static void clientAttackTimeAndCount(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide && event.getEntity().equals(Minecraft.getInstance().player)) {
            Player player = event.getEntity();
            leftClick(player);
        }
    }

    // 客户端侧左键发包
    public static void leftClick(Player player) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Utils.swordTag.containsKey(item)) {
            ModNetworking.sendToServer(new AttackRequestC2SPacket());
        }
        if (Utils.bowTag.containsKey(item)) {
            ModNetworking.sendToServer(new BowAttackRequestC2SPacket());
        }
        if (Utils.sceptreTag.containsKey(item) && !(item instanceof Laser)) {
            ModNetworking.sendToServer(new ManaAttackRequestC2SPacket());
        }
        if ((item instanceof WraqSceptre || item instanceof WraqBow) && item instanceof ActiveItem) {
            ModNetworking.sendToServer(new CommonActiveC2SPacket(item.getDefaultInstance()));
        }
        if (item.equals(ModItems.SoulSceptre.get())) {
            ModNetworking.sendToServer(new SoulSceptreC2SPacket());
        }
    }
}
