package com.very.wraq.networking.misc.SkillPackets.Charging;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowSkill12S2CPacket {
    private final int ChargeCount;

    public BowSkill12S2CPacket(int ChargeCount) {
        this.ChargeCount = ChargeCount;
    }

    public BowSkill12S2CPacket(FriendlyByteBuf buf) {
        this.ChargeCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.ChargeCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ClientUtils.ChargedCountsBowSkill12 + ChargeCount <= 100)
                ClientUtils.ChargedCountsBowSkill12 += ChargeCount;
            else ClientUtils.ChargedCountsBowSkill12 = 100;
        });
        return true;
    }
}
