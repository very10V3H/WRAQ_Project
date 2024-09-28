package fun.wraq.customized.uniform.bow;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.system.element.RainbowCrystal;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BowCuriosYxwg extends WraqBowUniformCurios implements OnShootArrowCurios {

    public BowCuriosYxwg(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("追猎").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("有33%的概率在0.2s内额外射出2支").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("35%基础伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(CustomStyle.styleOfFlexible)));
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
            WraqQuiver.batchAddExShoot(player, 0.35, 2);
        }
    }
}
