package fun.wraq.customized.uniform.bow.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class BowCurios0 extends WraqBowUniformCurios {

    public BowCurios0(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("闪避突袭", hoverMainStyle()));
        components.add(Te.s(" 使用", "翻滚", hoverMainStyle(), "后，获得",
                ComponentUtils.AttributeDescription.swiftness("15%总")));
        components.add(Te.s(" 并使", "箭矢", hoverMainStyle(), "提升",
                "50%基础伤害", CustomStyle.styleOfPower, "."));
        components.add(Te.s(" 效果持续3s.", ChatFormatting.AQUA));
        components.add(Te.s(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。", hoverMainStyle()));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("自然选择").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqCurios.hasCurio(player, UniformItems.BOW_CURIOS_0.get())
                || WraqCurios.hasCurio(player, UniformItems.BOW_ENHANCED_CURIOS_0.get());
    }

    public static int activeLastTick = 0;

    public static void onReleaseRolling(Player player) {
        if (!isOn(player)) {
            return;
        }
        activeLastTick += Tick.get() + 60;
        Compute.sendEffectLastTime(player, UniformItems.BOW_CURIOS_0.get().getDefaultInstance(), 60);
    }

    public static double getArrowBaseDamageEnhanceRate(Player player) {
        if (!isOn(player)) {
            return 0;
        }
        if (activeLastTick > Tick.get()) {
            if (WraqCurios.hasCurio(player, UniformItems.BOW_CURIOS_0.get())) {
                return 0.5;
            } else {
                return 0.75;
            }
        }
        return 0;
    }

    public static double getSwiftnessUpRate(Player player) {
        if (!isOn(player)) {
            return 0;
        }
        if (activeLastTick > Tick.get()) {
            return 0.15;
        }
        return 0;
    }
}
