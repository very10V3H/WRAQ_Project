package com.very.wraq.series.end.eventController.NetherRecall1;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.NetherRecallParticleS2CPacket;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter2.dimension.ToEnd;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.WitherSkeleton;
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
public class NetherRecallEvent1 {
    @SubscribeEvent
    public static void Nether1(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.NETHER);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "下界";
                Style style = CustomStyle.styleOfNether;
                double ZoneX = 564;
                double ZoneY = 67;
                double ZoneZ = -633;
                double PlatformX = -40;
                double PlatformY = 159;
                double PlatformZ = 0;
                if (Utils.netherRecall.recallCount != -1) {
                    if (Utils.netherRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.netherRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.NetherRecallWither != null)
                            Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);

                        Utils.netherRecall.Reset();
                        return;
                    }
                    if (Utils.netherRecall.killCount == -1 && Utils.netherRecall.recallCount != 400 && Utils.netherRecall.recallCount != 380)
                        Utils.netherRecall.recallCount--;
                    if (Utils.netherRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.netherRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.netherRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.netherRecall.recallCount == 540) {
                        Utils.netherRecall.killCount = 20;
                        Utils.netherRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只" + WitherSkeletonSpawnController.mobName).withStyle(style));
                        Utils.netherRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.netherRecall.recallCount--;
                    }
                    if (Utils.netherRecall.killCount == 0) {
                        Utils.netherRecall.killCount = -1;
                    }
                    if (Utils.netherRecall.recallCount == 500) {
                        Utils.netherRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.netherRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.netherRecall.recallCount == 400) {
                        if (Utils.NetherRecallWither == null || !Utils.NetherRecallWither.isAlive()) {
                            if (Utils.NetherRecallWither != null)
                                Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);
                            Utils.NetherRecallWither = new WitherSkeleton(EntityType.WITHER_SKELETON, level1);
                            Compute.setMobCustomName(Utils.NetherRecallWither, ModItems.ArmorNetherRecall.get(), Component.literal("模糊记忆中的下界凋零骷髅").withStyle(style));
                            Utils.NetherRecallWither.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorNetherRecall.get().getDefaultInstance());
                            Utils.NetherRecallWither.setItemSlot(EquipmentSlot.MAINHAND, ModItems.ManaSword1.get().getDefaultInstance());
                            Utils.NetherRecallWither.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.NetherRecallWither.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.NetherRecallWither.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.NetherRecallWither.setHealth(Utils.NetherRecallWither.getMaxHealth());
                            Utils.NetherRecallWither.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.NetherRecallWither);
                            Utils.netherRecall.recallCount--;
                            Utils.netherRecall.mob = Utils.NetherRecallWither;
                        }
                    }
                    if (Utils.netherRecall.recallCount == 380) {
                        if (Utils.netherRecall.recallSuccess) {
                            Utils.netherRecall.recallCount--;
                        }
                    }
                    if (Utils.netherRecall.recallCount == 340) {
                        if (Utils.netherRecall.recallSuccess) {
                            CompoundTag data = Utils.netherRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "NetherRecallTimes";
                            String RecallSuccessTimes = "NetherRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.netherRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.netherRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
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
                                Compute.sendFormatMSG(Utils.netherRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.NetherRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.netherRecall.recallPlayer, itemStack);
                                data.putInt(StringUtils.RecallEndRune3, 2);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.netherRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.netherRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.netherRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.NetherSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.netherRecall.recallPlayer, itemStack);
                                data.putInt(StringUtils.RecallEndRune3, 2);
                            }
                        }
                    }
                    if (Utils.netherRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.netherRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.netherRecall.Reset();
                    }
                }
                if (Utils.NetherRecallWither != null && Utils.NetherRecallWither.isAlive()) {
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        ModNetworking.sendToClient(new NetherRecallParticleS2CPacket(true), Utils.netherRecall.recallPlayer);
                    }
                    if (level1.getServer().getTickCount() % 200 == 20) {
                        List<Player> playerList = level1.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.NetherRecallWither.position(), 20, 20, 20));
                        for (Player player : playerList) {
                            player.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 10));
                            player.setHealth(player.getHealth() - player.getMaxHealth() * 0.25f);
                            player.getPersistentData().putInt("NetherRecallBuff", 60);
                        }
                    }
                }
                if (Utils.netherRecall.recallTimeLimit > 0) Utils.netherRecall.recallTimeLimit--;
                else {
                    if (Utils.netherRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.netherRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.netherRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.netherRecall.recallPlayer);
                        if (Utils.NetherRecallWither != null)
                            Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);
                        Utils.netherRecall.Reset();
                    }
                }
                if (Utils.netherRecall.recallTimeLimit > 0 && Utils.netherRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.netherRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.netherRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.netherRecall.recallPlayer);
                    if (Utils.NetherRecallWither != null) Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);
                    Utils.netherRecall.Reset();
                }
            }
        }
    }
}
