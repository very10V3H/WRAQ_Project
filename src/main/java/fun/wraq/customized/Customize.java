package fun.wraq.customized;

import fun.wraq.customized.uniform.attack.AttackCurios2;
import fun.wraq.customized.uniform.bow.BowCurios2;
import fun.wraq.customized.uniform.mana.ManaCurios2;
import net.minecraft.world.entity.player.Player;

public class Customize {
    public static void CustomizeTickEvent(Player player) {
        AttackCurios2.tick(player);
        BowCurios2.tick(player);
        ManaCurios2.tick(player);
    }
}
