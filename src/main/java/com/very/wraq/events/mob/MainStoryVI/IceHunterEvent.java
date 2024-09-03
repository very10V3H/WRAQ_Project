package com.very.wraq.events.mob.MainStoryVI;


import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.List;
import java.util.Random;

public class IceHunterEvent {
    public static void IceHunter(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Stray stray : Utils.IceHunter) {
                if (stray != null && stray.isAlive()) Element.ElementProvider(stray, Element.ice, 1.5);
            }
            if (level.getServer().getTickCount() % 400 == 0 && level.equals(level1)) {
                int[] indexX = {590, 624, 644, 621, 606, 623};
                int[] indexY = {64, 64, 64, 64, 64, 64};
                int[] indexZ = {1887, 1887, 1897, 1913, 1910, 1896};
                int BoundaryX1 = 663;
                int BoundaryX2 = 566;
                int BoundaryY1 = 75;
                int BoundaryY2 = 55;
                int BoundaryZ1 = 1928;
                int BoundaryZ2 = 1857;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) / 2,
                                (double) (BoundaryY1 + BoundaryY2) / 2, (double) (BoundaryZ1 + BoundaryZ2) / 2),
                        Math.abs(BoundaryX1 - BoundaryX2) + 100, Math.abs(BoundaryY1 - BoundaryY2) + 100, Math.abs(BoundaryZ1 - BoundaryZ2) + 100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) / 2,
                                (double) (BoundaryY1 + BoundaryY2) / 2, (double) (BoundaryZ1 + BoundaryZ2) / 2),
                        Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.formatBroad(level, Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了冰原猎手"));
                }
                for (int i = 0; i < 9; i++) {
                    Random random = new Random();
                    int Index = random.nextInt(6);
                    if (list0.size() != 0 && (Utils.IceHunter[i] == null || !Utils.IceHunter[i].isAlive())) {
                        if (Utils.IceHunter[i] != null) Utils.IceHunter[i].remove(Entity.RemovalReason.KILLED);
                        Utils.IceHunter[i] = new Stray(EntityType.STRAY, level);
                        Compute.SetMobCustomName(Utils.IceHunter[i], ModItems.MobArmorIceHunterHelmet.get(),
                                Component.literal("冰原猎手").withStyle(CustomStyle.styleOfIce));
                        Utils.IceHunter[i].setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorIceHunterHelmet.get().getDefaultInstance());
                        Utils.IceHunter[i].setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorIceHunterChest.get().getDefaultInstance());
                        Utils.IceHunter[i].setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorIceHunterLeggings.get().getDefaultInstance());
                        Utils.IceHunter[i].setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorIceHunterBoots.get().getDefaultInstance());
                        Utils.IceHunter[i].setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
                        Utils.IceHunter[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(150000);
                        Utils.IceHunter[i].setHealth(Utils.IceHunter[i].getMaxHealth());
                        Utils.IceHunter[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.IceHunter[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000);

                        Utils.IceHunter[i].moveTo(indexX[Index], indexY[Index], indexZ[Index]);
                        level.addFreshEntity(Utils.IceHunter[i]);
                    }
                    if (Utils.IceHunter[i] != null) {
                        double x = Utils.IceHunter[i].getX();
                        double y = Utils.IceHunter[i].getY();
                        double z = Utils.IceHunter[i].getZ();
                        if (x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            Utils.IceHunter[i].moveTo(indexX[Index], indexY[Index], indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
