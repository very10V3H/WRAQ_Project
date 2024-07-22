package com.very.wraq.series.overworld.sakuraSeries.MineWorker;

import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PurpleIronArmor extends WraqArmor implements ForgeItem {
    private static final Style style = CustomStyle.styleOfPurpleIron;

    public PurpleIronArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
        Utils.attackDamage.put(this, 200d);
        Utils.manaDamage.put(this, 400d);
        Utils.defence.put(this, 500d);
        Utils.maxHealth.put(this, 1000d);
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.4);
            Utils.maxHealth.put(this, 500d);
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPurpleIron;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("紫晶能量屏障").withStyle(style));
        components.add(Component.literal("每过10s，提供持续5s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("10% - 40%")).
                append(Component.literal("的护盾").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("数值基于已装备的紫晶铁装备数量(1~10% 2~20% 3~30% 4~40%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }


    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.PurpleIron.get(), 16));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.CompleteGem.get(), 8));
            add(new ItemStack(ModItems.ReputationMedal.get(), 8));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }};
    }
}
