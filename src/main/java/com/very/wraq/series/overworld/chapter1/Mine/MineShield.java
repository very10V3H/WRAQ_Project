package com.very.wraq.series.overworld.chapter1.Mine;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MineShield extends Item {
    private final double ExpUp = 0.3F;
    private final double MaxHealth = 200;
    private final double Defence = 100;

    public MineShield() {
        super(new Properties().rarity(CustomStyle.VolcanoItalic));
        Utils.maxHealth.put(this, MaxHealth);
        Utils.expUp.put(this, ExpUp);
        Utils.defence.put(this, Defence);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style MainStyle = CustomStyle.styleOfMine;
        stack.setHoverName(Component.literal("精钢圆盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        shieldAdditionDescription(components);
        Compute.DescriptionPassive(components, Component.literal("沉重之铁").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("受到来自怪物的伤害时，会为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("等级*5")));
        components.add(Component.literal("MineShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void shieldAdditionDescription(List<Component> components) {
        ComponentUtils.descriptionPassive(components, Component.literal("坚盾").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.AQUA).
                append(ComponentUtils.AttributeDescription.Defence("25%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.ManaDefence("25%")));
        Compute.DescriptionPassive(components, Component.literal("盾击").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 基于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("为你至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("75%近战攻击增幅").withStyle(CustomStyle.styleOfPower)));
    }

    public static double defenceEnhance(Player player) {
        if (Utils.shieldTag.containsKey(player.getOffhandItem().getItem())) {
            return 1.25;
        }
        return 1;
    }
}
