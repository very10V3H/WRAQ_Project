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
import net.minecraft.world.entity.monster.Skeleton;
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
public class NetherSkeletonEvent {
    @SubscribeEvent
    public static void ZombiePigLin(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.NETHER);
            if(level.getServer().getTickCount() % 400 == 340 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {-47,-64,-81,-79,-92};
                int [] indexY = {43,44,41,42,39};
                int [] indexZ = {222,234,216,191,220};
                int BoundaryX1 = -37;
                int BoundaryX2 = -102;
                int BoundaryY1 = 48;
                int BoundaryY2 = 36;
                int BoundaryZ1 = 244;
                int BoundaryZ2 = 181;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了下界遗骸。"));
                }
                for(int i=0;i<5;i++)
                {
                    if(list0.size() != 0 && (Utils.NetherSkeleton[i] == null || !Utils.NetherSkeleton[i].isAlive()))
                    {
                        if(Utils.NetherSkeleton[i] != null) Utils.NetherSkeleton[i].remove(Entity.RemovalReason.KILLED);
                        Utils.NetherSkeleton[i] = new Skeleton(EntityType.SKELETON,level);
                        Compute.SetMobCustomName(Utils.NetherSkeleton[i],Moditems.ArmorNetherSkeletonHelmet.get(),Component.literal("下界遗骸").withStyle(ChatFormatting.RED));
                        Utils.NetherSkeleton[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorNetherSkeletonHelmet.get().getDefaultInstance());
                        Utils.NetherSkeleton[i].setItemSlot(EquipmentSlot.CHEST , Moditems.ArmorNetherSkeletonChest.get().getDefaultInstance());
                        Utils.NetherSkeleton[i].setItemSlot(EquipmentSlot.LEGS , Moditems.ArmorNetherSkeletonLeggings.get().getDefaultInstance());
                        Utils.NetherSkeleton[i].setItemSlot(EquipmentSlot.FEET , Moditems.ArmorNetherSkeletonBoots.get().getDefaultInstance());
                        Utils.NetherSkeleton[i].setItemSlot(EquipmentSlot.MAINHAND , Items.BOW.getDefaultInstance());
                        Utils.NetherSkeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2500.0D);
                        Utils.NetherSkeleton[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.NetherSkeleton[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(210);
                        Utils.NetherSkeleton[i].setHealth(Utils.NetherSkeleton[i].getMaxHealth());
                        Utils.NetherSkeleton[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.NetherSkeleton[i]);
                    }
                    if(Utils.NetherSkeleton[i] != null){
                        double x = Utils.NetherSkeleton[i].getX();
                        double y = Utils.NetherSkeleton[i].getY();
                        double z = Utils.NetherSkeleton[i].getZ();
                        if(x > -37 || x < -102 || y > 48 || y < 36 || z > 244 || z < 181)
                        {
                            Utils.NetherSkeleton[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
        }

    }
}
