package fun.wraq.core;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.castle.CastleManaArmor;
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
            int tick = Tick.get();
            if (Utils.PlayerManaAttackTime.containsKey(serverPlayer)
                    && tick - Utils.PlayerManaAttackTime.get(serverPlayer) < 5) {
                return;
            }

            CastleManaArmor.NormalAttack(player);
            Utils.PlayerManaAttackTime.put(serverPlayer, tick);

            Item sceptre = player.getMainHandItem().getItem();
            if (!Utils.sceptreTag.containsKey(sceptre)) return;
            if (sceptre instanceof WraqSceptre wraqSceptre) {
                wraqSceptre.shootManaArrow(player, 1, true);
            }
        });
        return true;
    }
}
