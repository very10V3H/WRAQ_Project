package fun.wraq.series.overworld.cold.sc2.stone;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
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
        Utils.defencePenetration.put(this, 0.8);
    }

    public double getEffectRate() {
        return new double[]{0, 0, 0.1, 0.25}[tier];
    }

    public int getPenetration0() {
        return new int[]{0, 100, 200, 400}[tier];
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfStone;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (tier < 1) {
            return components;
        }
        ComponentUtils.descriptionPassive(components, Te.s("切割", getMainStyle()));
        components.add(Te.s(" 对怪物造成伤害时，击碎其:"));
        if (tier > 1) {
            components.add(Te.s(" · ",
                    getPenetration0(), "+", String.format("%.0f%%", getEffectRate() * 100) + "双抗", getMainStyle()));
        } else {
            components.add(Te.s(" · ", getPenetration0() + "双抗", getMainStyle()));
        }
        components.add(Te.s(" 效果持续8s."));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfStoneSpider();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        if (tier > 0) {
            StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentDefenceModifier,
                    "StoneSpiderKnifePercentDefencePenetration", -getEffectRate(), Tick.get() + Tick.s(8),
                    "item/stone_spider_knife");
            StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentManaDefenceModifier,
                    "StoneSpiderKnifePercentManaPenetration", -getEffectRate(), Tick.get() + Tick.s(8));
            StableAttributesModifier.addM(mob, StableAttributesModifier.mobDefenceModifier,
                    "StoneSpiderKnifeDefencePenetration0", -getPenetration0(), Tick.get() + Tick.s(8));
            StableAttributesModifier.addM(mob, StableAttributesModifier.mobManaDefenceModifier,
                    "StoneSpiderKnifeManaPenetration0", -getPenetration0(), Tick.get() + Tick.s(8));
        }
    }
}
