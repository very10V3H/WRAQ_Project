package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MoonArmor extends WraqArmor implements ForgeItem {

    public MoonArmor(ModArmorMaterials material, Type type, Properties properties) {
        super(material, type, properties);
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 16000d);
            Utils.defence.put(this, 20d);
            Utils.manaDefence.put(this, 10d);
        }
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.007);
            Utils.healthRecover.put(this, 50d);
            Utils.defence.put(this, 20d);
            Utils.manaDefence.put(this, 10d);
        }
        Utils.levelRequire.put(this, 160);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 8),
                new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 64)
        );
    }
}
