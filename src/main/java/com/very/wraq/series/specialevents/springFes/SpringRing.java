package com.very.wraq.series.specialevents.springFes;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class SpringRing extends Item implements ICurioItem {

    private final int level;

    public SpringRing(Properties p_41383_, int level) {
        super(p_41383_);
        this.level = level;
        Utils.attackDamage.put(this, Attack[level]);
        Utils.manaDamage.put(this, Mana[level]);
        Utils.defencePenetration0.put(this, DefencePenetration0[level]);
        Utils.manaPenetration0.put(this, ManaPenetration0[level]);
        Utils.expUp.put(this, ExpUp[level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    private final double[] Attack = {
            20, 40, 60, 80
    };
    private final double[] Mana = {
            40, 80, 120, 160
    };
    private final double[] DefencePenetration0 = {
            35, 70, 105, 140
    };
    private final double[] ManaPenetration0 = {
            25, 65, 95, 130
    };
    private final double[] ExpUp = {
            0.3, 0.5, 0.7, 1.0
    };
    private final int[] LevelRequire = {
            20, 40, 60, 80
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        ComponentUtils.DescriptionOfBasic(components);
        components.add(Component.literal("多件金龙戒指仅会生效最后装备的一件效果").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal(" 等级需求:" + LevelRequire[this.level]).withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.PlayerSpringRingAttackAttribute.put((Player) slotContext.entity(), Attack[this.level]);
        Utils.PlayerSpringRingManaAttackAttribute.put((Player) slotContext.entity(), Mana[this.level]);
        Utils.PlayerSpringRingDefencePenetration0Attribute.put((Player) slotContext.entity(), DefencePenetration0[this.level]);
        Utils.PlayerSpringRingManaPenetration0Attribute.put((Player) slotContext.entity(), ManaPenetration0[this.level]);
        Utils.PlayerSpringRingExpUpAttribute.put((Player) slotContext.entity(), ExpUp[this.level]);
        Utils.PlayerSpringRingLevelRequire.put((Player) slotContext.entity(), LevelRequire[this.level]);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.PlayerSpringRingAttackAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringRingManaAttackAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringRingDefencePenetration0Attribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringRingManaPenetration0Attribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringRingExpUpAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringRingLevelRequire.remove((Player) slotContext.entity());
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
