package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestBracelet extends Item {

    public ForestBracelet() {
        super(new Properties().rarity(CustomStyle.ForestItalic));
        Utils.critRate.put(this, 0.05);
        Utils.maxHealth.put(this, 50d);
        Utils.expUp.put(this, 0.3);
        Utils.defence.put(this, 1d);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ChatFormatting chatFormatting = ChatFormatting.DARK_GREEN;
        stack.setHoverName(Component.literal("森林手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.GRAY)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) ForestSuitDescription.SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
