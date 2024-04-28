package com.very.wraq.events.mob.MainStoryI.Lake;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
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
public class LakeBossEvent {
    @SubscribeEvent
    public static void LakeBoss(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            if (Utils.LakeBoss != null && Utils.LakeBoss.isAlive()) Element.ElementProvider(Utils.LakeBoss, Element.Water, 2);

            if(level.getServer().getTickCount() % 1800 == 300 && level.equals(level1) && event.phase == TickEvent.Phase.START) {
                int indexX = 35;
                int indexY = 50;
                int indexZ = 998;
                int BoundaryX1 = 84;
                int BoundaryX2 = -32;
                int BoundaryY1 = 65;
                int BoundaryY2 = 17;
                int BoundaryZ1 = 1014;
                int BoundaryZ2 = 926;

                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                Random r = new Random();
                if (list0.size() != 0 && (Utils.LakeBoss == null || !Utils.LakeBoss.isAlive())) {
                    if (Utils.LakeBoss != null) Utils.LakeBoss.remove(Entity.RemovalReason.KILLED);
                    Utils.LakeBoss = new Drowned(EntityType.DROWNED, level);
                    Compute.SetMobCustomName(Utils.LakeBoss, ModItems.ArmorLakeBoss.get(), Component.literal("东").withStyle(ChatFormatting.BLUE));
                    Utils.LakeBoss.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorLakeBoss.get().getDefaultInstance());
                    Utils.LakeBoss.setItemSlot(EquipmentSlot.MAINHAND , Items.TRIDENT .getDefaultInstance());
                    Utils.LakeBoss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1200.0D);
                    Utils.LakeBoss.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100.0D);
                    Utils.LakeBoss.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);

                    Utils.LakeBoss.setHealth(Utils.LakeBoss.getMaxHealth());
                    int X = indexX + r.nextInt(6);
                    int Y = indexY;
                    int Z = indexZ + r.nextInt(6);
                    Utils.LakeBoss.moveTo(X, Y, Z);
                    level.addFreshEntity(Utils.LakeBoss);
                    for (Player player : list0) {
                        Compute.FormatMSGSend(player,Component.literal("次元Boss").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("该区域的").withStyle(ChatFormatting.WHITE).
                                        append(Utils.LakeBoss.getDisplayName()).
                                        append(Component.literal("已重生！").withStyle(ChatFormatting.WHITE)));
                    }
                }
                if (Utils.LakeBoss != null) {
                    double x = Utils.LakeBoss.getX();
                    double y = Utils.LakeBoss.getY();
                    double z = Utils.LakeBoss.getZ();
                    if (x > BoundaryX1 || x < BoundaryX2 || y > BoundaryY1 || y < BoundaryY2 || z > BoundaryZ1 || z < BoundaryZ2) {
                        Utils.LakeBoss.moveTo(indexX + r.nextInt(6), indexY, indexZ + r.nextInt(6));
                    }
                }
            }
        }
    }
}
