package fun.wraq.series.instance.series.ice;

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

public class IceArmor extends WraqArmor implements ForgeItem {

    public IceArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 40d);
            Utils.defence.put(this, 20d);
            Utils.manaDefence.put(this, 10d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 35d);
            Utils.manaDefence.put(this, 15d);
            Utils.maxHealth.put(this, 5000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 10000d);
            Utils.defence.put(this, 20d);
            Utils.manaDefence.put(this, 10d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 5000d);
        }
        Utils.levelRequire.put(this, 135);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 3),
                new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 1)
        );
    }
}
