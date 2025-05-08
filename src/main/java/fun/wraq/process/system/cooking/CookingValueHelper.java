package fun.wraq.process.system.cooking;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class CookingValueHelper {
    public static Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}
