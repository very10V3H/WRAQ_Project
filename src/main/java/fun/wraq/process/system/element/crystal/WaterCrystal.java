
package fun.wraq.process.system.element.crystal;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ItemTier;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.common.equip.impl.ActiveItem;
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
import java.util.ArrayList;
import java.util.List;

public class WaterCrystal extends SwordItem implements ActiveItem {

    private static final double[] MaxHealth = {
            400, 600, 800, 1000
    };

    private static final double[] ExpUp = {
            0.5, 1, 1.5, 2
    };

    public WaterCrystal(Properties properties, int tier) {
        super(ItemTier.VMaterial, 2, 0, properties);
        Utils.maxHealth.put(this, MaxHealth[tier]);
        Utils.expUp.put(this, ExpUp[tier]);
        Element.WaterElementValue.put(this, new double[]{0.4, 0.6, 0.8, 1}[tier]);
        Utils.passiveEquipTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.levelRequire.put(this, new int[]{50, 100, 150, 200}[tier]);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfWater;
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura).append(Component.literal("元素水晶").withStyle(style)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionActive(components, Component.literal("碧水元素容器").withStyle(style));
        components.add(Component.literal(" 对周围生物").withStyle(ChatFormatting.WHITE).
                append(Component.literal("施加").withStyle(CustomStyle.styleOfPower)).
                append(Element.Description.WaterElement("100%")));
        components.add(Component.literal(" - 这个效果施加的元素量将会自适应附带较低的基础伤害").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.coolDownTimeDescription(components, 10);
        components.add(Component.literal(" - 根据归一化元素强度至多可以将冷却时间缩短至3s").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.LevelRequire(components, Utils.levelRequire.get(this));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfElement(components);
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

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (player.experienceLevel < Utils.levelRequire.get(this)) return;
        if (!player.getCooldowns().isOnCooldown(this)) {
            int coolDownTick = (int) (200 - Math.min(140, 60 * ElementValue.PlayerWaterElementValue(player)));
            List<Item> itemList = new ArrayList<>() {{
                add(ModItems.WaterCrystal0.get());
                add(ModItems.WaterCrystal1.get());
                add(ModItems.WaterCrystal2.get());
                add(ModItems.WaterCrystal3.get());
            }};
            itemList.forEach(item1 -> player.getCooldowns().addCooldown(item1, coolDownTick));
            boolean isAd = PlayerAttributes.attackDamage(player) * 4 > PlayerAttributes.manaDamage(player);
            Element.RangeElementProvider(player, Element.water, ElementValue.PlayerWaterElementValue(player),
                    isAd, isAd ? PlayerAttributes.attackDamage(player) * 4 : PlayerAttributes.manaDamage(player), 6);
        }
    }
}
