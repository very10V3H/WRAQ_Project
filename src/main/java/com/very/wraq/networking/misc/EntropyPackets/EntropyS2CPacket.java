package com.very.wraq.networking.misc.EntropyPackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EntropyS2CPacket {
    private final int Forest;
    private final int Volcano;
    private final int Lake;
    private final int Sky;
    private final int Snow;

    public EntropyS2CPacket(int Forest, int Volcano, int Lake, int Sky, int Snow) {
        this.Forest = Forest;
        this.Volcano = Volcano;
        this.Lake = Lake;
        this.Sky = Sky;
        this.Snow = Snow;
    }

    public EntropyS2CPacket(FriendlyByteBuf buf) {
        this.Forest = buf.readInt();
        this.Volcano = buf.readInt();
        this.Lake = buf.readInt();
        this.Sky = buf.readInt();
        this.Snow = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Forest);
        buf.writeInt(this.Volcano);
        buf.writeInt(this.Lake);
        buf.writeInt(this.Sky);
        buf.writeInt(this.Snow);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.Entropy.Forest = this.Forest;
            ClientUtils.Entropy.Volcano = this.Volcano;
            ClientUtils.Entropy.Lake = this.Lake;
            ClientUtils.Entropy.Sky = this.Sky;
            ClientUtils.Entropy.Snow = this.Snow;
        });
        return true;
    }
}
