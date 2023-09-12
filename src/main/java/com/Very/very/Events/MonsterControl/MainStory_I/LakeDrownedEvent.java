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
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class LakeDrownedEvent {
    @SubscribeEvent
    public static void lakedrowned(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 140 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                double [] indexX = {35.5,64.5,42.5,10.5,12.6,-4.7};
                double [] indexY = {60,51,53,55,49,53};
                double [] indexZ = {943.3,971.7,980.4,965.6,993.3,979.3};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 84;
                int BoundaryX2 = -32;
                int BoundaryY1 = 65;
                int BoundaryY2 = 17;
                int BoundaryZ1 = 1014;
                int BoundaryZ2 = 926;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了湖泊守卫。"));
                }
                for(int i=0;i<7;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.LakeDrown[i] == null || !Utils.LakeDrown[i].isAlive()))
                    {
                        if(Utils.LakeDrown[i] != null) Utils.LakeDrown[i].remove(Entity.RemovalReason.KILLED);
                        Utils.LakeDrown[i] = new Drowned(EntityType.DROWNED,level);
                        Compute.SetMobCustomName(Utils.LakeDrown[i],Moditems.ArmorDrown.get(),Component.literal("湖泊守卫者").withStyle(ChatFormatting.BLUE));
                        Utils.LakeDrown[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorDrown.get().getDefaultInstance());
                        Utils.LakeDrown[i].setItemSlot(EquipmentSlot.MAINHAND , Items.TRIDENT .getDefaultInstance());
                        Utils.LakeDrown[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.LakeDrown[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.LakeDrown[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25D);
                        Utils.LakeDrown[i].setHealth(Utils.LakeDrown[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.LakeDrown[i].moveTo(indexX[tmpnum],indexY[tmpnum],indexZ[tmpnum]);
                        level.addFreshEntity(Utils.LakeDrown[i]);
                    }
                    if(Utils.LakeDrown[i] != null){
                        double x = Utils.LakeDrown[i].getX();
                        double y = Utils.LakeDrown[i].getY();
                        double z = Utils.LakeDrown[i].getZ();
                        if(x > 84 || x < -32 || y > 65 || y < 17 || z > 1014 || z < 926)
                        {
                            int tmpnum = r.nextInt(6);
                            Utils.LakeDrown[i].moveTo(indexX[tmpnum],indexY[tmpnum],indexZ[tmpnum]);
                        }
                    }
                }
            }
        }
    }
}
