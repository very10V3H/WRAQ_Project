package fun.wraq.series.instance.series.warden.gem;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnKillMob;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import fun.wraq.series.gems.passive.impl.GemWithstandDamageRateModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AncientEchoGem extends WraqPassiveGem implements GemWithstandDamageRateModifier, GemTickHandler, GemOnKillMob {

    public AncientEchoGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfWarden;
        ComponentUtils.solePassiveDescription(components, Te.s("回响", style));
        components.add(Te.s(" 受到的", "30%伤害", ChatFormatting.RED, "将在3s内以", "回响", style, "形式逐渐扣除."));
        components.add(Te.s(" 击杀任意一名", "敌人", ChatFormatting.RED, "将", "清空", ChatFormatting.GREEN, "回响,"));
        components.add(Te.s(" 并回复", ComponentUtils.AttributeDescription.health("8%已损失")));
        return components;
    }


    public static Map<Player, Double> withstandDamageSumMap = new HashMap<>();
    public static Map<Player, Double> lastRecordSumMap = new HashMap<>();

    @Override
    public double getModifiedRate(Player player, Mob mob, double damage) {
        withstandDamageSumMap.compute(player, (k, v) -> v == null ? damage * 0.3 : v + damage * 0.3);
        lastRecordSumMap.put(player, withstandDamageSumMap.get(player));
        return -0.3;
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 10 == 0) {
            double lastRecordSumMap = AncientEchoGem.lastRecordSumMap.getOrDefault(player, 0d);
            if (Math.abs(lastRecordSumMap - 0) > 1e-6 && withstandDamageSumMap.getOrDefault(player, 0d) > 0) {
                double damage = lastRecordSumMap / 6;
                Compute.decreasePlayerHealth(player, damage, Te.s("被", "黑暗回响", CustomStyle.styleOfWarden, "吞没了"));
                withstandDamageSumMap.compute(player, (k, v) -> v == null ? 0 : v - damage);
                double storedValue = withstandDamageSumMap.get(player);
                if (storedValue > 0) {
                    Compute.sendDebuffTime(player, "item/warden_matrix", (int) (storedValue * 20),
                            (int) ((storedValue - damage) / player.getMaxHealth() * 100), true);
                } else {
                    Compute.removeDebuffTime(player, "item/warden_matrix");
                }
            }
        }
    }

    @Override
    public void onKill(Player player, Mob mob) {
        withstandDamageSumMap.remove(player);
        Compute.playerHeal(player, (player.getMaxHealth() - player.getHealth() * 0.08));
        Compute.removeDebuffTime(player, "item/warden_matrix");
    }
}
