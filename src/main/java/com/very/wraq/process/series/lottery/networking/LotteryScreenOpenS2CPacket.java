package com.very.wraq.process.series.lottery.networking;

import com.very.wraq.process.series.lottery.LotteryScreen;
import com.very.wraq.process.series.lottery.LotteryScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LotteryScreenOpenS2CPacket {

    public LotteryScreenOpenS2CPacket() {

    }

    public LotteryScreenOpenS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LotteryScreenUtils.rewards.clear();
            Minecraft.getInstance().setScreen(new LotteryScreen());
        });
        return true;
    }
}
