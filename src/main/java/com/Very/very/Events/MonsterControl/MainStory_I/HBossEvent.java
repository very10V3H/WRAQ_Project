package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.EntityInit;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Entity.HEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HBossEvent {
    @SubscribeEvent
    public static void HBoss(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (!level.isClientSide) {
            int TickCount = level.getServer().getTickCount();
            if (level.equals(level.getServer().getLevel(Level.OVERWORLD)) && Utils.HBossDelay >= 0 && TickCount % 20 == 0 && event.phase.equals(TickEvent.Phase.START)) {
                Vec3 endVec3 = new Vec3(-164+0.5,115,1417+0.5);
                Vec3 startVec3[] = {
                        new Vec3(-174+0.5,117+0.5,1427+0.5),
                        new Vec3(-178+0.5,118+0.5,1417+0.5),
                        new Vec3(-174+0.5,117+0.5,1407+0.5),
                        new Vec3(-164+0.5,118+0.5,1403+0.5),
                        new Vec3(-154+0.5,117+0.5,1407+0.5),
                        new Vec3(-150+0.5,118+0.5,1417+0.5),
                        new Vec3(-154+0.5,117+0.5,1427+0.5),
                        new Vec3(-164+0.5,118+0.5,1431+0.5),
                };
                Compute.LineParticle(level,50,startVec3[Utils.HBossDelay],endVec3, ParticleTypes.WAX_OFF);
                Utils.HBossDelay--;
            }
            if (level.equals(level.getServer().getLevel(Level.OVERWORLD)) && Utils.HBossDelay == -1 && TickCount % 20 == 0 && event.phase.equals(TickEvent.Phase.START)) {
                Vec3 LightVec[] = {
                        new Vec3(-169+0.5,114,1422+0.5),
                        new Vec3(-169+0.5,114,1412+0.5),
                        new Vec3(-159+0.5,114,1412+0.5),
                        new Vec3(-459+0.5,114,1422+0.5)
                };
                for (int i = 0; i < 4; i++) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(LightVec[i]);
                    level.addFreshEntity(lightningBolt);
                }
                Entity entity = new HEntity(EntityInit.HETITY.get(), level);
                entity.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
                entity.moveTo(-164+0.5,115,1417+0.5);
                level.addFreshEntity(entity);
                Utils.HBossDelay--;
            }
        }
    }
}
