package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute2S2CPacket {
    private final double HealSteal;
    private final double Defence;
    private final double ManaDefence;
    private final double Speed;

    public Attribute2S2CPacket(double HealSteal, double Defence, double manaDefence, double speed) {
        this.HealSteal = HealSteal;
        this.Defence = Defence;
        this.ManaDefence = manaDefence;
        this.Speed = speed;
    }

    public Attribute2S2CPacket(FriendlyByteBuf buf) {
        this.HealSteal = buf.readDouble();
        this.Defence = buf.readDouble();
        this.ManaDefence = buf.readDouble();
        this.Speed = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.HealSteal);
        buf.writeDouble(this.Defence);
        buf.writeDouble(this.ManaDefence);
        buf.writeDouble(this.Speed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.HealStealC = this.HealSteal;
            ClientUtils.DefenceC = this.Defence;
            ClientUtils.ManaDefenceC = this.ManaDefence;
            ClientUtils.SpeedC = this.Speed;
        });
        return true;
    }
}
