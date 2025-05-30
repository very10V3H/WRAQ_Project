package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class SpecificPlayerTagCommand implements Command<CommandSourceStack> {
    public static SpecificPlayerTagCommand instance = new SpecificPlayerTagCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        String topKey = StringArgumentType.getString(context, "topKey");
        String type = StringArgumentType.getString(context, "type");
        String key = StringArgumentType.getString(context, "key");
        String valueString = StringArgumentType.getString(context, "value");
        ServerPlayer target = null;
        for (GameProfile profile : gameProfile) {
            target = context.getSource().getServer().getPlayerList().getPlayer(profile.getId());
        }
        if (target == null) {
            if (player == null) {
                LogUtils.getLogger().info("玩家似乎不在线");
            } else {
                player.sendSystemMessage(Component.literal("玩家似乎不在线"));
            }
            return 0;
        }

        CompoundTag data = Compute.getPlayerSpecificKeyCompoundTagData(target, topKey);
        switch (type) {
            case "int" -> {
                int value = Integer.parseInt(valueString);
                data.putInt(key, value);
                Compute.sendCommandOpMSG(player,
                        "已添加int类型" + "(" + key + "," + valueString + ")" + "至" + Name.get(target));
            }
            case "double" -> {
                double value = Double.parseDouble(valueString);
                data.putDouble(key, value);
                Compute.sendCommandOpMSG(player,
                        "已添加double类型" + "(" + key + "," + valueString + ")" + "至" + Name.get(target));
            }
            case "string" -> {
                data.putString(key, valueString);
                Compute.sendCommandOpMSG(player,
                        "已添加string类型" + "(" + key + "," + valueString + ")" + "至" + Name.get(target));
            }
            case "boolean" -> {
                data.putBoolean(key, valueString.equals("true"));
                Compute.sendCommandOpMSG(player,
                        "已添加boolean类型" + "(" + key + "," + valueString + ")" + "至" + Name.get(target));
            }
            case "remove" -> {
                data.remove(key);
                Compute.sendCommandOpMSG(player,
                        "已移除" + Name.get(target) + "的" + key + "键值数据");
            }
            default -> {
                Compute.sendCommandOpMSG(player, "检查类型拼写");
            }
        }
        return 0;
    }
}
