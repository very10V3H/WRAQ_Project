package fun.wraq.networking.misc;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RemoveEffectLastTimeByItemIdS2CPacket {
    private final String itemId;

    public RemoveEffectLastTimeByItemIdS2CPacket(String itemId) {
        this.itemId = itemId;
    }

    public RemoveEffectLastTimeByItemIdS2CPacket(FriendlyByteBuf buf) {
        this.itemId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.itemId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.itemStack
                    .is(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Utils.MOD_ID, itemId))));
        });
        return true;
    }
}
