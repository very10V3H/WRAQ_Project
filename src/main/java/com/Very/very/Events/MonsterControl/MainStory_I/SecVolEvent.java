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
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SecVolEvent {
    @SubscribeEvent
    public static void SecVolcano(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
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
                        Compute.SetMobCustomName(Utils.SVBlaze[i],Moditems.ArmorSVH.get(),Component.literal("隐秘熔岩能源").withStyle(ChatFormatting.YELLOW));
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSVH.get().getDefaultInstance());
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.CHEST , Moditems.ArmorSVC.get().getDefaultInstance());
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.LEGS , Moditems.ArmorSVL.get().getDefaultInstance());
                        Utils.SVBlaze[i].setItemSlot(EquipmentSlot.FEET , Moditems.ArmorSVB.get().getDefaultInstance());
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
