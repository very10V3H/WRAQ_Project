package com.very.wraq.series.gems.MainStoryIII;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherSkeletonGem extends Item {
    private final double DefencePenetration0 = 100;
    private final double MaxHealth = -500;

    public NetherSkeletonGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefencePenetration0.put(StringUtils.GemName.NetherSkeletonGem, DefencePenetration0);
        Utils.gemsMaxHealth.put(StringUtils.GemName.NetherSkeletonGem, MaxHealth);
        Utils.defencePenetration0.put(this, DefencePenetration0);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfWither;
        components.add(Component.literal("用精细下界骷髅粉末打造而成。").withStyle(style));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefencePenetration0(components,DefencePenetration0);
        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);*/
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
