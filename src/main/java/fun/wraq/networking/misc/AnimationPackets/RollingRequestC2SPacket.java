package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.bow.normal.BowCurios0;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.render.hud.SwiftData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RollingRequestC2SPacket {


    public RollingRequestC2SPacket() {}

    public RollingRequestC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (SpecialEffectOnPlayer.inVertigo(serverPlayer)) {
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);
            } else {
                CompoundTag data = serverPlayer.getPersistentData();
                if (serverPlayer.onGround() && data.getDouble(StringUtils.Swift) >= 100.0 / 3) {
                    DelayOperationWithAnimation.remove(serverPlayer);

                    DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                            DelayOperationWithAnimation.Animation.rolling, Tick.get() + 8, serverPlayer
                    ) {
                        @Override
                        public void trig() {}
                    });

                    ModNetworking.sendToClient(new RollingS2CPacket(PlayerAttributes.extraSwiftness(serverPlayer) / 10.0), serverPlayer);
                    if (!serverPlayer.isCreative()) SwiftData.changePlayerSwift(serverPlayer, -100.0 / 3);
                    Utils.rollingTickMap.put(serverPlayer.getName().getString(), Tick.get() + 10);
                    MySound.soundToNearPlayer(serverPlayer, ModSounds.Rolling.get());

                    BowCurios0.onReleaseRolling(serverPlayer);
                    Guide.trigV2(serverPlayer, Guide.StageV2.ROLLING);
                }
            }
        });
        return true;
    }
}
