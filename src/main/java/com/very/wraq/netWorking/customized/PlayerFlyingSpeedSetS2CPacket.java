package com.very.wraq.netWorking.customized;

import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerFlyingSpeedSetS2CPacket {

    private final double flySpeed;

    public PlayerFlyingSpeedSetS2CPacket(double flySpeed)
    {
        this.flySpeed = flySpeed;
    }
    public PlayerFlyingSpeedSetS2CPacket(FriendlyByteBuf buf)
    {
        this.flySpeed = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeDouble(this.flySpeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()-> {
            LiulixianCurios2.flySpeed = flySpeed;
        });
        return true;
    }
}
