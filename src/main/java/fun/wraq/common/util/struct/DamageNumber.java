package fun.wraq.common.util.struct;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

/** AI-Generated, 2026-05-10 */
public class DamageNumber {
    private final Vec3 position;
    private final Component component;
    private final long expireAtMs;

    public DamageNumber(Vec3 position, Component component, long expireAtMs) {
        this.position = position;
        this.component = component;
        this.expireAtMs = expireAtMs;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Component getComponent() {
        return component;
    }

    public long getExpireAtMs() {
        return expireAtMs;
    }
}
