package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
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
public class ForestSkeletonEvent {
    @SubscribeEvent
    public static void ForestSkeleton(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 20 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount()) {
                int [] indexX = {128,135,146,138,149,143,160,149};
                int [] indexY = {98,89,86,81,79,80,74,70};
                int [] indexZ = {1122,1109,1093,1077,1062,1044,1024,1001};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 176;
                int BoundaryX2 = 121;
                int BoundaryY1 = 128;
                int BoundaryY2 = 63;
                int BoundaryZ1 = 1128;
                int BoundaryZ2 = 977;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));
                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了森林骷髅。"));
                }
                for(int i=0;i<10;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.ForestSkeleton[i] == null || (!Utils.ForestSkeleton[i].isAlive())))
                    {
                        if(Utils.ForestSkeleton[i] != null) Utils.ForestSkeleton[i].remove(Entity.RemovalReason.KILLED);
                        Utils.ForestSkeleton[i] = new Skeleton(EntityType.SKELETON,level);
                        Compute.SetMobCustomName(Utils.ForestSkeleton[i],Moditems.ArmorForestSkeleton.get(),Component.literal( "森林骷髅").withStyle(ChatFormatting.DARK_GREEN));
                        Utils.ForestSkeleton[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorForestSkeleton.get().getDefaultInstance());
                        Utils.ForestSkeleton[i].setItemSlot(EquipmentSlot.MAINHAND , Items.BOW .getDefaultInstance());
                        Utils.ForestSkeleton[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20.0D);
                        Utils.ForestSkeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(100.0D);
                        Utils.ForestSkeleton[i].setHealth(Utils.ForestSkeleton[i].getMaxHealth());
                        int tmpnum = r.nextInt(8);
                        int X = indexX[tmpnum] +r.nextInt(6);
                        int Y = indexY[tmpnum];
                        int Z = indexZ[tmpnum]+r.nextInt(6);
                        while(!level.getBlockState(new BlockPos(X,Y,Z)).getBlock().equals(Blocks.AIR)){
                            X = indexX[tmpnum] +r.nextInt(6);
                            Y = indexY[tmpnum];
                            Z = indexZ[tmpnum]+r.nextInt(6);
                        }
                        Utils.ForestSkeleton[i].moveTo(X,Y,Z);
                        level.addFreshEntity(Utils.ForestSkeleton[i]);
                    }
                    if(Utils.ForestSkeleton[i] != null){
                        double x = Utils.ForestSkeleton[i].getX();
                        double y = Utils.ForestSkeleton[i].getY();
                        double z = Utils.ForestSkeleton[i].getZ();
                        if(x > 176 || x < 121 || y > 128 || y < 63 || z > 1128 || z < 977)
                        {
                            int tmpnum = r.nextInt(8);
                            Utils.ForestSkeleton[i].moveTo(indexX[tmpnum] +r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
            if (level.getServer().getTickCount() % 100 == 0 && level.equals(level1)) {
                for (Skeleton skeleton : Utils.ForestSkeleton) {
                    if (skeleton != null) {
                        skeleton.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,1));
                        skeleton.addEffect(new MobEffectInstance(MobEffects.REGENERATION,20,1));
                    }
                }
            }
        }
    }
}
