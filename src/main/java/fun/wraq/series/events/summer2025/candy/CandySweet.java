package fun.wraq.series.events.summer2025.candy;

import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CandySweet extends WraqItem implements ActiveItem {

    public CandySweet(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 右键使用（不消耗）获得持续5min的:", ChatFormatting.AQUA));
        components.add(Te.s(" · ", CustomStyle.styleOfLucky,
                ComponentUtils.AttributeDescription.healAmplification("20%")));
        components.add(Te.s(" · ", CustomStyle.styleOfLucky,
                ComponentUtils.AttributeDescription.maxHealth("20%")));
        components.add(Te.s(" · ", CustomStyle.styleOfLucky,
                ComponentUtils.AttributeDescription.healthRecover("2%")));
        ComponentUtils.coolDownTimeDescription(components, 600);
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public void active(Player player) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerHealAmplifierModifier,
                "CandySweetModify", 0.2, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentMaxHealth,
                "CandySweetModify", 0.2, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentHealthRecoverModifier,
                "CandySweetModify", 0.02, Tick.get() + Tick.min(5));
        player.getCooldowns().addCooldown(this, Tick.min(10));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
