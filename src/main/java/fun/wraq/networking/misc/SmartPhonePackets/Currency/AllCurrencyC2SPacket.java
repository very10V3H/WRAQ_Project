package fun.wraq.networking.misc.SmartPhonePackets.Currency;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AllCurrencyC2SPacket {

    private final boolean sendMSG;

    public AllCurrencyC2SPacket(boolean sendMSG) {
        this.sendMSG = sendMSG;
    }

    public AllCurrencyC2SPacket(FriendlyByteBuf buf) {
        this.sendMSG = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(sendMSG);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            int copper = collect(player, ModItems.COPPER_COIN.get(), 1);
            int silver = collect(player, ModItems.SILVER_COIN.get(), 12);
            int gold = collect(player, ModItems.GOLD_COIN.get(), 144);

            if (sendMSG) {
                Compute.sendFormatMSG(player, Component.literal("货币").withStyle(ChatFormatting.GOLD),
                        Component.literal("本次存入了").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                        append(ModItems.COPPER_COIN.get().getDefaultInstance().getDisplayName()).
                        append(Component.literal(" " + copper + "枚" + " (" + copper + ")").withStyle(ChatFormatting.WHITE)));
                player.sendSystemMessage(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                        append(ModItems.SILVER_COIN.get().getDefaultInstance().getDisplayName()).
                        append(Component.literal(" " + silver + "枚" + " (" + silver * 12 + ")").withStyle(ChatFormatting.WHITE)));
                player.sendSystemMessage(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                        append(ModItems.GOLD_COIN.get().getDefaultInstance().getDisplayName()).
                        append(Component.literal(" " + gold + "枚" + " (" + gold * 144 + ")").withStyle(ChatFormatting.WHITE)));
            }

        });
        return true;
    }

    public static int collect(Player player, Item type, int rate) {
        Inventory inventory = player.getInventory();
        int coinCount = InventoryOperation.itemStackCount(inventory, type);
        if (coinCount > 0) {
            InventoryOperation.itemStackRemoveIgnoreVB(inventory, type, coinCount);
            Compute.VBIncomeAndMSGSend(player, coinCount * rate);
        }
        return coinCount;
    }
}
