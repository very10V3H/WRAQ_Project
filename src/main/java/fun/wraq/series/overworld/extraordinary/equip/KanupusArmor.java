package fun.wraq.series.overworld.extraordinary.equip;

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
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KanupusArmor extends WraqArmor implements ForgeItem {

    public KanupusArmor(ArmorMaterial armorMaterial, Type type, Properties properties, int tier) {
        super(armorMaterial, type, properties);
        Utils.attackDamage.put(this, new double[]{1400d, 1600d, 1800d, 2000d, 2200d, 2400d, 2600d}[tier]);
        Utils.manaDamage.put(this, new double[]{1400d, 1600d, 1800d, 2000d, 2200d, 2400d, 2600d}[tier] * 2);
        Utils.coolDownDecrease.put(this, new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier]);
        Utils.attackDamageEnhance.put(this, new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier]);
        Utils.manaDamageEnhance.put(this, new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier]);
        Utils.movementSpeedCommon.put(this, new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier]);
        Utils.percentMaxHealthEnhance.put(this, -new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier]);
        Utils.maxHealth.put(this, -new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier] * 100000);
        Utils.percentDefenceEnhance.put(this, -new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier] * 1);
        Utils.defence.put(this, -new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier] * 1000);
        Utils.percentManaDefenceEnhance.put(this, -new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier]);
        Utils.manaDefence.put(this, -new double[]{0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.26}[tier] * 1000);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.MANA_TOWER_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfManaTower();
    }

    @Override
    public List<Component> getExtraDescription() {
        return List.of(
                Te.s("直面神，接近神，蔑视神，成为神，创造神", ChatFormatting.GRAY, ChatFormatting.ITALIC)
        );
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())
        );
    }
}
