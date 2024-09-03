package com.very.wraq.networking.misc.SkillPackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillPointS2CPacket {
    private final int SkillPoint_Total;
    private final int SkillPoint_Used;

    public SkillPointS2CPacket(int SkillPoint_Total, int SkillPoint_Used) {
        this.SkillPoint_Total = SkillPoint_Total;
        this.SkillPoint_Used = SkillPoint_Used;
    }

    public SkillPointS2CPacket(FriendlyByteBuf buf) {
        this.SkillPoint_Total = buf.readInt();
        this.SkillPoint_Used = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.SkillPoint_Total);
        buf.writeInt(this.SkillPoint_Used);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.PlayerSkillPoint_Total = SkillPoint_Total;
            ClientUtils.PlayerSkillPoint_Used = SkillPoint_Used;
        });
        return true;
    }
}
