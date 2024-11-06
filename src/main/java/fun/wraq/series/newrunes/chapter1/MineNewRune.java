package fun.wraq.series.newrunes.chapter1;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.chapter1.MineSkeletonSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MineNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public MineNewRune(Properties properties) {
        super(properties);
        Utils.defence.put(this, 4d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("致密金属").withStyle(style));
        components.add(Component.literal(" 拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("60%")).
                append(Component.literal("时，为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(style).
                append(Component.literal("减少15%受到的伤害").withStyle(style)));
        components.add(Component.literal(" 2.").withStyle(style).
                append(ComponentUtils.getCommonDamageEnhance("12%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static boolean passiveTrig(Player player) {
        return WraqCurios.isOn(MineNewRune.class, player) && player.getHealth() / player.getMaxHealth() >= 0.6;
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 8) {
            if (passiveTrig(player)) {
                Compute.sendEffectLastTime(player, NewRuneItems.mineNewRune.get(), 0, true);
            } else {
                Compute.removeEffectLastTime(player, NewRuneItems.mineNewRune.get());
            }
        }
    }

    public static double withstandDamageInfluence(Player player) {
        return passiveTrig(player) ? -0.15 : 0;
    }

    public static double damageEnhance(Player player) {
        return passiveTrig(player) ? 0.12 : 0;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(MineSkeletonSpawnController.mobName).withStyle(CustomStyle.styleOfMine)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

