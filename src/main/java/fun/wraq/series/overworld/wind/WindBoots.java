package fun.wraq.series.overworld.wind;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WindBoots extends WraqArmor implements ForgeItem {

    private final int tier;
    public WindBoots(ModArmorMaterials Material, Type Slots, int tier) {
        super(Material, Slots, new Properties().rarity(CustomStyle.KazeItalic));
        Utils.movementSpeedCommon.put(this, new double[]{0.18, 0.18, 0.18, 0.22, 0.25}[tier]);
        if (tier > 2) {
            Utils.percentAttackDamageEnhance.put(this, new double[]{0, 0, 0, 0.02, 0.04}[tier]);
            Utils.percentManaDamageEnhance.put(this, new double[]{0, 0, 0, 0.02, 0.04}[tier]);
        }
        Utils.attackDamage.put(this, new double[]{300, 300, 300, 400, 550}[tier]);
        Utils.manaDamage.put(this, new double[]{300, 300, 300, 400, 550}[tier] * 2);
        Utils.swiftnessUp.put(this, new double[]{1, 1, 1, 1.5, 2.5}[tier]);
        Element.windElementValue.put(this, new double[]{0.2, 0.2, 0.2, 0.35, 0.5}[tier]);
        Utils.levelRequire.put(this, 200);
        this.tier = tier;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfKaze;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfWind();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 3) {
            return List.of(
                    new ItemStack(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())
            );
        }
        return List.of();
    }
}
