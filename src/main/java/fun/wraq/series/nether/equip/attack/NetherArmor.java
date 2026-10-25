package fun.wraq.series.nether.equip.attack;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class NetherArmor extends WraqArmor implements ForgeItem {

    public NetherArmor(ModArmorMaterials material, Type type, Properties properties) {
        super(material, type, properties);
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 30d);
            Utils.defence.put(this, 15d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 25d);
            Utils.maxHealth.put(this, 3000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 6000d);
            Utils.defence.put(this, 15d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 3000d);
        }
        Utils.attackDamage.put(this, 60d);
        Utils.critDamage.put(this, 0.05);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (type.equals(Type.HELMET)) {
            return List.of(
                    new ItemStack(ModItems.WITHER_RUNE.get(), 8),
                    new ItemStack(ModItems.NETHER_RUNE.get(), 2),
                    new ItemStack(ModItems.QUARTZ_RUNE.get(), 1)
            );
        }
        if (type.equals(Type.CHESTPLATE)) {
            return List.of(
                    new ItemStack(ModItems.NETHER_SKELETON_RUNE.get(), 8),
                    new ItemStack(ModItems.NETHER_RUNE.get(), 2),
                    new ItemStack(ModItems.QUARTZ_RUNE.get(), 1)
            );
        }
        if (type.equals(Type.LEGGINGS)) {
            return List.of(
                    new ItemStack(ModItems.MAGMA_RUNE.get(), 8),
                    new ItemStack(ModItems.NETHER_RUNE.get(), 2),
                    new ItemStack(ModItems.QUARTZ_RUNE.get(), 1)
            );
        }
        if (type.equals(Type.BOOTS)) {
            return List.of(
                    new ItemStack(ModItems.PIGLIN_RUNE.get(), 8),
                    new ItemStack(ModItems.NETHER_RUNE.get(), 2),
                    new ItemStack(ModItems.QUARTZ_RUNE.get(), 1)
            );
        }
        return List.of();
    }
}
