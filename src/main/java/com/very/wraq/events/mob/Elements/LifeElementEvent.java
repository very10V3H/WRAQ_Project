package com.very.wraq.events.mob.Elements;


import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
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
public class LifeElementEvent {
    public static Slime[] slimes = new Slime[20];
    @SubscribeEvent
    public static void LifeElement(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && level.getServer().getTickCount() % 20 == 0) {
                for (Slime slime : slimes) {
                    if (slime != null && slime.isAlive()) Element.ElementEffectAddToEntity(slime,slime,Element.Life,1,false,0);
                }
            }

            if (level.getServer().getTickCount() % 300 == 6 && level.equals(level1)) {
                int [] indexX = {1163,1166,1174,1168,1154,1148,1113,1087,1092,1111,1122};
                int [] indexY = {129,128,128,131,130,130,123,123,123,131,132};
                int [] indexZ = {1020,1036,1031,1054,1050,1073,1067,1072,1050,1023,1018};

                int BoundaryX1 = 1183;
                int BoundaryX2 = 1061;
                int BoundaryY1 = 152;
                int BoundaryY2 = 119;
                int BoundaryZ1 = 1094;
                int BoundaryZ2 = 985;

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
                            Component.literal("清理了生机元素"));
                }

                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(indexX.length);

                    if (players.size() != 0 && (slimes[i] == null || !slimes[i].isAlive())) {
                        if(slimes[i] != null) slimes[i].remove(Entity.RemovalReason.KILLED);
                        slimes[i] = new Slime(EntityType.SLIME, level);
                        Compute.SetMobCustomName(slimes[i], ModItems.MobArmorLifeElementHelmet.get(),
                                Component.literal("生机元素").withStyle(CustomStyle.styleOfLife));
                        slimes[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorLifeElementHelmet.get().getDefaultInstance());
                        slimes[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorLifeElementChest.get().getDefaultInstance());
                        slimes[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorLifeElementLeggings.get().getDefaultInstance());
                        slimes[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorLifeElementBoots.get().getDefaultInstance());
                        slimes[i].setSize(random.nextInt(1,2),true);
                        slimes[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(10 * Math.pow(10,7));
                        slimes[i].setHealth(slimes[i].getMaxHealth());
                        slimes[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        slimes[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                        slimes[i].moveTo(indexX[index],indexY[index],indexZ[index]);
                        level.addFreshEntity(slimes[i]);
                    }
                    if (slimes[i] != null) {
                        double x = slimes[i].getX();
                        double y = slimes[i].getY();
                        double z = slimes[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            slimes[i].moveTo(indexX[index],indexY[index],indexZ[index]);
                        }
                    }
                }
            }
        }
    }
}
