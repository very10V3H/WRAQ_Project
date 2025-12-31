package fun.wraq.series.events.years;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class YearsSouvenirsTicket extends WraqItem {

    private final int year;

    public YearsSouvenirsTicket(Properties properties, int year) {
        super(properties, false, true);
        this.year = year;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 以此纪念在", "维瑞阿契", ChatFormatting.AQUA, "度过的", year, ChatFormatting.GOLD));
        components.add(Te.s(" 每天", CustomStyle.styleOfMoon, "可以使用一次:"));
        components.add(Te.s(" + " + (year % 2000) + "理智", CustomStyle.styleOfFlexible));
        components.add(Te.s(" + " + year + "vb", CustomStyle.styleOfGold));
        components.add(Te.s(" 5%", ChatFormatting.LIGHT_PURPLE, "概率获取", ModItems.WORLD_SOUL_5, " * 40", ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static final String RECORD_DATE_KEY = "Souvenirs2024RecordKey";

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            try {
                if (canBeReward(player, player.getMainHandItem())) {
                    Reason.addOrCostPlayerReasonValueIgnoreLimit(player, year % 2000);
                    Compute.VBIncomeAndMSGSend(player, year);
                    if (RandomUtils.nextDouble(0, 1) < 0.05) {
                        Tower.givePlayerStar(player, 40, "维瑞阿契" + year + "纪念发行票");
                        Compute.sendFormatMSG(player, Te.s("纪念品", CustomStyle.styleOfGold),
                                Te.s(player.getDisplayName(), "通过", this, "获得了",
                                        ModItems.WORLD_SOUL_5, " * 40", ChatFormatting.AQUA));
                    }
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return super.use(level, player, interactionHand);
    }

    private String getRecordDataKey() {
        return "Souvenirs" + year + "RecordKey";
    }

    private boolean canBeReward(Player player, ItemStack stack) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String currentCalendarString = Compute.CalendarToString(calendar);
        Calendar calendar1;
        CompoundTag playerData = player.getPersistentData();
        CompoundTag stackData = stack.getOrCreateTagElement(Utils.MOD_ID);
        String recordDataKey = getRecordDataKey();
        if (!playerData.contains(recordDataKey)) {
            playerData.putString(recordDataKey, currentCalendarString);
            stackData.putString(recordDataKey, currentCalendarString);
            return true;
        }
        if (playerData.contains(recordDataKey)) {
            calendar1 = Compute.StringToCalendar(playerData.getString(recordDataKey));
            if (calendar.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR)
                    && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)
                    && calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) {
                Compute.sendFormatMSG(player, Te.s("纪念品", CustomStyle.styleOfGold),
                        Te.s("明天再试试吧"));
                return false;
            }
        }
        if (stackData.contains(recordDataKey)) {
            calendar1 = Compute.StringToCalendar(stackData.getString(recordDataKey));
            if (calendar.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR)
                    && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)
                    && calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) {
                Compute.sendFormatMSG(player, Te.s("纪念品", CustomStyle.styleOfGold),
                        Te.s("明天再试试吧"));
                return false;
            }
        }
        playerData.putString(recordDataKey, currentCalendarString);
        stackData.putString(recordDataKey, currentCalendarString);
        return true;
    }
}
