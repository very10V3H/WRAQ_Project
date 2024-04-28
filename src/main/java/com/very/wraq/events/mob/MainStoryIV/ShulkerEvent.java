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
import net.minecraft.world.entity.monster.Shulker;
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
public class ShulkerEvent {
    public static Shulker[] shulkers = new Shulker[20];
    @SubscribeEvent
    public static void Shulker(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.END);

            if (level.getServer().getTickCount() % 300 == 76 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(38,86,557),
                        new Vec3(52,86,-186),
                        new Vec3(74,87,-187),
                        new Vec3(60.5,86,-168.5),
                        new Vec3(7,88,-178),
                        new Vec3(-3.5,88,-154.5),
                        new Vec3(-11.5,87,-168.5),
                        new Vec3(-26,88,-178),
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
                            Component.literal("清理了寂域遗骸"));
                }

                for (int i = 0; i < 12; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (shulkers[i] == null || !shulkers[i].isAlive())) {
                        if(shulkers[i] != null) shulkers[i].remove(Entity.RemovalReason.KILLED);
                        shulkers[i] = new Shulker(EntityType.SHULKER, level);
                        Compute.SetMobCustomName(shulkers[i], ModItems.MobArmorShulkerHelmet.get(),
                                Component.literal("寂域遗骸").withStyle(CustomStyle.styleOfEnd));
                        shulkers[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorShulkerHelmet.get().getDefaultInstance());
                        shulkers[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1 * Math.pow(10,7));
                        shulkers[i].setHealth(shulkers[i].getMaxHealth());
                        shulkers[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                        shulkers[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        level.addFreshEntity(shulkers[i]);
                    }
                    if (shulkers[i] != null) {
                        double x = shulkers[i].getX();
                        double y = shulkers[i].getY();
                        double z = shulkers[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            shulkers[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        }
                    }
                }
            }
        }
    }
}
