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
import net.minecraft.world.entity.monster.Zombie;
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
public class ForestZombieEvent {
    @SubscribeEvent
    public static void ForestZombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 60 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {99,82,65,33,20,3};
                int [] indexY = {71,73,74,69,67,74};
                int [] indexZ = {948,934,944,914,934,918};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 126;
                int BoundaryX2 = -15;
                int BoundaryY1 = 90;
                int BoundaryY2 = 59;
                int BoundaryZ1 = 979;
                int BoundaryZ2 = 907;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了森林僵尸。"));
                }
                for(int i=0;i<8;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.ForestZombie[i] == null || !Utils.ForestZombie[i].isAlive()))
                    {
                        if(Utils.ForestZombie[i] != null) Utils.ForestZombie[i].remove(Entity.RemovalReason.KILLED);
                        Utils.ForestZombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Compute.SetMobCustomName(Utils.ForestZombie[i],Moditems.ArmorForestZombie.get(),Component.literal("森林僵尸").withStyle(ChatFormatting.DARK_GREEN));
                        Utils.ForestZombie[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorForestZombie.get().getDefaultInstance());
                        Utils.ForestZombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD .getDefaultInstance());
                        Utils.ForestZombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D);
                        Utils.ForestZombie[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20.0D);
                        Utils.ForestZombie[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.ForestZombie[i].setHealth(Utils.ForestZombie[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        int X = indexX[tmpnum] +r.nextInt(6);
                        int Y = indexY[tmpnum];
                        int Z = indexZ[tmpnum]+r.nextInt(6);
                        while(!level.getBlockState(new BlockPos(X,Y,Z)).getBlock().equals(Blocks.AIR)){
                            X = indexX[tmpnum] +r.nextInt(6);
                            Y = indexY[tmpnum];
                            Z = indexZ[tmpnum]+r.nextInt(6);
                        }
                        Utils.ForestZombie[i].moveTo(X,Y,Z);
                        level.addFreshEntity(Utils.ForestZombie[i]);
                    }
                    if(Utils.ForestZombie[i] != null){
                        double x = Utils.ForestZombie[i].getX();
                        double y = Utils.ForestZombie[i].getY();
                        double z = Utils.ForestZombie[i].getZ();
                        if(x > 126 || x < -15 || y > 90 || y < 59 || z > 979 || z < 907)
                        {
                            int tmpnum = r.nextInt(6);
                            Utils.ForestZombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
        }
    }
}
