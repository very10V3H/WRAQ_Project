package com.very.wraq.networking.misc.AnimationPackets;

import com.very.wraq.common.MySound;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.attributeValues.SpecialEffectOnPlayer;
import com.very.wraq.common.registry.ModSounds;
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
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (SpecialEffectOnPlayer.inVertigo(serverPlayer)) {
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);
            } else {
                CompoundTag data = serverPlayer.getPersistentData();
                if (serverPlayer.onGround() && data.getDouble(StringUtils.Swift) >= 100.0 / 3) {
                    List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();

                    playerList.forEach(serverPlayer1 -> {
                        ModNetworking.sendToClient(new RollingAnimationS2CPacket(serverPlayer.getId(), count), serverPlayer1);
                    });

                    ModNetworking.sendToClient(new RollingS2CPacket(PlayerAttributes.extraSwiftness(serverPlayer) / 10.0), serverPlayer);
                    if (!serverPlayer.isCreative()) Compute.PlayerSwiftChange(serverPlayer, -100.0 / 3);
                    Utils.rollingTickMap.put(serverPlayer.getName().getString(), serverPlayer.getServer().getTickCount() + 10);

                    MySound.SoundToAll(serverPlayer, ModSounds.Rolling.get());

                    BowCurios0.Active(serverPlayer);
                    Guide.trig(serverPlayer, 1);
                }
            }
        });
        return true;
    }
}
