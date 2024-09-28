package fun.wraq.series.overworld.chapter1.waterSystem;

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

public class LakeCord extends Item {
    private final double ExpUp = 0.5f;
    protected double CriticalRate = 0.05F;
    protected double MaxHealth = 100.0d;
    private final double CoolDown = 0.2f;

    public LakeCord(Properties p_41383_) {
        super(p_41383_);
        Utils.expUp.put(this, ExpUp);
        Utils.critRate.put(this, CriticalRate);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.coolDownDecrease.put(this, CoolDown);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("四元之证-澈源").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("传说之证").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.BLUE, ChatFormatting.WHITE);
        components.add(Component.literal("Lake-Star").withStyle(ChatFormatting.DARK_BLUE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
