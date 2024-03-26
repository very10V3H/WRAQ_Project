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
public class EarthManaEvent {
    @SubscribeEvent
    public static void EarthMana(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.getServer().getTickCount() % 400 == 350 && level.equals(level1)) {
                int [] indexX = {1838,1826,1846,1821,1832,1853,1864};
                int [] indexY = {120,116,135,116,136,138,136};
                int [] indexZ = {915,926,921,893,882,888,899};
                int BoundaryX1 = 1888;
                int BoundaryX2 = 1785;
                int BoundaryY1 = 152;
                int BoundaryY2 = 104;
                int BoundaryZ1 = 920;
                int BoundaryZ2 = 860;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了魔力衍生物-地魔"));
                }
                for (int i = 0; i < 9; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(indexX.length);
                    if (list0.size() != 0 && (Utils.EarthMana[i] == null || !Utils.EarthMana[i].isAlive())) {
                        if(Utils.EarthMana[i] != null) Utils.EarthMana[i].remove(Entity.RemovalReason.KILLED);
                        Utils.EarthMana[i] = new Zombie(EntityType.ZOMBIE, level);
                        Utils.EarthMana[i].setBaby(true);
                        Compute.SetMobCustomName(Utils.EarthMana[i], ModItems.MobArmorEarthManaHelmet.get(),
                                Component.literal("魔力衍生物-地魔").withStyle(Utils.styleOfBloodMana));
                        Utils.EarthMana[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorEarthManaHelmet.get().getDefaultInstance());
                        Utils.EarthMana[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorEarthManaChest.get().getDefaultInstance());
                        Utils.EarthMana[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorEarthManaLeggings.get().getDefaultInstance());
                        Utils.EarthMana[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorEarthManaBoots.get().getDefaultInstance());

                        Utils.EarthMana[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1100000);
                        Utils.EarthMana[i].setHealth(Utils.EarthMana[i].getMaxHealth());
                        Utils.EarthMana[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.EarthMana[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000);

                        Utils.EarthMana[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        level.addFreshEntity(Utils.EarthMana[i]);
                    }
                    if (Utils.EarthMana[i] != null) {
                        double x = Utils.EarthMana[i].getX();
                        double y = Utils.EarthMana[i].getY();
                        double z = Utils.EarthMana[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.EarthMana[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
