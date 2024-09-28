package fun.wraq.process.system.teamInstance.networking;

import fun.wraq.process.system.teamInstance.NewTeamInstanceHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NewTeamInstanceClearS2CPacket {


    public NewTeamInstanceClearS2CPacket() {

    }

    public NewTeamInstanceClearS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NewTeamInstanceHud.clientPreparedPlayerNames.clear();
            NewTeamInstanceHud.clientUnpreparedPlayerNames.clear();
            NewTeamInstanceHud.clientJoinedPlayerNames.clear();
        });
        return true;
    }
}
