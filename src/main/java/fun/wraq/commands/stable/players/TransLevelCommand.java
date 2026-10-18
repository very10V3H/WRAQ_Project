package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.translevel.TransLevelDimension;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Collectors;

/**
 * 跨维度传送命令：/vmd transLevel <维度标签>
 * 传送到目标维度中该玩家上次所在的位置；若无记录则传送到目标维度出生点。
 * AI-Generated, 2026-08-23
 */
public class TransLevelCommand implements Command<CommandSourceStack> {
    public static TransLevelCommand instance = new TransLevelCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String label = StringArgumentType.getString(context, "label");
        ResourceKey<Level> key = TransLevelDimension.get(label);
        if (key == null) {
            player.sendSystemMessage(Te.s("未知维度标签：", label, "。可用标签：",
                    TransLevelDimension.labels().keySet().stream().collect(Collectors.joining(", ")),
                    ChatFormatting.AQUA));
            return 0;
        }
        ServerLevel target = Tick.server.getLevel(key);
        if (target == null) {
            player.sendSystemMessage(Te.s("目标维度 ", key.location().toString(), " 尚未加载。", ChatFormatting.RED));
            return 0;
        }
        Vec3 dest = TransLevelDimension.getLastPosition(player, key);
        float yaw;
        float pitch;
        if (dest == null) {
            BlockPos spawn = target.getSharedSpawnPos();
            dest = new Vec3(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5);
            yaw = 0;
            pitch = 0;
        } else {
            // 轻微安全：记录位置若超出构建高度（地形被替换等），改为落地到地表
            if (dest.y < target.getMinBuildHeight() || dest.y >= target.getMaxBuildHeight()) {
                dest = new Vec3(dest.x,
                        target.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) dest.x, (int) dest.z), dest.z);
            }
            yaw = TransLevelDimension.getLastYaw(player, key);
            pitch = TransLevelDimension.getLastPitch(player, key);
        }
        player.teleportTo(target, dest.x, dest.y, dest.z, yaw, pitch);
        return 1;
    }
}
