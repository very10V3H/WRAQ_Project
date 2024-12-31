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

public class WardenKnife extends WardenOffhandItem implements ForgeItem {
    public WardenKnife(Properties properties, Component type) {
        super(properties, type);
        Utils.attackDamage.put(this, 377d);
        Utils.defencePenetration0.put(this, 17d);
        Utils.critRate.put(this, 0.17);
        Utils.critDamage.put(this, 0.7);
        Utils.expUp.put(this, 0.88);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.MoonKnife.get(), 1),
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
