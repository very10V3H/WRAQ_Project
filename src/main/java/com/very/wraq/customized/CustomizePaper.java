package com.very.wraq.customized;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
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

public class CustomizePaper extends Item {

    private final Item item;

    public CustomizePaper(Properties p_41383_, Item item) {
        super(p_41383_);
        this.item = item;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 右键获得: ").withStyle(ChatFormatting.GOLD).
                append(item.getDefaultInstance().getDisplayName()));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            ItemStack itemStack = item.getDefaultInstance();
            Compute.formatBroad(level, Component.literal("定制").withStyle(CustomStyle.styleOfSpring),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal(" 对维瑞阿契做出了杰出贡献！获得了: ").withStyle(ChatFormatting.AQUA)).
                            append(itemStack.getDisplayName()));
            Compute.itemStackGive(player, itemStack);
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
