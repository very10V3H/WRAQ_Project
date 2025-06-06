package fun.wraq.series.overworld.sakura.BloodMana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
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
        Utils.defence.put(this, 2d);
        Utils.manaDefence.put(this, 3d);
        Utils.maxHealth.put(this, 1200d);
        Utils.attackDamage.put(this, 40d);
        Utils.manaDamage.put(this, 80d);
        Utils.healthSteal.put(this, 0.04);
        Utils.expUp.put(this, 0.65);
        Utils.shieldTag.put(this, 1d);
        Utils.levelRequire.put(this, 125);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("旧世封魔遗志").withStyle(getMainStyle()));
        components.add(Component.literal(" 当拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("50%")).
                append(Component.literal("时，将你的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.healthSteal("")).
                append(Component.literal("以1:1全部转化为").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.critDamage("")));
        components.add(Component.literal(" 当拥有低于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("50%")).
                append(Component.literal("时，将你的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.critDamage("")).
                append(Component.literal("以1:1全部转化为").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.healthSteal("")));
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
            add(new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 16));
            add(new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 16));
            add(new ItemStack(Items.IRON_INGOT, 64));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 64));
        }};
    }
}
