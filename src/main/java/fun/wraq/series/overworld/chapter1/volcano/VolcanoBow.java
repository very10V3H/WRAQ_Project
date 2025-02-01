package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
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

public class VolcanoBow extends WraqBow implements OnHitEffectEquip {

    private final int tier;
    public VolcanoBow(Properties p_40524_, int tier) {
        super(p_40524_);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{80, 85, 90, 100}[tier]);
        Utils.defencePenetration0.put(this, new double[]{2, 2, 3, 3}[tier]);
        Utils.critRate.put(this, new double[]{0.2, 0.2, 0.2, 0.25}[tier]);
        Element.FireElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
    }

    private final double[] passiveRate = new double[]{30, 40, 50, 60};

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("熔岩涌动").withStyle(style));
        components.add(Component.literal("箭矢命中目标后获得持续2.5s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("" + passiveRate[tier])));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerAttackDamageModifier,
                "volcano bow passive", passiveRate[tier], Tick.get() + 50, "item/volcanobow");
    }
}
