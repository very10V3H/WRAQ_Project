
package com.very.wraq.process.element.crystal;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.ElementValue;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemTier;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

public class FireCrystal extends SwordItem{

    private static final double[] MaxHealth = {
            400,600,800,1000
    };

    private static final double[] MovementSpeed = {
            0.2,0.3,0.4,0.6
    };

    private static final double[] ExpUp = {
            0.5,1,1.5,2
    };

    private static final double[] ElementStrength = {
            0.2,0.4,0.6,0.8
    };

    public FireCrystal(Properties properties, int tier){
        super(ItemTier.VMaterial,2,0,properties);
        Utils.MaxHealth.put(this, MaxHealth[tier]);
        Utils.MovementSpeed.put(this, MovementSpeed[tier]);
        Utils.ExpUp.put(this, ExpUp[tier]);
        Element.FireElementValue.put(this, ElementStrength[tier]);
        Utils.PassiveEquipTag.put(this, 1d);
        Utils.WeaponList.add(this);
        Utils.LevelRequire.put(this, 200);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.USE(player);
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfFire;
        components.add(Component.literal("器灵                   ").withStyle(CustomStyle.styleOfSakura).append(Component.literal("元素水晶").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionActive(components,Component.literal("炽焰元素容器").withStyle(style));
        components.add(Component.literal(" 对周围生物").withStyle(ChatFormatting.WHITE).
                append(Component.literal("施加").withStyle(CustomStyle.styleOfPower)).
                append(Element.Description.FireElement("100%")));
        components.add(Component.literal(" - 这个效果施加的元素量将会自适应附带较低的基础伤害").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.CoolDownTimeDescription(components,10);
        components.add(Component.literal(" - 根据归一化元素强度至多可以将冷却时间缩短至3s").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.LevelRequire(components,Utils.LevelRequire.get(this));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfElement(components);
        super.appendHoverText(stack,level,components,flag);
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

    public static void Active(Player player, Item item) {
        if (player.experienceLevel < 200) return;
        if (!player.getCooldowns().isOnCooldown(item)) {
            int coolDownTick = (int) (200 - Math.min(140, 60 * ElementValue.PlayerFireElementValue(player)));
            List<Item> itemList = new ArrayList<>(){{
                add(ModItems.FireCrystal0.get());
                add(ModItems.FireCrystal1.get());
                add(ModItems.FireCrystal2.get());
                add(ModItems.FireCrystal3.get());
            }};
            itemList.forEach(item1 -> player.getCooldowns().addCooldown(item1,coolDownTick));
            boolean isAd = Compute.PlayerAttributes.PlayerAttackDamage(player) * 4 > Compute.PlayerAttributes.PlayerManaDamage(player);
            Element.RangeElementProvider(player,Element.Fire,ElementValue.PlayerFireElementValue(player),
                    isAd, isAd ? Compute.PlayerAttributes.PlayerAttackDamage(player) * 4 : Compute.PlayerAttributes.PlayerManaDamage(player),6);

        }
    }
}
