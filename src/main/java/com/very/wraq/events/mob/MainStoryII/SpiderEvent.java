package com.very.wraq.events.mob.MainStoryII;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
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
public class SpiderEvent {
    @SubscribeEvent
    public static void Spider(TickEvent.LevelTickEvent event) {
        if(event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Spider spider : Utils.Spider) {
                if (spider != null && spider.isAlive()) Element.ElementProvider(spider, Element.Life, 1);
            }
            if(level.getServer().getTickCount() % 400 == 40 && level.equals(level1)
                    && Utils.SummonTick0 != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START) {
                int [] indexX = {-128,-113,-139,-125,-117,-136};
                int [] indexY = {122, 126, 123, 124, 128, 126};
                int [] indexZ = {1229,1217,1226,1219,1204,1206};
                Utils.SummonTick0 = level.getServer().getTickCount();
                int BoundaryX1 = -93;
                int BoundaryX2 = -159;
                int BoundaryY1 = 138;
                int BoundaryY2 = 112;
                int BoundaryZ1 = 1249;
                int BoundaryZ2 = 1184;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了微光蜘蛛。"));
                }
                for(int i=0;i<12;i++)
                {
                    Random r = new Random();
                    int tmpNum = r.nextInt(6);
                    if(list0.size() != 0 && (Utils.Spider[i] == null || !Utils.Spider[i].isAlive()))
                    {
                        if(Utils.Spider[i] != null) Utils.Spider[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Spider[i] = new Spider(EntityType.SPIDER,level);
                        Compute.SetMobCustomName(Utils.Spider[i], ModItems.ArmorSpider.get(),Component.literal("微光蜘蛛").withStyle(CustomStyle.styleOfSpider));
                        Utils.Spider[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSpider.get().getDefaultInstance());
                        Utils.Spider[i].setItemSlot(EquipmentSlot.MAINHAND , ModItems.KazeSword3.get().getDefaultInstance());
                        Utils.Spider[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(4800);
                        Utils.Spider[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(84.0D);
                        Utils.Spider[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.Spider[i].setHealth(4800);
                        Utils.Spider[i].moveTo(indexX[tmpNum] + r.nextInt(3),indexY[tmpNum],indexZ[tmpNum] + r.nextInt(3));
                        level.addFreshEntity(Utils.Spider[i]);
                    }
                    if(Utils.Spider[i] != null){
                        double x = Utils.Spider[i].getX();
                        double y = Utils.Spider[i].getY();
                        double z = Utils.Spider[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.Spider[i].moveTo(indexX[tmpNum] + r.nextInt(3),indexY[tmpNum],indexZ[tmpNum] + r.nextInt(3));
                        }
                    }
                }
            }
        }
    }
}
