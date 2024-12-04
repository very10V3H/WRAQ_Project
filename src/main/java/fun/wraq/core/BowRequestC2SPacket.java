package fun.wraq.core;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.bow.BowCurios1;
import fun.wraq.series.instance.series.castle.CastleSwiftArmor;
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
            int tick = Tick.get();
            if (Utils.PlayerArrowAttackTime.containsKey(serverPlayer)
                    && tick - Utils.PlayerArrowAttackTime.get(serverPlayer) < 5)
                return;
            CastleSwiftArmor.NormalAttack(serverPlayer);
            Utils.PlayerArrowAttackTime.put(serverPlayer, tick);
            BowCurios1.playerShoot(serverPlayer);

            Item bow = serverPlayer.getMainHandItem().getItem();
            if (!Utils.bowTag.containsKey(bow)) return;
            if (bow instanceof WraqBow wraqBow) wraqBow.shoot(serverPlayer, 1, true);
        });
        return true;
    }
}
