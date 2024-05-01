package com.very.wraq.netWorking.reputation;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class ReputationBuyRequestC2SPacket {

    private final int index;
    public ReputationBuyRequestC2SPacket(int index)
    {
        this.index = index;
    }
    public ReputationBuyRequestC2SPacket(FriendlyByteBuf buf)
    {
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            if (Utils.ReputationStoreItemList.isEmpty()) Utils.setReputationStoreItemList();
            if (Utils.ReputationStorePrice.isEmpty()) Utils.setReputationStorePrice();
            if (index >= Utils.ReputationStoreItemList.size()) {
                Compute.FormatMSGSend(serverPlayer,Component.literal("声望商店").withStyle(ChatFormatting.YELLOW),
                        Component.literal("暂无商品。").withStyle(ChatFormatting.WHITE));
                return;
            }
            Item item = Utils.ReputationStoreItemList.get(index);
            int Price = Utils.ReputationStorePrice.get(item);
            if (Compute.playerReputationAddOrCost(serverPlayer,-Price)) {
                try {
                    Compute.ItemStackGive(serverPlayer,item.getDefaultInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return true;
    }
}
