package com.Very.very.NetWorking.Packets;

import com.Very.very.Render.Hud.ClientManaData;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemStackNameS2CPacket {
    private final ItemStack itemStack;
    public ItemStackNameS2CPacket(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }
    public ItemStackNameS2CPacket(FriendlyByteBuf buf)
    {
        this.itemStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeItem(this.itemStack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            if (ClientUtils.ItemComponentMap.isEmpty()) ClientUtils.ItemComponentMapInit();
            if (ClientUtils.ItemComponentMap.containsKey(itemStack.getItem())) {
                Compute.ForgingHoverName(itemStack,ClientUtils.ItemComponentMap.get(itemStack.getItem()));
            }
        });
        return true;
    }
}
