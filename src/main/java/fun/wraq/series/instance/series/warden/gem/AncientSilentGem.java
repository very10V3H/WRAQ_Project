package fun.wraq.series.instance.series.warden.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import fun.wraq.series.instance.series.warden.WardenItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AncientSilentGem extends WraqPassiveGem implements GemTickHandler, Decomposable {

    private final int tier;
    public AncientSilentGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                            Component oneLineDescription, Component suffix, int tier) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.tier = tier;
    }

    private final double[] rate = new double[]{0.03, 0.04, 0.05, 0.06};

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfWarden;
        ComponentUtils.solePassiveDescription(components, Te.s("潜伏", style));
        components.add(Te.s(" 潜行", style, "每秒将提供1层", "「寂」", style));
        components.add(Te.s(" 每层", "「寂」", style, "将提供:"));
        components.add(Te.s(" 1.", ComponentUtils.getCommonDamageEnhance(Compute.getPercent(rate[tier]))));
        components.add(Te.s(" 2.", Compute.getPercent(rate[tier]) + "受伤减免", ChatFormatting.GREEN));
        components.add(Te.s(" 至多叠加至", "5层", style));
        components.add(Te.s(" 离开", "潜行", style, "状态，每秒损失一层"));
        return components;
    }

    private static final String DAMAGE_ENHANCE_KEY = "AncientSilentGemDamageEnhance";
    private static final String WITHSTAND_DAMAGE_REDUCE_KEY = "AncientSilentGemWithstandDamageReduce";

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 0) {
            int tier;
            if (player.isShiftKeyDown()) {
                StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerCommonDamageEnhance,
                        DAMAGE_ENHANCE_KEY, rate[this.tier], Tick.get() + 30, 5, "");
                StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerWithstandDamageReduce,
                        WITHSTAND_DAMAGE_REDUCE_KEY, rate[this.tier], Tick.get() + 30, 5, "");
                tier = StableTierAttributeModifier.getAttributeModifierTier(player,
                        StableTierAttributeModifier.playerCommonDamageEnhance, DAMAGE_ENHANCE_KEY);
            } else {
                StableTierAttributeModifier.relativeSetAttributeModifierTier(player,
                        StableTierAttributeModifier.playerCommonDamageEnhance, DAMAGE_ENHANCE_KEY,
                        -1, "");
                StableTierAttributeModifier.relativeSetAttributeModifierTier(player,
                        StableTierAttributeModifier.playerWithstandDamageReduce, WITHSTAND_DAMAGE_REDUCE_KEY,
                        -1, "");
                tier = StableTierAttributeModifier.getAttributeModifierTier(player,
                        StableTierAttributeModifier.playerCommonDamageEnhance, DAMAGE_ENHANCE_KEY);
            }
            if (tier > 0) {
                Compute.sendEffectLastTime(player, "item/warden_matrix", 88, tier, true);
            } else {
                Compute.sendEffectLastTime(player, "item/warden_matrix", 0, tier, false);
            }
        }
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(WardenItems.WARDEN_SOUL_INGOT.get(), 10);
    }
}
