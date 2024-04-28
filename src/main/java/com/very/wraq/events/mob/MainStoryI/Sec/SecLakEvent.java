package com.very.wraq.events.mob.MainStoryI.Sec;

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
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SecLakEvent {
    @SubscribeEvent
    public static void SecVolcano(TickEvent.LevelTickEvent event) {
        if(event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Zombie zombie : Utils.PLDrown) {
                if (zombie != null && zombie.isAlive()) Element.ElementProvider(zombie, Element.Water, 2);
            }
            if(Utils.SLController > 0 && level.getServer().getTickCount() % 60 == 0 && level.equals(level1)
                    && Utils.PLSummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START) {
                Utils.SLController--;
                int [] indexX = {65,54,48};
                int [] indexY = {-49,-50,-51};
                int [] indexZ = {997,1012,991};
                Utils.PLSummonTick = level.getServer().getTickCount();
                for(int i = 0; i < 3; i++){
                    if(Utils.PLDrown[i] == null || !Utils.PLDrown[i].isAlive())
                    {
                        if(Utils.PLDrown[i] != null) Utils.PLDrown[i].remove(Entity.RemovalReason.KILLED);
                        Utils.PLDrown[i] = new Drowned(EntityType.DROWNED,level);
                        Compute.SetMobCustomName(Utils.PLDrown[i], ModItems.ArmorSLH.get(),Component.literal("被遗忘的探索者").withStyle(ChatFormatting.BLUE));
                        Utils.PLDrown[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSLH.get().getDefaultInstance());
                        Utils.PLDrown[i].setItemSlot(EquipmentSlot.CHEST , ModItems.ArmorSLC.get().getDefaultInstance());
                        Utils.PLDrown[i].setItemSlot(EquipmentSlot.LEGS , ModItems.ArmorSLL.get().getDefaultInstance());
                        Utils.PLDrown[i].setItemSlot(EquipmentSlot.FEET , ModItems.ArmorSLB.get().getDefaultInstance());
                        Utils.PLDrown[i].setItemSlot(EquipmentSlot.MAINHAND, Items.TRIDENT.getDefaultInstance());
                        Utils.PLDrown[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(75);
                        Utils.PLDrown[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(450.0D);

                        Utils.PLDrown[i].setHealth(Utils.PLDrown[i].getMaxHealth());
                        Utils.PLDrown[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.PLDrown[i]);
                    }
                    double x = Utils.PLDrown[i].getX();
                    double y = Utils.PLDrown[i].getY();
                    double z = Utils.PLDrown[i].getZ();
                    if(x > 75 || x < 38 || y > -25 || y < -60 || z > 1022 || z < 981)
                    {
                        Utils.PLDrown[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                    }
                }
            }
        }
    }
}
