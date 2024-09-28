package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class DebugCommand implements Command<CommandSourceStack> {
    public static DebugCommand instance = new DebugCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String name = player.getName().getString();
        playerFlagMap.put(name, !playerFlagMap.getOrDefault(name, false));
        if (!playerFlagMap.get(name)) {
            Compute.sendFormatMSG(player, Component.literal("调试").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("你已关闭伤害构成显示。").withStyle(ChatFormatting.WHITE));
        } else {
            Compute.sendFormatMSG(player, Component.literal("调试").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("你已启用伤害构成显示。").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }

    public static Map<String, Boolean> playerFlagMap = new HashMap<>();
}
