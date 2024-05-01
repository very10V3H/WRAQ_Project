package com.very.wraq.netWorking.customized;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShangMengLiC2SPacket {
    public ShangMengLiC2SPacket()
    {

    }
    public ShangMengLiC2SPacket(FriendlyByteBuf buf)
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
            if (Utils.ShangMengLiActiveCounts == 100) {
                Utils.ShangMengLiActiveTick = 6;
                Utils.ShangMengLiActiveCounts = 0;
                Compute.EffectLastTimeSend(serverPlayer,ModItems.ShangMengLi_Sceptre1.get().getDefaultInstance(),0,Utils.ShangMengLiActiveCounts);
            }
/*            if (TickCount > Utils.ShangMengLiSkillCoolDown) {
                int CoolDownSeconds = (int) (12 - (12 * PlayerAttributes.PlayerCoolDownDecrease(serverPlayer)));
                Utils.ShangMengLiSkillCoolDown = TickCount + CoolDownSeconds * 20;
                Compute.CoolDownTimeSend(serverPlayer,ModItems.ShangMengLi_Sceptre.get().getDefaultInstance(),CoolDownSeconds * 20);
                Utils.ShangMengLi = serverPlayer;
                Utils.ShangMengLiManaSkillDelay = TickCount + 30;
                List<Mob> mobList = serverPlayer.level().getEntitiesOfClass(Mob.class, AABB.ofSize(serverPlayer.position(),15,15,15));
                mobList.forEach(mob -> {
                    if (mob.distanceTo(serverPlayer) < 6) {
                        Compute.AddManaDefenceDescreaseEffectParticle(mob,60);
                        Utils.ShangMengLiSkillEffectMob.put(mob,TickCount + 60);
                        Compute.Damage.ManaDamageToMonster_RateApDamage(serverPlayer,mob,4);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 2, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.75, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.5, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.25, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                    }
                });
                ParticleProvider.DisperseParticle(serverPlayer.position(), (ServerLevel) serverPlayer.level(), 1, 1, 120, ModParticles.LONG_LAKE.get(), 1);
                ParticleProvider.DisperseParticle(serverPlayer.position(), (ServerLevel) serverPlayer.level(), 1.5, 1, 120, ModParticles.LONG_LAKE.get(), 1);
                Compute.SoundToAll(serverPlayer, SoundEvents.WATER_AMBIENT);
            }*/
        });
        return true;
    }
}
