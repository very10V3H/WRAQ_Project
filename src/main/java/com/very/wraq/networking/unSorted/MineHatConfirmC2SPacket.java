package com.very.wraq.networking.unSorted;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MineHatConfirmC2SPacket {
    public MineHatConfirmC2SPacket() {

    }

    public MineHatConfirmC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400));
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 2));
        });
        return true;
    }
}
