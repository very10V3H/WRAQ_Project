package fun.wraq.common.util.struct;

import net.minecraft.world.entity.Mob;

import java.util.HashMap;
import java.util.Map;

public class MyMobToIntMap {

    public final Map<Mob, Integer> map;

    public MyMobToIntMap() {
        map = new HashMap<>();
    }

    public int get(Mob mob) {
        if (!map.containsKey(mob)) {
            return 0;
        }
        map.entrySet().removeIf(entry -> {
            return entry.getKey() == null || !entry.getKey().isAlive();
        });
        return map.get(mob);
    }

    public void put(Mob mob, int value) {
        map.put(mob, value);
    }
}
