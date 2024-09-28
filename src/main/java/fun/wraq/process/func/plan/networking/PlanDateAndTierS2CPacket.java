package fun.wraq.process.func.plan.networking;

import fun.wraq.process.func.plan.PlanPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlanDateAndTierS2CPacket {

    private final int date;
    private final int tier;

    public PlanDateAndTierS2CPacket(int date, int tier) {
        this.date = date;
        this.tier = tier;
    }

    public PlanDateAndTierS2CPacket(FriendlyByteBuf buf) {
        this.date = buf.readInt();
        this.tier = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(date);
        buf.writeInt(tier);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlanPlayer.clientPlanLeftDate = date;
            PlanPlayer.clientPlanTier = tier;
        });
        return true;
    }
}
