package fun.wraq.process.func.multiblockactive.rightclick.top;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class RightClickActivation {
    private final List<Component> title;
    private final Vec3 centerPos;

    public RightClickActivation(Component title, Vec3 centerPos) {
        this.title = List.of(title);
        this.centerPos = centerPos;
    }

    public RightClickActivation(List<Component> title, Vec3 centerPos) {
        this.title = title;
        this.centerPos = centerPos;
    }

    public List<Component> getTitle() {
        return title;
    }

    public Vec3 getCenterPos() {
        return centerPos;
    }

    public abstract void handleRightClick(Player player);

    public abstract List<Component> getDescription();
}
