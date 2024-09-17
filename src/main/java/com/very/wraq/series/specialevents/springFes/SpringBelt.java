package com.very.wraq.series.specialevents.springFes;

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

public class SpringBelt extends Item implements ICurioItem {

    private final int level;

    public SpringBelt(Properties p_41383_, int level) {
        super(p_41383_);
        this.level = level;
        Utils.attackDamage.put(this, Attack[level]);
        Utils.defencePenetration0.put(this, DefencePenetration0[level]);
        Utils.swiftnessUp.put(this, Swift[level]);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed[level]);
        Utils.expUp.put(this, ExpUp[level]);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    private final double[] Attack = {
            20, 40, 60, 80
    };
    private final double[] DefencePenetration0 = {
            25, 50, 75, 100
    };
    private final double[] Swift = {
            1, 2, 3, 4
    };
    private final double[] MovementSpeed = {
            0.2, 0.25, 0.3, 0.4
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
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        components.add(Component.literal("多件金龙狩猎腰带仅会生效最后装备的一件效果").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));
        components.add(Component.literal(" 等级需求:" + LevelRequire[this.level]).withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.PlayerSpringBeltAttackAttribute.put((Player) slotContext.entity(), Attack[this.level]);
        Utils.PlayerSpringBeltDefencePenetration0Attribute.put((Player) slotContext.entity(), DefencePenetration0[this.level]);
        Utils.PlayerSpringBeltSwiftAttribute.put((Player) slotContext.entity(), Swift[this.level]);
        Utils.PlayerSpringBeltMovementAttribute.put((Player) slotContext.entity(), MovementSpeed[this.level]);
        Utils.PlayerSpringBeltExpUpAttribute.put((Player) slotContext.entity(), ExpUp[this.level]);
        Utils.PlayerSpringBeltLevelRequire.put((Player) slotContext.entity(), LevelRequire[this.level]);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.PlayerSpringBeltAttackAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBeltDefencePenetration0Attribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBeltSwiftAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBeltMovementAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBeltExpUpAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringBeltLevelRequire.remove((Player) slotContext.entity());
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
