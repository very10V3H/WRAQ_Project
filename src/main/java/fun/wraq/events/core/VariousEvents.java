package fun.wraq.events.core;

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
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.items.prefix.PrefixInfo;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import fun.wraq.render.hud.networking.ExpGetResetS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.instance.series.warden.gem.AncientEchoGem;
import fun.wraq.series.overworld.divine.DivineUtils;
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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.event.CurioEquipEvent;

import java.util.*;

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
        if (!InventoryCheck.itemOwnerCorrect(player, itemStack) && !player.isCreative()) {
            event.setCanceled(true);
        } else {
            if (itemStack.getTagElement(Utils.MOD_ID) != null
                    && InventoryOperation.hasRemainSpaceToPick(player, itemStack)) {
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                if (!Utils.mainHandTag.containsKey(item) && !Utils.offHandTag.containsKey(item)
                        && !Utils.armorTag.containsKey(item) && !InventoryCheck.getBoundingList().contains(item)) {
                    if (data.isEmpty()) {
                        itemStack.removeTagKey(Utils.MOD_ID);
                    }
                }
                if (data.contains("TossFrom")) {
                    Security.recordItemStream(data.getString("TossFrom"), player.getName().getString(),
                            itemStack, Security.RecordType.TOSS_PICK);
                    data.remove("TossFrom");
                    if (data.isEmpty()) itemStack.removeTagKey(Utils.MOD_ID);
                }
                if (InventoryCheck.containOwnerTag(itemStack)) {
                    Security.recordItemStream(player.getName().getString(),
                            itemStack, Security.RecordType.PICK_BOUNDING_ITEM);
                    Vec3 pos = player.position();
                    Compute.sendFormatMSG(player, Te.s("安全", CustomStyle.styleOfFlexible),
                            Te.s("你在", "("
                                    + String.format("%.0f", pos.x) + ","
                                    + String.format("%.0f", pos.y) + ","
                                    + String.format("%.0f", pos.z) + ")", " 拾取了 ", itemStack));
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
            DivineUtils.setHolyLightCount(player, 0);
            AncientEchoGem.clear(player);
        } else {
            ClientUtils.debuffTimes.clear();
        }
    }

    @SubscribeEvent
    public static void checkToss(ItemTossEvent event) {
        ItemStack stack = event.getEntity().getItem();
        Player player = event.getPlayer();
        CompoundTag data = stack.getTagElement(Utils.MOD_ID);
        boolean dropped = true;
        Item item = stack.getItem();
        if (!player.isCreative() && !event.getPlayer().level().isClientSide) {
            if ((data != null && data.contains(InventoryCheck.owner))
                    || InventoryCheck.getBoundingList().contains(item)) {
                if (InventoryCheck.getBoundingList().contains(item)
                        && (data == null || !data.contains(InventoryCheck.owner))) {
                    InventoryCheck.addOwnerTagToItemStack(player, stack);
                }
                InventoryOperation.giveItemStackWithMSG(player, stack);
                event.setCanceled(true);
                dropped = false;
            }
            if (Utils.weaponList.contains(item)) {
                event.getPlayer().addItem(stack);
                event.setCanceled(true);
                dropped = false;
                Compute.sendFormatMSG(player, Te.s("安全", CustomStyle.styleOfFlexible),
                        Te.s("为防止武器意外丢失，禁用了武器的丢弃。"));
            }
            if (Utils.customizedList.contains(item)) {
                event.getPlayer().addItem(stack);
                event.setCanceled(true);
                dropped = false;
            }
            if (item.toString().contains("backpack") && !player.isCreative()) {
                event.getPlayer().addItem(stack);
                event.setCanceled(true);
                dropped = false;
            }
            if (dropped) {
                Security.recordToss(player.getName().getString(), stack);
                stack.getOrCreateTagElement(Utils.MOD_ID).putString("TossFrom", player.getName().getString());
            }
        }
    }

    @SubscribeEvent
    public static void PickUpItem(PlayerEvent.ItemPickupEvent event) {
        if (!event.getEntity().level().isClientSide && event.getStack().is(ModItems.VALUE.get())) {
            InventoryOperation.removeItem(event.getEntity().getInventory(), ModItems.VALUE.get(),
                    InventoryOperation.itemStackCount(event.getEntity().getInventory(), ModItems.VALUE.get()));
        }
    }

    @SubscribeEvent
    public static void Chat1(ClientChatReceivedEvent event) {
        if (!event.isSystem()) {
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
            int xp = Integer.parseInt(Name.substring(FirstLvIndex + 4, SecondLvIndex));
            if (SecondPrefixIndex == 0) return;
            String playerName = Name.substring(SecondLvIndex + 1);
            UUID uuid = event.getSender();
            MutableComponent prefix = Te.s("");
            if (PrefixCommand.clientPrefixInfo.containsKey(uuid)) {
                PrefixInfo prefixInfo = PrefixCommand.clientPrefixInfo.get(uuid);
                prefix.append(Component.literal(prefixInfo.getPrefix())
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor(prefixInfo.getColor()))));
            } else {
                prefix.append(Component.literal("初来乍到").withStyle(CustomStyle.styleOfMine));
            }
            MutableComponent rankPrefix = Te.s("");
            if (RankData.clientPlayerCurrentRankMap.containsKey(uuid)) {
                String rank = RankData.clientPlayerCurrentRankMap.get(uuid);
                if (!rank.equals("null")) {
                    rankPrefix.append(Te.s("∮" + rank + "∮", RankData.rankStyleMap.get(rank)));
                }
            }
            MutableComponent locationInfo = Te.s("");
            if (ClientUtils.clientPlayerLevelIdMap.containsKey(uuid)) {
                int levelId = ClientUtils.clientPlayerLevelIdMap.get(uuid);
                Vec3 pos = ClientUtils.clientPlayerLocationMap.get(uuid);
                if (levelId == 0) {
                    locationInfo = getLocationInfo(MyWayPoint.overworldPointList, pos);
                } else if (levelId == 1) {
                    locationInfo = getLocationInfo(MyWayPoint.netherPointList, pos);
                } else if (levelId == 2) {
                    locationInfo = Te.s("终界", CustomStyle.styleOfEnd);
                }
            }
            event.setMessage(Te.s(
                    "|", CustomStyle.styleOfStone, ChatFormatting.BOLD,
                    locationInfo, "|", CustomStyle.styleOfStone, ChatFormatting.BOLD,
                    rankPrefix,
                    "|", ChatFormatting.GOLD, ChatFormatting.BOLD,
                    prefix, "|", ChatFormatting.GOLD, ChatFormatting.BOLD,
                    "[", ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD,
                    Utils.getLevelDescription(xp), "]", ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD,
                    playerName, " >> ", CustomStyle.styleOfStone, NewMSG));
            ClientUtils.Sounds = 12;
        }
        if (event.isSystem()) {
            String msg = event.getMessage().getString();
            if (msg.contains("chasing") || msg.contains("退出") || msg.contains("very_H") && msg.contains("游戏")) {
                event.setCanceled(true);
            }
        }
    }

    @Nullable
    private static MutableComponent getLocationInfo(List<MyWayPoint> myWayPoints, Vec3 pos) {
        MyWayPoint wayPoint = myWayPoints.stream().min(new Comparator<MyWayPoint>() {
            @Override
            public int compare(MyWayPoint o1, MyWayPoint o2) {
                return (int) (o1.pos.distanceTo(pos) - o2.pos.distanceTo(pos));
            }
        }).orElse(null);
        if (wayPoint != null && wayPoint.style != null) {
            if (wayPoint.pos.distanceTo(pos) > 100) {
                return Te.s("野外", CustomStyle.styleOfFlexible);
            }
            return Te.s(wayPoint.name, wayPoint.style);
        }
        return null;
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
    public static void onCurioEquip(CurioEquipEvent event) {
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

    public static Map<String, Integer> lastRefreshTickMap = new HashMap<>();

    @SubscribeEvent
    public static void onCuriosChange(CurioChangeEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            if (lastRefreshTickMap.getOrDefault(player.getName().getString(), -1) != Tick.get()) {
                lastRefreshTickMap.put(player.getName().getString(), Tick.get());
                List<ItemStack> curiosList = new ArrayList<>();
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    int size = iCuriosItemHandler.getEquippedCurios().getSlots();
                    Set<Item> curiosItemSet = new HashSet<>();
                    for (int i = 0; i < size; i++) {
                        ItemStack stack = iCuriosItemHandler.getEquippedCurios().getStackInSlot(i);
                        if (stack.is(Items.AIR)) continue;
                        if (!curiosItemSet.contains(stack.getItem())) {
                            if (!(stack.getItem() instanceof RepeatableCurios)) {
                                curiosItemSet.add(stack.getItem());
                            }
                            curiosList.add(stack);
                        }
                    }
                });
                Compute.CuriosAttribute.curiosListCache.put(player, curiosList);
                Set<Item> set = new HashSet<>(Compute.CuriosAttribute.getDistinctCuriosList(player)
                        .stream().map(ItemStack::getItem)
                        .toList());
                Compute.CuriosAttribute.curiosSetCache.put(player, set);
            }
        }
    }

    @SubscribeEvent
    public static void fixProjectileErrorDamageToPlayer(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        if (!(projectile instanceof MyArrow)
                && !(projectile instanceof ManaArrow)
                && !(projectile instanceof SwordAir)
                && !(projectile instanceof ThrownPotion)
                && !(projectile instanceof ShulkerBullet)) {
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.STOP_AT_CURRENT);
            projectile.remove(Entity.RemovalReason.KILLED);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void livingDrop(LivingDropsEvent event) {
        event.setCanceled(true);
    }
}
