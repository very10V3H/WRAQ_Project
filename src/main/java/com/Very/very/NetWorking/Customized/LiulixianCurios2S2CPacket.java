package com.Very.very.NetWorking.Customized;

import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisa;
import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaSceptre;
import com.Very.very.Customize.Players.liulixian_.LiulixianCurios2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LiulixianCurios2S2CPacket {

    private final double flySpeed;

    public LiulixianCurios2S2CPacket(double flySpeed)
    {
        this.flySpeed = flySpeed;
    }
    public LiulixianCurios2S2CPacket(FriendlyByteBuf buf)
    {
        this.flySpeed = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeDouble(this.flySpeed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            LiulixianCurios2.flySpeed = flySpeed;
        });
        return true;
    }
}
