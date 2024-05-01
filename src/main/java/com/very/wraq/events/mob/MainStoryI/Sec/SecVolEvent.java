package com.very.wraq.events.mob.MainStoryI.Sec;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SecVolEvent {
    @SubscribeEvent
    public static void SecVolcano(TickEvent.LevelTickEvent event) {
        if(event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Blaze blaze : Utils.SVBlaze) {
                if (blaze != null && blaze.isAlive()) Element.ElementProvider(blaze, Element.Fire, 2);
            }
            if(Utils.SVController > 0 && level.getServer().getTickCount() % 80 == 0 && level.equals(level1)
                    && Utils.PVSummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START) {
                Utils.SVController--;
                int [] indexX = {-29,-9,-7,-27};
                int [] indexY = {-43,-43,-43,-43};
                int [] indexZ = {884,884,902,899};
                Utils.PVSummonTick = level.getServer().getTickCount();
                for(int i = 0; i < 4; i++){
                    if(Utils.SVBlaze[i] == null || !Utils.SVBlaze[i].isAlive())
                    {
                        if(Utils.SVBlaze[i] != null) Utils.SVBlaze[i].remove(Entity.RemovalReason.KILLED);
                        Utils.SVBlaze[i] = new Blaze(EntityType.BLAZE,level);
                        Compute.SetMobCustomName(Utils.SVBlaze[i], ModItems.ArmorSVH.get(),Component.literal("隐秘熔岩能源").withStyle(ChatFormatting.YELLOW));
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorSVH.get().getDefaultInstance());
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.CHEST , ModItems.ArmorSVC.get().getDefaultInstance());
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.LEGS , ModItems.ArmorSVL.get().getDefaultInstance());
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.FEET , ModItems.ArmorSVB.get().getDefaultInstance());
                        Utils.SVBlaze[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60);
                        Utils.SVBlaze[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(600.0D);
                        Utils.SVBlaze[i].setHealth(Utils.SVBlaze[i].getMaxHealth());
                        Utils.SVBlaze[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.SVBlaze[i]);
                    }
                    double x = Utils.SVBlaze[i].getX();
                    double y = Utils.SVBlaze[i].getY();
                    double z = Utils.SVBlaze[i].getZ();
                    if(x > 0 || x < -36 || y > -36 || y < -44 || z > 912 || z < 874)
                    {
                        Utils.SVBlaze[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                    }
                }
            }
        }
    }
}
