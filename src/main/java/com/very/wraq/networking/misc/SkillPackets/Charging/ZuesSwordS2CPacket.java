package com.very.wraq.networking.misc.SkillPackets.Charging;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ZuesSwordS2CPacket {
    private final int ChargeCount;

    public ZuesSwordS2CPacket(int ChargeCount) {
        this.ChargeCount = ChargeCount;
    }

    public ZuesSwordS2CPacket(FriendlyByteBuf buf) {
        this.ChargeCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.ChargeCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ClientUtils.ChargedCountsZeusSword + ChargeCount <= 100)
                ClientUtils.ChargedCountsZeusSword += ChargeCount;
            else ClientUtils.ChargedCountsZeusSword = 100;
        });
        return true;
    }
}
