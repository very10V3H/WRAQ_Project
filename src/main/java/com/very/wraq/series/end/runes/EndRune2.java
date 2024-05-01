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

public class EndRune2 extends Item {
    public EndRune2(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfEnd,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        Compute.DescriptionPassive(components,Component.literal("末影龙息").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" 你的下次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将在命中目标后再原地留下").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("龙息").withStyle(CustomStyle.styleOfEnd)));
        components.add(Component.literal(" 龙息").withStyle(CustomStyle.styleOfEnd).
                append(Component.literal("将会对在其中的怪物每0.5s造成一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("适应性伤害").withStyle(CustomStyle.styleOfSea)));
        Compute.CoolDownTimeDescription(components,12);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfEnd,ChatFormatting.WHITE);
        components.add(Component.literal("Idea From:LengCheng").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("Runes-End").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt(StringUtils.EndRune,2);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("末地符石-末影龙息").withStyle(CustomStyle.styleOfEnd).append(Component.literal("已激活").withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString(InventoryCheck.owner,player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
