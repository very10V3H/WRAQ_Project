package com.Very.very.Series.SakuraSeries.SakurMob;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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

public class SakuraPocket extends Item {
    private final Style style = Utils.styleOfSakura;
    public SakuraPocket(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("樱花礼盒").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("樱花的馈赠").withStyle(style));
        components.add(Component.literal(" "));
        components.add(Component.literal("·礼盒中可能含有:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD));
        Item[] Rewards = {
                Moditems.SakuraPetal.get(), Moditems.ForgingStone2.get(),
                Moditems.GoldCoin.get(), Moditems.SakuraReForge.get(),
                Moditems.SakuraPetal.get(), Moditems.SakuraPetal.get(),
                Moditems.SakuraSwordForgingDrawing.get(), Moditems.SakuraArmorHelmet.get()
        };
        double[] RewardsRate = {
                1,0.25,
                0.3,0.1,
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
        components.add(Component.literal("Sakura_Pocket").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Random random = new Random();
            Item[] Rewards = {
                    Moditems.SakuraPetal.get(), Moditems.ForgingStone2.get(),
                    Moditems.GoldCoin.get(), Moditems.SakuraReForge.get(),
                    Moditems.SakuraPetal.get(), Moditems.SakuraPetal.get(),
                    Moditems.SakuraSwordForgingDrawing.get(), Moditems.SakuraArmorHelmet.get()
            };
            double[] RewardsRate = {
                    1,0.25,
                    0.3,0.1,
                    0.08,0.04,
                    0.02,0.02
            };
            for(int i = 0; i < 8; i ++) {
                if (random.nextDouble(1) < RewardsRate[i] * (1 + Compute.PlayerLuckyUp(player))) {
                    Compute.ItemStackGive(player,Rewards[i].getDefaultInstance());
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
