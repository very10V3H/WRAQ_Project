package com.Very.very.NetWorking.Packets.Limit;

import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.function.Supplier;

public class ScreenCloseC2SPacket {

    public ScreenCloseC2SPacket()
    {

    }
    public ScreenCloseC2SPacket(FriendlyByteBuf buf)
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
                PlayerCallBack playerCallBack = iterator.next();
                if (playerCallBack.getPlayer().getName().getString().equals(player.getName().getString())) {
                    Utils.playerCallBackQueue.remove(playerCallBack);
                    break;
                }
            }
        });
        return true;
    }
}
