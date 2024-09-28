package fun.wraq.networking.misc.ToolTipPackets;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.EffectTimeLast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CoolDownTimeS2CPacket {
    private final int LastTime;
    private final ItemStack itemStack;
    private final int Level;

    public CoolDownTimeS2CPacket(ItemStack itemStack, int counts) {
        this.itemStack = itemStack;
        this.LastTime = counts;
        this.Level = 0;
    }

    public CoolDownTimeS2CPacket(ItemStack itemStack, int counts, int level) {
        this.itemStack = itemStack;
        this.LastTime = counts;
        this.Level = level;
    }

    public CoolDownTimeS2CPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
        this.LastTime = buf.readInt();
        this.Level = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
        buf.writeInt(this.LastTime);
        buf.writeInt(this.Level);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.coolDownTimes.removeIf(effectTimeLast -> effectTimeLast.itemStack.is(this.itemStack.getItem()));
            ClientUtils.coolDownTimes.add(new EffectTimeLast(itemStack, LastTime, LastTime, Level));
        });
        return true;
    }
}
