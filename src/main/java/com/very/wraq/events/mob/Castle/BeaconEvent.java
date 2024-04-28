package com.very.wraq.events.mob.Castle;


import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
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
public class BeaconEvent {

    public static Stray[] strays = new Stray[15];
    @SubscribeEvent
    public static void IceHunter(TickEvent.LevelTickEvent event)
    {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Stray stray : strays) {
                if (stray != null && stray.isAlive()) Element.ElementProvider(stray, Element.Fire, 1);
            }
            if (level.getServer().getTickCount() % 300 == 102 && level.equals(level1)) {
                int [] indexX = {922,931,931,916,905,900};
                int [] indexY = {97 ,85 ,85 ,85 ,87 ,81 };
                int [] indexZ = {876,869,888,887,866,862};

                int BoundaryX1 = 951;
                int BoundaryX2 = 883;
                int BoundaryY1 = 115;
                int BoundaryY2 = 66;
                int BoundaryZ1 = 913;
                int BoundaryZ2 = 845;

                List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+100,Math.abs(BoundaryY1-BoundaryY2)+100,Math.abs(BoundaryZ1-BoundaryZ2)+100));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 50) {
                    for (Mob monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了烽火台哨卫"));
                }

                for (int i = 0; i < 9; i ++) {
                    Random random = new Random();
                    int Index = random.nextInt(6);
                    if (list0.size() != 0 && (strays[i] == null || !strays[i].isAlive())) {
                        if(strays[i] != null) strays[i].remove(Entity.RemovalReason.KILLED);
                        strays[i] = new Stray(EntityType.STRAY, level);
                        Compute.SetMobCustomName(strays[i], ModItems.MobArmorBeaconHelmet.get(),
                                Component.literal("烽火台哨卫").withStyle(CustomStyle.styleOfPower));
                        strays[i].setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorBeaconHelmet.get().getDefaultInstance());
                        strays[i].setItemSlot(EquipmentSlot.CHEST , ModItems.MobArmorBeaconChest.get().getDefaultInstance());
                        strays[i].setItemSlot(EquipmentSlot.LEGS , ModItems.MobArmorBeaconLeggings.get().getDefaultInstance());
                        strays[i].setItemSlot(EquipmentSlot.FEET , ModItems.MobArmorBeaconBoots.get().getDefaultInstance());

                        strays[i].setItemSlot(EquipmentSlot.MAINHAND , Items.BOW.getDefaultInstance());
                        strays[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(2 * Math.pow(10,6));
                        strays[i].setHealth(strays[i].getMaxHealth());
                        strays[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        strays[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3000);

                        strays[i].moveTo(indexX[Index] + 0.5,indexY[Index] + 1,indexZ[Index] + 0.5);
                        level.addFreshEntity(strays[i]);
                    }
                    if (strays[i] != null) {
                        double x = strays[i].getX();
                        double y = strays[i].getY();
                        double z = strays[i].getZ();
                        if(x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                            strays[i].moveTo(indexX[Index],indexY[Index],indexZ[Index]);
                        }
                    }
                }
            }
        }

    }
}
