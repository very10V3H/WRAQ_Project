package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.series.events.summer2025.Summer2025;
import net.minecraft.commands.CommandSourceStack;


public class EventShutdownCommand implements Command<CommandSourceStack> {
    public static EventShutdownCommand instance = new EventShutdownCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Summer2025.eventIsActive = !Summer2025.eventIsActive;
        Compute.sendCommandOpMSG(context.getSource().getPlayer(), "已设置为" + Summer2025.eventIsActive);
        return 0;
    }
}
