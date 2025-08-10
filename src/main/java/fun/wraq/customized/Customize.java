package fun.wraq.customized;

import fun.wraq.customized.uniform.attack.normal.AttackCurios2;
import fun.wraq.customized.uniform.bow.normal.BowCurios2;
import fun.wraq.customized.uniform.mana.normal.ManaCurios2;
import net.minecraft.world.entity.player.Player;

public class Customize {
    public static void CustomizeTickEvent(Player player) {
        AttackCurios2.tick(player);
        BowCurios2.tick(player);
        ManaCurios2.tick(player);
    }
}
