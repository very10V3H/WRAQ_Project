package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.bow.BowCurios0;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.render.hud.SwiftData;
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
                    if (!serverPlayer.isCreative()) SwiftData.changePlayerSwift(serverPlayer, -100.0 / 3);
                    Utils.rollingTickMap.put(serverPlayer.getName().getString(), Tick.get() + 10);

                    MySound.soundToNearPlayer(serverPlayer, ModSounds.Rolling.get());

                    BowCurios0.Active(serverPlayer);
                    Guide.trig(serverPlayer, 1);
                }
            }
        });
        return true;
    }
}
