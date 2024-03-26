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
public class BloodManaEvent {
    @SubscribeEvent
    public static void BloodMana(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.getServer().getTickCount() % 400 == 370 && level.equals(level1)) {
                int [] indexX = {2193,2177,2164,2164,2129,2125,2153,2167};
                int [] indexY = {71,71,72,70,69,71,71,71};
                int [] indexZ = {1092,1074,1062,1057,1071,1099,1115,1097};
                int BoundaryX1 = 2209;
                int BoundaryX2 = 2104;
                int BoundaryY1 = 84;
                int BoundaryY2 = 52;
                int BoundaryZ1 = 1126;
                int BoundaryZ2 = 1026;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了魔力衍生物-血魔"));
                }
                for (int i = 0; i < 9; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(indexX.length);
                    if (list0.size() != 0 && (Utils.BloodMana[i] == null || !Utils.BloodMana[i].isAlive())) {
                        if(Utils.BloodMana[i] != null) Utils.BloodMana[i].remove(Entity.RemovalReason.KILLED);
                        Utils.BloodMana[i] = new Zombie(EntityType.ZOMBIE, level);
                        Utils.BloodMana[i].setBaby(true);
                        Compute.SetMobCustomName(Utils.BloodMana[i], ModItems.MobArmorBloodManaHelmet.get(),
                                Component.literal("魔力衍生物-血魔").withStyle(Utils.styleOfBloodMana));
                        Utils.BloodMana[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorBloodManaHelmet.get().getDefaultInstance());
                        Utils.BloodMana[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorEarthManaChest.get().getDefaultInstance());
                        Utils.BloodMana[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorEarthManaLeggings.get().getDefaultInstance());
                        Utils.BloodMana[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorEarthManaBoots.get().getDefaultInstance());
                        Utils.BloodMana[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1100000);
                        Utils.BloodMana[i].setHealth(Utils.BloodMana[i].getMaxHealth());
                        Utils.BloodMana[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.BloodMana[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000);
                        Utils.BloodMana[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        level.addFreshEntity(Utils.BloodMana[i]);
                    }
                    if (Utils.BloodMana[i] != null) {
                        double x = Utils.BloodMana[i].getX();
                        double y = Utils.BloodMana[i].getY();
                        double z = Utils.BloodMana[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.BloodMana[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
