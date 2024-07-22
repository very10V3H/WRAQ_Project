package com.very.wraq.networking.misc.Limit;

import com.very.wraq.networking.unSorted.PlayerCallBack;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LimitC2SPacket {
    public LimitC2SPacket() {

    }

    public LimitC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            for (PlayerCallBack PerCallBack : Utils.playerCallBackList) {

                if (PerCallBack.getPlayer().getName().equals(player.getName())) {
                    PerCallBack.setTime(3);
                }

            }
        });
        return true;
    }
}
