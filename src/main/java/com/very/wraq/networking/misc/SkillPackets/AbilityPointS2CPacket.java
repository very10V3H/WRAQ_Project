package com.very.wraq.networking.misc.SkillPackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityPointS2CPacket {
    private final int AbilityPoint_Total;
    private final int AbilityPoint_Used;

    public AbilityPointS2CPacket(int AbilityPoint_Total, int AbilityPoint_Used) {
        this.AbilityPoint_Total = AbilityPoint_Total;
        this.AbilityPoint_Used = AbilityPoint_Used;
    }

    public AbilityPointS2CPacket(FriendlyByteBuf buf) {
        this.AbilityPoint_Total = buf.readInt();
        this.AbilityPoint_Used = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.AbilityPoint_Total);
        buf.writeInt(this.AbilityPoint_Used);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.PlayerAbilityPoint_Total = AbilityPoint_Total;
            ClientUtils.PlayerAbilityPoint_Used = AbilityPoint_Used;
        });
        return true;
    }
}
