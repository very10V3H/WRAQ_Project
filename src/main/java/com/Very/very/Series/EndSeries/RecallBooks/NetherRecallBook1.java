package com.Very.very.Series.EndSeries.RecallBooks;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherRecallBook1 extends Item {
    public NetherRecallBook1(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·回忆录").withStyle(Utils.styleOfEnd));
        components.add(Component.literal(" "));
        components.add(Component.literal("下界的回忆-其一").withStyle(Utils.styleOfNether));
        components.add(Component.literal(" "));
        components.add(Component.literal("Recall-Nether").withStyle(Utils.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (Compute.RecallPlayerCheck((ServerPlayer) player)) {
                Compute.FormatMSGSend(player,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                        Component.literal("先专心回忆这段经历吧！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                if (Utils.NetherRecall.RecallPlayer == null && Utils.NetherRecall.RecallCount == -1 && level.equals(level.getServer().getLevel(Level.END))) {
                    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    itemStack.setCount(itemStack.getCount() - 1);
                    player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
                    Utils.NetherRecall.RecallCount = 620;
                    Utils.NetherRecall.RecallPlayer = (ServerPlayer) player;
                    Utils.NetherRecall.RecallTimeLimit = 12000;
                }
                else {
                    if (!level.equals(level.getServer().getLevel(Level.END))) {
                        Compute.FormatMSGSend(player,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal("在这个维度，似乎不需要回忆什么。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

                    }
                    else {
                        Compute.FormatMSGSend(player,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal("有玩家正在进行这项回忆，请等一下。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
