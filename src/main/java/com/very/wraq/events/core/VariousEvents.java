package com.very.wraq.events.core;

import com.mojang.logging.LogUtils;
import com.very.wraq.Items.Prefix.PrefixInfo;
import com.very.wraq.commands.changeable.PrefixCommand;
import com.very.wraq.events.instance.CastleSecondFloor;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Mod.EventBusSubscriber
public class VariousEvents {

    @SubscribeEvent
    public static void itemEntity(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        ItemEntity itemEntity = event.getItem();
        ItemStack itemStack = itemEntity.getItem();
        if (!InventoryCheck.itemOwnerCorrect(player, itemStack)) event.setCanceled(true);
        else {
            Item item = itemStack.getItem();
            if (itemStack.getTagElement(Utils.MOD_ID) != null) {
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                if (InventoryCheck.boundingList.isEmpty()) InventoryCheck.setBoundingList();
                if (!Utils.mainHandTag.containsKey(item) && !Utils.offHandTag.containsKey(item)
                        && !Utils.armorTag.containsKey(item) && !InventoryCheck.boundingList.contains(item)) {
                    data.remove(InventoryCheck.owner);
                    if (data.isEmpty()) itemStack.removeTagKey(Utils.MOD_ID);
                }
            }
        }
    }

    @SubscribeEvent
    public static void ChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (!player.level().isClientSide) {
            ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);
        }
    }

    @SubscribeEvent
    public static void Clone(PlayerEvent.Clone event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.isWasDeath()) {
                Player player = event.getOriginal();
                ServerPlayer serverPlayer = (ServerPlayer) player;
                CompoundTag data = player.getPersistentData();
                for (int i = 0; i < Utils.AttributeName.length; i++) {
                    if (data.contains(Utils.AttributeName[i])) {
                        data.putDouble(Utils.AttributeName[i], 0);
                    }
                }
                for (String string : StringUtils.Crest.CrestList) {
                    if (data.contains(string)) data.putInt(string, 0);
                }
                Compute.CuriosAttribute.resetCuriosList(player);
                Compute.broad(event.getEntity().level(), Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal(event.getEntity().getName().getString() + "在探索过程中身负重伤，经过救治恢复了活力。").withStyle(ChatFormatting.WHITE)));
                event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
                serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD), 437.5, 69, 916.6, 0, 0);
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), (ServerPlayer) event.getEntity());
                Utils.instanceList.forEach(instance -> {
                    if (instance.getCurrentChallengePlayerTeam() != null && instance.getCurrentChallengePlayerTeam().getPlayerList().contains(player)) {
                        instance.addDeadTimes();
                    }
                });
                Utils.PlayerDeadTimeMap.put(player.getName().getString(), player.getServer().getTickCount() + 6000);
            } else {
                Player player = event.getOriginal();
                CompoundTag data = player.getPersistentData();
                for (int i = 0; i < Utils.AttributeName.length; i++) {
                    if (data.contains(Utils.AttributeName[i])) {
                        data.putDouble(Utils.AttributeName[i], 0);
                    }
                }
                for (String string : StringUtils.Crest.CrestList) {
                    if (data.contains(string)) data.putInt(string, 0);
                }
                Compute.CuriosAttribute.resetCuriosList(player);
                event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), (ServerPlayer) event.getEntity());
            }
        }
    }

    @SubscribeEvent
    public static void TossCheck(ItemTossEvent event) throws IOException {
        ItemStack itemStack = event.getEntity().getItem();
        Player player = event.getPlayer();
        CompoundTag data = itemStack.getTagElement(Utils.MOD_ID);
        boolean dropped = true;
        if (!player.isCreative() && !event.getPlayer().level().isClientSide) {
            if (data != null && data.contains(InventoryCheck.owner)) {
                ItemStack item = event.getEntity().getItem();
                event.getPlayer().addItem(item);
                event.setCanceled(true);
                dropped = false;
            }
            if (Utils.mainHandTag.containsKey(itemStack.getItem()) || Utils.offHandTag.containsKey(itemStack.getItem())
                    || Utils.armorTag.containsKey(itemStack.getItem()) || Utils.customizedList.contains(itemStack.getItem())) {
                ItemStack item = event.getEntity().getItem();
                event.getPlayer().addItem(item);
                event.setCanceled(true);
                dropped = false;
            }
            if (itemStack.getItem().toString().contains("backpack") && !player.isCreative()) {
                event.getPlayer().addItem(itemStack);
                event.setCanceled(true);
                dropped = false;
            }
            if (dropped) {
                LogUtils.getLogger().info("{} {} {}", player.getName().getString(), Utils.LogTypes.dropped, itemStack);
            }
        }
    }

    @SubscribeEvent
    public static void PickUpItem(PlayerEvent.ItemPickupEvent event) throws IOException {
        if (!event.getEntity().level().isClientSide && event.getStack().is(ModItems.Value.get())) {
            Compute.itemStackRemove(event.getEntity().getInventory(), ModItems.Value.get(),
                    Compute.itemStackCount(event.getEntity().getInventory(), ModItems.Value.get()));
        }
        CastleSecondFloor.PlayerPickItem(event.getEntity(), event.getStack());
    }

    @SubscribeEvent
    public static void Chat1(ClientChatReceivedEvent event) {
        if (!event.isSystem()) {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String showTime = format.format(time);
            String MSG = event.getMessage().getString();
            boolean FindFirstIndexFlag = false;
            int FirstIndex = 0;
            int SecondIndex = 0;
            for (int i = 0; i < MSG.length(); i++) {
                if (MSG.charAt(i) == '<' && !FindFirstIndexFlag) {
                    FirstIndex = i;
                    FindFirstIndexFlag = true;
                }
                if (MSG.charAt(i) == '>' && FindFirstIndexFlag) {
                    SecondIndex = i;
                    break;
                }
            }
            if (SecondIndex == 0) return;
            String NewMSG = MSG.substring(SecondIndex + 1);
            String Name = MSG.substring(FirstIndex + 1, SecondIndex);
            int FirstLvIndex = 0;
            int SecondLvIndex = 0;
            for (int i = 0; i < Name.length(); i++) {
                if (Name.charAt(i) == '[') FirstLvIndex = i;
                if (Name.charAt(i) == ']') SecondLvIndex = i;
            }
            int SecondPrefixIndex = 0;
            for (int i = 1; i < Name.length(); i++) {
                if (Name.charAt(i) == '|') {
                    SecondPrefixIndex = i;
                    break;
                }
            }
            String Level = Name.substring(FirstLvIndex + 1, SecondLvIndex);
            int xp = Integer.parseInt(Name.substring(FirstLvIndex + 4, SecondLvIndex));
            if (SecondPrefixIndex == 0) return;
            String NewName = Name.substring(SecondLvIndex + 1);
            String Prefix = Name.substring(1, SecondPrefixIndex);
            UUID uuid = event.getSender();
            Component component = Component.literal("初来乍到").withStyle(CustomStyle.styleOfMine);
            if (PrefixCommand.clientPrefixInfo.containsKey(uuid)) {
                PrefixInfo prefixInfo = PrefixCommand.clientPrefixInfo.get(uuid);
                component = Component.literal(prefixInfo.getPrefix()).withStyle(Style.EMPTY.withColor(TextColor.parseColor(prefixInfo.getColor())));
            }
            event.setMessage(Component.literal("[" + showTime + "]").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("|").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                    append(component).
                    append(Component.literal("|").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                    append(Component.literal("[").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                    append(Component.literal(Level).withStyle(Utils.levelStyleList.get(xp / 25))).
                    append(Component.literal("]").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                    append(Component.literal(NewName).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(" >> ").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(NewMSG).withStyle(ChatFormatting.WHITE)));
            ClientUtils.Sounds = 12;
        }
        if (event.isSystem()) {
            if (event.getMessage().getString().contains("chasing")) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void Dimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (player.experienceLevel < 50 && event.getTo().equals(ServerLevel.NETHER)) {
            player.changeDimension(player.level().getServer().getLevel(Level.OVERWORLD));
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("50级后再来探索吧！").withStyle(ChatFormatting.WHITE)));
        }
    }

    @SubscribeEvent
    public static void XpP(PlayerXpEvent.XpChange event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void pickUpXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ExperienceOrb orb = event.getOrb();
        if (orb.getPersistentData().contains(MobSpawn.fromMobSpawnTag)) {
            if (orb.getPersistentData().contains(MobSpawn.fromSlime)) {
                Compute.givePercentExpToPlayer(player, 0.003, PlayerAttributes.expUp(player), orb.value);
            } else Compute.givePercentExpToPlayer(player, 0.01, PlayerAttributes.expUp(player), orb.value);
        }
    }

    @SubscribeEvent
    public static void PreventBreak(BlockEvent.FarmlandTrampleEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void EnchantmentBook(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getItemStack();
        if (event.getSide().isServer() && itemStack.getOrCreateTag().contains("StoredEnchantments") && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            Player player = event.getEntity();
            player.setItemInHand(InteractionHand.MAIN_HAND, Items.AIR.getDefaultInstance());
            Compute.givePercentExpToPlayer(player, 0.1, 0, player.experienceLevel);
        }
    }

    @SubscribeEvent
    public static void NameFormat(PlayerEvent.NameFormat event) {
        event.setPhase(EventPriority.LOWEST);
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            CompoundTag data = player.getPersistentData();
            String prefix = "初来乍到";
            String color = String.valueOf(CustomStyle.styleOfMine.getColor());
            if (data.contains(PrefixCommand.prefix)) prefix = data.getString(PrefixCommand.prefix);
            if (data.contains(PrefixCommand.prefixColor)) color = data.getString(PrefixCommand.prefixColor);
            event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.GOLD).
                    append(Component.literal(prefix).withStyle(Style.EMPTY.withColor(TextColor.parseColor(color)))).
                    append(Component.literal("|").withStyle(ChatFormatting.GOLD)).
                    append(Component.literal("[Lv." + player.experienceLevel + "]").withStyle(Utils.levelStyleList.get(Math.min(Utils.levelStyleList.size() - 1, player.experienceLevel / 25)))).
                    append(Component.literal(event.getDisplayname().getString()).withStyle(CustomStyle.styleOfBloodMana)));
        }
        if (player.level().isClientSide) {
            String prefix = "初来乍到";
            String color = String.valueOf(CustomStyle.styleOfMine.getColor());
            int level = 0;
            if (PrefixCommand.clientPrefixInfo.containsKey(player.getUUID())) {
                PrefixInfo prefixInfo = PrefixCommand.clientPrefixInfo.get(player.getUUID());
                prefix = prefixInfo.getPrefix();
                color = prefixInfo.getColor();
                level = prefixInfo.getLevel();
            }
            event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.GOLD).
                    append(Component.literal(prefix).withStyle(Style.EMPTY.withColor(TextColor.parseColor(color)))).
                    append(Component.literal("|").withStyle(ChatFormatting.GOLD)).
                    append(Component.literal("[Lv." + level + "]").withStyle(Utils.levelStyleList.get(Math.min(Utils.levelStyleList.size() - 1, level / 25)))).
                    append(Component.literal(event.getDisplayname().getString()).withStyle(CustomStyle.styleOfBloodMana)));
        }
    }

    @SubscribeEvent
    public static void TabNameFormat(PlayerEvent.TabListNameFormat event) {
        event.setPhase(EventPriority.LOWEST);
        Player player = event.getEntity();
        event.setDisplayName(player.getDisplayName());
    }

    @SubscribeEvent
    public static void armorStandFixed(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof ArmorStand) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void armorStandFixed(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getTarget() instanceof ArmorStand) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void livingConversionEvent(LivingConversionEvent event) {
        if (event.getEntity() instanceof Villager) event.setCanceled(true);
    }
}
