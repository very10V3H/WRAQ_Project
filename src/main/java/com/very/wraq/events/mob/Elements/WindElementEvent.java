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
import net.minecraft.world.entity.monster.Stray;
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
public class WindElementEvent {
    public static Stray[] strays = new Stray[20];
    @SubscribeEvent
    public static void WindElement(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && level.getServer().getTickCount() % 20 == 0) {
                for (Stray stray : strays) {
                    if (stray != null && stray.isAlive()) Element.ElementEffectAddToEntity(stray,stray,Element.Wind,1,false,0);
                }
            }

            if (level.getServer().getTickCount() % 300 == 26 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(1142,302,404),
                        new Vec3(1154,302,396),
                        new Vec3(1158,283,370),
                        new Vec3(1146,283,360),
                        new Vec3(1118,254,376),
                        new Vec3(1115,254,391),
                        new Vec3(1141,217,411),
                        new Vec3(1156,217,407),
                        new Vec3(1161,177,363),
                        new Vec3(1153,177,353),
                        new Vec3(1108,137,374),
                        new Vec3(1109,137,393),
                };

                int BoundaryX1 = 1190;
                int BoundaryX2 = 1055;
                int BoundaryY1 = 322;
                int BoundaryY2 = 64;
                int BoundaryZ1 = 457;
                int BoundaryZ2 = 321;

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
                            Component.literal("清理了澄风元素"));
                }

                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (strays[i] == null || !strays[i].isAlive())) {
                        if(strays[i] != null) strays[i].remove(Entity.RemovalReason.KILLED);
                        strays[i] = new Stray(EntityType.STRAY, level);
                        Compute.SetMobCustomName(strays[i], ModItems.MobArmorWindElementHelmet.get(),
                                Component.literal("澄风元素").withStyle(CustomStyle.styleOfWind));
                        strays[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorWindElementHelmet.get().getDefaultInstance());
                        strays[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorWindElementChest.get().getDefaultInstance());
                        strays[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorWindElementLeggings.get().getDefaultInstance());
                        strays[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorWindElementBoots.get().getDefaultInstance());
                        strays[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(10 * Math.pow(10,7));
                        strays[i].setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                        strays[i].setHealth(strays[i].getMaxHealth());
                        strays[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                        strays[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                        strays[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        level.addFreshEntity(strays[i]);
                    }
                    if (strays[i] != null) {
                        double x = strays[i].getX();
                        double y = strays[i].getY();
                        double z = strays[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            strays[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        }
                    }
                }
            }
        }
    }
}
