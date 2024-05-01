package com.very.wraq.events.mob.MainStoryI.Volcano;

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
        if(event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            if (Utils.VolcanoBoss != null && Utils.VolcanoBoss.isAlive()) Element.ElementProvider(Utils.VolcanoBoss, Element.Fire, 2);
            if(level.getServer().getTickCount() % 1800 == 600 && level.equals(level1)) {
                int indexX = 18;
                int indexY = -52;
                int indexZ = 1112;
                int BoundaryX1 = 69;
                int BoundaryX2 = -12;
                int BoundaryY1 = -18;
                int BoundaryY2 = -57;
                int BoundaryZ1 = 1123;
                int BoundaryZ2 = 1007;

                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                Random r = new Random();
                if (list0.size() != 0 && (Utils.VolcanoBoss == null || !Utils.VolcanoBoss.isAlive())) {
                    if (Utils.VolcanoBoss != null) Utils.VolcanoBoss.remove(Entity.RemovalReason.KILLED);
                    Utils.VolcanoBoss = new Blaze(EntityType.BLAZE, level);
                    Compute.SetMobCustomName(Utils.VolcanoBoss, ModItems.ArmorVolcanoBoss.get(), Component.literal("熔岩").withStyle(CustomStyle.styleOfVolcano));
                    Utils.VolcanoBoss.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorVolcanoBoss.get().getDefaultInstance());
                    Utils.VolcanoBoss.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                    Utils.VolcanoBoss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1500.0D);
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
                    for (Player player : list0) {
                        Compute.FormatMSGSend(player,Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                        append(Utils.VolcanoBoss.getDisplayName()).
                                        append(Component.literal("已重生！").withStyle(ChatFormatting.WHITE)));
                    }
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
            if (level.getServer().getTickCount() % 20 == 0 && level.equals(level1)) {
                Blaze blaze = Utils.VolcanoBoss;
                if (blaze != null && blaze.isAlive()) {
                    List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(blaze.getPosition(1), 5, 5, 5));
                    for (Player player : playerList) {
                        if (player.getPosition(1).add(0, 1, 0).distanceTo(blaze.getPosition(1).add(0, 1, 0)) <= 2)
                            Compute.Damage.ManaDamageToPlayer(blaze, player, player.getMaxHealth() * 0.05f);
                    }
                }
            }
        }
    }
}
