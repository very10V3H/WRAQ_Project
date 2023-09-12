package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
@Mod.EventBusSubscriber
public class VolcanoBlazeEvent {
    @SubscribeEvent
    public static void volcanoblaze(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 160 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {28,50,42,29,17,5};
                int [] indexY = {-52,-46,-47,-46,-48,-46};
                int [] indexZ = {1052,1045,1073,1088,1104,1059};
                int BoundaryX1 = 69;
                int BoundaryX2 = -12;
                int BoundaryY1 = -18;
                int BoundaryY2 = -57;
                int BoundaryZ1 = 1123;
                int BoundaryZ2 = 1007;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了火山烈焰。"));
                }
                for(int i=0;i<7;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.Blazes[i] == null || !Utils.Blazes[i].isAlive()))
                    {
                        if(Utils.Blazes[i] != null) Utils.Blazes[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Blazes[i] = new Blaze(EntityType.BLAZE,level);
                        Compute.SetMobCustomName(Utils.Blazes[i],Moditems.ArmorBlaze.get(),Component.literal("火山烈焰").withStyle(ChatFormatting.YELLOW));
                        Utils.Blazes[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorBlaze.get().getDefaultInstance());
                        Utils.Blazes[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0D);
                        Utils.Blazes[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.Blazes[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.Blazes[i].setHealth(Utils.Blazes[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.Blazes[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.Blazes[i]);
                    }
                    if(Utils.Blazes[i] != null){
                        double x = Utils.Blazes[i].getX();
                        double y = Utils.Blazes[i].getY();
                        double z = Utils.Blazes[i].getZ();
                        if(x > 69 || x < -12 || y > -18 || y < -57 || z > 1123 || z < 1007)
                        {
                            int tmpnum = r.nextInt(6);
                            Utils.Blazes[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
        }
    }
}
