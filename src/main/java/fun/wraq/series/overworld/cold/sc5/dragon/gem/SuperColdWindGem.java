package fun.wraq.series.overworld.cold.sc5.dragon.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemCommonDamageEnhanceRateModifier;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SuperColdWindGem extends WraqPassiveGem implements GemCommonDamageEnhanceRateModifier, Decomposable {

    public final int tier;

    public SuperColdWindGem(Properties properties, List<AttributeMapValue> attributeMapValues,
                            Style hoverStyle, Component oneLineDescription, Component suffix, int tier) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.tier = tier;
    }

    public double getRate() {
        return new double[]{0.2, 0.35, 0.7}[tier];
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 对移动速度受损的敌人，造成的伤害获得",
                ComponentUtils.getCommonDamageEnhance(String.format("%.0f%%", getRate() * 100))));
        return components;
    }

    @Override
    public double getModifiedRate(Player player, Mob mob) {
        if (mob.getEffect(MobEffects.MOVEMENT_SLOWDOWN) != null) {
            return getRate();
        }
        return 0;
    }

    @Override
    public ItemStack getProduct() {
        if (tier == 0) {
            return new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get());
        }
        return null;
    }
}
