package fun.wraq.series.newrunes.chapter1;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.chapter1.FireLightSpawnController;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolcanoNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {
    public VolcanoNewRune(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 30d);
        Utils.manaDamage.put(this, 60d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("熔岩强击").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 每第三次 ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal(" 会拥有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("双倍基础伤害").withStyle(hoverMainStyle())));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static boolean isOn(Player player) {
        return WraqCurios.isOn(VolcanoNewRune.class, player);
    }

    public static Map<String, Integer> playerAttackCounts = new HashMap<>();

    public static double attackEnhance(Player player) {
        if (!isOn(player)) return 0;
        double rate = 0;
        String name = player.getName().getString();
        int counts = (playerAttackCounts.getOrDefault(name, 0) + 1);
        if (counts == 3) rate += 1;
        playerAttackCounts.put(name, counts % 3);
        Compute.sendEffectLastTime(player, NewRuneItems.volcanoNewRune.get(), 8888, counts % 3, true);
        return rate;
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(FireLightSpawnController.mobName).withStyle(CustomStyle.styleOfVolcano)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
