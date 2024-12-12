package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.randomevent.RandomEventsHandler;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class RandomEventOperationCommand implements Command<CommandSourceStack> {
    public static RandomEventOperationCommand instance = new RandomEventOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "ready" -> RandomEventsHandler.ready();
            case "begin" -> {
                if (RandomEventsHandler.nextTimeEvent == null) {
                    Compute.sendFormatMSG(player, Te.s("随机事件", CustomStyle.styleOfFlexible),
                            Te.s("请先使用ready创建一个就绪的随机事件"));
                } else {
                    RandomEventsHandler.begin();
                }
            }
            case "finish" -> {
                if (RandomEventsHandler.nextTimeEvent == null) {
                    Compute.sendFormatMSG(player, Te.s("随机事件", CustomStyle.styleOfFlexible),
                            Te.s("请先使用ready创建一个就绪的随机事件"));
                } else {
                    RandomEventsHandler.forcedFinish();
                }
            }
            default -> Compute.sendFormatMSG(player, Te.s("随机事件", CustomStyle.styleOfFlexible),
                    Te.s("请检查类型拼写"));
        }
        return 0;
    }
}
