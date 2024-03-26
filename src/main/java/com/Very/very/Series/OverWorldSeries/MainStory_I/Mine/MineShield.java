package com.Very.very.Series.OverWorldSeries.MainStory_I.Mine;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MineShield extends Item{
    private final double ExpUp = 0.3F;
    private final double MaxHealth = 200;
    private final double Defence = 100;

    public MineShield() {
        super(new Properties().rarity(Utils.VolcanoItalic));
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ExpUp.put(this,ExpUp);
        Utils.Defence.put(this,Defence);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.ShieldTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = Utils.styleOfMine;
        stack.setHoverName(Component.literal("精钢圆盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("盾击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("100")).
                append(Component.literal("，将为你提供10%近战攻击增幅。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("沉重之铁").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("受到来自怪物的伤害时，会为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("等级*5")));
        components.add(Component.literal("MineShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
