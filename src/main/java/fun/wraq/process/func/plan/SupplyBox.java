package fun.wraq.process.func.plan;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.process.system.tower.Tower;
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

import java.util.ArrayList;
import java.util.List;

public class SupplyBox extends Item {
    private final List<ItemStack> supplyItems = new ArrayList<>();

    private boolean isContentBounded = true;

    public SupplyBox(Properties properties, List<ItemStack> supplyItems, boolean isContentBounded) {
        super(properties);
        this.supplyItems.addAll(supplyItems);
        this.isContentBounded = isContentBounded;
    }

    public SupplyBox(Properties properties, List<ItemStack> supplyItems) {
        this(properties, supplyItems, true);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("内含:").withStyle(ChatFormatting.AQUA));
        for (int i = 0; i < supplyItems.size(); i++) {
            ItemStack supplyItem = supplyItems.get(i);
            components.add(Component.literal(" " + (i + 1) + " ").withStyle(ChatFormatting.AQUA).
                    append(supplyItem.getDisplayName()).
                    append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("" + supplyItem.getCount()).withStyle(ChatFormatting.AQUA)));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            supplyItems.forEach(supply -> {
                ItemStack itemStack = new ItemStack(supply.getItem(), supply.getCount());
                if (isContentBounded) {
                    InventoryCheck.addOwnerTagToItemStack(player, itemStack);
                }
                if (itemStack.is(ModItems.WORLD_SOUL_5.get())) {
                    Tower.givePlayerStar(player, itemStack.getCount(), "月卡/计划补给箱");
                } else {
                    InventoryOperation.giveItemStack(player, itemStack);
                }
            });
            Compute.playerItemUseWithRecord(player);
            NewLotteries.addPlayerOpenLotteryTimes(player, this);
        }
        return super.use(level, player, interactionHand);
    }

    public List<ItemStack> getSupplyItems() {
        return supplyItems;
    }
}
