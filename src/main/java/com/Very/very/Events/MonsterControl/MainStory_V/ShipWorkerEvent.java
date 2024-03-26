package com.Very.very.Events.MonsterControl.MainStory_V;


import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Zombie;
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
public class ShipWorkerEvent {
    @SubscribeEvent
    public static void ShipWorker(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.getServer().getTickCount() % 400 == 33 && level.equals(level1)) {
                int [] indexX = {1911,1890,1908,1904,1907,1907,1904,1910,1910};
                int [] indexY = {66,66,68,65,68,68,65,65,74};
                int [] indexZ = {1081,1081,1056,1056,1002,1019,1003,1019,994};
                int BoundaryX1 = 1942;
                int BoundaryX2 = 1873;
                int BoundaryY1 = 84;
                int BoundaryY2 = 54;
                int BoundaryZ1 = 1108;
                int BoundaryZ2 = 964;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了废弃船厂员工"));
                }
                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(indexX.length);
                    if (list0.size() != 0 && (Utils.Pillager[i] == null || !Utils.Pillager[i].isAlive())) {
                        if(Utils.Pillager[i] != null) Utils.Pillager[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Pillager[i] = new Pillager(EntityType.PILLAGER, level);
                        Compute.SetMobCustomName(Utils.Pillager[i], ModItems.MobArmorShipHelmet.get(),
                                Component.literal("废弃船厂员工").withStyle(Utils.styleOfShip));
                        Utils.Pillager[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorShipHelmet.get().getDefaultInstance());
                        Utils.Pillager[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorShipChest.get().getDefaultInstance());
                        Utils.Pillager[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorShipLeggings.get().getDefaultInstance());
                        Utils.Pillager[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorShipBoots.get().getDefaultInstance());
                        Utils.Pillager[i].setItemSlot(EquipmentSlot.MAINHAND , ModItems.ShipSword.get().getDefaultInstance());
                        Utils.Pillager[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(250000);
                        Utils.Pillager[i].setHealth(250000);
                        Utils.Pillager[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.Pillager[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(150);

                        Utils.Pillager[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        level.addFreshEntity(Utils.Pillager[i]);
                    }
                    if (Utils.Pillager[i] != null) {
                        double x = Utils.Pillager[i].getX();
                        double y = Utils.Pillager[i].getY();
                        double z = Utils.Pillager[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.Pillager[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
