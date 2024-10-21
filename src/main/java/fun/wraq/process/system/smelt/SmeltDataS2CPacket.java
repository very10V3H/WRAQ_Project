package fun.wraq.process.system.smelt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmeltDataS2CPacket {

    private final CompoundTag data;

    public SmeltDataS2CPacket(CompoundTag data) {
        this.data = data;
    }

    public SmeltDataS2CPacket(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(this.data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Smelt.clientData = this.data;
        });
        return true;
    }
}
