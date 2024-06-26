package com.very.wraq.events.mob.MainStoryII;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
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
public class KazeSkeletonEvent {
    @SubscribeEvent
    public static void KazeSkeleton(TickEvent.LevelTickEvent event) {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Skeleton skeleton : Utils.KazeSkeleton) {
                if (skeleton != null && skeleton.isAlive()) Element.ElementProvider(skeleton, Element.Wind, 1);
            }

            if(level.getServer().getTickCount() % 300 == 20 && level.equals(level1)) {
                int [] indexX = {35,30,21,8,-12,-23};
                int [] indexY = {73, 75, 87, 92, 94, 93};
                int [] indexZ = {1428,1435,1455,1475,1490,1515};
                int BoundaryX1 = 45;
                int BoundaryX2 = -33;
                int BoundaryY1 = 104;
                int BoundaryY2 = 63;
                int BoundaryZ1 = 1525;
                int BoundaryZ2 = 1418;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了狂风。"));
                }
                for(int i=0;i<12;i++)
                {
                    Random r = new Random();
                    int tmpNum = r.nextInt(6);
                    if(list0.size() != 0 && (Utils.KazeSkeleton[i] == null || !Utils.KazeSkeleton[i].isAlive()))
                    {
                        if(Utils.KazeSkeleton[i] != null) Utils.KazeSkeleton[i].remove(Entity.RemovalReason.KILLED);
                        Utils.KazeSkeleton[i] = new Skeleton(EntityType.SKELETON,level);
                        Compute.SetMobCustomName(Utils.KazeSkeleton[i], ModItems.ArmorKazeHelmet.get(),Component.literal("狂风").withStyle(CustomStyle.styleOfKaze));
                        Utils.KazeSkeleton[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorKazeHelmet.get().getDefaultInstance());
                        Utils.KazeSkeleton[i].setItemSlot(EquipmentSlot.CHEST , ModItems.ArmorKazeChest.get().getDefaultInstance());
                        Utils.KazeSkeleton[i].setItemSlot(EquipmentSlot.LEGS , ModItems.ArmorKazeLeggings.get().getDefaultInstance());
                        Utils.KazeSkeleton[i].setItemSlot(EquipmentSlot.FEET , ModItems.ArmorKazeBoots.get().getDefaultInstance());
                        Utils.KazeSkeleton[i].setItemSlot(EquipmentSlot.MAINHAND , ModItems.KazeSword3.get().getDefaultInstance());
                        Utils.KazeSkeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(5000);
                        Utils.KazeSkeleton[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100.0D);
                        Utils.KazeSkeleton[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                        Utils.KazeSkeleton[i].setHealth(5000);

                        Utils.KazeSkeleton[i].moveTo(indexX[tmpNum] + r.nextDouble(3),indexY[tmpNum],indexZ[tmpNum] + r.nextDouble(3));
                        level.addFreshEntity(Utils.KazeSkeleton[i]);
                    }
                    if(Utils.KazeSkeleton[i] != null){
                        double x = Utils.KazeSkeleton[i].getX();
                        double y = Utils.KazeSkeleton[i].getY();
                        double z = Utils.KazeSkeleton[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.KazeSkeleton[i].moveTo(indexX[tmpNum] + r.nextDouble(3),indexY[tmpNum],indexZ[tmpNum] + r.nextDouble(3));
                        }
                    }
                }
            }
        }
    }
}
