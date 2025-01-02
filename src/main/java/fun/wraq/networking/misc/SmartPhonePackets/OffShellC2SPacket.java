package fun.wraq.networking.misc.SmartPhonePackets;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.networking.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OffShellC2SPacket {

    private final String playerName;
    private final ItemStack itemStack;
    private final int price;
    private final int type;

    public OffShellC2SPacket(String playerName, ItemStack itemStack, int price, int type) {
        this.playerName = playerName;
        this.itemStack = itemStack;
        this.price = price;
        this.type = type;
    }

    public OffShellC2SPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
        this.itemStack = buf.readItem();
        this.price = buf.readInt();
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
        buf.writeItem(this.itemStack);
        buf.writeInt(this.price);
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (!playerName.equals(serverPlayer.getName().getString()) && !serverPlayer.isCreative()) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("你不能下架不属于你的物品").withStyle(ChatFormatting.WHITE));
            }
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName, itemStack, price, type);
            MarketItemInfo removeItemInfo = null;
            for (MarketItemInfo itemInfo : Utils.marketItemInfos) {
                if (itemInfo.equals(marketItemInfo)) removeItemInfo = itemInfo;
            }
            if (removeItemInfo != null) {
                serverPlayer.addItem(removeItemInfo.itemStack);
                Utils.marketItemInfos.remove(removeItemInfo);
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("你成功下架了一件物品").withStyle(ChatFormatting.WHITE));
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("物品可能已被购买").withStyle(ChatFormatting.WHITE));
            }
            ModNetworking.sendToClient(new MarketDataS2CPacket(Utils.marketItemInfos), serverPlayer);
        });
        return true;
    }
}
