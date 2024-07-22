package com.very.wraq.Items.Forging;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class randomsword extends SwordItem {
    public randomsword(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties());
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);

        Utils.swordTag.put(this, 1d);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if (!level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (!itemStack.getOrCreateTagElement(Utils.MOD_ID).contains("attackdamage")) {
                Random r = new Random();
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putDouble("attackdamage", r.nextDouble(100d));
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putDouble("breakDefence", r.nextDouble(0.5F));
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putDouble("criticalrate", r.nextDouble(0.5F));
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putDouble("criticaldamage", r.nextDouble(0.5F));
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putDouble("healsteal", r.nextDouble(0.2F));
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putDouble("speedup", r.nextDouble(0.5F));
                itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putBoolean("randomsword", true);
            }
        }
        return super.use(level, player, useHand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("··········································").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("BASIC:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }
}
