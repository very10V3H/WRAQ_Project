package fun.wraq.networking.misc.AttributePackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute0S2CPacket {
    private final double AttackDamage;
    private final double BreakDefence;
    private final double CritRate;
    private final double CritDamage;

    public Attribute0S2CPacket(double attackDamage, double BreakDefence, double critRate, double critDamage) {
        this.AttackDamage = attackDamage;
        this.BreakDefence = BreakDefence;
        this.CritRate = critRate;
        this.CritDamage = critDamage;
    }

    public Attribute0S2CPacket(FriendlyByteBuf buf) {
        this.AttackDamage = buf.readDouble();
        this.BreakDefence = buf.readDouble();
        this.CritRate = buf.readDouble();
        this.CritDamage = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.AttackDamage);
        buf.writeDouble(this.BreakDefence);
        buf.writeDouble(this.CritRate);
        buf.writeDouble(this.CritDamage);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.AttackDamageC = this.AttackDamage;
            ClientUtils.BreakDefenceC = this.BreakDefence;
            ClientUtils.CritRateC = this.CritRate;
            ClientUtils.CritDamageC = this.CritDamage;
        });
        return true;
    }
}
