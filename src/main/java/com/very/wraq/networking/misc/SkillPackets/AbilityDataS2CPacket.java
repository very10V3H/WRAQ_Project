package com.very.wraq.networking.misc.SkillPackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityDataS2CPacket {
    private final int Power;
    private final int Intelligent;
    private final int Flexibility;
    private final int Lucky;
    private final int Vitaliy;

    public AbilityDataS2CPacket(int Power, int Intelligent, int Flexibility, int Lucky, int Vitality) {
        this.Power = Power;
        this.Intelligent = Intelligent;
        this.Flexibility = Flexibility;
        this.Lucky = Lucky;
        this.Vitaliy = Vitality;
    }

    public AbilityDataS2CPacket(FriendlyByteBuf buf) {
        this.Power = buf.readInt();
        this.Intelligent = buf.readInt();
        this.Flexibility = buf.readInt();
        this.Lucky = buf.readInt();
        this.Vitaliy = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Power);
        buf.writeInt(this.Intelligent);
        buf.writeInt(this.Flexibility);
        buf.writeInt(this.Lucky);
        buf.writeInt(this.Vitaliy);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.AbilityChangeCache.Power = Power;
            ClientUtils.AbilityChangeCache.Intelligent = Intelligent;
            ClientUtils.AbilityChangeCache.Flexibility = Flexibility;
            ClientUtils.AbilityChangeCache.Lucky = Lucky;
            ClientUtils.AbilityChangeCache.Vitality = Vitaliy;
            ClientUtils.AbilityCache.Power = Power;
            ClientUtils.AbilityCache.Intelligent = Intelligent;
            ClientUtils.AbilityCache.Flexibility = Flexibility;
            ClientUtils.AbilityCache.Lucky = Lucky;
            ClientUtils.AbilityCache.Vitality = Vitaliy;
        });
        return true;
    }
}
