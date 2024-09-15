package com.very.wraq.events.mob.MainStoryVII;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.List;
import java.util.Random;

public class StarEvent {
    public static void vex(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.getServer().getTickCount() % 300 == 215 && level.equals(level1)) {
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 1111;
                int BoundaryX2 = 1002;
                int BoundaryY1 = 283;
                int BoundaryY2 = 200;
                int BoundaryZ1 = 1307;
                int BoundaryZ2 = 1184;
                Vec3 centerPos = new Vec3(1059, 241, 1238);
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                        Math.abs(BoundaryX1 - BoundaryX2) + 10, Math.abs(BoundaryY1 - BoundaryY2) + 10, Math.abs(BoundaryZ1 - BoundaryZ2) + 10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((BoundaryX1 + BoundaryX2) / 2, (BoundaryY1 + BoundaryY2) / 2, (BoundaryZ1 + BoundaryZ2) / 2),
                        Math.abs(BoundaryX1 - BoundaryX2) + 50, Math.abs(BoundaryY1 - BoundaryY2) + 50, Math.abs(BoundaryZ1 - BoundaryZ2) + 50));

                if (list.size() > 30) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.formatBroad(level, Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了梦灵"));
                }

                for (int i = 0; i < 10; i++) {
                    Random r = new Random();
                    if (list0.size() != 0 && (Utils.star[i] == null || !Utils.star[i].isAlive())) {
                        if (Utils.star[i] != null) Utils.star[i].remove(Entity.RemovalReason.KILLED);
                        Utils.star[i] = new Vex(EntityType.VEX, level);
                        ItemStack itemStack = ModItems.MobArmorStar.get().getDefaultInstance();
                        Compute.setMobCustomName(Utils.star[i], itemStack.getItem(), Component.literal("梦灵").withStyle(CustomStyle.styleOfMoon1));

                        Utils.star[i].setItemSlot(EquipmentSlot.HEAD, itemStack);
                        Utils.star[i].setItemSlot(EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultInstance());
                        Utils.star[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(4000000);
                        Utils.star[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10000D);

                        Utils.star[i].setHealth(Utils.star[i].getMaxHealth());
                        Utils.star[i].addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
                        Utils.star[i].moveTo(centerPos.x + r.nextDouble(24), centerPos.y + r.nextDouble(24), centerPos.z + r.nextDouble(24));
                        level.addFreshEntity(Utils.star[i]);
                    }
                    if (Utils.star[i] != null) {
                        double x = Utils.star[i].getX();
                        double y = Utils.star[i].getY();
                        double z = Utils.star[i].getZ();
                        if (x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            Utils.star[i].moveTo(centerPos.x + r.nextDouble(24), centerPos.y + r.nextDouble(24), centerPos.z + r.nextDouble(24));
                        }
                    }
                }
            }
        }
    }

    public static void SummonStar1(Level level, Vec3 pos) {
        Vex vex = new Vex(EntityType.VEX, level);
        ItemStack itemStack = ModItems.MobArmorStar1.get().getDefaultInstance();
        Compute.setMobCustomName(vex, itemStack.getItem(), Component.literal("星使").withStyle(CustomStyle.styleOfMoon1));

        vex.setItemSlot(EquipmentSlot.HEAD, itemStack);
        vex.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
        vex.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16000000);
        vex.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(40000);

        vex.setHealth(vex.getMaxHealth());
        vex.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
        vex.moveTo(pos);
        level.addFreshEntity(vex);
    }
}
