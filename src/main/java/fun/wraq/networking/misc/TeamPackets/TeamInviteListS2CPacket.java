package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class TeamInviteListS2CPacket {

    private final Map<String, Component> inviteRequestList;

    public TeamInviteListS2CPacket(Map<String, Component> inviteRequestList) {
        this.inviteRequestList = inviteRequestList;
    }

    public TeamInviteListS2CPacket(FriendlyByteBuf buf) {
        this.inviteRequestList = buf.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readComponent);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(this.inviteRequestList, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeComponent);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.TeamInviteRequestList.clear();
            ClientUtils.TeamInviteRequestList.putAll(inviteRequestList);
        });
        return true;
    }
}
