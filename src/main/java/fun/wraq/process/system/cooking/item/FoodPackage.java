package fun.wraq.process.system.cooking.item;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.cooking.CookingValue;
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
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoodPackage extends WraqItem {

    private final int lowerBoundary;
    private final int upperBoundary;
    public FoodPackage(Properties properties, int lowerBoundary, int upperBoundary) {
        super(properties);
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("随机获得4种价值在", lowerBoundary + "~" + upperBoundary, ChatFormatting.GOLD,
                "的食材 * 8"));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            List<Item> items = getRangeCookingItems(lowerBoundary, upperBoundary);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = new ItemStack(items.get(RandomUtils.nextInt(0, items.size())), 8);
                InventoryOperation.giveItemStackWithMSG(player, stack);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static List<Item> getRangeCookingItems(int lowerBoundary, int upperBoundary) {
        return CookingItems.getAllSellingItems().
                stream().filter(item -> CookingValue.getIngredientValue(item) > lowerBoundary
                        && CookingValue.getIngredientValue(item) < upperBoundary)
                .toList();
    }
}
