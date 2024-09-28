package fun.wraq.common.impl.inslot;

import fun.wraq.common.util.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface InCuriosSlotAttributesModify {
    String exAttackDamage = "exAttackDamage";
    String exManaDamage = "exManaDamage";
    String exReleaseSpeed = "exReleaseSpeed";
    double attributes(Player player, String attributesType);
    static double getAttributes(Player player, String attributeType) {
        if (!Utils.playerCuriosListMap.containsKey(player)) return 0;
        List<ItemStack> curiosList = Utils.playerCuriosListMap.get(player);
        double value = 0;
        Set<Class<? extends Item>> set = new HashSet<>();
        for (ItemStack stack : curiosList) {
            Item item = stack.getItem();
            if (!set.contains(item.getClass()) && item instanceof InCuriosSlotAttributesModify curios) {
                value += curios.attributes(player, attributeType);
                set.add(item.getClass());
            }
        }
        return value;
    }
}
