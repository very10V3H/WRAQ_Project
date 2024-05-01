package com.very.wraq.events.zone;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
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
            Level Nether = player.getServer().getLevel(Level.NETHER);
            Level End = player.getServer().getLevel(Level.END);
            Vec2 vec2 = new Vec2((float) player.getX(),(float) player.getZ());
            List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
            if (player.tickCount % 40 == 0) {
                if (Compute.DotIn(vec2, Utils.KazeDot1, Utils.KazeDot2, Utils.KazeDot3, Utils.KazeDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Kaze")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("风之谷").withStyle(CustomStyle.styleOfKaze));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("狂风吹拂的谷地").withStyle(CustomStyle.styleOfKaze));
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
                                new ClientboundSetTitleTextPacket(Component.literal("天空城").withStyle(CustomStyle.styleOfSky));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("与浮云共眠之城").withStyle(CustomStyle.styleOfSky));
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
                                new ClientboundSetTitleTextPacket(Component.literal("海底神殿").withStyle(CustomStyle.styleOfSea));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("海洋生物的领地意识").withStyle(CustomStyle.styleOfSea));
                        data.putString("Zone","Sea");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getY() > 63 && Compute.DotIn(vec2,Utils.LightningDot1,Utils.LightningDot2,Utils.LightningDot3,Utils.LightningDot4) && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("Lightning")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("唤雷岛").withStyle(CustomStyle.styleOfLightingIsland));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("唤起万千响雷").withStyle(CustomStyle.styleOfLightingIsland));
                        data.putString("Zone","Lightning");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (level.equals(level.getServer().getLevel(Level.NETHER))) {
                    if (!data.getString("Zone").equals("Nether")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("下界").withStyle(CustomStyle.styleOfNether));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("维度的残破躯壳").withStyle(CustomStyle.styleOfNether));
                        data.putString("Zone","Nether");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 2104 && player.getX() < 2209
                        && player.getY() > 52 && player.getY() < 84
                        && player.getZ() > 1026 && player.getZ() < 1126 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("BloodIsland")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("腥月岛").withStyle(CustomStyle.styleOfBloodMana));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("血腥与海雾弥漫").withStyle(CustomStyle.styleOfBloodMana));
                        data.putString("Zone","BloodIsland");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1785 && player.getX() < 1888
                        && player.getY() > 104 && player.getY() < 152
                        && player.getZ() > 860 && player.getZ() < 920 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("EarthMana")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("蕴魔山").withStyle(CustomStyle.styleOfBloodMana));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("充满魔力的土地").withStyle(CustomStyle.styleOfBloodMana));
                        data.putString("Zone","EarthMana");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1260 && player.getX() < 2106
                        && player.getY() > 55 && player.getY() < 205
                        && player.getZ() > 680 && player.getZ() < 1600 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("SakuraIsland")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("绯樱岛").withStyle(CustomStyle.styleOfSakura));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("春樱散华").withStyle(CustomStyle.styleOfSakura));
                        data.putString("Zone","SakuraIsland");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1900 && player.getX() < 2051
                        && player.getY() > 10 && player.getY() < 52
                        && player.getZ() > 1110 && player.getZ() < 1280 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("PurpleIron")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("紫晶矿洞").withStyle(CustomStyle.styleOfPurpleIron));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("紫晶能量逸散之地").withStyle(CustomStyle.styleOfPurpleIron));
                        data.putString("Zone","PurpleIron");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1052 && player.getX() < 1230
                        && player.getY() > 119 && player.getY() < 152
                        && player.getZ() > 940 && player.getZ() < 1112 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("LifeElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("生机岛").withStyle(CustomStyle.styleOfLife));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("祥和与生机").withStyle(CustomStyle.styleOfLife));
                        data.putString("Zone","LifeElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1140 && player.getX() < 1250
                        && player.getY() > 63 && player.getY() < 137
                        && player.getZ() > 1371 && player.getZ() < 1477 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("StoneElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("层岩寨").withStyle(CustomStyle.styleOfStone));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("层岩叠嶂").withStyle(CustomStyle.styleOfStone));
                        data.putString("Zone","StoneElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1052 && player.getX() < 1200
                        && player.getZ() > 300 && player.getZ() < 460 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("WindElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("镇风高厦").withStyle(CustomStyle.styleOfWind));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("凌驾云霄，镇风整流").withStyle(CustomStyle.styleOfWind));
                        data.putString("Zone","WindElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 656 && player.getX() < 940
                        && player.getZ() > 760 && player.getZ() < 1111 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("BlackCastle")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastleCrystal));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("危险的暗黑魔法遗址").withStyle(CustomStyle.styleOfCastleCrystal));
                        data.putString("Zone","BlackCastle");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > -200 && player.getX() < 220
                        && player.getZ() > 381 && player.getZ() < 626 && level.equals(Nether)) {
                    if (!data.getString("Zone").equals("FireElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("炽焰终焉之境").withStyle(CustomStyle.styleOfFire));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("硝烟弥漫，熔岩翻腾").withStyle(CustomStyle.styleOfFire));
                        data.putString("Zone","FireElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 761 && player.getX() < 960
                        && player.getZ() > 254 && player.getZ() < 452 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("WaterElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("碧水宫").withStyle(CustomStyle.styleOfWater));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("汇聚碧波，辉映水泽").withStyle(CustomStyle.styleOfWater));
                        data.putString("Zone","WaterElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1550 && player.getX() < 1750
                        && player.getZ() > 415 && player.getZ() < 623 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("LightningElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("怒雷岛").withStyle(CustomStyle.styleOfLightning));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("承接天怒，唤风引雷").withStyle(CustomStyle.styleOfLightning));
                        data.putString("Zone","LightningElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else
                if (player.getX() > 1550 && player.getX() < 1914
                        && player.getZ() > 1774 && player.getZ() < 2187 && level.equals(OverWorld)) {
                    if (!data.getString("Zone").equals("IceElement")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("凛冰之境").withStyle(CustomStyle.styleOfIce));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("凛冽种玉，冰寒境界").withStyle(CustomStyle.styleOfIce));
                        data.putString("Zone","IceElement");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }

                else
                if (player.getX() > -48 && player.getX() < 96
                        && player.getZ() > -278 && player.getZ() < -135 && level.equals(End)) {
                    if (!data.getString("Zone").equals("EndSilent")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("终界寂域").withStyle(CustomStyle.styleOfEnd));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("归终之境，寂寥空灵").withStyle(CustomStyle.styleOfEnd));
                        data.putString("Zone","EndSilent");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }

                else
                if (player.getX() > -132 && player.getX() < 160
                        && player.getZ() > -80 && player.getZ() < 150 && level.equals(End)) {
                    if (!data.getString("Zone").equals("End")) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("终末之地").withStyle(CustomStyle.styleOfEnd));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("回忆与归终").withStyle(CustomStyle.styleOfEnd));
                        data.putString("Zone","End");
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    }
                }
                else data.putString("Zone","Other");
            }
        }
    }
}
