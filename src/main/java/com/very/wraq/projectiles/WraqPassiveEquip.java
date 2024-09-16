package com.very.wraq.projectiles;

import com.very.wraq.blocks.blocks.inject.InjectRecipe;
import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.registry.ItemTier;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WraqPassiveEquip extends PickaxeItem {

    public WraqPassiveEquip(Item.Properties p_40524_) {
        super(ItemTier.VMaterial, 2, 0, p_40524_);
        Utils.passiveEquipTag.put(this, 1d);
        Utils.weaponList.add(this);
        if (this instanceof CanBeInjected canBeInjected) {
            InjectRecipe.injectingRecipeMap.put(this, canBeInjected.getRecipe());
            InjectRecipe.injectedGetItemSourceItemMap.put(canBeInjected.getRecipe().getForgingGetItem(), this);
        }
    }

    public abstract Style getMainStyle();
    public abstract List<Component> getAdditionDescriptions();
    public abstract Component getSuffix();
    public abstract Component getType();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = getMainStyle();
        Compute.forgingHoverName(stack);
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura)
                .append(getType()));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        if (getAdditionDescriptions() != null && !getAdditionDescriptions().isEmpty()) {
            ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            ComponentUtils.DescriptionOfAddition(components);
            components.addAll(getAdditionDescriptions());
        }
        if (Utils.levelRequire.containsKey(this) && Utils.levelRequire.get(this) > 0) {
            Compute.LevelRequire(components, Utils.levelRequire.get(this));
        }
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(getSuffix());
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }
}
