package fun.wraq.series.comsumable.active;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatDevice extends WraqItem implements ActiveItem {

    public final int tier;

    public HeatDevice(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    public int getEffectLastMinutes() {
        return new int[]{10, 20, 30}[tier];
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level,
                                List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 右键单次使用", ChatFormatting.AQUA));
        components.add(Te.s(" 使用后的", getEffectLastMinutes() + "min", ChatFormatting.AQUA,
                "内，在", "极寒地区", CustomStyle.styleOfIce, "内死亡将不会失去装备."));
        components.add(Te.s(" 在效果持续时间内使用，会累加剩余持续时间."));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static Map<String, Integer> effectExpiredTickMap = new HashMap<>();

    @Override
    public void active(Player player) {
        InventoryOperation.removeItem(player, this, 1);
        if (effectExpiredTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            effectExpiredTickMap.put(Name.get(player),
                    effectExpiredTickMap.get(Name.get(player)) + Tick.min(getEffectLastMinutes()));
        } else {
            effectExpiredTickMap.put(Name.get(player), Tick.get() + Tick.min(getEffectLastMinutes()));
        }
        Compute.sendEffectLastTime(player, "item/heat_device",
                effectExpiredTickMap.get(Name.get(player)), 0, false);
    }

    public static boolean isInEffect(Player player) {
        return effectExpiredTickMap.getOrDefault(Name.get(player), 0) > Tick.get();
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
