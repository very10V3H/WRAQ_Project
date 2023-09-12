package com.Very.very.Events.MonsterControl.MainStory_I;

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

@Mod.EventBusSubscriber
public class SnowStray {
    @SubscribeEvent
    public static void snowstray(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 120 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {-93,-95,-53,-39,-40,-55,-55,-66};
                int [] indexY = {171,187,168,161,159,196,191,194};
                int [] indexZ = {1350,1361,1368,1394,1478,1454,1423,1393};
                int BoundaryX1 = 0;
                int BoundaryX2 = -182 ;
                int BoundaryY1 = 200;
                int BoundaryY2 = 144;
                int BoundaryZ1 = 1499;
                int BoundaryZ2 = 1291;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了冰川流浪者。"));
                }
                for(int i=0;i<8;i++)
                {
                    if(list0.size() != 0 && (Utils.SnowStray[i] == null || !Utils.SnowStray[i].isAlive()))
                    {
                        if(Utils.SnowStray[i] != null) Utils.SnowStray[i].remove(Entity.RemovalReason.KILLED);
                        Utils.SnowStray[i] = new Stray(EntityType.STRAY,level);
                        Compute.SetMobCustomName(Utils.SnowStray[i],Moditems.ArmorSnow.get(),Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA));
                        Utils.SnowStray[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSnow.get().getDefaultInstance());
                        ItemStack itemStack = Items.BOW.getDefaultInstance();
                        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
                        map.put(Enchantments.POWER_ARROWS,8);
                        EnchantmentHelper.setEnchantments(map,itemStack);
                        Utils.SnowStray[i].setItemSlot(EquipmentSlot.MAINHAND , itemStack);
                        Utils.SnowStray[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.SnowStray[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.SnowStray[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30);
                        Utils.SnowStray[i].setHealth(Utils.SnowStray[i].getMaxHealth());
                        Utils.SnowStray[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.SnowStray[i]);
                    }
                    if(Utils.SnowStray[i] != null){
                        double x = Utils.SnowStray[i].getX();
                        double y = Utils.SnowStray[i].getY();
                        double z = Utils.SnowStray[i].getZ();
                        if(x > 0 || x < -182 || y > 200 || y < 144 || z > 1499 || z < 1291)
                        {
                            Utils.SnowStray[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
        }
    }
}
