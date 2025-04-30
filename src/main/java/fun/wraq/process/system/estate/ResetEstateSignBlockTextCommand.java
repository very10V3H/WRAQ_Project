package fun.wraq.process.system.estate;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class ResetEstateSignBlockTextCommand implements Command<CommandSourceStack> {
    public static ResetEstateSignBlockTextCommand instance = new ResetEstateSignBlockTextCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        EstateUtil.resetAllSignBlockText();
        return 0;
    }
}
