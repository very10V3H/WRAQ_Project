package com.very.wraq.events.mob.MainStoryII.Sky;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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
    public static void vex(TickEvent.LevelTickEvent event) {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Vex vex : Utils.vex) {
                if (vex != null && vex.isAlive()) Element.ElementProvider(vex, Element.Wind, 1);
            }
            if(level.getServer().getTickCount() % 300 == 180 && level.equals(level1)) {
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 49;
                int BoundaryX2 = 6;
                int BoundaryY1 = 200;
                int BoundaryY2 = 159;
                int BoundaryZ1 = 1040;
                int BoundaryZ2 = 998;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了天空城的不速之客。"));
                }
                for(int i=0;i<10;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.vex[i] == null || !Utils.vex[i].isAlive()))
                    {
                        if(Utils.vex[i] != null) Utils.vex[i].remove(Entity.RemovalReason.KILLED);
                        Utils.vex[i] = new Vex(EntityType.VEX,level);
                        Compute.SetMobCustomName(Utils.vex[i], ModItems.ArmorSky.get(),Component.literal("天空城的不速之客").withStyle(ChatFormatting.AQUA));
                        Utils.vex[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSky.get().getDefaultInstance());
                        Utils.vex[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                        Utils.vex[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(800D);
                        Utils.vex[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100D);

                        Utils.vex[i].setHealth(Utils.vex[i].getMaxHealth());
                        Utils.vex[i].addEffect(new MobEffectInstance(MobEffects.GLOWING,88888,1,false,false));
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
