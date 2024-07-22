package com.very.wraq.series.overworld.sakuraSeries.BloodMana;

import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.WraqOffHandItem;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ManaKnife extends WraqOffHandItem implements ForgeItem {

    public ManaKnife(Properties properties) {
        super(properties, Component.literal("匕首").withStyle(CustomStyle.styleOfBloodMana));
        Utils.attackDamage.put(this, 40d);
        Utils.defencePenetration0.put(this, 400d);
        Utils.critRate.put(this, 0.12);
        Utils.critDamage.put(this, 0.5);
        Utils.healthSteal.put(this, 0.04);
        Utils.expUp.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("旧世猎魔遗忆").withStyle(style));
        components.add(Component.literal(" 当你的箭矢命中目标后，为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("5%")).
                append(Compute.AttributeDescription.Health("")));
        Compute.DescriptionPassive(components, Component.literal("新世猎魔传技").withStyle(style));
        components.add(Component.literal(" 将你的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.HealthSteal("")).
                append(Component.literal("以1:3全部转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
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
