package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class QuickChooseC2SPacket {

    public QuickChooseC2SPacket() {

    }

    public QuickChooseC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            PlayerTeam playerTeam = Utils.playerTeamMap.get(serverPlayer);
            int type;
            int difficultyTier;
            if (Utils.LastTimeInstance.containsKey(serverPlayer)) {
                type = Utils.LastTimeInstance.get(serverPlayer);
                difficultyTier = Utils.LastTimeDifficulty.get(serverPlayer);
            } else {
                difficultyTier = -1;
                type = -1;
                serverPlayer.sendSystemMessage(Component.literal("请先选择一个副本后再进行快捷挑战。"));
                return;
            }

            if (Utils.instanceList.get(type).isChallenge())
                serverPlayer.sendSystemMessage(Component.literal("有队伍正在挑战该副本。"));
            else {
                List<Player> NoPsPlayers = new ArrayList<>();
                List<Player> XpLevelPlayers = new ArrayList<>();
                List<Player> playerListGetByName = new ArrayList<>();
                List<Player> PlayerDeadTime = new ArrayList<>();
                List<Player> PlayerInstanceProgress = new ArrayList<>();
                int TickCount = Tick.get();
                playerTeam.getPlayerList().forEach(player -> {
                    playerListGetByName.add(serverPlayer.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                playerListGetByName.forEach(player -> {
                    if (Compute.getPlayerPsValue(player) < Utils.instanceList.get(type).getPsCost()[difficultyTier])
                        NoPsPlayers.add(player);
                    if (player.experienceLevel < Utils.instanceList.get(type).getLevelRequire())
                        XpLevelPlayers.add(player);
                    if (Utils.PlayerDeadTimeMap.containsKey(player.getName().getString()) && Utils.PlayerDeadTimeMap.get(player.getName().getString()) > TickCount)
                        PlayerDeadTime.add(player);
                    if (!Compute.PlayerCanChallengeThisInstance(player, type)) PlayerInstanceProgress.add(player);
                });
                if (PlayerDeadTime.size() == 0 && NoPsPlayers.size() == 0 && XpLevelPlayers.size() == 0 && PlayerInstanceProgress.size() == 0
                        && playerListGetByName.size() >= Utils.instanceList.get(type).getLeastPeopleNum() && !Compute.thisTeamIsChallenging(playerTeam)) {
                    Utils.instanceList.get(type).setChallenge(true);
                    Utils.instanceList.get(type).setDifficulty(difficultyTier);

                    ServerLevel serverLevel = serverPlayer.getServer().getLevel(Utils.instanceList.get(type).getLevel());
                    Vec3 teleportPos = Utils.instanceList.get(type).getTeleportPosition();

                    playerListGetByName.forEach(player -> {
                        ServerPlayer serverPlayer1 = (ServerPlayer) player;
                        Compute.addOrCostPlayerPsValue(player, -Utils.instanceList.get(type).getPsCost()[difficultyTier]);
                        serverPlayer1.teleportTo(serverLevel, teleportPos.x, teleportPos.y, teleportPos.z, 0, 0);
                    });

                    Utils.instanceList.get(type).setCurrentChallengePlayerTeam(playerTeam);
                    Utils.ChallengingPlayerTeam.add(playerTeam);

                    MutableComponent difficulty = Component.literal("");
                    switch (difficultyTier) {
                        case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                        case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                        case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                    }

                    Utils.LastTimeInstance.put(serverPlayer, type);
                    Utils.LastTimeDifficulty.put(serverPlayer, difficultyTier);

                    Compute.formatBroad(serverLevel, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 正在挑战  ")).
                                    append(Utils.instanceList.get(type).getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));
                    Utils.instanceList.get(type).setTick(100);
                } else {
                    if (NoPsPlayers.size() != 0) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍中有成员体力不足。他们分别是："));
                        NoPsPlayers.forEach(player -> {
                            serverPlayer.sendSystemMessage(player.getDisplayName());
                        });
                    }
                    if (XpLevelPlayers.size() != 0) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍中有成员等级低于副本等级需求。他们分别是："));
                        XpLevelPlayers.forEach(player -> {
                            serverPlayer.sendSystemMessage(player.getDisplayName());
                        });
                    }
                    if (playerListGetByName.size() < Utils.instanceList.get(type).getLeastPeopleNum()) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍人数不足。"));
                    }
                    if (PlayerDeadTime.size() != 0) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍中有成员最近曾死亡过。他们分别是："));
                        PlayerDeadTime.forEach(player -> {
                            serverPlayer.sendSystemMessage(player.getDisplayName());
                        });
                    }
                    if (PlayerInstanceProgress.size() != 0) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍中有成员暂未取得副本挑战资格。他们分别是："));
                        PlayerInstanceProgress.forEach(player -> {
                            serverPlayer.sendSystemMessage(player.getDisplayName());
                        });
                    }
                    if (Compute.thisTeamIsChallenging(playerTeam)) {
                        serverPlayer.sendSystemMessage(Component.literal("请等待副本挑战完成后再选择副本。"));
                    }
                }
            }
        });
        return true;
    }
}
