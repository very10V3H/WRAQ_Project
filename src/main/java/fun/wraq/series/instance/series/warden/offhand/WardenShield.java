package fun.wraq.series.instance.series.warden.offhand;

import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.instance.series.warden.WardenOffhandItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WardenShield extends WardenOffhandItem implements ForgeItem {
    public WardenShield(Properties properties, Component type) {
        super(properties, type);
        Utils.defence.put(this, 17d);
        Utils.maxHealth.put(this, 5777d);
        Utils.attackDamage.put(this, 377d);
        Utils.critDamage.put(this, 0.37);
        Utils.expUp.put(this, 0.88);
        Utils.shieldTag.put(this, 1d);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.MoonShield.get(), 1),
                new ItemStack(WardenItems.WARDEN_HEART.get(), 4),
                new ItemStack(ModItems.GOLD_COIN.get(), 288),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 12),
                new ItemStack(ModItems.ReputationMedal.get(), 48),
                new ItemStack(PickaxeItems.TINKER_IRON.get(), 12),
                new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 4)
        );
    }
}
