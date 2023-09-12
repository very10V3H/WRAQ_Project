package com.Very.very.Items.LevelReward.VariousBag;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
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
import java.util.Random;

public class GoldCoinBag extends Item {

    public GoldCoinBag(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("右键使用").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("包含:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Moditems.GoldCoin.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("*(2~5)")));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-GoldCoinBag").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            ItemStack itemStack = Moditems.GoldCoin.get().getDefaultInstance();
            Random random = new Random();
            itemStack.setCount(random.nextInt(2,5));
            player.addItem(itemStack);
            ItemStack itemStack0 = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack0.setCount(itemStack0.getCount()-1);
            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack0);
        }
        return super.use(level, player, interactionHand);
    }
}
