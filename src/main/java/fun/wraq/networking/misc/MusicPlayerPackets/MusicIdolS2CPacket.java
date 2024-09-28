package fun.wraq.networking.misc.MusicPlayerPackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MusicIdolS2CPacket {
    private final boolean flag;

    public MusicIdolS2CPacket(boolean flag) {
        this.flag = flag;
    }

    public MusicIdolS2CPacket(FriendlyByteBuf buf) {
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.MusicPlayerIdol = flag;
        });
        return true;
    }
}
