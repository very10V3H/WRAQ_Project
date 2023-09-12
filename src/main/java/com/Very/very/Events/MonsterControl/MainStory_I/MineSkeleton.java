package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
@Mod.EventBusSubscriber
public class MineSkeleton {
    @SubscribeEvent
    public static void MineSkeleton(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 100 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {63,56,73,90,107,98};
                int [] indexY = {0,1,-1,-6,-7,-11};
                int [] indexZ = {912,934,923,916,926,934};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 126;
                int BoundaryX2 = 42;
                int BoundaryY1 = 11;
                int BoundaryY2 = -15;
                int BoundaryZ1 = 957;
                int BoundaryZ2 = 896;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了矿洞骷髅。"));
                }
                for(int i=0;i<6;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.MineSkeleton[i] == null || !Utils.MineSkeleton[i].isAlive()))
                    {
                        if(Utils.MineSkeleton[i] != null) Utils.MineSkeleton[i].remove(Entity.RemovalReason.KILLED);
                        Utils.MineSkeleton[i] = new Skeleton(EntityType.SKELETON,level);
                        Compute.SetMobCustomName(Utils.MineSkeleton[i],Moditems.ArmorMine.get(),Component.literal("矿洞骷髅").withStyle(ChatFormatting.GRAY));
                        Utils.MineSkeleton[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorMine.get().getDefaultInstance());
                        Utils.MineSkeleton[i].setItemSlot(EquipmentSlot.MAINHAND , Items.DIAMOND_PICKAXE .getDefaultInstance());
                        Utils.MineSkeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(500.0D);
                        Utils.MineSkeleton[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35D);
                        Utils.MineSkeleton[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25D);
                        Utils.MineSkeleton[i].setHealth(Utils.MineSkeleton[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        int X = indexX[tmpnum] +r.nextInt(6);
                        int Y = indexY[tmpnum];
                        int Z = indexZ[tmpnum]+r.nextInt(6);
                        while(!level.getBlockState(new BlockPos(X,Y,Z)).getBlock().equals(Blocks.AIR)){
                            X = indexX[tmpnum] +r.nextInt(6);
                            Y = indexY[tmpnum];
                            Z = indexZ[tmpnum]+r.nextInt(6);
                        }
                        Utils.MineSkeleton[i].moveTo(X,Y,Z);
                        level.addFreshEntity(Utils.MineSkeleton[i]);
                    }
                    if(Utils.MineSkeleton[i] != null){
                        double x = Utils.MineSkeleton[i].getX();
                        double y = Utils.MineSkeleton[i].getY();
                        double z = Utils.MineSkeleton[i].getZ();
                        if(x > 126 || x < 42 || y > 11 || y < -15 || z > 957 || z < 896)
                        {
                            int tmpnum = r.nextInt(6);
                            Utils.MineSkeleton[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
        }
    }
}
