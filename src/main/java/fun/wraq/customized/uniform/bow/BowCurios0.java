package fun.wraq.customized.uniform.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class BowCurios0 extends WraqBowUniformCurios {

    public BowCurios0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("闪避突袭").withStyle(style));
        components.add(Component.literal(" 使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("翻滚").withStyle(style)).
                append(Component.literal("后，获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.swiftness("15%总")).
                append(Component.literal("，并使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(style)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1.5倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("基础伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("自然选择").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean IsOn(Player player) {
        return WraqUniformCurios.isOn(BowCurios0.class, player);
    }

    public static int activeLastTick = 0;

    public static void Active(Player player) {
        if (!IsOn(player)) return;
        activeLastTick += Tick.get() + 60;
        Compute.sendEffectLastTime(player, UniformItems.BowCurios0.get().getDefaultInstance(), 60);
    }

    public static double BaseDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        if (activeLastTick > Tick.get()) return 0.5;
        return 0;
    }

    public static double SwiftnessUp(Player player) {
        if (!IsOn(player)) return 1;
        if (activeLastTick > Tick.get()) return 1.15;
        return 1;
    }
}
