package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.vp.VpDataHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class VpRechargeCommand implements Command<CommandSourceStack> {
    public static VpRechargeCommand instance = new VpRechargeCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        String s = StringArgumentType.getString(context, "value");
        double num = Double.parseDouble(s);
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = Tick.server.getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.sendCommandOpMSG(player, "玩家不在线.");
                return 0;
            }
            VpDataHandler.rechargeVp(target, num);
            Compute.sendCommandOpMSG(player, Name.get(target) + " 获得了 " + num + "vp");
        }
        return 0;
    }
}
