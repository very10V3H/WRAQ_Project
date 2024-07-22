package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute3S2CPacket {
    private final double BreakDefence0;
    private final double BreakManaDefence0;
    private final double AttackRangeUp;
    private final double ExpUp;

    public Attribute3S2CPacket(double BreakDefence0, double BreakManaDefence0, double AttackRangeUp, double ExpUp) {
        this.BreakDefence0 = BreakDefence0;
        this.BreakManaDefence0 = BreakManaDefence0;
        this.AttackRangeUp = AttackRangeUp;
        this.ExpUp = ExpUp;
    }

    public Attribute3S2CPacket(FriendlyByteBuf buf) {
        this.BreakDefence0 = buf.readDouble();
        this.BreakManaDefence0 = buf.readDouble();
        this.AttackRangeUp = buf.readDouble();
        this.ExpUp = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.BreakDefence0);
        buf.writeDouble(this.BreakManaDefence0);
        buf.writeDouble(this.AttackRangeUp);
        buf.writeDouble(this.ExpUp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.BreakDefence0C = this.BreakDefence0;
            ClientUtils.BreakManaDefence0C = this.BreakManaDefence0;
            ClientUtils.AttackRangeUpC = this.AttackRangeUp;
            ClientUtils.ExpUpC = this.ExpUp;
        });
        return true;
    }
}
