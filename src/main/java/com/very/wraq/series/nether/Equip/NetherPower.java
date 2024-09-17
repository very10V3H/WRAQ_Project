package com.very.wraq.series.nether.Equip;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.nether.Equip.Armor.NetherSuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherPower extends Item {
    protected double ManaDamage = 20;
    protected double ManaBreakDenfence = 0.2f;
    protected double ManaReply = 5;
    protected double SpeedUp = 0.2f;
    protected double CoolDown = 0.1f;

    public NetherPower(Properties p_41383_) {
        super(p_41383_);
        Utils.attackDamage.put(this, 30d);
        Utils.defencePenetration.put(this, 0.2);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.manaPenetration.put(this, ManaBreakDenfence);
        Utils.manaRecover.put(this, ManaReply);
        Utils.movementSpeedWithoutBattle.put(this, SpeedUp);
        Utils.coolDownDecrease.put(this, CoolDown);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("下界能量矩阵").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("能量矩阵").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfNether, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfNether, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        NetherSuitDescription.SuitDescription(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfNether, ChatFormatting.WHITE);
        components.add(Component.literal("Nether-Star").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
