package com.very.wraq.core;

import com.very.wraq.customized.uniform.bow.BowCurios1;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowRequestC2SPacket {

    public BowRequestC2SPacket() {
    }

    public BowRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerArrowAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerArrowAttackTime.get(serverPlayer) < 9)
                return;
            CastleSwiftArmor.NormalAttack(serverPlayer);
            Utils.PlayerArrowAttackTime.put(serverPlayer, tick);
            BowCurios1.playerShoot(serverPlayer);

            Item bow = serverPlayer.getMainHandItem().getItem();
            if (!Utils.bowTag.containsKey(bow)) return;
            if (bow instanceof WraqBow wraqBow) wraqBow.shoot(serverPlayer);
        });
        return true;
    }
}
