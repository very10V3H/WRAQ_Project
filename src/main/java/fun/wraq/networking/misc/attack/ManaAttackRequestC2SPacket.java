package fun.wraq.networking.misc.attack;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.series.instance.series.castle.CastleManaArmor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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
            if (serverPlayer == null) return;

            DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                    DelayOperationWithAnimation.Animation.manaAttack, Tick.get() + 8, Tick.get() + 10, serverPlayer
            ) {
                @Override
                public void trig() {
                    CastleManaArmor.NormalAttack(serverPlayer);
                    Item sceptre = serverPlayer.getMainHandItem().getItem();
                    if (!Utils.sceptreTag.containsKey(sceptre)) return;
                    if (sceptre instanceof WraqSceptre wraqSceptre) {
                        wraqSceptre.shootManaArrow(serverPlayer, 1, true);
                    }
                }
            });
        });
        return true;
    }
}
