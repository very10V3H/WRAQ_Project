package fun.wraq.process.func.guide.networking;

import fun.wraq.process.func.guide.Guide;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GuideFinishC2SPacket {

    private final String stageTag;

    public GuideFinishC2SPacket(String stageTag) {
        this.stageTag = stageTag;
    }

    public GuideFinishC2SPacket(FriendlyByteBuf buf) {
        this.stageTag = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(stageTag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            Guide.trigV2(player, stageTag);
        });
        return true;
    }
}
