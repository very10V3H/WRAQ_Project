package com.very.wraq.customized.uniform.mana;

import com.very.wraq.common.Compute;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaCurios1 extends WraqManaUniformCurios {

    public ManaCurios1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("解构凡躯").withStyle(style));
        components.add(Component.literal(" 造成的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔法伤害").withStyle(style)).
                append(Component.literal("将获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("35%额外真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("凌于自然").withStyle(hoverMainStyle());
    }

    public static Map<Player, Boolean> onPlayerMap = new HashMap<>();

    public static boolean IsOn(Player player) {
        return WraqUniformCurios.isOn(ManaCurios1.class, player);
    }

    public static void ManaDamageExIgnoreDefenceDamage(Player player, Mob mob, double damage) {
        if (!IsOn(player)) return;
        Compute.Damage.DamageIgNoreDefenceToMonster(player, mob, damage * 0.35);
    }
}
