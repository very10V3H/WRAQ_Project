package com.Very.very.Events.MonsterControl.MainStory_III;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class WitherSkeletonEvent {
    @SubscribeEvent
    public static void WitherSkeleton(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.NETHER);
            if(level.getServer().getTickCount() % 400 == 220 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {89,88,88,107,107};
                int [] indexY = {67,67,67,67,67};
                int [] indexZ = {235,208,262,235,254};
                int BoundaryX1 = 132;
                int BoundaryX2 = 55;
                int BoundaryY1 = 80;
                int BoundaryY2 = 60;
                int BoundaryZ1 = 270;
                int BoundaryZ2 = 200;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了下界凋零骷髅。"));
                }
                for(int i=0;i<5;i++)
                {
                    if(list0.size() != 0 && (Utils.WitherSkeleton[i] == null || !Utils.WitherSkeleton[i].isAlive()))
                    {
                        if(Utils.WitherSkeleton[i] != null) Utils.WitherSkeleton[i].remove(Entity.RemovalReason.KILLED);
                        Utils.WitherSkeleton[i] = new WitherSkeleton(EntityType.WITHER_SKELETON,level);
                        Compute.SetMobCustomName(Utils.WitherSkeleton[i],Moditems.ArmorWitherSkeleton.get(),Component.literal("下界凋零骷髅").withStyle(ChatFormatting.RED));
                        Utils.WitherSkeleton[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorWitherSkeleton.get().getDefaultInstance());
                        Utils.WitherSkeleton[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                        Utils.WitherSkeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2000.0D);
                        Utils.WitherSkeleton[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.WitherSkeleton[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(75);
                        Utils.WitherSkeleton[i].setHealth(Utils.WitherSkeleton[i].getMaxHealth());
                        Utils.WitherSkeleton[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.WitherSkeleton[i]);
                    }
                    if(Utils.WitherSkeleton[i] != null){
                        double x = Utils.WitherSkeleton[i].getX();
                        double y = Utils.WitherSkeleton[i].getY();
                        double z = Utils.WitherSkeleton[i].getZ();
                        if(x > 132 || x < 55 || y > 80 || y < 60 || z > 270 || z < 200)
                        {
                            Utils.WitherSkeleton[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
        }

    }
}
