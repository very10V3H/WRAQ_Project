package fun.wraq.process.system.cooking.network;

import fun.wraq.process.system.cooking.CookingPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CookingDataS2CPacket {

    private final int cookingExp;

    public CookingDataS2CPacket(int cookingExp) {
        this.cookingExp = cookingExp;
    }

    public CookingDataS2CPacket(FriendlyByteBuf buf) {
        this.cookingExp = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(cookingExp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            CookingPlayerData.clientCookingExp = cookingExp;
        });
        return true;
    }
}
