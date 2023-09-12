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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PFZombieEvent {
    @SubscribeEvent
    public static void PFZombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(Utils.PFController > 0 && level.getServer().getTickCount() % 20 == 0 && level.equals(level1)
                    && Utils.PFSummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START) {
                Utils.PFController--;
                int [] indexX = {215};
                int [] indexY = {80};
                int [] indexZ = {1070};
                Utils.PFSummonTick = level.getServer().getTickCount();
                if(Utils.PFZombie == null || !Utils.PFZombie.isAlive())
                {
                    if(Utils.PFZombie != null) Utils.PFZombie.remove(Entity.RemovalReason.KILLED);
                    Utils.PFZombie = new Zombie(EntityType.ZOMBIE,level);
                    Compute.SetMobCustomName(Utils.PFZombie,Moditems.ArmorPFH.get(),Component.literal("隐秘生机残骸").withStyle(ChatFormatting.DARK_GREEN));
                    Utils.PFZombie.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorPFH.get().getDefaultInstance());
                    Utils.PFZombie.setItemSlot(EquipmentSlot.CHEST , Moditems.ArmorPFC.get().getDefaultInstance());
                    Utils.PFZombie.setItemSlot(EquipmentSlot.LEGS , Moditems.ArmorPFL.get().getDefaultInstance());
                    Utils.PFZombie.setItemSlot(EquipmentSlot.FEET , Moditems.ArmorPFB.get().getDefaultInstance());
                    Utils.PFZombie.setItemSlot(EquipmentSlot.MAINHAND , Items.WOODEN_SWORD.getDefaultInstance());
                    Utils.PFZombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(30);
                    Utils.PFZombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                    Utils.PFZombie.setHealth(Utils.PFZombie.getMaxHealth());
                    Utils.PFZombie.moveTo(indexX[0],indexY[0],indexZ[0]);
                    level.addFreshEntity(Utils.PFZombie);
                }
                double x = Utils.PFZombie.getX();
                double y = Utils.PFZombie.getY();
                double z = Utils.PFZombie.getZ();
                if(x > 235 || x < 195 || y > 100 || y < 60 || z > 1100 || z < 1040)
                {
                    Utils.PFZombie.moveTo(indexX[0],indexY[0],indexZ[0]);
                }
            }
        }
    }
}
