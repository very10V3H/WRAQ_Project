package fun.wraq.series.overworld.chapter1.Main1Boss;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class FInalCord extends Item {

    public FInalCord(Properties p_41383_) {
        super(p_41383_);
        Utils.expUp.put(this, 1d);
        Utils.critRate.put(this, 0.15);
        Utils.healthRecover.put(this, 2d);
        Utils.maxHealth.put(this, 250d);
        Utils.defence.put(this, 50d);
        Utils.attackDamage.put(this, 20d);
        Utils.coolDownDecrease.put(this, 0.2);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("四元传说之证").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("传说之证").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal("Legend-Star-Fin.").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
