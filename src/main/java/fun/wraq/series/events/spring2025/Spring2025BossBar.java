package fun.wraq.series.events.spring2025;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;

import java.util.List;

public class Spring2025BossBar {
    public static ServerBossEvent bossEvent = new ServerBossEvent(Te.s("活动条", CustomStyle.styleOfSpring),
            BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS);
    public static final List<Component> content = List.of(
            Te.s("春节活动进行中！", CustomStyle.styleOfSpring, "委托", CustomStyle.styleOfWorld, "/",
                    "悬赏", ChatFormatting.GOLD, "经验奖励", " + 200%", ChatFormatting.LIGHT_PURPLE,
                    "，声望", " + 50%", ChatFormatting.GOLD),
            Te.s("春节活动进行中！", CustomStyle.styleOfSpring,
                    ComponentUtils.AttributeDescription.expUp(" + 150%")),
            Te.s("春节活动进行中！", CustomStyle.styleOfSpring, "击杀怪物概率掉落", SpecialEventItems.MONEY),
            Te.s("春节活动进行中！", CustomStyle.styleOfSpring, "完成", "悬赏任务", CustomStyle.styleOfGold,
                    "将额外获得", SpecialEventItems.MONEY, " * 1", "，完成", "委托任务", CustomStyle.styleOfWorld,
                    "将额外获得", SpecialEventItems.MONEY, " * 2", ChatFormatting.AQUA),
            Te.s("春节活动进行中！", CustomStyle.styleOfSpring, "理智回复已翻倍", CustomStyle.styleOfFlexible),
            Te.s("春节活动进行中！", CustomStyle.styleOfSpring, "购买", "月卡", ChatFormatting.LIGHT_PURPLE,
                    "将获得", "额外10天持续时间", ChatFormatting.LIGHT_PURPLE),
            Te.s("春节活动将持续至二月下旬", CustomStyle.styleOfSpring),
            Te.s("每天的 10:30 15:00 17:00 + 19 ~ 23时的整点，", "年兽", CustomStyle.styleOfSpring, "都会出现在",
                    "炼魔平原!", CustomStyle.styleOfMana),
            Te.s("旧世魔王居所", CustomStyle.styleOfDemon, "遭到", "金蛇次生体", CustomStyle.styleOfPower,
                    "入侵，前往讨伐以获取奖励!")
    );

    public static int lastSwitchTick = 0;

    public static void handleServerTick() {
        int changeSeconds = 10;
        if (Tick.get() % Tick.s(changeSeconds) == 25) {
            lastSwitchTick = Tick.get();
            bossEvent.setName(content.get((Tick.get() / Tick.s(changeSeconds)) % content.size()));
        }
        bossEvent.setProgress((lastSwitchTick + Tick.s(changeSeconds) - Tick.get()) * 1f / Tick.s(changeSeconds));
        if (Tick.get() % 20 == 0) {
            BossEvent.BossBarColor barColor = (Tick.get() / Tick.s(1)) % 2 == 1 ?
                    BossEvent.BossBarColor.RED : BossEvent.BossBarColor.YELLOW;
            bossEvent.setColor(barColor);
        }
    }

    public static void handleServerPlayerTick(ServerPlayer player) {
        if (Compute.playerIsInBattle(player)) {
            bossEvent.removePlayer(player);
        } else {
            bossEvent.addPlayer(player);
        }
    }
}
