package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestBow extends WraqBow implements OnHitEffectEquip {

    public ForestBow(Properties p_40524_, int tier) {
        super(p_40524_);
        Utils.attackDamage.put(this, new double[]{50, 55, 60, 70}[tier]);
        Utils.defencePenetration0.put(this, new double[]{1, 1, 2, 2}[tier]);
        Utils.critRate.put(this, new double[]{0.2f, 0.2f, 0.2f, 0.25f}[tier]);
        Utils.critDamage.put(this, new double[]{0.35, 0.35, 0.35, 0.35}[tier]);
        Element.LifeElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfForest;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("森林祝福").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.literal("箭矢命中目标后治疗自身").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("5%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Compute.playerHeal(player, player.getMaxHealth() * 0.05);
    }
}
