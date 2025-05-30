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

import java.util.HashSet;
import java.util.Set;

public class killCommand implements Command<CommandSourceStack> {
    public static killCommand instance = new killCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        player.sendSystemMessage(Te.s("击杀数统计如下:"));
        Set<String> set = new HashSet<>();
        for (MobSpawnController controller : MobSpawn.getAllControllers(true)) {
            if (!set.contains(controller.mobName.getString())) {
                set.add(controller.mobName.getString());
                player.sendSystemMessage(Te.s(controller.mobName, " : ",
                        MobSpawn.getPlayerKillCount(player, controller.mobName.getString())));
            }
        }
        for (NoTeamInstance noTeamInstance : NoTeamInstanceModule.getAllInstance()) {
            if (!set.contains(noTeamInstance.name.getString())) {
                set.add(noTeamInstance.name.getString());
                player.sendSystemMessage(Te.s(noTeamInstance.name, " : ",
                        MobSpawn.getPlayerKillCount(player, noTeamInstance.name.getString())));
            }
        }
        for (NewTeamInstance instance : NewTeamInstanceHandler.getInstances()) {
            if (!set.contains(instance.description.getString())) {
                set.add(instance.description.getString());
                player.sendSystemMessage(Te.s(instance.description, " : ",
                        MobSpawn.getPlayerKillCount(player, instance.description.getString())));
            }
        }
        player.sendSystemMessage(Te.s("总击杀数 : ",
                MobSpawn.totalKillCountCache.getOrDefault(Name.get(player), 0)));
        return 0;
    }
}
