package com.very.wraq.events.mob.Elements;


import com.very.wraq.process.element.Element;
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
import net.minecraft.world.entity.monster.Witch;
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
public class WitchElementEvent {
    public static Witch[] witches = new Witch[20];
    @SubscribeEvent
    public static void LightningElement(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {

            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && level.getServer().getTickCount() % 20 == 0) {
                for (Witch witch : witches) {
                    if (witch != null && witch.isAlive()) Element.ElementEffectAddToEntity(witch,witch,Element.Lightning,1,false,0);
                }
            }

            if (level.getServer().getTickCount() % 300 == 46 && level.equals(level1)) {
                Vec3[] vec3s = {
                        new Vec3(1695,70,587),
                        new Vec3(1678,70,577),
                        new Vec3(1656,70,558),
                        new Vec3(1627,70,540),
                        new Vec3(1626,70,517),
                        new Vec3(1630,70,488),
                        new Vec3(1669,70,539),
                        new Vec3(1671,70,506),
                        new Vec3(1696,70,511),
                        new Vec3(1700,69,542),
                        new Vec3(1726,69,515),
                        new Vec3(1717,69,564),
                };

                int BoundaryX1 = 1763;
                int BoundaryX2 = 1546;
                int BoundaryY1 = 131;
                int BoundaryY2 = 60;
                int BoundaryZ1 = 632;
                int BoundaryZ2 = 408;

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
                            Component.literal("清理了怒雷元素"));
                }

                for (int i = 0; i < 15; i ++) {
                    Random random = new Random();
                    int index = random.nextInt(vec3s.length);

                    if (players.size() != 0 && (witches[i] == null || !witches[i].isAlive())) {
                        if(witches[i] != null) witches[i].remove(Entity.RemovalReason.KILLED);
                        witches[i] = new Witch(EntityType.WITCH, level);
                        Compute.SetMobCustomName(witches[i], ModItems.MobArmorLightningElementHelmet.get(),
                                Component.literal("怒雷元素").withStyle(CustomStyle.styleOfLightning));
                        witches[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorLightningElementHelmet.get().getDefaultInstance());
                        witches[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorLightningElementChest.get().getDefaultInstance());
                        witches[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorLightningElementLeggings.get().getDefaultInstance());
                        witches[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorLightningElementBoots.get().getDefaultInstance());
                        witches[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(10 * Math.pow(10,7));
                        witches[i].setHealth(witches[i].getMaxHealth());
                        witches[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
                        witches[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60000);
                        witches[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        level.addFreshEntity(witches[i]);
                    }
                    if (witches[i] != null) {
                        double x = witches[i].getX();
                        double y = witches[i].getY();
                        double z = witches[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            witches[i].moveTo(vec3s[index].x, vec3s[index].y, vec3s[index].z);
                        }
                    }
                }
            }
        }
    }
}
