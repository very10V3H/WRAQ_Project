package fun.wraq.series.overworld.sakura.Ship;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShipBow extends WraqBow implements InCuriosOrEquipSlotAttributesModify {

    public ShipBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 450d);
        Utils.defencePenetration0.put(this, 22d);
        Utils.critRate.put(this, 0.25);
        Element.waterElementValue.put(this, 1d);
        Utils.levelRequire.put(this, 140);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfShip;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("领航").withStyle(style));
        components.add(Component.literal(" 根据周围玩家数量，为你提供").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defencePenetration("5 - 20")));
        components.add(Component.literal(" - 提供的护甲数额随周围玩家数量增长。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(new Attribute(Utils.defencePenetration0, 5 * Math.min(4, Compute.getNearEntity(player, Player.class, 6)
                .stream().filter(entity -> entity.distanceTo(player) <= 6).count())));
    }
}
