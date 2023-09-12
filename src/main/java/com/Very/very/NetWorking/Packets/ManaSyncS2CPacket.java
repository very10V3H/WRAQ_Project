package com.Very.very.NetWorking.Packets;

import com.Very.very.Render.Hud.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSyncS2CPacket {
    private final int mana;
    private final int manaValue;
    public ManaSyncS2CPacket(int mana,int manaValue)
    {
        this.mana = mana;
        this.manaValue = manaValue;
    }
    public ManaSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.mana = buf.readInt();
        this.manaValue = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.mana);
        buf.writeInt(this.manaValue);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientManaData.set(mana);
            ClientManaData.setValue(manaValue);
        });
        return true;
    }
}
