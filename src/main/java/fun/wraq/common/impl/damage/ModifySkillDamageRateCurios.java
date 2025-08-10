package fun.wraq.common.impl.damage;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ModifySkillDamageRateCurios {
    double modifySkillDamageRate(Player player);

    static double getModifiedSkillDamageRate(Player player) {
        double rate = 0;
        for (ItemStack armor : Compute.CuriosAttribute.getDistinctCuriosList(player)) {
            if (armor.getItem() instanceof ModifySkillDamageRateCurios curios) {
                rate += curios.modifySkillDamageRate(player);
            }
        }
        return rate;
    }
}
