package com.very.wraq.events.mob.MainStoryI.Snow;

import com.very.wraq.process.element.Element;
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
import net.minecraft.world.entity.monster.Stray;
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
public class SnowStrayEvent {
    @SubscribeEvent
    public static void SnowStray(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Stray stray : Utils.SnowStray) {
                if (stray != null && stray.isAlive()) Element.ElementProvider(stray, Element.Ice, 1);
            }
            for (Stray stray : Utils.SnowStray1) {
                if (stray != null && stray.isAlive()) Element.ElementProvider(stray, Element.Ice, 1);
            }
            if(level.getServer().getTickCount() % 300 == 140 && level.equals(level1)) {
                int [] indexX = {-93,-95,-53,-39,-40,-55,-55,-66};
                int [] indexY = {171,187,168,161,159,196,191,194};
                int [] indexZ = {1350,1361,1368,1394,1478,1454,1423,1393};
                int BoundaryX1 = -182;
                int BoundaryX2 = 0;
                int BoundaryY1 = 144;
                int BoundaryY2 = 210;
                int BoundaryZ1 = 1291;
                int BoundaryZ2 = 1499;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (double) (BoundaryX1 + BoundaryX2) /2, (double) (BoundaryY1 + BoundaryY2) /2, (double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (double) (BoundaryX1 + BoundaryX2) /2, (double) (BoundaryY1 + BoundaryY2) /2, (double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 30) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了冰川流浪者。"));
                }
                Random random = new Random();
                for(int i=0;i<8;i++) {
                    if(list0.size() != 0 && (Utils.SnowStray[i] == null || !Utils.SnowStray[i].isAlive()))
                    {
                        if(Utils.SnowStray[i] != null) Utils.SnowStray[i].remove(Entity.RemovalReason.KILLED);
                        Utils.SnowStray[i] = new Stray(EntityType.STRAY,level);
                        Compute.SetMobCustomName(Utils.SnowStray[i], ModItems.ArmorSnow.get(),Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA));
                        Utils.SnowStray[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSnow.get().getDefaultInstance());
                        ItemStack itemStack = Items.BOW.getDefaultInstance();
                        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
                        map.put(Enchantments.POWER_ARROWS,8);
                        EnchantmentHelper.setEnchantments(map,itemStack);
                        Utils.SnowStray[i].setItemSlot(EquipmentSlot.MAINHAND , itemStack);
                        Utils.SnowStray[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(800.0D);
                        Utils.SnowStray[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.SnowStray[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(80);
                        Utils.SnowStray[i].setHealth(Utils.SnowStray[i].getMaxHealth());
                        Utils.SnowStray[i].moveTo(indexX[i] + random.nextDouble(6) - 3,indexY[i],indexZ[i] + random.nextDouble(6) - 3);
                        level.addFreshEntity(Utils.SnowStray[i]);
                    }
                    if(Utils.SnowStray[i] != null){
                        double x = Utils.SnowStray[i].getX();
                        double y = Utils.SnowStray[i].getY();
                        double z = Utils.SnowStray[i].getZ();
                        if(x < BoundaryX1 || x > BoundaryX2 || y < BoundaryY1 || y > BoundaryY2 || z < BoundaryZ1 || z > BoundaryZ2)
                        {
                            Utils.SnowStray[i].moveTo(indexX[i] + random.nextDouble(6) - 3,indexY[i],indexZ[i] + random.nextDouble(6) - 3);
                        }
                    }
                }
                for(int i=0;i<8;i++) {
                    if(list0.size() != 0 && (Utils.SnowStray1[i] == null || !Utils.SnowStray1[i].isAlive()))
                    {
                        if(Utils.SnowStray1[i] != null) Utils.SnowStray1[i].remove(Entity.RemovalReason.KILLED);
                        Utils.SnowStray1[i] = new Stray(EntityType.STRAY,level);
                        Compute.SetMobCustomName(Utils.SnowStray1[i], ModItems.ArmorSnow.get(),Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA));
                        Utils.SnowStray1[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSnow.get().getDefaultInstance());
                        Utils.SnowStray1[i].setItemSlot(EquipmentSlot.MAINHAND , ModItems.SnowSword3.get().getDefaultInstance());
                        Utils.SnowStray1[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(800.0D);
                        Utils.SnowStray1[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.SnowStray1[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(80);
                        Utils.SnowStray1[i].setHealth(Utils.SnowStray1[i].getMaxHealth());
                        Utils.SnowStray1[i].moveTo(indexX[i] + random.nextDouble(6) - 3,indexY[i],indexZ[i] + random.nextDouble(6) - 3);
                        level.addFreshEntity(Utils.SnowStray1[i]);
                    }
                    if(Utils.SnowStray1[i] != null){
                        double x = Utils.SnowStray1[i].getX();
                        double y = Utils.SnowStray1[i].getY();
                        double z = Utils.SnowStray1[i].getZ();
                        if(x < BoundaryX1 || x > BoundaryX2 || y < BoundaryY1 || y > BoundaryY2 || z < BoundaryZ1 || z > BoundaryZ2)
                        {
                            Utils.SnowStray1[i].moveTo(indexX[i] + random.nextDouble(6) - 3,indexY[i],indexZ[i] + random.nextDouble(6) - 3);
                        }
                    }
                }

            }
        }
    }
}
