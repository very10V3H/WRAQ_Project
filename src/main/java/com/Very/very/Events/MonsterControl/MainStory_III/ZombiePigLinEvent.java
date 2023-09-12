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
import net.minecraft.world.entity.monster.piglin.Piglin;
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
public class ZombiePigLinEvent {
    @SubscribeEvent
    public static void ZombiePigLin(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.NETHER);
            if(level.getServer().getTickCount() % 400 == 260 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {1,-10,-18,-6,-12};
                int [] indexY = {47,47,47,47,48};
                int [] indexZ = {241,252,252,268,276};
                int BoundaryX1 = 11;
                int BoundaryX2 = -22;
                int BoundaryY1 = 53;
                int BoundaryY2 = 38;
                int BoundaryZ1 = 286;
                int BoundaryZ2 = 231;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));
                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了下界猪灵。"));
                }
                for(int i=0;i<5;i++)
                {
                    if(list0.size() != 0 && (Utils.ZombiePiglin[i] == null || !Utils.ZombiePiglin[i].isAlive()))
                    {
                        if(Utils.ZombiePiglin[i] != null) Utils.ZombiePiglin[i].remove(Entity.RemovalReason.KILLED);
                        Utils.ZombiePiglin[i] = new Piglin(EntityType.PIGLIN,level);
                        Compute.SetMobCustomName(Utils.ZombiePiglin[i],Moditems.ArmorZombiePiglin.get(),Component.literal("下界猪灵").withStyle(ChatFormatting.RED));
                        Utils.ZombiePiglin[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorZombiePiglin.get().getDefaultInstance());
                        Utils.ZombiePiglin[i].setItemSlot(EquipmentSlot.MAINHAND , Items.GOLDEN_SWORD.getDefaultInstance());
                        Utils.ZombiePiglin[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2500.0D);
                        Utils.ZombiePiglin[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.ZombiePiglin[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(90);
                        Utils.ZombiePiglin[i].setHealth(Utils.ZombiePiglin[i].getMaxHealth());
                        Utils.ZombiePiglin[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.ZombiePiglin[i]);
                    }
                    if(Utils.ZombiePiglin[i] != null){
                        double x = Utils.ZombiePiglin[i].getX();
                        double y = Utils.ZombiePiglin[i].getY();
                        double z = Utils.ZombiePiglin[i].getZ();
                        if(x > 31 || x < -42 || y > 53 || y < 38 || z > 296 || z < 221)
                        {
                            Utils.ZombiePiglin[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
        }

    }
}
