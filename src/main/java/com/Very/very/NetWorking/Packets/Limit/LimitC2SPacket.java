package com.Very.very.NetWorking.Packets.Limit;

import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.function.Supplier;

public class LimitC2SPacket {
    public LimitC2SPacket()
    {

    }
    public LimitC2SPacket(FriendlyByteBuf buf)
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
            Iterator<PlayerCallBack> iterator = Utils.playerCallBackQueue.iterator();
            while(iterator.hasNext())
            {
                PlayerCallBack PerCallBack = iterator.next();
                if(PerCallBack.getPlayer().getName().equals(player.getName()))
                {
                    PerCallBack.setTime(3);
                }
            }
        });
        return true;
    }
}
