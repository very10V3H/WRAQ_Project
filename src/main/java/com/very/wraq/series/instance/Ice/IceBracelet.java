package com.very.wraq.series.instance.Ice;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class IceBracelet extends Item {
    private final double ExpUp = 1;
    protected double CriticalRate = 0.25F;
    protected double HealReply = 10.0d;
    protected double MaxHealth = 500.0d;
    protected double Defence = 200.0d;
    protected double CoolDown = 0.2F;
    protected double Damage = 100.0d;
    private final Style style = CustomStyle.styleOfIce;
    public IceBracelet(Properties p_41383_) {
        super(p_41383_);
        Utils.ExpUp.put(this, ExpUp);
        Utils.CritRate.put(this, CriticalRate);
        Utils.HealthRecover.put(this, HealReply);
        Utils.MaxHealth.put(this, MaxHealth);
        Utils.Defence.put(this, Defence);
        Utils.AttackDamage.put(this, Damage);
        Utils.CoolDownDecrease.put(this, CoolDown);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("冰霜手环").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(style).append(Component.literal("传说之证").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("IceMemory").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
