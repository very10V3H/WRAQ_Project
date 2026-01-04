package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onshoot.OnReleaseSkillCurios;
import fun.wraq.common.impl.onshoot.OnShootManaArrowCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.process.system.element.RainbowCrystal;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManaCuriosYxwg extends WraqManaUniformCurios implements OnShootManaArrowCurios, UnCommonUniform,
        OnReleaseSkillCurios {

    public ManaCuriosYxwg(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("夺命").withStyle(hoverMainStyle()));
        components.add(Te.s(" 释放", "法球/法术技能", CustomStyle.styleOfMana, "有33%的概率在0.2s内额外射出2枚",
                "35%基础伤害", CustomStyle.styleOfPower, "的", "法球", CustomStyle.styleOfMana));
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

    @Override
    public void onReleaseSkill(Player player) {
        Random random = new Random();
        if (random.nextDouble() < 0.33) {
            WraqMixture.batchAddExShoot(player, 0.35, 2);
        }
    }
}
