package fun.wraq.series.events.summer2025;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.Calendar;

public class Summer2025 {
    public static void onLogin(Player player) {
        if (isInActivityDate()) {
            sendMSG(player, Te.s("暑期活动预热中:"));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "双倍理智回复", CustomStyle.styleOfFlexible));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "+200%经验加成", ChatFormatting.LIGHT_PURPLE));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "+25%额外产出", CustomStyle.styleOfGold));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "高温补贴:", CustomStyle.styleOfPower, "每完成一个", "委托任务", CustomStyle.styleOfWorld,
                            "+2500VB", CustomStyle.styleOfGold));
        }
    }

    // 活动在9.1日自动关闭（无需重启）
    public static boolean isInActivityDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == 2025
                && calendar.get(Calendar.MONTH) >= Calendar.JULY && calendar.get(Calendar.MONTH) < Calendar.SEPTEMBER;
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
        sendMSG(player, Te.s("高温补贴已发放!"));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("暑期活动", CustomStyle.styleOfPower), content);
    }
}
