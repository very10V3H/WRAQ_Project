package fun.wraq.networking.misc.SmartPhonePackets;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketProfitInfo;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class RequestGetC2SPacket {
    public RequestGetC2SPacket() {

    }

    public RequestGetC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            List<MarketProfitInfo> marketProfitInfos = Utils.marketProfitInfos
                    .stream().filter(info -> info.playerName.equals(player.getName().getString()))
                    .toList();
            marketProfitInfos.forEach(info -> {
                switch (info.type) {
                    case 0 -> {
                        Compute.VBIncomeAndMSGSend(player, info.profit);
                    }
                    case 1 -> {
                        InventoryOperation
                                .giveItemStackWithMSG(player, new ItemStack(ModItems.GOLDEN_BEANS.get(), info.profit));
                    }
                }
            });
            Utils.marketProfitInfos.removeAll(marketProfitInfos);
        });
        return true;
    }
}
