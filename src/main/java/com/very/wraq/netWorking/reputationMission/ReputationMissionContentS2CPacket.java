package com.very.wraq.netWorking.reputationMission;

import com.very.wraq.valueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReputationMissionContentS2CPacket {

    private final ItemStack itemStack;
    private final int Count;
    public ReputationMissionContentS2CPacket(ItemStack itemStack, int Count)
    {
        this.itemStack = itemStack;
        this.Count = Count;
    }
    public ReputationMissionContentS2CPacket(FriendlyByteBuf buf)
    {
        this.itemStack = buf.readItem();
        this.Count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.Count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.ReputationMissionItem = this.itemStack;
            ClientUtils.ReputationMissionItemCount = this.Count;
        });
        return true;
    }
}
