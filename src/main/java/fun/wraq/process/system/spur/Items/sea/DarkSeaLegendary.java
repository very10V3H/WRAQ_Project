package fun.wraq.process.system.spur.Items.sea;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DarkSeaLegendary extends WraqSceptre implements ActiveItem {

    public DarkSeaLegendary(Properties properties) {
        super(properties);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSea;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("深海祝福", getMainStyle()));
        components.add(Te.s(" 获得:"));
        components.add(Te.s(" · ", ComponentUtils.getManaDamageEnhance("")));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.manaRecover("")));
        ComponentUtils.descriptionActive(components, Te.s());
        components.add(Te.s(" 制造一个领域，使领域内的怪物"));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.movementSpeed("")));
        components.add(Te.s(" · ", "伤害"));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.manaDefence("")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void active(Player player) {
        PersistentRangeEffect.addEffect(player, WraqPower.getDefaultTargetPos(player, 32), 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangeMob().forEach(mob -> {
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2));
                    StableAttributesModifier.addM(mob, StableAttributesModifier.mobCauseDamageRateModifier,
                            "", 0.1, Tick.get() + 10, "");
                    StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentManaDefenceModifier,
                            "", 0.1, Tick.get() + 10, "");
                });
            }
        }, 10, Tick.s(3));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
