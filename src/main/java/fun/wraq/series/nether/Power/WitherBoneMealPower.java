package fun.wraq.series.nether.Power;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
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

public class WitherBoneMealPower extends Item implements ActiveItem {

    public WitherBoneMealPower(Properties p_41383_) {
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
                append(Component.literal("周围单位造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("800%")));
        components.add(Component.literal(" 并").withStyle(ChatFormatting.WHITE).
                append(Component.literal("移除范围内敌人").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDefence("50%")).
                append(Component.literal(" 持续5s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("扣除自身").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaValue("33%")));
        ComponentUtils.coolDownTimeDescription(components, 10);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        PowerLogic.WitherBoneMealPower(player, this);
        PowerLogic.PlayerReleasePowerType(player, 2);
    }
}
