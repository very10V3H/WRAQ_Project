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

public class HeatInjection extends WraqItem implements ActiveItem {

    public final int tier;

    public HeatInjection(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level,
                                List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 右键单次使用", ChatFormatting.AQUA));
        components.add(Te.s(" 获得持续", "20min", ChatFormatting.AQUA,
                "的", tier + 2 + "阶产热", CustomStyle.BUNKER_STYLE));
        components.add(Te.s(" 若当前已有针剂的产热效果等于该针剂，则累加持续时间."));
        components.add(Te.s(" 针剂的产热效果与其他产热效果不叠加."));
        components.add(Te.s(" 当有多种产热效果时，取最大值."));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static Map<String, Integer> effectTierMap = new HashMap<>();

    public static Map<String, Integer> effectExpiredTickMap = new HashMap<>();

    @Override
    public void active(Player player) {
        InventoryOperation.removeItem(player, this, 1);
        int expiredTick = 0;
        if (effectTierMap.getOrDefault(Name.get(player), 0) == tier + 2
                && effectExpiredTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            expiredTick = effectExpiredTickMap.get(Name.get(player));
        }
        effectTierMap.put(Name.get(player), tier + 2);
        if (expiredTick != 0) {
            effectExpiredTickMap.put(Name.get(player), expiredTick + Tick.min(20));
        } else {
            effectExpiredTickMap.put(Name.get(player), Tick.get() + Tick.min(20));
        }
        Compute.sendEffectLastTime(player, "item/heat_injection",
                effectExpiredTickMap.getOrDefault(Name.get(player), 0) - Tick.get(),
                effectTierMap.get(Name.get(player)), false);
        player.getCooldowns().addCooldown(this, Tick.s(5));
    }

    public static int getTier(Player player) {
        if (effectExpiredTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            return effectTierMap.get(Name.get(player));
        }
        return 0;
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
