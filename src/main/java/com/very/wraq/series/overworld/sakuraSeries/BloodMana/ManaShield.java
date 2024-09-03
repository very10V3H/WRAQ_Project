package com.very.wraq.series.overworld.sakuraSeries.BloodMana;

import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.WraqOffHandItem;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.series.overworld.chapter1.Mine.MineShield;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ManaShield extends WraqOffHandItem implements ForgeItem {

    public ManaShield(Properties properties) {
        super(properties, Component.literal("手盾").withStyle(CustomStyle.styleOfMine));
        Utils.defence.put(this, 150d);
        Utils.manaDefence.put(this, 300d);
        Utils.maxHealth.put(this, 300d);
        Utils.attackDamage.put(this, 40d);
        Utils.manaDamage.put(this, 80d);
        Utils.healthSteal.put(this, 0.04);
        Utils.expUp.put(this, 1d);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        MineShield.shieldAdditionDescription(components);
        Compute.DescriptionPassive(components, Component.literal("旧世封魔遗志").withStyle(getMainStyle()));
        components.add(Component.literal(" 当拥有高于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("50%")).
                append(Component.literal("时，将你的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.HealthSteal("")).
                append(Component.literal("以1:5全部转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        components.add(Component.literal(" 当拥有低于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("50%")).
                append(Component.literal("时，将你的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")).
                append(Component.literal("以5:1全部转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.HealthSteal("")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.BloodManaRune.get(), 16));
            add(new ItemStack(ModItems.EarthManaRune.get(), 16));
            add(new ItemStack(Items.IRON_INGOT, 64));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }
}
