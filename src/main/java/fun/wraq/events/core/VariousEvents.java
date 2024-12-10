package fun.wraq.events.core;

import fun.wraq.Items.Prefix.PrefixInfo;
import fun.wraq.commands.changeable.PrefixCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.render.hud.networking.ExpGetResetS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioEquipEvent;

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
        Item item = itemStack.getItem();
        if (item instanceof WraqItem wraqItem) {
            if (wraqItem.onPickupListener != null) {
                wraqItem.onPickupListener.onPickup(player);
                MySound.soundToPlayer(player, SoundEvents.ITEM_PICKUP);
                event.setCanceled(true);
                itemEntity.remove(Entity.RemovalReason.KILLED);
            }
        }
        if (!InventoryCheck.itemOwnerCorrect(player, itemStack)) event.setCanceled(true);
        else {
            if (itemStack.getTagElement(Utils.MOD_ID) != null) {
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                if (InventoryCheck.boundingList.isEmpty()) InventoryCheck.setBoundingList();
                if (!Utils.mainHandTag.containsKey(item) && !Utils.offHandTag.containsKey(item)
                        && !Utils.armorTag.containsKey(item) && !InventoryCheck.boundingList.contains(item)) {
                    if (data.isEmpty()) itemStack.removeTagKey(Utils.MOD_ID);
                }

                if (data.contains("TossFrom")) {
                    Security.recordItemStream(data.getString("TossFrom"), player.getName().getString(),
                            itemStack, Security.RecordType.TOSS_PICK);
                    data.remove("TossFrom");
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
            Player player = event.getOriginal();
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (event.isWasDeath()) {
                serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),
                        437.5, 69, 916.6, 0, 0);
                NewTeamInstanceHandler.getInstances().forEach(instance -> {
                    if (instance.players.contains(player)) {
                        ++instance.deadTimes;
                    }
                });
                Utils.PlayerDeadTimeMap.put(player.getName().getString(), Tick.get() + 6000);
            }
            ModNetworking.sendToClient(new ExpGetResetS2CPacket(), serverPlayer);
            event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
            ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);
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
                Security.recordToss(player.getName().getString(), itemStack);
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putString("TossFrom", player.getName().getString());
            }
        }
    }

    @SubscribeEvent
    public static void PickUpItem(PlayerEvent.ItemPickupEvent event) {
        if (!event.getEntity().level().isClientSide && event.getStack().is(ModItems.Value.get())) {
            InventoryOperation.removeItem(event.getEntity().getInventory(), ModItems.Value.get(),
                    InventoryOperation.itemStackCount(event.getEntity().getInventory(), ModItems.Value.get()));
        }
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
            UUID uuid = event.getSender();
            MutableComponent component = Te.s("");
            if (PrefixCommand.clientPrefixInfo.containsKey(uuid)) {
                PrefixInfo prefixInfo = PrefixCommand.clientPrefixInfo.get(uuid);
                component.append(Component.literal(prefixInfo.getPrefix())
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor(prefixInfo.getColor()))));
            } else {
                component.append(Component.literal("初来乍到").withStyle(CustomStyle.styleOfMine));
            }
            MutableComponent rankComponent = Te.s("");
            if (RankData.clientPlayerCurrentRankMap.containsKey(uuid)) {
                String rank = RankData.clientPlayerCurrentRankMap.get(uuid);
                if (!rank.equals("null")) {
                    rankComponent.append(Te.s("∮" + rank + "∮", RankData.rankStyleMap.get(rank)));
                }
            }
            event.setMessage(Component.literal("[" + showTime + "]").withStyle(ChatFormatting.GRAY).
                    append(rankComponent).
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
            String msg = event.getMessage().getString();
            if (msg.contains("chasing") || msg.contains("退出")) {
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
        CompoundTag data = orb.getPersistentData();
        if (data.contains(MobSpawn.fromMobSpawnTag)) {
            Compute.givePercentExpToPlayer(player,
                    data.getDouble(ItemAndRate.expRate), PlayerAttributes.expUp(player), orb.value);
        }
        if (data.contains(MineSpur.fromMineReward)) {
            Compute.givePercentExpToPlayer(player, data.getDouble(ItemAndRate.expRate), 0, orb.value);
        }
    }

    @SubscribeEvent
    public static void PreventBreak(BlockEvent.FarmlandTrampleEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void NameFormat(PlayerEvent.NameFormat event) {
        event.setPhase(EventPriority.LOWEST);
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            MutableComponent component = Te.s("");
            CompoundTag data = player.getPersistentData();
            String prefix = "初来乍到";
            String color = String.valueOf(CustomStyle.styleOfMine.getColor());
            String rank = RankData.getCurrentRank(player);
            if (!rank.equals("null")) {
                component.append(Te.s("∮" + rank + "∮", RankData.rankStyleMap.get(rank)));
            }
            if (data.contains(PrefixCommand.prefix)) prefix = data.getString(PrefixCommand.prefix);
            if (data.contains(PrefixCommand.prefixColor)) color = data.getString(PrefixCommand.prefixColor);
            component.append(Te.s("|", ChatFormatting.GOLD,
                    prefix, Style.EMPTY.withColor(TextColor.parseColor(color)), "|", ChatFormatting.GOLD,
                    "[Lv." + player.experienceLevel + "]", Utils.getLevelStyle(player.experienceLevel),
                    event.getDisplayname().getString(), CustomStyle.styleOfBloodMana));
            event.setDisplayname(component);
        }
        if (player.level().isClientSide) {
            MutableComponent component = Te.s("");
            String prefix = "初来乍到";
            String color = String.valueOf(CustomStyle.styleOfMine.getColor());
            int level = 0;
            if (RankData.clientPlayerCurrentRankMap.containsKey(player.getUUID())) {
                String rank = RankData.clientPlayerCurrentRankMap.get(player.getUUID());
                if (!rank.equals("null")) {
                    component.append(Te.s("∮" + rank + "∮", RankData.rankStyleMap.get(rank)));
                }
            }
            if (PrefixCommand.clientPrefixInfo.containsKey(player.getUUID())) {
                PrefixInfo prefixInfo = PrefixCommand.clientPrefixInfo.get(player.getUUID());
                prefix = prefixInfo.getPrefix();
                color = prefixInfo.getColor();
                level = prefixInfo.getLevel();
            }
            component.append(Te.s("|", ChatFormatting.GOLD,
                    prefix, Style.EMPTY.withColor(TextColor.parseColor(color)), "|", ChatFormatting.GOLD,
                    "[Lv." + level + "]", Utils.getLevelStyle(level),
                    event.getDisplayname().getString(), CustomStyle.styleOfBloodMana));
            event.setDisplayname(component);
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

    @SubscribeEvent
    public static void curiosTipEvent(CurioEquipEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            ItemStack itemStack = event.getStack();
            if (Compute.CuriosAttribute.getDistinctCuriosList(player)
                    .stream().anyMatch(
                            stack -> stack.is(itemStack.getItem()) && !(stack.getItem() instanceof RepeatableCurios)
                                    && stack.getItem() instanceof WraqCurios)) {
                Compute.sendFormatMSG(player, Te.s("饰品", ChatFormatting.LIGHT_PURPLE),
                        Te.s("该饰品因重复而未生效:", itemStack.getDisplayName()));
            }
        }
    }
}
