package com.Very.very.Series.OverWorldSeries.MainStory_II.CodeMana;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EffectMana extends Item {
    public EffectMana(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·基础魔符").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("魔符:效果").withStyle(Utils.styleOfMana));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Code").withStyle(Utils.styleOfMana).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
