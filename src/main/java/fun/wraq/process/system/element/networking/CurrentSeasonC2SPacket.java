package fun.wraq.process.system.element.networking;

import fun.wraq.common.Compute;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.networking.CurrentSeasonAndResonanceTypeS2CPacket;
import fun.wraq.process.system.missions.side.ElementStudyQuest;
import fun.wraq.process.system.season.MySeason;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CurrentSeasonC2SPacket {

    public CurrentSeasonC2SPacket() {

    }

    public CurrentSeasonC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            if (player.experienceLevel < Element.ROULETTE_REQUIRED_LEVEL) {
                Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.AQUA),
                        Component.literal("需要达到 135 级才能打开元素轮盘。").withStyle(ChatFormatting.RED));
                return;
            }
            ElementStudyQuest.onOpenedRoulette(player);
            ModNetworking.sendToClient(
                    new CurrentSeasonAndResonanceTypeS2CPacket(MySeason.currentSeason,
                            Element.PlayerResonanceType.getOrDefault(player, "")),
                    player);
        });
        return true;
    }
}
