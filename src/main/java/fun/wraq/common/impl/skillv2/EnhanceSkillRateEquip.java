package fun.wraq.common.impl.skillv2;

import fun.wraq.common.attribute.PlayerAttributes;
import net.minecraft.world.entity.player.Player;

public interface EnhanceSkillRateEquip {
    int getType(Player player);

    double getEnhanceRate(Player player);

    static double getEnhanceRate(Player player, int type) {
        return PlayerAttributes.getAllEquipSlotItems(player)
                .stream().filter(stack -> stack.getItem() instanceof EnhanceSkillRateEquip enhanceSkillRateEquip
                        && enhanceSkillRateEquip.getType(player) == type)
                .mapToDouble(stack -> ((EnhanceSkillRateEquip) stack.getItem()).getEnhanceRate(player))
                .sum();
    }
}
