package fun.wraq.customized.uniform.attack;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCurios0 extends WraqAttackUniformCurios {

    public AttackCurios0(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfPower;
        Compute.DescriptionPassive(components, Component.literal("暴政").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.critDamage("10%总")));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("君临天下").withStyle(hoverMainStyle());
    }

    public static double PlayerFinalCritDamageEnhance(Player player) {
        if (!WraqUniformCurios.isOn(AttackCurios0.class, player)) return 0;
        return 0.1;
    }
}
