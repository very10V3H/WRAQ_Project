package com.very.wraq.process.lottery;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
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

import java.io.IOException;
import java.util.List;

public class LotteryPrefix extends Item {

    public LotteryPrefix(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("使用以获取称号-").withStyle(ChatFormatting.WHITE).
                append(Component.literal("赌神").withStyle(ChatFormatting.GOLD)));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("使用/vmd prefix来使用新称号吧！").withStyle(ChatFormatting.WHITE));
            player.getPersistentData().putBoolean(StringUtils.LotteryPrefix,true);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

}
