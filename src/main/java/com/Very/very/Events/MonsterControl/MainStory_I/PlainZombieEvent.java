package com.Very.very.Events.MonsterControl.MainStory_I;

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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class PlainZombieEvent {
    @SubscribeEvent
    public static void PlainZombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.phase.equals(TickEvent.Phase.START))
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.getServer().getTickCount() % 400 == 0 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount()) {
                int [] indexX = {245,238,243,236,231,239,238,238,269,275,269,291,301,281};
                int [] indexY = {69,74,83,94,104,109,112,114,67,70,73,64,63,63};
                int [] indexZ = {1037,1056,1081,1108,1137,1160,1182,1194,1069,1093,1118,1099,1071,1047};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 320;
                int BoundaryX2 = 200;
                int BoundaryY1 = 136;
                int BoundaryY2 = 60;
                int BoundaryZ1 = 1210;
                int BoundaryZ2 = 1000;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 40) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了平原僵尸。"));
                }
                for(int i=0;i<20;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.PlainZombie[i] == null || !Utils.PlainZombie[i].isAlive()))
                    {
                        if(Utils.PlainZombie[i] != null) Utils.PlainZombie[i].remove(Entity.RemovalReason.KILLED);
                        Utils.PlainZombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Compute.SetMobCustomName(Utils.PlainZombie[i],Moditems.ArmorPlain.get(),Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN));
                        Utils.PlainZombie[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorPlain.get().getDefaultInstance());
                        Utils.PlainZombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.WOODEN_SWORD.getDefaultInstance());
                        Utils.PlainZombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(25.0D);
                        Utils.PlainZombie[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
                        Utils.PlainZombie[i].setHealth(Utils.PlainZombie[i].getMaxHealth());
                        int tmpnum = r.nextInt(14);
                        Utils.PlainZombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.PlainZombie[i]);
                    }
                    if(Utils.PlainZombie[i] != null){
                        double x = Utils.PlainZombie[i].getX();
                        double y = Utils.PlainZombie[i].getY();
                        double z = Utils.PlainZombie[i].getZ();
                        if(x > 320 || x < 200 || y > 136 || y < 60 || z > 1210 || z < 1000)
                        {
                            int tmpnum = r.nextInt(14);
                            Utils.PlainZombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
            if (level.getServer().getTickCount() % 100 == 0 && level.equals(level1)) {
                for (Zombie zombie : Utils.PlainZombie) {
                    if (zombie != null) {
                        zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,1));
                        zombie.addEffect(new MobEffectInstance(MobEffects.REGENERATION,20,1));
                    }
                }
            }
        }
    }
}
