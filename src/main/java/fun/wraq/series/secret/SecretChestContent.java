package fun.wraq.series.secret;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class SecretChestContent {
    public static Map<Item, Integer> secretItemValueMap = new HashMap<>();

    public static Map<Item, Integer> getSecretItemValueMap() {
        if (secretItemValueMap.isEmpty()) {

        }
        return secretItemValueMap;
    }


}
