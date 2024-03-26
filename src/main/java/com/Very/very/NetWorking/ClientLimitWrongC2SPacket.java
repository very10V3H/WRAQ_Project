package com.Very.very.NetWorking;

import com.Very.very.Process.Security.Security;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class ClientLimitWrongC2SPacket {

    private final String name;
    public ClientLimitWrongC2SPacket(String name)
    {
        this.name = name;
    }
    public ClientLimitWrongC2SPacket(FriendlyByteBuf buf)
    {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork( ()-> {
            try {
                Security.serverWrite(this.name + " " + serverPlayer.getName().getString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
