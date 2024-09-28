package fun.wraq.Items.Money;

import fun.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class security2 extends Item {
    public security2(Properties p_41383_) {
        super(p_41383_);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("价值64VB的维瑞库尤渔业股票").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("Money-Security").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            if (Utils.Security) {
                CompoundTag data = player.getPersistentData();
                if (!data.contains("security2")) data.putDouble("security2", 64 / Utils.security2);
                else data.putDouble("security2", data.getDouble("security2") + 64 / Utils.security2);
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("股市").withStyle(ChatFormatting.GOLD)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("成功购入维瑞库尤渔业股票！").withStyle(ChatFormatting.WHITE)));
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("股市").withStyle(ChatFormatting.GOLD)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("当前拥有维瑞库尤渔业股票数:").withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.3f", data.getDouble("security2"))).withStyle(ChatFormatting.GOLD))));
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                itemStack.setCount(itemStack.getCount() - 1);
                player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
                data.putInt("securityCounts", data.getInt("securityCounts") + 1);
            } else
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("股市").withStyle(ChatFormatting.GOLD)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("股市尚未开盘，暂不能购入股票。").withStyle(ChatFormatting.WHITE)));
        }
        return super.use(level, player, interactionHand);
    }
}
