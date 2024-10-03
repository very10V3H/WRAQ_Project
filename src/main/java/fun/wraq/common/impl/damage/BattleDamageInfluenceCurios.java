package fun.wraq.common.impl.damage;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface BattleDamageInfluenceCurios {
    double rate(Player player, Mob mob);

    static double getRate(Player player, Mob mob) {
        double rate = 0;
        for (ItemStack armor : Compute.CuriosAttribute.getCuriosList(player)) {
            if (armor.getItem() instanceof BattleDamageInfluenceCurios curios) {
                rate += curios.rate(player, mob);
            }
        }
        return rate;
    }
}
