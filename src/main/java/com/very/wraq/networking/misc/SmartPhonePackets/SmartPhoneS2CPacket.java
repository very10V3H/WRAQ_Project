package com.very.wraq.networking.misc.SmartPhonePackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmartPhoneS2CPacket {
    private final double VBNUM;
    private final double Security0Num;
    private final double Security1Num;
    private final double Security2Num;
    private final double Security3Num;
    private final double SecurityGet;

    public SmartPhoneS2CPacket(double VBNUM, double Security0Num, double Security1Num, double Security2Num, double Security3Num, double SecurityGet) {
        this.VBNUM = VBNUM;
        this.Security0Num = Security0Num;
        this.Security1Num = Security1Num;
        this.Security2Num = Security2Num;
        this.Security3Num = Security3Num;
        this.SecurityGet = SecurityGet;
    }

    public SmartPhoneS2CPacket(FriendlyByteBuf buf) {
        this.VBNUM = buf.readDouble();
        this.Security0Num = buf.readDouble();
        this.Security1Num = buf.readDouble();
        this.Security2Num = buf.readDouble();
        this.Security3Num = buf.readDouble();
        this.SecurityGet = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.VBNUM);
        buf.writeDouble(this.Security0Num);
        buf.writeDouble(this.Security1Num);
        buf.writeDouble(this.Security2Num);
        buf.writeDouble(this.Security3Num);
        buf.writeDouble(this.SecurityGet);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.VBNUM = this.VBNUM;
            ClientUtils.Security0Num = this.Security0Num;
            ClientUtils.Security1Num = this.Security1Num;
            ClientUtils.Security2Num = this.Security2Num;
            ClientUtils.Security3Num = this.Security3Num;
            ClientUtils.SecurityGet = this.SecurityGet;
        });
        return true;
    }
}
