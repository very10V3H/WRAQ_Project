package com.very.wraq.networking.misc;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveEffectLastTimeS2CPacket {
    private final ItemStack itemStack;

    public RemoveEffectLastTimeS2CPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public RemoveEffectLastTimeS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.itemStack.getItem().equals(itemStack.getItem()));
        });
        return true;
    }
}
