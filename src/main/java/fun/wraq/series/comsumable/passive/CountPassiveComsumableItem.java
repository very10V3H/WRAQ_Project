package fun.wraq.series.comsumable.passive;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CountPassiveComsumableItem extends WraqItem {

    public final int maxCount;

    public CountPassiveComsumableItem(Properties properties, int maxCount) {
        super(properties.stacksTo(1));
        this.maxCount = maxCount;
    }

    public static final String COUNT_KEY = "COUNT";

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level,
                                List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 剩余可用:",
                maxCount - itemStack.getOrCreateTagElement(Utils.MOD_ID).getInt(COUNT_KEY), ChatFormatting.AQUA,
                "/", maxCount, CustomStyle.styleOfMoon));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static void addCount(ItemStack itemStack, Player player) {
        itemStack.getOrCreateTagElement(Utils.MOD_ID).putInt(COUNT_KEY,
                itemStack.getOrCreateTagElement(Utils.MOD_ID).getInt(COUNT_KEY) + 1);
        CountPassiveComsumableItem item = (CountPassiveComsumableItem) itemStack.getItem();
        if (itemStack.getOrCreateTagElement(Utils.MOD_ID).getInt(COUNT_KEY) >= item.maxCount) {
            Compute.sendFormatMSG(player, Te.s("消耗品", CustomStyle.styleOfGold),
                    Te.s(itemStack, "已使用完毕"));
            itemStack.shrink(1);
            MySound.soundToPlayer(player, SoundEvents.ITEM_BREAK);
        }
    }

    public static Map<String, List<ItemStack>>
            playerInventoryPassiveComsumableItemMap = new HashMap<>();

    public static List<ItemStack> getItemList(Player player) {
        if (playerInventoryPassiveComsumableItemMap.containsKey(Name.get(player))) {
            return playerInventoryPassiveComsumableItemMap.get(Name.get(player));
        }
        return List.of();
    }

    public static void beforeCheckClear(Player player) {
        if (playerInventoryPassiveComsumableItemMap.containsKey(Name.get(player))) {
            getItemList(player).clear();
        } else {
            playerInventoryPassiveComsumableItemMap.put(Name.get(player), new ArrayList<>());
        }
    }

    public static void onCheck(Player player, ItemStack stack) {
        if (stack.getItem() instanceof CountPassiveComsumableItem) {
            getItemList(player).add(stack);
        }
    }
}
