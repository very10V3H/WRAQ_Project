package fun.wraq.series.instance.series.ice;

import fun.wraq.common.attribute.BasicAttributeDescription;
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

import javax.annotation.Nullable;
import java.util.List;

public class IceBracelet extends Item {
    private final Style style = CustomStyle.styleOfIce;

    public IceBracelet(Properties p_41383_) {
        super(p_41383_);
        Utils.expUp.put(this, 1d);
        Utils.critRate.put(this, 0.15);
        Utils.critDamage.put(this, 0.25);
        Utils.healthRecover.put(this, 10d);
        Utils.maxHealth.put(this, 500d);
        Utils.defence.put(this, 2d);
        Utils.attackDamage.put(this, 100d);
        Utils.coolDownDecrease.put(this, 0.2);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("冰霜手环").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(style).append(Component.literal("传说之证").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("IceMemory").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
