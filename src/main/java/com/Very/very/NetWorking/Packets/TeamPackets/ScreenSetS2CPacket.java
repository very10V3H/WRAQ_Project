package com.Very.very.NetWorking.Packets.TeamPackets;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScreenSetS2CPacket {
    private final int type;
    public ScreenSetS2CPacket(int type)
    {
        this.type = type;
    }
    public ScreenSetS2CPacket(FriendlyByteBuf buf)
    {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.clientScreenSetFlag = this.type;
        });
        return true;
    }
}
