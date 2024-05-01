package com.very.wraq.process.lottery;

import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
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
import java.util.Random;

public class NewLotteries extends Item {

    public record Loot(ItemStack itemStack, double rate) {}
    private final List<Loot> loots;
    public NewLotteries(Properties p_41383_, List<Loot> loots) {
        super(p_41383_);
        this.loots = loots;

        // 调整未满100%概率
        double frontRate = 0;
        for (int i = 0 ; i < loots.size() - 1 ; i++) frontRate += loots.get(i).rate;
        loots.set(loots.size() - 1, new Loot(loots.get(loots.size() - 1).itemStack, 1 - frontRate));
    }

    private int lootSerialNum() {
        Random rand = new Random();
        double fullRate = rand.nextDouble();
        for (int i = 0 ; i < loots.size() ; i++) {
            Loot loot = loots.get(i);
            fullRate -= loot.rate;
            if (fullRate <= 0) return i;
        }
        return loots.size() - 1;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 奖池:").withStyle(ChatFormatting.LIGHT_PURPLE));
        for (int i = 0 ; i < loots.size() ; i++) {
            Loot loot = loots.get(i);
            components.add(Component.literal(" " + (i + 1) + ".").withStyle(ChatFormatting.AQUA).
                    append(loot.itemStack.getDisplayName()).
                    append(Component.literal(" *" + loot.itemStack.getCount()).withStyle(ChatFormatting.AQUA)).
                    append(Component.literal(" 「" + String.format("%.2f%%", loot.rate * 100) + "」").withStyle(ChatFormatting.LIGHT_PURPLE)));
        }
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int serialNum = lootSerialNum();
            Loot loot = loots.get(serialNum);
            ItemStack itemStack = loot.itemStack;
            if (loot.rate <= 0.05) {
                Compute.FormatBroad(level, Component.literal(""),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 通过 ").withStyle(ChatFormatting.WHITE)).
                                append(this.getDefaultInstance().getDisplayName()).
                                append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()).
                                append(Component.literal(" *" + itemStack.getCount()).withStyle(ChatFormatting.AQUA)));
            }

            if (itemStack.is(ModItems.OldSilverCoin.get()) || itemStack.is(ModItems.OldGoldCoin.get()))
                InventoryCheck.addOwnerTagToItemStack(player, itemStack); // 为部分物品添加绑定tag

            try {
                Compute.ItemStackGive(player, new ItemStack(itemStack.getItem(), itemStack.getCount()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return super.use(level, player, interactionHand);
    }
}
