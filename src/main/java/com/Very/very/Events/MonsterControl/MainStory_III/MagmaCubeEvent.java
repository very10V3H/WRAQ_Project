package com.Very.very.Events.MonsterControl.MainStory_III;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
@Mod.EventBusSubscriber
public class MagmaCubeEvent {
    @SubscribeEvent
    public static void MagmaCube(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.NETHER);
            if(level.getServer().getTickCount() % 400 == 0 && level.equals(level1)
                    && Utils.SummonTick0 != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick0 = level.getServer().getTickCount();
                int [] indexX = {-3,-17,-31,-40,-30};
                int [] indexY = {63,61,60,59,59};
                int [] indexZ = {101,90,91,100,122};
                int BoundaryX1 = 10;
                int BoundaryX2 = -50;
                int BoundaryY1 = 73;
                int BoundaryY2 = 49;
                int BoundaryZ1 = 142;
                int BoundaryZ2 = 70;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 35) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了下界熔岩能量聚合物。"));
                }
                for(int i=0;i<5;i++)
                {
                    if(list0.size() != 0 && (Utils.MagmaCube[i] == null || !Utils.MagmaCube[i].isAlive()))
                    {
                        if(Utils.MagmaCube[i] != null) Utils.MagmaCube[i].remove(Entity.RemovalReason.KILLED);
                        Utils.MagmaCube[i] = new MagmaCube(EntityType.MAGMA_CUBE,level);
                        Utils.MagmaCube[i].setSize(2,true);
                        Compute.SetMobCustomName(Utils.MagmaCube[i],Moditems.ArmorMagma.get(),Component.literal("下界熔岩能量聚合物").withStyle(ChatFormatting.RED));
                        Utils.MagmaCube[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorMagma.get().getDefaultInstance());
                        Utils.MagmaCube[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2500.0D);
                        Utils.MagmaCube[i].setHealth(Utils.MagmaCube[i].getMaxHealth());
                        Utils.MagmaCube[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.MagmaCube[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60);
                        Utils.MagmaCube[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.MagmaCube[i]);
                    }
                    if(Utils.MagmaCube[i] != null){
                        double x = Utils.MagmaCube[i].getX();
                        double y = Utils.MagmaCube[i].getY();
                        double z = Utils.MagmaCube[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.MagmaCube[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
        }

    }
}
