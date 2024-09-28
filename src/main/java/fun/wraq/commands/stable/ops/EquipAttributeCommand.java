package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class EquipAttributeCommand implements Command<CommandSourceStack> {
    public static EquipAttributeCommand instance = new EquipAttributeCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        if (serverPlayer == null || !serverPlayer.isCreative()) return 0;
        String equip = StringArgumentType.getString(context, "equip");
        String attribute = StringArgumentType.getString(context, "attribute");
        Double value = DoubleArgumentType.getDouble(context, "value");

        return 0;
    }

}
