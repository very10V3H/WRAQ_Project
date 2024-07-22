package com.very.wraq.networking.unSorted;

import com.very.wraq.process.func.security.Security;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class ClientLimitSetS2CPacket {
    private final String name;

    public ClientLimitSetS2CPacket(String name) {
        this.name = name;
    }

    public ClientLimitSetS2CPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                Security.ClientPropertiesSet(this.name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
