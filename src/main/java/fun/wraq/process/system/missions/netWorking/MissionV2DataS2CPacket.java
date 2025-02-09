package fun.wraq.process.system.missions.netWorking;

import fun.wraq.process.system.missions.mission2.MissionV2;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionV2DataS2CPacket {

    private final CompoundTag data;

    public MissionV2DataS2CPacket(CompoundTag data) {
        this.data = data;
    }

    public MissionV2DataS2CPacket(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MissionV2.clientMissionData = data;
        });
        return true;
    }

}

