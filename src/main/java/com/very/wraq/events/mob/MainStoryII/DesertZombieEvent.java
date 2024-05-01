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
public class DesertZombieEvent {
    @SubscribeEvent
    public static void Husk(TickEvent.LevelTickEvent event) {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Zombie zombie : Utils.DesertZombie) {
                if (zombie != null && zombie.isAlive()) Element.ElementProvider(zombie, Element.Stone, 1);
            }
            if(level.getServer().getTickCount() % 300 == 220 && level.equals(level1)) {
                int [] indexX = {1131,1112,1083,1053,1212,1214};
                int [] indexY = {65, 64, 66, 70, 71, 63};
                int [] indexZ = {639,663,618,631,591,555};
                int BoundaryX1 = 1244;
                int BoundaryX2 = 1023;
                int BoundaryY1 = 78;
                int BoundaryY2 = 58;
                int BoundaryZ1 = 700;
                int BoundaryZ2 = 505;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了脆弱的灵魂。"));
                }
                for(int i=0;i<10;i++)
                {
                    Random r = new Random();
                    int tmpNum = r.nextInt(6);
                    if(list0.size() != 0 && (Utils.DesertZombie[i] == null || !Utils.DesertZombie[i].isAlive()))
                    {
                        if(Utils.DesertZombie[i] != null) Utils.DesertZombie[i].remove(Entity.RemovalReason.KILLED);
                        Utils.DesertZombie[i] = new Zombie(EntityType.HUSK,level);
                        Compute.SetMobCustomName(Utils.DesertZombie[i], ModItems.ArmorHusk.get(),Component.literal("脆弱的灵魂").withStyle(CustomStyle.styleOfBlackForest));
                        Utils.DesertZombie[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorHusk.get().getDefaultInstance());
                        Utils.DesertZombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                        Utils.DesertZombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2500.0D);
                        Utils.DesertZombie[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60.0D);
                        Utils.DesertZombie[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                        Utils.DesertZombie[i].setHealth(2500);
                        Utils.DesertZombie[i].moveTo(indexX[tmpNum]+r.nextInt(6),indexY[tmpNum],indexZ[tmpNum]+r.nextInt(6));
                        level.addFreshEntity(Utils.DesertZombie[i]);
                    }
                    if(Utils.DesertZombie[i] != null){
                        double x = Utils.DesertZombie[i].getX();
                        double y = Utils.DesertZombie[i].getY();
                        double z = Utils.DesertZombie[i].getZ();
                        if(x > 1244 || x < 1023 || y > 78 || y < 58 || z > 700 || z < 505) {
                            Utils.DesertZombie[i].moveTo(indexX[tmpNum]+r.nextInt(6),indexY[tmpNum],indexZ[tmpNum]+r.nextInt(6));
                        }
                    }
                }
            }
        }
    }
}
