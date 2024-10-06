package fun.wraq.process.func.multiblockactive.rightclick.top;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class RightClickActivation {
    private final Component title;
    private final Vec3 centerPos;

    public RightClickActivation(Component title, Vec3 centerPos) {
        this.title = title;
        this.centerPos = centerPos;
    }

    public Component getTitle() {
        return title;
    }

    public Vec3 getCenterPos() {
        return centerPos;
    }

    public abstract void handleRightClick(Player player);

    public abstract void summonArmorStand(Level level);
}
