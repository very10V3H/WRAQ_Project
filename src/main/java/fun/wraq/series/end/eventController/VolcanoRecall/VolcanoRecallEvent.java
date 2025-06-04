package fun.wraq.series.end.eventController.VolcanoRecall;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.chapter2.SearedSpiritSpawnController;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.particles.ModParticles;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class VolcanoRecallEvent {
    @SubscribeEvent
    public static void Volcano(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "火山平原";
                Style style = CustomStyle.styleOfVolcano;
                double ZoneX = 1490;
                double ZoneY = 79;
                double ZoneZ = 26;
                double PlatformX = 0;
                double PlatformY = 159;
                double PlatformZ = 20;
                if (Utils.volcanoRecall.recallCount != -1) {
                    if (Utils.volcanoRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.volcanoRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.VolcanoRecallBlaze != null)
                            Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);

                        Utils.volcanoRecall.Reset();
                        return;
                    }
                    if (Utils.volcanoRecall.killCount == -1 && Utils.volcanoRecall.recallCount != 400 && Utils.volcanoRecall.recallCount != 380)
                        Utils.volcanoRecall.recallCount--;
                    if (Utils.volcanoRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.volcanoRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.volcanoRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.volcanoRecall.recallCount == 540) {
                        Utils.volcanoRecall.killCount = 20;
                        Utils.volcanoRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只" + SearedSpiritSpawnController.mobName).withStyle(style));
                        Utils.volcanoRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.volcanoRecall.recallCount--;
                    }
                    if (Utils.volcanoRecall.killCount == 0) {
                        Utils.volcanoRecall.killCount = -1;
                    }
                    if (Utils.volcanoRecall.recallCount == 500) {
                        Utils.volcanoRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.volcanoRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.volcanoRecall.recallCount == 400) {
                        if (Utils.VolcanoRecallBlaze == null || !Utils.VolcanoRecallBlaze.isAlive()) {
                            if (Utils.VolcanoRecallBlaze != null)
                                Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);
                            Utils.VolcanoRecallBlaze = new Blaze(EntityType.BLAZE, level1);
                            MobSpawn.setMobCustomName(Utils.VolcanoRecallBlaze, ModItems.ARMOR_VOLCANO_RECALL.get(), Component.literal("模糊记忆中的火山熔岩").withStyle(style));
                            Utils.VolcanoRecallBlaze.setItemSlot(EquipmentSlot.HEAD, ModItems.ARMOR_VOLCANO_RECALL.get().getDefaultInstance());
                            Utils.VolcanoRecallBlaze.setItemSlot(EquipmentSlot.MAINHAND, ModItems.FOREST_SWORD_3.get().getDefaultInstance());
                            Utils.VolcanoRecallBlaze.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.VolcanoRecallBlaze.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.VolcanoRecallBlaze.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.VolcanoRecallBlaze.setHealth(Utils.VolcanoRecallBlaze.getMaxHealth());
                            Utils.VolcanoRecallBlaze.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.VolcanoRecallBlaze);
                            Utils.volcanoRecall.recallCount--;
                            Utils.volcanoRecall.mob = Utils.VolcanoRecallBlaze;
                        }
                    }
                    if (Utils.volcanoRecall.recallCount == 380) {
                        if (Utils.volcanoRecall.recallSuccess) {
                            Utils.volcanoRecall.recallCount--;
                        }
                    }
                    if (Utils.volcanoRecall.recallCount == 340) {
                        if (Utils.volcanoRecall.recallSuccess) {
                            CompoundTag data = Utils.volcanoRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "VolcanoRecallTimes";
                            String RecallSuccessTimes = "VolcanoRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.volcanoRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.volcanoRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
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
                                Compute.sendFormatMSG(Utils.volcanoRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.INTENSIFIED_VOLCANO_SOUL.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.giveItemStack(Utils.volcanoRecall.recallPlayer, itemStack);
                                data.putInt(StringUtils.RecallEndRune3, 1);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.volcanoRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.volcanoRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.volcanoRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.VOLCANO_RUNE.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.giveItemStack(Utils.volcanoRecall.recallPlayer, itemStack);
                                data.putInt(StringUtils.RecallEndRune3, 1);
                            }
                        }
                    }
                    if (Utils.volcanoRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.volcanoRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.volcanoRecall.Reset();
                    }
                }

                if (Utils.VolcanoRecallBlaze != null && Utils.VolcanoRecallBlaze.isAlive()) { // AI
                    if (Tick.get() % 20 == 0) {
                        List<Player> playerList = level1.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.VolcanoRecallBlaze.position(), 20, 20, 20));
                        for (Player player : playerList) {
                            if (player.position().distanceTo(Utils.VolcanoRecallBlaze.position()) <= 10) {
                                player.setHealth(player.getHealth() - 100);
                                ParticleProvider.createLineParticle(level1, 20, Utils.VolcanoRecallBlaze.position(), player.position(), ModParticles.LONG_VOLCANO.get());
                            }
                        }
                    }
                }

                if (Utils.volcanoRecall.recallTimeLimit > 0) Utils.volcanoRecall.recallTimeLimit--;
                else {
                    if (Utils.volcanoRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.volcanoRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.volcanoRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.volcanoRecall.recallPlayer);
                        if (Utils.VolcanoRecallBlaze != null)
                            Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);
                        Utils.volcanoRecall.Reset();
                    }
                }
                if (Utils.volcanoRecall.recallTimeLimit > 0 && Utils.volcanoRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.volcanoRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.volcanoRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.volcanoRecall.recallPlayer);
                    if (Utils.VolcanoRecallBlaze != null) Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);
                    Utils.volcanoRecall.Reset();
                }
            }
        }
    }
}
