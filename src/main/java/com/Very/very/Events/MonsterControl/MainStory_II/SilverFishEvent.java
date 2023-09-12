package com.Very.very.Events.MonsterControl.MainStory_II;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
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
public class SilverFishEvent {
    @SubscribeEvent
    public static void SilverFish(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 60 && level.equals(level1)
                    && Utils.SummonTick0 != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {-95,-87,-78,-69,-68};
                int [] indexY = {41, 37, 38, 36, 51};
                int [] indexZ = {1596,1596,1587,1602,1589};
                Utils.SummonTick0 = level.getServer().getTickCount();
                int BoundaryX1 = -48;
                int BoundaryX2 = -115;
                int BoundaryY1 = 71;
                int BoundaryY2 = 16;
                int BoundaryZ1 = 1622;
                int BoundaryZ2 = 1569;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了维度银鱼。"));
                }
                for(int i=0;i<5;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.SilverFish[i] == null || !Utils.SilverFish[i].isAlive()))
                    {
                        if(Utils.SilverFish[i] != null) Utils.SilverFish[i].remove(Entity.RemovalReason.KILLED);
                        Utils.SilverFish[i] = new Silverfish(EntityType.SILVERFISH,level);
                        Compute.SetMobCustomName(Utils.SilverFish[i],Moditems.ArmorSilverFish.get(),Component.literal("维度银鱼").withStyle(ChatFormatting.GRAY));
                        Utils.SilverFish[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSilverFish.get().getDefaultInstance());
                        Utils.SilverFish[i].setItemSlot(EquipmentSlot.MAINHAND , Moditems.KazeSword3.get().getDefaultInstance());
                        Utils.SilverFish[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(9600.0D);
                        Utils.SilverFish[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(168.0D);
                        Utils.SilverFish[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.SilverFish[i].setHealth(Utils.SilverFish[i].getMaxHealth());
                        Utils.SilverFish[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.SilverFish[i]);
                    }
                    if(Utils.SilverFish[i] != null){
                        double x = Utils.SilverFish[i].getX();
                        double y = Utils.SilverFish[i].getY();
                        double z = Utils.SilverFish[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.SilverFish[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                for (int i = 0; i < 5; i++) {
                    if (Utils.SilverFish[i] != null && Utils.SilverFish[i].isAlive() && level.getServer().getTickCount() % 5 == 0) {
                        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(Utils.SilverFish[i].getPosition(1.0f),5,5,5));
                        for (Player player : playerList) {
                            if (player.getPosition(1.0f).distanceTo(Utils.SilverFish[i].getPosition(1.0f)) <= 2.8) {
                                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20,3));
                                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,3));
                            }
                        }
                    }
                }
            }
        }
    }
}
