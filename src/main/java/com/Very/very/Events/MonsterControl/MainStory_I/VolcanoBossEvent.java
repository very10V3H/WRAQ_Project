package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class VolcanoBossEvent {
    @SubscribeEvent
    public static void VolcanoBoss(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 1800 == 0 && level.equals(level1) && event.phase == TickEvent.Phase.START) {
                int indexX = 18;
                int indexY = -52;
                int indexZ = 1112;
                int BoundaryX1 = 69;
                int BoundaryX2 = -12;
                int BoundaryY1 = -18;
                int BoundaryY2 = -57;
                int BoundaryZ1 = 1123;
                int BoundaryZ2 = 1007;

                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                Random r = new Random();
                if (list0.size() != 0 && (Utils.VolcanoBoss == null || !Utils.VolcanoBoss.isAlive())) {
                    if (Utils.VolcanoBoss != null) Utils.VolcanoBoss.remove(Entity.RemovalReason.KILLED);
                    Utils.VolcanoBoss = new Blaze(EntityType.BLAZE, level);
                    Compute.SetMobCustomName(Utils.VolcanoBoss, Moditems.ArmorVolcanoBoss.get(), Component.literal("熔岩").withStyle(Utils.styleOfVolcano));
                    Utils.VolcanoBoss.setItemSlot(EquipmentSlot.HEAD, Moditems.ArmorVolcanoBoss.get().getDefaultInstance());
                    Utils.VolcanoBoss.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                    Utils.VolcanoBoss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000.0D);
                    Utils.VolcanoBoss.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(80.0D);
                    Utils.VolcanoBoss.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                    Utils.VolcanoBoss.setHealth(Utils.VolcanoBoss.getMaxHealth());
                    int X = indexX + r.nextInt(6);
                    int Y = indexY;
                    int Z = indexZ + r.nextInt(6);
                    while (!level.getBlockState(new BlockPos(X, Y, Z)).getBlock().equals(Blocks.AIR)) {
                        X = indexX + r.nextInt(6);
                        Y = indexY;
                        Z = indexZ + r.nextInt(6);
                    }
                    Utils.VolcanoBoss.moveTo(X, Y, Z);
                    level.addFreshEntity(Utils.VolcanoBoss);
                }
                if (Utils.VolcanoBoss != null) {
                    double x = Utils.VolcanoBoss.getX();
                    double y = Utils.VolcanoBoss.getY();
                    double z = Utils.VolcanoBoss.getZ();
                    if (x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                        Utils.VolcanoBoss.moveTo(indexX + r.nextInt(6), indexY, indexZ + r.nextInt(6));
                    }
                }
            }
        }
    }
}
