package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.ClientPlayerTeam;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class TeamInfoS2CPacket {

    private final List<String> playerList;
    private final List<Component> playerDisplayNameList;

    private final String teamName;
    private final int num;

    public TeamInfoS2CPacket(String teamName, List<String> playerList, List<Component> playerDisplayNameList, int num) {
        this.teamName = teamName;
        this.playerList = playerList;
        this.playerDisplayNameList = playerDisplayNameList;
        this.num = num;
    }

    public TeamInfoS2CPacket(FriendlyByteBuf buf) {
        this.teamName = buf.readUtf();
        this.playerList = buf.readList(FriendlyByteBuf::readUtf);
        this.playerDisplayNameList = buf.readList(FriendlyByteBuf::readComponent);
        this.num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(teamName);
        buf.writeCollection(this.playerList, FriendlyByteBuf::writeUtf);
        buf.writeCollection(this.playerDisplayNameList, FriendlyByteBuf::writeComponent);
        buf.writeInt(num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPlayerTeam clientPlayerTeam = new ClientPlayerTeam(this.teamName,
                    playerList, playerDisplayNameList);
            playerList.forEach(s -> {
                ClientUtils.clientPlayerTeamMap.put(s, clientPlayerTeam);
            });
        });
        return true;
    }
}
