package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.NetWorking.ModNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestC2SPacket {
    public RequestC2SPacket()
    {

    }
    public RequestC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            ModNetworking.sendToClient(new SmartPhoneS2CPacket(data.getFloat("VB"),data.getFloat("security0"),
                    data.getFloat("security1"),data.getFloat("security2"),data.getFloat("security3"),data.getFloat("securityGet")),player);
        });
        return true;
    }
}
