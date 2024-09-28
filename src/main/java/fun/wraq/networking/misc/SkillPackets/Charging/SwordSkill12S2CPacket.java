package fun.wraq.networking.misc.SkillPackets.Charging;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwordSkill12S2CPacket {
    private final int ChargeCount;

    public SwordSkill12S2CPacket(int ChargeCount) {
        this.ChargeCount = ChargeCount;
    }

    public SwordSkill12S2CPacket(FriendlyByteBuf buf) {
        this.ChargeCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.ChargeCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ClientUtils.ChargedCountsSwordSkill12 + ChargeCount <= 100)
                ClientUtils.ChargedCountsSwordSkill12 += ChargeCount;
            else ClientUtils.ChargedCountsSwordSkill12 = 100;
        });
        return true;
    }
}
