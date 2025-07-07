package fun.wraq.common.util.struct;

import net.minecraft.world.entity.Mob;

import java.util.HashMap;
import java.util.Map;

public class MyMobToDoubleMap {

    public final Map<Mob, Double> map;

    public MyMobToDoubleMap() {
        map = new HashMap<>();
    }

    public double get(Mob mob) {
        if (!map.containsKey(mob)) {
            return 0;
        }
        map.entrySet().removeIf(entry -> {
            return entry.getKey() == null || !entry.getKey().isAlive();
        });
        return map.get(mob);
    }

    public void put(Mob mob, double value) {
        map.put(mob, value);
    }
}
