package fun.wraq.series.overworld.chapter2.lightningIsland;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LightningArmor extends WraqArmor implements ForgeItem {

    private final int tier;
    public LightningArmor(ModArmorMaterials material, Type type, Properties properties, int tier) {
        super(material, type, properties);
        this.tier = tier;
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 30d * (tier + 1));
            Utils.defence.put(this, 15d * (tier + 1));
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 25d * (tier + 1));
            Utils.maxHealth.put(this, 3000d * (tier + 1));
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 6000d * (tier + 1));
            Utils.defence.put(this, 15d * (tier + 1));
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 3000d * (tier + 1));
        }
        Utils.levelRequire.put(this, 92);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLightingIsland;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (this.tier == 0) {
            return List.of(
                    new ItemStack(ModItems.LIGHTNING_RUNE.get(), 5),
                    new ItemStack(PickaxeItems.TINKER_IRON.get(), 2)
            );
        }
        return List.of();
    }
}
