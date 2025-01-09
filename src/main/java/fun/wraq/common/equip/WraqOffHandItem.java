package fun.wraq.common.equip;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ItemTier;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WraqOffHandItem extends SwordItem {

    protected final Component type;
    public static final String KNIFE = "匕首";
    public static final String SHIELD = "盾牌";
    public static final String BOOK = "魔导书";

    public WraqOffHandItem(Properties properties, Component type) {
        super(ItemTier.VMaterial, 2, 0, properties.stacksTo(1));
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        this.type = type;
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
        Display.offHandList.add(this);
    }

    public abstract Style getMainStyle();

    public abstract List<Component> getAdditionalComponents(ItemStack stack);

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
        } else {
            int levelRequirement = Utils.levelRequire.getOrDefault(stack.getItem(), 0);
            if (levelRequirement != 0) {
                components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("Lv." + levelRequirement).withStyle(Utils.levelStyleList.get(levelRequirement / 25))));
            }
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!getAdditionalComponents(stack).isEmpty()) {
            ComponentUtils.descriptionOfAddition(components);
            shieldDescription(stack.getItem(), components);
            components.addAll(getAdditionalComponents(stack));
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

    public void tick(Player player) {}

    public static void serverTick(Player player) {
        if (player.getOffhandItem().getItem() instanceof WraqOffHandItem wraqOffHandItem) {
            wraqOffHandItem.tick(player);
        }
    }

    private void shieldDescription(Item item, List<Component> components) {
        if (Utils.shieldTag.containsKey(item)) {
            ComponentUtils.descriptionPassive(components, Component.literal("坚盾").withStyle(CustomStyle.styleOfStone));
            components.add(Te.s(" 手持近战武器", CustomStyle.styleOfPower, "时，", "提升", ChatFormatting.AQUA,
                    ComponentUtils.AttributeDescription.defence("25%"),
                    "与", ComponentUtils.AttributeDescription.manaDefence("25%")));
            components.add(Te.m(" 并基于").
                    append(ComponentUtils.AttributeDescription.defence("100%")).
                    append(Te.m("与")).
                    append(ComponentUtils.AttributeDescription.manaDefence("100%")).
                    append(Te.m("之和，在受击时提供等额")).
                    append(Te.m("直接伤害减免", CustomStyle.styleOfStone)));
            Compute.DescriptionPassive(components, Component.literal("盾击").withStyle(CustomStyle.styleOfStone));
            components.add(Component.literal(" 基于").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.defence("")).
                    append(Component.literal("为你至多提供").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("75%近战攻击增幅").withStyle(CustomStyle.styleOfPower)));
        }
    }
}
