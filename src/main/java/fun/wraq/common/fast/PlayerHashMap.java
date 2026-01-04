package fun.wraq.common.fast;

import fun.wraq.common.Compute;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import oshi.util.tuples.Pair;

import java.util.*;

public class PlayerHashMap<V> {

    protected final Map<String, V> map;

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

    public Collection<V> getValues() {
        return map.values();
    }

    public Set<Map.Entry<String, V>> entrySet() {
        return map.entrySet();
    }

    public Set<Pair<Player, V>> getEntrySet() {
        Set<Pair<Player, V>> set = new HashSet<>();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            ServerPlayer serverPlayer = Compute.getPlayerByName(entry.getKey());
            if (serverPlayer != null) {
                set.add(new Pair<>(serverPlayer, entry.getValue()));
            }
        }
        return set;
    }
}
