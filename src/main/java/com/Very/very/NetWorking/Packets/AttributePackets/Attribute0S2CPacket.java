package com.Very.very.NetWorking.Packets.AttributePackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute0S2CPacket {
    private final float AttackDamage;
    private final float BreakDefence;
    private final float CritRate;
    private final float CritDamage;
    public Attribute0S2CPacket(float attackDamage, float breakDefence, float critRate, float critDamage)
    {
        this.AttackDamage = attackDamage;
        this.BreakDefence = breakDefence;
        this.CritRate = critRate;
        this.CritDamage = critDamage;
    }
    public Attribute0S2CPacket(FriendlyByteBuf buf)
    {
        this.AttackDamage = buf.readFloat();
        this.BreakDefence = buf.readFloat();
        this.CritRate = buf.readFloat();
        this.CritDamage = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeFloat(this.AttackDamage);
        buf.writeFloat(this.BreakDefence);
        buf.writeFloat(this.CritRate);
        buf.writeFloat(this.CritDamage);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.AttackDamageC = this.AttackDamage;
            ClientUtils.BreakDefenceC = this.BreakDefence;
            ClientUtils.CritRateC = this.CritRate;
            ClientUtils.CritDamageC = this.CritDamage;
        });
        return true;
    }
}
