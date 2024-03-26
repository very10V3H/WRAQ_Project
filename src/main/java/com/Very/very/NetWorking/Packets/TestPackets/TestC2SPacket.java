package com.Very.very.NetWorking.Packets.TestPackets;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TestC2SPacket {
    private final int num;
    public TestC2SPacket(int num)
    {
        this.num = num;
    }
    public TestC2SPacket(FriendlyByteBuf buf)
    {
        this.num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            if(num == 1) Compute.Broad(player.getServer().overworld(),Component.literal("test!"));
            if(num == 2) Compute.Broad(player.getServer().overworld(),Component.literal("close？"));
        });
        return true;
    }
}
