package com.very.wraq.process.system.teamInstance.networking;

import com.very.wraq.process.system.teamInstance.NewTeamInstanceHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class NewTeamInstanceJoinedPlayerInfoS2CPacket {

    private final List<Component> joinedPlayerNames;

    public NewTeamInstanceJoinedPlayerInfoS2CPacket(List<Component> joinedPlayerNames) {
        this.joinedPlayerNames = joinedPlayerNames;
    }

    public NewTeamInstanceJoinedPlayerInfoS2CPacket(FriendlyByteBuf buf) {
        this.joinedPlayerNames = buf.readList(FriendlyByteBuf::readComponent);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.joinedPlayerNames, FriendlyByteBuf::writeComponent);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NewTeamInstanceHud.clientJoinedPlayerNames = joinedPlayerNames;
        });
        return true;
    }
}
