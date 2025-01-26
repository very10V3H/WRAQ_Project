package fun.wraq.networking.misc.SmartPhonePackets;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.files.MarketProfitInfo;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.market.MarketInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BuyCheckC2SPacket {

    private final String playerName;
    private final ItemStack itemStack;
    private final int price;
    private final int type;

    public BuyCheckC2SPacket(String playerName, ItemStack itemStack, int price, int type) {
        this.playerName = playerName;
        this.itemStack = itemStack;
        this.price = price;
        this.type = type;
    }

    public BuyCheckC2SPacket(FriendlyByteBuf buf) {
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
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName, itemStack, price, type);
            boolean hasThisGood = false;
            boolean hasEnoughCurrency = false;
            MarketItemInfo removeInfo = null;
            for (MarketItemInfo itemInfo : Utils.marketItemInfos) {
                if (itemInfo.playerName.equals(marketItemInfo.playerName)
                        && Compute.getItemStackString(itemStack).equals(Compute.getItemStackString(itemInfo.itemStack))
                        && itemInfo.getItemStackCount() == marketItemInfo.getItemStackCount()
                        && itemInfo.price == marketItemInfo.price
                        && itemInfo.type == marketItemInfo.type) {
                    if (type == 0) {
                        if (serverPlayer.getPersistentData().getDouble("VB") >= price) {
                            Utils.marketProfitInfos.add(new MarketProfitInfo(playerName, price, type));
                            hasThisGood = true;
                            removeInfo = itemInfo;
                        } else {
                            hasEnoughCurrency = true;
                        }
                        break;
                    } else if (type == 1) {
                        if (InventoryOperation.checkPlayerHasItem(serverPlayer, ModItems.GOLDEN_BEANS.get(), price)) {
                            Utils.marketProfitInfos.add(new MarketProfitInfo(playerName, price, type));
                            hasThisGood = true;
                            removeInfo = itemInfo;
                        } else {
                            hasEnoughCurrency = true;
                        }
                    }
                }
            }

            if (hasThisGood) {
                if (type == 0) {
                    Compute.VBExpenseAndMSGSend(serverPlayer, (float) marketItemInfo.price);
                } else if (type == 1) {
                    InventoryOperation.removeItem(serverPlayer, ModItems.GOLDEN_BEANS.get(), price);
                }
                ItemStack itemStack = marketItemInfo.itemStack;
                if (serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.playerName) != null) {
                    ServerPlayer seller = serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.playerName);
                    Compute.sendFormatMSG(seller, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(serverPlayer.getDisplayName()).
                                    append(Component.literal("购买了你上架的 ").withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()));
                }
                LogUtils.getLogger().info("市场 {} 以 {} 购买了 {} 出售的 {} ",
                        serverPlayer.getName().getString(), price + MarketInfo.getType(type),
                        marketItemInfo.playerName, itemStack);
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("购买成功！").withStyle(ChatFormatting.WHITE));
                InventoryOperation.giveItemStackWithMSG(serverPlayer, itemStack);
                Utils.marketItemInfos.remove(removeInfo);
            } else {
                if (hasEnoughCurrency) {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("余额/物品不足").withStyle(ChatFormatting.WHITE));
                } else {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("购买失败！商品可能已经被购买。").withStyle(ChatFormatting.WHITE));
                }
            }
            ModNetworking.sendToClient(new MarketDataS2CPacket(Utils.marketItemInfos), serverPlayer);
        });
        return true;
    }
}
