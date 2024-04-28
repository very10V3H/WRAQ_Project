package com.very.wraq.netWorking.misc.SmartPhonePackets;

import com.very.wraq.netWorking.ModNetworking;
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
            ModNetworking.sendToClient(new SmartPhoneS2CPacket(data.getDouble("VB"),data.getDouble("security0"),
                    data.getDouble("security1"),data.getDouble("security2"),data.getDouble("security3"),data.getDouble("securityGet")),player);
        });
        return true;
    }
}
