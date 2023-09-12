package com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.BossItems;

import com.Very.very.ValueAndTools.Compute;
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

public class ForestBossPocket extends Item {
    public ForestBossPocket(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料/次元口袋").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("森罗能量凝聚物").withStyle(Utils.styleOfHealth));
        components.add(Component.literal(" "));
        components.add(Component.literal("·将手伸入次元口袋，你将得到:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD));
        Item[] Rewards = {
                Moditems.ForestRune.get(), Moditems.ForgingStone0.get(),
                Moditems.GoldCoin.get(), Moditems.ForestBossGem.get(),
                Moditems.ForestBossCore.get(), Moditems.ForestBossCore.get(),
                Moditems.ForestBossSword.get(), Moditems.ForestBossCentral.get()
        };
        double[] RewardsRate = {
                1,0.25,
                0.2,0.1,
                0.08,0.04,
                0.02,0.02
        };
        for (int i = 0; i < 8; i ++) {
            components.add(Component.literal("  " + (i+1) + ".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                    append(Rewards[i].getDefaultInstance().getDisplayName()).
                    append(Component.literal(" *1").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(" (" + String.format("%.0f%%",RewardsRate[i] * 100) + ")")));
        }
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-ForestBoss").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Random random = new Random();
            Item[] Rewards = {
                    Moditems.ForestRune.get(), Moditems.ForgingStone0.get(),
                    Moditems.GoldCoin.get(), Moditems.ForestBossGem.get(),
                    Moditems.ForestBossCore.get(), Moditems.ForestBossCore.get(),
                    Moditems.ForestBossSword.get(), Moditems.ForestBossCentral.get()
            };
            double[] RewardsRate = {
                    1,0.25,
                    0.2,0.1,
                    0.08,0.04,
                    0.02,0.02
            };
            for(int i = 0; i < 8; i ++) {
                if (random.nextDouble(1) < RewardsRate[i] * (1 + Compute.PlayerLuckyUp(player))) {
                    Compute.FormatMSGSend(player,Component.literal("次元能量").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEntropy),
                            Component.literal("你通过次元口袋获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(Rewards[i].getDefaultInstance().getDisplayName()));
                    Compute.ItemStackGive(player,Rewards[i].getDefaultInstance());
                }
            }
            ItemStack itemStack = player.getItemInHand(interactionHand);
            itemStack.setCount(itemStack.getCount() - 1);
            player.setItemInHand(interactionHand,itemStack);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
