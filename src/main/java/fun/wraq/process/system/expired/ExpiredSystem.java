package fun.wraq.process.system.expired;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.tp.TpPass;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 用于控制物品过期
 */
public class ExpiredSystem {

    /**
     * 过期类型
     */
    public enum ExpiredType {
        /**
         * 过期后消失（默认）
         */
        DISAPPEARED,
        /**
         * 过期后失效（不计算属性/主动无效果）
         */
        DISABLED
    }

    public static final String EXPIRED_TYPE_DATA_KEY = "ExpiredType";

    public static void setExpiredType(ItemStack stack, ExpiredType type) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putString(EXPIRED_TYPE_DATA_KEY, type.name());
    }

    public static ExpiredType getExpiredType(ItemStack stack) {
        CompoundTag tag = stack.getTagElement(Utils.MOD_ID);
        return tag == null ? ExpiredType.DISAPPEARED : ExpiredType.valueOf(tag.getString(EXPIRED_TYPE_DATA_KEY));
    }

    public static final String EXPIRED_DATE_DATA_KEY = "ExpiredDate";

    public static void setStackExpiredDate(ItemStack stack, Calendar expiredDate) {
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        tag.putString(EXPIRED_DATE_DATA_KEY, Compute.castCalendarToString(expiredDate));
    }

    public static @Nullable Calendar getStackExpiredDate(ItemStack stack) {
        if (stack.getTagElement(Utils.MOD_ID) == null) {
            return null;
        }
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (!tag.contains(EXPIRED_DATE_DATA_KEY)) {
            return null;
        }
        return Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
    }

    public static void setStackExpiredDate(ItemStack stack, int hours, ExpiredType expiredType) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        setStackExpiredDate(stack, calendar);
        setExpiredType(stack, expiredType);
    }

    public static boolean checkValid(ItemStack stack) {
        boolean valid = isExpiredDateValid(stack);
        if (!valid) {
            ExpiredType expiredType = getExpiredType(stack);
            if (expiredType.equals(ExpiredType.DISAPPEARED)) {
                stack.shrink(stack.getCount());
            }
            return false;
        }
        return true;
    }

    private static boolean isExpiredDateValid(ItemStack stack) {
        Calendar calendar = Calendar.getInstance();
        CompoundTag tag = stack.getTagElement(Utils.MOD_ID);
        if (tag == null) {
            return true;
        }
        if (tag.contains(EXPIRED_DATE_DATA_KEY)) {
            Calendar recordDate = Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
            return recordDate.after(calendar);
        } else {
            return true;
        }
    }

    public static boolean checkMainHandValid(Player player) {
        return checkValid(player.getMainHandItem());
    }

    public static void addExpiredDateTooltips(ItemStack stack, List<Component> components) {
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (stack.getItem() instanceof TpPass) {
            return;
        }
        if (tag.contains(EXPIRED_DATE_DATA_KEY)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar expiredDate = Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
            ExpiredType expiredType = getExpiredType(stack);
            if (isExpiredDateValid(stack)) {
                if (expiredType.equals(ExpiredType.DISAPPEARED)) {
                    components.add(Te.s(" 在",
                            dateFormat.format(expiredDate.getTime()), ChatFormatting.AQUA, "前有效"));
                } else {
                    components.add(Te.s(" 在",
                            dateFormat.format(expiredDate.getTime()), CustomStyle.styleOfCastle, "后失效."));
                    components.add(Te.s(" 失效后属性、主动不生效，物品不消失.", ChatFormatting.GREEN));
                }
            } else {
                components.add(Te.s(" 已失效.", ChatFormatting.RED));
                if (expiredType.equals(ExpiredType.DISAPPEARED)) {
                    components.add(Te.s(" 在", ChatFormatting.STRIKETHROUGH,
                            dateFormat.format(expiredDate.getTime()), ChatFormatting.AQUA, ChatFormatting.STRIKETHROUGH,
                            "前有效", ChatFormatting.STRIKETHROUGH));
                } else {
                    components.add(Te.s(" 失效后属性、主动不生效，物品不消失.", ChatFormatting.GREEN));
                }
            }
        }
    }

    public static void addTradeScreenTooltip(ItemStack itemStack, List<Component> components) {
        Item item = itemStack.getItem();
        ExpiredType expiredType = ExpiredInfo.getExpiredType(item);
        if (expiredType != null && getStackExpiredDate(itemStack) == null) {
            components.add(Te.s(" 在购买后的",
                    ExpiredInfo.getExpiredHour(item) + "小时", ChatFormatting.AQUA, "过期."));
            if (expiredType.equals(ExpiredType.DISABLED)) {
                components.add(Te.s(" 过期后属性失效、主动失效、物品不消失.", ChatFormatting.GREEN));
            }
        }
    }

    /**
     * 在与村民交易中处理物品的过期信息
     * @param stack 处理的ItemStack
     */
    public static void handleTradeBuyProduct(ItemStack stack) {
        Item item = stack.getItem();
        ExpiredType expiredType = ExpiredInfo.getExpiredType(item);
        if (expiredType == null) {
            return;
        }
        setStackExpiredDate(stack, ExpiredInfo.getExpiredHour(item), expiredType);
    }
}
