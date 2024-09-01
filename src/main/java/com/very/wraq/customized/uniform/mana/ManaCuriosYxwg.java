package com.very.wraq.customized.uniform.mana;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.process.system.element.RainbowCrystal;
import com.very.wraq.projectiles.OnShootManaArrowCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManaCuriosYxwg extends WraqManaUniformCurios implements OnShootManaArrowCurios {

    public ManaCuriosYxwg(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("夺命").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 普通法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("有33%的概率在0.2s内额外射出2枚").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("35%基础伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球").withStyle(CustomStyle.styleOfMana)));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("英雄挽歌");
    }

    @Override
    public String getName() {
        return "yxwg";
    }

    @Override
    public void onShoot(Player player) {
        Random random = new Random();
        if (random.nextDouble() < 0.33) {
            WraqMixture.batchAddExShoot(player, 0.35, 2);
        }
    }
}
