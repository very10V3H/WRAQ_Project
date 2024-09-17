package com.very.wraq.series.nether.Power;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitherBonePower extends Item implements ActiveItem {

    public WitherBonePower(Properties p_41383_) {
        super(p_41383_);
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有单位造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("300%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("使周围怪物造成伤害降低20%，持续5s。").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 10);
        ComponentUtils.manaCostDescription(components, 360);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Wither").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 360, true)) {
            PowerLogic.WitherBonePower(player, this);
            PowerLogic.PlayerReleasePowerType(player, 0);
        }
    }
}
