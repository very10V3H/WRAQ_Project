package com.very.wraq.networking.misc.SkillPackets.Charging;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSkill12S2CPacket {
    private final int ChargeCount;

    public ManaSkill12S2CPacket(int ChargeCount) {
        this.ChargeCount = ChargeCount;
    }

    public ManaSkill12S2CPacket(FriendlyByteBuf buf) {
        this.ChargeCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.ChargeCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ClientUtils.ChargedCountsManaSkill12 + ChargeCount <= 100)
                ClientUtils.ChargedCountsManaSkill12 += ChargeCount;
            else ClientUtils.ChargedCountsManaSkill12 = 100;
        });
        return true;
    }
}
