package com.very.wraq.series.overworld.chapter1.forest;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
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
    private final double ExpUp = 0.3F;
    private final double CriticalRate = 0.05F;
    private final double MaxHealth = 50.0d;

    private final double Defence = 25F;

    public ForestBracelet() {
        super(new Properties().rarity(CustomStyle.ForestItalic));
        Utils.critRate.put(this, CriticalRate);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.expUp.put(this, ExpUp);
        Utils.defence.put(this, Defence);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ChatFormatting chatFormatting = ChatFormatting.DARK_GREEN;
        stack.setHoverName(Component.literal("森林手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        if (Screen.hasShiftDown()) ForestSuitDescription.SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        Compute.DescriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        components.add(Component.literal("Forest_Bracelet").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
