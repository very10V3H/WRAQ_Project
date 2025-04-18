package fun.wraq.process.system.endlessinstance.item.instance.plain;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.TickItem;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ManaPlainPlant extends WraqItem implements TickItem {
    public ManaPlainPlant(Properties properties) {
        super(properties, true, false,
                List.of(
                        Te.s(" 放置在", "背包", CustomStyle.styleOfSunIsland, "时:"),
                        Te.s(" 当", ComponentUtils.AttributeDescription.health(""), "低于", "25%"),
                        Te.s(" 自动消耗", ChatFormatting.AQUA,
                                "，并回复", ComponentUtils.AttributeDescription.maxHealth("50%"),
                                "/", ComponentUtils.AttributeDescription.maxMana("25%")),
                        ComponentUtils.getCooldownTimeDescription(10)
                ));
    }

    @Override
    public void handleTick(Player player, ItemStack stack) {
        if (!player.getCooldowns().isOnCooldown(this) && player.getHealth() / player.getMaxHealth() < 0.25) {
            stack.shrink(1);
            player.getCooldowns().addCooldown(this, Tick.s(10));
            Compute.playerHeal(player, player.getMaxHealth() * 0.5);
            Mana.addOrCostPlayerMana(player, Mana.getPlayerMaxManaNum(player) * 0.25);
        }
    }
}
