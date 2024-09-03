package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamInviteRequestS2CPacket {

    private final String name;
    private final Component displayName;

    public TeamInviteRequestS2CPacket(String uuid, Component displayName) {
        this.name = uuid;
        this.displayName = displayName;
    }

    public TeamInviteRequestS2CPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
        this.displayName = buf.readComponent();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
        buf.writeComponent(this.displayName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ClientUtils.TeamInviteRequestList.put(this.name, this.displayName);

        });
        return true;
    }
}
