package fun.wraq.networking.misc;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.EffectTimeLast;
import fun.wraq.process.system.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class EffectLastTimeByItemIdS2CPacket {
    private final String itemId;
    private final int lastTime;
    private final int level;
    private final boolean forever;

    public EffectLastTimeByItemIdS2CPacket(String itemId, int counts) {
        this.itemId = itemId;
        this.lastTime = counts;
        this.level = 0;
        this.forever = false;
    }

    public EffectLastTimeByItemIdS2CPacket(String itemId, int counts, int level) {
        this.itemId = itemId;
        this.lastTime = counts;
        this.level = level;
        this.forever = false;
    }

    public EffectLastTimeByItemIdS2CPacket(String itemId, int counts, int level, boolean forever) {
        this.itemId = itemId;
        this.lastTime = counts;
        this.level = level;
        this.forever = forever;
    }

    public EffectLastTimeByItemIdS2CPacket(FriendlyByteBuf buf) {
        this.itemId = buf.readUtf();
        this.lastTime = buf.readInt();
        this.level = buf.readInt();
        this.forever = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.itemId);
        buf.writeInt(this.lastTime);
        buf.writeInt(this.level);
        buf.writeBoolean(this.forever);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ItemStack itemStack = ForgeRegistries.ITEMS
                    .getValue(new ResourceLocation(Utils.MOD_ID, itemId)).getDefaultInstance();
            if (Element.elementItemList.isEmpty()) {
                Element.setElementList();
            }
            if (Element.elementItemList.contains(itemStack.getItem())) {
                ClientUtils.effectTimeLasts.removeIf(effectTimeLast
                        -> Element.elementItemList.contains(effectTimeLast.itemStack.getItem()));
            }

            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.itemStack.is(itemStack.getItem()));

            if (this.forever) {
                ClientUtils.effectTimeLasts.add(new EffectTimeLast(itemStack, lastTime, lastTime, level, true));
            }
            else {
                ClientUtils.effectTimeLasts.add(new EffectTimeLast(itemStack, lastTime, lastTime, level));
            }
        });
        return true;
    }
}
