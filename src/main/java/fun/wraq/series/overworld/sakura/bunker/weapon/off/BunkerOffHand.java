package fun.wraq.series.overworld.sakura.bunker.weapon.off;

import fun.wraq.common.registry.ModItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface BunkerOffHand {
    static List<ItemStack> getForgeRecipe(Item frontItem) {
        return List.of(
                new ItemStack(frontItem),
                new ItemStack(BunkerItems.BUNKER_BOSS_RUNE.get(), 8),
                new ItemStack(BunkerItems.BUNKER_RUNE.get(), 50),
                new ItemStack(ModItems.ReputationMedal.get(), 40)
        );
    }
}
