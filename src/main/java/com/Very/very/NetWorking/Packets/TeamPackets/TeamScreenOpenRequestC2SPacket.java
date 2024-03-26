package com.Very.very.NetWorking.Packets.TeamPackets;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamScreenOpenRequestC2SPacket {
    public TeamScreenOpenRequestC2SPacket()
    {

    }
    public TeamScreenOpenRequestC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork( ()->{
                ModNetworking.sendToClient(new TeamScreenOpenS2CPacket(),serverPlayer);
        });
        return true;
    }
}
