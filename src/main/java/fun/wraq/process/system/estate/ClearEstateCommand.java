package fun.wraq.process.system.estate;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;


public class ClearEstateCommand implements Command<CommandSourceStack> {
    public static ClearEstateCommand instance = new ClearEstateCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("玩家似乎不在线。。。").withStyle(ChatFormatting.WHITE));
                return 0;
            }
            int serial = EstatePlayerData.getEstateSerial(player);
            EstateInfo estateInfo = EstateInfo.values()[serial];
            EstateServerData.removeEstateServerData(serial);
            EstatePlayerData.setEstateData(player, -1, "");
            EstateUtil.sendMSG(player, Te.s("你已失去",
                    estateInfo.estateName.getString(), CustomStyle.styleOfGold, "的所有权."));
            EstateUtil.resetSignBlockText(estateInfo);
        }
        return 0;
    }
}
