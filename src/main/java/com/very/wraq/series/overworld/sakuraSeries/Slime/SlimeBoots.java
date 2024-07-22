package com.very.wraq.series.overworld.sakuraSeries.Slime;

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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SlimeBoots extends WraqArmor implements ForgeItem {
    public SlimeBoots(ItemMaterial itemMaterial, Type Slots) {
        super(itemMaterial, Slots, new Properties().rarity(CustomStyle.LifeItalic));
        Utils.attackDamage.put(this, 45d);
        Utils.manaDamage.put(this, 125d);
        Utils.manaDefence.put(this, 200d);
        Utils.defence.put(this, 100d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.4);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHealth;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("Q弹史莱姆！").withStyle(style));
        components.add(Component.literal(" 使穿戴者获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("跳跃提升！").withStyle(style)));
        Compute.DescriptionPassive(components, Component.literal("史莱姆缓冲！").withStyle(style));
        components.add(Component.literal(" 使你受到的伤害直接减少等同于你的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("10%")).
                append(Component.literal("的数额！").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SlimeBoots.get());
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.SlimeBall.get(), 384));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.CompleteGem.get(), 6));
            add(new ItemStack(ModItems.ReputationMedal.get(), 6));
            add(new ItemStack(ModItems.RefiningIron.get(), 1));
            add(new ItemStack(ModItems.RefiningCopper.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }};
    }
}
