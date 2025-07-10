package fun.wraq.series.events.summer2025.candy;

import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.item.InventoryOperation;
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

public class CandyIce extends WraqItem implements ActiveItem {

    public CandyIce(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 右键使用，获得持续5min的:", ChatFormatting.AQUA));
        components.add(Te.s(" · ", CustomStyle.styleOfIce,
                ComponentUtils.AttributeDescription.coolDown("50")));
        components.add(Te.s(" · ", CustomStyle.styleOfIce,
                "归一化冰元素强度 + 100%", CustomStyle.styleOfIce));
        components.add(Te.s(" · ", CustomStyle.styleOfIce,
                ComponentUtils.AttributeDescription.movementSpeed("20%")));
        components.add(Te.s(" · ", CustomStyle.styleOfIce,
                ComponentUtils.AttributeDescription.healthRecover("40")));
        ComponentUtils.coolDownTimeDescription(components, 300);
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public void active(Player player) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCooldownModifier,
                "CandyIceModify", 0.5, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerIceElementStrength,
                "CandyIceModify", 1, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                "CandyIceModify", 0.2, Tick.get() + Tick.min(5));
        StableAttributesModifier.addM(player, StableAttributesModifier.playerHealthRecoverModifier,
                "CandyIceModify", 40, Tick.get() + Tick.min(5), this);
        player.getCooldowns().addCooldown(this, Tick.min(5));
        InventoryOperation.removeItem(player, this, 1);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
