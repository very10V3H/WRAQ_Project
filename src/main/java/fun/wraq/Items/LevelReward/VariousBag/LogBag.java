package fun.wraq.Items.LevelReward.VariousBag;

import fun.wraq.common.Compute;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LogBag extends Item {

    public LogBag(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("右键使用").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("包含:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Items.OAK_LOG.getDefaultInstance().getDisplayName()).
                append(Component.literal("*256")));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = new ItemStack(Items.OAK_LOG, 256);
            InventoryOperation.giveItemStack(player, itemStack);
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }
}
