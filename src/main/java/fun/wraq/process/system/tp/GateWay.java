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
                        new Des(new Vec3(2354, 169, 1752), -90, 0, Component.literal("绯樱村").withStyle(CustomStyle.styleOfSakura)))
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
                if (nearGateway) {
                    if (InventoryOperation.itemStackCount(player, ModItems.WORLD_SOUL_2.get()) == 0
                            && InventoryOperation.itemStackCount(player, ModItems.TP_TICKET.get()) == 0
                            && PlanPlayer.getPlayerTier(player) < 2) {
                        playerTPDelayCount.put(name, -1);
                        Compute.setPlayerTitleAndSubTitle(player, Component.literal("位移进程中断").withStyle(ChatFormatting.RED),
                                Te.s("需要一个",
                                        ModItems.TP_TICKET.get().getDefaultInstance().getDisplayName(), "或",
                                        ModItems.WORLD_SOUL_2.get().getDefaultInstance().getDisplayName()),
                                0, 20, 10);
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
                            Compute.setPlayerTitleAndSubTitle(player, Component.literal("位移进程完成").withStyle(CustomStyle.styleOfEnd),
                                    Component.literal("已前往:").withStyle(CustomStyle.styleOfEnd).
                                            append(destination.name), 20, 60, 20);
                            playerTPDelayCount.put(name, -1);
                            if (destination.pos.distanceTo(skyTpCenterPos) < 30)
                                playerTPCooldownMap.put(name, tick + 2);
                            else playerTPCooldownMap.put(name, tick + 60);
                            if (PlanPlayer.getPlayerTier(player) < 2) {
                                if (!InventoryOperation.removeItem(player.getInventory(), ModItems.TP_TICKET.get(), 1)) {
                                    InventoryOperation.removeItem(player.getInventory(), ModItems.WORLD_SOUL_2.get(), 1);
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
