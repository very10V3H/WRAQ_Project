package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.rank.RankData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class RankDataCommand implements Command<CommandSourceStack> {
    public static RankDataCommand instance = new RankDataCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        String rank = StringArgumentType.getString(context, "rank");
        String description = StringArgumentType.getString(context, "description");
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                RankData.sendFormatMSG(player, Te.s("玩家似乎不在线"));
                return 0;
            }
            // target -> player
            if (!RankData.rankStyleMap.containsKey(rank)) {
                RankData.sendFormatMSG(player, Te.s("该职级不存在"));
                return 0;
            }
            RankData.addNewRank(target, rank, description);
            RankData.sendFormatMSG(player, Te.s("已为", target.getDisplayName(), "添加了职称", rank,
                    RankData.rankStyleMap.get(rank)));
        }
        return 0;
    }
}
