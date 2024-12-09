package fun.wraq.series.newrunes.chapter2;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.chapter2.SkyVexSpawnController;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.equip.WraqCurios;
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

public class SkyNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public SkyNewRune(Properties properties) {
        super(properties);
        Utils.critDamage.put(this, 0.1);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(SkyVexSpawnController.mobName).withStyle(CustomStyle.styleOfSky)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("腾云凌风").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 损失").withStyle(ChatFormatting.RED).
                append(ComponentUtils.AttributeDescription.critDamage("10%总")).
                append(Component.literal("，获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("20%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void tick(Player player) {
        if (WraqCurios.isOn(SkyNewRune.class, player)) {
            Compute.sendEffectLastTime(player, NewRuneItems.skyNewRune.get(), 0, true);
        } else Compute.removeEffectLastTime(player, NewRuneItems.skyNewRune.get());
    }

    public static double critDamageInfluence(Player player) {
        if (WraqCurios.isOn(SkyNewRune.class, player)) {
            return -0.1;
        }
        return 0;
    }
}
