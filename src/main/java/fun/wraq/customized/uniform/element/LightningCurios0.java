package fun.wraq.customized.uniform.element;

import fun.wraq.common.Compute;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class LightningCurios0 extends WraqElementUniformCurios {

    public LightningCurios0(Properties p_41383_) {
        super(p_41383_);
        Element.LightningElementValue.put(this, 1d);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("怒雷蕴能").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Element.Description.LightningElementValue("100%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfLightning;
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(LightningCurios0.class, player);
    }

    public static double playerLightningElementValueEnhance(Player player) {
        if (!isOn(player)) return 1;
        return 2;
    }
}