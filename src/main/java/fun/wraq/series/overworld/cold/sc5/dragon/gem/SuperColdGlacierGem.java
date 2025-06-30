package fun.wraq.series.overworld.cold.sc5.dragon.gem;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemCommonDamageEnhanceRateModifier;
import fun.wraq.series.gems.passive.impl.GemOnWithstandDamage;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import fun.wraq.series.gems.passive.impl.GemWithstandDamageRateModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperColdGlacierGem extends WraqPassiveGem implements GemCommonDamageEnhanceRateModifier,
        GemWithstandDamageRateModifier, GemOnWithstandDamage, GemTickHandler {

    public final int tier;

    public SuperColdGlacierGem(Properties properties, List<AttributeMapValue> attributeMapValues,
                               Style hoverStyle, Component oneLineDescription, Component suffix, int tier) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.tier = tier;
    }

    public double getDefenceValueRate() {
        return new double[]{0.2, 0.45, 0.8}[tier];
    }

    public double getDamageEnhanceRate() {
        return new double[]{0.1, 0.2, 0.35}[tier];
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 受到伤害后，获得", String.format("%.0f%%", getDefenceValueRate() * 100)
                + "最大生命值", CustomStyle.styleOfMaxHealth, "护盾", CustomStyle.styleOfStone));
        components.add(Te.s(" 护盾持续30s，脱离战斗后方可再次触发."));
        components.add(Te.s(" 拥有高于",
                "10%最大生命值", CustomStyle.styleOfMaxHealth, "护盾", CustomStyle.styleOfStone,
                "时，获得"));
        components.add(Te.s(" ·", ComponentUtils
                .getCommonDamageEnhance(String.format("%.0f%%", getDamageEnhanceRate() * 100))));
        components.add(Te.s(" ·",
                String.format("%.0f%%", getDamageEnhanceRate() * 100) + "伤害减免", CustomStyle.styleOfStone));
        return components;
    }

    public static Map<String, Boolean> allowActiveMap = new HashMap<>();

    @Override
    public void tick(Player player) {
        if (!Compute.playerIsInBattle(player)) {
            if (!allowActiveMap.getOrDefault(Name.get(player), false)) {
                Compute.sendEffectLastTime(player, "item/super_cold_dragon_gem", 0, true);
                allowActiveMap.put(Name.get(player), true);
            }
        }
    }

    @Override
    public void onWithStandDamage(Player player, Mob mob, double damage) {
        if (allowActiveMap.getOrDefault(Name.get(player), false)) {
            allowActiveMap.put(Name.get(player), false);
            Compute.removeEffectLastTime(player, "item/super_cold_dragon_gem");
            Shield.providePlayerShield(player, Tick.s(30), player.getMaxHealth() * getDefenceValueRate());
        }
    }

    @Override
    public double getModifiedRate(Player player, Mob mob) {
        if (Shield.computePlayerShield(player) > player.getMaxHealth() * 0.1) {
            return getDamageEnhanceRate();
        }
        return 0;
    }

    @Override
    public double getModifiedRate(Player player, Mob mob, double damage) {
        if (Shield.computePlayerShield(player) > player.getMaxHealth() * 0.1) {
            return -getDamageEnhanceRate();
        }
        return 0;
    }
}