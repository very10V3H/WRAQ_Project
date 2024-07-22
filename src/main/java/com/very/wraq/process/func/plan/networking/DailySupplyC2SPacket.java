package com.very.wraq.process.func.plan.networking;

import com.very.wraq.process.func.plan.DailySupply;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.function.Supplier;

public class DailySupplyC2SPacket {

    public DailySupplyC2SPacket() {

    }

    public DailySupplyC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = supplier.get().getSender();
            try {
                DailySupply.tryToReward(serverPlayer);
            } catch (SQLException | ParseException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
