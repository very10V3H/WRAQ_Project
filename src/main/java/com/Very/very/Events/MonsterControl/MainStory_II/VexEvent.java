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
import net.minecraft.world.entity.monster.Vex;
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
public class VexEvent {
    @SubscribeEvent
    public static void vex(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 180 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 49;
                int BoundaryX2 = 6;
                int BoundaryY1 = 200;
                int BoundaryY2 = 159;
                int BoundaryZ1 = 1040;
                int BoundaryZ2 = 998;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了天空城的不速之客。"));
                }
                for(int i=0;i<6;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.vex[i] == null || !Utils.vex[i].isAlive()))
                    {
                        if(Utils.vex[i] != null) Utils.vex[i].remove(Entity.RemovalReason.KILLED);
                        Utils.vex[i] = new Vex(EntityType.VEX,level);
                        Compute.SetMobCustomName(Utils.vex[i],Moditems.ArmorSky.get(),Component.literal("天空城的不速之客").withStyle(ChatFormatting.AQUA));
                        Utils.vex[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSky.get().getDefaultInstance());
                        Utils.vex[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                        Utils.vex[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(200D);
                        Utils.vex[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60D);
                        Utils.vex[i].setHealth(Utils.vex[i].getMaxHealth());
                        Utils.vex[i].addEffect(new MobEffectInstance(MobEffects.GLOWING,88888));
                        Utils.vex[i].moveTo(23+r.nextInt(10) , 175+r.nextInt(10) , 1003+r.nextInt(10));
                        level.addFreshEntity(Utils.vex[i]);
                    }
                    if(Utils.vex[i] != null){
                        double x = Utils.vex[i].getX();
                        double y = Utils.vex[i].getY();
                        double z = Utils.vex[i].getZ();
                        if(x > 49 || x < 6 || y > 200 || y < 159 || z > 1040 || z < 998)
                        {
                            Utils.vex[i].moveTo(23+r.nextInt(10) , 175+r.nextInt(10) , 1003+r.nextInt(10));
                        }
                    }
                }
            }
        }
    }
}
