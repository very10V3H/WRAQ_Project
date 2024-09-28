package fun.wraq.process.system.element.originSummon;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.*;

public class OriginSummon {
    public static Slime originLifeElement;
    public static ServerBossEvent lifeBossInfo;
    public static Zombie originWaterElement;
    public static ServerBossEvent waterBossInfo;
    public static Blaze originFireElement;
    public static ServerBossEvent fireBossInfo;
    public static Zombie originStoneElement;
    public static ServerBossEvent stoneBossInfo;
    public static Zombie originIceElement;
    public static ServerBossEvent iceBossInfo;
    public static Stray originWindElement;
    public static ServerBossEvent windBossInfo;
    public static Witch originLightningElement;
    public static ServerBossEvent lightningBossInfo;

    public static void DetectElementPiece(TickEvent.LevelTickEvent event) {
        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int type = DetectItemEntity(event.level, new Vec3(1124.5, 122, 1076.5), 4, new ArrayList<>() {{
                add(ModItems.LifeElementPiece0.get());
                add(ModItems.LifeElementPiece1.get());
                add(ModItems.LifeElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originLifeElement == null || !originLifeElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1124.5, 122, 1076.5), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初生机元素已出现！").withStyle(CustomStyle.styleOfLife));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originLifeElement != null) originLifeElement.remove(Entity.RemovalReason.KILLED);
                    originLifeElement = new Slime(EntityType.SLIME, event.level);
                    MobSpawn.setMobCustomName(originLifeElement, ModItems.MobArmorOriginLifeElement.get(),
                            Component.literal("原初生机元素").withStyle(CustomStyle.styleOfLife));
                    originLifeElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginLifeElement.get().getDefaultInstance());
                    originLifeElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorLifeElementChest.get().getDefaultInstance());
                    originLifeElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorLifeElementLeggings.get().getDefaultInstance());
                    originLifeElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorLifeElementBoots.get().getDefaultInstance());
                    originLifeElement.setSize(4, true);
                    originLifeElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originLifeElement.setHealth(originLifeElement.getMaxHealth());
                    originLifeElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originLifeElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originLifeElement.moveTo(new Vec3(1124.5, 123, 1076.5));
                    event.level.addFreshEntity(originLifeElement);

