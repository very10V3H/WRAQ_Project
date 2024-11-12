package fun.wraq.process.func.power;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class WraqPower extends Item implements ActiveItem {
    public WraqPower(Properties p_41383_) {
        super(p_41383_);
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        if (getActiveName() != null) {
            Compute.DescriptionActive(components, getActiveName());
        }
        components.addAll(getAdditionalComponents());
        ComponentUtils.coolDownTimeDescription(components, getCoolDownSecond());
        ComponentUtils.manaCostDescription(components, (int) getManaCost());
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        if (getSuffix() != null) {
            components.add(getSuffix());
        }
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public abstract Component getActiveName();
    public abstract List<Component> getAdditionalComponents();
    public abstract int getCoolDownSecond();
    public abstract double getManaCost();
    public abstract Component getSuffix();
    public abstract void release(Player player);

    @Override
    public void active(Player player) {
        release(player);
        PowerLogic.playerReleasePower(player);
        PowerLogic.playerLastTimeReleasePower.put(player, this);
        PowerLogic.playerLastTimeReleasePowerManaCost.put(player, getManaCost());
    }

    @Override
    public double manaCost(Player player) {
        return getManaCost();
    }
}
