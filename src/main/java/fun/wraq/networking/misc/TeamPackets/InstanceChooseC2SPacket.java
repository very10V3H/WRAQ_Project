package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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

    public InstanceChooseC2SPacket(int type) {
        this.type = type;
    }

    public InstanceChooseC2SPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            if (serverPlayer == null) return;
            PlayerTeam playerTeam = Utils.playerTeamMap.get(serverPlayer);
            if (NewTeamInstanceHandler.getInstances().size() <= this.type) return;
            NewTeamInstance instance = NewTeamInstanceHandler.getInstances().get(this.type);
            if (instance.inChallenging)
                serverPlayer.sendSystemMessage(Component.literal("有队伍正在挑战该副本。"));
            else {
                List<Player> playerListGetByName = new ArrayList<>();
                List<Player> xpUnReachPlayers = new ArrayList<>();
                playerTeam.getPlayerList().forEach(player -> {
                    playerListGetByName.add(serverPlayer
                            .getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                playerListGetByName.forEach(player -> {
                    if (player.experienceLevel < instance.levelRequire)
                        xpUnReachPlayers.add(player);
                });
                if (xpUnReachPlayers.isEmpty()
                        && playerListGetByName.size() >= instance.minPlayerNum
                        && !Compute.thisTeamIsChallenging(playerTeam)) {
                    instance.startByTeam(playerTeam);
                    Utils.ChallengingPlayerTeam.add(playerTeam);

                    ServerLevel serverLevel = serverPlayer.getServer().getLevel(instance.dimension);
                    Vec3 teleportPos = instance.prepareCenterPos;

                    playerListGetByName
                            .stream().map(player -> (ServerPlayer) player)
                            .forEach(player -> {
                                player.teleportTo(serverLevel, teleportPos.x, teleportPos.y, teleportPos.z,
                                instance.rot.x, instance.rot.y);
                    });

                    Compute.formatBroad(serverLevel, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 正在挑战  ")).
                                    append(instance.description));
                } else {
                    if (!xpUnReachPlayers.isEmpty()) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍中有成员等级低于副本等级需求。他们分别是："));
                        xpUnReachPlayers.forEach(player -> {
                            serverPlayer.sendSystemMessage(player.getDisplayName());
                        });
                    }
                    if (playerListGetByName.size() < instance.minPlayerNum) {
                        serverPlayer.sendSystemMessage(Component.literal("队伍人数不足。"));
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
