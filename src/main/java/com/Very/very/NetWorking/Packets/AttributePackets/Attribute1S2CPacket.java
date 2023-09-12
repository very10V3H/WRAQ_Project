package com.Very.very.NetWorking.Packets.AttributePackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute1S2CPacket {
    private final float ManaDamage;
    private final float BreakManaDefence;
    private final float ManaReply;
    private final float CoolDown;
    public Attribute1S2CPacket(float manaDamage, float breakManaDefence, float manaReply, float coolDown)
    {
        this.ManaDamage = manaDamage;
        this.BreakManaDefence = breakManaDefence;
        this.ManaReply = manaReply;
        this.CoolDown = coolDown;
    }
    public Attribute1S2CPacket(FriendlyByteBuf buf)
    {
        this.ManaDamage = buf.readFloat();
        this.BreakManaDefence = buf.readFloat();
        this.ManaReply = buf.readFloat();
        this.CoolDown = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeFloat(this.ManaDamage);
        buf.writeFloat(this.BreakManaDefence);
        buf.writeFloat(this.ManaReply);
        buf.writeFloat(this.CoolDown);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.ManaDamageC = this.ManaDamage;
            ClientUtils.BreakManaDefenceC = this.BreakManaDefence;
            ClientUtils.ManaReplyC = this.ManaReply;
            ClientUtils.CoolDownC = this.CoolDown;
        });
        return true;
    }
}
