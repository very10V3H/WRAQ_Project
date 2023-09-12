package com.Very.very.NetWorking.Packets;

import com.Very.very.Render.Hud.ClientShieldData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShieldSyncS2CPacket {
    private final int shield;
    private final int shieldValue;
    public ShieldSyncS2CPacket(int shield, int shieldValue)
    {
        this.shield = shield;
        this.shieldValue = shieldValue;
    }
    public ShieldSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.shield = buf.readInt();
        this.shieldValue = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.shield);
        buf.writeInt(this.shieldValue);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientShieldData.Set(this.shield);
            ClientShieldData.setValue(this.shieldValue);
        });
        return true;
    }
}
