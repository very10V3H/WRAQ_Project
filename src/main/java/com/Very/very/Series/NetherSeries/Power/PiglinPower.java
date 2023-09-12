package com.Very.very.Series.NetherSeries.Power;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
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

public class PiglinPower extends Item {

    public PiglinPower(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            if(player.getPersistentData().getInt("MANA") < 80) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("法术使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                Compute.USE(player,player.getMainHandItem().getItem());
            }
        }
        return super.use(level, player, interactionHand);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(Utils.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("基于周围怪物数量，对周围每个怪物造成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("100%*怪物数量")));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("基于周围玩家数量，提升每个玩家:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("10%*玩家数量")).
                append(Component.literal("以及").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("3%")).
                append(Component.literal("额外攻击力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
        Compute.CoolDownTimeDescription(components,10);
        Compute.EmojiDescriptionManaCost(components,80);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Piglin").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
