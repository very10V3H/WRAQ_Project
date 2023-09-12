package com.Very.very.Events.MonsterControl.MainStory_II;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
@Mod.EventBusSubscriber
public class EvokerMaster {
    @SubscribeEvent
    public static void EvokerMaster(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 240 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {101,46,-24,-59,-110};
                int [] indexY = {136,142,136,144,141};
                int [] indexZ = {906,879,885,916,951};
                int BoundaryX1 = 130;
                int BoundaryX2 = -130;
                int BoundaryY1 = 151;
                int BoundaryY2 = 126;
                int BoundaryZ1 = 971;
                int BoundaryZ2 = 886;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了森林唤魔大师。"));
                }
                for(int i=0;i<5;i++)
                {
                    if(list0.size() != 0 && (Utils.EvokerMaster[i] == null || !Utils.EvokerMaster[i].isAlive()))
                    {
                        if(Utils.EvokerMaster[i] != null) Utils.EvokerMaster[i].remove(Entity.RemovalReason.KILLED);
                        Utils.EvokerMaster[i] = new Evoker(EntityType.EVOKER,level);
                        Compute.SetMobCustomName(Utils.EvokerMaster[i],Moditems.ArmorEvokerMaster.get(),Component.literal("森林唤魔大师").withStyle(ChatFormatting.LIGHT_PURPLE));
                        Utils.EvokerMaster[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorEvokerMaster.get().getDefaultInstance());
                        Utils.EvokerMaster[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(4000.0D);
                        Utils.EvokerMaster[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.EvokerMaster[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(225);
                        Utils.EvokerMaster[i].setHealth(Utils.EvokerMaster[i].getMaxHealth());
                        Utils.EvokerMaster[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.EvokerMaster[i]);
                    }
                    if(Utils.EvokerMaster[i] != null){
                        double x = Utils.EvokerMaster[i].getX();
                        double y = Utils.EvokerMaster[i].getY();
                        double z = Utils.EvokerMaster[i].getZ();
                        if(x > 130 || x < -130 || y > 151 || y < 126 || z > 971 || z < 886)
                        {
                            Utils.EvokerMaster[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
        }
    }
}
