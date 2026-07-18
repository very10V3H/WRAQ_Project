package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlainBow extends WraqBow implements OnHitEffectEquip {

    private final int tier;

    public PlainBow(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, new double[]{12, 25, 35, 40}[tier]);
        Utils.critRate.put(this, new double[]{0.1, 0.2, 0.2, 0.25}[tier]);
        Element.lifeElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
        this.tier = tier;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("平原祝福", ChatFormatting.GREEN));
        components.add(Te.s("箭矢命中目标后提供持续2s的",
                ComponentUtils.AttributeDescription.movementSpeed(tier == 0 ? "10%" : "20%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                "plainBowPassiveExMovementSpeed", tier == 0 ? 0.1 : 0.2, Tick.get() + 40, this);
    }
}
