package fun.wraq.series.overworld.cold.sc5.dragon.gem;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SuperColdDragonBreathGem extends WraqPassiveGem implements GemTickHandler {

    public final int tier;

    public SuperColdDragonBreathGem(Properties properties, List<AttributeMapValue> attributeMapValues,
                                    Style hoverStyle, Component oneLineDescription, Component suffix, int tier) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.tier = tier;
    }

    public double getRange() {
        return new double[]{3, 5, 8}[tier];
    }

    public double getPenetration0Value() {
        return new double[]{100, 250, 500}[tier];
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 缓速周围", (int) getRange() + "格",
                "内的敌人，削减其", String.format("%.0f", getPenetration0Value()) + "双抗", CustomStyle.styleOfStone));
        return components;
    }

    @Override
    public void tick(Player player) {
        Compute.getNearMob(player, getRange()).forEach(eachMob -> {
            StableAttributesModifier.addM(eachMob, StableAttributesModifier.mobDefenceModifier,
                    "SuperColdDragonGem", -getPenetration0Value(),
                    Tick.get() + 30, "item/super_cold_dragon_gem");
            StableAttributesModifier.addM(eachMob, StableAttributesModifier.mobManaDefenceModifier,
                    "SuperColdDragonGem", -getPenetration0Value(),
                    Tick.get() + 30);
            Compute.addSlowDownEffect(eachMob, 30, 2);
        });
    }
}
