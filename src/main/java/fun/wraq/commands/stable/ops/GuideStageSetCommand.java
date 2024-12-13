package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.process.func.guide.Guide;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class GuideStageSetCommand implements Command<CommandSourceStack> {
    public static GuideStageSetCommand instance = new GuideStageSetCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        int stage = IntegerArgumentType.getInteger(context, "stage");
        Guide.setPlayerCurrentStage(player, stage);
        return 0;
    }
}
