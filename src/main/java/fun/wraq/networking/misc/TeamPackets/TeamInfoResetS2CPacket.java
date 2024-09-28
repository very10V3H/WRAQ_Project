package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.TeamInfoRequestC2SPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.function.Supplier;

public class TeamInfoResetS2CPacket {
    public TeamInfoResetS2CPacket() {

    }

    public TeamInfoResetS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ClientUtils.clientPlayerTeamMap = new HashMap<>();
            ClientUtils.clientPlayerList = new HashMap<>();
            ClientUtils.TeamPlayerRequestList = new HashMap<>();
            ClientUtils.TeamInviteRequestList = new HashMap<>();
            ModNetworking.sendToServer(new TeamInfoRequestC2SPacket());

        });
        return true;
    }
}
