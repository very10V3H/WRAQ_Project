package com.Very.very.Events.MonsterControl.MainStory_I;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
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
public class ForestBossEvent {
    @SubscribeEvent
    public static void ForestBoss(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 1800 == 0 && level.equals(level1) && event.phase == TickEvent.Phase.START) {
                int indexX = 34;
                int indexY = 68;
                int indexZ = 920;
                int BoundaryX1 = 126;
                int BoundaryX2 = -15;
                int BoundaryY1 = 90;
                int BoundaryY2 = 59;
                int BoundaryZ1 = 979;
                int BoundaryZ2 = 907;

                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3((double) (BoundaryX1 + BoundaryX2) /2,(double) (BoundaryY1 + BoundaryY2) /2,(double) (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                Random r = new Random();
                if (list0.size() != 0 && (Utils.ForestBoss == null || !Utils.ForestBoss.isAlive())) {
                    if (Utils.ForestBoss != null) Utils.ForestBoss.remove(Entity.RemovalReason.KILLED);
                    Utils.ForestBoss = new Zombie(EntityType.ZOMBIE, level);
                    Compute.SetMobCustomName(Utils.ForestBoss, Moditems.ArmorForestBoss.get(), Component.literal("森罗").withStyle(ChatFormatting.DARK_GREEN));
                    Utils.ForestBoss.setItemSlot(EquipmentSlot.HEAD, Moditems.ArmorForestBoss.get().getDefaultInstance());
                    Utils.ForestBoss.setItemSlot(EquipmentSlot.MAINHAND, Moditems.ForestBossSword.get().getDefaultInstance());
                    Utils.ForestBoss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(650.0D);
                    Utils.ForestBoss.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60.0D);
                    Utils.ForestBoss.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                    Utils.ForestBoss.setHealth(Utils.ForestBoss.getMaxHealth());
                    int X = indexX + r.nextInt(6);
                    int Y = indexY;
                    int Z = indexZ + r.nextInt(6);
                    while (!level.getBlockState(new BlockPos(X, Y, Z)).getBlock().equals(Blocks.AIR)) {
                        X = indexX + r.nextInt(6);
                        Y = indexY;
                        Z = indexZ + r.nextInt(6);
                    }
                    Utils.ForestBoss.moveTo(X, Y, Z);
                    level.addFreshEntity(Utils.ForestBoss);
                }
                if (Utils.ForestBoss != null) {
                    double x = Utils.ForestBoss.getX();
                    double y = Utils.ForestBoss.getY();
                    double z = Utils.ForestBoss.getZ();
                    if (x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                        Utils.ForestBoss.moveTo(indexX + r.nextInt(6), indexY, indexZ + r.nextInt(6));
                    }
                }
            }
        }
    }
}
