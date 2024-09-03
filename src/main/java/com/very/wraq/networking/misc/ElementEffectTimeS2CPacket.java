package com.very.wraq.networking.misc;

import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.struct.EffectTimeLast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ElementEffectTimeS2CPacket {
    private final int lastTime;
    private final ItemStack itemStack;
    private final int level;
    private final boolean noTime;

    public ElementEffectTimeS2CPacket(ItemStack itemStack, int counts) {
        this.itemStack = itemStack;
        this.lastTime = counts;
        this.level = 0;
        this.noTime = false;
    }

    public ElementEffectTimeS2CPacket(ItemStack itemStack, int counts, int level) {
        this.itemStack = itemStack;
        this.lastTime = counts;
        this.level = level;
        this.noTime = false;
    }

    public ElementEffectTimeS2CPacket(ItemStack itemStack, int counts, int level, boolean noTime) {
        this.itemStack = itemStack;
        this.lastTime = counts;
        this.level = level;
        this.noTime = noTime;
    }

    public ElementEffectTimeS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
        this.lastTime = buf.readInt();
        this.level = buf.readInt();
        this.noTime = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.lastTime);
        buf.writeInt(this.level);
        buf.writeBoolean(this.noTime);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.elementEffects = new EffectTimeLast(itemStack, lastTime, lastTime, level, true);
        });
        return true;
    }
}
