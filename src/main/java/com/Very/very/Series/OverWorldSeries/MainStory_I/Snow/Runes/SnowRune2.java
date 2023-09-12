package com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class SnowRune2 extends Item {
    public SnowRune2(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("每过").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("5s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                append(Component.literal("，你的下次攻击将使目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冻结1.5s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                append(Component.literal("并使该目标在被").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("冻结").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                append(Component.literal("时的护甲降低").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.AQUA,ChatFormatting.WHITE);
        components.add(Component.literal("Runes-Snow").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt("snowRune",2);
            player.getPersistentData().putInt("snowRune2",100);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("冰川符石-凛冬之意").withStyle(ChatFormatting.AQUA).append(Component.literal("已激活").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString("Owner",player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
