package fun.wraq.series.events.midautumn;

import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaMooncake extends WraqItem implements ActiveItem {

    public ManaMooncake(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 神奇的月饼，吸收周围魔力来自我修复.."));
        components.add(Te.s(" 右键使用，消耗", ComponentUtils.AttributeDescription.manaValue("25%当前")));
        components.add(Te.s(" 获得持续2min的:"));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.damageDirectDecrease("20%")));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.healthRecover("1%")));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.manaRecover("5%")));
        components.add(Te.s(" · ", "饱和", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 冷却时间 5min", ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public void active(Player player) {
        player.getCooldowns().addCooldown(this, Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerWithStandDamageModifier,
                "mana_mooncake", -0.2, Tick.get() + Tick.min(2), this);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentHealthRecoverModifier,
                "mana_mooncake", 0.01, Tick.get() + Tick.min(2));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentManaRecoverModifier,
                "mana_mooncake", 0.05, Tick.get() + Tick.min(2));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, Tick.min(2)));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
