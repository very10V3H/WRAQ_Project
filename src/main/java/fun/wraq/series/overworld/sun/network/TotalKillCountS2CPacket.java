package fun.wraq.series.overworld.sun.network;

import fun.wraq.networking.common.SingleIntParaS2CPacket;
import fun.wraq.series.overworld.sun.DevilPowerCurio;
import net.minecraft.network.FriendlyByteBuf;

public class TotalKillCountS2CPacket extends SingleIntParaS2CPacket {

    public TotalKillCountS2CPacket(int num) {
        super(num);
    }

    public TotalKillCountS2CPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void handler() {
        DevilPowerCurio.clientTotalKillCount = this.num;
    }
}
