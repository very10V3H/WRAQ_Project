package fun.wraq.series.events;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.midautumn.MidAutumnUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class SpecialEventCommon {

    private static final Style style = CustomStyle.styleOfMoon;

    public static void onLogin(Player player) {
        if (!isInActivityDate()) {
            return;
        }
        sendMSG(player, Te.s("中秋活动进行中:"));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "双倍理智回复", CustomStyle.styleOfFlexible));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "+200%经验加成", ChatFormatting.LIGHT_PURPLE));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "+25%额外产出", CustomStyle.styleOfGold));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "节假日补贴:", CustomStyle.styleOfPower, "每完成一个", "委托任务", CustomStyle.styleOfWorld,
                "+2500VB", CustomStyle.styleOfGold));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "中秋活动期间(9.30 - 10.8)登录，每日可获取限定奖励! (>=150级)"));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "超凶玉兔", style, "会在每天的 ", "11:20 14:20 15:20 16:20 17:20 " +
                        "19:50 20:20 20:50 21:20 21:50 22:20 22:50 23:20", style,
                "出现在", "尘月之梦中心岛附近", CustomStyle.styleOfMoon1));
        player.sendSystemMessage(Te.s(" ".repeat(8), "·", style,
                "在", "夜晚", CustomStyle.styleOfMoon1, "击杀怪物，概率获得", SpecialEventItems.OSMANTHUS,
                "(每日前1000个击杀会额外提供20个绑定的", SpecialEventItems.OSMANTHUS, ")", CustomStyle.styleOfMoon));
    }

    private static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("节假日", ChatFormatting.LIGHT_PURPLE), content);
    }

    private static boolean isInActivityDate() {
        return MidAutumnUtil.isInActivityDate();
    }

    public static double getExReasonRecoverRate() {
        return isInActivityDate() ? 1 : 0;
    }

    public static double getExExpUp() {
        return isInActivityDate() ? 2 : 0;
    }

    public static double getExHarvestRate() {
        return isInActivityDate() ? 0.25 : 0;
    }

    public static void onFinishEntrustment(Player player) {
        if (!isInActivityDate()) {
            return;
        }
        Compute.VBIncomeAndMSGSend(player, 2500);
        sendMSG(player, Te.s("节假日补贴已发放!"));
    }
}
