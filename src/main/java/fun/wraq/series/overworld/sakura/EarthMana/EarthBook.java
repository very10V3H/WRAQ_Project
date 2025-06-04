package fun.wraq.series.overworld.sakura.EarthMana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EarthBook extends WraqOffHandItem {

    public EarthBook(Properties properties) {
        super(properties, Component.literal("魔法书").withStyle(CustomStyle.styleOfJacaranda));
        Utils.manaDamage.put(this, 200d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 350d);
        Utils.expUp.put(this, 0.65);
        Utils.manaHealthSteal.put(this, 0.04);
        Utils.levelRequire.put(this, 125);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfJacaranda;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Te.s("上古法师智识", style));
        components.add(Te.s(" 减少", ChatFormatting.GREEN,
                ComponentUtils.AttributeDescription.manaCost("20%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    public static double getManaCostRate(Player player) {
        if (player.getOffhandItem().is(ModItems.EARTH_BOOK.get())) {
            return -0.2;
        }
        return 0;
    }
}
