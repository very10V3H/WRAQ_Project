package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.customized.uniform.mana.ManaCurioTaboo;
import net.minecraft.world.entity.player.Player;

public class ManaNewSkill {
    public static int modifyCooldown(Player player, int originCooldown) {
        if (ManaCurioTaboo.isActive(player)) {
            return originCooldown / 2;
        }
        return originCooldown;
    }

    public static int modifyManaCost(Player player, int originManaCost) {
        if (ManaCurioTaboo.isActive(player)) {
            return 0;
        }
        return originManaCost;
    }

    public static double modifyDamage(Player player, double originDamage) {
        if (ManaCurioTaboo.isActive(player)) {
            return originDamage * 2;
        }
        return originDamage;
    }
}
