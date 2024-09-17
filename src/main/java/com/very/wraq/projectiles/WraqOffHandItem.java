package com.very.wraq.projectiles;

import com.very.wraq.blocks.blocks.forge.ForgeRecipe;
import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.loot.RandomLootEquip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WraqOffHandItem extends Item {

    private final Component type;

    public WraqOffHandItem(Properties properties, Component type) {
        super(properties);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        this.type = type;
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
    }

    public abstract Style getMainStyle();

    public abstract List<Component> getAdditionalComponents();

    public abstract Component getSuffix();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = getMainStyle();
        Compute.forgingHoverName(stack);
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(type));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        if (this instanceof RandomLootEquip randomLootEquip) {
            if (randomLootEquip.levelRequire() != 0) {
                int levelRequirement = randomLootEquip.levelRequire();
                if (levelRequirement != 0) {
                    components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                            append(Component.literal("Lv." + levelRequirement).withStyle(Utils.levelStyleList.get(levelRequirement / 25))));
                }
            }
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!getAdditionalComponents().isEmpty()) {
            ComponentUtils.descriptionOfAddition(components);
            components.addAll(getAdditionalComponents());
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        }
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
