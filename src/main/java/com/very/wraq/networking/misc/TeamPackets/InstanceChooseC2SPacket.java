package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.common.util.Utils;
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

public class InstanceChooseC2SPacket {
    private final int type;
    private final int difficulty;

    public InstanceChooseC2SPacket(int type, int difficulty) {
        this.type = type;
        this.difficulty = difficulty;
    }

    public InstanceChooseC2SPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
        this.difficulty = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.type);
        buf.writeInt(this.difficulty);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            PlayerTeam playerTeam = Utils.playerTeamMap.get(serverPlayer);
            if (Utils.instanceList.get(type).isChallenge())
                serverPlayer.sendSystemMessage(Component.literal("有队伍正在挑战该副本。"));
            else {
                List<Player> NoPsPlayers = new ArrayList<>();
                List<Player> XpLevelPlayers = new ArrayList<>();
                List<Player> playerListGetByName = new ArrayList<>();
                List<Player> PlayerDeadTime = new ArrayList<>();
                List<Player> PlayerInstanceProgress = new ArrayList<>();
                int TickCount = serverPlayer.getServer().getTickCount();
                playerTeam.getPlayerList().forEach(player -> {
                    playerListGetByName.add(serverPlayer.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                playerListGetByName.forEach(player -> {
                    if (Compute.getPlayerPsValue(player) < Utils.instanceList.get(this.type).getPsCost()[difficulty])
                        NoPsPlayers.add(player);
                    if (player.experienceLevel < Utils.instanceList.get(this.type).getLevelRequire())
                        XpLevelPlayers.add(player);
                    if (Utils.PlayerDeadTimeMap.containsKey(player.getName().getString()) && Utils.PlayerDeadTimeMap.get(player.getName().getString()) > TickCount)
                        PlayerDeadTime.add(player);
                    if (!Compute.PlayerCanChallengeThisInstance(player, this.type) && this.type < 10)
                        PlayerInstanceProgress.add(player);
                });
                if (PlayerDeadTime.size() == 0 && NoPsPlayers.size() == 0 && XpLevelPlayers.size() == 0 && PlayerInstanceProgress.size() == 0
                        && playerListGetByName.size() >= Utils.instanceList.get(type).getLeastPeopleNum() && !Compute.thisTeamIsChallenging(playerTeam)) {
                    Utils.instanceList.get(this.type).setChallenge(true);
                    Utils.instanceList.get(this.type).setDifficulty(this.difficulty);

                    ServerLevel serverLevel = serverPlayer.getServer().getLevel(Utils.instanceList.get(this.type).getLevel());
                    Vec3 teleportPos = Utils.instanceList.get(this.type).getTeleportPosition();

                    playerListGetByName.forEach(player -> {
                        ServerPlayer serverPlayer1 = (ServerPlayer) player;
                        Compute.addOrCostPlayerPsValue(player, -Utils.instanceList.get(this.type).getPsCost()[difficulty]);
                        serverPlayer1.teleportTo(serverLevel, teleportPos.x, teleportPos.y, teleportPos.z, 0, 0);
                    });

                    Utils.instanceList.get(this.type).setCurrentChallengePlayerTeam(playerTeam);
                    Utils.ChallengingPlayerTeam.add(playerTeam);

                    MutableComponent difficulty = Component.literal("");
                    switch (this.difficulty) {
                        case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                        case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                        case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                    }

                    Utils.LastTimeInstance.put(serverPlayer, type);
                    Utils.LastTimeDifficulty.put(serverPlayer, this.difficulty);

                    Compute.formatBroad(serverLevel, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 正在挑战  ")).
                                    append(Utils.instanceList.get(this.type).getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));
                    Utils.instanceList.get(this.type).setTick(100);
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
