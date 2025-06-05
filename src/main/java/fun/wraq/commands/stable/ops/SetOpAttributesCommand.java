package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.items.dev.equip.OpsAttributes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;


public class SetOpAttributesCommand implements Command<CommandSourceStack> {
    public static SetOpAttributesCommand instance = new SetOpAttributesCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String attribute = StringArgumentType.getString(context, "key");
        double value = DoubleArgumentType.getDouble(context, "num");
        if (player.isCreative()) {
            OpsAttributes.setValue(attribute, value);
        }
        return 0;
    }
}
