package com.Very.very.Events.ZoneControlEvent;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
@Mod.EventBusSubscriber
public class ZoneTickEvent {
    @SubscribeEvent
    public static void ZoneTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            Player player = event.player;
            CompoundTag data = player.getPersistentData();
            ServerPlayer serverPlayer = (ServerPlayer) player;
            Level level = player.level();
            Level OverWorld = player.getServer().getLevel(Level.OVERWORLD);
            Vec2 vec2 = new Vec2((float) player.getX(),(float) player.getZ());
            List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
            if (player.tickCount % 40 == 0) {
                if (Compute.DotIn(vec2, Utils.KazeDot1, Utils.KazeDot2, Utils.KazeDot3, Utils.KazeDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Kaze")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("风之谷").withStyle(Utils.styleOfKaze));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("狂风吹拂的谷地").withStyle(Utils.styleOfKaze));
                        data.putString("Zone","Kaze");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > -7 && player.getX() < 103
                        && player.getY() > 70 && player.getY() < 205
                        && player.getZ() > 998 && player.getZ() < 1110 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("SkyCity")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("天空城").withStyle(Utils.styleOfSky));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("与浮云共眠之城").withStyle(Utils.styleOfSky));
                        data.putString("Zone","SkyCity");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (Compute.DotIn(vec2,Utils.SnowDot1,Utils.SnowDot2,Utils.SnowDot3,Utils.SnowDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Snow")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("冰川").withStyle(ChatFormatting.AQUA));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("凛冽刺骨寒气冲刷着你").withStyle(ChatFormatting.AQUA));
                        data.putString("Zone","Snow");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() < 115 && player.getY() > 63 && Compute.DotIn(vec2,Utils.ForestDot1,Utils.ForestDot2,Utils.ForestDot3,Utils.ForestDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Forest")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("森林").withStyle(ChatFormatting.DARK_GREEN));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("生机与危机共存").withStyle(ChatFormatting.DARK_GREEN));
                        data.putString("Zone","Forest");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() < 115 && player.getY() > 63 && Compute.DotIn(vec2,Utils.Forest1Dot1,Utils.Forest1Dot2,Utils.Forest1Dot3,Utils.Forest1Dot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Forest")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("森林").withStyle(ChatFormatting.DARK_GREEN));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("生机与危机共存").withStyle(ChatFormatting.DARK_GREEN));
                        data.putString("Zone","Forest");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() < 102 && Compute.DotIn(vec2,Utils.PlainDot1,Utils.PlainDot2,Utils.PlainDot3,Utils.PlainDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Plain")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("平原").withStyle(ChatFormatting.GREEN));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("平和与发展").withStyle(ChatFormatting.GREEN));
                        data.putString("Zone","Plain");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() > 115 && Compute.DotIn(vec2,Utils.ManaForest1Dot1,Utils.ManaForest1Dot2,Utils.ManaForest1Dot3,Utils.ManaForest1Dot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("ManaForest")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("魔源森林").withStyle(ChatFormatting.LIGHT_PURPLE));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("生机与魔法占据之地").withStyle(ChatFormatting.LIGHT_PURPLE));
                        data.putString("Zone","ManaForest");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() > 115 && Compute.DotIn(vec2,Utils.ManaForest2Dot1,Utils.ManaForest2Dot2,Utils.ManaForest2Dot3,Utils.ManaForest2Dot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("ManaForest")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("魔源森林").withStyle(ChatFormatting.LIGHT_PURPLE));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("生机与魔法占据之地").withStyle(ChatFormatting.LIGHT_PURPLE));
                        data.putString("Zone","ManaForest");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() < 63 && Compute.DotIn(vec2,Utils.SeaDot1,Utils.SeaDot2,Utils.SeaDot3,Utils.SeaDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Sea")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("海底神殿").withStyle(Utils.styleOfSea));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("海洋生物的领地意识").withStyle(Utils.styleOfSea));
                        data.putString("Zone","Sea");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() > 63 && Compute.DotIn(vec2,Utils.LightningDot1,Utils.LightningDot2,Utils.LightningDot3,Utils.LightningDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Lightning")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("唤雷岛").withStyle(Utils.styleOfLightingIsland));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("唤起万千响雷").withStyle(Utils.styleOfLightingIsland));
                        data.putString("Zone","Lightning");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (level.equals(level.getServer().getLevel(Level.NETHER))) {
                    if (!data.getString("Zone").equals("Nether")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("下界").withStyle(Utils.styleOfNether));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("维度的残破躯壳").withStyle(Utils.styleOfNether));
                        data.putString("Zone","Nether");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else data.putString("Zone","Other");
            }
        }
    }
}
