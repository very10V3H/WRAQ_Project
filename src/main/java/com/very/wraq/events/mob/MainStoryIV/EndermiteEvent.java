package com.very.wraq.events.mob.MainStoryIV;


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
import net.minecraft.world.entity.monster.Endermite;
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
public class EndermiteEvent {
    public static Endermite[] endermites = new Endermite[20];

    @SubscribeEvent
    public static void Endermite(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.END);

            if (level.getServer().getTickCount() % 300 == 86 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(-28,89,-220),
                        new Vec3(-11.5,87,-240.5),
                        new Vec3(5,87,-248),
                        new Vec3(0.5,86,-223.5),
                        new Vec3(39.5,89,-229.5),
                        new Vec3(46,89,-252),
                        new Vec3(60.5,87,-240.5),
                        new Vec3(71,89,-224),
                };

                int BoundaryX1 = 116;
                int BoundaryX2 = -80;
                int BoundaryY1 = 180;
                int BoundaryY2 = 0;
                int BoundaryZ1 = -100;
                int BoundaryZ2 = -300;

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
                            Component.literal("清理了寂域灵螨"));
                }

                for (int i = 0; i < 12; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (endermites[i] == null || !endermites[i].isAlive())) {
                        if(endermites[i] != null) endermites[i].remove(Entity.RemovalReason.KILLED);
                        endermites[i] = new Endermite(EntityType.ENDERMITE, level);
                        Compute.SetMobCustomName(endermites[i], ModItems.MobArmorEnderMiteHelmet.get(),
                                Component.literal("寂域灵螨").withStyle(CustomStyle.styleOfEnd));
                        endermites[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorEnderMiteHelmet.get().getDefaultInstance());
                        endermites[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1 * Math.pow(10,7));
                        endermites[i].setHealth(endermites[i].getMaxHealth());
                        endermites[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                        endermites[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        level.addFreshEntity(endermites[i]);
                    }
                    if (endermites[i] != null) {
                        double x = endermites[i].getX();
                        double y = endermites[i].getY();
                        double z = endermites[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            endermites[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        }
                    }
                }
            }
        }
    }
}
