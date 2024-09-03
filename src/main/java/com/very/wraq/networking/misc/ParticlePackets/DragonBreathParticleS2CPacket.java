package com.very.wraq.networking.misc.ParticlePackets;

import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.struct.PosAndLastTime;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DragonBreathParticleS2CPacket {

    private final double X;
    private final double Y;
    private final double Z;
    private final int levelType;

    public DragonBreathParticleS2CPacket(double x, double y, double z, int levelType) {
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.levelType = levelType;
    }

    public DragonBreathParticleS2CPacket(FriendlyByteBuf buf) {
        this.X = buf.readDouble();
        this.Y = buf.readDouble();
        this.Z = buf.readDouble();
        this.levelType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.X);
        buf.writeDouble(this.Y);
        buf.writeDouble(this.Z);
        buf.writeInt(this.levelType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.EndRune2Pos.add(new PosAndLastTime(new Vec3(X, Y, Z), 100, levelType));
        });
        return true;
    }
}
