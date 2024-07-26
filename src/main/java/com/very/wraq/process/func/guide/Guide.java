package com.very.wraq.process.func.guide;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.func.guide.networking.GuideStageS2CPacket;
import com.very.wraq.process.system.wayPoints.MyWayPoint;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Guide {
    public static int clientStage = 0;
    public static String key = "guide";

    public static int getPlayerCurrentStage(Player player) {
        return player.getPersistentData().getInt(key);
    }

    public static void setPlayerCurrentStage(Player player, int stage) {
        player.getPersistentData().putInt(key, stage);
    }

    public static Int2ObjectMap<List<Component>> descriptionMap = new Int2ObjectOpenHashMap<>() {{
        put(0, new ArrayList<>() {{
            add(Component.literal("引导 - 兑换背包").withStyle(ChatFormatting.GOLD));
            add(Component.literal("根据路径点，找到背包商人，兑换背包。").withStyle(ChatFormatting.WHITE));
        }});
        put(1, new ArrayList<>() {{
            add(Component.literal("引导 - 使用翻滚").withStyle(ChatFormatting.GOLD));
            add(Component.literal("按下z键，使用翻滚").withStyle(ChatFormatting.WHITE));
        }});
        put(2, new ArrayList<>() {{
            add(Component.literal("引导 - 打开物品图鉴").withStyle(ChatFormatting.GOLD));
            add(Component.literal("右键身份卡，点击物品图鉴").withStyle(ChatFormatting.WHITE));
        }});
        put(3, new ArrayList<>() {{
            add(Component.literal("引导 - 击杀第一只怪物").withStyle(ChatFormatting.GOLD));
            add(Component.literal("前往平原村南部，击杀").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN)));
            add(Component.literal("你可以按下M打开地图，查看位置").withStyle(ChatFormatting.WHITE));
        }});
        put(4, new ArrayList<>() {{
            add(Component.literal("引导 - 完成第一次锻造").withStyle(ChatFormatting.GOLD));
            add(Component.literal("手持锻造锤右键锻造台").withStyle(ChatFormatting.WHITE));
            add(Component.literal("打开锻造界面，进行锻造").withStyle(ChatFormatting.WHITE));
            add(Component.literal("提示：推荐首先锻造").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("平原靴子").withStyle(ChatFormatting.GREEN)));
        }});
        put(5, new ArrayList<>() {{
            add(Component.literal("引导 - 完成第一次灌注").withStyle(ChatFormatting.GOLD));
            add(Component.literal("右键灌注台，对任意武器进行一次灌注").withStyle(ChatFormatting.WHITE));
            add(Component.literal("对于描述最后一行有").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("[可灌注/增幅]").withStyle(CustomStyle.styleOfInject)));
            add(Component.literal("的物品或装备，你可以把它放到").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("灌注台").withStyle(CustomStyle.styleOfInject)));
            add(Component.literal("查看其灌注获得的物品及所需材料").withStyle(ChatFormatting.WHITE));
        }});
        put(6, new ArrayList<>() {{
            add(Component.literal("引导 - 打开元素轮盘").withStyle(ChatFormatting.AQUA));
            add(Component.literal("按下[左ALT]打开元素轮盘").withStyle(ChatFormatting.WHITE));
        }});
    }};

    public static void trig(Player player, int stage) {
        if (getPlayerCurrentStage(player) == stage) {
            // reward
            Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                    Component.literal("你完成了引导任务，获得了奖励！").withStyle(ChatFormatting.WHITE));
            Compute.expGive(player, (stage + 1) * 10);
            Compute.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            //
            int nextStage = stage + 1;
            setPlayerCurrentStage(player, nextStage);
            sendStageToClient(player);
            if (stage == 0) MyWayPoint.sendRemovePacketToClient(player, "背包商人");
            if (stage == 3) MyWayPoint.sendRemovePacketToClient(player, "平原僵尸刷怪点");
            if (stage == 4) MyWayPoint.sendRemovePacketToClient(player, "锻造台");
            if (stage == 5) MyWayPoint.sendRemovePacketToClient(player, "灌注台");
            if (stage >= descriptionMap.size()) {
                Compute.sendFormatMSG(player, Component.literal("引导").withStyle(ChatFormatting.AQUA),
                        Component.literal("你完成了所有基础引导任务，继续探索维瑞阿契世界吧！").withStyle(ChatFormatting.WHITE));
                player.addItem(new ItemStack(ModItems.PlainRing.get()));
            }
        }
    }

    public static void sendStageToClient(Player player) {
        int currentStage = getPlayerCurrentStage(player);
        ModNetworking.sendToClient(new GuideStageS2CPacket(currentStage), (ServerPlayer) player);
        if (currentStage == 0)
            MyWayPoint.sendAddPacketToClient(player, new MyWayPoint(new Vec3(949, 236, -7), "背包商人", MyWayPoint.colorMap.get(MyWayPoint.gold), 1));
        if (currentStage == 3)
            MyWayPoint.sendAddPacketToClient(player, new MyWayPoint(new Vec3(754, 78, 265), "平原僵尸刷怪点", MyWayPoint.colorMap.get(MyWayPoint.green), 1));
        if (currentStage == 4)
            MyWayPoint.sendAddPacketToClient(player, new MyWayPoint(new Vec3(730, 85, 211), "锻造台", MyWayPoint.colorMap.get(MyWayPoint.aqua), 1));
        if (currentStage == 5)
            MyWayPoint.sendAddPacketToClient(player, new MyWayPoint(new Vec3(730, 85, 211), "灌注台", MyWayPoint.colorMap.get(MyWayPoint.purple), 1));
    }
}
