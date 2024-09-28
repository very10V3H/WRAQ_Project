package fun.wraq.common.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ItemTier;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WraqPickaxe extends PickaxeItem {
    public static final List<Item> PICKAXE_ITEM_LIST = new ArrayList<>();
    public static Map<Item, Double> mineSpeed = new HashMap<>();

    public WraqPickaxe(Properties properties) {
        super(ItemTier.VMaterial, 2, 0, properties);
        PICKAXE_ITEM_LIST.add(this);
        Utils.mainHandTag.put(this, 1d);
    }

    public abstract Style getMainStyle();

    public abstract List<Component> getAdditionalComponents(ItemStack stack);

    public abstract Component getSuffix();

    public Component oneLineDescription() {
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = getMainStyle();
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Te.m("十字镐")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        int levelRequire = Utils.levelRequire.getOrDefault(stack.getItem(), 0);
        if (levelRequire != 0) {
            components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("Lv." + levelRequire).withStyle(Utils.levelStyleList.get(levelRequire / 25))));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        boolean shouldRenderAddition = !getAdditionalComponents(stack).isEmpty();
        if (shouldRenderAddition) {
            ComponentUtils.descriptionOfAddition(components);
        }
        if (!getAdditionalComponents(stack).isEmpty()) {
            components.addAll(getAdditionalComponents(stack));
        }
        if (shouldRenderAddition) {
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        }
        if (oneLineDescription() != null) {
            components.add(oneLineDescription());
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
