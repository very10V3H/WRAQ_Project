package fun.wraq.networking.dailyMission;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.Calendar;
import java.util.function.Supplier;

public class DailyMissionFinishedRequestC2SPacket {
    public DailyMissionFinishedRequestC2SPacket() {

    }

    public DailyMissionFinishedRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            CompoundTag data = serverPlayer.getPersistentData();
            if (!Utils.playerDailyMissionContent.containsKey(serverPlayer.getName().getString())) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(ChatFormatting.GREEN),
                        Component.literal("当前没有任务可以提交。").withStyle(ChatFormatting.WHITE));
                return;
            }
            ItemStack itemStack = Utils.playerDailyMissionContent.get(serverPlayer.getName().getString());
            int Count = Utils.playerDailyMissionContentNum.get(serverPlayer.getName().getString());
            Inventory inventory = serverPlayer.getInventory();
            if (InventoryOperation.checkPlayerHasItem(inventory, itemStack.getItem(), Count)) {
                InventoryOperation.removeItem(inventory, itemStack.getItem(), Count);
                data.putString(StringUtils.LastDailyMissionFinishedTime, Compute.CalendarToString(Calendar.getInstance()));
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你完成了每日任务！").withStyle(ChatFormatting.WHITE));
                Compute.givePercentExpToPlayer(serverPlayer, 0.5, 0, serverPlayer.experienceLevel);
                ItemStack gemPiece = ModItems.GEM_PIECE.get().getDefaultInstance();
                gemPiece.setCount(serverPlayer.experienceLevel);
                InventoryOperation.giveItemStack(serverPlayer, gemPiece);
                RankData.onPlayerFinishDailyReputationMission(serverPlayer);
                Compute.playerReputationAddOrCost(serverPlayer, serverPlayer.experienceLevel);
                ModNetworking.sendToClient(new DailyMissionFinishedTimeS2CPacket(data.getString(StringUtils.LastDailyMissionFinishedTime)), serverPlayer);
                Utils.playerDailyMissionContent.remove(serverPlayer.getName().getString());
                ModNetworking.sendToClient(new DailyMissionContentS2CPacket(Items.AIR.getDefaultInstance(), 0), serverPlayer);
                ModNetworking.sendToClient(new SoundsS2CPacket(3), serverPlayer);
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("暂未达成任务要求。").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }
}
