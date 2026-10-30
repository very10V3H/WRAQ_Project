package fun.wraq.render.gui.villagerTrade.networking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.render.gui.villagerTrade.TradeList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-30
 * 将单个 NPC 的运行时交易内容与配方同步到客户端。
 */
public class TradeDataSyncS2CPacket {

    private final String npc;
    private final List<ItemStack> content;
    private final List<List<ItemStack>> recipes;

    public TradeDataSyncS2CPacket(String npc, List<ItemStack> content, List<List<ItemStack>> recipes) {
        this.npc = npc;
        this.content = copy(content);
        this.recipes = recipes.stream().map(TradeDataSyncS2CPacket::copy).toList();
    }

    public TradeDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.npc = buf.readUtf();
        this.content = buf.readList(FriendlyByteBuf::readItem);
        this.recipes = buf.readList(b -> b.readList(FriendlyByteBuf::readItem));
    }

    private static List<ItemStack> copy(List<ItemStack> list) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack stack : list) {
            result.add(stack.copy());
        }
        return result;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(npc);
        buf.writeCollection(content, FriendlyByteBuf::writeItem);
        buf.writeCollection(recipes, (b, list) -> b.writeCollection(list, FriendlyByteBuf::writeItem));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> TradeList.applySync(npc, content, recipes));
        return true;
    }

    public static void sendToAll(MinecraftServer server, String npc) {
        List<ItemStack> content = TradeList.getContentCopy(npc);
        List<List<ItemStack>> recipes = new ArrayList<>();
        for (ItemStack stack : content) {
            recipes.add(TradeList.getRecipeCopy(stack));
        }
        TradeDataSyncS2CPacket packet = new TradeDataSyncS2CPacket(npc, content, recipes);
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ModNetworking.sendToClient(packet, player);
        }
    }
}
