package com.very.wraq.events.mob.MainStoryV;

import com.very.wraq.entities.entities.SakuraMob.SakuraMob;
import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
public class SakuraMobEvent {
    @SubscribeEvent
    public static void SakuraMob(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (SakuraMob sakuraMob : Utils.SakuraMob) {
                if (sakuraMob != null && sakuraMob.isAlive()) Element.ElementProvider(sakuraMob, Element.Life, 1.5);
            }
            if (level.getServer().getTickCount() % 400 == 0 && level.equals(level1)) {
                int [] indexX = {1800,1813,1791,1814,1789};
                int [] indexY = {92,88,84,79,78};
                int [] indexZ = {1320,1357,1374,1394,1418};
                int BoundaryX1 = 1864;
                int BoundaryX2 = 1741;
                int BoundaryY1 = 112;
                int BoundaryY2 = 58;
                int BoundaryZ1 = 1468;
                int BoundaryZ2 = 1270;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了樱灵"));
                }
                for (int i=0;i<10;i++) {
                    Random random = new Random();
                    int Index = random.nextInt(5);
                    if (list0.size() != 0 && (Utils.SakuraMob[i] == null || !Utils.SakuraMob[i].isAlive())) {
                        if(Utils.SakuraMob[i] != null) Utils.SakuraMob[i].remove(Entity.RemovalReason.KILLED);
                        Utils.SakuraMob[i] = new SakuraMob(ModEntityType.SakuraMob.get(), level);
                        Compute.SetMobCustomName(Utils.SakuraMob[i], ModItems.ArmorSakuraMob.get(),Component.literal("樱灵").withStyle(CustomStyle.styleOfSakura));
                        Utils.SakuraMob[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSakuraMob.get().getDefaultInstance());
                        Utils.SakuraMob[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(100000);
                        Utils.SakuraMob[i].setHealth(100000);
                        Utils.SakuraMob[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
                        Utils.SakuraMob[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60);

                        Utils.SakuraMob[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        level.addFreshEntity(Utils.SakuraMob[i]);
                    }
                    if (Utils.SakuraMob[i] != null) {
                        double x = Utils.SakuraMob[i].getX();
                        double y = Utils.SakuraMob[i].getY();
                        double z = Utils.SakuraMob[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.SakuraMob[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
