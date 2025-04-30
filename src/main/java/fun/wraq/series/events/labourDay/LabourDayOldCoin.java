package fun.wraq.series.events.labourDay;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.List;

public class LabourDayOldCoin extends WraqItem {

    public LabourDayOldCoin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        LabourDaySuffix(components);
        components.add(Component.literal(" "));
        components.add(Component.literal(" 辛勤地耕耘，换得了等值之回报").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" 该物品的材质是维瑞阿契最早的材质之一").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static void LabourDaySuffix(List<Component> components) {
        components.add(Component.literal(" 活动").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" · ").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("劳动节").withStyle(ChatFormatting.RED)));
    }

    public static Component getLabourDaySuffix() {
        return Te.s(" 活动", ChatFormatting.LIGHT_PURPLE,
                " · ", ChatFormatting.AQUA,
                "劳动节", ChatFormatting.RED);
    }

    public static final String GET_OLD_SILVER_COIN_COUNT_KEY = "GetOldSilverCoinCount";
    public static void onPlayerKillMob(Player player) {
        if (isInActivityDate() && RandomUtils.nextInt(0, 10000) < 1000) {
            int count = Compute.getTempTag(player).getInt(GET_OLD_SILVER_COIN_COUNT_KEY);
            if (count < 512) {
                if (count > 500) {
                    Compute.sendFormatMSG(player, Te.s("劳动节活动", CustomStyle.styleOfGold),
                            Te.s("今日还可获取", (512 - count) + "个", SpecialEventItems.OldSilverCoin.get()));
                }
                ItemAndRate.send(player, SpecialEventItems.OldSilverCoin.get());
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.OldSilverCoin.get());
                Compute.getTempTag(player).putInt(GET_OLD_SILVER_COIN_COUNT_KEY, count + 1);
            }
        }
    }

    public static void onFinishMobKillEntrustment(Player player, int count) {
        if (isInActivityDate()) {
            if (count <= 20 && count > 0 && count % 5 == 0) {
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.OldGoldCoin.get(), 2);
            }
        }
    }

    public static void refreshSilverCoinGetCount(Player player) {
        if (isInActivityDate()) {
            Compute.getTempTag(player).putInt(GET_OLD_SILVER_COIN_COUNT_KEY, 0);
        }
    }

    public static double getExExpUp() {
        return isInActivityDate() ? 5 : 0;
    }

    public static double getExReasonRecoverRate() {
        return isInActivityDate() ? 1 : 0;
    }

    public static double getExHarvest() {
        return isInActivityDate() ? 0.25 : 0;
    }

    public static void onPlayerLoginTips(Player player) {
        if (isInActivityDate()) {
            Compute.sendFormatMSG(player, Te.s("劳动节", CustomStyle.styleOfGold),
                    Te.s("劳动节活动", CustomStyle.styleOfGold, "进行中!"));
            Compute.sendFormatMSG(player, Te.s("劳动节", CustomStyle.styleOfGold),
                    Te.s(" 1. ", CustomStyle.styleOfGold, "翻倍经验加成", ChatFormatting.LIGHT_PURPLE));
            Compute.sendFormatMSG(player, Te.s("劳动节", CustomStyle.styleOfGold),
                    Te.s(" 2. ", CustomStyle.styleOfGold, "双倍理智回复", CustomStyle.styleOfFlexible));
            Compute.sendFormatMSG(player, Te.s("劳动节", CustomStyle.styleOfGold),
                    Te.s(" 3. ", CustomStyle.styleOfGold, "+25%额外产出", CustomStyle.styleOfGold));
            Compute.sendFormatMSG(player, Te.s("劳动节", CustomStyle.styleOfGold),
                    Te.s(" 4. ", CustomStyle.styleOfGold, "-20%月卡价格", CustomStyle.styleOfWorld));
            Compute.sendFormatMSG(player, Te.s("劳动节", CustomStyle.styleOfGold),
                    Te.s("击杀怪物/完成委托可以获取活动物品来兑换礼包!"));
        }
    }

    // 活动在5.6日00:00自动关闭（无需重启）
    public static boolean isInActivityDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == 2025
                && ((calendar.get(Calendar.MONTH) == Calendar.MAY && calendar.get(Calendar.DAY_OF_MONTH) <= 5)
                || (calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) >= 28));
    }
}
