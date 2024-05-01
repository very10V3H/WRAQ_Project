package com.very.wraq.series.end.runes;

import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class EndRune1 extends Item {
    public EndRune1(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfEnd,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        Compute.DescriptionPassive(components,Component.literal("能量搬移").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会直接消耗全部").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("并造成等同于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法力值/法力消耗").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("额外").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        Compute.CoolDownTimeDescription(components,12);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfEnd,ChatFormatting.WHITE);
        components.add(Component.literal("Runes-End").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt(StringUtils.EndRune,1);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("末地符石-能量搬移").withStyle(CustomStyle.styleOfEnd).append(Component.literal("已激活").withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString(InventoryCheck.owner,player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
