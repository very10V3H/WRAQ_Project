package fun.wraq.series.instance.series.warden.offhand.darkmoon;

import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.instance.series.warden.WardenOffhandItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DarkMoonKnife extends WardenOffhandItem implements ForgeItem {

    public DarkMoonKnife(Properties properties, Component type) {
        super(properties, type, true);
        Utils.attackDamage.put(this, 477d);
        Utils.defencePenetration0.put(this, 27d);
        Utils.critRate.put(this, 0.17);
        Utils.critDamage.put(this, 0.77);
        Utils.expUp.put(this, 1.08);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(WardenItems.WARDEN_KNIFE.get(), 1),
                new ItemStack(ModItems.MOON_BOW_E.get()),
                new ItemStack(WardenItems.WARDEN_HEART.get(), 4)
        );
    }
}
