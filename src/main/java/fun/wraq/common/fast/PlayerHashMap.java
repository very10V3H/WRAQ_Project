package fun.wraq.common.fast;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerHashMap<V> {

    private final Map<String, V> map;

    public PlayerHashMap() {
        map = new HashMap<>();
    }

    public V get(Player player) {
        return map.get(Name.get(player));
    }

    public void put(Player player, V v) {
        map.put(Name.get(player), v);
    }

    public boolean containsKey(Player player) {
        return map.containsKey(Name.get(player));
    }

    public boolean withoutKey(Player player) {
        return !map.containsKey(Name.get(player));
    }

    public boolean containsValue(V v) {
        return map.containsValue(v);
    }

    public V getOrDefault(Player player, V v) {
        return map.getOrDefault(Name.get(player), v);
    }

    public void remove(Player player) {
        map.remove(Name.get(player));
    }
}
