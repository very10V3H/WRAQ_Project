package com.very.wraq.series.overworld.sakuraSeries.SakuraMob;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class SakuraPocket extends Item {
    private final Style style = CustomStyle.styleOfSakura;

    public SakuraPocket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("樱花礼盒").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("樱花的馈赠").withStyle(style));
        components.add(Component.literal(" "));
        components.add(Component.literal("·礼盒中可能含有:").withStyle(ChatFormatting.GOLD));
        Item[] Rewards = {
                ModItems.SakuraPetal.get(), ModItems.ForgingStone2.get(),
                ModItems.goldCoin.get(),
                ModItems.SakuraPetal.get(), ModItems.SakuraPetal.get(),
                ModItems.SakuraArmorHelmet.get()
        };
        double[] RewardsRate = {
                1, 0.25,
                0.3,
                0.08, 0.04,
                0.02
        };
        for (int i = 0; i < Rewards.length; i++) {
            components.add(Component.literal("  " + (i + 1) + ".").withStyle(ChatFormatting.GRAY).
                    append(Rewards[i].getDefaultInstance().getDisplayName()).
                    append(Component.literal(" *1").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(" (" + String.format("%.0f%%", RewardsRate[i] * 100) + ")")));
        }
        components.add(Component.literal(" "));
        components.add(Component.literal("Sakura_Pocket").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Item[] Rewards = {
                    ModItems.SakuraPetal.get(), ModItems.ForgingStone2.get(),
                    ModItems.goldCoin.get(),
                    ModItems.SakuraPetal.get(), ModItems.SakuraPetal.get(),
                    ModItems.SakuraArmorHelmet.get()
            };
            double[] RewardsRate = {
                    1, 0.25,
                    0.3,
                    0.08, 0.04,
                    0.02
            };

            for (int i = 0; i < Rewards.length; i++) {
                InventoryOperation.giveItemStackByRate(Rewards[i].getDefaultInstance(), RewardsRate[i], player);
            }

            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
