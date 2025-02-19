package fun.wraq.series.instance.series.warden.offhand.darkmoon;

import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.instance.series.warden.WardenOffhandItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DarkMoonShield extends WardenOffhandItem implements ForgeItem {

    public DarkMoonShield(Properties properties, Component type) {
        super(properties, type, true);
        Utils.defence.put(this, 27d);
        Utils.maxHealth.put(this, 6777d);
        Utils.attackDamage.put(this, 477d);
        Utils.critDamage.put(this, 0.09);
        Utils.expUp.put(this, 1.08);
        Utils.shieldTag.put(this, 1d);
        Utils.levelRequire.put(this, 220);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(WardenItems.WARDEN_SHIELD.get(), 1),
                new ItemStack(ModItems.MOON_SWORD_E.get()),
                new ItemStack(WardenItems.WARDEN_HEART.get(), 4)
        );
    }
}
