package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber
public class FieldZombieEvent {
    @SubscribeEvent
    public static void FiledZombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 300 == 140 && level.equals(level1)) {
                int [] indexX = {130,157,164,169,163,136,122,118};
                int [] indexY = {124,125,125,126,125,125,127,126};
                int [] indexZ = {1457,1452,1469,1486,1501,1502,1488,1460};
                int BoundaryX1 = 99;
                int BoundaryX2 = 195;
                int BoundaryY1 = 124;
                int BoundaryY2 = 142;
                int BoundaryZ1 = 1413;
                int BoundaryZ2 = 1520;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (double) (BoundaryX1 + BoundaryX2) /2, (double) (BoundaryY1 + BoundaryY2) /2, (double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (double) (BoundaryX1 + BoundaryX2) /2, (double) (BoundaryY1 + BoundaryY2) /2, (double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 30) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了原野僵尸。"));
                }
                Random random = new Random();
                for(int i=0;i<8;i++) {
                    if(list0.size() != 0 && (Utils.FieldZombie[i] == null || !Utils.FieldZombie[i].isAlive()))
                    {
                        if(Utils.FieldZombie[i] != null) Utils.FieldZombie[i].remove(Entity.RemovalReason.KILLED);
                        Utils.FieldZombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Compute.SetMobCustomName(Utils.FieldZombie[i], ModItems.ArmorField.get(),Component.literal("原野僵尸").withStyle(ChatFormatting.GOLD));
                        Utils.FieldZombie[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorField.get().getDefaultInstance());
                        Utils.FieldZombie[i].setItemSlot(EquipmentSlot.MAINHAND , ModItems.FieldSword0.get().getDefaultInstance());
                        Utils.FieldZombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1600.0D);
                        Utils.FieldZombie[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.FieldZombie[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(80);
                        Utils.FieldZombie[i].setHealth(Utils.FieldZombie[i].getMaxHealth());
                        Utils.FieldZombie[i].moveTo(indexX[i] + random.nextDouble(6) - 3,indexY[i],indexZ[i] + random.nextDouble(6) - 3);
                        level.addFreshEntity(Utils.FieldZombie[i]);
                    }
                    if(Utils.FieldZombie[i] != null){
                        double x = Utils.FieldZombie[i].getX();
                        double y = Utils.FieldZombie[i].getY();
                        double z = Utils.FieldZombie[i].getZ();
                        if(x < BoundaryX1 || x > BoundaryX2 || y < BoundaryY1 || y > BoundaryY2 || z < BoundaryZ1 || z > BoundaryZ2)
                        {
                            Utils.FieldZombie[i].moveTo(indexX[i] + random.nextDouble(6) - 3,indexY[i],indexZ[i] + random.nextDouble(6) - 3);
                        }
                    }
                }
            }
        }
    }
}
