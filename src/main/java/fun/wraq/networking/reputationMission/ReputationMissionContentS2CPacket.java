package fun.wraq.networking.reputationMission;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReputationMissionContentS2CPacket {

    private final ItemStack itemStack;
    private final int count;

    public ReputationMissionContentS2CPacket(ItemStack itemStack, int Count) {
        this.itemStack = itemStack;
        this.count = Count;
    }

    public ReputationMissionContentS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.reputationMissionItem = this.itemStack;
            ClientUtils.reputationMissionItemCount = this.count;
        });
        return true;
    }
}
