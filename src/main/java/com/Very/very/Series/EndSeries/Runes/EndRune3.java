package com.Very.very.Series.EndSeries.Runes;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
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

public class EndRune3 extends Item {
    public EndRune3(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfEnd,ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        Compute.DescriptionPassive(components,Component.literal("归终秘法").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd));
        components.add(Component.literal(" 当你完成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("森林").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfForest)).
                append(Component.literal("/").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                append(Component.literal("火山").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("/").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                append(Component.literal("冰川").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSnow)).
                append(Component.literal("/").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                append(Component.literal("下界").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)));
        components.add(Component.literal(" 的回忆录后，你的对应系列的部分符石的效能将被提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("100%").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)));
        components.add(Component.literal(" 具体效能提升请查询相关资料。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfEnd,ChatFormatting.WHITE);
        components.add(Component.literal("Idea From:showdicker4").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("Runes-End").withStyle(Utils.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.getPersistentData().putInt(StringUtils.EndRune,3);
            Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("末地符石-归终秘法").withStyle(Utils.styleOfEnd).append(Component.literal("已激活").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            data.putString("Owner",player.getName().getString());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
