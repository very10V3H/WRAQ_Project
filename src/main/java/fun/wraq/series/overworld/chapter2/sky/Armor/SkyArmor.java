package fun.wraq.series.overworld.chapter2.sky.Armor;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class SkyArmor extends WraqArmor implements ForgeItem {

    public SkyArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 20d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 40d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 2000d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.45);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("天空的加护").withStyle(CustomStyle.styleOfSky));
        components.add(Component.literal("1.").
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("高于80%时，至多提升")).
                append(ComponentUtils.AttributeDescription.attackDamage("70%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("20%")));
        components.add(Component.literal("2.").
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("介于40%与80%之间时，至多提升")).
                append(ComponentUtils.AttributeDescription.attackDamage("20%")));
        components.add(Component.literal("3.").
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("低于40%时，每十秒至多获得")).
                append(ComponentUtils.AttributeDescription.attackDamage("10%")).
                append(Component.literal("护盾。")).
                append(Component.literal("持续10s")).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Te.s("基于套装数量的数值:(1_20%,2_50%,3_70%,4_100%)").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.SkyRune.get(), 10),
                new ItemStack(Items.DIAMOND, 16)
        );
    }

    @Override
    public void tick(Player player) {
        double healthRate = player.getHealth() / player.getMaxHealth();
        int effectTier;
        if (healthRate > 0.8) {
            effectTier = 3;
        } else if (healthRate > 0.4) {
            effectTier = 2;
        } else {
            effectTier = 1;
        }
        Compute.sendEffectLastTime(player, ModItems.SKY_ARMOR_HELMET.get(), effectTier, true);
        super.tick(player);
    }
}
