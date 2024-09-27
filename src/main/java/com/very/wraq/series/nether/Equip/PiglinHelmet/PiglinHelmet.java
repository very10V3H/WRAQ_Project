package com.very.wraq.series.nether.Equip.PiglinHelmet;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.OnEquipmentSlotAttributeEnhance;
import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PiglinHelmet extends WraqArmor implements ForgeItem, OnEquipmentSlotAttributeEnhance {

    public final int tier;
    public PiglinHelmet(ItemMaterial Material, Type Slots, int tier) {
        super(Material, Slots, new Properties().rarity(CustomStyle.PiglinItalic));
        this.tier = tier;
        Utils.defence.put(this, PiglinHelmetAttributes.Defence[tier]);
        Utils.maxHealth.put(this, PiglinHelmetAttributes.MaxHealthUp[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("群攻").withStyle(style));
        components.add(Component.literal("基于附近怪物数量，为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("怪物数量 * "
                        + String.format("%.0f", PiglinHelmetAttributes.Effect[tier]))));
        components.add(Component.literal("最大值：50").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("明明是金头盔，为什么不防猪灵呢？").withStyle(ChatFormatting.ITALIC).
                withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.WitherRune.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.RAW_GOLD, 32));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }

    @Override
    public double getAttributeValue(Player player) {
        return Math.min(50, PiglinHelmetAttributes.Effect[tier] * Compute.getNearEntity(player, Mob.class, 8).size());
    }

    @Override
    public Map<Item, Double> baseAttributeMap() {
        return Utils.defence;
    }
}
