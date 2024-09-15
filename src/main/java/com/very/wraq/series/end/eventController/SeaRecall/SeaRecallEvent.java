package com.very.wraq.series.end.eventController.SeaRecall;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.chapter2.GuardianSpawnController;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter2.dimension.ToEnd;
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
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Random;

@Mod.EventBusSubscriber
public class SeaRecallEvent {
    @SubscribeEvent
    public static void Sea(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "海洋神殿";
                Style style = CustomStyle.styleOfSea;
                double ZoneX = 1054;
                double ZoneY = 44;
                double ZoneZ = 934;
                double PlatformX = 20;
                double PlatformY = 159;
                double PlatformZ = 20;
                if (Utils.seaRecall.recallCount != -1) {
                    if (Utils.seaRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.seaRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.SeaRecallElderGuardian != null)
                            Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);

                        Utils.seaRecall.Reset();
                        return;
                    }
                    if (Utils.seaRecall.killCount == -1 && Utils.seaRecall.recallCount != 400 && Utils.seaRecall.recallCount != 380)
                        Utils.seaRecall.recallCount--;
                    if (Utils.seaRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.seaRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.seaRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.seaRecall.recallCount == 540) {
                        Utils.seaRecall.killCount = 20;
                        Utils.seaRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只" + GuardianSpawnController.mobName).withStyle(style));
                        Utils.seaRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.seaRecall.recallCount--;
                    }
                    if (Utils.seaRecall.killCount == 0) {
                        Utils.seaRecall.killCount = -1;
                    }
                    if (Utils.seaRecall.recallCount == 500) {
                        Utils.seaRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.seaRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.seaRecall.recallCount == 400) {
                        if (Utils.SeaRecallElderGuardian == null || !Utils.SeaRecallElderGuardian.isAlive()) {
                            if (Utils.SeaRecallElderGuardian != null)
                                Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);
                            Utils.SeaRecallElderGuardian = new ElderGuardian(EntityType.ELDER_GUARDIAN, level1);
                            MobSpawn.setMobCustomName(Utils.SeaRecallElderGuardian, ModItems.ArmorSeaRecall.get(), Component.literal("模糊记忆中的神殿守卫").withStyle(style));
                            Utils.SeaRecallElderGuardian.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorSeaRecall.get().getDefaultInstance());
                            Utils.SeaRecallElderGuardian.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
                            Utils.SeaRecallElderGuardian.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.SeaRecallElderGuardian.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.SeaRecallElderGuardian.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.SeaRecallElderGuardian.setHealth(Utils.SeaRecallElderGuardian.getMaxHealth());
                            Utils.SeaRecallElderGuardian.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.SeaRecallElderGuardian);
                            Utils.seaRecall.recallCount--;
                            Utils.seaRecall.mob = Utils.SeaRecallElderGuardian;
                        }
                    }
                    if (Utils.seaRecall.recallCount == 380) {
                        if (Utils.seaRecall.recallSuccess) {
                            Utils.seaRecall.recallCount--;
                        }
                    }
                    if (Utils.seaRecall.recallCount == 340) {
                        if (Utils.seaRecall.recallSuccess) {
                            CompoundTag data = Utils.seaRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "SeaRecallTimes";
                            String RecallSuccessTimes = "SeaRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.seaRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.seaRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
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
                                Compute.sendFormatMSG(Utils.seaRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.SeaRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.seaRecall.recallPlayer, itemStack);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.seaRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.seaRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.seaRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.SeaRune.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.seaRecall.recallPlayer, itemStack);
                            }
                        }
                    }
                    if (Utils.seaRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.seaRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.seaRecall.Reset();
                    }
                }

                if (Utils.seaRecall.recallTimeLimit > 0) Utils.seaRecall.recallTimeLimit--;
                else {
                    if (Utils.seaRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.seaRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.seaRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.seaRecall.recallPlayer);
                        if (Utils.SeaRecallElderGuardian != null)
                            Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);
                        Utils.seaRecall.Reset();
                    }
                }
                if (Utils.seaRecall.recallTimeLimit > 0 && Utils.seaRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.seaRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.seaRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.seaRecall.recallPlayer);
                    if (Utils.SeaRecallElderGuardian != null)
                        Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);
                    Utils.seaRecall.Reset();
                }
            }
        }
    }
}
