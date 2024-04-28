package com.very.wraq.events.mob.Castle;


import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
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
public class BlazeEvent {

    public static Blaze[] blazes = new Blaze[15];
    @SubscribeEvent
    public static void IceHunter(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Blaze blaze : blazes) {
                if (blaze != null && blaze.isAlive()) Element.ElementProvider(blaze, Element.Fire, 1);
            }
            if (level.getServer().getTickCount() % 300 == 104 && level.equals(level1)) {
                int [] indexX = {941,942,930,914,916,928};
                int [] indexY = {69 ,69 ,69 ,69 ,69 ,69 };
                int [] indexZ = {979,996,1004,997,978,967};

                int BoundaryX1 = 953;
                int BoundaryX2 = 899;
                int BoundaryY1 = 85;
                int BoundaryY2 = 63;
                int BoundaryZ1 = 1011;
                int BoundaryZ2 = 952;

                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 50) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了熔岩湖溢出物"));
                }

                for (int i = 0; i < 9; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(6);
                    if (list0.size() != 0 && (blazes[i] == null || !blazes[i].isAlive())) {
                        if(blazes[i] != null) blazes[i].remove(Entity.RemovalReason.KILLED);
                        blazes[i] = new Blaze(EntityType.BLAZE, level);
                        Compute.SetMobCustomName(blazes[i], ModItems.MobArmorBlazeHelmet.get(),
                                Component.literal("熔岩湖溢出物").withStyle(CustomStyle.styleOfPower));
                        blazes[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorBlazeHelmet.get().getDefaultInstance());

                        blazes[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2 * Math.pow(10,6));
                        blazes[i].setHealth(blazes[i].getMaxHealth());
                        blazes[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4);
                        blazes[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3000);

                        blazes[i].moveTo(indexX[Index] + 0.5,indexY[Index] + 1,indexZ[Index] + 0.5);
                        level.addFreshEntity(blazes[i]);
                    }
                    if (blazes[i] != null) {
                        double x = blazes[i].getX();
                        double y = blazes[i].getY();
                        double z = blazes[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            blazes[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
