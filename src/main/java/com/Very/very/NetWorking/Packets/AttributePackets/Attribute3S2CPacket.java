package com.Very.very.NetWorking.Packets.AttributePackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute3S2CPacket {
    private final float BreakDefence0;
    private final float BreakManaDefence0;
    private final float AttackRangeUp;
    private final float ExpUp;
    public Attribute3S2CPacket(float BreakDefence0, float BreakManaDefence0, float AttackRangeUp, float ExpUp)
    {
        this.BreakDefence0 = BreakDefence0;
        this.BreakManaDefence0 = BreakManaDefence0;
        this.AttackRangeUp = AttackRangeUp;
        this.ExpUp = ExpUp;
    }
    public Attribute3S2CPacket(FriendlyByteBuf buf)
    {
        this.BreakDefence0 = buf.readFloat();
        this.BreakManaDefence0 = buf.readFloat();
        this.AttackRangeUp = buf.readFloat();
        this.ExpUp = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeFloat(this.BreakDefence0);
        buf.writeFloat(this.BreakManaDefence0);
        buf.writeFloat(this.AttackRangeUp);
        buf.writeFloat(this.ExpUp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.BreakDefence0C = this.BreakDefence0;
            ClientUtils.BreakManaDefence0C = this.BreakManaDefence0;
            ClientUtils.AttackRangeUpC = this.AttackRangeUp;
            ClientUtils.ExpUpC = this.ExpUp;
        });
        return true;
    }
}
