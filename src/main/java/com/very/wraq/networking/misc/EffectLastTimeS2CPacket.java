package com.very.wraq.networking.misc;

import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.struct.EffectTimeLast;
import com.very.wraq.process.system.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EffectLastTimeS2CPacket {
    private final int lastTime;
    private final ItemStack itemStack;
    private final int level;
    private final boolean forever;

    public EffectLastTimeS2CPacket(ItemStack itemStack, int counts) {
        this.itemStack = itemStack;
        this.lastTime = counts;
        this.level = 0;
        this.forever = false;
    }

    public EffectLastTimeS2CPacket(ItemStack itemStack, int counts, int level) {
        this.itemStack = itemStack;
        this.lastTime = counts;
        this.level = level;
        this.forever = false;
    }

    public EffectLastTimeS2CPacket(ItemStack itemStack, int counts, int level, boolean forever) {
        this.itemStack = itemStack;
        this.lastTime = counts;
        this.level = level;
        this.forever = forever;
    }

    public EffectLastTimeS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
        this.lastTime = buf.readInt();
        this.level = buf.readInt();
        this.forever = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.lastTime);
        buf.writeInt(this.level);
        buf.writeBoolean(this.forever);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Element.elementList.isEmpty()) Element.setElementList();
            if (Element.elementList.contains(itemStack.getItem())) {
                ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> Element.elementList.contains(effectTimeLast.itemStack.getItem()));
            }

            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.itemStack.is(this.itemStack.getItem()));

            if (this.forever) ClientUtils.effectTimeLasts.add(new EffectTimeLast(itemStack, lastTime, lastTime, level, true));
            else ClientUtils.effectTimeLasts.add(new EffectTimeLast(itemStack, lastTime, lastTime, level));
        });
        return true;
    }
}
