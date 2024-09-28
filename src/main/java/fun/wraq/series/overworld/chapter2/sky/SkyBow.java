package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyBow extends WraqBow implements OnHitEffectMainHandWeapon {

    public SkyBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 120d);
        Utils.defencePenetration0.put(this, 6d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.WindElementValue.put(this, 0.8);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("鹰隼之速").withStyle(style));
        components.add(Te.m(" 箭矢", CustomStyle.styleOfFlexible).
                append(Te.m("命中目标后，为你提供持续3s的")));
        components.add(Te.m(" ").
                append(ComponentUtils.AttributeDescription.movementSpeed("30%")));
        components.add(Te.m(" ").
                append(ComponentUtils.AttributeDescription.critDamage("30%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCritDamageModifier,
                "SkyBow passive critDamage", 0.35, Tick.get() + 60);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                "SkyBow passive movementSpeed", 0.35, Tick.get() + 60);
        Compute.sendEffectLastTime(player, ModItems.SkyBow.get(), 60);
    }
}
