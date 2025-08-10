package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.customized.uniform.mana.normal.ManaCurioTaboo;
import fun.wraq.process.system.skill.ManaSkillTree;
import net.minecraft.world.entity.player.Player;

public class ManaNewSkill {
    public static int modifyCooldown(Player player, int originCooldown) {
        if (ManaCurioTaboo.isActive(player)) {
            return originCooldown / 2;
        }
        return originCooldown;
    }

    public static double modifyManaCost(Player player, double originManaCost) {
        if (ManaCurioTaboo.isActive(player)) {
            return 0;
        }
        double exManaCostRate = 0;
        exManaCostRate += ManaSkillTree.getManaSkill13ExManaCostRate(player, 2);
        return originManaCost * (1 + exManaCostRate);
    }

    public static double modifyDamage(Player player, double originDamage) {
        if (ManaCurioTaboo.isActive(player)) {
            return (originDamage * 2);
        }
        return originDamage;
    }
}
