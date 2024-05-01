package com.very.wraq.events.mob.MainStoryI.Snow;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
public class SnowBossEvent {
    @SubscribeEvent
    public static void VolcanoBoss(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            if (Utils.SnowBoss != null && Utils.SnowBoss.isAlive()) Element.ElementProvider(Utils.SnowBoss, Element.Ice, 2);
            if(level.getServer().getTickCount() % 1800 == 1200 && level.equals(level1)) {
                int indexX = -58;
                int indexY = 191;
                int indexZ = 1421;
                int BoundaryX1 = -182;
                int BoundaryX2 = 0;
                int BoundaryY1 = 144;
                int BoundaryY2 = 210;
                int BoundaryZ1 = 1291;
                int BoundaryZ2 = 1499;
                Item Armor = ModItems.ArmorSnowBoss.get();

                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                Random r = new Random();
                if (list0.size() != 0 && (Utils.SnowBoss == null || !Utils.SnowBoss.isAlive())) {
                    if (Utils.SnowBoss != null) Utils.SnowBoss.remove(Entity.RemovalReason.KILLED);
                    Utils.SnowBoss = new Stray(EntityType.STRAY, level);
                    Compute.SetMobCustomName(Utils.SnowBoss, Armor, Component.literal("遗忘者").withStyle(CustomStyle.styleOfSnow));
                    Utils.SnowBoss.setItemSlot(EquipmentSlot.HEAD, Armor.getDefaultInstance());
                    Utils.SnowBoss.setItemSlot(EquipmentSlot.MAINHAND, ModItems.SnowSword3.get().getDefaultInstance());
                    Utils.SnowBoss.setItemSlot(EquipmentSlot.CHEST,ModItems.SnowBossArmorChest.get().getDefaultInstance());
                    Utils.SnowBoss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(3000.0D);
                    Utils.SnowBoss.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100.0D);
                    Utils.SnowBoss.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);

                    Utils.SnowBoss.setHealth(Utils.SnowBoss.getMaxHealth());
                    int X = indexX + r.nextInt(6);
                    int Y = indexY;
                    int Z = indexZ + r.nextInt(6);
                    while (!level.getBlockState(new BlockPos(X, Y, Z)).getBlock().equals(Blocks.AIR)) {
                        X = indexX + r.nextInt(6);
                        Y = indexY;
                        Z = indexZ + r.nextInt(6);
                    }
                    Utils.SnowBoss.moveTo(X, Y, Z);
                    level.addFreshEntity(Utils.SnowBoss);
                    for (Player player : list0) {
                        Compute.FormatMSGSend(player,Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                        append(Utils.SnowBoss.getDisplayName()).
                                        append(Component.literal("已重生！").withStyle(ChatFormatting.WHITE)));
                    }
                }
                if (Utils.SnowBoss != null) {
                    double x = Utils.SnowBoss.getX();
                    double y = Utils.SnowBoss.getY();
                    double z = Utils.SnowBoss.getZ();
                    if (x < BoundaryX1 || x > BoundaryX2 || y < BoundaryY1 || y > BoundaryY2 || z < BoundaryZ1 || z > BoundaryZ2) {
                        Utils.SnowBoss.moveTo(indexX + r.nextInt(6), indexY, indexZ + r.nextInt(6));
                    }
                }
            }
        }
    }
}
