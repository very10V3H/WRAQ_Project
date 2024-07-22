package com.very.wraq.networking.dailyMission;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DailyMissionContentS2CPacket {

    private final ItemStack itemStack;
    private final int Count;

    public DailyMissionContentS2CPacket(ItemStack itemStack, int Count) {
        this.itemStack = itemStack;
        this.Count = Count;
    }

    public DailyMissionContentS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
        this.Count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.Count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.DailyMissionItem = this.itemStack;
            ClientUtils.DailyMissionItemCount = this.Count;
        });
        return true;
    }
}
