package fun.wraq.series.overworld.mt.curio;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TianShou extends WraqCurios {
    public TianShou(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("供奉", hoverMainStyle()));
        components.add(Te.s(" 在", "战斗状态", CustomStyle.styleOfRed, "下，若拥有",
                ComponentUtils.AttributeDescription.health("100%"), "，则消耗",
                ComponentUtils.AttributeDescription.health("40%")));
        components.add(Te.s(" 获得持续", "5s", ChatFormatting.AQUA, "的"));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.MANA_TOWER_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfManaTower();
    }

    @Override
    public void tick(Player player) {
        if (Compute.playerIsInBattle(player) && player.getHealth() == player.getMaxHealth()) {
            Compute.decreasePlayerHealth(player, player.getMaxHealth() * 0.4, 0.1);
        }
        super.tick(player);
    }
}
