package fun.wraq.networking.misc.attack;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.series.instance.series.castle.CastleSwiftArmor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowAttackRequestC2SPacket {

    public BowAttackRequestC2SPacket() {
    }

    public BowAttackRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;

            DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                    DelayOperationWithAnimation.Animation.bowAttack, Tick.get() + 8, Tick.get() + 10, serverPlayer
            ) {
                @Override
                public void trig() {
                    CastleSwiftArmor.NormalAttack(serverPlayer);
                    Item bow = serverPlayer.getMainHandItem().getItem();
                    if (!Utils.bowTag.containsKey(bow)) {
                        return;
                    }
                    if (bow instanceof WraqBow wraqBow) {
                        wraqBow.shoot(serverPlayer, 1, true);
                    }
                }
            });
        });
        return true;
    }
}
