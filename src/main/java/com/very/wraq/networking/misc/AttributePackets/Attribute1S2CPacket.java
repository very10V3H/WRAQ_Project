package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute1S2CPacket {
    private final double ManaDamage;
    private final double BreakManaDefence;
    private final double ManaReply;
    private final double CoolDown;

    public Attribute1S2CPacket(double manaDamage, double breakManaDefence, double manaReply, double coolDown) {
        this.ManaDamage = manaDamage;
        this.BreakManaDefence = breakManaDefence;
        this.ManaReply = manaReply;
        this.CoolDown = coolDown;
    }

    public Attribute1S2CPacket(FriendlyByteBuf buf) {
        this.ManaDamage = buf.readDouble();
        this.BreakManaDefence = buf.readDouble();
        this.ManaReply = buf.readDouble();
        this.CoolDown = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.ManaDamage);
        buf.writeDouble(this.BreakManaDefence);
        buf.writeDouble(this.ManaReply);
        buf.writeDouble(this.CoolDown);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.ManaDamageC = this.ManaDamage;
            ClientUtils.BreakManaDefenceC = this.BreakManaDefence;
            ClientUtils.ManaReplyC = this.ManaReply;
            ClientUtils.CoolDownC = this.CoolDown;
        });
        return true;
    }
}
