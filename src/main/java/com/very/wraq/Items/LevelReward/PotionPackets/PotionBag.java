package com.very.wraq.Items.LevelReward.PotionPackets;

import com.very.wraq.common.Compute;
import com.very.wraq.process.func.item.InventoryOperation;
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

import java.util.List;

public class PotionBag extends Item {

    private final Item potion;

    public PotionBag(Properties properties, Item potion) {
        super(properties);
        this.potion = potion;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("右键使用").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("获得:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(potion.getDefaultInstance().getDisplayName()).
                append(Component.literal("药水 * 5").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-PotionBag").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = new ItemStack(potion, 5);
            InventoryOperation.itemStackGive(player, itemStack);
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }
}
