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
public class StoneElementEvent {
    public static Zombie[] zombies = new Zombie[20];
    @SubscribeEvent
    public static void StoneElement(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && level.getServer().getTickCount() % 20 == 0) {
                for (Zombie zombie : zombies) {
                    if (zombie != null && zombie.isAlive()) Element.ElementEffectAddToEntity(zombie,zombie,Element.Stone,1,false,0);
                }
            }

            if (level.getServer().getTickCount() % 300 == 16 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(1233,69,1422),
                        new Vec3(1233,70,1436),
                        new Vec3(1217,72,1427),
                        new Vec3(1202,76,1438),
                        new Vec3(1191,68,1460),
                        new Vec3(1198,83,1415),
                        new Vec3(1179,74,1408),
                        new Vec3(1165,65,1430),
                        new Vec3(1223,93,1401),
                        new Vec3(1244,67,1410),
                        new Vec3(1232,67,1387),
                        new Vec3(1201,69,1425),
                };

                int BoundaryX1 = 1250;
                int BoundaryX2 = 1149;
                int BoundaryY1 = 63;
                int BoundaryY2 = 137;
                int BoundaryZ1 = 1477;
                int BoundaryZ2 = 1371;

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
                            Component.literal("清理了层岩元素"));
                }

                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (zombies[i] == null || !zombies[i].isAlive())) {
                        if(zombies[i] != null) zombies[i].remove(Entity.RemovalReason.KILLED);
                        zombies[i] = new Zombie(EntityType.ZOMBIE, level);
                        Compute.SetMobCustomName(zombies[i], ModItems.MobArmorStoneElementHelmet.get(),
                                Component.literal("层岩元素").withStyle(CustomStyle.styleOfStone));
                        zombies[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorStoneElementHelmet.get().getDefaultInstance());
                        zombies[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorStoneElementChest.get().getDefaultInstance());
                        zombies[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorStoneElementLeggings.get().getDefaultInstance());
                        zombies[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorStoneElementBoots.get().getDefaultInstance());
                        zombies[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(10 * Math.pow(10,7));
                        zombies[i].setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_PICKAXE));
                        zombies[i].setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.MineShield.get()));
                        zombies[i].setHealth(zombies[i].getMaxHealth());
                        zombies[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
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
