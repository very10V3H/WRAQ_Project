package fun.wraq.networking.reputationMission;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.function.Supplier;

public class ReputationMissionFinishedRequestC2SPacket {
    public ReputationMissionFinishedRequestC2SPacket() {

    }

    public ReputationMissionFinishedRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (!Utils.playerReputationMissionContent.containsKey(serverPlayer.getName().getString())) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(ChatFormatting.GREEN),
                        Component.literal("当前没有任务可以提交。").withStyle(ChatFormatting.WHITE));
                return;
            }

            ItemStack itemStack = Utils.playerReputationMissionContent.get(serverPlayer.getName().getString());
            int Count = Utils.playerReputationMissionContentNum.get(serverPlayer.getName().getString());
            Inventory inventory = serverPlayer.getInventory();
            if (InventoryOperation.checkPlayerHasItem(inventory, itemStack.getItem(), Count)) {
                InventoryOperation.removeItem(inventory, itemStack.getItem(), Count);
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你完成了悬赏任务！").withStyle(ChatFormatting.WHITE));
                Calendar currentTime = Calendar.getInstance();
                Calendar missionStartTime;
                try {
                    missionStartTime = Compute.StringToCalendar(Utils.playerReputationMissionStartTime.get(serverPlayer.getName().getString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                long timeDelta = currentTime.getTimeInMillis() - missionStartTime.getTimeInMillis();
                long minuteDelta = timeDelta / (1000 * 60);
                if (minuteDelta >= 75) minuteDelta = 75;
                int tier = 5 - (int) (minuteDelta / 15);
                Compute.givePercentExpToPlayer(serverPlayer, 0.02 * tier, 0, serverPlayer.experienceLevel);
                Compute.playerReputationAddOrCost(serverPlayer, tier * (serverPlayer.experienceLevel / 20));
                if (serverPlayer.experienceLevel == Compute.levelUpperLimit) Compute.playerReputationAddOrCost(serverPlayer, tier);

                Utils.playerReputationMissionContent.remove(serverPlayer.getName().getString());
                ModNetworking.sendToClient(new ReputationMissionContentS2CPacket(Items.AIR.getDefaultInstance(), 0), serverPlayer);
                Utils.playerReputationMissionStartTime.remove(serverPlayer.getName().getString());

                String[] Level = {
                        "普通", "优秀", "精良", "史诗", "传说", "神话"
                };

                ChatFormatting[] chatFormattings = {
                        ChatFormatting.GRAY,
                        ChatFormatting.GREEN,
                        ChatFormatting.AQUA,
                        ChatFormatting.LIGHT_PURPLE,
                        ChatFormatting.GOLD,
                        ChatFormatting.RED,
                };

                Compute.formatBroad(serverPlayer.level(), Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(serverPlayer.getDisplayName()).
                                append(Component.literal("在 ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(minuteDelta + "min").withStyle(chatFormattings[tier])).
                                append(Component.literal(" 内完成了悬赏任务，获得了: ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(Level[tier]).withStyle(chatFormattings[tier])).
                                append(Component.literal(" 评级！").withStyle(ChatFormatting.WHITE)));

                Utils.playerReputationMissionAllowRequestTime.remove(serverPlayer.getName().getString());
                if (Utils.playerReputationMissionPunishLevel.containsKey(serverPlayer.getName().getString()) && Utils.playerReputationMissionPunishLevel.get(serverPlayer.getName().getString()) > 0) {
                    Utils.playerReputationMissionPunishLevel.put(serverPlayer.getName().getString(), Utils.playerReputationMissionPunishLevel.get(serverPlayer.getName().getString()) - 1);
                }

                MySound.soundToNearPlayer(serverPlayer, SoundEvents.PLAYER_LEVELUP);
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("暂未达成任务要求。").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }
}
