package fun.wraq.networking.misc.TeamPackets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamDeleteS2CPacket {
    private String teamLeaderName;

    public TeamDeleteS2CPacket(String teamLeaderName) {
        this.teamLeaderName = teamLeaderName;
    }

    public TeamDeleteS2CPacket(FriendlyByteBuf buf) {
        this.teamLeaderName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.teamLeaderName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {


        });
        return true;
    }
}
