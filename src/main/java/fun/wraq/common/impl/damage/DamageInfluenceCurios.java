package fun.wraq.common.impl.damage;

import fun.wraq.common.equip.WraqCurios;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface DamageInfluenceCurios {
    double rate(Player player);

    static double getRate(Player player) {
        double rate = 0;
        for (ItemStack armor : WraqCurios.CuriosAttribute.getDistinctCuriosList(player)) {
            if (armor.getItem() instanceof DamageInfluenceCurios curios) {
                rate += curios.rate(player);
            }
        }
        return rate;
    }
}
