package com.very.wraq.networking.unSorted;

import com.mojang.logging.LogUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientLimitWrongC2SPacket {

    private final String name;

    public ClientLimitWrongC2SPacket(String name) {
        this.name = name;
    }

    public ClientLimitWrongC2SPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            LogUtils.getLogger().info("{} {} {}", serverPlayer.getName().getString(), this.name, Utils.LogTypes.speciousLogin);
        });
        return true;
    }
}
