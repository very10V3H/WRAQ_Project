package fun.wraq.networking.common;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class SingleIntParaS2CPacket {

    protected final int num;

    public SingleIntParaS2CPacket(int num) {
        this.num = num;
    }

    public SingleIntParaS2CPacket(FriendlyByteBuf buf) {
        this.num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.num);
    }

    public abstract void handler();

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(this::handler);
        return true;
    }
}
