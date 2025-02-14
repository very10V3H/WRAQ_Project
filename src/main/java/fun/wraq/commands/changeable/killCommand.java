package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class killCommand implements Command<CommandSourceStack> {
    public static killCommand instance = new killCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        player.sendSystemMessage(Te.s("击杀数统计如下:"));
        for (MobSpawnController controller : MobSpawn.getAllControllers(true)) {
            player.sendSystemMessage(Te.s(controller.mobName, " : ",
                    MobSpawn.getPlayerKillCount(player, controller.mobName.getString())));
        }
        for (NoTeamInstance noTeamInstance : NoTeamInstanceModule.getAllInstance()) {
            player.sendSystemMessage(Te.s(noTeamInstance.name, " : ",
                    MobSpawn.getPlayerKillCount(player, noTeamInstance.name.getString())));
        }
        for (NewTeamInstance instance : NewTeamInstanceHandler.getInstances()) {
            player.sendSystemMessage(Te.s(instance.description, " : ",
                    MobSpawn.getPlayerKillCount(player, instance.description.getString())));
        }
        player.sendSystemMessage(Te.s("总击杀数 : ",
                MobSpawn.totalKillCountCache.getOrDefault(Name.get(player), 0)));
        return 0;
    }
}
