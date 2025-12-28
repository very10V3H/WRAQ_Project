package fun.wraq.series.secret;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SecretKey extends WraqItem {

    private final int maxCount;

    private final SecretSeries series;

    private final int tier;

    public SecretKey(Properties properties, int maxCount, SecretSeries series, int tier) {
        super(properties.stacksTo(1));
        this.maxCount = maxCount;
        this.series = series;
        this.tier = tier;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public SecretSeries getSeries() {
        return series;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level,
                                List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("用于开启",
                series.description, "系列", "隐秘箱", ChatFormatting.AQUA));
        components.add(Te.s(" · ", "剩余使用次数 ", getLeftUsedCount(itemStack), ChatFormatting.AQUA,
                " / ", maxCount));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    private static final String USED_COUNT_DATA_KEY = "UsedCount";

    private static int getUsedCount(ItemStack stack) {
        return stack.getOrCreateTagElement(Utils.MOD_ID).getInt(USED_COUNT_DATA_KEY);
    }

    private static void setUsedCount(ItemStack stack, int count) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt(USED_COUNT_DATA_KEY, count);
    }

    private static int getLeftUsedCount(ItemStack stack) {
        if (stack.getItem() instanceof SecretKey secretKey) {
            return secretKey.maxCount - getUsedCount(stack);
        }
        return 0;
    }

    public static void addUsedCount(Player player, ItemStack stack, int count) {
        int leftCount = getLeftUsedCount(stack) - count;
        if (leftCount <= 0) {
            SecretChest.sendMSG(player, Te.s(stack, "的使用次数已耗尽."));
            stack.shrink(1);
        }
        setUsedCount(stack, getUsedCount(stack) + count);
    }
}
