package com.very.wraq.netWorking.misc.AttributePackets;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.Compute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MobAttributeC2SPacket {
    private final int id;
    public MobAttributeC2SPacket(int id) {
        this.id = id;
    }
    public MobAttributeC2SPacket(FriendlyByteBuf buf)
    {
        this.id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()-> {
            ServerPlayer serverPlayer = context.getSender();
            Entity entity = serverPlayer.level().getEntity(id);
            if (entity instanceof Mob mob) {
                ModNetworking.sendToClient(new MobAttributeS2CPacket(id, mob.getAttribute(Attributes.ATTACK_DAMAGE).getValue(),
                        Compute.MonsterDefence(mob), Compute.MonsterManaDefence(mob),
                        0,0,0,0,0,Element.EntityElementUnit.containsKey(id) ? Element.EntityElementUnit.get(id).type() : "empty",
                        Element.EntityElementUnit.containsKey(id) ? Element.EntityElementUnit.get(id).value() : 0), serverPlayer);

            }
        });
        return true;
    }
}
