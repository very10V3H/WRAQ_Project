package fun.wraq.series.end.eventController.LightningIslandRecall;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.chapter2.LightningZombieController;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.dimension.ToEnd;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class LightningRecallEvent {
    @SubscribeEvent
    public static void Lightning(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "雷光灯塔";
                Style style = CustomStyle.styleOfLightingIsland;
                double ZoneX = 1744;
                double ZoneY = 69;
                double ZoneZ = 1242;
                double PlatformX = 0;
                double PlatformY = 159;
                double PlatformZ = 0;
                if (Utils.lightningRecall.recallCount != -1) {
                    if (Utils.lightningRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.lightningRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.LightingRecallZombie != null)
                            Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);

                        Utils.lightningRecall.Reset();
                        return;
                    }
                    if (Utils.lightningRecall.killCount == -1 && Utils.lightningRecall.recallCount != 400 && Utils.lightningRecall.recallCount != 380)
                        Utils.lightningRecall.recallCount--;
                    if (Utils.lightningRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.lightningRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.lightningRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.lightningRecall.recallCount == 540) {
                        Utils.lightningRecall.killCount = 20;
                        Utils.lightningRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只" + LightningZombieController.mobName).withStyle(style));
                        Utils.lightningRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.lightningRecall.recallCount--;
                    }
                    if (Utils.lightningRecall.killCount == 0) {
                        Utils.lightningRecall.killCount = -1;
                    }
                    if (Utils.lightningRecall.recallCount == 500) {
                        Utils.lightningRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.lightningRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.lightningRecall.recallCount == 400) {
                        if (Utils.LightingRecallZombie == null || !Utils.LightingRecallZombie.isAlive()) {
                            if (Utils.LightingRecallZombie != null)
                                Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);
                            Utils.LightingRecallZombie = new Zombie(EntityType.ZOMBIE, level1);
                            MobSpawn.setMobCustomName(Utils.LightingRecallZombie, ModItems.ArmorLightningRecall.get(), Component.literal("模糊记忆中的唤雷守卫").withStyle(style));
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorLightningRecall.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorLZChest.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorLZLeggings.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorLZBoots.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
                            Utils.LightingRecallZombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.LightingRecallZombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.LightingRecallZombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.LightingRecallZombie.setHealth(Utils.LightingRecallZombie.getMaxHealth());
                            Utils.LightingRecallZombie.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.LightingRecallZombie);
                            Utils.lightningRecall.recallCount--;
                            Utils.lightningRecall.mob = Utils.LightingRecallZombie;
                        }
                    }
                    if (Utils.lightningRecall.recallCount == 380) {
                        if (Utils.lightningRecall.recallSuccess) {
                            Utils.lightningRecall.recallCount--;
                        }
                    }
                    if (Utils.lightningRecall.recallCount == 340) {
                        if (Utils.lightningRecall.recallSuccess) {
                            CompoundTag data = Utils.lightningRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "LightningRecallTimes";
                            String RecallSuccessTimes = "LightningRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.lightningRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.lightningRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.lightningRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.LightningRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.lightningRecall.recallPlayer, itemStack);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.lightningRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.lightningRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.lightningRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.LightningRune.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.lightningRecall.recallPlayer, itemStack);
                            }
                        }
                    }
                    if (Utils.lightningRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.lightningRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.lightningRecall.Reset();
                    }
                }
                if (Utils.LightingRecallZombie != null && Utils.LightingRecallZombie.isAlive()) {
                    if (Tick.get() % 20 == 0) {
                        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.LightingRecallZombie.position(), 20, 20, 20));
                        for (Player player : playerList) {
                            if (player.position().distanceTo(Utils.LightingRecallZombie.position()) <= 2.5f) {
                                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level1);
                                lightningBolt.setDamage(player.getMaxHealth() * 0.2f);
                                lightningBolt.moveTo(player.position());
                                level1.addFreshEntity(lightningBolt);
                            }
                        }
                    }
                    if (Tick.get() % 20 < random.nextInt(5)) {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level1);
                        lightningBolt.setDamage(400);
                        lightningBolt.moveTo(PlatformX - 9 + random.nextInt(18), PlatformY, PlatformZ - 9 + random.nextInt(18));
                        level1.addFreshEntity(lightningBolt);
                    }
                }
                if (Utils.lightningRecall.recallTimeLimit > 0) Utils.lightningRecall.recallTimeLimit--;
                else {
                    if (Utils.lightningRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.lightningRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.lightningRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.lightningRecall.recallPlayer);
                        if (Utils.LightingRecallZombie != null)
                            Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);
                        Utils.lightningRecall.Reset();
                    }
                }
                if (Utils.lightningRecall.recallTimeLimit > 0 && Utils.lightningRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.lightningRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.lightningRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.lightningRecall.recallPlayer);
                    if (Utils.LightingRecallZombie != null)
                        Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);
                    Utils.lightningRecall.Reset();
                }
            }
        }
    }
}
