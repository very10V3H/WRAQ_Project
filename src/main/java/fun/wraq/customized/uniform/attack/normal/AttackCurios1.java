package fun.wraq.customized.uniform.attack.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class AttackCurios1 extends WraqAttackUniformCurios {

    public AttackCurios1(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("横行", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("暴怒", hoverMainStyle());
        components.add(Te.s(" 暴击", hoverMainStyle(), "将会获得一层", countName, "，每层持续5s，至多叠加至5层"));
        components.add(Te.s(" 每层", countName, "为你提供:"));
        components.add(Te.s(" 1.", ComponentUtils.AttributeDescription.critDamage("5%")));
        components.add(Te.s(" 2.", ComponentUtils.AttributeDescription.attackDamage("5%总")));
        components.add(Te.s(" 只有近战攻击的暴击能够触发横行", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(Te.s(" 残暴的君主，终将被民众推翻。", hoverMainStyle()));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("独夫之心").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Integer> playerCountsMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerLastTickMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqCurios.hasCurio(player, UniformItems.ATTACK_CURIOS_1.get())
                || WraqCurios.hasCurio(player, UniformItems.ATTACK_ENHANCED_CURIOS_0.get());
    }

    public static void playerCritEffect(Player player) {
        if (!isOn(player)) {
            return;
        }
        if (!playerLastTickMap.containsKey(player) || playerLastTickMap.get(player) < Tick.get()) {
            playerCountsMap.put(player, 0);
        }
        playerLastTickMap.put(player, Tick.get() + 100);
        int maxCount = 5;
        if (WraqCurios.hasCurio(player, UniformItems.ATTACK_ENHANCED_CURIOS_0.get())) {
            maxCount = 7;
        }
        int counts = Math.min(maxCount, playerCountsMap.getOrDefault(player, 0) + 1);
        playerCountsMap.put(player, counts);
        Compute.sendEffectLastTime(player, UniformItems.ATTACK_CURIOS_1.get(), 100, counts, false);
    }

    public static double playerCritDamageUp(Player player) {
        if (!isOn(player)) return 0;
        if (playerLastTickMap.getOrDefault(player, 0) > Tick.get()) {
            return 0.05 * playerCountsMap.getOrDefault(player, 0);
        }
        return 0;
    }

    public static double playerAttackDamageEnhance(Player player) {
        if (!isOn(player)) return 0;
        if (playerLastTickMap.getOrDefault(player, 0) > Tick.get()) {
            return 0.05 * playerCountsMap.getOrDefault(player, 0);
        }
        return 0;
    }
}
