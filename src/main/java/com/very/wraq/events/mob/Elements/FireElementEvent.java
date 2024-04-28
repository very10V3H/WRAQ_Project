package com.very.wraq.events.mob.Elements;


import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
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
import net.minecraft.world.entity.monster.Blaze;
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
public class FireElementEvent {
    public static Blaze[] blazes = new Blaze[20];
    @SubscribeEvent
    public static void FireElement(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.NETHER);
            if (level.equals(level1) && level.getServer().getTickCount() % 20 == 0) {
                for (Blaze blaze : blazes) {
                    if (blaze != null && blaze.isAlive()) Element.ElementEffectAddToEntity(blaze,blaze,Element.Fire,1,false,0);
                }
            }

            if (level.getServer().getTickCount() % 300 == 56 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(77,108,557),
                        new Vec3(100,108,546),
                        new Vec3(100,107,528),
                        new Vec3(120,105,537),
                        new Vec3(121,111,519),
                        new Vec3(118,108,500),
                        new Vec3(136,108,508),
                        new Vec3(136,102,489),
                        new Vec3(116,117,531),
                        new Vec3(108,117,530),
                };

                int BoundaryX1 = 141;
                int BoundaryX2 = 57;
                int BoundaryY1 = 146;
                int BoundaryY2 = 80;
                int BoundaryZ1 = 599;
                int BoundaryZ2 = 475;

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
                            Component.literal("清理了炽焰元素"));
                }

                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (blazes[i] == null || !blazes[i].isAlive())) {
                        if(blazes[i] != null) blazes[i].remove(Entity.RemovalReason.KILLED);
                        blazes[i] = new Blaze(EntityType.BLAZE, level);
                        Compute.SetMobCustomName(blazes[i], ModItems.MobArmorFireElementHelmet.get(),
                                Component.literal("炽焰元素").withStyle(CustomStyle.styleOfFire));
                        blazes[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorFireElementHelmet.get().getDefaultInstance());
                        blazes[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorFireElementChest.get().getDefaultInstance());
                        blazes[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorFireElementLeggings.get().getDefaultInstance());
                        blazes[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorFireElementBoots.get().getDefaultInstance());
                        blazes[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(10 * Math.pow(10,7));
                        blazes[i].setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
                        blazes[i].setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.SnowShield.get()));
                        blazes[i].setHealth(blazes[i].getMaxHealth());
                        blazes[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                        blazes[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                        blazes[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        level.addFreshEntity(blazes[i]);
                    }
                    if (blazes[i] != null) {
                        double x = blazes[i].getX();
                        double y = blazes[i].getY();
                        double z = blazes[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            blazes[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        }
                    }
                }
            }
        }
    }
}
