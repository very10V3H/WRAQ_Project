package com.very.wraq.series.overWorld.MainStory_I.Volcano;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoBracelet extends Item{
    private final double ExpUp = 0.3F;
    private final double CriticalRate = 0.05F;
    private final double MaxHealth = 50.0d;

    private final double ExDamage = 10;
    public VolcanoBracelet() {
        super(new Properties().rarity(Utils.VolcanoItalic));
        Utils.CritRate.put(this,CriticalRate);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ExpUp.put(this,ExpUp);

        Utils.AttackDamage.put(this,ExDamage);

        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        ChatFormatting chatFormatting = ChatFormatting.YELLOW;
        stack.setHoverName(Component.literal("火山手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        VolcanoSuitDescription.VolcanoSuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        components.add(Component.literal("Volcano_Bracelet").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
