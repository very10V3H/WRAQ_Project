package com.very.wraq.process.func.plan;

import com.very.wraq.process.system.lottery.NewLotteries;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplyBox extends Item {
    private final List<ItemStack> supplyItems = new ArrayList<>();

    public SupplyBox(Properties properties, List<ItemStack> supplyItems) {
        super(properties);
        this.supplyItems.addAll(supplyItems);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("内含:").withStyle(ChatFormatting.AQUA));
        for (int i = 0; i < supplyItems.size(); i++) {
            ItemStack supplyItem = supplyItems.get(i);
            components.add(Component.literal(" " + i + " ").withStyle(ChatFormatting.AQUA).
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
                if (itemStack.is(ModItems.worldSoul5.get())) {
                    try {
                        Tower.givePlayerStar(player, itemStack.getCount(), "月卡/计划补给箱");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Compute.itemStackGive(player, itemStack);
                }
            });
            Compute.playerItemUseWithRecord(player);
            try {
                NewLotteries.addPlayerOpenLotteryTimes(player, this);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public List<ItemStack> getSupplyItems() {
        return supplyItems;
    }
}
