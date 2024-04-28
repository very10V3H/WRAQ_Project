package com.very.wraq.events.mob.Castle;


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
import net.minecraft.world.entity.monster.Evoker;
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
public class TreeEvent {

    public static Evoker[] evokers = new Evoker[15];
    @SubscribeEvent
    public static void IceHunter(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Evoker evoker : evokers) {
                if (evoker != null && evoker.isAlive()) Element.ElementProvider(evoker, Element.Lightning, 1);
            }
            if (level.getServer().getTickCount() % 300 == 106 && level.equals(level1)) {
                int [] indexX = {839,858,876,871,857,850};
                int [] indexY = {71 ,71 ,71 ,71 ,72 ,71 };
                int [] indexZ = {955,953,962,980,987,970};

                int BoundaryX1 = 904;
                int BoundaryX2 = 820;
                int BoundaryY1 = 84;
                int BoundaryY2 = 65;
                int BoundaryZ1 = 1011;
                int BoundaryZ2 = 938;

                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 50) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了古树魔能研究者"));
                }

                for (int i = 0; i < 9; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(6);
                    if (list0.size() != 0 && (evokers[i] == null || !evokers[i].isAlive())) {
                        if(evokers[i] != null) evokers[i].remove(Entity.RemovalReason.KILLED);
                        evokers[i] = new Evoker(EntityType.EVOKER, level);
                        Compute.SetMobCustomName(evokers[i], ModItems.MobArmorTreeHelmet.get(),
                                Component.literal("古树魔能研究者").withStyle(CustomStyle.styleOfIce));
                        evokers[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorTreeHelmet.get().getDefaultInstance());

                        evokers[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2 * Math.pow(10,6));
                        evokers[i].setHealth(evokers[i].getMaxHealth());
                        evokers[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4);
                        evokers[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3000);

                        evokers[i].moveTo(indexX[Index] + 0.5,indexY[Index] + 1,indexZ[Index] + 0.5);
                        level.addFreshEntity(evokers[i]);
                    }
                    if (evokers[i] != null) {
                        double x = evokers[i].getX();
                        double y = evokers[i].getY();
                        double z = evokers[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            evokers[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
