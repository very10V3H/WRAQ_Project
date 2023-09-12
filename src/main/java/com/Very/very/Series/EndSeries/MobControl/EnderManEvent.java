package com.Very.very.Series.EndSeries.MobControl;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class EnderManEvent {
    @SubscribeEvent
    public static void EnderMan(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.END);
            if(level.getServer().getTickCount() % 400 == 80 && level.equals(level1)
                    && Utils.SummonTick0 != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick0 = level.getServer().getTickCount();
                int [] indexX = {-21,16,10,-22};
                int [] indexY = {63,58,59,63};
                int [] indexZ = {-16,-10,20,13};
                int BoundaryX1 = 36;
                int BoundaryX2 = -42;
                int BoundaryY1 = 83;
                int BoundaryY2 = 38;
                int BoundaryZ1 = 40;
                int BoundaryZ2 = -36;
                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

/*                if (list.size() > 40) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了终界使者。"));
                }*/
                for(int i=0;i<4;i++)
                {
                    if(list0.size() != 0 && (Utils.EnderMan[i] == null || !Utils.EnderMan[i].isAlive()))
                    {
                        if(Utils.EnderMan[i] != null) Utils.EnderMan[i].remove(Entity.RemovalReason.KILLED);
                        Utils.EnderMan[i] = new EnderMan(EntityType.ENDERMAN,level);
                        Utils.EnderMan[i].setCustomName(Component.literal("终界使者").withStyle(Utils.styleOfEnd));
                        Utils.EnderMan[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorEnderMan.get().getDefaultInstance());
                        Utils.EnderMan[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(19200.0D);
                        Utils.EnderMan[i].setHealth(Utils.EnderMan[i].getMaxHealth());
                        Utils.EnderMan[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.EnderMan[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200);
                        Utils.EnderMan[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        Utils.EnderMan[i].addEffect(new MobEffectInstance(MobEffects.GLOWING,10000,1));
                        level.addFreshEntity(Utils.EnderMan[i]);
                    }
                    if(Utils.EnderMan[i] != null){
                        double x = Utils.EnderMan[i].getX();
                        double y = Utils.EnderMan[i].getY();
                        double z = Utils.EnderMan[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2)
                        {
                            Utils.EnderMan[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                for (int i = 0; i < 4; i++) {
                    if (Utils.EnderMan[i] != null && Utils.EnderMan[i].isAlive() && level.getServer().getTickCount() % 5 == 0) {
                        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(Utils.EnderMan[i].getPosition(1.0f),5,5,5));
                        for (Player player : playerList) {
                            if (player.getPosition(1.0f).distanceTo(Utils.EnderMan[i].getPosition(1.0f)) <= 2.8) {
                                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,40,3));
                                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,3));
                                player.addEffect(new MobEffectInstance(MobEffects.WITHER,40,10));
                            }
                        }
                    }
                }
            }
        }

    }
}
