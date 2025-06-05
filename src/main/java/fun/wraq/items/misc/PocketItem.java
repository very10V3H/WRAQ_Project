package fun.wraq.items.misc;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PocketItem extends WraqItem {

    private final Item originItem;
    public static Map<Item, Item> map = new HashMap<>();
    public PocketItem(Properties properties, Item originItem) {
        super(properties);
        this.originItem = originItem;
        map.put(originItem, this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(originItem, 64));
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("使用获得", originItem, " * 64", ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static void onRightClick(ItemStack itemStack, Player player) {
        Item item = itemStack.getItem();
        if (map.containsKey(item)) {
            if (itemStack.getCount() == 64) {
                itemStack.shrink(64);
                ItemStack stack = new ItemStack(map.get(item));
                InventoryOperation.giveItemStackWithMSG(player, stack);
                Compute.sendFormatMSG(player, Te.s("装袋", ChatFormatting.YELLOW),
                        Te.s("成功将64个", item, "装袋."));
            }
        }
    }

    public static void addTooltip(ItemStack stack, List<Component> components) {
        if (map.containsKey(stack.getItem())) {
            Item item = map.get(stack.getItem());
            components.add(Te.s("手持64个此物品右键可打包为", item));
        }
    }
}
