package fun.wraq.process.system.tp;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TpPass extends WraqItem {

    public final int effectiveDays;
    public TpPass(Properties properties, int effectiveDays) {
        super(properties.stacksTo(1));
        this.effectiveDays = effectiveDays;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level,
                                List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Te.s(" 由天空城发行的", "传送中枢", CustomStyle.styleOfEnd, "通票"));
        components.add(Te.s(" 可在有效期内无限次使用", "传送中枢", CustomStyle.styleOfEnd));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CompoundTag tag = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (tag.contains(EXPIRED_DATE_DATA_KEY)) {
            Calendar expiredDate = Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
            if (isValid(itemStack)) {
                components.add(Te.s(" 在",
                        dateFormat.format(expiredDate.getTime()), ChatFormatting.AQUA, "前有效"));
            } else {
                components.add(Te.s(" 在", ChatFormatting.STRIKETHROUGH,
                        dateFormat.format(expiredDate.getTime()), ChatFormatting.AQUA, ChatFormatting.STRIKETHROUGH,
                        "前有效", ChatFormatting.STRIKETHROUGH));
                components.add(Te.s(" 已失效.", ChatFormatting.RED, "可在票务员处回收"));
            }
        } else {
            components.add(Te.s(" 在首次使用后的", effectiveDays + "天内有效", ChatFormatting.AQUA));
        }
    }

    public int getEffectiveDays() {
        return effectiveDays;
    }

    public static final String EXPIRED_DATE_DATA_KEY = "ExpiredDate";
    public static boolean isValid(ItemStack stack) {
        Calendar calendar = Calendar.getInstance();
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (tag.contains(EXPIRED_DATE_DATA_KEY)) {
            Calendar recordDate = Compute.castStringToCalendar(tag.getString(EXPIRED_DATE_DATA_KEY));
            return recordDate.after(calendar);
        } else {
            return true;
        }
    }

    public static ItemStack playerHasValidTpPass(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof TpPass) {
                if (isValid(stack)) {
                    return stack;
                }
            }
        }
        return null;
    }

    public static void setExpiredDate(ItemStack stack) {
        Calendar calendar = Calendar.getInstance();
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        TpPass tpPass = (TpPass) stack.getItem();
        if (!tag.contains(EXPIRED_DATE_DATA_KEY)) {
            calendar.add(Calendar.DATE, tpPass.getEffectiveDays());
            tag.putString(EXPIRED_DATE_DATA_KEY, Compute.castCalendarToString(calendar));
        }
    }
}
