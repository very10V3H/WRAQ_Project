package com.Very.very.Items.LevelReward.PotionPackets;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CritDamageUpPotionBag extends Item {

    public CritDamageUpPotionBag(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("右键使用").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("包含:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("暴击伤害提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)).
                append(Component.literal("药水 *5").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-PotionBag").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            ItemStack attackUpPotion = Items.POTION.getDefaultInstance();
            attackUpPotion.getOrCreateTagElement(Utils.MOD_ID);
            attackUpPotion.setCount(5);
            CompoundTag PotionData = attackUpPotion.getOrCreateTag();
            PotionData.putString("Potion","vmd:critdamageup_potion");
            player.addItem(attackUpPotion);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack.setCount(itemStack.getCount()-1);
            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
        }
        return super.use(level, player, interactionHand);
    }
}
