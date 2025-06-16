package fun.wraq.series.overworld.cold.sc2.stone;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StoneSpiderKnife extends WraqSword implements OnHitEffectEquip {

    private final int tier;
    public StoneSpiderKnife(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    public double getEffectRate() {
        return new double[]{0.2, 0.25, 0.3, 0.4}[tier];
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfStone;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s(""));
        components.add(Te.s(" 对怪物造成伤害时，可以",
                "击碎" + String.format("%.0f%%", getEffectRate() * 100), getMainStyle(),
                "怪物抗性与特殊抗性"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfStoneSpider();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentDefenceModifier,
                "StoneSpiderKnife", getEffectRate(), Tick.get() + Tick.s(8),
                "item/stone_spider_knife");
        StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentManaDefenceModifier,
                "StoneSpiderKnife", getEffectRate(), Tick.get() + Tick.s(8));
    }
}
