/** AI-Generated, 2026-07-12 */
package fun.wraq.process.system.tp;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.tp.networking.WaypointTeleportS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.spring2024.FireworkGun;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class WaypointTeleportHandler {

    /** 允许传送的目的地列表 (名称 → 坐标) */
    public static final Map<String, Vec3> ALLOWED_WAYPOINTS = new LinkedHashMap<>() {{
        put("潮汐城中央广场", new Vec3(3925, 82, 3499));
        put("潮汐城东北门", new Vec3(3977, 76, 3416));
        put("项潮林", new Vec3(4021, 119, 3158));
    }};

    /** 坐标匹配容差 (方块半径) */
    private static final double MATCH_TOLERANCE = 3.0;

    /** 解锁交互范围 */
    private static final double UNLOCK_RANGE = 4.0;

    /** 匹配 /tp @s <x> <y> <z> 格式 */
    private static final Pattern TP_SELF_COORDS_PATTERN =
            Pattern.compile("^/?tp @s (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)$");

    // =================================================================
    //  待传送队列：冷却时记录目标，冷却结束后自动传送
    // =================================================================

    /** 玩家名 → {目标坐标, 锚点名} */
    private static final Map<String, PendingTeleport> pendingTeleports = new HashMap<>();

    private record PendingTeleport(Vec3 pos, String name) {}

    @SubscribeEvent
    public static void onServerTick(net.minecraftforge.event.TickEvent.ServerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;

        Iterator<Map.Entry<String, PendingTeleport>> it = pendingTeleports.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, PendingTeleport> entry = it.next();
            String pName = entry.getKey();
            PendingTeleport pending = entry.getValue();

            int tick = Tick.get();
            int cooldownEnd = GateWay.playerTPCooldownMap.getOrDefault(pName, 0);
            if (tick >= cooldownEnd) {
                ServerPlayer player = Tick.server.getPlayerList().getPlayerByName(pName);
                if (player != null && player.isAlive()) {
                    Vec3 target = pending.pos();
                    player.teleportTo(player.server.getLevel(Level.OVERWORLD),
                            target.x, target.y, target.z, player.getYRot(), player.getXRot());
                    Compute.sendFormatMSG(player,
                            Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                            Te.s("冷却结束，已自动传送至 - ", pending.name(), CustomStyle.styleOfWorld));
                }
                it.remove();
            }
        }
    }


    // =================================================================
    //  玩家 persistentData 存储 key
    // =================================================================

    private static final String WAYPOINT_TP_DATA_KEY = "WaypointTPData";

    /**
     * 获取玩家传送锚点数据所在的 CompoundTag
     */
    private static CompoundTag getWaypointData(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(WAYPOINT_TP_DATA_KEY)) {
            data.put(WAYPOINT_TP_DATA_KEY, new CompoundTag());
        }
        return data.getCompound(WAYPOINT_TP_DATA_KEY);
    }

    /**
     * 判断玩家是否已解锁指定锚点
     */
    public static boolean isWaypointUnlocked(Player player, String waypointName) {
        return getWaypointData(player).getBoolean(waypointName);
    }

    /**
     * 设置锚点解锁状态
     */
    private static void setWaypointUnlocked(Player player, String waypointName) {
        getWaypointData(player).putBoolean(waypointName, true);
    }

    /**
     * 获取所有锚点的解锁状态数组（用于 S2C 同步）
     */
    private static String[] getAllWaypointNames() {
        return ALLOWED_WAYPOINTS.keySet().toArray(new String[0]);
    }

    private static boolean[] getUnlockedStatusArray(Player player) {
        String[] names = getAllWaypointNames();
        boolean[] status = new boolean[names.length];
        for (int i = 0; i < names.length; i++) {
            status[i] = isWaypointUnlocked(player, names[i]);
        }
        return status;
    }

    /**
     * 向客户端同步所有锚点的解锁状态
     */
    private static void syncAllToClient(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ModNetworking.sendToClient(
                    new WaypointTeleportS2CPacket(getAllWaypointNames(), getUnlockedStatusArray(player)),
                    serverPlayer);
        }
    }

    // =================================================================
    //  玩家登录时同步锚点状态
    // =================================================================

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncAllToClient(serverPlayer);
        }
    }

    // =================================================================
    //  右键解锁锚点 (监听所有右键方式：物品/空点/方块)
    // =================================================================

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        tryUnlockWaypoint(event);
    }

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        tryUnlockWaypoint(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        tryUnlockWaypoint(event);
    }

    private static void tryUnlockWaypoint(PlayerInteractEvent event) {
        if (event.getSide().isClient()) return;
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        // 检查是否在任意锚点附近且可解锁
        for (Map.Entry<String, Vec3> entry : ALLOWED_WAYPOINTS.entrySet()) {
            String name = entry.getKey();
            Vec3 wpPos = entry.getValue();
            double dist = player.position().distanceTo(wpPos);

            if (dist < UNLOCK_RANGE && !isWaypointUnlocked(player, name)) {
                // 解锁
                setWaypointUnlocked(player, name);

                // 全量同步客户端状态 (避免单点更新覆盖其他条目)
                syncAllToClient(serverPlayer);

                // 播放烟花特效
                FireworkGun.summonFireWork(player.level(), wpPos);
                // 额外在附近随机位置放几个烟花
                Random random = new Random();
                for (int i = 0; i < 3; i++) {
                    Vec3 offset = new Vec3(
                            (random.nextDouble() - 0.5) * 3,
                            0.5,
                            (random.nextDouble() - 0.5) * 3);
                    FireworkGun.summonFireWork(player.level(), wpPos.add(offset));
                }

                Compute.sendFormatMSG(player,
                        Te.s("传送锚点", CustomStyle.styleOfEnd),
                        Te.s("已解锁传送锚点 - ", name, CustomStyle.styleOfWorld,
                                "。按\"M\"打开地图右键路径点进行传送"));

                event.setCanceled(true);
                return;
            }
        }
    }


    // =================================================================
    //  1. 反射移除 /tp 的 OP 要求
    // =================================================================

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        // 通过 getChild 返回的运行时类型找到 /tp 节点，避免直接引用 com.mojang.brigadier.CommandNode
        var tpNode = event.getDispatcher().getRoot().getChild("tp");
        if (tpNode == null) return;

        try {
            // 从实际节点的类层次向上遍历，找到 "requirement" 字段
            // (Brigadier 1.0.18 中 CommandNode.requirement 是 private final Predicate<S>)
            Class<?> clazz = tpNode.getClass();
            Field reqField = null;
            while (clazz != null && reqField == null) {
                try {
                    reqField = clazz.getDeclaredField("requirement");
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (reqField == null) throw new NoSuchFieldException("requirement");

            reqField.setAccessible(true);

            // 去掉 final 修饰符 (JDK 17+ 需要)
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(reqField, reqField.getModifiers() & ~Modifier.FINAL);
            } catch (Exception ignored) {
            }

            // 设为允许所有玩家 (原为 requires -> hasPermission(2))
            reqField.set(tpNode, (Predicate<CommandSourceStack>) s -> true);
        } catch (Exception e) {
            // 反射失败时记录但不阻塞启动
            System.out.println("[VMD] 无法修改 /tp 权限 (非OP玩家将无法使用路径点传送): " + e.getMessage());
        }

        // 同时注册 /vmd_waypoint_tp 作为备用命令
        event.getDispatcher().register(
                Commands.literal("vmd_waypoint_tp")
                        .then(Commands.argument("x", DoubleArgumentType.doubleArg())
                                .then(Commands.argument("y", DoubleArgumentType.doubleArg())
                                        .then(Commands.argument("z", DoubleArgumentType.doubleArg())
                                                .executes(WaypointTeleportCommandHandler.INSTANCE)))));
    }

    // =================================================================
    //  2. CommandEvent 拦截 /tp → 校验坐标 + 解锁状态 + 冷却
    // =================================================================

    @SubscribeEvent
    public static void onCommand(CommandEvent event) {
        CommandSourceStack source = event.getParseResults().getContext().getSource();
        if (!(source.getEntity() instanceof ServerPlayer player)) return;

        // 只拦截 /tp 命令
        // (getNodes() 返回 Collection，需要用 iterator 而非 get(0))
        var nodesIter = event.getParseResults().getContext().getNodes().iterator();
        if (!nodesIter.hasNext()) return;
        String cmdName = nodesIter.next().getNode().getName();
        if (!"tp".equals(cmdName)) return;

        // OP 玩家 → 放行 (完整 /tp 权限)
        /*if (player.hasPermissions(2)) return;*/
        if (player.isCreative()) return;

        // 非 OP 玩家 → 拒绝执行，改为 VMD 的受控传送流
        event.setCanceled(true);

        String fullInput = event.getParseResults().getReader().getString();
        Matcher matcher = TP_SELF_COORDS_PATTERN.matcher(fullInput.trim());

        if (!matcher.matches()) {
            source.sendFailure(Component.literal("你没有权限使用此命令").withStyle(ChatFormatting.RED));
            return;
        }

        double x = Double.parseDouble(matcher.group(1));
        double y = Double.parseDouble(matcher.group(2));
        double z = Double.parseDouble(matcher.group(3));

        // 检查是否在允许的路径点列表中
        Vec3 target = new Vec3(x, y, z);

        // 找到匹配的锚点名称
        String matchedName = null;
        for (Map.Entry<String, Vec3> entry : ALLOWED_WAYPOINTS.entrySet()) {
            if (entry.getValue().distanceTo(target) < MATCH_TOLERANCE) {
                matchedName = entry.getKey();
                break;
            }
        }

        if (matchedName == null) {
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("此路径点不可传送").withStyle(ChatFormatting.RED));
            return;
        }

        // 检查锚点是否已解锁
        if (!isWaypointUnlocked(player, matchedName)) {
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Te.s("传送锚点 - ", matchedName, CustomStyle.styleOfWorld,
                            " 尚未解锁！请前往该位置右键以解锁"));
            return;
        }

        // 冷却检查 (复用 GateWay 的冷却 Map)
        String pName = player.getName().getString();
        int tick = Tick.get();
        int cooldownEnd = GateWay.playerTPCooldownMap.getOrDefault(pName, 0);
        if (tick < cooldownEnd) {
            // 记录待传送目标，冷却结束后自动传送
            pendingTeleports.put(pName, new PendingTeleport(target, matchedName));
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Te.s("传送冷却中，剩余 ", String.valueOf((cooldownEnd - tick) / 20), " 秒",
                            CustomStyle.styleOfFlexible, "。冷却结束后将自动传送至 ",
                            matchedName, CustomStyle.styleOfWorld));
            return;
        }

        // 执行传送
        player.teleportTo(player.server.getLevel(Level.OVERWORLD), x, y, z, player.getYRot(), player.getXRot());

        // 清除待传送记录（主动传送后不再需要）
        pendingTeleports.remove(pName);

        // 设置传送冷却 (3秒冷却，与 GateWay 的 60 tick 一致)
        GateWay.playerTPCooldownMap.put(pName, tick + 60);

        Compute.sendFormatMSG(player,
                Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                Component.literal("已传送至目标路径点").withStyle(ChatFormatting.GREEN));
    }

    // =================================================================
    //  3. 备用命令处理器 (/vmd_waypoint_tp <x> <y> <z>)
    //     当反射失效时可手动调用
    // =================================================================

    public static class WaypointTeleportCommandHandler implements Command<CommandSourceStack> {
        public static final WaypointTeleportCommandHandler INSTANCE = new WaypointTeleportCommandHandler();

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            ServerPlayer player = context.getSource().getPlayerOrException();
            double x = DoubleArgumentType.getDouble(context, "x");
            double y = DoubleArgumentType.getDouble(context, "y");
            double z = DoubleArgumentType.getDouble(context, "z");

            Vec3 target = new Vec3(x, y, z);

            String matchedName = null;
            for (Map.Entry<String, Vec3> entry : ALLOWED_WAYPOINTS.entrySet()) {
                if (entry.getValue().distanceTo(target) < MATCH_TOLERANCE) {
                    matchedName = entry.getKey();
                    break;
                }
            }

            if (matchedName == null) {
                Compute.sendFormatMSG(player,
                        Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("此路径点不可传送").withStyle(ChatFormatting.RED));
                return 0;
            }

            if (!isWaypointUnlocked(player, matchedName)) {
                Compute.sendFormatMSG(player,
                        Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                        Te.s("传送锚点 - ", matchedName, CustomStyle.styleOfWorld,
                                " 尚未解锁！请前往该位置右键以解锁"));
                return 0;
            }

            player.teleportTo(player.server.getLevel(Level.OVERWORLD), x, y, z, player.getYRot(), player.getXRot());
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("已传送至目标路径点").withStyle(ChatFormatting.GREEN));
            return 1;
        }
    }
}
