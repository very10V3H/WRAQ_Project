package com.Very.very.NetWorking.Packets.QuartzSword;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class QuartzSwordParticleS2CPacket {
    private final double x;
    private final double y;
    private final double z;
    private final int index;
    public QuartzSwordParticleS2CPacket(double x,double y,double z,int index)
    {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public QuartzSwordParticleS2CPacket(FriendlyByteBuf buf)
    {
        this.index = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.index);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.QuartzSabreParticlePos = index;
            ClientUtils.QuartzSabreParticleIndexX = x;
            ClientUtils.QuartzSabreParticleIndexY = y;
            ClientUtils.QuartzSabreParticleIndexZ = z;
        });
        return true;
    }
}
