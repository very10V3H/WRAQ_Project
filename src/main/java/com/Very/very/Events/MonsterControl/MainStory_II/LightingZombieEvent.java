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
public class LightingZombieEvent {
    @SubscribeEvent
    public static void plainzombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 300 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {1449,1448,1418,1393,1399,1402};
                int [] indexY = {66, 72, 70, 70, 73, 71};
                int [] indexZ = {541,581,595,585,560,536};
                Utils.SummonTick = level.getServer().getTickCount();
                int BoundaryX1 = 1470;
                int BoundaryX2 = 1379;
                int BoundaryY1 = 80;
                int BoundaryY2 = 59;
                int BoundaryZ1 = 615;
                int BoundaryZ2 = 516;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了唤雷塔守卫。"));
                }
                for(int i=0;i<6;i++)
                {
                    Random r = new Random();
                    if(list0.size() != 0 && (Utils.LightingZombie[i] == null || !Utils.LightingZombie[i].isAlive()))
                    {
                        if(Utils.LightingZombie[i] != null) Utils.LightingZombie[i].remove(Entity.RemovalReason.KILLED);
                        Utils.LightingZombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Compute.SetMobCustomName(Utils.LightingZombie[i],Moditems.ArmorLZHelmet.get(),Component.literal("唤雷塔守卫").withStyle(ChatFormatting.AQUA));
                        Utils.LightingZombie[i].setItemSlot(EquipmentSlot.HEAD ,  Compute.FoilAddItemStack( Moditems.ArmorLZHelmet.get().getDefaultInstance()));
                        Utils.LightingZombie[i].setItemSlot(EquipmentSlot.CHEST ,  Compute.FoilAddItemStack( Moditems.ArmorLZChest.get().getDefaultInstance()));
                        Utils.LightingZombie[i].setItemSlot(EquipmentSlot.LEGS ,  Compute.FoilAddItemStack( Moditems.ArmorLZLeggings.get().getDefaultInstance()));
                        Utils.LightingZombie[i].setItemSlot(EquipmentSlot.FEET ,  Compute.FoilAddItemStack( Moditems.ArmorLZBoots.get().getDefaultInstance()));
                        Utils.LightingZombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                        Utils.LightingZombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2500.0D);
                        Utils.LightingZombie[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60.0D);
                        Utils.LightingZombie[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                        Utils.LightingZombie[i].setHealth(Utils.LightingZombie[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.LightingZombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.LightingZombie[i]);
                    }
                    if(Utils.LightingZombie[i] != null){
                        double x = Utils.LightingZombie[i].getX();
                        double y = Utils.LightingZombie[i].getY();
                        double z = Utils.LightingZombie[i].getZ();
                        if(x > 1470 || x < 1379 || y > 80 || y < 59 || z > 615 || z < 516)
                        {
                            int tmpnum = r.nextInt(6);
                            Utils.LightingZombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        }
                    }
                }
            }
        }
    }
}
