package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.events.mob.loot.RandomLootEquip;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;

public class QuickDecomposeCommand implements Command<CommandSourceStack> {
    public static QuickDecomposeCommand instance = new QuickDecomposeCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RandomLootEquip.quickDecompose(serverPlayer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        return 0;
    }
}
