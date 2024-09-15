package com.very.wraq.render.hud.networking;

import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.networking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AttributeDataC2SPacket {
    private final int id;

    public AttributeDataC2SPacket(int id) {
        this.id = id;
    }

    public AttributeDataC2SPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Entity entity = serverPlayer.level().getEntity(id);
            if (entity instanceof Player player) {
                List<Double> attributes = new ArrayList<>() {{
                    double[] doubles = {
                            PlayerAttributes.attackDamage(player),
                            PlayerAttributes.defencePenetration(player),
                            PlayerAttributes.critRate(player),
                            PlayerAttributes.critDamage(player),
                            PlayerAttributes.manaDamage(player),
                            PlayerAttributes.manaPenetration(player),
                            PlayerAttributes.manaRecover(player),
                            PlayerAttributes.powerReleaseSpeed(player),
                            PlayerAttributes.healthSteal(player),
                            PlayerAttributes.defence(player),
                            PlayerAttributes.manaDefence(player),
                            PlayerAttributes.movementSpeedWithoutBattle(player),
                            PlayerAttributes.defencePenetration0(player),
                            PlayerAttributes.manaPenetration0(player),
                            PlayerAttributes.attackRangeUp(player),
                            PlayerAttributes.expUp(player)
                    };
                    for (double aDouble : doubles) add(aDouble);
                }};
                ModNetworking.sendToClient(new AttributeDataS2CPacket(attributes, player.getId()), serverPlayer);
            }
        });
        return true;
    }
}
