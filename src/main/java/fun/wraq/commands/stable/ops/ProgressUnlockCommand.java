package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.List;

public class ProgressUnlockCommand implements Command<CommandSourceStack> {
    public static ProgressUnlockCommand instance = new ProgressUnlockCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "name");
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("玩家似乎不在线。。。").withStyle(ChatFormatting.WHITE));
                return 0;
            }

            // target -> player
            List<String> tags = List.of(NoTeamInstanceModule.AllowRewardKey.blackCastle,
                    NoTeamInstanceModule.AllowRewardKey.nether,
                    NoTeamInstanceModule.AllowRewardKey.iceKnight,
                    NoTeamInstanceModule.AllowRewardKey.purpleIron,
                    NoTeamInstanceModule.AllowRewardKey.devil,
                    NoTeamInstanceModule.AllowRewardKey.moon,
                    NoTeamInstanceModule.AllowRewardKey.sakuraBoss,
                    NoTeamInstanceModule.AllowRewardKey.blackCastle,
                    NoTeamInstanceModule.AllowRewardKey.moontainBoss,
                    NoTeamInstanceModule.AllowRewardKey.enderGuardian,
                    NoTeamInstanceModule.AllowRewardKey.warden,
                    NoTeamInstanceModule.AllowRewardKey.harbinger,
                    NoTeamInstanceModule.AllowRewardKey.bunker,
                    NoTeamInstanceModule.AllowRewardKey.divine);
            tags.forEach(s -> {
                NoTeamInstanceModule.putPlayerAllowReward(target, s, true);
            });
            Compute.sendFormatMSG(target, Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("所有副本的前置条件已解锁").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
