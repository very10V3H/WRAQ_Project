package fun.wraq.series.instance.series.taboo;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TabooAttackArmor extends WraqArmor implements ForgeItem {

    public TabooAttackArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 20000d);
        Utils.defence.put(this, 18d);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-加护").withStyle(style));
        components.add(Component.literal(" 当受到来自怪物的伤害时，若拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaValue("25%")));
        components.add(Component.literal(" ").
                append(Component.literal("则消耗").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaValue("3%")).
                append(Component.literal("来使即将到来的伤害减少40%").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.TABOO_ATTACK_LEGGINGS.get());
    }

    public static double Passive(Player player) {
        if (!IsOn(player)) return 1;
        if (Mana.getPlayerCurrentManaNum(player) / Mana.getPlayerMaxManaNum(player) > 0.25) {
            Mana.addOrCostPlayerMana(player, (-Mana.getPlayerMaxManaNum(player) * 0.03));
            return 0.6;
        }
        return 1;
    }


    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.CONSTRAINT_TABOO.get(), 1),
                new ItemStack(ModItems.PURPLE_IRON_LEGGINGS.get(), 1),
                new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 32)
        );
    }
}
