package fun.wraq.series.overworld.castle;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class TreeBracelet extends Item implements ICurioItem {

    public TreeBracelet(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, 400d);
        Utils.curiosList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("魔能汲取").withStyle(style));
        components.add(Component.literal(" 使你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("汲取").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("目标2s").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，每0.5s造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25倍").withStyle(style)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(style)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfCastle(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
