package com.very.wraq.process.system.teamInstance.networking;

import com.very.wraq.process.system.teamInstance.NewTeamInstanceHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
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
