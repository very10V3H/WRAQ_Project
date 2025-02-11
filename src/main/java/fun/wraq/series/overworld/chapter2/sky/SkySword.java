package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkySword extends WraqSword implements OnHitEffectEquip {

    public SkySword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 120d);
        Utils.defencePenetration0.put(this, 6d);
        Utils.critRate.put(this, 0.25);
        Element.WindElementValue.put(this, 0.8);
        Utils.levelRequire.put(this, 72);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("锐利锋刃", getMainStyle()));
        components.add(Te.s(" 普攻", CustomStyle.styleOfPower, "命中后，获得持续3s的:"));
        components.add(Te.s(" 1.", getMainStyle(),
                ComponentUtils.AttributeDescription.movementSpeed("12%")));
        components.add(Te.s(" 2.", getMainStyle(),
                ComponentUtils.AttributeDescription.critRate("10%")));
        components.add(Te.s(" 3.", getMainStyle(),
                ComponentUtils.AttributeDescription.critDamage("20%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                "SkySword passive movementSpeed", 0.12, Tick.get() + 60);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCritRateModifier,
                "SkySword passive critDamage", 0.1, Tick.get() + 60);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCritDamageModifier,
                "SkySword passive critDamage", 0.2, Tick.get() + 60);
        Compute.sendEffectLastTime(player, ModItems.SKY_SWORD.get(), 60);
    }
}
