package com.very.wraq.series.end.recallBooks;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
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

public class BlackForestRecallBook extends Item {
    public BlackForestRecallBook(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·回忆录").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" "));
        components.add(Component.literal("沙岸的回忆").withStyle(CustomStyle.styleOfHusk));
        components.add(Component.literal(" "));
        components.add(Component.literal("Recall-BlackForest").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
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
                Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("先专心回忆这段经历吧！").withStyle(ChatFormatting.WHITE));
            } else {
                if (Utils.huskRecall.recallPlayer == null && Utils.huskRecall.recallCount == -1 && level.equals(level.getServer().getLevel(Level.END))) {
                    Compute.playerItemUseWithRecord(player);
                    Utils.huskRecall.recallCount = 620;
                    Utils.huskRecall.recallPlayer = (ServerPlayer) player;
                    Utils.huskRecall.recallTimeLimit = 12000;
                    Utils.huskRecall.playerName = player.getDisplayName();
                    Utils.huskRecall.style = CustomStyle.styleOfHusk;
                    Utils.huskRecall.zoneName = "沙岸";
                } else {
                    if (!level.equals(level.getServer().getLevel(Level.END))) {
                        Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("在这个维度，似乎不需要回忆什么。").withStyle(ChatFormatting.WHITE));

                    } else {
                        Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("有玩家正在进行这项回忆，请等一下。").withStyle(ChatFormatting.WHITE));
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
