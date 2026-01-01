package fun.wraq.process.system.expired;

import fun.wraq.common.registry.ModItems;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 物品有效期信息
 */
public class ExpiredInfo {

    private static final Map<Item, ExpiredSystem.ExpiredType> expiredTypeMap = new HashMap<>();

    private static Map<Item, ExpiredSystem.ExpiredType> getExpiredTypeMap() {
        ExpiredSystem.ExpiredType disabled = ExpiredSystem.ExpiredType.DISABLED;
        ExpiredSystem.ExpiredType disappeared = ExpiredSystem.ExpiredType.DISAPPEARED;
        if (expiredTypeMap.isEmpty()) {
            expiredTypeMap.put(ModItems.CASTLE_INGOT.get(), disappeared);

            expiredTypeMap.put(ModItems.CASTLE_SOUL_SWORD.get(), disabled);
            expiredTypeMap.put(ModItems.CASTLE_SOUL_BOW.get(), disabled);
            expiredTypeMap.put(ModItems.CASTLE_SOUL_SCEPTRE.get(), disabled);

            expiredTypeMap.put(ModItems.CASTLE_ATTACK_SOUL_BOOTS.get(), disabled);
            expiredTypeMap.put(ModItems.CASTLE_SWIFT_SOUL_BOOTS.get(), disabled);
            expiredTypeMap.put(ModItems.CASTLE_MANA_SOUL_BOOTS.get(), disabled);

            expiredTypeMap.put(ModItems.MOON_SWORD_BELL.get(), disabled);
            expiredTypeMap.put(ModItems.MOON_BOW_BELL.get(), disabled);
            expiredTypeMap.put(ModItems.MOON_SCEPTRE_BELL.get(), disabled);
            expiredTypeMap.put(ModItems.MOON_HELMET_BELL.get(), disabled);
            expiredTypeMap.put(ModItems.MOON_LEGGINGS_BELL.get(), disabled);
        }
        return expiredTypeMap;
    }

    public static @Nullable ExpiredSystem.ExpiredType getExpiredType(Item item) {
        return getExpiredTypeMap().getOrDefault(item, null);
    }

    private static final Map<Item, Integer> expiredHours = new HashMap<>();

    /**
     * @return 物品会在x小时后过期
     */
    private static Map<Item, Integer> getExpiredHours() {
        if (expiredHours.isEmpty()) {
            expiredHours.put(ModItems.CASTLE_INGOT.get(), 14);

            expiredHours.put(ModItems.CASTLE_SOUL_SWORD.get(), 14);
            expiredHours.put(ModItems.CASTLE_SOUL_BOW.get(), 14);
            expiredHours.put(ModItems.CASTLE_SOUL_SCEPTRE.get(), 14);

            expiredHours.put(ModItems.CASTLE_ATTACK_SOUL_BOOTS.get(), 14);
            expiredHours.put(ModItems.CASTLE_SWIFT_SOUL_BOOTS.get(), 14);
            expiredHours.put(ModItems.CASTLE_MANA_SOUL_BOOTS.get(), 14);

            expiredHours.put(ModItems.MOON_SWORD_BELL.get(), 18);
            expiredHours.put(ModItems.MOON_BOW_BELL.get(), 18);
            expiredHours.put(ModItems.MOON_SCEPTRE_BELL.get(), 18);
            expiredHours.put(ModItems.MOON_HELMET_BELL.get(), 18);
            expiredHours.put(ModItems.MOON_LEGGINGS_BELL.get(), 18);
        }
        return expiredHours;
    }

    public static int getExpiredHour(Item item) {
        return getExpiredHours().getOrDefault(item, 0);
    }

    public static Set<Item> getHasExpiredInfoItems() {
        return getExpiredHours().keySet();
    }
}
