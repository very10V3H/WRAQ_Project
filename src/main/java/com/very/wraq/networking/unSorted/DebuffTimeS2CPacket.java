package com.very.wraq.networking.unSorted;

import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.Struct.EffectTimeLast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DebuffTimeS2CPacket {
    private final int LastTime;
    private final ItemStack itemStack;
    private final int Level;
    private final boolean NoTime;

    public DebuffTimeS2CPacket(ItemStack itemStack, int counts) {
        this.itemStack = itemStack;
        this.LastTime = counts;
        this.Level = 0;
        this.NoTime = false;
    }

    public DebuffTimeS2CPacket(ItemStack itemStack, int counts, int level) {
        this.itemStack = itemStack;
        this.LastTime = counts;
        this.Level = level;
        this.NoTime = false;
    }

    public DebuffTimeS2CPacket(ItemStack itemStack, int counts, int level, boolean noTime) {
        this.itemStack = itemStack;
        this.LastTime = counts;
        this.Level = level;
        this.NoTime = noTime;
    }

    public DebuffTimeS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
        this.LastTime = buf.readInt();
        this.Level = buf.readInt();
        this.NoTime = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.LastTime);
        buf.writeInt(this.Level);
        buf.writeBoolean(this.NoTime);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.debuffTimes.removeIf(effectTimeLast -> effectTimeLast.itemStack.is(this.itemStack.getItem()));
            if (this.NoTime)
                ClientUtils.debuffTimes.add(new EffectTimeLast(itemStack, LastTime, LastTime, Level, true));
            else ClientUtils.debuffTimes.add(new EffectTimeLast(itemStack, LastTime, LastTime, Level));
        });
        return true;
    }
}
