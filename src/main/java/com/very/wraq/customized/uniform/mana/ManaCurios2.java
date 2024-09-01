package com.very.wraq.customized.uniform.mana;

import com.very.wraq.common.Compute;
import com.very.wraq.customized.UniformItems;
import com.very.wraq.customized.WraqUniformCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                append(Compute.AttributeDescription.ManaDamage("50%总")));
        components.add(Component.literal(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("先天之能").withStyle(hoverMainStyle());
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(ManaCurios2.class, player);
    }

    public static boolean playerNearbyHasNoOthers(Player player) {
        List<Player> players = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 30, 30, 30));
        players.removeIf(player1 -> player1.equals(player) || player1.distanceTo(player) < 12);
        return players.isEmpty();
    }

    public static double playerFinalManaDamageEnhance(Player player) {
        if (!isOn(player) || !playerNearbyHasNoOthers(player)) return 1;
        return 1.5;
    }

    public static void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, UniformItems.ManaCurios2.get());
            return;
        }
        if (playerNearbyHasNoOthers(player))
            Compute.sendEffectLastTime(player, UniformItems.ManaCurios2.get(), 8888, 0, true);
        else Compute.sendEffectLastTime(player, UniformItems.ManaCurios2.get(), 0, 0, true);
    }
}
