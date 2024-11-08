package fun.wraq.render.hud.networking;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerIsInBattleS2CPacket {

    private final boolean isInBattle;
    public PlayerIsInBattleS2CPacket(boolean isInBattle) {
        this.isInBattle = isInBattle;
    }

    public PlayerIsInBattleS2CPacket(FriendlyByteBuf buf) {
        this.isInBattle = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isInBattle);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.isInBattle = isInBattle;
        });
        return true;
    }
}
