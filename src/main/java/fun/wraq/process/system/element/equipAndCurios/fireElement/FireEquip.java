package fun.wraq.process.system.element.equipAndCurios.fireElement;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class FireEquip {
    public static void IgniteEffect(Player player, Mob mob) {
        FireElementSword.IgniteEffect(player, mob);
        FireElementBow.IgniteEffect(player, mob);
        FireElementSceptre.IgniteEffect(player, mob);
    }

    public static void Tick(Player player) {
        FireElementSword.Tick(player);
        FireElementBow.Tick(player);
        FireElementSceptre.Tick(player);
    }

    public static void PlayerIgniteMobEffect(Player player, Mob mob) {
        FireElementSword.PlayerIgniteMobEffect(player, mob);
        FireElementBow.PlayerIgniteMobEffect(player, mob);
        FireElementSceptre.PlayerIgniteMobEffect(player, mob);
    }

    public static double DamageEnhance(Player player) {
        double rate = 0;
        rate += FireElementSword.DamageEnhance(player);
        return rate;
    }
}
