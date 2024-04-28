package com.very.wraq.events.mob.MainStoryII.Sky;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vex;
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
public class SkyBossEvent {
    @SubscribeEvent
    public static void SkyBoss(TickEvent.LevelTickEvent event) {
        if(event.side.isServer()) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            if (Utils.SkyBoss != null && Utils.SkyBoss.isAlive()) Element.ElementProvider(Utils.SkyBoss, Element.Wind, 2);
            if(level.getServer().getTickCount() % 1800 == 900 && level.equals(level1) && event.phase == TickEvent.Phase.START) {
                int indexX = 29;
                int indexY = 184;
                int indexZ = 1005;
                int BoundaryX1 = 39;
                int BoundaryX2 = 16;
                int BoundaryY1 = 190;
                int BoundaryY2 = 169;
                int BoundaryZ1 = 1030;
                int BoundaryZ2 = 1008;

                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                Random r = new Random();
                if (list0.size() != 0 && (Utils.SkyBoss == null || !Utils.SkyBoss.isAlive())) {
                    if (Utils.SkyBoss != null) Utils.SkyBoss.remove(Entity.RemovalReason.KILLED);
                    Utils.SkyBoss = new Vex(EntityType.VEX, level);
                    Compute.SetMobCustomName(Utils.SkyBoss, ModItems.ArmorSkyBoss.get(), Component.literal("天").withStyle(ChatFormatting.BLUE));
                    Utils.SkyBoss.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorSkyBoss.get().getDefaultInstance());
                    Utils.SkyBoss.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_AXE.getDefaultInstance());
                    Utils.SkyBoss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(2000.0D);
                    Utils.SkyBoss.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(120.0D);
                    Utils.SkyBoss.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);

                    Utils.SkyBoss.addEffect(new MobEffectInstance(MobEffects.GLOWING,88888,1,false,false));
                    Utils.SkyBoss.setHealth(Utils.SkyBoss.getMaxHealth());
                    int X = indexX + r.nextInt(6);
                    int Y = indexY;
                    int Z = indexZ + r.nextInt(6);
                    while (!level.getBlockState(new BlockPos(X, Y, Z)).getBlock().equals(Blocks.AIR)) {
                        X = indexX + r.nextInt(6);
                        Y = indexY;
                        Z = indexZ + r.nextInt(6);
                    }
                    Utils.SkyBoss.moveTo(X, Y, Z);
                    level.addFreshEntity(Utils.SkyBoss);
                    for (Player player : list0) {
                        Compute.FormatMSGSend(player,Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                        append(Utils.SkyBoss.getDisplayName()).
                                        append(Component.literal("已重生！").withStyle(ChatFormatting.WHITE)));
                    }
                }
                if (Utils.SkyBoss != null) {
                    double x = Utils.SkyBoss.getX();
                    double y = Utils.SkyBoss.getY();
                    double z = Utils.SkyBoss.getZ();
                    if (x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                        Utils.SkyBoss.moveTo(indexX + r.nextInt(6), indexY, indexZ + r.nextInt(6));
                    }
                }
            }
        }
    }
}
