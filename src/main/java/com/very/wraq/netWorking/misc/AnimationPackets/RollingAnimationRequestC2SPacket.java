package com.very.wraq.netWorking.misc.AnimationPackets;

import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class RollingAnimationRequestC2SPacket {

    private final int count;

    public RollingAnimationRequestC2SPacket(int count) {
        this.count = count;
    }
    public RollingAnimationRequestC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            CompoundTag data = serverPlayer.getPersistentData();
            if (serverPlayer.onGround() && data.getDouble(StringUtils.Swift) >= 100.0/3) {
                List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();

                playerList.forEach(serverPlayer1 -> {
                    ModNetworking.sendToClient(new RollingAnimationS2CPacket(serverPlayer.getId(),count),serverPlayer1);
                });

                ModNetworking.sendToClient(new RollingS2CPacket(PlayerAttributes.PlayerExtraSwiftness(serverPlayer) / 10.0),serverPlayer);
                Compute.PlayerSwiftChange(serverPlayer,-100.0/3);
                Utils.RollingTickMap.put(serverPlayer,serverPlayer.getServer().getTickCount() + 10);

                Compute.SoundToAll(serverPlayer, ModSounds.Rolling.get());

                BowCurios0.Active(serverPlayer);
                QiFuCurios1.Passive3(serverPlayer);
            }
        });
        return true;
    }
}