                    lifeBossInfo = (ServerBossEvent) (new ServerBossEvent(originLifeElement.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        lifeBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originLifeElement != null && lifeBossInfo != null) {
                lifeBossInfo.setProgress(originLifeElement.getHealth() / originLifeElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originLifeElement, originLifeElement, Element.life, 5, true, 1);
            }
            if (lifeBossInfo != null && (originLifeElement == null || !originLifeElement.isAlive())) {
                lifeBossInfo.removeAllPlayers();
                lifeBossInfo = null;
            }
        }

        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int type = DetectItemEntity(event.level, new Vec3(861, 64, 380), 4, new ArrayList<>() {{
                add(ModItems.WaterElementPiece0.get());
                add(ModItems.WaterElementPiece1.get());
                add(ModItems.WaterElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originWaterElement == null || !originWaterElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(861, 64, 380), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初碧水元素已出现！").withStyle(CustomStyle.styleOfWater));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originWaterElement != null) originWaterElement.remove(Entity.RemovalReason.KILLED);
                    originWaterElement = new Zombie(EntityType.ZOMBIE, event.level);
                    MobSpawn.setMobCustomName(originWaterElement, ModItems.MobArmorOriginWaterElement.get(),
                            Component.literal("原初碧水元素").withStyle(CustomStyle.styleOfWater));
                    originWaterElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginWaterElement.get().getDefaultInstance());
                    originWaterElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorWaterElementChest.get().getDefaultInstance());
                    originWaterElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorWaterElementLeggings.get().getDefaultInstance());
                    originWaterElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorWaterElementBoots.get().getDefaultInstance());
                    originWaterElement.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                    originWaterElement.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.SnowShield.get()));
                    originWaterElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originWaterElement.setHealth(originWaterElement.getMaxHealth());
                    originWaterElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originWaterElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originWaterElement.moveTo(new Vec3(861, 65, 380));
                    event.level.addFreshEntity(originWaterElement);
                    waterBossInfo = (ServerBossEvent) (new ServerBossEvent(originWaterElement.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        waterBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originWaterElement != null && waterBossInfo != null) {
                waterBossInfo.setProgress(originWaterElement.getHealth() / originWaterElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originWaterElement, originWaterElement, Element.water, 5, true, 1);
            }
            if (waterBossInfo != null && (originWaterElement == null || !originWaterElement.isAlive())) {
                waterBossInfo.removeAllPlayers();
                waterBossInfo = null;
            }
        }

        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int type = DetectItemEntity(event.level, new Vec3(1221.5, 64, 1438.5), 4, new ArrayList<>() {{
                add(ModItems.StoneElementPiece0.get());
                add(ModItems.StoneElementPiece1.get());
                add(ModItems.StoneElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originStoneElement == null || !originStoneElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1221.5, 64, 1438.5), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初层岩元素已出现！").withStyle(CustomStyle.styleOfStone));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originStoneElement != null) originStoneElement.remove(Entity.RemovalReason.KILLED);
                    originStoneElement = new Zombie(EntityType.ZOMBIE, event.level);
                    MobSpawn.setMobCustomName(originStoneElement, ModItems.MobArmorOriginStoneElement.get(),
                            Component.literal("原初层岩元素").withStyle(CustomStyle.styleOfStone));
                    originStoneElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginStoneElement.get().getDefaultInstance());
                    originStoneElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorStoneElementChest.get().getDefaultInstance());
                    originStoneElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorStoneElementLeggings.get().getDefaultInstance());
                    originStoneElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorStoneElementBoots.get().getDefaultInstance());
                    originStoneElement.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_PICKAXE));
                    originStoneElement.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.MineShield.get()));
                    originStoneElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originStoneElement.setHealth(originStoneElement.getMaxHealth());
                    originStoneElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originStoneElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originStoneElement.moveTo(new Vec3(1221.5, 65, 1438.5));
                    event.level.addFreshEntity(originStoneElement);
                    stoneBossInfo = (ServerBossEvent) (new ServerBossEvent(originStoneElement.getDisplayName(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        stoneBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originStoneElement != null && stoneBossInfo != null) {
                stoneBossInfo.setProgress(originStoneElement.getHealth() / originStoneElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originStoneElement, originStoneElement, Element.stone, 5, true, 1);
            }
            if (stoneBossInfo != null && (originStoneElement == null || !originStoneElement.isAlive())) {
                stoneBossInfo.removeAllPlayers();
                stoneBossInfo = null;
            }
        }

        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int type = DetectItemEntity(event.level, new Vec3(1660.5, 72, 1985.5), 4, new ArrayList<>() {{
                add(ModItems.IceElementPiece0.get());
                add(ModItems.IceElementPiece1.get());
                add(ModItems.IceElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originIceElement == null || !originIceElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1660.5, 72, 1985.5), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初凛冰元素已出现！").withStyle(CustomStyle.styleOfIce));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originIceElement != null) originIceElement.remove(Entity.RemovalReason.KILLED);
                    originIceElement = new Zombie(EntityType.ZOMBIE, event.level);
                    MobSpawn.setMobCustomName(originIceElement, ModItems.MobArmorOriginIceElement.get(),
                            Component.literal("原初凛冰元素").withStyle(CustomStyle.styleOfIce));
                    originIceElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginIceElement.get().getDefaultInstance());
                    originIceElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorIceElementChest.get().getDefaultInstance());
                    originIceElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorIceElementLeggings.get().getDefaultInstance());
                    originIceElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorIceElementBoots.get().getDefaultInstance());
                    originIceElement.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                    originIceElement.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.SnowShield.get()));
                    originIceElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originIceElement.setHealth(originIceElement.getMaxHealth());
                    originIceElement.setBaby(true);
                    originIceElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originIceElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originIceElement.moveTo(new Vec3(1660.5, 73, 1985.5));
                    event.level.addFreshEntity(originIceElement);
                    iceBossInfo = (ServerBossEvent) (new ServerBossEvent(originIceElement.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        iceBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originIceElement != null && iceBossInfo != null) {
                iceBossInfo.setProgress(originIceElement.getHealth() / originIceElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originIceElement, originIceElement, Element.ice, 5, true, 1);
            }
            if (iceBossInfo != null && (originIceElement == null || !originIceElement.isAlive())) {
                iceBossInfo.removeAllPlayers();
                iceBossInfo = null;
            }
        }

        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int type = DetectItemEntity(event.level, new Vec3(1158.5, 67, 356.5), 4, new ArrayList<>() {{
                add(ModItems.WindElementPiece0.get());
                add(ModItems.WindElementPiece1.get());
                add(ModItems.WindElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originWindElement == null || !originWindElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1158.5, 67, 356.5), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初澄风元素已出现！").withStyle(CustomStyle.styleOfWind));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originWindElement != null) originWindElement.remove(Entity.RemovalReason.KILLED);
                    originWindElement = new Stray(EntityType.STRAY, event.level);
                    MobSpawn.setMobCustomName(originWindElement, ModItems.MobArmorOriginWindElement.get(),
                            Component.literal("原初澄风元素").withStyle(CustomStyle.styleOfWind));
                    originWindElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginWindElement.get().getDefaultInstance());
                    originWindElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorWindElementChest.get().getDefaultInstance());
                    originWindElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorWindElementLeggings.get().getDefaultInstance());
                    originWindElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorWindElementBoots.get().getDefaultInstance());
                    originWindElement.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                    originWindElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originWindElement.setHealth(originWindElement.getMaxHealth());
                    originWindElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originWindElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originWindElement.moveTo(new Vec3(1158.5, 68, 356.5));
                    event.level.addFreshEntity(originWindElement);
                    windBossInfo = (ServerBossEvent) (new ServerBossEvent(originWindElement.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        windBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originWindElement != null && windBossInfo != null) {
                windBossInfo.setProgress(originWindElement.getHealth() / originWindElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originWindElement, originWindElement, Element.wind, 5, true, 1);
            }
            if (windBossInfo != null && (originWindElement == null || !originWindElement.isAlive())) {
                windBossInfo.removeAllPlayers();
                windBossInfo = null;
            }
        }

        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int type = DetectItemEntity(event.level, new Vec3(1679.5, 69, 528.5), 4, new ArrayList<>() {{
                add(ModItems.LightningElementPiece0.get());
                add(ModItems.LightningElementPiece1.get());
                add(ModItems.LightningElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originLightningElement == null || !originLightningElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(1679.5, 69, 528.5), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初怒雷元素已出现！").withStyle(CustomStyle.styleOfLightning));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originLightningElement != null) originLightningElement.remove(Entity.RemovalReason.KILLED);
                    originLightningElement = new Witch(EntityType.WITCH, event.level);
                    MobSpawn.setMobCustomName(originLightningElement, ModItems.MobArmorOriginLightningElement.get(),
                            Component.literal("原初怒雷元素").withStyle(CustomStyle.styleOfLightning));
                    originLightningElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginLightningElement.get().getDefaultInstance());
                    originLightningElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorLightningElementChest.get().getDefaultInstance());
                    originLightningElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorLightningElementLeggings.get().getDefaultInstance());
                    originLightningElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorLightningElementBoots.get().getDefaultInstance());
                    originLightningElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originLightningElement.setHealth(originLightningElement.getMaxHealth());
                    originLightningElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originLightningElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originLightningElement.moveTo(new Vec3(1679.5, 70, 528.5));
                    event.level.addFreshEntity(originLightningElement);
                    lightningBossInfo = (ServerBossEvent) (new ServerBossEvent(originLightningElement.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        lightningBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originLightningElement != null && lightningBossInfo != null) {
                lightningBossInfo.setProgress(originLightningElement.getHealth() / originLightningElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originLightningElement, originLightningElement, Element.lightning, 5, true, 1);
            }
            if (lightningBossInfo != null && (originLightningElement == null || !originLightningElement.isAlive())) {
                lightningBossInfo.removeAllPlayers();
                lightningBossInfo = null;
            }
        }

        if (event.level.getServer() != null && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.NETHER))) {
            int type = DetectItemEntity(event.level, new Vec3(25.5, 105, 519.5), 4, new ArrayList<>() {{
                add(ModItems.FireElementPiece0.get());
                add(ModItems.FireElementPiece1.get());
                add(ModItems.FireElementPiece2.get());
            }});
            if (type != -1) {
                if (CanSummon(type) && (originFireElement == null || !originFireElement.isAlive())) {
                    List<Player> players = event.level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(25.5, 105, 519.5), 15, 15, 15));
                    players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player));
                    players.forEach(player -> {
                        ClientboundSetTitleTextPacket clientBoundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("原初炽焰元素已出现！").withStyle(CustomStyle.styleOfFire));
                        ((ServerPlayer) player).connection.send(clientBoundSetTitleTextPacket);
                    });
                    if (originFireElement != null) originFireElement.remove(Entity.RemovalReason.KILLED);
                    originFireElement = new Blaze(EntityType.BLAZE, event.level);
                    MobSpawn.setMobCustomName(originFireElement, ModItems.MobArmorOriginFireElement.get(),
                            Component.literal("原初炽焰元素").withStyle(CustomStyle.styleOfFire));
                    originFireElement.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorOriginFireElement.get().getDefaultInstance());
                    originFireElement.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorFireElementChest.get().getDefaultInstance());
                    originFireElement.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorFireElementLeggings.get().getDefaultInstance());
                    originFireElement.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorFireElementBoots.get().getDefaultInstance());
                    originFireElement.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 * Math.pow(10, 8));
                    originFireElement.setHealth(originFireElement.getMaxHealth());
                    originFireElement.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                    originFireElement.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                    originFireElement.moveTo(new Vec3(25.5, 106, 519.5));
                    event.level.addFreshEntity(originFireElement);
                    fireBossInfo = (ServerBossEvent) (new ServerBossEvent(originFireElement.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    players.forEach(player -> {
                        fireBossInfo.addPlayer((ServerPlayer) player);
                    });
                }
            }
            if (originFireElement != null && fireBossInfo != null) {
                fireBossInfo.setProgress(originFireElement.getHealth() / originFireElement.getMaxHealth());
                Element.ElementEffectAddToEntity(originFireElement, originFireElement, Element.fire, 5, true, 1);
            }
            if (fireBossInfo != null && (originFireElement == null || !originFireElement.isAlive())) {
                fireBossInfo.removeAllPlayers();
                fireBossInfo = null;
            }
        }
    }

    public static int DetectItemEntity(Level level, Vec3 pos, double r, List<Item> itemList) {
        List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class, AABB.ofSize(pos, r * Math.sqrt(2), r * Math.sqrt(2), r * Math.sqrt(2)));
        list.removeIf(itemEntity -> itemEntity.position().distanceTo(pos) > r);
        for (ItemEntity itemEntity : list) {
            if (itemEntity.tickCount >= 100 && itemList.contains(itemEntity.getItem().getItem())) {
                itemEntity.remove(Entity.RemovalReason.KILLED);
                List<Player> players = level.getEntitiesOfClass(Player.class, AABB.ofSize(pos, 15, 15, 15));
                players.forEach(player -> ModNetworking.sendToClient(new SoundsS2CPacket(13), (ServerPlayer) player));
                return itemList.indexOf(itemEntity.getItem().getItem());
            }
        }
        return -1;
    }

    public static boolean CanSummon(int type) {
        Random random = new Random();
        switch (type) {
            case 0 -> {
                return random.nextDouble() < 0.005;
            }
            case 1 -> {
                return random.nextDouble() < 0.5;
            }
            case 2 -> {
                return true;
            }
        }
        return false;
    }

    public static void Drops(Player player, Mob monster, Item item) {
        List<Player> playerList = monster.level().getEntitiesOfClass(Player.class, AABB.ofSize(monster.position(), 50, 50, 50));
        playerList.removeIf(player1 -> player1.distanceTo(monster) > 16 || player1.experienceLevel < 200);
        playerList.forEach(player1 -> {
            CompoundTag data1 = player1.getPersistentData();
            if (!data1.contains(StringUtils.OriginElementGetTimes)) data1.putInt(StringUtils.OriginElementGetTimes, 0);
            if (data1.getInt(StringUtils.OriginElementGetTimes) < 32) {
                data1.putInt(StringUtils.OriginElementGetTimes, data1.getInt(StringUtils.OriginElementGetTimes) + 1);

                InventoryOperation.itemStackGive(player1, new ItemStack(item, 4));

                Compute.sendFormatMSG(player1, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").
                                append(player.getDisplayName()).
                                append(Component.literal(" 击杀了 ").withStyle(ChatFormatting.WHITE)).
                                append(monster.getDisplayName()));
                Compute.sendFormatMSG(player1, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("当日可用奖励获取次数: ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(data1.getInt(StringUtils.OriginElementGetTimes) + "/" + 32).withStyle(ChatFormatting.LIGHT_PURPLE)));
                Random random = new Random();
                if (random.nextDouble() < 0.007) {
                    Map<Item, Item[]> map = new HashMap<>() {{
                        put(ModItems.LifeElementPiece1.get(), new Item[]{ModItems.LifeHolyStone0.get(), ModItems.LifeHolyStone1.get(), ModItems.LifeHolyStone2.get()});
                        put(ModItems.WaterElementPiece1.get(), new Item[]{ModItems.WaterHolyStone0.get(), ModItems.WaterHolyStone1.get(), ModItems.WaterHolyStone2.get()});
                        put(ModItems.FireElementPiece1.get(), new Item[]{ModItems.FireHolyStone0.get(), ModItems.FireHolyStone1.get(), ModItems.FireHolyStone2.get()});
                        put(ModItems.StoneElementPiece1.get(), new Item[]{ModItems.StoneHolyStone0.get(), ModItems.StoneHolyStone1.get(), ModItems.StoneHolyStone2.get()});
                        put(ModItems.IceElementPiece1.get(), new Item[]{ModItems.IceHolyStone0.get(), ModItems.IceHolyStone1.get(), ModItems.IceHolyStone2.get()});
                        put(ModItems.LightningElementPiece1.get(), new Item[]{ModItems.LightningHolyStone0.get(), ModItems.LightningHolyStone1.get(), ModItems.LightningHolyStone2.get()});
                        put(ModItems.WindElementPiece1.get(), new Item[]{ModItems.WindHolyStone0.get(), ModItems.WindHolyStone1.get(), ModItems.WindHolyStone2.get()});
                    }};

                    ItemStack itemStack = new ItemStack(map.get(item)[random.nextInt(3)]);
                    Compute.formatBroad(player.level(), Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("").
                                    append(player1.getDisplayName()).
                                    append(Component.literal(" 获得了:").withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()));
                    InventoryOperation.itemStackGive(player1, itemStack);
                }
            } else {
                Compute.sendFormatMSG(player1, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("当日可用奖励获取次数已耗尽。").withStyle(ChatFormatting.WHITE));

            }
        });
    }
}
