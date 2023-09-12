package com.Very.very.Series.EndSeries.EventControl.NetherRecall1;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.NetherRecallParticleS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
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

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class NetherRecallEvent1 {
    @SubscribeEvent
    public static void Nether1(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.NETHER);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "下界";
                Style style = Utils.styleOfNether;
                double ZoneX = 62;
                double ZoneY = 67;
                double ZoneZ = 235;
                double PlatformX = -40;
                double PlatformY = 158;
                double PlatformZ = 0;
                if (Utils.NetherRecall.RecallCount != -1) {
                    if (Utils.NetherRecall.KillCount == -1 && Utils.NetherRecall.RecallCount != 400 && Utils.NetherRecall.RecallCount != 380) Utils.NetherRecall.RecallCount--;
                    if (Utils.NetherRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.NetherRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.NetherRecall.RecallCount == 540) {
                        Utils.NetherRecall.KillCount = 20;
                        Utils.NetherRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只下界凋零骷髅").withStyle(style));
                        Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.NetherRecall.RecallCount--;
                    }
                    if (Utils.NetherRecall.KillCount == 0) {
                        Utils.NetherRecall.KillCount = -1;
                    }
                    if (Utils.NetherRecall.RecallCount == 500) {
                        Utils.NetherRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.NetherRecall.RecallCount == 400) {
                        if (Utils.NetherRecallWither == null || !Utils.NetherRecallWither.isAlive()) {
                            if(Utils.NetherRecallWither != null) Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);
                            Utils.NetherRecallWither = new WitherSkeleton(EntityType.WITHER_SKELETON,level1);
                            Compute.SetMobCustomName(Utils.NetherRecallWither,Moditems.ArmorNetherRecall.get(),Component.literal("模糊记忆中的下界凋零骷髅").withStyle(style));
                            Utils.NetherRecallWither.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorNetherRecall.get().getDefaultInstance());
                            Utils.NetherRecallWither.setItemSlot(EquipmentSlot.MAINHAND , Moditems.ManaSword1.get().getDefaultInstance());
                            Utils.NetherRecallWither.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.NetherRecallWither.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.NetherRecallWither.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.NetherRecallWither.setHealth(Utils.NetherRecallWither.getMaxHealth());
                            Utils.NetherRecallWither.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.NetherRecallWither);
                            Utils.NetherRecall.RecallCount--;
                        }
                    }
                    if (Utils.NetherRecall.RecallCount == 380) {
                        if (Utils.NetherRecall.RecallSuccess) {
                            Utils.NetherRecall.RecallCount --;
                        }
                    }
                    if (Utils.NetherRecall.RecallCount == 340) {
                        if (Utils.NetherRecall.RecallSuccess) {
                            CompoundTag data = Utils.NetherRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "NetherRecallTimes";
                            String RecallSuccessTimes = "NetherRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.NetherRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.NetherRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.NetherRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.NetherRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.NetherRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.NetherRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.NetherRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.NetherRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.NetherSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.NetherRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.NetherRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.NetherRecall.Reset();
                    }
                }
                if (Utils.NetherRecallWither != null && Utils.NetherRecallWither.isAlive()) {
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        ModNetworking.sendToClient(new NetherRecallParticleS2CPacket(true),Utils.NetherRecall.RecallPlayer);
                    }
                    if (level1.getServer().getTickCount() % 200 == 20) {
                        List<Player> playerList = level1.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.NetherRecallWither.getPosition(1.0f),20,20,20));
                        for (Player player : playerList) {
                            player.addEffect(new MobEffectInstance(MobEffects.WITHER,60,10));
                            player.setHealth(player.getHealth() - player.getMaxHealth() * 0.25f);
                            player.getPersistentData().putInt("NetherRecallBuff",60);
                        }
                    }
                }
                if (Utils.NetherRecall.RecallTimeLimit > 0) Utils.NetherRecall.RecallTimeLimit--;
                else {
                    if (Utils.NetherRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.NetherRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.NetherRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.NetherRecallWither != null) Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);
                        Utils.NetherRecall.Reset();
                    }
                }
                if (Utils.NetherRecall.RecallTimeLimit > 0 && Utils.NetherRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.NetherRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.NetherRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.NetherRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.NetherRecallWither != null) Utils.NetherRecallWither.remove(Entity.RemovalReason.KILLED);
                    Utils.NetherRecall.Reset();
                }
            }
        }
    }
}
