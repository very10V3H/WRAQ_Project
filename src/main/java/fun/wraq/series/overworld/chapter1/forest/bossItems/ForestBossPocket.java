package fun.wraq.series.overworld.chapter1.forest.bossItems;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
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
        components.add(Component.literal("森罗能量凝聚物").withStyle(CustomStyle.styleOfHealth));
        components.add(Component.literal(" "));
        components.add(Component.literal("·将手伸入次元口袋，你将得到:").withStyle(ChatFormatting.GOLD));
        Item[] Rewards = {
                ModItems.ForestRune.get(), ModItems.ForgingStone0.get(),
                ModItems.GOLD_COIN.get(), ModItems.ForestBossGem.get(),
                ModItems.ForestBossCore.get(), ModItems.ForestBossCore.get(),
                ModItems.ForestBossSwordForgeDraw.get(), ModItems.ForestBossCentral.get()
        };
        double[] RewardsRate = {
                1, 0.25,
                0.2, 0.1,
                0.08, 0.04,
                0.04, 0.04
        };
        for (int i = 0; i < 8; i++) {
            components.add(Component.literal("  " + (i + 1) + ".").withStyle(ChatFormatting.GRAY).
                    append(Rewards[i].getDefaultInstance().getDisplayName()).
                    append(Component.literal(" *1").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(" (" + String.format("%.0f%%", RewardsRate[i] * 100) + ")")));
        }
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-ForestBoss").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Random random = new Random();
            Item[] Rewards = {
                    ModItems.ForestRune.get(), ModItems.ForgingStone0.get(),
                    ModItems.GOLD_COIN.get(), ModItems.ForestBossGem.get(),
                    ModItems.ForestBossCore.get(), ModItems.ForestBossCore.get(),
                    ModItems.ForestBossSwordForgeDraw.get(), ModItems.ForestBossCentral.get()
            };
            double[] RewardsRate = {
                    1, 0.25,
                    0.2, 0.1,
                    0.08, 0.04,
                    0.04, 0.04
            };
            for (int i = 0; i < 8; i++) {
                if (random.nextDouble(1) < RewardsRate[i]) {
                    Compute.sendFormatMSG(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfEntropy),
                            Component.literal("你通过次元口袋获得了").withStyle(ChatFormatting.WHITE).
                                    append(Rewards[i].getDefaultInstance().getDisplayName()));
                    InventoryOperation.giveItemStack(player, Rewards[i].getDefaultInstance());
                    Compute.formatBroad(level, Component.literal("次元").withStyle(CustomStyle.styleOfEntropy),
                            Component.literal("玩家").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("通过次元口袋获得了").withStyle(ChatFormatting.WHITE)).
                                    append(Rewards[i].getDefaultInstance().getDisplayName()));
                }
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
