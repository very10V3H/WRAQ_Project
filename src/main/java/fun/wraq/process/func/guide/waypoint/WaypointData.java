/** AI-Generated, 2026-05-17 */
package fun.wraq.process.func.guide.waypoint;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class WaypointData {
    public final String name;
    public final double x;
    public final double y;
    public final double z;
    public final String dimension;

    public WaypointData(String name, double x, double y, double z, String dimension) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public WaypointData(String name, double x, double y, double z, Level level) {
        this(name, x, y, z, level.dimension().location().toString());
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        tag.putString("dim", dimension);
        return tag;
    }

    public static WaypointData fromNbt(CompoundTag tag) {
        return new WaypointData(
                tag.getString("name"),
                tag.getDouble("x"),
                tag.getDouble("y"),
                tag.getDouble("z"),
                tag.getString("dim")
        );
    }

    public double distanceTo(double px, double py, double pz) {
        double dx = x - px;
        double dy = y - py;
        double dz = z - pz;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
