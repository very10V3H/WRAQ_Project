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

public class WardenBook extends WardenOffhandItem implements ForgeItem {
    public WardenBook(Properties properties, Component type) {
        super(properties, type);
        Utils.manaDamage.put(this, 577d);
        Utils.manaPenetration0.put(this, 17d);
        Utils.maxMana.put(this, 87d);
        Utils.coolDownDecrease.put(this, 0.23);
        Utils.expUp.put(this, 0.88);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.MoonBook.get(), 1),
                new ItemStack(WardenItems.WARDEN_SOUL_INGOT.get(), 256),
                new ItemStack(ModItems.GOLD_COIN.get(), 288),
                new ItemStack(ModItems.completeGem.get(), 12),
                new ItemStack(ModItems.ReputationMedal.get(), 48),
                new ItemStack(PickaxeItems.TINKER_IRON.get(), 12),
                new ItemStack(PickaxeItems.TINKER_COPPER.get(), 12),
                new ItemStack(ModItems.WorldSoul3.get(), 4)
        );
    }
}
