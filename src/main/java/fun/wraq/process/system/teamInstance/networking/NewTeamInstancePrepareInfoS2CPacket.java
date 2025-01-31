package fun.wraq.process.system.teamInstance.networking;

import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class NewTeamInstancePrepareInfoS2CPacket {

    private final List<Component> preparedPlayerNames;
    private final List<Component> unPreparedPlayerNames;

    public NewTeamInstancePrepareInfoS2CPacket(List<Component> preparedPlayerNames,
                                               List<Component> unPreparedPlayerNames) {
        this.preparedPlayerNames = preparedPlayerNames;
        this.unPreparedPlayerNames = unPreparedPlayerNames;
    }

    public NewTeamInstancePrepareInfoS2CPacket(FriendlyByteBuf buf) {
        this.preparedPlayerNames = buf.readList(FriendlyByteBuf::readComponent);
        this.unPreparedPlayerNames = buf.readList(FriendlyByteBuf::readComponent);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.preparedPlayerNames, FriendlyByteBuf::writeComponent);
        buf.writeCollection(this.unPreparedPlayerNames, FriendlyByteBuf::writeComponent);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NewTeamInstanceHud.clientPreparedPlayerNames = preparedPlayerNames;
            NewTeamInstanceHud.clientUnpreparedPlayerNames = unPreparedPlayerNames;
            NewTeamInstanceHud.clientDisplayLastSeconds = ClientUtils.serverTick + Tick.s(1);
        });
        return true;
    }
}
