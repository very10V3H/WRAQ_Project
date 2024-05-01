package com.very.wraq.events.mob.Elements;


import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
public class IceElementEvent {
    public static Zombie[] zombies = new Zombie[20];
    @SubscribeEvent
    public static void IceElement(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && level.getServer().getTickCount() % 20 == 0) {
                for (Zombie zombie : zombies) {
                    if (zombie != null && zombie.isAlive()) Element.ElementEffectAddToEntity(zombie,zombie,Element.Ice,1,false,0);
                }
            }

            if (level.getServer().getTickCount() % 300 == 66 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(1681,73,1977),
                        new Vec3(1661,73,1985),
                        new Vec3(1675,73,1999),
                        new Vec3(1693,73,2007),
                        new Vec3(1710,73,2012),
                        new Vec3(1695,72,2029),
                        new Vec3(1731,72,2014),
                        new Vec3(1716,73,1997),
                        new Vec3(1729,73,1991),
                        new Vec3(1732,73,1970),
                        new Vec3(1722,73,1955),
                        new Vec3(1706,73,1962),
                };

                int BoundaryX1 = 1765;
                int BoundaryX2 = 1635;
                int BoundaryY1 = 116;
                int BoundaryY2 = 60;
                int BoundaryZ1 = 2039;
                int BoundaryZ2 = 1922;

                List<Mob> mobs = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (double) (BoundaryX1 + BoundaryX2) /2,
                                (double) (BoundaryY1 + BoundaryY2) /2, (double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2) + 100,Math.abs(BoundaryY1-BoundaryY2) + 100,Math.abs(BoundaryZ1-BoundaryZ2) + 100));
                List<Player> players = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (double) (BoundaryX1 + BoundaryX2) /2,
                                (double) (BoundaryY1 + BoundaryY2) /2, (double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2) + 50,Math.abs(BoundaryY1-BoundaryY2) + 50,Math.abs(BoundaryZ1-BoundaryZ2) + 50));

                if (mobs.size() > 40) {
                    for (Mob monster : mobs) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了凛冰元素"));
                }

                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (zombies[i] == null || !zombies[i].isAlive())) {
                        if(zombies[i] != null) zombies[i].remove(Entity.RemovalReason.KILLED);
                        zombies[i] = new Zombie(EntityType.ZOMBIE, level);
                        Compute.SetMobCustomName(zombies[i], ModItems.MobArmorIceElementHelmet.get(),
                                Component.literal("凛冰元素").withStyle(CustomStyle.styleOfIce));
                        zombies[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorIceElementHelmet.get().getDefaultInstance());
                        zombies[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorIceElementChest.get().getDefaultInstance());
                        zombies[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorIceElementLeggings.get().getDefaultInstance());
                        zombies[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorIceElementBoots.get().getDefaultInstance());
                        zombies[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(10 * Math.pow(10,7));
                        zombies[i].setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                        zombies[i].setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.SnowShield.get()));
                        zombies[i].setHealth(zombies[i].getMaxHealth());
                        zombies[i].setBaby(true);
                        zombies[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                        zombies[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                        zombies[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        level.addFreshEntity(zombies[i]);
                    }
                    if (zombies[i] != null) {
                        double x = zombies[i].getX();
                        double y = zombies[i].getY();
                        double z = zombies[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            zombies[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        }
                    }
                }
            }
        }
    }
}
