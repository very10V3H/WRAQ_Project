package fun.wraq.series.newrunes.chapter2;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.chapter2.HuskSpawnController;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class HuskNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public HuskNewRune(Properties properties) {
        super(properties);
        Utils.healingAmplification.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("灵魂收集").withStyle(style));
        components.add(Component.literal(" 击杀怪物时，会收集其").withStyle(ChatFormatting.WHITE).
                append(Component.literal("灵魂").withStyle(style)));
        components.add(Component.literal(" 每枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.getCommonDamageEnhance("1%")));
        components.add(Component.literal(" 至多可收集20枚，每枚存活5min").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfHusk;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(HuskSpawnController.mobName).withStyle(CustomStyle.styleOfHusk)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    public static boolean isOn(Player player) {
        return Compute.hasCurios(player, NewRuneItems.HUSK_NEW_RUNE.get());
    }

    public static Map<String, Queue<Integer>> soulCollectionMap = new HashMap<>();

    public static void onKill(Player player, Mob mob) {
        if (!isOn(player)) return;
        handleQueue(player).add(Tick.get());
        ParticleProvider.dustParticle(player, mob.getEyePosition().add(0, 0, 0), 0.3, 8, CustomStyle.styleOfHusk.getColor().getValue());
    }

    @Override
    public void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, NewRuneItems.HUSK_NEW_RUNE.get());
            return;
        }
        int size = handleQueue(player).size();
        Compute.sendEffectLastTime(player, NewRuneItems.HUSK_NEW_RUNE.get(), size, true);
    }

    public static double damageEnhance(Player player) {
        if (!isOn(player)) return 0;
        return handleQueue(player).size() * 0.01;
    }

    public static Queue<Integer> handleQueue(Player player) {
        int tick = Tick.get();
        String name = player.getName().getString();
        if (!soulCollectionMap.containsKey(name)) soulCollectionMap.put(name, new ArrayDeque<>());
        Queue<Integer> queue = soulCollectionMap.get(name);
        queue.removeIf(integer -> tick - integer > 6000);
        while (queue.size() > 20) queue.remove();
        return queue;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
