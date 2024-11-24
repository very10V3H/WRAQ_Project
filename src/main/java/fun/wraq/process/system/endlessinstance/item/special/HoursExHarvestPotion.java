package fun.wraq.process.system.endlessinstance.item.special;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class HoursExHarvestPotion extends WraqItem {
    private final int hours;
    public HoursExHarvestPotion(Properties properties, int hours) {
        super(properties, false, false, List.of(
                Te.s("使用以获得持续", hours + "小时", CustomStyle.styleOfWorld,
                        "的", "15%额外产出", CustomStyle.styleOfGold)
        ));
        this.hours = hours;
    }

    public static Map<Player, Integer> expiredTickMap = new WeakHashMap<>();

    @SuppressWarnings("ALL")
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            Compute.playerItemCoolDown(player, this, 3600);
            expiredTickMap.put(player, Tick.get() + 20 * 3600 * hours);
        }
        return super.use(level, player, interactionHand);
    }

    public static void tick(Player player) {
        if (player.tickCount % 200 == 0) {
            if (expiredTickMap.getOrDefault(player, 0) > Tick.get()) {
                Compute.sendEffectLastTime(player, "item/gold_ingot", 1200, 0, false);
            }
        }
    }

    public static double getHoursExHarvestPotionEnhanceRate(Player player) {
        if (expiredTickMap.getOrDefault(player, 0) > Tick.get()) {
            return 0.15;
        }
        return 0;
    }
}
