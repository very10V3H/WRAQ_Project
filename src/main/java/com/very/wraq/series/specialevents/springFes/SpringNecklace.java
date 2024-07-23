package com.very.wraq.series.specialevents.springFes;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
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

public class SpringNecklace extends Item implements ICurioItem {

    private final int level;

    public SpringNecklace(Properties p_41383_, int level) {
        super(p_41383_);
        this.level = level;
        Utils.manaDamage.put(this, Attack[level]);
        Utils.manaPenetration0.put(this, DefencePenetration0[level]);
        Utils.manaRecover.put(this, ManaRecover[level]);
        Utils.maxMana.put(this, MaxMana[level]);
        Utils.expUp.put(this, ExpUp[level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    private final double[] Attack = {
            40, 80, 120, 160
    };
    private final double[] DefencePenetration0 = {
            25, 50, 75, 100
    };
    private final double[] ManaRecover = {
            1, 2, 3, 4
    };
    private final double[] MaxMana = {
            10, 20, 30, 40
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
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        components.add(Component.literal("多件金龙蕴才项链仅会生效最后装备的一件效果").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal(" 等级需求:" + LevelRequire[this.level]).withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.PlayerSpringBraceletAttackAttribute.put((Player) slotContext.entity(), Attack[this.level]);
        Utils.PlayerSpringBraceletManaPenetration0Attribute.put((Player) slotContext.entity(), DefencePenetration0[this.level]);
        Utils.PlayerSpringBraceletManaRecoverAttribute.put((Player) slotContext.entity(), ManaRecover[this.level]);
        Utils.PlayerSpringBraceletMaxManaAttribute.put((Player) slotContext.entity(), MaxMana[this.level]);
        Utils.PlayerSpringBraceletExpUpAttribute.put((Player) slotContext.entity(), ExpUp[this.level]);
        Utils.PlayerSpringBraceletLevelRequire.put((Player) slotContext.entity(), LevelRequire[this.level]);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.PlayerSpringBraceletAttackAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBraceletManaPenetration0Attribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBraceletManaRecoverAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBraceletMaxManaAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBraceletExpUpAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBraceletLevelRequire.remove((Player) slotContext.entity());
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
