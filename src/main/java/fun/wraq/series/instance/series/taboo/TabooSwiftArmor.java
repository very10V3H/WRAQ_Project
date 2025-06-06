package fun.wraq.series.instance.series.taboo;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
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

public class TabooSwiftArmor extends WraqArmor implements ForgeItem {

    public TabooSwiftArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.percentHealthRecover.put(this, 0.006);
        Utils.defence.put(this, 18d);
        Utils.healthRecover.put(this, 50d);
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
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-狩猎").withStyle(style));
        components.add(Component.literal(" 当箭矢命中目标时，若拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaValue("25%")));
        components.add(Component.literal(" ").
                append(Component.literal("则消耗").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaValue("3%")).
                append(Component.literal("，来使你的箭矢附带").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.exAttackDamage("200%")));
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
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.TABOO_SWIFT_HELMET.get());
    }

    public static double ExDamage(Player player) {
        if (!IsOn(player)) return 0;
        if (Mana.getPlayerCurrentManaNum(player) / Mana.getPlayerMaxManaNum(player) > 0.25) {
            Mana.addOrCostPlayerMana(player, (-Mana.getPlayerMaxManaNum(player) * 0.03));
            return PlayerAttributes.attackDamage(player) * 4;
        }
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.CONSTRAINT_TABOO.get(), 1),
                new ItemStack(ModItems.PURPLE_IRON_HELMET.get(), 1),
                new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 32)
        );
    }
}
