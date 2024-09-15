package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.attribute.MobAttributes;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.system.element.Element;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MobAttributeC2SPacket {
    private final int id;

    public MobAttributeC2SPacket(int id) {
        this.id = id;
    }

    public MobAttributeC2SPacket(FriendlyByteBuf buf) {
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
            if (entity instanceof Mob mob) {
                ModNetworking.sendToClient(new MobAttributeS2CPacket(id, MobAttributes.attackDamage(mob),
                        MobAttributes.defence(mob), MobAttributes.manaDefence(mob),
                        MobAttributes.critRate(mob), MobAttributes.defencePenetration(mob), MobAttributes.defencePenetration0(mob),
                        MobAttributes.healthSteal(mob), MobAttributes.critDamage(mob),
                        Element.entityElementUnit.containsKey(entity) ? Element.entityElementUnit.get(entity).type() : "empty",
                        Element.entityElementUnit.containsKey(entity) ? Element.entityElementUnit.get(entity).value() : 0), serverPlayer);
            }
        });
        return true;
    }
}
