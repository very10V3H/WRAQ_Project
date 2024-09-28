package fun.wraq.networking.misc.Limit;

import fun.wraq.common.util.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveBlockLimitC2SPacket {
    public RemoveBlockLimitC2SPacket() {

    }

    public RemoveBlockLimitC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Utils.playerCallBackList.removeIf(playerCallBack -> playerCallBack.getPlayer().equals(player));
        });
        return true;
    }
}
