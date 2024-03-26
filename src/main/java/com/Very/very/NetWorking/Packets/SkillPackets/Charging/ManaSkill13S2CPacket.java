package com.Very.very.NetWorking.Packets.SkillPackets.Charging;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSkill13S2CPacket {
    private final int ChargeCount;
    public ManaSkill13S2CPacket(int ChargeCount)
    {
        this.ChargeCount = ChargeCount;
    }
    public ManaSkill13S2CPacket(FriendlyByteBuf buf)
    {
        this.ChargeCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.ChargeCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            if (ClientUtils.ChargedCountsManaSkill13 + ChargeCount <= 100) ClientUtils.ChargedCountsManaSkill13 += ChargeCount;
            else ClientUtils.ChargedCountsManaSkill13 = 100;
        });
        return true;
    }
}
