package fun.wraq.common.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ItemTier;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.blade.WraqBlade;
import fun.wraq.series.instance.mixture.WraqMixture;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WraqPassiveEquip extends PickaxeItem {

    public WraqPassiveEquip(Properties p_40524_) {
        super(ItemTier.VMaterial, 2, 0, p_40524_);
        Utils.passiveEquipTag.put(this, 1d);
        if (!(this instanceof WraqBlade || this instanceof WraqMixture || this instanceof WraqQuiver)) {
            Utils.weaponList.add(this);
        }
        Display.passiveEquipList.add(this);
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
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        if (getAdditionDescriptions() != null && !getAdditionDescriptions().isEmpty()) {
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            ComponentUtils.descriptionOfAddition(components);
            components.addAll(getAdditionDescriptions());
        }
        if (Utils.levelRequire.containsKey(this) && Utils.levelRequire.get(this) > 0) {
            Compute.LevelRequire(components, Utils.levelRequire.get(this));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(getSuffix());
        if (this instanceof WraqBlade || this instanceof WraqQuiver || this instanceof WraqMixture) {
            components.add(Te.s("随着新版技能组的上线，这件物品成为了一件纪念品。", ChatFormatting.GOLD));
            components.add(Te.s("Souvenirs-2025.1.23", CustomStyle.styleOfSakura, ChatFormatting.ITALIC));
        }
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
