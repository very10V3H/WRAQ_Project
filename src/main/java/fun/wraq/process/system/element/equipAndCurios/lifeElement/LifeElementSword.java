package fun.wraq.process.system.element.equipAndCurios.lifeElement;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class LifeElementSword extends WraqSword implements ActiveItem {

    public LifeElementSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 600d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.30d);
        Element.LifeElementValue.put(this, 2d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("化作春泥").withStyle(style));
        components.add(Component.literal(" 失去").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("80%当前")).
                append(Component.literal("，并在10s内为你回复").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.health("200%消耗的等额")));
        ComponentUtils.coolDownTimeDescription(components, 25);
        Compute.DescriptionPassive(components, Component.literal("护花").withStyle(style));
        components.add(Component.literal(" 处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("化作春泥").withStyle(style)).
                append(Component.literal("状态时，根据5s内回复的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("，为你提供等同于回复量12.5%的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("")));
        components.add(Component.literal(" 多件生机武器的效果将不会叠加").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 落红不是无情物，化作春泥更护花\uD83C\uDF37").withStyle(ChatFormatting.ITALIC).withStyle(style));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }


    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static WeakHashMap<Player, Integer> lifeElementActiveLastTick = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> lifeElementActiveHealth = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> lifeElementActiveCoolDown = new WeakHashMap<>();
    public static WeakHashMap<Player, List<ShortTimeStoreHealth>> playerShortTimeStoreHealthMap = new WeakHashMap<>();

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, lifeElementActiveCoolDown, this, lifeElementActiveLastTick, 100, 0, 25)) {
            Compute.playerItemCoolDown(player, this, 25);
            lifeElementActiveHealth.put(player, player.getHealth() * 0.8);
            Compute.decreasePlayerHealth(player, player.getHealth() * 0.8, Component.literal(" 被生机元素吞噬了。").withStyle(CustomStyle.styleOfLife));
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public record ShortTimeStoreHealth(int tickCount, double num) {
    }

    public static void Tick(Player player) {
        if (lifeElementActiveLastTick.containsKey(player) && lifeElementActiveLastTick.get(player) >= Tick.get()) {
            int tickCount = lifeElementActiveLastTick.get(player) - Tick.get();
            Compute.sendEffectLastTime(player, ModItems.LIFE_ELEMENT_SWORD.get().getDefaultInstance(), tickCount, tickCount, true);
            Compute.playerHeal(player, lifeElementActiveHealth.get(player) * 0.01);
        }
    }

    public static void StoreToList(Player player, double num) {
        if (lifeElementActiveLastTick.containsKey(player) && lifeElementActiveLastTick.get(player) > Tick.get()) {
            if (!playerShortTimeStoreHealthMap.containsKey(player))
                playerShortTimeStoreHealthMap.put(player, new ArrayList<>());
            List<ShortTimeStoreHealth> list = playerShortTimeStoreHealthMap.get(player);
            list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount < Tick.get());
            list.add(new ShortTimeStoreHealth(Tick.get() + 100, num));
        }
    }

    public static double ComputeStoreNum(Player player) {
        if (!playerShortTimeStoreHealthMap.containsKey(player)) return 0;
        List<ShortTimeStoreHealth> list = playerShortTimeStoreHealthMap.get(player);
        double sum = 0;
        list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount < Tick.get());
        for (ShortTimeStoreHealth shortTimeStoreHealth : list) sum += shortTimeStoreHealth.num;
        return sum;
    }

    public static double ExAttackDamage(Player player) {
        if (Utils.swordTag.containsKey(player.getMainHandItem().getItem())) return ComputeStoreNum(player) * 0.125;
        return 0;
    }
}
