package com.Very.very.NetWorking.Packets.ParticlePackets;

import com.Very.very.NetWorking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class SnowSwordParticleC2SPacket {
    private final double X;
    private final double Y;
    private final double Z;
    private final double X1;
    private final double Y1;
    private final double Z1;
    public SnowSwordParticleC2SPacket(double X, double Y, double Z, double X1, double Y1, double Z1)
    {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.X1 = X1;
        this.Y1 = Y1;
        this.Z1 = Z1;
    }
    public SnowSwordParticleC2SPacket(FriendlyByteBuf buf)
    {
        this.X = buf.readDouble();
        this.Y = buf.readDouble();
        this.Z = buf.readDouble();
        this.X1 = buf.readDouble();
        this.Y1 = buf.readDouble();
        this.Z1 = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeDouble(this.X);
        buf.writeDouble(this.Y);
        buf.writeDouble(this.Z);
        buf.writeDouble(this.X1);
        buf.writeDouble(this.Y1);
        buf.writeDouble(this.Z1);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
            Iterator<ServerPlayer> iterator = playerList.iterator();
            while(iterator.hasNext())
            {
                ModNetworking.sendToClient(new SnowSwordParticleS2CPacket(X,Y,Z,X1,Y1,Z1),iterator.next());
            }
        });
        return true;
    }
}
