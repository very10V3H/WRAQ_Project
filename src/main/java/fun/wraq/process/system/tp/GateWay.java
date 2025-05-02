package fun.wraq.process.system.tp;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class GateWay {

    public record Des(Vec3 pos, float rotX, float rotY, Component name) {
    }

    public final Des pos1;
    public final Des pos2;

    public GateWay(final Des pos1, final Des pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public static List<GateWay> getConnections() {
        return List.of(new GateWay(
                        new Des(new Vec3(962, 207.5, -1), 0, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(1553, 64, -2935), 90, 0, Component.literal("冰霜骑士驻地").withStyle(CustomStyle.styleOfIce))),
                new GateWay(
                        new Des(new Vec3(962, 207.5, 27), 180, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(1043, 227, 620), 180, 0, Component.literal("尘月之梦").withStyle(CustomStyle.styleOfMoon1))),
                new GateWay(
                        new Des(new Vec3(970, 207.5, 27), 180, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(1744, 71, 1215), 180, 0, Component.literal("雷光灯塔").withStyle(CustomStyle.styleOfLightning))),
                new GateWay(
                        new Des(new Vec3(973, 207.5, 13), 90, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(2352, 130, 30), 90, 0, Component.literal("东洋塔").withStyle(CustomStyle.styleOfHusk))),
                new GateWay(
                        new Des(new Vec3(973, 207.5, 15), 90, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(1760, 80, 58), 180, 0, Component.literal("原始森林").withStyle(CustomStyle.styleOfForest))),
                new GateWay(
                        new Des(new Vec3(970, 207.5, -1), 0, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(1914, 152, -936), -90, 0, Component.literal("望山据点").withStyle(CustomStyle.styleOfForest))),
                new GateWay(
                        new Des(new Vec3(973, 207.5, 23), 0, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(2354, 169, 1752), -90, 0, Component.literal("绯樱村").withStyle(CustomStyle.styleOfSakura))),

                new GateWay(
                        new Des(new Vec3(964, 207.5, -1), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(1295, 69, -1607), 180, 0, Te.s("北洋村", CustomStyle.styleOfSnow))),
                new GateWay(
                        new Des(new Vec3(968, 207.5, -1), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(2450, 153, -1368), 180, 0, Te.s("暗黑城堡", CustomStyle.styleOfCastle))),
                new GateWay(
                        new Des(new Vec3(973, 207.5, 3), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(2014, 132, -1741), 180, 0, Te.s("菌菇聚落", CustomStyle.MUSHROOM_STYLE))),
                new GateWay(
                        new Des(new Vec3(960, 207.5, -1), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(1037, 79, -1301), 90, 0, Te.s("薰曦村", CustomStyle.styleOfJacaranda))),
                new GateWay(
                        new Des(new Vec3(958, 207.5, -1), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(1157, 76, -1075), 0, 0, Te.s("薰楠村", CustomStyle.styleOfJacaranda))),
                new GateWay(
                        new Des(new Vec3(966, 207.5, -1), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(1430, 78, -1062), 0, 0, Te.s("炼雨府邸", CustomStyle.styleOfSea))),
                new GateWay(
                        new Des(new Vec3(956, 207.5, -1), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(922, 64, -416), 180, 0, Te.s("海岸村", CustomStyle.styleOfSea))),
                new GateWay(
                        new Des(new Vec3(973, 207.5, 5), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(2527, 126, -492), 0, 0, Te.s("火山村", CustomStyle.styleOfVolcano))),
                new GateWay(
                        new Des(new Vec3(964, 207.5, 27), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(1088, 52, 826), 0, 0, Te.s("海底神殿", CustomStyle.styleOfSea))),
                new GateWay(
                        new Des(new Vec3(973, 207.5, 7), 0, 0, Te.s("天空城", CustomStyle.styleOfSky)),
                        new Des(new Vec3(1493, 75, -125), 180, 0, Te.s("唤魔森林", CustomStyle.styleOfMana))),

                new GateWay(
                        new Des(new Vec3(973, 207.5, 17), 0, 0, Component.literal("天空城").withStyle(CustomStyle.styleOfSky)),
                        new Des(new Vec3(1761, 74.5, 329), -90, 0, Component.literal("旭升岛").withStyle(CustomStyle.styleOfSunIsland)))
        );
    }

    public static Map<Des, Des> getDestinationMap() {
        return new HashMap<>() {{
            for (GateWay connection : getConnections()) {
                put(connection.pos1, connection.pos2);
                put(connection.pos2, connection.pos1);
            }
        }};
    }

    public static List<Des> getPosList() {
        return new ArrayList<>() {{
            for (GateWay connection : getConnections()) {
                add(connection.pos1);
                add(connection.pos2);
            }
        }};
    }

    public static Map<String, Integer> playerTPDelayCount = new HashMap<>();
    public static Map<String, Integer> playerTPCooldownMap = new HashMap<>();

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) throws ParseException {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.player.level().dimension().equals(Level.OVERWORLD)) {
            ServerPlayer player = (ServerPlayer) event.player;
            String name = player.getName().getString();
            int tick = Tick.get();
            Vec3 skyTpCenterPos = new Vec3(962, 207.5, 13);
            // 天空城传送至传送中枢
            if (player.position().distanceTo(new Vec3(957.5, 224.5, 14.5)) < 1) {
                player.teleportTo(skyTpCenterPos.x, skyTpCenterPos.y + 0.5, skyTpCenterPos.z);
                // 防止玩家前往传送中枢后直接被传送回天空城经典重生点
                playerTPCooldownMap.put(name, tick + 2);
            }
            int coolDownTick = playerTPCooldownMap.getOrDefault(name, 0);

            if (tick > coolDownTick) {
                boolean nearGateway = false;
                Des nearDes = null;
                for (Des des : getPosList()) {
                    if (player.position().distanceTo(des.pos.add(0.5, 0, 0.5)) < 1) {
                        nearGateway = true;
                        nearDes = des;
                        if (playerTPDelayCount.getOrDefault(name, -1) == -1) {
                            playerTPDelayCount.put(name, 60);
                        }
                    }
                }
                ItemStack validTpPass = TpPass.playerHasValidTpPass(player);
                if (nearGateway) {
                    if (validTpPass == null
                            && InventoryOperation.itemStackCount(player, ModItems.TP_TICKET.get()) == 0
                            && PlanPlayer.getPlayerTier(player) < 2) {
                        playerTPDelayCount.put(name, -1);
                        Compute.setPlayerTitleAndSubTitle(player, Component.literal("位移进程中断").withStyle(ChatFormatting.RED),
                                Te.s("传送需要",
                                        ModItems.TP_TICKET.get().getDefaultInstance().getDisplayName(), "或",
                                        "传送中枢通票", CustomStyle.styleOfEnd), 0, 20, 10);
                    } else {
                        int tpDelayCount = playerTPDelayCount.getOrDefault(name, -1);
                        playerTPDelayCount.put(name, tpDelayCount - 1);
                        Des destination = getDestinationMap().getOrDefault(nearDes, null);
                        sendTpProgressBar(player, tpDelayCount, destination);
                        if (tpDelayCount == 0) {
                            // tp
                            // 如果是传送回天空城附近，那应当直接传送回天空城传送中心
                            if (destination.pos.distanceTo(skyTpCenterPos) < 30) {
                                player.teleportTo(player.getServer().getLevel(Level.OVERWORLD), skyTpCenterPos.x,
                                        skyTpCenterPos.y, skyTpCenterPos.z, 180, 0);
                            } else {
                                player.teleportTo(player.getServer().getLevel(Level.OVERWORLD), destination.pos.x,
                                        destination.pos.y, destination.pos.z, destination.rotX, destination.rotY);
                            }
                            Compute.setPlayerTitleAndSubTitle(player,
                                    Te.s("位移进程完成", CustomStyle.styleOfEnd),
                                    Component.literal("已传送至: ").withStyle(CustomStyle.styleOfEnd).
                                            append(destination.name), 20, 60, 20);
                            playerTPDelayCount.put(name, -1);
                            if (destination.pos.distanceTo(skyTpCenterPos) < 30)
                                playerTPCooldownMap.put(name, tick + 2);
                            else playerTPCooldownMap.put(name, tick + 60);
                            if (PlanPlayer.getPlayerTier(player) < 2) {
                                if (validTpPass != null) {
                                    TpPass.setExpiredDate(validTpPass);
                                } else {
                                    InventoryOperation.removeItem(player.getInventory(),
                                            ModItems.TP_TICKET.get(), 1);
                                }
                            }
                        }
                    }
                } else {
                    if (playerTPDelayCount.getOrDefault(name, -1) != -1) {
                        Compute.setPlayerTitleAndSubTitle(player, Component.literal("位移进程终止").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("离开了传送区域").withStyle(CustomStyle.styleOfEnd), 20, 60, 20);
                    }
                    playerTPDelayCount.put(name, -1);
                }
            }
        }
    }

    public static void sendTpProgressBar(ServerPlayer player, int delay, Des nearDes) {
        Component title = Component.literal("正在前往:").withStyle(CustomStyle.styleOfEnd).
                append(nearDes.name);
        int progressUnitCount = 20 - delay / 3;
        Component subTitle = Component.literal("[").withStyle(CustomStyle.styleOfEnd).
                append(Component.literal("|".repeat(progressUnitCount)).withStyle(CustomStyle.styleOfEnd)).
                append(Component.literal("|".repeat(delay / 3)).withStyle(ChatFormatting.GRAY)).
                append(Component.literal("]").withStyle(CustomStyle.styleOfEnd));
        Compute.setPlayerTitleAndSubTitle(player, title, subTitle, 0, 60, 0);
    }
}
