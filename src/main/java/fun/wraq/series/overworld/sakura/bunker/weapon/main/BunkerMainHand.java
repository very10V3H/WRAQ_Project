package fun.wraq.series.overworld.sakura.bunker.weapon.main;

import fun.wraq.common.registry.ModItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface BunkerMainHand {
    static List<ItemStack> getForgeRecipe(Item frontItem) {
        return new ArrayList<>() {{
            addAll(List.of(
                    new ItemStack(frontItem),
                    new ItemStack(BunkerItems.BUNKER_RUNE.get(), 150),
                    new ItemStack(ModItems.REPUTATION_MEDAL.get(), 80)
            ));
        }};
    }
}
