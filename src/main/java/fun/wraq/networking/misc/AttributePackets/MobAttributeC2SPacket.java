package fun.wraq.networking.misc.AttributePackets;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AttributePackets.MobAttributeS2CPacket;
import fun.wraq.process.system.element.Element;
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
            // 仅VMD生成的怪物（注册过基础属性的怪物）才允许显示左上角属性
            if (entity instanceof Mob mob && MobSpawn.MobBaseAttributes.isRegisteredMob(mob)) {
                ModNetworking.sendToClient(new MobAttributeS2CPacket(id, MobAttributes.attackDamage(mob),
                        MobAttributes.defence(mob), MobAttributes.manaDefence(mob),
                        MobAttributes.critRate(mob), MobAttributes.defencePenetration(mob), MobAttributes.defencePenetration0(mob),
                        MobAttributes.healthSteal(mob), MobAttributes.critDamage(mob),
                        Element.entityElementUnit.containsKey(entity) ? Element.entityElementUnit.get(entity).type() : "empty",
                        Element.entityElementUnit.containsKey(entity) ? Element.entityElementUnit.get(entity).value() : 0), serverPlayer);
            } else {
                // 非VMD生物（原版怪物/动物/NPC等）或实体已消失：通知客户端清除当前显示的属性
                ModNetworking.sendToClient(new MobAttributeS2CPacket(-1, 0, 0, 0, 0, 0, 0, 0, 0,
                        "empty", 0), serverPlayer);
            }
        });
        return true;
    }
}
