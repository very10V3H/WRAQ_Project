package com.very.wraq.Items.LevelReward.PotionPackets;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

import java.io.IOException;
import java.util.List;

public class PotionBag extends Item {
    private final Component component;
    private final String potion;

    public PotionBag(Properties p_41383_) {
        super(p_41383_);
        this.component = null;
        this.potion = null;
    }


    public PotionBag(Properties properties, Component component, String potion) {
        super(properties);
        this.component = component;
        this.potion = potion;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("右键使用").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("包含:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(component).
                append(Component.literal("药水 *5").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-PotionBag").withStyle(ChatFormatting.AQUA));
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
            PotionData.putString("Potion","vmd:" + potion);
            player.addItem(attackUpPotion);
            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.use(level, player, interactionHand);
    }
}
