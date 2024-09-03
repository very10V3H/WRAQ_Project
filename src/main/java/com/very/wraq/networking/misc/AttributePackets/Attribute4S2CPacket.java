package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute4S2CPacket {
    private final double healthRecover;
    private final double swiftness;
    private final double manaHealthSteal;
    private final double dodgeRate;

    public Attribute4S2CPacket(double healthRecover, double swiftness, double manaHealthSteal, double dodgeRate) {
        this.healthRecover = healthRecover;
        this.swiftness = swiftness;
        this.manaHealthSteal = manaHealthSteal;
        this.dodgeRate = dodgeRate;
    }

    public Attribute4S2CPacket(FriendlyByteBuf buf) {
        this.healthRecover = buf.readDouble();
        this.swiftness = buf.readDouble();
        this.manaHealthSteal = buf.readDouble();
        this.dodgeRate = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.healthRecover);
        buf.writeDouble(this.swiftness);
        buf.writeDouble(this.manaHealthSteal);
        buf.writeDouble(this.dodgeRate);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.clientHealthRecover = this.healthRecover;
            ClientUtils.clientSwiftness = this.swiftness;
            ClientUtils.clientManaHealthSteal = this.manaHealthSteal;
            ClientUtils.clientDodgeRate = this.dodgeRate;
        });
        return true;
    }
}
