package com.very.wraq.networking.misc.AttackPackets;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.core.AttackEvent;
import com.very.wraq.series.instance.series.castle.CastleAttackArmor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AttackC2SPacket {
    private final int count;

    public AttackC2SPacket(int count) {
        this.count = count;
    }

    public AttackC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerPlayer serverPlayer = context.getSender();
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerAttackTime.get(serverPlayer) < 9)
                return;
            Utils.PlayerAttackTime.put(serverPlayer, tick);
            CastleAttackArmor.NormalAttack(player); //
            switch (count) {
                case 0 -> {
                    AttackEvent.module(player, 0.8);
                }
                case 1 -> {
                    AttackEvent.module(player, 1.2);
                }
                case 2 -> {
                    AttackEvent.module(player, 1.5);
                }
            }
        });
        return true;
    }
}
