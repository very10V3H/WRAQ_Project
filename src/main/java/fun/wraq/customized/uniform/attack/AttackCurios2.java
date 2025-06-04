package fun.wraq.customized.uniform.attack;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.WraqUniformCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class AttackCurios2 extends WraqAttackUniformCurios {

    public AttackCurios2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("独断").withStyle(style));
        components.add(Component.literal(" 当附近").withStyle(ChatFormatting.WHITE).
                append(Component.literal("没有其他玩家").withStyle(style)).
                append(Component.literal("时，为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.critRate("20%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.critDamage("20%总")));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("截胫剖心").withStyle(hoverMainStyle());
    }

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(AttackCurios2.class, player);
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
        if (!isOn(player) || !playerNearbyHasNoOthers(player)) return 0;
        return 0.15;
    }

    public static void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, UniformItems.ATTACK_CURIOS_2.get());
            return;
        }
        if (playerNearbyHasNoOthers(player))
            Compute.sendEffectLastTime(player, UniformItems.ATTACK_CURIOS_2.get(), 8888, 0, true);
        else Compute.sendEffectLastTime(player, UniformItems.ATTACK_CURIOS_2.get(), 0, 0, true);
    }
}
