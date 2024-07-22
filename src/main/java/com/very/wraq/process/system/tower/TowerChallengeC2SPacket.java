package com.very.wraq.process.system.tower;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.sql.SQLException;
import java.util.function.Supplier;

public class TowerChallengeC2SPacket {

    private final int serialNum;

    public TowerChallengeC2SPacket(int serialNum) {
        this.serialNum = serialNum;
    }

    public TowerChallengeC2SPacket(FriendlyByteBuf buf) {
        this.serialNum = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(serialNum);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            try {
                Tower.playerTryToChallenging(player, serialNum);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

}

