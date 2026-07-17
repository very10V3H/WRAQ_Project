/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TitleUtil {

    public static void setPlayerTitleAndSubTitle(ServerPlayer serverPlayer, Component title, Component subTitle,
                                                  int fadeIn, int stay, int fadeOut) {
        serverPlayer.connection.send(new ClientboundSetTitleTextPacket(title));
        serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(subTitle));
        serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut));
    }

    public static void setPlayerTitleAndSubTitle(ServerPlayer serverPlayer, Component title, Component subTitle) {
        setPlayerTitleAndSubTitle(serverPlayer, title, subTitle, 20, 60, 20);
    }

    public static void setPlayerTitleAndSubTitle(Player player, Component title, Component subTitle) {
        setPlayerTitleAndSubTitle((ServerPlayer) player, title, subTitle, 20, 60, 20);
    }

    public static void setPlayerTitleAndSubTitle(Player player, Component title, Component subTitle,
                                                  int fadeIn, int stay, int fadeOut) {
        setPlayerTitleAndSubTitle((ServerPlayer) player, title, subTitle, fadeIn, stay, fadeOut);
    }

    public static void setPlayerShortTitleAndSubTitle(Player player, Component title, Component subTitle) {
        setPlayerTitleAndSubTitle((ServerPlayer) player, title, subTitle, 0, 20, 10);
    }

    public static void clearPlayerScreen(Player player) {
        ModNetworking.sendToClient(new ScreenSetS2CPacket(0), (ServerPlayer) player);
    }
}
