package fun.wraq.series.overworld.cold.sc5.dragon;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.WraqItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class SuperColdCarrot extends WraqItem implements ActiveItem {

    public SuperColdCarrot(Properties properties) {
        super(properties);
    }

    public static Map<String, Integer> effectExpiredTickMap = new HashMap<>();

    @Override
    public void active(Player player) {
        InventoryOperation.removeItem(player, this, 1);
        effectExpiredTickMap.put(Name.get(player), Tick.get() + Tick.min(10));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, Tick.min(10)));
        Compute.sendEffectLastTime(player, this, Tick.min(10));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public static void onHit(Player player, Mob mob) {
        if (effectExpiredTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            Compute.addSlowDownEffect(mob, Tick.s(2), 2);
        }
    }
}
