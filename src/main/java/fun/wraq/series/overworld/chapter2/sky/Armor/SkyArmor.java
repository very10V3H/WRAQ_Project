package fun.wraq.series.overworld.chapter2.sky.Armor;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class SkyArmor extends WraqArmor implements ForgeItem {

    public SkyArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 20d);
            Utils.defence.put(this, 10d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 20d);
            Utils.maxHealth.put(this, 2000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 4000d);
            Utils.defence.put(this, 10d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.15);
            Utils.maxHealth.put(this, 2000d);
        }
        Utils.attackDamage.put(this, 50d);
        Utils.levelRequire.put(this, 72);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.SKY_RUNE.get(), 5),
                new ItemStack(Items.DIAMOND, 16)
        );
    }
}
