package com.very.wraq.series.specialevents.labourDay;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.io.IOException;
import java.util.*;

public class LabourDayMobSummon {
    public static Vec3[] pos = {
            new Vec3(296, 62, 1084), // 平原
            new Vec3(92, -9, 917), // 矿洞
            new Vec3(168, 124, 1468), // 农庄
            new Vec3(-57, 191, 1422), // 玉山
            new Vec3(1998, 32, 1165), // 紫晶矿洞

            new Vec3(1055, 248, 1273), // 尘月之梦
            new Vec3(897, 69, 861), // 暗黑城堡
            new Vec3(1930, 159, 980), // 樱岛山
            new Vec3(634, 62, 1896), // 雪原村
            new Vec3(1098, 130, 1015) // 生机岛
    };

    public static Component[] components = {
            Component.literal("平原").withStyle(CustomStyle.styleOfPlain),
            Component.literal("矿洞").withStyle(CustomStyle.styleOfMine),
            Component.literal("农庄").withStyle(CustomStyle.styleOfField),
            Component.literal("玉山").withStyle(CustomStyle.styleOfSnow),
            Component.literal("紫晶矿洞").withStyle(CustomStyle.styleOfPurpleIron),

            Component.literal("尘月之梦").withStyle(CustomStyle.styleOfMoon),
            Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
            Component.literal("樱岛山").withStyle(CustomStyle.styleOfSakura),
            Component.literal("雪原村").withStyle(CustomStyle.styleOfIce),
            Component.literal("生机岛").withStyle(CustomStyle.styleOfLife),
    };

    public static Skeleton[] mobs = new Skeleton[15];

    public static Map<Integer, Vec3> currentPos = new HashMap<>();
    public static int[] serialNum = new int[15];
    public static boolean[] summonFlag = new boolean[15];

    public static void getSummonPos(Level level) {
        Random rand = new Random();
        Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                Component.literal("工贼").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("会在以下区域出没:").withStyle(ChatFormatting.WHITE)));
        for (int i = 0; i < 5; i++) {
            int index = rand.nextInt(pos.length);
            Vec3 position = pos[index];
            if (currentPos.containsValue(position)) {
                --i;
                continue;
            }
            currentPos.put(i, position);
            serialNum[i] = index;
            summonFlag[i] = false;
            Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                    Component.literal((i + 1) + ". ").withStyle(ChatFormatting.WHITE).
                            append(components[index]).
                            append(Component.literal(" 位置: ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("「" + position.toString() + "」").withStyle(ChatFormatting.AQUA)));
        }
    }

    public static void detectPlayerNearSummonPos(Player player) {
        Level level = player.level();
        if (currentPos.isEmpty()) return;
        for (int i = 0; i < 5; i++) {
            Vec3 position = currentPos.get(i);
            if (player.position().distanceTo(position) <= 16 && !summonFlag[i]) {
                summonFlag[i] = true;
                summon(level, serialNum[i]);
                Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 遇到了位于 ").withStyle(ChatFormatting.WHITE)).
                                append(components[serialNum[i]]).
                                append(Component.literal(" 的").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" 工贼").withStyle(ChatFormatting.GRAY)));
            }
        }
    }

    public static void remainMob(Level level) {
        if (!currentPos.isEmpty()) {
            int num = 0;
            for (int i = 0; i < 5; i++) {
                if (!summonFlag[i]) num++;
            }
            if (num == 0) {
                Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                        Component.literal("所有地区的 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("工贼 ").withStyle(ChatFormatting.GRAY)).
                                append(Component.literal("都已被消灭，胜利属于工人阶级！").withStyle(ChatFormatting.WHITE)));
            } else {
                Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                        Component.literal("还有 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(String.valueOf(num)).withStyle(ChatFormatting.RED)).
                                append(Component.literal(" 个地区的 ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("工贼").withStyle(ChatFormatting.GRAY)).
                                append(Component.literal("没有被消灭，分别是:").withStyle(ChatFormatting.WHITE)));

                int index = 1;
                for (int i = 0; i < 5; i++) {
                    if (!summonFlag[i]) {
                        Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                                Component.literal(index++ + ". ").withStyle(ChatFormatting.WHITE).
                                        append(components[serialNum[i]]).
                                        append(Component.literal("位置: ").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("「" + pos[serialNum[i]] + "」").withStyle(ChatFormatting.AQUA)));
                    }
                }
            }
        }
    }

    public static void summon(Level level, int serialNum) {
        Skeleton mob = mobs[serialNum];
        if (mob == null || !mob.isAlive())
            mob = new Skeleton(EntityType.SKELETON, level);

        mobs[serialNum] = mob;
        boolean isWeek = serialNum < 5;
        ItemStack armor = isWeek ? ModItems.MobArmorLabourDay1.get().getDefaultInstance()
                : ModItems.MobArmorLabourDay2.get().getDefaultInstance();

        ItemStack weapon = isWeek ? Items.IRON_HOE.getDefaultInstance() : Items.IRON_PICKAXE.getDefaultInstance();

        Compute.setMobCustomName(mob, armor.getItem(), Component.literal("工贼").withStyle(ChatFormatting.GRAY));
        mob.setItemSlot(EquipmentSlot.HEAD, armor);
        mob.setItemInHand(InteractionHand.MAIN_HAND, weapon);
        mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(isWeek ? 5.1 * Math.pow(10, 4) : 5.1 * Math.pow(10, 7));
        mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(isWeek ? 510 : 5100);

        mob.moveTo(pos[serialNum].add(0, 1, 0));
        level.addFreshEntity(mob);
    }

    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)) && event.phase.equals(TickEvent.Phase.START)) {
            Level level = event.level;
            if (event.level.getServer().getTickCount() % 36000 == 17000) getSummonPos(level);
            if (event.level.getServer().getTickCount() % 6000 == 1700) remainMob(level);
        }
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        detectPlayerNearSummonPos(player);
    }

    public static void kill(Player player, Mob mob) throws IOException {
        Level level = player.level();
        List<Skeleton> mobList = Arrays.stream(mobs).toList();
        if (mob instanceof Skeleton skeleton && mobList.contains(skeleton)) {
            Compute.formatBroad(level, Component.literal("五一活动").withStyle(ChatFormatting.RED),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal(" 击杀了位于 ").withStyle(ChatFormatting.WHITE)).
                            append(components[mobList.indexOf(skeleton)]).
                            append(Component.literal(" 的 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("工贼").withStyle(ChatFormatting.GRAY)));
            if (mobList.indexOf(skeleton) < 5) {
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.OldGoldCoin.get()));
            } else {
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.OldGoldCoin.get(), 2));
            }
        }
    }

}
