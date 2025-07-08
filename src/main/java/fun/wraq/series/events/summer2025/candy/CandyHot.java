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

public class CandyHot extends WraqItem implements ActiveItem {

    public CandyHot(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 右键使用（不消耗）获得持续5min的:", ChatFormatting.AQUA));
        components.add(Te.s(" · ", CustomStyle.styleOfFire,
                "归一化火元素强度 + 100%", CustomStyle.styleOfFire));
        components.add(Te.s(" · ", CustomStyle.styleOfFire,
                ComponentUtils.AttributeDescription.attackDamage("10%")));
        components.add(Te.s(" · ", CustomStyle.styleOfFire,
                ComponentUtils.AttributeDescription.manaDamage("10%")));
        components.add(Te.s(" · ", CustomStyle.styleOfFire,
                ComponentUtils.AttributeDescription.defencePenetration("10%")));
        components.add(Te.s(" · ", CustomStyle.styleOfFire,
                ComponentUtils.AttributeDescription.manaPenetration("10%")));
        ComponentUtils.coolDownTimeDescription(components, 600);
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public void active(Player player) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerFireElementStrength,
                "CandyHotModify", 1, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentAttackDamageModifier,
                "CandyHotModify", 0.1, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentManaDamageModifier,
                "CandyHotModify", 0.1, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerDefencePenetrationModifier,
                "CandyHotModify", 0.1, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerManaPenetrationModifier,
                "CandyHotModify", 0.1, Tick.get() + Tick.min(5));
        player.getCooldowns().addCooldown(this, Tick.min(10));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
