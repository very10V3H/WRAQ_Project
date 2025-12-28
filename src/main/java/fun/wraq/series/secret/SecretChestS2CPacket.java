package fun.wraq.series.secret;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SecretChestS2CPacket {

    private final BlockPos blockPos;
    private final int dimension;
    private final int tier;

    public SecretChestS2CPacket(BlockPos blockPos, int dimension, int tier) {
        this.blockPos = blockPos;
        this.dimension = dimension;
        this.tier = tier;
    }

    public SecretChestS2CPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.dimension = buf.readInt();
        this.tier = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeInt(dimension);
        buf.writeInt(tier);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SecretChest.clientCenterPos = blockPos;
            SecretChest.clientDimension = dimension;
            SecretChest.clientTier = tier;
        });
        return true;
    }
}
