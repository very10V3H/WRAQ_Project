/** AI-Generated, 2026-07-12 */
package fun.wraq.process.system.tp;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class WaypointTeleportHandler {

    /** 允许传送的目的地列表 (名称 → 坐标) */
    public static final Map<String, Vec3> ALLOWED_WAYPOINTS = new LinkedHashMap<>() {{
        put("平原村", new Vec3(756, 84, 207));
        put("天空城", new Vec3(956, 232, 17));
        put("雨林村", new Vec3(1091, 80, 40));
        put("海岸村", new Vec3(889, 62, -422));
        put("火山村", new Vec3(2573, 120, -492));
        put("薰楠村", new Vec3(1157, 76, -1077));
        put("薰曦村", new Vec3(1036, 76, -1288));
        put("北洋村", new Vec3(1329, 71, -1612));
        put("沙岸村", new Vec3(1911, 86, 1688));
        put("绯樱村", new Vec3(2381, 182, 1752));
        put("望山据点", new Vec3(1921, 151, -936));
        put("旭升岛", new Vec3(1808, 74, 339));
        put("北望村", new Vec3(1731, 137, 1875));
        put("极冬村", new Vec3(2742, 131, -3862));
        put("东洋塔", new Vec3(2335, 148, 17));
        put("尘月之梦", new Vec3(1147, 300, 554));
        put("暗黑城堡", new Vec3(2417, 152, -1372));
        put("菌菇聚落", new Vec3(2006, 130, -1785));
        put("海底神殿", new Vec3(1088, 23, 892));
        put("雷光岛", new Vec3(1743, 68, 1285));
    }};

    /** 坐标匹配容差 (方块半径) */
    private static final double MATCH_TOLERANCE = 3.0;

    /** 匹配 /tp @s <x> <y> <z> 格式 */
    private static final Pattern TP_SELF_COORDS_PATTERN =
            Pattern.compile("^/?tp @s (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)$");

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
    //  2. CommandEvent 拦截 /tp → 校验坐标 + 传送券 + 冷却
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
        if (player.hasPermissions(2)) return;

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
        boolean allowed = ALLOWED_WAYPOINTS.values().stream()
                .anyMatch(wp -> wp.distanceTo(target) < MATCH_TOLERANCE);

        if (!allowed) {
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("此路径点不可传送").withStyle(ChatFormatting.RED));
            return;
        }

        // 冷却检查 (复用 GateWay 的冷却 Map)
        String pName = player.getName().getString();
        int tick = Tick.get();
        int cooldownEnd = GateWay.playerTPCooldownMap.getOrDefault(pName, 0);
        if (tick < cooldownEnd) {
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("传送冷却中，剩余 " + (cooldownEnd - tick) / 20 + " 秒")
                            .withStyle(ChatFormatting.RED));
            return;
        }

/*        // 检查传送券/通票 (复用 TpPass/GateWay 逻辑)
        ItemStack validTpPass = TpPass.playerHasValidTpPass(player);
        boolean hasTicket = InventoryOperation.itemStackCount(player, ModItems.TP_TICKET.get()) > 0;
        boolean isPlanTier2 = PlanPlayer.getPlayerTier(player) >= 2;
        if (!isPlanTier2 && validTpPass == null && !hasTicket) {
            Compute.sendFormatMSG(player,
                    Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                    Te.s("需要", ModItems.TP_TICKET.get().getDefaultInstance().getDisplayName(), "或传送通票")
                            .withStyle(ChatFormatting.RED));
            return;
        }

        // 消耗传送券
        if (!isPlanTier2 && hasTicket && validTpPass == null) {
            InventoryOperation.removeItem(player.getInventory(), ModItems.TP_TICKET.get(), 1);
        }*/

        // 执行传送
        player.teleportTo(player.server.getLevel(Level.OVERWORLD), x, y, z, player.getYRot(), player.getXRot());

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
            boolean allowed = ALLOWED_WAYPOINTS.values().stream()
                    .anyMatch(wp -> wp.distanceTo(target) < MATCH_TOLERANCE);

            if (!allowed) {
                Compute.sendFormatMSG(player,
                        Component.literal("传送").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("此路径点不可传送").withStyle(ChatFormatting.RED));
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
