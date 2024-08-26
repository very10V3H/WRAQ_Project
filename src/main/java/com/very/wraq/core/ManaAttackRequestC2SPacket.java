package com.very.wraq.core;

import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.series.instance.series.castle.CastleManaArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaAttackRequestC2SPacket {

    public ManaAttackRequestC2SPacket() {
    }

    public ManaAttackRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Player player = (Player) serverPlayer;
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerManaAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerManaAttackTime.get(serverPlayer) < 9)
                return;
            if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) == 10)
                CastleManaArmor.NormalAttack(player); //
            Utils.PlayerManaAttackTime.put(serverPlayer, tick);

            Item sceptre = player.getMainHandItem().getItem();
            if (!Utils.sceptreTag.containsKey(sceptre)) return;
            if (sceptre instanceof WraqSceptre wraqSceptre) {
                wraqSceptre.shootManaArrow(player, 1);
            }
        });
        return true;
    }
}
