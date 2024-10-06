package fun.wraq.common.equip;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ItemTier;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WraqMainHandEquip extends SwordItem {

    public WraqMainHandEquip(Properties properties) {
        super(ItemTier.VMaterial, 2, 0, properties);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        if (this instanceof WraqMainHandOrPassiveEquip) {
            Utils.passiveEquipTag.put(this, 1d);
        }
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
    }

    public abstract Style getMainStyle();

    public abstract List<Component> getAdditionalComponents(ItemStack stack);

    public abstract Component getSuffix();

    public Component oneLineDescription() {
        return null;
    }

    public abstract Component getType();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = getMainStyle();
        Compute.forgingHoverName(stack);
        if (getType() != null) {
            components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(getType()));
        }
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
        boolean shouldRenderAddition = !getAdditionalComponents(stack).isEmpty() || this instanceof fun.wraq.common.equip.WraqSceptre;
        if (shouldRenderAddition) {
            ComponentUtils.descriptionOfAddition(components);
        }

        if (!getAdditionalComponents(stack).isEmpty()) {
            components.addAll(getAdditionalComponents(stack));
        }
        if (this instanceof WraqMainHandOrPassiveEquip wraqMainHandOrPassiveEquip) {
            ComponentUtils.descriptionPassive(components, Component.literal("伴魂").withStyle(CustomStyle.styleOfMoontain));
            components.add(Te.m(" 若将这件装备放置于").
                    append(Te.m("快捷使用栏", ChatFormatting.AQUA)));
            components.add(Te.m(" 则将获得这件装备").
                    append(Te.m(String.format("%.0f%%", wraqMainHandOrPassiveEquip.rate() * 100))).
                    append(Te.m("的")).
                    append(Te.m("基础属性", ChatFormatting.AQUA)));
        }
        if (this instanceof fun.wraq.common.equip.WraqSceptre) {
            WraqSceptre.getManaCoreAddition(stack, components);
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

    public void tick(Player player) {}

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (!level.isClientSide) {
            tick(player);
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }
}
