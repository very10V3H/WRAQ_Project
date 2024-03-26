package com.Very.very.Events.MonsterControl.MainStory_V;

import com.Very.very.Entity.Entities.Scarecrow.Scarecrow;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
public class ScarecrowEvent {
    @SubscribeEvent
    public static void Scarecrow(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 0 && level.equals(level1)) {
                int [] indexX = {1550,1550,1515,1511,1481};
                int [] indexY = {69,64,66,66,69};
                int [] indexZ = {1511,1478,1497,1465,1445};
                int BoundaryX1 = 1600;
                int BoundaryX2 = 1431;
                int BoundaryY1 = 89;
                int BoundaryY2 = 44;
                int BoundaryZ1 = 1561;
                int BoundaryZ2 = 1395;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 20) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了稻草人"));
                }
                for (int i=0;i<10;i++) {
                    Random random = new Random();
                    int Index = random.nextInt(5);
                    if (list0.size() != 0 && (Utils.Scarecrow[i] == null || !Utils.Scarecrow[i].isAlive())) {
                        if(Utils.Scarecrow[i] != null) Utils.Scarecrow[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Scarecrow[i] = new Scarecrow(ModEntityType.Scarecrow.get(), level);
                        Compute.SetMobCustomName(Utils.Scarecrow[i], ModItems.ArmorScarecrow.get(),Component.literal("稻草人").withStyle(Utils.styleOfWheat));
                        Utils.Scarecrow[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorScarecrow.get().getDefaultInstance());
                        Utils.Scarecrow[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(100000);
                        Utils.Scarecrow[i].setHealth(100000);
                        Utils.Scarecrow[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.Scarecrow[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100);

                        Utils.Scarecrow[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        level.addFreshEntity(Utils.Scarecrow[i]);
                    }
                    if (Utils.Scarecrow[i] != null){
                        double x = Utils.Scarecrow[i].getX();
                        double y = Utils.Scarecrow[i].getY();
                        double z = Utils.Scarecrow[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.Scarecrow[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
