package com.Very.very.NetWorking.Packets.AttributePackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Attribute2S2CPacket {
    private final float HealSteal;
    private final float Defence;
    private final float ManaDefence;
    private final float Speed;
    public Attribute2S2CPacket(float healSteal, float defence, float manaDefence, float speed)
    {
        this.HealSteal = healSteal;
        this.Defence = defence;
        this.ManaDefence = manaDefence;
        this.Speed = speed;
    }
    public Attribute2S2CPacket(FriendlyByteBuf buf)
    {
        this.HealSteal = buf.readFloat();
        this.Defence = buf.readFloat();
        this.ManaDefence = buf.readFloat();
        this.Speed = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeFloat(this.HealSteal);
        buf.writeFloat(this.Defence);
        buf.writeFloat(this.ManaDefence);
        buf.writeFloat(this.Speed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.HealStealC = this.HealSteal;
            ClientUtils.DefenceC = this.Defence;
            ClientUtils.ManaDefenceC = this.ManaDefence;
            ClientUtils.SpeedC = this.Speed;
        });
        return true;
    }
}
