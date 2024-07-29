package com.very.wraq.series.specialevents.summer;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.process.system.season.MySeason;
import com.very.wraq.projectiles.OnCuriosSlotAttributesModify;
import com.very.wraq.projectiles.OnCuriosSlotTickEffect;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SummerCurios2024 extends WraqCurios implements OnCuriosSlotAttributesModify, OnCuriosSlotTickEffect {

    private final int tier;
    public SummerCurios2024(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.maxHealth.put(this, new double[]{150, 200, 250, 300, 350, 400}[tier]);
        Utils.expUp.put(this, new double[]{0.2, 0.25, 0.3, 0.35, 0.4, 0.5}[tier]);
    }

    private final int[] exAttackDamage = new int[]{40, 80, 120, 160, 240, 320};
    private final int[] exManaDamage = new int[]{80, 160, 240, 320, 480, 640};
    private final double[] releaseSpeed = new double[]{0.05, 0.1, 0.15, 0.25, 0.35, 0.5};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("暑期就该摸鱼！").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("夏季").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的白天下水，每15s有概率获得一只鱼").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionPassive(components, Component.literal("热切激昂").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" 获得以下属性:").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal(" 1.").withStyle(CustomStyle.styleOfWater).
                append(ComponentUtils.AttributeDescription.AttackDamage(String.valueOf(exAttackDamage[tier]))));
        components.add(Component.literal(" 2.").withStyle(CustomStyle.styleOfWater).
                append(ComponentUtils.AttributeDescription.ManaDamage(String.valueOf(exManaDamage[tier]))));
        components.add(Component.literal(" 3.").withStyle(CustomStyle.styleOfWater).
                append(ComponentUtils.AttributeDescription.CoolDown(String.format("%.0f", releaseSpeed[tier] * 100))));
        components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("夏季").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("水中").withStyle(CustomStyle.styleOfWater)).
                append(Component.literal("获得双倍效能!").withStyle(ChatFormatting.LIGHT_PURPLE)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return ForgeEquipUtils.tierStyle.get(tier);
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSummerEvent();
    }

    @Override
    public int levelRequirement() {
        return new int[]{60, 90, 120, 150, 180, 210}[tier];
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }

    public static boolean isOn(Player player) {
        return WraqCurios.isOn(SummerCurios2024.class, player);
    }

    @Override
    public double attributes(Player player, String attributesType) {
        if (player.experienceLevel < levelRequirement()) return 0;
        double rate = 1;
        if (player.isInWater()
                || (player.level().dimension().equals(Level.OVERWORLD)
                && MySeason.currentSeason.contains(MySeason.summer))) rate = 2;
        return switch (attributesType) {
            case OnCuriosSlotAttributesModify.exAttackDamage -> exAttackDamage[tier] * rate;
            case OnCuriosSlotAttributesModify.exManaDamage -> exManaDamage[tier] * rate;
            case OnCuriosSlotAttributesModify.exReleaseSpeed -> releaseSpeed[tier] * rate;
            default -> 0;
        };
    }

    @Override
    public void tick(Player player) {
        if (!player.level().isClientSide) {
            if (player.tickCount % 300 == 0 && player.level().dimension().equals(Level.OVERWORLD)
                    && MySeason.currentSeason.contains(MySeason.summer) && player.isInWater()
                    && player.level().isDay()) {
                Compute.itemStackGive(player, new ItemStack(Items.TROPICAL_FISH));
                Compute.sendFormatMSG(player, Component.literal("摸鱼!").withStyle(CustomStyle.styleOfWater),
                        Component.literal("你摸到了一条鱼！").withStyle(ChatFormatting.GOLD));
            }
        } else {
            int rate = 1;
            if (player.isInWater()
                    || (player.level().dimension().equals(Level.OVERWORLD)
                    && MySeason.clientSeason != null && MySeason.clientSeason.contains(MySeason.summer))) rate = 2;
            Compute.sendEffectLastTimeToClientPlayer(this, rate, 20, false);
        }
    }
}
