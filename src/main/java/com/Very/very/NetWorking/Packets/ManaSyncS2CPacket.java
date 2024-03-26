package com.Very.very.NetWorking.Packets;

import com.Very.very.Render.Hud.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSyncS2CPacket {
    private final int MaxMana;
    private final int CurrentMana;
    public ManaSyncS2CPacket(int MaxMana, int CurrentMana)
    {
        this.MaxMana = MaxMana;
        this.CurrentMana = CurrentMana;
    }
    public ManaSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.MaxMana = buf.readInt();
        this.CurrentMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.MaxMana);
        buf.writeInt(this.CurrentMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientManaData.setMaxMana(MaxMana);
            ClientManaData.setCurrentMana(CurrentMana);
        });
        return true;
    }
}
