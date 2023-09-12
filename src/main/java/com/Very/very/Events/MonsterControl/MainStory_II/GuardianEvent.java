package com.Very.very.Events.MonsterControl.MainStory_II;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
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
public class GuardianEvent {
    @SubscribeEvent
    public static void plainzombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 280 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {671,709,710,664,673,699,688};
                int [] indexY = {54, 54, 53, 57, 50, 59,57};
                int [] indexZ = {207,208,245,247,223,225,234};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 723;
                int BoundaryX2 = 650;
                int BoundaryY1 = 62;
                int BoundaryY2 = 37;
                int BoundaryZ1 = 260;
                int BoundaryZ2 = 188;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了神殿守卫。"));
                }
                for(int i=0;i<7;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.Guardians[i] == null || !Utils.Guardians[i].isAlive()))
                    {
                        if(Utils.Guardians[i] != null) Utils.Guardians[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Guardians[i] = new Guardian(EntityType.GUARDIAN,level);
                        Compute.SetMobCustomName(Utils.Guardians[i],Moditems.ArmorGuardian.get(),Component.literal("神殿守卫").withStyle(ChatFormatting.AQUA));
                        Utils.Guardians[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorGuardian.get().getDefaultInstance());
                        Utils.Guardians[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1200.0D);
                        Utils.Guardians[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30.0D);
                        Utils.Guardians[i].setHealth(Utils.Guardians[i].getMaxHealth());
                        int tmpnum = r.nextInt(7);
                        Utils.Guardians[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.Guardians[i]);
                    }
                    if(Utils.Guardians[i] != null){
                        double x = Utils.Guardians[i].getX();
                        double y = Utils.Guardians[i].getY();
                        double z = Utils.Guardians[i].getZ();
                        if(x > 723 || x < 650 || y > 62 || y < 37 || z > 260 || z < 188)
                        {
                            int tmpnum = r.nextInt(7);
                            Utils.Guardians[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
        }
    }
}
