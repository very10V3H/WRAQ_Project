package com.Very.very.Series.NetherSeries.Material;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherGemPieceBag2 extends Item{
    public NetherGemPieceBag2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·饰品袋").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("换取中阶下界征讨者胸章。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal(" "));
        components.add(Component.literal("Gems-Nether").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack.setCount(itemStack.getCount() - 1);
            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            ItemStack itemStack1 = Moditems.NetherGem.get().getDefaultInstance();
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.RandomAttributeProvider(itemStack1,1,1,player);
            Compute.FormatBroad(level,Component.literal("饰品").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal(player.getName().getString()+"通过下界饰品袋获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(itemStack1.getDisplayName()));
            Compute.ItemStackGive(player,itemStack1);
        }
        return super.use(level, player, interactionHand);
    }
}

