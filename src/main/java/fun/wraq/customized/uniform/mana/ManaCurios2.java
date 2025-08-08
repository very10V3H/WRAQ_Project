package fun.wraq.customized.uniform.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.WraqUniformCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class ManaCurios2 extends WraqManaUniformCurios {

    public ManaCurios2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("巫术研究").withStyle(style));
        components.add(Component.literal(" 周围").withStyle(ChatFormatting.WHITE).
                append(Component.literal("没有其他玩家").withStyle(style)).
                append(Component.literal("时，为你提供").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("50%总")));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("先天之能").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(ManaCurios2.class, player);
    }

    public static boolean playerNearbyHasNoOthers(Player player) {
        return Compute.getNearPlayer(player, 12).stream()
                .filter(eachPlayer -> !eachPlayer.equals(player))
                .toList()
                .isEmpty();
    }

    public static double playerFinalManaDamageEnhance(Player player) {
        if (!isOn(player) || !playerNearbyHasNoOthers(player)) return 0;
        return 0.5;
    }

    public static void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, UniformItems.MANA_CURIOS_2.get());
            return;
        }
        if (playerNearbyHasNoOthers(player))
            Compute.sendEffectLastTime(player, UniformItems.MANA_CURIOS_2.get(), 8888, 0, true);
        else Compute.sendEffectLastTime(player, UniformItems.MANA_CURIOS_2.get(), 0, 0, true);
    }
}
