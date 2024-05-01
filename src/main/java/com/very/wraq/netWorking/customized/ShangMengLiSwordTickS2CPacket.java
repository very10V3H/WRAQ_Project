package com.very.wraq.netWorking.customized;

import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShangMengLiSwordTickS2CPacket {
    private final int tick;
    public ShangMengLiSwordTickS2CPacket(int tick)
    {
        this.tick = tick;
    }
    public ShangMengLiSwordTickS2CPacket(FriendlyByteBuf buf)
    {
        this.tick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.tick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ShangMengLiSword.ShangMengLiAnimationClientTick = 30;
        });
        return true;
    }
}
