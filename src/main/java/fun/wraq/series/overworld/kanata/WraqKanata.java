package fun.wraq.series.overworld.kanata;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WraqKanata extends WraqMainHandEquip implements ActiveItem {

    private final double movementSpeedWithoutBattle;
    private final Style style;
    private final Component suffix;
    public WraqKanata(Properties properties, double movementSpeedWithoutBattle, Style style, Component suffix) {
        super(properties);
        this.movementSpeedWithoutBattle = movementSpeedWithoutBattle;
        this.style = style;
        this.suffix = suffix;
        Utils.movementSpeedWithoutBattle.put(this, movementSpeedWithoutBattle);
    }

    @Override
    public Style getMainStyle() {
        return style;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("急行军", CustomStyle.styleOfKaze));
        components.add(Te.s(" 为附近所有玩家提供", ComponentUtils.AttributeDescription
                .movementSpeedWithoutBattle(String.format("%.0f%%", movementSpeedWithoutBattle * 100)), "，持续30s"));
        ComponentUtils.coolDownTimeDescription(components, 120);
        return components;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public Component getType() {
        return Te.s("行军刀", CustomStyle.styleOfKaze);
    }

    @Override
    public void active(Player player) {
        Compute.getNearEntity(player, Player.class, 8)
                .stream().map(entity -> (Player) entity)
                .forEach(eachPlayer -> {
                    StableAttributesModifier.addM(eachPlayer,
                            StableAttributesModifier.playerMovementSpeedWithoutBattleModifier,
                            "kanata active " + String.format("%.0f%%", movementSpeedWithoutBattle * 100),
                            movementSpeedWithoutBattle, Tick.get() + 20 * 30, this);
        });
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
