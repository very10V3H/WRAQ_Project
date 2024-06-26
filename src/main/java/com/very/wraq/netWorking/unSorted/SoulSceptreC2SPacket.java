package com.very.wraq.netWorking.unSorted;

import com.very.wraq.projectiles.mana.Meteorite;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SoulSceptreC2SPacket {
    public SoulSceptreC2SPacket()
    {

    }
    public SoulSceptreC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        int TickCount = serverPlayer.getServer().getTickCount();
        context.enqueueWork( ()->{
            if ((!Utils.PlayerSoulSceptreCoolDown.containsKey(serverPlayer) ||
                    (Utils.PlayerSoulSceptreCoolDown.containsKey(serverPlayer) && Utils.PlayerSoulSceptreCoolDown.get(serverPlayer) < TickCount))
                    && Compute.PlayerManaCost(serverPlayer,40)) {
                Meteorite meteorite = new Meteorite(ModEntityType.METEORITE.get(), serverPlayer, serverPlayer.level(),false);
                Vec3 vec3 = serverPlayer.pick(5,0,false).getLocation();
                meteorite.setSilent(true);
                meteorite.setDeltaMovement(0,- 0.5, 0);
                meteorite.moveTo(vec3.add(0,10,0));
                serverPlayer.level().addFreshEntity(meteorite);
                Utils.PlayerSoulSceptreCoolDown.put((Player) serverPlayer,
                       TickCount + (int) (160 * (1 - PlayerAttributes.PlayerCoolDownDecrease(serverPlayer))));
                Compute.CoolDownTimeSend(serverPlayer,ModItems.SoulSceptre.get().getDefaultInstance(),
                        (int) (160 * (1 - PlayerAttributes.PlayerCoolDownDecrease(serverPlayer))));
            }
        });
        return true;
    }
}
