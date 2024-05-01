package com.very.wraq.events.mob.MainStoryV;


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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
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
public class SlimeEvent {
    @SubscribeEvent
    public static void Slime(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Slime slime : Utils.Slime) {
                if (slime != null && slime.isAlive()) Element.ElementProvider(slime, Element.Life, 1.5);
            }
            if (level.getServer().getTickCount() % 400 == 275 && level.equals(level1)) {
                int [] indexX = {1150,1156,1145,1132,1139,1107,1110,1099};
                int [] indexY = {72,72,72,70,73,65,70,66};
                int [] indexZ = {675,686,686,679,699,687,703,698};
                int BoundaryX1 = 1176;
                int BoundaryX2 = 1081;
                int BoundaryY1 = 101;
                int BoundaryY2 = 62;
                int BoundaryZ1 = 721;
                int BoundaryZ2 = 694;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了莘岛史莱姆"));
                }
                for (int i = 0; i < 9; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(indexX.length);
                    if (list0.size() != 0 && (Utils.Slime[i] == null || !Utils.Slime[i].isAlive())) {
                        if(Utils.Slime[i] != null) Utils.Slime[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Slime[i] = new Slime(EntityType.SLIME, level);
                        Utils.Slime[i].setSize(1,true);
                        Utils.Slime[i].setBaby(true);
                        Compute.SetMobCustomName(Utils.Slime[i], ModItems.MobArmorSlime.get(),
                                Component.literal("莘岛史莱姆").withStyle(CustomStyle.styleOfHealth));
                        Utils.Slime[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorSlime.get().getDefaultInstance());
                        Utils.Slime[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1500000);
                        Utils.Slime[i].setHealth(Utils.Slime[i].getMaxHealth());
                        Utils.Slime[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.Slime[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000);
                        Utils.Slime[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        level.addFreshEntity(Utils.Slime[i]);
                    }
                    if (Utils.Slime[i] != null) {
                        double x = Utils.Slime[i].getX();
                        double y = Utils.Slime[i].getY();
                        double z = Utils.Slime[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.Slime[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
