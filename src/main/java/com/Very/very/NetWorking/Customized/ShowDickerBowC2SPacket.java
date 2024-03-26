package com.Very.very.NetWorking.Customized;

import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class ShowDickerBowC2SPacket {
    public ShowDickerBowC2SPacket()
    {

    }
    public ShowDickerBowC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork( ()->{
            if (Utils.ShowDickerCount == 10) {
                Utils.ShowDickerCount = 0;
                Utils.ShowDickerArrowCount = 8;
                Compute.EffectLastTimeSend(serverPlayer,ModItems.ShowDickerBow.get().getDefaultInstance(),8888,0,true);
            }
        });
        return true;
    }
}
