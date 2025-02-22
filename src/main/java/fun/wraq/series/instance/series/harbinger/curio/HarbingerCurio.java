package fun.wraq.series.instance.series.harbinger.curio;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnWithStandDamageCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class HarbingerCurio extends WraqCurios implements RandomCurios, OnWithStandDamageCurios {

    public HarbingerCurio(Properties properties) {
        super(properties);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("淬火", hoverMainStyle()));
        components.add(Te.s(" 受到来自", "火元素", CustomStyle.styleOfFire, "怪物造成的伤害后:"));
        components.add(Te.s(" 提升", ComponentUtils.AttributeDescription.defencePenetration("12%"),
                " + ", ComponentUtils.AttributeDescription.manaPenetration("12%"), "，持续5s"));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfHarbinger;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfHarbinger();
    }

    @Override
    public void setAttribute(ItemStack stack) {
        if (RandomUtils.nextDouble(0, 1) < 0.8) {
            if (RandomUtils.nextBoolean()) {
                RandomCuriosAttributesUtil
                        .provideRandomAttributeFromList(2, 3, true, stack, List.of(
                                StringUtils.CuriosAttribute.defencePenetration,
                                StringUtils.CuriosAttribute.defencePenetration0,
                                StringUtils.CuriosAttribute.manaPenetration,
                                StringUtils.CuriosAttribute.manaPenetration0
                        ));
            } else {
                RandomCuriosAttributesUtil.randomAttackAttributeProvide(stack, 2, 3, true);
            }
        } else {
            RandomCuriosAttributesUtil.provideSingleAttribute(stack,
                    StringUtils.CuriosAttribute.finalDamageEnhance, 3, 0.5, 1);
        }
    }

    @Override
    public double fullRate() {
        return 3;
    }

    @Override
    public void onWithStandDamage(Player player, Mob mob, double damage) {
        if (Element.entityElementUnit.containsKey(mob)) {
            Element.Unit unit = Element.entityElementUnit.get(mob);
            if (unit.type().equals(Element.fire) && unit.value() > 0) {
                StableAttributesModifier.addM(player, StableAttributesModifier.playerDefencePenetrationModifier,
                        "HarbingerCurioPenetrationEnhance", 0.12, Tick.get() + Tick.s(5));
                StableAttributesModifier.addM(player, StableAttributesModifier.playerManaPenetrationModifier,
                        "HarbingerCurioPenetrationEnhance", 0.12, Tick.get() + Tick.s(5), this);
            }
        }
    }
}
