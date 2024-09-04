package com.very.wraq.customized.uniform.bow;

import com.very.wraq.common.Compute;
import com.very.wraq.customized.UniformItems;
import com.very.wraq.customized.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class BowCurios2 extends WraqBowUniformCurios {

    public BowCurios2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfFlexible;
        Compute.DescriptionPassive(components, Component.literal("独行猎手").withStyle(style));
        components.add(Component.literal(" 周围").withStyle(ChatFormatting.WHITE).
                append(Component.literal("没有其他玩家").withStyle(style)).
                append(Component.literal("时，为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritRate("20%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("20%总")));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("中心法则").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(BowCurios2.class, player);
    }

    public static boolean playerNearbyHasNoOthers(Player player) {
        List<Player> players = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 30, 30, 30));
        players.removeIf(player1 -> player1.equals(player) || player1.distanceTo(player) < 12);
        return players.isEmpty();
    }

    public static double playerCritRateUp(Player player) {
        if (!isOn(player) || !playerNearbyHasNoOthers(player)) return 0;
        return 0.2;
    }

    public static double playerCritDamageEnhance(Player player) {
        if (!isOn(player) || !playerNearbyHasNoOthers(player)) return 1;
        return 1.2;
    }

    public static void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, UniformItems.BowCurios2.get());
            return;
        }
        if (playerNearbyHasNoOthers(player))
            Compute.sendEffectLastTime(player, UniformItems.BowCurios2.get(), 8888, 0, true);
        else Compute.sendEffectLastTime(player, UniformItems.BowCurios2.get(), 0, 0, true);
    }
}
